package es.ieci.tecdoc.fwktd.dir3.services;

import java.util.Date;


/**
 * Servicio para obtener los ficheros de actualización del DCO
 *
 */
public interface ServicioObtenerActualizacionesDCO {

	/**
	 * Obtiene del DCO el fichero XML para actualizar las oficinas
	 * @param String fechaUltimaActualizacion
	 * @return
	 */
	public String getFicheroActualizarOficinasDCO(Date fechaUltimaActualizacion);

	/**
	 * Obtiene del DCO el fichero XML para actualizar las unidades
	 * @return Fichero XML
	 */
	public String getFicheroActualizarUnidadesDCO(Date fechaUltimaActualizacion);

	/**
	 * Obtiene del DCO el fichero XML para actualizar las relaciones entre las oficinas y las unid. orgánicas
	 * @param fechaUltimaActualizacion
	 * @return Fichero XML con las relaciones
	 */
	public String getFicheroActualizarRelacionOficinasUnidadesDCO(Date fechaUltimaActualizacion);
}
