package ieci.tecdoc.test.sgm.nt.ws.client;

import ieci.tecdoc.sgm.base.dbex.DbUtil;
import ieci.tecdoc.sgm.core.exception.SigemException;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.notificaciones.CriterioBusquedaDocumentos;
import ieci.tecdoc.sgm.core.services.notificaciones.CriterioBusquedaNotificaciones;
import ieci.tecdoc.sgm.core.services.notificaciones.EstadoNotificacion;
import ieci.tecdoc.sgm.core.services.notificaciones.EstadoNotificacionBD;
import ieci.tecdoc.sgm.core.services.notificaciones.IdentificadorNotificacion;
import ieci.tecdoc.sgm.core.services.notificaciones.InfoDocumento;
import ieci.tecdoc.sgm.core.services.notificaciones.Notificacion;
import ieci.tecdoc.sgm.core.services.notificaciones.Notificaciones;
import ieci.tecdoc.sgm.core.services.notificaciones.ServicioNotificaciones;

import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import junit.framework.TestCase;

public class NotificacionesWebServiceRemoteClientTest extends TestCase {
	private ieci.tecdoc.sgm.core.services.dto.Entidad entidad = null;
	private Notificacion notificacion=null;
	public static String notiId=null;
	public static String codNoti=null;
	
	private ServicioNotificaciones oServicio=null;
	
	protected void setUp() throws Exception {
		entidad = new ieci.tecdoc.sgm.core.services.dto.Entidad();
		entidad.setIdentificador("000");
		entidad.setNombre("Entidad 1");
		
		oServicio = LocalizadorServicios.getServicioNotificaciones("SIGEM_ServicioNotificaciones.SIGEM.WEBSERVICE");
  
		if(notificacion!=null) return;
		notificacion=new Notificacion();
		
		notificacion.setNombreDocumentos(Arrays.asList(new String[]{"prueba1.txt"}));
		notificacion.setExtension(Arrays.asList(new String[]{"txt"}));
		notificacion.setGuid(Arrays.asList(new String[]{UUID.randomUUID().toString()}));
		notificacion.setDocumentos(Arrays.asList(new byte[][]{"12345".getBytes()}));
        
		notificacion.setDeu("71882675QMOR");
		notificacion.setNifDest("71882675Q");
		
		//notificacion.setApellidosDest(apellidosDest);
		//notificacion.setCodigoNoti(codigoNoti);
		//notificacion.setCodigoPostal(codigoPostal);
		//notificacion.setCorreoDest(correoDest);
		//notificacion.setDescripcionEstado(descripcionEstado);
		//notificacion.setEscaleraDireccion(escaleraDireccion);
		//notificacion.setEstado(estado);
		//notificacion.setFechaActualiEstado(fechaActualiEstado);
		//notificacion.setFechaEntrega(fechaEntrega);
		//notificacion.setFechaRegistro(fechaRegistro);
		//notificacion.setIdioma(idioma);
		//notificacion.setMovil(movil);
		//notificacion.setMunicipio(municipio);
		//notificacion.setNombreDest(nombreDest);
		//notificacion.setNotiId(notiId);
		//notificacion.setNumeroDireccion(numeroDireccion);
		notificacion.setNumeroExpediente("EXP2011/000009");
		//notificacion.setNumeroRegistro("EXP2011/000009");
		//notificacion.setOrganismo(organismo);
		//notificacion.setPisoDireccion(pisoDireccion);
		notificacion.setProcedimiento("PCD-5");
		//notificacion.setProvincia(provincia);
		//notificacion.setPuertaDireccion(puertaDireccion);
		notificacion.setSistemaId("SISNOT");
		//notificacion.setTelefono(telefono);
		notificacion.setAsunto("Prueba de Notificación");
		notificacion.setTexto("Texto de prueba");
		//notificacion.setTipo(tipo);
		//notificacion.setTipoCorrespondencia(tipoCorrespondencia);
		//notificacion.setTipoViaDireccion(tipoViaDireccion);
		notificacion.setUsuario("71882675Q");
		//notificacion.setViaDireccion(viaDireccion);
	}
		 
	public void testAltaNotificacion(){
		try{  
			IdentificadorNotificacion noti=oServicio.altaNotificacion(notificacion, entidad);
			notiId=noti.getCodigoDeNotificacion();
			assertTrue(noti.getCodigoDeNotificacion()!=null);
			return;
		}catch(Exception e){ 
			e.printStackTrace();
		}
		fail();
	}
	
	public void testDetalleNotificacionByNotiId() {
		try{  
			Notificacion noti=oServicio.detalleNotificacionByNotiId(notiId,entidad);
			assertNotNull(noti.getCodigoNoti());
			codNoti=noti.getCodigoNoti();
			return;
		}catch(Exception e){ 
			e.printStackTrace();
		}
		fail();
	}
	
	public void testDetalleNotificacion() {
		IdentificadorNotificacion identificador=new IdentificadorNotificacion();
		identificador.setCodigoDeNotificacion(codNoti);
		try{  
			Notificacion noti=oServicio.detalleNotificacion(identificador,entidad);
			assertNotNull(noti.getNotiId());
			return;
		}catch(Exception e){ 
			e.printStackTrace();
		}
		fail();
	}
	
	public void testConsultarNotificaciones() {
		try {
			CriterioBusquedaNotificaciones criterio=new CriterioBusquedaNotificaciones();
			criterio.setNotificacion(codNoti);
			Notificaciones notificaciones=oServicio.consultarNotificaciones(criterio, true, entidad);
			assertTrue(notificaciones.getNotificaciones().size()>0);
			assertNotNull(notificaciones.getNotificaciones().get(0));
			return;
		} catch (SigemException e) {
			e.printStackTrace();
		}
		fail();
	}
	
	public void testObtenerEstado() {
		try{  
			EstadoNotificacion estado=oServicio.obtenerEstado(notiId, entidad);
			assertNotNull(estado.getEstado());
			return;
		}catch(Exception e){ 
			e.printStackTrace();
		}
		fail();
	}
	
	public void testObtenerEstadoBD() {
		try{  
			EstadoNotificacionBD estado=oServicio.obtenerEstadoBD(new Integer(0), entidad);
			assertNotNull(estado.getId());
			return;
		}catch(Exception e){ 
			e.printStackTrace();
		}
		fail();
	}
	
	
	public void testActualizaEstado() {
		try{			
			oServicio.actualizaEstado(notificacion.getNumeroExpediente(),
					new Integer(0), new Date(), notificacion.getNifDest(), notiId, entidad);
			assertTrue(true);
			return;
		}catch(Exception e){ 
			e.printStackTrace();
		}
		fail();
	}
	
	public void testRecuperaDocumento() {
		try{ 
			//los 4 campos obligatorios
			CriterioBusquedaDocumentos criterio=new CriterioBusquedaDocumentos();
			criterio.setNifDestinatario(notificacion.getNifDest());
			criterio.setCodigoNoti(codNoti);
			criterio.setExpediente(notificacion.getNumeroExpediente());
			criterio.setCodigDoc("prueba1.txt");
			
			InfoDocumento info=oServicio.recuperaDocumento(criterio, entidad);
			assertNotNull(info.getGuid());
			return;
		}catch(Exception e){ 
			e.printStackTrace();
		}
		fail();
	}
		 	 
	public void testActualizaEstados() {
		try{  
			oServicio.actualizaEstados(entidad);
			assertTrue(true);
			return;
		}catch(Exception e){ 
			e.printStackTrace();
		}
		fail();
	}
	
	
}
