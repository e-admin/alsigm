/**
 * 
 */
package ieci.tecdoc.sgm.web.view.tag.ecologo;

import ieci.tecdoc.sgm.manager.ecologo.LogoManager;
import ieci.tecdoc.sgm.manager.ecologo.impl.LogoManagerImpl;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

/**
 * @author IECISA
 * @version $Revision$
 * 
 */
public class LogoTag extends TagSupport {

	/**
	 * 
	 */
	private static final String CIUDADANO_ID_APLICACION = "C";

	/**
	 * 
	 */
	private static final long serialVersionUID = -2924454987461491768L;

	private LogoManager logoManager = new LogoManagerImpl();

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
	 */
	@Override
	public int doStartTag() throws JspException {
		try {
			String serverName = this.pageContext.getRequest().getServerName();
			String idAplicacion = this.pageContext.getRequest().getParameter("idAplicacion");
			/*
			 * Si es vacío suponemos que es una aplicación de ciudadano
			 */
			if (StringUtils.isEmpty(idAplicacion)){
				idAplicacion=CIUDADANO_ID_APLICACION;
			}
			pageContext.getOut().print(logoManager.render(idAplicacion,serverName));
		} catch (IOException e) {
			throw new JspException("Error: IOException" + e.getMessage());
		}
		return SKIP_BODY;
	}

}
