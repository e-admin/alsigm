/*
 *
 */
package es.ieci.tecdoc.fwktd.sql.node.visitor;

import es.ieci.tecdoc.fwktd.sql.node.expression.AllComparisonExpression;
import es.ieci.tecdoc.fwktd.sql.node.expression.AnyComparisonExpression;
import es.ieci.tecdoc.fwktd.sql.node.expression.CaseExpression;
import es.ieci.tecdoc.fwktd.sql.node.expression.DateValue;
import es.ieci.tecdoc.fwktd.sql.node.expression.DoubleValue;
import es.ieci.tecdoc.fwktd.sql.node.expression.InverseExpression;
import es.ieci.tecdoc.fwktd.sql.node.expression.JdbcParameter;
import es.ieci.tecdoc.fwktd.sql.node.expression.LongValue;
import es.ieci.tecdoc.fwktd.sql.node.expression.NullValue;
import es.ieci.tecdoc.fwktd.sql.node.expression.Parenthesis;
import es.ieci.tecdoc.fwktd.sql.node.expression.SequenceNextValue;
import es.ieci.tecdoc.fwktd.sql.node.expression.StringValue;
import es.ieci.tecdoc.fwktd.sql.node.expression.TimeValue;
import es.ieci.tecdoc.fwktd.sql.node.expression.TimestampValue;
import es.ieci.tecdoc.fwktd.sql.node.expression.WhenClause;
import es.ieci.tecdoc.fwktd.sql.node.expression.functions.agregate.AggregateFunction;
import es.ieci.tecdoc.fwktd.sql.node.expression.functions.agregate.AvgFunction;
import es.ieci.tecdoc.fwktd.sql.node.expression.functions.agregate.CountFunction;
import es.ieci.tecdoc.fwktd.sql.node.expression.functions.agregate.MaxFunction;
import es.ieci.tecdoc.fwktd.sql.node.expression.functions.agregate.MinFunction;
import es.ieci.tecdoc.fwktd.sql.node.expression.functions.agregate.SumFunction;
import es.ieci.tecdoc.fwktd.sql.node.expression.functions.cast.CastFunction;
import es.ieci.tecdoc.fwktd.sql.node.expression.functions.cast.ToCharacterFunction;
import es.ieci.tecdoc.fwktd.sql.node.expression.functions.cast.ToDateFunction;
import es.ieci.tecdoc.fwktd.sql.node.expression.functions.cast.ToNumericFunction;
import es.ieci.tecdoc.fwktd.sql.node.expression.functions.character.ConcatFunction;
import es.ieci.tecdoc.fwktd.sql.node.expression.functions.date.AddMonthsFunction;
import es.ieci.tecdoc.fwktd.sql.node.expression.functions.user.UserFunction;
import es.ieci.tecdoc.fwktd.sql.node.expression.operators.arithmetic.Addition;
import es.ieci.tecdoc.fwktd.sql.node.expression.operators.arithmetic.Division;
import es.ieci.tecdoc.fwktd.sql.node.expression.operators.arithmetic.Multiplication;
import es.ieci.tecdoc.fwktd.sql.node.expression.operators.arithmetic.Subtraction;
import es.ieci.tecdoc.fwktd.sql.node.expression.operators.conditional.AndExpression;
import es.ieci.tecdoc.fwktd.sql.node.expression.operators.conditional.OrExpression;
import es.ieci.tecdoc.fwktd.sql.node.expression.operators.relational.Between;
import es.ieci.tecdoc.fwktd.sql.node.expression.operators.relational.EqualsTo;
import es.ieci.tecdoc.fwktd.sql.node.expression.operators.relational.ExistsExpression;
import es.ieci.tecdoc.fwktd.sql.node.expression.operators.relational.GreaterOrEqualsThan;
import es.ieci.tecdoc.fwktd.sql.node.expression.operators.relational.GreaterThan;
import es.ieci.tecdoc.fwktd.sql.node.expression.operators.relational.InExpression;
import es.ieci.tecdoc.fwktd.sql.node.expression.operators.relational.IsNullExpression;
import es.ieci.tecdoc.fwktd.sql.node.expression.operators.relational.LikeExpression;
import es.ieci.tecdoc.fwktd.sql.node.expression.operators.relational.MinorOrEqualsThan;
import es.ieci.tecdoc.fwktd.sql.node.expression.operators.relational.MinorThan;
import es.ieci.tecdoc.fwktd.sql.node.expression.operators.relational.NotEqualsTo;
import es.ieci.tecdoc.fwktd.sql.node.schema.Column;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.SubSelect;

/**
 * Visitor de expresiones SQL
 * 
 * @author IECISA
 */
public interface ExpressionVisitor {

	/**
	 * Visit para constantes NULL
	 * 
	 * @param aNullValue
	 */
	public void visit(NullValue aNullValue);

	/**
	 * Visit para funciones de conversión de tipos.
	 * 
	 * @param aFunction
	 *            Función de conversión
	 */
	public void visit(CastFunction aFunction);

	/**
	 * Visit para Conversión a tipo fecha
	 * 
	 * @param aFunction
	 *            Función de conversión a fecha
	 */
	public void visit(ToDateFunction aFunction);

	/**
	 * Visit para Conversión a tipo string
	 * 
	 * @param aFunction
	 *            Función de conversión a string
	 */
	public void visit(ToCharacterFunction aFunction);

	/**
	 * Visit para Conversión a tipo numérico
	 * 
	 * @param aFunction
	 *            Función de conversión a numero
	 */
	public void visit(ToNumericFunction aFunction);

	/**
	 * Visit para funciones de usuario
	 * 
	 * @param aFunction
	 *            Función de usuario
	 */
	public void visit(UserFunction aFunction);

	/**
	 * Visit para funciones de agregación.
	 * 
	 * @param aFunction
	 */
	public void visit(AggregateFunction aFunction);

	/**
	 * Visit para función Media
	 * 
	 * @param aFunction
	 */
	public void visit(AvgFunction aFunction);

	/**
	 * Visit para funcion Conteo
	 * 
	 * @param aFunction
	 */
	public void visit(CountFunction aFunction);

	/**
	 * Visit para función Máximo
	 * 
	 * @param aFunction
	 */
	public void visit(MaxFunction aFunction);

	/**
	 * Visit para función Mínimo
	 * 
	 * @param aFunction
	 */
	public void visit(MinFunction aFunction);

	/**
	 * Visit para función Suma
	 * 
	 * @param aFunction
	 */
	public void visit(SumFunction aFunction);

	/**
	 * 
	 * Visit para función Concatenación
	 * 
	 * @param aFunction
	 */
	public void visit(ConcatFunction aFunction);

	/**
	 * Visit para función Suma de meses
	 * 
	 * @param aFunction
	 */
	public void visit(AddMonthsFunction aFunction);

	public void visit(SequenceNextValue aSequenceValue);

	public void visit(InverseExpression anInverseExpression);

	public void visit(JdbcParameter aJdbcParameter);

	public void visit(DoubleValue aDoubleValue);

	public void visit(LongValue aLongValue);

	public void visit(DateValue aDateValue);

	public void visit(TimeValue aTimeValue);

	public void visit(TimestampValue aTimestampValue);

	public void visit(Parenthesis aParenthesis);

	public void visit(StringValue aStringValue);

	public void visit(Addition anAddition);

	public void visit(Division aDivision);

	public void visit(Multiplication aMultiplication);

	public void visit(Subtraction aSubtraction);

	public void visit(AndExpression anAndExpression);

	public void visit(OrExpression anOrExpression);

	public void visit(Between aBetween);

	public void visit(EqualsTo anEqualsTo);

	public void visit(GreaterThan aGreaterThan);

	public void visit(GreaterOrEqualsThan aGreaterThanEquals);

	public void visit(InExpression anInExpression);

	public void visit(IsNullExpression anIsNullExpression);

	public void visit(LikeExpression anLikeExpression);

	public void visit(MinorThan aMinorThan);

	public void visit(MinorOrEqualsThan aMinorThanEquals);

	public void visit(NotEqualsTo aNotEqualsTo);

	public void visit(Column aTableColumn);

	public void visit(SubSelect aSubSelect);

	public void visit(CaseExpression aCaseExpression);

	public void visit(WhenClause aWhenClause);

	public void visit(ExistsExpression anExistsExpression);

	public void visit(AllComparisonExpression anAllComparisonExpression);

	public void visit(AnyComparisonExpression anAnyComparisonExpression);
}
