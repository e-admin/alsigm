package fondos.utils;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbUtil;
import ieci.core.exception.IeciTdException;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.displaytag.properties.SortOrderEnum;

import transferencias.db.RelacionEntregaDBEntityBaseImpl;
import transferencias.db.UDocRelacionDBEntityImpl;
import transferencias.db.UdocEnUIDBEntityImpl;
import transferencias.db.UnidadInstalacionReeaDBEntityImpl;
import transferencias.model.EstadoREntrega;
import xml.config.Busqueda;
import xml.config.CampoBusqueda;
import xml.config.CampoDescriptivoConfigBusqueda;
import xml.config.ConfiguracionBusquedas;
import xml.config.ConfiguracionBusquedasFactory;
import xml.config.RestriccionCampoBusqueda;

import common.CodigoTransferenciaUtils;
import common.Constants;
import common.db.ConstraintType;
import common.db.DBUtils;
import common.db.JoinDefinition;
import common.db.TableDef;
import common.util.ArrayUtils;
import common.util.CustomDateFormat;
import common.util.ListUtils;
import common.util.StringUtils;
import common.vos.ConsultaConnectBy;

import deposito.db.UDocEnUiDepositoDbEntityImpl;
import deposito.db.UInstalacionDepositoDBEntity;
import descripcion.db.DescriptorDBEntityImpl;
import descripcion.db.FechaDBEntityImpl;
import descripcion.db.NumeroDBEntityImpl;
import descripcion.db.ReferenciaDBEntityImpl;
import descripcion.db.TextoCortoDBEntityImpl;
import descripcion.db.TextoLargoDBEntityImpl;
import descripcion.vos.CampoReferenciaVO;
import descripcion.vos.ElementoCFVO;
import docelectronicos.db.UnidadDocumentalElectronicaDBEntityImpl;
import fondos.FondosConstants;
import fondos.db.ElementoCuadroClasificacionDBEntityImplBase;
import fondos.db.NivelCFDBEntityImpl;
import fondos.db.SerieDBEntityImpl;
import fondos.db.UnidadDocumentalDBEntityImpl;
import fondos.db.oracle.ElementoCuadroClasificacionDBEntityImpl;
import fondos.model.CamposBusquedas;
import fondos.model.RestriccionesCamposBusquedas;
import fondos.vos.AmbitoVO;
import fondos.vos.BusquedaElementosVO;
import fondos.vos.BusquedaFondosQueryVO;
import gcontrol.db.ArchivoDbEntityImpl;

public class BusquedasGeneratorHelper {

	static Logger logger = Logger
			.getLogger(ElementoCuadroClasificacionDBEntityImpl.class);

	private static String[] camposElementoCf = {
			CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_CODIGO,
			CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_CODIGO_REFERENCIA,
			CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_ELEMENTOS_EXCLUIR,
			CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_ELEMENTOS_RESTRINGIR,
			CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_ESTADO,
			CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_IDARCHIVO,
			CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_FONDO,
			CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_NIVELES_DESCRIPCION,
			CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_SUBQUERY_ELEMENTOS_RESTRINGIR,
			CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_TIPO_ELEMENTO,
			CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_TITULO,
			CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_AMBITO,
			CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_CONSERVADA };

	private static String[] camposNoElementoCf = {
			CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_SIGNATURA,
			CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_PROCEDIMIENTO,
			CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_CODIGO_RELACION,
			CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_NUMERO_EXPEDIENTE,
			CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_RANGOS,
			CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_TEXTO,
			CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_FECHA_INICIAL,
			CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_FECHA_FINAL,
			CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_FECHAS,
			CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_DATO_NUMERICO,
			CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_PRODUCTOR,
			CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_DESCRIPTORES,
			CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_CONDICIONES_AVANZADAS,
			CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_BLOQUEO,
			CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_AMBITO };

	private static String[] camposGenericos = {
			CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_GENERICO_TEXTO_CORTO,
			CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_GENERICO_TEXTO_LARGO,
			CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_GENERICO_CAMPO_NUMERICO,
			CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_GENERICO_CAMPO_FECHA };

	/**
    *
	 * <pre>
	 * Separa tokens del texto por comillas dobles o espacios. Ejemplo:
    *
	 * esto es "una prueba"
    *
	 * se separaría en los siguientes tokens:
	 * esto
	 * es
	 * una prueba
	 * </pre>
    *
	 * @param texto
	 *            Texto a separar en tokens
	 * @return Lista de {@link String} con los distintos tokens
	 */
	public static ArrayList obtenerListaCondicionesTexto(String texto) {
		ArrayList listaTextos = new ArrayList();

		if (StringUtils.isNotEmpty(texto)) {
			texto = texto.trim();

			int cont = 0;
			while (cont < texto.length()) {
				char c = texto.charAt(cont);
				String condicion = new String();
				// Si encontramos una comilla, recorremos el string hasta la
				// comilla de fin y vamos concatenando
				// todos los caracteres a la condición de búsqueda
				if (c == Constants.CARACTER_COMILLA_DOBLE) {
					boolean comillaFin = false;
					cont++;
					while (cont < texto.length() && !comillaFin) {
						char cEntreComillas = texto.charAt(cont);
						if (cEntreComillas == Constants.CARACTER_COMILLA_DOBLE)
							comillaFin = true;
						else
							condicion = condicion + cEntreComillas;
						cont++;
					}
					if (StringUtils.isNotEmpty(condicion)) {
						listaTextos.add(condicion);
					}
				} else if (c == Constants.CARACTER_ESPACIO_BLANCO
						|| c != Constants.CARACTER_COMILLA_DOBLE) {
					boolean finPalabra = false;
					if (c != Constants.CARACTER_ESPACIO_BLANCO)
						condicion = condicion + c;
					cont++;
					while (cont < texto.length() && !finPalabra) {
						char cPalabra = texto.charAt(cont);
						if (cPalabra == Constants.CARACTER_ESPACIO_BLANCO)
							finPalabra = true;
						else
							condicion = condicion + cPalabra;
						cont++;
					}
					if (StringUtils.isNotEmpty(condicion)) {
						listaTextos.add(condicion);
					}
				}
			}
		}

		return listaTextos;
	}

	public static String joinConditions(List condiciones, String operator) {
		StringBuffer sqlWhere = new StringBuffer();
		if (!ListUtils.isEmpty(condiciones)) {
			Iterator it = condiciones.iterator();

			int cont = 0;
			while (it.hasNext()) {
				String st = (String) it.next();
				if (StringUtils.isNotEmpty(st)) {
					if (cont == 0) {
						sqlWhere.append(st);
					} else {
						sqlWhere.append(operator).append(st);
					}
					cont++;
				}
			}
		}
		return sqlWhere.toString();
	}

	public static DbColumnDef getCustomizedField(DbColumnDef column,
			String alias) {
		return new DbColumnDef(column.getBindPropertyVO(), alias,
				column.getName(), column.getDataType(), column.getMaxLen(),
				column.isNullable());
	}

	/**
	 * Permite obtener la definición de una columna a partir de su nombre
    *
	 * @param nameColumn
	 *            Nombre de la columna
	 * @return Definición de la columna
	 */
	private static DbColumnDef getColumnDefinition(String nameColumn) {
		for (int i = 0; i < ElementoCuadroClasificacionDBEntityImplBase.COLS_DEFS_ELEMENTO.length; i++) {
			DbColumnDef col = ElementoCuadroClasificacionDBEntityImplBase.COLS_DEFS_ELEMENTO[i];
			if (col.getName().equals(nameColumn)) {
				return col;
			}
		}
		return null;
	}

	private static String componerCondicionElementoCfCodigoReferencia(
			String codigoReferencia, String aliasTablaPrincipal) {
		List condiciones = new ArrayList();
		if (StringUtils.isNotBlank(codigoReferencia)) {
			ArrayList listaTexto = obtenerListaCondicionesTexto(codigoReferencia);
			if (listaTexto != null && listaTexto.size() > 0) {
				condiciones = new ArrayList();
				Iterator it = listaTexto.iterator();
				while (it.hasNext()) {
					String texto = (String) it.next();
					condiciones
							.add(DBUtils
									.generateLikeTokenField(
											getCustomizedField(
													ElementoCuadroClasificacionDBEntityImplBase.CODIGO_REFERENCIA_FIELD,
													aliasTablaPrincipal), texto));
				}
			}
		}
		return joinConditions(condiciones, DBUtils.AND);
	}

	private static String componerCondicionElementoCfIdFondo(String idFondo,
			String aliasTablaPrincipal) {
		return DBUtils
				.generateEQTokenField(
						getCustomizedField(
								ElementoCuadroClasificacionDBEntityImplBase.IDFONDO_FIELD,
								aliasTablaPrincipal), idFondo);
	}

	private static String componerCondicionElementoCfNiveles(String[] niveles,
			String aliasTablaPrincipal) {
		StringBuffer qual = new StringBuffer();
		if (!ArrayUtils.isEmpty(niveles)) {
			qual.append(DBUtils
					.generateInTokenField(
							getCustomizedField(
									ElementoCuadroClasificacionDBEntityImplBase.NIVEL_FIELD,
									aliasTablaPrincipal), niveles));
		}
		return qual.toString();
	}

	private static String componerCondicionElementoCfEstados(String[] estados,
			String aliasTablaPrincipal) {
		StringBuffer qual = new StringBuffer();
		if (!ArrayUtils.isEmpty(estados)) {
			qual.append(DBUtils
					.generateInTokenField(
							getCustomizedField(
									ElementoCuadroClasificacionDBEntityImplBase.ESTADO_ELEMENTO_FIELD,
									aliasTablaPrincipal), estados));
		}
		return qual.toString();
	}

	private static String componerCondicionElementoCfTipoElemento(
			String tipoElemento, String aliasTablaPrincipal) {
		StringBuffer qual = new StringBuffer();
		if (StringUtils.isNotBlank(tipoElemento)) {
			qual.append(DBUtils
					.generateEQTokenField(
							getCustomizedField(
									ElementoCuadroClasificacionDBEntityImplBase.TIPO_ELEMENTO_FIELD,
									aliasTablaPrincipal), tipoElemento));
		}
		return qual.toString();
	}

	private static String componerCondicionElementoCfCodigo(String codigo,
			String aliasTablaPrincipal) {
		List condiciones = new ArrayList();
		ArrayList listaTexto = obtenerListaCondicionesTexto(codigo);
		if (listaTexto != null && listaTexto.size() > 0) {
			Iterator it = listaTexto.iterator();
			while (it.hasNext()) {
				String texto = (String) it.next();
				condiciones
						.add(DBUtils
								.generateLikeTokenField(
										getCustomizedField(
												ElementoCuadroClasificacionDBEntityImplBase.CODIGO_FIELD,
												aliasTablaPrincipal), texto));
			}
		}
		return joinConditions(condiciones, DBUtils.AND);
	}

	private static String componerCondicionElementoCfIdArchivo(
			String idArchivo, String aliasTablaPrincipal) {
		return DBUtils
				.generateEQTokenField(
						getCustomizedField(
								ElementoCuadroClasificacionDBEntityImplBase.ARCHIVO_FIELD,
								aliasTablaPrincipal), idArchivo);
	}

	private static String componerCondicionElementoCfElementosExcluir(
			Map elementosExcluir, String aliasTablaPrincipal) {
		StringBuffer qual = new StringBuffer();
		int numCondiciones = 0;

		if ((elementosExcluir != null) && (!elementosExcluir.isEmpty())) {
			Iterator it = elementosExcluir.entrySet().iterator();
			while (it.hasNext()) {

				Entry entry = (Entry) it.next();
				String columna = (String) entry.getKey();
				String[] valores = (String[]) entry.getValue();
				DbColumnDef colDef = getColumnDefinition(columna);
				if ((colDef != null) && (valores != null)
						&& (valores.length > 0)) {
					numCondiciones++;
					qual.append(DBUtils.generateNotInTokenField(
							getCustomizedField(colDef, aliasTablaPrincipal),
							valores));

					if (it.hasNext()) {
						qual.append(DBUtils.AND);
					}
				}
			}
		}

		// Sólo retornar algo si alguna de las condiciones es correcta
		if (numCondiciones > 0) {
			return qual.toString();
		} else {
			return Constants.STRING_EMPTY;
		}
	}

	private static String componerCondicionElementoCfSubqueryElementosRestringir(
			Map subqueryElementosRestringir, String aliasTablaPrincipal) {
		StringBuffer qual = new StringBuffer();
		int numCondiciones = 0;

		if ((subqueryElementosRestringir != null)
				&& (!subqueryElementosRestringir.isEmpty())) {
			Iterator it = subqueryElementosRestringir.entrySet().iterator();
			while (it.hasNext()) {
				Entry entry = (Entry) it.next();
				String columna = (String) entry.getKey();
				String subquery = (String) entry.getValue();
				DbColumnDef colDef = getColumnDefinition(columna);
				if ((colDef != null) && (subquery != null)
						&& (!Constants.BLANK.equals(subquery))) {
					numCondiciones++;
					qual.append(DBUtils.generateInTokenFieldSubQuery(
							getCustomizedField(colDef, aliasTablaPrincipal),
							subquery));

					if (it.hasNext()) {
						qual.append(DBUtils.AND);
					}
				}
			}
		}

		// Sólo retornar algo si alguna de las condiciones es correcta
		if (numCondiciones > 0)
			return qual.toString();
		else
			return Constants.STRING_EMPTY;
	}

	private static String componerCondicionElementoCfElementosRestringir(
			Map elementosRestringir, String aliasTablaPrincipal) {
		StringBuffer qual = new StringBuffer();
		int numCondiciones = 0;

		if ((elementosRestringir != null) && (!elementosRestringir.isEmpty())) {
			Iterator it = elementosRestringir.entrySet().iterator();
			while (it.hasNext()) {

				Entry entry = (Entry) it.next();
				String columna = (String) entry.getKey();
				String[] valores = (String[]) entry.getValue();
				DbColumnDef colDef = getColumnDefinition(columna);
				if ((colDef != null) && (valores != null)
						&& (valores.length > 0)) {
					numCondiciones++;
					qual.append(DBUtils.generateInTokenField(
							getCustomizedField(colDef, aliasTablaPrincipal),
							valores));

					if (it.hasNext()) {
						qual.append(DBUtils.AND);
					}
				}
			}
		}

		// Sólo retornar algo si alguna de las condiciones es correcta
		if (numCondiciones > 0)
			return qual.toString();
		else
			return Constants.STRING_EMPTY;
	}

	private static String componerCondicionElementoCfIdsElementos(
			String[] idsElementos, String aliasTablaPrincipal) {
		StringBuffer qual = new StringBuffer();

		if (ArrayUtils.isNotEmpty(idsElementos)) {
			DbColumnDef columnaIdElemento = getCustomizedField(
					ElementoCuadroClasificacionDBEntityImplBase.ID_ELEMENTO_FIELD,
					aliasTablaPrincipal);
			qual.append(DBUtils.generateInTokenField(columnaIdElemento,
					idsElementos));
		}

		return qual.toString();
	}

	private static String componerCondicionSignaturasIds(String[] signaturas) {
		StringBuffer qual = new StringBuffer();

		if (ArrayUtils.isNotEmpty(signaturas)) {
			qual.append(DBUtils.generateInTokenField(
					UdocEnUIDBEntityImpl.SIGNATURA_FIELD, signaturas));
		}

		return qual.toString();
	}

	private static String componerCondicionElementoCfTitulo(DbConnection conn,
			String titulo, String aliasTablaPrincipal) {
		List condiciones = new ArrayList();
		if (StringUtils.isNotBlank(titulo)) {
			ArrayList listaTexto = obtenerListaCondicionesTexto(titulo);
			if (listaTexto != null && listaTexto.size() > 0) {
				Iterator it = listaTexto.iterator();
				while (it.hasNext()) {
					String texto = (String) it.next();

					condiciones
							.add(DBUtils
									.generateContainsTokenField(
											conn,
											getCustomizedField(
													ElementoCuadroClasificacionDBEntityImplBase.TITULO_FIELD,
													aliasTablaPrincipal),
											getCustomizedField(
													ElementoCuadroClasificacionDBEntityImplBase.IDXTITULO_FIELD,
													aliasTablaPrincipal), texto));
				}
			}
		}
		return joinConditions(condiciones, DBUtils.AND);
	}

	private static String componerQueryNoElementoCfSignatura(String signatura,
			String calificadorSignatura) {
		StringBuffer query = new StringBuffer();
		if (StringUtils.isNotEmpty(signatura)) {
			query.append(DBUtils.SELECT)
					.append(UDocEnUiDepositoDbEntityImpl.IDUNIDADDOC_COLUMN_NAME)
					.append(DBUtils.FROM)
					.append(UDocEnUiDepositoDbEntityImpl.TABLE_NAME)
					.append(" WHERE ")
					.append(DBUtils.generateLikeFieldQualified(
							UDocEnUiDepositoDbEntityImpl.SIGNATURAUDOC_FIELD,
							signatura, UDocEnUiDepositoDbEntityImpl.TABLE_NAME,
							calificadorSignatura));
		}
		return query.toString();
	}

	private static String componerQueryNoElementoCfProcedimiento(
			String procedimiento) {
		StringBuffer query = new StringBuffer();
		if (StringUtils.isNotBlank(procedimiento)) {
			query.append(DBUtils.SELECT)
					.append(SerieDBEntityImpl.CAMPO_ID)
					.append(DBUtils.FROM)
					.append(SerieDBEntityImpl.TABLE_NAME_SERIE)
					.append(DBUtils.WHERE)
					.append(DBUtils.generateEQTokenField(
							SerieDBEntityImpl.CAMPO_IDPROCEDIMIENTO,
							procedimiento));
		}
		return query.toString();
	}

	private static String componerQueryNoElementoCfCodigoRelacion(
			DbConnection conn, String codigoRelacion,
			String aliasTablaPrincipal, Map restricciones) {
		StringBuffer query = new StringBuffer();
		if (StringUtils.isNotEmpty(codigoRelacion)) {
			String[] tablesRelacion = new String[] {
					new TableDef(RelacionEntregaDBEntityBaseImpl.TABLE_NAME)
							.getDeclaration(),
					new TableDef(ArchivoDbEntityImpl.TABLE_NAME)
							.getDeclaration(),
					new TableDef(UDocRelacionDBEntityImpl.TABLE_NAME)
							.getDeclaration(),
					new TableDef(UDocEnUiDepositoDbEntityImpl.TABLE_NAME)
							.getDeclaration(),
					new TableDef(UInstalacionDepositoDBEntity.TABLE_NAME)
							.getDeclaration() };

			String[] tablesRelacionEa = new String[] {
					new TableDef(RelacionEntregaDBEntityBaseImpl.TABLE_NAME)
							.getDeclaration(),
					new TableDef(ArchivoDbEntityImpl.TABLE_NAME)
							.getDeclaration(),
					new TableDef(UnidadInstalacionReeaDBEntityImpl.TABLE_NAME)
							.getDeclaration(),
					new TableDef(UDocEnUiDepositoDbEntityImpl.TABLE_NAME)
							.getDeclaration(),
					new TableDef(UInstalacionDepositoDBEntity.TABLE_NAME)
							.getDeclaration() };

			query.append(DBUtils.SELECT)
					.append(UDocEnUiDepositoDbEntityImpl.IDUNIDADDOC_FIELD)
					.append(DBUtils.FROM)
					.append(DBUtils.generateTableSet(tablesRelacion))
					.append(DBUtils.WHERE)
					.append(DBUtils
							.generateJoinCondition(
									RelacionEntregaDBEntityBaseImpl.CAMPO_IDARCHIVORECEPTOR,
									ArchivoDbEntityImpl.ID_FIELD))
					.append(DBUtils.AND)
					.append(CodigoTransferenciaUtils
							.textSqlCodigoTransferenciaPrevision(
									RelacionEntregaDBEntityBaseImpl.CAMPO_ANO
											.getQualifiedName(),
									RelacionEntregaDBEntityBaseImpl.CAMPO_ORDEN
											.getQualifiedName(), conn))
					.append(DBUtils.LIKE)
					.append(DBUtils.COMILLA_SIMPLE)
					.append(DBUtils.TANTO_POR_CIENTO)
					.append(codigoRelacion)
					.append(DBUtils.TANTO_POR_CIENTO)
					.append(DBUtils.COMILLA_SIMPLE)
					.append(DBUtils.AND)
					.append(DBUtils.generateJoinCondition(
							RelacionEntregaDBEntityBaseImpl.CAMPO_ID,
							UDocRelacionDBEntityImpl.ID_RELACION_FIELD))
					.append(DBUtils.AND)
					.append(DBUtils.generateJoinCondition(
							UDocEnUiDepositoDbEntityImpl.IDUDOCRE_FIELD,
							UDocRelacionDBEntityImpl.ID_FIELD))
					.append(DBUtils.AND)
					.append(DBUtils.generateJoinCondition(
							UDocEnUiDepositoDbEntityImpl.IDUINSTALACION_FIELD,
							UInstalacionDepositoDBEntity.ID_FIELD))
					.append(DBUtils.AND)
					.append(DBUtils.generateEQTokenField(
							UInstalacionDepositoDBEntity.MARCAS_BLOQUEO_FIELD,
							0));

			if ((restricciones != null)
					&& restricciones
							.containsKey(RestriccionesCamposBusquedas.RESTRICCION_CAMPO_BUSQUEDA_ESTADO_UDOC_VALIDADA)) {
				query.append(DBUtils.AND).append(
						DBUtils.generateEQTokenField(
								RelacionEntregaDBEntityBaseImpl.CAMPO_ESTADO,
								EstadoREntrega.VALIDADA.getIdentificador()));
			}

			query.append(DBUtils.UNION)
					.append(DBUtils.SELECT)
					.append(UDocEnUiDepositoDbEntityImpl.IDUNIDADDOC_FIELD)
					.append(DBUtils.FROM)
					.append(DBUtils.generateTableSet(tablesRelacionEa))
					.append(DBUtils.WHERE)
					.append(DBUtils
							.generateJoinCondition(
									RelacionEntregaDBEntityBaseImpl.CAMPO_IDARCHIVORECEPTOR,
									ArchivoDbEntityImpl.ID_FIELD))
					.append(DBUtils.AND)
					.append(CodigoTransferenciaUtils
							.textSqlCodigoTransferenciaPrevision(
									RelacionEntregaDBEntityBaseImpl.CAMPO_ANO
											.getQualifiedName(),
									RelacionEntregaDBEntityBaseImpl.CAMPO_ORDEN
											.getQualifiedName(), conn))
					.append(DBUtils.LIKE)
					.append(DBUtils.COMILLA_SIMPLE)
					.append(DBUtils.TANTO_POR_CIENTO)
					.append(codigoRelacion)
					.append(DBUtils.TANTO_POR_CIENTO)
					.append(DBUtils.COMILLA_SIMPLE)
					.append(DBUtils.AND)
					.append(DBUtils
							.generateJoinCondition(
									RelacionEntregaDBEntityBaseImpl.CAMPO_ID,
									UnidadInstalacionReeaDBEntityImpl.ID_RELACIONENTREGA_FIELD))
					.append(DBUtils.AND)
					.append(DBUtils
							.generateJoinCondition(
									UInstalacionDepositoDBEntity.ID_FIELD,
									UnidadInstalacionReeaDBEntityImpl.ID_UIDEPOSITO_FIELD))
					.append(DBUtils.AND)
					.append(DBUtils.generateJoinCondition(
							UDocEnUiDepositoDbEntityImpl.IDUINSTALACION_FIELD,
							UInstalacionDepositoDBEntity.ID_FIELD))
					.append(DBUtils.AND)
					.append(DBUtils.generateEQTokenField(
							UInstalacionDepositoDBEntity.MARCAS_BLOQUEO_FIELD,
							0));

			if ((restricciones != null)
					&& restricciones
							.containsKey(RestriccionesCamposBusquedas.RESTRICCION_CAMPO_BUSQUEDA_ESTADO_UDOC_VALIDADA)) {
				query.append(DBUtils.AND).append(
						DBUtils.generateEQTokenField(
								RelacionEntregaDBEntityBaseImpl.CAMPO_ESTADO,
								EstadoREntrega.VALIDADA.getIdentificador()));
			}
		}
		return query.toString();
	}

	private static String componerQueryNoElementoCfNumeroExpediente(
			DbConnection conn, CampoBusqueda campoBusqueda,
			BusquedaElementosVO busquedaElementosVO, String numeroExpediente,
			String calificadorNumeroExpediente, String aliasTablaPrincipal,
			boolean componerCondicionRangos) {
		StringBuffer query = new StringBuffer();
		List condiciones = new ArrayList();
		Map restricciones = campoBusqueda.getRestricciones();

		if (StringUtils.isNotEmpty(numeroExpediente)) {
			String[] tables = null;
			if ((restricciones != null)
					&& restricciones
							.containsKey(RestriccionesCamposBusquedas.RESTRICCION_CAMPO_BUSQUEDA_UDOC_ENRELENTREGA)) {
				tables = new String[] {
						new TableDef(
								UnidadDocumentalDBEntityImpl.TABLE_NAME_UNIDAD_DOCUMENTAL)
								.getDeclaration(),
						new TableDef(UDocEnUiDepositoDbEntityImpl.TABLE_NAME)
								.getDeclaration() };
			} else {
				tables = new String[] { new TableDef(
						UnidadDocumentalDBEntityImpl.TABLE_NAME_UNIDAD_DOCUMENTAL)
						.getDeclaration() };
			}

			query.append(DBUtils.SELECT)
					.append(UnidadDocumentalDBEntityImpl.CAMPO_ID)
					.append(DBUtils.FROM)
					.append(DBUtils.generateTableSet(tables))
					.append(DBUtils.WHERE);

			query.append(DBUtils.generateLikeFieldQualified(
					UnidadDocumentalDBEntityImpl.CAMPO_NUMERO_EXPEDIENTE,
					numeroExpediente,
					UnidadDocumentalDBEntityImpl.TABLE_NAME_UNIDAD_DOCUMENTAL,
					calificadorNumeroExpediente));

			if ((restricciones != null)
					&& restricciones
							.containsKey(RestriccionesCamposBusquedas.RESTRICCION_CAMPO_BUSQUEDA_UDOC_ENRELENTREGA)) {
				query.append(DBUtils.AND)
						.append(DBUtils.generateJoinCondition(
								UnidadDocumentalDBEntityImpl.CAMPO_ID,
								UDocEnUiDepositoDbEntityImpl.IDUNIDADDOC_FIELD));
			}
		}

		String condicion = query.toString();
		if (StringUtils.isNotEmpty(condicion)) {
			condiciones.add(condicion);
		}

		// Tratar restricciones
		if ((restricciones != null)
				&& (restricciones
						.containsKey(RestriccionesCamposBusquedas.RESTRICCION_CAMPO_BUSQUEDA_RANGOS))
				&& componerCondicionRangos) {
			condicion = componerQueryNoElementoCfRango(conn, campoBusqueda,
					busquedaElementosVO, numeroExpediente, aliasTablaPrincipal,
					false);
			if (StringUtils.isNotEmpty(condicion)) {
				condiciones.add(condicion);
			}
		}

		return joinConditions(condiciones, DBUtils.UNION);
	}

	private static String componerQueryNoElementoCfTextoCorto(
			DbConnection conn, String texto, String aliasTablaPrincipal,
			String idCampo) {
		StringBuffer query = new StringBuffer();
		if (StringUtils.isNotBlank(texto)) {
			ArrayList listaTexto = obtenerListaCondicionesTexto(texto);
			ArrayList condiciones = new ArrayList();
			if (listaTexto != null && listaTexto.size() > 0) {
				Iterator it = listaTexto.iterator();
				String textoBusqueda = null;
				while (it.hasNext()) {
					textoBusqueda = (String) it.next();
					condiciones.add(DBUtils.generateContainsTokenField(conn,
							TextoCortoDBEntityImpl.CAMPO_VALOR,
							TextoCortoDBEntityImpl.CAMPO_IDXVALOR,
							textoBusqueda));
				}
			}

			query.append(DBUtils.SELECT)
					.append(TextoCortoDBEntityImpl.CAMPO_ID_ELEMENTO)
					.append(DBUtils.FROM)
					.append(new TableDef(TextoCortoDBEntityImpl.TABLE_NAME)
							.getDeclaration()).append(DBUtils.WHERE)
					.append(joinConditions(condiciones, DBUtils.AND));

			if (StringUtils.isNotEmpty(idCampo)
					&& !(idCampo
							.equals(RestriccionCampoBusqueda.RESTRICCION_IDCAMPO_TODOS_VALORES))) {
				query.append(DBUtils.AND)
						.append(DBUtils.generateEQTokenField(
								TextoCortoDBEntityImpl.CAMPO_ID_CAMPO, idCampo));
			}

		}

		return query.toString();
	}

	private static ArrayList getCondicionesBusquedaDocumental(DbConnection conn, DbColumnDef colDef, DbColumnDef idxColDef,String texto){
		if (StringUtils.isNotBlank(texto)) {
			ArrayList listaTexto = obtenerListaCondicionesTexto(texto);
			ArrayList condiciones = new ArrayList();
			if (listaTexto != null && listaTexto.size() > 0) {
				Iterator it = listaTexto.iterator();
				String textoBusqueda = null;
				while (it.hasNext()) {
					textoBusqueda = (String) it.next();
					condiciones.add(DBUtils.generateContainsTokenField(conn,
							colDef,
							idxColDef,
							textoBusqueda));
				}
			}
			return condiciones;
		}
		return null;
	}


	private static String componerQueryGenericoTextoCorto(DbConnection conn,
			String texto, String idCampo, String operador,
			String aliasTablaPrincipal) {
		StringBuffer query = new StringBuffer();
		if (StringUtils.isNotBlank(texto)) {
			query.append(DBUtils.SELECT)
					.append(TextoCortoDBEntityImpl.CAMPO_ID_ELEMENTO)
					.append(DBUtils.FROM)
					.append(new TableDef(TextoCortoDBEntityImpl.TABLE_NAME)
							.getDeclaration())
					.append(DBUtils.WHERE)
					.append(DBUtils.generateLikeFieldQualified(
							TextoCortoDBEntityImpl.CAMPO_VALOR, texto, null,
							operador))
					.append(DBUtils.AND)
					.append(DBUtils.generateEQTokenField(
							TextoCortoDBEntityImpl.CAMPO_ID_CAMPO, idCampo));
		}

		return query.toString();
	}

	private static String componerQueryNoElementoCfTextoCorto(String texto,
			String aliasTablaPrincipal, String idCampoInicial,
			String idCampoFinal) {
		StringBuffer query = new StringBuffer();

		if (StringUtils.isNotBlank(texto)) {
			String aliasTextoCorto1 = TextoCortoDBEntityImpl.TABLE_NAME + "1";
			String aliasTextoCorto2 = TextoCortoDBEntityImpl.TABLE_NAME + "2";
			TableDef tablaTextoCorto1 = new TableDef(
					TextoCortoDBEntityImpl.TABLE_NAME, aliasTextoCorto1);
			TableDef tablaTextoCorto2 = new TableDef(
					TextoCortoDBEntityImpl.TABLE_NAME, aliasTextoCorto2);
			DbColumnDef columnaIdTextoCorto1 = getCustomizedField(
					TextoCortoDBEntityImpl.CAMPO_ID_ELEMENTO, aliasTextoCorto1);
			DbColumnDef columnaIdTextoCorto2 = getCustomizedField(
					TextoCortoDBEntityImpl.CAMPO_ID_ELEMENTO, aliasTextoCorto2);
			DbColumnDef columnaOrdenTextoCorto1 = getCustomizedField(
					TextoCortoDBEntityImpl.CAMPO_ORDEN, aliasTextoCorto1);
			DbColumnDef columnaOrdenTextoCorto2 = getCustomizedField(
					TextoCortoDBEntityImpl.CAMPO_ORDEN, aliasTextoCorto2);

			String[] tables = new String[] { tablaTextoCorto1.getDeclaration(),
					tablaTextoCorto2.getDeclaration() };

			query.append(DBUtils.SELECT)
					.append(columnaIdTextoCorto1)
					.append(DBUtils.FROM)
					.append(DBUtils.generateTableSet(tables))
					.append(DBUtils.WHERE)
					.append(DBUtils.generateJoinCondition(columnaIdTextoCorto1,
							columnaIdTextoCorto2))
					.append(DBUtils.AND)
					.append(DBUtils.generateJoinCondition(
							columnaOrdenTextoCorto1, columnaOrdenTextoCorto2))
					.append(DBUtils.AND)
					.append(DBUtils.generateLTEQTokenField(
							getCustomizedField(
									TextoCortoDBEntityImpl.CAMPO_VALOR,
									aliasTextoCorto1), texto))
					.append(DBUtils.AND)
					.append(DBUtils.generateEQTokenField(
							getCustomizedField(
									TextoCortoDBEntityImpl.CAMPO_ID_CAMPO,
									aliasTextoCorto1), idCampoInicial))
					.append(DBUtils.AND)
					.append(DBUtils.generateGTEQTokenField(
							getCustomizedField(
									TextoCortoDBEntityImpl.CAMPO_VALOR,
									aliasTextoCorto2), texto))
					.append(DBUtils.AND)
					.append(DBUtils.generateEQTokenField(
							getCustomizedField(
									TextoCortoDBEntityImpl.CAMPO_ID_CAMPO,
									aliasTextoCorto2), idCampoFinal));

		}
		return query.toString();
	}

	private static String componerQueryNoElementoCfTextoLargo(
			DbConnection conn, String texto, String aliasTablaPrincipal,
			String idCampo) {
		StringBuffer query = new StringBuffer();
		if (StringUtils.isNotBlank(texto)) {
			ArrayList listaTexto = obtenerListaCondicionesTexto(texto);
			ArrayList condiciones = new ArrayList();
			if (listaTexto != null && listaTexto.size() > 0) {
				Iterator it = listaTexto.iterator();
				String textoBusqueda = null;
				while (it.hasNext()) {
					textoBusqueda = (String) it.next();
					condiciones.add(DBUtils.generateContainsTokenField(conn,
							TextoLargoDBEntityImpl.CAMPO_VALOR,
							TextoLargoDBEntityImpl.CAMPO_IDXVALOR,
							textoBusqueda));
				}
			}

			query.append(DBUtils.SELECT)
					.append(TextoLargoDBEntityImpl.CAMPO_ID_ELEMENTO)
					.append(DBUtils.FROM)
					.append(new TableDef(TextoLargoDBEntityImpl.TABLE_NAME)
							.getDeclaration()).append(DBUtils.WHERE)
					.append(joinConditions(condiciones, DBUtils.AND));

			if (StringUtils.isNotEmpty(idCampo)
					&& !(idCampo
							.equals(RestriccionCampoBusqueda.RESTRICCION_IDCAMPO_TODOS_VALORES))) {
				query.append(DBUtils.AND)
						.append(DBUtils.generateEQTokenField(
								TextoLargoDBEntityImpl.CAMPO_ID_CAMPO, idCampo));
			}
		}

		return query.toString();
	}

	private static String componerQueryNoElementoCfTextoTextos(
			DbConnection conn, String texto, String aliasTablaPrincipal, boolean incluirTitulo) {
		StringBuffer query = new StringBuffer();
		query.append(
				componerQueryNoElementoCfTextoCorto(conn, texto,
						aliasTablaPrincipal, null))
				.append(DBUtils.UNION)
				.append(componerQueryNoElementoCfTextoLargo(conn, texto,
						aliasTablaPrincipal, null));


				if(incluirTitulo){
					query.append(DBUtils.UNION)
					.append(componerQueryTitulo(conn, texto));
				}

		return query.toString();
	}

	private static String componerQueryTitulo(DbConnection conn, String texto) {

		StringBuffer query = new StringBuffer()
		.append(DBUtils.SELECT)
		.append(ElementoCuadroClasificacionDBEntityImplBase.ID_ELEMENTO_FIELD.getQualifiedName())
		.append(DBUtils.FROM)
		.append(new TableDef(ElementoCuadroClasificacionDBEntityImplBase.TABLE_NAME_ELEMENTO)
				.getDeclaration()).append(DBUtils.WHERE)
		.append(joinConditions(getCondicionesBusquedaDocumental(conn,
				ElementoCuadroClasificacionDBEntityImplBase.TITULO_FIELD,
				ElementoCuadroClasificacionDBEntityImplBase.IDXTITULO_FIELD
				, texto), DBUtils.AND));

		return query.toString();

	}

	private static String componerQueryNoElementoCfTextoDescriptores(
			DbConnection conn, String texto, String aliasTablaPrincipal) {
		StringBuffer query = new StringBuffer();
		if (StringUtils.isNotBlank(texto)) {
			ArrayList listaTexto = obtenerListaCondicionesTexto(texto);
			ArrayList condiciones = new ArrayList();
			if (listaTexto != null && listaTexto.size() > 0) {
				Iterator it = listaTexto.iterator();
				String textoBusqueda = null;
				while (it.hasNext()) {
					textoBusqueda = (String) it.next();
					condiciones.add(DBUtils.generateContainsTokenField(conn,
							DescriptorDBEntityImpl.CAMPO_NOMBRE,
							DescriptorDBEntityImpl.IDXNOMBRE_FIELD,
							textoBusqueda));
				}
			}

			String[] tables = new String[] {
					new TableDef(DescriptorDBEntityImpl.TABLE_NAME)
							.getDeclaration(),
					new TableDef(ReferenciaDBEntityImpl.TABLE_NAME)
							.getDeclaration() };

			query.append(DBUtils.SELECT)
					.append(ReferenciaDBEntityImpl.CAMPO_ID_ELEMENTO)
					.append(DBUtils.FROM)
					.append(DBUtils.generateTableSet(tables))
					.append(DBUtils.WHERE)
					.append(DBUtils.generateJoinCondition(
							ReferenciaDBEntityImpl.CAMPO_IDOBJREF,
							DescriptorDBEntityImpl.CAMPO_ID))
					.append(DBUtils.AND)
					.append(joinConditions(condiciones, DBUtils.AND));
		}
		return query.toString();
	}

	private static String componerQueryNoElementoCfTexto(DbConnection conn,
			String texto, String aliasTablaPrincipal, String tipo, Map restricciones) {

		boolean incluirTextos = false;

		if(restricciones != null && StringUtils.isNotBlank(texto)){
			Object restriccionTexto = restricciones.get(RestriccionesCamposBusquedas.RESTRICCION_CAMPO_BUSQUEDA_TITULO);

			if(restriccionTexto != null){
				incluirTextos = true;
			}
		}


		StringBuffer query = new StringBuffer();
		if (CamposBusquedas.CAMPO_TIPO_TODOS_TEXTOS.equals(tipo)) {
			query.append(componerQueryNoElementoCfTextoTextos(conn, texto,
					aliasTablaPrincipal, incluirTextos));
		} else if (CamposBusquedas.CAMPO_TIPO_TODOS_DESCRIPTORES.equals(tipo)) {
			query.append(componerQueryNoElementoCfTextoDescriptores(conn,
					texto, aliasTablaPrincipal));
		} else if (CamposBusquedas.CAMPO_TIPO_TODOS_TEXTOS_TODOS_DESCRIPTORES
				.equals(tipo)) {
			query.append(
					componerQueryNoElementoCfTextoTextos(conn, texto,
							aliasTablaPrincipal, incluirTextos))
					.append(DBUtils.UNION)
					.append(componerQueryNoElementoCfTextoDescriptores(conn,
							texto, aliasTablaPrincipal));
		}
		return query.toString();
	}

	private static String componerQueryNoElementoCfFecha(DbConnection conn,
			CampoBusqueda campoBusqueda,
			BusquedaElementosVO busquedaElementosVO, String aliasTablaPrincipal) {
		Map restriccionesFecha = campoBusqueda.getRestricciones();
		RestriccionCampoBusqueda restriccionCampoBusqueda = null;
		String idCampoKey = null;
		CampoDescriptivoConfigBusqueda configuracionCampo = null;
		if ((restriccionesFecha != null) && !restriccionesFecha.isEmpty()) {
			Iterator it = restriccionesFecha.entrySet().iterator();
			restriccionCampoBusqueda = (RestriccionCampoBusqueda) restriccionesFecha
					.get(((Entry) it.next()).getKey());
		}

		ConfiguracionBusquedas configuracionBusquedas = ConfiguracionBusquedasFactory
				.getConfiguracionBusquedas();
		if (restriccionCampoBusqueda == null) {
			// Intentar buscar por el nombre del campo
			configuracionCampo = configuracionBusquedas
					.getCampoDescriptivo(campoBusqueda.getNombre());
		} else {
			idCampoKey = restriccionCampoBusqueda.getId();
			if (idCampoKey != null) {
				configuracionCampo = configuracionBusquedas
						.getCampoDescriptivo(idCampoKey);
				;
			}
		}

		String idCampoFecha = configuracionCampo == null ? RestriccionCampoBusqueda.RESTRICCION_IDCAMPO_TODOS_VALORES
				: configuracionCampo.getValor();
		String calificador = busquedaElementosVO
				.getCalificadorFechaCampo(campoBusqueda.getNombre());
		Date fechaIni = busquedaElementosVO.getFechaIniCampo(campoBusqueda
				.getNombre());
		Date fechaFin = busquedaElementosVO.getFechaFinCampo(campoBusqueda
				.getNombre());

		String[] tables = new String[] { new TableDef(
				FechaDBEntityImpl.TABLE_NAME).getDeclaration() };

		StringBuffer query = new StringBuffer();

		query.append(DBUtils.SELECT)
				.append(FechaDBEntityImpl.CAMPO_ID_ELEMENTO)
				.append(DBUtils.FROM).append(DBUtils.generateTableSet(tables))
				.append(DBUtils.WHERE);

		List condiciones = new ArrayList();

		if (StringUtils.isNotEmpty(idCampoFecha)
				&& !(idCampoFecha
						.equals(RestriccionCampoBusqueda.RESTRICCION_IDCAMPO_TODOS_VALORES))) {
			condiciones.add(DBUtils.generateEQTokenField(
					FechaDBEntityImpl.CAMPO_ID_CAMPO, idCampoFecha));
		}

		if ((fechaIni != null) || (fechaFin != null)) {
			if (fechaIni != null) {
				condiciones.add(DBUtils.generateTokenFieldDateAnioMesDia(conn,
						FechaDBEntityImpl.CAMPO_FECHA_FINAL,
						DBUtils.MAYOR_IGUAL, fechaIni));
			}

			if (fechaFin != null) {
				condiciones.add(DBUtils.generateTokenFieldDateAnioMesDia(conn,
						FechaDBEntityImpl.CAMPO_FECHA_FINAL,
						DBUtils.MENOR_IGUAL, fechaFin));
			}
		}

		if (StringUtils.isNotEmpty(calificador)) {
			condiciones.add(DBUtils.generateEQTokenField(
					FechaDBEntityImpl.CAMPO_CALIFICADOR, calificador));
		}

		query.append(joinConditions(condiciones, DBUtils.AND));

		return query.toString();
	}

	private static String componerQueryNoElementoCfRango(DbConnection conn,
			CampoBusqueda campoBusqueda,
			BusquedaElementosVO busquedaElementosVO, String texto,
			String aliasTablaPrincipal, boolean componerNumeroExpediente) {
		ConfiguracionBusquedas configuracionBusquedas = ConfiguracionBusquedasFactory
				.getConfiguracionBusquedas();
		CampoDescriptivoConfigBusqueda campoDCBInicial = configuracionBusquedas
				.getCampoDescriptivo(RestriccionesCamposBusquedas.RESTRICCION_CAMPO_BUSQUEDA_RANGO_INICIAL_NORM);
		String idCampoInicial = (campoDCBInicial == null ? RestriccionCampoBusqueda.RESTRICCION_IDCAMPO_TODOS_VALORES
				: campoDCBInicial.getValor());
		CampoDescriptivoConfigBusqueda campoDCBFinal = configuracionBusquedas
				.getCampoDescriptivo(RestriccionesCamposBusquedas.RESTRICCION_CAMPO_BUSQUEDA_RANGO_FINAL_NORM);
		String idCampoFinal = (campoDCBFinal == null ? RestriccionCampoBusqueda.RESTRICCION_IDCAMPO_TODOS_VALORES
				: campoDCBFinal.getValor());

		List condiciones = new ArrayList();
		String condicion = componerQueryNoElementoCfTextoCorto(
				common.util.StringUtils.normalizarTexto(texto),
				aliasTablaPrincipal, idCampoInicial, idCampoFinal);
		if (StringUtils.isNotEmpty(condicion)) {
			condiciones.add(condicion);
		}

		// Tratar restricciones
		Map restricciones = campoBusqueda.getRestricciones();
		if ((restricciones != null)
				&& (restricciones
						.containsKey(RestriccionesCamposBusquedas.RESTRICCION_CAMPO_BUSQUEDA_NUMERO_EXPEDIENTE))
				&& componerNumeroExpediente) {
			condicion = componerQueryNoElementoCfNumeroExpediente(conn,
					campoBusqueda, busquedaElementosVO,
					busquedaElementosVO.getRango(), null, aliasTablaPrincipal,
					false);
			if (StringUtils.isNotEmpty(condicion)) {
				condiciones.add(condicion);
			}
		}

		return joinConditions(condiciones, DBUtils.UNION);
	}

	private static String componerQueryNoElementoCfDatoNumerico(String numero,
			String numeroComp, String tipoMedida, String unidadMedida,
			String aliasTablaPrincipal) {
		StringBuffer query = new StringBuffer();

		if (StringUtils.isNotEmpty(numero)
				|| StringUtils.isNotEmpty(tipoMedida)
				|| StringUtils.isNotEmpty(unidadMedida)) {

			String[] tables = new String[] { new TableDef(
					NumeroDBEntityImpl.TABLE_NAME).getDeclaration() };

			query.append(DBUtils.SELECT)
					.append(NumeroDBEntityImpl.CAMPO_ID_ELEMENTO)
					.append(DBUtils.FROM)
					.append(DBUtils.generateTableSet(tables))
					.append(DBUtils.WHERE);

			List condiciones = new ArrayList();
			if (StringUtils.isNotBlank(numero)) {

				condiciones.add(new StringBuffer(NumeroDBEntityImpl.CAMPO_VALOR
						.getQualifiedName()).append(numeroComp).append(numero)
						.toString());
			}

			if (StringUtils.isNotBlank(tipoMedida)) {
				condiciones.add(DBUtils.generateEQTokenField(
						NumeroDBEntityImpl.CAMPO_TIPOMEDIDA, tipoMedida));
			}

			if (StringUtils.isNotBlank(unidadMedida)) {
				condiciones.add(new StringBuffer().append(DBUtils.UPPER)
						.append(DBUtils.ABRIR_PARENTESIS)
						.append(NumeroDBEntityImpl.CAMPO_UNIDADMEDIDA)
						.append(DBUtils.CERRAR_PARENTESIS).append(DBUtils.LIKE)
						.append(DBUtils.COMILLA_SIMPLE)
						.append(DBUtils.TANTO_POR_CIENTO)
						.append(unidadMedida.toUpperCase())
						.append(DBUtils.TANTO_POR_CIENTO)
						.append(DBUtils.COMILLA_SIMPLE).toString());
			}
			query.append(joinConditions(condiciones, DBUtils.AND));
		}

		return query.toString();
	}

	private static String componerQueryNoElementoCfDescriptores(
			String[] descriptores, String aliasTablaPrincipal, String idCampo) {
		StringBuffer query = new StringBuffer();

		String[] idsCampo = null;
		if (StringUtils.isNotEmpty(idCampo)) {
			idsCampo = idCampo.split(Constants.COMMA);
		}

		if ((descriptores != null) && (descriptores.length > 0)) {

			query.append(DBUtils.SELECT)
					.append(ReferenciaDBEntityImpl.CAMPO_ID_ELEMENTO)
					.append(DBUtils.FROM)
					.append(new TableDef(ReferenciaDBEntityImpl.TABLE_NAME)
							.getDeclaration())
					.append(DBUtils.WHERE)
					.append(DBUtils.generateEQTokenField(
							ReferenciaDBEntityImpl.CAMPO_TIPOOBJREF,
							CampoReferenciaVO.REFERENCIA_A_ELEMENTO_DESCRIPTOR))
					.append(DBUtils.AND)
					.append(DBUtils
							.generateInTokenField(
									ReferenciaDBEntityImpl.CAMPO_IDOBJREF,
									descriptores));

			if (ArrayUtils.isNotEmpty(idsCampo)
					&& !(idCampo
							.equals(RestriccionCampoBusqueda.RESTRICCION_IDCAMPO_TODOS_VALORES))) {
				query.append(DBUtils.AND)
						.append(DBUtils
								.generateInTokenField(
										ReferenciaDBEntityImpl.CAMPO_ID_CAMPO,
										idsCampo));
			}
		}

		return query.toString();
	}

	private static String componerQueryNoElementoCfProductor(
			String[] descriptores, String aliasTablaPrincipal) {

		ConfiguracionBusquedas configuracionBusquedas = ConfiguracionBusquedasFactory
				.getConfiguracionBusquedas();
		CampoDescriptivoConfigBusqueda campoConfiguracionProductor = configuracionBusquedas
				.getCampoDescriptivo(RestriccionesCamposBusquedas.RESTRICCION_CAMPO_BUSQUEDA_PRODUCTOR);
		String idCampo = (campoConfiguracionProductor == null ? RestriccionCampoBusqueda.RESTRICCION_IDCAMPO_TODOS_VALORES
				: campoConfiguracionProductor.getValor());

		return componerQueryNoElementoCfDescriptores(descriptores,
				aliasTablaPrincipal, idCampo);
	}

	private static String componerQueryNoElementoCfDescriptores(
			String[] descriptores, String aliasTablaPrincipal) {
		return componerQueryNoElementoCfDescriptores(descriptores,
				aliasTablaPrincipal,
				RestriccionCampoBusqueda.RESTRICCION_IDCAMPO_TODOS_VALORES);
	}

	private static String componerQueryNoElementoCfBloqueos(DbConnection conn,
			String[] bloqueos, String aliasTablaPrincipal) {
		StringBuffer query = new StringBuffer();

		if (!ArrayUtils.isEmpty(bloqueos)) {
			// Solamente puede llegarle un valor (0 ó 1)
			query.append(DBUtils.SELECT)
					.append(UnidadDocumentalDBEntityImpl.CAMPO_ID)
					.append(DBUtils.FROM)
					.append(new TableDef(
							UnidadDocumentalDBEntityImpl.TABLE_NAME_UNIDAD_DOCUMENTAL)
							.getDeclaration()).append(DBUtils.WHERE);

			if (FondosConstants.UDOC_DESBLOQUEADA.equals(bloqueos[0])) {
				query.append(
						DBUtils.generateEQTokenField(
								UnidadDocumentalDBEntityImpl.CAMPO_MARCAS_BLOQUEO,
								FondosConstants.UDOC_DESBLOQUEADA))
						.append(DBUtils.OR)
						.append(DBUtils
								.generateIsNullCondition(
										conn,
										UnidadDocumentalDBEntityImpl.CAMPO_MARCAS_BLOQUEO));
			} else
				query.append(DBUtils.generateGTTokenField(
						UnidadDocumentalDBEntityImpl.CAMPO_MARCAS_BLOQUEO,
						FondosConstants.UDOC_DESBLOQUEADA));
		}

		return query.toString();
	}

	private static String componerCondicionNoElementoCf(DbConnection conn,
			CampoBusqueda campoBusqueda,
			BusquedaElementosVO busquedaElementosVO, String aliasTablaPrincipal) {
		if (campoBusqueda == null)
			return null;

		String condicion = null;

		if (campoBusqueda.getNombre().equals(
				CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_SIGNATURA)) {
			condicion = componerQueryNoElementoCfSignatura(
					busquedaElementosVO.getSignatura(),
					busquedaElementosVO.getSignaturaLike());
		} else if (campoBusqueda.getNombre().equals(
				CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_PROCEDIMIENTO)) {
			condicion = componerQueryNoElementoCfProcedimiento(busquedaElementosVO
					.getProcedimiento());
		} else if (campoBusqueda.getNombre().equals(
				CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_CODIGO_RELACION)) {
			condicion = componerQueryNoElementoCfCodigoRelacion(conn,
					busquedaElementosVO.getCodigoRelacion(),
					aliasTablaPrincipal, campoBusqueda.getRestricciones());
		} else if (campoBusqueda.getNombre().equals(
				CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_NUMERO_EXPEDIENTE)) {
			condicion = componerQueryNoElementoCfNumeroExpediente(conn,
					campoBusqueda, busquedaElementosVO,
					busquedaElementosVO.getNumeroExpediente(),
					busquedaElementosVO.getCalificadorNumeroExpediente(),
					aliasTablaPrincipal, true);
		} else if (campoBusqueda.getNombre().equals(
				CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_TEXTO)) {
			StringBuffer sql = new StringBuffer()
			.append(componerQueryNoElementoCfTexto(conn,
					busquedaElementosVO.getTexto(), aliasTablaPrincipal,
					campoBusqueda.getTipo(), campoBusqueda.getRestricciones()));

			condicion = sql.toString();

		} else if (campoBusqueda.getNombre().equals(
				CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_FECHA_INICIAL)) {
			condicion = componerQueryNoElementoCfFecha(conn, campoBusqueda,
					busquedaElementosVO, aliasTablaPrincipal);
		} else if (campoBusqueda.getNombre().equals(
				CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_FECHA_FINAL)) {
			condicion = componerQueryNoElementoCfFecha(conn, campoBusqueda,
					busquedaElementosVO, aliasTablaPrincipal);
		} else if (campoBusqueda.getNombre().equals(
				CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_FECHAS)) {
			condicion = componerQueryNoElementoCfFecha(conn, campoBusqueda,
					busquedaElementosVO, aliasTablaPrincipal);
		} else if (campoBusqueda.getNombre().equals(
				CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_DATO_NUMERICO)) {
			condicion = componerQueryNoElementoCfDatoNumerico(
					busquedaElementosVO.getNumero(),
					busquedaElementosVO.getNumeroComp(),
					busquedaElementosVO.getTipoMedida(),
					busquedaElementosVO.getUnidadMedida(), aliasTablaPrincipal);
		} else if (campoBusqueda.getNombre().equals(
				CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_RANGOS)) {
			condicion = componerQueryNoElementoCfRango(conn, campoBusqueda,
					busquedaElementosVO, busquedaElementosVO.getRango(),
					aliasTablaPrincipal, true);
		} else if (campoBusqueda.getNombre().equals(
				CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_PRODUCTOR)) {
			condicion = componerQueryNoElementoCfProductor(
					busquedaElementosVO.getProductores(), aliasTablaPrincipal);
		} else if (campoBusqueda.getNombre().equals(
				CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_DESCRIPTORES)) {
			condicion = componerQueryNoElementoCfDescriptores(
					busquedaElementosVO.getDescriptores(), aliasTablaPrincipal);
		} else if (campoBusqueda.getNombre().equals(
				CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_BLOQUEO)) {
			condicion = componerQueryNoElementoCfBloqueos(conn,
					busquedaElementosVO.getBloqueos(), aliasTablaPrincipal);
		} else if (campoBusqueda.getNombre().equals(
				CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_CONDICIONES_AVANZADAS)) {
			condicion = BusquedasGeneratorCondicionesAvanzadasHelper
					.componerQueryNoElementoCfCondicionesAvanzadas(conn,
							busquedaElementosVO, campoBusqueda,
							aliasTablaPrincipal);
		}

		if (StringUtils.isNotEmpty(condicion)) {
			condicion = DBUtils.ABRIR_PARENTESIS + condicion
					+ DBUtils.CERRAR_PARENTESIS;
		}

		return condicion;
	}

	private static String componerQueryGenericoDatoNumerico(DbConnection conn,
			String numero, String numeroFin, String idCampo, String operador,
			String aliasTablaPrincipal) {
		StringBuffer query = new StringBuffer();

		query.append(DBUtils.SELECT)
				.append(NumeroDBEntityImpl.CAMPO_ID_ELEMENTO)
				.append(DBUtils.FROM)
				.append(new TableDef(NumeroDBEntityImpl.TABLE_NAME)
						.getDeclaration()).append(DBUtils.WHERE);

		int operator = ConstraintType.getValue(operador);
		if (operator == -1)
			operator = ConstraintType.EQUAL;

		if (operador.equals(CustomDateFormat.DATE_OPERATOR_RANGE)) {
			int numeroInt = NumberUtils.toInt(numero);
			int numeroFinInt = NumberUtils.toInt(numeroFin);

			query.append(DBUtils.ABRIR_PARENTESIS)
					.append(DBUtils.generateBetweenNumerico(
							NumeroDBEntityImpl.CAMPO_VALOR, numeroInt,
							numeroFinInt)).append(DBUtils.CERRAR_PARENTESIS);
		} else {
			query.append(DBUtils.generateTokenFieldQualified(
					NumeroDBEntityImpl.CAMPO_VALOR, numero, null, operator));
		}

		query.append(DBUtils.AND).append(
				DBUtils.generateEQTokenField(NumeroDBEntityImpl.CAMPO_ID_CAMPO,
						idCampo));

		return query.toString();
	}

	private static String componerCondicionGenericoDatoNumerico(
			DbConnection conn, String nombreCampo,
			BusquedaElementosVO busquedaElementosVO, String aliasTablaPrincipal) {
		String condicion = null;
		List condiciones = new ArrayList();

		String[] numeros = busquedaElementosVO.getGenericoCampoNumerico();
		String[] numerosFin = busquedaElementosVO.getGenericoCampoNumericoFin();
		String[] idsCampo = busquedaElementosVO.getGenericoIdCampoNumerico();
		String[] operadores = busquedaElementosVO
				.getGenericoOperadorCampoNumerico();

		if (StringUtils.isNotEmpty(numeros)) {
			for (int i = 0; i < numeros.length; i++) {
				condicion = componerQueryGenericoDatoNumerico(conn, numeros[i],
						numerosFin[i], idsCampo[i], operadores[i],
						aliasTablaPrincipal);
				if (StringUtils.isNotEmpty(condicion)) {
					condiciones.add(condicion);
				}
			}
		}

		return joinConditions(condiciones, DBUtils.INTERSECT);
	}

	private static String componerQueryGenericoFecha(DbConnection conn,
			String idCampo, Date fechaIni, Date fechaFin, String calificador,
			String aliasTablaPrincipal) {

		String[] tables = new String[] { new TableDef(
				FechaDBEntityImpl.TABLE_NAME).getDeclaration() };

		StringBuffer query = new StringBuffer();

		query.append(DBUtils.SELECT)
				.append(FechaDBEntityImpl.CAMPO_ID_ELEMENTO)
				.append(DBUtils.FROM).append(DBUtils.generateTableSet(tables))
				.append(DBUtils.WHERE);

		List condiciones = new ArrayList();

		if (StringUtils.isNotEmpty(idCampo)
				&& !(idCampo
						.equals(RestriccionCampoBusqueda.RESTRICCION_IDCAMPO_TODOS_VALORES))) {
			condiciones.add(DBUtils.generateEQTokenField(
					FechaDBEntityImpl.CAMPO_ID_CAMPO, idCampo));
		}

		if ((fechaIni != null) || (fechaFin != null)) {
			if (fechaIni != null) {
				condiciones.add(DBUtils.generateTokenFieldDateAnioMesDia(conn,
						FechaDBEntityImpl.CAMPO_FECHA_FINAL,
						DBUtils.MAYOR_IGUAL, fechaIni));
			}

			if (fechaFin != null) {
				condiciones.add(DBUtils.generateTokenFieldDateAnioMesDia(conn,
						FechaDBEntityImpl.CAMPO_FECHA_FINAL,
						DBUtils.MENOR_IGUAL, fechaFin));
			}
		}

		if (StringUtils.isNotEmpty(calificador)) {
			condiciones.add(DBUtils.generateEQTokenField(
					FechaDBEntityImpl.CAMPO_CALIFICADOR, calificador));
		}

		query.append(joinConditions(condiciones, DBUtils.AND));

		return query.toString();
	}

	private static String componerCondicionGenericoFecha(DbConnection conn,
			String nombreCampo, BusquedaElementosVO busquedaElementosVO,
			String aliasTablaPrincipal) {
		String condicion = null;
		List condiciones = new ArrayList();

		String[] idsFecha = busquedaElementosVO.getGenericoIdFecha();
		Date[] fechasIni = busquedaElementosVO.getGenericoFechaIni();
		Date[] fechasFin = busquedaElementosVO.getGenericoFechaFin();
		String[] calificadores = busquedaElementosVO
				.getGenericoFechaCalificador();

		if (StringUtils.isNotEmpty(idsFecha)) {
			for (int i = 0; i < idsFecha.length; i++) {
				condicion = componerQueryGenericoFecha(conn, idsFecha[i],
						fechasIni[i], fechasFin[i], calificadores[i],
						aliasTablaPrincipal);
				if (StringUtils.isNotEmpty(condicion)) {
					condiciones.add(condicion);
				}
			}
		}

		return joinConditions(condiciones, DBUtils.INTERSECT);
	}

	private static String componerCondicionGenericoTextoCorto(
			DbConnection conn, String nombreCampo,
			BusquedaElementosVO busquedaElementosVO, String aliasTablaPrincipal) {

		String condicion = null;
		List condiciones = new ArrayList();

		String[] textos = busquedaElementosVO.getGenericoTextoCorto();
		String[] idsCampo = busquedaElementosVO.getGenericoIdTextoCorto();
		String[] operadores = busquedaElementosVO
				.getGenericoOperadorTextoCorto();

		if (ArrayUtils.isNotEmpty(textos)) {
			for (int i = 0; i < textos.length; i++) {
				condicion = componerQueryGenericoTextoCorto(conn, textos[i],
						idsCampo[i], operadores[i], aliasTablaPrincipal);
				if (StringUtils.isNotEmpty(condicion)) {
					condiciones.add(condicion);
				}
			}
		}

		return joinConditions(condiciones, DBUtils.INTERSECT);
	}

	private static String componerCondicionGenericoTextoLargo(
			DbConnection conn, String nombreCampo,
			BusquedaElementosVO busquedaElementosVO, String aliasTablaPrincipal) {
		String condicion = null;
		List condiciones = new ArrayList();

		String[] textos = busquedaElementosVO.getGenericoTextoLargo();
		String[] idsCampo = busquedaElementosVO.getGenericoIdTextoLargo();

		if (ArrayUtils.isNotEmpty(textos)) {
			for (int i = 0; i < textos.length; i++) {
				condicion = componerQueryNoElementoCfTextoLargo(conn,
						textos[i], aliasTablaPrincipal, idsCampo[i]);
				if (StringUtils.isNotEmpty(condicion)) {
					condiciones.add(condicion);
				}
			}
		}

		return joinConditions(condiciones, DBUtils.INTERSECT);
	}

	private static String componerCondicionGenerico(DbConnection conn,
			String nombreCampo, BusquedaElementosVO busquedaElementosVO,
			String aliasTablaPrincipal) {
		String condicion = null;

		if (nombreCampo
				.equals(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_GENERICO_CAMPO_FECHA)) {
			condicion = componerCondicionGenericoFecha(conn, nombreCampo,
					busquedaElementosVO, aliasTablaPrincipal);
		} else if (nombreCampo
				.equals(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_GENERICO_CAMPO_NUMERICO)) {
			condicion = componerCondicionGenericoDatoNumerico(conn,
					nombreCampo, busquedaElementosVO, aliasTablaPrincipal);
		} else if (nombreCampo
				.equals(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_GENERICO_TEXTO_CORTO)) {
			condicion = componerCondicionGenericoTextoCorto(conn, nombreCampo,
					busquedaElementosVO, aliasTablaPrincipal);
		} else if (nombreCampo
				.equals(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_GENERICO_TEXTO_LARGO)) {
			condicion = componerCondicionGenericoTextoLargo(conn, nombreCampo,
					busquedaElementosVO, aliasTablaPrincipal);
		}

		if (StringUtils.isNotEmpty(condicion)) {
			condicion = DBUtils.ABRIR_PARENTESIS + condicion
					+ DBUtils.CERRAR_PARENTESIS;
		}

		return condicion;
	}

	private static ConsultaConnectBy componerCondicionElementoCfAmbito(
			DbConnection conn, String[] idsAmbito, String aliasTablaPrincipal)
			throws IeciTdException {
		ConsultaConnectBy consultaConnectBy = null;

		if (ArrayUtils.isNotEmpty(idsAmbito)) {
			consultaConnectBy = DbUtil
					.generateNativeSQLWithConnectBy(
							conn,
							new TableDef(
									ElementoCuadroClasificacionDBEntityImplBase.TABLE_NAME_ELEMENTO),
							ElementoCuadroClasificacionDBEntityImplBase.ID_ELEMENTO_FIELD,
							ElementoCuadroClasificacionDBEntityImplBase.IDPADRE_FIELD,
							idsAmbito, null);

			if (consultaConnectBy != null) {
				if (StringUtils.isNotEmpty(consultaConnectBy.getSqlClause())) {
					StringBuffer sql = new StringBuffer()
							.append(getCustomizedField(
									ElementoCuadroClasificacionDBEntityImplBase.ID_ELEMENTO_FIELD,
									aliasTablaPrincipal)).append(DBUtils.IN)
							.append("(")
							.append(consultaConnectBy.getSqlClause())
							.append(")");

					consultaConnectBy.setSqlClause(sql.toString());
				}
			}
		}
		return consultaConnectBy;
	}

	private static String componerCondicionElementoCfAmbitoTipoFondo(
			DbConnection conn, String[] idsAmbitoFondo,
			String aliasTablaPrincipal) {
		StringBuffer sql = new StringBuffer();

		if (ArrayUtils.isNotEmpty(idsAmbitoFondo)) {
			sql.append(DBUtils
					.generateInTokenField(
							getCustomizedField(
									ElementoCuadroClasificacionDBEntityImplBase.IDFONDO_FIELD,
									aliasTablaPrincipal), idsAmbitoFondo));
		}

		return sql.toString();
	}

	private static String componerCondicionElementoCfAmbitoTipoSerie(
			DbConnection conn, String[] idsAmbitoSerie,
			String aliasTablaPrincipal) {
		StringBuffer sql = new StringBuffer();

		if (ArrayUtils.isNotEmpty(idsAmbitoSerie)) {
			sql.append(DBUtils
					.generateInTokenField(
							getCustomizedField(
									ElementoCuadroClasificacionDBEntityImplBase.IDPADRE_FIELD,
									aliasTablaPrincipal), idsAmbitoSerie));
		}

		return sql.toString();
	}

	private static String componerCondicionElementoCf(DbConnection conn,
			CampoBusqueda campoBusqueda,
			BusquedaElementosVO busquedaElementosVO,
			BusquedaFondosQueryVO busquedaFondosQueryVO,
			String aliasTablaPrincipal) {
		String condicion = null;
		ConsultaConnectBy consultaConnectBy = null;

		if (campoBusqueda.getNombre().equals(
				CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_CODIGO)) {
			condicion = componerCondicionElementoCfCodigo(
					busquedaElementosVO.getCodigo(), aliasTablaPrincipal);
		} else if (campoBusqueda.getNombre().equals(
				CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_CODIGO_REFERENCIA)) {
			condicion = componerCondicionElementoCfCodigoReferencia(
					busquedaElementosVO.getCodigoReferencia(),
					aliasTablaPrincipal);
		} else if (campoBusqueda.getNombre().equals(
				CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_ELEMENTOS_EXCLUIR)) {
			condicion = componerCondicionElementoCfElementosExcluir(
					busquedaElementosVO.getElementosExcluir(),
					aliasTablaPrincipal);
		} else if (campoBusqueda.getNombre().equals(
				CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_ELEMENTOS_RESTRINGIR)) {
			condicion = componerCondicionElementoCfElementosRestringir(
					busquedaElementosVO.getElementosRestringir(),
					aliasTablaPrincipal);
		} else if (campoBusqueda.getNombre().equals(
				CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_ESTADO)) {
			condicion = componerCondicionElementoCfEstados(
					busquedaElementosVO.getEstados(), aliasTablaPrincipal);
		} else if (campoBusqueda.getNombre().equals(
				CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_IDARCHIVO)) {
			condicion = componerCondicionElementoCfIdArchivo(
					busquedaElementosVO.getIdArchivo(), aliasTablaPrincipal);
		} else if (campoBusqueda.getNombre().equals(
				CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_FONDO)) {
			condicion = componerCondicionElementoCfIdFondo(
					busquedaElementosVO.getFondo(), aliasTablaPrincipal);
		} else if (campoBusqueda.getNombre().equals(
				CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_NIVELES_DESCRIPCION)) {
			condicion = componerCondicionElementoCfNiveles(
					busquedaElementosVO.getNiveles(), aliasTablaPrincipal);
		} else if (campoBusqueda
				.getNombre()
				.equals(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_SUBQUERY_ELEMENTOS_RESTRINGIR)) {
			condicion = componerCondicionElementoCfSubqueryElementosRestringir(
					busquedaElementosVO.getSubqueryElementosRestringir(),
					aliasTablaPrincipal);
		} else if (campoBusqueda.getNombre().equals(
				CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_TIPO_ELEMENTO)) {
			condicion = componerCondicionElementoCfTipoElemento(
					busquedaElementosVO.getTipoElemento(), aliasTablaPrincipal);
		} else if (campoBusqueda.getNombre().equals(
				CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_TITULO)) {
			condicion = componerCondicionElementoCfTitulo(conn,
					busquedaElementosVO.getTitulo(), aliasTablaPrincipal);
		} else if (campoBusqueda.getNombre().equals(
				CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_CONSERVADA)) {
			condicion = componerCondicionConservada(conn,
					busquedaElementosVO.getConservada(), aliasTablaPrincipal);
		} else if (campoBusqueda.getNombre().equals(
				CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_AMBITO)) {

			AmbitoVO ambito = new AmbitoVO(
					busquedaElementosVO.getIdObjetoAmbito(),
					busquedaElementosVO.getTipoObjetoAmbito());

			// Comprobar si hay ambitos de tipo fondo o serie
			if (ambito != null && ambito.hasClasificadores()) {
				try {
					consultaConnectBy = componerCondicionElementoCfAmbito(conn,
							busquedaElementosVO.getIdObjetoAmbito(),
							aliasTablaPrincipal);
					if (consultaConnectBy != null) {
						if (StringUtils.isNotEmpty(consultaConnectBy
								.getWithClause())) {
							busquedaFondosQueryVO.setWith(consultaConnectBy
									.getWithClause());
						}
						if (StringUtils.isNotEmpty(consultaConnectBy
								.getSqlClause())) {
							condicion = consultaConnectBy.getSqlClause();
						}
					}
				} catch (IeciTdException e) {
					logger.error("Error al Generar la condición de ámbito", e);
				}
			} else {
				if (ArrayUtils.isNotEmptyOrBlank(ambito.getIdsFondo())) {
					condicion = componerCondicionElementoCfAmbitoTipoFondo(
							conn, ambito.getIdsFondo(), aliasTablaPrincipal);
				}

				if (ArrayUtils.isNotEmptyOrBlank(ambito.getIdsSerie())) {
					if (condicion == null)
						condicion = new String();
					condicion = condicion
							+ DBUtils.getOr(condicion)
							+ componerCondicionElementoCfAmbitoTipoSerie(conn,
									ambito.getIdsSerie(), aliasTablaPrincipal);
				}
			}
		}

		if (StringUtils.isNotEmpty(condicion)) {
			condicion = DBUtils.ABRIR_PARENTESIS + condicion
					+ DBUtils.CERRAR_PARENTESIS;
		}

		return condicion;
	}

	/**
	 * @param conn
	 * @param conservada
	 * @param aliasTablaPrincipal
	 * @return
	 */
	private static String componerCondicionConservada(DbConnection conn,
			String conservada, String aliasTablaPrincipal) {

		if (FondosConstants.UDOC_CONSERVADA.equals(conservada)) {
			return DBUtils
					.generateIsNotNullCondition(
							conn,
							getCustomizedField(
									ElementoCuadroClasificacionDBEntityImplBase.ID_ELIMINACION_FIELD,
									aliasTablaPrincipal));
		} else if (FondosConstants.UDOC_NO_CONSERVADA.equals(conservada)) {
			return DBUtils
					.generateIsNullCondition(
							conn,
							getCustomizedField(
									ElementoCuadroClasificacionDBEntityImplBase.ID_ELIMINACION_FIELD,
									aliasTablaPrincipal));
		} else {
			return Constants.STRING_EMPTY;
		}
	}

	private static String componerCondicionesNoElementoCfUnidadInstalacionNoBloqueada(
			DbConnection conn) {

		String[] tables = new String[] {
				new TableDef(UDocEnUiDepositoDbEntityImpl.TABLE_NAME)
						.getDeclaration(),
				new TableDef(UInstalacionDepositoDBEntity.TABLE_NAME)
						.getDeclaration() };

		StringBuffer query = new StringBuffer()
				.append(DBUtils.SELECT)
				.append(UDocEnUiDepositoDbEntityImpl.IDUNIDADDOC_FIELD)
				.append(DBUtils.FROM)
				.append(DBUtils.generateTableSet(tables))
				.append(DBUtils.WHERE)
				.append(DBUtils.generateJoinCondition(
						UDocEnUiDepositoDbEntityImpl.IDUINSTALACION_FIELD,
						UInstalacionDepositoDBEntity.ID_FIELD))
				.append(DBUtils.AND)
				.append(DBUtils.ABRIR_PARENTESIS)
				.append(DBUtils.generateEQTokenField(
						UInstalacionDepositoDBEntity.MARCAS_BLOQUEO_FIELD,
						FondosConstants.UDOC_DESBLOQUEADA))
				.append(DBUtils.CERRAR_PARENTESIS);
		return query.toString();
	}

	private static String componerCondicionesNoElementoCfUnidadSinDocFisicosNoBloqueada(
			DbConnection conn) {
		StringBuffer query = new StringBuffer()
				.append(DBUtils.SELECT)
				.append(UnidadDocumentalElectronicaDBEntityImpl.IDELEMENTOCF_FIELD)
				.append(DBUtils.FROM)
				.append(new TableDef(
						UnidadDocumentalElectronicaDBEntityImpl.TABLE_NAME)
						.getDeclaration())
				.append(DBUtils.WHERE)
				.append(DBUtils
						.generateEQTokenField(
								UnidadDocumentalElectronicaDBEntityImpl.MARCAS_BLOQUEO_FIELD,
								FondosConstants.UDOC_DESBLOQUEADA));
		return query.toString();
	}

	private static String componerQueryNoElementoCfUnidadNoBloqueada(
			DbConnection conn) {
		List condiciones = new ArrayList();
		condiciones
				.add(componerCondicionesNoElementoCfUnidadInstalacionNoBloqueada(conn));
		condiciones
				.add(componerCondicionesNoElementoCfUnidadSinDocFisicosNoBloqueada(conn));
		StringBuffer query = new StringBuffer()
				.append(DBUtils.ABRIR_PARENTESIS)
				.append(joinConditions(condiciones, DBUtils.UNION))
				.append(DBUtils.CERRAR_PARENTESIS);
		return query.toString();
	}

	private static String componerCondicionesNoElementoCfGenericos(
			ConfiguracionBusquedas configuracionBusquedas,
			List camposRellenosNoElementoCf, List camposRellenosGenericos,
			DbConnection conn, Busqueda busqueda,
			BusquedaElementosVO busquedaElementosVO,
			String aliasTablaPrincipal, boolean eliminarElementosNoBloqueados) {
		List condiciones = new ArrayList();
		String condicion = null;

		// Añadir condiciones de elementos no bloqueados
		if (eliminarElementosNoBloqueados) {
			condiciones.add(componerQueryNoElementoCfUnidadNoBloqueada(conn));
		}

		// Componer condiciones de tablas distintas de elementocf
		if (ListUtils.isNotEmpty(camposRellenosNoElementoCf)) {
			CampoBusqueda campoBusqueda = null;
			Map mapCamposEntrada = busqueda.getMapEntrada();
			Iterator it = camposRellenosNoElementoCf.iterator();
			while (it.hasNext()) {
				campoBusqueda = (CampoBusqueda) mapCamposEntrada.get(it.next());
				condicion = componerCondicionNoElementoCf(conn, campoBusqueda,
						busquedaElementosVO, aliasTablaPrincipal);
				if (StringUtils.isNotEmpty(condicion)) {
					condiciones.add(condicion);
				}
			}
		}

		// Componer condiciones de campos genéricos
		if (ListUtils.isNotEmpty(camposRellenosGenericos)) {
			Iterator it = camposRellenosGenericos.iterator();
			while (it.hasNext()) {
				String nombreCampo = (String) it.next();
				condicion = componerCondicionGenerico(conn, nombreCampo,
						busquedaElementosVO, aliasTablaPrincipal);
				if (StringUtils.isNotEmpty(condicion)) {
					condiciones.add(condicion);
				}
			}
		}

		StringBuffer condicionIn = new StringBuffer();

		if (ListUtils.isNotEmpty(condiciones)) {

			String textoUnion = DBUtils.INTERSECT;

			if (busquedaElementosVO.isReemplazoValoresNulos()) {
				try {
					textoUnion = DbUtil.getNativeMinusSintax(conn);
				} catch (IeciTdException e) {
					logger.error(e);
				}
			}

			condicionIn
					.append(DBUtils
							.generateInTokenFieldSubQuery(
									getCustomizedField(
											ElementoCuadroClasificacionDBEntityImplBase.ID_ELEMENTO_FIELD,
											aliasTablaPrincipal),
									joinConditions(condiciones, textoUnion)));
		}

		return condicionIn.toString();
	}

	private static String componerCondicionesElementoCf(
			ConfiguracionBusquedas configuracionBusquedas,
			List camposRellenosElementoCf, DbConnection conn,
			Busqueda busqueda, BusquedaElementosVO busquedaElementosVO,
			BusquedaFondosQueryVO busquedaFondosQueryVO,
			String aliasTablaPrincipal) {
		List condiciones = new ArrayList();
		String condicion = null;
		if (ListUtils.isNotEmpty(camposRellenosElementoCf)) {
			CampoBusqueda campoBusqueda = null;
			Map mapCamposEntrada = busqueda.getMapEntrada();
			Iterator it = camposRellenosElementoCf.iterator();
			while (it.hasNext()) {
				String nombreCampo = (String) it.next();
				campoBusqueda = (CampoBusqueda) mapCamposEntrada
						.get(nombreCampo);
				if (campoBusqueda == null) {
					campoBusqueda = new CampoBusqueda();
					campoBusqueda.setNombre(nombreCampo);
				}
				condicion = componerCondicionElementoCf(conn, campoBusqueda,
						busquedaElementosVO, busquedaFondosQueryVO,
						aliasTablaPrincipal);
				if (StringUtils.isNotEmpty(condicion)) {
					condiciones.add(condicion);
				}
			}
		}
		return joinConditions(condiciones, DBUtils.AND);
	}

	private static DbColumnDef getOrderColumn(
			BusquedaElementosVO busquedaElementosVO, String aliasTablaPrincipal) {
		DbColumnDef orderColumn = ElementoCFVO.getColsMappings()[busquedaElementosVO
				.getOrderColumn()];

		if (orderColumn
				.getTableName()
				.equals(ElementoCuadroClasificacionDBEntityImplBase.TABLE_NAME_ELEMENTO)) {
			orderColumn = new DbColumnDef(aliasTablaPrincipal,
					getCustomizedField(orderColumn, aliasTablaPrincipal));
		}

		return orderColumn;
	}

	private static String generateOrderBy(
			BusquedaElementosVO busquedaElementosVO, String aliasTablaPrincipal) {
		DbColumnDef orderColumn = getOrderColumn(busquedaElementosVO,
				aliasTablaPrincipal);

		String orderBy = Constants.STRING_EMPTY;
		if (SortOrderEnum.ASCENDING.getName().equalsIgnoreCase(
				busquedaElementosVO.getTipoOrdenacion())) {
			orderBy = DBUtils.generateOrderByWithNullValues(orderColumn);
		} else if (SortOrderEnum.DESCENDING.getName().equalsIgnoreCase(
				busquedaElementosVO.getTipoOrdenacion())) {
			orderBy = DBUtils.generateOrderByDescWithNullValues(orderColumn);
		}
		return orderBy;
	}

	private static String generateOrderByFullQuery(
			BusquedaElementosVO busquedaElementosVO, String aliasTablaPrincipal) {
		DbColumnDef orderColumn = getOrderColumn(busquedaElementosVO,
				aliasTablaPrincipal);

		if (orderColumn.getName().equals(
				FechaDBEntityImpl.FECHA_INICIAL_COLUMN_NAME)
				|| orderColumn.getName().equals(
						FechaDBEntityImpl.FECHA_FINAL_COLUMN_NAME)) {
			orderColumn = getCustomizedField(orderColumn, aliasTablaPrincipal);
		}

		String orderBy = Constants.STRING_EMPTY;
		if (SortOrderEnum.ASCENDING.getName().equalsIgnoreCase(
				busquedaElementosVO.getTipoOrdenacion())) {
			orderBy = DBUtils.generateOrderByWithNullValues(orderColumn);
		} else if (SortOrderEnum.DESCENDING.getName().equalsIgnoreCase(
				busquedaElementosVO.getTipoOrdenacion())) {
			orderBy = DBUtils.generateOrderByDescWithNullValues(orderColumn);
		}
		return orderBy;
	}

	private static DbColumnDef[] addOrderColumns(DbColumnDef[] columnas,
			DbColumnDef orderColumn) {
		DbColumnDef[] totalCols = new DbColumnDef[columnas.length + 2];
		for (int i = 0; i < columnas.length; i++)
			totalCols[i] = columnas[i];

		DbColumnDef colOrder1 = new DbColumnDef("Order1",
				orderColumn.getQualifiedName() + " AS ORDER1",
				DbDataType.SHORT_TEXT);
		totalCols[totalCols.length - 2] = colOrder1;

		DbColumnDef colOrder2 = new DbColumnDef("Order2",
				DBUtils.generateColWithNullValues(orderColumn) + " AS ORDER2",
				DbDataType.SHORT_INTEGER);
		totalCols[totalCols.length - 1] = colOrder2;
		return totalCols;
	}

	private static void completeOrderColsTablesForIds(
			BusquedaFondosQueryVO busquedaFondosQueryVO,
			BusquedaElementosVO busquedaElementosVO, boolean obtenerPartes,
			String aliasTablaPrincipal) {

		DbColumnDef[] columnas = null;

		boolean generateLeftOuterTableUdocEnUi = false;
		boolean generatedTablaUnidadDoc = false;

		if (obtenerPartes) {
			// Obtener distintas signaturas
			columnas = new DbColumnDef[] {
					getCustomizedField(
							ElementoCuadroClasificacionDBEntityImplBase.ID_ELEMENTO_FIELD,
							aliasTablaPrincipal),
					UdocEnUIDBEntityImpl.SIGNATURA_FIELD,
					UnidadDocumentalDBEntityImpl.CAMPO_NUMERO_EXPEDIENTE };
		} else {
			columnas = new DbColumnDef[] { getCustomizedField(
					ElementoCuadroClasificacionDBEntityImplBase.ID_ELEMENTO_FIELD,
					aliasTablaPrincipal) };
		}

		DbColumnDef orderColumn = getOrderColumn(busquedaElementosVO,
				aliasTablaPrincipal);

		// Establecer las columnas a completar
		busquedaFondosQueryVO.setColsIdsFill(columnas);

		// Establecer todas las columnas a generar en la query de Ids
		DbColumnDef[] totalCols = addOrderColumns(columnas, orderColumn);
		busquedaFondosQueryVO.setColsIds(totalCols);

		DbColumnDef columnaIdElementoCf = getCustomizedField(
				ElementoCuadroClasificacionDBEntityImplBase.ID_ELEMENTO_FIELD,
				aliasTablaPrincipal);
		TableDef tablaPrincipal = new TableDef(
				ElementoCuadroClasificacionDBEntityImplBase.TABLE_NAME_ELEMENTO,
				aliasTablaPrincipal);
		if (orderColumn.getTableName().equals(
				UnidadDocumentalDBEntityImpl.TABLE_NAME_UNIDAD_DOCUMENTAL)) {
			JoinDefinition joinElementoCfUnidadDocumental = new JoinDefinition(
					columnaIdElementoCf, UnidadDocumentalDBEntityImpl.CAMPO_ID);
			JoinDefinition[] joinDefinitions = new JoinDefinition[] { joinElementoCfUnidadDocumental };
			busquedaFondosQueryVO.setTablesIds(DBUtils
					.generateLeftOuterJoinCondition(tablaPrincipal,
							joinDefinitions));
			generatedTablaUnidadDoc = true;
		} else if (orderColumn.getTableName().equals(
				FechaDBEntityImpl.TABLE_NAME)) {
			ConfiguracionBusquedas configuracionBusquedas = ConfiguracionBusquedasFactory
					.getConfiguracionBusquedas();
			if (orderColumn.getName().equals(
					FechaDBEntityImpl.FECHA_INICIAL_COLUMN_NAME)) {
				CampoDescriptivoConfigBusqueda configuracionFechaInicial = configuracionBusquedas
						.getCampoDescriptivo(CamposBusquedas.CAMPO_SALIDA_BUSQUEDA_FECHA_INICIAL);
				StringBuffer sqlFechaInicial = new StringBuffer().append(
						DBUtils.AND).append(
						DBUtils.generateEQTokenField(
								FechaDBEntityImpl.CAMPO_ID_CAMPO,
								configuracionFechaInicial.getValor()));
				JoinDefinition joinElementoCfFechaCf = new JoinDefinition(
						columnaIdElementoCf,
						FechaDBEntityImpl.CAMPO_ID_ELEMENTO,
						sqlFechaInicial.toString());
				JoinDefinition[] joinDefinitions = new JoinDefinition[] { joinElementoCfFechaCf };
				busquedaFondosQueryVO.setTablesIds(DBUtils
						.generateLeftOuterJoinCondition(tablaPrincipal,
								joinDefinitions));
			} else if (orderColumn.getName().equals(
					FechaDBEntityImpl.FECHA_FINAL_COLUMN_NAME)) {
				CampoDescriptivoConfigBusqueda configuracionFechaFinal = configuracionBusquedas
						.getCampoDescriptivo(CamposBusquedas.CAMPO_SALIDA_BUSQUEDA_FECHA_FINAL);
				if (orderColumn.getName().equals(
						FechaDBEntityImpl.FECHA_FINAL_COLUMN_NAME)) {
					StringBuffer sqlFechaFinal = new StringBuffer().append(
							DBUtils.AND).append(
							DBUtils.generateEQTokenField(
									FechaDBEntityImpl.CAMPO_ID_CAMPO,
									configuracionFechaFinal.getValor()));
					JoinDefinition joinElementoCfFechaCf = new JoinDefinition(
							columnaIdElementoCf,
							FechaDBEntityImpl.CAMPO_ID_ELEMENTO,
							sqlFechaFinal.toString());
					JoinDefinition[] joinDefinitions = new JoinDefinition[] { joinElementoCfFechaCf };
					busquedaFondosQueryVO.setTablesIds(DBUtils
							.generateLeftOuterJoinCondition(tablaPrincipal,
									joinDefinitions));
				}
			}
		} else if (orderColumn.getTableName().equals(
				UDocEnUiDepositoDbEntityImpl.TABLE_NAME)) {
			generateLeftOuterTableUdocEnUi = true;
		} else if (orderColumn.getTableName().equals(
				NivelCFDBEntityImpl.NIVELCF_TABLE_NAME)) {
			DbColumnDef columnNivelElementoCf = getCustomizedField(
					ElementoCuadroClasificacionDBEntityImplBase.NIVEL_FIELD,
					aliasTablaPrincipal);
			JoinDefinition joinElementoCfNivelCf = new JoinDefinition(
					columnNivelElementoCf, NivelCFDBEntityImpl.ID_NIVEL_FIELD);
			JoinDefinition[] joinDefinitions = new JoinDefinition[] { joinElementoCfNivelCf };
			busquedaFondosQueryVO
					.setTablesIds(DBUtils.generateInnerJoinCondition(
							tablaPrincipal, joinDefinitions));
		} else {
			busquedaFondosQueryVO.setTablesIds(tablaPrincipal.getDeclaration());
		}

		// Aniadir la tabla de unidades documentales para poder obtener
		// signaturas distintas
		if (obtenerPartes) {
			String tablesIds = busquedaFondosQueryVO.getTablesIds();

			// Si no hay tabla aniadir la principal
			if (StringUtils.isEmpty(tablesIds)) {
				tablesIds = tablaPrincipal.getDeclaration();
			}

			// Si no esta aniadida la tabla de unidaddoc aniadirla
			if (generatedTablaUnidadDoc == false) {
				JoinDefinition joinElementoCfUnidadDocumental = new JoinDefinition(
						columnaIdElementoCf,
						UnidadDocumentalDBEntityImpl.CAMPO_ID);
				JoinDefinition[] joinDefinitions = new JoinDefinition[] { joinElementoCfUnidadDocumental };
				TableDef tables = new TableDef(tablesIds,
						Constants.STRING_EMPTY);
				tablesIds = DBUtils.generateInnerJoinCondition(tables,
						joinDefinitions);
			}

			// aniadir la tabla de signaturas
			TableDef tables = new TableDef(tablesIds, Constants.STRING_EMPTY);

			JoinDefinition joinElementoCfUdocEnUiDeposito = new JoinDefinition(
					columnaIdElementoCf,
					UDocEnUiDepositoDbEntityImpl.IDUNIDADDOC_FIELD);
			JoinDefinition[] joinDefinitions = new JoinDefinition[] { joinElementoCfUdocEnUiDeposito };

			busquedaFondosQueryVO.setTablesIds(DBUtils
					.generateInnerJoinCondition(tables, joinDefinitions));
		} else if (generateLeftOuterTableUdocEnUi) {
			JoinDefinition joinElementoCfUdocEnUiDeposito = new JoinDefinition(
					columnaIdElementoCf,
					UDocEnUiDepositoDbEntityImpl.IDUNIDADDOC_FIELD);
			JoinDefinition[] joinDefinitions = new JoinDefinition[] { joinElementoCfUdocEnUiDeposito };
			busquedaFondosQueryVO.setTablesIds(DBUtils
					.generateLeftOuterJoinCondition(tablaPrincipal,
							joinDefinitions));
		}

		// Establecer el order by
		busquedaFondosQueryVO.setOrderByIds(generateOrderBy(
				busquedaElementosVO, aliasTablaPrincipal));

	}

	private static void completeOrderColsTablesForFullQuery(
			BusquedaFondosQueryVO busquedaFondosQueryVO, Busqueda busqueda,
			BusquedaElementosVO busquedaElementosVO, boolean obtenerPartes,
			String aliasTablaPrincipal) {

		TableDef tablaPrincipal = new TableDef(
				ElementoCuadroClasificacionDBEntityImplBase.TABLE_NAME_ELEMENTO,
				aliasTablaPrincipal);

		DbColumnDef orderColumn = getOrderColumn(busquedaElementosVO,
				aliasTablaPrincipal);

		boolean generateLeftOuterTableUnidadDoc = false;
		boolean generateInnerTableUnidadDoc = false;
		boolean generateLeftOuterTableFechaInicial = false;
		boolean generateLeftOuterTableFechaFinal = false;
		boolean generateLeftOuterTableUdocEnUi = false;
		boolean generateInnerTableUdocEnUi = false;
		boolean generateLeftOuterTableFondo = false;
		boolean generateInnerTableNivelCf = false;

		if (orderColumn.getTableName().equals(
				UnidadDocumentalDBEntityImpl.TABLE_NAME_UNIDAD_DOCUMENTAL)) {
			generateLeftOuterTableUnidadDoc = true;
		} else if (orderColumn.getTableName().equals(
				FechaDBEntityImpl.TABLE_NAME)) {
			if (orderColumn.getName().equals(
					FechaDBEntityImpl.FECHA_INICIAL_COLUMN_NAME)) {
				generateLeftOuterTableFechaInicial = true;
			} else if (orderColumn.getName().equals(
					FechaDBEntityImpl.FECHA_FINAL_COLUMN_NAME)) {
				generateLeftOuterTableFechaFinal = true;
			}
		} else if (orderColumn.getTableName().equals(
				UDocEnUiDepositoDbEntityImpl.TABLE_NAME)) {
			generateLeftOuterTableUdocEnUi = true;
		} else if (orderColumn.getTableName().equals(
				NivelCFDBEntityImpl.NIVELCF_TABLE_NAME)) {
			generateInnerTableNivelCf = true;
		}

		if (obtenerPartes) {
			generateInnerTableUdocEnUi = true;
		}

		List columnas = new ArrayList();

		// Columnas de elementos del cuadro
		DbColumnDef columnaIdElementoCf = getCustomizedField(
				ElementoCuadroClasificacionDBEntityImplBase.ID_ELEMENTO_FIELD,
				aliasTablaPrincipal);
		columnas.add(columnaIdElementoCf);
		columnas.add(getCustomizedField(
				ElementoCuadroClasificacionDBEntityImplBase.TIPO_ELEMENTO_FIELD,
				aliasTablaPrincipal));
		columnas.add(getCustomizedField(
				ElementoCuadroClasificacionDBEntityImplBase.SUBTIPO_ELEMENTO_FIELD,
				aliasTablaPrincipal));
		columnas.add(getCustomizedField(
				ElementoCuadroClasificacionDBEntityImplBase.TITULO_FIELD,
				aliasTablaPrincipal));
		columnas.add(getCustomizedField(
				ElementoCuadroClasificacionDBEntityImplBase.IDFONDO_FIELD,
				aliasTablaPrincipal));
		columnas.add(getCustomizedField(
				ElementoCuadroClasificacionDBEntityImplBase.CODIGO_FIELD,
				aliasTablaPrincipal));
		columnas.add(getCustomizedField(
				ElementoCuadroClasificacionDBEntityImplBase.CODIGO_REFERENCIA_FIELD,
				aliasTablaPrincipal));
		columnas.add(getCustomizedField(
				ElementoCuadroClasificacionDBEntityImplBase.ESTADO_ELEMENTO_FIELD,
				aliasTablaPrincipal));
		columnas.add(getCustomizedField(
				ElementoCuadroClasificacionDBEntityImplBase.ID_ELIMINACION_FIELD,
				aliasTablaPrincipal));

		String aliasFechaInicial = "FI";
		String sufijoAliasFechaInicial = "FInicial";
		String aliasFechaFinal = "FF";
		String sufijoAliasFechaFinal = "FFinal";
		String aliasCampoFondo = "nombreFondo";
		String aliasTablaFondo = "ELEMENTOCFFONDO";

		Map camposSalida = busqueda.getMapSalida();
		if (!camposSalida.isEmpty()) {
			Iterator it = camposSalida.entrySet().iterator();
			while (it.hasNext()) {
				Entry cbEntry = (Entry) it.next();
				CampoBusqueda cb = (CampoBusqueda) cbEntry.getValue();
				if (CamposBusquedas.CAMPO_SALIDA_BUSQUEDA_FECHA_INICIAL
						.equals(cb.getNombre())) {
					generateLeftOuterTableFechaInicial = true;
					columnas.add(new DbColumnDef(FechaDBEntityImpl.CAMPO_VALOR
							.getName() + sufijoAliasFechaInicial,
							getCustomizedField(FechaDBEntityImpl.CAMPO_VALOR,
									aliasFechaInicial)));
					columnas.add(new DbColumnDef(
							FechaDBEntityImpl.CAMPO_CALIFICADOR.getName()
									+ sufijoAliasFechaInicial,
							getCustomizedField(
									FechaDBEntityImpl.CAMPO_CALIFICADOR,
									aliasFechaInicial)));
					columnas.add(new DbColumnDef(
							FechaDBEntityImpl.CAMPO_FORMATO.getName()
									+ sufijoAliasFechaInicial,
							getCustomizedField(FechaDBEntityImpl.CAMPO_FORMATO,
									aliasFechaInicial)));
				} else if (CamposBusquedas.CAMPO_SALIDA_BUSQUEDA_FECHA_FINAL
						.equals(cb.getNombre())) {
					generateLeftOuterTableFechaFinal = true;
					columnas.add(new DbColumnDef(FechaDBEntityImpl.CAMPO_VALOR
							.getName() + sufijoAliasFechaFinal,
							getCustomizedField(FechaDBEntityImpl.CAMPO_VALOR,
									aliasFechaFinal)));
					columnas.add(new DbColumnDef(
							FechaDBEntityImpl.CAMPO_CALIFICADOR.getName()
									+ sufijoAliasFechaFinal,
							getCustomizedField(
									FechaDBEntityImpl.CAMPO_CALIFICADOR,
									aliasFechaFinal)));
					columnas.add(new DbColumnDef(
							FechaDBEntityImpl.CAMPO_FORMATO.getName()
									+ sufijoAliasFechaFinal,
							getCustomizedField(FechaDBEntityImpl.CAMPO_FORMATO,
									aliasFechaFinal)));

				} else if (CamposBusquedas.CAMPO_SALIDA_BUSQUEDA_FONDO
						.equals(cb.getNombre())) {
					generateLeftOuterTableFondo = true;
					columnas.add(new DbColumnDef(
							aliasCampoFondo,
							getCustomizedField(
									ElementoCuadroClasificacionDBEntityImplBase.TITULO_FIELD,
									aliasTablaFondo)));
				} else if (CamposBusquedas.CAMPO_SALIDA_BUSQUEDA_NIVEL
						.equals(cb.getNombre())) {
					generateInnerTableNivelCf = true;
					columnas.add(new DbColumnDef(new TableDef(
							NivelCFDBEntityImpl.NIVELCF_TABLE_NAME),
							NivelCFDBEntityImpl.NOMBRE_NIVEL_FIELD));
				} else if (CamposBusquedas.CAMPO_SALIDA_BUSQUEDA_NUMERO_EXPEDIENTE
						.equals(cb.getNombre())) {
					generateLeftOuterTableUnidadDoc = true;
				} else if (CamposBusquedas.CAMPO_SALIDA_BUSQUEDA_RANGOS
						.equals(cb.getNombre())) {
					generateLeftOuterTableUnidadDoc = true;
				} else if (CamposBusquedas.CAMPO_SALIDA_BUSQUEDA_RANGOS_NUMERO_EXPEDIENTE
						.equals(cb.getNombre())) {
					generateLeftOuterTableUnidadDoc = true;
				} else if (CamposBusquedas.CAMPO_SALIDA_BUSQUEDA_SIGNATURA
						.equals(cb.getNombre())) {
					generateLeftOuterTableUdocEnUi = true;
					generateLeftOuterTableUnidadDoc = true;
					columnas.add(getCustomizedField(
							ElementoCuadroClasificacionDBEntityImplBase.IDFONDO_FIELD,
							aliasTablaPrincipal));
					columnas.add(UnidadDocumentalDBEntityImpl.CAMPO_SISTEMA_PRODUCTOR);
				} else if (CamposBusquedas.CAMPO_SALIDA_BUSQUEDA_SIGNATURA_OBLIGATORIA
						.equals(cb.getNombre())) {
					generateLeftOuterTableUdocEnUi = true;
					generateInnerTableUnidadDoc = true;
					columnas.add(getCustomizedField(
							ElementoCuadroClasificacionDBEntityImplBase.IDFONDO_FIELD,
							aliasTablaPrincipal));
					columnas.add(UnidadDocumentalDBEntityImpl.CAMPO_SISTEMA_PRODUCTOR);
				} else if (CamposBusquedas.CAMPO_SALIDA_BUSQUEDA_NIVEL
						.equals(cb.getNombre())) {
					TableDef tablaNiveles = new TableDef(
							NivelCFDBEntityImpl.NIVELCF_TABLE_NAME);
					generateInnerTableNivelCf = true;
					columnas.add(new DbColumnDef(tablaNiveles,
							NivelCFDBEntityImpl.NOMBRE_NIVEL_FIELD));
				}
				// else if
				// (CamposBusquedas.CAMPO_SALIDA_BUSQUEDA_DOCUMENTOS_ELECTRONICOS.equals(cb.getNombre())){
				// DbColumnDef columnaDocs = new
				// DbColumnDef(ElementoCuadroClasificacionDBEntityImplBase.NUM_DOCS_ELECTRONICOS_NAME,DocDocumentoCFDBEntityImpl.getCountSQL(columnaIdElementoCf,
				// ElementoCuadroClasificacionDBEntityImplBase.NUM_DOCS_ELECTRONICOS_NAME),
				// DbDataType.SHORT_INTEGER);
				// columnas.add(columnaDocs);
				// }
			}
		}

		// Si se genera inner join con la tabla de unidaddoc no generar el left
		// outer
		if (generateInnerTableUnidadDoc) {
			generateLeftOuterTableUnidadDoc = false;
		}

		if (generateInnerTableUdocEnUi) {
			generateLeftOuterTableUdocEnUi = false;
		}

		if (generateInnerTableUdocEnUi || generateLeftOuterTableUdocEnUi) {
			columnas.add(UDocEnUiDepositoDbEntityImpl.SIGNATURAUDOC_FIELD);
			columnas.add(UDocEnUiDepositoDbEntityImpl.IDENTIFICACION_FIELD);
		}

		// Aniadir la columna numero de expediente si es necesario
		if (generateInnerTableUnidadDoc || generateLeftOuterTableUnidadDoc) {
			columnas.add(UnidadDocumentalDBEntityImpl.CAMPO_NUMERO_EXPEDIENTE);
		}

		List innerJoins = new ArrayList();
		List leftOuterJoins = new ArrayList();

		if (generateInnerTableNivelCf) {
			DbColumnDef columnNivelElementoCf = getCustomizedField(
					ElementoCuadroClasificacionDBEntityImplBase.NIVEL_FIELD,
					aliasTablaPrincipal);
			JoinDefinition joinElementoCfNivelCf = new JoinDefinition(
					columnNivelElementoCf, NivelCFDBEntityImpl.ID_NIVEL_FIELD);
			innerJoins.add(joinElementoCfNivelCf);
		}

		if (generateInnerTableUnidadDoc) {
			JoinDefinition joinElementoCfUnidadDocumental = new JoinDefinition(
					columnaIdElementoCf, UnidadDocumentalDBEntityImpl.CAMPO_ID);
			innerJoins.add(joinElementoCfUnidadDocumental);
		}

		if (generateInnerTableUdocEnUi) {
			JoinDefinition joinElementoCfUdocEnUiDeposito = new JoinDefinition(
					columnaIdElementoCf,
					UDocEnUiDepositoDbEntityImpl.IDUNIDADDOC_FIELD);
			innerJoins.add(joinElementoCfUdocEnUiDeposito);
		}

		ConfiguracionBusquedas configuracionBusquedas = ConfiguracionBusquedasFactory
				.getConfiguracionBusquedas();

		if (generateLeftOuterTableFechaInicial) {
			TableDef tablaFecha = new TableDef(FechaDBEntityImpl.TABLE_NAME,
					aliasFechaInicial);
			TableDef tablaElemento = new TableDef(
					ElementoCuadroClasificacionDBEntityImplBase.TABLE_NAME_ELEMENTO,
					aliasTablaPrincipal);
			CampoDescriptivoConfigBusqueda configuracionFechaInicial = configuracionBusquedas
					.getCampoDescriptivo(CamposBusquedas.CAMPO_SALIDA_BUSQUEDA_FECHA_INICIAL);

			StringBuffer sqlFechaInicial = new StringBuffer().append(
					DBUtils.AND).append(
					DBUtils.generateEQTokenField(
							getCustomizedField(
									FechaDBEntityImpl.CAMPO_ID_CAMPO,
									aliasFechaInicial),
							configuracionFechaInicial.getValor()));

			JoinDefinition joinElementoCfFechaCf = new JoinDefinition(
					new DbColumnDef(
							tablaElemento,
							getCustomizedField(
									ElementoCuadroClasificacionDBEntityImplBase.ID_ELEMENTO_FIELD,
									aliasTablaPrincipal)), new DbColumnDef(
							tablaFecha, getCustomizedField(
									FechaDBEntityImpl.CAMPO_ID_ELEMENTO,
									aliasFechaInicial)),
					sqlFechaInicial.toString());

			leftOuterJoins.add(joinElementoCfFechaCf);
		}

		if (generateLeftOuterTableFechaFinal) {
			TableDef tablaFecha = new TableDef(FechaDBEntityImpl.TABLE_NAME,
					aliasFechaFinal);
			TableDef tablaElemento = new TableDef(
					ElementoCuadroClasificacionDBEntityImplBase.TABLE_NAME_ELEMENTO,
					aliasTablaPrincipal);
			CampoDescriptivoConfigBusqueda configuracionFechaFinal = configuracionBusquedas
					.getCampoDescriptivo(CamposBusquedas.CAMPO_SALIDA_BUSQUEDA_FECHA_FINAL);
			StringBuffer sqlFechaFinal = new StringBuffer().append(DBUtils.AND)
					.append(DBUtils.generateEQTokenField(
							getCustomizedField(
									FechaDBEntityImpl.CAMPO_ID_CAMPO,
									aliasFechaFinal), configuracionFechaFinal
									.getValor()));

			JoinDefinition joinElementoCfFechaCf = new JoinDefinition(
					new DbColumnDef(
							tablaElemento,
							getCustomizedField(
									ElementoCuadroClasificacionDBEntityImplBase.ID_ELEMENTO_FIELD,
									aliasTablaPrincipal)), new DbColumnDef(
							tablaFecha, getCustomizedField(
									FechaDBEntityImpl.CAMPO_ID_ELEMENTO,
									aliasFechaFinal)), sqlFechaFinal.toString());

			leftOuterJoins.add(joinElementoCfFechaCf);
		}

		if (generateLeftOuterTableFondo) {
			TableDef tablaFondo = new TableDef(
					ElementoCuadroClasificacionDBEntityImplBase.TABLE_NAME_ELEMENTO,
					aliasTablaFondo);
			TableDef tablaElemento = new TableDef(
					ElementoCuadroClasificacionDBEntityImplBase.TABLE_NAME_ELEMENTO,
					aliasTablaPrincipal);

			JoinDefinition joinElementoCfFondo = new JoinDefinition(
					new DbColumnDef(
							tablaElemento,
							getCustomizedField(
									ElementoCuadroClasificacionDBEntityImplBase.IDFONDO_FIELD,
									aliasTablaPrincipal)),
					new DbColumnDef(
							tablaFondo,
							getCustomizedField(
									ElementoCuadroClasificacionDBEntityImplBase.ID_ELEMENTO_FIELD,
									aliasTablaFondo)));
			innerJoins.add(joinElementoCfFondo);
		}

		if (generateLeftOuterTableUdocEnUi) {
			JoinDefinition joinElementoCfUdocEnUiDeposito = new JoinDefinition(
					columnaIdElementoCf,
					UDocEnUiDepositoDbEntityImpl.IDUNIDADDOC_FIELD);
			leftOuterJoins.add(joinElementoCfUdocEnUiDeposito);
		}

		if (generateLeftOuterTableUnidadDoc) {
			JoinDefinition joinElementoCfUnidadDocumental = new JoinDefinition(
					columnaIdElementoCf, UnidadDocumentalDBEntityImpl.CAMPO_ID);
			leftOuterJoins.add(joinElementoCfUnidadDocumental);
		}

		StringBuffer tabla = new StringBuffer();
		TableDef tablaQuery = null;

		if (!innerJoins.isEmpty()) {
			tabla.append(DBUtils.generateInnerJoinCondition(tablaPrincipal,
					(JoinDefinition[]) innerJoins
							.toArray(new JoinDefinition[] {})));
			if (!leftOuterJoins.isEmpty()) {
				tabla.append(DBUtils
						.generateLeftOuterJoinChainedCondition((JoinDefinition[]) leftOuterJoins
								.toArray(new JoinDefinition[] {})));
			}

			tablaQuery = new TableDef(tabla.toString(), Constants.STRING_EMPTY);
		} else {
			if (!leftOuterJoins.isEmpty()) {
				tabla.append(DBUtils.generateLeftOuterJoinCondition(
						tablaPrincipal, (JoinDefinition[]) leftOuterJoins
								.toArray(new JoinDefinition[] {})));

				tablaQuery = new TableDef(tabla.toString(),
						Constants.STRING_EMPTY);
			} else {
				tablaQuery = tablaPrincipal;
			}
		}

		// Establecer las columnas a completar
		DbColumnDef[] columnasFullQuery = (DbColumnDef[]) columnas
				.toArray(new DbColumnDef[] {});
		busquedaFondosQueryVO.setColsFullQueryFill(columnasFullQuery);

		// Establecer todas las columnas a generar en la query de Ids
		DbColumnDef[] totalCols = addOrderColumns(columnasFullQuery,
				orderColumn);
		busquedaFondosQueryVO.setColsFullQuery(totalCols);

		busquedaFondosQueryVO.setTablesFullQuery(tablaQuery.getDeclaration());

		// Establecer el order by
		if (orderColumn.getName().equals(
				FechaDBEntityImpl.FECHA_INICIAL_COLUMN_NAME)) {
			busquedaFondosQueryVO.setOrderByFullQuery(generateOrderByFullQuery(
					busquedaElementosVO, aliasFechaInicial));
		} else if (orderColumn.getName().equals(
				FechaDBEntityImpl.FECHA_FINAL_COLUMN_NAME)) {
			busquedaFondosQueryVO.setOrderByFullQuery(generateOrderByFullQuery(
					busquedaElementosVO, aliasFechaFinal));
		} else {
			busquedaFondosQueryVO.setOrderByFullQuery(generateOrderByFullQuery(
					busquedaElementosVO, aliasTablaPrincipal));
		}
	}

	public static BusquedaFondosQueryVO getQueryElementos(DbConnection conn,
			String[] idsElementosCf, String[] signaturas,
			BusquedaElementosVO busquedaElementosVO, Busqueda busqueda,
			String aliasTablaPrincipal, boolean eliminarElementosBloqueados,
			boolean obtenerPartes) {

		StringBuffer where = new StringBuffer();
		List condiciones = new ArrayList();
		BusquedaFondosQueryVO busquedaFondosQueryVO = new BusquedaFondosQueryVO();

		if (!ListUtils.isEmpty(busquedaElementosVO.getCamposRellenos())
				&& ArrayUtils.isEmpty(idsElementosCf)
				&& ArrayUtils.isEmpty(signaturas)) {
			Iterator it = busquedaElementosVO.getCamposRellenos().iterator();
			ConfiguracionBusquedas configuracionBusquedas = ConfiguracionBusquedasFactory
					.getConfiguracionBusquedas();

			List ltCamposRellenosElementoCf = new ArrayList();
			List ltCamposRellenosNoElementoCf = new ArrayList();
			List ltCamposGenericos = new ArrayList();

			while (it.hasNext()) {
				String key = (String) it.next();
				if (ArrayUtils.contains(camposElementoCf, key)) {
					ltCamposRellenosElementoCf.add(key);
				} else if (ArrayUtils.contains(camposNoElementoCf, key)) {
					ltCamposRellenosNoElementoCf.add(key);
				} else if (ArrayUtils.contains(camposGenericos, key)) {
					ltCamposGenericos.add(key);
				}
			}

			condiciones.add(BusquedasGeneratorHelper
					.componerCondicionesElementoCf(configuracionBusquedas,
							ltCamposRellenosElementoCf, conn, busqueda,
							busquedaElementosVO, busquedaFondosQueryVO,
							aliasTablaPrincipal));
			condiciones.add(BusquedasGeneratorHelper
					.componerCondicionesNoElementoCfGenericos(
							configuracionBusquedas,
							ltCamposRellenosNoElementoCf, ltCamposGenericos,
							conn, busqueda, busquedaElementosVO,
							aliasTablaPrincipal, eliminarElementosBloqueados));

			DBUtils.addPermissionsCheckingClauses(
					busquedaElementosVO.getServiceClient(),
					where,
					getCustomizedField(
							ElementoCuadroClasificacionDBEntityImplBase.NIVEL_ACCESO_FIELD,
							aliasTablaPrincipal),
					getCustomizedField(
							ElementoCuadroClasificacionDBEntityImplBase.ARCHIVO_FIELD,
							aliasTablaPrincipal),
					getCustomizedField(
							ElementoCuadroClasificacionDBEntityImplBase.LISTA_ACCESO_FIELD,
							aliasTablaPrincipal));
		} else {

			if (ArrayUtils.isNotEmpty(idsElementosCf)) {
				condiciones.add(componerCondicionElementoCfIdsElementos(
						idsElementosCf, aliasTablaPrincipal));
			}

			if (ArrayUtils.isNotEmpty(signaturas)) {
				condiciones.add(componerCondicionSignaturasIds(signaturas));
			}
		}

		where.append(DBUtils.getCondition(where.toString())).append(
				BusquedasGeneratorHelper.joinConditions(condiciones,
						DBUtils.AND));

		// Establecer el where
		busquedaFondosQueryVO.setWhere(where.toString());

		// Establecer las columnas por las que se busca
		completeOrderColsTablesForIds(busquedaFondosQueryVO,
				busquedaElementosVO, obtenerPartes, aliasTablaPrincipal);
		completeOrderColsTablesForFullQuery(busquedaFondosQueryVO, busqueda,
				busquedaElementosVO, obtenerPartes, aliasTablaPrincipal);

		return busquedaFondosQueryVO;
	}
}
