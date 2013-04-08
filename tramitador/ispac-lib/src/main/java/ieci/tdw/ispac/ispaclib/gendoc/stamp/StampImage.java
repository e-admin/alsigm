package ieci.tdw.ispac.ispaclib.gendoc.stamp;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.ispaclib.configuration.ConfigurationMgr;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.util.FileTemporaryManager;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.TypeConverter;
import ieci.tdw.ispac.ispaclib.utils.XmlFacade;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Types;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.apache.struts.util.MessageResources;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeIterator;


/**
 * Información del sello.
 * 
 * La configuración del sello se recoge de la variable del sistema STAMP_CONFIG.
 * Esta variable contiene un XML con el formato:
 * 
<stamp width="4500" height="4000">
	<image width="250" height="100">
		<rectangle x="0" y="0" width="250" height="100" color="#000000"/>
		<string x="20" y="25" color="#0000FF">
			<labels>
				<label locale="ca">NOM DE L'ORGANITZACIO</label>
				<label locale="eu">ERAKUNDEAREN IZENA</label>
				<label locale="es">NOMBRE DE ORGANIZACI\u00d3N</label>
				<label locale="gl">NOME DE ORGANIZACI\u00d3N</label>
			</labels>
		</string>
		<string x="20" y="45" color="#0000FF">
			<labels>
				<label locale="ca">REGISTRE DE ${TP_REG}</label>
				<label locale="eu">ERREGISTROA ${TP_REG}</label>
				<label locale="es">REGISTRO DE ${TP_REG}</label>
				<label locale="gl">REXISTRO DE ${TP_REG}</label>
			</labels>
		</string>
		<string x="20" y="65" color="#0000FF">
			<labels>
				<label locale="ca">NUMERO: ${NREG}</label>
				<label locale="eu">ZENBAKIA: ${NREG}</label>
				<label locale="es">N\u00daMERO: ${NREG}</label>
				<label locale="gl">N\u00daMERO: ${NREG}</label>
			</labels>
		</string>
		<string x="20" y="85" color="#0000FF">
			<labels>
				<label locale="ca">DATA: ${FREG("dd/MM/yyyy HH:mm:ss")}</label>
				<label locale="eu">DATA: ${FREG("dd/MM/yyyy HH:mm:ss")}</label>
				<label locale="es">FECHA: ${FREG("dd/MM/yyyy HH:mm:ss")}</label>
				<label locale="gl">DATA: ${FREG("dd/MM/yyyy HH:mm:ss")}</label>
			</labels>
		</string>
		<rectangle x="2" y="2" width="245" height="95" color="#0000FF"/>
	</image>
</stamp>
 */
public class StampImage {

	/**
	 * Variable con la configuración del sello.
	 */
	public static final String STAMP_CONFIG_VAR_NAME = "STAMP_CONFIG";
	
	private static final int DEFAULT_STAMP_WIDTH = 4500; 
	private static final int DEFAULT_STAMP_HEIGHT = 4000;
	private static final int DEFAULT_STAMP_IMAGE_WIDTH = 250;
	private static final int DEFAULT_STAMP_IMAGE_HEIGHT = 100;

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(StampImage.class);
	
	/**
	 * Contexto de cliente.
	 */
	private ClientContext ctx = null;

	/**
	 * Anchura del sello.
	 */
	private int stampWidth = DEFAULT_STAMP_WIDTH;
	
	/**
	 * Altura del sello.
	 */
	private int stampHeight = DEFAULT_STAMP_HEIGHT;

	/**
	 * Anchura de la imagen.
	 */
	private int imageWidth = DEFAULT_STAMP_IMAGE_WIDTH;
	
	/**
	 * Altura de la imagen.
	 */
	private int imageHeight = DEFAULT_STAMP_IMAGE_HEIGHT;

	/**
	 * XML con la configuración del sello. 
	 */
	private XmlFacade stampCfgXml = null;

	
	/**
	 * Constructor
	 * @throws ISPACException si ocurre algún error. 
	 */
	protected StampImage(ClientContext ctx) throws ISPACException {
		super();
		
		this.ctx = ctx;
		initStampCfg();
	}

	/**
	 * Crea una instancia de la clase.
	 * @return Instancia de la clase.
	 * @throws ISPACException si ocurre algún error. 
	 */
	public static StampImage getInstance(ClientContext ctx) throws ISPACException {
		return new StampImage(ctx);
	}
	
	/**
	 * Inicializa la configuración del sello.
	 * @throws ISPACException si ocurre algún error.
	 */
	protected void initStampCfg() throws ISPACException {
	
    	// Configuración del sello
		String stampCfg = ConfigurationMgr.getVarGlobal(ctx, STAMP_CONFIG_VAR_NAME);
    	if (StringUtils.isNotBlank(stampCfg)) {
    		this.stampCfgXml = new XmlFacade(stampCfg);
    		
    		this.stampWidth = TypeConverter.parseInt(stampCfgXml.get("/stamp/@width"), DEFAULT_STAMP_WIDTH);
    		this.stampHeight = TypeConverter.parseInt(stampCfgXml.get("/stamp/@height"), DEFAULT_STAMP_HEIGHT);
    		this.imageWidth = TypeConverter.parseInt(stampCfgXml.get("/stamp/image/@width"), DEFAULT_STAMP_IMAGE_WIDTH);
    		this.imageHeight = TypeConverter.parseInt(stampCfgXml.get("/stamp/image/@height"), DEFAULT_STAMP_IMAGE_HEIGHT);
    	}
	}

	public int getStampWidth() {
		return stampWidth;
	}
	
	public int getStampHeight() {
		return stampHeight;
	}
	
	public int getImageWidth() {
		return imageWidth;
	}
	
	public int getImageHeight() {
		return imageHeight;
	}
	
	public File createStampImage(IItem document, MessageResources messageResources) throws ISPACException {

		try {
	    	BufferedImage image = new BufferedImage(imageWidth, imageHeight, Transparency.TRANSLUCENT);
	    	Graphics2D graphics = image.createGraphics();
	    	graphics.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON));
	
	    	if (stampCfgXml != null) {
	    		
	        	NodeIterator nodeIt = stampCfgXml.getNodeIterator("/stamp/image/*");
	        	for (Node node = nodeIt.nextNode(); node != null; node = nodeIt.nextNode()) {

	        		// Establece el color
	        		Color color = parseColor(XmlFacade.get(node, "@color"));
	        		if (color != null) {
	        			graphics.setColor(color);
	        		}

	        		// Establece el color de fondo
	        		Color bgcolor = parseColor(XmlFacade.get(node, "@bgcolor"));
	        		if (bgcolor != null) {
	        			graphics.setBackground(bgcolor);
	        		}

	        		// Pinta el objeto
	        		if ("string".equalsIgnoreCase(node.getLocalName())) {
	        			
	        			String lang = ctx.getLocale().getLanguage();
	        			
	        			String message = XmlFacade.get(node, "./labels/label[@locale='" + lang + "']/text()");
	        			if (StringUtils.isBlank(message)) {
	        				message = XmlFacade.get(node, "./labels/label[@default='true']/text()");
	        			}
	        			
	        			message = replace(message, document);
	        			
	    		    	graphics.drawString(message, 
	    		    			TypeConverter.parseInt(XmlFacade.get(node, "@x"), 0),
	    		    			TypeConverter.parseInt(XmlFacade.get(node, "@y"), 0));

	        		} else if ("line".equalsIgnoreCase(node.getLocalName())) {
	        			
	    		    	graphics.drawLine(
	    		    			TypeConverter.parseInt(XmlFacade.get(node, "@x"), 0),
	    		    			TypeConverter.parseInt(XmlFacade.get(node, "@y"), 0),
	    		    			TypeConverter.parseInt(XmlFacade.get(node, "@width"), DEFAULT_STAMP_IMAGE_WIDTH),
	    		    			TypeConverter.parseInt(XmlFacade.get(node, "@height"), DEFAULT_STAMP_IMAGE_HEIGHT));
	    		    	
	        		} else if ("rectangle".equalsIgnoreCase(node.getLocalName())) {
		    		    	
	    		    	graphics.drawRect(
	    		    			TypeConverter.parseInt(XmlFacade.get(node, "@x"), 0),
	    		    			TypeConverter.parseInt(XmlFacade.get(node, "@y"), 0),
	    		    			TypeConverter.parseInt(XmlFacade.get(node, "@width"), DEFAULT_STAMP_IMAGE_WIDTH),
	    		    			TypeConverter.parseInt(XmlFacade.get(node, "@height"), DEFAULT_STAMP_IMAGE_HEIGHT));
		        			
	        		} else if ("fillRect".equalsIgnoreCase(node.getLocalName())) {
	        			
	    		    	graphics.fillRect(
	    		    			TypeConverter.parseInt(XmlFacade.get(node, "@x"), 0),
	    		    			TypeConverter.parseInt(XmlFacade.get(node, "@y"), 0),
	    		    			TypeConverter.parseInt(XmlFacade.get(node, "@width"), DEFAULT_STAMP_IMAGE_WIDTH),
	    		    			TypeConverter.parseInt(XmlFacade.get(node, "@height"), DEFAULT_STAMP_IMAGE_HEIGHT));
	        		}
	        	}
	      		
	    	} else {
	    		
		    	graphics.setColor(new Color(0,0,0,0));
		    	graphics.fillRect(0,0,250,100);
	
		    	graphics.setColor(Color.blue);
		    	graphics.drawString(messageResources.getMessage(ctx.getLocale(), "stamp.document.title"), 20, 25);
		    	//graphics.drawLine(1,38,199,38);
		    	graphics.drawString(messageResources.getMessage(ctx.getLocale(), 
		    			"stamp.document.registry.type") + " " + document.getString("TP_REG"), 20, 45); 
		    	//graphics.drawLine(1,50,199,50);
		       	graphics.drawString(messageResources.getMessage(ctx.getLocale(), 
		       			"stamp.document.registry.number") + " " + document.getString("NREG"), 20, 65);
		    	graphics.drawString(messageResources.getMessage(ctx.getLocale(), 
		    			"stamp.document.registry.date") + " " + TypeConverter.toString(document.getDate("FREG"), TypeConverter.TIMESTAMPFORMAT), 20, 85);
		    	
		    	graphics.setColor(Color.BLUE);
		    	graphics.drawRect(2,2,245,95);
	    	}
	    	
	      	graphics.dispose();
	    	
	    	// Guardar la imagen generada en el fichero temporal
	      	File imageFile = FileTemporaryManager.getInstance().newFile(".png");
	       	ImageIO.write(image, "png", imageFile);
	
	       	return imageFile;
	       	
		} catch (Exception e) {
			logger.error("Error al crear el fichero temporal de la imagen del sello", e);
			throw new ISPACException(e);
		}
	}

	public static Color parseColor(String colorCode) throws NumberFormatException {
		Color color = null;
		
		if (StringUtils.isNotBlank(colorCode)) {
			if (colorCode.startsWith("#")) {
				colorCode = colorCode.substring(1);
			}
			
			colorCode = colorCode.toLowerCase();
			if (colorCode.length() > 6) {
				colorCode.substring(0, 6);
			}
			
			color = new Color(Integer.parseInt(colorCode, 16));
		}
		
		return color;
	}
	
	public static String replace(String text, IItem item) throws ISPACException {
		
        StringBuffer value = new StringBuffer();

        if (StringUtils.isNotBlank(text)) {
        	
            Pattern pattern = Pattern.compile("\\$\\{[^}]*}");
            Matcher matcher = pattern.matcher(text);
            
            while (matcher.find()) { 
            	String var = matcher.group();
            	String key = var.substring(2, var.length()-1);
            	String format = null;
            	
            	// Comprobar si se ha especificado un formato
            	int ixinicio = key.indexOf("(");
            	if (ixinicio > 0) {
            		int ixfin = key.lastIndexOf(")");
            		if (ixfin > 0) {
            			format = key.substring(ixinicio + 1, ixfin);
            		} else {
            			format = key.substring(ixinicio + 1);
            		}
            		
            		key = key.substring(0, ixinicio);
            	}
            	
            	if (StringUtils.isNotBlank(format)) {
            		
            		// Aplicar el formato en función del tipo de campo
	            	Property itemProperty = item.getProperty(key);
	            	switch (itemProperty.getType()) {
						case Types.DATE:
						case Types.TIMESTAMP:
							matcher.appendReplacement(value, TypeConverter.toString(item.getDate(key), TypeConverter.TIMESTAMPFORMAT));
							break;
	
						default:
							matcher.appendReplacement(value, item.getString(key));
							break;
					}
            	} else {
            		matcher.appendReplacement(value, item.getString(key));
            	}
            }
            
            matcher.appendTail(value);
        }

        return value.toString();
	}
	
}
