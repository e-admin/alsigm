package ieci.tdw.ispac.api.rule.states;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISPACEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.rule.IRule;
import ieci.tdw.ispac.api.rule.IRuleContext;
import ieci.tdw.ispac.ispaclib.context.IClientContext;

import java.util.Date;

/**
 * Establecer Estado Administrativo
 *
 * Establece el estado administrativo
 * en el campo ESTADOADM de la entidad de SPAC_EXPEDIENTES.
 *
 */
public abstract class ChangeStateAdmRule implements IRule {

        public abstract String getEstado();

        public boolean init(IRuleContext rulectx) throws ISPACRuleException {

                return true;
        }

        public boolean validate(IRuleContext rulectx) throws ISPACRuleException {

                return true;
        }

        public Object execute(IRuleContext rulectx) throws ISPACRuleException {

                try {
                        IClientContext cct = rulectx.getClientContext();

                        IInvesflowAPI invesflowAPI = cct.getAPI();
                        IEntitiesAPI entitiesAPI = invesflowAPI.getEntitiesAPI();

                        String numexp = rulectx.getNumExp();

                    // Obtener la entidad expediente
                    IItemCollection itemCollection = entitiesAPI.getEntities(ISPACEntities.DT_ID_EXPEDIENTES, numexp);
                    IItem expediente = itemCollection.value();

                    if (!getEstado().equals(expediente.getString("ESTADOADM"))) {

                            // Establecer el estado administrativo
                                expediente.set("ESTADOADM", getEstado());
                            //Actualizamos la fecha del estado
                                expediente.set("FESTADO" ,new Date());
                                expediente.store(cct);
                        }

                        return null;
                }
                catch (ISPACException e){
                        throw new ISPACRuleException(e);
                }
        }

        public void cancel(IRuleContext rulectx) throws ISPACRuleException {
        }

}