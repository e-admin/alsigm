package ieci.tecdoc.sgm.pe;
/*
 * $Id: NumeroReferenciaManager.java,v 1.1.2.2 2008/03/14 11:22:22 jnogales Exp $
 */
import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.pe.database.NumeroSecuenciaAutoLiquidacion12;
import ieci.tecdoc.sgm.pe.database.NumeroSecuenciaAutoLiquidacion3;
import ieci.tecdoc.sgm.pe.database.exception.DbExcepcion;
import ieci.tecdoc.sgm.pe.exception.PagoElectronicoExcepcion;

import org.apache.log4j.Logger;

/**
 * Gestor de la creación de números de referenica para cada determinado tipo de liquidación.
 */
public class NumeroReferenciaManager {

	/**
	 * Instancia del logger
	 */
	private static final Logger logger = Logger.getLogger(NumeroReferenciaManager.class);
	
	
	/**
	 * Metodo que se encarga de obtener el número de referencia para una liquidación dependiendo 
	 * del su tipo.
	 * @param conn Conexión a la base de datos con una transacción activa.
	 * @param poLiquidacion Datos de la liquidación
	 * @param poTasa Datos de la tasa asociada a la liquidación.
	 * @return Cadena Número de referencia.
	 * @throws PagoElectronicoExcepcion En caso de producirse algún error.
	 */
	public static String obtenerNumeroReferencia(DbConnection conn, Liquidacion poLiquidacion, Tasa poTasa) throws PagoElectronicoExcepcion{
		if(	(Tasa.TIPO_AL1.equalsIgnoreCase(poTasa.getTipo())) || 
				(Tasa.TIPO_AL2.equalsIgnoreCase(poTasa.getTipo()))
		){
			return obtenerNumeroReferenciaAL1_2(conn, poLiquidacion);
		}else if(Tasa.TIPO_AL3.equalsIgnoreCase(poTasa.getTipo())){
			return obtenerNumeroReferenicaAL3(conn, poLiquidacion);
		}
		return null;
	}
	
	/**
	 * Método que obtiene el identificador de un pago del tipo AL1.
	 * El identificador está formado por:
	 *  - (3) Id. tributo.
	 *  - (2) Ejercicio.
	 *  - (2) Remesa.
	 * @param poLiquidacion Datos de la liquidación.
	 * @return String Identificador del pago.
	 */
	private static String obtenerIdentificadorPagoAL1(Liquidacion poLiquidacion){
		StringBuffer sbIdent = new StringBuffer(poLiquidacion.getIdTasa());
		sbIdent.append(poLiquidacion.getEjercicio()).append(poLiquidacion.getRemesa());
		return sbIdent.toString();
	}
	

	/**
	 * Método que obtiene el número de referencia para una liquidación del tipo AL1 o AL2.
	 * @param conn Conexión a la base de datos con una transacción activa.
	 * @param poLiquidacion Datos de la liquidación de la que se quiere obtener el número de referencia.
	 * @return String Número de referencia.
	 * @throws PagoElectronicoExcepcion En caso de producirse algún error.
	 */
	private static String obtenerNumeroReferenciaAL1_2(DbConnection conn,Liquidacion poLiquidacion) throws PagoElectronicoExcepcion
    {	
		
		long lNumeroSecuencia = -1;
		try {
			lNumeroSecuencia = NumeroSecuenciaAutoLiquidacion12.obtenerIdentificador(conn);
		} catch (DbExcepcion e) {
			logger.error("Error obteniendo número de referencia para liquidación AL1_2", e);
			throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_GENERANDO_NREF_AL12, e);
		}
		
		// Se formatea a cadena de 10 posiciones
		String sNumSec = Util.format(lNumeroSecuencia, 10);
		short ctrlDigit = obtenerDigitoControlAL12(poLiquidacion.getIdEntidadEmisora(), 
												obtenerIdentificadorPagoAL1(poLiquidacion), 
												poLiquidacion.getImporte(), 
												sNumSec);
		
		StringBuffer sbNumRef = new StringBuffer();
		if (ctrlDigit < 10){
			sbNumRef.append(sNumSec).append("0").append(new Short(ctrlDigit).toString());
		}else{
			sbNumRef.append(sNumSec).append(new Short(ctrlDigit).toString());
		}
		return sbNumRef.toString();
    }

	
	/**
	 * Método que obtiene el número de referencia para una liquidación del tipo AL3.
	 * @param conn Conexión a la base de datos con una transacción activa.
	 * @param poLiquidacion Datos de la liquidación de la que se quiere obtener el número de referencia.
	 * @return String Número de referencia.
	 * @throws PagoElectronicoExcepcion  En caso de producirse algún error.
	 */
	private static String obtenerNumeroReferenicaAL3(DbConnection conn,Liquidacion poLiquidacion) throws PagoElectronicoExcepcion
    {	
		
		long lNumeroSecuencia = -1;
		try {
			lNumeroSecuencia = NumeroSecuenciaAutoLiquidacion3.obtenerIdentificador(conn, poLiquidacion.getTasa().getModelo());
		} catch (DbExcepcion e) {
			logger.error("Error obteniendo número de referencia para liquidación AL3", e);
			throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_GENERANDO_NREF_AL3, e);
		}
		
		// formateamos el número de secuencia
		String cNumeroSecuencia = Util.format(lNumeroSecuencia, 8);
		// obtenemos el digito de control
		short sDigitoControl = obtenerDigitoControlAL3(poLiquidacion.getIdTasa(), 
													poLiquidacion.getTasa().getIdEntidadEmisora(), 
													poLiquidacion.getTasa().getCaptura(), 
													cNumeroSecuencia);
													
		
		StringBuffer sbReferencia = new StringBuffer(poLiquidacion.getIdTasa());
		sbReferencia.append(poLiquidacion.getTasa().getCaptura());
		sbReferencia.append(cNumeroSecuencia);
		sbReferencia.append(sDigitoControl);

		return sbReferencia.toString();
    }
	

	/**
	 * Método que obtiene el dígito de control para un número de referencia de una liquidación del tipo AL1_2
	 * @param emisora Identificador de la entidad emisora.
	 * @param identificacion Identificación de la liquidación.
	 * @param importe Importe de la liquidación.
	 * @param numSec Número de secuencia.
	 * @return short Dígito de control.
	 */
	private static short obtenerDigitoControlAL12(String emisora, String identificacion, String importe, String numSec){
		short ctrlDigit = 0;
		String referencia = null;
		String aux = null;
		String aux1 = null;
		String aux2 = null;
		double param = 0;
		double param1 = 0;
		double param2 = 0;
		double param3 = 0;
		double imp = 0;
		double total = 0;
		int AA = 0;
		
		referencia = numSec;
		
		param1 = new Double(emisora).doubleValue();
		param2 = new Double(referencia).doubleValue();
		param = new Double(identificacion).doubleValue();
		
		aux = importe;
		aux = Util.removeChar(aux, '.');
		aux = Util.removeChar(aux, ',');
		imp = new Double(aux).doubleValue();
		
		param3 = param + imp;
		
		total = ((param1*76) + (param2*9) + ((param3-1)*55)) / 97;
		
		aux1 = new Double(total).toString();
		aux2 = Util.getDecimalsStr(aux1,  2);
		AA = new Integer(aux2).shortValue();
		
		ctrlDigit = (short)(99 - AA);
		
		return ctrlDigit;
   }
	
	
	/**
	 * Metodo que obtiene el digito de control para el número de referencia de una
	 * liquidación del tipo AL3.
	 * @param modelo Modelo de la tasa.
	 * @param emisora Identificador de la entidad emisora.
	 * @param indicadorCaptura Indicador de captura.
	 * @param numSec Número de secuencia para el modelo.
	 * @return short Dígito de control.
	 */
	private static short obtenerDigitoControlAL3(String modelo, String emisora,String indicadorCaptura, String numSec){
		short    ctrlDigit = 0;
		String   num = null;
		double   numJustWDigit = 0;
		double 	 emisor = 0;
		double   sum = 0;
		double   remainder = 0;
		int      factor = 7;
		
		num = modelo + indicadorCaptura + numSec;
		
		numJustWDigit = new Double(num).doubleValue();
		emisor = new Double(emisora).doubleValue();
		
		sum = numJustWDigit + emisor;
		remainder = Math.IEEEremainder(sum, factor);
		if(remainder < 0){
			remainder = remainder * -1;
		}
		if (remainder == 0)
		ctrlDigit = 7;
		else
		ctrlDigit = (short)remainder;
		
		return ctrlDigit;
	}
}
