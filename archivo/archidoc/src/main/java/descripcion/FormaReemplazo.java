package descripcion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import common.Messages;
import common.util.StringUtils;

public class FormaReemplazo {

	// NOTA: Si se modifican estos valores, se deberá modificar tambien
	// reemplazo.js
	private static final String REEMPLAZO_VALORES_EXACTOS = "0";
	private static final String REEMPLAZO_VALORES_PARCIALES = "1";
	private static final String REEMPLAZO_VALORES_NULOS = "2";
	private static final String KEY_NOMBRE = "archigest.archivo.forma.reemplazo.";

	private static final Map mapListaFormas = new HashMap();

	private static final String[] FORMAS_REEMPLAZO = new String[] {
			REEMPLAZO_VALORES_EXACTOS, REEMPLAZO_VALORES_PARCIALES,
			REEMPLAZO_VALORES_NULOS };

	private String id = null;
	private String nombre = null;

	private FormaReemplazo(String id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}

	public static List getLista(Locale locale) {
		List listaFormas = (List) mapListaFormas.get(locale);

		if (listaFormas == null) {
			listaFormas = new ArrayList();
			for (int i = 0; i < FORMAS_REEMPLAZO.length; i++) {
				String id = FORMAS_REEMPLAZO[i];
				String nombre = Messages.getString(KEY_NOMBRE + id, locale);

				FormaReemplazo formaReemplazo = new FormaReemplazo(id, nombre);
				listaFormas.add(formaReemplazo);
			}
			mapListaFormas.put(locale, listaFormas);
		}

		return listaFormas;
	}

	public String getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public static boolean isReemplazoValoresExactos(String formaReemplazo) {
		if (StringUtils.isEmpty(formaReemplazo)
				|| REEMPLAZO_VALORES_EXACTOS.equals(formaReemplazo))
			return true;

		return false;
	}

	public static boolean isReemplazoValoresParciales(String formaReemplazo) {
		if (REEMPLAZO_VALORES_PARCIALES.equals(formaReemplazo))
			return true;

		return false;
	}

	public static boolean isReemplazoValoresNulos(String formaReemplazo) {
		if (REEMPLAZO_VALORES_NULOS.equals(formaReemplazo))
			return true;

		return false;
	}

}
