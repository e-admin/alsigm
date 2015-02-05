package ieci.tecdoc.sgm.core.services;

public class ServicioFirmaDigitalAFirma6Test extends ServicioFirmaDigitalTestBase{
	public ServicioFirmaDigitalAFirma6Test(){
		try{
			setServicio(LocalizadorServicios.getServicioFirmaDigital(
					SERVICE_DIGITAL_SIGN_AFIRMA6_IMPL));
		}catch(Exception e){
			gestionarException(e);
		}
	}
}
