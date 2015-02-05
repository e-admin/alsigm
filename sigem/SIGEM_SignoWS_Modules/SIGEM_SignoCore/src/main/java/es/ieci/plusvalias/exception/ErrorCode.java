package es.ieci.plusvalias.exception;

/**
 * @author angel_castro@ieci.es - 10/09/2010
 */
public class ErrorCode { 
	private static String BASE_ERROR = "0000";
	public static String UNKNOWN = "0001";
	public static String UNKNOWN_CADASTRAL_REFERENCE = "0002";
	public static String INVALID_PAYMENT_AMOUNT = "0003";
	public static String COMUNICATION_ERROR = "0004";
	public static String PAYMENT_CREDENTIAL_ERROR = "0005";
	public static String PAYMENT_UNKNOW_ERROR = "0006";
	public static String PLUSVALIA_EXPIRED = "0007";
	public static String NOT_PLUSVALIA = "0008";
	public static String PLUSVALIA_NOT_EXIST = "0009";
	public static String PLUSVALIA_PAID = "0010";
	public static String PLUSVALIA_EXISTS = "0011";
	public static String PLUSVALIA_SAVE_ERROR = "0012";
	public static String DATABASE_ERROR = "0013";
	public static String ERROR_1_TRANS = "0014";
	public static String ERROR_1_ADQU = "0015";
	public static String NOT_VALID_IP = "0016";
	public static String NOT_VALID_DERECHO = "0017";
	
	private ErrorCode(){
		super();
	}
}
