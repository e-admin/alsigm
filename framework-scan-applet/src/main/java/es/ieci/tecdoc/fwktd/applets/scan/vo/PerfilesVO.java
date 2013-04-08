package es.ieci.tecdoc.fwktd.applets.scan.vo;

import java.util.HashMap;
import com.thoughtworks.xstream.XStream;

@SuppressWarnings({"rawtypes"})
public class PerfilesVO {	
	private String sourceDefault;
	private String selectName;
	private HashMap hashPerfiles;
	private String userHome;
	
	public PerfilesVO(){
		hashPerfiles = new HashMap();		
	}	

	public String getSourceDefault() {
		return sourceDefault;
	}

	public void setSourceDefault(String sourceDefault) {
		this.sourceDefault = sourceDefault;
	}

	public HashMap getHashPerfiles() {
		return hashPerfiles;
	}

	public void setHashPerfiles(HashMap hashPerfiles) {
		this.hashPerfiles = hashPerfiles;
	}	
	
	public String getSelectName() {
		return selectName;
	}
	
	public void setSelectName(String selectName) {
		this.selectName = selectName;
	}
	
	public String toXml() {
		String result="";
		XStream xstream = configureXstream();		
		result=xstream.toXML(this);
		return result;
	}
	
	protected XStream configureXstream(){
		XStream result = new XStream();		
		result.alias("Configuracion", this.getClass());		
		result.aliasField("Perfiles", this.getClass(), "hashPerfiles");
		result.alias("perfil", PerfilVO.class);					
		return result;	
	}
	
	public PerfilesVO fromXml(String xml) {
		PerfilesVO objectFromXml = new PerfilesVO();
		try{
			XStream xstream =configureXstream();
		   objectFromXml = (PerfilesVO)xstream.fromXML(xml);
		}catch(Exception e){
		}
		return objectFromXml;
	}

	public String getUserHome() {
		return userHome;
	}

	public void setUserHome(String userHome) {
		this.userHome = userHome;
	}
}
