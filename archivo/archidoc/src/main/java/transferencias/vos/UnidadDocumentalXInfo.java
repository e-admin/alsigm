package transferencias.vos;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.keyvalue.DefaultMapEntry;
import org.apache.commons.lang.StringUtils;

import common.Constants;
import common.vos.Direccion;

import descripcion.model.ValoresFicha;

/**
 * Parte de la informacion referente a una unidad documental se almacena en
 * forma de xml. Para su tratamiento se genera una jerarquia de objetos que
 * representan la informacion contenida en ese xml. UnidadDocumentalXInfo sirve
 * como contenedor de esa jerarquía de objetos
 */
// class UnidadDocumentalXInfo {
public class UnidadDocumentalXInfo {

	/** Lista de interesados asociados a la unidad documental */
	List interesados = null;
	/** Interesado principal */
	InteresadoVO interesadoPrincipal = null;
	/** Lista de emplazamientos asociados a la unidad documental */
	List emplazamientos = null;
	/** Lista de documentos que integran la unidad documental */
	List documentosFisicos = new ArrayList();
	List documentosElectronicos = new ArrayList();
	boolean enSAC;
	/** Organo productor de la unidad documental */
	ProductorUnidadDocumentalVO productor = null;
	/**
	 * Codigo y nombre del sistema productor mediante el que se ha tramitado el
	 * expediente en caso de que la tramitacion haya sido realizada mediante un
	 * sistema automatizado
	 */
	Map.Entry sistemaProductor = null;
	/**
	 * Fecha inicial de la unidad documental. Si la unidad documental se
	 * corresponde con un expediente será la fehca de apertura del expediente
	 */
	String fechaInicio = null;
	/**
	 * Fecha final de la unidad documental. Si la unidad documental se
	 * corresponde con un expediente será la fehca de cierre del expediente
	 */
	String fechaFin = null;
	/**
	 * Soportes en los que se encuentra la documentación que integra la unidad
	 * documental
	 */
	List soportes = new ArrayList();
	/**
	 * Rangos entre los que se encuentran los expedientes que forman parte de
	 * una unidad documental de nivel fracción de serie
	 */
	List rangos = null;
	/**
	 * Flag para indicar si ya se ha generado la descripción para esta unidad
	 * documental
	 **/
	boolean tieneDescripcion = false;
	/**
	 * Lista de valores de los campos de la ficha en el caso de que la unidad
	 * haya sido creada mediante una ficha de descripción.
	 */
	private Map listaValoresDescripcion = new LinkedHashMap();

	public boolean isEnSAC() {
		return enSAC;
	}

	public void setEnSAC(boolean enSAC) {
		this.enSAC = enSAC;
	}

	public void setEnSAC(String enSAC) {
		this.enSAC = enSAC != null && enSAC.equals("S");
	}

	public ProductorUnidadDocumentalVO getProductor() {
		return productor;
	}

	public void setProductor(String id, String nombre, String codigoOrgano) {
		this.productor = new ProductorUnidadDocumentalVO(id, nombre,
				codigoOrgano);
	}

	public Map.Entry getSistemaProductor() {
		return sistemaProductor;
	}

	public void setSistemaProductor(String codigo, String nombre) {
		this.sistemaProductor = new DefaultMapEntry(codigo, nombre);
	}

	public void addInteresado(InteresadoVO interesado) {
		if (this.interesados == null)
			this.interesados = new ArrayList();
		this.interesados.add(interesado);
		if (interesado.isPrincipal())
			this.interesadoPrincipal = interesado;
	}

	public void setInteresados(List interesados) {
		this.interesados = interesados;
	}

	public void addEmplazamiento(Direccion emplazamiento) {
		if (this.emplazamientos == null)
			this.emplazamientos = new ArrayList();
		this.emplazamientos.add(emplazamiento);
	}

	public void setEmplazamientos(List emplazamientos) {
		this.emplazamientos = emplazamientos;
	}

	public void addDocumento(DocumentoVO documento) {
		List listaDocumentos;
		if (documento.isElectronico())
			listaDocumentos = documentosElectronicos;
		else
			listaDocumentos = documentosFisicos;
		listaDocumentos.add(documento);
	}

	public void addDocumento(DocumentoElectronicoVO documento) {
		documentosElectronicos.add(documento);
	}

	public void setDocumentosFisicos(List documentosFisicos) {
		this.documentosFisicos.clear();
		if (documentosFisicos != null)
			this.documentosFisicos.addAll(documentosFisicos);
	}

	public List getDocumentosFisicos() {
		return this.documentosFisicos;
	}

	public void setDocumentosElectronicos(List documentosElectronicos) {
		this.documentosElectronicos.clear();
		if (documentosElectronicos != null)
			this.documentosElectronicos.addAll(documentosElectronicos);
	}

	public void addSoporte(SoporteDocumentacionVO infoVolumen) {
		getSoportes().add(infoVolumen);
	}

	public String toXMLString() {
		return toXMLStringWithValores(null);
	}

	public String toXMLStringWithValores(ValoresFicha valores) {
		StringBuffer asXML = new StringBuffer();
		if (this.interesados != null && this.interesados.size() > 0) {
			asXML.append("<INTERESADOS>");
			InteresadoVO unInteresado = null;
			for (Iterator i = this.interesados.iterator(); i.hasNext();) {
				unInteresado = (InteresadoVO) i.next();
				asXML.append("<INTERESADO>");
				asXML.append("<IDENTIDAD>")
						.append(Constants.addCData(StringUtils
								.defaultString(unInteresado.getNombre())))
						.append("</IDENTIDAD>");
				asXML.append("<NUM_IDENTIDAD>")
						.append(Constants.addCData(StringUtils
								.defaultString(unInteresado
										.getNumeroIdentificacion())))
						.append("</NUM_IDENTIDAD>");
				asXML.append("<ROL>")
						.append(Constants.addCData(StringUtils
								.defaultString(unInteresado.getRol())))
						.append("</ROL>");
				asXML.append("<PRINCIPAL>")
						.append(unInteresado == interesadoPrincipal ? Constants.TRUE_STRING
								: Constants.FALSE_STRING)
						.append("</PRINCIPAL>");
				asXML.append("<VALIDADO_EN_TERCEROS>")
						.append(unInteresado.isValidado() ? Constants.TRUE_STRING
								: Constants.FALSE_STRING)
						.append("</VALIDADO_EN_TERCEROS>");
				asXML.append("<ID_EN_TERCEROS>")
						.append(Constants.addCData(StringUtils
								.defaultString(unInteresado.getIdEnTerceros())))
						.append("</ID_EN_TERCEROS>");
				asXML.append("</INTERESADO>");
			}
			asXML.append("</INTERESADOS>");
		}
		if (this.emplazamientos != null) {
			asXML.append("<EMPLAZAMIENTOS>");
			Direccion unEmplazamiento = null;
			for (Iterator i = this.emplazamientos.iterator(); i.hasNext();) {
				unEmplazamiento = (Direccion) i.next();
				asXML.append("<EMPLAZAMIENTO>");
				asXML.append("<PAIS>")
						.append(Constants.addCData(StringUtils
								.defaultString(unEmplazamiento.pais)))
						.append("</PAIS>");
				// asXML.append("<COMUNIDAD>").append(Constants.addCData(StringUtils.defaultString(unEmplazamiento.comunidad))).append("</COMUNIDAD>");
				asXML.append("<PROVINCIA>")
						.append(Constants.addCData(StringUtils
								.defaultString(unEmplazamiento.comunidad)))
						.append("</PROVINCIA>");
				asXML.append("<CONCEJO>")
						.append(Constants.addCData(StringUtils
								.defaultString(unEmplazamiento.concejo)))
						.append("</CONCEJO>");
				// asXML.append("<PARROQUIA>").append(unEmplazamiento.parroquia).append("</PARROQUIA>");
				asXML.append("<POBLACION>")
						.append(Constants.addCData(StringUtils
								.defaultString(unEmplazamiento.poblacion)))
						.append("</POBLACION>");
				asXML.append("<LOCALIZACION>")
						.append(Constants.addCData(StringUtils
								.defaultString(unEmplazamiento.localizacion)))
						.append("</LOCALIZACION>");
				asXML.append("<VALIDADO>")
						.append(Constants.addCData(StringUtils
								.defaultString(unEmplazamiento.validado)))
						.append("</VALIDADO>");
				asXML.append("</EMPLAZAMIENTO>");
			}
			asXML.append("</EMPLAZAMIENTOS>");
		}
		asXML.append("<DOCUMENTOS>");
		// StringBuffer buff = new StringBuffer();
		int nDocumentosFisicos = 0, nDocumentosElectronicos = 0;
		// if (buff.length() > 0) {
		if (documentosFisicos != null && documentosFisicos.size() > 0) {
			asXML.append("<DOCUMENTOS_FISICOS>");
			for (Iterator i = this.documentosFisicos.iterator(); i.hasNext(); nDocumentosFisicos++) {
				DocumentoVO unDocumento = (DocumentoVO) i.next();
				asXML.append("<DOCUMENTO>")
						.append("<NOMBRE>")
						.append(Constants.addCData(unDocumento.nombre))
						.append("</NOMBRE>")
						.append("<DESCRIPCION>")
						.append(Constants.addCData(unDocumento.descripcion))
						.append("</DESCRIPCION>")
						.append("<INCLUIDO_EN_TRANFERENCIA>S</INCLUIDO_EN_TRANFERENCIA>")
						.append("</DOCUMENTO>");
			}
			asXML.append("</DOCUMENTOS_FISICOS>");
		}
		asXML.append("<NUM_DOCS_FISICOS>").append(nDocumentosFisicos)
				.append("</NUM_DOCS_FISICOS>");
		if (documentosElectronicos != null && documentosElectronicos.size() > 0) {
			asXML.append("<DOCUMENTOS_ELECTRONICOS>");
			for (Iterator i = this.documentosElectronicos.iterator(); i
					.hasNext(); nDocumentosElectronicos++) {
				DocumentoElectronicoVO unDocumento = (DocumentoElectronicoVO) i
						.next();
				asXML.append("<DOCUMENTO>")
						.append("<NOMBRE>")
						.append(Constants.addCData(unDocumento.nombre))
						.append("</NOMBRE>")
						.append("<DESCRIPCION>")
						.append(Constants.addCData(unDocumento.descripcion))
						.append("</DESCRIPCION>")
						.append("<REPOSITORIO>")
						.append(Constants.addCData(unDocumento.getRepositorio()))
						.append("</REPOSITORIO>")
						.append("<LOCALIZADOR>")
						.append(Constants.addCData(unDocumento.getLocalizador()))
						.append("</LOCALIZADOR>").append("<EXTENSION>")
						.append(Constants.addCData(unDocumento.getExtension()))
						.append("</EXTENSION>").append("</DOCUMENTO>");
			}
			asXML.append("</DOCUMENTOS_ELECTRONICOS>");
		}
		asXML.append("<NUM_DOCS_ELECTRONICOS>").append(nDocumentosElectronicos)
				.append("</NUM_DOCS_ELECTRONICOS>");
		if (soportes != null && soportes.size() > 0) {
			asXML.append("<VOLUMEN_Y_SOPORTE>");
			for (Iterator j = soportes.iterator(); j.hasNext();) {
				SoporteDocumentacionVO soporte = (SoporteDocumentacionVO) j
						.next();
				asXML.append("<SOPORTE>").append("<NUM_DOCS>")
						.append(soporte.getVolumen()).append("</NUM_DOCS>")
						.append("<FORMATO>")
						.append(Constants.addCData(soporte.getFormato()))
						.append("</FORMATO>").append("<TIPO>")
						.append(Constants.addCData(soporte.getSoporte()))
						.append("</TIPO>").append("</SOPORTE>");
			}
			asXML.append("</VOLUMEN_Y_SOPORTE>");
		}
		asXML.append("</DOCUMENTOS>");

		if (this.rangos != null && this.rangos.size() > 0) {
			asXML.append("<RANGOS>");
			RangoVO unRango = null;
			for (Iterator i = this.rangos.iterator(); i.hasNext();) {
				unRango = (RangoVO) i.next();
				asXML.append("<RANGO>");
				asXML.append("<DESDE>")
						.append(Constants.addCData(StringUtils
								.defaultString(unRango.getDesde())))
						.append("</DESDE>");
				asXML.append("<HASTA>")
						.append(Constants.addCData(StringUtils
								.defaultString(unRango.getHasta())))
						.append("</HASTA>");
				asXML.append("</RANGO>");
			}
			asXML.append("</RANGOS>");
		}

		asXML.append("<TIENE_DESCRIPCION>");
		if (this.tieneDescripcion)
			asXML.append(Constants.TRUE_STRING);
		else
			asXML.append(Constants.FALSE_STRING);
		asXML.append("</TIENE_DESCRIPCION>");

		if (valores != null)
			asXML.append(valores.generateXmlValoresDescripcionUdocRe());

		return asXML.toString();
	}

	public InteresadoVO getInteresadoPrincipal() {
		return interesadoPrincipal;
	}

	public void setInteresadoPrincipal(InteresadoVO interesado) {
		if (this.interesadoPrincipal != null)
			this.interesadoPrincipal.setPrincipal(false);
		this.interesadoPrincipal = interesado;
		if (interesado != null)
			interesado.setPrincipal(true);
	}

	// public String getFechaFin() {
	// return fechaFin;
	// }
	//
	// public void setFechaFin(String fechaFin) {
	// this.fechaFin = fechaFin;
	// }
	//
	// public String getFechaInicio() {
	// return fechaInicio;
	// }
	//
	// public void setFechaInicio(String fechaInicio) {
	// this.fechaInicio = fechaInicio;
	// }

	public List getSoportes() {
		return soportes;
	}

	public List getInteresados() {
		return this.interesados;
	}

	public void reset() {
		this.interesados = null;
		this.emplazamientos = null;
		this.documentosFisicos.clear();
		this.documentosElectronicos.clear();
		this.rangos = null;
	}

	public List getRangos() {
		return rangos;
	}

	// public String [] getRangosIni() {
	// String [] rangosIni = null;
	//
	// if (rangos != null && rangos.size() > 0) {
	// Iterator it = rangos.iterator();
	// rangosIni = new String [rangos.size()];
	// int i = 0;
	// while (it.hasNext()) {
	// RangoVO rango = (RangoVO)it.next();
	// rangosIni[i] = rango.getDesde();
	// i++;
	// }
	// }
	//
	// return rangosIni;
	// }
	//
	// public String [] getRangosFin() {
	// String [] rangosFin = null;
	//
	// if (rangos != null && rangos.size() > 0) {
	// Iterator it = rangos.iterator();
	// rangosFin = new String [rangos.size()];
	// int i = 0;
	// while (it.hasNext()) {
	// RangoVO rango = (RangoVO)it.next();
	// rangosFin[i] = rango.getHasta();
	// i++;
	// }
	// }
	//
	// return rangosFin;
	// }

	public void setRangos(List rangos) {
		this.rangos = rangos;
	}

	public void addRango(RangoVO rango) {
		if (this.rangos == null)
			this.rangos = new ArrayList();
		this.rangos.add(rango);
	}

	public void addCampoDescripcion(CampoDescripcionUdocReVO campo) {
		this.listaValoresDescripcion.put(campo.getIdCampo(),
				campo.getListaValoresCampo());
	}

	/**
	 * @return el listaValoresDescripcion
	 */
	public Map getListaValoresDescripcion() {
		return listaValoresDescripcion;
	}

	/**
	 * @param listaValoresDescripcion
	 *            el listaValoresDescripcion a establecer
	 */
	public void setListaValoresDescripcion(Map listaValoresDescripcion) {
		this.listaValoresDescripcion = listaValoresDescripcion;
	}

	public void cleanProductor() {
		productor = null;
	}

	public void cleanFechasExtremas() {
		this.fechaInicio = null;
		this.fechaFin = null;
	}

	public boolean getTieneDescripcion() {
		return tieneDescripcion;
	}

	public void setTieneDescripcion(String tieneDescripcion) {
		this.tieneDescripcion = tieneDescripcion != null
				&& tieneDescripcion.equals(Constants.TRUE_STRING);
	}
}
