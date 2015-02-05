package ieci.tdw.applets.idocscan;

import ieci.tdw.applets.idocscan.exceptions.UserCancelException;
import ieci.tdw.applets.idocscan.helpers.InstallerHelper;
import ieci.tdw.applets.idocscan.helpers.ZipHelper;
import ieci.tdw.applets.idocscan.privileged.PrivilegedActionUploadImpl;

import java.applet.AppletContext;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JApplet;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import netscape.javascript.JSObject;

import org.apache.commons.httpclient.Cookie;
import org.jawin.COMException;
import org.jawin.DispatchPtr;

public class InvesDocApplet extends JApplet {
	
	private InstallerHelper helper;
	
	private static String m_documentTypeId = null;

	private static JSObject jsBrowserWindow = null;

	private static String dllDir = null;

	private static String activexName = null;

	public static QueueSelectedFiles filesToUpload;

	public static URL codeBase = null;

	private URL downloadBase = null;
	
	private AppletContext m_Context;

	private boolean running;

	private String m_ver;

	private String m_ven;

	private boolean isReady = false;

	private static String sessionId = null;
	
	private static String getSessionId(){
		return sessionId;
	}
	
	public InvesDocApplet() {
		
		Debug.log("creating Applet");

		filesToUpload = new QueueSelectedFiles();
		running = false;
	}

   public void init() {
	   
		Debug.log("Initializating Applet");

		sessionId = getParameter("sessionId");
		
		codeBase = getCodeBase();
		
		Debug.log("Applet codeBase: " + codeBase);

        String pDownloadBase = getParameter("downloadBase");
        if ((pDownloadBase != null) && (pDownloadBase.trim().length() > 0)) {
            try {
                downloadBase = new URL(pDownloadBase);
            } catch(MalformedURLException mue) {
            	try {
            		downloadBase = new URL(getCodeBase(), pDownloadBase);
            	} catch(MalformedURLException mue2) {
            		downloadBase = getCodeBase();
            	}
            }
        } else {
            downloadBase = getCodeBase();
        }
        
        Debug.log("Applet downloadBase: " + downloadBase);

		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			Debug.log("Error al establecer el look&feel: " + e.toString());
		}

		try {
			m_Context = getAppletContext();
			Debug.log("Applet m_Context: " + m_Context);
			
			helper = InstallerHelper.getInstance();
			Debug.log("Installer helper: " + helper.getClass().getName());
			
		} catch (Exception e) {
			handle(e);
		}
	}

   public void start()
   {
      Debug.log("starting the applet");

      if (running) {
    	  return;
      }

      running = true;
      
      if (!isLibrariesInstalled()) {
			boolean installed = installLibraries();
			if (!installed) {
				JOptionPane.showMessageDialog(null, 
						Messages.getString("applet.warning.libraries.notInstalled"), 
						Messages.getString("applet.warning.title"),
						JOptionPane.WARNING_MESSAGE);
				isReady = false;
				return;
			}
		}

      String jsObjectClassString = "netscape.javascript.JSObject";
      boolean jsObjectFound = false;

      jsBrowserWindow = null;

      try
      {
         Class.forName(jsObjectClassString);
         jsObjectFound = true;
      }
      catch (ClassNotFoundException e1)
      {
         Debug.log("Exception[ClassNotFoundException]: " + e1.getMessage());
      }
      catch (UnsatisfiedLinkError e2)
      {
         Debug.log("Exception[UnsatisfiedLinkError]: " + e2.getMessage());
      }
      catch (NoClassDefFoundError e3)
      {
         Debug.log("Exception[NoClassDefFoundError]: " + e3.getMessage());
      }

      if (jsObjectFound)
      {
         try
         {
            jsBrowserWindow = JSObject.getWindow(this);
            Debug.log("jsBrowserWindow: " + jsBrowserWindow);

         }
         catch (Exception e)
         {
            Debug.log("Exception: " + e.getMessage());
         }
      }

      if (checkJavaVersion())
      {
         Configurator.readConfiguration(this);

         isReady = true;
      }
      else
      {
         isReady = false;
      }

      dllDir = Configurator.getProperty("dllDir");
      activexName = Configurator.getProperty("activexName");

      Debug.logToFile(this, "Scan Dll path: " + dllDir);
      Debug.logToFile(this, "ActiveX Name: " + activexName);
      Debug.logToFile(this, "applet Started");
   }

   public boolean jsIsReady()
   {
      Debug.logToFile(this, "JavaScript call: jsIsReady(): " + isReady);

      return this.isReady;
   }

   private boolean checkJavaVersion()
   {

      String javaVersion = System.getProperty("java.class.version");
      int dbJavaVersion = Integer.parseInt(javaVersion.substring(0, 2));

      m_ver = System.getProperty("java.version");
      m_ven = System.getProperty("java.vendor");

      Debug.log("javaVersion: " + javaVersion);
      Debug.log("dbJavaVersion: " + dbJavaVersion);
      Debug.log("m_ver: " + m_ver);
      Debug.log("m_ven: " + m_ven);

      return true;

   }

   public boolean isReady()
   {
      return this.isReady;
   }

   public int jsGetTotalProgressbarValue()
   {
      return -1;
   }

   public int jsAddFile(String filePath)
   {
      Debug.logToFile(this, "file to added: " + filePath);

      int result = addFile(filePath);

      Debug.logToFile(this, "isFileAdded: " + result);

      return result;
   }

   public int addFile(final String filePath)
   {
      Debug.logToFile(this, "addFile[file]: " + filePath);

      if (isReady())
      {
         Integer result = (Integer) AccessController.doPrivileged(new PrivilegedAction()
         {

            public Object run()
            {
               try
               {
                  File file = MyFile.rename(filePath);

                  Debug.logToFile(this, "file to add : " + "_" + file.getName());
                  filesToUpload.addFile(file);

                  return new Integer(1);

               }
               catch (Exception e)
               {
                  Debug.logToFile(this, "Exception adding File. Cause: "
                           + e.getMessage());
                  e.printStackTrace();
               }
               return new Integer(0);
            }
         });

         return result.intValue();
      }
      else
      {
         Debug.logToFile(this, "Applet is not ready");

         return 0;
      }
   }

   public int jsUploadFiles() throws MalformedURLException
   {
      Debug.logToFile(this, "Uploading File");

      Integer returnedValue = (Integer) AccessController.doPrivileged(new PrivilegedActionUploadImpl(getParameter("sessionID")));

      Debug.logToFile(this, "Uploading File returned value: " + returnedValue);

      if (returnedValue.intValue() == 1)
      {
         removeAllFile(filesToUpload);
         filesToUpload.removeAllFiles();
         reset();
      }

      return returnedValue.intValue();
   }

   private void reset() {
      m_documentTypeId = null;
   }

   public int jsGetFileNumber()
   {
      if (isReady())
      {
         Integer size = (Integer) AccessController.doPrivileged(new PrivilegedAction()
         {

            public Object run()
            {
               QueueSelectedFiles qsf = getQueueSelectedFiles();

               if (qsf == null)
               {
                  return new Integer(0);
               }
               else
               {
                  Integer size = new Integer(qsf.size());

                  Debug.logToFile(this, "files enqueuequed: " + size);
                  return size;
               }
            }
         });

         return size.intValue();
      }
      else
      {
         Debug.logToFile(this, "Applet is not ready");

         return 0;
      }
   }

   private QueueSelectedFiles getQueueSelectedFiles()
   {
      return filesToUpload;
   }

   public String jsGetFileExt(int index)
   {
      String fileExt = getFileExt(index);

      Debug.logToFile(this, "fileExt: " + fileExt);

      return fileExt;
   }

   public String getFileExt(final int i)
   {
      if (isReady())
      {
         String fileExt = (String) AccessController.doPrivileged(new PrivilegedAction()
         {

            public Object run()
            {
               QueueSelectedFiles qsf = getQueueSelectedFiles();

               if (qsf == null)
               {
                  return "";
               }
               else
               {
                  File file = qsf.getFileAt(i);
                  String fileExt = FileUtil.getFileExt(file.getName());

                  Debug.logToFile(this, "startup() jsGetFileAt() = " + fileExt);

                  return fileExt;
               }
            }
         });
         return fileExt;
      }
      else
      {
         Debug.logToFile(this, "Applet is not ready");

         return "";
      }
   }

   public String jsGetFile(int index)
   {
      String fileName = getFile(index);

      Debug.logToFile(this, "fileName: " + fileName);

      return fileName;
   }

   public String getFile(final int i)
   {
      if (isReady())
      {
         String file = (String) AccessController.doPrivileged(new PrivilegedAction()
         {

            public Object run()
            {
               QueueSelectedFiles qsf = getQueueSelectedFiles();

               if (qsf == null)
               {
                  return null;
               }
               else
               {
                  File file = qsf.getFileAt(i);

                  Debug.logToFile(this, "startup() jsGetFileAt() = "
                           + file.getName());

                  return file.getName();
               }
            }
         });
         return file;
      }
      else
      {
         Debug.logToFile(this, "Applet is not ready");
         return null;
      }
   }

   public int jsGetFileSize(int index)
   {
      int fileSize = getFileSize(index);

      Debug.logToFile(this, "fileSize: " + fileSize);

      return fileSize;
   }

   public int getFileSize(final int i)
   {
      if (isReady())
      {
         Integer fileSize = (Integer) AccessController.doPrivileged(new PrivilegedAction()
         {

            public Object run()
            {
               QueueSelectedFiles qsf = getQueueSelectedFiles();

               if (qsf == null)
               {
                  return new Integer(0);
               }
               else
               {
                  return new Integer((int) qsf.getFileAt(i).length());
               }
            }
         });

         return fileSize.intValue();
      }
      else
      {
         Debug.logToFile(this, "Applet is not ready");

         return 0;
      }
   }

   public void jsRemoveFile(int index)
   {
      Debug.logToFile(this, "File index to remove: " + index);
      removeFile(index);
   }

   public void removeFile(final int i)
   {
      if (isReady())
      {
         AccessController.doPrivileged(new PrivilegedAction()
         {

            public Object run()
            {
               QueueSelectedFiles qsf = getQueueSelectedFiles();

               Debug.logToFile(this, "QueueSelectedFiles: " + qsf);

               if (qsf == null)
               {
                  return null;
               }
               else
               {
                  File file = qsf.getFileAt(i);

                  qsf.removeFileAt(i);
                  Debug.logToFile(this, "File removed from QueueSelectedFiles");

                  file.delete();
                  Debug.logToFile(this, "File deleted from System");

                  return null;
               }
            }
         });
      }
      else
      {
         Debug.logToFile(this, "Applet is not ready");
      }

      return;
   }

   public void jsRemoveAllFile()
   {
      Debug.logToFile(this, "Removing all scanned files");

      final QueueSelectedFiles qsf = getQueueSelectedFiles();

      removeAllFile(qsf);
      reset();
   }

   public void removeAllFile(final QueueSelectedFiles qsf)
   {
      if (isReady())
      {
         AccessController.doPrivileged(new PrivilegedAction()
         {

            public Object run()
            {

               if (qsf == null)
                  return null;
               else
               {
                  if (qsf.size() > 0)
                  {
                     File file = qsf.getFileAt(0);
                     String path = FileUtil.getPath(file);

                     Debug.log(path);

                     File file1 = new File(path);
                     File[] listFiles = file1.listFiles();
                     int listCount = listFiles.length;
                     File tmpFile;

                     for (int i = 0; i < listCount; i++)
                     {
                        tmpFile = listFiles[i];

                        tmpFile.delete();
                     }

                  }

                  qsf.removeAllFiles();

                  return null;
               }
            }
         });
      }
      else
      {
         Debug.logToFile(this, "Applet is not ready");
      }

      return;
   }

   public void showStatus(String msg)
   {
      super.showStatus(msg);
   }

   public String getVersion()
   {
      return m_ver;
   }

   public String getVendor()
   {
      return m_ven;
   }

	public static String getCookie() {
		try {
			JSObject myDocument = (JSObject) jsBrowserWindow.getMember("document");
			String myCookie = (String) myDocument.getMember("cookie");
			Debug.log("cookie: " + myCookie);

			if (myCookie.length() > 0) {
				return myCookie;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
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

					if (name.trim().equalsIgnoreCase("JSESSIONID")){
						cookies.add(new Cookie(path, name.trim(), getSessionId(), "/", -1, false));
					}else{
						cookies.add(new Cookie(path, name.trim(), value, "/", -1, false));
					}
			   }
		   }
		   
	   } catch (Exception e) {
		   e.printStackTrace();
	   }

	   return cookies;
	}

   public void jsSetDocumentTypeId(String documentTypeId) {
		Debug.logToFile(this, "documentTypeId: " + documentTypeId);
		m_documentTypeId = documentTypeId;
	}

	public static String jsGetDocumentTypeId() {
		return m_documentTypeId;
	}

   public void jsStartScanner() {
		Debug.logToFile(this, "Starting Scanner");
		startScanner();
	}

   public void startScanner() {
		if (isReady()) {
			AccessController.doPrivileged(new PrivilegedAction() {

				public Object run() {
					DispatchPtr iDocScan = null;
					Object scannedFiles[];

					if ((activexName == null) && (activexName.equals(""))) {
						return "error";
					}

					try {

						if ((dllDir != null) && (!dllDir.equals(""))) {
							Debug.logToFile(this,
									"loading scan ddl from path: " + dllDir);
							System.setProperty("org.jawin.hardlib", dllDir);
						}

						iDocScan = new DispatchPtr(activexName);

						boolean scanReady = false;
						try {
							// Suponemos que el escaner esta configurado
							iDocScan.invoke("Initialize");
							scanReady = true;
						} catch (COMException come) {
						}

						if (!scanReady) {
							// No esta configurado, asi que lo configuramos
							Debug.logToFile(this, "Configurando Scaner");
							iDocScan.invoke("ConfigureScan");
							iDocScan.invoke("Initialize");
						}

						scannedFiles = (Object[]) iDocScan.invoke("UINewFiles");
						Debug.logToFile(this, "number of scanned files: "
								+ scannedFiles.length);

						for (int i = 0; i < scannedFiles.length; i++) {
							String scannedFilePath = (String) scannedFiles[i];

							addFile(scannedFilePath);

							Debug.logToFile(this, "Scanned File Number "
									+ (i + 1) + " : " + scannedFilePath);
						}

						// iDocScan.invoke("Terminate");
						iDocScan.close();
					} catch (Exception e) {
						handle(e);
					}

					return null;
				}
			});
		} else {
			JOptionPane.showMessageDialog(null, Messages
					.getString("applet.warning.not.ready"), Messages
					.getString("applet.warning.title"),
					JOptionPane.WARNING_MESSAGE);
			Debug.logToFile(this, "Applet is not ready");
		}

		return;
	}

   public void jsConfigureScanner() {
		this.configureScanner();
	}
   
   public void configureScanner() {
		if (isReady()) {
			AccessController.doPrivileged(new PrivilegedAction() {

				public Object run() {
					DispatchPtr iDocScan = null;

					if ((activexName == null) && (activexName.equals(""))) {
						return "error";
					}

					try {
						if ((dllDir != null) && (!dllDir.equals(""))) {
							Debug.logToFile(this,
									"loading scan ddl from path: " + dllDir);
							System.setProperty("org.jawin.hardlib", dllDir);
						}

						iDocScan = new DispatchPtr(activexName);

						Debug.logToFile(this, "Configurando Scaner");
						iDocScan.invoke("ConfigureScan");
						iDocScan.close();
					} catch (Exception e) {
						handle(e);
					}

					return null;
				}
			});
		} else {
			JOptionPane.showMessageDialog(null, Messages
					.getString("applet.warning.not.ready"),
					Messages.getString("applet.warning.title"),
					JOptionPane.WARNING_MESSAGE);
			Debug.logToFile(this, "Applet is not ready");
		}

		return;
	}

	public boolean isLibrariesInstalled() {
		
        Boolean installed = (Boolean)AccessController.doPrivileged(new PrivilegedAction() {

            public Object run() {
            	try {
            		String libNames[] = new String [] { "jawin.dll" };

            		boolean flag = true;
            		for (int i = 0; i < libNames.length && flag; i++) {
            			File file = helper.getLibFile(libNames[i]);
            			flag = file != null && file.exists();
            		}

	                return Boolean.valueOf(flag);
            	} catch (Exception e) {
            		InvesDocApplet.handle(e);
	                return Boolean.FALSE;
            	}
            }
        });
        return installed.booleanValue();
    }	

	public boolean installLibraries() {

		Boolean result = (Boolean) AccessController.doPrivileged(
				new PrivilegedAction() {

			public Object run() {
				try {
					int i = JOptionPane.showConfirmDialog(null,
							Messages.getString("applet.installation.message"),
							Messages.getString("applet.installation.title"),
							JOptionPane.YES_NO_OPTION, 
							JOptionPane.INFORMATION_MESSAGE);

					if (i != 0) {
						return Boolean.FALSE;
					}

					helper.doBeforeLibrariesInstallation();

					ZipHelper.getInstance().unzip(
							new URL(downloadBase + "/" + helper.getLibrariesZipName()),
							helper.getLibraryDir());

					helper.doAfterInstallation();
					
				} catch (Exception exception) {
					handle(exception);
					return Boolean.FALSE;
				}

				return Boolean.TRUE;
			}
		});
		
		return result.booleanValue();
	}

	public static void handle(Throwable throwable) {
		Debug.log("ERROR: " + throwable.toString());
		throwable.printStackTrace();
		
		if (!(throwable instanceof UserCancelException)) {
			JOptionPane.showMessageDialog(null,
					throwable.getLocalizedMessage(), 
					Messages.getString("applet.error.title"), 
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public void destroy() {
		super.destroy();
	}

}