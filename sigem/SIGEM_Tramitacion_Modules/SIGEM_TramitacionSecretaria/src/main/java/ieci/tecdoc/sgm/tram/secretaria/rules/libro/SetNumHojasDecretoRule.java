package ieci.tecdoc.sgm.tram.secretaria.rules.libro;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tecdoc.sgm.tram.secretaria.helper.SecretariaConstants;

import org.apache.log4j.Logger;


/**
 * Si el documento esta firmado  por todos sus firmantes ya tendra el numero de hojas, pero en caso contrario
 * no y si ya ha firmado el alcalde es decreto y debe tener num hojas calculado
 * @author Iecisa
 * @version $Revision$
 *
 */
public class SetNumHojasDecretoRule extends SetNumHojasRule {

	private static final Logger logger = Logger
	.getLogger(SetNumHojasDecretoRule.class);




	public String getEntityName() {
		return SecretariaConstants.ENTITY_DECRETO;
	}

	public String getFieldNumHojasName() {
		return SecretariaConstants.FIELD_DECRETO_NUM_HOJAS;
	}

	public String getIdTpDoc() throws ISPACException {

		return  ctx.getAPI().getCatalogAPI().getIdTpDocByCode(SecretariaConstants.COD_TPDOC_DECRETO);
	}


	public String getKeyMessage() {
		return "noDocDecretoSign.CreacionDecreto" 	;
	}




}
