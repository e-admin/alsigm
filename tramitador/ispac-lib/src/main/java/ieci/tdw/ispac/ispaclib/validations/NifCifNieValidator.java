package ieci.tdw.ispac.ispaclib.validations;

import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import org.apache.regexp.RE;
import org.apache.regexp.RESyntaxException;
import org.apache.log4j.Logger;

/**
 * Clase que valida NIF-CIF
 * @author 66423658
 *
 */

public class NifCifNieValidator implements IValidator {


	
	/** Logger de la clase. */
    private static Logger logger = Logger.getLogger(NifCifNieValidator.class);
    
	 /**
     * Conjunto de letras validas para un NIF o NIE
     */
    protected final static char[] allowedLettersForNIF = { 'T', 'R', 'W', 'A',
            'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q',
            'V', 'H', 'L', 'C', 'K', 'E', 'T' };

    /**
     * Conjunto de letras validas para un CIF
     */
    protected final static char[] controlDigitsForCIF = { 'J', 'A', 'B', 'C',
            'D', 'E', 'F', 'G', 'H', 'I' };
    /**
     * Sólo admiten números como caracter de control
     */
    protected final static  String aloneControlNumbers = "ABEH"; 
    /**
     * Sólo admiten letras como caracter de control
     */
    protected final static String aloneControlCharacters = "KPQS";  
    

    /**
     * Expresion regular para validar NIFs
     */
    protected final static String NIF_REGEXP = "^([:digit:]{0,10})[a-zA-Z^IiOoUu]$";

    /**
     * Expresion regular para validar NIEs
     */
    protected static final String NIE_REGEXP = "^[x-zX-Z]([:digit:]+){0,8}[a-zA-Z^IiOoUu]$";

    /**
     * Expresion regular para validar CIFs   
     */
    protected static final String CIF_REGEXP = "^[a-zA-Z^IiÑñOoTtXxYyZz]([:digit:]{0,7}([abcdefghijABCDEFGHIJ]|[:digit:]))$";
   

    /**
     * Expresion regular para validar NIFs Especiales
     */
    protected final static String SPECIAL_NIF_REGEXP = "^[klmKLM][:digit:]{8,}";

    /**
     * Constante para identificar NIFs
     */
    public final static int NIF = 0;

    /**
     * Constante para identificar CIFs
     */
    public final static int CIF = 1;

    /**
     * Constante para identificar NIEs
     */
    public final static int NIE = 2;

    /**
     * Longitud de un NIF
     */
    protected final static int NIF_length = 10;

    /**
     * Logingitud de un NIE
     */
    protected final static int NIE_length = 8;

    /**
     * Longitud de un CIF
     */
    protected final static int CIF_length = 8;

    /**
     * Se encarga de validar si el parámetro <code>id</code> tiene formato de
     * NIF.
     *
     * @param id
     *            cadena a validar
     * @return <code>TRUE</code> en caso de que el formato sea correcto y
     *         <code>FALSE</code> en caso contrario
     */
    public static boolean isNIF(String id)  {
        boolean result = true;

        try {
			RE re = new RE(NIF_REGEXP);

			if (!re.match(id)) {
			    result = false;
			} 
			else {
			    String digits = "";
			   
			        digits = re.getParen(1);
			        digits = completeIFDigits(digits, NIF);
			        if (!checkControlDigit(digits,
			                id.substring(id.length() - 1).charAt(0), NIF)) {
			            result = false;
			        }
			    }
		} catch (RESyntaxException e) {
			if(logger.isDebugEnabled()){
				logger.debug("Error en NifCifNieValidator::isNif", e);
			}
			return false;
		} 
        

        return result;
    }

    /**
     * Se encarga de validar si el parámetro <code>id</code> tiene formato de
     * NIE.
     *
     * @param id
     *            cadena a validar
     * @return <code>TRUE</code> en caso de que el formato sea correcto y
     *         <code>FALSE</code> en caso contrario
     */
    public static boolean isNIE(String id) {

       //Un NIE se valida como un NIF sabo que delante lleva una X, Y o Z
        
        if (id.charAt(0)!='x' && id.charAt(0)!='X'
        		&& id.charAt(0)!='y' && id.charAt(0)!='Y'
        		&& id.charAt(0)!='z' && id.charAt(0)!='Z')
        	return false;
        else
        	return isNIF(id.substring(1));
        
     
      
    }

    /**
     * Valida si un identificador se ajusta al formato de un NIF especial.
     * @param id el identificador a validar
     * @return <code>TRUE</code> si el identificador es un NIF especial, <code>FALSE</code>
     * en caso contrario.
   
     */
    public static boolean isSpecialNIF(String id) {

    	try {
			RE re = new RE(SPECIAL_NIF_REGEXP);
			return re.match(id);
		} catch (RESyntaxException e) {
			if(logger.isDebugEnabled()){
				logger.debug("Error en NifCifNieValidator::isSpecialNIF", e);
			}
			return false;
		}
    }

    /**
     * Se encarga de validar si el parámetro <code>id</code> tiene formato de
     * CIF.
     *
     * @param id
     *            cadena a validar
     * @return <code>TRUE</code> en caso de que el formato sea correcto y
     *         <code>FALSE</code> en caso contrario
 
     */
    public static boolean isCIF(String id){
        boolean result = true;

    
        try {
        
			RE re = new RE(CIF_REGEXP);
         
			if (!re.match(id)) {
			    result = false;
			} else {
			    String digits = "";
			
			        digits = re.getParen(1);
			        digits = completeIFDigits(digits, CIF);
			        if (!checkControlDigit(digits,
			                id.substring(id.length() - 1).charAt(0), CIF)) {
			            result = false;
			        }
			   
			}
		} catch (RESyntaxException e) {
			if(logger.isDebugEnabled()){
				logger.debug("Error en NifCifNieValidator::isCIF", e);
			}
			return false;
		}

        return result;
    }




    /**
     * Valida el dígito de control de un documento
     *
     * @param digits dígitos del documento
     * @param controlDigit dígito de control
     * @param tipo tipo de documento
     * @return verdadero si el dígito de control es correcto, falso en caso contrario.
     */
    private static boolean checkControlDigit(String digits, char controlDigit,
            int tipo) {
        Boolean ok = Boolean.TRUE;
        int pos;
        char rightLetter = '0';
        boolean aloneDigit=false;
        boolean aloneLetter=false;
        
       char firstCharacter = digits.charAt(0);
       //Para aquellos documentos cuya letra este en el grupo de letras
       //aloneControlCharacters activaremos el indicador
       if(StringUtils.contains(aloneControlCharacters, firstCharacter)){
        	aloneLetter=true;
       }
       else if(StringUtils.contains(aloneControlNumbers, firstCharacter)){
        	aloneDigit=true;
       }
        try {
            switch (tipo) {
            case NIF:
            case NIE:
                pos = Integer.valueOf(digits).intValue() % 23;

                rightLetter = allowedLettersForNIF[pos];
                break;
            case CIF:
                // Nos quedamos con todos los digitos menos con el carácter de
                // control

            	int digit = calculateControlDigitForCIF(digits.substring(0,
                        digits.length() - 1));

                /**
                 * En caso de que el último carácter del CIF sea una letra se
                 * hace la conversión haciendo uso de los caracteres almacenados
                 * en el array control <code>controlDigitsForCIF</code>
                 */
                char lastChar = digits.substring(digits.length() - 1).charAt(0);
                if (Character.isLetter(lastChar)) {
                	rightLetter = controlDigitsForCIF[digit];
                } else {
                	//No todo los cifs pueden tener al final un número 
                	rightLetter = Character.forDigit(digit, 10);
                }
                break;
            }
        } catch (Exception e) {
        	if(logger.isDebugEnabled()){
        		logger.debug("Error en NifCifNieValidator::checkControlDigit", e);
			}
            ok = Boolean.FALSE;
        }

        if (!(Character.toLowerCase(rightLetter) ==
        	Character.toLowerCase(controlDigit)) 
        	||(Character.isLetter(rightLetter)&& aloneDigit) 
        	|| (Character.isDigit(rightLetter) && aloneLetter)){
            ok = Boolean.FALSE;
        }
     

        return ok.booleanValue();
    }

    /**
     * Clacula el dígito de control correcto para un CIF determinado
     *
     * @param digits digitos del CIF
     * @return dígito de control correspondiente a los dígitos indicados
     */
    private static int calculateControlDigitForCIF(String digits) {
        int A = 0, B = 0, C = 0, D = 0;
        int temp = 0;
        // Calculamos A
        for (int i = 1; i < digits.length(); i += 2) {
            A += Integer.valueOf(digits.substring(i, i + 1)).intValue();
        }

        // Calculamos B
        for (int i = 0; i < digits.length(); i += 2) {
            temp = Integer.valueOf(digits.substring(i, i + 1)).intValue() * 2;
            if (temp >= 10) {
                String b = String.valueOf(temp);
                B += Integer.valueOf(b.substring(0, 1)).intValue()
                        + Integer.valueOf(b.substring(1)).intValue();
            } else {
                B += temp;
            }
        }

        // Calculamos C
        C = A + B;

        if (C >= 10) {
            C = Integer.valueOf(String.valueOf(C).substring(1)).intValue();
        }

        // Calculamos D
        D = (10 - C) % 10;

        return D;
    }

    /**
     * Se encarga de completar con ceros a la izquierda el parámetro
     * <code>digits</code> con el fin de adaptarlo al formato requerido según
     * el tipo de identificador fiscal que se pasa en <code>tipo</code>. Los
     * tipos de identificadores fiscales válidos son <code>NIF</code>,
     * <code>NIE</code> y <code>CIF</code>.
     *
     * @param digits digitos del documento
     * @param tipo tipo de documento
     * @return identificador completo con ceros a la izquierda
     */
    public static String completeIFDigits(String digits, int tipo) {
        StringBuffer sb = new StringBuffer();
        int numOfDigits = 0;

        if (null != digits) {
            numOfDigits = digits.length();
        }
        switch (tipo) {
        case NIF:
            for (int i = 0; i < (NIF_length - numOfDigits); i++) {
                sb.append("0");
            }
            break;
        case CIF:
            for (int i = 0; i < (CIF_length - numOfDigits); i++) {
                sb.append("0");
            }
            break;
        case NIE:
            for (int i = 0; i < (NIE_length - numOfDigits); i++) {
                sb.append("0");
            }
        }

        if (null != digits) {
            sb.append(digits);
        }

        return sb.toString();
    }
	
    

	public NifCifNieValidator() {
		super();
		
	}

	public  boolean validate(Object objeto)  {
	
		if(objeto!=null && !StringUtils.isBlank(objeto.toString())){
			String nifCif=objeto.toString();
			
			if(nifCif.length() < 9 ){
				return false;
			}
		
				if(isNIF(nifCif)){
					return true;
				}
				if(isSpecialNIF(nifCif)){
					return true;
				}
				if(isCIF(nifCif)){
					return true;
				}
				if(isNIE(nifCif)){
					return true;
				}
			
			

			}
		return false;
		
	}
	
	
	
	
	
}
