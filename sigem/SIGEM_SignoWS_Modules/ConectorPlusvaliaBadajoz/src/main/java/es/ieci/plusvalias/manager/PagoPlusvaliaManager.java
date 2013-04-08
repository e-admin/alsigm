package es.ieci.plusvalias.manager;

import org.apache.log4j.Logger;

import es.ieci.plusvalias.api.Adquiriente;
import es.ieci.plusvalias.api.Plusvalia;
import es.ieci.plusvalias.api.Transmitente;
import es.ieci.plusvalias.dao.PlusvaliaTempDao;
import es.ieci.plusvalias.domain.PlusvaliaTempDTO;
import es.ieci.plusvalias.exception.ErrorCode;
import es.ieci.plusvalias.exception.PlusvaliaException;
import es.ieci.plusvalias.util.PlusvaliaHelper;

public class PagoPlusvaliaManager {

	public static Logger logger = Logger.getLogger(PagoPlusvaliaManager.class);

	private PlusvaliaTempDao plusvaliatempdao;
	private PlusvaliaHelper plusvaliaHelper;

	public void comprobar(String refCatastral, Transmitente[] transmitentes, Adquiriente[] adquirientes, String tipoAncert) throws Exception{
		for (int i = 0; i < transmitentes.length; i++){
			for (int j = 0; j < adquirientes.length; j++){
				comprobar(refCatastral, transmitentes[i].getNif(), adquirientes[j].getNif(), tipoAncert, adquirientes[j].getNumDerecho());
			}
		}
	}

	// Comprobamos en en BBDD si ya está pagada
	public void comprobar(String refCatastral, String nifTrans, String nifAdq, String tipoAncert, int claseDerecho)
	{
		String nifTransmitiente = nifTrans;		
		
		// Si es por herencia, la fecha de transmisión actual es la fecha de defunción
		if (tipoAncert.equalsIgnoreCase("H"))
		{
			//nifTransmitiente = "HERENCIA";
		}
		
		PlusvaliaTempDTO plusvaliaBBDD = plusvaliatempdao.find(refCatastral, nifTransmitiente, nifAdq, claseDerecho);
		
//		if (plusvaliaBBDD != null && plusvaliaBBDD.getPagada().equals("S"))
//		{
//			throw new PlusvaliaException(ErrorCode.PLUSVALIA_PAID);
//		}
	}

	public Plusvalia getPago(String refCatastral, String nifTrans, String nifAdq, int claseDerecho) {
		PlusvaliaTempDTO plusvaliaBBDD = plusvaliatempdao.find(refCatastral, nifTrans, nifAdq, claseDerecho);
		return plusvaliaHelper.getPlusvalia(plusvaliaBBDD);
	}

	public String getReferenciaPago(String refCatastral, String nifTrans, String nifAdq, int claseDerecho)
	{
		return getPago(refCatastral, nifTrans, nifAdq, claseDerecho).getReferenciaPago();
	}

	public String getNumeroExpediente(String refCatastral, String nifTrans, String nifAdq, int claseDerecho)
	{
		return getPago(refCatastral, nifTrans, nifAdq, claseDerecho).getNumExpediente();
	}

	public String getIdFolderRegistro(String refCatastral, String nifTrans, String nifAdq, int claseDerecho)
	{
		return getPago(refCatastral, nifTrans, nifAdq, claseDerecho).getFolderIdRegistro();
	}

	// Marcamos la plusvalía como pagada
	public void pagar(Plusvalia plusvalia){
		if (logger.isInfoEnabled())	{
			logger.info("Marcando plusvalia como pagada: " + plusvalia.toString());
		}
		
		plusvaliatempdao.pagar(plusvalia.getInmueble().getRefCatastral(), plusvalia.getNifTrans(), plusvalia.getNifAdq(),
				plusvalia.getClaseDerecho());
	}
	
	public boolean existePlusvaliaPagada(Plusvalia plusvalia){
		PlusvaliaTempDTO plusvaliaBBDD = plusvaliatempdao.find(plusvalia.getInmueble().getRefCatastral(), plusvalia.getNifTrans(),
				plusvalia.getNifAdq(), plusvalia.getClaseDerecho());
		return plusvaliaBBDD!=null;
	}
	
	public void setPlusvaliatempdao(PlusvaliaTempDao plusvaliatempdao){
		this.plusvaliatempdao = plusvaliatempdao;
	}
	
	public void setPlusvaliaHelper(PlusvaliaHelper plusvaliaHelper) {
		this.plusvaliaHelper = plusvaliaHelper;
	}
}
