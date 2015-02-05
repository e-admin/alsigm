package ieci.tecdoc.sgm.core.services;

import ieci.tecdoc.sgm.core.services.dto.IRetornoServicio;
import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class ServiciosUtils {

	public static RetornoServicio createReturnOK(){
		RetornoServicio oReturn = new RetornoServicio();
		oReturn.setReturnCode(ConstantesServicios.SERVICE_RETURN_OK);
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
		oReturn.setReturnCode(ConstantesServicios.SERVICE_RETURN_ERROR);
		if((psErrorCode == null) || ("".equals(psErrorCode))){
			oReturn.setErrorCode(ConstantesServicios.SERVICE_RETURN_UNKNOWN_ERROR);
		}else{
			oReturn.setErrorCode(psErrorCode);			
		}
		return oReturn;		
	}
	
	public static RetornoServicio completeReturnOK(RetornoServicio poReturn){
		if(poReturn == null){
			return null;
		}
		poReturn.setReturnCode(ConstantesServicios.SERVICE_RETURN_OK);
		return poReturn;
	}

	public static RetornoServicio completeReturnError(RetornoServicio poReturn){
		return completeReturnError(poReturn, null);
	}

	public static RetornoServicio completeReturnError(RetornoServicio poReturn, long plErrorCode){
		return completeReturnError(poReturn, String.valueOf(plErrorCode));
	}
	
	public static RetornoServicio completeReturnError(RetornoServicio poReturn, String psErrorCode){
		poReturn.setReturnCode(ConstantesServicios.SERVICE_RETURN_ERROR);
		if((psErrorCode == null) || ("".equals(psErrorCode))){
			poReturn.setErrorCode(ConstantesServicios.SERVICE_RETURN_UNKNOWN_ERROR);
		}else{
			poReturn.setErrorCode(psErrorCode);			
		}
		return poReturn;		
	}
	
	public static boolean isReturnOK(IRetornoServicio poReturn){
		if(poReturn == null){
			return false;
		}
		if(ConstantesServicios.SERVICE_RETURN_OK.equals(poReturn.getReturnCode())){
			return true;
		}else{
			return false;
		}
	}

	public static String getReturnErrorCode(IRetornoServicio poReturn){
		if(poReturn == null){
			return ConstantesServicios.SERVICE_RETURN_UNKNOWN_ERROR;
		}
		if((poReturn.getErrorCode() == null) || ("".equals(poReturn.getErrorCode()))){
			return ConstantesServicios.SERVICE_RETURN_UNKNOWN_ERROR;
		}
		return poReturn.getErrorCode();
	}
	
	public static long getErrorCode(String psServicePrefix, String psErrorCode){
		return Long.valueOf(getFormatedErrorCode(psServicePrefix, psErrorCode)).longValue();
	}
	
	
	public static String getFormatedErrorCode(String psServicePrefix, String psErrorCode){
		StringBuffer sbError = new StringBuffer();
		if( (psServicePrefix == null) || ("".equals(psServicePrefix))){
			sbError.append(ConstantesServicios.SIGEM_ERROR_PREFIX);
		}else{
			if(psServicePrefix.length() > ConstantesServicios.SIGEM_ERROR_PREFIX_LENGTH){
				sbError.append(psServicePrefix.substring(0, ConstantesServicios.SIGEM_ERROR_PREFIX_LENGTH));
			}else{
				if(psServicePrefix.length() < ConstantesServicios.SIGEM_ERROR_PREFIX_LENGTH){
					int icounter = ConstantesServicios.SIGEM_ERROR_PREFIX_LENGTH - psServicePrefix.length();
					for(int iIndex = 0; iIndex < icounter; iIndex++){
						sbError.append(ConstantesServicios.SIGEM_ERROR_DEFAULT_MASK_ELEMENT);
					}
				}
				sbError.append(psServicePrefix);
			}
		}
		if((psErrorCode == null) || ("".equals(psErrorCode))){
			sbError.append(ConstantesServicios.SERVICE_RETURN_UNKNOWN_ERROR);
		}else{
			if(psErrorCode.length() > ConstantesServicios.SIGEM_ERROR_CODE_LENGTH){
				sbError.append(psErrorCode.substring(0, ConstantesServicios.SIGEM_ERROR_CODE_LENGTH));
			}else{
				if(psServicePrefix.length() < ConstantesServicios.SIGEM_ERROR_CODE_LENGTH){
					int icounter = ConstantesServicios.SIGEM_ERROR_CODE_LENGTH - psErrorCode.length();
					for(int iIndex = 0; iIndex < icounter; iIndex++){
						sbError.append(ConstantesServicios.SIGEM_ERROR_DEFAULT_MASK_ELEMENT);
					}
				}
				sbError.append(psErrorCode);
			}			
		}
		return sbError.toString();
	}
}
