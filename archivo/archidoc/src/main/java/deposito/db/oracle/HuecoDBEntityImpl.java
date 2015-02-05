package deposito.db.oracle;

import ieci.core.db.DbConnection;
import ieci.core.db.DbSelectFns;

import org.apache.log4j.Logger;

import common.db.DBCommand;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.lang.MutableBoolean;

import deposito.db.ElementoAsignableDBEntity;
import deposito.db.ElementoNoAsignableDBEntity;
import deposito.db.common.HuecoDbEntityImplBase;

/**
 * Implementación de los métodos de acceso a los datos referidos a huecos en el
 * fondo físico
 */
public class HuecoDBEntityImpl extends HuecoDbEntityImplBase {

	/** Logger de la clase */
	static Logger logger = Logger.getLogger(HuecoDBEntityImpl.class);

	/**
	 * @param dataSource
	 */
	public HuecoDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public HuecoDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	public boolean checkHuecosByElementoNoAsignable(
			final String idElementoNoAsignable, final String[] estados) {
		final MutableBoolean hayHuecosEnEstados = new MutableBoolean(false);
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				final StringBuffer huecosOcupadosQual = new StringBuffer(
						"WHERE ")
						.append(DBUtils.generateInTokenField(CAMPO_ESTADO,
								estados))
						.append(" AND ")
						.append(IDELEMPADRE_COLUMN_NAME)
						.append(" in")
						.append(" (select ")
						.append(ElementoAsignableDBEntity.ID_COLUMN_NAME)
						.append(" from ")
						.append(ElementoAsignableDBEntity.TABLE_NAME)
						.append(" where ")
						.append(ElementoAsignableDBEntity.IDELEMNPADRE_COLUMN_NAME)
						.append(" in")
						.append(" (select ")
						.append(ElementoNoAsignableDBEntity.ID_COLUMN_NAME)
						.append(" from ")
						.append(ElementoNoAsignableDBEntity.TABLE_NAME)
						.append(" start with ")
						.append(ElementoNoAsignableDBEntity.ID_COLUMN_NAME)
						.append("='")
						.append(idElementoNoAsignable)
						.append("'")
						.append(" connect by prior ")
						.append(ElementoNoAsignableDBEntity.ID_COLUMN_NAME)
						.append("=")
						.append(ElementoNoAsignableDBEntity.IDPADRE_COLUMN_NAME)
						.append("))");
				hayHuecosEnEstados.setValue(DbSelectFns.rowExists(
						getConnection(), TABLE_NAME,
						huecosOcupadosQual.toString()));
			}
		};
		command.execute();
		return hayHuecosEnEstados.getValue();
	}
}