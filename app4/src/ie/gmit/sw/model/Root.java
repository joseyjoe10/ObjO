package ie.gmit.sw.model;

public class Root {
	   private static Node instance = null;
	   protected Root() {
	      // Exists only to defeat instantiation.
	   }
	   public static Node getInstance() {
	      if(instance == null) {
	         instance = new Node("root",null);
	      }
	      return instance;
	   }
	}
