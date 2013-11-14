package es.ieci.tecdoc.fwktd.applets.sendfiles.privileged;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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

import es.ieci.tecdoc.fwktd.applets.sendfiles.applet.SendFilesApplet;

public class UploadFilesPrivilegedAction implements PrivilegedAction{

	public Object run() {
			String uploadActionPath = SendFilesApplet.getUploadPath();
			Boolean   returnedValue  =  null;
			String result;
	        HttpState initialState   =  new HttpState();
	        int       timeout        =  SendFilesApplet.getConnectionTimeout();

	        List mycookies = SendFilesApplet.getCookies(uploadActionPath);
	        if ((mycookies != null) && !mycookies.isEmpty()) {
	        	for (int i = 0; i < mycookies.size(); i++) {
	        		Cookie mycookie = (Cookie) mycookies.get(i);
	        	//	Debug.logToFile(this, "mycookie: " + mycookie.toString());
	        		System.out.println("mycookie:"+mycookie.toString());
	                initialState.addCookie(mycookie);
	        	}
	        }

	        // Get HTTP client instance
	        final HttpClient client = new HttpClient();

	        client.getHttpConnectionManager().getParams().setConnectionTimeout(timeout);
	        client.setState(initialState);

	        // RFC 2101 cookie management spec is used per default
	        // to parse, validate, format & match cookies
	        client.getParams().setCookiePolicy(CookiePolicy.RFC_2109);

	        try {
	            //Getting the files to upload
	            HashMap<String,File>    files       =  SendFilesApplet.getFilesToUpload();
	            URL                     appletURL   =  SendFilesApplet.getCodebase();
	            String                  path        =  appletURL.getPath();
	            PostMethod              filePost    =  null;
	            URI                     uri         =  null;
	            ArrayList               filesList   =  new ArrayList();
	            MultipartRequestEntity  request     =  null;
	            Cookie[]                cookies     =  null;
	            Part[]               parts;

	            //path = FileUtil.getURLPath(path);

	         //   Debug.logToFile(this, "appletURL: "+appletURL);
	          //  Debug.logToFile(this, "path: "+path);
	            System.out.println("appletURL:"+appletURL);
	            System.out.println("path:"+path);

	            if ("https".equalsIgnoreCase (appletURL.getProtocol ())) {
	               int port = (appletURL.getPort()!=-1)?(appletURL.getPort()):(443);
	               Protocol easyhttps = new Protocol ("https", new EasySSLProtocolSocketFactory (), port);
	               client.getHostConfiguration ().setHost (appletURL.getHost(), port, easyhttps);

	               uri   =  new URI(null, null, path + uploadActionPath, null, null);
	            } else {
	               uri   =  new URI(appletURL.getProtocol() , null, appletURL.getHost() ,appletURL.getPort() , path + uploadActionPath, null, null);
	            }

	           // Debug.logToFile(this, "uri: "+uri);
	            System.out.println("uri:"+uri);
	            filePost = new PostMethod(uri.toString());
	            filePost.getParams().setBooleanParameter(HttpMethodParams.USE_EXPECT_CONTINUE, true);
	            Set<String> keySet = files.keySet();
	            Iterator<String> itrFiles = keySet.iterator();
	            while(itrFiles.hasNext())
	            {
	                String fileName  =  itrFiles.next();
	                File targetFile = files.get(fileName);
	               // Debug.logToFile(this, "targetFile: "+targetFile);
	                System.out.println("fileName:"+fileName+" - file:"+targetFile);
	                filesList.add( new FilePart(URLEncoder.encode(fileName, "ISO-8859-15"), targetFile) );
	                filesList.add( new StringPart("fileNames", fileName, "ISO-8859-15") );
	            }
	            HashMap<String, String> params = SendFilesApplet.getParams();
	            Iterator<String> itr = params.keySet().iterator();
	            while(itr.hasNext())
	            {
	            	String name = itr.next();
	            	System.out.println("Agregado el parametro: "+name+"="+params.get(name));
	            	filesList.add(new StringPart(name,params.get(name)));
	            }

	            parts    =  (Part[])filesList.toArray( new Part[0] );
	            request  =  new MultipartRequestEntity(parts, filePost.getParams());

	            filePost.setRequestHeader("connection", "Keep-Alive");
	            filePost.setRequestHeader("cookie", SendFilesApplet.getCookie());
	            filePost.setRequestEntity(request);

	      //      Debug.logToFile(this, "filePost.setRequestEntity: " + filePost.getURI());

	            cookies = client.getState().getCookies();
	            // Display the cookies
	          //  Debug.logToFile(this, "Present cookies: ");
	            System.out.println("Present cookies");
	            for (int i = 0; i < cookies.length; i++) {
	            //   Debug.logToFile(this, " - " + cookies[i].toExternalForm());
	            	System.out.println(" - "+ cookies[i].toExternalForm());
	            }


	            //Uploading files
	            int   status   =  client.executeMethod(filePost);
	           // Debug.logToFile(this, "Status=" + HttpStatus.getStatusText(status));
	            System.out.println("Status="+HttpStatus.getStatusText(status));
	            if (status == HttpStatus.SC_OK)
	            {
	            	result =  filePost.getResponseBodyAsString();
	            	System.out.println("Upload OK, response: "+result);
	            	
	            	returnedValue = Boolean.FALSE;
	            	if(result==null || result.trim().length()==0){
	            		returnedValue = Boolean.TRUE;
	            	}
	             //  Debug.logToFile(this, "Upload complete, response=" + filePost.getResponseBodyAsString());
	            } else
	            {
	            	returnedValue = Boolean.FALSE;
	            	System.out.println("Upload Error, response:"+ HttpStatus.getStatusText(status));
	              // Debug.logToFile(this, "Upload failed, response=" + HttpStatus.getStatusText(status));
	            }

	        } catch (FileNotFoundException e) {
	        	System.out.println("Archivo no encontrado");
	        	 //  Debug.logToFile(this, "Archivo no encontrado ");
	            e.printStackTrace();
	        } catch (IOException e) {
	        	System.out.println("Error de Intercambio IO");
	        	  // Debug.logToFile(this, "Error de Intercambio IO ");
	            e.printStackTrace();
	        } catch (URISyntaxException e) {
	        	System.out.println("Error creando la uri");
	        	 //  Debug.logToFile(this, "Error creando la uri ");
	            e.printStackTrace();
	        }
	       // Debug.logToFile(this, "files readed");
	        return returnedValue;
	}

}
