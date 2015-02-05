package ieci.tdw.ispac.ispaccatalog.app;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IReportsAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaccatalog.messages.Messages;
import ieci.tdw.ispac.ispaclib.app.SimpleEntityApp;
import ieci.tdw.ispac.ispaclib.bean.ValidationError;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.cat.CTReportDAO;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.util.Date;

public class ReportApp extends SimpleEntityApp {

	public ReportApp(ClientContext context) {
		super(context);
	}

	public void initiate() throws ISPACException {
		super.initiate();
	}

	public boolean validate() throws ISPACException {

		boolean ret = super.validate();

		if (ret) {

			ValidationError error = null;
			IInvesflowAPI invesFlowAPI = mContext.getAPI();
			
			String xml = getString("XML");
			String nombre = getString("NOMBRE");
			
			//Se hace en el validation.xml
			/* 
			if (StringUtils.isBlank(nombre)) {
				error = new ValidationError("property(NOMBRE)", "errors.required",new String[]{Messages.getString(mContext.getAppLanguage(), "form.report.propertyLabel.name")});
				addError(error);
				return false;
			}

			
			if (StringUtils.isBlank(xml)) {
				error = new ValidationError("property(XML)", "errors.required",new String[]{Messages.getString(mContext.getAppLanguage(), "form.report.propertyLabel.xml")});
				addError(error);
				return false;
			}
			 */
			
			// La validacion del xml se realiza al compilar
			try {
				IReportsAPI reportsAPI = invesFlowAPI.getReportsAPI();
				reportsAPI.checkReport(xml);
			} catch (Exception e) {
				error = new ValidationError("property(XML)", "errors.invalid", new String[]{Messages.getString(mContext.getAppLanguage(), "form.report.propertyLabel.xml")});
				addError(error);
				return false;
			}

			ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();

			// Bloqueo de la tabla
			catalogAPI.setCTEntitiesForUpdate(ICatalogAPI.ENTITY_CT_INFORMES, "");

			// Nombre único del informe
			IItemCollection itemcol = catalogAPI.queryCTEntities(
					ICatalogAPI.ENTITY_CT_INFORMES, " WHERE NOMBRE = '"
							+ DBUtil.replaceQuotes(nombre) + "' AND ID !="
							+ getString("ID"));
			if (itemcol.next()) {

				addError(new ValidationError("property(NOMBRE)",
						"error.report.nameDuplicated", new String[]{nombre}));
				return false;
			}
			
			String filas = getString("FILAS");
			String columnas = getString("COLUMNAS");
			
			if (StringUtils.isNotBlank(filas) && (StringUtils.isBlank(columnas))) {

				addError(new ValidationError("property(COLUMNAS)",
						"errors.required", new String[]{Messages.getString(mContext.getAppLanguage(), "form.report.propertyLabel.columnas")}));
				return false;
			}

			if (StringUtils.isNotBlank(columnas) && (StringUtils.isBlank(filas))) {

				addError(new ValidationError("property(FILAS)",
						"errors.required", new String[]{Messages.getString(mContext.getAppLanguage(), "form.report.propertyLabel.filas")}));
				return false;
			}
			
			return true;
		}
		else {
			return false;
		}
	}

	public void store() throws ISPACException {

		// Si el tipo es genérico, eliminar las relaciones con objetos
		if (mitem.getInt("TIPO") == CTReportDAO.GENERIC_TYPE) {
			mContext.getAPI().getProcedureAPI().deletePReport(mitem.getKeyInt());
		}
		
		// Establecer la fecha actual
		mitem.set("FECHA", new Date());
		
		super.store();
		//Si el campo VISIBLIDAD !=0, no puede tener ningún responsable asociado, en caso de que los tenga se borrar
		if (mitem.getInt("VISIBILIDAD") != CTReportDAO.VISIBILIDAD_RESTRINGIDA) {
			mContext.getAPI().getProcedureAPI().deleteUsersAssociatedToReport(mitem.getKeyInt());
		}
	}

}
