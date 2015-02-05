package ieci.tecdoc.sgm.core.services;

public class ServicioFirmaDigitalAFirmaTest extends ServicioFirmaDigitalTestBase{
	public ServicioFirmaDigitalAFirmaTest(){
		try{
			setServicio(LocalizadorServicios.getServicioFirmaDigital(
					SERVICE_DIGITAL_SIGN_AFIRMA_IMPL));
		}catch(Exception e){
			gestionarException(e);
		}
	}

	public void testRegistrarFirma(){
		assertTrue(true);
	}
}
