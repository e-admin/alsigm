package es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager;


public interface IntercambioRegistralActualizadorEstadosManager {

	/**
	 * Método que comprueba y actualiza los  estados de todos los intermcabios que haya en estado ENVIADO
	 * 
	 */
	public void actualizarEstadoEnviados();
	
}
