package ieci.tecdoc.sgm.tram.secretaria.manager;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tecdoc.sgm.tram.secretaria.dao.SecContadoresDAO;
import ieci.tecdoc.sgm.tram.secretaria.helper.SecretariaConstants;

import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class ContadorManager {


	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(ContadorManager.class);

	public ContadorManager() throws ISPACException
	{

	}



	public static String getContador(IClientContext ctx, int tipoContador) throws ISPACException{

		String contadorConFormato="";
		SecContadoresDAO contadorAnt=null;
		//Obtenemos el año
		Date date=new Date(System.currentTimeMillis());
        GregorianCalendar todayGregorianCalendar = new GregorianCalendar();
  		todayGregorianCalendar.setTime(date);
  		int anio=todayGregorianCalendar.get(GregorianCalendar.YEAR);
		if(logger.isDebugEnabled()){
			logger.debug("Vamos a obtener el contador para el anio "+anio+" y el tipo de Contador "+tipoContador);
		}
		SecContadoresDAO contador= SecContadoresDAO.getContador(ctx.getConnection(), " WHERE ANIO="+anio+" AND TIPO="+tipoContador);
		if(contador==null){
			if(logger.isDebugEnabled()){
				logger.debug("No existe contador para el anio "+anio+
							" y el tipo de Contador "+tipoContador+
							"en la tabla"+SecretariaConstants.TBL_CONTADOR+".Creamos nueva entrada");
			}
			//obtenemos el contador del año anterior para poder conocer el formato
			contadorAnt=SecContadoresDAO.getContador(ctx.getConnection(),
																			" WHERE ANIO="+(anio-1)+" AND TIPO="+tipoContador);
			if(contadorAnt==null){
				//En caso de que no tengamos el año anterior obtenemos de cualquier año
				contadorAnt=SecContadoresDAO.getContador(ctx.getConnection(),
						" WHERE TIPO="+tipoContador);
				if(contadorAnt==null){
					logger.debug("En la tabla de contadores  para el tipo de contador "
							+tipoContador+" no hay registro ");
					throw new ISPACException("Error: No existe ningún registro para el tipo de contador "+tipoContador);
				}
			}
			contador=new SecContadoresDAO();
        	contador.createNew(ctx.getConnection());
	    	contador.set("ANIO", anio);
	    	contador.set("CONTADOR", 0);
	    	contador.set("TIPO", tipoContador);
	    	contador.set("FORMATO", contadorAnt.get("FORMATO"));


		}
		int num_contador=contador.getInt("CONTADOR") +1;
		contadorConFormato=contador.getString("FORMATO");

		//Tratamos el formato
		contadorConFormato = StringUtils.replace(contadorConFormato,
												SecretariaConstants.VARIABLE_DELIMIT +
												SecretariaConstants.VARIABLE_YEAR +
												SecretariaConstants.VARIABLE_DELIMIT, String.valueOf(anio));
		contadorConFormato = StringUtils.replace(contadorConFormato,
											SecretariaConstants.VARIABLE_DELIMIT +
											SecretariaConstants.VARIABLE_NUM_CONTADOR+
											SecretariaConstants.VARIABLE_DELIMIT,String.valueOf(num_contador));

		contador.set("CONTADOR", num_contador);
		contador.store(ctx.getConnection());
		return contadorConFormato;

	}




}