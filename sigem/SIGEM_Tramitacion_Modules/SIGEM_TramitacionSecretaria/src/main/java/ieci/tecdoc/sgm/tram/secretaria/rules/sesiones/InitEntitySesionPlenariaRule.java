package ieci.tecdoc.sgm.tram.secretaria.rules.sesiones;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.rule.IRule;
import ieci.tdw.ispac.api.rule.IRuleContext;
import ieci.tecdoc.sgm.tram.secretaria.helper.SecretariaConstants;
import ieci.tecdoc.sgm.tram.secretaria.manager.ContadorManager;

import org.apache.log4j.Logger;


/**
 * Establece el número de la sesión
 * @author iecisa
 *
 */
public class InitEntitySesionPlenariaRule  implements IRule {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger (InitEntitySesionPlenariaRule.class);

	public void cancel(IRuleContext rulectx) throws ISPACRuleException {


	}

	public Object execute(IRuleContext rulectx) throws ISPACRuleException {
		try {
			//Creamos un registro de la entidad de sesión plenaria y se establecen valores
			IEntitiesAPI entitiesAPI = rulectx.getClientContext().getAPI().getEntitiesAPI();
			IItem sesion_plenaria=null;
			String num_sesion="";
			// Comprobar si ya se ha creado el registro
			IItemCollection itemcol= entitiesAPI.getEntities(SecretariaConstants.ENTITY_SESIONES_PLENARIAS, rulectx.getNumExp());
			if(itemcol.next()){
				if(logger.isDebugEnabled()){
					logger.debug("InitEntitySesionPlenaria :execute. Existe ya un registro de la entidad sesiones plenarias creado");
				}
				sesion_plenaria=itemcol.value();
			}
			else{
				if(logger.isDebugEnabled()){
					logger.debug("InitEntitySesionPlenaria :execute. Creamos un registro de la entidad");
				}
				sesion_plenaria=entitiesAPI.createEntity(SecretariaConstants.ENTITY_SESIONES_PLENARIAS, rulectx.getNumExp());
			}


            if(logger.isDebugEnabled()){
				logger.debug("InitEntitySesionPlenaria. Número de sesión: "+num_sesion);
			}

            num_sesion=ContadorManager.getContador(rulectx.getClientContext(), SecretariaConstants.TIPO_CONTADOR_SESIONES_PLENARIAS);
			sesion_plenaria.set("NUMEXP", rulectx.getNumExp());
			sesion_plenaria.set(SecretariaConstants.FIELD_SESIONES_PLENARIAS_NUM_SESION, num_sesion);
			sesion_plenaria.store(rulectx.getClientContext());


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
