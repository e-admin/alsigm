package ieci.tdw.ispac.api.graph;

import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.TypeConverter;
import ieci.tdw.ispac.ispaclib.utils.XmlFacade;
import ieci.tdw.ispac.ispaclib.utils.XmlTag;

import java.io.Serializable;

/**
 * Clase que almacena la información gráfica de un nodo.
 * 
 */
public class GInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static final int DEFAULT_X = 0;
	private static final int DEFAULT_Y = 0;
	
	/**
	 * Coordenada x.
	 */
	private int x = DEFAULT_X;
	
	/**
	 * Coordenada y.
	 */
	private int y = DEFAULT_Y;
	
	
	/**
	 * Constructor.
	 * 
	 */
	public GInfo() {
		super();
	}

	/**
	 * Constructor.
	 * @param x Coordenada x.
	 * @param y Coordenada y.
	 */
	public GInfo(int x, int y) {
		this();
		setX(x);
		setY(y);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * Compone un objeto a partir de una cadena con el XML.
	 * @param xml XML con la información gráfica.
	 * @return Objeto GInfo.
	 */
	public static GInfo parse(String xml) {
		GInfo ginfo = null;
		
		if (StringUtils.isNotBlank(xml)) {
			XmlFacade xmlfacade = new XmlFacade(xml);
			ginfo = new GInfo();
			ginfo.setX(TypeConverter.parseInt(xmlfacade.get("/ginfo/position/@x"), DEFAULT_X));
			ginfo.setY(TypeConverter.parseInt(xmlfacade.get("/ginfo/position/@y"), DEFAULT_Y));
		}
		
		return ginfo;
	}
	
	/**
	 * Obtiene un XML con la representación del objeto.
	 * @return XML con la representación del objeto.
	 */
	public String toXml() {

		// Atributos del tag position
		String positionAttrs = new StringBuffer()
			.append(XmlTag.newAttr("x", String.valueOf(getX())))
			.append(XmlTag.newAttr("y", String.valueOf(getY())))
			.toString();
		
        return XmlTag.newTag("ginfo", 
        		XmlTag.newTag("position", null, positionAttrs));
	}
	
	/**
	 * Obtiene la representación del objeto.
	 * @return Representación del objeto.
	 */
	public String toString() {
		return toXml();
	}
}
