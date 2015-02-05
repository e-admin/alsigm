package xml.config;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import xml.XMLObject;

import common.Constants;


/**
 * Clase que almacena la configuración de la descripción.
 */
public class ConfiguracionDescripcion extends XMLObject
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Identificador del campo que contiene la fecha extrema inicial. */
	private String fechaExtremaInicial = null;

    /** Identificador del campo que contiene la fecha extrema final. */
	private String fechaExtremaFinal = null;

    /** Identificador del campo que contiene el id de tercero del interesado. */
	private String idTerceroInteresado = null;

//	/** Plantilla XSL para mostrar la ficha de descripción en modo consulta. */
//	private String plantillaXSLConsulta = null;
//
//	/** Plantilla XSL para mostrar la ficha de descripción en modo edición. */
//	private String plantillaXSLEdicion = null;
//
//	/** Plantilla XSL para exportar la ficha de descripción. */
//	private String plantillaXSLExportacion = null;

    /** Identificador del campo que contiene el id de productor para las fichas de fondos y clasificadores de series. */
	private String productor = null;

	/** Identificador del campo cantidad de la ficha de unidad documental */
	private String cantidadVolumenSoporte = null;

	/** Identificadores de los campos cantidad y soporte documental de las fichas de fondos, series y clasificadores de series**/
	private String cantidadVolumenSoporteDocumental = null;
	private String soporteDocumental = null;

	/** Identificadores de los campos productor, fecha de inicio de productor y fecha de fin de productor de la ficha de serie **/
	private String productorSerie = null;
	private String fechaInicioProductorSerie = null;
	private String fechaFinProductorSerie = null;

	private String rangoInicial = null;
	private String rangoFinal = null;
	private String rangoInicialNormalizado = null;
	private String rangoFinalNormalizado = null;

	/** Identificadores de los campos interesado identidad e interesado principal de la unidad documental **/
	private String interesadoIdentidad = null;
	private String interesadoPrincipal = null;
	private String interesadoNumeroIdentidad = null;
	private String interesadoRol = null;
	private String interesadoValidado = null;

	/** Identificador del campo denominación de expediente **/
	private String denominacionExpediente = null;

	/** Identificador del campo Unidades Documentales Relacionadas */
	private String udocsRel = null;

	/** Identificadores de campos para los reemplazables de la ficha. */
	private Map camposNoReemplazables=null;
	//private String idCampoNoReemplazable=null;

	/** Identificador del campo Condiciones de Acceso */
	private String condicionesAcceso = null;

	/**
	 * Identificador de la tabla Interesado Principal
	 */
	private String idTablaInteresados = null;

	/**
	 * Valor cuando el interesado es principal (Defecto Si)
	 */
	private String valorInteresadoPrincipal = null;


	/**
	 */
	private String idTablaRangos = null;

	private String idFechaDoc = null;

	/**
	 * Constructor.
	 */
	public ConfiguracionDescripcion()
	{
		super();
	}


	/**
	 * Obtiene el identificador del campo que contiene la fecha extrema final.
	 * @return Identificador del campo que contiene la fecha extrema final.
	 */
	public String getFechaExtremaFinal()
	{
		return fechaExtremaFinal;
	}


	/**
	 * Establece el identificador del campo que contiene la fecha extrema final.
	 * @param fechaExtremaFinal Identificador del campo que contiene la fecha extrema final.
	 */
	public void setFechaExtremaFinal(String fechaExtremaFinal)
	{
		this.fechaExtremaFinal = fechaExtremaFinal;
	}


	/**
	 * Obtiene el identificador del campo que contiene la fecha extrema inicial.
	 * @return Identificador del campo que contiene la fecha extrema inicial.
	 */
	public String getFechaExtremaInicial()
	{
		return fechaExtremaInicial;
	}


	/**
	 * Establece el identificador del campo que contiene la fecha extrema inicial.
	 * @param fechaExtremaInicial Identificador del campo que contiene la fecha extrema inicial.
	 */
	public void setFechaExtremaInicial(String fechaExtremaInicial)
	{
		this.fechaExtremaInicial = fechaExtremaInicial;
	}


//	/**
//	 * Obtiene la plantilla XSL para mostrar la ficha de descripción en modo consulta.
//	 * @return Plantilla XSL para mostrar la ficha de descripción en modo consulta.
//	 */
//	public String getPlantillaXSLConsulta()
//	{
//		return plantillaXSLConsulta;
//	}


	/**
	 * Obtiene el identificador del campo que contiene el id de tercero del interesado.
	 * @return Identificador del campo que contiene el id de tercero del interesado.
	 */
    public String getIdTerceroInteresado()
    {
        return idTerceroInteresado;
    }


    /**
     * Establece el identificador del campo que contiene el id de tercero del interesado.
     * @param idTerceroInteresado Identificador del campo que contiene el id de tercero del interesado.
     */
    public void setIdTerceroInteresado(String idTerceroInteresado)
    {
        this.idTerceroInteresado = idTerceroInteresado;
    }


//	/**
//	 * Establece la plantilla XSL para mostrar la ficha de descripción en modo consulta.
//	 * @param plantillaXSLConsulta Plantilla XSL para mostrar la ficha de descripción en modo consulta.
//	 */
//	public void setPlantillaXSLConsulta(String plantillaXSLConsulta)
//	{
//		this.plantillaXSLConsulta = plantillaXSLConsulta;
//	}
//
//
//	/**
//	 * Obtiene la plantilla XSL para mostrar la ficha de descripción en modo edición.
//	 * @return Plantilla XSL para mostrar la ficha de descripción en modo edición.
//	 */
//	public String getPlantillaXSLEdicion()
//	{
//		return plantillaXSLEdicion;
//	}
//
//
//	/**
//	 * Establece la plantilla XSL para mostrar la ficha de descripción en modo edición.
//	 * @param plantillaXSLEdicion Plantilla XSL para mostrar la ficha de descripción en modo edición.
//	 */
//	public void setPlantillaXSLEdicion(String plantillaXSLEdicion)
//	{
//		this.plantillaXSLEdicion = plantillaXSLEdicion;
//	}
//
//	/**
//	 * Obtiene la plantilla XSL para exportar la ficha de descripción.
//	 * @return Plantilla XSL para exportar la ficha de descripción.
//	 */
//	public String getPlantillaXSLExportacion() {
//		return plantillaXSLExportacion;
//	}
//
//	/**
//	 * Establece la plantilla XSL para exportar la ficha de descripción.
//	 * @param plantillaXSLExportacion Plantilla XSL para exportar la ficha de descripción.
//	 */
//	public void setPlantillaXSLExportacion(String plantillaXSLExportacion) {
//		this.plantillaXSLExportacion = plantillaXSLExportacion;
//	}

	public String getProductor() {
		return productor;
	}


	public void setProductor(String productor) {
		this.productor = productor;
	}




	/**
	 * @return the condicionesAcceso
	 */
	public String getCondicionesAcceso() {
		return condicionesAcceso;
	}


	/**
	 * @param condicionesAcceso the condicionesAcceso to set
	 */
	public void setCondicionesAcceso(String condicionesAcceso) {
		this.condicionesAcceso = condicionesAcceso;
	}

	public String getIdFechaDoc() {
		return idFechaDoc;
	}


	public void setIdFechaDoc(String idFechaDoc) {
		this.idFechaDoc = idFechaDoc;
	}

	/**
	 * Obtiene una representación XML del objeto.
	 * @param indent Número de espacios de tabulación.
	 * @return Representación del objeto.
	 */
	public String toXML(int indent)
	{
		final StringBuffer xml = new StringBuffer();
		final String tabs = StringUtils.repeat(" ", indent);

		// Tag de inicio
		xml.append(tabs + "<Configuracion_Descripcion>");
		xml.append(Constants.NEWLINE);

		// Campos_Descriptivos
		xml.append(tabs + "  <Campos_Descriptivos>");
		xml.append(Constants.NEWLINE);

		// FechaExtremaInicial
		xml.append(tabs + "    <Fecha_Extrema_Inicial>");
		xml.append(fechaExtremaInicial != null ? fechaExtremaInicial : "");
		xml.append("</Fecha_Extrema_Inicial>");
		xml.append(Constants.NEWLINE);

		// FechaExtremaFinal
		xml.append(tabs + "    <Fecha_Extrema_Final>");
		xml.append(fechaExtremaFinal != null ? fechaExtremaFinal : "");
		xml.append("</Fecha_Extrema_Final>");
		xml.append(Constants.NEWLINE);

		// IdTerceroInteresado
		xml.append(tabs + "    <Id_Tercero_Interesado>");
		xml.append(idTerceroInteresado != null ? idTerceroInteresado : "");
		xml.append("</Id_Tercero_Interesado>");
		xml.append(Constants.NEWLINE);

		// RangoInicial
		xml.append(tabs + "    <RangoInicial>");
		xml.append(rangoInicial != null ? rangoInicial : "");
		xml.append("</RangoInicial>");
		xml.append(Constants.NEWLINE);

		// RangoFinal
		xml.append(tabs + "    <RangoFinal>");
		xml.append(rangoFinal != null ? rangoFinal : "");
		xml.append("</RangoFinal>");
		xml.append(Constants.NEWLINE);

		// RangoInicialNorm
		xml.append(tabs + "    <RangoInicialNorm>");
		xml.append(rangoInicialNormalizado != null ? rangoInicialNormalizado : "");
		xml.append("</RangoInicialNorm>");
		xml.append(Constants.NEWLINE);

		// RangoFinalNorm
		xml.append(tabs + "    <RangoFinalNorm>");
		xml.append(rangoFinalNormalizado != null ? rangoFinalNormalizado : "");
		xml.append("</RangoFinalNorm>");
		xml.append(Constants.NEWLINE);

		//Campos_no_reemplazables
		xml.append(tabs + "    <Campos_no_reemplazables>");
		if(camposNoReemplazables!=null){
			for(Iterator it=camposNoReemplazables.entrySet().iterator();it.hasNext();){
				Map.Entry par=(Map.Entry)it.next();
				xml.append(Constants.NEWLINE);
				xml.append(tabs + "       <Campo>");
				xml.append((String)par.getKey());
				xml.append("</Campo>");
			}
		}
		xml.append(Constants.NEWLINE);
		xml.append(tabs + "    </Campos_no_reemplazables>");
		xml.append(Constants.NEWLINE);

/*		<Cantidad_Volumen_Soporte>7</Cantidad_Volumen_Soporte>
		<Cantidad_Volumen_Soporte_Documental>214</Cantidad_Volumen_Soporte_Documental>
		<Soporte_Documental>8</Soporte_Documental>
		<Productor>16</Productor>
		<Productor_Serie>34</Productor_Serie>
		<Fecha_Inicio_Productor_Serie>36</Fecha_Inicio_Productor_Serie>
		<Fecha_Fin_Productor_Serie>37</Fecha_Fin_Productor_Serie>
*/
		// Cantidad Volumen Soporte
		xml.append(tabs + "    <Cantidad_Volumen_Soporte>");
		xml.append(cantidadVolumenSoporte != null ? cantidadVolumenSoporte : "");
		xml.append("</Cantidad_Volumen_Soporte>");
		xml.append(Constants.NEWLINE);

		// Cantidad Volumen Soporte Documental
		xml.append(tabs + "    <Cantidad_Volumen_Soporte_Documental>");
		xml.append(cantidadVolumenSoporteDocumental != null ? cantidadVolumenSoporteDocumental : "");
		xml.append("</Cantidad_Volumen_Soporte_Documental>");
		xml.append(Constants.NEWLINE);

		// Soporte Documental
		xml.append(tabs + "    <Soporte_Documental>");
		xml.append(soporteDocumental != null ? soporteDocumental : "");
		xml.append("</Soporte_Documental>");
		xml.append(Constants.NEWLINE);

		// Productor
		xml.append(tabs + "    <Productor>");
		xml.append(productor != null ? productor : "");
		xml.append("</Productor>");
		xml.append(Constants.NEWLINE);

		// Productor Serie
		xml.append(tabs + "    <Productor_Serie>");
		xml.append(productorSerie != null ? productorSerie : "");
		xml.append("</Productor_Serie>");
		xml.append(Constants.NEWLINE);

		// Fecha Inicio Productor Serie
		xml.append(tabs + "    <Fecha_Inicio_Productor_Serie>");
		xml.append(fechaInicioProductorSerie != null ? fechaInicioProductorSerie : "");
		xml.append("</Fecha_Inicio_Productor_Serie>");
		xml.append(Constants.NEWLINE);

		// Fecha Fin Productor Serie
		xml.append(tabs + "    <Fecha_Fin_Productor_Serie>");
		xml.append(fechaFinProductorSerie != null ? fechaFinProductorSerie : "");
		xml.append("</Fecha_Fin_Productor_Serie>");
		xml.append(Constants.NEWLINE);

		// Fecha Fin Productor Serie
		xml.append(tabs + "    <Condiciones_Acceso>");
		xml.append(condicionesAcceso != null ? condicionesAcceso : "");
		xml.append("</Condiciones_Acceso>");
		xml.append(Constants.NEWLINE);

		// Fecha del Documento Fisico
		xml.append(tabs + "    <Fecha_Documento>");
		xml.append(idFechaDoc != null ? idFechaDoc : "");
		xml.append("</Fecha_Documento>");
		xml.append(Constants.NEWLINE);

		// Cierre Campos_Descriptivos
		xml.append(tabs + "  </Campos_Descriptivos>");
		xml.append(Constants.NEWLINE);

//		// Plantillas_XSL
//		xml.append(tabs + "  <Plantillas_XSL>");
//		xml.append(Constants.NEWLINE);
//
//		// Consulta
//		xml.append(tabs + "    <Consulta>");
//		xml.append(plantillaXSLConsulta != null ? plantillaXSLConsulta : "");
//		xml.append("</Consulta>");
//		xml.append(Constants.NEWLINE);
//
//		// Edicion
//		xml.append(tabs + "    <Edicion>");
//		xml.append(plantillaXSLEdicion != null ? plantillaXSLEdicion : "");
//		xml.append("</Edicion>");
//		xml.append(Constants.NEWLINE);
//
//		// Exportacion
//		xml.append(tabs + "    <Exportacion>");
//		xml.append(plantillaXSLExportacion != null ? plantillaXSLExportacion : "");
//		xml.append("</Exportacion>");
//		xml.append(Constants.NEWLINE);
//
//		// Cierre Plantillas_XSL
//		xml.append(tabs + "  </Plantillas_XSL>");
//		xml.append(Constants.NEWLINE);

		// Tag de cierre
		xml.append(tabs + "</Configuracion_Descripcion>");
		xml.append(Constants.NEWLINE);

		return xml.toString();
	}

//	public void setIdCampo(String idCampo){
//		idCampoNoReemplazable=idCampo;
//	}

	/*public void addCampo(){
		if(camposNoReemplazables==null) camposNoReemplazables=new HashMap();
		if(idCampoNoReemplazable!=null){
			camposNoReemplazables.put(idCampoNoReemplazable,idCampoNoReemplazable);
			idCampoNoReemplazable=null;
		}
	}*/

	public void addIdCampo(String idCampo){
		if(camposNoReemplazables==null) camposNoReemplazables=new HashMap();
		camposNoReemplazables.put(idCampo,idCampo);
	}

	public Map getCamposNoReemplazables(){
		return camposNoReemplazables;
	}


	public String getCantidadVolumenSoporte() {
		return cantidadVolumenSoporte;
	}


	public void setCantidadVolumenSoporte(String cantidadVolumenSoporte) {
		this.cantidadVolumenSoporte = cantidadVolumenSoporte;
	}


	public String getCantidadVolumenSoporteDocumental() {
		return cantidadVolumenSoporteDocumental;
	}


	public void setCantidadVolumenSoporteDocumental(
			String cantidadVolumenSoporteDocumental) {
		this.cantidadVolumenSoporteDocumental = cantidadVolumenSoporteDocumental;
	}


	public String getSoporteDocumental() {
		return soporteDocumental;
	}


	public void setSoporteDocumental(String soporteDocumental) {
		this.soporteDocumental = soporteDocumental;
	}


	public String getFechaFinProductorSerie() {
		return fechaFinProductorSerie;
	}


	public void setFechaFinProductorSerie(String fechaFinProductorSerie) {
		this.fechaFinProductorSerie = fechaFinProductorSerie;
	}


	public String getFechaInicioProductorSerie() {
		return fechaInicioProductorSerie;
	}


	public void setFechaInicioProductorSerie(String fechaInicioProductorSerie) {
		this.fechaInicioProductorSerie = fechaInicioProductorSerie;
	}


	public String getProductorSerie() {
		return productorSerie;
	}


	public void setProductorSerie(String productorSerie) {
		this.productorSerie = productorSerie;
	}


	public String getRangoFinal() {
		return rangoFinal;
	}


	public void setRangoFinal(String rangoFinal) {
		this.rangoFinal = rangoFinal;
	}


	public String getRangoFinalNormalizado() {
		return rangoFinalNormalizado;
	}


	public void setRangoFinalNormalizado(String rangoFinalNormalizado) {
		this.rangoFinalNormalizado = rangoFinalNormalizado;
	}


	public String getRangoInicial() {
		return rangoInicial;
	}


	public void setRangoInicial(String rangoInicial) {
		this.rangoInicial = rangoInicial;
	}


	public String getRangoInicialNormalizado() {
		return rangoInicialNormalizado;
	}


	public void setRangoInicialNormalizado(String rangoInicialNormalizado) {
		this.rangoInicialNormalizado = rangoInicialNormalizado;
	}


	public String getInteresadoIdentidad() {
		return interesadoIdentidad;
	}


	public void setInteresadoIdentidad(String interesadoIdentidad) {
		this.interesadoIdentidad = interesadoIdentidad;
	}


	public String getInteresadoPrincipal() {
		return interesadoPrincipal;
	}


	public void setInteresadoPrincipal(String interesadoPrincipal) {
		this.interesadoPrincipal = interesadoPrincipal;
	}


	public String getInteresadoNumeroIdentidad() {
		return interesadoNumeroIdentidad;
	}


	public void setInteresadoNumeroIdentidad(String interesadoNumeroIdentidad) {
		this.interesadoNumeroIdentidad = interesadoNumeroIdentidad;
	}


	public String getInteresadoRol() {
		return interesadoRol;
	}


	public void setInteresadoRol(String interesadoRol) {
		this.interesadoRol = interesadoRol;
	}


	public String getInteresadoValidado() {
		return interesadoValidado;
	}


	public void setInteresadoValidado(String interesadoValidado) {
		this.interesadoValidado = interesadoValidado;
	}


	public String getDenominacionExpediente() {
		return denominacionExpediente;
	}

	public void setDenominacionExpediente(String denominacionExpediente) {
		this.denominacionExpediente = denominacionExpediente;
	}

	public String getUdocsRel() {
		return udocsRel;
	}

	public void setUdocsRel(String udocsRel) {
		this.udocsRel = udocsRel;
	}


	public String getIdTablaInteresados() {
		return idTablaInteresados;
	}


	public void setIdTablaInteresados(String idTablaInteresados) {
		this.idTablaInteresados = idTablaInteresados;
	}


	public String getValorInteresadoPrincipal() {
		return valorInteresadoPrincipal;
	}


	public void setValorInteresadoPrincipal(String valorInteresadoPrincipal) {
		this.valorInteresadoPrincipal = valorInteresadoPrincipal;
	}


	public String getIdTablaRangos() {
		return idTablaRangos;
	}


	public void setIdTablaRangos(String idTablaRangos) {
		this.idTablaRangos = idTablaRangos;
	}

}
