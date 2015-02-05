package descripcion.model.automaticos;

import java.util.Date;
import java.util.List;
import java.util.Map;

import util.CollectionUtils;
import xml.config.ConfiguracionSistemaArchivoFactory;

import common.bi.GestionDescripcionBI;
import common.bi.ServiceRepository;
import common.bi.ServiceSession;

import descripcion.model.xml.card.TipoFicha;
import descripcion.vos.CampoFechaVO;
import descripcion.vos.CampoNumericoVO;
import descripcion.vos.CampoReferenciaVO;
import descripcion.vos.CampoTextoVO;
import descripcion.vos.ValorCampoGenericoVO;
import descripcion.vos.ValorCampoGenericoVOBase;
import fondos.vos.ProductorSerieVO;
import fondos.vos.SerieVO;
import fondos.vos.VolumenSerieVO;

/**
 * Clase que realiza el cálculo de los valores automáticos de una serie.
 */
public class ADReglaGenDatosSerieImpl implements IADReglaGenDatos {

	/** Sesión de base de datos. */
	protected ServiceSession session = null;

	/** Repositorio de servicios. */
	private ServiceRepository service = null;

	/** Identificador de la serie. */
	private String id = null;

	/** Si hay que actualizar el volumen y soporte. */
	private boolean isActVolSop = false;

	/** Si hay que actualizar las fechas extremas. */
	private boolean isActFechas = false;

	/** Si hay que actualizar los productores. */
	private boolean isActProds = false;

	/** Información de la serie. */
	private SerieVO cachedSerie = null;

	/** Lista de volúmenes y soportes. */
	private List listaVolumenesYSoportes = null;

	/** Fecha extrema inicial. */
	private Date fechaInicial = null;

	/** Fecha extrema final. */
	private Date fechaFinal = null;

	/** Lista de productores de la serie. */
	private List listaProductores = null;

	private static final String ID_CAMPO_CANTIDAD = ConfiguracionSistemaArchivoFactory
			.getConfiguracionSistemaArchivo().getConfiguracionDescripcion()
			.getCantidadVolumenSoporteDocumental();
	private static final String ID_CAMPO_SOPORTE_DOCUMENTAL = ConfiguracionSistemaArchivoFactory
			.getConfiguracionSistemaArchivo().getConfiguracionDescripcion()
			.getSoporteDocumental();
	private static final String ID_CAMPO_FECHA_INICIAL = ConfiguracionSistemaArchivoFactory
			.getConfiguracionSistemaArchivo().getConfiguracionDescripcion()
			.getFechaExtremaInicial();
	private static final String ID_CAMPO_FECHA_FINAL = ConfiguracionSistemaArchivoFactory
			.getConfiguracionSistemaArchivo().getConfiguracionDescripcion()
			.getFechaExtremaFinal();
	private static final String ID_CAMPO_PRODUCTOR = ConfiguracionSistemaArchivoFactory
			.getConfiguracionSistemaArchivo().getConfiguracionDescripcion()
			.getProductorSerie();
	private static final String ID_CAMPO_FECHA_INICIO_PRODUCTOR = ConfiguracionSistemaArchivoFactory
			.getConfiguracionSistemaArchivo().getConfiguracionDescripcion()
			.getFechaInicioProductorSerie();
	private static final String ID_CAMPO_FECHA_FIN_PRODUCTOR = ConfiguracionSistemaArchivoFactory
			.getConfiguracionSistemaArchivo().getConfiguracionDescripcion()
			.getFechaFinProductorSerie();

	/**
	 * Constructor.
	 */
	public ADReglaGenDatosSerieImpl() {
	}

	/**
	 * Inicializa la clase con unos parámetros.
	 * 
	 * @param parametros
	 *            Parámetros de la clase.
	 */
	public void inicializa(Map parametros) {
		if (parametros != null) {
			// Accion
			int[] acciones = (int[]) parametros
					.get(ADReglaGenDatosContants.ACCIONES);
			if ((acciones == null) || (acciones.length == 0)) {
				isActVolSop = true;
				isActFechas = true;
				isActProds = true;
			} else {
				for (int i = 0; i < acciones.length; i++) {
					switch (acciones[i]) {
					case ADReglaGenDatosContants.ACTUALIZAR_TODO:
						isActVolSop = true;
						isActFechas = true;
						isActProds = true;
						break;

					case ADReglaGenDatosContants.ACTUALIZAR_VOLUMENES_SOPORTES:
						isActVolSop = true;
						break;

					case ADReglaGenDatosContants.ACTUALIZAR_FECHAS_EXTREMAS:
						isActFechas = true;
						break;

					case ADReglaGenDatosContants.ACTUALIZAR_PRODUCTORES:
						isActProds = true;
						break;
					}
				}
			}

			// Lista de volúmenes y soportes
			if (isActVolSop)
				listaVolumenesYSoportes = (List) parametros
						.get(ADReglaGenDatosContants.LISTA_VOL_SOP);

			// Fechas extremas
			if (isActFechas) {
				fechaInicial = (Date) parametros
						.get(ADReglaGenDatosContants.FECHA_INICIAL);
				fechaFinal = (Date) parametros
						.get(ADReglaGenDatosContants.FECHA_FINAL);
			}

			// Lista de productores
			if (isActProds)
				listaProductores = (List) parametros
						.get(ADReglaGenDatosContants.LISTA_PRODUCTORES);
		} else {
			isActVolSop = true;
			isActFechas = true;
			isActProds = true;
		}
	}

	/**
	 * Genera los datos automáticos.
	 * 
	 * @param session
	 *            Sesión de base de datos.
	 * @param id
	 *            Identificador del objeto descrito.
	 * @throws ADReglaGenDatosException
	 *             si ocurre algún error.
	 */
	public void generacionDatosAutomaticos(ServiceSession session, String id)
			throws ADReglaGenDatosException {
		this.id = id;
		this.session = session;
		this.service = ServiceRepository.getInstance(session);

		if (isActVolSop)
			actualizarVolumenYSoporte();

		if (isActFechas)
			actualizarFechasExtremas();

		if (isActProds)
			actualizarProductores();
	}

	/**
	 * Actualiza el volúmen y soporte de la serie.
	 */
	protected void actualizarVolumenYSoporte() {
		if (CollectionUtils.isEmpty(listaVolumenesYSoportes))
			listaVolumenesYSoportes = service.lookupGestionSeriesBI()
					.getVolumenesYSoporteSerie(id);

		// Servicio de gestión de descripción
		GestionDescripcionBI descripcionBI = service
				.lookupGestionDescripcionBI();

		// Elimina información anterior
		eliminaInformacionAnteriorVolumen(descripcionBI);

		// Inserta volumen y soporte
		if (!CollectionUtils.isEmpty(listaVolumenesYSoportes)) {
			VolumenSerieVO volumenSerie;

			for (int i = 0; i < listaVolumenesYSoportes.size(); i++) {
				volumenSerie = (VolumenSerieVO) listaVolumenesYSoportes.get(i);

				descripcionBI
						.actualizaCampo(
								TipoFicha.FICHA_ELEMENTO_CF,
								new CampoNumericoVO(
										id,
										ID_CAMPO_CANTIDAD,
										(i + 1),
										volumenSerie.getCantidad(),
										ValorCampoGenericoVOBase.TIPO_ELEMENTO_INDEFINIDO));

				descripcionBI
						.actualizaCampo(
								TipoFicha.FICHA_ELEMENTO_CF,
								new CampoTextoVO(
										id,
										ID_CAMPO_SOPORTE_DOCUMENTAL,
										(i + 1),
										volumenSerie.getTipoDocumental(),
										ValorCampoGenericoVOBase.TIPO_ELEMENTO_INDEFINIDO));
			}
		}
	}

	/**
	 * Actualiza las fechas extremas de la serie.
	 */
	protected void actualizarFechasExtremas() {
		if (fechaInicial == null || fechaFinal == null) {
			fechaInicial = getSerie().getFextremainicial();
			fechaFinal = getSerie().getFextremafinal();
		}

		// Servicio de gestión de descripción
		GestionDescripcionBI descripcionBI = service
				.lookupGestionDescripcionBI();

		// Fecha inicial
		if (fechaInicial != null)
			descripcionBI.actualizaCampo(TipoFicha.FICHA_ELEMENTO_CF,
					new CampoFechaVO(id, ID_CAMPO_FECHA_INICIAL, 1,
							fechaInicial,
							ValorCampoGenericoVOBase.TIPO_ELEMENTO_INDEFINIDO));
		else
			descripcionBI
					.vaciaCampo(TipoFicha.FICHA_ELEMENTO_CF,
							ValorCampoGenericoVO.TIPO_FECHA, id,
							ID_CAMPO_FECHA_INICIAL);

		// Fecha final
		if (fechaFinal != null)
			descripcionBI.actualizaCampo(TipoFicha.FICHA_ELEMENTO_CF,
					new CampoFechaVO(id, ID_CAMPO_FECHA_FINAL, 1, fechaFinal,
							ValorCampoGenericoVOBase.TIPO_ELEMENTO_INDEFINIDO));
		else
			descripcionBI.vaciaCampo(TipoFicha.FICHA_ELEMENTO_CF,
					ValorCampoGenericoVO.TIPO_FECHA, id, ID_CAMPO_FECHA_FINAL);
	}

	/**
	 * Actualiza los productores de la serie.
	 */
	protected void actualizarProductores() {
		if (CollectionUtils.isEmpty(listaProductores))
			listaProductores = service.lookupGestionSeriesBI()
					.getProductoresSerie(id);

		// Servicio de gestión de descripción
		GestionDescripcionBI descripcionBI = service
				.lookupGestionDescripcionBI();

		// Elimina información anterior
		eliminaInformacionAnteriorProductores(descripcionBI);

		// Inserta los productores
		if (!CollectionUtils.isEmpty(listaProductores)) {
			ProductorSerieVO productor;

			for (int i = 0; i < listaProductores.size(); i++) {
				productor = (ProductorSerieVO) listaProductores.get(i);

				descripcionBI
						.actualizaCampo(
								TipoFicha.FICHA_ELEMENTO_CF,
								new CampoReferenciaVO(
										id,
										ID_CAMPO_PRODUCTOR,
										(i + 1),
										CampoReferenciaVO.REFERENCIA_A_ELEMENTO_DESCRIPTOR,
										productor.getIdProductor(),
										ValorCampoGenericoVOBase.TIPO_ELEMENTO_INDEFINIDO));

				if (productor.getFechaInicial() != null)
					descripcionBI
							.actualizaCampo(
									TipoFicha.FICHA_ELEMENTO_CF,
									new CampoFechaVO(
											id,
											ID_CAMPO_FECHA_INICIO_PRODUCTOR,
											(i + 1),
											productor.getFechaInicial(),
											ValorCampoGenericoVOBase.TIPO_ELEMENTO_INDEFINIDO));

				if (productor.getFechaFinal() != null)
					descripcionBI
							.actualizaCampo(
									TipoFicha.FICHA_ELEMENTO_CF,
									new CampoFechaVO(
											id,
											ID_CAMPO_FECHA_FIN_PRODUCTOR,
											(i + 1),
											productor.getFechaFinal(),
											ValorCampoGenericoVOBase.TIPO_ELEMENTO_INDEFINIDO));
			}
		}
	}

	/**
	 * Obtiene la información de la serie.
	 * 
	 * @return Serie.
	 */
	protected SerieVO getSerie() {
		if (cachedSerie == null)
			cachedSerie = service.lookupGestionSeriesBI().getSerie(id);

		return cachedSerie;
	}

	/**
	 * Elimina la información anterior del volumen de la serie.
	 * 
	 * @param descripcionBI
	 *            Servicio de gestión de la descripción.
	 */
	protected void eliminaInformacionAnteriorVolumen(
			GestionDescripcionBI descripcionBI) {
		// Cantidad
		descripcionBI.vaciaCampo(TipoFicha.FICHA_ELEMENTO_CF,
				ValorCampoGenericoVO.TIPO_NUMERICO, id, ID_CAMPO_CANTIDAD);

		// Soporte documental
		descripcionBI.vaciaCampo(TipoFicha.FICHA_ELEMENTO_CF,
				ValorCampoGenericoVO.TIPO_TEXTO_CORTO, id,
				ID_CAMPO_SOPORTE_DOCUMENTAL);
	}

	/**
	 * Elimina la información anterior de los productores.
	 * 
	 * @param descripcionBI
	 *            Servicio de gestión de la descripción.
	 */
	protected void eliminaInformacionAnteriorProductores(
			GestionDescripcionBI descripcionBI) {
		// Productor
		descripcionBI.vaciaCampo(TipoFicha.FICHA_ELEMENTO_CF,
				ValorCampoGenericoVO.TIPO_REFERENCIA, id, ID_CAMPO_PRODUCTOR);

		// Fecha inicio
		descripcionBI.vaciaCampo(TipoFicha.FICHA_ELEMENTO_CF,
				ValorCampoGenericoVO.TIPO_FECHA, id,
				ID_CAMPO_FECHA_INICIO_PRODUCTOR);

		// Fecha fin
		descripcionBI.vaciaCampo(TipoFicha.FICHA_ELEMENTO_CF,
				ValorCampoGenericoVO.TIPO_FECHA, id,
				ID_CAMPO_FECHA_FIN_PRODUCTOR);
	}
}
