package es.ieci.tecdoc.isicres.admin.core;

public class UtilsBD {
	
	private static final String[] FOREIGN_KEY_WORDS = new String[]{
		"FOREIGN KEY",
		"CONSTRAINT"
	};
	
	private static final String[] PRIMARY_KEY_WORDS = new String[]{
		"PRIMARY KEY",
		"CONSTRAINT"
	};

	private static final String[] UNIQUE_KEY_WORDS = new String[]{
		"DUPLICATE",
		"UNIQUE",
		"CONSTRAINT"
	};

	
	public static boolean isErrorForeignKey(String msg){
		
		for(int i=0;i<FOREIGN_KEY_WORDS.length;i++){
			if(msg.toUpperCase().indexOf(FOREIGN_KEY_WORDS[i]) != -1){
				return true;
			}
		}
		
	return false;
	}
	
	public static boolean isErrorPrimaryKey(String msg){
		
		for(int i=0;i<PRIMARY_KEY_WORDS.length;i++){
			if(msg.toUpperCase().indexOf(PRIMARY_KEY_WORDS[i]) != -1){
				return true;
			}
		}
		
	return false;
	}	
	
	public static boolean isErrorDuplicateKey(String msg){
		
		for(int i=0;i<UNIQUE_KEY_WORDS.length;i++){
			if(msg.toUpperCase().indexOf(UNIQUE_KEY_WORDS[i]) != -1){
				return true;
			}
		}
		
	return false;
	}	
	
}
