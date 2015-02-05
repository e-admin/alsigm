/*
 * Created on Dec 28, 2004 by IECISA
 *
 */
package ieci.tdw.ispac.ispacweb.tag;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.util.ISPACConfiguration;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.taglib.TagUtils;


public class KeepAlive extends TagSupport
{

	// 1 minuto: 60000 ms. Este periodo es utilizado si no se encuentra
	// definido en el fichero de configuracion ispac.properties
	private String DEFAULT_PERIOD = "60000";

	public int doStartTag() throws JspException
	{
		String ticket = getTicket ();
		String url = getUrl (ticket);
  	String period = getPeriod ();
  	StringBuffer out = buildStartContents (period, url);

		try
		{
			JspWriter writer = pageContext.getOut();
			writer.print(out.toString());
		}
		catch (IOException ioe)
		{
			TagUtils.getInstance().saveException(pageContext, ioe);
			throw new JspException(ioe.toString());
		}

		return (EVAL_BODY_INCLUDE);
	}


	private String getPeriod ()
	{
		String period = null;
  	try
		{
  		ISPACConfiguration config = ISPACConfiguration.getInstance();
  		period = config.get(ISPACConfiguration.KEEP_ALIVE);
  		Integer.parseInt(period);
  	}
  	catch (NumberFormatException e)
		{
  		period = DEFAULT_PERIOD;
  	}
  	catch (ISPACException e)
		{
  		period = DEFAULT_PERIOD;
  	}
		return period;
	}

	public String getUrl (String ticket)
	{
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		String scheme = request.getScheme();
		String server = request.getServerName();
		int port = request.getServerPort();
		String context = request.getContextPath();
		return scheme + "://" + server + ":" + port +  context + "/keepalivesession?ticket="+ ticket;
	}

	private String getTicket ()
	{
		String ticket = null;
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		Cookie[] cookies = request.getCookies();
		for (int i=0; i<cookies.length; i++)
		{
			if (cookies[i].getName().equals("user"))
			{
				ticket = cookies[i].getValue();
			}
		}
		return ticket;
	}

	private StringBuffer buildStartContents (String period, String url)
	{
		StringBuffer out = new StringBuffer();
  	out.append("<script language='javascript'>\n");
  	out.append("//  FUNCIONES KEEP-ALIVE\n");
  	out.append("var xmlhttp=false;\n");
  	out.append("function xmlHttpResquestInit () {\n");
  	out.append("try {\n");
  	out.append("xmlhttp = new ActiveXObject ('Msxml2.XMLHTTP');\n");
  	out.append("} catch (e) {\n");
  	out.append("try {\n");
  	out.append("xmlhttp = new ActiveXObject ('Microsoft.XMLHTTP');\n");
  	out.append("} catch (E)	{\n");
  	out.append("xmlhttp = false; \n} \n}\n");
  	out.append("if (!xmlhttp && typeof XMLHttpRequest!='undefined')\n");
  	out.append("{ xmlhttp = new XMLHttpRequest (); \n}	\n}\n");
  	out.append("function keepAliveInit()\n{\n");
  	out.append("xmlHttpResquestInit ();\n");
  	out.append("window.setInterval ('keepAliveRefresh()',");
  	out.append(period + "); \n}\n");
  	out.append("function keepAliveRefresh() {\n");
  	out.append("xmlhttp.open ('GET', '" + url+ "', true);\n");
  	out.append("xmlhttp.onreadystatechange=function () {\n");
  	out.append("if (xmlhttp.readyState==4) {\n");
/*  out.append("if (xmlhttp.status != 200) {");
  	out.append("alert('Error keepAlive:' + xmlhttp.status)\n");
  	out.append("}\n");
*/
  	out.append("}\n");
  	out.append("\n}\n");
  	out.append("xmlhttp.send (null); \n}\n");
  	out.append("// FIN FUNCIONES KEEP-ALIVE\n");
  	out.append("keepAliveInit();");
  	out.append("</script>\n");
		return out;
	}

}
