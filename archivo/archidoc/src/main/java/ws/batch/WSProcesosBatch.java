package ws.batch;

/**
 * Interfaz del servicio web de acceso a los procesos batch.
 */
public interface WSProcesosBatch {

	/**
	 * Cierra las previsiones que hayan caducado.
	 * 
	 * @throws Exception
	 *             si ocurre algún error.
	 */
	public void cerrarPrevisiones() throws Exception;

	/**
	 * Publica las unidades documentales con acceso restringido que han
	 * sobrepasado la fecha de restricción.
	 * 
	 * @throws Exception
	 *             si ocurre algún error.
	 */
	public void publicarUnidadesDocumentales() throws Exception;

	/**
	 * Elimina las unidades documentales prestadas que no han sido devueltas
	 * tras dos reclamaciones.
	 * 
	 * @throws Exception
	 *             si ocurre algún error.
	 */
	public void eliminarUnidadesDocumentalesPrestadasNoDevueltas()
			throws Exception;

	/**
	 * Comprueba la validez de los documentos vitales vigentes y los pasa a
	 * histórico si han caducado.
	 * 
	 * @throws Exception
	 *             si ocurre algún error.
	 */
	public void pasarAHistoricoDocumentosVitalesCaducados() throws Exception;

	/**
	 * Elimina los documentos vitales históricos que no están referenciados en
	 * ningún expediente.
	 * 
	 * @throws Exception
	 *             si ocurre algún error.
	 */
	public void eliminarDocumentosVitalesObsoletos() throws Exception;

}