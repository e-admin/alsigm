/*
 * Created on 15-mar-2011
 *
 */
package ieci.tdw.ispac.api.rule.docs.tags;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.rule.IRule;
import ieci.tdw.ispac.api.rule.IRuleContext;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.dao.cat.CTEntityDAO;
import ieci.tdw.ispac.ispaclib.dao.entity.EntityFactoryDAO;
import ieci.tdw.ispac.ispaclib.dao.entity.MultivalueTable;
import ieci.tdw.ispac.ispaclib.dao.join.TableJoinFactoryDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.db.DbResultSet;
import ieci.tdw.ispac.ispaclib.entity.def.EntityDef;
import ieci.tdw.ispac.ispaclib.entity.def.EntityField;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.TypeConverter;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.jfree.util.Log;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * Cuando se genera un documento para un registro determinado  de una entidad,
 *  DOCUMENTOS A NIVEL DE ENTIDAD , se
 * ha de poder mostrar datos de dicho registro en el documento que se está
 * generando. Con el tag <ispactag entity='SPAC_DT_INTERVINIENTES'
 * property='xxx'/> se obtienen los valores de campos simples y con <ispactag
 * rule='PropertyRegSubstitute' entity='SPAC_DT_INTERVINIENTES'
 * property='TIPO_DIRECCION' codetable='SPAC_TBL_005' /> se obtienen los valores
 * de campos validados.
 *
 * Un formulario puede tener entidades secundarias y en algunos casos puede
 * resultar interesante mostrar algunos de esos campos en un documento generado
 * mediante plantilla.
 *
 * Para mostrar valores de campos validados de entidades secondarias se utiliza
 * la regla PropertyRegSubstitute.
 *
 * Para mostrar los valores de campos no validados de entidades secundarias se
 * ha de utilizar la regla PropertyRegRule
 *
 * Esta misma regla también permitirá mostrar campos de la entidad principal
 *
 * <ul>
 * Atributos
 * <li>entity: Entidad a la que pertenece la propiedad/campo que queremos
 * obtener (OBLIGATORIO)</li>
 * <li>property: Propiedad/Campo a mostrar (OBLIGATORIO)</li>
 * <li>sqlquery: Filtro a tener en cuenta en la consulta (OPCIONAL)</li>
 * <li>multivaluefieldseparator: Separadores para los valores de un campo
 * multivalor (OPCIONAL)</li>
 * <li>mainentity: Nombre de la entidad principal. Campo útil en los casos que
 * se quieran mostrar datos de una entidad auxiliar(OPCIONAL)</li>
 * <li>bindfield: Indica el campo que relaciona un registro de la entidad
 * principal (mainentity) con la entidad (entity). En aquellos casos que se
 * especifique valor para el atributo mainentity será necesario informar este
 * atributo.(OPCIONAL)</li>
 * <li>dateFormat: Indica el formato a utilizar en campos de tipo fecha, solo se
 * podrá indicar este atributo en aquellos casos que el campo a mostrar sea
 * fecha(OPCIONAL)</li>
 * <li>numberFormat:Indica el formato a utilizar en campo numéricos (OPCIONAL)</li>
 * </ul>
 * <p>
 * Ejemplos
 *
 * <ispactag rule='PropertyReg' entity='CONC_PARTICIPANTES'
 * property='OBSERVACIONES' mainentity='SPAC_DT_INTERVINIENTES'
 * bindfield='ID_PARTICIPANTE' />
 *
 * @author iecisa
 *
 */
public class PropertyRegRule implements IRule {
	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger
	.getLogger(PropertyRegSubstituteRule.class);

	public boolean init(IRuleContext rctx) throws ISPACRuleException {
		return true;
	}

	public boolean validate(IRuleContext rctx) throws ISPACRuleException {
		return true;
	}

	public Object execute(IRuleContext rctx) throws ISPACRuleException {

		try {
			IClientContext cct = rctx.getClientContext();

			IInvesflowAPI invesflowAPI = cct.getAPI();
			IEntitiesAPI entitiesAPI = invesflowAPI.getEntitiesAPI();

			String entityname = rctx.get("entity");
			String property = rctx.get("property");
			String sqlquery = rctx.get("sqlquery");
			String mainentity = rctx.get("mainentity");
			String bindfield = rctx.get("bindfield");
			String dateformat = rctx.get("dateFormat");
			String numberformat = rctx.get("numberFormat");
			String cadena = "";

			String multivaluefieldseparator = rctx
			.get("multivaluefieldseparator");

			if (StringUtils.isNotEmpty(sqlquery)) {
				sqlquery = "(" + sqlquery + ") AND ";
			}
			String regId = cct.getStateContext().getKey() + "";

			// Si estamos en el contexto del trámite el reg de la entidad se
			// obtiene del contexto del documento
			if (cct.getStateContext().getEntity() == SpacEntities.SPAC_DT_TRAMITES) {

				NodeList nodeList = rctx.getDocContext().getElementsByTagName(
				"_regentityid");

				if (nodeList != null) {
					Node regentityid = nodeList.item(0);
					// Obtengo el primer hijo que es el que tiene el id
					if (regentityid != null) {
						regentityid = regentityid.getFirstChild();
						if (regentityid != null) {
							// Obtengo el valor
							regId = regentityid.getNodeValue();
						}
					}
				}

			}
			if (logger.isDebugEnabled()) {
				Log.debug(" Ejecutando regla PropertyRegRule regiId:" + regId);
			}
			if (StringUtils.isNotBlank(regId + "")) {
				if (StringUtils.isBlank(sqlquery)) {
					sqlquery = "";
				}
				if (StringUtils.isBlank(mainentity)) {
					sqlquery += " " + entityname + ".ID= " + regId;
				} else {
					sqlquery += " " + entityname + "." + bindfield + "="
					+ regId;
				}
			}
			// Obtener el registro de la entidad
			IItemCollection itemCollection = null;

			if (StringUtils.isEmpty(mainentity)) {
				itemCollection = entitiesAPI.getEntities(entityname, rctx
						.getNumExp(), sqlquery);
			} else {
				TableJoinFactoryDAO tableJoinFactoryDAO = new TableJoinFactoryDAO();
				tableJoinFactoryDAO.addTable(entityname, entityname);
				tableJoinFactoryDAO.addTable(mainentity, mainentity);
				itemCollection = tableJoinFactoryDAO.queryTableJoin(
						cct.getConnection(),
						" WHERE " + sqlquery + "AND " + mainentity + ".ID="
						+ regId + " AND " + mainentity + ".NUMEXP="
						+ entityname + ".NUMEXP").disconnect();
			}

			if (itemCollection.next()) {

				IItem entity = itemCollection.value();

				CTEntityDAO ctentity = EntityFactoryDAO.getInstance()
				.getCatalogEntityDAO(cct.getConnection(), entityname);
				EntityDef entityDef = EntityDef.parseEntityDef(ctentity
						.getDefinition());
				EntityField entityField = entityDef.getField(property);
				// Si el campo es multivalor se sacan todos los sustitutos de
				// todos los campos
				if (entityField.isMultivalue()) {
					DbResultSet dbrs = null;
					String stmt = null;
					DbCnt cnt = cct.getConnection();
					StringBuffer buffer = new StringBuffer();
					try {
						stmt = "SELECT "
							+ MultivalueTable.FIELD_VALUE
							+ " FROM "
							+ MultivalueTable.getInstance()
							.composeMultivalueTableName(
									ctentity.getName(),
									entityField.getType()
									.getJdbcType())
									+ " WHERE "
									+ MultivalueTable.FIELD_FIELD
									+ " = '"
									+ DBUtil.replaceQuotes(entityField
											.getPhysicalName().toUpperCase())
											+ "' " + " AND " + MultivalueTable.FIELD_REG
											+ " = " + entity.getString(entityname + ":ID");
						dbrs = cnt.executeQuery(stmt);
						while (dbrs.getResultSet().next()) {
							if (StringUtils.isEmpty(dateformat)) {
								buffer.append(dbrs.getResultSet().getString(
										MultivalueTable.FIELD_VALUE));
							} else if (StringUtils.isNotEmpty(numberformat)) {
								buffer.append(TypeConverter.toString(dbrs
										.getResultSet().getDouble(
												MultivalueTable.FIELD_VALUE),
												numberformat));

							} else {
								buffer.append(TypeConverter.toString(dbrs
										.getResultSet().getDate(
												MultivalueTable.FIELD_VALUE),
												dateformat));
							}

							if (StringUtils.isEmpty(multivaluefieldseparator)) {
								buffer.append("\n");
							} else {
								buffer
								.append(StringUtils
										.unescapeJava(multivaluefieldseparator));
							}
						}
					} catch (SQLException e) {
						Log.error("Error ejecutando:PropertyRegRule" + e);
						throw new ISPACException(e);
					} finally {
						if (dbrs != null) {
							dbrs.close();
						}
						cct.releaseConnection(cnt);
					}
					if (logger.isDebugEnabled()) {
						Log.debug(" Resultado regla PropertyRegRule regiId:"
								+ buffer.toString());
					}
					return buffer.toString();

				}

				// Obtener el valor
				if (StringUtils.isNotEmpty(dateformat)) {
					cadena = TypeConverter.toString(entity.getDate(entityname
							+ ":" + property), dateformat);

				} else if (StringUtils.isNotEmpty(numberformat)) {
					cadena = TypeConverter.toString(entity.getDouble(entityname
							+ ":" + property), dateformat);

				} else {
					cadena = entity.getString(entityname + ":" + property);
				}

			}

			if (logger.isDebugEnabled()) {
				Log.debug(" Resultado regla PropertyRegRule regiId:" + cadena);
			}

			return cadena;
		} catch (ISPACException e) {
			throw new ISPACRuleException("Error obteniendo el valor.", e);
		}
	}

	public void cancel(IRuleContext rctx) throws ISPACRuleException {

	}
}
