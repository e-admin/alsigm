package ieci.tecdoc.sgm.cripto.firma.impl.bouncycastle.config;

import ieci.tecdoc.sgm.cripto.firma.impl.bouncycastle.Constants;

public class FirmaBCConfiguration {
	
	public static String FIRMA_SIGEM_API_KEYSTORE="FIRMA_SIGEM_API_KEYSTORE";
	public static String  FIRMA_SIGEM_API_PASSWORD="FIRMA_SIGEM_API_PASSWORD";
	public static String  FIRMA_SIGEM_API_ALIAS="FIRMA_SIGEM_API_ALIAS";
	public static String  FIRMA_SIGEM_API_KEYSTORE_TYPE="FIRMA_SIGEM_API_KEYSTORE_TYPE";
	public static String  FIRMA_SIGEM_API_KEYSTORE_PROVIDER="FIRMA_SIGEM_API_KEYSTORE_PROVIDER";
		
	private String keyStore	= Constants.KEY_STORE;
	private String password = Constants.PASSWORD;
	private String alias = 	  Constants.ALIAS;
	private String keyStoreType = Constants.KEY_STORE_TYPE;
	private String keyStoreProvider = Constants.KEY_STORE_PROVIDER;
	
	public String getKeyStore() {
		return keyStore;
	}
	public void setKeyStore(String keyStore) {
		this.keyStore = keyStore;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getKeyStoreType() {
		return keyStoreType;
	}
	public void setKeyStoreType(String keyStoreType) {
		this.keyStoreType = keyStoreType;
	}
	public String getKeyStoreProvider() {
		return keyStoreProvider;
	}
	public void setKeyStoreProvider(String keyStoreProvider) {
		this.keyStoreProvider = keyStoreProvider;
	}
}
