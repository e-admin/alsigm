package descripcion.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import xml.config.Busqueda;
import auditoria.ArchivoDetails;
import auditoria.logger.DataLoggingEvent;
import auditoria.logger.LoggingEvent;
import auditoria.utils.AuditoriaUtils;

import common.Constants;
import common.Messages;
import common.bi.GestionCuadroClasificacionBI;
import common.bi.GestionDescripcionBI;
import common.bi.IServiceBase;
import common.bi.ServiceRepository;
import common.definitions.ArchivoActions;
import common.definitions.ArchivoModules;
import common.definitions.ArchivoObjects;
import common.util.ArrayUtils;
import common.util.CustomDate;
import common.util.DateUtils;
import common.util.StringUtils;
import common.util.TypeConverter;

import descripcion.model.xml.card.ContenedorElementos;
import descripcion.model.xml.card.Elemento;
import descripcion.model.xml.card.ElementoEtiquetaDato;
import descripcion.model.xml.card.Ficha;
import descripcion.model.xml.card.TipoFicha;
import descripcion.model.xml.card.TiposElemento;
import descripcion.model.xml.card.Valor;
import descripcion.model.xml.definition.DefTipos;
import descripcion.vos.BusquedaGeneralAutVO;
import descripcion.vos.CampoDatoVO;
import descripcion.vos.CampoFechaVO;
import descripcion.vos.CampoNumericoVO;
import descripcion.vos.CampoReferenciaVO;
import descripcion.vos.CampoTablaVO;
import descripcion.vos.CampoTextoVO;
import descripcion.vos.DescriptorVO;
import descripcion.vos.ElementoCFVO;
import descripcion.vos.FichaVO;
import descripcion.vos.ListaDescrVO;
import descripcion.vos.TablaValidacionVO;
import descripcion.vos.TextoTablaValidacionVO;
import descripcion.vos.ValorCampoGenericoVO;
import fondos.model.ElementoCuadroClasificacion;
import fondos.vos.BusquedaElementosVO;
import fondos.vos.ElementoCuadroClasificacionVO;

/**
 * Clase para auditar la gestión de la descripción.
 */
public class AuditoriaDescripcion {

	/**
	 * Obtiene un evento de auditoría.
    *
	 * @param service
	 *            Servicio a auditar.
	 * @param action
	 *            Código de la acción a auditar.
	 * @return Evento de auditoría ({@link LoggingEvent}).
	 */
	public static LoggingEvent getLogginEvent(IServiceBase service, int action) {
		// Crear el evento
		LoggingEvent event = new LoggingEvent(
				ArchivoModules.DESCRIPCION_MODULE, action,
				service.getServiceClient(), false);

		// Añadir el evento al logger
		service.getLogger().add(event);

		return event;
	}

	/**
	 * Audita la creación de una ficha de descripción.
    *
	 * @param service
	 *            Servicio a auditar.
	 * @param tipoFicha
	 *            Tipo de ficha.
	 * @param id
	 *            Identificador del objeto descrito.
	 */
	public static void auditaCreacionFichaElementoCuadro(Locale locale,
			IServiceBase service, Ficha ficha) {
		// se va a crear una ficha

		LoggingEvent event = null;
		DataLoggingEvent data = null;

		if (ficha.getTipo() == TipoFicha.FICHA_ELEMENTO_CF) {
			event = getLogginEvent(service,
					ArchivoActions.DESCRIPCION_MODULE_CREACION_FICHA_ELEMENTO);
			data = event.getDataLoggingEvent(
					ArchivoObjects.OBJECT_DESCRIPCION_ELEMENTO, ficha.getId());
		} else {
			event = getLogginEvent(service,
					ArchivoActions.DESCRIPCION_MODULE_CREACION_FICHA_DESCRIPTOR);
			data = event
					.getDataLoggingEvent(
							ArchivoObjects.OBJECT_DESCRIPCION_DESCRIPTOR,
							ficha.getId());
		}
		auditaFicha(locale, ficha, data);

	}

	/**
	 * Audita la creación de una ficha de descriptor.
    *
	 * @param service
	 *            Servicio a auditar.
	 * @param tipoFicha
	 *            Tipo de ficha.
	 * @param id
	 *            Identificador del objeto descrito.
	 */
	public static void auditaCreacionFicha(Locale locale, IServiceBase service,
			FichaVO ficha) {

		LoggingEvent event = getLogginEvent(service,
				ArchivoActions.DESCRIPCION_MODULE_CREACION_FICHA_DESCRIPTOR);

		// Detalle de auditoría
		DataLoggingEvent data = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_DESCRIPCION_DESCRIPTOR, ficha.getId());

		data.addDetalleNoVacio(locale, ArchivoDetails.DESCRIPCION_CAMPO_NOMBRE,
				ficha.getNombre());
		data.addDetalleNoVacio(locale,
				ArchivoDetails.DESCRIPCION_CAMPO_TIPO_NORMA,
				TipoNorma.toText(ficha.getTipoNorma()));
		data.addDetalleNoVacio(locale,
				ArchivoDetails.DESCRIPCION_CAMPO_ELEMENTO_APLICADO,
				TipoNiveles.toText(ficha.getTipoNivel()));
		data.addDetalleNoVacio(locale,
				ArchivoDetails.DESCRIPCION_CAMPO_DESCRIPCION,
				ficha.getDescripcion());
		// data.addDetalle(ArchivoDetails.DESCRIPCION_CAMPO_DEFINICION,
		// ficha.getDefinicion());
	}

	/**
	 * Audita la modificación de una ficha de descripción.
    *
	 * @param service
	 *            Servicio a auditar.
	 * @param tipoFicha
	 *            Tipo de ficha.
	 * @param id
	 *            Identificador del objeto descrito.
	 */
	public static void auditaModificacionFicha(Locale locale,
			IServiceBase service, FichaVO ficha) {
		// se va a crear una ficha

		LoggingEvent event = getLogginEvent(service,
				ArchivoActions.DESCRIPCION_MODULE_EDICION_FICHA_DESCRIPTOR);

		// Detalle de auditoría
		DataLoggingEvent data = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_DESCRIPCION_DESCRIPTOR, ficha.getId());

		data.addDetalleNoVacio(locale, ArchivoDetails.DESCRIPCION_CAMPO_NOMBRE,
				ficha.getNombre());
		data.addDetalleNoVacio(locale,
				ArchivoDetails.DESCRIPCION_CAMPO_TIPO_NORMA,
				TipoNorma.toText(ficha.getTipoNorma()));
		data.addDetalleNoVacio(locale,
				ArchivoDetails.DESCRIPCION_CAMPO_ELEMENTO_APLICADO,
				TipoNiveles.toText(ficha.getTipoNivel()));
		data.addDetalleNoVacio(locale,
				ArchivoDetails.DESCRIPCION_CAMPO_DESCRIPCION,
				ficha.getDescripcion());
		// data.addDetalle(ArchivoDetails.DESCRIPCION_CAMPO_DEFINICION,
		// ficha.getDefinicion());
	}

	/**
	 * Audita la modificación de reemplazo en campo de elemento.
    *
	 * @param service
	 *            Servicio a auditar.
	 * @param busqueda
	 * @param tipoFicha
	 *            Tipo de ficha.
	 * @param id
	 *            Identificador del objeto descrito.
	 */
	public static void auditaReemplazoCampoElemento(Locale locale,
			IServiceBase service, BusquedaElementosVO vo,
			String nombreCampoCambio, Busqueda busqueda) {
		GestionCuadroClasificacionBI cuadroBI = ServiceRepository.getInstance(
				service.getServiceSession())
				.lookupGestionCuadroClasificacionBI();

		List elementos = null;
		if (vo.getSelectedIds() != null && vo.getSelectedIds().length > 0) {
			auditaReemplazoCampoConIdsElementos(locale, service, vo,
					nombreCampoCambio);
			return;
		}

		try {
			elementos = cuadroBI.getElementosAfectados(vo, busqueda);
		} catch (Exception e) {
			elementos = new ArrayList();
		}

		auditaReemplazoCampoElemento(locale, service, nombreCampoCambio, vo,
				elementos);

	}

	private static void auditaReemplazoCampoConIdsElementos(Locale locale,
			IServiceBase service, BusquedaElementosVO vo,
			String nombreCampoCambio) {
		// obtener los titulos y codigos de referencia de los elementos
		// actualizados
		GestionCuadroClasificacionBI cuadroBI = ServiceRepository.getInstance(
				service.getServiceSession())
				.lookupGestionCuadroClasificacionBI();

		List elementos = cuadroBI.getElementos(vo.getSelectedIds());
		auditaReemplazoCampoElemento(locale, service, nombreCampoCambio, vo,
				elementos);
	}

	public static void auditaReemplazoCampoElemento(Locale locale,
			IServiceBase service, String nombreCampoCambio,
			BusquedaElementosVO vo, List elementos) {
		int tipoCampo = vo.getTipoCampoCambio().intValue();
		if (tipoCampo == DefTipos.TIPO_DATO_FECHA) {
			String valor = vo.getValor3()[0];
			String formato = vo.getValor3()[1];
			auditaReemplazoCampoFechaElemento(locale, service,
					nombreCampoCambio, elementos, valor, formato);
			return;
		}
		auditaReemplazoCampoNoFechaElemento(locale, service, nombreCampoCambio,
				elementos, vo.getValor3()[0]);
	}

	private static void auditaReemplazoCampoFechaElemento(Locale locale,
			IServiceBase service, String nombreCampoCambio, List elementos,
			String valor, String formato) {
		// crear la ficha
		// añadir informacion del reemplazo

		// añadir detalle para cada elemento modificado

		LoggingEvent event = getLogginEvent(service,
				ArchivoActions.DESCRIPCION_MODULE_REEMPLAZO_CAMPO_ELEMENTO);

		// Detalle de auditoría
		DataLoggingEvent data = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_ELEMENTO_CUADRO, "");

		data.addDetalleNoVacio(locale,
				ArchivoDetails.DESCRIPCION_REEMPLAZO_CAMPO_NOMBRE,
				nombreCampoCambio);
		data.addDetalleNoVacio(locale,
				ArchivoDetails.DESCRIPCION_REEMPLAZO_NUEVO_VALOR, valor);
		data.addDetalleNoVacio(locale,
				ArchivoDetails.DESCRIPCION_CAMPO_FORMATO, formato);

		String titulo = null;
		String codRef = null;
		for (Iterator it = elementos.iterator(); it.hasNext();) {
			data = event.getDataLoggingEvent(
					ArchivoObjects.OBJECT_ELEMENTO_CUADRO, "");
			Object elemento = (Object) it.next();
			if (elemento instanceof ElementoCFVO) {
				titulo = ((ElementoCFVO) elemento).getTitulo();
				codRef = ((ElementoCFVO) elemento).getCodReferencia();
			} else {
				titulo = ((ElementoCuadroClasificacion) elemento).getTitulo();
				codRef = ((ElementoCuadroClasificacion) elemento)
						.getCodReferencia();
			}

			data.addDetalleNoVacio(locale,
					ArchivoDetails.DESCRIPCION_BUSQUEDA_TITULO, titulo);
			data.addDetalleNoVacio(locale,
					ArchivoDetails.DESCRIPCION_BUSQUEDA_CODIGO_REFERENCIA,
					codRef);
		}
	}

	private static void auditaReemplazoCampoNoFechaElemento(Locale locale,
			IServiceBase service, String nombreCampoCambio, List elementos,
			String valor) {
		// crear la ficha
		// añadir informacion del reemplazo

		// añadir detalle para cada elemento modificado

		LoggingEvent event = getLogginEvent(service,
				ArchivoActions.DESCRIPCION_MODULE_REEMPLAZO_CAMPO_ELEMENTO);

		// Detalle de auditoría
		DataLoggingEvent data = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_ELEMENTO_CUADRO, "");

		data.addDetalleNoVacio(locale,
				ArchivoDetails.DESCRIPCION_REEMPLAZO_CAMPO_NOMBRE,
				nombreCampoCambio);
		data.addDetalleNoVacio(locale,
				ArchivoDetails.DESCRIPCION_REEMPLAZO_NUEVO_VALOR, valor);

		for (Iterator it = elementos.iterator(); it.hasNext();) {
			data = event.getDataLoggingEvent(
					ArchivoObjects.OBJECT_ELEMENTO_CUADRO, "");
			Object elemento = it.next();

			String titulo = "";
			String codigoReferencia = "";

			if (elemento instanceof ElementoCFVO) {
				ElementoCFVO elementoCFVO = (ElementoCFVO) elemento;
				titulo = elementoCFVO.getTitulo();
				codigoReferencia = elementoCFVO.getCodReferencia();
			} else if (elemento instanceof ElementoCuadroClasificacion) {
				ElementoCuadroClasificacion elementoCuadroClasificacion = (ElementoCuadroClasificacion) elemento;
				titulo = elementoCuadroClasificacion.getTitulo();
				codigoReferencia = elementoCuadroClasificacion.getCodigo();
			}

			data.addDetalleNoVacio(locale,
					ArchivoDetails.DESCRIPCION_BUSQUEDA_TITULO, titulo);
			data.addDetalleNoVacio(locale,
					ArchivoDetails.DESCRIPCION_BUSQUEDA_CODIGO_REFERENCIA,
					codigoReferencia);
		}
	}

	public static void auditaModificacionFichaElementoCuadro(Locale locale,
			IServiceBase service, Ficha ficha) {
		LoggingEvent event = null;
		DataLoggingEvent data = null;
		if (ficha.getTipo() == TipoFicha.FICHA_ELEMENTO_CF) {
			event = getLogginEvent(service,
					ArchivoActions.DESCRIPCION_MODULE_EDICION_FICHA_ELEMENTO);
			data = event.getDataLoggingEvent(
					ArchivoObjects.OBJECT_DESCRIPCION_ELEMENTO, ficha.getId());
		} else /* if (tipoFicha == TipoFicha.FICHA_DESCRIPTOR) */{
			event = getLogginEvent(service,
					ArchivoActions.DESCRIPCION_MODULE_EDICION_FICHA_DESCRIPTOR);
			data = event
					.getDataLoggingEvent(
							ArchivoObjects.OBJECT_DESCRIPCION_DESCRIPTOR,
							ficha.getId());
		}
		auditaFicha(locale, ficha, data);
	}

	/**
	 * Audita la consulta de una ficha de descripción.
    *
	 * @param service
	 *            Servicio a auditar.
	 * @param tipoFicha
	 *            Tipo de ficha.
	 * @param id
	 *            Identificador del objeto descrito.
	 */
	public static void auditaVerDescripcion(IServiceBase service,
			int tipoFicha, String id) {
		LoggingEvent event = null;
		// DataLoggingEvent data = null;
		if (tipoFicha == TipoFicha.FICHA_ELEMENTO_CF) {
			event = getLogginEvent(service,
					ArchivoActions.DESCRIPCION_MODULE_CONSULTA_FICHA_ELEMENTO);
			/* data = */event.getDataLoggingEvent(
					ArchivoObjects.OBJECT_DESCRIPCION_ELEMENTO, id);
		} else /* if (tipoFicha == TipoFicha.FICHA_DESCRIPTOR) */{
			event = getLogginEvent(service,
					ArchivoActions.DESCRIPCION_MODULE_CONSULTA_FICHA_DESCRIPTOR);
			/* data= */event.getDataLoggingEvent(
					ArchivoObjects.OBJECT_DESCRIPCION_DESCRIPTOR, id);
		}
	}

	/**
	 * Audita la creación de una ficha de descripción.
    *
	 * @param service
	 *            Servicio a auditar.
	 * @param tipoFicha
	 *            Tipo de ficha.
	 * @param id
	 *            Identificador del objeto descrito.
	 */
	private static void auditaCampo(Locale locale, LoggingEvent event,
			IServiceBase service, Object campo) {
		boolean campoTabla = false;
		CampoTablaVO campoTablaPadre = null;
		if (campo instanceof CampoDatoVO) {
			campoTabla = false;
			CampoDatoVO campoDato = (CampoDatoVO) campo;
			if (StringUtils.isNotEmpty(campoDato.getIdTblPadre())) {
				campoTablaPadre = ((GestionDescripcionBI) service)
						.getCampoTabla(campoDato.getIdTblPadre());
			}
		} else if (campo instanceof CampoTablaVO) {
			campoTabla = true;
		} else
			return;

		// Detalle de auditoría
		DataLoggingEvent data = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_DESCRIPCION_ELEMENTO,
				campoTabla ? ((CampoTablaVO) campo).getId()
						: ((CampoDatoVO) campo).getId());

		if (campoTablaPadre != null) {
			// puede ser que el campo dato pertenezca a una tabla
			// en ese caso sacar el ID de la tabla editada
			data.addDetalleNoVacio(locale,
					ArchivoDetails.DESCRIPCION_CAMPO_TABLA_PADRE,
					campoTablaPadre.getNombre());
		}

		data.addDetalleNoVacio(locale, ArchivoDetails.DESCRIPCION_CAMPO_NOMBRE,
				campoTabla ? ((CampoTablaVO) campo).getNombre()
						: ((CampoDatoVO) campo).getNombre());

		if (!campoTabla)
			data.addDetalleNoVacio(locale,
					ArchivoDetails.DESCRIPCION_CAMPO_TIPO,
					TipoCampo.toText(((CampoDatoVO) campo).getTipo()));

		data.addDetalleNoVacio(locale,
				ArchivoDetails.DESCRIPCION_CAMPO_TIPO_NORMA, TipoNorma
						.toText(campoTabla ? ((CampoTablaVO) campo)
								.getTipoNorma() : ((CampoDatoVO) campo)
								.getTipoNorma()));

		data.addDetalleNoVacio(locale, ArchivoDetails.DESCRIPCION_CAMPO_AREA,
				campoTabla ? ((CampoTablaVO) campo).getNombreArea()
						: ((CampoDatoVO) campo).getNombreArea());

		data.addDetalleNoVacio(locale,
				ArchivoDetails.DESCRIPCION_CAMPO_ETIQUETA,
				campoTabla ? ((CampoTablaVO) campo).getEtiquetaXml()
						: ((CampoDatoVO) campo).getEtiquetaXml());

		data.addDetalleNoVacio(locale,
				ArchivoDetails.DESCRIPCION_CAMPO_DESCRIPCION,
				campoTabla ? ((CampoTablaVO) campo).getDescripcion()
						: ((CampoDatoVO) campo).getDescripcion());
	}

	private static void auditaCampoTabla(Locale locale, LoggingEvent event,
			IServiceBase service, Object campo) {
		auditaCampo(locale, event, service, campo);
		if (campo instanceof CampoTablaVO) {
			List camposDato = ((GestionDescripcionBI) service)
					.getCamposDatoXIdTabla(((CampoTablaVO) campo).getId());

			Iterator it = camposDato.iterator();
			while (it.hasNext()) {
				auditaCampo(locale, event, service, it.next());
			}
		}
	}

	/**
	 * Audita la creación de una ficha de descripción.
    *
	 * @param service
	 *            Servicio a auditar.
	 * @param tipoFicha
	 *            Tipo de ficha.
	 * @param id
	 *            Identificador del objeto descrito.
	 */
	public static void auditaCreacionCampo(Locale locale, IServiceBase service,
			Object campo) {
		LoggingEvent event = getLogginEvent(service,
				ArchivoActions.DESCRIPCION_MODULE_CREACION_CAMPO);
		auditaCampo(locale, event, service, campo);
	}

	public static void auditaModificacionCampo(Locale locale,
			IServiceBase service, Object campo) {
		// se va a crear una ficha
		LoggingEvent event = getLogginEvent(service,
				ArchivoActions.DESCRIPCION_MODULE_MODIFICACION_CAMPO);
		auditaCampo(locale, event, service, campo);
	}

	/**
	 * Audita la unificación de Descriptores
    *
	 * @param locale
	 *            Locale del Usuario
	 * @param service
	 *            Servicio
	 * @param descriptorFinal
	 *            Descriptor Final
	 * @param listaReemplazados
	 *            Lista de {@link DescriptorVO} Descrptores Reemplazados
	 */
	public static void auditaUnificacionDescriptores(Locale locale,
			IServiceBase service, String descriptorFinal,
			String[] nombresReemplazados) {
		// se va a crear una ficha
		LoggingEvent event = getLogginEvent(service,
				ArchivoActions.DESCRIPCION_MODULE_UNIFICAR_DESCRIPTORES);

		if (!ArrayUtils.isEmpty(nombresReemplazados)) {
			DataLoggingEvent data = event.getDataLoggingEvent(
					ArchivoObjects.OBJECT_DESCRIPCION_ELEMENTO, null);

			for (int i = 0; i < nombresReemplazados.length; i++) {
				String nombre = nombresReemplazados[i];
				data.addDetalle(
						locale,
						ArchivoDetails.DESCRIPCION_UNIFICAR_DESCRIPTORES_DESCRIPTOR_ORIGEN,
						nombre, Constants.STRING_SPACE + String.valueOf(i + 1));
			}
			data.addDetalle(
					locale,
					ArchivoDetails.DESCRIPCION_UNIFICAR_DESCRIPTORES_DESCRIPTOR_DESTINO,
					descriptorFinal);
		}
	}

	public static void auditaEliminacionCampo(Locale locale,
			IServiceBase service, Object campo) {
		// se va a crear una ficha
		LoggingEvent event = getLogginEvent(service,
				ArchivoActions.DESCRIPCION_MODULE_ELIMINACION_CAMPO);
		if (campo instanceof CampoTablaVO)
			auditaCampoTabla(locale, event, service, campo);
		else
			auditaCampo(locale, event, service, campo);
	}

	public static void auditaEliminacionCampos(Locale locale,
			IServiceBase service, String[] idCampos, boolean camposTabla) {
		for (int i = 0; i < idCampos.length; i++) {
			auditaEliminacionCampo(locale, service, idCampos[i], camposTabla);
		}
	}

	public static void auditaEliminacionCampo(Locale locale,
			IServiceBase service, String idCampo, boolean camposTabla) {
		LoggingEvent event = getLogginEvent(service,
				ArchivoActions.DESCRIPCION_MODULE_ELIMINACION_CAMPO);
		Object campo = null;

		if (camposTabla) {
			campo = ((GestionDescripcionBI) service).getCampoTabla(idCampo);
			auditaCampoTabla(locale, event, service, campo);
		} else {
			campo = ((GestionDescripcionBI) service).getCampoDato(idCampo);
			auditaCampo(locale, event, service, campo);
		}

	}

	/**
	 * Audita la creación de un valor de un campo.
    *
	 * @param service
	 *            Servicio a auditar.
	 * @param tipoFicha
	 *            Tipo de ficha.
	 * @param campo
	 *            Información del valor del campo.
	 */
	public static void auditaInsercionValorCampo(Locale locale,
			IServiceBase service, int tipoFicha, ValorCampoGenericoVO campo) {
		// Evento de auditoría
		LoggingEvent event = getLogginEvent(service,
				ArchivoActions.DESCRIPCION_MODULE_CREACION_CAMPO);

		// Detalle de auditoría
		DataLoggingEvent data = event
				.getDataLoggingEvent(
						tipoFicha == TipoFicha.FICHA_ELEMENTO_CF ? ArchivoObjects.OBJECT_DESCRIPCION_ELEMENTO
								: ArchivoObjects.OBJECT_DESCRIPCION_DESCRIPTOR,
						campo.getIdObjeto());

		// Detalles de auditoría comunes
		data.addDetalle(locale, ArchivoDetails.DESCRIPCION_CAMPO_ID_ELEMENTO,
				campo.getIdObjeto());
		data.addDetalle(locale, ArchivoDetails.DESCRIPCION_CAMPO_ID,
				campo.getIdCampo());
		data.addDetalle(locale, ArchivoDetails.DESCRIPCION_CAMPO_ORDEN,
				new Integer(campo.getOrden()).toString());
		data.addDetalle(locale, ArchivoDetails.DESCRIPCION_CAMPO_TIMESTAMP,
				TypeConverter.toString(campo.getTimestamp()));
		data.addDetalle(locale, ArchivoDetails.DESCRIPCION_CAMPO_TIPO,
				new Integer(campo.getTipo()).toString());

		// Detalles de auditoría particulares
		switch (campo.getTipo()) {
		case ValorCampoGenericoVO.TIPO_FECHA:
			data.addDetalle(locale, ArchivoDetails.DESCRIPCION_CAMPO_VALOR,
					((CampoFechaVO) campo).getValor());
			data.addDetalle(
					locale,
					ArchivoDetails.DESCRIPCION_CAMPO_FECHA_INICIO,
					TypeConverter.toString(((CampoFechaVO) campo).getFechaIni()));
			data.addDetalle(
					locale,
					ArchivoDetails.DESCRIPCION_CAMPO_FECHA_FIN,
					TypeConverter.toString(((CampoFechaVO) campo).getFechaFin()));
			data.addDetalle(locale,
					ArchivoDetails.DESCRIPCION_CAMPO_CALIFICADOR,
					((CampoFechaVO) campo).getCalificador());
			data.addDetalle(locale, ArchivoDetails.DESCRIPCION_CAMPO_FORMATO,
					((CampoFechaVO) campo).getFormato());
			data.addDetalle(locale, ArchivoDetails.DESCRIPCION_CAMPO_SEPARADOR,
					((CampoFechaVO) campo).getSep());
			break;

		case ValorCampoGenericoVO.TIPO_TEXTO_CORTO:
			data.addDetalle(locale, ArchivoDetails.DESCRIPCION_CAMPO_VALOR,
					((CampoTextoVO) campo).getValor());
			break;

		case ValorCampoGenericoVO.TIPO_TEXTO_LARGO:
			data.addDetalle(locale, ArchivoDetails.DESCRIPCION_CAMPO_VALOR,
					((CampoTextoVO) campo).getValor());
			break;

		case ValorCampoGenericoVO.TIPO_NUMERICO:
			data.addDetalle(locale, ArchivoDetails.DESCRIPCION_CAMPO_VALOR,
					new Double(((CampoNumericoVO) campo).getValor()).toString());
			data.addDetalle(locale,
					ArchivoDetails.DESCRIPCION_CAMPO_TIPO_MEDIDA, ""
							+ ((CampoNumericoVO) campo).getTipoMedida());
			data.addDetalle(locale,
					ArchivoDetails.DESCRIPCION_CAMPO_UNIDAD_MEDIDA,
					((CampoNumericoVO) campo).getUnidadMedida());
			break;

		case ValorCampoGenericoVO.TIPO_REFERENCIA:
			data.addDetalle(locale,
					ArchivoDetails.DESCRIPCION_CAMPO_ID_OBJ_REF,
					((CampoReferenciaVO) campo).getIdObjRef());
			data.addDetalle(locale,
					ArchivoDetails.DESCRIPCION_CAMPO_TIPO_OBJ_REF, new Integer(
							((CampoReferenciaVO) campo).getTipoObjRef())
							.toString());
			break;
		}
	}

	/**
	 * Audita la modificacion de un valor de un campo.
    *
	 * @param service
	 *            Servicio a auditar.
	 * @param tipoFicha
	 *            Tipo de ficha.
	 * @param campo
	 *            Información del valor del campo.
	 * @param valorAnterior
	 *            Valor anterior del campo.
	 */
	public static void auditaModificacionValorCampo(Locale locale,
			IServiceBase service, int tipoFicha, ValorCampoGenericoVO campo,
			Valor valorAnterior) {
		// Evento de auditoría
		LoggingEvent event = getLogginEvent(service,
				ArchivoActions.DESCRIPCION_MODULE_MODIFICACION_CAMPO);

		// Detalle de auditoría
		DataLoggingEvent data = event
				.getDataLoggingEvent(
						tipoFicha == TipoFicha.FICHA_ELEMENTO_CF ? ArchivoObjects.OBJECT_DESCRIPCION_ELEMENTO
								: ArchivoObjects.OBJECT_DESCRIPCION_DESCRIPTOR,
						campo.getIdObjeto());

		// Detalles de auditoría comunes
		data.addDetalle(locale, ArchivoDetails.DESCRIPCION_CAMPO_ID_ELEMENTO,
				campo.getIdObjeto());
		data.addDetalle(locale, ArchivoDetails.DESCRIPCION_CAMPO_ID,
				campo.getIdCampo());
		data.addDetalle(locale, ArchivoDetails.DESCRIPCION_CAMPO_ORDEN,
				new Integer(campo.getOrden()).toString());
		data.addDetalle(locale, ArchivoDetails.DESCRIPCION_CAMPO_TIMESTAMP,
				TypeConverter.toString(campo.getTimestamp()));
		data.addDetalle(locale, ArchivoDetails.DESCRIPCION_CAMPO_TIPO,
				new Integer(campo.getTipo()).toString());

		// Detalles de auditoría particulares
		switch (campo.getTipo()) {
		case ValorCampoGenericoVO.TIPO_FECHA:
			data.addDetalle(locale, ArchivoDetails.DESCRIPCION_CAMPO_VALOR,
					((CampoFechaVO) campo).getValor());

			if (!StringUtils.equals(valorAnterior.getValor(),
					((CampoFechaVO) campo).getValor()))
				data.addDetalle(locale,
						ArchivoDetails.DESCRIPCION_CAMPO_VALOR_ANTERIOR,
						valorAnterior.getValor());
			data.addDetalle(
					locale,
					ArchivoDetails.DESCRIPCION_CAMPO_FECHA_INICIO,
					TypeConverter.toString(((CampoFechaVO) campo).getFechaIni()));
			data.addDetalle(
					locale,
					ArchivoDetails.DESCRIPCION_CAMPO_FECHA_FIN,
					TypeConverter.toString(((CampoFechaVO) campo).getFechaFin()));
			data.addDetalle(locale,
					ArchivoDetails.DESCRIPCION_CAMPO_CALIFICADOR,
					((CampoFechaVO) campo).getCalificador());

			if (!StringUtils.equals(valorAnterior.getCalificador(),
					((CampoFechaVO) campo).getCalificador()))
				data.addDetalle(locale,
						ArchivoDetails.DESCRIPCION_CAMPO_CALIFICADOR_ANTERIOR,
						valorAnterior.getCalificador());
			data.addDetalle(locale, ArchivoDetails.DESCRIPCION_CAMPO_FORMATO,
					((CampoFechaVO) campo).getFormato());

			if (!StringUtils.equals(valorAnterior.getFormato(),
					((CampoFechaVO) campo).getFormato()))
				data.addDetalle(locale,
						ArchivoDetails.DESCRIPCION_CAMPO_FORMATO_ANTERIOR,
						valorAnterior.getFormato());

			data.addDetalle(locale, ArchivoDetails.DESCRIPCION_CAMPO_SEPARADOR,
					((CampoFechaVO) campo).getSep());

			if (!StringUtils.equals(valorAnterior.getSeparador(),
					((CampoFechaVO) campo).getSep()))
				data.addDetalle(locale,
						ArchivoDetails.DESCRIPCION_CAMPO_SEPARADOR_ANTERIOR,
						valorAnterior.getSeparador());
			break;

		case ValorCampoGenericoVO.TIPO_TEXTO_CORTO:

			data.addDetalle(locale, ArchivoDetails.DESCRIPCION_CAMPO_VALOR,
					((CampoTextoVO) campo).getValor());
			if (!StringUtils.equals(valorAnterior.getValor(),
					((CampoTextoVO) campo).getValor()))
				data.addDetalle(locale,
						ArchivoDetails.DESCRIPCION_CAMPO_VALOR_ANTERIOR,
						valorAnterior.getValor());
			break;

		case ValorCampoGenericoVO.TIPO_TEXTO_LARGO:

			data.addDetalle(locale, ArchivoDetails.DESCRIPCION_CAMPO_VALOR,
					((CampoTextoVO) campo).getValor());
			if (!StringUtils.equals(valorAnterior.getValor(),
					((CampoTextoVO) campo).getValor()))
				data.addDetalle(locale,
						ArchivoDetails.DESCRIPCION_CAMPO_VALOR_ANTERIOR,
						valorAnterior.getValor());
			break;

		case ValorCampoGenericoVO.TIPO_NUMERICO:

			String strValor = new Double(((CampoNumericoVO) campo).getValor())
					.toString();

			data.addDetalle(locale, ArchivoDetails.DESCRIPCION_CAMPO_VALOR,
					strValor);
			if (!StringUtils.equals(valorAnterior.getValor(), strValor))
				data.addDetalle(locale,
						ArchivoDetails.DESCRIPCION_CAMPO_VALOR_ANTERIOR,
						valorAnterior.getValor());
			data.addDetalle(locale,
					ArchivoDetails.DESCRIPCION_CAMPO_TIPO_MEDIDA, new Integer(
							((CampoNumericoVO) campo).getTipoMedida())
							.toString());

			if (valorAnterior.getTipoMedida() != ((CampoNumericoVO) campo)
					.getTipoMedida())
				data.addDetalle(locale,
						ArchivoDetails.DESCRIPCION_CAMPO_TIPO_MEDIDA_ANTERIOR,
						new Integer(valorAnterior.getTipoMedida()).toString());

			data.addDetalle(locale,
					ArchivoDetails.DESCRIPCION_CAMPO_UNIDAD_MEDIDA,
					((CampoNumericoVO) campo).getUnidadMedida());

			if (!StringUtils.equals(valorAnterior.getUnidadMedida(),
					((CampoNumericoVO) campo).getUnidadMedida()))
				data.addDetalle(
						locale,
						ArchivoDetails.DESCRIPCION_CAMPO_UNIDAD_MEDIDA_ANTERIOR,
						valorAnterior.getUnidadMedida());
			break;

		case ValorCampoGenericoVO.TIPO_REFERENCIA:

			data.addDetalle(locale,
					ArchivoDetails.DESCRIPCION_CAMPO_ID_OBJ_REF,
					((CampoReferenciaVO) campo).getIdObjRef());

			if (!StringUtils.equals(valorAnterior.getIdRef(),
					((CampoReferenciaVO) campo).getIdObjRef()))
				data.addDetalle(locale,
						ArchivoDetails.DESCRIPCION_CAMPO_ID_OBJ_REF_ANTERIOR,
						valorAnterior.getIdRef());

			data.addDetalle(locale,
					ArchivoDetails.DESCRIPCION_CAMPO_TIPO_OBJ_REF, new Integer(
							((CampoReferenciaVO) campo).getTipoObjRef())
							.toString());

			if (valorAnterior.getTipoRef() != ((CampoReferenciaVO) campo)
					.getTipoObjRef())
				data.addDetalle(locale,
						ArchivoDetails.DESCRIPCION_CAMPO_TIPO_OBJ_REF_ANTERIOR,
						new Integer(valorAnterior.getTipoRef()).toString());
			break;
		}
	}

	/**
	 * Audita la eliminación de un valor de un campo.
    *
	 * @param service
	 *            Servicio a auditar.
	 * @param tipoFicha
	 *            Tipo de ficha.
	 * @param idObjeto
	 *            Identificador del objeto descrito.
	 * @param idCampo
	 *            Identificador del campo.
	 * @param orden
	 *            Órden del valor del campo.
	 */
	public static void auditaEliminacionValorCampo(Locale locale,
			IServiceBase service, int tipoFicha, String idObjeto,
			String idCampo, String orden) {
		// Evento de auditoría
		LoggingEvent event = getLogginEvent(service,
				ArchivoActions.DESCRIPCION_MODULE_ELIMINACION_CAMPO);

		// Detalle de auditoría
		DataLoggingEvent data = event
				.getDataLoggingEvent(
						tipoFicha == TipoFicha.FICHA_ELEMENTO_CF ? ArchivoObjects.OBJECT_DESCRIPCION_ELEMENTO
								: ArchivoObjects.OBJECT_DESCRIPCION_DESCRIPTOR,
						idObjeto);

		data.addDetalle(locale, ArchivoDetails.DESCRIPCION_CAMPO_ID_ELEMENTO,
				idObjeto);
		data.addDetalle(locale, ArchivoDetails.DESCRIPCION_CAMPO_ID, idCampo);
		data.addDetalle(locale, ArchivoDetails.DESCRIPCION_CAMPO_ORDEN, orden);
	}

	/**
	 * Audita la búsqueda de elementos en el cuadro de clasificación.
    *
	 * @param service
	 *            Servicio a auditar.
	 * @param busquedaGeneralVO
	 *            Parámetros del formulario de búsquedas.
	 */
	// busquedas rapida y avanzada de fondos
	public static void auditaBusquedaElementos(Locale locale,
			IServiceBase service, BusquedaElementosVO busquedaElementosVO) {
		// Evento de auditoría
		LoggingEvent event = getLogginEvent(service,
				ArchivoActions.DESCRIPCION_MODULE_BUSQUEDA_FONDOS);

		// Detalle de auditoría
		DataLoggingEvent data = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_DESCRIPCION_ELEMENTO, null);

		// convertir ids de ambito en descripciones.
		String ambitos = "";
		String[] idsAmbito = busquedaElementosVO.getIdObjetoAmbito();
		if (idsAmbito != null) {
			GestionCuadroClasificacionBI cuadroBI = ServiceRepository
					.getInstance(
							((GestionDescripcionBIImpl) service)
									.getServiceSession())
					.lookupGestionCuadroClasificacionBI();

			for (int i = 0; i < idsAmbito.length; i++) {
				ElementoCuadroClasificacionVO vo = cuadroBI
						.getElementoCuadroClasificacion(idsAmbito[i]);
				ambitos = ambitos
						+ ((vo == null) ? idsAmbito[i] : vo.getTitulo());
				if (i + 1 < idsAmbito.length)
					ambitos = ambitos + ", ";
			}

			// String
			// niveles=AuditoriaUtils.transformOptionsToString(busquedaElementosVO.getNiveles(),
			// options, "")

			data.addDetalleNoVacio(locale,
					ArchivoDetails.DESCRIPCION_BUSQUEDA_AMBITO, ambitos);
		}
		data.addDetalleNoVacio(locale,
				ArchivoDetails.DESCRIPCION_BUSQUEDA_NUM_EXPEDIENTE,
				busquedaElementosVO.getNumeroExpediente());
		data.addDetalleNoVacio(locale,
				ArchivoDetails.DESCRIPCION_BUSQUEDA_CODIGO_REFERENCIA,
				busquedaElementosVO.getCodigoReferencia());
		data.addDetalleNoVacio(locale,
				ArchivoDetails.DESCRIPCION_BUSQUEDA_TITULO,
				busquedaElementosVO.getTitulo());
		data.addDetalleNoVacio(locale,
				ArchivoDetails.DESCRIPCION_BUSQUEDA_TEXTO,
				busquedaElementosVO.getTexto());
		data.addDetalleNoVacio(locale,
				ArchivoDetails.DESCRIPCION_BUSQUEDA_FECHA_INICIO,
				TypeConverter.toString(busquedaElementosVO.getFechaIni()));
		data.addDetalleNoVacio(locale,
				ArchivoDetails.DESCRIPCION_BUSQUEDA_FECHA_FIN,
				TypeConverter.toString(busquedaElementosVO.getFechaFin()));
		data.addDetalleNoVacio(
				locale,
				ArchivoDetails.DESCRIPCION_BUSQUEDA_CALIFICADOR,
				Messages.getString(Constants.ETIQUETA_CALIFICADOR + "."
						+ busquedaElementosVO.getCalificador(), locale));
		data.addDetalleNoVacio(locale,
				ArchivoDetails.DESCRIPCION_BUSQUEDA_FONDO,
				busquedaElementosVO.getFondo());
		data.addDetalleNoVacio(locale,
				ArchivoDetails.DESCRIPCION_BUSQUEDA_NIVELES, null);

		if (busquedaElementosVO.getDescriptores() != null) {
			data.addDetalleNoVacio(locale,
					ArchivoDetails.DESCRIPCION_BUSQUEDA_DESCRIPTORES,
					StringUtils.join(busquedaElementosVO.getDescriptores(),
							", "));
		}

		// private String tipoBusqueda = null;
		// private String codigo = null;
		// private String[] estados = new String[0];
		// private String idFicha = null;
		//
		// private String [] booleano = new String[0];
		// private String [] abrirpar = new String[0];
		// private int [] campo = new int[0];
		// private int [] tipoCampo = new int[0];
		// private String [] operador = new String[0];
		// private String [] cerrarpar = new String[0];
		//
		// private String [] valor1 = new String[0];
		// private String [] valor1A = new String[0];
		// private String [] valor1D = new String[0];
		// private String [] valor1M = new String[0];
		//
		// private String [] valor2 = new String[0];
		// private String [] valor2A = new String[0];
		// private String [] valor2D = new String[0];
		// private String [] valor2M = new String[0];
		//
		// private String numeroComp = null;
		// private String numero = null;
		// private String tipoMedida = null;
		// private String unidadMedida = null;
		// private Date fechaIniIni = null;
		// private Date fechaIniFin = null;
		// private Date fechaFinIni = null;
		// private Date fechaFinFin = null;
		// private String calificadorInicial = null;
		// private String calificadorFinal = null;
		// private String [] productores = null;
		// private String procedimiento = null;
		// private String rango = null;
		// private String signatura = null;
		// private String codigoRelacion = null;
		// private String idArchivo = null;
		// private String calificadorSignatura = null;
		// private String calificadorNumeroExpediente = null;
	}

	/**
	 * Audita la búsqueda de elementos en el cuadro de clasificación.
    *
	 * @param service
	 *            Servicio a auditar.
	 * @param busquedaGeneralVO
	 *            Parámetros del formulario de búsquedas.
	 */
	// public static void auditaBusquedaElementos(IServiceBase service,
	// BusquedaGeneralVO busquedaGeneralVO)
	public static void auditaBusquedaArchivista(Locale locale,
			IServiceBase service, BusquedaElementosVO busquedaElementosVO) {
		// Evento de auditoría
		LoggingEvent event = getLogginEvent(service,
				ArchivoActions.DESCRIPCION_MODULE_BUSQUEDA_FONDOS);

		// Detalle de auditoría
		DataLoggingEvent data = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_DESCRIPCION_ELEMENTO, null);

		// convertir ids de ambito en descripciones.
		String[] idsAmbito = busquedaElementosVO.getIdObjetoAmbito();
		if (idsAmbito != null) {
			String ambitos = "";

			GestionCuadroClasificacionBI cuadroBI = ServiceRepository
					.getInstance(
							((GestionDescripcionBIImpl) service)
									.getServiceSession())
					.lookupGestionCuadroClasificacionBI();

			for (int i = 0; i < idsAmbito.length; i++) {
				ElementoCuadroClasificacionVO vo = cuadroBI
						.getElementoCuadroClasificacion(idsAmbito[i]);
				ambitos = ambitos
						+ ((vo == null) ? idsAmbito[i] : vo.getTitulo());
				if (i + 1 < idsAmbito.length)
					ambitos = ambitos + ", ";
			}

			data.addDetalleNoVacio(locale,
					ArchivoDetails.DESCRIPCION_BUSQUEDA_AMBITO, ambitos);
		}
		data.addDetalleNoVacio(locale,
				ArchivoDetails.DESCRIPCION_BUSQUEDA_NUM_EXPEDIENTE,
				busquedaElementosVO.getNumeroExpediente());
		data.addDetalleNoVacio(locale,
				ArchivoDetails.DESCRIPCION_BUSQUEDA_TITULO,
				busquedaElementosVO.getTitulo());
		data.addDetalleNoVacio(locale,
				ArchivoDetails.DESCRIPCION_BUSQUEDA_TEXTO,
				busquedaElementosVO.getTexto());
		data.addDetalleNoVacio(locale,
				ArchivoDetails.DESCRIPCION_BUSQUEDA_FECHA_INICIO,
				DateUtils.formatDate(busquedaElementosVO.getFechaIni()));
		data.addDetalleNoVacio(locale,
				ArchivoDetails.DESCRIPCION_BUSQUEDA_FECHA_FIN,
				DateUtils.formatDate(busquedaElementosVO.getFechaFin()));
		if (StringUtils.isNotEmpty(busquedaElementosVO.getCalificador())) {
			data.addDetalleNoVacio(
					locale,
					ArchivoDetails.DESCRIPCION_BUSQUEDA_CALIFICADOR,
					Messages.getString(Constants.ETIQUETA_CALIFICADOR + "."
							+ busquedaElementosVO.getCalificador(), locale));
		}

		// posiblemente pendientes de auditar:
		// String tipoBusqueda = null;
		// String fondo = null;
		// String codigo = null;
		// String[] niveles = new String[0];
		// String[] estados = new String[0];
		// String idFicha = null;

		// String [] booleano = new String[0];
		// String [] abrirpar = new String[0];
		// int [] campo = new int[0];
		// int [] tipoCampo = new int[0];
		// String [] operador = new String[0];
		// String [] cerrarpar = new String[0];

		// String [] valor1 = new String[0];
		// String [] valor1A = new String[0];
		// String [] valor1D = new String[0];
		// String [] valor1M = new String[0];

		// String [] valor2 = new String[0];
		// String [] valor2A = new String[0];
		// String [] valor2D = new String[0];
		// String [] valor2M = new String[0];

		// String tipoOrdenacion=null;
		// int orderColumn=1;

		// String codigoReferencia = null;
		// String numeroComp = null;
		// String numero = null;
		// String tipoMedida = null;
		// String unidadMedida = null;
		// Date fechaIniIni = null;
		// Date fechaIniFin = null;
		// Date fechaFinIni = null;
		// Date fechaFinFin = null;
		// String calificadorInicial = null;
		// String calificadorFinal = null;
		// String [] descriptores = null;
		// String [] productores = null;
		// String procedimiento = null;
		// String rango = null;
		// String signatura = null;
		// String codigoRelacion = null;
		// String idArchivo = null;
		// String calificadorSignatura = null;
		// String calificadorNumeroExpediente = null;

		// ArrayList camposRellenos = new ArrayList();
	}

	/**
	 * Audita la búsqueda de autoridades.
    *
	 * @param service
	 *            Servicio a auditar.
	 * @param busquedaGeneralAutVO
	 *            Parámetros del formulario de búsquedas.
	 */
	public static void auditaBusquedaAutoridades(Locale locale,
			IServiceBase service, BusquedaGeneralAutVO busquedaGeneralAutVO) {
		// Evento de auditoría
		LoggingEvent event = getLogginEvent(service,
				ArchivoActions.DESCRIPCION_MODULE_BUSQUEDA_AUTORIDADES);

		// Detalle de auditoría
		DataLoggingEvent data = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_DESCRIPCION_DESCRIPTOR, null);

		String listasDescriptoras = AuditoriaUtils
				.transformOptionsToString(
						busquedaGeneralAutVO.getListasDescriptoras(),
						((GestionDescripcionBI) service)
								.getListasDescrByTipoDescriptor(TipoDescriptor.ENTIDAD),
						"nombre");

		data.addDetalleNoVacio(locale,
				ArchivoDetails.DESCRIPCION_BUSQUEDA_NOMBRE,
				busquedaGeneralAutVO.getNombre());
		data.addDetalleNoVacio(locale,
				ArchivoDetails.DESCRIPCION_BUSQUEDA_TEXTO,
				busquedaGeneralAutVO.getTexto());

		if (StringUtils.isNotEmpty(busquedaGeneralAutVO.getDescripcion())) {
			HashMap descripciones = new HashMap();
			descripciones.put("", Messages.getString(
					ArchivoDetails.DESCRIPCION_BUSQUEDA_DESCRIPCION_TODAS,
					locale));
			descripciones
					.put("S",
							Messages.getString(
									ArchivoDetails.DESCRIPCION_BUSQUEDA_DESCRIPCION_CON,
									locale));
			descripciones
					.put("N",
							Messages.getString(
									ArchivoDetails.DESCRIPCION_BUSQUEDA_DESCRIPCION_SIN,
									locale));

			data.addDetalleNoVacio(locale,
					ArchivoDetails.DESCRIPCION_BUSQUEDA_DESCRIPCION,
					descripciones.get(busquedaGeneralAutVO.getDescripcion())
							.toString());
		}
		data.addDetalleNoVacio(locale,
				ArchivoDetails.DESCRIPCION_BUSQUEDA_FECHA_INICIO,
				DateUtils.formatDate(busquedaGeneralAutVO.getFechaIni()));
		data.addDetalleNoVacio(locale,
				ArchivoDetails.DESCRIPCION_BUSQUEDA_FECHA_FIN,
				DateUtils.formatDate(busquedaGeneralAutVO.getFechaFin()));
		data.addDetalleNoVacio(locale,
				ArchivoDetails.DESCRIPCION_BUSQUEDA_CALIFICADOR,
				busquedaGeneralAutVO.getCalificador());

		String datoNumerico = getStringElementoNumericoFicha(locale,
				busquedaGeneralAutVO.getNumeroComp(),
				busquedaGeneralAutVO.getNumero(),
				busquedaGeneralAutVO.getUnidadMedida(),
				busquedaGeneralAutVO.getTipoMedida());

		data.addDetalleNoVacio(locale,
				ArchivoDetails.DESCRIPCION_BUSQUEDA_DATO_NUMERICO, datoNumerico);
		data.addDetalleNoVacio(locale,
				ArchivoDetails.DESCRIPCION_BUSQUEDA_LISTAS_DESCRIPTORAS,
				listasDescriptoras);
	}

	/**
	 * Audita la inserción de una tabla de validación.
    *
	 * @param event
	 *            Evento de auditoría.
	 * @param tablaValidacion
	 *            Tabla de validación.
	 */
	public static void auditaInsercionTablaValidacion(Locale locale,
			LoggingEvent event, TablaValidacionVO tablaValidacion) {
		// Detalle de la auditoría
		DataLoggingEvent data = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_DESCRIPCION_LISTA_VALORES,
				tablaValidacion.getId());

		data.addDetalle(locale,
				ArchivoDetails.DESCRIPCION_NOMBRE_LISTA_VALORES,
				tablaValidacion.getNombre());
	}

	/**
	 * Audita la modificación de una tabla de validación.
    *
	 * @param service
	 *            Servicio a auditar.
	 * @param tablaValidacion
	 *            Tabla de validación.
	 */
	public static void auditaModificacionTablaValidacion(Locale locale,
			IServiceBase service, TablaValidacionVO tablaValidacion) {
		// Evento de auditoría
		LoggingEvent event = getLogginEvent(service,
				ArchivoActions.DESCRIPCION_MODULE_MODIFICACION_LISTA_VALORES);

		// Detalle de auditoría
		DataLoggingEvent data = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_DESCRIPCION_LISTA_VALORES,
				tablaValidacion.getId());

		data.addDetalle(locale,
				ArchivoDetails.DESCRIPCION_NOMBRE_LISTA_VALORES,
				tablaValidacion.getNombre());
	}

	/**
	 * Audita la eliminación de una tabla de validación.
    *
	 * @param service
	 *            Servicio a auditar.
	 * @param id
	 *            Identificador de la tabla de validación.
	 * @param nombre
	 *            Nombre de la tabla de validación.
	 */
	public static void auditaEliminacionTablaValidacion(Locale locale,
			IServiceBase service, String id, String nombre) {
		// Evento de auditoría
		LoggingEvent event = getLogginEvent(service,
				ArchivoActions.DESCRIPCION_MODULE_BAJA_LISTA_VALORES);

		// Detalle de auditoría
		DataLoggingEvent data = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_DESCRIPCION_LISTA_VALORES, id);
		data.addDetalle(locale,
				ArchivoDetails.DESCRIPCION_NOMBRE_LISTA_VALORES, nombre);
	}

	/**
	 * Audita la inserción de un valor de una tabla de validación.
    *
	 * @param event
	 *            Evento de auditoría.
	 * @param valor
	 *            Valor de una tabla de validación.
	 */
	public static void auditaInsercionValorTablaValidacion(Locale locale,
			LoggingEvent event, TextoTablaValidacionVO valor) {
		// Detalle de la auditoría
		DataLoggingEvent data = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_DESCRIPCION_VALOR_VALIDACION,
				valor.getId());

		data.addDetalle(locale, ArchivoDetails.DESCRIPCION_VALOR_VALIDACION,
				valor.getValor());
		data.addDetalle(locale,
				ArchivoDetails.DESCRIPCION_NOMBRE_LISTA_VALORES,
				valor.getNombreTblVld());
	}

	/**
	 * Audita la modificación de un valor de una tabla de validación.
    *
	 * @param service
	 *            Servicio a auditar.
	 * @param valor
	 *            Valor de una tabla de validación.
	 */
	public static void auditaModificacionValorTablaValidacion(Locale locale,
			IServiceBase service, TextoTablaValidacionVO valor) {
		// Evento de auditoría
		LoggingEvent event = getLogginEvent(service,
				ArchivoActions.DESCRIPCION_MODULE_MODIFICACION_VALOR_VALIDACION);

		// Detalle de auditoría
		DataLoggingEvent data = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_DESCRIPCION_VALOR_VALIDACION,
				valor.getId());

		data.addDetalle(locale, ArchivoDetails.DESCRIPCION_VALOR_VALIDACION,
				valor.getValor());
		data.addDetalle(locale,
				ArchivoDetails.DESCRIPCION_NOMBRE_LISTA_VALORES,
				valor.getNombreTblVld());
	}

	/**
	 * Audita la eliminación de un valor de una tabla de validación.
    *
	 * @param service
	 *            Servicio a auditar.
	 * @param id
	 *            Identificador del valor de la tabla de validación.
	 * @param nombre
	 *            Valor de la tabla de validación.
	 * @param nombreTabla
	 *            Nombre de la tabla de validación.
	 */
	public static void auditaEliminacionValorTablaValidacion(Locale locale,
			IServiceBase service, String id, String nombre, String nombreTabla) {
		// Evento de auditoría
		LoggingEvent event = getLogginEvent(service,
				ArchivoActions.DESCRIPCION_MODULE_BAJA_VALOR_VALIDACION);

		// Detalle de auditoría
		DataLoggingEvent data = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_DESCRIPCION_VALOR_VALIDACION, id);

		data.addDetalle(locale, ArchivoDetails.DESCRIPCION_VALOR_VALIDACION,
				nombre);
		data.addDetalle(locale,
				ArchivoDetails.DESCRIPCION_NOMBRE_LISTA_VALORES, nombreTabla);
	}

	/**
	 * Audita la inserción de una lista descriptora.
    *
	 * @param event
	 *            Evento de auditoría.
	 * @param listaDescriptora
	 *            Lista descriptora.
	 */
	public static void auditaInsercionListaDescriptora(Locale locale,
			LoggingEvent event, ListaDescrVO listaDescriptora) {
		// Detalle de la auditoría
		DataLoggingEvent data = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_DESCRIPCION_LISTA_DESCRIPTORES,
				listaDescriptora.getId());

		data.addDetalle(locale,
				ArchivoDetails.DESCRIPCION_NOMBRE_LISTA_DESCRIPTORES,
				listaDescriptora.getNombre());
	}

	/**
	 * Audita la modificación de una lista descriptora.
    *
	 * @param service
	 *            Servicio a auditar.
	 * @param listaDescriptora
	 *            Lista descriptora.
	 */
	public static void auditaModificacionListaDescriptora(Locale locale,
			IServiceBase service, ListaDescrVO listaDescriptora) {
		// Evento de auditoría
		LoggingEvent event = getLogginEvent(
				service,
				ArchivoActions.DESCRIPCION_MODULE_MODIFICACION_LISTA_DESCRIPTORES);

		// Detalle de auditoría
		DataLoggingEvent data = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_DESCRIPCION_LISTA_DESCRIPTORES,
				listaDescriptora.getId());

		data.addDetalle(locale,
				ArchivoDetails.DESCRIPCION_NOMBRE_LISTA_DESCRIPTORES,
				listaDescriptora.getNombre());
	}

	/**
	 * Audita la eliminación de una lista descriptora.
    *
	 * @param service
	 *            Servicio a auditar.
	 * @param id
	 *            Identificador de la lista descriptora.
	 * @param nombre
	 *            Nombre de la lista descriptora.
	 */
	public static void auditaEliminacionListaDescriptora(Locale locale,
			IServiceBase service, String id, String nombre) {
		// Evento de auditoría
		LoggingEvent event = getLogginEvent(service,
				ArchivoActions.DESCRIPCION_MODULE_BAJA_LISTA_DESCRIPTORES);

		// Detalle de auditoría
		DataLoggingEvent data = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_DESCRIPCION_LISTA_DESCRIPTORES, id);
		data.addDetalle(locale,
				ArchivoDetails.DESCRIPCION_NOMBRE_LISTA_DESCRIPTORES, nombre);
	}

	/**
	 * Audita la inserción de un descriptor.
    *
	 * @param event
	 *            Evento de auditoría.
	 * @param descriptor
	 *            Descriptor.
	 */
	public static void auditaInsercionDescriptor(Locale locale,
			LoggingEvent event, DescriptorVO descriptor) {
		// Detalle de la auditoría
		DataLoggingEvent data = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_DESCRIPCION_DESCRIPTOR,
				descriptor.getId());

		data.addDetalle(locale, ArchivoDetails.DESCRIPCION_NOMBRE_DESCRIPTOR,
				descriptor.getNombre());
		data.addDetalle(locale,
				ArchivoDetails.DESCRIPCION_NOMBRE_LISTA_DESCRIPTORES,
				descriptor.getNombreLista());
	}

	/**
	 * Audita la modificación de un descriptor.
    *
	 * @param service
	 *            Servicio a auditar.
	 * @param descriptor
	 *            Descriptor.
	 */
	public static void auditaModificacionDescriptor(Locale locale,
			IServiceBase service, DescriptorVO descriptor) {
		// Evento de auditoría
		LoggingEvent event = getLogginEvent(service,
				ArchivoActions.DESCRIPCION_MODULE_MODIFICACION_DESCRIPTOR);

		// Detalle de auditoría
		DataLoggingEvent data = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_DESCRIPCION_DESCRIPTOR,
				descriptor.getId());

		data.addDetalle(locale, ArchivoDetails.DESCRIPCION_NOMBRE_DESCRIPTOR,
				descriptor.getNombre());
		data.addDetalle(locale,
				ArchivoDetails.DESCRIPCION_NOMBRE_LISTA_DESCRIPTORES,
				descriptor.getNombreLista());
	}

	/**
	 * Audita la eliminación de un descriptor.
    *
	 * @param service
	 *            Servicio a auditar.
	 * @param id
	 *            Identificador del descriptor.
	 * @param nombre
	 *            Nombre del descriptor.
	 * @param nombreLista
	 *            Nombre de la lista descriptora.
	 */
	public static void auditaEliminacionDescriptor(Locale locale,
			IServiceBase service, String id, String nombre, String nombreLista) {
		// Evento de auditoría
		LoggingEvent event = getLogginEvent(service,
				ArchivoActions.DESCRIPCION_MODULE_BAJA_DESCRIPTOR);

		// Detalle de auditoría
		DataLoggingEvent data = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_DESCRIPCION_DESCRIPTOR, id);

		data.addDetalle(locale, ArchivoDetails.DESCRIPCION_NOMBRE_DESCRIPTOR,
				nombre);
		data.addDetalle(locale,
				ArchivoDetails.DESCRIPCION_NOMBRE_LISTA_DESCRIPTORES,
				nombreLista);
	}

	private static void auditaFicha(Locale locale, Ficha ficha,
			DataLoggingEvent data) {
		auditaElementosFicha(locale, ficha.getAreas(), data);
	}

	private static void auditaElementosFicha(Locale locale,
			Elemento[] elementos, DataLoggingEvent data) {
		for (int i = 0; i < elementos.length; i++) {
			Elemento elem = elementos[i];
			switch (elem.getTipo()) {
			case TiposElemento.TIPO_ELEMENTO_ETIQUETA_DATO:
				auditaElementoFicha(locale, (ElementoEtiquetaDato) elem, data);
				break;

			case TiposElemento.TIPO_ELEMENTO_AREA:
			case TiposElemento.TIPO_ELEMENTO_CABECERA:
			case TiposElemento.TIPO_ELEMENTO_TABLA:
			case TiposElemento.TIPO_ELEMENTO_TABLA_TEXTUAL:
				auditaElementosFicha(locale,
						((ContenedorElementos) elem).getElementos(), data);
				break;
			}
		}
	}

	private static void auditaElementoFicha(Locale locale,
			ElementoEtiquetaDato elemento, DataLoggingEvent data) {
		Valor valor = null;
		String etiquetaCampo = elemento.getEtiqueta().getTitulo();
		if (etiquetaCampo == null)
			return;

		String strValue = null;
		String[] strValues = null;
		String fechaInicial = null;
		String fechaFinal = null;
		boolean complex = false;
		if (elemento.getTotalValores() > 1) {
			strValues = new String[elemento.getTotalValores()];
			complex = true;
		}

		int i = 0;
		for (int contValores = elemento.getTotalValores(); contValores > 0; contValores--) {
			valor = ((ElementoEtiquetaDato) elemento).getValor(contValores - 1);

			switch (elemento.getTipo()) {
			case ValorCampoGenericoVO.TIPO_TEXTO_CORTO:
			case ValorCampoGenericoVO.TIPO_TEXTO_LARGO:
			case ValorCampoGenericoVO.TIPO_REFERENCIA:
				strValue = valor.getValor();
				break;

			case ValorCampoGenericoVO.TIPO_FECHA:
				CustomDate cd = new CustomDate(valor.getFormato(),
						valor.getAnio(), valor.getMes(), valor.getDia(),
						valor.getHoras(), valor.getMinutos(), valor.getSegundos(),
						valor.getSiglo(), valor.getSeparador(),
						valor.getCalificador());
				fechaInicial = DateUtils.formatDate(cd.getMinDate());
				fechaFinal = DateUtils.formatDate(cd.getMinDate());
				break;

			case ValorCampoGenericoVO.TIPO_NUMERICO:
				String tipoMedida = "" + valor.getTipoMedida();
				String unidad = valor.getUnidadMedida();
				strValue = getStringElementoNumericoFicha(locale, null,
						valor.getValor(), unidad, tipoMedida);
				break;
			}

			if (complex) {
				strValues[i] = strValue;
				i++;
			}
		}
		if (complex) {
			strValue = ArrayUtils.join(strValues, ", ");
		}

		// auditar el campo en el detalle
		if (elemento.getTipo() == ValorCampoGenericoVO.TIPO_FECHA) { // si
			// es
			// una
			// fecha/rango
			// de
			// fechas
			data.addDetalleNoVacioSinProperty(
					Messages.getString(etiquetaCampo, locale) + " inicial",
					fechaInicial);
			data.addDetalleNoVacioSinProperty(
					Messages.getString(etiquetaCampo, locale) + " final",
					fechaFinal);
		} else {
			data.addDetalleNoVacioSinProperty(etiquetaCampo, strValue);
		}
	}

	private static String getStringElementoNumericoFicha(Locale locale,
			String comparador, String numero, String unidad, String tipoMedida) {
		String strTipoMedida = null;
		String datoNumerico = null;

		if (StringUtils.isNotEmpty(numero)) {
			datoNumerico = numero;
		}

		if (StringUtils.isNotEmpty(numero) && StringUtils.isNotEmpty(unidad)) {
			datoNumerico = datoNumerico + " " + unidad;
		}

		if (StringUtils.isNotEmpty(comparador)
				&& StringUtils.isNotEmpty(numero)
				&& StringUtils.isNotEmpty(unidad)) {
			int intTipoMedida = 0;

			if (StringUtils.isNotEmpty(tipoMedida)) {
				String[] tiposMedidas = {
						"",
						ArchivoDetails.DESCRIPCION_BUSQUEDA_DATO_NUMERICO_LONGITUD,
						ArchivoDetails.DESCRIPCION_BUSQUEDA_DATO_NUMERICO_PESO,
						ArchivoDetails.DESCRIPCION_BUSQUEDA_DATO_NUMERICO_VOLUMEN };

				try {
					intTipoMedida = Integer.parseInt(tipoMedida);
				} catch (Exception e) {
				}

				if (intTipoMedida > 0) {
					strTipoMedida = Messages.getString(
							tiposMedidas[intTipoMedida], locale);
					strTipoMedida = " (" + strTipoMedida + ")";
				}
			}
			datoNumerico = comparador + " " + datoNumerico;
			if (StringUtils.isNotEmpty(strTipoMedida)) {
				datoNumerico = datoNumerico + strTipoMedida;
			}
		}
		return datoNumerico;
	}
}
