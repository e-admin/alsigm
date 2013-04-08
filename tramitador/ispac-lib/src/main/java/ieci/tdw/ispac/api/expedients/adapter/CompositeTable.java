package ieci.tdw.ispac.api.expedients.adapter;

import java.util.Map;

public class CompositeTable{
	private String fieldForeignKey;
	private String fieldPrimaryKey;
	private String valueForeignKey;
	Map mapValues = null;
	
	public CompositeTable() {
		super();
		this.fieldForeignKey = null;
		this.mapValues = null;
	}
	public String getFieldPrimaryKey() {
		return fieldPrimaryKey;
	}
	public void setFieldPrimaryKey(String fieldPrimaryKey) {
		this.fieldPrimaryKey = fieldPrimaryKey;
	}
	public String getValueForeignKey() {
		return valueForeignKey;
	}
	public void setValueForeignKey(String valueForeignKey) {
		this.valueForeignKey = valueForeignKey;
	}
	public String getFieldForeignKey() {
		return fieldForeignKey;
	}
	public void setFieldForeignKey(String foreignKey) {
		this.fieldForeignKey = foreignKey;
	}
	public Map getMapValues() {
		return mapValues;
	}
	public void setMapValues(Map mapValues) {
		this.mapValues = mapValues;
	}
	
}
