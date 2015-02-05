package descripcion.model;

import java.util.HashMap;

public class TipoCampo {

	private static HashMap tiposCampo = new HashMap();

	public static final int TABLA_VALUE = -1;

	public static final int TEXTO_CORTO_VALUE = 1;
	public static final int TEXTO_LARGO_VALUE = 2;
	public static final int FECHA_VALUE = 3;
	public static final int NUMERICO_VALUE = 4;
	public static final int REFERENCIA_VALUE = 5;

	public static final String TEXTO_CORTO_LABEL = "Texto corto";
	public static final String TEXTO_LARGO_LABEL = "Texto largo";
	public static final String FECHA_LABEL = "Fecha";
	public static final String NUMERICO_LABEL = "Número";
	public static final String REFERENCIA_LABEL = "Referencia";

	public static final String TABLA_LABEL ="Tabla";


	static {
		tiposCampo.put(new Integer(TEXTO_CORTO_VALUE).toString(),
				TEXTO_CORTO_LABEL);
		tiposCampo.put(new Integer(TEXTO_LARGO_VALUE).toString(),
				TEXTO_LARGO_LABEL);
		tiposCampo.put(new Integer(FECHA_VALUE).toString(), FECHA_LABEL);
		tiposCampo.put(new Integer(NUMERICO_VALUE).toString(), NUMERICO_LABEL);
		tiposCampo.put(new Integer(REFERENCIA_VALUE).toString(),
				REFERENCIA_LABEL);
	}

	public static String toText(int tipoCampo) {
		return (String) tiposCampo.get(new Integer(tipoCampo).toString());
	}

}
