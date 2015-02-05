package ieci.tecdoc.sgm.core.services;

import sun.misc.BASE64Encoder;
import ieci.tecdoc.sgm.core.exception.SigemException;
import ieci.tecdoc.sgm.core.services.cripto.validacion.ResultadoValidacion;
import ieci.tecdoc.sgm.core.services.cripto.validacion.ServicioCriptoValidacion;
import ieci.tecdoc.sgm.core.services.cripto.validacion.InfoCertificado;
import ieci.tecdoc.sgm.cripto.validacion.impl.afirma.xml.ResultadoValidarCertificado;
import junit.framework.Assert;
import junit.framework.TestCase;

public class ServiceLocatorTest extends TestCase {

	public static final String CERTIFICADO_B64 = 
	"MIIFJDCCBI2gAwIBAgIEPH2KhDANBgkqhkiG9w0BAQUFADA2MQswCQYDVQQGEwJFUzENMAsGA1UE\n\r"+
	"ChMERk5NVDEYMBYGA1UECxMPRk5NVCBDbGFzZSAyIENBMB4XDTA0MTEzMDE0NTYxNVoXDTA3MTEz\n\r"+
	"MDE0NTYxNVowgYMxCzAJBgNVBAYTAkVTMQ0wCwYDVQQKEwRGTk1UMRgwFgYDVQQLEw9GTk1UIENs\n\r"+
	"YXNlIDIgQ0ExEjAQBgNVBAsTCTcwMzAxNTI0MTE3MDUGA1UEAxMuTk9NQlJFIFJFR09ET04gSE9M\n\r"+
	"R1VJTiBKT0FRVUlOIC0gTklGIDA1MjYxMDQyRTCBnzANBgkqhkiG9w0BAQEFAAOBjQAwgYkCgYEA\n\r"+
	"wvxZfqcg/dBu1RyAYpywLBMmXtG0O3KeclGSmy6/LNR4HzN8a4YED5OVxHmnqI/hud3G7w+C+9nG\n\r"+
	"KRHpxwxuovSJceKE2p77lfZPMGezPJeI4yP1CTmxCndBI++45SJ7pz0aenmomUeSE8E03kxoAT+b\n\r"+
	"Csddl2XGsfVRNuIAvWkCAwEAAaOCAu8wggLrMG8GA1UdEQRoMGakZDBiMRgwFgYJKwYBBAGsZgEE\n\r"+
	"EwkwNTI2MTA0MkUxFjAUBgkrBgEEAaxmAQMTB0hPTEdVSU4xFjAUBgkrBgEEAaxmAQITB1JFR09E\n\r"+
	"T04xFjAUBgkrBgEEAaxmAQETB0pPQVFVSU4wCQYDVR0TBAIwADArBgNVHRAEJDAigA8yMDA0MTEz\n\r"+
	"MDE0NTYxNVqBDzIwMDcxMTMwMTQ1NjE1WjALBgNVHQ8EBAMCBaAwEQYJYIZIAYb4QgEBBAQDAgWg\n\r"+
	"MB0GA1UdDgQWBBQL+pukiFR7fvkuxP1G/S8KdkquqjAfBgNVHSMEGDAWgBRAmnZEl3QHxKwUyx6N\n\r"+
	"TzpFfDDXYTCCATEGA1UdIASCASgwggEkMIIBIAYJKwYBBAGsZgMFMIIBETA0BggrBgEFBQcCARYo\n\r"+
	"aHR0cDovL3d3dy5jZXJ0LmZubXQuZXMvY29udmVuaW8vZHBjLnBkZjCB2AYIKwYBBQUHAgIwgcsa\n\r"+
	"gchDZXJ0aWZpY2FkbyBSZWNvbm9jaWRvIGV4cGVkaWRvIHNlZ/puIGxlZ2lzbGFjafNuIHZpZ2Vu\n\r"+
	"dGUuVXNvIGxpbWl0YWRvIGEgbGEgQ29tdW5pZGFkIEVsZWN0cvNuaWNhIHBvciB2YWxvciBt4Xhp\n\r"+
	"bW8gZGUgMTAwIGUgc2Fsdm8gZXhjZXBjaW9uZXMgZW4gRFBDLkNvbnRhY3RvIEZOTVQ6Qy9Kb3Jn\n\r"+
	"ZSBKdWFuIDEwNi0yODAwOS1NYWRyaWQtRXNwYfFhLjAdBgkrBgEEAaxmASEEEBYOUEVSU09OQSBG\n\r"+
	"SVNJQ0EwLwYIKwYBBQUHAQMEIzAhMAgGBgQAjkYBATAVBgYEAI5GAQIwCxMDRVVSAgFkAgEAMFsG\n\r"+
	"A1UdHwRUMFIwUKBOoEykSjBIMQswCQYDVQQGEwJFUzENMAsGA1UEChMERk5NVDEYMBYGA1UECxMP\n\r"+
	"Rk5NVCBDbGFzZSAyIENBMRAwDgYDVQQDEwdDUkwxNTg5MA0GCSqGSIb3DQEBBQUAA4GBAJLwWiXz\n\r"+
	"C0YT+YPd6mfEnaXI5ew1vr2xxU3KwQgp2z6Iz7xhlgvN25eJU7NCkdNQEyoB1lJktWOQfGYlbVHq\n\r"+
	"zO3K+PtmkHYIxrgsLbofzdb47XHe49XQPwmkRxU6b9h4RYah+y3s/QIF2U+YKUyc4JCsNgzjWwj3\n\r"+
	"5MLaFpJnGZHO\n\r";

	public void testHash() {
		ServicioCriptoValidacion oService;
		try {
			oService = LocalizadorServicios.getServicioCriptoValidacion();
			String cdocumento = new String("asdñkfjañsdlkfjañsdklfmqñwm efñqdkcma`sidjva´ñdskfjañdskjfañldskfj");
			BASE64Encoder oencoder = new BASE64Encoder();
			String param = new String(oencoder.encode(cdocumento.getBytes()));
			String hashB64 = oService.createHash(param);
			if(oService.validateHash(param, hashB64)){
				System.out.println("ok");
				Assert.assertTrue(true);
			}else{
				Assert.fail();
			}
			
		} catch (SigemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.fail();
		}
	}

	
	public void testValidateCertificate() {
		ServicioCriptoValidacion oService;
		try {
			oService = LocalizadorServicios.getServicioCriptoValidacion();
			ResultadoValidacion oCert = oService.validateCertificate(CERTIFICADO_B64);
			if(oCert != null){
				System.out.println("ok");
				Assert.assertTrue(true);
			}else{
				Assert.fail();
			}
			
		} catch (SigemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.fail();
		}
	}

}
