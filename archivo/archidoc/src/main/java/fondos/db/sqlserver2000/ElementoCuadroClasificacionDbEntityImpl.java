package fondos.db.sqlserver2000;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.mutable.Mutable;
import org.apache.commons.lang.mutable.MutableObject;

import util.CollectionUtils;
import xml.config.ConfiguracionFondos;
import xml.config.ConfiguracionSistemaArchivoFactory;

import common.Constants;
import common.db.DBUtils;
import common.db.DbDataSource;

import fondos.db.commonPostgreSQLServer.ElementoCuadroClasificacionPostgreeSQLServerBaseDBEntityImpl;
import fondos.model.ElementoCuadroClasificacion;
import fondos.vos.ElementoCuadroClasificacionVO;

public class ElementoCuadroClasificacionDbEntityImpl extends
		ElementoCuadroClasificacionPostgreeSQLServerBaseDBEntityImpl {

	public static final String GET_COD_REF_SQL_SERVER_FUNCTION = "dbo.GETCODREF";

	public ElementoCuadroClasificacionDbEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public ElementoCuadroClasificacionDbEntityImpl(DbConnection conn) {
		super(conn);
	}

	public List getIdsHijos(String idElementoCuadro, int tipoElemento) {
		List ret = new ArrayList();
		// obtener los hijos
		StringBuffer qual = new StringBuffer().append(" WHERE (");

		if (tipoElemento >= 0) {
			qual.append(DBUtils.generateEQTokenField(TIPO_ELEMENTO_FIELD,
					tipoElemento));
			qual.append(" AND ");
		}
		qual.append(
				DBUtils.generateEQTokenField(IDPADRE_FIELD, idElementoCuadro))
				.append(")").toString();
		DbColumnDef id = new DbColumnDef("value", ID_ELEMENTO_FIELD);
		List hijosDelElemento = getVOS(qual.toString(), TABLE_NAME_ELEMENTO,
				new DbColumnDef[] { id }, MutableObject.class);
		if (!CollectionUtils.isEmpty(hijosDelElemento)) {
			for (Iterator itHijos = hijosDelElemento.iterator(); itHijos
					.hasNext();) {
				Mutable hijo = (Mutable) itHijos.next();
				ret.add((String) hijo.getValue());

				// obtener nietos
				List idsHijos = getIdsHijos((String) hijo.getValue(),
						tipoElemento);
				ret.addAll(idsHijos);
			}
		}
		return ret;
	}

	public List getIdsHijos(String idElementoCuadro) {
		return getIdsHijos(idElementoCuadro, -1);
	}

	public String getCodRefElementoCfFunction(final String id) {

		ConfiguracionFondos config = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionFondos();
		String separador = config.getDelimitadorCodigoReferencia();

		String qual = new StringBuffer(DBUtils.WHERE).append(
				DBUtils.generateEQTokenField(ID_ELEMENTO_FIELD, id)).toString();

		StringBuffer columnText = new StringBuffer()
				.append(GET_COD_REF_SQL_SERVER_FUNCTION)
				.append(DBUtils.ABRIR_PARENTESIS).append(ID_ELEMENTO_FIELD)
				.append(Constants.COMMA).append(DBUtils.COMILLA_SIMPLE)
				.append(separador).append(DBUtils.COMILLA_SIMPLE)
				.append(DBUtils.CERRAR_PARENTESIS);

		DbColumnDef column = new DbColumnDef(CODREFERENCIA_COLUMN_NAME,
				columnText.toString(), DbDataType.SHORT_TEXT);
		DbColumnDef[] cols = new DbColumnDef[] { column };

		ElementoCuadroClasificacionVO elemento = (ElementoCuadroClasificacionVO) getVO(
				qual, TABLE_NAME_ELEMENTO, cols,
				ElementoCuadroClasificacion.class);
		if (elemento != null) {
			return elemento.getCodReferencia();
		} else {
			return null;
		}
	}
}
