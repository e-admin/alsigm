package ieci.tdw.ispac.ispaclib.session;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.messages.MessagesFormatter;
import ieci.tdw.ispac.ispaclib.util.ISPACConfiguration;

import java.io.Serializable;

public class OrganizationUserInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -509186234413388144L;
	String userName;
	String userId;
	String organizationName;
	String organizationId;
	String spacPoolName;
	String usersPoolName;
	String thirdPartyPoolName;
	String sicresPoolName;
	
	public String getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	
	
	public String getPoolName(String pattern){
		return MessagesFormatter.format(pattern, new Object[]{getOrganizationId()});
	}

	private String getPoolName(String pattern, String propertyPoolNamePattern){
		if (pattern == null){
	    	ISPACConfiguration config = null;
			try {
				config = ISPACConfiguration.getInstance();
			} catch (ISPACException e) {
				e.printStackTrace();
			}			
			pattern = config.get(propertyPoolNamePattern);
		}
		Object[]params = new Object[]{getOrganizationId()};
		return  MessagesFormatter.format(pattern, params);
	}
	
	
	public String getSpacPoolName(String pattern){
		if (spacPoolName == null)
			spacPoolName = getPoolName(pattern, ISPACConfiguration.POOLNAME_PATTERN);
		return spacPoolName;
	}
	
	public String getSpacPoolName(){
		return getSpacPoolName(null);
	}

	
	public String getUsersPoolName(String pattern){
		if (usersPoolName == null)
			usersPoolName = getPoolName(pattern, ISPACConfiguration.USERS_POOLNAME_PATTERN);
		return usersPoolName;
	}	
	
	public String getUsersPoolName(){
		return getUsersPoolName(null);
	}
	
	
	public String getThirdPartyPoolName(String pattern) {
		if (thirdPartyPoolName == null){
			thirdPartyPoolName = getPoolName(pattern, ISPACConfiguration.THIRDPARTY_SICRES_POOL_NAME_PATTERN);
		}
		return thirdPartyPoolName;
	}
	
	
	public String getThirdPartyPoolName(){
		return getThirdPartyPoolName(null);
	}
	
	public String getSicresPoolName(String pattern) {
		if (sicresPoolName == null){
			sicresPoolName = getPoolName(pattern);
		}
		return sicresPoolName;
	}
	
}