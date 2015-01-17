package com.example.app4;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Application;
import android.graphics.Bitmap;
import ie.gmit.sw.model.Node;
public class Helper  {

	private Node node;
	private String parent;
	private Node root;
	List<Pair> bitmapList = new ArrayList<Pair>();
	
	
	

	public Bitmap findPic(String nodeName){
		for (Pair s : bitmapList)
		    if (s.getPicName().equalsIgnoreCase(nodeName)){
		    	return s.getBitmap();
		    }
		return null;
		      
	}
	 public String findName(String nodeName) {
		 for (Pair s : bitmapList)
			    if (s.getPicName().equalsIgnoreCase(nodeName)){
			    	return s.getPicName();
			    }
			return "its fucking not there!";
	}
	
	

	

	private Bitmap bitmap;
	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	

	

	public Node getRoot() {
		return root;
	}

	public void setRoot(Node root) {
		this.root = root;
	}

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	
    
}
