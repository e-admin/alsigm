package ieci.tdw.ispac.designer.client.messages;

import com.google.gwt.i18n.client.Messages;

public interface ErrorMensajes extends Messages {

	String accionNoRealizada(int errorCode, String accion, String causa);
	String parametroNoRecibido(int errorCode, String parametro);
	String entornoIncorrecto(int errorCode);
	String campoObligatorio(int errorCode, String campo);
	String campoLinea(int errorCode, String campo, int linea);
	String valorIncorrecto(int errorCode, String campo, int linea);
	String parentesisDesequilibradosAbiertos(int errorCode);
	String parentesisDesequilibradosCerrados(int errorCode);
	String opCampo(int errorCode, String campo, int linea);
	String noExisteEvento(int errorCode);
	String AndOrNoVacio(int errorCode, String campo, int linea);
						
	
}
