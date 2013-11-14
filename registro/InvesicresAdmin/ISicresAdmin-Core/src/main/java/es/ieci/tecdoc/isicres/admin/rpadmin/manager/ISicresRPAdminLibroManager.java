package es.ieci.tecdoc.isicres.admin.rpadmin.manager;



import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.core.beans.FiltroImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.FiltrosImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.IDocArchHDRImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.IDocArchsHDRImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.IUserObjPermImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.IUserObjsPermsImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.IVolArchListImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresContadorCentralImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresContadoresOficinasImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresLibroEstadoImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresLibroOficinaImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresLibrosOficinasImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.definicion.DefinicionFilterCombo;
import es.ieci.tecdoc.isicres.admin.core.beans.definicion.DefinicionFilterFecha;
import es.ieci.tecdoc.isicres.admin.core.beans.definicion.DefinicionFilterField;
import es.ieci.tecdoc.isicres.admin.core.beans.definicion.DefinicionFilterOficina;
import es.ieci.tecdoc.isicres.admin.core.beans.definicion.DefinicionFilterString;
import es.ieci.tecdoc.isicres.admin.core.beans.definicion.DefinicionFilterTipoAsunto;
import es.ieci.tecdoc.isicres.admin.core.beans.definicion.DefinicionFilterUnidadAdm;
import es.ieci.tecdoc.isicres.admin.core.beans.definicion.DefinicionLibroEntrada;
import es.ieci.tecdoc.isicres.admin.core.beans.definicion.DefinicionLibroRegistro;
import es.ieci.tecdoc.isicres.admin.core.beans.definicion.DefinicionLibroSalida;
import es.ieci.tecdoc.isicres.admin.core.beans.definicion.DefinicionLibroSicres3Utils;
import es.ieci.tecdoc.isicres.admin.core.database.IDocArchHDRDatos;
import es.ieci.tecdoc.isicres.admin.core.database.IUserObjPermDatos;
import es.ieci.tecdoc.isicres.admin.core.database.IVolArchListDatos;
import es.ieci.tecdoc.isicres.admin.core.database.IdsGenerator;
import es.ieci.tecdoc.isicres.admin.core.database.SicresContadorCentralDatos;
import es.ieci.tecdoc.isicres.admin.core.database.SicresContadorOficinaDatos;
import es.ieci.tecdoc.isicres.admin.core.database.SicresLibroEstadoDatos;
import es.ieci.tecdoc.isicres.admin.core.database.SicresLibroInformesDatos;
import es.ieci.tecdoc.isicres.admin.core.database.SicresLibroOficinaDatos;
import es.ieci.tecdoc.isicres.admin.core.database.SicresUserFilterDatos;
import es.ieci.tecdoc.isicres.admin.core.db.DBSessionManager;
import es.ieci.tecdoc.isicres.admin.core.exception.ISicresAdminDAOException;
import es.ieci.tecdoc.isicres.admin.core.exception.IeciTdException;
import es.ieci.tecdoc.isicres.admin.estructura.adapter.ISicresAdminEstructuraAdapter;
import es.ieci.tecdoc.isicres.admin.estructura.beans.Archive;
import es.ieci.tecdoc.isicres.admin.estructura.beans.ArchiveFld;
import es.ieci.tecdoc.isicres.admin.estructura.beans.ArchiveFlds;
import es.ieci.tecdoc.isicres.admin.estructura.beans.Listas;
import es.ieci.tecdoc.isicres.admin.estructura.dao.ArchiveUpdInfo;
import es.ieci.tecdoc.isicres.admin.estructura.dao.impl.ArchiveFldImpl;
import es.ieci.tecdoc.isicres.admin.estructura.factory.ISicresAdminObjectFactory;
import es.ieci.tecdoc.isicres.admin.estructura.keys.ISicresAdminDefsKeys;
import es.ieci.tecdoc.isicres.admin.exception.ISicresAdminEstructuraException;
import es.ieci.tecdoc.isicres.admin.exception.ISicresAdminException;
import es.ieci.tecdoc.isicres.admin.exception.ISicresRPAdminDAOException;
import es.ieci.tecdoc.isicres.admin.service.ISicresAdminEstructuraService;

/*$Id*/

public class ISicresRPAdminLibroManager {


	private static final Logger logger = Logger
			.getLogger(ISicresRPAdminLibroManager.class);

	public static IDocArchsHDRImpl obtenerLibros(int tipoLibro, String entidad)
			throws ISicresRPAdminDAOException {
		DbConnection db = new DbConnection();
		IDocArchsHDRImpl archivadores = null;
		try {
			db.open(DBSessionManager.getSession());
			archivadores = IDocArchHDRDatos.getArchivadores(tipoLibro, db);
		} catch (Exception e) {
			logger.error("Error obteniendo libros");
			throw new ISicresRPAdminDAOException(
					ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		} finally {
			try {
				if (db != null && db.existConnection()){
					db.close();
				}
			} catch (Exception e) {
				logger.error("No se ha podido cerrar la conexión a la BBDD");
			}
		}
		return archivadores;
	}

	public static IDocArchHDRImpl getArchivador(int bookId, String entidad)
			throws ISicresRPAdminDAOException {
		DbConnection db = new DbConnection();
		IDocArchHDRDatos libro = new IDocArchHDRDatos();
		try {
			db.open(DBSessionManager.getSession());
			libro.load(bookId, db);
		} catch (Exception e) {
			logger.error("Error obteniendo libro");
			throw new ISicresRPAdminDAOException(
					ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		} finally {
			try {
				if (db != null && db.existConnection()){
					db.close();
				}
			} catch (Exception e) {
				logger.error("No se ha podido cerrar la conexión a la BBDD");
			}
		}
		return libro;
	}

	public static SicresLibroEstadoImpl getLibro(int bookId, String entidad)
			throws ISicresRPAdminDAOException {
		DbConnection db = new DbConnection();
		SicresLibroEstadoDatos libro = new SicresLibroEstadoDatos();
		try {
			db.open(DBSessionManager.getSession());
			libro.load(bookId, db);
		} catch (Exception e) {
			logger.error("Error obteniendo libro");
			throw new ISicresRPAdminDAOException(
					ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		} finally {
			try {
				if (db != null && db.existConnection()){
					db.close();
				}
			} catch (Exception e) {
				logger.error("No se ha podido cerrar la conexión a la BBDD");
			}
		}
		return libro;
	}

	/**
	 * Método que comprueba si un Libro ya tiene los campos de Sicres3
	 * @param idLibro: identificador del libro
	 * @param entidad: entidad de BBDD
	 * @return true si el libro ya es Sicres3, false en caso contrario
	 * @throws ISicresRPAdminDAOException
	 */

	public static boolean isLibroSicres3 (int idLibro, String entidad) throws ISicresRPAdminDAOException {
		boolean isLibroSicres3 = false;

		try {
			es.ieci.tecdoc.isicres.admin.estructura.dao.Archive arch =
				ISicresAdminObjectFactory.createArchive(ISicresAdminDefsKeys.NULL_ID, ISicresAdminDefsKeys.NULL_ID, false);

			arch.load(idLibro, entidad);

			es.ieci.tecdoc.isicres.admin.estructura.dao.ArchiveFlds flds = arch.getFldsDef();

			isLibroSicres3 = isLibroSicres3(flds);

		} catch (Exception e) {
			logger.error("Error obteniendo libro");
			throw new ISicresRPAdminDAOException(
					ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}

		return isLibroSicres3;
	}

	/**
	 * Método que comprueba si un archivador determinado tiene los campos de Sicres3
	 * @param flds:
	 * 				lista de campos del archivador
	 * @return: true si el libro es Sicres3, false en caso contrario
	 * @throws Exception
	 */
	public static boolean isLibroSicres3 (es.ieci.tecdoc.isicres.admin.estructura.dao.ArchiveFlds flds)
																														throws Exception {
		boolean isLibroSicres3 = false;

		try {
			flds.getFldDefById(DefinicionLibroSicres3Utils.SICRES3_EXPONE_FIELD_ID);
			isLibroSicres3 = true;
		} catch (IeciTdException e) {
			// No es un libro Sicres3, dejamos constancia en el log y continuamos
			logger.info("El libro seleccionado no tiene los campos de Intercambio Registral, no es un libro Sicres3");
		}

		return isLibroSicres3;
	}

	public static boolean hasReservedFields (es.ieci.tecdoc.isicres.admin.estructura.dao.ArchiveFlds flds)
																															throws Exception {
		boolean hasReservedFields = false;

		try {
			flds.getFldDefById(DefinicionLibroSicres3Utils.ADDITIONAL_RESERVED_FIELDS_LOWER_LIMIT_FIELD_ID);
			hasReservedFields = true;
		} catch (IeciTdException e) {
			// No es un libro Sicres3, dejamos constancia en el log y continuamos
			logger.info("El libro seleccionado no tiene los campos de límite de campos reservados para producto");
		}

		return hasReservedFields;
	}

	/**
	 * Método que actualiza un libro a Sicres3 añadiéndole los campos de IR
	 * @param idLibro:
	 * 					identificador del libro a actualizar
	 * @param entidad:
	 * 					entidad de BBDD
	 * @throws ISicresRPAdminDAOException
	 */
	public static void actualizarLibroASicres3(int idLibro, String entidad)
			throws ISicresRPAdminDAOException {

		boolean isLibroSicres3 = false;
		boolean hasReservedFields = false;
		ArchiveFlds archiveFlds = null;

		try {
			es.ieci.tecdoc.isicres.admin.estructura.dao.Archive arch =
				ISicresAdminObjectFactory.createArchive(ISicresAdminDefsKeys.NULL_ID, ISicresAdminDefsKeys.NULL_ID, false);

			arch.load(idLibro, entidad);

			es.ieci.tecdoc.isicres.admin.estructura.dao.ArchiveFlds flds = arch.getFldsDef();

			//comprobamos si incluimos los campos de sicres3
			isLibroSicres3 = isLibroSicres3(flds);
			//comprobamos si se debe añadir la cota de campos reservados
			hasReservedFields = hasReservedFields(flds);

			if (!isLibroSicres3 || !hasReservedFields) {

				//Obtenemos la definicion del libro
				DefinicionLibroRegistro defLibro = getDefinicionLibroRegistro(arch.getType());

				//obtenemos los campos del libro que estamos tratando
				archiveFlds = getArchiveFields(flds);

				//Validamos si el libro tiene todos los campos (Ref. Expediene y Fecha de Documento)
				List fieldsAddModifRefExpedienteOrFechaDocumento = validarCamposLibro(idLibro, archiveFlds, arch.getType(), defLibro);

				if (!isLibroSicres3) {
					//añadimos la definición de los campos de sicres3
					DefinicionLibroSicres3Utils.addSicres3Fields(archiveFlds);
				}

				if (!hasReservedFields) {
					//añadimos la definición de la cota de campos reservados, desde FLD500 hasta FLD1000
					DefinicionLibroSicres3Utils.addAditionalReservedFields(archiveFlds);
				}

				flds = getArchiveFields(archiveFlds);

				// En la información de actualización van los valores existentes hasta el momento más los nuevos campos añadidos
				ArchiveUpdInfo updInfo = ISicresAdminObjectFactory
											.createArchiveUpdInfo(
													arch.getName(),
													arch.getRemarks(),
													arch.getAdminUserId(),
													arch.isFtsInContents(),
													flds,
													arch.getIdxsDef(),
													arch.getMiscDef());

				//comprobamos si el archivador ya tiene registros
				if(arch.existsFdrsInArch(entidad)){
					ArrayList camposNuevos = new ArrayList();

					// Añadimos al array de campos nuevos los campos de ref.
					// expediente y fecha documento, si estos han sido
					// modificados o creados
					camposNuevos.addAll(fieldsAddModifRefExpedienteOrFechaDocumento);

					//si es asi, añadimos los nuevos campos, como campos nuevos del archivador, para que actualice los datos
					camposNuevos.addAll(DefinicionLibroSicres3Utils
							.getCamposNuevosSicres3(!isLibroSicres3,
									!hasReservedFields));

					//setemos los valores con los nuevos campos en el objeto
					updInfo.setUpdateFlds(camposNuevos, null, false);
				}

				//Proceso de actualización
				boolean recrearConstraintsYFormatos = arch.updateCheckArchiveDeletion(updInfo, entidad);

				if (recrearConstraintsYFormatos) {
					// Todo esto debería ir dentro del método updateArchWithoutFdrs de la clase ArchiveImpl, el problema es que se necesitan clases
					// del proyecto de ISicresAdmin-Core y no se puede referenciar desde el proyecto ISicresAdmin-Utils porque se entraría en una
					// referencia circular, por lo que antes de poder mejorar esto es necesario analizar cómo poder refactorizar esto adecuadamente.

					//recrea todos los constraints y formatos
					recrearConstraintsYFormatos(idLibro, defLibro, entidad);
				}else{
					//añadir los nuevos formatos con sicres3
					formatos(idLibro, defLibro, entidad);
				}
			}
		} catch (ISicresRPAdminDAOException rpaE){
			logger.error("Error actualizando libro a SICRES3", rpaE);
			throw rpaE;
		} catch (Exception e) {
			logger.error("Error actualizando libro a SICRES3",e);
			throw new ISicresRPAdminDAOException(
					ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}

		return;
	}

	/**
	 * Método que valida que el archivador contenga los campos Fecha del Documento y Ref. Expediente (Este solo en libros de Entrada)
	 *
	 * @param archiveFlds - Lista de campos del libro
	 * @param typeBook - Tipo de libro (1-Entrada / 2-Salida)
	 * @param defLibro - Definición del libro
	 *
	 * @return {@link List} - Listado de ids con los campos modificados/creados
	 *
	 * @throws Exception
	 * @throws ISicresRPAdminDAOException
	 * @throws ISicresAdminEstructuraException
	 */
	private static List validarCamposLibro(int idLibro,
			ArchiveFlds archiveFlds, int typeBook,
			DefinicionLibroRegistro defLibro) throws Exception,
			ISicresRPAdminDAOException, ISicresAdminEstructuraException {
		// Array con los ids de los campos modificados o creados
		List result = new ArrayList();

		// verificamos que campo existe
		ArchiveFlds camposByDefault = defLibro.getBookDefinition("")
				.getFldsDef();

		ArchiveFld campo = null;

		// Verificamos tipo de libro
		if (typeBook == DefinicionLibroRegistro.LIBRO_ENTRADA) {

			// Libro de Entrada
			DefinicionLibroEntrada definicionLibroEntrada = new DefinicionLibroEntrada();

			// Validamos el campo
			Integer fieldId = validateFieldRefExpediente(idLibro, archiveFlds,
					camposByDefault, definicionLibroEntrada);
			if (fieldId != null) {
				// añadimos al array de campos modificados el id del campo
				result.add(fieldId);
			}

			// Comprobamos el campo Fecha de Documento - ID: 20 - FLD20
			campo = getFieldById(archiveFlds,
					definicionLibroEntrada.FECHA_DOCUMENTO);
			if (!validateIfExistField(camposByDefault, campo)) {
				definicionLibroEntrada.getFieldFechaDocumento(archiveFlds);
				// añadimos al array de campos modificados el id del campo
				result.add(new Integer(definicionLibroEntrada.FECHA_DOCUMENTO));
			}

		} else {
			// Libro de Salida
			DefinicionLibroSalida definicionLibroSalida = new DefinicionLibroSalida();

			// Comprobamos el campo Fecha de Documento - ID: 15 - FLD15
			campo = getFieldById(archiveFlds,
					definicionLibroSalida.FECHA_DOCUMENTO);
			if (!validateIfExistField(camposByDefault, campo)) {
				definicionLibroSalida.getFieldFechaDocumento(archiveFlds);
				// añadimos al array de campos modificados el id del campo
				result.add(new Integer(definicionLibroSalida.FECHA_DOCUMENTO));
			}
		}

		return result;
	}

	public static boolean validarCampoRefExpediente(ArchiveFlds archiveFlds,
			DefinicionLibroRegistro defLibro, int typeBook) throws Exception{
		boolean result = false;

		//verificamos que campo existe
		ArchiveFlds camposByDefault = defLibro.getBookDefinition("").getFldsDef();

		//Verificamos tipo de libro
		if(typeBook == DefinicionLibroRegistro.LIBRO_ENTRADA){
			// Comprobamos el campo Referencia de Expediente - ID: 19 - FLD19
			ArchiveFld campo = getFieldById(archiveFlds, 19);

			// Se comprueba si el campo tiene el mismo nombre, id, ... y si mide 50
			// caracteres
			if ((campo != null)
					&& (StringUtils.equals(campo.getColName(), camposByDefault
							.getFldDefById(campo.getId()).getColName()))
					&& (StringUtils.equals(campo.getName(), camposByDefault
							.getFldDefById(campo.getId()).getName()))
					&& (campo.getLen() == 50)// Se compara con 50 caracteres por
												// las versiones anteriores
					&& (campo.getType() == camposByDefault.getFldDefById(
							campo.getId()).getType())) {
				// en este caso actualizamos el campo existente a una longitud de 80
				// caracteres para adaptarlo a intercambio registral
				archiveFlds.getFldDefById(19).setLen(80);
			} else {
				// sino se realizan el resto de validaciones
				if (!validateIfExistField(camposByDefault, campo)) {
					result = true;
				}
			}
		}

		return result;
	}


	public static boolean validarCampoFechaDocumento(ArchiveFlds archiveFlds,
			DefinicionLibroRegistro defLibro, int typeBook) throws Exception {
		boolean result = false;

		//verificamos que campo existe
		ArchiveFlds camposByDefault = defLibro.getBookDefinition("").getFldsDef();

		ArchiveFld campo = null;

		//Verificamos tipo de libro
		if(typeBook == DefinicionLibroRegistro.LIBRO_ENTRADA){

			//Libro de Entrada
			DefinicionLibroEntrada definicionLibroEntrada = new DefinicionLibroEntrada();

			//Comprobamos el campo Fecha de Documento - ID: 20 - FLD20
			campo = getFieldById(archiveFlds, 20);
			if(!validateIfExistField(camposByDefault, campo)){
				result = true;
			}
		}else{
			//Libro de Salida
			DefinicionLibroSalida definicionLibroSalida = new DefinicionLibroSalida();
			//Comprobamos el campo Fecha de Documento - ID: 15 - FLD15
			campo = getFieldById(archiveFlds, 15);
			if(!validateIfExistField(camposByDefault, campo)){
				result = true;
			}
		}

		return result;
	}

	/**
	 * Método que verifica si el campo Ref. Expediente existe y sino lo generamos en el archivador
	 *
	 * @param archiveFlds - Array de campos del libro
	 * @param camposByDefault - Campos por defecto para un libro
	 * @param definicionLibroEntrada - Definicion del archivador a crear/actualizar
	 *
	 * @return {@link Interger} - Ids del campo modificado/creado
	 *
	 * @throws Exception
	 * @throws ISicresRPAdminDAOException
	 * @throws ISicresAdminEstructuraException
	 */
	private static Integer validateFieldRefExpediente(int idLibro,
			ArchiveFlds archiveFlds, ArchiveFlds camposByDefault,
			DefinicionLibroEntrada definicionLibroEntrada) throws Exception,
			ISicresRPAdminDAOException, ISicresAdminEstructuraException {

		Integer result = null;

		// Comprobamos el campo Referencia de Expediente - ID: 19 - FLD19
		ArchiveFld campo = getFieldById(archiveFlds,
				definicionLibroEntrada.REFERENCIA_EXPEDIENTE);

		// Se comprueba si el campo tiene el mismo nombre, id, ... y si mide 50
		// caracteres
		if ((campo != null)
				&& (StringUtils.equals(campo.getColName(), camposByDefault
						.getFldDefById(campo.getId()).getColName()))
				&& (StringUtils.equals(campo.getName(), camposByDefault
						.getFldDefById(campo.getId()).getName()))
				&& (campo.getLen() == 50)// Se compara con 50 caracteres por
											// las versiones anteriores
				&& (campo.getType() == camposByDefault.getFldDefById(
						campo.getId()).getType())) {
			// en este caso actualizamos el campo existente a una longitud de 80
			// caracteres para adaptarlo a intercambio registral
			archiveFlds.getFldDefById(campo.getId()).setLen(80);

			// actualizamos el tamaño del campo en BBDD
			definicionLibroEntrada.updateFieldRefExpediente(idLibro);

		} else {
			// sino se realizan el resto de validaciones
			if (!validateIfExistField(camposByDefault, campo)) {
				definicionLibroEntrada.getFieldRefExpediente(archiveFlds);
				// añadimos al resultado el id del campo creado
				result = new Integer(
						definicionLibroEntrada.REFERENCIA_EXPEDIENTE);
			}
		}

		return result;
	}

	/**
	 * Método que comprueba si el campo existe en el libro
	 *
	 * @param camposByDefault - Campos por defecto para el libro
	 * @param campo - Campo a verificar
	 *
	 * @return TRUE - El campo ya existe / False - El campo indicado no existe
	 *
	 * @throws Exception
	 * @throws ISicresRPAdminDAOException
	 * @throws ISicresAdminEstructuraException
	 */
	private static boolean validateIfExistField(ArchiveFlds camposByDefault,
			ArchiveFld campo) throws Exception, ISicresRPAdminDAOException,
			ISicresAdminEstructuraException {
		boolean result = false;

		// comprobamos si el campo es distinto de nulo
		if (campo != null) {
			// validamos si el campo indicado es igual al por defecto del libro
			if (!validateEqualsFields(campo,
					camposByDefault.getFldDefById(campo.getId()))) {
				StringBuffer sb = new StringBuffer();
				sb.append("El libro ya tiene el campo con ID[")
						.append(campo.getId()).append("] creado");
				logger.error(sb.toString());
				throw new ISicresRPAdminDAOException(
						ISicresRPAdminDAOException.ERROR_UPDATE_BOOK_SICRES3);
			}
			result = true;
		}

		return result;
	}


	/**
	 * Método que obtiene la información del campo según su ID
	 * @param flds - Colección de campos
	 * @param fldId - Id del campo a buscar
	 *
	 * @return {@link ArchiveFld} Información del campo buscado
	 *
	 * @throws Exception
	 */
	private static ArchiveFld getFieldById(ArchiveFlds flds, int fldId) throws Exception {

		ArchiveFld result = null;

		try {
			//buscamos el campo
			result = flds.getFldDefById(fldId);
		} catch (IeciTdException e) {
			//Si el campo no es encontrado se devuelve una excepción por tanto la capturamos
			if (logger.isDebugEnabled()) {
				StringBuffer sb = new StringBuffer();
				sb.append("No se ha encontrado el campo [").append(fldId)
						.append("]");
				logger.debug(sb.toString(), e);
			}
		}
		return result;
	}

	/**
	 * Método que verifica si ambos campos pasados como paramétros son iguales
	 * @param campoFld - Campo a comparar
	 * @param campoDefinicionLibroDefault - Campo por defecto del libro
	 * @return boolean - TRUE: los campos son iguales / FALSE: los campos no son iguales
	 */
	private static boolean validateEqualsFields(ArchiveFld campoFld,
			ArchiveFld campoDefinicionLibroDefault) {
		boolean result = false;

		// comprobamos el nombre de columna, el nombre del campo, el tamaño y el
		// tipo de campo si todo ello es idéntico consideramos el mismo campo
		if ((StringUtils.equals(campoFld.getColName(),
				campoDefinicionLibroDefault.getColName()))
				&& (StringUtils.equals(campoFld.getName(),
						campoDefinicionLibroDefault.getName()))
				&& (campoFld.getLen() == campoDefinicionLibroDefault.getLen())
				&& (campoFld.getType() == campoDefinicionLibroDefault.getType())) {
			result = true;
		}

		return result;
	}

	/**
	 * Método que actualiza el formato del archivador
	 * @param idLibro
	 * @param defLibro
	 * @param entidad
	 * @throws ISicresRPAdminDAOException
	 */
	private static void recrearConstraintsYFormatos (int idLibro, DefinicionLibroRegistro defLibro, String entidad)
																						throws ISicresRPAdminDAOException {
		DbConnection db = new DbConnection();

		try {
			db.open(DBSessionManager.getSession());
			db.beginTransaction();
			defLibro.makeConstraints(idLibro, db);
			defLibro.makeFormats(idLibro, db, entidad);
			db.endTransaction(true);
		} catch (Exception e) {
			// Deshacer transaccion
			if (db != null && db.inTransaction()) {
				try {
					db.endTransaction(false);
				} catch (Exception e1) {
					logger.error("Problemas con rollback");
				}
			}
			logger.error("Error creando las constraints y formatos del libro");
			throw new ISicresRPAdminDAOException(
					ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		} finally {
			try {
				if (db != null && db.existConnection()){
					db.close();
				}
			} catch (Exception e) {
				logger.error("No se ha podido cerrar la conexión a la BBDD");
			}
		}
	}

	private static void formatos(int idLibro, DefinicionLibroRegistro defLibro, String entidad) throws ISicresRPAdminDAOException{
		DbConnection db = new DbConnection();

		try {
			db.open(DBSessionManager.getSession());
			db.beginTransaction();
			defLibro.makeFormats(idLibro, db, entidad);
			db.endTransaction(true);
		} catch (Exception e) {
			// Deshacer transaccion
			if (db != null && db.inTransaction()) {
				try {
					db.endTransaction(false);
				} catch (Exception e1) {
					logger.error("Problemas con rollback");
				}
			}
			logger.error("Error creando los formatos del libro");
			throw new ISicresRPAdminDAOException(
					ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		} finally {
			try {
				if (db != null && db.existConnection()){
					db.close();
				}
			} catch (Exception e) {
				logger.error("No se ha podido cerrar la conexión a la BBDD");
			}
		}
	}

	/**
	 * Método de transformación de un tipo ArchiveFlds del paquete dao al ArchiveFlds del paquete beans
	 * @param doArchiveFlds ArchiveFlds del paquete dao
	 * @return ArchiveFlds del paquete beans
	 * @throws Exception
	 */
	private static ArchiveFlds getArchiveFields(es.ieci.tecdoc.isicres.admin.estructura.dao.ArchiveFlds doArchiveFlds) throws Exception {
		ArchiveFlds archiveFlds = new ArchiveFlds();
		for(int i=0; i<doArchiveFlds.count(); i++) {
			ArchiveFld archiveFld = new ArchiveFld(doArchiveFlds.get(i).getId(),
											doArchiveFlds.get(i).getName(),
											doArchiveFlds.get(i).getType(),
											doArchiveFlds.get(i).getLen(),
											doArchiveFlds.get(i).isNullable(),
											doArchiveFlds.get(i).getColName(),
											doArchiveFlds.get(i).isDoc(),
											doArchiveFlds.get(i).isMult(),
											doArchiveFlds.get(i).getRemarks());
			archiveFlds.addFld(archiveFld);
		}
		return archiveFlds;
	}

	/**
	 * Método de transformación de un tipo ArchiveFlds del paquete beans al ArchiveFlds del paquete dao
	 * @param doArchiveFlds ArchiveFlds del paquete beans
	 * @return ArchiveFlds del paquete dao
	 * @throws Exception
	 */
	private static es.ieci.tecdoc.isicres.admin.estructura.dao.ArchiveFlds getArchiveFields(ArchiveFlds doArchiveFlds) throws Exception {
		es.ieci.tecdoc.isicres.admin.estructura.dao.ArchiveFlds archiveFlds = ISicresAdminObjectFactory.createArchiveFlds();
		for(int i=0; i<doArchiveFlds.count(); i++) {
			es.ieci.tecdoc.isicres.admin.estructura.dao.ArchiveFld archiveFld = new ArchiveFldImpl(doArchiveFlds.get(i).getId(),
											doArchiveFlds.get(i).getName(),
											doArchiveFlds.get(i).getType(),
											doArchiveFlds.get(i).getLen(),
											doArchiveFlds.get(i).isNullable(),
											doArchiveFlds.get(i).getColName(),
											doArchiveFlds.get(i).isDoc(),
											doArchiveFlds.get(i).isMult(),
											doArchiveFlds.get(i).getRemarks());
			archiveFlds.addFld(archiveFld);
		}
		return archiveFlds;
	}

	public static int crearLibro(IDocArchHDRImpl archivador, SicresLibroEstadoImpl libro,
			String entidad) throws ISicresRPAdminDAOException {
		DbConnection db = new DbConnection();
		SicresLibroEstadoDatos libroDAO = null;
		int archiveId = 0;
		try {

			libroDAO = new SicresLibroEstadoDatos(libro);
			libroDAO.setId(IdsGenerator.generateNextId(
					IdsGenerator.SCR_REGSTATE, entidad));
			DefinicionLibroRegistro defLibro = getDefinicionLibroRegistro(archivador.getType());
			Archive archivadorDTO = defLibro.getBookDefinition(archivador.getName());

			/* NOTA:
			 * ParentId: Identificador del directorio padre (0 si raíz). Tabla idocdatnode
			 * Por defecto se esta declarando la variable y no se llega inizializar, por lo que
			 * el valor de esta variable llega a 0. Se deberia identificar el id del directorio y
			 * asociarlo a la variable parentId.
			 */
			archivadorDTO.setParentId(0);
			//**************************//
			ISicresAdminEstructuraService oServicio = new ISicresAdminEstructuraAdapter();
			archiveId = oServicio.crearArchivador(archivadorDTO, entidad);

			db.open(DBSessionManager.getSession());
			db.beginTransaction();
			defLibro.makeConstraints(archiveId, db);
			defLibro.makeFormats(archiveId, db, entidad);
			libroDAO.setIdArchReg(archiveId);
			libroDAO.add(db);
			db.endTransaction(true);
		} catch (Exception e) {
			// Deshacer transaccion
			if (db != null && db.inTransaction())
				try {
					db.endTransaction(false);
				} catch (Exception e1) {
					logger.error("Problemas con rollback");
				}

			if(archiveId != 0) {
				ISicresAdminEstructuraService oServicio;
				try {
					oServicio = new ISicresAdminEstructuraAdapter();
					oServicio.eliminarArchivador(0, archiveId, entidad);
				} catch (ISicresAdminException e1) {
					logger.error("Ocurrió un error tras crear el archivador " + archiveId + " y no se pudo eliminar, deberá hacerlo manualmente");
				}
			}

			logger.error("Error creando el libro");
			throw new ISicresRPAdminDAOException(
					ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		} finally {
			try {
				if (db != null && db.existConnection()){
					db.close();
				}
			} catch (Exception e) {
				logger.error("No se ha podido cerrar la conexión a la BBDD");
			}
		}
		return libroDAO.getIdArchReg();
	}

	public static void editarLibro(SicresLibroEstadoImpl libro,
			IDocArchHDRImpl archivador, String entidad)
			throws ISicresRPAdminDAOException {
		DbConnection db = new DbConnection();
		try {
			db.open(DBSessionManager.getSession());
			db.beginTransaction();
			if (archivador != null) {
				IDocArchHDRDatos archivadorDAO = new IDocArchHDRDatos(
						archivador);
				archivadorDAO.update(db);
			}
			SicresLibroEstadoDatos libroDAO = new SicresLibroEstadoDatos(libro);
			libroDAO.update(db);
			db.endTransaction(true);
		} catch (Exception e) {
			if (db != null && db.inTransaction())
				try {
					db.endTransaction(false);
				} catch (Exception e1) {
					logger.error("Problemas con rollback");
				}
			logger.error("Error editando el libro");
			throw new ISicresRPAdminDAOException(
					ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		} finally {
			try {
				if (db != null && db.existConnection()){
					db.close();
				}
			} catch (Exception e) {
				logger.error("No se ha podido cerrar la conexión a la BBDD");
			}
		}
		return;
	}

	public static void eliminarLibro(int idLibro, String entidad)
			throws ISicresRPAdminDAOException {
		DbConnection db = new DbConnection();
		try {
			ISicresAdminEstructuraService oServicio = new ISicresAdminEstructuraAdapter();
			oServicio.eliminarArchivador(0, idLibro, entidad);

			db.open(DBSessionManager.getSession());
			db.beginTransaction();

			IUserObjPermDatos.deleteObjeto(idLibro, db);
			SicresLibroOficinaDatos.deleteLibro(idLibro, db);
			SicresLibroInformesDatos.deleteByLibro(idLibro, db);
			SicresLibroEstadoDatos libroDAO = new SicresLibroEstadoDatos();
			libroDAO.setIdArchReg(idLibro);
			libroDAO.delete(db);

			db.endTransaction(true);
		} catch (Exception e) {
			if (db != null && db.inTransaction())
				try {
					db.endTransaction(false);
				} catch (Exception e1) {
					logger.error("Problemas con rollback");
				}
			logger.error("Error eliminando el libro");
			throw new ISicresRPAdminDAOException(
					ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		} finally {
			try {
				if (db != null && db.existConnection()){
					db.close();
				}
			} catch (Exception e) {
				logger.error("No se ha podido cerrar la conexión a la BBDD");
			}
		}
		return;
	}

	public static IVolArchListImpl getLista(int bookId, String entidad)
			throws ISicresRPAdminDAOException {
		DbConnection db = new DbConnection();
		IVolArchListDatos lista = new IVolArchListDatos();
		try {
			db.open(DBSessionManager.getSession());
			lista.load(bookId, db);
		} catch (Exception e) {
			logger.error("Error obteniendo lista");
		} finally {
			try {
				if (db != null && db.existConnection()){
					db.close();
				}
			} catch (Exception e) {
				logger.error("No se ha podido cerrar la conexión a la BBDD");
			}
		}
		return lista;
	}

	public static void asociarListaALibro(IVolArchListImpl lista, String entidad)
			throws ISicresRPAdminDAOException {
		DbConnection db = new DbConnection();
		try {
			db.open(DBSessionManager.getSession());
			IVolArchListDatos listaDAO = new IVolArchListDatos();

			try {
				//cargamos los datos del libro
				listaDAO.load(lista.getArchId(), db);
				//se actualiza la lista de volumen asociada al libro
				new IVolArchListDatos(lista).update(db);
			} catch (ISicresAdminDAOException e) {
				// Añadimos la lista del archivador siempre que la lista sea
				// distinto de 0
				if(lista.getListId()!=0){
					new IVolArchListDatos(lista).add(db);
				}
			}
		} catch (Exception e) {
			logger.error("Error obteniendo lista");
			throw new ISicresRPAdminDAOException(
					ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		} finally {
			try {
				if (db != null && db.existConnection()){
					db.close();
				}
			} catch (Exception e) {
				logger.error("No se ha podido cerrar la conexión a la BBDD");
			}
		}
		return;
	}

	public static IUserObjsPermsImpl obtenerPermisos(int bookId, int tipo,
			String entidad) throws ISicresRPAdminDAOException {
		DbConnection db = new DbConnection();
		IUserObjsPermsImpl permisos = null;
		try {
			db.open(DBSessionManager.getSession());
			permisos = IUserObjPermDatos.getPermisos(bookId, tipo, db);
		} catch (Exception e) {
			logger.error("Error obteniendo permisos");
			throw new ISicresRPAdminDAOException(
					ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		} finally {
			try {
				if (db != null && db.existConnection()){
					db.close();
				}
			} catch (Exception e) {
				logger.error("No se ha podido cerrar la conexión a la BBDD");
			}
		}
		return permisos;
	}

	public static SicresContadoresOficinasImpl obtenerContadoresOficinas(
			int anyo, int tipo, String entidad) throws ISicresRPAdminDAOException {
		DbConnection db = new DbConnection();
		SicresContadoresOficinasImpl contadores = null;
		try {
			db.open(DBSessionManager.getSession());
			contadores = SicresContadorOficinaDatos.getContadores(anyo, tipo,
					db);
		} catch (Exception e) {
			logger.error("Error obteniendo contadores de oficinas");
			throw new ISicresRPAdminDAOException(
					ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		} finally {
			try {
				if (db != null && db.existConnection()){
					db.close();
				}
			} catch (Exception e) {
				logger.error("No se ha podido cerrar la conexión a la BBDD");
			}
		}
		return contadores;
	}

	public static void editarContadoresOficinas(
			SicresContadoresOficinasImpl contadores, String entidad)
			throws ISicresRPAdminDAOException {
		DbConnection db = new DbConnection();
		try {
			db.open(DBSessionManager.getSession());
			db.beginTransaction();
			for (int i = 0; i < contadores.count(); i++) {
				SicresContadorOficinaDatos contadorDAO = new SicresContadorOficinaDatos(
						contadores.get(i));
				contadorDAO.delete(db);
				contadorDAO.add(db);
			}
			db.endTransaction(true);
		} catch (Exception e) {
			if (db != null && db.inTransaction())
				try {
					db.endTransaction(false);
				} catch (Exception e1) {
					logger.error("Problemas con rollback");
				}
			logger.error("Error editando el contadorCentral");
			throw new ISicresRPAdminDAOException(
					ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		} finally {
			try {
				if (db != null && db.existConnection()){
					db.close();
				}
			} catch (Exception e) {
				logger.error("No se ha podido cerrar la conexión a la BBDD");
			}
		}
		return;
	}

	public static SicresContadorCentralImpl obtenerContadorCentral(int anyo,
			int tipo, String entidad) throws ISicresRPAdminDAOException {
		DbConnection db = new DbConnection();
		SicresContadorCentralDatos contador = new SicresContadorCentralDatos();
		try {
			db.open(DBSessionManager.getSession());
			contador.load(tipo, anyo, db);
		} catch (Exception e) {
			logger.error("Error obteniendo contador central");
			throw new ISicresRPAdminDAOException(
					ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		} finally {
			try {
				if (db != null && db.existConnection()){
					db.close();
				}
			} catch (Exception e) {
				logger.error("No se ha podido cerrar la conexión a la BBDD");
			}
		}
		return contador;
	}

	public static void editarContadorCentral(
			SicresContadorCentralImpl contador, String entidad)
			throws ISicresRPAdminDAOException {
		DbConnection db = new DbConnection();
		try {
			db.open(DBSessionManager.getSession());
			SicresContadorCentralDatos contadorDAO = new SicresContadorCentralDatos(
					contador);
			contadorDAO.delete(db);
			contadorDAO.add(db);
		} catch (Exception e) {
			logger.error("Error editando el contadorCentral");
			throw new ISicresRPAdminDAOException(
					ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		} finally {
			try {
				if (db != null && db.existConnection()){
					db.close();
				}
			} catch (Exception e) {
				logger.error("No se ha podido cerrar la conexión a la BBDD");
			}
		}
		return;
	}

	public static SicresLibrosOficinasImpl obtenerOficinas(int bookId,
			String entidad) throws ISicresRPAdminDAOException {
		DbConnection db = new DbConnection();
		SicresLibrosOficinasImpl oficinas = null;
		try {
			db.open(DBSessionManager.getSession());
			oficinas = SicresLibroOficinaDatos.getOficinas(bookId, db);

		} catch (Exception e) {
			logger.error("Error obteniendo oficinas");
			throw new ISicresRPAdminDAOException(
					ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		} finally {
			try {
				if (db != null && db.existConnection()){
					db.close();
				}
			} catch (Exception e) {
				logger.error("No se ha podido cerrar la conexión a la BBDD");
			}
		}
		return oficinas;
	}

	public static void asociarOficinasALibro(
			SicresLibrosOficinasImpl libroOficinas, String entidad)
			throws ISicresRPAdminDAOException {
		DbConnection db = new DbConnection();

		try {
			db.open(DBSessionManager.getSession());
			db.beginTransaction();
			for (int i = 0; i < libroOficinas.count(); i++) {
				SicresLibroOficinaImpl libroOficina = libroOficinas.get(i);
				libroOficina.setId(IdsGenerator.generateNextId(
						IdsGenerator.SCR_BOOKOFIC, entidad));

				// Traducir código departamento por oficina.
				// SicresOficinaImpl oficina = RPAdminOficinaManager
				// .getOficinaById(libroOficina.getIdOfic(), entidad);
				// libroOficina.setIdOfic(oficina.getId());

				new SicresLibroOficinaDatos(libroOficina).add(db);
			}
			db.endTransaction(true);
		} catch (Exception e) {
			if (db != null && db.inTransaction())
				try {
					db.endTransaction(false);
				} catch (Exception e1) {
					logger.error("Problemas con rollback");
				}
			logger.error("Error asociando oficina");
			throw new ISicresRPAdminDAOException(
					ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		} finally {
			try {
				if (db != null && db.existConnection()){
					db.close();
				}
			} catch (Exception e) {
				logger.error("No se ha podido cerrar la conexión a la BBDD");
			}
		}
	}

	public static void desasociarOficinasALibro(
			SicresLibrosOficinasImpl libroOficinas, String entidad)
			throws ISicresRPAdminDAOException {
		DbConnection db = new DbConnection();

		try {
			db.open(DBSessionManager.getSession());
			db.beginTransaction();
			for (int iOfic = 0; iOfic < libroOficinas.count(); iOfic++) {
				SicresLibroOficinaImpl libroOficina = libroOficinas.get(iOfic);

				// Borrar sus permisos asociados
				IUserObjPermDatos.delete(IUserObjPermImpl.TIPO_DEPARTAMENTO,
						libroOficina.getDeptId(), libroOficina.getIdBook(), db);

				new SicresLibroOficinaDatos(libroOficina).delete(db);
			}
			db.endTransaction(true);
		} catch (Exception e) {
			if (db != null && db.inTransaction())
				try {
					db.endTransaction(false);
				} catch (Exception e1) {
					logger.error("Problemas con rollback");
				}
			logger.error("Error desasociando oficina");
			throw new ISicresRPAdminDAOException(
					ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		} finally {
			try {
				if (db != null && db.existConnection()){
					db.close();
				}
			} catch (Exception e) {
				logger.error("No se ha podido cerrar la conexión a la BBDD");
			}
		}
	}

	public static void actualizarNumeracionOficinaAsociadaALibro(int idBook,
				int idOfic, int numeration, String entidad) throws ISicresRPAdminDAOException {
		DbConnection db = new DbConnection();
		try {
			db.open(DBSessionManager.getSession());
			db.beginTransaction();
			SicresLibroOficinaImpl libroOficina = new SicresLibroOficinaImpl();
			libroOficina.setIdBook(idBook);
			libroOficina.setIdOfic(idOfic);
			libroOficina.setNumeration(numeration);
			new SicresLibroOficinaDatos(libroOficina).update(db);
			db.endTransaction(true);
		} catch (Exception e) {
			if (db != null && db.inTransaction())
				try {
					db.endTransaction(false);
				} catch (Exception e1) {
					logger.error("Problemas con rollback");
				}
			logger.error("Error actualizando numeracion oficinas asociadas a libro");
			throw new ISicresRPAdminDAOException(ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		} finally {
			try {
				if (db != null && db.existConnection()){
					db.close();
				}
			} catch (Exception e) {
				logger.error("No se ha podido cerrar la conexión a la BBDD");
			}
		}
	}

	public static void modificarPermisos(IUserObjsPermsImpl permisosDelete,
			IUserObjsPermsImpl permisosAdd, String entidad)
			throws ISicresRPAdminDAOException {
		DbConnection db = new DbConnection();

		try {
			db.open(DBSessionManager.getSession());
			db.beginTransaction();
			for (int i = 0; i < permisosDelete.count(); i++) {
				IUserObjPermImpl permiso = permisosDelete.get(i);
				IUserObjPermDatos.delete(permiso.getDstType(), permiso
						.getDstId(), permiso.getObjId(), db);
			}

			for (int i = 0; i < permisosAdd.count(); i++) {
				IUserObjPermImpl permiso = permisosAdd.get(i);
				new IUserObjPermDatos(permiso).add(db);
			}
			db.endTransaction(true);
		} catch (Exception e) {
			if (db != null && db.inTransaction())
				try {
					db.endTransaction(false);
				} catch (Exception e1) {
					logger.error("Problemas con rollback");
				}
			logger.error("Error editando el usuario");
			throw new ISicresRPAdminDAOException(
					ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		} finally {
			try {
				if (db != null && db.existConnection()){
					db.close();
				}
			} catch (Exception e) {
				logger.error("No se ha podido cerrar la conexión a la BBDD");
			}
		}
	}

	public static void configureFiltros(int tipoFiltro, int tipoLibro, int idLibro, int idUserOfic, FiltrosImpl filtros, String entidad)
		throws ISicresRPAdminDAOException {
			DbConnection db = new DbConnection();
			StringBuffer sbWhere = new StringBuffer();
			StringBuffer sbVista= new StringBuffer();
			try {
//				if(tipoFiltro==FiltroImpl.TIPO_FILTRO_OFICINAS) { //Traducir idDept por idOfic
//					//TODO ¿Quitar?
//					SicresOficinaImpl oficina = RPAdminOficinaManager.getOficinaByDeptId(idUserOfic, entidad);
//					idUserOfic = oficina.getId();
//				}

				db.open(DBSessionManager.getSession());
				db.beginTransaction();

				SicresUserFilterDatos filtroDAO = new SicresUserFilterDatos();

				//Si no hay filtros borrarlo.
				if(filtros.count()==0) {
					filtroDAO.setTypeObj(tipoFiltro);
					filtroDAO.setIdArch(idLibro);
					filtroDAO.setIdUser(idUserOfic);
					filtroDAO.delete(db);
				} else {
					for(int i = 0; i<filtros.count();i++) {
						FiltroImpl filtro = filtros.get(i);
						String tipo = DefinicionFilterField.idToType(tipoLibro, filtro.getCampo());
						DefinicionFilterField definicion = getDefinicionFiltro(tipo, tipoLibro, entidad);
						String encodedWhere = "";
						String encodedFilter = "";
						try {
							encodedWhere = definicion.encodeWhere(filtro, entidad);
							encodedFilter = definicion.encodeFilter(filtro);
						} catch (SQLException e) {
							logger.error("Aplicando el filtro", e);
						}
						sbWhere.append(encodedWhere);
						sbVista.append(encodedFilter);
					}

					try{
						filtroDAO.load(idLibro, idUserOfic, tipoFiltro, db);
						filtroDAO.setFilterDef(sbWhere.append(" $$").append(sbVista).toString());
						filtroDAO.update(db);
					} catch (Exception e) {
						filtroDAO.setFilterDef(sbWhere.append(" $$").append(sbVista).toString());
						filtroDAO.setIdArch(idLibro);
						filtroDAO.setIdUser(idUserOfic);
						filtroDAO.setTypeObj(tipoFiltro);
						filtroDAO.add(db);
					}
				}
				db.endTransaction(true);
			} catch (Exception e) {
				if (db != null && db.inTransaction())
					try {
						db.endTransaction(false);
					} catch (Exception e1) {
						logger.error("Problemas con rollback");
					}
				logger.error("Error configurando filtros");
				throw new ISicresRPAdminDAOException(
						ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
			} finally {
				try {
					if (db != null && db.existConnection()){
						db.close();
					}
				} catch (Exception e) {
					logger.error("No se ha podido cerrar la conexión a la BBDD");
				}
			}
	}

	public static FiltrosImpl obtenerFiltros(int tipoFiltro, int tipoLibro, int idLibro, int idUserOfic, String entidad)
	throws ISicresRPAdminDAOException {

		DbConnection db = new DbConnection();
		FiltrosImpl filtros = new FiltrosImpl();
		IDocArchHDRDatos arch = new IDocArchHDRDatos();
		SicresUserFilterDatos filtro = new SicresUserFilterDatos();
		try {
//			if(tipoFiltro==FiltroImpl.TIPO_FILTRO_OFICINAS) { //Traducir idDept por idOfic
//				//TODO ¿Quitar?
//				SicresOficinaImpl oficina = RPAdminOficinaManager.getOficinaByDeptId(idUserOfic, entidad);
//				idUserOfic = oficina.getId();
//			}
			db.open(DBSessionManager.getSession());
			arch.load(idLibro, db);
			try {
				filtro.load(idLibro, idUserOfic, tipoFiltro, db);
			} catch (Exception e) {
				return filtros;
			}
			String[] filtroWhereVista = filtro.getFilterDef().split("\\$\\$");
			String[] clausulas = filtroWhereVista[1].split("\\$");
			for(int i=0;i<clausulas.length;i++) {
				String[] cadenas = clausulas[i].split("\\|");
				ArchiveFld campo = getCampo(arch.getType(), cadenas[0]);
				String tipo = DefinicionFilterField.idToType(arch.getType(), campo.getId());
				filtros.add(getDefinicionFiltro(tipo, arch.getType(), entidad).decode(cadenas));
			}
		} catch (Exception e) {
			logger.error("Error obteniendo filtros");
			throw new ISicresRPAdminDAOException(
					ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		} finally {
			try {
				if (db != null && db.existConnection()){
					db.close();
				}
			} catch (Exception e) {
				logger.error("No se ha podido cerrar la conexión a la BBDD");
			}
		}
		return filtros;
	}

	public static Listas obtenerListas(String entidad)
			throws ISicresRPAdminDAOException {
		Listas listas = null;
		try {
			ISicresAdminEstructuraService oServicio = new ISicresAdminEstructuraAdapter();
			listas = oServicio.getListas(entidad);
		} catch (Exception e) {
			logger.error("Error obteniendo listas de volúmenes");
			throw new ISicresRPAdminDAOException(
					ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
		return listas;
	}

	public static int[] getOperadores(String tipo, String entidad) {
		return getDefinicionFiltro(tipo, -1, entidad).getOperators();
	}

	public static DefinicionLibroRegistro getDefinicionLibroRegistro(int tipo) {
		if (tipo == DefinicionLibroRegistro.LIBRO_ENTRADA)
			return new DefinicionLibroEntrada();
		else
			return new DefinicionLibroSalida();
	}

	public static DefinicionFilterField getDefinicionFiltro(String tipo,
			int tipoLibro, String entidad) {
		if (FiltroImpl.TIPO_STRING.equals(tipo))
			return new DefinicionFilterString(tipoLibro);
		else if (FiltroImpl.TIPO_DATE.equals(tipo))
			return new DefinicionFilterFecha(tipoLibro);
		else if (FiltroImpl.TIPO_COMBO.equals(tipo))
			return new DefinicionFilterCombo(tipoLibro);
		else if (FiltroImpl.TIPO_OFICINAS.equals(tipo))
			return new DefinicionFilterOficina(tipoLibro, entidad);
		else if (FiltroImpl.TIPO_UNIDADES_ADMIN.equals(tipo))
			return new DefinicionFilterUnidadAdm(tipoLibro, entidad);
		else if (FiltroImpl.TIPO_ASUNTO.equals(tipo)) {
			return new DefinicionFilterTipoAsunto(tipoLibro, entidad);
		} else {
			return null;
		}

	}

	public static ArchiveFld getCampo(int tipoLibro, int id) {
		DefinicionLibroRegistro libro = ISicresRPAdminLibroManager.getDefinicionLibroRegistro(tipoLibro);
		ArchiveFlds fields = libro.getBookDefinition("").getFldsDef();
		for(int i=0;i<fields.count();i++) {
			ArchiveFld field = fields.get(i);
			if(field.getId()==id) {
				return field;
			}
		}
		return new ArchiveFld();
	}

	public static ArchiveFld getCampo(int tipoLibro, String nombre) {
		DefinicionLibroRegistro libro = ISicresRPAdminLibroManager.getDefinicionLibroRegistro(tipoLibro);
		ArchiveFlds fields = libro.getBookDefinition("").getFldsDef();
		for(int i=0;i<fields.count();i++) {
			ArchiveFld field = fields.get(i);
			if(field.getName().equals(nombre)) {
				return field;
			}
		}
		return new ArchiveFld();
	}

}
