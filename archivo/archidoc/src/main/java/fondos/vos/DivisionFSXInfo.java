package fondos.vos;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.commons.lang.StringUtils;

import common.Constants;

/**
 * Parte de la informacion referente a una división de fracción de serie que se
 * almacena en forma de xml. Para su tratamiento se genera una jerarquia de
 * objetos que representan la informacion contenida en ese xml. sirve como
 * contenedor de esa jerarquía de objetos
 */
public class DivisionFSXInfo {

	/** Asunto de la fracción de serie */
	String asunto = null;
	/** Organo productor de la fracción de serie */
	OrganoProductorVO productor = null;
	/** Fecha inicial de la fracción de serie */
	String fechaExtIni = null;
	/** Fecha final de la unidad documental */
	String fechaExtFin = null;
	/**
	 * Rangos entre los que se encuentran los expedientes que forman parte de
	 * una unidad documental de nivel fracción de serie
	 */
	// List rangos = null;
	String codReferencia = null;
	/** Identificador de la fracción de serie en la relación de entrega */
	String idFSEnREntrega = null;
	/** Identificador de la caja original en la que estaba la fracción de serie **/
	String idUIDeposito = null;
	/** Nombre del archivo al que pertenecía la fracción de serie original **/
	String nombreArchivo = null;
	/** Titulo de la serie a la que pertenecía la fraccuión de serie original **/
	String tituloSerie = null;

	public OrganoProductorVO getProductor() {
		return productor;
	}

	public void setProductor(OrganoProductorVO organoProductorVO) {
		productor = organoProductorVO;
	}

	public void setProductor(String id, String nombre) {
		productor = new OrganoProductorVO();
		productor.setId(id);
		productor.setNombre(nombre);
	}

	static DateFormat dateFormater = new SimpleDateFormat("dd/MM/yyyy");

	public String asXML() {

		StringBuffer xmlInfo = new StringBuffer();
		xmlInfo.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>");
		xmlInfo.append("<DIVISION_FRACCION_SERIE>");
		xmlInfo.append("<FRACCION_SERIE_DIVIDIDA>");
		xmlInfo.append("<ASUNTO>").append(Constants.addCData(this.asunto))
				.append("</ASUNTO>");
		if (StringUtils.isNotEmpty(this.codReferencia))
			xmlInfo.append("<CODIGO_REFERENCIA>").append(this.codReferencia)
					.append("</CODIGO_REFERENCIA>");
		if (this.fechaExtIni != null)
			xmlInfo.append("<FECHA_INICIAL>")
					.append("<FECHA FMT=\"DDMMAAAA\" SEP=\"/\" CALIF=\"\">")
					.append(dateFormater.format(this.fechaExtIni))
					.append("</FECHA></FECHA_INICIAL>");
		if (this.fechaExtFin != null)
			xmlInfo.append("<FECHA_FINAL>")
					.append("<FECHA FMT=\"DDMMAAAA\" SEP=\"/\" CALIF=\"\">")
					.append(dateFormater.format(this.fechaExtFin))
					.append("</FECHA></FECHA_FINAL>");
		if (productor != null) {
			xmlInfo.append("<PRODUCTOR>");
			if (StringUtils.isNotEmpty(productor.getId()))
				xmlInfo.append("<ID>").append(productor.getId())
						.append("</ID>");
			xmlInfo.append("<NOMBRE>")
					.append(Constants.addCData(StringUtils
							.defaultString(productor.getNombre())))
					.append("</NOMBRE>");
			xmlInfo.append("</PRODUCTOR>");
		}
		if (StringUtils.isNotEmpty(this.idFSEnREntrega))
			xmlInfo.append("<IDFSRE>").append(this.idFSEnREntrega)
					.append("</IDFSRE>");
		if (StringUtils.isNotEmpty(this.idUIDeposito))
			xmlInfo.append("<IDUIDEPOSITO>").append(this.idUIDeposito)
					.append("</IDUIDEPOSITO>");
		if (StringUtils.isNotEmpty(this.nombreArchivo))
			xmlInfo.append("<NOMBRE_ARCHIVO>").append(this.nombreArchivo)
					.append("</NOMBRE_ARCHIVO>");
		xmlInfo.append("</FRACCION_SERIE_DIVIDIDA>");
		xmlInfo.append("</DIVISION_FRACCION_SERIE>");

		/*
		 * if (this.rangos != null && this.rangos.size() > 0) {
		 * asXML.append("<RANGOS>"); RangoVO unRango = null; for (Iterator
		 * i=this.rangos.iterator();i.hasNext();) { unRango = (RangoVO)i.next();
		 * asXML.append("<RANGO>");
		 * asXML.append("<DESDE>").append(Constants.addCData
		 * (StringUtils.defaultString(unRango.getDesde()))).append("</DESDE>");
		 * asXML
		 * .append("<HASTA>").append(Constants.addCData(StringUtils.defaultString
		 * (unRango.getHasta()))).append("</HASTA>"); asXML.append("</RANGO>");
		 * } asXML.append("</RANGOS>"); }
		 */
		return xmlInfo.toString();
	}

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public String getFechaExtFin() {
		return fechaExtFin;
	}

	public void setFechaExtFin(String fechaExtFin) {
		this.fechaExtFin = fechaExtFin;
	}

	public String getFechaExtIni() {
		return fechaExtIni;
	}

	public void setFechaExtIni(String fechaExtIni) {
		this.fechaExtIni = fechaExtIni;
	}

	public String getCodReferencia() {
		return codReferencia;
	}

	public void setCodReferencia(String codReferencia) {
		this.codReferencia = codReferencia;
	}

	public String getIdFSEnREntrega() {
		return idFSEnREntrega;
	}

	public void setIdFSEnREntrega(String idFSEnREntrega) {
		this.idFSEnREntrega = idFSEnREntrega;
	}

	public String getIdUIDeposito() {
		return idUIDeposito;
	}

	public void setIdUIDeposito(String idUIDeposito) {
		this.idUIDeposito = idUIDeposito;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public String getTituloSerie() {
		return tituloSerie;
	}

	public void setTituloSerie(String tituloSerie) {
		this.tituloSerie = tituloSerie;
	}

	/*
	 * void reset() { this.rangos = null; } public List getRangos() { return
	 * rangos; } public void setRangos(List rangos) { this.rangos = rangos; }
	 * 
	 * public void addRango(RangoVO rango) { if (this.rangos == null)
	 * this.rangos = new ArrayList(); this.rangos.add(rango); }
	 */
}
