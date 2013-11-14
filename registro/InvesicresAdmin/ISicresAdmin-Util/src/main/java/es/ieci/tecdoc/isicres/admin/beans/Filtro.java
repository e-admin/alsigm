package es.ieci.tecdoc.isicres.admin.beans;

/**
 * Filtro de tratamiento de libros
 *
 *
 */
public class Filtro {

	private String campo;
	private String operador;
	private String valor;
	private String nexo;
	private OptionsBean nexos;
	private OptionsBean operadores;
	private OptionsBean valores;

	/** **** CAMPOS FILTRO ******* */
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
	// public static final int CAMPO_TIPO_ASUNTO = 16;

	public static final int CAMPO_FECHA_DEL_DOCUMENTO_ENTRADA = 20;
	public static final int CAMPO_FECHA_DEL_DOCUMENTO_SALIDA = 15;

	public static final int CAMPO_TIPO_ASUNTO_ENTRADA = 16;
	public static final int CAMPO_TIPO_ASUNTO_SALIDA = 12;

	/** ***** OPERADORES FILTRO ******** */
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

	/** ***** ESTADOS FILTRO ********* */
	public static final int ESTADO_COMPLETO = 0;
	public static final int ESTADO_INCOMPLETO = 1;
	public static final int ESTADO_RESERVADO = 2;
	//Este valor esta reservado para futuros estados, de momento no se utiliza
	public static final int VALOR_ESTADO_NO_VALIDO = 3;
	//
	public static final int ESTADO_ANULADO = 4;
	public static final int ESTADO_CERRADO = 5;

	/** ******* TIPO REGISTRO ORIGINAL ********** */
	public static final int TIPO_REGISTRO_ORIGINAL_ENTRADA = 1;
	public static final int TIPO_REGISTRO_ORIGINAL_SALIDA = 2;

	/** ****** TIPOS DE CAMPOS *********** */
	public static final String TIPO_STRING = "A";
	public static final String TIPO_DATE = "B";
	public static final String TIPO_COMBO = "C";
	public static final String TIPO_OFICINAS = "D";
	public static final String TIPO_UNIDADES_ADMIN = "E";
	public static final String TIPO_ASUNTO = "F";

	/** ***** NEXOS FILTRO ************* */
	public static final String NEXO_Y = "AND";
	public static final String NEXO_O = "OR";

	/** ****** TIPOS FILTRO ************ */
	public static final int TIPO_FILTRO_OFICINAS = 2;
	public static final int TIPO_FILTRO_USUARIOS = 1;

	/**
	 * @return
	 */
	public OptionsBean getNexos() {
		return nexos;
	}

	/**
	 * @param nexos
	 */
	public void setNexos(OptionsBean nexos) {
		this.nexos = nexos;
	}

	/**
	 * @return
	 */
	public OptionsBean getOperadores() {
		return operadores;
	}

	/**
	 * @param operadores
	 */
	public void setOperadores(OptionsBean operadores) {
		this.operadores = operadores;
	}

	/**
	 * @return
	 */
	public OptionsBean getValores() {
		return valores;
	}

	/**
	 * @param valores
	 */
	public void setValores(OptionsBean valores) {
		this.valores = valores;
	}

	/**
	 * @return
	 */
	public String getCampo() {
		return campo;
	}

	/**
	 * @param campo
	 */
	public void setCampo(String campo) {
		this.campo = campo;
	}

	/**
	 * @return
	 */
	public String getNexo() {
		return nexo;
	}

	/**
	 * @param nexo
	 */
	public void setNexo(String nexo) {
		this.nexo = nexo;
	}

	/**
	 * @return
	 */
	public String getOperador() {
		return operador;
	}

	/**
	 * @param operador
	 */
	public void setOperador(String operador) {
		this.operador = operador;
	}

	/**
	 * @return
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * @param valor
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}
}
