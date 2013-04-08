package es.ieci.plusvalias.sigem.receipt;

public class KeyStoreData
{
	private String keystorePath;
	private String keystoreType;
	private String keystorePassword;
	private String keyAlias;
	private String keyAliasPassword;
	
	public KeyStoreData(String keystorePath, String keystoreType, String keystorePassword, String keyAlias, String keyAliasPassword)
	{
		this.keystorePath = keystorePath;
		this.keystoreType = keystoreType;
		this.keystorePassword = keystorePassword;
		this.keyAlias = keyAlias;
		this.keyAliasPassword = keyAliasPassword;
	}
	
	public String getKeystorePath()
	{
		return keystorePath;
	}

	public void setKeystorePath(String rutaKeystore)
	{
		this.keystorePath = rutaKeystore;
	}

	public String getKeystoreType()
	{
		return keystoreType;
	}

	public void setKeystoreType(String tipoCertificado)
	{
		this.keystoreType = tipoCertificado;
	}

	public String getKeystorePassword()
	{
		return keystorePassword;
	}

	public void setKeystorePassword(String keystorePassword)
	{
		this.keystorePassword = keystorePassword;
	}

	public String getKeyAlias()
	{
		return keyAlias;
	}

	public void setKeyAlias(String keyAlias)
	{
		this.keyAlias = keyAlias;
	}

	public String getKeyAliasPassword()
	{
		return keyAliasPassword;
	}

	public void setKeyAliasPassword(String keyAliasPassword)
	{
		this.keyAliasPassword = keyAliasPassword;
	}
}
