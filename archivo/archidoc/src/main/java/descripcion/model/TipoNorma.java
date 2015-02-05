package descripcion.model;

import java.util.HashMap;

/**
 * Tipos de normas de las fichas de descripción.
 */
public class TipoNorma {
	private static HashMap tiposNorma = new HashMap();

	public final static int SIN_TIPO = 0;
	public final static int NORMA_ISADG = 1;
	public final static int NORMA_ISAAR = 2;

	public final static String NINGUNO = "NINGUNO";
	public final static String ISADG = "ISAD(G)";
	public final static String ISAAR = "ISAAR";

	static {
		tiposNorma.put(new Integer(SIN_TIPO).toString(), NINGUNO);
		tiposNorma.put(new Integer(NORMA_ISADG).toString(), ISADG);
		tiposNorma.put(new Integer(NORMA_ISAAR).toString(), ISAAR);
	}

	public static String toText(int tipoNorma) {
		return (String) tiposNorma.get(new Integer(tipoNorma).toString());
	}

}
