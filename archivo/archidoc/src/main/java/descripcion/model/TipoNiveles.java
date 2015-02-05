package descripcion.model;

import java.util.HashMap;

public class TipoNiveles {

	private static HashMap tipoNiveles = new HashMap();

	public static final int TODOS_VALUE = 0;
	public static final int CLASIFICADOR_DE_FONDOS_VALUE = 1;
	public static final int FONDOS_VALUE = 2;
	public static final int CLASIFICADOR_DE_SERIES_VALUE = 3;
	public static final int SERIE_VALUE = 4;
	public static final int CLASIFICADOR_UNIDAD_DOCUMENTAL_VALUE = 5;
	public static final int UNIDAD_DOCUMENTAL_VALUE = 6;
	public static final int AUTORIDAD_VALUE = 7;

	public static final String TODOS_LABEL = "TODOS";
	public static final String CLASIFICADOR_DE_FONDOS_LABEL = "CLASIFICADOR_DE_FONDOS";
	public static final String FONDOS_LABEL = "FONDOS";
	public static final String CLASIFICADOR_DE_SERIES_LABEL = "CLASIFICADOR_DE_SERIES";
	public static final String SERIE_LABEL = "SERIE";
	public static final String CLASIFICADOR_UNIDAD_DOCUMENTAL_LABEL = "CLASIFICADOR_UNIDAD_DOCUMENTAL";
	public static final String UNIDAD_DOCUMENTAL_LABEL = "UNIDAD_DOCUMENTAL";
	public static final String AUTORIDAD_LABEL = "AUTORIDAD";

	static {
		tipoNiveles.put(new Integer(TODOS_VALUE).toString(), TODOS_LABEL);
		tipoNiveles.put(new Integer(CLASIFICADOR_DE_FONDOS_VALUE).toString(),
				CLASIFICADOR_DE_FONDOS_LABEL);
		tipoNiveles.put(new Integer(FONDOS_VALUE).toString(), FONDOS_LABEL);
		tipoNiveles.put(new Integer(CLASIFICADOR_DE_SERIES_VALUE).toString(),
				CLASIFICADOR_DE_SERIES_LABEL);
		tipoNiveles.put(new Integer(SERIE_VALUE).toString(), SERIE_LABEL);
		tipoNiveles.put(
				new Integer(CLASIFICADOR_UNIDAD_DOCUMENTAL_VALUE).toString(),
				CLASIFICADOR_UNIDAD_DOCUMENTAL_LABEL);
		tipoNiveles.put(new Integer(UNIDAD_DOCUMENTAL_VALUE).toString(),
				UNIDAD_DOCUMENTAL_LABEL);
		tipoNiveles.put(new Integer(AUTORIDAD_VALUE).toString(),
				AUTORIDAD_LABEL);
	}

	public static String toText(int tipoNivel) {
		return (String) tipoNiveles.get(new Integer(tipoNivel).toString());
	}
}
