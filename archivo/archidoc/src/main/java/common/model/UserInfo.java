package common.model;

public interface UserInfo {

	/** Identificador del usuario en el Sistema Gestor de Organización. */
	public String getOrganizationUserId();

	/** Identificador del usuario en el Sistema Gestor de Usuarios. */
	public String getExternalUserId();

	/**
	 * @return Returns the address.
	 */
	public String getAddress();

	/**
	 * @return Returns the email.
	 */
	public String getEmail();

	/**
	 * @return Returns the name.
	 */
	public String getName();

	/**
	 * @return Returns the surname.
	 */
	public String getSurname();

	public String getDescription();

}