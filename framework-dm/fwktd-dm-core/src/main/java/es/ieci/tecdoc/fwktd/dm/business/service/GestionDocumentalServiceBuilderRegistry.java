package es.ieci.tecdoc.fwktd.dm.business.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class GestionDocumentalServiceBuilderRegistry {

    /**
     * Mapa de builders.
     */
	private static Map<String, GestionDocumentalServiceBuilder> builders = new HashMap<String, GestionDocumentalServiceBuilder>();

	/**
	 * Singleton
	 */
	private static GestionDocumentalServiceBuilderRegistry singleton = null;


	/**
	 * Constructor privado.
	 */
	private GestionDocumentalServiceBuilderRegistry() {
		super();
	}

	/**
	 * Devuelve la instancia de la clase.
	 * @return Instancia de la clase.
	 */
	public static synchronized GestionDocumentalServiceBuilderRegistry getInstance() {
		if (singleton == null) {
			singleton = new GestionDocumentalServiceBuilderRegistry();
		}
		return singleton;
	}

	/**
	 * Registra un builder.
	 * @param type Tipo del builder
	 * @param builder Builder.
	 */
	public void register(String type, GestionDocumentalServiceBuilder builder) {
		if (StringUtils.isNotBlank(type)) {
			builders.put(type, builder);
		}
	}

	/**
	 * Obtiene el builder para un tipo concreto
	 * @param type Tipo de builder.
	 * @return Builder.
	 */
	public GestionDocumentalServiceBuilder getBuilder(String type) {
		return builders.get(type);
	}
}
