package es.ieci.tecdoc.fwktd.dir3.core.service;

/**
 * 
 * Servicio para actualizar el catalogo de datos del DCO
 *
 */
public interface ServicioActualizacionDirectorioComun {

	/**
	 * Realiza la actualización directamente en BBDD.
	 */
	public void actualizarDirectorioComun();

	/**
	 * Genera los scripts de actualizacion del DCO para Oficinas y Organismos.
	 * Los scripts se dejaran en la ruta configurada en la propiedad externalizada <code>dco.generateScripts.dir</code>
	 * y tendrán el siguiente nombrado: script_dco_update_organismos_aaaaMMdd.sql / script_dco_update_oficinas_aaaaMMdd.sql
	 */
	public void generateScriptsActualizacionDirectorioComun();

}
