/**
 * 
 */
package ieci.tdw.ispac.audit.business.vo.enums;

import org.apache.commons.lang.enums.ValuedEnum;

/**
 * @author IECISA
 * @version $Revision$
 * 
 *          Enumerado con los tipos de eventos a auditar
 * 
 */
public class IspacEventAuditTypeEnum extends ValuedEnum {

	public static int ACCESO_APLICACION_VALUE = 1000;
	public static String ACCESO_APLICACION_NAME = "EVENTO DE ACCESO A LA APLICACION";

	public static int BUSQUEDA_VALUE = 2000;
	public static String BUSQUEDA_NAME = "BÚSQUEDA AVANZADA";

	public static int INFORME_VALUE = 3000;
	public static String INFORME_NAME = "EJECUCIÓN INFORME";

	public static int EXPEDIENTE_BAJA_VALUE = 4001;
	public static String EXPEDIENTE_BAJA_NAME = "EXPEDIENTE - BAJA";

	public static int EXPEDIENTE_ALTA_VALUE = 4002;
	public static String EXPEDIENTE_ALTA_NAME = "EXPEDIENTE - ALTA";
	
	public static int EXPEDIENTE_PAPELERA_VALUE = 4003;
	public static String EXPEDIENTE_PAPELERA_NAME = "EXPEDIENTE - ENVÍO PAPELERA";

	public static int AVISO_ALTA_VALUE = 5001;
	public static String AVISO_ALTA_NAME = "AVISO - ALTA";

	public static int AVISO_CONSULTA_VALUE = 5002;
	public static String AVISO_CONSULTA_NAME = "AVISO - CONSULTA";

	public static int AVISO_MODIFICACION_VALUE = 5003;
	public static String AVISO_MODIFICACION_NAME = "AVISO - MODIFICACION";
	
	public static int AVISO_BAJA_VALUE = 5004;
	public static String AVISO_BAJA_NAME = "AVISO - BAJA";

	public static int TRAMITE_ALTA_VALUE = 6001;
	public static String TRAMITE_ALTA_NAME = "TRAMITE - ALTA";

	public static int TRAMITE_BAJA_VALUE = 6002;
	public static String TRAMITE_BAJA_NAME = "TRAMITE - BAJA";

	public static int TRAMITE_MODIFICACION_VALUE = 6003;
	public static String TRAMITE_MODIFICACION_NAME = "TRAMITE - MODIFICACION";

	public static int TRAMITE_CONSULTA_VALUE = 6004;
	public static String TRAMITE_CONSULTA_NAME = "TRAMITE - CONSULTA";
	
	public static int DOCUMENTO_CONSULTA_VALUE = 7001;
	public static String DOCUMENTO_CONSULTA_NAME = "DOCUMENTO - CONSULTA";
	
	public static int DOCUMENTO_ALTA_VALUE = 7002;
	public static String DOCUMENTO_ALTA_NAME = "DOCUMENTO - ALTA";
	
	public static int DOCUMENTO_BAJA_VALUE = 7003;
	public static String DOCUMENTO_BAJA_NAME = "DOCUMENTO - BAJA";
	
	public static int DOCUMENTO_MODIFICACION_VALUE = 7004;
	public static String DOCUMENTO_MODIFICACION_NAME = "DOCUMENTO - MODIFICACION";
	
	public static int FICHERO_BAJA_VALUE = 7005;
	public static String FICHERO_BAJA_NAME = "FICHERO - BAJA";
	
	public static int ENTIDAD_ALTA_VALUE = 8001;
	public static String ENTIDAD_ALTA_NAME = "ENTIDAD - ALTA";
	
	public static int ENTIDAD_MODIFICACION_VALUE = 8002;
	public static String ENTIDAD_MODIFICACION_NAME = "ENTIDAD - MOFICICACION";
	
	public static int ENTIDAD_CONSULTA_VALUE = 8003;
	public static String ENTIDAD_CONSULTA_NAME = "ENTIDAD - CONSULTA";
	
	public static int ENTIDAD_BAJA_VALUE = 8004;
	public static String ENTIDAD_BAJA_NAME = "ENTIDAD - BAJA";
	
	public static int REGISTRO_DISTRIBUIDO_CONSULTA_VALUE = 9001;
	public static String REGISTRO_DISTRIBUIDO_CONSULTA_NAME = "REGISTRO DISTRIBUIDO - CONSULTA";
	
	public static int REGISTRO_DISTRIBUIDO_MODIFICACION_VALUE = 9002;
	public static String REGISTRO_DISTRIBUIDO_MODIFICACION_NAME = "REGISTRO DISTRIBUIDO - MODIFICACION";

	public static IspacEventAuditTypeEnum ACCESO_APLICACION = new IspacEventAuditTypeEnum(
			ACCESO_APLICACION_NAME, ACCESO_APLICACION_VALUE);

	public static IspacEventAuditTypeEnum BUSQUEDA_AVANZADA = new IspacEventAuditTypeEnum(
			BUSQUEDA_NAME, BUSQUEDA_VALUE);

	public static IspacEventAuditTypeEnum INFORME = new IspacEventAuditTypeEnum(INFORME_NAME,
			INFORME_VALUE);

	public static IspacEventAuditTypeEnum EXPEDIENTE_ALTA = new IspacEventAuditTypeEnum(
			EXPEDIENTE_ALTA_NAME, EXPEDIENTE_ALTA_VALUE);

	public static IspacEventAuditTypeEnum EXPEDIENTE_BAJA = new IspacEventAuditTypeEnum(
			EXPEDIENTE_BAJA_NAME, EXPEDIENTE_BAJA_VALUE);
	
	public static IspacEventAuditTypeEnum EXPEDIENTE_PAPELERA = new IspacEventAuditTypeEnum(
			EXPEDIENTE_PAPELERA_NAME, EXPEDIENTE_PAPELERA_VALUE);

	public static IspacEventAuditTypeEnum AVISO_ALTA = new IspacEventAuditTypeEnum(AVISO_ALTA_NAME,
			AVISO_ALTA_VALUE);

	public static IspacEventAuditTypeEnum AVISO_CONSULTA = new IspacEventAuditTypeEnum(
			AVISO_CONSULTA_NAME, AVISO_CONSULTA_VALUE);
	
	public static IspacEventAuditTypeEnum AVISO_MODIFICACION = new IspacEventAuditTypeEnum(
			AVISO_MODIFICACION_NAME, AVISO_MODIFICACION_VALUE);
	
	public static IspacEventAuditTypeEnum AVISO_BAJA = new IspacEventAuditTypeEnum(
			AVISO_BAJA_NAME, AVISO_BAJA_VALUE);

	public static IspacEventAuditTypeEnum TRAMITE_ALTA = new IspacEventAuditTypeEnum(
			TRAMITE_ALTA_NAME, TRAMITE_ALTA_VALUE);

	public static IspacEventAuditTypeEnum TRAMITE_CONSULTA = new IspacEventAuditTypeEnum(
			TRAMITE_CONSULTA_NAME, TRAMITE_CONSULTA_VALUE);

	public static IspacEventAuditTypeEnum TRAMITE_BAJA = new IspacEventAuditTypeEnum(
			TRAMITE_BAJA_NAME, TRAMITE_BAJA_VALUE);
	
	public static IspacEventAuditTypeEnum TRAMITE_MODIFICACION = new IspacEventAuditTypeEnum(
			TRAMITE_MODIFICACION_NAME, TRAMITE_MODIFICACION_VALUE);
	
	public static IspacEventAuditTypeEnum DOCUMENTO_ALTA = new IspacEventAuditTypeEnum(
			DOCUMENTO_ALTA_NAME, DOCUMENTO_ALTA_VALUE);
	
	public static IspacEventAuditTypeEnum DOCUMENTO_BAJA = new IspacEventAuditTypeEnum(
			DOCUMENTO_BAJA_NAME, DOCUMENTO_BAJA_VALUE);
	
	public static IspacEventAuditTypeEnum FICHERO_BAJA = new IspacEventAuditTypeEnum(
			FICHERO_BAJA_NAME, FICHERO_BAJA_VALUE);
	
	public static IspacEventAuditTypeEnum DOCUMENTO_CONSULTA = new IspacEventAuditTypeEnum(
			DOCUMENTO_CONSULTA_NAME, DOCUMENTO_CONSULTA_VALUE);
	
	public static IspacEventAuditTypeEnum DOCUMENTO_MODIFICACION = new IspacEventAuditTypeEnum(
			DOCUMENTO_MODIFICACION_NAME, DOCUMENTO_MODIFICACION_VALUE);
	
	public static IspacEventAuditTypeEnum ENTIDAD_ALTA = new IspacEventAuditTypeEnum(
			ENTIDAD_ALTA_NAME, ENTIDAD_ALTA_VALUE);
	
	public static IspacEventAuditTypeEnum ENTIDAD_MODIFICACION = new IspacEventAuditTypeEnum(
			ENTIDAD_MODIFICACION_NAME, ENTIDAD_MODIFICACION_VALUE);
	
	public static IspacEventAuditTypeEnum ENTIDAD_BAJA = new IspacEventAuditTypeEnum(
			ENTIDAD_BAJA_NAME, ENTIDAD_BAJA_VALUE);
	
	public static IspacEventAuditTypeEnum ENTIDAD_CONSULTA = new IspacEventAuditTypeEnum(
			ENTIDAD_CONSULTA_NAME, ENTIDAD_CONSULTA_VALUE);
	
	public static IspacEventAuditTypeEnum REGISTRO_DISTRIBUIDO_CONSULTA = new IspacEventAuditTypeEnum(
			REGISTRO_DISTRIBUIDO_CONSULTA_NAME, REGISTRO_DISTRIBUIDO_CONSULTA_VALUE);
	
	public static IspacEventAuditTypeEnum REGISTRO_DISTRIBUIDO_MODIFICACION = new IspacEventAuditTypeEnum(
			REGISTRO_DISTRIBUIDO_MODIFICACION_NAME, REGISTRO_DISTRIBUIDO_MODIFICACION_VALUE);

	/**
	 * 
	 */
	private static final long serialVersionUID = 7805284978789647949L;

	/**
	 * @param name
	 * @param value
	 */
	protected IspacEventAuditTypeEnum(String name, int value) {
		super(name, value);
	}
}
