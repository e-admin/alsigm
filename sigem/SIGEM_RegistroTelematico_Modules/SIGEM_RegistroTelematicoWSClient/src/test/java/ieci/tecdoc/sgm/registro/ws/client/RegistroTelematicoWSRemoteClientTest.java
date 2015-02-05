package ieci.tecdoc.sgm.registro.ws.client;

import ieci.tecdoc.sgm.core.exception.SigemException;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.telematico.ServicioRegistroTelematico;
import junit.framework.TestCase;

public class RegistroTelematicoWSRemoteClientTest extends TestCase {

	public void testGetService() throws SigemException {
		try{
//		<Domicilio_Notificacion>qwerqwer</Domicilio_Notificacion><Localidad>qwerqwe</Localidad><Provincia>rqwerqwe</Provincia><Codigo_Postal>28023</Codigo_Postal><Motivo_Queja>adsfa</Motivo_Queja><Acto_Impugnado>adsfads</Acto_Impugnado><Expone>asdfasd</Expone><Solicitar_Envio>No</Solicitar_Envio><Direccion_Electronica_Unica></Direccion_Electronica_Unica>
		String xmlrequest = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Solicitud_Registro><Version>1.1</Version><Datos_Registro><Numero_Registro/><Fecha_Presentacion/><Hora_Presentacion/></Datos_Registro><Datos_Firmados><Datos_Genericos><Organismo>Ayuntamiento de Madrid</Organismo><Idioma>es</Idioma><Remitente><Nombre>JOAQUIN</Nombre><Apellidos>REGODON HOLGUIN</Apellidos><Documento_Identificacion><Tipo/><Numero>05261042E</Numero></Documento_Identificacion><Correo_Electronico/></Remitente><Asunto><Codigo>TSUB</Codigo><Descripcion>Subvención</Descripcion></Asunto><Destino><Codigo>003</Codigo></Destino><Numero_Expediente/></Datos_Genericos><Datos_Especificos><Domicilio_Notificacion>qwerqwer</Domicilio_Notificacion><Localidad>qwerqwe</Localidad><Provincia>rqwerqwe</Provincia><Codigo_Postal>28023</Codigo_Postal><Motivo_Queja>adsfa</Motivo_Queja><Acto_Impugnado>adsfads</Acto_Impugnado><Expone>asdfasd</Expone><Solicitar_Envio>No</Solicitar_Envio><Direccion_Electronica_Unica/></Datos_Especificos><Documentos><Documento><Codigo>TRAM3D1</Codigo><Extension>PDF</Extension><Descripcion>Documento PDF</Descripcion><Hash>d13d19f69f05079f3f1394f2884bea97</Hash><Firma/><Contenido/></Documento></Documentos></Datos_Firmados><Firma/></Solicitud_Registro>";
		String datosEspeficos = "<Acto_Impugnado>acto</Acto_Impugnado><Motivo_Queja>motivo</Motivo_Queja><Expone>exposicion</Expone><Codigo_Postal>33203</Codigo_Postal><Localidad>localidad</Localidad><Provincia>provincia</Provincia><Domicilio_Notificacion>domicilio</Domicilio_Notificacion>";
		ieci.tecdoc.sgm.core.services.dto.Entidad entidad = new ieci.tecdoc.sgm.core.services.dto.Entidad();
		entidad.setIdentificador("00001");
		ServicioRegistroTelematico oServicio = LocalizadorServicios.getServicioRegistroTelematico();
    	byte[] justificante = oServicio.registrar("NBB4A43008B711DC8000EA8A35A7AE59", xmlrequest.getBytes(), datosEspeficos, "es", "999", 
    			"/home/sigem/SIGEM/jakarta-tomcat-5.0.28/webapps/SIGEM_RegistroTelematicoWeb/WEB-INF/classes/resources/plantilla.pdf", 
    			"/home/sigem/SIGEM/certificados/certificadoServidor.pfx", entidad);
    	System.out.println("asdfasdf");    	
		} catch(Throwable e){
    		System.out.println("error");
    	}
		System.out.println("asdfasdf");
	}

	public void testSetService() {
		fail("Not yet implemented");
	}

	public void testActualizarDocumentoRegistro() {
		fail("Not yet implemented");
	}

	public void testCrearPeticionRegistro() {
		fail("Not yet implemented");
	}

	public void testEliminarDocumentoRegistro() {
		fail("Not yet implemented");
	}

	public void testEliminarDocumentosTemporales() {
		fail("Not yet implemented");
	}

	public void testEstablecerDocumentosSubsanacion() {
		fail("Not yet implemented");
	}

	public void testEstablecerEstadoRegistro() {
		fail("Not yet implemented");
	}

	public void testInsertarDocumentoRegistro() {
		fail("Not yet implemented");
	}

	public void testObtenerContenidoDocumento() {
		fail("Not yet implemented");
	}

	public void testObtenerDatosDocumentosRegistro() {
		fail("Not yet implemented");
	}

	public void testObtenerDocumento() {
		fail("Not yet implemented");
	}

	public void testObtenerDocumentoRegistro() {
		fail("Not yet implemented");
	}

	public void testObtenerDocumentosRegistro() {
		fail("Not yet implemented");
	}

	public void testObtenerJustificanteRegistro() {
		fail("Not yet implemented");
	}

	public void testObtenerNumeroRegistro() {
		fail("Not yet implemented");
	}

	public void testObtenerPeticionRegistro() {
		fail("Not yet implemented");
	}

	public void testObtenerRegistro() {
		fail("Not yet implemented");
	}

	public void testObtenerRegistrosConsolidados() {
		fail("Not yet implemented");
	}

	public void testQuery() {
		fail("Not yet implemented");
	}

	public void testRegistrar() {
		fail("Not yet implemented");
	}

	public void testTieneDocumentos() {
		fail("Not yet implemented");
	}

	public void testHashCode() {
		fail("Not yet implemented");
	}

	public void testObject() {
		fail("Not yet implemented");
	}

	public void testFinalize() {
		fail("Not yet implemented");
	}

	public void testNotify() {
		fail("Not yet implemented");
	}

	public void testNotifyAll() {
		fail("Not yet implemented");
	}

	public void testWait() {
		fail("Not yet implemented");
	}

	public void testWaitLong() {
		fail("Not yet implemented");
	}

	public void testWaitLongInt() {
		fail("Not yet implemented");
	}

	public void testGetClass() {
		fail("Not yet implemented");
	}

	public void testClone() {
		fail("Not yet implemented");
	}

	public void testEquals() {
		fail("Not yet implemented");
	}

	public void testToString() {
		fail("Not yet implemented");
	}

}
