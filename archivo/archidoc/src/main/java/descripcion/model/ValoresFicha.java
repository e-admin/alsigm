package descripcion.model;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import common.Constants;
import common.bi.GestionCuadroClasificacionBI;
import common.bi.GestionDescripcionBI;
import common.bi.ServiceRepository;
import common.bi.ServiceSession;
import common.util.CustomDate;
import common.util.ListUtils;
import common.util.StringUtils;
import common.util.TypeConverter;

import descripcion.model.xml.card.TipoFicha;
import descripcion.model.xml.card.Valor;
import descripcion.model.xml.definition.DefTipos;
import descripcion.vos.CampoFechaVO;
import descripcion.vos.CampoNumericoVO;
import descripcion.vos.CampoReferenciaVO;
import descripcion.vos.CampoTextoVO;
import descripcion.vos.DescriptorVO;
import descripcion.vos.ValorCampoGenericoVO;
import descripcion.vos.ValorCampoGenericoVOBase;
import fondos.vos.ElementoCuadroClasificacionVO;
import fondos.vos.UDocEnFraccionSerieVO;

/**
 * Clase que carga los valores de la ficha de un elemento del cuadro de
 * clasificación.
 */
public class ValoresFicha {

	/** Identificador del objeto descrito. */
	private String id = null;

	/** Tipo de ficha. */
	private int tipoFicha;

	/** Lista de valores de los campos de la ficha. */
	private Map listaValores = null;

	/**
	 * @return the listaValores
	 */
	public Map getListaValores() {
		return listaValores;
	}

	/** Unidad documental en relación o en división de fracción de serie **/
	Object udoc = null;
	/** Unidad documental en la relación */
	// private transferencias.vos.UnidadDocumentalVO udoc = null;

	/** Unidad documental en división de fracción de serie **/
	// private UDocEnFraccionSerieVO udocEnFS = null;

	/** Interfaz de acceso a la lógica de descripción. */
	GestionDescripcionBI descripcionBI = null;

	/** Interfaz de acceso a la lógica del cuadro de clasificación. */
	GestionCuadroClasificacionBI cuadroBI = null;

	/** Valor nulo para guardar en los valores nulos en el xml de descripción */
	public static final String XML_DESCRIPCION_VALUE_NULL = "vnull";

	private int tipoElemento = ValorCampoGenericoVOBase.TIPO_ELEMENTO_INDEFINIDO;

	/**
	 * Constructor.
	 * 
	 * @param serviceSession
	 *            Sesión del servicio.
	 * @param id
	 *            Identificador del objeto descrito.
	 * @param tipoFicha
	 *            Tipo de ficha ({@link descripcion.model.xml.card.TipoFicha}).
	 * @param udoc
	 *            Unidad documental en la relación
	 */
	/*
	 * private ValoresFicha(ServiceSession serviceSession, String id, int
	 * tipoFicha, transferencias.vos.UnidadDocumentalVO udoc) { this.id = id;
	 * this.tipoFicha = tipoFicha; this.tipoElemento =
	 * ValorCampoGenericoVOBase.TIPO_ELEMENTO_UDOCRE; this.listaValores = new
	 * LinkedHashMap(); this.udoc = udoc; this.descripcionBI =
	 * ServiceRepository.
	 * getInstance(serviceSession).lookupGestionDescripcionBI(); this.cuadroBI =
	 * ServiceRepository
	 * .getInstance(serviceSession).lookupGestionCuadroClasificacionBI(); }
	 */

	/**
	 * Constructor.
	 * 
	 * @param serviceSession
	 *            Sesión del servicio.
	 * @param id
	 *            Identificador del objeto descrito.
	 * @param tipoFicha
	 *            Tipo de ficha ({@link descripcion.model.xml.card.TipoFicha}).
	 * @param udoc
	 *            Unidad documental en la relación
	 */
	// private ValoresFicha(ServiceSession serviceSession, String id, int
	// tipoFicha, UDocEnFraccionSerieVO udocEnDivisionFSVO)
	// private ValoresFicha(ServiceSession serviceSession, String id, int
	// tipoFicha, int tipoElemento)
	private ValoresFicha(ServiceSession serviceSession, String id,
			int tipoFicha, Object udoc) {
		this.id = id;
		this.tipoFicha = tipoFicha;

		if (udoc != null) {
			if (udoc instanceof transferencias.vos.UnidadDocumentalVO) {
				this.tipoElemento = ValorCampoGenericoVOBase.TIPO_ELEMENTO_UDOC_EN_RE;
			} else {
				if (udoc instanceof UDocEnFraccionSerieVO)
					this.tipoElemento = ValorCampoGenericoVO.TIPO_ELEMENTO_UDOC_EN_FS;
			}
		}

		this.udoc = udoc;
		this.listaValores = new LinkedHashMap();
		this.descripcionBI = ServiceRepository.getInstance(serviceSession)
				.lookupGestionDescripcionBI();
		this.cuadroBI = ServiceRepository.getInstance(serviceSession)
				.lookupGestionCuadroClasificacionBI();
	}

	/**
	 * Devuelve una instancia de la clase.
	 * 
	 * @param serviceSession
	 *            Sesión del servicio.
	 * @param id
	 *            Identificador del objeto descrito.
	 * @param tipoFicha
	 *            Tipo de ficha ({@link descripcion.model.xml.card.TipoFicha}).
	 * @param initialize
	 *            Si hay que inicializar los valores de la ficha.
	 * @param udoc
	 *            Unidad documental en la relación
	 * @return Instancia de la clase.
	 */
	// public static ValoresFicha getInstance(ServiceSession serviceSession,
	// String id, int tipoFicha, boolean initialize, UDocEnFraccionSerieVO
	// udocEnDivisionFSVO)
	public static ValoresFicha getInstance(ServiceSession serviceSession,
			String id, int tipoFicha, int tipoElemento, boolean initialize,
			Object udoc) {
		ValoresFicha ficha = new ValoresFicha(serviceSession, id, tipoFicha,
				udoc);
		if (initialize)
			ficha.initialize();

		return ficha;
	}

	/**
	 * Devuelve una instancia de la clase.
	 * 
	 * @param serviceSession
	 *            Sesión del servicio.
	 * @param id
	 *            Identificador del objeto descrito.
	 * @param tipoFicha
	 *            Tipo de ficha ({@link descripcion.model.xml.card.TipoFicha}).
	 * @param udoc
	 *            Unidad documental a tratar si el tipo de la ficha es unidad
	 *            documental en relación
	 * @return Instancia de la clase.
	 */
	// public static ValoresFicha getInstance(ServiceSession serviceSession,
	// String id, int tipoFicha, UDocEnFraccionSerieVO udocEnDivisionFSVO)
	public static ValoresFicha getInstance(ServiceSession serviceSession,
			String id, int tipoFicha, Object udoc) {
		return getInstance(serviceSession, id, tipoFicha, true, udoc);
	}

	/**
	 * Constructor.
	 * 
	 * @param serviceSession
	 *            Sesión del servicio.
	 * @param id
	 *            Identificador del objeto descrito.
	 * @param tipoFicha
	 *            Tipo de ficha ({@link descripcion.model.xml.card.TipoFicha}).
	 * @param udoc
	 *            Unidad documental en la relación
	 */
	private ValoresFicha(ServiceSession serviceSession, String id,
			int tipoFicha, int tipoElemento) {
		this.id = id;
		this.tipoFicha = tipoFicha;
		this.tipoElemento = tipoElemento;
		this.listaValores = new LinkedHashMap();
		this.descripcionBI = ServiceRepository.getInstance(serviceSession)
				.lookupGestionDescripcionBI();
		this.cuadroBI = ServiceRepository.getInstance(serviceSession)
				.lookupGestionCuadroClasificacionBI();
	}

	/**
	 * Devuelve una instancia de la clase.
	 * 
	 * @param serviceSession
	 *            Sesión del servicio.
	 * @param id
	 *            Identificador del objeto descrito.
	 * @param tipoFicha
	 *            Tipo de ficha ({@link descripcion.model.xml.card.TipoFicha}).
	 * @param udoc
	 *            Unidad documental a tratar si el tipo de la ficha es unidad
	 *            documental en relación
	 * @return Instancia de la clase.
	 */
	/*
	 * public static ValoresFicha getInstance(ServiceSession serviceSession,
	 * String id, int tipoFicha, transferencias.vos.UnidadDocumentalVO udoc) {
	 * return getInstance(serviceSession, id, tipoFicha, true, udoc); }
	 */

	/**
	 * Devuelve una instancia de la clase.
	 * 
	 * @param serviceSession
	 *            Sesión del servicio.
	 * @param id
	 *            Identificador del objeto descrito.
	 * @param tipoFicha
	 *            Tipo de ficha ({@link descripcion.model.xml.card.TipoFicha}).
	 * @param udoc
	 *            Unidad documental a tratar si el tipo de la ficha es unidad
	 *            documental en relación
	 * @return Instancia de la clase.
	 */
	public static ValoresFicha getInstance(ServiceSession serviceSession,
			String id, int tipoFicha, int tipoElemento) {
		return getInstance(serviceSession, id, tipoFicha, true, tipoElemento);
	}

	/**
	 * Devuelve una instancia de la clase.
	 * 
	 * @param serviceSession
	 *            Sesión del servicio.
	 * @param id
	 *            Identificador del objeto descrito.
	 * @param tipoFicha
	 *            Tipo de ficha ({@link descripcion.model.xml.card.TipoFicha}).
	 * @param initialize
	 *            Si hay que inicializar los valores de la ficha.
	 * @param udoc
	 *            Unidad documental en la relación
	 * @return Instancia de la clase.
	 */
	// public static ValoresFicha getInstance(ServiceSession serviceSession,
	// String id, int tipoFicha, boolean initialize,
	// transferencias.vos.UnidadDocumentalVO udoc)
	public static ValoresFicha getInstance(ServiceSession serviceSession,
			String id, int tipoFicha, boolean initialize, Object udoc) {
		ValoresFicha ficha = new ValoresFicha(serviceSession, id, tipoFicha,
				udoc);
		if (initialize)
			ficha.initialize();

		return ficha;
	}

	/**
	 * Devuelve una instancia de la clase.
	 * 
	 * @param serviceSession
	 *            Sesión del servicio.
	 * @param id
	 *            Identificador del objeto descrito.
	 * @param tipoFicha
	 *            Tipo de ficha ({@link descripcion.model.xml.card.TipoFicha}).
	 * @param initialize
	 *            Si hay que inicializar los valores de la ficha.
	 * @param udoc
	 *            Unidad documental en la relación
	 * @return Instancia de la clase.
	 */
	public static ValoresFicha getInstance(ServiceSession serviceSession,
			String id, int tipoFicha, boolean initialize, int tipoElemento) {
		ValoresFicha ficha = new ValoresFicha(serviceSession, id, tipoFicha,
				tipoElemento);
		if (initialize)
			ficha.initialize();

		return ficha;
	}

	/**
	 * Inicializa la clase, cargando los valores de la base de datos.
	 */
	private void initialize() {
		boolean descripcionGeneral = true;

		if ((tipoFicha == TipoFicha.FICHA_UDOCRE) && (udoc != null)) {
			transferencias.vos.UnidadDocumentalVO udocRE = (transferencias.vos.UnidadDocumentalVO) udoc;
			if (Constants.TRUE_STRING.equals(udocRE.getValidada())) {
				// Obtener los valores de la unidad documental ya validada
				this.listaValores = udocRE.getExtraInfo()
						.getListaValoresDescripcion();
				descripcionGeneral = false;
			}
		} else {
			if ((tipoFicha == TipoFicha.FICHA_UDOCFS) && (udoc != null)) {
				UDocEnFraccionSerieVO udocFS = (UDocEnFraccionSerieVO) udoc;
				if (Constants.TRUE_STRING.equals(udocFS.getValidada())) {
					// Obtener los valores de la unidad documental en división
					// de fracción de serie ya validada
					this.listaValores = udocFS.getXinfo()
							.getListaValoresDescripcion();
					descripcionGeneral = false;
				}
			}
		}

		// Si la descripción es la general, esto es, se lee de las tablas que no
		// son las de unidades en relación o en división de fracción de serie
		if (descripcionGeneral) {
			loadShortTextValues();
			loadLongTextValues();
			loadNumericValues();
			loadDateValues();
			loadReferenceValues();
		}
	}

	/**
	 * Obtiene el identificador del objeto descrito.
	 * 
	 * @return Identificador del objeto descrito.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Obtiene el tipo de ficha.
	 * 
	 * @return Tipo de ficha.
	 */
	public int getTipoFicha() {
		return tipoFicha;
	}

	/**
	 * Añade los valores especiales.
	 * 
	 * @param valoresEspeciales
	 *            Mapa de valores especiales.
	 */
	public void addValoresEspeciales(Map valoresEspeciales) {
		if (valoresEspeciales != null)
			this.listaValores.putAll(valoresEspeciales);
	}

	/**
	 * Carga los valores de los campos de texto corto de la ficha.
	 */
	private void loadShortTextValues() {
		addValues(descripcionBI.getValues(tipoFicha,
				ValorCampoGenericoVO.TIPO_TEXTO_CORTO, id));
	}

	/**
	 * Carga los valores de los campos de texto largo de la ficha.
	 */
	private void loadLongTextValues() {
		addValues(descripcionBI.getValues(tipoFicha,
				ValorCampoGenericoVO.TIPO_TEXTO_LARGO, id));
	}

	/**
	 * Carga los valores de los campos numéricos de la ficha.
	 */
	private void loadNumericValues() {
		addValues(descripcionBI.getValues(tipoFicha,
				ValorCampoGenericoVO.TIPO_NUMERICO, id));
	}

	/**
	 * Carga los valores de los campos de tipo fecha de la ficha.
	 */
	private void loadDateValues() {
		addValues(descripcionBI.getValues(tipoFicha,
				ValorCampoGenericoVO.TIPO_FECHA, id));
	}

	/**
	 * Carga los valores de los campos de tipo referencia de la ficha.
	 */
	private void loadReferenceValues() {
		addValues(descripcionBI.getValues(tipoFicha,
				ValorCampoGenericoVO.TIPO_REFERENCIA, id));
	}

	/**
	 * Añade la lista de valores al mapa de valores.
	 * 
	 * @param valores
	 *            Lista de valores.
	 */
	private void addValues(List valores) {
		if (valores == null)
			return;
		for (int i = 0; i < valores.size(); i++) {
			ValorCampoGenericoVO valor = (ValorCampoGenericoVO) valores.get(i);
			List listaValoresCampo = (List) listaValores
					.get(valor.getIdCampo());

			if (listaValoresCampo == null) {
				listaValoresCampo = new LinkedList();
				listaValoresCampo.add(valor);
				listaValores.put(valor.getIdCampo(), listaValoresCampo);
			} else
				listaValoresCampo.add(valor);
		}
	}

	/**
	 * Obtiene el valor de un campo.
	 * 
	 * @param idCampo
	 *            Identificador de un campo.
	 * @return Valor del campo.
	 */
	public Valor[] getValues(String idCampo) {
		Valor[] valores = null;

		if (idCampo != null) {
			if (DefTipos.CAMPO_ESPECIAL_ID_CODIGO_REFERENCIA.equals(idCampo)) {
				valores = new Valor[] { new Valor(
						1,
						TypeConverter.toString(listaValores
								.get(DefTipos.CAMPO_ESPECIAL_ID_CODIGO_REFERENCIA))) };
			} else if (DefTipos.CAMPO_ESPECIAL_ID_NUMERO_EXPEDIENTE
					.equals(idCampo)) {
				valores = new Valor[] { new Valor(
						1,
						TypeConverter.toString(listaValores
								.get(DefTipos.CAMPO_ESPECIAL_ID_NUMERO_EXPEDIENTE))) };
			} else if (DefTipos.CAMPO_ESPECIAL_ID_TITULO.equals(idCampo)) {
				valores = new Valor[] { new Valor(1,
						TypeConverter.toString(listaValores
								.get(DefTipos.CAMPO_ESPECIAL_ID_TITULO))) };
			} else if (DefTipos.CAMPO_ESPECIAL_ID_NIVEL_DESCRIPCION
					.equals(idCampo)) {
				valores = new Valor[] { new Valor(
						1,
						TypeConverter.toString(listaValores
								.get(DefTipos.CAMPO_ESPECIAL_ID_NIVEL_DESCRIPCION))) };
			} else if (DefTipos.CAMPO_ESPECIAL_ID_NOMBRE_DESCRIPTOR
					.equals(idCampo)) {
				valores = new Valor[] { new Valor(
						1,
						TypeConverter.toString(listaValores
								.get(DefTipos.CAMPO_ESPECIAL_ID_NOMBRE_DESCRIPTOR))) };
			} else {
				List listaValoresCampo = (List) listaValores.get(idCampo);

				if (listaValoresCampo != null) {
					valores = new Valor[listaValoresCampo.size()];

					for (int i = 0; i < listaValoresCampo.size(); i++) {
						ValorCampoGenericoVO valor = (ValorCampoGenericoVO) listaValoresCampo
								.get(i);

						switch (valor.getTipo()) {
						case ValorCampoGenericoVO.TIPO_TEXTO_CORTO:
						case ValorCampoGenericoVO.TIPO_TEXTO_LARGO:
							valores[i] = new Valor(valor.getOrden(),
									((CampoTextoVO) valor).getValor());
							break;

						case ValorCampoGenericoVO.TIPO_FECHA:
							CustomDate date = new CustomDate(
									((CampoFechaVO) valor).getValor(),
									((CampoFechaVO) valor).getFormato(),
									((CampoFechaVO) valor).getSep(),
									((CampoFechaVO) valor).getCalificador());
							valores[i] = new Valor(valor.getOrden(), date);
							break;

						case ValorCampoGenericoVO.TIPO_NUMERICO:
							Valor v = new Valor(valor.getOrden(),
									((CampoNumericoVO) valor)
											.getValorAsString());
							// Valor v = new Valor(valor.getOrden(),
							// TypeConverter.toString(((CampoNumericoVO)valor).getValor()));
							v.setTipoMedida(((CampoNumericoVO) valor)
									.getTipoMedida());
							v.setUnidadMedida(((CampoNumericoVO) valor)
									.getUnidadMedida());

							valores[i] = v;
							break;

						case ValorCampoGenericoVO.TIPO_REFERENCIA:
							valores[i] = new Valor(
									valor.getOrden(),
									((CampoReferenciaVO) valor).getNombre(),
									((CampoReferenciaVO) valor).getIdObjRef(),
									((CampoReferenciaVO) valor).getTipoObjRef(),
									((CampoReferenciaVO) valor)
											.getDescripcion());
							switch (((CampoReferenciaVO) valor).getTipoObjRef()) {
							case CampoReferenciaVO.REFERENCIA_A_ELEMENTO_DESCRIPTOR:
								DescriptorVO descriptorVO = descripcionBI
										.getDescriptor(((CampoReferenciaVO) valor)
												.getIdObjRef());
								if (descriptorVO != null
										&& !StringUtils.isEmpty(descriptorVO
												.getIdFichaDescr())) {
									Valor valorActual = valores[i];
									valorActual.setTieneFichaAsociada(true);
								}
								break;

							case CampoReferenciaVO.REFERENCIA_A_ELEMENTO_CF:
								ElementoCuadroClasificacionVO elemento = cuadroBI
										.getElementoCuadroClasificacion(((CampoReferenciaVO) valor)
												.getIdObjRef());
								if (elemento != null
										&& !StringUtils.isAlpha(elemento
												.getIdFichaDescr())) {
									Valor valorActual = valores[i];
									valorActual.setTieneFichaAsociada(true);
								}
								break;
							}
							break;
						}
					}
				}
			}
		}

		return valores;
	}

	/**
	 * Obtiene una representación del objeto.
	 * 
	 * @return Representación del objeto.
	 */
	public String toString() {
		StringBuffer xml = new StringBuffer();

		xml.append("<ValoresFicha>");
		xml.append(Constants.NEWLINE);

		Iterator it = listaValores.keySet().iterator();
		while (it.hasNext()) {
			String idCampo = (String) it.next();
			Valor[] valores = getValues(idCampo);

			xml.append("  <Campo id=\"");
			xml.append(idCampo);
			xml.append("\">");
			xml.append(Constants.NEWLINE);

			for (int i = 0; (valores != null) && (i < valores.length); i++) {
				xml.append("    <Valor orden=\"");
				xml.append(i + 1);
				xml.append("\">");
				xml.append(valores[i].toString());
				xml.append("</Valor>");
				xml.append(Constants.NEWLINE);
			}

			xml.append("  </Campo>");
			xml.append(Constants.NEWLINE);
		}

		xml.append("</ValoresFicha>");
		xml.append(Constants.NEWLINE);

		return xml.toString();
	}

	/**
	 * Genera el xml para añadir a la unidad documental en una relación de
	 * entrega con alta de unidades con ficha de descripción
	 * 
	 * @return Xml con los datos de descripción de la unidad documental
	 */
	public String generateXmlValoresDescripcionUdocRe() {
		return getXmlValoresDescripcionUdocRe(this.listaValores);
	}

	/**
	 * Genera el xml para añadir a la unidad documental en una relación de
	 * entrega con alta de unidades con ficha de descripción
	 * 
	 * @return Xml con los datos de descripción de la unidad documental
	 */
	public static String getXmlValoresDescripcionUdocRe(Map mapValores) {

		if ((mapValores != null) && (!mapValores.isEmpty())) {

			StringBuffer buffer = new StringBuffer(
					"<VALORES_DESCRIPCION_UNIDAD_DOCUMENTAL_TRANSFERENCIA>");

			// Obtener los campos con sus valores
			Iterator it = mapValores.entrySet().iterator();
			while (it.hasNext()) {
				Entry campo = (Entry) it.next();

				// Obtener el identificador del campo
				String idCampo = (String) campo.getKey();

				// Obtener los valores del campo
				List listaValoresCampo = (List) campo.getValue();

				// Recorrer el campo y sus valores y añadirlo al xml
				if (StringUtils.isNotEmpty(idCampo)
						&& (!ListUtils.isEmpty(listaValoresCampo))) {

					buffer.append("<CAMPO>");

					// Añadir el identificador del campo
					buffer.append("<IDCAMPO>").append(idCampo)
							.append("</IDCAMPO>");

					// Añadir el tipo del campo
					ValorCampoGenericoVO valor = (ValorCampoGenericoVO) listaValoresCampo
							.get(0);
					buffer.append("<TIPOCAMPO>").append(valor.getTipo())
							.append("</TIPOCAMPO>");

					// Añadir los valores
					buffer.append("<VALORES>");
					Iterator itValores = listaValoresCampo.iterator();
					while (itValores.hasNext()) {
						ValorCampoGenericoVO valorCampoGenericoVO = (ValorCampoGenericoVO) itValores
								.next();
						buffer.append("<VALOR>");

						switch (valorCampoGenericoVO.getTipo()) {
						case ValorCampoGenericoVO.TIPO_TEXTO_CORTO:
						case ValorCampoGenericoVO.TIPO_TEXTO_LARGO:

							CampoTextoVO campoTextoVO = (CampoTextoVO) valorCampoGenericoVO;

							// Añadir el orden
							buffer.append("<ORDEN>");
							buffer.append(campoTextoVO.getOrden());
							buffer.append("</ORDEN>");

							// Añadir el valor
							buffer.append("<VALOR>");
							buffer.append((campoTextoVO.getValor() == null) ? XML_DESCRIPCION_VALUE_NULL
									: Constants.addCData(campoTextoVO
											.getValor()));
							buffer.append("</VALOR>");

							break;

						case ValorCampoGenericoVO.TIPO_FECHA:

							CampoFechaVO campoFechaVO = (CampoFechaVO) valorCampoGenericoVO;

							// Añadir el orden
							buffer.append("<ORDEN>");
							buffer.append(campoFechaVO.getOrden());
							buffer.append("</ORDEN>");

							// Añadir el valor
							buffer.append("<VALOR>");
							buffer.append((campoFechaVO.getValor() == null) ? XML_DESCRIPCION_VALUE_NULL
									: Constants.addCData(campoFechaVO
											.getValor()));
							buffer.append("</VALOR>");

							// Añadir el calificador
							buffer.append("<CALIFICADOR>");
							buffer.append((campoFechaVO.getCalificador() == null) ? XML_DESCRIPCION_VALUE_NULL
									: Constants.addCData(campoFechaVO
											.getCalificador()));
							buffer.append("</CALIFICADOR>");

							// Añadir el formato
							buffer.append("<FORMATO>");
							buffer.append((campoFechaVO.getFormato() == null) ? XML_DESCRIPCION_VALUE_NULL
									: Constants.addCData(campoFechaVO
											.getFormato()));
							buffer.append("</FORMATO>");

							// Añadir el separador
							buffer.append("<SEPARADOR>");
							buffer.append((campoFechaVO.getSep() == null) ? XML_DESCRIPCION_VALUE_NULL
									: Constants.addCData(campoFechaVO.getSep()));
							buffer.append("</SEPARADOR>");

							break;

						case ValorCampoGenericoVO.TIPO_NUMERICO:

							CampoNumericoVO campoNumericoVO = (CampoNumericoVO) valorCampoGenericoVO;

							// Añadir el orden
							buffer.append("<ORDEN>");
							buffer.append(campoNumericoVO.getOrden());
							buffer.append("</ORDEN>");

							// Añadir el valor
							buffer.append("<VALOR>");
							buffer.append(Constants.addCData(campoNumericoVO
									.getValorAsString()));
							buffer.append("</VALOR>");

							// Añadir el tipo de medida
							buffer.append("<TIPO_MEDIDA>");
							buffer.append(campoNumericoVO.getTipoMedida());
							buffer.append("</TIPO_MEDIDA>");

							// Añadir la unidad de medida
							buffer.append("<UNIDAD_MEDIDA>");
							buffer.append((campoNumericoVO.getUnidadMedida() == null) ? XML_DESCRIPCION_VALUE_NULL
									: Constants.addCData(campoNumericoVO
											.getUnidadMedida()));
							buffer.append("</UNIDAD_MEDIDA>");

							break;

						case ValorCampoGenericoVO.TIPO_REFERENCIA:

							CampoReferenciaVO campoReferenciaVO = (CampoReferenciaVO) valorCampoGenericoVO;

							// Añadir el orden
							buffer.append("<ORDEN>");
							buffer.append(campoReferenciaVO.getOrden());
							buffer.append("</ORDEN>");

							// Añadir el tipo de la referencia
							buffer.append("<TIPO_OBJ_REF>");
							buffer.append(campoReferenciaVO.getTipoObjRef());
							buffer.append("</TIPO_OBJ_REF>");

							// Añadir el id de la referencia
							buffer.append("<ID_OBJ_REF>");
							buffer.append((campoReferenciaVO.getIdObjRef() == null) ? XML_DESCRIPCION_VALUE_NULL
									: Constants.addCData(campoReferenciaVO
											.getIdObjRef()));
							buffer.append("</ID_OBJ_REF>");

							// Añadir el nombre de la referencia
							buffer.append("<NOMBRE>");
							buffer.append((campoReferenciaVO.getNombre() == null) ? XML_DESCRIPCION_VALUE_NULL
									: Constants.addCData(campoReferenciaVO
											.getNombre()));
							buffer.append("</NOMBRE>");

							break;
						}
						buffer.append("</VALOR>");
					}
					buffer.append("</VALORES>");
					buffer.append("</CAMPO>");
				}
			}

			buffer.append("</VALORES_DESCRIPCION_UNIDAD_DOCUMENTAL_TRANSFERENCIA>");

			return buffer.toString();
		}

		return Constants.STRING_EMPTY;
	}

	public int getTipoElemento() {
		return tipoElemento;
	}

	public void setTipoElemento(int tipoElemento) {
		this.tipoElemento = tipoElemento;
	}
}
