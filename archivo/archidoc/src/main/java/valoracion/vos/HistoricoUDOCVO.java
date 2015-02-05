package valoracion.vos;

import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.xmlrules.DigesterLoader;
import org.xml.sax.SAXException;

import common.Globals;
import common.exceptions.UncheckedArchivoException;
import common.vos.BaseVO;

/**
 * Clase que encapula la informacion histórica de unidades documentales
 */
public class HistoricoUDOCVO extends BaseVO {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String idEliminacion = null;
	private String idUDOC = null;
	private String signaturaUdoc = null;
	private String tituloUdoc = null;
	private String numExpUdoc = null;
	private String codRefUdoc = null;
	protected UnidadDocumentalHistoricaXInfo xinfo = new UnidadDocumentalHistoricaXInfo();

	public String getCodRefUdoc() {
		return codRefUdoc;
	}

	public void setCodRefUdoc(String codRefUdoc) {
		this.codRefUdoc = codRefUdoc;
	}

	public String getIdEliminacion() {
		return idEliminacion;
	}

	public void setIdEliminacion(String idEliminacion) {
		this.idEliminacion = idEliminacion;
	}

	public String getIdUDOC() {
		return idUDOC;
	}

	public void setIdUDOC(String idUDOC) {
		this.idUDOC = idUDOC;
	}

	public String getNumExpUdoc() {
		return numExpUdoc;
	}

	public void setNumExpUdoc(String numExpUdoc) {
		this.numExpUdoc = numExpUdoc;
	}

	public String getSignaturaUdoc() {
		return signaturaUdoc;
	}

	public void setSignaturaUdoc(String signaturaUdoc) {
		this.signaturaUdoc = signaturaUdoc;
	}

	public String getTituloUdoc() {
		return tituloUdoc;
	}

	public void setTituloUdoc(String tituloUdoc) {
		this.tituloUdoc = tituloUdoc;
	}

	public void setFechaInicial(String fechaInicial) {
		getXinfo().setFechaInicial(fechaInicial);
	}

	public String getFechaInicial() {
		return getXinfo().getFechaInicial();
	}

	public String getStrFechaInicial() {
		return getXinfo().getFechaInicial();
	}

	public void setFechaFinal(String fechaFinal) {
		getXinfo().setFechaFinal(fechaFinal);
	}

	public String getFechaFinal() {
		return getXinfo().getFechaFinal();
	}

	public String getStrFechaFinal() {
		return getXinfo().getFechaFinal();
	}

	/**
	 * @return el xinfo
	 */
	public UnidadDocumentalHistoricaXInfo getXinfo() {
		return xinfo;
	}

	/**
	 * @param xinfo
	 *            el xinfo a establecer
	 */
	public void setXinfo(UnidadDocumentalHistoricaXInfo xinfo) {
		this.xinfo = xinfo;
	}

	public String asXML() {
		StringBuffer xmlInfo = new StringBuffer();
		xmlInfo.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>");
		xmlInfo.append("<UNIDAD_DOCUMENTAL_HISTORICA version=\"1.0\">");

		xmlInfo.append(xinfo.toXMLString());
		xmlInfo.append("</UNIDAD_DOCUMENTAL_HISTORICA>");

		return xmlInfo.toString();
	}

	public String getXmlinfo() {
		return asXML();
	}

	public void setXmlinfo(String xmlinfo) {
		if (xmlinfo != null)
			try {
				URL digesterRulesFile = getClass().getResource(
						Globals.RULES_MAP_DESCR_UDOC_HIST);
				Digester digester = DigesterLoader
						.createDigester(digesterRulesFile);

				digester.push(xinfo);
				StringReader strReader = new StringReader(xmlinfo.trim());
				digester.parse(strReader);
			} catch (MalformedURLException mue) {
				throw new UncheckedArchivoException(
						"Error leyendo xml de unidad documental histórica", mue);
			} catch (IOException ioe) {
				throw new UncheckedArchivoException(
						"Error leyendo xml de unidad documental histórica", ioe);
			} catch (SAXException saxe) {
				throw new UncheckedArchivoException(
						"Error leyendo xml de unidad documental histórica",
						saxe);
			}
	}
}
