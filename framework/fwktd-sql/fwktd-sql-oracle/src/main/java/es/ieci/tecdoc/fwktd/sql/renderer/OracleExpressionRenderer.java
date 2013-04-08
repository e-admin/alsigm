/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.renderer;

import java.beans.PropertyEditor;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.util.Assert;

import es.ieci.tecdoc.fwktd.sql.node.expression.DateValue;
import es.ieci.tecdoc.fwktd.sql.node.expression.SequenceNextValue;
import es.ieci.tecdoc.fwktd.sql.node.expression.TimeValue;
import es.ieci.tecdoc.fwktd.sql.node.expression.TimestampValue;
import es.ieci.tecdoc.fwktd.sql.node.expression.functions.cast.CastFunction;
import es.ieci.tecdoc.fwktd.sql.node.expression.functions.cast.ToCharacterFunction;
import es.ieci.tecdoc.fwktd.sql.node.expression.functions.cast.ToDateFunction;
import es.ieci.tecdoc.fwktd.sql.node.expression.functions.cast.ToNumericFunction;
import es.ieci.tecdoc.fwktd.sql.node.expression.functions.date.AddMonthsFunction;
import es.ieci.tecdoc.fwktd.sql.util.SqlUtils;

/**
 * <code>ExpressionRenderer</code> para Oracle
 * 
 */
public class OracleExpressionRenderer extends ExpressionRenderer {

	@Override
	public String decorate(String name) {
		return SqlUtils.doubleQuotes(name);
	}

	
	public void visit(DateValue dateValue) {
		StringBuffer buffer = new StringBuffer();
		PropertyEditor editor = new CustomDateEditor(new SimpleDateFormat(
				"yyyy-MM-dd"), false);
		editor.setValue(dateValue.getValue());
		buffer.append("TO_DATE('").append(editor.getAsText()).append(
				"','RRRR-MM-DD')");
		dateValue.setSqlString(buffer.toString());
	}

	public void visit(TimestampValue timestampValue) {
		StringBuffer buffer = new StringBuffer();
		PropertyEditor editor = new CustomDateEditor(new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss"), false);
		editor.setValue(timestampValue.getValue());
		buffer.append("TO_DATE('").append(editor.getAsText()).append(
				"','RRRR-MM-DD HH24:MI:SS')");
		timestampValue.setSqlString(buffer.toString());
	}

	public void visit(TimeValue timeValue) {
		StringBuffer buffer = new StringBuffer();
		PropertyEditor editor = new CustomDateEditor(new SimpleDateFormat(
				"HH:mm:ss"), false);
		editor.setValue(timeValue.getValue());
		buffer.append("TO_DATE('").append(editor.getAsText()).append(
				"','HH24:MI:SS')");
		timeValue.setSqlString(buffer.toString());
	}

	public void visit(ToDateFunction function) {
		function.setName(" TO_DATE");
		this.visit((CastFunction) function);
	}

	public void visit(ToCharacterFunction function) {
		function.setName(" TO_CHAR");
		this.visit((CastFunction) function);
	}

	public void visit(ToNumericFunction function) {
		function.setName(" TO_NUMBER");
		this.visit((CastFunction) function);
	}

	@Override
	public void visit(AddMonthsFunction function) {
		Assert.notNull(function);
		function.setName(" ADD_MONTHS");

		StringBuffer buffer = new StringBuffer(function.getName());
		buffer.append("(");
		function.getDateExpression().accept(this);
		buffer.append(function.getDateExpression().getSqlString());
		buffer.append(",");
		function.getUnits().accept(this);
		buffer.append(function.getUnits().getSqlString());
		buffer.append(")");
		function.setSqlString(buffer.toString());
	}

	public void visit(SequenceNextValue sequenceValue) {

		StringBuffer buffer = new StringBuffer();

		if (StringUtils.isNotEmpty(sequenceValue.getSchema())) {
			buffer.append(decorate(sequenceValue.getSchema())).append(".");
		}
		buffer.append(decorate(sequenceValue.getName())).append(".nextval");
		sequenceValue.setSqlString(buffer.toString());
	}

	@Override
	protected Map<String, String> getPredefMaskMappings() {
		if (predefMaskMappings == null) {
			predefMaskMappings = new HashMap<String, String>();
			predefMaskMappings.put("yyyy/MM/dd", "RRRR/MM/DD");
			predefMaskMappings.put("yyyy-MM-dd", "RRRR-MM-DD");
			predefMaskMappings.put("yyyyMMdd", "RRRRMMDD");
			predefMaskMappings.put("yy/MM/dd", "RRRR/MM/DD");
			predefMaskMappings.put("yy-MM-dd", "RRRR-MM-DD");
			predefMaskMappings.put("yyMMdd", "RRRRMMDD");

			predefMaskMappings.put("dd/MM/yyyy", "DD/MM/RRRR");
			predefMaskMappings.put("dd-MM-yyyy", "DD-MM-RRRR");
			predefMaskMappings.put("dd/MM/yy", "DD/MM/RRRR");
			predefMaskMappings.put("dd-MM-yy", "DD-MM-RRRR");

			predefMaskMappings.put("yyyy/MM/dd HH:mm:ss",
					"RRRR/MM/DD HH24:MI:SS");
			predefMaskMappings.put("yyyy-MM-dd HH:mm:ss",
					"RRRR-MM-DD HH24:MI:SS");
			predefMaskMappings.put("yyyyMMdd HH:mm:ss", "RRRRMMDD HH24:MI:SS");
			predefMaskMappings
					.put("yy/MM/dd HH:mm:ss", "RRRR/MM/DD HH24:MI:SS");
			predefMaskMappings
					.put("yy-MM-dd HH:mm:ss", "RRRR-MM-DD HH24:MI:SS");
			predefMaskMappings.put("yyMMdd HH:mm:ss", "RRRRMMDD HH24:MI:SS");

			predefMaskMappings.put("dd/MM/yyyy HH:mm:ss",
					"DD/MM/RRRR HH24:MI:SS");
			predefMaskMappings.put("dd-MM-yyyy HH:mm:ss",
					"DD-MM-RRRR HH24:MI:SS");
			predefMaskMappings
					.put("dd/MM/yy HH:mm:ss", "DD/MM/RRRR HH24:MI:SS");
			predefMaskMappings
					.put("dd-MM-yy HH:mm:ss", "DD-MM-RRRR HH24:MI:SS");

			predefMaskMappings.put("HH:mm:ss", "HH24:MI:SS");

			predefMaskMappings.put("###############.##########",
					"999999999999999D9999999999");
		}
		return predefMaskMappings;
	}
}