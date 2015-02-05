package ieci.tdw.ispac.ispacmgr.components.worklist;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.IProcedure;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.bean.TreeItemBean;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispacweb.components.DefaultWebComponent;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
public abstract class AbstractProcedureTreeComponent extends
		DefaultWebComponent {

	/**
	 * Obtiene el árbol de procedimientos
	 * @param ctx Contexto de cliente.
	 * @return Árbol de procedimientos
	 * @throws ISPACException si ocurre algún error.
	 */
	protected TreeItemBean getProcedureTree(ClientContext ctx)
			throws ISPACException {

		Map<String, String> tableentitymap = new HashMap<String, String>();
		tableentitymap.put("SPAC_CT_PROCEDIMIENTO", "SPAC_CT_PROCEDIMIENTOS");
		tableentitymap.put("SPAC_P_PROCEDIMIENTO", "SPAC_P_PROCEDIMIENTOS");

		// Seleccionamos la última versión de cada procedimiento.
		String whereClause = " WHERE SPAC_P_PROCEDIMIENTO.ID = SPAC_CT_PROCEDIMIENTO.ID"
				+ " AND SPAC_P_PROCEDIMIENTO.TIPO=" + IProcedure.PROCEDURE_TYPE;

		IItemCollection itemcol = ctx.getAPI().getCatalogAPI()
				.queryCTEntities(tableentitymap, whereClause);

		// Obtenemos el árbol de ItemBeans
		return CollectionBean.getBeanTree(itemcol, "SPAC_P_PROCEDIMIENTO:ID",
				"SPAC_CT_PROCEDIMIENTO:ID_PADRE");
	}

}
