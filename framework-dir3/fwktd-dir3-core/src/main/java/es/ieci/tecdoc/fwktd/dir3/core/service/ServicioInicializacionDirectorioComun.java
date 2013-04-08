package es.ieci.tecdoc.fwktd.dir3.core.service;

/**
 * Servicio para inicializar la replica del DCO
 *
 */
public interface ServicioInicializacionDirectorioComun {

	/**
	 * Carga los datos iniciales del DCO. El modelo de datos tiene que estar creado
	 */
	public void inicializarDirectorioComun();

	/**
	 * Genera los scripts de inicialización del DCO para la carga de Oficinas y Organismos.
	 * Los scripts se dejaran en la ruta configurada en la propiedad externalizada <code>dco.generateScripts.dir</code>
	 * y tendrán el siguiente nombrado: script_dco_init_organismos_aaaaMMdd.sql / script_dco_init_oficinas_aaaaMMdd.sql
	 */
	public void generateScriptsInicializacionDirectorioComun();
}
