package ieci.tecdoc.mvc.service.adminUser;

import ieci.tecdoc.mvc.dto.access.ErrorCertDTO;
import ieci.tecdoc.mvc.dto.access.TypeCertDTO;
import ieci.tecdoc.mvc.error.MvcError;
import ieci.tecdoc.mvc.error.WardaAdmException;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.digester.Digester;


public class ConfigCertificate {

	private boolean _enableCert;
	private int _typeCert;
	private List typesList;
	private List ignoredErrorsList;
	private String typeCert; 
	
	public boolean getEnableCert(){
		return _enableCert;
	}
	
	public String getTypeCert(){
		Iterator it = typesList.iterator();
		boolean success = false;
		while (it.hasNext() && !success){
			TypeCertDTO obj = (TypeCertDTO) it.next();
			if (obj.getId() == this._typeCert){
				success = true;
				this.typeCert = obj.getToken();				
			}				
		}
		return typeCert;
	}
	public List getIgnoredErrorsList(){
		return ignoredErrorsList;
	}
	
	public ConfigCertificate(){
		typesList = new ArrayList();
		ignoredErrorsList = new ArrayList();
		typeCert = new String();
	}
	public void setEnableCert (String is){
		this._enableCert = new Boolean(is).booleanValue();
	}
	public void setTypeCert(String type){
		this._typeCert = Integer.parseInt(type);
	}
	public void addTypeCert(TypeCertDTO type){
		typesList.add(type); 
	}
	public void ignoreError(ErrorCertDTO error){
		ignoredErrorsList.add(error);
	}
	
	public void parse() throws Exception {

		String fileName = "wardA-CertificateConfig.xml";
		InputStream is ;
		is = Thread.currentThread().getContextClassLoader(). getResourceAsStream(fileName); // este funciona
		 
		try {
			Digester digester = initDigester();
			digester.parse(is);
			
		}catch (Exception ex){
			ex.printStackTrace();
			WardaAdmException.throwException(MvcError.EC_CERTIFICATE_PARSE_);
		}
		
		
	}
	private Digester initDigester() throws Exception {
		Digester digester = new Digester();
		digester.setValidating(false);
		digester.push(this);
		digester.addCallMethod("config","setEnableCert",1);
		digester.addCallParam("config/enableCertificate",0);
		
		digester.addCallMethod("config","setTypeCert",1);
		digester.addCallParam("config/typeCert",0);
		
		digester.addObjectCreate("config/typesCert/type", TypeCertDTO.class);
		digester.addSetProperties("config/typesCert/type");
		digester.addSetNext("config/typesCert/type", "addTypeCert");
	
		digester.addObjectCreate("config/ignoredErrors/error", ErrorCertDTO.class);
		digester.addSetNext("config/ignoredErrors/error", "ignoreError");
		digester.addBeanPropertySetter("config/ignoredErrors/error","num");
		
		
		return digester;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ConfigCertificate obj = new ConfigCertificate();
			obj.parse();	
		}catch (Exception ex){
			ex.printStackTrace();
		}
		

	}

}
