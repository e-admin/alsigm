/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.renderer;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import es.ieci.tecdoc.fwktd.sql.node.expression.DateValue;
import es.ieci.tecdoc.fwktd.sql.node.expression.DoubleValue;
import es.ieci.tecdoc.fwktd.sql.node.expression.LongValue;
import es.ieci.tecdoc.fwktd.sql.node.expression.SequenceNextValue;
import es.ieci.tecdoc.fwktd.sql.node.expression.StringValue;
import es.ieci.tecdoc.fwktd.sql.node.expression.TimestampValue;
import es.ieci.tecdoc.fwktd.sql.node.expression.functions.agregate.CountFunction;
import es.ieci.tecdoc.fwktd.sql.node.expression.functions.agregate.MinFunction;
import es.ieci.tecdoc.fwktd.sql.node.expression.functions.cast.ToCharacterFunction;
import es.ieci.tecdoc.fwktd.sql.node.expression.functions.cast.ToDateFunction;
import es.ieci.tecdoc.fwktd.sql.node.expression.functions.cast.ToNumericFunction;
import es.ieci.tecdoc.fwktd.sql.node.expression.functions.character.ConcatFunction;
import es.ieci.tecdoc.fwktd.sql.node.expression.functions.date.AddMonthsFunction;

@RunWith(BlockJUnit4ClassRunner.class)
public class ExpressionRendererTest {

	private ExpressionRenderer expressionRenderer;

	private TableReferenceRenderer tableReferenceRenderer;

	private SelectBodyRenderer selectBodyRenderer;

	@Before
	public void setUp() throws Exception {

		// Damos de alta todos los visitor y los relacionamos
		expressionRenderer = new OracleExpressionRenderer();
		tableReferenceRenderer = new OracleTableReferenceRenderer();
		selectBodyRenderer = new OracleSelectBodyRenderer();

		expressionRenderer.setSelectBodyRenderer(selectBodyRenderer);
		expressionRenderer.setTableReferenceRenderer(tableReferenceRenderer);
		tableReferenceRenderer.setSelectBodyRenderer(selectBodyRenderer);
		selectBodyRenderer.setExpressionRenderer(expressionRenderer);
		selectBodyRenderer.setTableReferenceRenderer(tableReferenceRenderer);
	}

	@Test
	public void testVisitSequenceNextValue() {

		SequenceNextValue sNV = new SequenceNextValue("anyGivenSchema",
				"anyGivenSequence");

		expressionRenderer.visit(sNV);
		assertNotNull(sNV.getSqlString());
	}

	@Test
	public void testVisitStringValue() {
		StringValue value = new StringValue("String con ' simple");
		expressionRenderer.visit(value);
		assertNotNull(value.getSqlString());
	}

	@Test
	public void testVisitStringValue2() {
		StringValue value = new StringValue("'Hola a todos'", true);
		expressionRenderer.visit(value);
		assertNotNull(value.getSqlString());
	}

	@Test
	public void testVisitLongValue() {
		LongValue value = new LongValue(122345L);
		expressionRenderer.visit(value);
		assertNotNull(value.getSqlString());
	}

	@Test
	public void testVisitDoubleValue() {
		DoubleValue value = new DoubleValue(122345.3434D);
		expressionRenderer.visit(value);
		assertNotNull(value.getSqlString());
	}

	@Test
	public void testVisitDateValue() {
		DateValue value = new DateValue("2008-01-01");
		expressionRenderer.visit(value);
		assertNotNull(value.getSqlString());
	}

	@Test
	public void testVisitTimeStampValue() {
		TimestampValue value = new TimestampValue("2008-01-01 23:23:12");
		expressionRenderer.visit(value);
		assertNotNull(value.getSqlString());
	}

	@Test
	public void testVisitMinFunction() {
		DateValue value = new DateValue("2008-01-01");
		MinFunction function = new MinFunction(value);
		expressionRenderer.visit(function);
		assertNotNull(function.getSqlString());
	}

	@Test
	public void testVisitCountFunction() {
		CountFunction function = new CountFunction();
		expressionRenderer.visit(function);
		assertNotNull(function.getSqlString());
	}

	@Test
	public void testVisitCountFunction2() {
		DateValue value = new DateValue("2008-01-01");
		CountFunction function = new CountFunction(value);
		expressionRenderer.visit(function);
		assertNotNull(function.getSqlString());
	}

	@Test
	public void testVisitToDateFunction() {
		StringValue value = new StringValue("2008-01-01");
		ToDateFunction function = new ToDateFunction(value, "yyyy-MM-dd");
		expressionRenderer.visit(function);
		assertNotNull(function.getSqlString());
	}

	@Test
	public void testVisitToNumberFunction() {
		StringValue value = new StringValue("233443");

		ToNumericFunction function = new ToNumericFunction(value,
				"###############.##########");
		expressionRenderer.visit(function);
		assertNotNull(function.getSqlString());
	}

	@Test
	public void testVisitToNumberFunction2() {
		LongValue value = new LongValue("233443");
		ToNumericFunction function = new ToNumericFunction(value);
		expressionRenderer.visit(function);
		assertNotNull(function.getSqlString());
	}

	@Test
	public void testVisitToNumberFunction3() {
		DateValue value = new DateValue("2008-12-23");
		ToNumericFunction function = new ToNumericFunction(value);
		expressionRenderer.visit(function);
		assertNotNull(function.getSqlString());
	}

	@Test
	public void testVisitToCharacterFunction() {
		DateValue value = new DateValue("2008-12-23");
		ToCharacterFunction function = new ToCharacterFunction(value);
		expressionRenderer.visit(function);
		assertNotNull(function.getSqlString());

		ToCharacterFunction function2 = new ToCharacterFunction(value,
				"yyyyMMdd");
		expressionRenderer.visit(function2);
		assertNotNull(function2.getSqlString());
	}

	@Test
	public void testVisitConcatFunction() {
		StringValue value1 = new StringValue("String1 con ' simple");
		StringValue value2 = new StringValue("String2 con ' simple");
		ConcatFunction concat = new ConcatFunction(value1, value2);
		expressionRenderer.visit(concat);
		assertNotNull(concat.getSqlString());
	}

	@Test
	public void testVisitAddMonthsFunction() {
		DateValue value = new DateValue("1998-01-01");
		AddMonthsFunction function = new AddMonthsFunction(value,
				new LongValue(new Long(5)));
		expressionRenderer.visit(function);
		assertNotNull(function.getSqlString());
	}
}
