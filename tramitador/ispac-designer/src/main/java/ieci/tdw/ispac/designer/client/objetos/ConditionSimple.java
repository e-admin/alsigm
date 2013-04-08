package ieci.tdw.ispac.designer.client.objetos;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ConditionSimple implements IsSerializable {
	
	int parentesisOpen; // 0 si falso 1 si verdadero
	int parentesisClose; // 0 si falso 1 si verdadero

	//"" si no tiene ni and ni or
	String andOr;
	//Operando 1 , siempre ha de existir
	Operando op1;
	//Operando 2, puede exisitir op > op2 o no op1 not null
	Operando op2;
	//Solo existirá con el operador between
	Operando op3;
	//Operador > >= < <= LIKE
	String operador ;
	

	
	public String getAndOr() {
		return andOr;
	}
	public void setAndOr(String andOr) {
		this.andOr = andOr;
	}
	public Operando getOp1() {
		return op1;
	}
	public void setOp1(Operando op1) {
		this.op1 = op1;
	}
	public Operando getOp2() {
		return op2;
	}
	public void setOp2(Operando op2) {
		this.op2 = op2;
	}
	public Operando getOp3() {
		return op3;
	}
	public void setOp3(Operando op3) {
		this.op3 = op3;
	}
	public String getOperador() {
		return operador;
	}
	public void setOperador(String operador) {
		this.operador = operador;
	}
	public ConditionSimple( String andOr, Operando op1, Operando op2,
			Operando op3, String operador) {
		super();
	
		this.andOr = andOr;
		this.op1 = op1;
		this.op2 = op2;
		this.op3 = op3;
		this.operador = operador;
	
	}
	public ConditionSimple(int id, String andOr, Operando op1, Operando op2,
			String operador) {
		super();
		
		this.andOr = andOr;
		this.op1 = op1;
		this.op2 = op2;
		this.operador = operador;
		
	}
	public ConditionSimple(int id, String andOr, Operando op1, String operador ) {
		super();
		
		this.andOr = andOr;
		this.op1 = op1;
		this.operador = operador;
		
	}
	public ConditionSimple() {
		super();
		this.andOr="";
	    this.parentesisClose=0;
	    this.parentesisOpen=0;
	    this.op1=new Operando();
	    this.op2=new Operando();
	    this.op3=new Operando();
		// TODO Auto-generated constructor stub
	}

	
	public ConditionSimple(int parentesisOpen, int parentesisClose,
		 String andOr, Operando op1, Operando op2, Operando op3,
			String operador) {
		super();
		this.parentesisOpen = parentesisOpen;
		this.parentesisClose = parentesisClose;
		
		this.andOr = andOr;
		this.op1 = op1;
		this.op2 = op2;
		this.op3 = op3;
		this.operador = operador;
	}
	public int getParentesisOpen() {
		return parentesisOpen;
	}
	public void setParentesisOpen(int parentesisOpen) {
		this.parentesisOpen = parentesisOpen;
	}
	public int getParentesisClose() {
		return parentesisClose;
	}
	public void setParentesisClose(int parentesisClose) {
		this.parentesisClose = parentesisClose;
	}
	
	

}
