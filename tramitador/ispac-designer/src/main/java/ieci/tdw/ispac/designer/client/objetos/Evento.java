package ieci.tdw.ispac.designer.client.objetos;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Evento implements IsSerializable {


	int code; 
	String codeDescription = null;
	int order;
	int ruleId;
	//Concatenación separada por '-' de id_obj'-'tp_obj'-'evento'-'orden
	String id;
	Regla regla= new Regla();
	Condition condition= new Condition();
	
	
	
	public Evento(int code, int order, int ruleId, String id, Regla regla,
			Condition condition) {
		super();
		this.code = code;
		this.order = order;
		this.ruleId = ruleId;
		this.id = id;
		this.regla = regla;
		this.condition = condition;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Evento(int code, int order, int ruleId, Regla regla,
			Condition condition) {
		super();
		this.code = code;
		this.order = order;
		this.ruleId = ruleId;
		this.regla = regla;
		this.condition = condition;
	}

	public Evento() {
		// TODO Auto-generated constructor stub
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public int getRuleId() {
		return ruleId;
	}

	public void setRuleId(int ruleId) {
		this.ruleId = ruleId;
	}

	public Regla getRegla() {
		return regla;
	}

	public void setRegla(Regla regla) {
		this.regla = regla;
	}

	public Condition getCondition() {
		return condition;
	}

	public void setCondition(Condition condition) {
		this.condition = condition;
	}

	public String getCodeDescription() {
		return codeDescription;
	}

	public void setCodeDescription(String codeDescription) {
		this.codeDescription = codeDescription;
	}
	
}
