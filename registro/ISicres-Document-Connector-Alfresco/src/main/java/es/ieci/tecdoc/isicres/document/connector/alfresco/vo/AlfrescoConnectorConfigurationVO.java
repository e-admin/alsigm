package es.ieci.tecdoc.isicres.document.connector.alfresco.vo;

import es.ieci.tecdoc.isicres.document.connector.vo.IsicresAbstractConnectorConfigurationVO;

/**
 * 
 * @author Iecisa 
 * @version $Revision$ 
 *
 */
public class AlfrescoConnectorConfigurationVO extends IsicresAbstractConnectorConfigurationVO{
	private String usuario;
	private String pass;
	private String ip;
	private String puerto;	
	
	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}
	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	/**
	 * @return the pass
	 */
	public String getPass() {
		return pass;
	}
	/**
	 * @param pass the pass to set
	 */
	public void setPass(String pass) {
		this.pass = pass;
	}
	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}
	/**
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}
	/**
	 * @return the puerto
	 */
	public String getPuerto() {
		return puerto;
	}
	/**
	 * @param puerto the puerto to set
	 */
	public void setPuerto(String puerto) {
		this.puerto = puerto;
	}
	
	public String getPathAPI(){
		return "http://"+this.ip+":"+this.puerto+"/alfresco/api";
	}
	public void fromXml(String xml) {
		// TODO Auto-generated method stub
		
	}
	public String toXml() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
