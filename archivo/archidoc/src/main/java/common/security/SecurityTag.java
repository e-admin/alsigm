package common.security;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;
import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;

import se.usuarios.AppUser;
import se.usuarios.ServiceClient;
import util.StringOwnTokenizer;

import common.Constants;
import common.definitions.ArchivoModules;

public class SecurityTag extends TagSupport {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	protected final static Logger logger = Logger.getLogger(SecurityTag.class);

	String action = null;
	String permission = null;

	public SecurityTag() {
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public int doStartTag() throws JspException {
		int returnValue = SKIP_BODY;
		if (action != null) {
			ActionObject actionObject = (ActionObject) ExpressionEvaluatorManager
					.evaluate("action", // attribute name
							action, // expression
							ActionObject.class, // expected type
							this, // this tag handler
							pageContext);

			if (actionObject != null)
				returnValue = eval(actionObject) ? EVAL_BODY_INCLUDE
						: SKIP_BODY;
		} else if (permission != null) {
			String appPermission = (String) ExpressionEvaluatorManager
					.evaluate("permission", // attribute name
							permission, // expression
							String.class, // expected type
							this, // this tag handler
							pageContext);
			if (appPermission != null) {
				StringOwnTokenizer tok = new StringOwnTokenizer(appPermission,
						"|");
				AppUser appUser = getAppUser(pageContext.getRequest());
				if (appUser.hasAnyPermission(tok.toArray()))
					returnValue = EVAL_BODY_INCLUDE;
			}
		}
		return returnValue;
	}

	public boolean eval(ActionObject actionObject) {
		try {
			switch (actionObject.getModule()) {
			case ArchivoModules.DEPOSITOS_MODULE:
				SecurityManagerLocator.loockupDepositoSM().check(actionObject,
						getServiceClient(pageContext.getRequest()));

				break;
			case ArchivoModules.SERVICIOS_MODULE:
				SecurityManagerLocator.loockupServiciosSM().check(actionObject,
						getServiceClient(pageContext.getRequest()));

				break;
			case ArchivoModules.AUDITORIA_MODULE:
				SecurityManagerLocator.loockupAuditoriaSM().check(actionObject,
						getServiceClient(pageContext.getRequest()));

				break;

			case ArchivoModules.DESCRIPCION_MODULE:
				SecurityManagerLocator.loockupDescripcionSM().check(
						actionObject,
						getServiceClient(pageContext.getRequest()));
				break;

			case ArchivoModules.DOCUMENTOS_ELECTRONICOS_MODULE:
				SecurityManagerLocator.loockupDocumentosElectronicosSM().check(
						actionObject,
						getServiceClient(pageContext.getRequest()));
				break;

			case ArchivoModules.DOCUMENTOS_VITALES_MODULE:
				SecurityManagerLocator.lookupDocumentosVitalesSM().check(
						actionObject,
						getServiceClient(pageContext.getRequest()));
				break;

			case ArchivoModules.FONDOS_MODULE:
				SecurityManagerLocator.lookupFondosSecurityManager().check(
						actionObject,
						getServiceClient(pageContext.getRequest()));
				break;

			case ArchivoModules.TRANSFERENCIAS_MODULE:
				SecurityManagerLocator.loockupTransferenciasSM().check(
						actionObject,
						getServiceClient(pageContext.getRequest()));
				break;

			case ArchivoModules.USUARIOS_MODULE:
				SecurityManagerLocator.loockupUsuariosSM().check(actionObject,
						getServiceClient(pageContext.getRequest()));
				break;
			case ArchivoModules.SALAS_MODULE:
				SecurityManagerLocator.lookupSalasSecurityManager().check(
						actionObject,
						getServiceClient(pageContext.getRequest()));
				break;
			default:
				return false;
			}
			return true;
		} catch (common.exceptions.SecurityException e) {
			return false;
		}
	}

	public AppUser getAppUser(ServletRequest request) {
		return (AppUser) ((HttpServletRequest) request).getSession()
				.getAttribute(Constants.USUARIOKEY);
	}

	public ServiceClient getServiceClient(ServletRequest request) {
		AppUser appUser = getAppUser(request);
		return (appUser != null ? ServiceClient.create(appUser) : null);
	}

}