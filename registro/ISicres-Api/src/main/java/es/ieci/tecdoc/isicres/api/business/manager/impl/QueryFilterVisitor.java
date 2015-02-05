package es.ieci.tecdoc.isicres.api.business.manager.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.CaseExpression;
import net.sf.jsqlparser.expression.DateValue;
import net.sf.jsqlparser.expression.DoubleValue;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.ExpressionVisitor;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.expression.InverseExpression;
import net.sf.jsqlparser.expression.JdbcParameter;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.NullValue;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.TimeValue;
import net.sf.jsqlparser.expression.TimestampValue;
import net.sf.jsqlparser.expression.WhenClause;
import net.sf.jsqlparser.expression.operators.arithmetic.Addition;
import net.sf.jsqlparser.expression.operators.arithmetic.Division;
import net.sf.jsqlparser.expression.operators.arithmetic.Multiplication;
import net.sf.jsqlparser.expression.operators.arithmetic.Subtraction;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.Between;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExistsExpression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.GreaterThan;
import net.sf.jsqlparser.expression.operators.relational.GreaterThanEquals;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.IsNullExpression;
import net.sf.jsqlparser.expression.operators.relational.ItemsListVisitor;
import net.sf.jsqlparser.expression.operators.relational.LikeExpression;
import net.sf.jsqlparser.expression.operators.relational.MinorThan;
import net.sf.jsqlparser.expression.operators.relational.MinorThanEquals;
import net.sf.jsqlparser.expression.operators.relational.NotEqualsTo;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.SelectVisitor;
import net.sf.jsqlparser.statement.select.SubSelect;
import net.sf.jsqlparser.statement.select.Union;

import com.ieci.tecdoc.common.isicres.AxSfQueryField;

/**
 * Implementación del patrón visitor que se encarga de recoger el WHERE de una
 * consulta SQL con el fin de extraer sus componentes y traducirlos a instancias
 * de <code>AxSfQueryField</code>.
 * 
 * @see AxSfQueryField
 * 
 * @author IECISA
 * 
 */
public class QueryFilterVisitor implements ExpressionVisitor, SelectVisitor,
		ItemsListVisitor {

	public void visit(NullValue aNullValue) {
		stack.push("NULL");
	}

	public void visit(Function aFunction) {
		throw new UnsupportedOperationException();
	}

	public void visit(InverseExpression anInverseExpression) {
		throw new UnsupportedOperationException();
	}

	public void visit(JdbcParameter aJdbcParameter) {
		throw new UnsupportedOperationException();
	}

	public void visit(DoubleValue aDoubleValue) {
		stack.push(new Double(aDoubleValue.getValue()));
	}

	public void visit(LongValue aLongValue) {
		stack.push(new Long(aLongValue.getValue()));
	}

	public void visit(DateValue aDateValue) {
		stack.push(aDateValue);
	}

	public void visit(TimeValue aTimeValue) {
		stack.push(aTimeValue.getValue());/* java.sql */
	}

	public void visit(TimestampValue aTimestampValue) {
		stack.push(aTimestampValue.getValue());/* java.sql */
	}

	public void visit(Parenthesis aParenthesis) {
	}

	public void visit(StringValue aStringValue) {
		stack.push(aStringValue);
	}

	public void visit(Addition anAddition) {
		throw new UnsupportedOperationException();
	}

	public void visit(Division aDivision) {
		throw new UnsupportedOperationException();
	}

	public void visit(Multiplication aMultiplication) {
		throw new UnsupportedOperationException();
	}

	public void visit(Subtraction aSubtraction) {
		throw new UnsupportedOperationException();
	}

	public void visit(AndExpression anAndExpression) {
		visitBinaryExpression(anAndExpression);

		AxSfQueryField leftExpression = (AxSfQueryField) stack.pop();
		leftExpression.setNexo("AND");
		queryFilters.add(leftExpression);
		queryFilters.add(stack.pop());
	}

	public void visit(OrExpression anOrExpression) {
		visitBinaryExpression(anOrExpression);
	}

	public void visit(Between aBetween) {
		aBetween.getLeftExpression().accept(this);
		aBetween.getBetweenExpressionStart().accept(this);
		aBetween.getBetweenExpressionEnd().accept(this);
	}

	public void visit(EqualsTo anEqualsTo) {
		visitBinaryExpression(anEqualsTo);

		AxSfQueryField field = new AxSfQueryField();
		field.setValue(stack.pop());
		field.setFldId((String) stack.pop());
		field.setOperator("=");

		stack.push(field);

	}

	public void visit(GreaterThan aGreaterThan) {
		visitBinaryExpression(aGreaterThan);
	}

	public void visit(GreaterThanEquals aGreaterThanEquals) {
		visitBinaryExpression(aGreaterThanEquals);
	}

	public void visit(InExpression anInExpression) {
		throw new UnsupportedOperationException();
	}

	public void visit(IsNullExpression anIsNullExpression) {
		anIsNullExpression.getLeftExpression().accept(this);
	}

	public void visit(LikeExpression aLikeExpression) {
		visitBinaryExpression(aLikeExpression);
	}

	public void visit(MinorThan aMinorThan) {
		visitBinaryExpression(aMinorThan);
	}

	public void visit(MinorThanEquals aMinorThanEquals) {
		visitBinaryExpression(aMinorThanEquals);
	}

	public void visit(NotEqualsTo aNotEqualsTo) {
		visitBinaryExpression(aNotEqualsTo);

		AxSfQueryField field = new AxSfQueryField();
		field.setFldId((String) stack.pop());
		field.setValue(stack.pop());
		field.setOperator("<>");

		stack.push(field);
	}

	public void visit(Column aColumn) {
		stack.push(aColumn.getWholeColumnName());
	}

	public void visit(SubSelect aSubSelect) {
		throw new UnsupportedOperationException();
	}

	public void visit(CaseExpression aCaseExpression) {
		throw new UnsupportedOperationException();
	}

	public void visit(WhenClause aWhenClause) {
		throw new UnsupportedOperationException();
	}

	public void visit(ExistsExpression anExistsExpression) {
		throw new UnsupportedOperationException();
	}

	public void visit(PlainSelect aPlainSelect) {
		if (aPlainSelect.getWhere() != null)
			aPlainSelect.getWhere().accept(this);
	}

	public void visit(Union anUnion) {
		// nothing to do
		throw new UnsupportedOperationException();
	}

	public void visit(ExpressionList anExpressionList) {
		for (Iterator iterator = anExpressionList.getExpressions().iterator(); iterator
				.hasNext();) {
			Expression expression = (Expression) iterator.next();
			expression.accept(this);
		}
	}

	public List getFilters() {
		return queryFilters;
	}

	/**
	 * Método que visita las dos expresiones que forman la expresión binaria que
	 * se recibe como parámetro.
	 * 
	 * @param binaryExpression
	 *            expresión a visitar
	 */
	protected void visitBinaryExpression(BinaryExpression binaryExpression) {
		binaryExpression.getLeftExpression().accept(this);
		binaryExpression.getRightExpression().accept(this);
	}

	// Members
	protected Stack stack = new Stack();

	protected List queryFilters = new ArrayList();
}
