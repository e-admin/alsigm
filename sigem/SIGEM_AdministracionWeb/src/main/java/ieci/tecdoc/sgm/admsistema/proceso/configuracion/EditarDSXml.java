package ieci.tecdoc.sgm.admsistema.proceso.configuracion;

import java.util.*;
import java.io.*;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import javax.xml.transform.dom.*;

import org.apache.log4j.Logger;
import org.w3c.dom.*;

public class EditarDSXml {

	private static final Logger logger = Logger.getLogger(EditarDSXml.class);

	final public static String DATASOURCES = "datasources";
	final public static String LOCAL_TX_DATASOURCE = "local-tx-datasource";
	
	final public static String JNDI_NAME = "jndi-name";
	final public static String USERNAME = "user-name";
    final public static String PASSWORD = "password";
    final public static String DRIVER_CLASS = "driver-class";
    final public static String CONNECTION_URL = "connection-url";
	
    private DocumentBuilderFactory docFactory;
    private DocumentBuilder docBuilder;
    private Document doc;
    
    final public static String[]basesDeDatos = {"archivo", "eTramitacion", "registro", "tramitador", "registro"};
    final public static String[]aliasBasesDeDatos = {"archivo", "eTramitacion", "registro", "tramitador", "terceros"};
    final public static int[][]posicionArray = {{7,8}, {13,14}, {11,12}, {9,10}, {11,12}};
    
	/*
     * esto ha de recibir un InputStream de un fichero Context de jboss , y agregarle datasources     
     * 
     */
    public EditarDSXml(InputStream is) throws Exception {
    	docFactory=DocumentBuilderFactory.newInstance();
    	docBuilder=docFactory.newDocumentBuilder();
    	doc=docBuilder.parse(is);
    }
    
    /*
     * crea elementos <local-tx-datasource> con los datasources en DATASOURCES
     */
    public void añadirResource(Hashtable hash, String entidad, boolean addEntidad) throws Exception {
    	NodeList gnsl=doc.getElementsByTagName(DATASOURCES);
    	Node gns=gnsl.item(0);
    	Enumeration e=hash.keys();
	
    	while(e.hasMoreElements()) {
    		String aliasbd=(String)e.nextElement();
    		DataConfigTomcat dct=(DataConfigTomcat)hash.get(aliasbd);	    
    		Element localTxDS=doc.createElement(LOCAL_TX_DATASOURCE);
    		
    		String params[][]={
    				{JNDI_NAME, componerNombre(dct.getName(), entidad)},
    				{CONNECTION_URL, dct.getUrl() + (addEntidad ? "_"+entidad : "")},
    				{DRIVER_CLASS, dct.getDriverClassName()},
    				{USERNAME, dct.getUsername()},
    				{PASSWORD, dct.getPassword()},
    			};
	    
    		añadirParametros(localTxDS, params);
    		gns.appendChild(localTxDS);
    		
    		logger.debug("Añadido local-tx-datasource = " + dct.getName());
    	}
    }
    
    private String componerNombre(String bd, String entidad) {
    	return bd + "DS_" + entidad;
    }
    
    private void añadirParametros(Node localTxDS, String[][]params) throws Exception {
    	for(int i=0;i<params.length;i++) {
    	    Element parameter=doc.createElement(params[i][0]);
    	    Text t=doc.createTextNode(params[i][1]);
    	    parameter.appendChild(t);
    	    localTxDS.appendChild(parameter);
    	}
    }
    
    public String escribir() throws Exception {
    	Transformer transformer=TransformerFactory.newInstance().newTransformer();
    	transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    	
    	StreamResult result=new StreamResult(new StringWriter());
    	DOMSource source=new DOMSource(doc);
    	transformer.transform(source, result);
    	
    	String xmlString=result.getWriter().toString();

    	return xmlString;
    }
}

