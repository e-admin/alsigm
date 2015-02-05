package ieci.tecdoc.sgm.pe.pasarela;

import ieci.tecdoc.sgm.pe.ConfiguracionComun;

public class ConectorPasarelaPagoFactory {
	private static final String KEY_IMPLEMENTACION="pago.SPT.conector.pasarela";
	
	public static ConectorPasarelaPago getConectorPasarelaPago() throws Exception{
		return (ConectorPasarelaPago)ConfiguracionComun.getConfiguracion().getBean(KEY_IMPLEMENTACION);
	}
}
