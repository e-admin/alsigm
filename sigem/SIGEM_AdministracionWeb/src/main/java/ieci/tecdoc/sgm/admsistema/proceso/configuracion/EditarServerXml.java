package ieci.tecdoc.sgm.admsistema.proceso.configuracion;

import java.util.*;
import java.io.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import javax.xml.transform.dom.*;

import org.apache.log4j.Logger;
import org.w3c.dom.*;

public class EditarServerXml {

	private static final Logger logger = Logger.getLogger(EditarServerXml.class);

    // elementos y atributos xml
    final public static String CONTEXT="Context";
    final public static String DOC_BASE="docBase";

    final public static String GLOBAL_NAMING_RESOURCES="GlobalNamingResources";
    final public static String RESOURCE_LINK="ResourceLink";
    final public static String RESOURCE="Resource";
    final public static String RESOURCE_PARAMS="ResourceParams";
    final public static String GLOBAL="global";
    final public static String NAME="name";
    final public static String VALUE="value";
    final public static String PARAMETER="parameter";
    // final public static String ="";

    // nombres
    final public static String USERNAME="username";
    final public static String PASSWORD="password";
    final public static String DRIVER_CLASS_NAME="driverClassName";
    final public static String URL="url";
    final public static String MAX_ACTIVE="maxActive";
    final public static String MAX_IDLE="maxIdle";
    final public static String REMOVE_ABANDONED="removeAbandoned";
    final public static String REMOVE_ABANDONED_TIMEOUT="removeAbandonedTimeout";


    //
    private ResourceBundle rb;
    private Hashtable proyectos=new Hashtable(); // asocia el nombre que aparece en rb con un array de nombres de bases de datos

    private DocumentBuilderFactory docFactory;
    private DocumentBuilder docBuilder;
    private Document doc;

    final public static String[]basesDeDatos={"archivo", "eTramitacion", "registro", "tramitador", "registro"};
    final public static String[]aliasBasesDeDatos={"archivo", "eTramitacion", "registro", "tramitador", "terceros"};
    final public static int[][]posicionArray={{7,8}, {13,14}, {11,12}, {9,10}, {11,12}};

    /*
     * esto ha de recibir un InputStream de un fichero Context de tomcat , y agregarle datasources     
     * 
     */
    public EditarServerXml(InputStream is) throws Exception {

	leerResourceBundle();

	// this.fichero=fichero;

	docFactory=DocumentBuilderFactory.newInstance();
	docBuilder=docFactory.newDocumentBuilder();
	doc=docBuilder.parse(is);
	
    }


    public void tratar(DataConfigTomcat[]dataSources, String entidad) throws Exception {

	// modificarContext(dataSources, entidad);
	// añadirDataSources(dataSources, entidad);

	
	/*Vector añadidos=*/ añadirResourceLink(entidad);
	// añadirResource(dataSources, entidad);

    }

    /*
     *
     * entidad tienen que ser tres numeros
     */
    public void modificarContext(DataConfigTomcat[]dataSources, String entidad) throws Exception {
	NodeList contexts=doc.getElementsByTagName(CONTEXT);

	for(int i=0;i<contexts.getLength();i++) {
	    Element context=(Element)contexts.item(i);

	    NodeList resourceLinks=context.getElementsByTagName(RESOURCE_LINK);

	    Vector vElementos=new Vector();

	    // nombres que hemos añadido nuevos en este Context, necesitamos guardarlos para asegurarnos de que no guardamos iguales
	    Vector vNames=new Vector();
	    for(int j=0;j<resourceLinks.getLength();j++) {
		Element resourceLink=(Element)resourceLinks.item(j);

		String name=resourceLink.getAttribute(NAME);
		name=establecerEntidad(name, entidad);
		// resourceLink.setAttribute(NAME, name);

		Element resourceLinkNuevo=doc.createElement(RESOURCE_LINK);
		resourceLinkNuevo.setAttribute(NAME, name);

		boolean existe=false;

		// comprobamos que no existe otro JNDI igual en este Context
		for(int k=0;k<vNames.size();k++) {
		    String s=(String)vNames.get(k);
		    if(s.equals(name)) {
			// ya esta agregado
			existe=true;
		    }
		}

		if(existe) {
		    continue;
		}

		String global=resourceLink.getAttribute(GLOBAL);
		global=establecerEntidad(global, entidad);
		// resourceLink.setAttribute(GLOBAL, global);
		
		resourceLinkNuevo.setAttribute(GLOBAL, global);

		vNames.add(name);
		vElementos.add(resourceLinkNuevo);

		// System.out.println(resourceLink);
	    }

	    for(int j=0;j<vElementos.size();j++) {
		context.appendChild((Element)vElementos.get(j));
	    }
		
	}

    }

    void crearCamposDeBD(String entidad) throws Exception {

    }

    private void leerResourceBundle() {
	rb=ResourceBundle.getBundle("ieci.tecdoc.sgm.admsistema.proceso.configuracion.EditarServerXml");

	Enumeration enumera=rb.getKeys();
	while(enumera.hasMoreElements()) {
	    String entrada=(String)enumera.nextElement();
	    logger.debug("entrada: "+entrada);
	    String valor=rb.getString(entrada);
	    StringTokenizer st=new StringTokenizer(valor.trim(), ",");
	    String[]s=new String[st.countTokens()];
	    for(int i=0;i<s.length;i++) {
		s[i]=((String)st.nextElement()).trim();
	    }

	    proyectos.put(entrada, s);
	}

    }

    /*
     * crea <ResourceLink> en los <Context>
     */
    public void añadirResourceLink(String entidad) throws Exception {

	NodeList contexts=doc.getElementsByTagName(CONTEXT);

	Hashtable p=(Hashtable)proyectos.clone();

	for(int i=0;i<contexts.getLength();i++) {
	    Element context=(Element)contexts.item(i);
	    // System.out.println(context);
	    String docBase=context.getAttribute(DOC_BASE);
	    // System.out.println("docBase: "+docBase);
	    Enumeration e=p.keys();
	    boolean encontrado=false;
	    while(e.hasMoreElements()) {
		String s=(String)e.nextElement();
		// System.out.println("s: "+s);
		if(s.equals(docBase)) {
		    encontrado=true;
		    
		    String[]bds=(String[])p.remove(docBase);
		    for(int j=0;j<bds.length;j++) {
			Element resourceLinkNuevo=doc.createElement(RESOURCE_LINK);
			String name=componerNombre(bds[j], entidad);
			resourceLinkNuevo.setAttribute(NAME, name);
			resourceLinkNuevo.setAttribute(GLOBAL, name);
			resourceLinkNuevo.setAttribute("type", "javax.sql.DataSource");
			
			context.appendChild(resourceLinkNuevo);
		    }
		    break;
		}
	    }
	}
	Enumeration e=p.keys();
	while(e.hasMoreElements()) {
	    logger.debug("No se encuentra proyecto "+e.nextElement());
	}
    }

    /*
     * crea elementos <Resource> y <ResourceParams> con los datasources en GLOBAL_NAMING_RESOURCES
     */
    public void añadirResource(Hashtable hash, String entidad, boolean addEntidad) throws Exception {
	// Node context=doc.getFirstChild();

	NodeList gnsl=doc.getElementsByTagName(GLOBAL_NAMING_RESOURCES);
	Node gns=gnsl.item(0);

	// System.err.println("gns: "+gnsl);

	// for(int i=0;i<basesDeDatos.length;i++) {
	Enumeration e=hash.keys();
	while(e.hasMoreElements()) {
	    String aliasbd=(String)e.nextElement();
	    DataConfigTomcat dct=(DataConfigTomcat)hash.get(aliasbd);

	    // System.err.println("dct: "+dct);
	    
	    Element resource=doc.createElement(RESOURCE);
	    resource.setAttribute("auth", "Container");
	    // resource.setAttribute(NAME, dataSources[i].getName());
	    resource.setAttribute(NAME, componerNombre(dct.getName(), entidad));
	    resource.setAttribute("type", "javax.sql.DataSource");
	    gns.appendChild(resource);

	    Element resourceParams=doc.createElement(RESOURCE_PARAMS);
	    resourceParams.setAttribute(NAME, componerNombre(dct.getName(), entidad));
	    String params[][]={{USERNAME, dct.getUsername()},
			       {PASSWORD, dct.getPassword()},
			       {DRIVER_CLASS_NAME, dct.getDriverClassName()},
			       {URL, dct.getUrl() + (addEntidad ? "_"+entidad : "")},
			       {MAX_ACTIVE, "20"},
			       {MAX_IDLE, "10"},
			       {REMOVE_ABANDONED, "true"},
			       {REMOVE_ABANDONED_TIMEOUT, "5"}
	    };
	    
	    añadirParametros(resourceParams, params);
	    gns.appendChild(resourceParams);
	}
    }

    private String componerNombre(String bd, String entidad) {
	return "jdbc/"+bd+"DS_"+entidad;
    }

    public void añadirDataSources(DataConfigTomcat[]dataSources, String entidad) throws Exception {
	Node context=doc.getFirstChild();

	for(int i=0;i<dataSources.length;i++) {
	    
	    Element resource=doc.createElement(RESOURCE);
	    resource.setAttribute("auth", "Container");
	    resource.setAttribute(NAME, dataSources[i].getName());
	    resource.setAttribute("type", "javax.sql.DataSource");
	    context.appendChild(resource);

	    Element resourceParams=doc.createElement(RESOURCE_PARAMS);
	    resourceParams.setAttribute(NAME, dataSources[i].getName());
	    String params[][]={{USERNAME, dataSources[i].getUsername()},
			       {PASSWORD, dataSources[i].getPassword()},
			       {DRIVER_CLASS_NAME, dataSources[i].getDriverClassName()},
			       {URL, dataSources[i].getUrl()+"_"+entidad}};
	    añadirParametros(resourceParams, params);
	    context.appendChild(resourceParams);

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

    private void añadirParametros(Node resourceParams, String[][]params) throws Exception {
	for(int i=0;i<params.length;i++) {
	    Element parameter=doc.createElement(PARAMETER);
	    Element name=doc.createElement(NAME);
	    Text t=doc.createTextNode(params[i][0]);
	    name.appendChild(t);
	    parameter.appendChild(name);
	    Element value=doc.createElement(VALUE);
	    t=doc.createTextNode(params[i][1]);
	    value.appendChild(t);

	    parameter.appendChild(value);
	    resourceParams.appendChild(parameter);
	}
    }

    /*
     * esto tiene que devolver name+"_"+entidad, sustituyendo la entidad si ya existiese
     */
    private String establecerEntidad(String name, String entidad) throws Exception {

	int idx=name.lastIndexOf("_");
	if(idx<0) { // no tiene '_', agregamos
	    name=name+"_"+entidad;
	}
	else {
	    int k=1;
	    try {
		for(;k<=3;k++) {
		    if(name.charAt(idx+k)>='0' && name.charAt(idx+k)<='9')
			continue;
		    else
			break;
		}
	    } catch (IndexOutOfBoundsException e) {
	    }
	    if(k==4) { // tiene ya multientidad
		name=name.substring(0, idx+1)+entidad;
	    }
	    else {
		name=name+"_"+entidad;
	    }
	}
	return name;
    }
}

