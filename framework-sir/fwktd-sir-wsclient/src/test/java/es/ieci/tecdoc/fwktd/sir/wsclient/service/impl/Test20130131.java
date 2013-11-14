package es.ieci.tecdoc.fwktd.sir.wsclient.service.impl;

import java.util.Date;

import org.apache.commons.codec.binary.Base64;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import es.ieci.tecdoc.fwktd.sir.core.service.ServicioIntercambioRegistral;
import es.ieci.tecdoc.fwktd.sir.core.types.DocumentacionFisicaEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.IndicadorPruebaEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoDocumentoEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoRegistroEnum;
import es.ieci.tecdoc.fwktd.sir.core.vo.AnexoFormVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralFormVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralVO;

@ContextConfiguration({ 
	"/beans/fwktd-sir-wsclient-applicationContext.xml" })
public class Test20130131 extends AbstractJUnit4SpringContextTests {

	@Autowired
	private ServicioIntercambioRegistralWSClientImpl fwktd_sir_servicioIntercambioRegistralWSClientImpl;

	protected ServicioIntercambioRegistral getServicioIntercambioRegistral() {
		return fwktd_sir_servicioIntercambioRegistralWSClientImpl;
	}

	@Test
	public void testService() {
		Assert.assertNotNull(getServicioIntercambioRegistral());
	}

	@Test
	public void testEnviarAsientoRegistral() {
/*
        AsientoRegistralFormVO asientoForm = new AsientoRegistralFormVO();

        asientoForm.setCodigoEntidadRegistral("O00002061");

        // Origen o remitente
        asientoForm.setCodigoEntidadRegistralOrigen("O00002061");
        asientoForm.setDescripcionEntidadRegistralOrigen("REGISTRO GENERAL DE LA DIPUTACIÓN PROVINCIAL DE SORIA");
        asientoForm.setCodigoUnidadTramitacionOrigen("L01390232");
        asientoForm.setDescripcionUnidadTramitacionOrigen("AYUNTAMIENTO DE COLINDRES");
        asientoForm.setNumeroRegistro("201300000054");
        asientoForm.setFechaRegistro(new Date());
        
        // Destino
        asientoForm.setCodigoEntidadRegistralDestino("O00001223");
        asientoForm.setDescripcionEntidadRegistralDestino("MUTUALIDAD GENERAL DE FUNCIONARIOS CIVILES DEL ESTADO (MUFACE)");
        asientoForm.setCodigoUnidadTramitacionDestino("E00106104");
        asientoForm.setDescripcionUnidadTramitacionDestino("MUTUALIDAD GENERAL DE FUNCIONARIOS CIVILES DEL ESTADO (MUFACE)");

        // Interesado
        
        // Asunto
        asientoForm.setResumen("SE ANEXA DOCUMENTO COMPULSADO FIRMADO CON CERTIFICADO DE LA FNMT. DEBEN LLEGAR TRES ANEXOS: EL PDF ORIGINAL, EL XADES CON LA FIRMA Y OTRO PDF CON LA MARCA DE AGUA DE LA FIRMA.");

        // Anexos

        AnexoFormVO anexoForm = new AnexoFormVO();
        anexoForm.setNombreFichero("img_1359536569935.pdf");
        anexoForm.setCodigoFichero("O00002061_13_00002078_01_0001.pdf");
        anexoForm.setTipoDocumento(TipoDocumentoEnum.DOCUMENTO_ADJUNTO);
        anexoForm.setTipoMIME("application/pdf");
        anexoForm.setContenido("Contenido del anexo".getBytes());
        asientoForm.getAnexos().add(anexoForm);

        anexoForm = new AnexoFormVO();
        anexoForm.setNombreFichero("img_1359536569935.xsig");
        anexoForm.setCodigoFichero("O00002061_13_00002078_01_0002.xsig");
        anexoForm.setTipoDocumento(TipoDocumentoEnum.FICHERO_TECNICO_INTERNO);
        anexoForm.setCertificado(Base64.decodeBase64("MIID0zCCArugAwIBAgIKVd9kNAAAAAAADjANBgkqhkiG9w0BAQUFADAUMRIwEAYDVQQDEwlDRVggVEQtV0YwHhcNMTEwMzIyMTQyMjI4WhcNMTIwMzIyMTQzMjI4WjAXMRUwEwYDVQQDDAxSYcO6bCBOdcOxZXowgZ8wDQYJKoZIhvcNAQEBBQADgY0AMIGJAoGBAMB+T29tyeYf0kKMNH6SUKjfdeCfpHuBmhZt43nxTKJiYR5Qt/tYAsvOwGQc8hgcASel+tbyk3/4TpmjGJVZKnqcYFFCkchbBDIJ2J4lFaNLU+Xnpjj5Yp7YwcXAVFXNBqumlV8U8VI8vKu0frEILM/Et7TzXaTPUHMU3I8Fo0GHAgMBAAGjggGmMIIBojAOBgNVHQ8BAf8EBAMCBPAwRAYJKoZIhvcNAQkPBDcwNTAOBggqhkiG9w0DAgICAIAwDgYIKoZIhvcNAwQCAgCAMAcGBSsOAwIHMAoGCCqGSIb3DQMHMB0GA1UdDgQWBBRomqmxGm2Va3jf7RHNdp2ahHOUbTATBgNVHSUEDDAKBggrBgEFBQcDAzAfBgNVHSMEGDAWgBRneb340I2ZTq9cOvShaC38/80jejBlBgNVHR8EXjBcMFqgWKBWhilodHRwOi8vdGRvY2RjMS9DZXJ0RW5yb2xsL0NFWCUyMFRELVdGLmNybIYpZmlsZTovL1xcdGRvY2RjMVxDZXJ0RW5yb2xsXENFWCBURC1XRi5jcmwwgY0GCCsGAQUFBwEBBIGAMH4wPQYIKwYBBQUHMAKGMWh0dHA6Ly90ZG9jZGMxL0NlcnRFbnJvbGwvdGRvY2RjMV9DRVglMjBURC1XRi5jcnQwPQYIKwYBBQUHMAKGMWZpbGU6Ly9cXHRkb2NkYzFcQ2VydEVucm9sbFx0ZG9jZGMxX0NFWCBURC1XRi5jcnQwDQYJKoZIhvcNAQEFBQADggEBAHqVinteAuIsN4pSkLqyYmvWO18wp8/zCTBuqhjeCPHk7+e+FutE55quu0ffoPKRpx48ixFDDmM9e985LeYbkr9f28UmrEIWb4tPdo69xGD9lBu3yesATC0vxWnOsx2o2DYXAliF0s+/OPsIllQFImNm0+GxNui5KfN3s5UfOldqiCIdNNUbL9S1FRzOr317Ny4R2xv+G+eFTUtwiGnoHh5QVzUIeGltu3RLc8L3rXVUCYFq+7Bsxpz0lOjwGNoPEbi3nDmnPKvYpuw2kWjDq47OP21spXKYz3nXexFpSEIxJIuwfjufCS+EH5IizVX3fkXvO6lDuozhYhjNI/BEpGI="));
        anexoForm.setCodigoFicheroFirmado("O00002061_13_00002078_01_0001.pdf");
        anexoForm.setContenido("Contenido del anexo".getBytes());
        asientoForm.getAnexos().add(anexoForm);

        anexoForm = new AnexoFormVO();
        anexoForm.setNombreFichero("img_1359536569935_LOC.pdf");
        anexoForm.setCodigoFichero("O00002061_13_00002078_01_0003.pdf");
        anexoForm.setTipoDocumento(TipoDocumentoEnum.DOCUMENTO_ADJUNTO);
        anexoForm.setTipoMIME("application/pdf");
        anexoForm.setContenido("Contenido del anexo".getBytes());
        asientoForm.getAnexos().add(anexoForm);

        // Internos/control
//		asientoForm.setIdentificadorIntercambio("O00002061_13_00002078");
//		asientoForm.setTipoAnotacion(TipoAnotacionEnum.ENVIO);
		asientoForm.setTipoRegistro(TipoRegistroEnum.ENTRADA);
		asientoForm.setDocumentacionFisica(DocumentacionFisicaEnum.SIN_DOCUMENTACION_FISICA);
		asientoForm.setIndicadorPrueba(IndicadorPruebaEnum.PRUEBA);
        asientoForm.setCodigoEntidadRegistralInicio(asientoForm.getCodigoEntidadRegistralOrigen());
        asientoForm.setDescripcionEntidadRegistralInicio(asientoForm.getDescripcionEntidadRegistralOrigen());
		
        // Formulario genérico

        
		AsientoRegistralVO asiento = getServicioIntercambioRegistral().enviarAsientoRegistral(asientoForm);
		Assert.assertNotNull(asiento);
*/
	}

}
