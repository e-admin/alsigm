/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.renderer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

import es.ieci.tecdoc.fwktd.sql.node.expression.AllComparisonExpression;
import es.ieci.tecdoc.fwktd.sql.node.expression.AnyComparisonExpression;
import es.ieci.tecdoc.fwktd.sql.node.expression.BinaryExpression;
import es.ieci.tecdoc.fwktd.sql.node.expression.CaseExpression;
import es.ieci.tecdoc.fwktd.sql.node.expression.DateValue;
import es.ieci.tecdoc.fwktd.sql.node.expression.DoubleValue;
import es.ieci.tecdoc.fwktd.sql.node.expression.IExpression;
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
import es.ieci.tecdoc.fwktd.sql.node.expression.operators.relational.ExpressionList;
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
import es.ieci.tecdoc.fwktd.sql.node.visitor.ExpressionVisitor;
import es.ieci.tecdoc.fwktd.sql.node.visitor.ItemsListVisitor;
import es.ieci.tecdoc.fwktd.sql.util.SqlUtils;

/**
 * Renderer SQL para expresiones.
 */
public class ExpressionRenderer implements ExpressionVisitor, ItemsListVisitor {

	public void visit(Addition anAddition) {

		visitBinaryExpression(anAddition, anAddition.getOperator());
	}

	public void visit(AndExpression anAndExpression) {

		visitBinaryExpression(anAndExpression, anAndExpression.getOperator());
	}

	public void visit(Between aBetween) {

		StringBuffer buffer = new StringBuffer();

		aBetween.getLeftExpression().accept(this);
		buffer.append(aBetween.getLeftExpression().getSqlString());

		if (aBetween.isNot()) {
			buffer.append(" NOT");
		}

		buffer.append(" BETWEEN ");

		aBetween.getBetweenExpressionStart().accept(this);
		buffer.append(aBetween.getBetweenExpressionStart().getSqlString());

		buffer.append(" AND ");

		aBetween.getBetweenExpressionEnd().accept(this);
		buffer.append(aBetween.getBetweenExpressionEnd().getSqlString());

		aBetween.setSqlString(buffer.toString());
	}

	public void visit(Division aDivision) {

		visitBinaryExpression(aDivision, aDivision.getOperator());
	}

	public void visit(EqualsTo anEqualsTo) {

		visitBinaryExpression(anEqualsTo, anEqualsTo.getOperator());
	}

	public void visit(GreaterThan aGreaterThan) {

		visitBinaryExpression(aGreaterThan, aGreaterThan.getOperator());
	}

	public void visit(GreaterOrEqualsThan aGreaterThanEquals) {

		visitBinaryExpression(aGreaterThanEquals, aGreaterThanEquals
				.getOperator());
	}

	public void visit(InExpression anInExpression) {

		StringBuffer buffer = new StringBuffer();

		anInExpression.getLeftExpression().accept(this);
		buffer.append(anInExpression.getLeftExpression().getSqlString());

		if (anInExpression.isNot()) {
			buffer.append(" NOT");
		}
		buffer.append(" IN ");

		anInExpression.getItemsList().accept(this);
		buffer.append(anInExpression.getItemsList().getSqlString());

		anInExpression.setSqlString(buffer.toString());
	}

	public void visit(InverseExpression anInverseExpression) {

		anInverseExpression.getExpression().accept(this);
		anInverseExpression.setSqlString("-"
				+ anInverseExpression.getExpression().getSqlString());
	}

	public void visit(IsNullExpression anIsNullExpression) {

		StringBuffer buffer = new StringBuffer();

		anIsNullExpression.getLeftExpression().accept(this);
		buffer.append(anIsNullExpression.getLeftExpression().getSqlString());

		if (anIsNullExpression.isNot()) {
			buffer.append(" IS NOT NULL");
		} else {
			buffer.append(" IS NULL");
		}

		anIsNullExpression.setSqlString(buffer.toString());
	}

	public void visit(JdbcParameter aJdbcParameter) {

		aJdbcParameter.setSqlString("?");
	}

	public void visit(LikeExpression aLikeExpression) {
		visitBinaryExpression(aLikeExpression, aLikeExpression.getOperator());
	}

	public void visit(ExistsExpression anExistsExpression) {

		StringBuffer buffer = new StringBuffer();

		if (anExistsExpression.isNot()) {
			buffer.append(" NOT EXISTS");
		} else {
			buffer.append(" EXISTS");
		}

		anExistsExpression.getRightExpression().accept(this);
		buffer.append(anExistsExpression.getRightExpression().getSqlString());
		anExistsExpression.setSqlString(buffer.toString());
	}

	public void visit(MinorThan aMinorThan) {

		visitBinaryExpression(aMinorThan, aMinorThan.getOperator());
	}

	public void visit(MinorOrEqualsThan aMinorThanEquals) {

		visitBinaryExpression(aMinorThanEquals, aMinorThanEquals.getOperator());
	}

	public void visit(Multiplication aMultiplication) {

		visitBinaryExpression(aMultiplication, aMultiplication.getOperator());
	}

	public void visit(NotEqualsTo aNotEqualsTo) {

		visitBinaryExpression(aNotEqualsTo, aNotEqualsTo.getOperator());
	}

	public void visit(NullValue aNullValue) {

		aNullValue.setSqlString("NULL");
	}

	public void visit(OrExpression anOrExpression) {

		visitBinaryExpression(anOrExpression, anOrExpression.getOperator());
	}

	public void visit(Parenthesis aParenthesis) {

		aParenthesis.getExpression().accept(this);
		aParenthesis.setSqlString("("
				+ aParenthesis.getExpression().getSqlString() + ")");
	}

	public void visit(Subtraction aSubtraction) {

		visitBinaryExpression(aSubtraction, aSubtraction.getOperator());
	}

	private void visitBinaryExpression(BinaryExpression aBinaryExpression,
			String anOperator) {

		aBinaryExpression.getLeftExpression().accept(this);
		aBinaryExpression.getRightExpression().accept(this);

		StringBuffer buffer = new StringBuffer();

		buffer.append(aBinaryExpression.getLeftExpression().getSqlString())
				.append(" ").append(anOperator).append(" ").append(
						aBinaryExpression.getRightExpression().getSqlString());

		aBinaryExpression.setSqlString(buffer.toString());
	}

	public void visit(SubSelect aSubSelect) {

		aSubSelect.getSelectBody().accept(getSelectBodyRenderer());

		StringBuffer buffer = new StringBuffer();

		buffer.append("(").append(aSubSelect.getSelectBody().getSqlString())
				.append(")");

		aSubSelect.setSqlString(buffer.toString());
	}

	public void visit(Column aColumn) {

		StringBuffer buffer = new StringBuffer();

		if (aColumn.getTable() != null) {
			aColumn.getTable().accept(getTableReferenceRenderer());
			if (StringUtils.isNotEmpty(aColumn.getTable().getReferenceName())) {
				buffer.append(aColumn.getTable().getReferenceName() + ".");
			}
		}
		buffer.append(decorate(aColumn.getColumnName()));
		aColumn.setSqlString(buffer.toString());
	}

	/**
	 * Decorador aplicable a cualquier expresión, en el standard no se decora
	 * 
	 * @param aName
	 *            Nombre a decorar
	 * @return por defecto no se aplican decoradores
	 */
	protected String decorate(String aName) {
		return aName;
	}

	public void visit(ExpressionList anExpressionList) {

		StringBuffer buffer = new StringBuffer();

		buffer.append("(");

		for (Iterator<IExpression> iter = anExpressionList.getExpressions()
				.iterator(); iter.hasNext();) {

			IExpression expression = (IExpression) iter.next();
			expression.accept(this);
			buffer.append(expression.getSqlString());

			if (iter.hasNext())
				buffer.append(", ");
		}
		buffer.append(")");

		anExpressionList.setSqlString(buffer.toString());
	}

	public void visit(StringValue aStringValue) {

		if (aStringValue.isQuoted()) {
			aStringValue.setSqlString(aStringValue.getValue());

		} else {
			String transformedValue = StringUtils.replace(aStringValue
					.getValue(), "'", "''");
			aStringValue.setSqlString(SqlUtils.quote(transformedValue));
		}
	}

	public void visit(DoubleValue aDoubleValue) {

		aDoubleValue.setSqlString(Double.toString(aDoubleValue.getValue()));
	}

	public void visit(LongValue aLongValue) {

		aLongValue.setSqlString(Long.toString(aLongValue.getValue()));
	}

	public void visit(DateValue aDateValue) {

		StringBuffer buffer = new StringBuffer();

		buffer.append("{d '" + aDateValue.getValue().toString() + "'}");

		aDateValue.setSqlString(buffer.toString());
	}

	public void visit(TimestampValue aTimestampValue) {

		StringBuffer buffer = new StringBuffer();

		buffer.append("{ts '" + aTimestampValue.getValue().toString() + "'}");

		aTimestampValue.setSqlString(buffer.toString());
	}

	public void visit(TimeValue aTimeValue) {

		StringBuffer buffer = new StringBuffer();

		buffer.append("{t '" + aTimeValue.getValue().toString() + "'}");

		aTimeValue.setSqlString(buffer.toString());
	}

	public void visit(CaseExpression aCaseExpression) {

		StringBuffer buffer = new StringBuffer();

		buffer.append("CASE ");
		IExpression switchExp = aCaseExpression.getSwitchExpression();
		if (switchExp != null) {
			switchExp.accept(this);
			buffer.append(switchExp.getSqlString());
		}

		List<IExpression> clauses = aCaseExpression.getWhenClauses();
		for (IExpression exp : clauses) {
			exp.accept(this);
			buffer.append(exp.getSqlString());
		}

		IExpression elseExp = aCaseExpression.getElseExpression();
		if (elseExp != null) {
			elseExp.accept(this);
			buffer.append(elseExp.getSqlString());
		}

		buffer.append(" END");

		aCaseExpression.setSqlString(buffer.toString());
	}

	public void visit(WhenClause aWhenClause) {

		StringBuffer buffer = new StringBuffer();

		buffer.append(" WHEN ");
		aWhenClause.getWhenExpression().accept(this);
		buffer.append(aWhenClause.getWhenExpression().getSqlString());

		buffer.append(" THEN ");
		aWhenClause.getThenExpression().accept(this);
		buffer.append(aWhenClause.getThenExpression().getSqlString());

		aWhenClause.setSqlString(buffer.toString());
	}

	public void visit(AllComparisonExpression anAllComparisonExpression) {

		StringBuffer buffer = new StringBuffer(" ALL ");

		anAllComparisonExpression.getSubSelect().accept(
				(ExpressionVisitor) this);
		buffer.append(anAllComparisonExpression.getSubSelect().getSqlString());

		anAllComparisonExpression.setSqlString(buffer.toString());
	}

	public void visit(AnyComparisonExpression anAnyComparisonExpression) {

		StringBuffer buffer = new StringBuffer(" ANY ");

		anAnyComparisonExpression.getSubSelect().accept(
				(ExpressionVisitor) this);
		buffer.append(anAnyComparisonExpression.getSubSelect().getSqlString());

		anAnyComparisonExpression.setSqlString(buffer.toString());
	}

	public void visit(UserFunction aFunction) {

		StringBuffer buffer = new StringBuffer();

		if (aFunction.isEscaped()) {
			buffer.append("{fn ");
		}

		buffer.append(aFunction.getName());
		if (aFunction.getParameters() == null) {
			buffer.append("()");
		} else {
			visit(aFunction.getParameters());
		}

		if (aFunction.isEscaped()) {
			buffer.append("}");
		}

		aFunction.setSqlString(buffer.toString());
	}

	public void visit(CastFunction aFunction) {
		StringBuffer buffer = new StringBuffer();

		if (!StringUtils.isEmpty(aFunction.getName())) {
			buffer.append(aFunction.getName());
			buffer.append("(");
		}
		aFunction.getExpression().accept((ExpressionVisitor) this);
		buffer.append(aFunction.getExpression().getSqlString());

		if (!StringUtils.isEmpty(aFunction.getName())) {
			// Aplicar la mascara de formato.
			String adaptedMask = adaptMask(aFunction.getMask());
			if (!StringUtils.isEmpty(adaptedMask)) {
				buffer.append(",'").append(adaptedMask).append("'");
			}
			buffer.append(")");
		}

		aFunction.setSqlString(buffer.toString());
	}

	public void visit(ToDateFunction aFunction) {
		this.visit((CastFunction) aFunction);
	}

	public void visit(ToCharacterFunction aFunction) {
		this.visit((CastFunction) aFunction);
	}

	public void visit(ToNumericFunction aFunction) {
		this.visit((CastFunction) aFunction);
	}

	public void visit(AggregateFunction aFunction) {
		Assert.notNull(aFunction);
		Assert.notNull(aFunction.getName());
		StringBuffer buffer = new StringBuffer(aFunction.getName());

		buffer.append("(");
		aFunction.getExpression().accept(this);
		buffer.append(aFunction.getExpression().getSqlString());
		buffer.append(")");

		aFunction.setSqlString(buffer.toString());
	}

	public void visit(AvgFunction aFunction) {
		aFunction.setName("AVG");
		this.visit((AggregateFunction) aFunction);
	}

	public void visit(CountFunction aFunction) {

		Assert.notNull(aFunction);

		StringBuffer buffer = new StringBuffer("COUNT(");
		if (aFunction.isAllColumns()) {
			buffer.append("*");
		} else {
			aFunction.getExpression().accept(this);
			buffer.append("DISTINCT ").append(
					aFunction.getExpression().getSqlString());
		}

		buffer.append(")");

		aFunction.setSqlString(buffer.toString());
	}

	public void visit(MaxFunction aFunction) {
		aFunction.setName("MAX");
		this.visit((AggregateFunction) aFunction);
	}

	public void visit(MinFunction aFunction) {
		aFunction.setName("MIN");
		this.visit((AggregateFunction) aFunction);
	}

	public void visit(SumFunction aFunction) {
		aFunction.setName("SUM");
		this.visit((AggregateFunction) aFunction);
	}

	public void visit(ConcatFunction aFunction) {
		Assert.notNull(aFunction);
		aFunction.setName("CONCAT");

		StringBuffer buffer = new StringBuffer(aFunction.getName());
		buffer.append("(");
		aFunction.getFirstExpression().accept(this);
		buffer.append(aFunction.getFirstExpression().getSqlString());
		buffer.append(",");
		aFunction.getSecondExpression().accept(this);
		buffer.append(aFunction.getSecondExpression().getSqlString());
		buffer.append(")");
		aFunction.setSqlString(buffer.toString());
	}

	public void visit(AddMonthsFunction aFunction) {
		throw new UnsupportedOperationException(
				"La Función AddMonthsFunction no está soportada en el standard");
	}

	protected String adaptMask(String aJavaMask) {
		if (StringUtils.isEmpty(aJavaMask)) {
			return null;
		}
		String sqlMask = getCustomMaskMappings().get(aJavaMask);
		if (StringUtils.isEmpty(sqlMask)) {
			return getPredefMaskMappings().get(aJavaMask);
		}
		return sqlMask;
	}

	protected Map<String, String> getCustomMaskMappings() {
		if (customMaskMappings == null) {
			customMaskMappings = new HashMap<String, String>();
		}
		return customMaskMappings;
	}

	public void setCustomMaskMappings(Map<String, String> aCustomMaskMappings) {
		this.customMaskMappings = aCustomMaskMappings;
	}

	protected Map<String, String> getPredefMaskMappings() {
		if (predefMaskMappings == null) {
			predefMaskMappings = new HashMap<String, String>();
		}
		return predefMaskMappings;
	}

	public void setSelectBodyRenderer(SelectBodyRenderer aSelectBodyRenderer) {
		this.selectBodyRenderer = aSelectBodyRenderer;
	}

	public void setTableReferenceRenderer(
			TableReferenceRenderer aTableReferenceRenderer) {
		this.tableReferenceRenderer = aTableReferenceRenderer;
	}

	public void visit(SequenceNextValue aSequenceValue) {
		throw new UnsupportedOperationException(
				"El Standard ANSI SQL 92 no soporta secuencias");
	}

	public SelectBodyRenderer getSelectBodyRenderer() {
		return selectBodyRenderer;
	}

	public TableReferenceRenderer getTableReferenceRenderer() {
		return tableReferenceRenderer;
	}

	// Members

	/**
	 * Mapeos de mascaras java a mascaras SQL.
	 */
	protected Map<String, String> predefMaskMappings;

	/**
	 * Mapeos customizados de mascaras java a mascaras SQL.
	 */
	protected Map<String, String> customMaskMappings;

	/** Renderer para el cuerpo de las claúsulas SELECT */
	protected SelectBodyRenderer selectBodyRenderer;

	/** Renderer para tablas */
	protected TableReferenceRenderer tableReferenceRenderer;

}