package valoracion.forms;

import ieci.core.db.DbEngine;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import valoracion.ValoracionConstants;
import valoracion.vos.CriterioFechaVO;
import valoracion.vos.EliminacionSerieVO;
import valoracion.vos.FechaVO;

import common.Constants;
import common.Messages;
import common.db.DBUtils;
import common.util.DateUtils;
import common.util.StringUtils;

import es.archigest.framework.web.action.ArchigestActionForm;

/**
 * Clase que encapsula la información del formulario de una eliminacion de serie
 */
public class EliminacionForm extends ArchigestActionForm {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String id = null;
	private String idValoracion = null;
	private String idSerie = null;
	private String titulo = null;
	private String estado = null;
	private String fechaEstado = null;
	private String motivoRechazo = null;
	private String notas = null;
	private String maxAnoVigencia = null;
	private String condicionBusqueda = null;
	private String tipoEliminacion = null;
	private String seleccionUdoc = null;
	private String fechaEjecucion = null;
	private String fechaAprobacion = null;
	private String fechaDestruccion = null;
	private String fechaFinalizacion = null;

	// Fechas para la adiccion de criterios
	private String fechaMIni = null;
	private String fechaDIni = null;
	private String fechaMFin = null;
	private String fechaDFin = null;

	private String idArchivo = null;

	String[] selectedUdoc = null;

	public String getCondicionBusqueda() {
		return condicionBusqueda;
	}

	public void setCondicionBusqueda(String condicionBusqueda) {
		this.condicionBusqueda = condicionBusqueda;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getFechaAprobacion() {
		return fechaAprobacion;
	}

	public void setFechaAprobacion(String fechaAprobacion) {
		this.fechaAprobacion = fechaAprobacion;
	}

	public String getFechaDestruccion() {
		return fechaDestruccion;
	}

	public void setFechaDestruccion(String fechaDestruccion) {
		this.fechaDestruccion = fechaDestruccion;
	}

	public String getFechaEjecucion() {
		return fechaEjecucion;
	}

	public void setFechaEjecucion(String fechaEjecucion) {
		this.fechaEjecucion = fechaEjecucion;
	}

	public String getFechaEstado() {
		return fechaEstado;
	}

	public void setFechaEstado(String fechaEstado) {
		this.fechaEstado = fechaEstado;
	}

	public String getFechaFinalizacion() {
		return fechaFinalizacion;
	}

	public void setFechaFinalizacion(String fechaFinalizacion) {
		this.fechaFinalizacion = fechaFinalizacion;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdSerie() {
		return idSerie;
	}

	public void setIdSerie(String idSerie) {
		this.idSerie = idSerie;
	}

	public String getIdValoracion() {
		return idValoracion;
	}

	public void setIdValoracion(String idValoracion) {
		this.idValoracion = idValoracion;
	}

	public String getMaxAnoVigencia() {
		return maxAnoVigencia;
	}

	public void setMaxAnoVigencia(String maxAnoVigencia) {
		this.maxAnoVigencia = maxAnoVigencia;
	}

	public String getMotivoRechazo() {
		return motivoRechazo;
	}

	public void setMotivoRechazo(String motivoRechazo) {
		this.motivoRechazo = motivoRechazo;
	}

	public String getNotas() {
		return notas;
	}

	public void setNotas(String notas) {
		this.notas = notas;
	}

	public String getSeleccionUdoc() {
		return seleccionUdoc;
	}

	public void setSeleccionUdoc(String seleccionUDOC) {
		this.seleccionUdoc = seleccionUDOC;
	}

	public String getTipoEliminacion() {
		return tipoEliminacion;
	}

	public void setTipoEliminacion(String tipoEliminacion) {
		this.tipoEliminacion = tipoEliminacion;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getFechaDFin() {
		return fechaDFin;
	}

	public void setFechaDFin(String fechaDFin) {
		this.fechaDFin = fechaDFin;
	}

	public String getFechaDIni() {
		return fechaDIni;
	}

	public void setFechaDIni(String fechaDIni) {
		this.fechaDIni = fechaDIni;
	}

	public String getFechaMFin() {
		return fechaMFin;
	}

	public void setFechaMFin(String fechaMFin) {
		this.fechaMFin = fechaMFin;
	}

	public String getFechaMIni() {
		return fechaMIni;
	}

	public void setFechaMIni(String fechaMIni) {
		this.fechaMIni = fechaMIni;
	}

	/**
	 * Construye un VO con la información del formulario.
	 * 
	 * @param vo
	 *            Value Object.
	 */
	public void populate(EliminacionSerieVO vo) {
		try {
			BeanUtils.copyProperties(vo, this);
		} catch (Exception e) {
		}
	}

	/**
	 * Valida el formulario
	 * 
	 * @param mapping
	 *            {@link ActionMapping} con los mapeos asociado.
	 * @param request
	 *            {@link HttpServletRequest}
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();

		// Si esta en modo edicion
		if (request.getParameter("edicion") != null
				&& request.getParameter("edicion").trim().length() > 0
				&& request.getParameter("edicion").trim()
						.equalsIgnoreCase("true")) {
			if (request.getParameter("type").trim()
					.equalsIgnoreCase(ValoracionConstants.ACTUALIZACION_DATOS)) {
				if (StringUtils.isBlank(this.titulo)) {
					errors.add(
							ActionErrors.GLOBAL_MESSAGE,
							new ActionError(
									Constants.ERROR_REQUIRED,
									Messages.getString(
											ValoracionConstants.LABEL_VALORACION_ELIMINACION_TITULO,
											request.getLocale())));
				}

				if (StringUtils.isBlank(this.fechaEjecucion)) {
					errors.add(
							ActionErrors.GLOBAL_MESSAGE,
							new ActionError(
									Constants.ERROR_REQUIRED,
									Messages.getString(
											ValoracionConstants.LABEL_VALORACIONES_ELIMINACION_FECHA_EJECUCION,
											request.getLocale())));
				} else {
					if (!DateUtils.isDate(this.fechaEjecucion)) {
						errors.add(
								ActionErrors.GLOBAL_MESSAGE,
								new ActionError(
										Constants.ERROR_DATE,
										Messages.getString(
												ValoracionConstants.LABEL_VALORACIONES_ELIMINACION_FECHA_EJECUCION,
												request.getLocale())));
					} else {
						Date fechaEjecucion = DateUtils
								.getDate(this.fechaEjecucion);

						if (DBUtils.getFechaActual().compareTo(fechaEjecucion) > 0) {
							errors.add(
									ActionErrors.GLOBAL_MESSAGE,
									new ActionError(
											ValoracionConstants.ERROR_VALORACION_FECHA_EJECUCION_NO_VALIDA));
						}
					}
				}
			}
		} else {
			if (StringUtils.isBlank(this.titulo)) {
				errors.add(
						ActionErrors.GLOBAL_MESSAGE,
						new ActionError(
								Constants.ERROR_REQUIRED,
								Messages.getString(
										ValoracionConstants.LABEL_VALORACION_ELIMINACION_TITULO,
										request.getLocale())));
			}
		}

		return errors;
	}

	/**
	 * Asigna al formulatio los datos de la eliminacion
	 * 
	 * @param vo
	 *            Value Object con los datos de la eliminacion
	 */
	public void setEliminacion(EliminacionSerieVO vo) {
		try {
			BeanUtils.copyProperties(this, vo);
			this.setFechaEjecucion(DateUtils.formatDate(vo.getFechaEjecucion()));
			this.setFechaAprobacion(DateUtils.formatDate(vo
					.getFechaAprobacion()));
			this.setFechaDestruccion(DateUtils.formatDate(vo
					.getFechaDestruccion()));
			this.setFechaFinalizacion(DateUtils.formatDate(vo
					.getFechaFinalizacion()));

			/*
			 * if (DateUtils.isDate( vo.getFechaEjecucion() ))
			 * this.setFechaEjecucion(
			 * DateUtils.toString(vo.getFechaEjecucion()) ); else
			 * this.setFechaEjecucion("");
			 * 
			 * if (DateUtils.isDate( vo.getFechaAprobacion() ))
			 * this.setFechaAprobacion(
			 * DateUtils.toString(vo.getFechaAprobacion()) ); else
			 * this.setFechaAprobacion("");
			 * 
			 * if (DateUtils.isDate( vo.getFechaDestruccion() ))
			 * this.setFechaDestruccion(
			 * DateUtils.toString(vo.getFechaDestruccion()) ); else
			 * this.setFechaDestruccion("");
			 * 
			 * if (DateUtils.isDate( vo.getFechaFinalizacion() ))
			 * this.setFechaFinalizacion(
			 * DateUtils.toString(vo.getFechaFinalizacion()) ); else
			 * this.setFechaFinalizacion("");
			 */
		} catch (Exception e) {
		}
	}

	public CriterioFechaVO getCriterio(DbEngine dbEngine) {
		CriterioFechaVO criterio = new CriterioFechaVO(dbEngine);

		FechaVO fechaIni = new FechaVO();
		fechaIni.setMes(StringUtils.leftPad(fechaMIni, 2, '0'));
		fechaIni.setDia(StringUtils.leftPad(fechaDIni, 2, '0'));
		fechaIni.setOperador(ValoracionConstants.OPERATOR_MAYOR_IGUAL);

		FechaVO fechaFin = new FechaVO();
		fechaFin.setMes(StringUtils.leftPad(fechaMFin, 2, '0'));
		fechaFin.setDia(StringUtils.leftPad(fechaDFin, 2, '0'));
		fechaFin.setOperador(ValoracionConstants.OPERATOR_MENOR_IGUAL);

		criterio.setFechaInicial(fechaIni);
		criterio.setFechaFinal(fechaFin);

		return criterio;
	}

	public ActionErrors validateCriterios(HttpServletRequest request) {
		ActionErrors errores = new ActionErrors();

		if (!(GenericValidator.isBlankOrNull(fechaDIni) && GenericValidator
				.isBlankOrNull(fechaMIni))) {
			// Esto se utiliza para verificar que la fecha es correcta.
			String fechaInicio = fechaDIni + "/" + fechaMIni + "/" + "2004";

			if (!DateUtils.isDate(fechaInicio)) {
				errores.add(
						ActionErrors.GLOBAL_MESSAGE,
						new ActionError(Constants.ERROR_DATE, Messages
								.getString(Constants.ETIQUETA_FECHA_INICIO,
										request.getLocale())));
			}
		} else {
			errores.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(Constants.ERROR_REQUIRED, Messages
							.getString(Constants.ETIQUETA_FECHA_INICIO,
									request.getLocale())));
		}

		if (!(GenericValidator.isBlankOrNull(fechaDFin) && GenericValidator
				.isBlankOrNull(fechaMFin))) {
			// Esto se utiliza para verificar que la fecha es correcta.
			String fechaFin = fechaDFin + "/" + fechaMFin + "/" + "2004";

			if (!GenericValidator.isDate(fechaFin,
					common.Constants.FORMATO_FECHA, false)) {
				errores.add(
						ActionErrors.GLOBAL_MESSAGE,
						new ActionError(Constants.ERROR_DATE, Messages
								.getString(Constants.ETIQUETA_FECHA_FIN,
										request.getLocale())));
			}
		} else {
			errores.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(Constants.ERROR_REQUIRED, Messages
							.getString(Constants.ETIQUETA_FECHA_FIN,
									request.getLocale())));
		}

		return errores;
	}

	public String getIdArchivo() {
		return idArchivo;
	}

	public void setIdArchivo(String idArchivo) {
		this.idArchivo = idArchivo;
	}

	public String[] getSelectedUdoc() {
		return selectedUdoc;
	}

	public void setSelectedUdoc(String[] selectedUdoc) {
		this.selectedUdoc = selectedUdoc;
	}

}
