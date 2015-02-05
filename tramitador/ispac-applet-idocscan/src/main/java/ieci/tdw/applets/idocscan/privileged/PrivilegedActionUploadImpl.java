package ieci.tdw.applets.idocscan.privileged;

import ieci.tdw.applets.idocscan.Configurator;
import ieci.tdw.applets.idocscan.Debug;
import ieci.tdw.applets.idocscan.InvesDocApplet;
import ieci.tdw.applets.idocscan.QueueSelectedFiles;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.contrib.ssl.EasySSLProtocolSocketFactory;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.protocol.Protocol;

public class PrivilegedActionUploadImpl implements PrivilegedAction {

    public static final String   uploadActionPath  =  Configurator.getProperty("uploadActionPath");
    public static final String   connectionTimeout =  Configurator.getProperty("connectionTimeout");
	
	private static String sessionId = null;
	
	public PrivilegedActionUploadImpl(String sessionId) {
		this.sessionId = sessionId;
	}

	private static String getSessionId(){
		return sessionId;
	}
    
    public Object run() {
      
        Debug.logToFile(this, "Uploading Files");
        Debug.logToFile(this, "uploadActionPath: "+uploadActionPath);
        Debug.logToFile(this, "connectionTimeout: "+connectionTimeout);
       
        Integer   returnedValue  =  null;
        HttpState initialState   =  new HttpState();
        int       timeout        =  30000;
        
        if(connectionTimeout != null)
        {
        	   try
            {
               Integer  time  =  new Integer(connectionTimeout);
               
               timeout  =  time.intValue();
               
            } catch ( NumberFormatException nfex)
            {
               Debug.logToFile(this, "NumberFormatException: "+nfex.getMessage());
               Debug.logToFile(this, "connectionTimeout properties value is: "+ connectionTimeout);
               Debug.logToFile(this, "connectionTimeout set to default value: "+ timeout);
            }
        }
        
        List mycookies = InvesDocApplet.getCookies(uploadActionPath);
        if ((mycookies != null) && !mycookies.isEmpty()) {
        	for (int i = 0; i < mycookies.size(); i++) {
        		Cookie mycookie = (Cookie) mycookies.get(i);
        		Debug.logToFile(this, "mycookie: " + mycookie.toString());

                initialState.addCookie(mycookie);
        	}
        }
        
        // Get HTTP client instance
        final HttpClient client = new HttpClient();
                
        client.getHttpConnectionManager().getParams().setConnectionTimeout(30000);
        client.setState(initialState);

        // RFC 2101 cookie management spec is used per default
        // to parse, validate, format & match cookies
        client.getParams().setCookiePolicy(CookiePolicy.RFC_2109);

        try {
            //Getting the files to upload
            QueueSelectedFiles      files       =  InvesDocApplet.filesToUpload;
            URL                     appletURL   =  InvesDocApplet.codeBase ;
            String                  path        =  appletURL.getPath();
            PostMethod              filePost    =  null;
            URI                     uri         =  null; 
            ArrayList               filesList   =  new ArrayList();
            MultipartRequestEntity  request     =  null;
            Cookie[]                cookies     =  null; 
            Part[]               parts;
            
            //path = FileUtil.getURLPath(path);

            Debug.logToFile(this, "appletURL: "+appletURL);
            Debug.logToFile(this, "path: "+path);
            
          
            if ("https".equalsIgnoreCase (appletURL.getProtocol ())) {  
               Protocol easyhttps = new Protocol ("https", new EasySSLProtocolSocketFactory (), appletURL.getPort ());
               client.getHostConfiguration ().setHost (appletURL.getHost(), appletURL.getPort (), easyhttps);
               
               uri   =  new URI(null, null, path + uploadActionPath, null, null);
            } else {
               uri   =  new URI(appletURL.getProtocol() , null, appletURL.getHost() ,appletURL.getPort() , path + uploadActionPath, null, null);
            }
            
            Debug.logToFile(this, "uri: "+uri);
            
            filePost = new PostMethod(uri.toString());
            filePost.getParams().setBooleanParameter(HttpMethodParams.USE_EXPECT_CONTINUE, true);
            
            for (int i = 0; i < files.size(); i++)
            {
                File targetFile  =  files.getFileAt(i);
                
                Debug.logToFile(this, "targetFile: "+targetFile);
                
                filesList.add( new FilePart(URLEncoder.encode(targetFile.getName(), "ISO-8859-15"), targetFile) );
                filesList.add( new StringPart("fileNames", targetFile.getName(), "ISO-8859-15") );
            }

            filesList.add( new StringPart("documentTypeId",
            		InvesDocApplet.jsGetDocumentTypeId()) );
			   
            parts    =  (Part[])filesList.toArray( new Part[0] );
            request  =  new MultipartRequestEntity(parts, filePost.getParams());

            filePost.setRequestHeader("connection", "Keep-Alive");
            String cookieSessionIdReplaced = replaceSessionId();
            filePost.setRequestHeader("cookie", cookieSessionIdReplaced);
            
            Debug.logToFile(this, "cookieSessionIdReplaced: "+cookieSessionIdReplaced);

            filePost.setRequestEntity(request);
            
            Debug.logToFile(this, "filePost.setRequestEntity: " + filePost.getURI());

            cookies = client.getState().getCookies();
            // Display the cookies
            Debug.logToFile(this, "Present cookies: ");
            
            for (int i = 0; i < cookies.length; i++) {
               Debug.logToFile(this, " - " + cookies[i].toExternalForm());
            }

            //Uploading files
            int   status   =  client.executeMethod(filePost);
            Debug.logToFile(this, "Status=" + HttpStatus.getStatusText(status));

            if (status == HttpStatus.SC_OK) 
            {
            	returnedValue =  new Integer(1);
               Debug.logToFile(this, "Upload complete, response=" + filePost.getResponseBodyAsString());
            } else 
            {
            	returnedValue = new Integer(0);
               Debug.logToFile(this, "Upload failed, response=" + HttpStatus.getStatusText(status));
            }

        } catch (FileNotFoundException e) {
        	   Debug.logToFile(this, "Archivo no encontrado ");
            e.printStackTrace();
        } catch (IOException e) {
        	   Debug.logToFile(this, "Error de Intercambio IO ");
            e.printStackTrace();
        } catch (URISyntaxException e) {
        	   Debug.logToFile(this, "Error creando la uri ");
            e.printStackTrace();
        }
        Debug.logToFile(this, "files readed");
        return returnedValue; 
    }

    private String replaceSessionId(){
        List list = InvesDocApplet.getCookies(null);
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



}
