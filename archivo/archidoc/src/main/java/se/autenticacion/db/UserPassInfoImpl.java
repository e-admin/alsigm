package se.autenticacion.db;

import se.autenticacion.UserInfoImpl;

public class UserPassInfoImpl extends UserInfoImpl {

	/**
	 * Contraseña del usuario
	 */
	private String password;

	/**
	 * Contructor
	 * 
	 * @param externalUserId
	 * @param organizationUserId
	 * @param address
	 * @param email
	 * @param name
	 * @param surname
	 * @param description
	 * @param password
	 */
	public UserPassInfoImpl(String externalUserId, String organizationUserId,
			String address, String email, String name, String surname,
			String description, String password) {
		super(externalUserId, organizationUserId, address, email, name,
				surname, description);
		this.password = password;
	}

	/**
	 * Permite obtener la password del usuario
	 * 
	 * @return password del usuario
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Permite establecer la password del usuario
	 * 
	 * @param password
	 *            Password del usuario
	 */
	public void setPassword(String password) {
		this.password = password;
	}

}
