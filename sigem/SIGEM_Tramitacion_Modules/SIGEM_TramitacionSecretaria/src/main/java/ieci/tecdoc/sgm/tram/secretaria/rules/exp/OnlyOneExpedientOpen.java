package ieci.tecdoc.sgm.tram.secretaria.rules.exp;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.rule.IRule;
import ieci.tdw.ispac.api.rule.IRuleContext;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.dao.join.TableJoinFactoryDAO;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispactx.TXConstants;
import ieci.tecdoc.sgm.tram.secretaria.helper.SecretariaResourcesHelper;

import org.apache.log4j.Logger;
/**
 * Se comprueba si existe algún expediente del procedimiento abierto,
 * en caso de que exista NO se permmite crear ningún otro
 * @author IECISA
 *
 */
public class OnlyOneExpedientOpen implements IRule {

	 /**
     * Logger de la clase.
     */
    private static final Logger logger = Logger.getLogger(OnlyOneExpedientOpen.class);

	public void cancel(IRuleContext rulectx) throws ISPACRuleException {


	}

	public Object execute(IRuleContext rulectx) throws ISPACRuleException {

		return null;
	}

	public boolean init(IRuleContext rulectx) throws ISPACRuleException {

		return true;
	}

	public boolean validate(IRuleContext rulectx) throws ISPACRuleException {

		int procedureId=rulectx.getProcedureId();
		if(logger.isDebugEnabled()){
			logger.debug("Comprobamos si existe algún expediente ya creado para el procedureId: "+procedureId);
		}

		try {

			IClientContext ctx = rulectx.getClientContext();
			TableJoinFactoryDAO tableJoinFactoryDAO = new TableJoinFactoryDAO();
			tableJoinFactoryDAO.addTable("SPAC_PROCESOS","SPAC_PROCESOS");
			tableJoinFactoryDAO.addTable("SPAC_CT_PROCEDIMIENTOS" , "SPAC_CT_PROCEDIMIENTOS");
			IItemCollection procs = tableJoinFactoryDAO.queryTableJoin(ctx.getConnection(),
					"WHERE ID_PCD=SPAC_CT_PROCEDIMIENTOS.ID AND SPAC_PROCESOS.ESTADO="+TXConstants.STATUS_OPEN+
					" AND SPAC_PROCESOS.NUMEXP!='"+DBUtil.replaceQuotes(rulectx.getNumExp())+"'"+
					" AND COD_PCD IN (SELECT COD_PCD FROM SPAC_CT_PROCEDIMIENTOS  CP where CP.ID="+procedureId+")").disconnect();

			if(procs.next()){
				rulectx.setInfoMessage(SecretariaResourcesHelper.getMessage(rulectx.getClientContext().getLocale(),"onlyOne.expedient.open"));
				return false;
			}
			return true;

		} catch (ISPACException e) {
			if(logger.isDebugEnabled()){
				logger.debug("Error en OnlyOneExpedientOpen:validate "+e);
			}
			throw new ISPACRuleException(e);
		}


	}

}

