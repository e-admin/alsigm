package es.ieci.tecdoc.isicres.admin.core.beans.definicion;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.keys.ConfigurationKeys;
import com.ieci.tecdoc.common.utils.Configurator;

import es.ieci.tecdoc.isicres.admin.estructura.beans.ArchiveFlds;
import es.ieci.tecdoc.isicres.admin.exception.ISicresAdminEstructuraException;

/**
 * Clase de utilidad para gestionar la información de los campos específicos para SICRES3
 * @author Iecisa
 * @version $Revision$
 *
 */
public class DefinicionLibroSicres3Utils {

	public static final Logger logger = Logger
			.getLogger(DefinicionLibroSicres3Utils.class);

	public static int ADDITIONAL_RESERVED_FIELDS_LOWER_LIMIT_FIELD_ID = 500;
	public static int ADDITIONAL_RESERVED_FIELDS_UPPER_LIMIT_FIELD_ID = 1000;

	public static int SICRES3_EXPONE_FIELD_ID = 501;
	public static int SICRES3_SOLICITA_FIELD_ID = 502;
	public static int SICRES3_INVOLUCRADO_IR_FIELD_ID = 503;
	public static int SICRES3_ACOMPANIA_DOCFISREQ_FIELD_ID = 504;
	public static int SICRES3_ACOMPANIA_DOCFISCOMP_FIELD_ID = 505;
	public static int SICRES3_NO_ACOMPANIA_DOCFISNOS_FIELD_ID = 506;
	/**
	 * Añadir los campos reservados propios de producto y los campos específicos de SICRES3
	 * @param fields
	 * @throws ISicresAdminEstructuraException
	 */
	public static void addAditionalReservedAndSicres3Fields(ArchiveFlds fields) throws ISicresAdminEstructuraException{
		addAditionalReservedFields(fields);
		addSicres3Fields(fields);
	}

	/** Se reserva el intervalo entre 500 y 1000 de flds para campos propios de producto
	 * @param fields
	 * @throws ISicresAdminEstructuraException
	 */
	public static void addAditionalReservedFields(ArchiveFlds fields) throws ISicresAdminEstructuraException{

		//Limite inferior campo de reserva
		fields.add(ADDITIONAL_RESERVED_FIELDS_LOWER_LIMIT_FIELD_ID, "LimiteInferiorReserva", 2, 65535, true, false, false, "");

		//Limite superior campo de reserva
		fields.add(ADDITIONAL_RESERVED_FIELDS_UPPER_LIMIT_FIELD_ID, "LimiteSuperiorReserva", 2, 65535, true, false, false, "");
	}

	/**
	 * Añade los campos específicos de SICRES 3
	 * @param fields
	 * @throws ISicresAdminEstructuraException
	 */
	public static void addSicres3Fields (ArchiveFlds fields) throws ISicresAdminEstructuraException {
		//campos para sicres3 expone y solicita
		boolean sicres3Enabled= isSicres3Enabled();
		if (sicres3Enabled){
			fields.add(SICRES3_EXPONE_FIELD_ID, "Expone", 2, 65535, true, false, false, "");
			fields.add(SICRES3_SOLICITA_FIELD_ID, "Solicita", 2, 65535, true, false, false, "");
			fields.add(SICRES3_INVOLUCRADO_IR_FIELD_ID, "Involucrado en Interc. Registral" , 4, 0, true, false, false, "");
			fields.add(SICRES3_ACOMPANIA_DOCFISREQ_FIELD_ID, "Acompaña doc. física requerida", 4, 0, true, false, false, "");
			fields.add(SICRES3_ACOMPANIA_DOCFISCOMP_FIELD_ID, "Acompaña doc. física complementaria", 4, 0, true, false, false, "");
			fields.add(SICRES3_NO_ACOMPANIA_DOCFISNOS_FIELD_ID, "No acompaña doc. física ni otros soportes",4, 0, true, false, false, "");
		}
	}

	/**
	 * Método que devuelve un array con los ids de los campos específicos de SICRES 3, también se devuelven los ids reservados
	 * @param isLibroSicres3 - Si se desean los campos específicos de SICRES3
	 * @param hasReservedFields - Si se desean obtener los campos de reservados para la aplicación
	 *
	 * @return Listado de ids de los campos de sicres3
	 */
	public static List<Integer> getCamposNuevosSicres3(boolean isLibroSicres3 , boolean hasReservedFields){
		ArrayList<Integer> result = new ArrayList<Integer>();

		if(hasReservedFields){
			//margen de campos reservados, desde el FLD500 hasta el FLD1000
			result.add(new Integer(ADDITIONAL_RESERVED_FIELDS_LOWER_LIMIT_FIELD_ID));
			result.add(new Integer(ADDITIONAL_RESERVED_FIELDS_UPPER_LIMIT_FIELD_ID));
		}

		if(isLibroSicres3){
			//campos expecíficos de SICRES3
			result.add(new Integer(SICRES3_EXPONE_FIELD_ID));
			result.add(new Integer(SICRES3_SOLICITA_FIELD_ID));
			result.add(new Integer(SICRES3_INVOLUCRADO_IR_FIELD_ID));
			result.add(new Integer(SICRES3_ACOMPANIA_DOCFISREQ_FIELD_ID));
			result.add(new Integer(SICRES3_ACOMPANIA_DOCFISCOMP_FIELD_ID));
			result.add(new Integer(SICRES3_NO_ACOMPANIA_DOCFISNOS_FIELD_ID));
		}

		return result;
	}

	/**
	 * Chequea si está habilitado el intercambio registral, esto es, si estamos usando SICRES3
	 * @return
	 */
	public static boolean isSicres3Enabled(){
		boolean result = false;
		result = Configurator.getInstance().getPropertyBoolean(ConfigurationKeys.KEY_INTERCAMBIO_ENABLE_INTERCAMBIO_REGISTRAL);
		return result;
	}
}
