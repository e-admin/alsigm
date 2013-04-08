package fondos.db.postgreSQL;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbSelectStatement;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.mutable.Mutable;
import org.apache.commons.lang.mutable.MutableObject;
import org.apache.log4j.Logger;

import util.CollectionUtils;

import common.db.DBUtils;
import common.db.DbDataSource;

import fondos.db.UpdateCodRefsReturns;
import fondos.db.commonPostgreSQLServer.ElementoCuadroClasificacionPostgreeSQLServerBaseDBEntityImpl;
import fondos.model.IElementoCuadroClasificacion;

public class ElementoCuadroClasificacionDBEntityImpl extends
		ElementoCuadroClasificacionPostgreeSQLServerBaseDBEntityImpl {

	/** Logger de la clase */
	static Logger logger = Logger
			.getLogger(ElementoCuadroClasificacionDBEntityImpl.class);

	public ElementoCuadroClasificacionDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);

	}

	public ElementoCuadroClasificacionDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fondos.db.commonPostgreeSQLServer.
	 * ElementoCuadroClasificacionPostgreeSQLServerBaseDBEntityImpl
	 * #getIdsHijos(java.lang.String, int)
	 */
	// TODO ZMIGRACION BD - PRIOR (PROBADO
	public List getIdsHijos(String idElementoCuadro, int tipoElemento) {
		// SELECT * FROM asgfelementocf WHERE id IN (SELECT value FROM connectby
		// ('asgfelementocf', 'id', 'idpadre', '1', 0)
		// AS t(value text, idpadre text, level int)) AND TIPO=1;

		// obtener ids de todos los hijos
		StringBuffer qual = new StringBuffer();
		qual.append(" WHERE ");
		qual.append(ID_ELEMENTO_FIELD.getQualifiedName()).append(" IN ( ");
		qual.append(
				" SELECT idHijo FROM connectby('asgfelementocf', 'id', 'idpadre', '")
				.append(idElementoCuadro).append("', 0)");
		qual.append("  AS t(idHijo text, idpadre text, level int))  ");
		if (tipoElemento >= 0) {
			qual.append(" AND ")
					.append(DBUtils.generateEQTokenField(TIPO_ELEMENTO_FIELD,
							tipoElemento)).append(" ");
		}

		DbColumnDef id = new DbColumnDef("value", ID_ELEMENTO_FIELD);
		List ids = getVOS(qual.toString(),
				ElementoCuadroClasificacionDBEntityImpl.TABLE_NAME_ELEMENTO,
				new DbColumnDef[] { id }, MutableObject.class);
		List idList = new ArrayList();
		if (!CollectionUtils.isEmpty(ids)) {
			for (int i = 0; i < ids.size(); i++)
				idList.add((String) ((Mutable) ids.get(i)).getValue());
		}
		return idList;
	}

	public List getIdsHijos(String idElementoCuadro) {
		return getIdsHijos(idElementoCuadro, -1);
	}

	public UpdateCodRefsReturns updateCodRefElementoYHijos(String idElemento,
			String separador, boolean updateOtherTables) {
		IElementoCuadroClasificacion elementoActualizado = null;

		DbSelectStatement stmt = null;

		StringBuffer buff = new StringBuffer(UPDATE_COD_REF_PROCEDURE)
				.append("('").append(idElemento).append("','")
				.append(separador).append("','")
				.append(updateOtherTables ? 'S' : 'N').append("')");

		try {
			stmt = new DbSelectStatement();
			stmt.create(getConnection(),
					DbSelectStatement.getSelectStmtText(buff.toString()));
			stmt.execute();
			stmt.release();

			elementoActualizado = getElementoCuadroClasificacion(idElemento);
		} catch (Exception e) {
			try {
				DbSelectStatement.ensureRelease(stmt, e);
			} catch (Exception e1) {
				logger.error(e1);
			}
			logger.error(e);
		}

		if (elementoActualizado != null) {
			return new UpdateCodRefsReturns(
					elementoActualizado.getFinalCodRefPadre(),
					elementoActualizado.getCodReferencia());
		} else {
			return null;
		}
	}

}
