package common.definitions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import auditoria.vos.ArchivoModule;

import common.ConfigConstants;

/**
 * Clase que encapsula los módulos existentes para la aplicación.
 */
public class ArchivoModules {
	/** Ctes de identificacion de los módulos */
	public static final int NONE_MODULE = 0;
	public static final int SISTEMA_MODULE = 1;
	public static final int TRANSFERENCIAS_MODULE = 2;
	public static final int SERVICIOS_MODULE = 3;
	public static final int DEPOSITOS_MODULE = 4;
	public static final int FONDOS_MODULE = 5;
	public static final int DESCRIPCION_MODULE = 6;
	public static final int USUARIOS_MODULE = 7;
	public static final int DOCUMENTOS_VITALES_MODULE = 8;
	public static final int EXPLOTACION_MODULE = 9;
	public static final int DOCUMENTOS_ELECTRONICOS_MODULE = 10;
	public static final int AUDITORIA_MODULE = 11;
	public static final int ARCHIVOS_MODULE = 13;
	public static final int SALAS_MODULE = 14;
	public static final int ORGANIZACION_MODULE = 15;

	/** Nombre de los modulos */
	public static final String SISTEMA_MODULE_NAME = "nombreModulo.modulo1";
	public static final String TRANSFERENCIAS_MODULE_NAME = "nombreModulo.modulo2";
	public static final String SERVICIOS_MODULE_NAME = "nombreModulo.modulo3";
	public static final String DEPOSITOS_MODULE_NAME = "nombreModulo.modulo4";
	public static final String FONDOS_MODULE_NAME = "nombreModulo.modulo5";
	public static final String DESCRIPCION_MODULE_NAME = "nombreModulo.modulo6";
	public static final String USUARIOS_MODULE_NAME = "nombreModulo.modulo7";
	public static final String DOCUMENTOS_VITALES_MODULE_NAME = "nombreModulo.modulo8";
	public static final String EXPLOTACION_MODULE_NAME = "nombreModulo.modulo9";
	public static final String DOCUMENTOS_ELECTRONICOS_MODULE_NAME = "nombreModulo.modulo10";
	public static final String AUDITORIA_MODULE_NAME = "nombreModulo.modulo11";
	public static final String ARCHIVOS_MODULE_NAME = "nombreModulo.modulo13";
	public static final String SALAS_MODULE_NAME = "nombreModulo.modulo14";

	/** Asociacion modulo/nombre */
	/** Comentamos aquellos que aun no están desarrollados */
	private static HashMap moduleNames;

	static {
		moduleNames = new HashMap();
		moduleNames.put(new Integer(SISTEMA_MODULE), SISTEMA_MODULE_NAME);
		moduleNames.put(new Integer(TRANSFERENCIAS_MODULE),
				TRANSFERENCIAS_MODULE_NAME);
		moduleNames.put(new Integer(SERVICIOS_MODULE), SERVICIOS_MODULE_NAME);
		moduleNames.put(new Integer(DEPOSITOS_MODULE), DEPOSITOS_MODULE_NAME);
		moduleNames.put(new Integer(FONDOS_MODULE), FONDOS_MODULE_NAME);
		moduleNames.put(new Integer(DESCRIPCION_MODULE),
				DESCRIPCION_MODULE_NAME);
		moduleNames.put(new Integer(USUARIOS_MODULE), USUARIOS_MODULE_NAME);
		if (ConfigConstants.getInstance().getMostrarDocVitales()) {
			moduleNames.put(new Integer(DOCUMENTOS_VITALES_MODULE),
					DOCUMENTOS_VITALES_MODULE_NAME);
		}
		/*
		 * moduleNames.put(new
		 * Integer(EXPLOTACION_MODULE),EXPLOTACION_MODULE_NAME);
		 */
		moduleNames.put(new Integer(DOCUMENTOS_ELECTRONICOS_MODULE),
				DOCUMENTOS_ELECTRONICOS_MODULE_NAME);
		// moduleNames.put(new Integer(AUDITORIA_MODULE),AUDITORIA_MODULE_NAME);
		// if
		// (ConfigConstants.getInstance().getPermitirTransferenciasEntreArchivos())
		// {
		// moduleNames.put(new Integer(ARCHIVOS_MODULE), ARCHIVOS_MODULE_NAME);
		// }

		if (ConfigConstants.getInstance().getMostrarSalas()) {
			moduleNames.put(new Integer(SALAS_MODULE), SALAS_MODULE_NAME);
		}
	}

	/**
	 * Devuelve el nombre asociado al tipo de modulo pasado o null en caso de no
	 * existir el modulo.
	 * 
	 * @param module
	 *            Módulo del que deseamos obtener el nombre
	 * @return Nombre del módulo
	 */
	public static String getModuleName(int module) {
		return (String) moduleNames.get(new Integer(module));
	}

	/**
	 * Obtiene un listado de los modulos existentes.
	 * 
	 * @return Listado de los módulos{@link ArchivoModule} existentes.
	 */
	public static Collection getModuleNames() {
		Integer i = null;
		String name = null;
		ArchivoModule module = null;
		ArrayList result = new ArrayList();
		Iterator it = moduleNames.keySet().iterator();

		while (it.hasNext()) {
			i = (Integer) it.next();

			name = (String) moduleNames.get(i);

			module = new ArchivoModule();
			module.setId(i.intValue());
			module.setName(name);

			result.add(module);
		}

		// Ordenamos los resultados
		Collections.sort(result);

		return result;
	}

	public static Map allModules() {
		return moduleNames;
	}
}
