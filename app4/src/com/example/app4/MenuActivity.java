package com.example.app4;

import ie.gmit.sw.model.Node;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;



public class MenuActivity extends Activity {

	final Context context = this;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		try {

			Node root = deSerializeTree("someFile.ser");

			if (root == null) {
				Toast.makeText(context, "root not exist",
						Toast.LENGTH_SHORT).show();
				root = new Node("root", null);
				MyApplication.helper.setRoot(root);
				root = MyApplication.helper.getRoot();
				

			} else if (root != null) {
				//Toast.makeText(context, "root exist", Toast.LENGTH_SHORT).show();
				MyApplication.helper.setRoot(root);
				root = MyApplication.helper.getRoot();
				

			}

		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();

		}
		
		Button editButton= (Button) findViewById(R.id.editBtn); 
		editButton.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View view) {
	            	Intent intent = new Intent(context, MainActivity.class);
	                 startActivity(intent); 
	            	
	            	
	            }
		});
		Button cameraButton= (Button) findViewById(R.id.cameraBtn);
		cameraButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	Intent intent = new Intent(context, CameraActivity.class);
                 startActivity(intent); 
            	
            	
            }
	});
		
	          
		
	}

	private List<File> getListFiles(File parentDir) {
		ArrayList<File> inFiles = new ArrayList<File>();
		File[] files = parentDir.listFiles();
		for (File file : files) {
			if (file.isFile()) {
				inFiles.add(file);
			} 
		}
		return inFiles;
	}
	public boolean fileExistance(String fname) {
		File file = getBaseContext().getFileStreamPath(fname);
		return file.exists();
	}
	public Node deSerializeTree(String fileName) throws IOException,ClassNotFoundException {
		if (fileExistance(fileName) == true) {
			FileInputStream fis = openFileInput(fileName);
			ObjectInputStream is = new ObjectInputStream(fis);

			Node root = (Node) is.readObject();
			is.close();
			fis.close();
			//Toast.makeText(context, "root exist", Toast.LENGTH_SHORT).show();
			MyApplication.helper.setRoot(root);
			// showLevel(root);

			List<Pair> pics = new ArrayList<Pair>() ;
			ContextWrapper cw = new ContextWrapper(getApplicationContext());
			// path to /data/data/yourapp/app_data/imageDir
			File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
			/*if(directory!=null){
				Toast.makeText(MainActivity.this, directory.list().toString(),Toast.LENGTH_SHORT).show();
				
			}*/
			List<File> picFiles = getListFiles(directory);		
			
			for (File f : picFiles) {
				Pair newPair= new Pair(f.getName(),loadImageFromStorage(directory.getAbsolutePath(), f.getName()));
				//Toast.makeText(MainActivity.this, f.getName(),Toast.LENGTH_SHORT).show();
				MyApplication.helper.bitmapList.add(newPair);
			}
			/*// lists all the files into an array
			if(pics!=null){
				MyApplication.helper.bitmapList=pics;
			}*/
			//Toast.makeText(this,String.valueOf(MyApplication.helper.bitmapList.size()),Toast.LENGTH_SHORT).show();

			return root;
		}
		return null;

		/*
		 * while(is.readObject()!=null) { root.addChild((Node) is.readObject());
		 * 
		 * }
		 */

	}
	private Bitmap loadImageFromStorage(String path, String picName) {

		try {
			File f = new File(path, picName);
			Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));

			return b;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;

	}
	
	
}
