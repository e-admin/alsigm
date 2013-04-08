package transferencias.vos;

import ieci.core.types.IeciTdType;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import common.db.DBUtils;

/**
 * Value Object con la informacion referente a una unidad documental
 */
public class UnidadDocumentalBaseVO {

	/** Logger de la clase */
	protected final static Logger logger = Logger
			.getLogger(UnidadDocumentalBaseVO.class);

	/** Identificador de la unidad documental */
	protected String id;
	/**
	 * Identificador de la relación de entrega en la que se transfiere la unidad
	 * documental
	 */
	protected String idRelEntrega;
	/** Tipo de transferencia en la que es transferida la unidad documental */
	protected int tipoTransferencia;
	/** Número de expediente con el que se corresponde la unidad documental */
	protected String numeroExpediente;
	/**
	 * Código del sistema productor mediante el que ha sido tramitado el
	 * expediente que constituye la unidad documental
	 */
	protected String codSistProductor;
	/**
	 * Fecha inicial de la unidad documental. Si la unidad documental se
	 * corresponde con un expediente será la fecha de apertura del expediente
	 */
	// protected String fechaInicio;
	protected Date fechaInicio;
	/**
	 * Fecha final de la unidad documental. Si la unidad documental se
	 * corresponde con un expediente será la fehca de cierre del expediente
	 */
	// protected String fechaFin;
	protected Date fechaFin;
	/** Asunto de la unidad documental */
	protected String asunto;
	/** Indicador de si la unidad documental contiene algún documento físico */
	protected String sinDocsFisicos = null;
	/** Estado de cotejo de la unidad documental */
	protected int estadoCotejo;
	/** Indicativo de si la unidad documental se encuentra validada o no */
	protected boolean validada;
	/** Observaciones */
	protected String observaciones;
	/**
	 * Número de partes en las que ha sido dividida la unidad documental. La
	 * división de una unidad documental en varias partes se produce cuando por
	 * su volumen no puede ser instalada en una única unidad de instalación
	 */
	protected int numeroPartes = 1;
	/** Indicativo si se ha extraido la información extendida almacenada en XML */
	// private boolean parsedXMLinfo = false;

	/** Información extendida de la unidad documental */
	// protected UnidadDocumentalXInfo xinfo = new UnidadDocumentalXInfo();

	/*
	 * Control de acciones sobre una unidad documental incluida en una relacion
	 * de entrega
	 */
	boolean puedeSerEliminadaDeRelacion = false;
	boolean permitidoRealizarCambios = false;

	/**
	 * Autonumerico que representa el orden de inserción del registro en la
	 * tabla
	 */
	private int orden = IeciTdType.NULL_LONG_INTEGER;

	private String uiOcupadas = null;

	public UnidadDocumentalBaseVO() {
	}

	public UnidadDocumentalBaseVO(String numeroExpediente) {
		this.numeroExpediente = numeroExpediente;
	}

	public UnidadDocumentalBaseVO(String id, String numeroExpediente) {
		this.id = id;
		this.numeroExpediente = numeroExpediente;
	}

	public String getNumeroExpediente() {
		return numeroExpediente;
	}

	public void setNumeroExpediente(String numeroExpediente) {
		this.numeroExpediente = numeroExpediente;
	}

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	/*
	 * public void setProductor(String id, String nombre, String codigoOrgano) {
	 * getExtraInfo().setProductor(id, nombre, codigoOrgano); }
	 * 
	 * public List getInteresados() { return getExtraInfo().getInteresados(); }
	 * public void setInteresados(List interesados) {
	 * getExtraInfo().setInteresados(interesados); }
	 * 
	 * public void addInteresado(InteresadoVO interesado) {
	 * getExtraInfo().addInteresado(interesado); }
	 * 
	 * public List getSoportes() { return getExtraInfo().getSoportes(); } public
	 * void addDocumento(DocumentoVO documento) {
	 * getExtraInfo().addDocumento(documento); }
	 * 
	 * public void setDocumentosFisicos(List documentosFisicos) {
	 * getExtraInfo().setDocumentosFisicos(documentosFisicos); } public List
	 * getDocumentosFisicos() { return getExtraInfo().documentosFisicos; }
	 * public void setDocumentosElectronicos(List documentosElectronicos) {
	 * getExtraInfo().setDocumentosElectronicos(documentosElectronicos); }
	 * public List getDocumentosElectronicos() { return
	 * getExtraInfo().documentosElectronicos; } public List getEmplazamientos()
	 * { return getExtraInfo().emplazamientos; } public void
	 * setEmplazamientos(List emplazamientos) {
	 * getExtraInfo().setEmplazamientos(emplazamientos); }
	 * 
	 * public void addEmplazamiento(Direccion emplazamiento) {
	 * this.getExtraInfo().addEmplazamiento(emplazamiento); }
	 * 
	 * public List getDocumentos(int tipoDocumento) { if (tipoDocumento ==
	 * DocumentoVO.DOCUMENTO_FISICO) return getExtraInfo().documentosFisicos;
	 * else if (tipoDocumento == DocumentoVO.DOCUMENTO_ELECTRONICO) return
	 * getExtraInfo().documentosElectronicos; throw new
	 * UncheckedArchivoException("Tipo de documento no reconocido"); }
	 * 
	 * public InteresadoVO getInteresadoPrincipal() { return
	 * getExtraInfo().interesadoPrincipal; }
	 * 
	 * public void setInteresadoPrincipal(InteresadoVO interesadoPrincipal) {
	 * getExtraInfo().setInteresadoPrincipal(interesadoPrincipal); }
	 * 
	 * public void setSistemaProductor(String codigo, String nombre) {
	 * getExtraInfo().setSistemaProductor(codigo, nombre); this.codSistProductor
	 * = codigo; } public Map.Entry getSistemaProductor() { return
	 * getExtraInfo().getSistemaProductor(); }
	 * 
	 * public ProductorUnidadDocumentalVO getProductor() { return
	 * getExtraInfo().getProductor(); }
	 * 
	 * UnidadDocumentalXInfo getExtraInfo() { return this.xinfo; }
	 */

	static DateFormat dateFormater = new SimpleDateFormat("dd/MM/yyyy");

	/*
	 * public String asXML() { StringBuffer xmlInfo = new StringBuffer();
	 * xmlInfo.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>");
	 * xmlInfo.append("<UNIDAD_DOCUMENTAL_TRANSFERENCIA version=\"1.1\">");
	 * xmlInfo.append("<EXPEDIENTE>");
	 * xmlInfo.append("<NUMERO>").append(Constants
	 * .addCData(this.numeroExpediente)).append("</NUMERO>"); Map.Entry
	 * sistemaProductor = xinfo.getSistemaProductor(); if (sistemaProductor !=
	 * null) { xmlInfo.append("<SISTEMA_PRODUCTOR>");
	 * xmlInfo.append("<CODIGO>").
	 * append(sistemaProductor.getKey()).append("</CODIGO>");
	 * xmlInfo.append("<NOMBRE>"
	 * ).append(Constants.addCData(sistemaProductor.getValue
	 * ().toString())).append("</NOMBRE>");
	 * xmlInfo.append("</SISTEMA_PRODUCTOR>"); }
	 * xmlInfo.append("</EXPEDIENTE>");
	 * xmlInfo.append("<ASUNTO>").append(Constants
	 * .addCData(this.asunto)).append("</ASUNTO>"); if (getFechaInicio() !=
	 * null) xmlInfo.append("<FECHA_INICIAL>").append(
	 * "<FECHA FMT=\"DDMMAAAA\" SEP=\"/\" CALIF=\"\">"
	 * ).append(dateFormater.format
	 * (getFechaInicio())).append("</FECHA></FECHA_INICIAL>"); if (getFechaFin()
	 * != null) xmlInfo.append("<FECHA_FINAL>").append(
	 * "<FECHA FMT=\"DDMMAAAA\" SEP=\"/\" CALIF=\"\">"
	 * ).append(dateFormater.format
	 * (getFechaFin())).append("</FECHA></FECHA_FINAL>");
	 * ProductorUnidadDocumentalVO productor = xinfo.getProductor(); if
	 * (productor != null) { xmlInfo.append("<PRODUCTOR>"); if
	 * (productor.getId() != null)
	 * xmlInfo.append("<ID>").append(productor.getId()).append("</ID>"); if
	 * (productor.getCodigoOrgano() != null)
	 * xmlInfo.append("<CODIGO_ORGANO>").append
	 * (productor.getCodigoOrgano()).append("</CODIGO_ORGANO>");
	 * xmlInfo.append("<NOMBRE>"
	 * ).append(Constants.addCData(StringUtils.defaultString
	 * (productor.getNombre()))).append("</NOMBRE>");
	 * xmlInfo.append("</PRODUCTOR>"); }
	 * xmlInfo.append("<PUBLICACION_EN_SAC>").append(xinfo.isEnSAC()?
	 * Constants.TRUE_STRING :
	 * Constants.FALSE_STRING).append("</PUBLICACION_EN_SAC>");
	 * xmlInfo.append(xinfo.toXMLString());
	 * xmlInfo.append("</UNIDAD_DOCUMENTAL_TRANSFERENCIA>");
	 * 
	 * //String xml = xmlInfo.toString();
	 * 
	 * return xmlInfo.toString(); }
	 */

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/*
	 * public int getNumeroDocumentos() { List documentosFisicos =
	 * getExtraInfo().documentosFisicos; List documentosElectronicos =
	 * getExtraInfo().documentosElectronicos;
	 * 
	 * return (documentosFisicos != null ? documentosFisicos.size() : 0) +
	 * (documentosElectronicos != null ? documentosElectronicos.size() : 0); }
	 */

	public boolean isValidada() {
		return validada;
	}

	public void setValidada(boolean validada) {
		this.validada = validada;
	}

	public int getEstadoCotejo() {
		return estadoCotejo;
	}

	public void setEstadoCotejo(int estadoCotejo) {
		this.estadoCotejo = estadoCotejo;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public boolean equals(Object obj) {
		if (obj instanceof UnidadDocumentalBaseVO)
			if (this.id != null)
				return this.id.equals(((UnidadDocumentalBaseVO) obj).id);

		return false;
	}

	public String getIdRelEntrega() {
		return idRelEntrega;
	}

	public void setIdRelEntrega(String idRelEntrega) {
		this.idRelEntrega = idRelEntrega;
	}

	/*
	 * public boolean tieneDocumentosFisicos() { boolean returnValue = false; if
	 * (sinDocsFisicos == null) { List documentosFisicos =
	 * getExtraInfo().documentosFisicos; returnValue = documentosFisicos != null
	 * && documentosFisicos.size() > 0; } else returnValue =
	 * sinDocsFisicos.equals(Constants.FALSE_STRING); return returnValue; }
	 * 
	 * 
	 * public boolean getTieneDocumentosFisicos(){ return
	 * tieneDocumentosFisicos(); }
	 * 
	 * public boolean tieneDocumentosFisicosYElectronicos() { boolean
	 * returnValue = false; List documentosFisicos =
	 * getExtraInfo().documentosFisicos; List documentosElectronicos =
	 * getExtraInfo().documentosElectronicos; if
	 * (!ListUtils.isEmpty(documentosFisicos) &&
	 * (!ListUtils.isEmpty(documentosElectronicos))) { returnValue = true; }
	 * return returnValue; }
	 * 
	 * public boolean getTieneDocumentosFisicosYElectronicos(){ return
	 * tieneDocumentosFisicosYElectronicos(); }
	 * 
	 * public void setTieneDocumentosFisicos(boolean tieneDocsFisicos) {
	 * sinDocsFisicos = TypeConverter.toString(!tieneDocsFisicos); }
	 * 
	 * public String getXmlinfo() { return asXML(); }
	 */

	// TODO revisar si se llama desde algún sitio
	/*
	 * public void setXmlinfo(String xmlinfo, String xmlinfoRules) { if (xmlinfo
	 * != null) try { //ConfiguracionTransferencias config =
	 * ConfiguracionSistemaArchivoFactory
	 * .getConfiguracionSistemaArchivo().getConfiguracionTransferencias(); //URL
	 * digesterRulesFile =
	 * getClass().getResource(config.getReglasProcesoInfoUnidadDocumental());
	 * //Digester digester = DigesterLoader.createDigester(digesterRulesFile);
	 * 
	 * String rulesXML = xmlinfoRules; InputSource is = new InputSource(new
	 * StringReader(rulesXML)); Digester digester =
	 * DigesterLoader.createDigester(is);
	 * 
	 * xinfo.reset(); digester.push(xinfo); StringReader strReader = new
	 * StringReader(xmlinfo.trim()); digester.parse(strReader); } catch
	 * (MalformedURLException mue) { throw new
	 * UncheckedArchivoException("Error leyendo xml de unidad documental", mue);
	 * } catch (IOException ioe) { throw new
	 * UncheckedArchivoException("Error leyendo xml de unidad documental", ioe);
	 * } catch (SAXException saxe) { throw new
	 * UncheckedArchivoException("Error leyendo xml de unidad documental",
	 * saxe); } }
	 */

	public int getNumeroPartes() {
		return numeroPartes;
	}

	public void setNumeroPartes(int numeroPartes) {
		this.numeroPartes = numeroPartes;
	}

	// TODO unicamente vale para el rellenado automatico. Implementar mapeo y
	// quitar
	/*
	 * public String getSinDocsFisicos() { return tieneDocumentosFisicos() ?
	 * Constants.FALSE_STRING : Constants.TRUE_STRING; }
	 */
	public void setSinDocsFisicos(String sindocsFisicos) {
		this.sinDocsFisicos = sindocsFisicos;
	}

	public String getValidada() {
		return DBUtils.getStringValue(isValidada());
	}

	public void setValidada(String value) {
		setValidada(DBUtils.getBooleanValue(value));
	}

	// public String getFechaExtIni() {
	// return fechaInicio;
	// }
	// public void setFechaExtIni(String fechaInicio) {
	// this.fechaInicio = fechaInicio;
	// }
	//
	// public String getFechaExtFin() {
	// return fechaFin;
	// }
	// public void setFechaExtFin(String fechaFin) {
	// this.fechaFin = fechaFin;
	// }

	/*
	 * public Date getFechaFin() { Date fFin = null; try { if
	 * (getExtraInfo().getFechaFin() != null) fFin =
	 * dateFormater.parse(getExtraInfo().getFechaFin()); return fFin; } catch
	 * (ParseException pe) { throw new UncheckedArchivoException(pe); } } public
	 * Date getFechaInicio() { Date fInicio = null; try { if
	 * (getExtraInfo().getFechaInicio() != null) fInicio =
	 * dateFormater.parse(getExtraInfo().getFechaInicio()); return fInicio; }
	 * catch (ParseException pe) { throw new UncheckedArchivoException(pe); } }
	 * 
	 * public void setFechaInicio(Date fechaInicio) { if (fechaInicio != null) {
	 * String fechaInicioAsString = dateFormater.format(fechaInicio);
	 * getExtraInfo().setFechaInicio(fechaInicioAsString); //
	 * setFechaExtIni(fechaInicioAsString); } } public void setFechaFin(Date
	 * fechaFin) { if (fechaFin != null) { String fechaFinAsString =
	 * dateFormater.format(fechaFin);
	 * getExtraInfo().setFechaFin(fechaFinAsString); //
	 * setFechaExtFin(fechaFinAsString); } }
	 */
	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	/*
	 * public void addSoporte(int numeroDocumentos, String formato, String
	 * soporte) { List listaSoportes = getExtraInfo().getSoportes();
	 * SoporteDocumentacionVO infoVolumen = new
	 * SoporteDocumentacionVO(numeroDocumentos, formato, soporte); int index =
	 * listaSoportes.indexOf(infoVolumen); if (index < 0)
	 * listaSoportes.add(infoVolumen); else listaSoportes.set(index,
	 * infoVolumen); }
	 */
	public boolean getPuedeSerEliminadaDeRelacion() {
		return puedeSerEliminadaDeRelacion;
	}

	public void setPuedeSerEliminadaDeRelacion(
			boolean puedeSerEliminadaDeRelacion) {
		this.puedeSerEliminadaDeRelacion = puedeSerEliminadaDeRelacion;
	}

	public boolean getPermitidoRealizarCambios() {
		return permitidoRealizarCambios;
	}

	public void setPermitidoRealizarCambios(boolean permitidoRealizarCambios) {
		this.permitidoRealizarCambios = permitidoRealizarCambios;
	}

	public int getTipoTransferencia() {
		return tipoTransferencia;
	}

	public void setTipoTransferencia(int tipoTransferencia) {
		this.tipoTransferencia = tipoTransferencia;
	}

	public final String getCodSistProductor() {
		return codSistProductor;
	}

	public final void setCodSistProductor(String codSistProductor) {
		this.codSistProductor = codSistProductor;
	}

	public int getOrden() {
		return orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}

	public String getUiOcupadas() {
		return uiOcupadas;
	}

	public void setUiOcupadas(String uiOcupadas) {
		this.uiOcupadas = uiOcupadas;
	}
}