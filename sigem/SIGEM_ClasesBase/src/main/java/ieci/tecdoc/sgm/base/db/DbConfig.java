package ieci.tecdoc.sgm.base.db;

import java.util.ResourceBundle;

public class DbConfig {
	
	public static String getDriver()
	{
		String driver = "";
		
		ResourceBundle props=ResourceBundle.getBundle(filename);
		driver=props.getString("driver");
		
		return driver;
	}
	
	public static String getUrl()
	{
		String url = "";

		ResourceBundle props=ResourceBundle.getBundle(filename);
		url=props.getString("url");
		
		return url;
	}
	
	public static String getUsuario()
	{
		String usuario = "";
		
		ResourceBundle props=ResourceBundle.getBundle(filename);
		usuario=props.getString("usuario");
		
		return usuario;
	}
	
	public static String getClave()
	{
		String clave = "";

		ResourceBundle props=ResourceBundle.getBundle(filename);
		clave=props.getString("clave");
		
		return clave;
	}

	public static boolean isDataSource()
	{
		String es = "";

		ResourceBundle props=ResourceBundle.getBundle(filename);
		es=props.getString("esdatasource");
		
		if (es.compareTo("1") == 0)
			return true;
		else
			return false;
	}

	public static String getDataSource()
	{
		String datasource = "";

		ResourceBundle props=ResourceBundle.getBundle(filename);
		datasource=props.getString("datasource");
		
		return datasource;
	}
	
	private static String filename = "SIGEM_Tramitacion_Electronica";

}
