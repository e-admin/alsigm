package ieci.tecdoc.sgm.catalogo_tramites;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;

import ieci.tecdoc.sgm.catalogo_tramites.exception.CatalogoTramitesExcepcion;
import ieci.tecdoc.sgm.core.exception.SigemException;
import ieci.tecdoc.sgm.core.services.ConstantesServicios;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.catalogo.ServicioCatalogoTramites;
import ieci.tecdoc.sgm.core.services.catalogo.Tramite;
import ieci.tecdoc.sgm.core.services.cripto.firma.ServicioFirmaDigital;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.tramitacion.ServicioTramitacion;
import ieci.tecdoc.sgm.core.test.SigemBaseTestCase;
import sun.misc.BASE64Encoder;
import junit.framework.TestCase;

public class ServicioCatalogoTramitesTest extends SigemBaseTestCase{
	private static final String LOCAL_SERVICE_PROCEDURES_IMPL="SIGEM_ServicioCatalogoTramites.SIGEM.API";
	
//	protected void setUp() throws Exception {
//        try {
//            // Create initial context
//            System.setProperty(Context.INITIAL_CONTEXT_FACTORY,"org.apache.naming.java.javaURLContextFactory");
//            System.setProperty(Context.URL_PKG_PREFIXES,"org.apache.naming");            
//            InitialContext ic = new InitialContext();
//
//            ic.createSubcontext("java:");
//            ic.createSubcontext("java:/comp");
//            ic.createSubcontext("java:/comp/env");
//            ic.createSubcontext("java:/comp/env/jdbc");
//           
//            // Construct DataSource
//            OracleConnectionPoolDataSource ds = new OracleConnectionPoolDataSource();
//            ds.setURL("jdbc:oracle:thin:@host:port:db");
//            ds.setUser("MY_USER_NAME");
//            ds.setPassword("MY_USER_PASSWORD");
//            
//            ic.bind("java:/comp/env/jdbc/nameofmyjdbcresource", ds);
//        } catch (NamingException ex) {
//            Logger.getLogger(MyDAOTest.class.getName()).log(Level.SEVERE, null, ex);
//        }
//	}
	
	public ServicioCatalogoTramitesTest(){
		super();
		try{ setUp(); } 
		catch(Exception e){ fail("Error al inicializar spring JNDI Datasources: "+e); }
	}
	
	public void testCheckTopicOfficeCodes() {
		try {
			Tramite tramite=getTramite();
			Entidad entidad=getEntidad();
			checkTopicOfficeCodes(tramite,entidad);
			tramite.setOficina("997");
			try{ 
				checkTopicOfficeCodes(tramite,entidad);
			}catch(Exception e){
				Exception eTarget=getTargetException(e);
				if(eTarget instanceof CatalogoTramitesExcepcion){
					if(((CatalogoTramitesExcepcion)eTarget).getErrorCode()==4001046){
						assertTrue(true);
						return;
					}
				}
				e.printStackTrace();
			}
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail();
	}
	
	private void checkTopicOfficeCodes(Tramite procedure, Entidad entidad) throws Exception{
		ServicioCatalogoTramites servicio=LocalizadorServicios.getServicioCatalogoTramites(LOCAL_SERVICE_PROCEDURES_IMPL);
		//if(!(servicio instanceof ServicioCatalogoTramitesAdapter)) throw new Exception("No se está llamando al API con "+LOCAL_SERVICE_PROCEDURES_IMPL); 
		
		Method method = servicio.getClass().getDeclaredMethod("checkTopicOfficeCodes",new Class[]{Tramite.class,Entidad.class});
		method.setAccessible(true);
		method.invoke(servicio,new Object[]{procedure,entidad});
		method.setAccessible(false);
	}
			
	private Entidad getEntidad(){
		Entidad entidad=new Entidad();
		entidad.setIdentificador("000");
		entidad.setNombre("");
		return entidad;
	}
	
	private Tramite getTramite(){
		Tramite tramite=new Tramite();
//		tramite.setAddressee("");
//		tramite.setDescription("");
//		tramite.setDocuments(null);
//		tramite.setFirma(false);
//		tramite.setId("");
//		tramite.setIdProcedimiento("");
//		tramite.setLimitDocs(true);
		tramite.setOficina("999");
		tramite.setTopic("TLIC");
		return tramite;
	}
	
	private Exception getTargetException(Exception e){
		try{
			Field field = e.getClass().getDeclaredField("target");
			field.setAccessible(true);
			Object eTarget=field.getType().newInstance();
			eTarget=(Exception)field.get(e);
			return (Exception)eTarget;
		}catch(Exception ex){
			e.printStackTrace();
		}
		return null;
		
	}
}
