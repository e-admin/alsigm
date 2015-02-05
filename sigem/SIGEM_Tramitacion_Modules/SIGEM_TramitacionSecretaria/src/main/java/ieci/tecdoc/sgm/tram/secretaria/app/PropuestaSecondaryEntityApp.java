package ieci.tecdoc.sgm.tram.secretaria.app;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.app.GenericSecondaryEntityApp;
import ieci.tdw.ispac.ispaclib.configuration.ConfigurationMgr;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tecdoc.sgm.tram.secretaria.helper.SecretariaConstants;

import org.apache.commons.lang.StringUtils;

public class PropuestaSecondaryEntityApp extends GenericSecondaryEntityApp {

	private static final long serialVersionUID = 8785811211339148577L;

	public PropuestaSecondaryEntityApp(ClientContext context) {
		super(context);
	}

	/**
	 * Comprobamos si la propuesta ya sido enviada a sesión, en caso
	 * afirmativo,se informará propiedad adicional con 1
	 *
	 */
	public void setItem(IItem item) throws ISPACException {
		super.setItem(item);

		IEntitiesAPI entitiesAPI = mContext.getAPI().getEntitiesAPI();
		setProperty("sentToSession", "0");
		IItem relationType = null;
		String envio = "0";
		// Se obtiene el tipo de sesiçon si plenaria o de junta

		IItemCollection sesiones = entitiesAPI.getEntities(
				SecretariaConstants.ENTITY_PROPUESTA, msExpedient);
		String relacion="";
		if (sesiones.next()) {
			IItem sesion = sesiones.value();
			envio = sesion.getString("ENVIO_SESION");
			if (!StringUtils.isBlank(envio) && SecretariaConstants.VALUE_SESION_JUNTA_GOBIERNO.equalsIgnoreCase(envio)) {
				relacion = ConfigurationMgr.getVarGlobal(mContext,
						SecretariaConstants.VALUE_RELACION_JUNTA_PROPUESTA);

			} else if (!StringUtils.isBlank(envio)
					&& SecretariaConstants.VALUE_SESION_PLENO.equalsIgnoreCase(envio)) {
				relacion = ConfigurationMgr.getVarGlobal(mContext,
						SecretariaConstants.VALUE_RELACION_SESION_PROPUESTA);

			}


		}
		if (StringUtils.isNotBlank(relacion)) {


			// Comprobamos si el expediente al que pertenece la propuesta tiene
			// alguna relacion de tipo Sesión/Propuesta
			if (relationType != null) {
				String query = "WHERE NUMEXP_HIJO = '"
						+ DBUtil.replaceQuotes(msExpedient)
						+ "' AND  RELACION='"
						+ DBUtil.replaceQuotes(relacion)
						+ "' ";

				int count = entitiesAPI.countResultQuery(
						"SPAC_EXP_RELACIONADOS", query);
				if (count > 0) {
					setProperty("sentToSession", "1");
				}
			}
		}

	}

}