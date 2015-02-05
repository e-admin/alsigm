package ieci.tecdoc.sgm.core.services;

import org.apache.commons.codec.binary.Base64;

import ieci.tecdoc.sgm.core.exception.SigemException;
import ieci.tecdoc.sgm.core.services.cripto.firma.CertificadoX509Info;
import ieci.tecdoc.sgm.core.services.cripto.firma.ResultadoValidacionFirma;
import ieci.tecdoc.sgm.core.services.cripto.firma.ServicioFirmaDigital;
import junit.framework.TestCase;

public class ServicioFirmaDigitalTestBase extends TestCase{
	protected static final String SERVICE_DIGITAL_SIGN_AFIRMA_IMPL="SIGEM_ServicioFirmaElectronica.AFIRMA.API";
	protected static final String SERVICE_DIGITAL_SIGN_AFIRMA6_IMPL="SIGEM_ServicioFirmaElectronica.AFIRMA6.API";
	protected static final String SERVICE_DIGITAL_SIGN_PKCS7_DETACHED_IMPL="SIGEM_ServicioFirmaElectronica.PKCS7.API";
	protected static final String SERVICE_DIGITAL_SIGN_PKCS7_ATTACHED_IMPL="SIGEM_ServicioFirmaElectronica.SIGEM.API";

	protected static ServicioFirmaDigital servicio=null;

	protected String cadAFirmar="Cadena de texto largo que se va a usar en la generación de firmas de las pruebas";
	protected String cadAFirmarEnBase64(){
		return Base64.encodeBase64String(cadAFirmar.getBytes());
	}


	public void testFirma() {
		System.setProperty("javax.xml.soap.MessageFactory","org.apache.axis.soap.MessageFactoryImpl");

		String datos = cadAFirmarEnBase64();
		try {
			//BASE64Encoder encoder = new BASE64Encoder();
			String cResultado = servicio.firmar(datos.getBytes());
			System.out.println(cResultado);
			assertNotNull(cResultado);
			return;
		} catch (SigemException e) {
			gestionarException(e);
		}
		fail();
	}

	public void testValidacionFirma(){
		try{
//			String firmaConCertificadoNoValido="MIIOnAYJKoZIhvcNAQcCoIIOjTCCDokCAQExCzAJBgUrDgMCGgUAMAsGCSqGSIb3DQEHAaCCAqYw"+
//			"ggKiMIIBigIKHvwX6QAAAAAAWjANBgkqhkiG9w0BAQUFADAUMRIwEAYDVQQDDAlQcnVUZWNEb2Mw"+
//			"HhcNMTEwODAyMTIyMTAxWhcNMzYwMzIzMTIyMTAxWjCBlDELMAkGA1UEBhMCRVMxEDAOBgNVBAgM"+
//			"B0VTUEHCpUExDzANBgNVBAcMBk1BRFJJRDEPMA0GA1UECgwGSUVDSVNBMRswGQYDVQQLDBJHRVNU"+
//			"SU9OIERPQ1VNRU5UQUwxFTATBgNVBAMMDE5PTUJSRSBTSUdFTTEdMBsGCSqGSIb3DQEJARYOc2ln"+
//			"ZW1Ac2lnZW0uZXMwgZ8wDQYJKoZIhvcNAQEBBQADgY0AMIGJAoGBAMZ+dBcCp9dZWTjNAdTW9US2"+
//			"8JrhObWU+841bsMXEir8jUyv7ZtCsO0b10MN1OsFMco5Ovb6Kowm+ElRXO9cs+nHvd5aGr0Abp45"+
//			"h6wfm1SeJxHcXniF9acZL7RmPVMOESGMMkh6iWgABCeybFxvucXATc/0gUKtqMadiUotjQoXAgMB"+
//			"AAEwDQYJKoZIhvcNAQEFBQADggEBADmd/81Uvw1MrmlfijMoU3QY58yblQWQxVCln5M9oWx3Ebji"+
//			"CJ2H/aJvwbGwwOMTPbnVpSJcqiF+VX9ayQG4Ore+lLslGpF7fP+uiRtYonst+5eIwmFkTIiYGG3O"+
//			"TDMofpZ/OBDGtNE13mnBabpFVRw3v+IXWfy7PmwM3ZYdeJXdndjRFee2ALa0KHlbKk9DZgj6wEN/"+
//			"rSByFFBWbgjdU/0weM9C1PRpC2puTXzu1uklyHXo/WLW9RHXM6wYXXnRC/UZGrqHmAVjB/XYnhwW"+
//			"2E2NDsiaWiDy/J46bZBFrnmfRZEex1bi8vt8w3YXWod8iMPMzk+t3TGcyOmbTkhsvSkxggu+MIIL"+
//			"ugIBATAiMBQxEjAQBgNVBAMMCVBydVRlY0RvYwIKHvwX6QAAAAAAWjAJBgUrDgMCGgUAoIGbMBgG"+
//			"CSqGSIb3DQEJAzELBgkqhkiG9w0BBwEwGQYDVQQtMRITEDEzMjgxNzM5ODMxMjA4MDMwHAYJKoZI"+
//			"hvcNAQkFMQ8XDTEyMDIwMjA5MzAyOFowIQYDVQQFMRoTGDE0NjMyMTI5NjkwMDk5NjYwOTM0MzU3"+
//			"ODAjBgkqhkiG9w0BCQQxFgQUD3TDWhbwjXqXuLmhcTG3mobvlMAwDQYJKoZIhvcNAQEFBQAEgYCv"+
//			"OQPHJb7kMeYKtaGEkDRTbAMZkv/zCWhK5K/09LuHfN8PqXHHM/e8OjUElZWiLnqBBU/Qu6xzENdh"+
//			"W2dtN81UQrMIr3x7SeU2g8qAU+Ao36uMgnvnINrb1aXKcoIP4PN3g93/vLfrTXjtF5HRqUS9oOSc"+
//			"LYbeuvHotRg7GaGyL6GCClQwggpQBgsqhkiG9w0BCRACDjGCCj8wggo7BgkqhkiG9w0BBwKgggos"+
//			"MIIKKAIBAzELMAkGBSsOAwIaBQAwZwYLKoZIhvcNAQkQAQSgWARWMFQCAQEGC2CFVAEDAQEEAgEC"+
//			"MCEwCQYFKw4DAhoFAAQUItO+PZPHkPLIdNmUknb92RiVUUACA0ce3hgPMjAxMjAyMDIwOTMwMjha"+
//			"MAkCAQGAAQGBAQKgggdzMIIHbzCCBlegAwIBAgIDBQeeMA0GCSqGSIb3DQEBBQUAMEQxCzAJBgNV"+
//			"BAYTAkVTMQ0wCwYDVQQKEwRNREVGMQwwCgYDVQQLEwNQS0kxGDAWBgNVBAMTD01JTklTREVGLUVD"+
//			"LVdQRzAeFw0xMTA4MTcwOTUwMjJaFw0yMTA4MTcwOTUwMjJaMHIxCzAJBgNVBAYTAkVTMQ0wCwYD"+
//			"VQQKDARNREVGMQwwCgYDVQQLDANQS0kxEjAQBgNVBAUTCVMyODMzMDAyRTEyMDAGA1UEAwwpU2Vs"+
//			"bG8gZGUgdGllbXBvIFRTQCAtIEBmaXJtYSAtIGRlc2Fycm9sbG8wggEiMA0GCSqGSIb3DQEBAQUA"+
//			"A4IBDwAwggEKAoIBAQCgwVZQB5Lfhe7nJeuBHJ3ft88sv9GZjwZVrJ31IWcozouIdMo19UABAZ0r"+
//			"HrOUbX/35J0TmRWIt/61mFo3QqXlQJRQYRvMHAZtMQhLRECI4nbYkkkwvSGln/GEJ2g3j8g2u4JO"+
//			"MxVmj1jLtZMaAlPWKiH2B7UcR8cjvNalD0XHA1z542YD6WHTWvb9ojXD6zHa1N2HCc+TVt1h1ja9"+
//			"RbNJ2QL9KizQHkRwTP7HbjIfRjOB7HZdHdXtw8ZvuIoYLAD4YujnUkaY+Z0t/w5qhDMQiKKXiEkP"+
//			"DFU7GG1578dLDqYTuAmPDuGKBhFCbdTxRhIuhXLz438uEskGdZAXrC+nAgMBAAGjggQ6MIIENjCB"+
//			"8QYDVR0RBIHpMIHmgRZzb3BvcnRlLmFmaXJtYTVAbXB0LmVzpIHLMIHIMR4wHAYJYIVUAQMFAgIB"+
//			"DA9zZWxsbyBkZSB0aWVtcG8xUDBOBglghVQBAwUCAgIMQU1pbmlzdGVyaW8gZGUgbGEgUG9sw610"+
//			"aWNhIFRlcnJpdG9yaWFsIHkgQWRtaW5pc3RyYWNpw7NuIFDDumJsaWNhMRgwFgYJYIVUAQMFAgID"+
//			"DAlTMjgzMzAwMkUxOjA4BglghVQBAwUCAgUMK1RTQC0gQXV0b3JpZGFkIFNlbGxhZG8gZGUgdGll"+
//			"bXBvLWRlc2Fycm9sbG8wgcQGA1UdEgSBvDCBuYEPYWdwbWRAb2MubWRlLmVzpIGlMIGiMQswCQYD"+
//			"VQQGEwJFUzENMAsGA1UECgwETURFRjEMMAoGA1UECwwDUEtJMSYwJAYDVQQHDB1BcnR1cm8gU29y"+
//			"aWEgMjg5IDI4MDcxIE1hZHJpZDESMBAGA1UEBRMJUzI4MDAyMzFJMSkwJwYDVQQLDCBNaW5pc3Rl"+
//			"cmlvIGRlIERlZmVuc2EgZGUgRXNwYcOxYTEPMA0GA1UEAwwGUEtJREVGMAwGA1UdEwEB/wQCMAAw"+
//			"DgYDVR0PAQH/BAQDAgbAMBYGA1UdJQEB/wQMMAoGCCsGAQUFBwMIMB0GA1UdDgQWBBTrdJ9nNZTm"+
//			"0L9WlyFzLCWOt/Pt1zA4BggrBgEFBQcBAQQsMCowKAYIKwYBBQUHMAGGHGh0dHA6Ly9ldjAxLXdw"+
//			"Zy5tZGVmLmVzOjkzMDgwRAYDVR0gBD0wOzA5BglghVQBAQEBAwQwLDAqBggrBgEFBQcCARYeaHR0"+
//			"cDovL3BraS5tZGVmLmVzL2Nwcy9jcHMuaHRtMB8GA1UdIwQYMBaAFKvjuSGAW/p2D8dG27CbiAta"+
//			"scqyMIIBgQYDVR0fBIIBeDCCAXQwggFwoIIBbKCCAWiGgbZsZGFwOi8vL0NOPU1JTklTREVGLUVD"+
//			"LVdQRyxDTj1FQy1XUEcsQ049Q0RQLENOPVB1YmxpYyUyMEtleSUyMFNlcnZpY2VzLENOPVNlcnZp"+
//			"Y2VzLENOPUNvbmZpZ3VyYXRpb24sREM9ZXQsREM9bWRlLERDPWVzP2NlcnRpZmljYXRlUmV2b2Nh"+
//			"dGlvbkxpc3Q/YmFzZT9vYmplY3RjbGFzcz1jUkxEaXN0cmlidXRpb25Qb2ludIZ9bGRhcDovL2xk"+
//			"YXAubWRlZi5lcy9jbj1NSU5JU0RFRi1DUkwtRUMtV1BHLE9VPVBLSSxPPU1ERUYsQz1FUz9jZXJ0"+
//			"aWZpY2F0ZVJldm9jYXRpb25MaXN0P2Jhc2U/b2JqZWN0Y2xhc3M9Y1JMRGlzdHJpYnV0aW9uUG9p"+
//			"bnSGLmh0dHA6Ly9wa2kubWRlZi5lcy9jcmwvTUlOSVNERUYtQ1JMLUVDLVdQRy5jcmwwDQYJKoZI"+
//			"hvcNAQEFBQADggEBALk7tZzaf/jGkbiahLjLGaMrnxUaPcD8rC3keurxXJberQDC9FjZ3Oi3424l"+
//			"Q2SgPYZsT1sFHmGHylMJ6Rj0k8dcWCJ/JFM8UU4xU3z8WtyfxpQBz6JM/OxGsqPwawwSSVgh8Pns"+
//			"dZ3VKTWsryLj4ROTlst7h2IOkGIh1+d5A0XWQy0A9BjLuvJKAuhI7IA+y0kojfAkblLXDDDxQemx"+
//			"mqgj/84xYOgev1P/2Ecfxll6II9FrsfVmYywaEcERzBiuwX2AU1+NCO4l+GDk9FaHBz63GwKDuXg"+
//			"A0CEL+1RnTi469L+RVYSeZd731hkbWcj3Yy9+lPoB8/c7rHJ7gIHa+gxggI0MIICMAIBATBLMEQx"+
//			"CzAJBgNVBAYTAkVTMQ0wCwYDVQQKEwRNREVGMQwwCgYDVQQLEwNQS0kxGDAWBgNVBAMTD01JTklT"+
//			"REVGLUVDLVdQRwIDBQeeMAkGBSsOAwIaBQCggb8wGgYJKoZIhvcNAQkDMQ0GCyqGSIb3DQEJEAEE"+
//			"MCMGCSqGSIb3DQEJBDEWBBT5hx/dw7ctzLIWaUbMhSwjZYdoozB8BgsqhkiG9w0BCRACDDFtMGsw"+
//			"aTBnBBT3oha42ij3HgcZMRG9GKsqVen0qzBPMEikRjBEMQswCQYDVQQGEwJFUzENMAsGA1UEChME"+
//			"TURFRjEMMAoGA1UECxMDUEtJMRgwFgYDVQQDEw9NSU5JU0RFRi1FQy1XUEcCAwUHnjANBgkqhkiG"+
//			"9w0BAQEFAASCAQBGFFOPqBfNhwC/KetLZTHoWu65VdUGEj83mfQJyhmtd4O6IhSHfrOYOMTbzffW"+
//			"UKPJvxBR+R98vvfvqIxKBv2i9ynzHevXjzjiwmtbPn52PhMBSMSnytEPaGm4lNRnIjz5Ripf59Uy"+
//			"YC/FOO06SQnNhzpwo2mt1vbCw3+5z1O7hGnzJ2AMhBpOnSdq4QA6/m7mfyBEsYfd7fijvoumCf7W"+
//			"Mo8TyKqhxwPxt1PbV+J/t5/4NHsLvMqTx7/kd2GG06x689MqEaS2nFqKhCvpJT5oktEq4HXx5GbP"+
//			"b4uC54IKWFHCk9DSZ98g08kCmM0o/crON6J4dJOumcqQuvGyzxet";

//			String firmaAValidarNoValidaEnAfirma5="MIIQrQYJKoZIhvcNAQcCoIIQnjCCEJoCAQExCzAJBgUrDgMCGgUAMAsGCSqGSIb3DQEHAaCCBKkw"+
//			"ggSlMIIEDqADAgECAgQ8uyG8MA0GCSqGSIb3DQEBBQUAMDYxCzAJBgNVBAYTAkVTMQ0wCwYDVQQK"+
//			"EwRGTk1UMRgwFgYDVQQLEw9GTk1UIENsYXNlIDIgQ0EwHhcNMTAwNDA2MDgwMzA4WhcNMTIwNDA2"+
//			"MDgwMzA4WjCBxjELMAkGA1UEBhMCRVMxDTALBgNVBAoTBEZOTVQxGDAWBgNVBAsTD0ZOTVQgQ2xh"+
//			"c2UgMiBDQTESMBAGA1UECxMJNTAwMDUxNTI2MXoweAYDVQQDE3FFTlRJREFEIElORk9STUFUSUNB"+
//			"IEVMIENPUlRFIElOR0xFUyBTQSAtIENJRiBBMjg4NTUyNjAgLSBOT01CUkUgWkFSQUdPWkEgQVJS"+
//			"SUJBUyBGUkFOQ0lTQ08gSkFWSUVSIC0gTklGIDUxOTU5MTAzWDCBnzANBgkqhkiG9w0BAQEFAAOB"+
//			"jQAwgYkCgYEA1HnFzWyngtpdlRnYdqFS1pG3SBjXB9ddLko66UDfOZrRjvG3jjpGxvPHYNNuqv+z"+
//			"Kww/Vdm4DaC6vM4MtkZcm2oQdqATbIjlyE8Kgp6Q1iqbGlyN3zF4X7SRrE/rM4pRMNXf7O0g+K4D"+
//			"7FKa1RvjXX5ME6zbRXuy7H2JP0Vfb18CAwEAAaOCAi0wggIpMIHxBgNVHREEgekwgeaBKUZSQU5D"+
//			"SVNDT0pBVklFUl9aQVJBR09aQUBFTENPUlRFSU5HTEVTLkVTpIG4MIG1MRgwFgYJKwYBBAGsZgEH"+
//			"EwlBMjg4NTUyNjAxLTArBgkrBgEEAaxmAQYTHklORk9STUFUSUNBIEVMIENPUlRFIElOR0xFUyBT"+
//			"QTEYMBYGCSsGAQQBrGYBBBMJNTE5NTkxMDNYMRYwFAYJKwYBBAGsZgEDEwdBUlJJQkFTMRcwFQYJ"+
//			"KwYBBAGsZgECEwhaQVJBR09aQTEfMB0GCSsGAQQBrGYBARMQRlJBTkNJU0NPIEpBVklFUjAJBgNV"+
//			"HRMEAjAAMCsGA1UdEAQkMCKADzIwMTAwNDA2MDgwMzA4WoEPMjAxMjA0MDYwODAzMDhaMAsGA1Ud"+
//			"DwQEAwIFoDARBglghkgBhvhCAQEEBAMCBaAwHQYDVR0OBBYEFBbA+qKj5UyxLhgP4vKDm5IP6uSF"+
//			"MB8GA1UdIwQYMBaAFECadkSXdAfErBTLHo1POkV8MNdhMD4GCSsGAQQBrGYBIQQxFi9DRVJUSUZJ"+
//			"Q0FETyBFWENMVVNJVk8gUEFSQSBFTCBBTUJJVE8gVFJJQlVUQVJJTzBbBgNVHR8EVDBSMFCgTqBM"+
//			"pEowSDELMAkGA1UEBhMCRVMxDTALBgNVBAoTBEZOTVQxGDAWBgNVBAsTD0ZOTVQgQ2xhc2UgMiBD"+
//			"QTEQMA4GA1UEAxMHQ1JMNjk3MTANBgkqhkiG9w0BAQUFAAOBgQCRRXZwp7NmVTGXkmiFQ+Gp0PCH"+
//			"prgTUx8jcSUUyLm4cNu6tRW64ZORl+SRm+oIEh0HE7JdGeVe3gVFB/w/EArV9KzBVKemQyTAyPoC"+
//			"sN5BasOJmVZUG03ZEoC4s2CJe/BB3AXQ3w1GbHLZLRQfd0dS3uaiHax6vnjbbC/yKeoKRDGCC8ww"+
//			"ggvIAgEBMD4wNjELMAkGA1UEBhMCRVMxDTALBgNVBAoTBEZOTVQxGDAWBgNVBAsTD0ZOTVQgQ2xh"+
//			"c2UgMiBDQQIEPLshvDAJBgUrDgMCGgUAoIGNMBMGA1UEBTEMEwoxMDE4ODk2ODI4MBgGCSqGSIb3"+
//			"DQEJAzELBgkqhkiG9w0BBwEwGQYDVQQtMRITEDEzMjkxNTEyOTAwODEyMzQwHAYJKoZIhvcNAQkF"+
//			"MQ8XDTEyMDIxMzE2NTI0OFowIwYJKoZIhvcNAQkEMRYEFA90w1oW8I16l7i5oXExt5qG75TAMA0G"+
//			"CSqGSIb3DQEBBQUABIGAZbjWZwPjNZw+bn3qRHN/8m3JYUDWwnDfiP0xz0QIl5B0zgbgXDTgTBR2"+
//			"CFs5wXWVGurPdwci4E/ZrHSalg1ZrtvtN+6maozDEdNEB8NgTNCdU1nYu3GAadnkuiNooxUfpnUa"+
//			"y0XbBTFkJDaYF1ScF0caz/gRTweJn1x9Oj9QGC2hggpUMIIKUAYLKoZIhvcNAQkQAg4xggo/MIIK"+
//			"OwYJKoZIhvcNAQcCoIIKLDCCCigCAQMxCzAJBgUrDgMCGgUAMGcGCyqGSIb3DQEJEAEEoFgEVjBU"+
//			"AgEBBgtghVQBAwEBBAIBAjAhMAkGBSsOAwIaBQAEFA8V+ZCmdyhlcf5LFPUMJZqMxIOfAgNH71QY"+
//			"DzIwMTIwMjEzMTY1MjQ4WjAJAgEBgAEBgQECoIIHczCCB28wggZXoAMCAQICAwUHnjANBgkqhkiG"+
//			"9w0BAQUFADBEMQswCQYDVQQGEwJFUzENMAsGA1UEChMETURFRjEMMAoGA1UECxMDUEtJMRgwFgYD"+
//			"VQQDEw9NSU5JU0RFRi1FQy1XUEcwHhcNMTEwODE3MDk1MDIyWhcNMjEwODE3MDk1MDIyWjByMQsw"+
//			"CQYDVQQGEwJFUzENMAsGA1UECgwETURFRjEMMAoGA1UECwwDUEtJMRIwEAYDVQQFEwlTMjgzMzAw"+
//			"MkUxMjAwBgNVBAMMKVNlbGxvIGRlIHRpZW1wbyBUU0AgLSBAZmlybWEgLSBkZXNhcnJvbGxvMIIB"+
//			"IjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAoMFWUAeS34Xu5yXrgRyd37fPLL/RmY8GVayd"+
//			"9SFnKM6LiHTKNfVAAQGdKx6zlG1/9+SdE5kViLf+tZhaN0Kl5UCUUGEbzBwGbTEIS0RAiOJ22JJJ"+
//			"ML0hpZ/xhCdoN4/INruCTjMVZo9Yy7WTGgJT1ioh9ge1HEfHI7zWpQ9FxwNc+eNmA+lh01r2/aI1"+
//			"w+sx2tTdhwnPk1bdYdY2vUWzSdkC/Sos0B5EcEz+x24yH0Yzgex2XR3V7cPGb7iKGCwA+GLo51JG"+
//			"mPmdLf8OaoQzEIiil4hJDwxVOxhtee/HSw6mE7gJjw7higYRQm3U8UYSLoVy8+N/LhLJBnWQF6wv"+
//			"pwIDAQABo4IEOjCCBDYwgfEGA1UdEQSB6TCB5oEWc29wb3J0ZS5hZmlybWE1QG1wdC5lc6SByzCB"+
//			"yDEeMBwGCWCFVAEDBQICAQwPc2VsbG8gZGUgdGllbXBvMVAwTgYJYIVUAQMFAgICDEFNaW5pc3Rl"+
//			"cmlvIGRlIGxhIFBvbMOtdGljYSBUZXJyaXRvcmlhbCB5IEFkbWluaXN0cmFjacOzbiBQw7pibGlj"+
//			"YTEYMBYGCWCFVAEDBQICAwwJUzI4MzMwMDJFMTowOAYJYIVUAQMFAgIFDCtUU0AtIEF1dG9yaWRh"+
//			"ZCBTZWxsYWRvIGRlIHRpZW1wby1kZXNhcnJvbGxvMIHEBgNVHRIEgbwwgbmBD2FncG1kQG9jLm1k"+
//			"ZS5lc6SBpTCBojELMAkGA1UEBhMCRVMxDTALBgNVBAoMBE1ERUYxDDAKBgNVBAsMA1BLSTEmMCQG"+
//			"A1UEBwwdQXJ0dXJvIFNvcmlhIDI4OSAyODA3MSBNYWRyaWQxEjAQBgNVBAUTCVMyODAwMjMxSTEp"+
//			"MCcGA1UECwwgTWluaXN0ZXJpbyBkZSBEZWZlbnNhIGRlIEVzcGHDsWExDzANBgNVBAMMBlBLSURF"+
//			"RjAMBgNVHRMBAf8EAjAAMA4GA1UdDwEB/wQEAwIGwDAWBgNVHSUBAf8EDDAKBggrBgEFBQcDCDAd"+
//			"BgNVHQ4EFgQU63SfZzWU5tC/Vpchcywljrfz7dcwOAYIKwYBBQUHAQEELDAqMCgGCCsGAQUFBzAB"+
//			"hhxodHRwOi8vZXYwMS13cGcubWRlZi5lczo5MzA4MEQGA1UdIAQ9MDswOQYJYIVUAQEBAQMEMCww"+
//			"KgYIKwYBBQUHAgEWHmh0dHA6Ly9wa2kubWRlZi5lcy9jcHMvY3BzLmh0bTAfBgNVHSMEGDAWgBSr"+
//			"47khgFv6dg/HRtuwm4gLWrHKsjCCAYEGA1UdHwSCAXgwggF0MIIBcKCCAWygggFohoG2bGRhcDov"+
//			"Ly9DTj1NSU5JU0RFRi1FQy1XUEcsQ049RUMtV1BHLENOPUNEUCxDTj1QdWJsaWMlMjBLZXklMjBT"+
//			"ZXJ2aWNlcyxDTj1TZXJ2aWNlcyxDTj1Db25maWd1cmF0aW9uLERDPWV0LERDPW1kZSxEQz1lcz9j"+
//			"ZXJ0aWZpY2F0ZVJldm9jYXRpb25MaXN0P2Jhc2U/b2JqZWN0Y2xhc3M9Y1JMRGlzdHJpYnV0aW9u"+
//			"UG9pbnSGfWxkYXA6Ly9sZGFwLm1kZWYuZXMvY249TUlOSVNERUYtQ1JMLUVDLVdQRyxPVT1QS0ks"+
//			"Tz1NREVGLEM9RVM/Y2VydGlmaWNhdGVSZXZvY2F0aW9uTGlzdD9iYXNlP29iamVjdGNsYXNzPWNS"+
//			"TERpc3RyaWJ1dGlvblBvaW50hi5odHRwOi8vcGtpLm1kZWYuZXMvY3JsL01JTklTREVGLUNSTC1F"+
//			"Qy1XUEcuY3JsMA0GCSqGSIb3DQEBBQUAA4IBAQC5O7Wc2n/4xpG4moS4yxmjK58VGj3A/Kwt5Hrq"+
//			"8VyW3q0AwvRY2dzot+NuJUNkoD2GbE9bBR5hh8pTCekY9JPHXFgifyRTPFFOMVN8/Frcn8aUAc+i"+
//			"TPzsRrKj8GsMEklYIfD57HWd1Sk1rK8i4+ETk5bLe4diDpBiIdfneQNF1kMtAPQYy7rySgLoSOyA"+
//			"PstJKI3wJG5S1www8UHpsZqoI//OMWDoHr9T/9hHH8ZZeiCPRa7H1ZmMsGhHBEcwYrsF9gFNfjQj"+
//			"uJfhg5PRWhwc+txsCg7l4ANAhC/tUZ04uOvS/kVWEnmXe99YZG1nI92MvfpT6AfP3O6xye4CB2vo"+
//			"MYICNDCCAjACAQEwSzBEMQswCQYDVQQGEwJFUzENMAsGA1UEChMETURFRjEMMAoGA1UECxMDUEtJ"+
//			"MRgwFgYDVQQDEw9NSU5JU0RFRi1FQy1XUEcCAwUHnjAJBgUrDgMCGgUAoIG/MBoGCSqGSIb3DQEJ"+
//			"AzENBgsqhkiG9w0BCRABBDAjBgkqhkiG9w0BCQQxFgQUBcQlk050kCvjq+QImed+8EAUeyowfAYL"+
//			"KoZIhvcNAQkQAgwxbTBrMGkwZwQU96IWuNoo9x4HGTERvRirKlXp9KswTzBIpEYwRDELMAkGA1UE"+
//			"BhMCRVMxDTALBgNVBAoTBE1ERUYxDDAKBgNVBAsTA1BLSTEYMBYGA1UEAxMPTUlOSVNERUYtRUMt"+
//			"V1BHAgMFB54wDQYJKoZIhvcNAQEBBQAEggEAMSI0tE9kji6LLf3hRNPFgHis3KcvyCnd0xy77RZx"+
//			"If/+qV5qVmc91mwwwajNdavawqklM+GzSaM8DLtt7I1EF7KnpdgAnk+iP/QyTcnv/c6uAzuBlCiE"+
//			"pnU3/dLkDpNsCicfn2Hpo3dHaIS7ZTQpoJ7zA/fOckBm3c0ILTy7AKhwm8l2YgT7jVOI0E8g5v63"+
//			"hIKYSUQnLW0mTeetioo4Xq2jhZTFXr6iNBC3JM11QjNYMo6QuFXugay5ibZP02Mm4SFMOnC4PB1N"+
//			"wNF7wdP7qqT7wkcGy5Eu4LwE+BtWGbT/8sAUtrCTooeouNLuDty/SuzGicDAQnhMnLuG8KDpeg==";

			String firmaAValidar="MIAGCSqGSIb3DQEHAqCAMIACAQExCzAJBgUrDgMCGgUAMIAGCSqGSIb3DQEHAQAAoIAwggeOMIIF"+
									"dqADAgECAhBmc600GmDiF8xIWgNKqRneMA0GCSqGSIb3DQEBBQUAMIHOMQswCQYDVQQGEwJFUzFE"+
									"MEIGA1UEBxM7UGFzZW8gZGVsIEdlbmVyYWwgTWFydGluZXogQ2FtcG9zIDQ2IDZhIHBsYW50YSAy"+
									"ODAxMCBNYWRyaWQxQTA/BgNVBAoTOEFnZW5jaWEgTm90YXJpYWwgZGUgQ2VydGlmaWNhY2lvbiBT"+
									"LkwuVS4gLSBDSUYgQjgzMzk1OTg4MTYwNAYDVQQDEy1BTkNFUlQgQ2VydGlmaWNhZG9zIE5vdGFy"+
									"aWFsZXMgZGUgU2lzdGVtYXMgVjIwHhcNMTAxMjIyMTIyODEzWhcNMTYxMjIyMTIyODEzWjCByTEL"+
									"MAkGA1UEBhMCRVMxMTAvBgNVBAoTKEFnZW5jaWEgTm90YXJpYWwgZGUgQ2VydGlmaWNhY2lvbiBT"+
									"LkwuVS4xIDAeBgNVBAsTF0F1dG9yaXphZG8gYW50ZSBOb3RhcmlvMTIwMAYDVQQLEylDZXJ0aWZp"+
									"Y2FkbyBOb3RhcmlhbCBkZSBTZWxsYWRvIGRlIFRpZW1wbzESMBAGA1UEBRMJQjgzMzk1OTg4MR0w"+
									"GwYDVQQDExRBTkNFUlQgVFNVIERFTU8gQ0VSVDCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoC"+
									"ggEBAL2htZmfAN+P1yCyddP+eNHI16mId3gZS+oRbwtWuauTs/q5n4sa9VBO6B7R8tJzoFZepXmg"+
									"+vRJRr8Aoh4Ewy6kXSQTrB3Q/ZCXNhIHpw1VLgJJjf7eWXqHN5LbiOgS/+J3gr1XkANitozbEvqs"+
									"TLxy85dJ+5lE9VfECclTDNQDFmwqHN0tWCtgQUlaa6PJNPhP22PJtYn0SNIbqP+v7la0Toji7sFK"+
									"uvNaK1n74f2YARF/+zc+BATcvRwobZwaaMIf8N34YPM2HZLGXtaU2sQkd9fR2M9mC9dnIlsFTpQ5"+
									"tRiSS9vJiTWWZYKv/pnEkUGanesho/WpkStrNxQRYEsCAwEAAaOCAmkwggJlMBYGA1UdJQEB/wQM"+
									"MAoGCCsGAQUFBwMIMA4GA1UdDwEB/wQEAwIHgDA/BggrBgEFBQcBAQQzMDEwLwYIKwYBBQUHMAGG"+
									"I2h0dHA6Ly9vY3NwLmFjLmFuY2VydC5jb20vb2NzcC54dWRhMB8GA1UdIwQYMBaAFNs1uTEf/fd8"+
									"wxcNycvIVQvqFXwKMAwGA1UdEwEB/wQCMAAwgeoGA1UdIASB4jCB3zCB3AYNKwYBBAGBk2gBAgMC"+
									"ATCByjA3BggrBgEFBQcCARYraHR0cHM6Ly93d3cuYW5jZXJ0LmNvbS9jb25kaWNpb25lcy9DTlNU"+
									"SGFyZDCBjgYIKwYBBQUHAgIwgYEwDRYGQU5DRVJUMAMCAQEacEVzdGUgY2VydGlmaWNhZG8gc2Ug"+
									"ZXhwaWRlIGRlIGFjdWVyZG8gY29uIGxhcyBjb25kaWNpb25lcyBkZSB1c28gZW4gIGh0dHBzOi8v"+
									"d3d3LmFuY2VydC5jb20vY29uZGljaW9uZXMvQ05TVEhhcmQwgZcGA1UdHwSBjzCBjDCBiaCBhqCB"+
									"g4YpaHR0cDovL3d3dy5hbmNlcnQuY29tL2NybC9BTkNFUlRDU19WMi5jcmyGKmh0dHA6Ly93d3cy"+
									"LmFuY2VydC5jb20vY3JsL0FOQ0VSVENTX1YyLmNybIYqaHR0cDovL3d3dzMuYW5jZXJ0LmNvbS9j"+
									"cmwvQU5DRVJUQ1NfVjIuY3JsMB0GA1UdDgQWBBTIo7hADkDQ6qJFKRDiGUDlhrCbuTAlBgNVHREE"+
									"HjAcgRpwa2kuZXhwbG90YWNpb25AYW5jZXJ0LmNvbTANBgkqhkiG9w0BAQUFAAOCAgEAQVoR8/T9"+
									"YFo/APHWU+0pkKu639GMY761ejBXZhe1KuowW2u5uzOdes9EVAh5BCfsxTEi1HarMYn3Gdl9N+sV"+
									"kcdRwC/0iSnz86HvpHUH5+LhgIJvliOaBhLa11F05uyUCxmqSzpnF3FRijuQHSlHY1+wv56Bbtnn"+
									"hJB9QRQbLOTytOEeE8YkzhwfthO8eFpfhBDVWb7I7jrGeK+fLDnjLUVZBDHpPkQoEoCUE+wh/8Cw"+
									"ajiNSvygIKtTbRdiHohgPYXsa5D7VUCTkqrZx+GFpYzau9MIPS/eSv+ul91R+LiPWrsjLUIhwsrb"+
									"wcKY7637o06BUOITU6QMgMGN7oT/l9jqPbpVXLKEZMLuJvPs/WJ4QCwffShhMIhW4p4DqxMElB9O"+
									"v21hWDcrvJnKkR5/fePj2GFKTNLDLWaVLjh9yQla+sPr2sy100JPP4gxHOrxrGcOn1GUrSsIK3Qi"+
									"h31yLWewp7VsaDNglwHaiIO0dQMO4/uL+8gSwyVo1hmpk7UKk5cXQNlBb2h1CRuIEbQfC2fqVGT8"+
									"wLkNIUoWbgZLwDTYoqxoDrNFOxTcKbP9y/Ks2g5ZypzTy1XD1IQxyP7yeWXEtZJ7z0UE2PgjeAaG"+
									"WrBg31Ryvk1iW8PhUCaItSfLBR6HW+Rl2kk2nu2k4wugvT12SiX4BUxsa9/RwZa3xfgAADGCAmow"+
									"ggJmAgEBMIHjMIHOMQswCQYDVQQGEwJFUzFEMEIGA1UEBxM7UGFzZW8gZGVsIEdlbmVyYWwgTWFy"+
									"dGluZXogQ2FtcG9zIDQ2IDZhIHBsYW50YSAyODAxMCBNYWRyaWQxQTA/BgNVBAoTOEFnZW5jaWEg"+
									"Tm90YXJpYWwgZGUgQ2VydGlmaWNhY2lvbiBTLkwuVS4gLSBDSUYgQjgzMzk1OTg4MTYwNAYDVQQD"+
									"Ey1BTkNFUlQgQ2VydGlmaWNhZG9zIE5vdGFyaWFsZXMgZGUgU2lzdGVtYXMgVjICEGZzrTQaYOIX"+
									"zEhaA0qpGd4wCQYFKw4DAhoFAKBdMBgGCSqGSIb3DQEJAzELBgkqhkiG9w0BBwEwHAYJKoZIhvcN"+
									"AQkFMQ8XDTEzMDMxOTEyMzMyNlowIwYJKoZIhvcNAQkEMRYEFOV6rXeiAJfvIQlpOjL0jHRZDJxp"+
									"MA0GCSqGSIb3DQEBAQUABIIBALedbIcyHSVNZE6HEfsOOyNbb25dDmXehotPPShrQyeAUIY0Pztf"+
									"2trwyBj2r+43Qyro0yHKN84b3a1mlDnAbrRrWUVQrKKk763SjBX/c+9tMnkVhQRH7cXBQKVW1WEK"+
									"lCATGWA8LcvW43xNAamW5Lh1HDVKLkmjSgdV30jidzP5Aoqmsm5f0qxsRRNeoFfMm04TbJV6vI9s"+
									"dUQf834kwIzpwqSRg1PZNfERzjYbvAUzcAs6F6DXJiwQe5GfRbDuNPsd39bDXaSKLQcOnHvGUrRK"+
									"zXc2iC7r1ryI3vO93cCTq2sztPW0ZHPwOngeshPb1hUMtXPBm2sc2tORnJCwidQAAAAAAAA=";

			//String datos2="IZENPECENSO000000";
			String datos=cadAFirmarEnBase64();

			ResultadoValidacionFirma oResultado = servicio.validarFirma(firmaAValidar.getBytes(), datos.getBytes());
			System.out.println("OK");
			assertNotNull(oResultado);
			return;
		} catch (SigemException e) {
			gestionarException(e);
		}
		fail();
	}

	public void testGetCertInfo(){
		try{
			CertificadoX509Info certInfo=servicio.getcertInfo();
			assertNotNull(certInfo);
			assertNotNull(certInfo.getCert());
			assertNotNull(certInfo.getCert().getSubjectDN());
			return;
		}catch(Exception e){
			gestionarException(e);
		}
		fail();
	}

	public void testRegistrarFirma(){
		try{
			byte[] signatureValue=null;
			byte[] signCertificateValue=null;
			byte[] hash=null;
			servicio.registrarFirma(signatureValue, signCertificateValue, hash);
			assertTrue(true);
			return;
		}catch(Exception e){
			gestionarException(e);
		}
		fail();
	}

	protected void gestionarException(Exception e){
		System.out.println(e.getMessage());
		e.printStackTrace();
	}

	protected void setServicio(ServicioFirmaDigital servicio){
		ServicioFirmaDigitalTestBase.servicio=servicio;
	}

//	private boolean isBase64(String cadToTest){
//		if(cadToTest.length()%4>0) return false;
//		Pattern p = Pattern.compile("[A-Za-z0-9+/]+={0,3}");
//        Matcher m = p.matcher(cadToTest);
//        return m.find();
//	}
//
//	private byte[] loadCertificateFile(String pathCertificate){
//		if(StringUtils.isEmpty(pathCertificate)) return null;
//		byte[] encodedPublicKey=null;
//		try{
//			File filePublicKey = new File(pathCertificate);
//			if(!filePublicKey.exists()) return null;
//			FileInputStream fis = new FileInputStream(filePublicKey);
//			encodedPublicKey = new byte[(int) filePublicKey.length()];
//			fis.read(encodedPublicKey);
//			fis.close();
//		}catch(Exception e){
//			gestionarException(e);
//		}
//
//		byte [] encodedPublicKeyNoBase64=encodedPublicKey;
//
//		//comprobar si es un certificado en formato CER
//		final String CER_BEGIN="----BEGIN CERTIFICATE----";
//		final String   CER_END="----END CERTIFICATE----";
//		String posibleBase64String=new String(encodedPublicKey);
//		int posIni=posibleBase64String.indexOf(CER_BEGIN);
//		int posFin=posibleBase64String.indexOf(CER_END);
//		if(posIni>-1 && posFin>-1 ){
//			posIni+=CER_BEGIN.length()+1; posFin--;
//			posibleBase64String=posibleBase64String.substring(posIni,posFin);
//			posibleBase64String=posibleBase64String.replaceAll("\r?\n", "");
//			encodedPublicKeyNoBase64=Base64.decode(posibleBase64String);
//		}
//		return encodedPublicKeyNoBase64;
//	}
}
