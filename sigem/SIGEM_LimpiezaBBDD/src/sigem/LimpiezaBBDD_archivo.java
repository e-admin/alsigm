package sigem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LimpiezaBBDD_archivo {
	
	public static void main(String[] args) {
		
		if (args.length != 6) {
			System.out.println("USO: limpiezaBBDD [host] [puerto] [usuario] [password] [tipo] [Entidad]");
			System.exit(-1);
		}

		bd_host = args[0];
    	bd_puerto = args[1];
    	bd_user = args[2];
    	bd_pass = args[3];
    	bd_tipo = args[4];
		id_entidad = args[5];
		
		try {
			abrirBBDD();
		
			limpiarTablasFijas();
			limpiarTablasSelectivas();
			limpiarTablasVariables();
			reiniciarContadores();

		} catch (Exception e) {
			
		} finally {	
			try { cerrarBBDD(); } catch(Exception e) { }
		}
	}
	
	/**
	 * Método que abre la conexión con base de datos
	 * @throws Exception En caso de producirse error
	 */
    static private void abrirBBDD() throws Exception {
    	url = "jdbc:" + bd_tipo + "://" + bd_host + ":" + bd_puerto + "/" + NOMBRE_BBDD + "_" + id_entidad;

    	System.out.println("Conectado a: "+url);
	
    	// XXX aqui hay que consultar bd_tipo y pasar driver apropiado
    	Class.forName("org.postgresql.Driver");

    	conn=DriverManager.getConnection(url, bd_user, bd_pass);
    }
    
    /**
	 * Método que cierra la conexión con base de datos
	 * @throws Exception En caso de producirse error
	 */
    static private void cerrarBBDD() throws Exception {
    	if(conn!=null)
    		conn.close();
    }
    
    /**
     * Método que limpia los datos de las tablas fijas del sistema
     * @param tablas Listado de tablas fijas a limpiar
     */
    private static void limpiarTablasFijas() throws Exception {
    	Statement stmt=null;
    	for(int i=0; i<tablas_fijas.length; i++) {
    		stmt = conn.createStatement();
    		stmt.execute(SENTENCIA_DELETE + tablas_fijas[i]);
    		System.out.println("Limpiada tabla: " + tablas_fijas[i]);
    		stmt.close();
    	}
    }
    
    /**
     * Método que limpia los datos de las tablas variables del sistema
     * @param condicion Datos necesarios para obtener los ids de las tablas variables
     * @param tablas Listado de tablas variables con marcadores a limpiar
     */
    private static void limpiarTablasVariables() throws Exception {
    	Object[] valores = (Object[])obtenerValores();
    	if (valores.length > 0) {
    		Statement stmt=null;
    		String valor=null;
    		for(int i=0; i<valores.length; i++) {
    			for (int j=0; j<tablas_variables.length; j++) {
    				stmt = conn.createStatement();
    				valor = reemplazar((String)valores[i], tablas_variables[j]);
    				stmt.execute(SENTENCIA_DELETE + valor);
    				System.out.println("Limpiada tabla: " + valor);
    				stmt.close();
    			}
    		}
    	}
    }
    
    /**
     * Método que limpia los datos de las tablas con clausula de condicion
     * @param tablas Listado de tablas con clausula de condicion
     */
    private static void limpiarTablasSelectivas() throws Exception {
    	Statement stmt=null;
    	for(int i=0; i<tablas_borrado_selectivo.length; i++) {
    		stmt = conn.createStatement();
    		stmt.execute(SENTENCIA_DELETE + tablas_borrado_selectivo[i][0] + SENTENCIA_WHERE + tablas_borrado_selectivo[i][1]);
    		System.out.println("Limpiada tabla: " + tablas_borrado_selectivo[i][0] + " con clausula: " + tablas_borrado_selectivo[i][1]);
    		stmt.close();
    	}
    }
    
    private static void reiniciarContadores() throws Exception {
    	Statement stmt=null;
    	String strCondicion = null;
    	
    	for(int i=0; i<contador.length; i++) {
    		stmt = conn.createStatement();
    		strCondicion = SENTENCIA_UPDATE + contador[i][1] + SET + contador[i][0] + EQUALS + contador[i][2]; 
    		if (!"".equals(contador[i][3]))
    			strCondicion = strCondicion + SENTENCIA_WHERE + contador[i][3];
    		stmt.execute(strCondicion);
    		System.out.println("Reiniciando campo: " + contador[i][0] + " con valor: " + contador[i][2] + " en tabla: " + contador[i][1] + ("".equals(contador[i][3]) ? "" : " para condicion: " + contador[i][3]));
    		stmt.close();
    	}
    }
    
    /**
     * Método que obtiene los identificadores de las tablas variables en el sistemas
     * @param condicion Datos necesarios para obtener los ids de las tablas variables
     * @return Listado de identificadores de tablas variables
     */
    private static Object[] obtenerValores() throws Exception {
    	if (condicion.length >= 2) {
    		String strCondicion = "SELECT " + condicion[0] + " FROM " + condicion[1];
    		if (!"".equals(condicion[2]))
    			strCondicion = strCondicion + " WHERE " + condicion[2];
    	
    		Statement stmt=conn.createStatement();
    		ResultSet rs = stmt.executeQuery(strCondicion);
    	
	    	List valores = new ArrayList();
	    	while(rs.next())
	    		valores.add(rs.getString(condicion[0]));
	    	
	    	return valores.toArray();
    	} else {
    		return new Object[0];
    	}
    }
    
    /**
     * Método que compone el nombre de la tabla variable a limpiar
     * @param valor Valor a incluir dentro del parámetro tabla
     * @param tabla Nombre de la tabla con marcador (cadena##cadena)
     * @return Nombre de la tabla variable con su identificador incluido
     */
    private static String reemplazar (String valor, String tabla) {
    	int index = tabla.indexOf("##");
    	return tabla.substring(0, index) + valor + tabla.substring(index+"##".length(), tabla.length());
    }
    
    private static Connection conn;

    private static String url;
    
    private static final String SENTENCIA_DELETE = "DELETE FROM ";
    private static final String SENTENCIA_WHERE = " WHERE ";
    private static final String SENTENCIA_UPDATE = "UPDATE ";
    private static final String SET = " SET ";
    private static final String EQUALS = " = ";
    
    //Tablas fijas
    private static final String[] tablas_fijas = new String [] {
    	"ASGTSNSEC",
    	"ASGTSNSECUI",
    	"ASGTSNSECUDOC",
    	"ASGTUDOCSDF",
    	"ASGTUINSTALACIONREEA",
    	"ASGTUDOCENUI",
    	"ASGTUNIDADDOCRE",
    	"ASGTUINSTALACIONRE",
    	"ASGTRENTREGA",
    	"ASGTDETALLEPREV",
    	"ASGTPREVISION",
    	"ASGDUDOCENUI",
    	"ASGDUINSTALACION",
    	"ASGFUNIDADDOC",
    	"ASGPSNSEC",
    	"ASGPPRORROGA",
    	"ASGPPRESTAMO",
    	"ASGPCONSULTA",
    	"ASGPSOLICITUDUDOC",
    	"ASGFVOLUMENSERIE",
    	"ASGFNUMSECSEL",
    	"ASGFHISTUDOC",
    	"ASGFELIMSELUDOC",
    	"ASGFELIMSERIE",
    	"ASGFUDOCSDF",
    	"ADPCUSODOCVIT",
    	"ADPCTIPODOCVIT",
    	"ADPCTIPODOCPROC",
    	"ADPCDOCUMENTOVIT"
    };

    //Tablas variables. Se sustituirá el marcador ## por valores obtenidos mediante la condición obtenida en condicion_bbdd
    private static final String[] tablas_variables = new String [0]; 
    
    //Tablas con borrado selectivo. {Tabla, Condicion SQL}
    private static final String[][] tablas_borrado_selectivo = new String[][] {
    	{"AGOBJBLOQUEO", "TIPOOBJ = 4"},
    	{"ADVCTEXTCF","IDELEMENTOCF IN (SELECT ID FROM ASGFELEMENTOCF WHERE TIPO = 6)"},
    	{"ADVCTEXTLCF","IDELEMENTOCF IN (SELECT ID FROM ASGFELEMENTOCF WHERE TIPO = 6)"},
    	{"ADVCNUMCF","IDELEMENTOCF IN (SELECT ID FROM ASGFELEMENTOCF WHERE TIPO = 6)"},
    	{"ADVCFECHACF","IDELEMENTOCF IN (SELECT ID FROM ASGFELEMENTOCF WHERE TIPO = 6)"},
    	{"ADVCREFCF","IDELEMENTOCF IN (SELECT ID FROM ASGFELEMENTOCF WHERE TIPO = 6)"},
    	{"ADOCCLASIFCF","IDELEMENTOCF IN (SELECT ID FROM ASGFELEMENTOCF WHERE TIPO = 6)"},
    	{"ADOCDOCUMENTOCF","IDELEMENTOCF IN (SELECT ID FROM ASGFELEMENTOCF WHERE TIPO = 6)"},
    	{"ASGFELEMENTOCF","TIPO = 6"},
    	{"ADOCTCAPTURA","TIPOOBJ = 12"},
    	{"AADATOSTRAZA","IDTRAZA IN (SELECT ID FROM AATRAZA WHERE MODULO IN (2, 3) OR ACCION IN (100, 101, 102, 103, 104, 105, 106, 107, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 400, 402, 403, 404, 405, 406, 407, 417, 418, 419, 500, 501, 502, 503, 510, 511, 618, 634, 642, 643, 644, 645, 646, 647, 648, 649, 650, 651, 652, 653, 655, 656, 657, 1000, 1001, 1002, 1003, 1004, 1005, 1006, 1007, 1008))"},
    	{"AATRAZA","MODULO IN (2, 3) OR ACCION IN (100, 101, 102, 103, 104, 105, 106, 107, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119,120, 121, 400, 402, 403, 404, 405, 406, 407, 417, 418, 419, 500, 501, 502, 503, 510, 511, 618, 634, 642, 643, 644, 645, 646, 647, 648, 649, 650, 651, 652, 653, 655, 656, 657, 1000, 1001, 1002, 1003, 1004, 1005, 1006, 1007, 1008)"}
    };
	
    //Condiciones para obtener ids o nombres de tablas variables. {Nombre campo, Tabla, Condición SQL}
    private static final String[] condicion = new String [0];
    
    //Contadores a reiniciar. {Nombre campo, tabla, valor, Condicion SQL}
    private static final String[][] contador = new String[][] {
    	{"ESTADO","ASGDHUECO","'L'",""},
    	{"IDUINSTALACION","ASGDHUECO","NULL",""},
    	{"IDRELENTREGA","ASGDHUECO","NULL",""},
    	{"ORDENENRELACION","ASGDHUECO","0",""}
    };
                                
    private static String bd_host;
    private static String bd_puerto;
    private static String bd_user;
    private static String bd_pass;
    private static String id_entidad;
    private static String bd_tipo;
    
    //Bases de datos a limpiar
    private static final String NOMBRE_BBDD = "archivo";
}
