package fondos.model;

/**
 * Clase que almacena las posibles restricciones aplicadas a los tipos en
 * acciones de búsquedas
 */
public class RestriccionesTipoAccionBusqueda {

	/**
	 * Restriccion aplicable a varios elementos
	 */
	public static final String RESTRICCION_VARIOS_ELEMENTOS = "VARIOS_ELEMENTOS";

	/**
	 * Restriccion aplicable a elementos de distinto padre
	 */
	public static final String RESTRICCION_DISTINTO_PADRE = "DISTINTO_PADRE";

	/**
	 * Restricción aplicable a elemento de distinto archivo
	 */
	public static final String RESTRICCION_DISTINTO_ARCHIVO = "DISTINTO_ARCHIVO";

	/**
	 * Definición de permisos que puede realizar la acción
	 */
	public static final String RESTRICCION_PERMISOS = "PERMISOS";

	/**
	 * Array con las posibles restricciones
	 */
	public static final String[] restriccionesTipoAccionBusqueda = new String[] {
			RESTRICCION_VARIOS_ELEMENTOS, RESTRICCION_DISTINTO_PADRE,
			RESTRICCION_DISTINTO_ARCHIVO, RESTRICCION_PERMISOS };

}
