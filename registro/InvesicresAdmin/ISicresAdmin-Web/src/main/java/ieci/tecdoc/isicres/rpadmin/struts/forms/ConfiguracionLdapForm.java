package ieci.tecdoc.isicres.rpadmin.struts.forms;

import javax.servlet.http.HttpServletRequest;

import ieci.tecdoc.isicres.rpadmin.struts.util.Utils;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorActionForm;

import es.ieci.tecdoc.isicres.admin.sbo.config.CfgLdapConfig;


public class ConfiguracionLdapForm extends ValidatorActionForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 8786066064255561271L;

	protected String tipoServidor;
	protected String direccion;
	protected String puerto;
	protected String usuario;
	protected String password;
	protected String nodoRaiz;
	protected Boolean usarSOAuth = Boolean.FALSE;

	/**
	 * Método para establecer los datos del formulario a partir de la configuración LDAP recuperada
	 * @param ldapConfig
	 */
	public void set(CfgLdapConfig ldapConfig) {
		//if (ldapConfig.getEngine() != 0) {
		this.tipoServidor = String.valueOf(ldapConfig.getEngine());
		//}
		this.direccion = ldapConfig.getServer();
		if (ldapConfig.getPort() != 0) {
			this.puerto = String.valueOf(ldapConfig.getPort());
		}
		this.usuario = ldapConfig.getUser();
		this.password = ldapConfig.getPwd();
		this.nodoRaiz = ldapConfig.getRoot();
		this.usarSOAuth = ldapConfig.isUseOSAuth();
	}

	public CfgLdapConfig populate () {
		CfgLdapConfig ldapConfig = new CfgLdapConfig();

		ldapConfig.setEngine(Integer.parseInt(this.getTipoServidor()));
		if (Utils.isNotNuloOVacio(this.getPuerto())) {
			ldapConfig.setPort(Integer.parseInt(this.getPuerto()));
		}
		ldapConfig.setUser(this.getUsuario());
		ldapConfig.setPwd(this.getPassword());
		ldapConfig.setServer(this.getDireccion());
		ldapConfig.setRoot(this.getNodoRaiz());
		ldapConfig.setUseOSAuth(this.getUsarSOAuth());

		return ldapConfig;
	}

	public String getTipoServidor() {
		return tipoServidor;
	}
	public void setTipoServidor(String tipoServidor) {
		this.tipoServidor = tipoServidor;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getPuerto() {
		return puerto;
	}
	public void setPuerto(String puerto) {
		this.puerto = puerto;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNodoRaiz() {
		return nodoRaiz;
	}
	public void setNodoRaiz(String nodoRaiz) {
		this.nodoRaiz = nodoRaiz;
	}

	public Boolean getUsarSOAuth() {
		return usarSOAuth;
	}

	public void setUsarSOAuth(Boolean usarSOAuth) {
		this.usarSOAuth = usarSOAuth;
	}
}
