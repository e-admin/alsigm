package ieci.tdw.ispac.ispacweb.tag;

import ieci.tdw.ispac.ispaclib.sign.ISignConnector;
import ieci.tdw.ispac.ispaclib.sign.SignConnectorFactory;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacweb.tag.context.StaticContext;
import ieci.tecdoc.sgm.core.config.ports.PortsConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.Globals;
import org.apache.struts.taglib.TagUtils;

public class SignTag extends TagSupport {

	private static final long serialVersionUID = 1L;

	private boolean massive = false;
	private String var = null;

	public boolean isMassive() {
		return massive;
	}

	public void setMassive(boolean massive) {
		this.massive = massive;
	}

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

	public int doStartTag() throws JspException	{

		TagUtils tagUtils = TagUtils.getInstance();

		JspWriter writer = pageContext.getOut();

		try {

			HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
			String context = request.getContextPath();
	        String baseURL = StaticContext.getInstance().getBaseUrl(context);

	        if (StringUtils.isNotBlank(baseURL) && baseURL.startsWith("/")) {

	    		String serverPort = String.valueOf(request.getServerPort());

	    		String proxyHttpPort = PortsConfig.getHttpFrontendPort();
	    		String proxyHttpsNoCertPort = PortsConfig.getHttpsFrontendPort();
	    		String proxyHttpsSiCertPort = PortsConfig.getHttpsFrontendAuthclientPort();

	    		if ((proxyHttpPort != null && proxyHttpPort.equals(serverPort)) ||
	    			(proxyHttpsNoCertPort != null && proxyHttpsNoCertPort.equals(serverPort)) ||
	    			(proxyHttpsSiCertPort != null && proxyHttpsSiCertPort.equals(serverPort))) {

	    			// Servidor Frontend por delante del Servidor de Aplicaciones (Ej: APACHE + TOMCAT)
	    			// HTTP
	    			serverPort = proxyHttpPort;
	    			// HTTPs sin certificado
	    			// serverPort = proxyHttpsNoCertPort;
	    		}
	    		else {
	    			// HTTP
	    			serverPort = PortsConfig.getHttpPort();
	    			// HTTPs sin certificado
	    			// serverPort = PortsConfig.getHttpsPort();
	    		}

	    		String appletDownload = "http://" + request.getServerName() + ":" + serverPort;

	    		/*
	        	baseURL = new StringBuffer(request.getScheme())
	        		.append("://")
	        		.append(request.getServerName())
	        		.append(":")
	        		.append(request.getServerPort())
	        		.append(baseURL)
	        		.toString();
	        	*/

	    		baseURL = appletDownload + baseURL;
	        }

			String htmlCode = "";

			ISignConnector signConnector = SignConnectorFactory.getSignConnector();
			if (signConnector != null) {

				if (massive) {
					String [] hashCodes = (String[]) pageContext.findAttribute(getVar());
					htmlCode = signConnector.getHTMLCode(request.getLocale(), baseURL, hashCodes);
				} else {
					String hashCode = (String) pageContext.findAttribute(getVar());
					htmlCode = signConnector.getHTMLCode(request.getLocale(), baseURL, hashCode);
				}

			} else {

				htmlCode += "<script type=\"text/javascript\" language=\"javascript\">";
				//htmlCode += "jAlert(\"" + TagUtils.getInstance().message(pageContext, null, Globals.LOCALE_KEY, "sign.message.error.notConfigured")+ "\" , '"+TagUtils.getInstance().message(pageContext, null, Globals.LOCALE_KEY, "common.alert")+"' , '"+tagUtils.message(pageContext, null, Globals.LOCALE_KEY, "common.message.ok")+"', '"+tagUtils.message(pageContext, null, Globals.LOCALE_KEY, "common.message.cancel")+"');";
				htmlCode += "alert(\"" + TagUtils.getInstance().message(pageContext, null, Globals.LOCALE_KEY, "sign.message.error.notConfigured")+ "\");";

				htmlCode += "</script>\n";
			}

			writer.print(htmlCode);

		} catch (Exception e) {
			tagUtils.saveException(pageContext, e);
			throw new JspException("Error al mostrar el componente de firma", e);
		}

		return (EVAL_BODY_INCLUDE);
	}

}
