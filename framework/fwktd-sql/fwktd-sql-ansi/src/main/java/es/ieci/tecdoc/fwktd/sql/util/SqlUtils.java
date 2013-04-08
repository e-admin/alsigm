/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.util;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import es.ieci.tecdoc.fwktd.sql.node.Types;
import es.ieci.tecdoc.fwktd.sql.node.expression.BinaryExpression;
import es.ieci.tecdoc.fwktd.sql.node.expression.IExpression;
import es.ieci.tecdoc.fwktd.sql.node.expression.JdbcParameter;
import es.ieci.tecdoc.fwktd.sql.node.expression.Parenthesis;
import es.ieci.tecdoc.fwktd.sql.node.expression.operators.arithmetic.Addition;
import es.ieci.tecdoc.fwktd.sql.node.expression.operators.arithmetic.Subtraction;
import es.ieci.tecdoc.fwktd.sql.node.expression.operators.relational.EqualsTo;
import es.ieci.tecdoc.fwktd.sql.node.expression.operators.relational.ExpressionList;
import es.ieci.tecdoc.fwktd.sql.node.expression.operators.relational.GreaterOrEqualsThan;
import es.ieci.tecdoc.fwktd.sql.node.expression.operators.relational.GreaterThan;
import es.ieci.tecdoc.fwktd.sql.node.expression.operators.relational.IItemsList;
import es.ieci.tecdoc.fwktd.sql.node.expression.operators.relational.MinorOrEqualsThan;
import es.ieci.tecdoc.fwktd.sql.node.expression.operators.relational.MinorThan;
import es.ieci.tecdoc.fwktd.sql.node.expression.operators.relational.NotEqualsTo;
import es.ieci.tecdoc.fwktd.sql.node.schema.Column;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.ITableReference;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.JoinSpecification;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.JoinedTable;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.JoinedTable.JoinType;

/**
 * Utilidades para el manejo de clases del package sql
 * 
 * @author IECISA
 */
public class SqlUtils {

	/** Constante para las comillas dobles */
	public static final String DOUBLE_QUOTE = "\"";

	/** Macro separador de linea */
	public static final String NEWLINE = System.getProperty("line.separator");

	public static String orderByToString(List<? extends Object> orderByElements) {
		return getFormatedList(orderByElements, "ORDER BY");
	}

	public static String getFormatedList(List<? extends Object> aList,
			String anExpression) {
		return getFormatedList(aList, anExpression, true, false);
	}

	public static String getFormatedList(List<? extends Object> aList,
			String anExpression, boolean anUseComma, boolean anUseBrackets) {
		String sql = getStringList(aList, anUseComma, anUseBrackets);

		if (sql.length() > 0) {
			if (anExpression.length() > 0) {
				sql = " " + anExpression + " " + sql;
			} else {
				sql = " " + sql;
			}
		}

		return sql;
	}

	/**
	 * List the toString out put of the objects in the List comma separated. If
	 * the List is null or empty an empty string is returned.
	 * 
	 * The same as getStringList(list, true, false)
	 * 
	 * @see #getStringList(List, boolean, boolean)
	 * @param aList
	 *            list of objects with toString methods
	 * @return comma separated list of the elements in the list
	 */
	public static String getStringList(List<? extends Object> aList) {
		return getStringList(aList, true, false);
	}

	/**
	 * List the toString out put of the objects in the List that can be comma
	 * separated. If the List is null or empty an empty string is returned.
	 * 
	 * @param aList
	 *            list of objects with toString methods
	 * @param anUseComma
	 *            true if the list has to be comma separated
	 * @param anUseBrackets
	 *            true if the list has to be enclosed in brackets
	 * @return comma separated list of the elements in the list
	 */
	public static String getStringList(List<? extends Object> aList,
			boolean anUseComma, boolean anUseBrackets) {
		String ans = "";
		String comma = ",";
		if (!anUseComma) {
			comma = "";
		}
		if (aList != null) {
			if (anUseBrackets) {
				ans += "(";
			}

			for (int i = 0; i < aList.size(); i++) {
				ans += "" + aList.get(i)
						+ ((i < aList.size() - 1) ? comma + " " : "");
			}

			if (anUseBrackets) {
				ans += ")";
			}
		}

		return ans;
	}

	/**
	 * Método que retorna una cadena entre comillas dobles
	 * 
	 * @param aName
	 * @return Cadena entre comillas dobles
	 */
	public static String doubleQuotes(String aName) {

		return DOUBLE_QUOTE + aName + DOUBLE_QUOTE;
	}

	/**
	 * Método que retorna un nombre entre comillas simples
	 * 
	 * @param aName
	 * @return
	 */
	public static String quote(String aName) {

		return org.springframework.util.StringUtils.quote(aName);
	}

	/**
	 * Combina dos <code>IExpression</code> de manera segura. Si alguna de las
	 * dos es <code>null</code> el resultado es la que no es <code>null</code>
	 * 
	 * @param aLeftExpression
	 *            expresión de la izquieda
	 * @param operator
	 *            Operador
	 * @param aRightExpression
	 *            expresión de la derecha
	 * @param aParenthesis
	 *            Flag que indica si la condición resultado debe estar entre
	 *            paréntesis
	 */
	@SuppressWarnings("unchecked")
	public static IExpression combineExpressions(IExpression aLeftExpression,
			Class<? extends BinaryExpression> anExpressionType,
			IExpression aRightExpression, boolean aParenthesis) {
		/*
		 * Si alguna de las expresiones es null retornamos la que no lo es.
		 * Puede ser que las dos sean null, en cuyo caso el resultado también es
		 * null
		 */
		if (aLeftExpression == null) {
			return aRightExpression;
		} else if (aRightExpression == null) {
			return aLeftExpression;
		}

		Assert
				.isTrue(anExpressionType.getClass().isInstance(
						BinaryExpression.class),
						"Sólo se pueden utilizar expresiones binarias para combinar expresiones");
		IExpression expression = null;

		Class[] constructorArgs = new Class[] { IExpression.class,
				IExpression.class };

		try {
			Constructor<?> constructor = anExpressionType
					.getConstructor(constructorArgs);
			expression = (IExpression) constructor
					.newInstance(new IExpression[] { aLeftExpression,
							aRightExpression });
		} catch (Exception e) {
			logger.error("No se ha podido instanciar la clase de tipo {}",
					anExpressionType.getName());
		}

		if (aParenthesis) {
			expression = new Parenthesis(expression);
		}

		return expression;
	}

	public static ITableReference createJoinedTable(ITableReference aLeftTable,
			ITableReference aRightTable, JoinType aType,
			JoinSpecification aCondition) {

		if (null != aLeftTable && null != aRightTable) {
			JoinedTable joinedTable = new JoinedTable(aLeftTable, aRightTable);
			joinedTable.setJoinType(aType);
			joinedTable.setJoinSpecification(aCondition);

			return joinedTable;
		} else if (aLeftTable != null) {
			return aLeftTable;
		} else if (aRightTable != null) {
			return aRightTable;
		}

		return null;
	}

	public static IExpression createBinaryCondition(
			IExpression aLeftExpression, Types.operator anOperator,
			IExpression aRightExpression) {
		IExpression condition = null;
		switch (anOperator) {
		case distinct:
			condition = new NotEqualsTo(aLeftExpression, aRightExpression);
			break;
		case greater:
			condition = new GreaterThan(aLeftExpression, aRightExpression);
			break;
		case greaterOrEqual:
			condition = new GreaterOrEqualsThan(aLeftExpression,
					aRightExpression);
			break;
		case equal:
			condition = new EqualsTo(aLeftExpression, aRightExpression);
			break;
		case minor:
			condition = new MinorThan(aLeftExpression, aRightExpression);
			break;
		case minorOrEqual:
			condition = new MinorOrEqualsThan(aLeftExpression, aRightExpression);
			break;
		case minus:
			condition = new Subtraction(aLeftExpression, aRightExpression);
			break;
		case plus:
			condition = new Addition(aLeftExpression, aRightExpression);
			break;
		}

		return condition;
	}

	/**
	 * Devuelve una instancia de <code>ExpressionList</code> con tantas
	 * expresiones de tipo <code>JdbcParameter</code> como columnas se vayan a
	 * insertar.
	 * 
	 * @param aFields
	 * @return
	 */
	public static IItemsList adaptValues(String[] aFields) {
		ExpressionList values = new ExpressionList();
		for (int i = 0; i < aFields.length; i++) {
			values.addExpression(new JdbcParameter());
		}
		return values;
	}

	/**
	 * Devuelve una <code>List</code> de <code>Column</code> creadas a partir de
	 * las cadenas de caracteres recibidas en <code>fields</code>. Para cada
	 * entrada del <code>array</code> se devuelve una columna.
	 * 
	 * @param aFields
	 * @return
	 */
	public static List<Column> adaptFields(String[] aFields) {
		List<Column> columns = new ArrayList<Column>();
		for (int i = 0; i < aFields.length; i++) {
			String col = aFields[i];
			columns.add(new Column(col));
		}

		return columns;
	}

	/**
	 * Mete entre paréntesis una expresión. Si ya lo está retorna la propia
	 * expresión.
	 * 
	 * @param anExpression
	 * @return expresión entre paréntesis
	 */
	public static Parenthesis roundWithParens(IExpression anExpression) {

		if (anExpression instanceof Parenthesis) {
			return (Parenthesis) anExpression;
		}

		return new Parenthesis(anExpression);
	}

	// Members

	protected static final Logger logger = LoggerFactory
			.getLogger(SqlUtils.class);
}
