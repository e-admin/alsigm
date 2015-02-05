package ieci.tecdoc.sgm.core.services.cripto.firma;

import java.util.ArrayList;
import java.util.List;

public class ResultadoValidacionFirma {

	private boolean validationResult;
	private List signers;
	
	public void addSigner(Firmante poSigner){
		if(signers == null){
			signers = new ArrayList(1);
		}
		signers.add(poSigner);
	}
	
	public List getSigners() {
		return signers;
	}
	
	public void setSigners(List signers) {
		this.signers = signers;
	}
	
	public boolean getValidationResult() {
		return validationResult;
	}

	public boolean isValidationResultOK() {
		return validationResult;
	}

	public void setValidationResult(boolean validationResult) {
		this.validationResult = validationResult;
	}
	
	
	
}
