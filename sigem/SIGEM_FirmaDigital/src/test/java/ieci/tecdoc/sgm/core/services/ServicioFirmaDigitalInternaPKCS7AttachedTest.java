package ieci.tecdoc.sgm.core.services;

public class ServicioFirmaDigitalInternaPKCS7AttachedTest extends ServicioFirmaDigitalTestBase{
	public ServicioFirmaDigitalInternaPKCS7AttachedTest(){
		try{
			setServicio(LocalizadorServicios.getServicioFirmaDigital(
					SERVICE_DIGITAL_SIGN_PKCS7_ATTACHED_IMPL));
		}catch(Exception e){
			gestionarException(e);
		}
	}
}
