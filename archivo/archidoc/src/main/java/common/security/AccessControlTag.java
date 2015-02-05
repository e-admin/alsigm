package common.security;

import gcontrol.model.TipoAcceso;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;

import se.usuarios.AppUser;
import se.usuarios.ServiceClient;

import common.Constants;
import common.util.StringUtils;
import common.vos.Restringible;

public class AccessControlTag extends TagSupport {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String object = null;
	private String permission = null;

	/**
	 * Constructor.
	 */
	public AccessControlTag() {
	}

	public String getObject() {
		return object;
	}

	public void setObject(String name) {
		this.object = name;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public int doStartTag() throws JspException {
		int returnValue = SKIP_BODY;

		Restringible restringibleObj = (Restringible) ExpressionEvaluatorManager
				.evaluate("object", // attribute name
						object, // expression
						Restringible.class, // expected type
						this, // this tag handler
						pageContext);

		String appPermission = (String) ExpressionEvaluatorManager.evaluate(
				"permission", // attribute name
				permission, // expression
				String.class, // expected type
				this, // this tag handler
				pageContext);

		if (StringUtils.isNotBlank(appPermission)) {
			if (SecurityManagerBase.isAccessAllowed(
					getServiceClient(pageContext.getRequest()),
					TipoAcceso.EDICION, restringibleObj.getNivelAcceso(),
					restringibleObj.getIdArchivo(), restringibleObj.getIdLCA(),
					appPermission))
				return EVAL_BODY_INCLUDE;
		} else {
			if (SecurityManagerBase.isAccessAllowed(
					getServiceClient(pageContext.getRequest()),
					TipoAcceso.CONSULTA, restringibleObj.getNivelAcceso(),
					restringibleObj.getIdArchivo(), restringibleObj.getIdLCA()))
				return EVAL_BODY_INCLUDE;
		}

		return returnValue;
	}

	public ServiceClient getServiceClient(ServletRequest request) {
		AppUser appUser = (AppUser) ((HttpServletRequest) request).getSession()
				.getAttribute(Constants.USUARIOKEY);
		return ServiceClient.create(appUser);
	}

}