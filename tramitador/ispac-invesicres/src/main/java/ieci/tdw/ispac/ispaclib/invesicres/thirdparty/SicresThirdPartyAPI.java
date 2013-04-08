package ieci.tdw.ispac.ispaclib.invesicres.thirdparty;

import ieci.tdw.ispac.api.BasicThirdPartyAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.db.DbQuery;
import ieci.tdw.ispac.ispaclib.thirdparty.ElectronicAddressAdapter;
import ieci.tdw.ispac.ispaclib.thirdparty.IElectronicAddressAdapter;
import ieci.tdw.ispac.ispaclib.thirdparty.IPostalAddressAdapter;
import ieci.tdw.ispac.ispaclib.thirdparty.IThirdPartyAdapter;
import ieci.tdw.ispac.ispaclib.thirdparty.PostalAddressAdapter;
import ieci.tdw.ispac.ispaclib.thirdparty.ThirdPartyAdapter;
import ieci.tdw.ispac.ispaclib.util.ISPACConfiguration;
import ieci.tdw.ispac.ispaclib.utils.ArrayUtils;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * Implementación del API de acceso a terceros en SICRES.
 *
 */
public class SicresThirdPartyAPI extends BasicThirdPartyAPI {

	/** Logger de la clase. */
	private static final Logger logger = Logger.getLogger(SicresThirdPartyAPI.class);
	
	/** Nombre del origen de datos por defecto. */
	private static final String DEFAULT_DATASOURCE_NAME = 
		"java:comp/env/jdbc/sicres";
	
	/** Nombre del origen de datos. */
	protected String dsName = DEFAULT_DATASOURCE_NAME;
	
	/**Código para el teléfono móvil*/
	private static final String TYPE_TLFN_MOVIL="TM";
	/**Código para el teléfono fijo*/
	private static final String TYPE_TLFN_FIJO="TF";
	/**Código para el deu*/
	private static final String TYPE_DEU="DU";
	/**Código para el mail*/
	private static final String TYPE_MAIL="CE";
	/**Tipo de dirección electrónica*/
	private static final String TYPE_DIR_ELECTRONICA="1";
	
	private static final String TYPE_DIR_DOMICILIO="0";
	
	/**
	 * Constructor.
	 * @exception ISPACException si ocurre algún error.
	 */
	public SicresThirdPartyAPI() throws ISPACException {
		super();
		
		// Nombre del datasource de SICRES
		this.dsName = ISPACConfiguration.getInstance().get("THIRDPARTY_API_DATASOURCE_NAME",DEFAULT_DATASOURCE_NAME);
	}

	/**
	 * Constructor.
	 * @param dsName Nombre del origen de datos.
	 * @exception ISPACException si ocurre algún error.
	 */
	public SicresThirdPartyAPI(String dsName) throws ISPACException {
		super();
		
		// Nombre del datasource de SICRES
		this.dsName = dsName;
	}

	/**
	 * Obtiene una lista de terceros en función del códigos de identificación. 
	 * @param code Código de identificación del tercero
	 * @param onlyDefaultValues Indica si se cargan solamente las direcciones asociadas por defecto
	 * @return lista de terceros asociados con un código de identificación, 
	 * cargando las direcciones (postal y telemática) por defecto si así se indica. 
	 * @throws ISPACException
	 */
	public IThirdPartyAdapter[] lookup(String code, boolean onlyDefaultValues) throws ISPACException {

		List terceros = new ArrayList();
		
		DbCnt cnt = new DbCnt(dsName);
		DbQuery dbQuery = null;
		
		try {
			
			cnt.getConnection();
			
			dbQuery = cnt.executeDbQuery(getThirdPartySQLQuery(null, code, null, null, null));

			while (dbQuery.next()) {
				
				ThirdPartyAdapter tercero = createThirdPartyAdapter(dbQuery);
				
				if (onlyDefaultValues) {
					tercero.setDefaultDireccionElectronica(lookupDefaultElectronicAddress(tercero.getIdExt()));
					tercero.setDefaultDireccionPostal(lookupDefaultPostalAddress(tercero.getIdExt()));
				} else {
					tercero.setDireccionesElectronicas(lookupElectronicAddresses(tercero.getIdExt()));
					tercero.setDireccionesPostales(lookupPostalAddresses(tercero.getIdExt()));
				}
				
				if (ArrayUtils.isEmpty(tercero.getDireccionesPostales()) 
						&& !ArrayUtils.isEmpty(tercero.getDireccionesElectronicas())) {
					tercero.setNotificacionTelematica(true);
				} else {
					tercero.setNotificacionTelematica(false);
				}		

				terceros.add(tercero);
			}

		} catch (ISPACException e) {
			logger.error("Error en la búsqueda de terceros", e);
			throw e;
		} catch (Exception e) {
			logger.error("Error en la búsqueda de terceros", e);
			throw new ISPACException("Error en la búsqueda de terceros", e);
		} finally {
			if (dbQuery != null) {
				dbQuery.close();
			}
			cnt.closeConnection();
		}
		
		return (IThirdPartyAdapter[])terceros.toArray(
				new IThirdPartyAdapter[terceros.size()]);
	}
	
	/**
	 * Obtiene una lista de terceros en función del nombre y dos apellidos.
	 * @param name nombre del tercero
	 * @param surnam1 primer apellido del tercero
	 * @param surname2 segundo apellido del tercero
	 * @param onlyDefaultValues Indica si se cargan solamente las direcciones asociadas por defecto
	 * @return Terceros que cumple la condición.
	 * @exception ISPACException si ocurre algún error.
	 */
	public IThirdPartyAdapter [] lookup(String name, String surname1, String surname2, 
			boolean onlyDefaultValues) throws ISPACException {

		List terceros = new ArrayList();
		
		DbCnt cnt = new DbCnt(dsName);
		DbQuery dbQuery = null;
		
		try {
			
			cnt.getConnection();
			
			dbQuery = cnt.executeDbQuery(getThirdPartySQLQuery(null, null, name, surname1, surname2));

			while (dbQuery.next()) {
				
				ThirdPartyAdapter tercero = createThirdPartyAdapter(dbQuery);
				
				if (onlyDefaultValues) {
					tercero.setDefaultDireccionElectronica(lookupDefaultElectronicAddress(tercero.getIdExt()));
					tercero.setDefaultDireccionPostal(lookupDefaultPostalAddress(tercero.getIdExt()));
				} else {
					tercero.setDireccionesElectronicas(lookupElectronicAddresses(tercero.getIdExt()));
					tercero.setDireccionesPostales(lookupPostalAddresses(tercero.getIdExt()));
				}
				
				if (ArrayUtils.isEmpty(tercero.getDireccionesPostales()) 
						&& !ArrayUtils.isEmpty(tercero.getDireccionesElectronicas())) {
					tercero.setNotificacionTelematica(true);
				} else {
					tercero.setNotificacionTelematica(false);
				}		

				terceros.add(tercero);
			}

		} catch (ISPACException e) {
			logger.error("Error en la búsqueda de terceros", e);
			throw e;
		} catch (Exception e) {
			logger.error("Error en la búsqueda de terceros", e);
			throw new ISPACException("Error en la búsqueda de terceros", e);
		} finally {
			if (dbQuery != null) {
				dbQuery.close();
			}
			cnt.closeConnection();
		}
		
		return (IThirdPartyAdapter[])terceros.toArray(
				new IThirdPartyAdapter[terceros.size()]);
	}

	/**
	 * Obtiene un tercero a partir de su identificador.
	 * @param id identificador del tercero
	 * @param onlyDefaultValues Indica si se cargan las direcciones asociadas por defecto
	 * @return Información del tercero con sus direcciones por defecto si así se indica 
	 * @throws ISPACException
	 */
	public IThirdPartyAdapter lookupById(String id, boolean onlyDefaultValues) throws ISPACException {

		ThirdPartyAdapter tercero = null;
		
		DbCnt cnt = new DbCnt(dsName);
		DbQuery dbQuery = null;
		
		try {
			
			cnt.getConnection();
			
			dbQuery = cnt.executeDbQuery(getThirdPartySQLQuery(id, null, null, null, null));

			if (dbQuery.next()) {
				
				tercero = createThirdPartyAdapter(dbQuery);
				
				if (onlyDefaultValues) {
					tercero.setDefaultDireccionElectronica(lookupDefaultElectronicAddress(tercero.getIdExt()));
					tercero.setDefaultDireccionPostal(lookupDefaultPostalAddress(tercero.getIdExt()));
				} else {
					tercero.setDireccionesElectronicas(lookupElectronicAddresses(tercero.getIdExt()));
					tercero.setDireccionesPostales(lookupPostalAddresses(tercero.getIdExt()));
				}
				
				if (ArrayUtils.isEmpty(tercero.getDireccionesPostales()) 
						&& !ArrayUtils.isEmpty(tercero.getDireccionesElectronicas())) {
					tercero.setNotificacionTelematica(true);
				} else {
					tercero.setNotificacionTelematica(false);
				}		
			}
			
		} catch (ISPACException e) {
			logger.error("Error en la búsqueda de tercero", e);
			throw e;
		} catch (Exception e) {
			logger.error("Error en la búsqueda de tercero", e);
			throw new ISPACException("Error en la búsqueda de tercero", e);
		} finally {
			if (dbQuery != null) {
				dbQuery.close();
			}
			cnt.closeConnection();
		}
		
		return tercero;
	}
	
	/**
	 * Obtiene un tercero a partir de su identificador interno y los identificadores de sus direcciones.
	 * @param id Identificador interno del tercero.
	 * @param postalAddressId Identificador de la dirección postal.
	 * @param electronicAddressId Identificador de la dirección electrónica.
	 * @return Información del tercero.
	 * @exception ISPACException si ocurre algún error.
	 */
	public IThirdPartyAdapter lookupById(String id, String postalAddressId, String electronicAddressId) 
			throws ISPACException {

		ThirdPartyAdapter tercero = null;
		
		DbCnt cnt = new DbCnt(dsName);
		DbQuery dbQuery = null;
		
		try {
			
			cnt.getConnection();
			
			dbQuery = cnt.executeDbQuery(getThirdPartySQLQuery(id, null, null, null, null));

			if (dbQuery.next()) {
				
				tercero = createThirdPartyAdapter(dbQuery);
				
				tercero.setDefaultDireccionElectronica(getElectronicAddress(electronicAddressId));
				tercero.setDefaultDireccionPostal(getPostalAddress(postalAddressId));
				
				if (ArrayUtils.isEmpty(tercero.getDireccionesPostales()) 
						&& !ArrayUtils.isEmpty(tercero.getDireccionesElectronicas())) {
					tercero.setNotificacionTelematica(true);
				} else {
					tercero.setNotificacionTelematica(false);
				}		
			}
			
		} catch (ISPACException e) {
			logger.error("Error en la búsqueda de tercero", e);
			throw e;
		} catch (Exception e) {
			logger.error("Error en la búsqueda de tercero", e);
			throw new ISPACException("Error en la búsqueda de tercero", e);
		} finally {
			if (dbQuery != null) {
				dbQuery.close();
			}
			cnt.closeConnection();
		}
		
		return tercero;
	}

	
    /**
     * Obtiene una colección de todas las direcciones postales para un tercero
     * @param id identificador de tercero
     * @return lista de direcciones de postales relacionadas con un tercero
     * @throws Exception
     */
    public IPostalAddressAdapter [] lookupPostalAddresses(String id) throws ISPACException {
    	
		List direcciones = new ArrayList();
		
		DbCnt cnt = new DbCnt(dsName);
		DbQuery dbQuery = null;
		
		try {
			
			cnt.getConnection();
			
			dbQuery = cnt.executeDbQuery(getPostalAddressesSQLQuery(id));

			while (dbQuery.next()) {
				direcciones.add(createPostalAddressAdapter(dbQuery));
			}

		} catch (ISPACException e) {
			logger.error("Error en la búsqueda de direcciones postales", e);
			throw e;
		} catch (Exception e) {
			logger.error("Error en la búsqueda de direcciones postales", e);
			throw new ISPACException("Error en la búsqueda de direcciones postales", e);
		} finally {
			if (dbQuery != null) {
				dbQuery.close();
			}
			cnt.closeConnection();
		}
		
		return (IPostalAddressAdapter[])direcciones.toArray(
				new IPostalAddressAdapter[direcciones.size()]);
    }
    
    /**
     * Obtiene la dirección postal por defecto para un tercero
     * @param id identificador de tercero
     * @return dirección postal por defecto
     * @throws ISPACException
     */
    public IPostalAddressAdapter lookupDefaultPostalAddress(String id) throws ISPACException {

    	IPostalAddressAdapter direccion = null;
		
		DbCnt cnt = new DbCnt(dsName);
		DbQuery dbQuery = null;
		
		try {
			
			cnt.getConnection();
			
			dbQuery = cnt.executeDbQuery(getDefaultPostalAddressSQLQuery(id));

			if (dbQuery.next()) {
				direccion = createPostalAddressAdapter(dbQuery);
			}

		} catch (ISPACException e) {
			logger.error("Error en la búsqueda de la dirección postal por defecto", e);
			throw e;
		} catch (Exception e) {
			logger.error("Error en la búsqueda de la dirección postal por defecto", e);
			throw new ISPACException("Error en la búsqueda de la dirección postal por defecto", e);
		} finally {
			if (dbQuery != null) {
				dbQuery.close();
			}
			cnt.closeConnection();
		}
		
		return direccion;
    }
    
    /**
     * Obtiene una colección de todas las direcciones electrónicas para un tercero
     * @param id identificador de tercero
     * @return colección de direcciones electrónicas
     * @throws ISPACException
     */
    public IElectronicAddressAdapter [] lookupElectronicAddresses(String id) throws ISPACException {

		List direcciones = new ArrayList();
		
		DbCnt cnt = new DbCnt(dsName);
		DbQuery dbQuery = null;
		
		try {
			
			cnt.getConnection();
			
			dbQuery = cnt.executeDbQuery(getElectronicAddressesSQLQuery(id));

			while (dbQuery.next()) {
				direcciones.add(createElectronicAddressAdapter(dbQuery));
			}

		} catch (ISPACException e) {
			logger.error("Error en la búsqueda de direcciones electrónicas", e);
			throw e;
		} catch (Exception e) {
			logger.error("Error en la búsqueda de direcciones electrónicas", e);
			throw new ISPACException("Error en la búsqueda de direcciones electrónicas", e);
		} finally {
			if (dbQuery != null) {
				dbQuery.close();
			}
			cnt.closeConnection();
		}
		
		return (IElectronicAddressAdapter[])direcciones.toArray(
				new IElectronicAddressAdapter[direcciones.size()]);
    }

    /**
     * Obtiene la dirección electrónica por defecto para un tercero
     * @param id identificador de tercero
     * @return dirección electrónica
     * @throws ISPACException
     */
    public IElectronicAddressAdapter lookupDefaultElectronicAddress(String id) throws ISPACException {

    	IElectronicAddressAdapter direccion = null;
		
		DbCnt cnt = new DbCnt(dsName);
		DbQuery dbQuery = null;
		
		try {
			
			cnt.getConnection();
			
			dbQuery = cnt.executeDbQuery(getDefaultElectronicAddressSQLQuery(id));

			if (dbQuery.next()) {
				direccion = createElectronicAddressAdapter(dbQuery);
			}

		} catch (ISPACException e) {
			logger.error("Error en la búsqueda de la dirección electrónica por defecto", e);
			throw e;
		} catch (Exception e) {
			logger.error("Error en la búsqueda de la dirección electrónica por defecto", e);
			throw new ISPACException("Error en la búsqueda de la dirección electrónica por defecto", e);
		} finally {
			if (dbQuery != null) {
				dbQuery.close();
			}
			cnt.closeConnection();
		}
		
		return direccion;
    }

	/**
	 * Obtiene una dirección postal según su identificador
	 * @param addressId identificador de dirección postal
	 * @return dirección postal
	 * @throws ISPACException
	 */
	public IPostalAddressAdapter getPostalAddress(String addressId) throws ISPACException {

    	IPostalAddressAdapter direccion = null;
		
		DbCnt cnt = new DbCnt(dsName);
		DbQuery dbQuery = null;
		
		try {
			
			cnt.getConnection();
			
			dbQuery = cnt.executeDbQuery(getPostalAddressSQLQuery(addressId));

			if (dbQuery.next()) {
				direccion = createPostalAddressAdapter(dbQuery);
			}

		} catch (ISPACException e) {
			logger.error("Error en la búsqueda de la dirección postal", e);
			throw e;
		} catch (Exception e) {
			logger.error("Error en la búsqueda de la dirección postal", e);
			throw new ISPACException("Error en la búsqueda de la dirección postal", e);
		} finally {
			if (dbQuery != null) {
				dbQuery.close();
			}
			cnt.closeConnection();
		}
		
		return direccion;
	}

	/**
	 * Obtiene una dirección electrónica según su identificador
	 * @param addressId identificador de dirección electrónica
	 * @return dirección electrónica
	 * @throws ISPACException
	 */
	public IElectronicAddressAdapter getElectronicAddress(String addressId) throws ISPACException {

    	IElectronicAddressAdapter direccion = null;
		
		DbCnt cnt = new DbCnt(dsName);
		DbQuery dbQuery = null;
		
		try {
			
			cnt.getConnection();
			
			dbQuery = cnt.executeDbQuery(getElectronicAddressSQLQuery(addressId));

			if (dbQuery.next()) {
				direccion = createElectronicAddressAdapter(dbQuery);
			}

		} catch (ISPACException e) {
			logger.error("Error en la búsqueda de la dirección electrónica", e);
			throw e;
		} catch (Exception e) {
			logger.error("Error en la búsqueda de la dirección electrónica", e);
			throw new ISPACException("Error en la búsqueda de la dirección electrónica", e);
		} finally {
			if (dbQuery != null) {
				dbQuery.close();
			}
			cnt.closeConnection();
		}
		
		return direccion;
	}
	
	/**
	 * Compone un tercero con la información de la base de datos.
	 * @param dbQuery Información obtenida de la base de datos.
	 * @return Tercero.
	 * @throws ISPACException si ocurre algún error.
	 */
	private static ThirdPartyAdapter createThirdPartyAdapter(DbQuery dbQuery)
			throws ISPACException {

		ThirdPartyAdapter tercero = new ThirdPartyAdapter();

		tercero.setIdExt(dbQuery.getString("ID"));
		tercero.setTipoPersona(dbQuery.getString("PERSON_TYPE"));
		tercero.setIdentificacion(dbQuery.getString("DOCUMENT"));
		tercero.setNombre(dbQuery.getString("NAME"));
		tercero.setPrimerApellido(dbQuery.getString("FIRST_NAME"));
		tercero.setSegundoApellido(dbQuery.getString("SECOND_NAME"));

		return tercero;
	}

	/**
	 * Compone una dirección postal con la información de la base de datos. 
	 * @param dbQuery Información obtenida de la base de datos.
	 * @return Dirección postal.
	 * @throws ISPACException si ocurre algún error.
	 */
	private static IPostalAddressAdapter createPostalAddressAdapter(DbQuery dbQuery) throws ISPACException {

		PostalAddressAdapter direccion = new PostalAddressAdapter();
		
		direccion.setId(dbQuery.getString("ID"));
		direccion.setDireccionPostal(dbQuery.getString("ADDRESS"));
		direccion.setMunicipio(dbQuery.getString("CITY"));
		direccion.setCodigoPostal(dbQuery.getString("ZIP"));
		direccion.setTelefono(dbQuery.getString("PHONE"));
		
		String provinciaPais = dbQuery.getString("COUNTRY");
		if (StringUtils.isNotBlank(provinciaPais)) {
			int ix = provinciaPais.indexOf("/");
			if (ix > 0) {
				direccion.setProvincia(provinciaPais.substring(0, ix));
				direccion.setPais(provinciaPais.substring(ix+1));
			} else {
				direccion.setProvincia(provinciaPais);
			}
		}
		
		return direccion;
	}

	/**
	 * Compone una dirección electrónica con la información de la base de datos. 
	 * @param dbQuery Información obtenida de la base de datos.
	 * @return Dirección electrónica.
	 * @throws ISPACException si ocurre algún error.
	 */
	private static IElectronicAddressAdapter createElectronicAddressAdapter(DbQuery dbQuery) throws ISPACException {

		ElectronicAddressAdapter direccion = new ElectronicAddressAdapter();

		direccion.setId(dbQuery.getString("ID"));
		direccion.setDireccion(dbQuery.getString("ADDRESS"));
		
		String code = dbQuery.getString("CODE");
	
		if(StringUtils.equalsIgnoreCase(code, TYPE_TLFN_MOVIL)){
			direccion.setTipo(IElectronicAddressAdapter.MOBILE_PHONE_TYPE);
		}
		else if(StringUtils.equalsIgnoreCase(code, TYPE_DEU)){
			direccion.setTipo(IElectronicAddressAdapter.DEU_TYPE);
		}
		else if(StringUtils.equalsIgnoreCase(code, TYPE_MAIL)){
			direccion.setTipo(IElectronicAddressAdapter.MAIL_TYPE);
		}
		else{
			direccion.setTipo(IElectronicAddressAdapter.PHONE_TYPE);
		}
		
		return direccion;
	}

	/**
	 * Obtiene la consulta SQL para obtener el/los tercero/s.
	 * @param id Identificador del tercero.
	 * @param nifcif NIF/CIF del tercero.
	 * @param name Nombre o razón social del tercero.
	 * @param surname1 Primer apellido del tercero.
	 * @param surname2 Segundo apellido del tercero.
	 * @return Consulta SQL.
	 */
	private static String getThirdPartySQLQuery(String id, String nifcif, String name,
			String surname1, String surname2) {

		StringBuffer pfisCondition = new StringBuffer();
		StringBuffer pjurCondition = new StringBuffer();

		// Filtro por identificador de tercero
		if (StringUtils.isNotBlank(id)) {

			pfisCondition.append(" WHERE").append(" PERSON.ID=").append(id.trim());

			pjurCondition.append(" WHERE").append(" PERSON.ID=").append(id.trim());
		}

		// Filtro por NIF/CIF
		if (StringUtils.isNotBlank(nifcif)) {
			if (pfisCondition.length() > 0) {
				pfisCondition.append(" AND ");
				pjurCondition.append(" AND ");
			} else {
				pfisCondition.append(" WHERE ");
				pjurCondition.append(" WHERE ");
			}

			pfisCondition.append("PERSON.NIF='").append(
					nifcif.toUpperCase().trim()).append("'");

			pjurCondition.append("PERSON.CIF='").append(
					nifcif.toUpperCase().trim()).append("'");
		}

		// Filtro por nombre y apellidos
		if (StringUtils.isNotBlank(name) || StringUtils.isNotBlank(surname1)
				|| StringUtils.isNotBlank(surname2)) {

			StringBuffer pjurName = new StringBuffer();
			
			if (StringUtils.isNotBlank(name)) {
				if (pfisCondition.length() > 0) {
					pfisCondition.append(" AND ");
				} else {
					pfisCondition.append(" WHERE ");
				}
				pfisCondition.append("PERSON.SURNAME LIKE '%")
							 .append(DBUtil.replaceQuotes(name.toUpperCase().trim()))
							 .append("%'");
				
				pjurName.append("%").append(name.toUpperCase().trim());
			}

			if (StringUtils.isNotBlank(surname1)) {
				if (pfisCondition.length() > 0) {
					pfisCondition.append(" AND ");
				} else {
					pfisCondition.append(" WHERE ");
				}
				pfisCondition.append("PERSON.FIRST_NAME LIKE '%")
							 .append(DBUtil.replaceQuotes(surname1.toUpperCase().trim()))
							 .append("%'");
				
				pjurName.append("%").append(surname1.toUpperCase().trim());
			}

			if (StringUtils.isNotBlank(surname2)) {
				if (pfisCondition.length() > 0) {
					pfisCondition.append(" AND ");
				} else {
					pfisCondition.append(" WHERE ");
				}
				pfisCondition.append("PERSON.SECOND_NAME LIKE '%")
							 .append(DBUtil.replaceQuotes(surname2.toUpperCase().trim()))
							 .append("%'");
				
				pjurName.append("%").append(surname2.toUpperCase().trim());
			}
			
			if (pjurCondition.length() > 0) {
				pjurCondition.append(" AND ");
			} else {
				pjurCondition.append(" WHERE ");
			}
			pjurCondition.append("PERSON.NAME LIKE '")
						 .append(DBUtil.replaceQuotes(pjurName.toString()))
						 .append("%'");
		}

		String sql = new StringBuffer()
			.append("SELECT ")
			.append(" PERSON.ID, 'F' AS PERSON_TYPE, PERSON.NIF AS DOCUMENT, PERSON.SURNAME AS NAME, PERSON.FIRST_NAME, PERSON.SECOND_NAME")
			.append(" FROM SCR_PFIS PERSON")
			.append(pfisCondition)
			.append(" UNION")
			.append(" SELECT")
			.append(" PERSON.ID, 'J' AS PERSON_TYPE, PERSON.CIF AS DOCUMENT, PERSON.NAME, '' AS FIRST_NAME, '' AS SECOND_NAME")
			.append(" FROM SCR_PJUR PERSON")
			.append(pjurCondition)
			.toString();

		return sql;
	}

	/**
	 * Obtiene la consulta SQL común para las direcciones electrónicas.
	 * @return Consulta SQL.
	 */
	private static String getCommonElectronicAddressesSQLQuery() {

		String sql = new StringBuffer()
			.append("SELECT ")
			.append(" BOUND.ID, TYPEADDRESS.CODE,ADDRTEL.ADDRESS")
			.append(" FROM SCR_ADDRESS BOUND,SCR_ADDRTEL ADDRTEL ,SCR_TYPEADDRESS TYPEADDRESS")
			.toString();

		return sql;
	}

	/**
	 * Obtiene la consulta SQL para obtener las direcciones electrónicas.
	 * @param thirdPartyId Identificador del tercero.
	 * @return Consulta SQL.
	 */
	private static String getElectronicAddressesSQLQuery(String thirdPartyId) {

		String sql = new StringBuffer()
			.append(getCommonElectronicAddressesSQLQuery())
			.append(" WHERE BOUND.ID_PERSON=")
			.append(thirdPartyId)
			.append(" AND BOUND.TYPE="+TYPE_DIR_ELECTRONICA)
			.append(" AND BOUND.ID=ADDRTEL.ID")
			.append(" AND ADDRTEL.TYPE=TYPEADDRESS.ID")
			.append(" AND (TYPEADDRESS.CODE='"+TYPE_DEU+"' OR TYPEADDRESS.CODE='"+TYPE_TLFN_MOVIL+"')")
			.append(" ORDER BY ADDRTEL.PREFERENCE DESC, BOUND.ID")
			.toString();

		return sql;
	}

	/**
	 * Obtiene la consulta SQL para obtener la dirección electrónica por defecto.
	 * @param thirdPartyId Identificador del tercero.
	 * @return Consulta SQL.
	 */
	private static String getDefaultElectronicAddressSQLQuery(String thirdPartyId) {

	
		String sql = new StringBuffer()
			.append(getCommonElectronicAddressesSQLQuery())
			.append(" WHERE BOUND.ID_PERSON=")
			.append(thirdPartyId)
			.append(" AND BOUND.TYPE="+TYPE_DIR_ELECTRONICA)
			.append(" AND BOUND.ID=ADDRTEL.ID")
			.append(" AND ADDRTEL.TYPE=TYPEADDRESS.ID")
			.append(" AND (TYPEADDRESS.CODE='"+TYPE_DEU+"' OR TYPEADDRESS.CODE='"+TYPE_TLFN_MOVIL+"')")
		   .append(" AND ADDRTEL.PREFERENCE=1")
			.toString();

		return sql;
	}

	/**
	 * Obtiene la consulta SQL para obtener una dirección electrónica.
	 * @param addressId Identificador de la dirección.
	 * @return Consulta SQL.
	 */
	private static String getElectronicAddressSQLQuery(String addressId) {

		String sql = new StringBuffer()
			.append(getCommonElectronicAddressesSQLQuery())
			.append(" WHERE BOUND.ID=")
			.append(addressId)
			.append(" AND BOUND.ID=ADDRTEL.ID AND ADDRTEL.TYPE=TYPEADDRESS.ID")
			.toString();

		return sql;
	}

	/**
	 * Obtiene la consulta SQL común para las direcciones postales.
	 * @return Consulta SQL.
	 */
	
	
	private static String getCommonPostalAddressesSQLQuery() {	
		String sql = new StringBuffer()
			.append("SELECT ")
			.append(" BOUND.ID,DOM.ADDRESS,DOM.CITY,DOM.ZIP,DOM.COUNTRY,PHONE.ADDRESS AS PHONE")
			.append(" FROM SCR_ADDRESS BOUND")
			.append(" LEFT OUTER JOIN (SELECT PBOUND.ID_PERSON,ADDRTEL.ADDRESS FROM SCR_ADDRESS PBOUND,SCR_ADDRTEL ADDRTEL, SCR_TYPEADDRESS TYPEADDRESS WHERE PBOUND.TYPE="+TYPE_DIR_ELECTRONICA+" AND PBOUND.ID=ADDRTEL.ID AND ADDRTEL.TYPE=TYPEADDRESS.ID AND TYPEADDRESS.CODE='"+TYPE_TLFN_FIJO+"' AND ADDRTEL.PREFERENCE=1) PHONE ON BOUND.ID_PERSON=PHONE.ID_PERSON")
			.append(",SCR_DOM DOM")
			.toString();

		return sql;
	}


	
	/**
	 * Obtiene la consulta SQL para obtener las direcciones postales.
	 * @param thirdPartyId Identificador del tercero.
	 * @return Consulta SQL.
	 */
	private static String getPostalAddressesSQLQuery(String thirdPartyId) {

		String sql = new StringBuffer()
			.append(getCommonPostalAddressesSQLQuery())
			.append(" WHERE BOUND.ID_PERSON=")
			.append(thirdPartyId)
			.append(" AND BOUND.TYPE="+TYPE_DIR_DOMICILIO+"")
			.append(" AND BOUND.ID=DOM.ID")
			.append(" ORDER BY DOM.PREFERENCE DESC, BOUND.ID")
			.toString();

		return sql;
	}

	/**
	 * Obtiene la consulta SQL para obtener la dirección postal por defecto.
	 * @param thirdPartyId Identificador del tercero.
	 * @return Consulta SQL.
	 */
	private static String getDefaultPostalAddressSQLQuery(String thirdPartyId) {

		String sql = new StringBuffer()
			.append(getCommonPostalAddressesSQLQuery())
			.append(" WHERE BOUND.ID_PERSON=")
			.append(thirdPartyId)
			.append(" AND BOUND.TYPE=0")
			.append(" AND BOUND.ID=DOM.ID")
			.append(" AND DOM.PREFERENCE=1")
			.toString();

		return sql;
	}

	/**
	 * Obtiene la consulta SQL para obtener una dirección postal.
	 * @param addressId Identificador de la dirección.
	 * @return Consulta SQL.
	 */
	private static String getPostalAddressSQLQuery(String addressId) {

		String sql = new StringBuffer()
			.append(getCommonPostalAddressesSQLQuery())
			.append(" WHERE BOUND.ID=")
			.append(addressId)
			.append(" AND BOUND.ID=DOM.ID")
		
			.toString();

		return sql;
	}

//	/**
//	 * Obtiene la consulta SQL para obtener el/los tercero/s.
//	 * @param id Identificador del tercero.
//	 * @param nifcif NIF/CIF del tercero.
//	 * @param name Nombre o razón social del tercero.
//	 * @param surname1 Primer apellido del tercero.
//	 * @param surname2 Segundo apellido del tercero.
//	 * @return Consulta SQL.
//	 */
//	private static String getSQLQuery(String id, String nifcif, String name,
//			String surname1, String surname2) {
//
//		StringBuffer pfisCondition = new StringBuffer();
//		StringBuffer pjurCondition = new StringBuffer();
//
//		// Filtro por identificador de tercero
//		if (StringUtils.isNotBlank(id)) {
//
//			pfisCondition.append(" WHERE").append(" PERSON.ID=").append(id.trim());
//
//			pjurCondition.append(" WHERE").append(" PERSON.ID=").append(id.trim());
//		}
//
//		// Filtro por NIF/CIF
//		if (StringUtils.isNotBlank(nifcif)) {
//			if (pfisCondition.length() > 0) {
//				pfisCondition.append(" AND ");
//				pjurCondition.append(" AND ");
//			} else {
//				pfisCondition.append(" WHERE ");
//				pjurCondition.append(" WHERE ");
//			}
//
//			pfisCondition.append("PERSON.NIF='").append(
//					nifcif.toUpperCase().trim()).append("'");
//
//			pjurCondition.append("PERSON.CIF='").append(
//					nifcif.toUpperCase().trim()).append("'");
//		}
//
//		// Filtro por nombre y apellidos
//		if (StringUtils.isNotBlank(name) || StringUtils.isNotBlank(surname1)
//				|| StringUtils.isNotBlank(surname2)) {
//
//			StringBuffer pjurName = new StringBuffer();
//			
//			if (StringUtils.isNotBlank(name)) {
//				if (pfisCondition.length() > 0) {
//					pfisCondition.append(" AND ");
//				} else {
//					pfisCondition.append(" WHERE ");
//				}
//				pfisCondition.append("PERSON.SURNAME LIKE '%")
//							 .append(DBUtil.replaceQuotes(name.toUpperCase().trim()))
//							 .append("%'");
//				
//				pjurName.append("%").append(name.toUpperCase().trim());
//			}
//
//			if (StringUtils.isNotBlank(surname1)) {
//				if (pfisCondition.length() > 0) {
//					pfisCondition.append(" AND ");
//				} else {
//					pfisCondition.append(" WHERE ");
//				}
//				pfisCondition.append("PERSON.FIRST_NAME LIKE '%")
//							 .append(DBUtil.replaceQuotes(surname1.toUpperCase().trim()))
//							 .append("%'");
//				
//				pjurName.append("%").append(surname1.toUpperCase().trim());
//			}
//
//			if (StringUtils.isNotBlank(surname2)) {
//				if (pfisCondition.length() > 0) {
//					pfisCondition.append(" AND ");
//				} else {
//					pfisCondition.append(" WHERE ");
//				}
//				pfisCondition.append("PERSON.SECOND_NAME LIKE '%")
//							 .append(DBUtil.replaceQuotes(surname2.toUpperCase().trim()))
//							 .append("%'");
//				
//				pjurName.append("%").append(surname2.toUpperCase().trim());
//			}
//			
//			if (pjurCondition.length() > 0) {
//				pjurCondition.append(" AND ");
//			} else {
//				pjurCondition.append(" WHERE ");
//			}
//			pjurCondition.append("PERSON.NAME LIKE '")
//						 .append(DBUtil.replaceQuotes(pjurName.toString()))
//						 .append("%'");
//		}
//
//		String sql = new StringBuffer()
//			.append("SELECT ")
//			.append(
//					" PERSON.ID, 'F' AS PERSON_TYPE, PERSON.NIF AS DOCUMENT, PERSON.SURNAME AS NAME, PERSON.FIRST_NAME, PERSON.SECOND_NAME, DOM.ADDRESS AS ADDRESS, DOM.CITY AS CITY, DOM.ZIP AS ZIP, DOM.COUNTRY AS COUNTRY, EMAIL.ADDRESS AS EMAIL, PHONE.ADDRESS AS PHONE, MOBILE.ADDRESS AS MOBILE")
//			.append(" FROM SCR_PFIS PERSON")
//			.append(
//					" LEFT OUTER JOIN (SELECT BOUND.ID_PERSON,DOM.ADDRESS,DOM.CITY,DOM.ZIP,DOM.COUNTRY FROM SCR_ADDRESS BOUND,SCR_DOM DOM WHERE BOUND.TYPE=0 AND BOUND.ID=DOM.ID AND DOM.PREFERENCE=1) DOM ON PERSON.ID=DOM.ID_PERSON")
//			.append(
//					" LEFT OUTER JOIN (SELECT BOUND.ID_PERSON,ADDRTEL.ADDRESS FROM SCR_ADDRESS BOUND,SCR_ADDRTEL ADDRTEL WHERE BOUND.TYPE=1 AND BOUND.ID=ADDRTEL.ID AND ADDRTEL.TYPE=2 AND ADDRTEL.PREFERENCE=1) EMAIL ON PERSON.ID=EMAIL.ID_PERSON")
//			.append(
//					" LEFT OUTER JOIN (SELECT BOUND.ID_PERSON,ADDRTEL.ADDRESS FROM SCR_ADDRESS BOUND,SCR_ADDRTEL ADDRTEL WHERE BOUND.TYPE=1 AND BOUND.ID=ADDRTEL.ID AND ADDRTEL.TYPE=1 AND ADDRTEL.PREFERENCE=1 AND ADDRTEL.ADDRESS LIKE '9%') PHONE ON PERSON.ID=PHONE.ID_PERSON")
//			.append(
//					" LEFT OUTER JOIN (SELECT BOUND.ID_PERSON,ADDRTEL.ADDRESS FROM SCR_ADDRESS BOUND,SCR_ADDRTEL ADDRTEL WHERE BOUND.TYPE=1 AND BOUND.ID=ADDRTEL.ID AND ADDRTEL.TYPE=1 AND ADDRTEL.PREFERENCE=1 AND ADDRTEL.ADDRESS LIKE '6%') MOBILE ON PERSON.ID=MOBILE.ID_PERSON")
//			.append(pfisCondition)
//			.append(" UNION")
//			.append(" SELECT")
//			.append(
//					" PERSON.ID, 'J' AS PERSON_TYPE, PERSON.CIF AS DOCUMENT, PERSON.NAME, '' AS FIRST_NAME, '' AS SECOND_NAME, DOM.ADDRESS AS ADDRESS, DOM.CITY AS CITY, DOM.ZIP AS ZIP, DOM.COUNTRY AS COUNTRY, EMAIL.ADDRESS AS EMAIL, PHONE.ADDRESS AS PHONE, MOBILE.ADDRESS AS MOBILE")
//			.append(" FROM SCR_PJUR PERSON")
//			.append(
//					" LEFT OUTER JOIN (SELECT BOUND.ID_PERSON,DOM.ADDRESS,DOM.CITY,DOM.ZIP,DOM.COUNTRY FROM SCR_ADDRESS BOUND,SCR_DOM DOM WHERE BOUND.TYPE=0 AND BOUND.ID=DOM.ID AND DOM.PREFERENCE=1) DOM ON PERSON.ID=DOM.ID_PERSON")
//			.append(
//					" LEFT OUTER JOIN (SELECT BOUND.ID_PERSON,ADDRTEL.ADDRESS FROM SCR_ADDRESS BOUND,SCR_ADDRTEL ADDRTEL WHERE BOUND.TYPE=1 AND BOUND.ID=ADDRTEL.ID AND ADDRTEL.TYPE=2 AND ADDRTEL.PREFERENCE=1) EMAIL ON PERSON.ID=EMAIL.ID_PERSON")
//			.append(
//					" LEFT OUTER JOIN (SELECT BOUND.ID_PERSON,ADDRTEL.ADDRESS FROM SCR_ADDRESS BOUND,SCR_ADDRTEL ADDRTEL WHERE BOUND.TYPE=1 AND BOUND.ID=ADDRTEL.ID AND ADDRTEL.TYPE=1 AND ADDRTEL.PREFERENCE=1 AND ADDRTEL.ADDRESS LIKE '9%') PHONE ON PERSON.ID=PHONE.ID_PERSON")
//			.append(
//					" LEFT OUTER JOIN (SELECT BOUND.ID_PERSON,ADDRTEL.ADDRESS FROM SCR_ADDRESS BOUND,SCR_ADDRTEL ADDRTEL WHERE BOUND.TYPE=1 AND BOUND.ID=ADDRTEL.ID AND ADDRTEL.TYPE=1 AND ADDRTEL.PREFERENCE=1 AND ADDRTEL.ADDRESS LIKE '6%') MOBILE ON PERSON.ID=MOBILE.ID_PERSON")
//			.append(pjurCondition)
//			.toString();
//
//		return sql;
//	}
}
