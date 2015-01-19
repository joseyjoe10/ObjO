package com.example.app4;

//import com.example.camera.R;

import java.io.IOException;
import java.util.Calendar;

import ie.gmit.sw.model.Node;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CameraActivity extends Activity implements LocationListener{
	final Context context = this;
	String nodeName = null;
	Bitmap bitmap ;
	String parentNode = null;
	static final int REQUEST_IMAGE_CAPTURE = 1;
	private ImageView imageView;
	protected TextView mLatitudeText, mLongitudeText, mLastLocation;
	LocationManager mLocationManager;
	private static final int RESULT_SETTINGS = 1;
	

	private void dispatchTakePictureIntent() {
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
			startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
			Bundle extras = data.getExtras();
			Bitmap imageBitmap= (Bitmap) extras.get("data");
			imageView = (ImageView) findViewById(R.id.imageView1);
			imageView.setImageBitmap(imageBitmap);
			bitmap=imageBitmap;
		}
			
	}

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);
		parentNode = MyApplication.helper.getParent();
		Toast.makeText(context, parentNode, Toast.LENGTH_SHORT).show();	
	
		dispatchTakePictureIntent();
		
		/*
		 * if(parentNode==null){
		 * 
		 * showDialogue1(); } else{ dispatchTakePictureIntent();
		 * showDialogue2();
		 * 
		 * 
		 * 
		 * }
		 */

		Button addButton = (Button) findViewById(R.id.add);
		addButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (parentNode != null) {
					
					showDialogue1();
					
					
				} else {
					//showDialogue2();
					Node newNode = new Node(null, null);
					//MyApplication.helper.myMap.put(nodeName, bitmap);
					//newNode.setPosition(location.getLatitude(),location.getLongitude());					
					MyApplication.helper.setNode(newNode);
					Intent intent = new Intent(context, DecisionTree.class);
					startActivity(intent);
					
				}

			}

		});
		Button deleteButton = (Button) findViewById(R.id.deleteBtn);
		deleteButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				((ImageView) findViewById(R.id.imageView1)).setImageResource(android.R.color.transparent);

			}

		});
	}

	
	public void showDialogue1() {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("Add Leaf Node To Tree");

		// Set up the input
		final EditText input = new EditText(context);

		// Specify the type of input expected; this, for example, sets the input
		// as a password, and will mask the text
		input.setInputType(InputType.TYPE_CLASS_TEXT);
		builder.setView(input);

		// Set up the buttons
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				nodeName = input.getText().toString();
				Node newNode = new Node(nodeName, null);
				newNode.setLeaf(true);
				Bitmap b=bitmap;
				MyApplication.helper.setBitmap(b);
				//MyApplication.helper.getMyMap().put(input.getText().toString(), b);
				MyApplication.helper.setNode(newNode);
				Intent intent = new Intent(context, MainActivity.class);
				startActivity(intent);
				

			}
		});
		builder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});

		builder.show();
	}

	public void showDialogue2() {

		Node newNode= new Node("new",null);
		MyApplication.helper.setNode(newNode);
		Intent intent = new Intent(context, DecisionTree.class);
		startActivity(intent);
		
	}

	 @Override
	    public void onLocationChanged(Location location) {
	        
	    	if (location != null) {
	           
	            mLocationManager.removeUpdates(this);
	            mLatitudeText.setText(String.valueOf("Latitude: "+location.getLatitude()));
	        	mLongitudeText.setText(String.valueOf("Longitude: "+location.getLongitude())); 
	        }
	    	
	    	
	    }

	    @Override
	    public void onProviderDisabled(String provider) {
	    	
	    	Toast.makeText( getApplicationContext(),"Gps Disabled", Toast.LENGTH_SHORT ).show();
	    }
	    
	    @Override
	    public void onProviderEnabled(String provider) {
	    	
	    	Toast.makeText( getApplicationContext(),"Gps Enabled", Toast.LENGTH_SHORT ).show();   
	    }
	    
	    @Override
	    public void onStatusChanged(String provider, int status, Bundle extras) {
	    }
	    public void getLocation(){
			mLatitudeText = (TextView) findViewById((R.id.latitude_text));
	        mLongitudeText = (TextView) findViewById((R.id.longitude_text));
	        
	        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

	        final Location location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	        if(location != null && location.getTime() > Calendar.getInstance().getTimeInMillis() - 2 * 60 * 1000) {
	            
	        	mLatitudeText.setText(String.valueOf("Latitude: "+location.getLatitude()));
	        	mLongitudeText.setText(String.valueOf("Longitude: "+location.getLongitude()));
	        }
	        else {
	            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
	        }
		}
	
	
}
