package fondos.utils;

import fondos.db.ElementoCuadroClasificacionDBEntityImplBase;
import fondos.db.NivelCFDBEntityImpl;
import fondos.db.UnidadDocumentalDBEntityImpl;
import fondos.model.RestriccionesCamposBusquedas;
import fondos.vos.BusquedaElementosVO;
import ieci.core.db.DbConnection;
import ieci.core.db.DbUtil;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;

import xml.config.CampoBusqueda;
import xml.config.ConfiguracionSistemaArchivoFactory;

import common.Constants;
import common.db.DBUtils;
import common.db.TableDef;
import common.util.ArrayUtils;
import common.util.CustomDateFormat;
import common.util.StringUtils;

import descripcion.db.FechaDBEntityImpl;
import descripcion.db.NumeroDBEntityImpl;
import descripcion.db.ReferenciaDBEntityImpl;
import descripcion.db.TextoCortoDBEntityImpl;
import descripcion.db.TextoLargoDBEntityImpl;
import descripcion.model.xml.definition.DefTipos;

public class BusquedasGeneratorCondicionesAvanzadasHelper {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(BusquedasGeneratorCondicionesAvanzadasHelper.class);

	private static String CONDICION_BOOLEANA_Y = "Y";
	private static String CONDICION_BOOLEANA_O = "O";
	private static String CONDICION_BOOLEANA_Y_NO = "Y NO";

	private static String componerQueryCondicionesAvanzadasCampoEspecialCodigoReferencia(
			DbConnection conn, String operador, String valor1) {

		StringBuffer query = new StringBuffer();

		query.append(DBUtils.SELECT)
				.append(ElementoCuadroClasificacionDBEntityImplBase.ID_ELEMENTO_FIELD)
				.append(DBUtils.FROM)
				.append((new TableDef(
						ElementoCuadroClasificacionDBEntityImplBase.TABLE_NAME_ELEMENTO))
						.getDeclaration()).append(DBUtils.WHERE);

		if (DBUtils.IGUAL.equals(operador) && StringUtils.isEmpty(valor1)) {
			query.append(DBUtils
					.generateIsNotNullCondition(
							conn,
							ElementoCuadroClasificacionDBEntityImplBase.CODIGO_REFERENCIA_FIELD));
			query.append(DBUtils.AND);
			query.append(DBUtils
					.generateIsNotEmptyValue(
							conn,
							ElementoCuadroClasificacionDBEntityImplBase.CODIGO_REFERENCIA_FIELD));
		} else if (DBUtils.IGUAL.equals(operador)
				|| DBUtils.MENOR.equals(operador)
				|| DBUtils.MENOR_IGUAL.equals(operador)
				|| DBUtils.MAYOR.equals(operador)
				|| DBUtils.MAYOR_IGUAL.equals(operador)) {
			query.append(
					ElementoCuadroClasificacionDBEntityImplBase.CODIGO_REFERENCIA_FIELD)
					.append(operador).append(DBUtils.COMILLA_SIMPLE)
					.append(valor1).append(DBUtils.COMILLA_SIMPLE);
		} else if (DBUtils.QUE_COMIENCE_CON.equals(operador)) {
			query.append(
					ElementoCuadroClasificacionDBEntityImplBase.CODIGO_REFERENCIA_FIELD)
					.append(DBUtils.LIKE).append(DBUtils.COMILLA_SIMPLE)
					.append(valor1).append(DBUtils.TANTO_POR_CIENTO)
					.append(DBUtils.COMILLA_SIMPLE);
		} else if (DBUtils.QUE_CONTENGA.equals(operador)) {
			query.append(
					ElementoCuadroClasificacionDBEntityImplBase.CODIGO_REFERENCIA_FIELD)
					.append(DBUtils.LIKE).append(DBUtils.COMILLA_SIMPLE)
					.append(DBUtils.TANTO_POR_CIENTO).append(valor1)
					.append(DBUtils.TANTO_POR_CIENTO)
					.append(DBUtils.COMILLA_SIMPLE);
		}

		return query.toString();
	}

	private static String componerQueryCondicionesAvanzadasCampoEspecialNivelDescripcion(
			DbConnection conn, String operador, String valor1) {

		StringBuffer query = new StringBuffer();

		String[] tables = new String[] {
				new TableDef(
						ElementoCuadroClasificacionDBEntityImplBase.TABLE_NAME_ELEMENTO)
						.getDeclaration(),
				new TableDef(NivelCFDBEntityImpl.NIVELCF_TABLE_NAME)
						.getDeclaration() };

		query.append(DBUtils.SELECT)
				.append(ElementoCuadroClasificacionDBEntityImplBase.ID_ELEMENTO_FIELD)
				.append(DBUtils.FROM)
				.append(DBUtils.generateTableSet(tables))
				.append(DBUtils.WHERE)
				.append(DBUtils
						.generateJoinCondition(
								ElementoCuadroClasificacionDBEntityImplBase.NIVEL_FIELD,
								NivelCFDBEntityImpl.ID_NIVEL_FIELD))
				.append(DBUtils.AND);

		if (DBUtils.IGUAL.equals(operador) && StringUtils.isEmpty(valor1)) {
			query.append(DBUtils.generateIsNotNullCondition(conn,
					NivelCFDBEntityImpl.NOMBRE_NIVEL_FIELD));
		} else if (DBUtils.IGUAL.equals(operador)
				|| DBUtils.MENOR.equals(operador)
				|| DBUtils.MENOR_IGUAL.equals(operador)
				|| DBUtils.MAYOR.equals(operador)
				|| DBUtils.MAYOR_IGUAL.equals(operador)) {
			query.append(NivelCFDBEntityImpl.NOMBRE_NIVEL_FIELD)
					.append(operador).append(DBUtils.COMILLA_SIMPLE)
					.append(valor1).append(DBUtils.COMILLA_SIMPLE);
		} else if (DBUtils.QUE_COMIENCE_CON.equals(operador)) {
			query.append(NivelCFDBEntityImpl.NOMBRE_NIVEL_FIELD)
					.append(DBUtils.LIKE).append(DBUtils.COMILLA_SIMPLE)
					.append(valor1).append(DBUtils.TANTO_POR_CIENTO)
					.append(DBUtils.COMILLA_SIMPLE);
		} else if (DBUtils.QUE_CONTENGA.equals(operador)) {
			query.append(NivelCFDBEntityImpl.NOMBRE_NIVEL_FIELD)
					.append(DBUtils.LIKE).append(DBUtils.COMILLA_SIMPLE)
					.append(DBUtils.TANTO_POR_CIENTO).append(valor1)
					.append(DBUtils.TANTO_POR_CIENTO)
					.append(DBUtils.COMILLA_SIMPLE);
		}

		return query.toString();
	}

	private static String componerQueryCondicionesAvanzadasCampoEspecialNumeroExpediente(
			DbConnection conn, String operador, String valor1) {

		StringBuffer query = new StringBuffer();

		query.append(DBUtils.SELECT)
				.append(UnidadDocumentalDBEntityImpl.CAMPO_ID)
				.append(DBUtils.FROM)
				.append((new TableDef(
						UnidadDocumentalDBEntityImpl.TABLE_NAME_UNIDAD_DOCUMENTAL))
						.getDeclaration()).append(DBUtils.WHERE);

		if (DBUtils.IGUAL.equals(operador) && StringUtils.isEmpty(valor1)) {
			query.append(DBUtils.generateIsNotNullCondition(conn,
					UnidadDocumentalDBEntityImpl.CAMPO_NUMERO_EXPEDIENTE));
			query.append(DBUtils.AND);
			query.append(DBUtils.generateIsNotEmptyValue(conn,
					UnidadDocumentalDBEntityImpl.CAMPO_NUMERO_EXPEDIENTE));
		} else if (DBUtils.IGUAL.equals(operador)
				|| DBUtils.MENOR.equals(operador)
				|| DBUtils.MENOR_IGUAL.equals(operador)
				|| DBUtils.MAYOR.equals(operador)
				|| DBUtils.MAYOR_IGUAL.equals(operador)) {
			query.append(UnidadDocumentalDBEntityImpl.CAMPO_NUMERO_EXPEDIENTE)
					.append(operador).append(DBUtils.COMILLA_SIMPLE)
					.append(valor1).append(DBUtils.COMILLA_SIMPLE);
		} else if (DBUtils.QUE_COMIENCE_CON.equals(operador)) {
			query.append(UnidadDocumentalDBEntityImpl.CAMPO_NUMERO_EXPEDIENTE)
					.append(DBUtils.LIKE).append(DBUtils.COMILLA_SIMPLE)
					.append(valor1).append(DBUtils.TANTO_POR_CIENTO)
					.append(DBUtils.COMILLA_SIMPLE);
		} else if (DBUtils.QUE_CONTENGA.equals(operador)) {
			query.append(UnidadDocumentalDBEntityImpl.CAMPO_NUMERO_EXPEDIENTE)
					.append(DBUtils.LIKE).append(DBUtils.COMILLA_SIMPLE)
					.append(DBUtils.TANTO_POR_CIENTO).append(valor1)
					.append(DBUtils.TANTO_POR_CIENTO)
					.append(DBUtils.COMILLA_SIMPLE);
		}

		return query.toString();
	}

	private static String componerQueryCondicionesAvanzadasCampoEspecialTitulo(
			DbConnection conn, String operador, String valor1) {

		StringBuffer query = new StringBuffer();

		query.append(DBUtils.SELECT)
				.append(ElementoCuadroClasificacionDBEntityImplBase.ID_ELEMENTO_FIELD)
				.append(DBUtils.FROM)
				.append((new TableDef(
						ElementoCuadroClasificacionDBEntityImplBase.TABLE_NAME_ELEMENTO))
						.getDeclaration()).append(DBUtils.WHERE);

		if (DBUtils.IGUAL.equals(operador) && StringUtils.isEmpty(valor1)) {
			query.append(DBUtils.generateIsNotNullCondition(conn,
					ElementoCuadroClasificacionDBEntityImplBase.TITULO_FIELD));
			query.append(DBUtils.AND);
			query.append(DBUtils.generateIsNotEmptyValue(conn,
					ElementoCuadroClasificacionDBEntityImplBase.TITULO_FIELD));
		} else if (DBUtils.IGUAL.equals(operador)
				|| DBUtils.MENOR.equals(operador)
				|| DBUtils.MENOR_IGUAL.equals(operador)
				|| DBUtils.MAYOR.equals(operador)
				|| DBUtils.MAYOR_IGUAL.equals(operador)) {
			query.append(
					ElementoCuadroClasificacionDBEntityImplBase.TITULO_FIELD)
					.append(operador).append(DBUtils.COMILLA_SIMPLE)
					.append(valor1).append(DBUtils.COMILLA_SIMPLE);
		} else if (DBUtils.QUE_COMIENCE_CON.equals(operador)) {
			query.append(
					ElementoCuadroClasificacionDBEntityImplBase.TITULO_FIELD)
					.append(DBUtils.LIKE).append(DBUtils.COMILLA_SIMPLE)
					.append(valor1).append(DBUtils.TANTO_POR_CIENTO)
					.append(DBUtils.COMILLA_SIMPLE);
		} else if (DBUtils.QUE_CONTENGA.equals(operador)) {
			query.append(DBUtils
					.generateContainsTokenField(
							conn,
							ElementoCuadroClasificacionDBEntityImplBase.TITULO_FIELD,
							ElementoCuadroClasificacionDBEntityImplBase.IDXTITULO_FIELD,
							valor1));
		}

		return query.toString();
	}

	private static String componerQueryCondicionesAvanzadasCampoEspecialTipoTextoCorto(
			DbConnection conn, String idCampo, String operador, String valor1) {
		StringBuffer query = new StringBuffer();

		query.append(DBUtils.SELECT)
				.append(TextoCortoDBEntityImpl.CAMPO_ID_ELEMENTO)
				.append(DBUtils.FROM)
				.append(new TableDef(TextoCortoDBEntityImpl.TABLE_NAME)
						.getDeclaration()).append(DBUtils.WHERE);

		if (DBUtils.IGUAL.equals(operador) && StringUtils.isEmpty(valor1)) {
			query.append(DBUtils.generateIsNotNullCondition(conn,
					TextoCortoDBEntityImpl.CAMPO_VALOR));
			query.append(DBUtils.AND);
			query.append(DBUtils.generateEQTokenField(
					TextoCortoDBEntityImpl.CAMPO_ID_CAMPO, idCampo));
		} else if (DBUtils.IGUAL.equals(operador)
				|| DBUtils.MENOR.equals(operador)
				|| DBUtils.MENOR_IGUAL.equals(operador)
				|| DBUtils.MAYOR.equals(operador)
				|| DBUtils.MAYOR_IGUAL.equals(operador)) {
			query.append(TextoCortoDBEntityImpl.CAMPO_VALOR)
					.append(operador)
					.append(DBUtils.COMILLA_SIMPLE)
					.append(valor1)
					.append(DBUtils.COMILLA_SIMPLE)
					.append(DBUtils.AND)
					.append(DBUtils.generateEQTokenField(
							TextoCortoDBEntityImpl.CAMPO_ID_CAMPO, idCampo));
		} else if (DBUtils.QUE_COMIENCE_CON.equals(operador)) {
			query.append(TextoCortoDBEntityImpl.CAMPO_VALOR)
					.append(DBUtils.LIKE)
					.append(DBUtils.COMILLA_SIMPLE)
					.append(valor1)
					.append(DBUtils.TANTO_POR_CIENTO)
					.append(DBUtils.COMILLA_SIMPLE)
					.append(DBUtils.AND)
					.append(DBUtils.generateEQTokenField(
							TextoCortoDBEntityImpl.CAMPO_ID_CAMPO, idCampo));
		} else if (DBUtils.QUE_CONTENGA.equals(operador)) {
			query.append(
					DBUtils.generateContainsTokenField(conn,
							TextoCortoDBEntityImpl.CAMPO_VALOR,
							TextoCortoDBEntityImpl.CAMPO_IDXVALOR, valor1))
					.append(DBUtils.AND)
					.append(DBUtils.generateEQTokenField(
							TextoCortoDBEntityImpl.CAMPO_ID_CAMPO, idCampo));
		}

		return query.toString();
	}

	private static String componerQueryCondicionesAvanzadasCampoEspecialTipoTextoLargo(
			DbConnection conn, String idCampo, String operador, String valor1) {

		StringBuffer query = new StringBuffer();

		query.append(DBUtils.SELECT)
				.append(TextoLargoDBEntityImpl.CAMPO_ID_ELEMENTO)
				.append(DBUtils.FROM)
				.append(new TableDef(TextoLargoDBEntityImpl.TABLE_NAME)
						.getDeclaration()).append(DBUtils.WHERE);

		if (DBUtils.IGUAL.equals(operador) && StringUtils.isEmpty(valor1)) {
			query.append(DBUtils.generateIsNotNullCondition(conn,
					TextoLargoDBEntityImpl.CAMPO_VALOR));
			query.append(DBUtils.AND);
			query.append(DBUtils.generateEQTokenField(
					TextoLargoDBEntityImpl.CAMPO_ID_CAMPO, idCampo));
		} else {
			ArrayList listaTexto = BusquedasGeneratorHelper
					.obtenerListaCondicionesTexto(valor1);
			if (listaTexto != null && listaTexto.size() > 0) {
				ArrayList condiciones = new ArrayList();
				Iterator it = listaTexto.iterator();
				while (it.hasNext()) {
					String texto = (String) it.next();
					condiciones.add(DBUtils.generateContainsTokenField(conn,
							TextoLargoDBEntityImpl.CAMPO_VALOR,
							TextoLargoDBEntityImpl.CAMPO_IDXVALOR, texto));
				}

				query.append(
						BusquedasGeneratorHelper.joinConditions(condiciones,
								DBUtils.AND))
						.append(DBUtils.AND)
						.append(DBUtils.generateEQTokenField(
								TextoLargoDBEntityImpl.CAMPO_ID_CAMPO, idCampo));
			}
		}
		return query.toString();
	}

	private static String componerQueryCondicionesAvanzadasCampoEspecialTipoNumerico(
			DbConnection conn, String idCampo, String operador, String valor1,
			String valor2) {
		StringBuffer query = new StringBuffer();

		query.append(DBUtils.SELECT)
				.append(NumeroDBEntityImpl.CAMPO_ID_ELEMENTO)
				.append(DBUtils.FROM)
				.append(new TableDef(NumeroDBEntityImpl.TABLE_NAME)
						.getDeclaration()).append(DBUtils.WHERE);

		if (DBUtils.IGUAL.equals(operador) && StringUtils.isEmpty(valor1)) {
			query.append(DBUtils.generateIsNotNullCondition(conn,
					NumeroDBEntityImpl.CAMPO_VALOR));
			query.append(DBUtils.AND);
			query.append(DBUtils.generateEQTokenField(
					NumeroDBEntityImpl.CAMPO_ID_CAMPO, idCampo));
		} else if (DBUtils.IGUAL.equals(operador)
				|| DBUtils.MENOR.equals(operador)
				|| DBUtils.MENOR_IGUAL.equals(operador)
				|| DBUtils.MAYOR.equals(operador)
				|| DBUtils.MAYOR_IGUAL.equals(operador)) {
			query.append(NumeroDBEntityImpl.CAMPO_VALOR)
					.append(operador)
					.append(valor1)
					.append(DBUtils.AND)
					.append(DBUtils.generateEQTokenField(
							NumeroDBEntityImpl.CAMPO_ID_CAMPO, idCampo));
		} else if (CustomDateFormat.DATE_OPERATOR_RANGE.equals(operador)) {
			query.append(NumeroDBEntityImpl.CAMPO_VALOR)
					.append(DBUtils.BETWEEN)
					.append(valor1)
					.append(DBUtils.AND)
					.append(valor2)
					.append(DBUtils.AND)
					.append(DBUtils.generateEQTokenField(
							NumeroDBEntityImpl.CAMPO_ID_CAMPO, idCampo));
		}

		return query.toString();
	}

	private static String componerQueryCondicionesAvanzadasCampoEspecialTipoFecha(
			DbConnection conn, String idCampo, String operador, String valor1,
			String valor2) {

		StringBuffer query = new StringBuffer();

		query.append(DBUtils.SELECT)
				.append(FechaDBEntityImpl.CAMPO_ID_ELEMENTO)
				.append(DBUtils.FROM)
				.append(new TableDef(FechaDBEntityImpl.TABLE_NAME)
						.getDeclaration()).append(DBUtils.WHERE);

		if (CustomDateFormat.DATE_OPERATOR_EQ.equals(operador)
				&& StringUtils.isEmpty(valor1)) {
			query.append(
					DBUtils.generateIsNotNullCondition(conn,
							FechaDBEntityImpl.CAMPO_VALOR))
					.append(DBUtils.AND)
					.append(DBUtils.generateIsNotEmptyValue(conn,
							FechaDBEntityImpl.CAMPO_VALOR))
					.append(DBUtils.AND)
					.append(DBUtils.generateEQTokenField(
							FechaDBEntityImpl.CAMPO_ID_CAMPO, idCampo));
		} else if (CustomDateFormat.DATE_OPERATOR_EQ.equals(operador)) {
			// OPERADOR IGUAL
			query.append(
					DBUtils.generateTokenFieldDateAnioMesDia(conn,
							FechaDBEntityImpl.CAMPO_FECHA_FINAL,
							DBUtils.MAYOR_IGUAL, valor1))
					.append(DBUtils.AND)
					.append(DBUtils.generateTokenFieldDateAnioMesDia(conn,
							FechaDBEntityImpl.CAMPO_FECHA_INICIAL,
							DBUtils.MENOR_IGUAL, valor2))
					.append(DBUtils.AND)
					.append(DBUtils.generateEQTokenField(
							FechaDBEntityImpl.CAMPO_ID_CAMPO, idCampo));
		} else if (CustomDateFormat.DATE_OPERATOR_LT.equals(operador)) {
			// OPERADOR MENOR
			query.append(
					DBUtils.generateTokenFieldDateAnioMesDia(conn,
							FechaDBEntityImpl.CAMPO_FECHA_INICIAL,
							DBUtils.MENOR_IGUAL, valor1))
					.append(DBUtils.AND)
					.append(DBUtils.generateEQTokenField(
							FechaDBEntityImpl.CAMPO_ID_CAMPO, idCampo));
		} else if (CustomDateFormat.DATE_OPERATOR_LT_OR_EQ.equals(operador)) {
			// OPERADOR MENOR O IGUAL
			query.append(
					DBUtils.generateTokenFieldDateAnioMesDia(conn,
							FechaDBEntityImpl.CAMPO_FECHA_FINAL,
							DBUtils.MENOR_IGUAL, valor1))
					.append(DBUtils.AND)
					.append(DBUtils.generateEQTokenField(
							FechaDBEntityImpl.CAMPO_ID_CAMPO, idCampo));
		} else if (CustomDateFormat.DATE_OPERATOR_GT.equals(operador)) {
			// OPERADOR MAYOR
			query.append(
					DBUtils.generateTokenFieldDateAnioMesDia(conn,
							FechaDBEntityImpl.CAMPO_FECHA_FINAL,
							DBUtils.MAYOR_IGUAL, valor1))
					.append(DBUtils.AND)
					.append(DBUtils.generateEQTokenField(
							FechaDBEntityImpl.CAMPO_ID_CAMPO, idCampo));
		} else if (CustomDateFormat.DATE_OPERATOR_GT_OR_EQ.equals(operador)) {
			// OPERADOR MAYOR O IGUAL
			query.append(
					DBUtils.generateTokenFieldDateAnioMesDia(conn,
							FechaDBEntityImpl.CAMPO_FECHA_INICIAL,
							DBUtils.MAYOR_IGUAL, valor1))
					.append(DBUtils.AND)
					.append(DBUtils.generateEQTokenField(
							FechaDBEntityImpl.CAMPO_ID_CAMPO, idCampo));
		} else if (CustomDateFormat.DATE_OPERATOR_RANGE.equals(operador)) {
			// OPERADOR DE RANGO
			query.append(
					DBUtils.generateBetweenDateAnioMesDia(conn,
							FechaDBEntityImpl.CAMPO_FECHA_INICIAL, valor1,
							valor2, DbUtil.TO_CHAR_PATTERN_AAAAMMDD))
					.append(DBUtils.AND)
					.append(DBUtils.generateBetweenDateAnioMesDia(conn,
							FechaDBEntityImpl.CAMPO_FECHA_FINAL, valor1,
							valor2, DbUtil.TO_CHAR_PATTERN_AAAAMMDD))
					.append(DBUtils.AND)
					.append(DBUtils.generateEQTokenField(
							FechaDBEntityImpl.CAMPO_ID_CAMPO, idCampo));
		} else if (CustomDateFormat.DATE_OPERATOR_CONTAINS.equals(operador)) {
			// OPERADOR CONTAINS
			query.append(" ((")
					.append(DBUtils.generateTokenFieldDateAnioMesDia(conn,
							FechaDBEntityImpl.CAMPO_FECHA_INICIAL,
							DBUtils.MENOR_IGUAL, valor1))
					.append(DBUtils.AND)
					.append(DBUtils.generateTokenFieldDateAnioMesDia(conn,
							FechaDBEntityImpl.CAMPO_FECHA_FINAL,
							DBUtils.MAYOR_IGUAL, valor1))
					.append(") OR (")
					.append(DBUtils.generateBetweenDateAnioMesDia(conn,
							FechaDBEntityImpl.CAMPO_FECHA_INICIAL, valor1,
							valor2, DbUtil.TO_CHAR_PATTERN_AAAAMMDD))
					.append(")) AND ")
					.append(DBUtils.generateEQTokenField(
							FechaDBEntityImpl.CAMPO_ID_CAMPO, idCampo));
		} else if (CustomDateFormat.DATE_OPERATOR_EXACT.equals(operador)) {
			// OPERADOR EXACTA
			query.append(
					DBUtils.generateTokenFieldDateAnioMesDia(conn,
							FechaDBEntityImpl.CAMPO_FECHA_INICIAL,
							DBUtils.IGUAL, valor1))
					.append(DBUtils.AND)
					.append(DBUtils.generateTokenFieldDateAnioMesDia(conn,
							FechaDBEntityImpl.CAMPO_FECHA_FINAL, DBUtils.IGUAL,
							valor2))
					.append(DBUtils.AND)
					.append(DBUtils.generateEQTokenField(
							FechaDBEntityImpl.CAMPO_ID_CAMPO, idCampo));
		}

		return query.toString();
	}

	private static String componerQueryCondicionesAvanzadasCampoEspecialTipoReferencia(
			DbConnection conn, String idCampo, String valor1,
			Integer tipoReferencia) {
		StringBuffer query = new StringBuffer();

		query.append(DBUtils.SELECT)
				.append(ReferenciaDBEntityImpl.CAMPO_ID_ELEMENTO)
				.append(DBUtils.FROM)
				.append(new TableDef(ReferenciaDBEntityImpl.TABLE_NAME)
						.getDeclaration()).append(DBUtils.WHERE);

		if (StringUtils.isEmpty(valor1)) {
			query.append(
					DBUtils.generateIsNotNullCondition(conn,
							ReferenciaDBEntityImpl.CAMPO_IDOBJREF))
					.append(DBUtils.AND)
					.append(DBUtils.generateEQTokenField(
							ReferenciaDBEntityImpl.CAMPO_ID_CAMPO, idCampo));
		} else {
			if (tipoReferencia != null) {
				query.append(DBUtils.generateEQTokenField(
						ReferenciaDBEntityImpl.CAMPO_TIPOOBJREF,
						tipoReferencia.intValue()));
			} else {
				query.append(DBUtils.generateEQTokenField(
						ReferenciaDBEntityImpl.CAMPO_TIPOOBJREF,
						DefTipos.TIPO_REFERENCIA_DESCRIPTOR));
			}
			query.append(DBUtils.AND).append(
					DBUtils.generateEQTokenField(
							ReferenciaDBEntityImpl.CAMPO_IDOBJREF, valor1));

			// No incluir esta condición si estamos buscando por todos los
			// descriptores
			if (!idCampo.equals(DefTipos.CAMPO_ESPECIAL_TODOS_DESCRIPTORES)) {
				query.append(DBUtils.AND)
						.append(DBUtils.generateEQTokenField(
								ReferenciaDBEntityImpl.CAMPO_ID_CAMPO, idCampo));
			}
		}

		return query.toString();
	}

	private static String componerQueryCondicionesAvanzadasFicha(
			BusquedaElementosVO busquedaElementosVO) {
		StringBuffer query = new StringBuffer();
		query.append(DBUtils.SELECT)
				.append(ElementoCuadroClasificacionDBEntityImplBase.ID_ELEMENTO_FIELD)
				.append(DBUtils.FROM)
				.append(new TableDef(
						ElementoCuadroClasificacionDBEntityImplBase.TABLE_NAME_ELEMENTO)
						.getDeclaration())
				.append(DBUtils.WHERE)
				.append(DBUtils
						.generateEQTokenField(
								ElementoCuadroClasificacionDBEntityImplBase.IDFICHADESCR_FIELD,
								busquedaElementosVO.getIdFicha()));

		return query.toString();
	}

	private static String componerQueryCondicionesAvanzadasCampoEspecialSegunTipo(
			DbConnection conn, int tipoCampo, String idCampo, String operador,
			String valor1, String valor2, Integer tipoReferencia) {
		StringBuffer sqlAdvanced = new StringBuffer("");

		switch (tipoCampo) {
		case DefTipos.TIPO_DATO_REFERENCIA: // Enlace
			sqlAdvanced
					.append(componerQueryCondicionesAvanzadasCampoEspecialTipoReferencia(
							conn, idCampo, valor1, tipoReferencia));
			break;
		case DefTipos.TIPO_DATO_FECHA: // Fecha
			sqlAdvanced
					.append(componerQueryCondicionesAvanzadasCampoEspecialTipoFecha(
							conn, idCampo, operador, valor1, valor2));
			break;

		case DefTipos.TIPO_DATO_NUMERICO: // Número
			sqlAdvanced
					.append(componerQueryCondicionesAvanzadasCampoEspecialTipoNumerico(
							conn, idCampo, operador, valor1, valor2));
			break;

		case DefTipos.TIPO_DATO_TEXTO_LARGO: // Texto largo
			sqlAdvanced
					.append(componerQueryCondicionesAvanzadasCampoEspecialTipoTextoLargo(
							conn, idCampo, operador, valor1));
			break;

		case DefTipos.TIPO_DATO_TEXTO_CORTO: // Texto corto
			sqlAdvanced
					.append(componerQueryCondicionesAvanzadasCampoEspecialTipoTextoCorto(
							conn, idCampo, operador, valor1));
			break;
		}

		return sqlAdvanced.toString();
	}

	public static String componerQueryNoElementoCfCondicionesAvanzadas(
			DbConnection conn, BusquedaElementosVO busquedaElementosVO,
			CampoBusqueda campoBusqueda, String aliasTablaPrincipal) {

		StringBuffer query = new StringBuffer();

		if (!ArrayUtils.isEmpty(busquedaElementosVO.getCampo())) {

			String[] idsCampo = busquedaElementosVO.getCampo();
			boolean existenCampos = (idsCampo != null) && (idsCampo[0] != null)
					&& (!Constants.STRING_EMPTY.equals(idsCampo[0]));

			if (existenCampos) {
				String booleano, operador, valor1, valor2;
				String idCampo;
				int tipoCampo;
				Integer tipoReferencia = null;
				String[] tiposReferencia = busquedaElementosVO
						.getTiposReferencia();
				String[] booleanos = busquedaElementosVO.getBooleano();

				int[] tiposCampo = busquedaElementosVO.getTipoCampo();
				String[] operadores = busquedaElementosVO.getOperador();
				String[] abrirPar = busquedaElementosVO.getAbrirpar();
				String[] cerrarPar = busquedaElementosVO.getCerrarpar();
				String[] valores1 = busquedaElementosVO.getValor1();
				String[] valores2 = busquedaElementosVO.getValor2();

				String idCampoRangoInicial = ConfiguracionSistemaArchivoFactory
						.getConfiguracionSistemaArchivo()
						.getConfiguracionDescripcion().getRangoInicial();
				String idCampoRangoFinal = ConfiguracionSistemaArchivoFactory
						.getConfiguracionSistemaArchivo()
						.getConfiguracionDescripcion().getRangoFinal();
				String idCampoRangoInicialNormalizado = ConfiguracionSistemaArchivoFactory
						.getConfiguracionSistemaArchivo()
						.getConfiguracionDescripcion()
						.getRangoInicialNormalizado();
				String idCampoRangoFinalNormalizado = ConfiguracionSistemaArchivoFactory
						.getConfiguracionSistemaArchivo()
						.getConfiguracionDescripcion()
						.getRangoFinalNormalizado();

				query.append(DBUtils.ABRIR_PARENTESIS);

				for (int i = 0; i < idsCampo.length; i++) {
					idCampo = idsCampo[i];
					tipoReferencia = null;
					booleano = booleanos[i];
					tipoCampo = tiposCampo[i];
					operador = operadores[i];
					if (tiposReferencia != null
							&& StringUtils.isNotEmpty(tiposReferencia[i])
							&& Integer.valueOf(tiposReferencia[i]) != null) {
						tipoReferencia = new Integer(
								Integer.parseInt(tiposReferencia[i]));
					} else if (busquedaElementosVO.getTipoReferencia() != null
							&& StringUtils.isNotEmpty(busquedaElementosVO
									.getTipoReferencia())
							&& Integer.valueOf(busquedaElementosVO
									.getTipoReferencia()) != null) {
						tipoReferencia = new Integer(
								Integer.parseInt(busquedaElementosVO
										.getTipoReferencia()));
					}

					if (StringUtils.isNotBlank(booleano) && i > 0
							&& StringUtils.isNotEmpty(idCampo)) {
						if (booleano.equals(CONDICION_BOOLEANA_Y))
							query.append(DBUtils.INTERSECT);
						else if (booleano.equals(CONDICION_BOOLEANA_O))
							query.append(DBUtils.UNION);
						else if (booleano.equals(CONDICION_BOOLEANA_Y_NO)) {
							query.append(DBUtils.getMinusKey(conn));
						}
					}

					// Paréntesis de inicio
					if (StringUtils.isNotBlank(abrirPar[i])) {
						query.append(DBUtils.ABRIR_PARENTESIS);
					}

					if ((tipoCampo > 0) && StringUtils.isNotBlank(operador)) {
						valor1 = valores1[i];
						valor2 = valores2[i];

						if (idCampo
								.equals(DefTipos.CAMPO_ESPECIAL_ID_CODIGO_REFERENCIA)) {
							query.append(componerQueryCondicionesAvanzadasCampoEspecialCodigoReferencia(
									conn, operador, valor1));
						} else if (idCampo
								.equals(DefTipos.CAMPO_ESPECIAL_ID_NIVEL_DESCRIPCION)) {
							query.append(componerQueryCondicionesAvanzadasCampoEspecialNivelDescripcion(
									conn, operador, valor1));
						} else if (idCampo
								.equals(DefTipos.CAMPO_ESPECIAL_ID_NUMERO_EXPEDIENTE)) {
							query.append(componerQueryCondicionesAvanzadasCampoEspecialNumeroExpediente(
									conn, operador, valor1));
						} else if (idCampo
								.equals(DefTipos.CAMPO_ESPECIAL_ID_TITULO)) {
							query.append(componerQueryCondicionesAvanzadasCampoEspecialTitulo(
									conn, operador, valor1));
						} else if (StringUtils.isNotEmpty(idCampoRangoInicial)
								&& idCampo.equals(idCampoRangoInicial)
								&& StringUtils
										.isNotEmpty(idCampoRangoInicialNormalizado)) {
							query.append(componerQueryCondicionesAvanzadasCampoEspecialTipoTextoCorto(
									conn, idCampoRangoInicialNormalizado,
									operador, common.util.StringUtils
											.normalizarTexto(valor1)));
						} else if (StringUtils.isNotEmpty(idCampoRangoFinal)
								&& idCampo.equals(idCampoRangoFinal)
								&& StringUtils
										.isNotEmpty(idCampoRangoFinalNormalizado)) {
							query.append(componerQueryCondicionesAvanzadasCampoEspecialTipoTextoCorto(
									conn, idCampoRangoFinalNormalizado,
									operador, common.util.StringUtils
											.normalizarTexto(valor1)));
						} else if (StringUtils.isNotEmpty(idCampo)) {
							query.append(componerQueryCondicionesAvanzadasCampoEspecialSegunTipo(
									conn, tipoCampo, idCampo, operador, valor1,
									valor2, tipoReferencia));
						}
					}

					// Paréntesis de fin
					if (StringUtils.isNotBlank(cerrarPar[i])) {
						query.append(DBUtils.CERRAR_PARENTESIS);
					}
				}

				query.append(DBUtils.CERRAR_PARENTESIS);
			}
		}

		// Añadir la ficha si es necesario
		if (StringUtils.isNotEmpty(query.toString())
				|| campoBusqueda
						.getRestriccion(RestriccionesCamposBusquedas.RESTRICCION_CAMPO_BUSQUEDA_FICHA) != null) {
			if (StringUtils.isNotEmpty(busquedaElementosVO.getIdFicha())
					&& !Constants.STRING_CERO.equals(busquedaElementosVO
							.getIdFicha())) {
				if (StringUtils.isNotEmpty(query.toString())) {
					query.append(DBUtils.INTERSECT);
				}
				query.append(componerQueryCondicionesAvanzadasFicha(busquedaElementosVO));
			}
		}

		return query.toString();
	}
}
