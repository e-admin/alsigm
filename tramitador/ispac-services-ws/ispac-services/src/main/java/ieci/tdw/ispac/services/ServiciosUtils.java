package ieci.tdw.ispac.services;

import ieci.tdw.ispac.services.dto.RetornoServicio;

public class ServiciosUtils {

	/**
	 * Constantes para la invocación de servicios.
	 */
	public static final String SERVICE_RETURN_OK			= "OK";
	public static final String SERVICE_RETURN_ERROR			= "ERROR";
	public static final String SERVICE_RETURN_UNKNOWN_ERROR	= "00000000";

	public static RetornoServicio createReturnOK(){
		RetornoServicio oReturn = new RetornoServicio();
		oReturn.setReturnCode(SERVICE_RETURN_OK);
		return oReturn;
	}
	
	public static RetornoServicio createReturnError(){
		return createReturnError(null);
	}
	
	public static RetornoServicio createReturnError(long plErrorCode){
		return createReturnError(String.valueOf(plErrorCode));
	}

	public static RetornoServicio createReturnError(String psErrorCode){
		RetornoServicio oReturn = new RetornoServicio();
		oReturn.setReturnCode(SERVICE_RETURN_ERROR);
		if((psErrorCode == null) || ("".equals(psErrorCode))){
			oReturn.setErrorCode(SERVICE_RETURN_UNKNOWN_ERROR);
		}else{
			oReturn.setErrorCode(psErrorCode);			
		}
		return oReturn;		
	}
	
	public static RetornoServicio completeReturnOK(RetornoServicio poReturn){
		if(poReturn == null){
			return null;
		}
		poReturn.setReturnCode(SERVICE_RETURN_OK);
		return poReturn;
	}

	public static RetornoServicio completeReturnError(RetornoServicio poReturn){
		return completeReturnError(poReturn, null);
	}

	public static RetornoServicio completeReturnError(RetornoServicio poReturn, long plErrorCode){
		return completeReturnError(poReturn, String.valueOf(plErrorCode));
	}
	
	public static RetornoServicio completeReturnError(RetornoServicio poReturn, String psErrorCode){
		poReturn.setReturnCode(SERVICE_RETURN_ERROR);
		if((psErrorCode == null) || ("".equals(psErrorCode))){
			poReturn.setErrorCode(SERVICE_RETURN_UNKNOWN_ERROR);
		}else{
			poReturn.setErrorCode(psErrorCode);			
		}
		return poReturn;		
	}
	
	public static boolean isReturnOK(RetornoServicio poReturn){
		if(poReturn == null){
			return false;
		}
		if(SERVICE_RETURN_OK.equals(poReturn.getReturnCode())){
			return true;
		}else{
			return false;
		}
	}

	public static String getReturnErrorCode(RetornoServicio poReturn){
		if(poReturn == null){
			return SERVICE_RETURN_UNKNOWN_ERROR;
		}
		if((poReturn.getErrorCode() == null) || ("".equals(poReturn.getErrorCode()))){
			return SERVICE_RETURN_UNKNOWN_ERROR;
		}
		return poReturn.getErrorCode();
	}
	
}
