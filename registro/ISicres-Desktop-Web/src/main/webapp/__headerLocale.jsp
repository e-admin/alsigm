<!--
Include con las transformaciones de los locale Microsoft a Java.
-->

<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Locale" %>



	
	
<%!	
	List defaultLocales = new ArrayList();
	Object[][] microsoftLocales = new Object[4][3];
	Map microsoftLocalesID2DefaultLocales = new HashMap();
	Map microsoftLocalesName2DefaultLocales = new HashMap();
				
	public void jspInit() {
		defaultLocales.add(new Locale("es","ES"));
		defaultLocales.add(new Locale("eu","ES"));
		defaultLocales.add(new Locale("ca","ES"));
		defaultLocales.add(new Locale("gl","ES"));
		
		microsoftLocales[0][0] = "";
		microsoftLocales[0][1] = "Castellano";
		microsoftLocales[0][2] = new Long(10);
		microsoftLocales[1][0] = "EU_";
		microsoftLocales[1][1] = "Euskera";
		microsoftLocales[1][2] = new Long(45);
		microsoftLocales[2][0] = "CT_";
		microsoftLocales[2][1] = "Catal&aacute;n";
		microsoftLocales[2][2] = new Long(3);
		microsoftLocales[3][0] = "GL_";
		microsoftLocales[3][1] = "Gallego";
		microsoftLocales[3][2] = new Long(86);
	
		microsoftLocalesID2DefaultLocales.put(microsoftLocales[0][2], defaultLocales.get(0));
		microsoftLocalesID2DefaultLocales.put(microsoftLocales[1][2], defaultLocales.get(1));
		microsoftLocalesID2DefaultLocales.put(microsoftLocales[2][2], defaultLocales.get(2));
		microsoftLocalesID2DefaultLocales.put(microsoftLocales[3][2], defaultLocales.get(3));
	
		microsoftLocalesName2DefaultLocales.put(microsoftLocales[0][0], defaultLocales.get(0));
		microsoftLocalesName2DefaultLocales.put(microsoftLocales[1][0], defaultLocales.get(1));
		microsoftLocalesName2DefaultLocales.put(microsoftLocales[2][0], defaultLocales.get(2));
		microsoftLocalesName2DefaultLocales.put(microsoftLocales[3][0], defaultLocales.get(3));
	}
%>
