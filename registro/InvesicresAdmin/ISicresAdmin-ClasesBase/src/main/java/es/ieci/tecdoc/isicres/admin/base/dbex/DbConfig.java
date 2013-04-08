package es.ieci.tecdoc.isicres.admin.base.dbex;

import java.util.ResourceBundle;

public class DbConfig {
	
	public static String getDriver()
	{
		String driver = "";
		
		ResourceBundle props=ResourceBundle.getBundle(configFile);
		driver=props.getString("driver");
		
		return driver;
	}
	
	public static String getUrl()
	{
		String url = "";

		ResourceBundle props=ResourceBundle.getBundle(configFile);
		url=props.getString("url");
		
		return url;
	}
	
	public static String getUsuario()
	{
		String usuario = "";
		
		ResourceBundle props=ResourceBundle.getBundle(configFile);
		usuario=props.getString("usuario");
		
		return usuario;
	}
	
	public static String getClave()
	{
		String clave = "";

		ResourceBundle props=ResourceBundle.getBundle(configFile);
		clave=props.getString("clave");
		
		return clave;
	}

	public static boolean esDataSource()
	{
		String es = "";

		ResourceBundle props=ResourceBundle.getBundle(configFile);
		es=props.getString("esdatasource");
		
		if (es.compareTo("1") == 0)
			return true;
		else
			return false;
	}

	public static String getDataSource()
	{
		String datasource = "";

		ResourceBundle props=ResourceBundle.getBundle(configFile);
		datasource=props.getString("datasource");
		
		return datasource;
	}
	
	private static final String configFile = "SIGEM_Tramitacion_Electronica";

}
