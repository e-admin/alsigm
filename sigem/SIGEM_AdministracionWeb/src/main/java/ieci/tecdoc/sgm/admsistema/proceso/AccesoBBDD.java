package ieci.tecdoc.sgm.admsistema.proceso;

import ieci.tecdoc.sbo.util.idoccrypto.IdocCryptoUtil;
import ieci.tecdoc.sgm.admsistema.util.Defs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * esta clase tiene dos funciones:
 * 1) recoger los campos de los datos ftp desde BBDD
 *
 * 2) actualizar la tabla de BD con la informacion que se le pasa relativa al FTP creado
 * y actualizar usuario, contraseña (encriptada), IP y path de FTP
 *
 * la informacion es pasada en un archivo que se leeara como un .properties, asi se evitara que se pueda ver la informacion
 *
 * .fran.
 */ 

public class AccesoBBDD {
	
    // nombre de la BBDD, detrás lleva el id entidad (registro_entidad)
    final public static String REGISTRO="registro";

    // parametros para BD
    final public static String BD_HOST="bd_host";
    final public static String BD_PUERTO="bd_puerto";
    final public static String BD_USUARIO="bd_usuario";
    final public static String BD_PASS="bd_pass";
    final public static String ID_ENTIDAD="id_entidad";
    final public static String BD_TIPO="bd_tipo"; // postgres, oracle, db2, sqlserver
    final public static String BD="bd"; // sigemAdmin, ivolrephdr
    final public static String BD_INSTANCE="bd_instancia";
    
    // parametros para ftp
    final public static String FTP_HOST="ftp_host";
    final public static String FTP_PUERTO="ftp_puerto";
    final public static String FTP_PATH="ftp_path";
    final public static String FTP_USUARIO="ftp_usuario";
    final public static String FTP_PASS="ftp_pass";

    private static final String FTP_PWD_TO_CRYPT="IVOLFTP";

    static private String url;

    static private String ftp_host;
    static private String ftp_puerto;
    static private String ftp_path;
    static private String ftp_user;
    static private String ftp_pass;

    static private String bd_host;
    static private String bd_puerto;
    static private String bd_user;
    static private String bd_pass;
    static private String bd_tipo;
    static private String bd;
    static private String id_entidad;
    
    static private String bd_instance;

    static private String patron="(.*)\\|\"(.*),(.*),(.*),(.*),.*([0-9][0-9][0-9])_([^/]*)\"\\|(.*)";

    final static private int GROUP_HOST=2;
    final static private int GROUP_PUERTO=3;
    final static private int GROUP_USUARIO=4;
    final static private int GROUP_PASSWORD=5;
    final static private int GROUP_ID_ENT=6;
    final static private int GROUP_PATH=7;

    static private Pattern pat=Pattern.compile(patron);
    static private Matcher mat;
  
    final private static String SELECT="SELECT * FROM ivolrephdr";

    static private Connection conn;
    
    
    /*
     * funcion 1, recoger la informacion en BBDD (exportar)
     *
     * las keys que establece son FTP_HOST, FTP_PUERTO, FTP_PATH, FTP_USUARIO, FTP_PASS
     * los datos de conexion de BBDD los recoge de los keys en bdData BD_HOST, BD_PUERTO, BD_USUARIO, BD_PASS
     */
    static public Hashtable recoger(Hashtable bdData) throws Exception {
		Hashtable hash;
		try {
		    cargarPropiedades(bdData);
	
		    abrirBBDD();
	
		    Statement stmt=conn.createStatement();
		    ResultSet rs=stmt.executeQuery(SELECT);
	
		    String info=null;
		    Vector vPaths=new Vector();
		    while(rs.next()) {
				info=rs.getString("info");
				String[]p=recogerPath(info);
				if (p != null) {
					vPaths.add(p[0]+"_"+p[1]);
				}
		    }
	
		    hash=new Hashtable();
	
		    String[]paths=new String[vPaths.size()];
		    vPaths.toArray(paths);
	
		    hash.put(FTP_PATH, paths);
	
		    // añadimos la informacion restante
		    recoger(info, hash);
		    String p=(String)hash.get(FTP_PASS);
		    p=IdocCryptoUtil.decryptPassword(p, FTP_PWD_TO_CRYPT);
		    hash.put(FTP_PASS, p);
		} finally {
		    cerrarBBDD();
		}
	
		return hash;
    }

    public static void cambiar(Hashtable bdData) throws Exception {
		try {
			
			cargarPropiedades(bdData);
			
			abrirBBDD();
			
			ftp_pass = IdocCryptoUtil.encryptPassword(ftp_pass, "IVOLFTP");
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(SELECT);
			
			while (rs.next()) {
				
				int id = rs.getInt("id");
				String info = rs.getString("info");
				
				String cadena = establecer(info, ftp_host, ftp_puerto, ftp_user, ftp_pass, id_entidad);

				PreparedStatement pstmt = conn.prepareStatement("UPDATE IVOLREPHDR SET info = ? WHERE id = ? ");
				pstmt.setString(1, cadena);
				pstmt.setInt(2, id);
				pstmt.executeUpdate();
			}

			rs.close();
			
		} finally {
			cerrarBBDD();
		}
		
		return;
	}

    private static String establecer(String orig, String host, String puerto, String usuario, String password, String idEntidad) {
		String cad = "$1|\"" + host + "," + puerto + "," + usuario + "," + password + "," + idEntidad + "_$7\"|1|3|3|0|0";
		Matcher matcher = pat.matcher(orig);
		return matcher.replaceAll(cad);
	}

    static private void abrirBBDD() throws Exception {
    	if (bd_tipo.equalsIgnoreCase("postgresql")) {
    		url = "jdbc:postgresql://" + bd_host + ":" + bd_puerto + "/" + bd;
    		Class.forName(Defs.DRIVER_POSTGRES);
    	} else if (bd_tipo.equalsIgnoreCase("db2")) { 
    		url = "jdbc:db2://" + bd_host + ":" + bd_puerto + "/" + bd;
    		Class.forName(Defs.DRIVER_DB2);
    	} else if (bd_tipo.equalsIgnoreCase("oracle")) {
    		url = "jdbc:oracle:thin:@" + bd_host + ":" + bd_puerto + ":" + bd;
    		Class.forName(Defs.DRIVER_ORACLE);
    	} else if (bd_tipo.equalsIgnoreCase("sqlserver")) {
    		url = "jdbc:sqlserver://" + bd_host + ":" + bd_puerto + ";DatabaseName=" + bd;
    		Class.forName(Defs.DRIVER_SQLSERVER);
    	}
    	
		conn=DriverManager.getConnection(url, bd_user, bd_pass);
    }

    static private void cerrarBBDD() throws Exception {
		if(conn!=null)
		    conn.close();
    }

    static private void cargarPropiedades(Hashtable hash) throws Exception {
		ftp_host=(String)hash.get(FTP_HOST);
		ftp_puerto=(String)hash.get(FTP_PUERTO);
		if(ftp_puerto==null)
		    ftp_puerto="21";
		ftp_path=(String)hash.get(FTP_PATH);
		ftp_user=(String)hash.get(FTP_USUARIO);
		ftp_pass=(String)hash.get(FTP_PASS);
	
		bd_host=(String)hash.get(BD_HOST);
		bd_puerto=(String)hash.get(BD_PUERTO); // , "5432");
		bd_user=(String)hash.get(BD_USUARIO);
		bd_pass=(String)hash.get(BD_PASS);
		bd_tipo=(String)hash.get(BD_TIPO);
		bd=(String)hash.get(BD);
		id_entidad=(String)hash.get(ID_ENTIDAD);
		
		bd_instance = (String)hash.get(BD_INSTANCE);
	
		if (bd_instance == null || bd_instance.equals(""))
			bd = bd + "_" + id_entidad;
		else bd = bd_instance;
    }

    /*
     * establecer keys: FTP_HOST, FTP_PUERTO, FTP_USUARIO, FTP_PASS
     * FTP_PATH no se establece
     */
    static private void recoger(String orig, Hashtable hash) {
		mat=pat.matcher(orig);
	
		mat.matches();
	
		String keys[]={FTP_HOST, FTP_PUERTO, FTP_USUARIO, FTP_PASS};
		int iKeys[]={GROUP_HOST, GROUP_PUERTO, GROUP_USUARIO, GROUP_PASSWORD};
		for(int i=0;i<keys.length;i++) {
		    String valor=mat.group(iKeys[i]);
		    hash.put(keys[i], valor);
		}
    }

    static private String[] recogerPath(String orig) {
		mat=pat.matcher(orig);
	
		boolean matches = mat.matches();

		if (matches) {
			String idEnt=mat.group(GROUP_ID_ENT);
			String path=mat.group(GROUP_PATH);
		
			return new String[]{idEnt, path};
		} else {
			return null;
		}
    }

}
