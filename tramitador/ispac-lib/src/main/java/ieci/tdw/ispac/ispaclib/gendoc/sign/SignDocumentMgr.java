package ieci.tdw.ispac.ispaclib.gendoc.sign;

import ieci.tdw.ispac.api.IGenDocAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.sign.SignDocument;
import ieci.tdw.ispac.ispaclib.utils.HashHelper;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.io.ByteArrayOutputStream;
import java.security.NoSuchAlgorithmException;


public class SignDocumentMgr {

	SignDocument signDocument;
	private IClientContext clientContext;
	
	
	public SignDocumentMgr(SignDocument signDocument, ClientContext clientContext) {
		this.signDocument = signDocument;
		this.clientContext = clientContext;
	}


	public String generateHashCode() throws ISPACException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		String infoPag = signDocument.getItemDoc().getString("INFOPAG");
		if (StringUtils.isEmpty(infoPag))
			throw new ISPACInfo("exception.documents.notExists");
		
		
		IGenDocAPI genDocAPI = clientContext.getAPI().getGenDocAPI();
		Object connectorSession = null;
		try {
			connectorSession = genDocAPI.createConnectorSession();
			if (!genDocAPI.existsDocument(connectorSession, infoPag)){
				throw new ISPACInfo("exception.documents.notExists");
			}
			genDocAPI.getDocument(connectorSession, infoPag, out);
		}finally {
			if (connectorSession != null) {
				genDocAPI.closeConnectorSession(connectorSession);
			}
    	}		

		String hash;
		try {
			hash = HashHelper.generateHashCode(out.toByteArray());
		} catch (NoSuchAlgorithmException e) {
			throw new ISPACException(e);
		}
		return hash;
	}	

}
