package ieci.tecdoc.sgm.tram.secretaria.rules.libro;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.rule.IRule;
import ieci.tdw.ispac.api.rule.IRuleContext;
import ieci.tecdoc.sgm.tram.secretaria.helper.SecretariaConstants;

import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;




public class InitEntityLibroRule  implements IRule {
	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(InitEntityLibroRule.class);

	public void cancel(IRuleContext rulectx) throws ISPACRuleException {


	}

	public Object execute(IRuleContext rulectx) throws ISPACRuleException {
		try {
			//Creamos un registro de la entidad de libro y se establecen valores
			IEntitiesAPI entitiesAPI = rulectx.getClientContext().getAPI().getEntitiesAPI();
			IItem libro=null;
			// Comprobar si ya se ha creado el registro
			IItemCollection itemcol= entitiesAPI.getEntities(SecretariaConstants.ENTITY_LIBRO, rulectx.getNumExp());
			if(itemcol.next()){
				if(logger.isDebugEnabled()){
					logger.debug("InitEntityLibroRule:execute. Existe ya un registro de la entidad libro creado");
				}
				libro=itemcol.value();
			}
			else{
				if(logger.isDebugEnabled()){
					logger.debug("InitEntityLibroRule:execute. Creamos un registro de la entidad");
				}
				libro=entitiesAPI.createEntity(SecretariaConstants.ENTITY_LIBRO, rulectx.getNumExp());
			}

			itemcol=entitiesAPI.getEntities("SPAC_EXPEDIENTES", rulectx.getNumExp());
			IItem expediente=itemcol.value();
			Date fapertura=expediente.getDate("FAPERTURA");
			GregorianCalendar fechaGregorianCalendar = new GregorianCalendar();
            fechaGregorianCalendar.setTime(fapertura);
            if(logger.isDebugEnabled()){
				logger.debug("InitEntityLibroRule:execute. Año del libro: "+fechaGregorianCalendar.get(GregorianCalendar.YEAR));
			}

			libro.set("NUMEXP", rulectx.getNumExp());
			libro.set(SecretariaConstants.FIELD_LIBRO_ANIO, fechaGregorianCalendar.get(GregorianCalendar.YEAR));
			libro.store(rulectx.getClientContext());


		} catch (ISPACException e) {
			logger.error("Error en la regla " + getClass().getName(), e);
			throw new ISPACRuleException(e);
		}
		return null;
	}

	public boolean init(IRuleContext rulectx) throws ISPACRuleException {
		return true;
	}

	public boolean validate(IRuleContext rulectx) throws ISPACRuleException {

		return true;
	}



}
