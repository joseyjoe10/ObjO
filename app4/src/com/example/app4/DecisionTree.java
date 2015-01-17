package com.example.app4;

import ie.gmit.sw.model.Node;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DecisionTree extends ActionBarActivity {

	final Context context = this;
	Node currentNode;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_decision_tree);
		Button button1 = new Button(context);
		
		 try {
			 Node root=deSerializeTree("someFile.ser");		
			nextLevel(root);
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	}
	
	
	
	
	
	public void nextLevel(Node node){
		
		final LinearLayout layout1 = (LinearLayout) findViewById(R.id.treeLayout2);
		if(node!=null && node.children()!=null){
			for(final Node node1:node.children()){
				
				if(node1!=null){
					Button button1 = new Button(context); 
					button1.setText(node1.getName());
					layout1.addView(button1);
					 button1.setOnClickListener(new View.OnClickListener() {
	     	            public void onClick(View view) {
	     	            	if (node1.isLeaf() == true) {
								layout1.removeAllViews();

								ImageView imageview = new ImageView(context);
								imageview.setImageBitmap(MyApplication.helper.findPic(node1.getName()+".png"));
								int dimens = 420;
								float density = context.getResources().getDisplayMetrics().density;
								int finalDimens = (int)(dimens * density);

								LinearLayout.LayoutParams imgvwDimens = 
								        new LinearLayout.LayoutParams(finalDimens, finalDimens);
								imageview.setLayoutParams(imgvwDimens);
								layout1.addView(imageview);
								Button b1 =new Button(context);
								b1.setText("Select");
								b1.setOnClickListener(new View.OnClickListener() {
				     	            public void onClick(View view) {
				     	            	 
				     	            	
				     	           }
				     	        });
								layout1.addView(b1);

								
								
								//layout1.setContentView(R.layout.custom);
								currentNode=node1;
								Toast.makeText(context, "fuck",Toast.LENGTH_SHORT).show();
								;
							} else {

								layout1.removeAllViews();
								nextLevel(node1);

							}
	     	            	/*Button b = (Button)view;
	     	                String buttonText = b.getText().toString();
	     	            	layout1.removeAllViews();
	     	            	String s = null;
	     	            	String s1=b.getText().toString();            	            	
	     	               //;parentNode=root.recursiveSearch(s1,root);
	     	               nextLevel(node1);*/
	     	            }
	     	        });
						
						
				}
			}
		}
		/*else {
			Node newNode=MyApplication.helper.getNode();
			ImageView imageView= new ImageView(context);
			imageView.setImageBitmap(newNode.getImage());			
			node.addChild(newNode);
			Toast.makeText(context,"add leaf here", Toast.LENGTH_SHORT).show();
		}*/
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.decision_tree, menu);
		return true;
	}
	 public Node deSerializeTree(String fileName) throws IOException, ClassNotFoundException
	    {
	    	FileInputStream fis = openFileInput(fileName);
	    	ObjectInputStream is = new ObjectInputStream(fis);
	    	
	    	@SuppressWarnings("unchecked")
			Node root =(Node) is.readObject();
	      /*  while(is.readObject()!=null)
	        {
	        	root.addChild((Node) is.readObject());
	        	
	        }*/
	    	
	    	
	    	
	    	is.close();
	    	fis.close();
			return root;
	    	
	    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
