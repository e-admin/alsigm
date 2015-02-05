package ieci.tdw.ispac.ispacweb.plugin;

import javax.servlet.ServletException;

import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;

public class ParametersPlugin implements PlugIn {
	//private ModuleConfig config = null;
	private ActionServlet servlet = null;

	// Parámetros
	private String ispacbase;
	private String skin;

	// Métodos get y set de los parámetros
	public String getSkin() {
		return skin;
	}
	public void setSkin(String skin) {
		this.skin = skin;
	}


	public String getISPACBase() {
		return ispacbase;
	}
	public void setISPACBase(String ispacbase) {
		this.ispacbase = ispacbase;
	}


	public void destroy() {
		// TODO Auto-generated method stub
		servlet.getServletContext().removeAttribute("ispacbase");
		servlet.getServletContext().removeAttribute("skin");

		servlet = null;
		//config = null;
	}

	public void init(ActionServlet servlet, ModuleConfig config)
			throws ServletException {
		// TODO Auto-generated method stub
		//this.config = config;
		this.servlet = servlet;

		servlet.getServletContext().setAttribute("ispacbase", ispacbase);
		servlet.getServletContext().setAttribute("skin", skin);
	}

}
