package configuracion.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;

import java.io.IOException;
import java.io.StringReader;
import java.net.URL;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.xmlrules.DigesterLoader;
import org.xml.sax.SAXException;

import common.Globals;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.util.StringUtils;
import common.vos.DatosGeograficosVO;
import common.vos.PaisVO;
import common.vos.PaisesVO;

import configuracion.model.GestionInfoSistemaBIImpl;
import configuracion.vos.InfoSistemaVO;

public class InfoSistemaDBEntityImpl extends DBEntity implements
		IInfoSistemaDBEntity {

	private static final String KEY_DATOS_GEOGRAFICOS = "DATOS_GEOGRAFICOS";
	private static final String KEY_MAP_PAISES = "MAP_PAISES";
	private static final String KEY_MANEJADOR_ITEXT = "MANEJADOR_ITEXT";

	public static final String TABLE_NAME = "AGINFOSISTEMA";
	public static final String NOMBRE_COLUMN_NAME = "NOMBRE";
	public static final String VALOR_COLUMN_NAME = "VALOR";
	public static final String FECHA_COLUMN_NAME = "FECHAACTUALIZACION";

	public static final DbColumnDef NOMBRE_FIELD = new DbColumnDef(null,
			TABLE_NAME, NOMBRE_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef VALOR_FIELD = new DbColumnDef(null,
			TABLE_NAME, VALOR_COLUMN_NAME, DbDataType.LONG_TEXT, 1024, false);

	public static final DbColumnDef FECHA_FIELD = new DbColumnDef(null,
			TABLE_NAME, FECHA_COLUMN_NAME, DbDataType.DATE_TIME, 10, false);

	public static final DbColumnDef[] COLS_DEFS = { NOMBRE_FIELD, VALOR_FIELD,
			FECHA_FIELD };

	PaisVO paisVO = new PaisVO();

	public InfoSistemaDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public InfoSistemaDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	/**
	 * Obtiene el nombre de la tabla
	 * 
	 * @return
	 */
	public String getTableName() {
		return TABLE_NAME;
	}

	public DatosGeograficosVO getDatosGeograficos() {
		InfoSistemaVO infoSistemaVO = getInfoSistema(KEY_DATOS_GEOGRAFICOS);

		if (infoSistemaVO != null) {
			String xmlInfo = infoSistemaVO.getValor();
			URL rules = GestionInfoSistemaBIImpl.class.getClassLoader()
					.getResource(Globals.RULES_DATOS_GEOGRAFICOS);

			Digester digester = DigesterLoader.createDigester(rules);
			try {
				if (!StringUtils.isBlank(xmlInfo)) {
					return (DatosGeograficosVO) digester
							.parse(new StringReader(xmlInfo));
				}
			} catch (IOException e) {
				logger.error("Se ha producido un error al leer el fichero:" + e);
			} catch (SAXException e) {
				logger.error("Se ha producido un error al parsear el xml:" + e);
			}
		}
		return null;
	}

	public PaisesVO getMapPaises() {
		InfoSistemaVO infoSistemaVO = getInfoSistema(KEY_MAP_PAISES);

		if (infoSistemaVO != null) {
			String xmlInfo = infoSistemaVO.getValor();
			URL rules = GestionInfoSistemaBIImpl.class.getClassLoader()
					.getResource(Globals.RULES_MAPPAISES);

			Digester digester = DigesterLoader.createDigester(rules);
			try {
				if (!StringUtils.isBlank(xmlInfo)) {
					return (PaisesVO) digester.parse(new StringReader(xmlInfo));
				}
			} catch (IOException e) {
				logger.error("Se ha producido un error al leer el fichero:" + e);

			} catch (SAXException e) {
				logger.error("Se ha producido un error al parsear el xml:" + e);
			}
		}
		return null;
	}

	public String getManejadorIText() {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(NOMBRE_FIELD, KEY_MANEJADOR_ITEXT));

		InfoSistemaVO vo = (InfoSistemaVO) getVO(qual.toString(), TABLE_NAME,
				COLS_DEFS, InfoSistemaVO.class);
		if (vo == null)
			return null;
		return vo.getValor();
	}

	private InfoSistemaVO getInfoSistema(String nombre) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(NOMBRE_FIELD, nombre));

		return (InfoSistemaVO) getVO(qual.toString(), TABLE_NAME, COLS_DEFS,
				InfoSistemaVO.class);
	}
}
