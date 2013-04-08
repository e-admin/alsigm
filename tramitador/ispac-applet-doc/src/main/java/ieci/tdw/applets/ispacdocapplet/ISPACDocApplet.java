package ieci.tdw.applets.ispacdocapplet;

import ieci.tdw.applets.ispacdocapplet.config.ConfigFactory;
import ieci.tdw.applets.ispacdocapplet.config.LocalConfig;
import ieci.tdw.applets.ispacdocapplet.dto.FileInfo;
import ieci.tdw.applets.ispacdocapplet.logging.Log;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.swing.JApplet;
import javax.swing.JFileChooser;

import netscape.javascript.JSObject;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.contrib.ssl.EasySSLProtocolSocketFactory;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.protocol.Protocol;

public class ISPACDocApplet extends JApplet {

	/**
	 * Nombre del parámetro que almacena la ruta del directorio por defecto.
	 */
	private static final String PARAM_DEFAULT_DIRECTORY = "default.directory";
	
	/**
	 * Nombre del parámetro que indica si hay que eliminar los ficheros anexados.
	 */
	private static final String PARAM_DELETE_UPLOADED_FILES = "delete.uploaded.files";

	private static JSObject jsBrowserWindow = null;

	private boolean initiated = false;
	
	private boolean running = false;
	
	private boolean deleteFiles = false;
	
	private static String sessionId = null;
	
	private static String getSessionId(){
		return sessionId;
	}
	
	/**
	 * Constructor.
	 * 
	 */
	public ISPACDocApplet() {
		super();
		Log.log("Creating Applet");
	}

	/**
	 * Inicializa el applet.
	 */
	public void init() {

		Log.log("Initiating Applet 1.0");

		sessionId = getParameter("sessionID");
		Log.log("sessionID: " + sessionId);
		
		// Cargar la configuración local
		LocalConfig localConfig = ConfigFactory.getLocalConfig();

		// Establecer si se eliminarán los ficheros anezados por defecto
		setDeleteFiles("true".equalsIgnoreCase(localConfig.getProperty(PARAM_DELETE_UPLOADED_FILES)));
		
		this.initiated = true;
		
		Log.log("Applet initiated");
	}

	/**
	 * Inicia la ejecución del applet
	 */
	public void start() {
		
		Log.log("Starting Applet");

		if (running) {
			return;
		}

		running = true;

		boolean jsObjectFound = false;
		jsBrowserWindow = null;

		try {
			Class.forName("netscape.javascript.JSObject");
			jsObjectFound = true;
		} catch (Exception e) {
			Log.log("Error instantiating JSObject class", e);
		}

		if (jsObjectFound) {
			try {
				jsBrowserWindow = JSObject.getWindow(this);
				Log.log("jsBrowserWindow: " + jsBrowserWindow);
			} catch (Exception e) {
				Log.log("Error in JSObject", e);
			}
		}

		Log.log("Applet Started");
	}

	/**
	 * Destruye el applet.
	 */
	public void destroy() {
		
		Log.log("Destroying Applet");
		
		super.destroy();
		
		Log.log("Applet Destroyed");
	}

	public boolean isInitiated() {
		return this.initiated;
	}

	public String getDefaultDirectory() {
		
		Log.log("Getting default directory");
		
		String defaultDirectory = ConfigFactory.getLocalConfig().getProperty(PARAM_DEFAULT_DIRECTORY);
		if (defaultDirectory == null) {
			defaultDirectory = "";
		}
		
		return defaultDirectory;
	}

	public boolean selectDefaultDirectory() {
		
		Log.log("Selecting default directory");
		
		Boolean ret = (Boolean) AccessController.doPrivileged(new PrivilegedAction() {
			
        	public Object run() {
        		
        		JFileChooser fc = new JFileChooser();
        		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        		fc.setLocation(calculateCenter(fc));
        		
        		// Establecer el directorio por defecto
        		String defaultDir = getDefaultDirectory();
        		if ((defaultDir != null) && (defaultDir.trim().length() > 0)) {
        			File defaultDirFile = new File(defaultDir);
        			if (defaultDirFile.exists()) {
        				fc.setCurrentDirectory(defaultDirFile);
        			}
        		}
        		
                int returnVal = fc.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    if (file != null) {

                    	String dir = null;
                    	if (file.isDirectory()) {
                    		dir = file.getAbsolutePath();
                    	} else {
                    		dir = file.getParentFile().getAbsolutePath();
                    	}
                    	
                		if ((dir != null) && (dir.trim().length() > 0)) {
                			try {
                				LocalConfig localConfig = ConfigFactory.getLocalConfig();
                				localConfig.setProperty(PARAM_DEFAULT_DIRECTORY, dir);
                				localConfig.store();
                				
                				return Boolean.TRUE;
                			} catch (Exception e) {
                				Log.log("Error setting default directory", e);
                			}
                		}
                    }
                }

        		return Boolean.FALSE;
        	}
		});
		
		return ret.booleanValue();
	}

	public List getDefaultDirectoryFiles() {
		
		Log.log("Getting default directory files");
		
        return (List) AccessController.doPrivileged(new PrivilegedAction() {

        	public Object run() {
        		
	       		List files = new ArrayList();
	    		
	    		String strdir = getDefaultDirectory();
	    		if ((strdir != null) && (strdir.trim().length() > 0)) {
	    			File dir = new File(strdir);
	    			
	    			if (dir.exists() && dir.isDirectory()) {
	    				File[] content = dir.listFiles();
	    				if ((content != null) && (content.length > 0)){
	    					for (int i = 0; i < content.length; i++) {
	    						if (content[i].isFile()) {
	    							files.add(new FileInfo(content[i]));
	    						}
	    					}
	    					
	    					// Ordenar los ficheros por fecha de última modificación descendente
	    					Collections.sort(files, new Comparator() {
	    						public int compare(Object o1, Object o2) {
	    							return new Long(((FileInfo)o2).getLastModified()).compareTo(
	    									new Long(((FileInfo)o1).getLastModified()));
	    						}
	    						
	    					});
	    				}
	    			}
	    		}
	    		
	    		return files;
        	}
        });
	}

	public int uploadFiles(String documentTypeId, String arrayOfFileNames) {

		Log.log("Uploading Files: " + documentTypeId + " -> " + arrayOfFileNames);

		final String docTypeId = documentTypeId;
		//final String filePath = getDefaultDirectory() + File.separator + fileName;
		final String fileNames = arrayOfFileNames;
		
		Log.log("docTypeId: " + docTypeId);
		//Log.log("filePath: " + filePath);
		
		Integer returnedValue = (Integer) AccessController.doPrivileged(new PrivilegedAction() {

		    public static final String uploadActionPath  =  "uploadScanned.do";
		    
		    public Object run() {
		      
		        Integer   returnedValue = new Integer(0);
		        int       timeout        =  30000;
		        
		        try {

			        HttpState initialState   =  new HttpState();

			        List mycookies = ISPACDocApplet.getCookies(uploadActionPath);
			        if ((mycookies != null) && !mycookies.isEmpty()) {
			        	for (int i = 0; i < mycookies.size(); i++) {
			        		Cookie mycookie = (Cookie) mycookies.get(i);
			                initialState.addCookie(mycookie);
			        	}
			        }
			        
			        // Get HTTP client instance
			        HttpClient client = new HttpClient();
			                
			        client.getHttpConnectionManager().getParams().setConnectionTimeout(timeout);
			        client.setState(initialState);

			        // RFC 2101 cookie management spec is used per default
			        // to parse, validate, format & match cookies
			        client.getParams().setCookiePolicy(CookiePolicy.RFC_2109);

			        //Getting the files to upload
		            URL                     appletURL   =  ISPACDocApplet.this.getCodeBase();
		            String                  path        =  appletURL.getPath();
		            PostMethod              filePost    =  null;
		            URI                     uri         =  null; 
		            ArrayList               filesList   =  new ArrayList();
		            MultipartRequestEntity  request     =  null;
		            Cookie[]                cookies     =  null; 
		            Part[]               parts;
		            
		            //path = FileUtil.getURLPath(path);

		            Log.log("appletURL: "+appletURL);
		            Log.log("path: "+path);
		          
		            
		            
		            if ("https".equalsIgnoreCase (appletURL.getProtocol ())) {  
		               Protocol easyhttps = new Protocol ("https", new EasySSLProtocolSocketFactory (), appletURL.getPort ());
		               client.getHostConfiguration ().setHost (appletURL.getHost(), appletURL.getPort (), easyhttps);
		               
		               uri   =  new URI(null, null, path + uploadActionPath, null, null);
		            } else {
		               uri   =  new URI(appletURL.getProtocol() , null, appletURL.getHost() ,appletURL.getPort() , path + uploadActionPath, null, null);
		            }
		            
		            Log.log("uri: "+uri);
		            
		            filePost = new PostMethod(uri.toString());
		            filePost.getParams().setBooleanParameter(HttpMethodParams.USE_EXPECT_CONTINUE, true);
		            
		            if ((fileNames != null) && (fileNames.trim().length() > 0)) {
			            StringTokenizer tok = new StringTokenizer(fileNames, "|");
			            while (tok.hasMoreTokens()) {
			            	String fileName = tok.nextToken();
			            	Log.log("fileName: "+fileName);
			                File targetFile = new File(getDefaultDirectory() + File.separator + fileName);
			                Log.log("targetFile: "+targetFile);
			                
			                if (targetFile.isFile()) {
				                filesList.add( new FilePart(URLEncoder.encode(targetFile.getName(), "ISO-8859-15"), targetFile) );
				                filesList.add( new StringPart("fileNames", targetFile.getName(), "ISO-8859-15") );
			                }
						}
		            }

		            filesList.add( new StringPart("documentTypeId", docTypeId) );
					   
		            parts    =  (Part[])filesList.toArray( new Part[0] );
		            request  =  new MultipartRequestEntity(parts, filePost.getParams());

		            filePost.setRequestHeader("connection", "Keep-Alive");
		            String cookieReplaced = replaceSessionId();
		            Log.log("cookie(JSESSIONID replaced): " + cookieReplaced);
		            filePost.setRequestHeader("cookie", cookieReplaced);
		            filePost.setRequestEntity(request);
		            
		            Log.log("filePost.setRequestEntity: " + filePost.getURI());

		            cookies = client.getState().getCookies();
		            // Display the cookies
		            Log.log("Present cookies: ");
		            
		            for (int i = 0; i < cookies.length; i++) {
		               Log.log(" - " + cookies[i].getName() + " -> "+ cookies[i].toExternalForm());
		            }
		            //Uploading files
		            int   status   =  client.executeMethod(filePost);
		            Log.log("Status=" + HttpStatus.getStatusText(status));

		            if (status == HttpStatus.SC_OK) {
		    
		            	// Parsear la lista de fichero anexados correctamente
		            	List uploadedFileNames = getUploadedFileNames(filePost);
		            	Log.log("Upload complete");

		            	returnedValue =  new Integer(1);

		            	checkDeleteFile();

						if (isDeleteFiles()) {
							
							if ((fileNames != null) && (fileNames.trim().length() > 0) && !uploadedFileNames.isEmpty()) {
								
								for (int i = 0; i < uploadedFileNames.size(); i++) {
									
									String uploadedFileName = (String) uploadedFileNames.get(i);

						            StringTokenizer tok = new StringTokenizer(fileNames, "|");
						            while (tok.hasMoreTokens()) {
						            	String filename = tok.nextToken();
						            	if (uploadedFileName.equals(filename)) {
							                File targetFile = new File(getDefaultDirectory() + File.separator + filename);
											if (targetFile.delete()) {
												Log.log("File deleted: " + targetFile);
											} else {
												Log.log("File not deleted: " + targetFile);
											}
											break;
						            	}
						            }
								}
				            
				            }
						}

		            } else {
		            	returnedValue = new Integer(0);
		            	Log.log("Upload failed, status=" + HttpStatus.getStatusText(status) + ", response=" + filePost.getResponseBodyAsString());
		            }

		        } catch (FileNotFoundException e) {
		        	Log.log("File not found", e);
		        } catch (IOException e) {
		        	Log.log("I/O exception", e);
		        } catch (Throwable e) {
		        	Log.log("Error uploading file", e);
		        }
		        
		        Log.log("file readed");
		        return returnedValue; 
		    }
		    
		    /**
		     * @return cookie en la que suplanta el identificador de la sesion con el recibido por parametro 
		     */
		    private String replaceSessionId(){
	            List list = getCookies(null);
	            String result = "";
	            boolean jSessionId = false;
	            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
					Cookie obj = (Cookie) iterator.next();
					if (!result.equals("")){
						result += ";";
					}
					String value = obj.getValue();
					if (obj.getName().equals("JSESSIONID")){
						jSessionId = true;
						value = getSessionId();
					}
					result += obj.getName() +  "=" + value;
				}
	            if (!jSessionId){
	            	result += ";JSESSIONID="+getSessionId();
				}
	            return result;
		    }
		    
		    protected List getUploadedFileNames(PostMethod postMethod) throws IOException {
		    	
		    	List filenames = new ArrayList();
		    	
		    	try {
		    		
		    		Properties props = new Properties();
					props.load(postMethod.getResponseBodyAsStream());
					
					String oks = props.getProperty("oks");
					if ((oks != null) && (oks.trim().length() > 0)) {
						StringTokenizer tok = new StringTokenizer(oks, "\",");
						while (tok.hasMoreTokens()) {
							String filename = tok.nextToken();
							Log.log("Nombre de fichero anexado correctamente: " + filename);
							filenames.add(filename);
						}
					}
					
				} catch (IOException e) {
					Log.log("Error al parsear los nombres de los ficheros anexados correctamente", e);
					Log.log("Response: " + postMethod.getResponseBodyAsString());
					throw e;
				}
		    	
		    	return filenames;
		    }
		    
			protected void checkDeleteFile() {
				
				LocalConfig localConfig = ConfigFactory.getLocalConfig();
				boolean defaultValue = "true".equalsIgnoreCase(
						localConfig.getProperty(PARAM_DELETE_UPLOADED_FILES));
				
				if (defaultValue != isDeleteFiles()) {
        			try {
        				localConfig.setProperty(PARAM_DELETE_UPLOADED_FILES, 
        						Boolean.toString(isDeleteFiles()));
        				localConfig.store();
        			} catch (Exception e) {
        				Log.log("Error setting delete files property", e);
        			}
				}
			}
		    
		});

		Log.log("Uploading File returned value: " + returnedValue);

		return returnedValue.intValue();
	}
	
	protected static Point calculateCenter(Container container) {
		
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension containerSize = container.getPreferredSize();

        int x = (screenSize.width/2) - (containerSize.width/2);
        int y = (screenSize.height/2) - (containerSize.height/2);
		
		return new Point(x, y);
	}          		

	public static String getCookie() {
		try {
			JSObject myDocument = (JSObject) jsBrowserWindow.getMember("document");
			String myCookie = (String) myDocument.getMember("cookie");
			Log.log("cookie: " + myCookie);

			if (myCookie.length() > 0) {
				return myCookie;
			}

		} catch (Exception e) {
			Log.log("Error getting cookie", e);
		}
		return "";
	}

	public static List getCookies(String path) {

		List cookies = new ArrayList();

		try {
			String myCookie = getCookie();
			if (myCookie.length() > 0) {
				String[] pairs = myCookie.split(";");

				String name = null;
				String value = null;
				String[] values = null;

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

					if (name.trim().equalsIgnoreCase("JSESSIONID")){
						cookies.add(new Cookie(path, name.trim(), getSessionId(), "/", -1, false));
					}else{
						cookies.add(new Cookie(path, name.trim(), value, "/", -1, false));
					}
				}
			}

		} catch (Exception e) {
			Log.log("Error getting cookies", e);
		}

		return cookies;
	}

	public boolean isDeleteFiles() {
		return deleteFiles;
	}

	public void setDeleteFiles(boolean deleteFiles) {
		this.deleteFiles = deleteFiles;
	}
	
}
