package es.ieci.tecdoc.isicres.admin.core.beans;

/*$Id*/

public class FiltroImpl {

	public static final String TIPO_STRING = "A";
	public static final String TIPO_DATE = "B";
	public static final String TIPO_COMBO = "C";
	public static final String TIPO_OFICINAS = "D";
	public static final String TIPO_UNIDADES_ADMIN = "E";
	public static final String TIPO_ASUNTO = "F";

	public static final int OPERADOR_IGUAL_A = 1;
	public static final int OPERADOR_EMPIEZA_POR = 2;
	public static final int OPERADOR_TERMINA_POR = 3;
	public static final int OPERADOR_CONTIENE = 4;
	public static final int OPERADOR_DISTINTO_DE = 5;
	public static final int OPERADOR_MAYOR_QUE = 6;
	public static final int OPERADOR_MENOR_QUE = 7;
	public static final int OPERADOR_MAYOR_O_IGUAL_QUE = 8;
	public static final int OPERADOR_MENOR_O_IGUAL_QUE = 9;
	public static final int OPERADOR_EN_LA_SEMANA = 10;
	public static final int OPERADOR_EN_EL_MES = 11;
	public static final int OPERADOR_EN_EL_AÑO = 12;
	public static final int OPERADOR_POSTERIOR_A = 13;
	public static final int OPERADOR_ANTERIOR_A = 14;

	/****** CAMPOS FILTRO ********/
	public static final int CAMPO_NUMERO_REGISTRO = 1;
	public static final int CAMPO_USUARIO = 3;
	public static final int CAMPO_REMITENTES = 9;
	public static final int CAMPO_NUMERO_REGISTRO_ORIGINAL = 10;
	public static final int CAMPO_TIPO_TRANSPORTE = 14;
	public static final int CAMPO_NUMERO_TRANSPORTE = 15;
	public static final int CAMPO_RESUMEN = 17;
	public static final int CAMPO_REFERENCIA_EXPEDIENTE = 18;
	public static final int CAMPO_FECHA_REGISTRO = 2;
	public static final int CAMPO_FECHA_TRABAJO = 4;
	public static final int CAMPO_FECHA_REGISTRO_ORIGINAL = 12;
	public static final int CAMPO_ESTADO = 6;
	public static final int CAMPO_TIPO_REGISTRO_ORIGINAL = 11;
	public static final int CAMPO_OFICINA_REGISTRO = 5;
	public static final int CAMPO_ORIGEN = 7;
	public static final int CAMPO_DESTINO = 8;
	public static final int CAMPO_ENTIDAD_REGISTRO_ORIGINAL = 13;
	
	public static final int CAMPO_TIPO_ASUNTO_ENTRADA = 16;
	public static final int CAMPO_TIPO_ASUNTO_SALIDA = 12;
	
	public static final int CAMPO_FECHA_DEL_DOCUMENTO_ENTRADA = 20;
	public static final int CAMPO_FECHA_DEL_DOCUMENTO_SALIDA = 15;

	public static final int TIPO_FILTRO_OFICINAS = 2;
	public static final int TIPO_FILTRO_USUARIOS = 1;
	
	private int campo;
	private int operador;
	private String valor;
	private String nexo;

	public int getCampo() {
		return campo;
	}
	public void setCampo(int campo) {
		this.campo = campo;
	}
	public String getNexo() {
		return nexo;
	}
	public void setNexo(String nexo) {
		this.nexo = nexo;
	}
	public int getOperador() {
		return operador;
	}
	public void setOperador(int operador) {
		this.operador = operador;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}

	
}
