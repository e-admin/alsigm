package fondos.vos;

import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.xmlrules.DigesterLoader;
import org.apache.commons.lang.StringUtils;
import org.xml.sax.SAXException;

import transferencias.vos.ProductorUnidadDocumentalVO;
import transferencias.vos.RangoVO;
import transferencias.vos.UnidadDocumentalXInfo;

import common.Constants;
import common.Globals;
import common.db.DBUtils;
import common.exceptions.UncheckedArchivoException;
import common.vos.Describible;

import descripcion.model.ValoresFicha;

/**
 * Métodos de obtención de los valores de los campos de una unidad documental en
 * una división de fracción de serie del cuadro de clasificación de fondos
 * documentales
 */
public class UDocEnFraccionSerieVO implements Describible {

	protected String idUDoc = null;
	protected String numExp = null;
	protected String asunto = null;
	protected String signatura = null;
	protected Date fechaExtIni = null;
	protected Date fechaExtFin = null;
	protected String idFS = null;
	protected int orden = 0;
	/** Indicativo de si la unidad documental se encuentra validada o no */
	protected boolean validada;
	// protected String xmlInfo = null;

	// Campos añadidos para implementación de la división de fracción de serie
	// con ficha descriptiva
	// Sólo hace falta que estén rellenos en el caso de una división de fracción
	// de serie de este tipo
	protected String idFichaDescr = null;
	protected String idNivelDocumental = null;

	/** Descriptor del Órgano Productor de la unidad documental **/
	// OrganoProductorVO organoProductor = null;

	/** Información extendida de la unidad documental */
	protected UnidadDocumentalXInfo xinfo = new UnidadDocumentalXInfo();

	public String getNumExp() {
		return numExp;
	}

	public void setNumExp(String numExp) {
		this.numExp = numExp;
	}

	public String getSignatura() {
		return signatura;
	}

	public void setSignatura(String signatura) {
		this.signatura = signatura;
	}

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public Date getFechaExtFin() {
		return fechaExtFin;
	}

	public void setFechaExtFin(Date fechaExtFin) {
		this.fechaExtFin = fechaExtFin;
	}

	public Date getFechaExtIni() {
		return fechaExtIni;
	}

	public void setFechaExtIni(Date fechaExtIni) {
		this.fechaExtIni = fechaExtIni;
	}

	public UnidadDocumentalXInfo getXinfo() {
		return xinfo;
	}

	public void setXinfo(UnidadDocumentalXInfo xinfo) {
		this.xinfo = xinfo;
	}

	public String getIdFS() {
		return idFS;
	}

	public void setIdFS(String idFS) {
		this.idFS = idFS;
	}

	public String getIdUDoc() {
		return idUDoc;
	}

	public void setIdUDoc(String idUDoc) {
		this.idUDoc = idUDoc;
	}

	public int getOrden() {
		return orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}

	public String getXmlinfo() {
		/*
		 * if (xinfo == null) parseXmlInfo(rulesXml);
		 */
		return asXML();
	}

	public String asXML() {
		return asXMLWithValores(null);
	}

	public void setXmlinfo(String xmlinfo) {
		if (xmlinfo != null)
			// this.xmlInfo = xmlinfo.trim();
			try {
				// ConfiguracionTransferencias config =
				// ConfiguracionSistemaArchivoFactory.getConfiguracionSistemaArchivo().getConfiguracionTransferencias();
				// URL digesterRulesFile =
				// getClass().getResource(config.getReglasProcesoInfoUnidadDocumental());
				URL digesterRulesFile = getClass().getResource(
						Globals.RULES_MAP_DESCR_UDOC);
				Digester digester = DigesterLoader
						.createDigester(digesterRulesFile);

				// String rulesXML = xmlinfoRules;
				// String rulesXML = "";
				// InputSource is = new InputSource(new StringReader(rulesXML));
				// Digester digester = DigesterLoader.createDigester(is);

				xinfo.reset();
				digester.push(xinfo);
				StringReader strReader = new StringReader(xmlinfo.trim());
				digester.parse(strReader);
			} catch (MalformedURLException mue) {
				throw new UncheckedArchivoException(
						"Error leyendo xml de unidad documental", mue);
			} catch (IOException ioe) {
				throw new UncheckedArchivoException(
						"Error leyendo xml de unidad documental", ioe);
			} catch (SAXException saxe) {
				throw new UncheckedArchivoException(
						"Error leyendo xml de unidad documental", saxe);
			}
	}

	static DateFormat dateFormater = new SimpleDateFormat("dd/MM/yyyy");

	public String asXMLWithValores(ValoresFicha valores) {
		StringBuffer xmlInfo = new StringBuffer();
		xmlInfo.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>");
		xmlInfo.append("<UNIDAD_DOCUMENTAL_TRANSFERENCIA>");
		xmlInfo.append("<EXPEDIENTE>");
		xmlInfo.append("<NUMERO>").append(Constants.addCData(this.numExp))
				.append("</NUMERO>");
		xmlInfo.append("</EXPEDIENTE>");
		xmlInfo.append("<ASUNTO>").append(Constants.addCData(this.asunto))
				.append("</ASUNTO>");
		if (getFechaExtIni() != null)
			xmlInfo.append("<FECHA_INICIAL>")
					.append("<FECHA FMT=\"DDMMAAAA\" SEP=\"/\" CALIF=\"\">")
					.append(dateFormater.format(getFechaExtIni()))
					.append("</FECHA></FECHA_INICIAL>");
		if (getFechaExtFin() != null)
			xmlInfo.append("<FECHA_FINAL>")
					.append("<FECHA FMT=\"DDMMAAAA\" SEP=\"/\" CALIF=\"\">")
					.append(dateFormater.format(getFechaExtFin()))
					.append("</FECHA></FECHA_FINAL>");
		ProductorUnidadDocumentalVO productor = xinfo.getProductor();
		if (productor != null) {
			xmlInfo.append("<PRODUCTOR>");
			if (productor.getId() != null)
				xmlInfo.append("<ID>").append(productor.getId())
						.append("</ID>");
			xmlInfo.append("<NOMBRE>")
					.append(Constants.addCData(StringUtils
							.defaultString(productor.getNombre())))
					.append("</NOMBRE>");
			xmlInfo.append("</PRODUCTOR>");
		}
		xmlInfo.append("<PUBLICACION_EN_SAC>")
				.append(xinfo.isEnSAC() ? Constants.TRUE_STRING
						: Constants.FALSE_STRING)
				.append("</PUBLICACION_EN_SAC>");
		xmlInfo.append(xinfo.toXMLStringWithValores(valores));
		xmlInfo.append("</UNIDAD_DOCUMENTAL_TRANSFERENCIA>");
		return xmlInfo.toString();
	}

	/*
	 * static DateFormat dateFormater = new SimpleDateFormat("dd/MM/yyyy");
	 * public String asXML() { StringBuffer xmlInfo = new StringBuffer();
	 * xmlInfo.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>");
	 * xmlInfo.append("<UNIDAD_DOCUMENTAL_TRANSFERENCIA>");
	 * xmlInfo.append("<EXPEDIENTE>");
	 * xmlInfo.append("<NUMERO>").append(Constants
	 * .addCData(this.numExp)).append("</NUMERO>");
	 * xmlInfo.append("</EXPEDIENTE>");
	 * xmlInfo.append("<ASUNTO>").append(Constants
	 * .addCData(this.asunto)).append("</ASUNTO>"); if (getFechaExtIni() !=
	 * null) xmlInfo.append("<FECHA_INICIAL>").append(
	 * "<FECHA FMT=\"DDMMAAAA\" SEP=\"/\" CALIF=\"\">"
	 * ).append(dateFormater.format
	 * (getFechaExtIni())).append("</FECHA></FECHA_INICIAL>"); if
	 * (getFechaExtFin() != null) xmlInfo.append("<FECHA_FINAL>").append(
	 * "<FECHA FMT=\"DDMMAAAA\" SEP=\"/\" CALIF=\"\">"
	 * ).append(dateFormater.format
	 * (getFechaExtFin())).append("</FECHA></FECHA_FINAL>"); if (organoProductor
	 * != null) { xmlInfo.append("<PRODUCTOR>"); if (organoProductor.getId() !=
	 * null)
	 * xmlInfo.append("<ID>").append(organoProductor.getId()).append("</ID>");
	 * xmlInfo
	 * .append("<NOMBRE>").append(Constants.addCData(StringUtils.defaultString
	 * (organoProductor.getNombre()))).append("</NOMBRE>");
	 * xmlInfo.append("</PRODUCTOR>"); }
	 * xmlInfo.append("<PUBLICACION_EN_SAC>").append(xinfo.isEnSAC()?
	 * Constants.TRUE_STRING :
	 * Constants.FALSE_STRING).append("</PUBLICACION_EN_SAC>");
	 * xmlInfo.append(xinfo.toXMLString());
	 * xmlInfo.append("</UNIDAD_DOCUMENTAL_TRANSFERENCIA>"); return
	 * xmlInfo.toString(); }
	 */

	public String getIdFichaDescr() {
		return idFichaDescr;
	}

	public void setIdFichaDescr(String idFichaDescr) {
		this.idFichaDescr = idFichaDescr;
	}

	public String getIdNivelDocumental() {
		return idNivelDocumental;
	}

	public void setIdNivelDocumental(String idNivelDocumental) {
		this.idNivelDocumental = idNivelDocumental;
	}

	public String getValidada() {
		return DBUtils.getStringValue(isValidada());
	}

	public void setValidada(String value) {
		setValidada(DBUtils.getBooleanValue(value));
	}

	public boolean isValidada() {
		return validada;
	}

	public void setValidada(boolean validada) {
		this.validada = validada;
	}

	public void addRango(RangoVO rango) {
		if (this.xinfo.getRangos() == null)
			this.xinfo.setRangos(new ArrayList());
		this.xinfo.getRangos().add(rango);
	}

	public void resetRangos() {
		this.xinfo.setRangos(null);
	}
}
