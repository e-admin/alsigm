package ieci.tecdoc.sgm.core.services.rpadmin;

/**
 * Estados de los vo que se almacenan en base de datos
 * 
 * @author lucas
 * 
 */
public class Estados {
	public static final int SIN_CAMBIOS = 0;
	public static final int NUEVO = 1;
	public static final int MODIFICADO = 2;
	public static final int ELIMINADO = 3;
	public static final int ELIMINADO_NUEVO = 4;

	/**
	 * @param estado
	 * @return
	 */
	public static final boolean isSinCambios(int estado) {
		if (estado == SIN_CAMBIOS)
			return true;
		return false;
	}

	/**
	 * @param estado
	 * @return
	 */
	public static final boolean isNuevo(int estado) {
		if (estado == NUEVO)
			return true;
		return false;
	}

	/**
	 * @param estado
	 * @return
	 */
	public static final boolean isModificado(int estado) {
		if (estado == MODIFICADO)
			return true;
		return false;
	}

	/**
	 * @param estado
	 * @return
	 */
	public static final boolean isEliminado(int estado) {
		if (estado == ELIMINADO)
			return true;
		return false;
	}

	/**
	 * @param estado
	 * @return
	 */
	public static final boolean isEliminadoNuevo(int estado) {
		if (estado == ELIMINADO_NUEVO)
			return true;
		return false;
	}
}
