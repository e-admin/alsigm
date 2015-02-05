package ieci.tecdoc.sgm.core.services;

public class ServicioFirmaDigitalInternaPKCS7DetachedTest extends ServicioFirmaDigitalTestBase{
	public ServicioFirmaDigitalInternaPKCS7DetachedTest(){
		try{
			setServicio(LocalizadorServicios.getServicioFirmaDigital(
					SERVICE_DIGITAL_SIGN_PKCS7_DETACHED_IMPL));
		}catch(Exception e){
			gestionarException(e);
		}
	}
}
