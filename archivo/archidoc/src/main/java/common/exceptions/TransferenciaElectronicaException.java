package common.exceptions;

import org.w3c.dom.Element;

import common.Constants;

import se.NotAvailableException;
import se.instituciones.exceptions.GestorOrganismosException;
import transferencias.TransferenciasElectronicasConstants;


public class TransferenciaElectronicaException extends CheckedArchivoException{

	private String codigo;

	private Element[] detalles;
	private String mensaje;
	private Exception exception = null;

	private Object[] objDetalles;

	private static final long serialVersionUID = 1L;


	// Definición de excepciones
	public TransferenciaElectronicaException(String codigo) {
		this.setCodigo(codigo);
	}

	public TransferenciaElectronicaException(String codigo, String mensaje) {
		this.setCodigo(codigo);
		this.setMensaje(mensaje);
	}

	public TransferenciaElectronicaException(String codigo, Object[] detalles) {
		this.setCodigo(codigo);
		this.setObjDetalles(detalles);

		StringBuilder str = new StringBuilder();
		for(int i=0;i<detalles.length;i++){
			str.append(detalles[0].toString())
			.append(Constants.NEWLINE);

		}

		this.setMensaje(str.toString());
	}

	public TransferenciaElectronicaException(String codigo, String mensaje, Exception e) {
		this.setCodigo(codigo);
		this.setMensaje(mensaje);
		this.setException(e);
	}


	public TransferenciaElectronicaException(String codigo, Exception e) {
		this.setCodigo(codigo);
		this.setException(e);
	}

	public TransferenciaElectronicaException(Exception e, String codigo) {
		this.setException(e);
		this.setCodigo(codigo);
	}

	public TransferenciaElectronicaException(GestorOrganismosException e) {
		this.setCodigo(TransferenciasElectronicasConstants.ERROR_GESTOR_ESTRUCTURA_ORGANIZATIVA);
		setException(e);
	}

	public TransferenciaElectronicaException(NotAvailableException e) {
		this.setCodigo(TransferenciasElectronicasConstants.ERROR_GESTOR_ESTRUCTURA_ORGANIZATIVA);
		setException(e);
	}

	public TransferenciaElectronicaException(ActionNotAllowedException e, String codigoParametro) {
		this.setCodigo(TransferenciasElectronicasConstants.ERROR_SIN_PERMISOS);
		this.setMensaje(TransferenciasElectronicasConstants.getParametro(codigoParametro));
		setException(e);
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	public Exception getException() {
		return exception;
	}

	/**
	 * @param detalles el objeto detalles a fijar
	 */
	public void setDetalles(Element[] detalles) {
		this.detalles = detalles;
	}

	/**
	 * @return el objeto detalles
	 */
	public Element[] getDetalles() {
		return detalles;
	}

	/**
	 * @param objDetalles el objeto objDetalles a fijar
	 */
	public void setObjDetalles(Object[] objDetalles) {
		this.objDetalles = objDetalles;
	}

	/**
	 * @return el objeto objDetalles
	 */
	public Object[] getObjDetalles() {
		return objDetalles;
	}


}
