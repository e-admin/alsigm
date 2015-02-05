package ieci.tecdoc.sgm.core.services.dto;

public class RetornoServicio implements IRetornoServicio{

	public String returnCode;
	public String errorCode;
	
	public String getErrorCode() {
		return errorCode;
	}
	
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}
	
}
