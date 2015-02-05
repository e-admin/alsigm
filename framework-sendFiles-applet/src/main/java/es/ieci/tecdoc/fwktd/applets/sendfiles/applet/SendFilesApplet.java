package es.ieci.tecdoc.fwktd.applets.sendfiles.applet;

import java.applet.Applet;
import java.io.File;
import java.net.URL;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import netscape.javascript.JSException;
import netscape.javascript.JSObject;
import es.ieci.tecdoc.fwktd.applets.sendfiles.privileged.DeleteFilePrivilegedAction;
import es.ieci.tecdoc.fwktd.applets.sendfiles.privileged.DeleteFilesPrivilegedAction;
import es.ieci.tecdoc.fwktd.applets.sendfiles.privileged.UploadFilesPrivilegedAction;

public class SendFilesApplet extends Applet {

	private static final long serialVersionUID = 1L;
	private static final String SESSIONID="SESSIONID";
	
	
//	private static List<File> filesToUpload;
	private static HashMap<String, File> filesToUpload;
	private static String uploadPath;
	private static String fileToDelete;
	private static boolean deleteFilesAfterUpload=false;
	private static int connectionTimeout;
	private static JSObject jsBrowserWindow=null;
	private static URL codebase;
	private static HashMap<String, String> params;
	private static String sessionId;
	
	@Override
	public void init() {
		System.out.println("sendFilesApplet:init() -> start");
		super.init();
		//filesToUpload = new ArrayList<File>();
		filesToUpload = new LinkedHashMap<String,File>();
		params = new HashMap<String, String>();
		connectionTimeout=3000;
		codebase = getCodeBase();
		
		try{
			jsBrowserWindow = JSObject.getWindow(this);
		}catch (JSException e) {
			System.out.println("Ha ocurrido un error al obtener el objeto JSObject");
		}

		try{
			System.out.println("Llamamos a la funcion finishLoadSendFilesAppletJSFunction");
			jsBrowserWindow.call("finishLoadSendFilesAppletJSFunction", null);
		}catch (Exception e) {
			System.out.println("No se ha podido invocar a la funcion callback finishLoadSendFilesAppletJSFunction, probablemente no esté definida");
		}
		System.out.println("sendFilesApplet:init() -> finish");
	}



	public void configure(String uploadPath, String connectionTimeout, boolean deleteFilesAfterUpload){
		sessionId=getParameter(SESSIONID);
		this.uploadPath=uploadPath;
		this.deleteFilesAfterUpload = deleteFilesAfterUpload;
		 if(connectionTimeout != null){
			 try{
				 Integer  time  =  new Integer(connectionTimeout);
				 this.connectionTimeout  =  time.intValue();
             }catch ( NumberFormatException nfex){
	             System.out.println("El parametro connectiontimeout debe ser un numero. Se usara el valor por defecto: "+this.connectionTimeout);
	         }
	     }
	}


	public boolean addFile(String fileName,String fileToUpload)
	{
		File file = new File(fileToUpload);
		if(filesToUpload.containsKey(fileName))
		{
			System.out.println("Nombre de fichero duplicado.");
			return false;
		}
		if(filesToUpload.containsValue(file))
		{
			System.out.println("Fichero duplicado.");
			return false;
		}
		filesToUpload.put(fileName, file);
		return true;
	}

	public boolean addFile(String fileToUpload)
	{
		File file = new File(fileToUpload);
			if(filesToUpload.containsValue(file))
		{
			System.out.println("Fichero duplicado.");
			return false;
		}
		filesToUpload.put(file.getName(), file);
		return true;
	}
	

	public boolean sendFiles() {	
		Boolean result=Boolean.FALSE;
		if(uploadPath==null || "".equals(uploadPath)) {
			System.out.println("No se ha configurado la dirección de envio.");
		} else {
			result = (Boolean)AccessController.doPrivileged(new UploadFilesPrivilegedAction());
			if(result.booleanValue()) {
				System.out.println("Ficheros subidos correctamente");
				if(this.deleteFilesAfterUpload) {
					System.out.println("Eliminamos los ficheros subidos");
					AccessController.doPrivileged(new DeleteFilesPrivilegedAction());
				}
				filesToUpload.clear();
			} else {
				System.out.println("Ha ocurrido un problema en el envio de ficheros.");
			}
		}
		return result.booleanValue();
	}

	public boolean deleteFile(String file){
		boolean result = false;
		if(file!=null && !file.trim().equals("")) {
			this.fileToDelete=file;
			result = (Boolean)AccessController.doPrivileged(new DeleteFilePrivilegedAction());
			System.out.println("Eliminado el fichero: "+file);
		} else {
			System.out.println("No se ha indicado ningún fichero a eliminar");
		}
		return result;
	}

	public static String getCookie() {
		String result="";
		if(sessionId!=null){
			result="JSESSIONID="+sessionId;
		}	
		return result;
	}

	public static List getCookies(String path) {

		   List cookies = new ArrayList();

		   try {
			   String myCookie = getCookie();
			   if (myCookie.length() > 0) {
				   String[] pairs = myCookie.split(";");

				   String name = null;
				   String value = null;
				   String [] values = null;

				   for (int i = 0; i < pairs.length; i++) {
					   name = null;
					   value = null;
					   values = pairs[i].split("=");

					   if (values.length > 0) {
						   name = values[0];
					   }

					   if (values.length > 1) {
						   value = values[1];
					   }

					   cookies.add(new org.apache.commons.httpclient.Cookie(path, name, value, "/", -1, false));
				   }
			   }

		   } catch (Exception e) {
			   e.printStackTrace();
		   }

		   return cookies;
		}

	

	public void addParam(String name, String value){
		params.put(name, value);
	}

	public void removeParam(String name){
		params.remove(name);
	}

	public static HashMap<String, String> getParams()
	{
		return params;
	}

	public static String getFileToDelete() {
		return fileToDelete;
	}

	public static String getUploadPath() {
		return uploadPath;
	}

	public static void setUploadPath(String uploadPath) {
		SendFilesApplet.uploadPath = uploadPath;
	}

	public static int getConnectionTimeout() {
		return connectionTimeout;
	}

	public static void setConnectionTimeout(int connectionTimeout) {
		SendFilesApplet.connectionTimeout = connectionTimeout;
	}

	public static JSObject getJsBrowserWindow() {
		return jsBrowserWindow;
	}

	public static void setJsBrowserWindow(JSObject jsBrowserWindow) {
		SendFilesApplet.jsBrowserWindow = jsBrowserWindow;
	}

	public static URL getCodebase() {
		return codebase;
	}

	public static void setCodebase(URL codebase) {
		SendFilesApplet.codebase = codebase;
	}



	public static HashMap<String, File> getFilesToUpload() {
		return filesToUpload;
	}



	public static void setFilesToUpload(HashMap<String, File> filesToUpload) {
		SendFilesApplet.filesToUpload = filesToUpload;
	}

}
