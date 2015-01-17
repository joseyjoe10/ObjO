package com.example.app4;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import ie.gmit.sw.model.Node;
import ie.gmit.sw.model.Root;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	int n = 10;
	// Node root = Root.getInstance();
	Node root = null;
	boolean deletePressed = false;
	Node parentNode = null;
	Node node = null;
	final Context context = this;
	boolean leafLevel = false;
	Bitmap bitmap;
	String path;

	
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		if (MyApplication.helper.getNode() != null) {
			//Toast.makeText(context, MyApplication.helper.getParent(),Toast.LENGTH_SHORT).show();
			root = MyApplication.helper.getRoot();
			Node newNode=MyApplication.helper.getNode();			
			parentNode = root.recursiveSearch(MyApplication.helper.getParent(),root);
			newNode.setParent(parentNode);
			bitmap= MyApplication.helper.getBitmap();
			//path=saveToInternalSorage(bitmap);
			//loadImageFromStorage(path);
			Pair pair= new Pair(newNode.getName()+".png",bitmap);
			MyApplication.helper.bitmapList.add(pair);
			parentNode.addChild(MyApplication.helper.getNode());
			MyApplication.helper.setParent(null);
			MyApplication.helper.setNode(null);
			showLevel(parentNode);

		} else {
			parentNode=MyApplication.helper.getRoot();
			showLevel(parentNode);
			

		}

		Button addButton = (Button) findViewById(R.id.addButton);
		addButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				if (leafLevel == true && parentNode != root) {
					showDialogue();
				} else {
					addNode();
				}
			}
		});

		Button goToDecision = (Button) findViewById(R.id.cameraBtn);
		goToDecision.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				/*
				 * Intent intent = new Intent(context, DecisionTree.class);
				 * startActivity(intent);
				 */
				if (parentNode == null) {
					showLevel(root);
					parentNode = root;
				} else if (parentNode != root) {
					showLevel(parentNode.getParent());
					parentNode = parentNode.getParent();
					Toast.makeText(MainActivity.this, parentNode.getName(),
							Toast.LENGTH_SHORT).show();
				}

			}
		});
		Button deleteButton = (Button) findViewById(R.id.deleteBtn);
		deleteButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {

				Toast.makeText(context, "Select the Node to Delete",
						Toast.LENGTH_SHORT).show();

				deletePressed = true;

				/*
				 * Node root= new Node("root",null);
				 * 
				 * try { serializeTree(root,"someFile.ser"); } catch
				 * (IOException e) { // TODO Auto-generated catch block
				 * e.printStackTrace(); }
				 */
				/*
				 * showDialogue(); String m_Text; AlertDialog.Builder builder =
				 * new AlertDialog.Builder(MainActivity.this);
				 * builder.setTitle("Add Node");
				 * 
				 * // Set up the input final EditText input = new
				 * EditText(MainActivity.this);
				 * //input.setTextColor(Color.WHITE); // Specify the type of
				 * input expected; this, for example, sets the input as a
				 * password, and will mask the text
				 * input.setInputType(InputType.TYPE_CLASS_TEXT);
				 * builder.setView(input);
				 * 
				 * // Set up the buttons builder.setPositiveButton("OK", new
				 * DialogInterface.OnClickListener() {
				 * 
				 * @Override public void onClick(DialogInterface dialog, int
				 * which) { //m_Text = input.getText().toString(); } });
				 * builder.setNegativeButton("Cancel", new
				 * DialogInterface.OnClickListener() {
				 * 
				 * @Override public void onClick(DialogInterface dialog, int
				 * which) { dialog.cancel(); } }); builder.create();
				 * builder.show();
				 * 
				 * 
				 * //Toast.makeText(MainActivity.this, "add clicked",
				 * Toast.LENGTH_SHORT).show();
				 */}
		});

		Button saveButton = (Button) findViewById(R.id.saveBtn);
		saveButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					serializeTree(MyApplication.helper.getRoot(),
							"someFile.ser");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});
	}

		/*Button loadTreeBtn = (Button) findViewById(R.id.loadTreeBtn);
		loadTreeBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {

				Node root;
				try {
					root = deSerializeTree("someFile.ser");
					if (parentNode == null) {
						showLevel(root);
					} else {
						showLevel(parentNode);
					}
					showLevel(root);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				
				 * try { Node root =read();
				 * 
				 * for (Node node :root.children()) { Button button = new
				 * Button(MainActivity.this); button.setText(node.getName());
				 * button.setId(node.hashCode()); final int buttonID =
				 * button.getId();
				 * 
				 * LinearLayout layout = (LinearLayout)
				 * findViewById(R.id.activity_main); layout.addView(button);
				 * 
				 * button.setOnClickListener(new View.OnClickListener() { public
				 * void onClick(View view) { Toast.makeText(MainActivity.this,
				 * "Button clicked index = " + buttonID,
				 * Toast.LENGTH_SHORT).show(); } }); }
				 * 
				 * } catch (StreamCorruptedException e1) { // TODO
				 * Auto-generated catch block e1.printStackTrace(); } catch
				 * (ClassNotFoundException e1) { // TODO Auto-generated catch
				 * block e1.printStackTrace(); } catch (IOException e1) { //
				 * TODO Auto-generated catch block e1.printStackTrace(); }
				 

			}
		});

	}*/

	/*
	 * public String showAlert(){ AlertDialog.Builder alertDialogBuilder = new
	 * AlertDialog.Builder( context);
	 * 
	 * // set title alertDialogBuilder.setTitle("Your Title");
	 * 
	 * final EditText input = new EditText(MainActivity.this);
	 * input.setTextColor(Color.WHITE); // Specify the type of input expected;
	 * this, for example, sets the input as a password, and will mask the text
	 * input.setInputType(InputType.TYPE_TEXT_VARIATION_NORMAL |
	 * InputType.TYPE_TEXT_VARIATION_NORMAL); alertDialogBuilder.setView(input);
	 * // set dialog message alertDialogBuilder
	 * .setMessage("Click yes to exit!") .setCancelable(false)
	 * .setPositiveButton("Yes",new DialogInterface.OnClickListener() { public
	 * void onClick(DialogInterface dialog,int id) { // if this button is
	 * clicked, close // current activity MainActivity.this.finish(); } })
	 * .setNegativeButton("No",new DialogInterface.OnClickListener() { public
	 * void onClick(DialogInterface dialog,int id) { // if this button is
	 * clicked, just close // the dialog box and do nothing dialog.cancel(); }
	 * });
	 * 
	 * // create alert dialog AlertDialog alertDialog =
	 * alertDialogBuilder.create();
	 * 
	 * // show it alertDialog.show(); return null; }
	 */

	// Deserialize
	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	


	

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	

	public void deleteNode(String nodeName) {
		Node node = root.recursiveSearch(nodeName, root);
		Node parent = node.getParent();
		parent.removeChild(node);
		Toast.makeText(MainActivity.this, node.getName() + "was deleted",
				Toast.LENGTH_SHORT).show();
		deletePressed = false;

	}

	public void showDialogue() {
		final Dialog dialog = new Dialog(context);
		dialog.setContentView(R.layout.custom);
		dialog.setTitle("Title...");

		Button btnAdd1 = (Button) dialog.findViewById(R.id.btnAdd1);

		Button btnAdd2 = (Button) dialog.findViewById(R.id.btnAdd2);
		// if button is clicked, close the custom dialog
		btnAdd2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {

				MyApplication.helper.setParent(parentNode.getName());
				Intent intent = new Intent(context, CameraActivity.class);
				// intent.putExtra("nodeName", userInput.getText().toString());
				startActivity(intent);
				dialog.dismiss();
			}
		});
		btnAdd1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				dialog.dismiss();
			}
		});

		dialog.show();
	}

	List<Node> tree1 = new ArrayList<Node>();

	public void serializeTree(Node root, String fileName) throws IOException {
		// Base case
		if (MyApplication.helper.getRoot() == null)
			return;

		// Else, store current node and recur for its children

		/*
		 * tree1.add(root);
		 * 
		 * if(root.children()!=null){ for (int i = 0; i <
		 * root.children().length; i++) {
		 * serializeTree(root.children()[i],fileName); //tree.add(arg0) }
		 * 
		 * }
		 */
		List<Pair> pics = MyApplication.helper.bitmapList;
		if (pics != null) {
			for (Pair s : pics){
				path=saveToInternalStorage(s.getBitmap(), s.getPicName());
			}
				
		}
		//ContextWrapper cw = new ContextWrapper(getApplicationContext());
		// path to /data/data/yourapp/app_data/imageDir
		/*File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
		if(directory!=null){
			Toast.makeText(MainActivity.this, "its there",Toast.LENGTH_SHORT).show();
			
		}
		else{
			Toast.makeText(MainActivity.this, "its not",Toast.LENGTH_SHORT).show();
		}
		List<File> picFiles = getListFiles(directory);
		if(picFiles!=null){
			Toast.makeText(MainActivity.this,String.valueOf(picFiles.size()) ,Toast.LENGTH_SHORT).show();
		}
		else{
			
		}*/
		
		//Toast.makeText(MainActivity.this, directory.getPath(),Toast.LENGTH_SHORT).show();
		
		
		
		
		FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
		ObjectOutputStream os = new ObjectOutputStream(fos);
		os.writeObject(MyApplication.helper.getRoot());
		// Store marker at the end of children
		os.close();
		fos.close();

	}

	public void showLevel(final Node node) {

		final LinearLayout layout1 = (LinearLayout) findViewById(R.id.treeLayout);
		layout1.removeAllViews();
		if (node != null && node.children() != null) {
			for (final Node node1 : node.children()) {

				if (node1 != null) {
					final Button button1 = new Button(context);
					button1.setText(node1.getName());
					layout1.addView(button1);
					button1.setOnClickListener(new View.OnClickListener() {
						public void onClick(View view) {
							if (deletePressed == true) {
								deleteNode(node1.getName());
								ViewGroup layout = (ViewGroup) button1
										.getParent();
								if (null != layout) // for safety only as you
													// are doing onClick
									layout.removeView(button1);
							} else if (node1.isLeaf() == true) {
								layout1.removeAllViews();
								
								ImageView imageview = new ImageView(context);
								Bitmap bitmap=MyApplication.helper.findPic(node1.getName()+".png");
								Toast.makeText(MainActivity.this, MyApplication.helper.findName(node1.getName()+".png"),Toast.LENGTH_SHORT).show();
								//Toast.makeText(MainActivity.this, String.valueOf(MyApplication.helper.bitmapList.size()),Toast.LENGTH_SHORT).show();
								if(bitmap!=null){
									imageview.setImageBitmap(bitmap);
									int dimens = 420;
									float density = context.getResources().getDisplayMetrics().density;
									int finalDimens = (int)(dimens * density);

									LinearLayout.LayoutParams imgvwDimens = 
									        new LinearLayout.LayoutParams(finalDimens, finalDimens);
									imageview.setLayoutParams(imgvwDimens);
									layout1.addView(imageview);
									TextView t= new TextView(context);
									t.setTextSize(42);
									t.setGravity(Gravity.CENTER);
									t.setText(node1.getName());
									layout1.addView(t);
;
						Toast.makeText(MainActivity.this, "Done",Toast.LENGTH_SHORT).show();
								}
								
								
								
								

								

								
								;
							} else {

								parentNode = node1;
								showLevel(node1);

							}
						}
					});

				}
			}
		} else {
			leafLevel = true;
			parentNode = node;
			layout1.removeAllViews();

		}

	}

	private String saveToInternalStorage(Bitmap bitmapImage, String imageName) {
		ContextWrapper cw = new ContextWrapper(getApplicationContext());
		// path to /data/data/yourapp/app_data/imageDir
		File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
		// Create imageDir
		File mypath = new File(directory, imageName + ".png");

		FileOutputStream fos = null;
		try {

			fos = new FileOutputStream(mypath);

			// Use the compress method on the BitMap object to write image to
			// the OutputStream

			bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return directory.getAbsolutePath();
	}

	/*
	 * public void addLeaf(final Node node){ AlertDialog.Builder builder = new
	 * AlertDialog.Builder(MainActivity.this); builder.setTitle("Add Leaf");
	 * 
	 * // Set up the input final EditText input = new
	 * EditText(MainActivity.this);
	 * 
	 * // Specify the type of input expected; this, for example, sets the input
	 * // as a password, and will mask the text
	 * input.setInputType(InputType.TYPE_CLASS_TEXT); builder.setView(input);
	 * 
	 * // Set up the buttons builder.setPositiveButton("OK", new
	 * DialogInterface.OnClickListener() {
	 * 
	 * @Override public void onClick(DialogInterface dialog, int which) { final
	 * LinearLayout layout1 = (LinearLayout) findViewById(R.id.treeLayout);
	 * String buttonText; final Button button1 = new Button(context);
	 * node.setName(input.getText().toString()) ; if (parentNode == null)
	 * parentNode = root;
	 * 
	 * 
	 * parentNode.addChild(node); Toast.makeText(MainActivity.this,
	 * parentNode.getName(),Toast.LENGTH_SHORT).show();
	 * button1.setText(node.getName()); // button1.setId(newNode.hashCode()); n
	 * += 100; button1.setId(n); buttonText = button1.getText().toString();
	 * button1.setOnClickListener(new View.OnClickListener() { public void1
	 * onClick(View view) { layout1.removeAllViews(); ImageView imageview = new
	 * ImageView(context); imageview.setImageBitmap(node.getImage());
	 * layout1.addView(imageview); Toast.makeText(MainActivity.this,
	 * "fuck",Toast.LENGTH_SHORT).show();;
	 * 
	 * } }); layout1.addView(button1);
	 * 
	 * } });
	 * 
	 * 
	 * }
	 */

	public void addNode() {
		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
		builder.setTitle("Add Node");

		// Set up the input
		final EditText input = new EditText(MainActivity.this);

		// Specify the type of input expected; this, for example, sets the input
		// as a password, and will mask the text
		input.setInputType(InputType.TYPE_CLASS_TEXT);
		builder.setView(input);

		// Set up the buttons
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				final LinearLayout layout1 = (LinearLayout) findViewById(R.id.treeLayout);
				String buttonText;
				final Button button1 = new Button(context);
				String nodeName = input.getText().toString();
				if (parentNode == null)
					parentNode = root;

				final Node newNode = new Node(nodeName, parentNode);
				parentNode.addChild(newNode);
				Toast.makeText(MainActivity.this, parentNode.getName(),
						Toast.LENGTH_SHORT).show();
				button1.setText(nodeName);
				// button1.setId(newNode.hashCode());
				n += 1;
				button1.setId(n);
				buttonText = button1.getText().toString();
				button1.setOnClickListener(new View.OnClickListener() {
					public void onClick(View view) {
						Button b = (Button) view;
						String buttonText = b.getText().toString();
						if (deletePressed == true) {
							Toast.makeText(MainActivity.this, "fuck",
									Toast.LENGTH_SHORT).show();
							deleteNode(buttonText);
							ViewGroup layout = (ViewGroup) button1.getParent();
							if (null != layout) // for safety only as you are
												// doing onClick
								layout.removeView(button1);
						} else {
							String s = null;
							String s1 = b.getText().toString();

							parentNode = root.recursiveSearch(s1, root);
							Toast.makeText(MainActivity.this,
									parentNode.getName(), Toast.LENGTH_SHORT)
									.show();

							showLevel(newNode);
						}
						// layout1.removeAllViews();

					}
				});
				layout1.addView(button1);

			}
		});
		builder.setNegativeButton("Cancel",	new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});

		builder.show();
	}

}
