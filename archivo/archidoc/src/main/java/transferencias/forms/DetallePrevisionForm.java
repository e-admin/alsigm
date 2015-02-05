package transferencias.forms;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.validator.GenericValidator;

import se.NotAvailableException;
import se.procedimientos.exceptions.GestorCatalogoException;
import transferencias.actions.DetallePrevisionPO;

import common.Constants;

import es.archigest.framework.web.action.ArchigestActionForm;
import fondos.vos.ElementoCuadroClasificacionVO;
import fondos.vos.SerieVO;

/**
 * ActionForm empleado en la recogida de los datos introducidos por el usuario
 * en la creacion y edicion de detalles de prevision
 * 
 */
public class DetallePrevisionForm extends ArchigestActionForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	public final static String EXPEDIENTES_POR_PROCEDIMIENTO = "0";
	public final static String EXPEDIENTES_POR_SERIE = "1";

	static final SimpleDateFormat dateFormat = new SimpleDateFormat(
			Constants.FORMATO_FECHA);

	String idDetallePrevision;
	String procedimiento;
	String nombreProcedimiento;
	String serieOrigen;
	String nombreSerieOrigen;
	String serieDestino;
	String nombreSerieDestino;
	String funcion;
	String sistemaProductor;
	String nombreSistemaProductor;

	String origenExpedientes;
	String numUInstalacion;
	String formato;
	String observaciones;
	String fechaInicio;
	String fechaFin;
	String idFondoOrigen;
	String idFondoDestino;

	String[] seleccionDetalle = null;

	public String getProcedimiento() {
		return this.procedimiento;
	}

	public void setProcedimiento(String procedimiento) {
		this.procedimiento = procedimiento;
	}

	public String getOrigenExpedientes() {
		return origenExpedientes;
	}

	public void setOrigenExpedientes(String origenProcedimientos) {
		this.origenExpedientes = origenProcedimientos;
	}

	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	public String getNumUInstalacion() {
		return numUInstalacion;
	}

	public void setNumUInstalacion(String numUInstalacion) {
		this.numUInstalacion = numUInstalacion;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getIdDetallePrevision() {
		return idDetallePrevision;
	}

	public void setIdDetallePrevision(String idDetallePrevision) {
		this.idDetallePrevision = idDetallePrevision;
	}

	public int getNumUInstalacionAsInt() {
		int numUInstalacionAsInt = 0;
		if (numUInstalacion != null && numUInstalacion.length() > 0)
			numUInstalacionAsInt = Integer.parseInt(numUInstalacion);
		return numUInstalacionAsInt;
	}

	public void setNumUInstalacionAsInt(int numUInstalacion) {
		this.numUInstalacion = String.valueOf(numUInstalacion);
	}

	public String getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaInicioAsDate() throws Exception {
		return GenericValidator.isBlankOrNull(this.fechaInicio) ? null
				: dateFormat.parse(this.fechaInicio);
	}

	public void setFechaInicioAsDate(Date fechaInicio) {
		this.fechaInicio = fechaInicio != null ? dateFormat.format(fechaInicio)
				: null;
	}

	public String getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}

	/*
	 * public Date getFechaFinAsDate() throws Exception { return
	 * GenericValidator
	 * .isBlankOrNull(this.fechaFin)?null:dateFormat.parse(fechaFin); } public
	 * void setFechaFinAsDate(Date fechaFin) { this.fechaFin = fechaFin !=
	 * null?dateFormat.format(fechaFin):null; }
	 */

	public String getSerieOrigen() {
		return serieOrigen;
	}

	public void setSerieOrigen(String serieOrigen) {
		this.serieOrigen = serieOrigen;
	}

	public String getSerieDestino() {
		return serieDestino;
	}

	public void setSerieDestino(String serieDestino) {
		this.serieDestino = serieDestino;
	}

	public String[] getSeleccionDetalle() {
		return seleccionDetalle;
	}

	public void setSeleccionDetalle(String[] seleccionDetalle) {
		this.seleccionDetalle = seleccionDetalle;
	}

	public String getNombreProcedimiento() {
		return nombreProcedimiento;
	}

	public void setNombreProcedimiento(String nombreProcedimiento) {
		this.nombreProcedimiento = nombreProcedimiento;
	}

	public String getNombreSerieOrigen() {
		return nombreSerieOrigen;
	}

	public void setNombreSerieOrigen(String nombreSerieOrigen) {
		this.nombreSerieOrigen = nombreSerieOrigen;
	}

	public String getNombreSerieDestino() {
		return nombreSerieDestino;
	}

	public void setNombreSerieDestino(String nombreSerieDestino) {
		this.nombreSerieDestino = nombreSerieDestino;
	}

	public String getNombreSistemaProductor() {
		return nombreSistemaProductor;
	}

	public void setNombreSistemaProductor(String nombreSistemaProductor) {
		this.nombreSistemaProductor = nombreSistemaProductor;
	}

	public String getSistemaProductor() {
		return sistemaProductor;
	}

	public void setSistemaProductor(String sistemaProductor) {
		this.sistemaProductor = sistemaProductor;
	}

	public String getFuncion() {
		return funcion;
	}

	public void setFuncion(String funcion) {
		this.funcion = funcion;
	}

	/**
	 * @return el idFondoDestino
	 */
	public String getIdFondoDestino() {
		return idFondoDestino;
	}

	/**
	 * @param idFondoDestino
	 *            el idFondoDestino a establecer
	 */
	public void setIdFondoDestino(String idFondoDestino) {
		this.idFondoDestino = idFondoDestino;
	}

	/**
	 * @return el idFondoOrigen
	 */
	public String getIdFondoOrigen() {
		return idFondoOrigen;
	}

	/**
	 * @param idFondoOrigen
	 *            el idFondoOrigen a establecer
	 */
	public void setIdFondoOrigen(String idFondoOrigen) {
		this.idFondoOrigen = idFondoOrigen;
	}

	public void clear() {
		idDetallePrevision = null;
		procedimiento = null;
		nombreProcedimiento = null;
		serieOrigen = null;
		nombreSerieOrigen = null;
		serieDestino = null;
		nombreSerieDestino = null;

		sistemaProductor = null;
		nombreSistemaProductor = null;

		origenExpedientes = null;
		numUInstalacion = null;
		formato = null;
		observaciones = null;
		fechaInicio = null;
		fechaFin = null;

		seleccionDetalle = null;
	}

	/**
	 * @throws GestorCatalogoException
	 * @throws NotAvailableException
	 * 
	 */

	public void populate(DetallePrevisionPO detallePrevision)
			throws GestorCatalogoException, NotAvailableException {
		if (detallePrevision.getIdProcedimiento() != null) {
			setProcedimiento(detallePrevision.getIdProcedimiento());
			setNombreProcedimiento(detallePrevision.getProcedimiento()
					.getNombre());
			setNombreSistemaProductor(detallePrevision.getProcedimiento()
					.getNombreSistProductor());
			setSistemaProductor(detallePrevision.getProcedimiento()
					.getCodSistProductor());
		}
		if (detallePrevision.getIdSerieDestino() != null) {
			SerieVO serie = detallePrevision.getSerieDestino();
			if (serie != null) {
				setSerieDestino(detallePrevision.getIdSerieDestino());
				setNombreSerieDestino(serie.getCodigoTitulo());
			}

			serie = detallePrevision.getSerieOrigen();
			if (serie != null) {
				setSerieOrigen(detallePrevision.getIdSerieOrigen());
				setNombreSerieOrigen(serie.getCodigoTitulo());
			}
			ElementoCuadroClasificacionVO funcion = detallePrevision
					.getFuncion();
			setFuncion(funcion.getCodReferenciaTitulo());
		}
		setFechaInicio(detallePrevision.getAnoIniUdoc());
		setFechaFin(detallePrevision.getAnoFinUdoc());

		setFormato(detallePrevision.getIdFormatoUI());
		setNumUInstalacionAsInt(detallePrevision.getNumUInstalacion());
		setObservaciones(detallePrevision.getObservaciones());

	}
}
