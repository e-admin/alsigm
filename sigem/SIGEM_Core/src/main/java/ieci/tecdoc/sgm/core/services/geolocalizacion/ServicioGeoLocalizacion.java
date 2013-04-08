package ieci.tecdoc.sgm.core.services.geolocalizacion;

public interface ServicioGeoLocalizacion {
	
	/**
	 * Método que obtiene un plano a partir de unas coordenadas
	 * @param datosLocalizacion Datos de la localización
	 * @return Url de la imagen del plano generado
	 * @throws GeoLocalizacionServicioException En caso de producirse algún error
	 */
	public URLsPlano verPlanoPorCoordenadas(PeticionPlanoCoordenadas datosLocalizacion) throws GeoLocalizacionServicioException;

	/**
	 * Método que obtiene un plano a partir de un número de referencia de catastro
	 * @param datosLocalizacion Datos de la localización
	 * @return Url de la imagen del plano generado
	 * @throws GeoLocalizacionServicioException En caso de producirse algún error
	 */
	public URLsPlano verPlanoPorReferenciaCatastral(PeticionPlanoReferenciaCatastral datosLocalizacion) throws GeoLocalizacionServicioException;
	
	/**
	 * Método que obtiene un plano a partir de un idntificador de vía
	 * @param datosLocalizacion Datos de la localización
	 * @return Url de la imagen del plano generado
	 * @throws GeoLocalizacionServicioException En caso de producirse algún error
	 */
	public URLsPlano verPlanoPorIdVia(PeticionPlanoVia datosLocalizacion) throws GeoLocalizacionServicioException;
	
	/**
	 * Método que obtiene un plano a partir de un número de portal
	 * @param datosLocalizacion Datos de la localización
	 * @return Url de la imagen del plano generado
	 * @throws GeoLocalizacionServicioException En caso de producirse algún error
	 */
	public URLsPlano verPlanoPorIdNumeroPolicia(PeticionPlanoPortal datosLocalizacion) throws GeoLocalizacionServicioException;
	
	/**
	 * Método que obtiene los planos publicados de un municipio
	 * @param idEntidadMunicipal Identificador de la entidad de agrupación de municipios del que obtener los planos
	 * @return mapas[] Datos del los planos publicados
	 * @throws GeoLocalizacionServicioException En caso de producirse algún error
	 */
	public Mapas verPlanosPublicados(int idEntidadMunicipal) throws GeoLocalizacionServicioException;
	
	/**
	 * Método que obtiene un listado de vías coincidentes con la vía de entrada
	 * @param nombreVia Nombre de la vía a consultar
	 * @param codigoINEMunicipio Código de municipio
	 * @return Listado de vías coincidentes
	 * @throws GeoLocalizacionServicioException En caso de producirse algún error
	 */
	public Vias validarVia(String nombreVia, int codigoINEMunicipio) throws GeoLocalizacionServicioException;
	
	/**
	 * Método que obtiene los datos asociados a un portal a partir de su identificador
	 * @param idPortal Identificador del portal
	 * @return Datos del portal
	 * @throws GeoLocalizacionServicioException En caso de producirse algún error
	 */
	public Portal obtenerPortal(int idPortal) throws GeoLocalizacionServicioException;
	
	/**
	 * Método que obtiene un listado de portales coincidentes para una vía
	 * @param idVia Identificador de la vía
	 * @param numeroPortal Número del portal
	 * @return Listado de portales coincidentes
	 * @throws GeoLocalizacionServicioException En caso de producirse algún error
	 */
	public Portales validarPortal(int idVia, String numeroPortal) throws GeoLocalizacionServicioException;
	
	/**
	 * Método que valida una dirección postal completa
	 * @param tipoVia Tipo de vía
	 * @param nombreVia Nombre de la vía
	 * @param numeroPortal Número del portal
	 * @param codigoINEMunicipio Código de municipio
	 * @return true si existe, false si no existe
	 * @throws GeoLocalizacionServicioException En caso de producirse algún error
	 */
	public boolean validarDireccionPostal(String tipoVia, String nombreVia, String numeroPortal, int codigoINEMunicipio) throws GeoLocalizacionServicioException;
	
	/**
	 * Método que valida una dirección postal completa
	 * @param tipoVia Tipo de vía
	 * @param nombreVia Nombre de la vía
	 * @param numeroPortal Número del portal
	 * @param codigoINEMunicipio Código de municipio
	 * @return true si existe, false si no existe
	 * @throws GeoLocalizacionServicioException En caso de producirse algún error
	 */
	public Via validarDireccionPostalCompleta(String tipoVia, String nombreVia, String numeroPortal, int codigoINEMunicipio) throws GeoLocalizacionServicioException;
	
	/**
	 * Método que obtiene los municipios pertenecientes a una provincia
	 * @param idProvincia Identificador de la provincia
	 * @return Listado de municipios de la provincia
	 * @throws GeoLocalizacionServicioException En caso de producirse algún error
	 */
	public Municipios obtenerMunicipios(int idProvincia) throws GeoLocalizacionServicioException;;
	
	/**
	 * Método que obtiene las provincias
	 * @return Listado de provincias
	 * @throws GeoLocalizacionServicioException En caso de producirse algún error
	 */
	public Provincias obtenerProvincias() throws GeoLocalizacionServicioException;;
	
	/**
	 * Método que obtiene los tipos de vías
	 * @return Listado de tipos de vías
	 * @throws GeoLocalizacionServicioException
	 */
	public TiposVia obtenerTiposDeVia() throws GeoLocalizacionServicioException;;
}
