package sigem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LimpiezaBBDD {
	
	public static void main(String[] args) {
		
		if (args.length != 7) {
			System.out.println("USO: limpiezaBBDD [host] [puerto] [usuario] [password] [tipo] [BBDD] [Entidad]");
			System.exit(-1);
		}

		bd_host = args[0];
    	bd_puerto = args[1];
    	bd_user = args[2];
    	bd_pass = args[3];
    	bd_tipo = args[4];
    	bd_name = args[5];
		id_entidad = args[6];
		
		try {
			abrirBBDD();
		
			if (ETRAMITACION.equals(bd_name)) {
				limpiarTablasFijas(tablas_fijas_eTramitacion);
				limpiarTablasSelectivas(tablas_borrado_selectivo_eTramitacion);
				limpiarTablasVariables(condicion_eTramitacion, tablas_variables_eTramitacion);
				reiniciarContadores(contador_eTramitacion);
			} else if (REGISTRO.equals(bd_name)) {
				limpiarTablasFijas(tablas_fijas_registro);
				limpiarTablasSelectivas(tablas_borrado_selectivo_registro);
				limpiarTablasVariables(condicion_registro, tablas_variables_registro);
				reiniciarContadores(contador_registro);
			} else if (ARCHIVO.equals(bd_name)) {
				limpiarTablasFijas(tablas_fijas_archivo);
				limpiarTablasSelectivas(tablas_borrado_selectivo_archivo);
				limpiarTablasVariables(condicion_archivo, tablas_variables_archivo);
				reiniciarContadores(contador_archivo);
			} else if (TRAMITADOR.equals(bd_name)) {
				limpiarTablasFijas(tablas_fijas_tramitador);
				limpiarTablasSelectivas(tablas_borrado_selectivo_tramitador);
				limpiarTablasVariables(condicion_tramitador, tablas_variables_tramitador);
				reiniciarContadores(contador_tramitador);
			}
		} catch (Exception e) {
			System.out.println(e.fillInStackTrace());
		} finally {	
			try { cerrarBBDD(); } catch(Exception e) { }
		}
	}
	
	/**
	 * Método que abre la conexión con base de datos
	 * @throws Exception En caso de producirse error
	 */
    static private void abrirBBDD() throws Exception {
    	url = "jdbc:" + bd_tipo + "://" + bd_host + ":" + bd_puerto + "/" + bd_name + "_" + id_entidad;

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
    private static void limpiarTablasFijas(String[] tablas) throws Exception {
    	Statement stmt=null;
    	for(int i=0; i<tablas.length; i++) {
    		stmt = conn.createStatement();
    		stmt.execute(SENTENCIA_DELETE + tablas[i]);
    		System.out.println("Limpiada tabla: " + tablas[i]);
    		stmt.close();
    	}
    }
    
    /**
     * Método que limpia los datos de las tablas variables del sistema
     * @param condicion Datos necesarios para obtener los ids de las tablas variables
     * @param tablas Listado de tablas variables con marcadores a limpiar
     */
    private static void limpiarTablasVariables(String[] condicion, String[] tablas) throws Exception {
    	Object[] valores = (Object[])obtenerValores(condicion);
    	if (valores.length > 0) {
    		Statement stmt=null;
    		String valor=null;
    		for(int i=0; i<valores.length; i++) {
    			for (int j=0; j<tablas.length; j++) {
    				stmt = conn.createStatement();
    				valor = reemplazar((String)valores[i], tablas[j]);
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
    private static void limpiarTablasSelectivas(String[][] tablas) throws Exception {
    	Statement stmt=null;
    	for(int i=0; i<tablas.length; i++) {
    		stmt = conn.createStatement();
    		stmt.execute(SENTENCIA_DELETE + tablas[i][0] + SENTENCIA_WHERE + tablas[i][1]);
    		System.out.println("Limpiada tabla: " + tablas[i][0] + " con clausula: " + tablas[i][1]);
    		stmt.close();
    	}
    }
    
    private static void reiniciarContadores(String[][] contador) throws Exception {
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
    private static Object[] obtenerValores(String[] condicion) throws Exception {
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
    private static final String[] tablas_fijas_eTramitacion = new String [] {
    	"sgm_au_usuarios",
    	"sgm_ctfichhito",
    	"sgm_cthitoestexp",
    	"sgm_cthitohistexp",
    	"sgm_ctinfoexp",
    	"sgm_ctintexp",
    	"sgm_ntfichnotif",
    	"sgm_ntinfonotif",
    	"sgm_pe_liquidaciones",
    	/*"sgm_pe_tasas",*/ //No se si se debería borrar
    	"sgmafsesion_info",
    	"sgmcertificacion",
    	"sgmctnotificacion",
    	"sgmctpago",
    	"sgmctsubsanacion",
    	"sgmntinfo_documento",
    	"sgmntinfo_estado_noti",
    	"sgmntinfo_notificacion",
    	"sgmrdedocumentos",
    	"sgmrtregistro",
    	"sgmrtregistro_documentos",
    	"sgmrtregistro_secuencia", //Da igual (en teoría) borrarla que reiniciarla
    	"sgmrttmp_documentos"
    };
    private static final String[] tablas_fijas_registro = new String [0];
    private static final String[] tablas_fijas_archivo = new String [] {
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
    private static final String[] tablas_fijas_tramitador = new String [] {
    	"pub_errores",
    	"pub_hitos_activos",
    	"pub_ultimo_hito_tratado",
    	"spac_anotaciones",
    	"spac_avisos_electronicos",
    	"spac_docobj",
    	"spac_dt_documentos",
    	"spac_dt_intervinientes",
    	"spac_dt_tramites",
    	"spac_exp_relacionados",
    	"spac_expedientes",
    	"spac_fases",
    	"spac_hitos",
    	"spac_infotramite",
    	"spac_procesos",
    	"spac_s_bloqueos",
    	"spac_s_sesiones",
    	"spac_s_vars",
    	"spac_sincnodo",
    	"spac_tramitaciones_agrupadas",
    	"spac_tramitcs_agrupadas_exps",
    	"spac_tramites"
    };

    //Tablas variables. Se sustituirá el marcador ## por valores obtenidos mediante la condición obtenida en condicion_bbdd
    private static final String[] tablas_variables_eTramitacion = new String [0]; 
    private static final String[] tablas_variables_registro = new String [] {
    	"a##clfhx",
    	"a##clfhx",
    	"a##doch",
    	"a##dochx",
    	"a##fdrh",
    	"a##pageh",
    	"a##pagehx",
    	"a##sf",
    	"a##xf",
    	"a##xnid"
    };
    private static final String[] tablas_variables_archivo = new String [0]; 
    private static final String[] tablas_variables_tramitador = new String [] {
    	"##"
    }; 
    
    //Tablas con borrado selectivo. {Tabla, Condicion SQL}
    private static final String[][] tablas_borrado_selectivo_eTramitacion = new String[0][0];
    private static final String[][] tablas_borrado_selectivo_registro = new String[0][0];
    private static final String[][] tablas_borrado_selectivo_archivo = new String[][] {
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
    private static final String[][] tablas_borrado_selectivo_tramitador = new String[0][0];
	
    //Condiciones para obtener ids o nombres de tablas variables. {Nombre campo, Tabla, Condición SQL}
    private static final String[] condicion_eTramitacion = new String [0];
    private static final String[] condicion_registro = new String [] {"id", "idocarchhdr", ""};
    private static final String[] condicion_archivo = new String [0];
    private static final String[] condicion_tramitador = new String [] {"nombre", "spac_ct_entidades", "tipo=1"};
    
    //Contadores a reiniciar. {Nombre campo, tabla, valor, Condicion SQL}
    private static final String[][] contador_eTramitacion = new String[][] {
    	{"numsec","sgm_pe_al12nsec","1",""},
    	{"numsec","sgm_pe_al3nsec","1", ""}
    };
    private static final String[][] contador_registro = new String[0][0]; 
    private static final String[][] contador_archivo = new String[][] {
    	{"ESTADO","ASGDHUECO","'L'",""},
    	{"IDUINSTALACION","ASGDHUECO","NULL",""},
    	{"IDRELENTREGA","ASGDHUECO","NULL",""},
    	{"ORDENENRELACION","ASGDHUECO","0",""}
    };
    private static final String[][] contador_tramitador = new String[][] {
    	{"contador","spac_numexp_contador","1",""},
    	{"valor","spac_vars","'14'","nombre='ID_TASK_SOLICITUD_SUBSANACION'"},
    	{"valor","spac_vars","'DC'","nombre='ESTADOADM_DOC_COMPLETA'"},
    	{"valor","spac_vars","'DI'","nombre='ESTADOADM_DOC_INCOMPLETA'"},
    	{"valor","spac_vars","'PR'","nombre='ESTADO_ADM_INICIAL'"},
    	{"valor","spac_vars","'1'","nombre='DEFAULT_CALENDAR_ID'"}
    }; 
                                
    private static String bd_host;
    private static String bd_puerto;
    private static String bd_user;
    private static String bd_pass;
    private static String bd_name;
    private static String id_entidad;
    private static String bd_tipo;
    
    //Bases de datos a limpiar
    private static final String ETRAMITACION = "eTramitacion";
    private static final String REGISTRO = "registro";
    private static final String ARCHIVO = "archivo";
    private static final String TRAMITADOR = "tramitador";
}
