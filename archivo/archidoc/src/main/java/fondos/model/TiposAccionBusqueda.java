package fondos.model;

import fondos.vos.AccionVO;

/**
 * Clase que almacena los tipos de acciones posibles a realizar sobre una
 * búsqueda
 */
public class TiposAccionBusqueda {

	/**
	 * Tipo de acción mover elementos
	 */
	public static final String TIPO_ACCION_BUSQUEDA_MOVER_ELEMENTOS = "MOVER_ELEMENTOS";

	/**
	 * Crear de Nuevo Préstamo
	 */
	public static final String TIPO_ACCION_BUSQUEDA_CREAR_PRESTAMO = "CREAR_PRESTAMO";

	/**
	 * A&#241;adir unidades documentales a un Pr&eacute;stamo
	 */
	public static final String TIPO_ACCION_BUSQUEDA_ANIADIR_A_PRESTAMO = "ANIADIR_A_PRESTAMO";

	/**
	 * Creación de Nueva Consulta
	 */
	public static final String TIPO_ACCION_BUSQUEDA_CREAR_CONSULTA = "CREAR_CONSULTA";

	/**
	 * A&#241;adir unidades documentales a una Consulta
	 */
	public static final String TIPO_ACCION_BUSQUEDA_ANIADIR_A_CONSULTA = "ANIADIR_A_CONSULTA";

	/**
	 * Bloquear unidades documentales
	 */
	public static final String TIPO_ACCION_BUSQUEDA_BLOQUEAR = "BLOQUEAR";

	/**
	 * Desbloqueo unidades documentales
	 */
	public static final String TIPO_ACCION_BUSQUEDA_DESBLOQUEAR = "DESBLOQUEAR";

	/**
	 * Establecer el valor de IDELIMINACION a null de las unidades documentales
	 * seleccionadas
	 */
	public static final String TIPO_ACCION_BUSQUEDA_NO_CONSERVAR = "NO_CONSERVAR";

	/**
	 * Array con las posibles acciones a realizar sobre la búsqueda
	 */
	public static final String[] tiposAccionesBusqueda = new String[] {
			TIPO_ACCION_BUSQUEDA_MOVER_ELEMENTOS,
			TIPO_ACCION_BUSQUEDA_CREAR_PRESTAMO,
			TIPO_ACCION_BUSQUEDA_ANIADIR_A_PRESTAMO,
			TIPO_ACCION_BUSQUEDA_CREAR_CONSULTA,
			TIPO_ACCION_BUSQUEDA_ANIADIR_A_CONSULTA,
			TIPO_ACCION_BUSQUEDA_BLOQUEAR, TIPO_ACCION_BUSQUEDA_DESBLOQUEAR,
			TIPO_ACCION_BUSQUEDA_NO_CONSERVAR };

	/**
	 * Constructor a partir del identificador de la acción
	 * 
	 * @param id
	 * @return
	 */
	public static final AccionVO getVOAccionBusqueda(String id) {
		AccionVO accionVO = null;
		if (TIPO_ACCION_BUSQUEDA_MOVER_ELEMENTOS.equals(id)) {
			accionVO = new AccionVO(TIPO_ACCION_BUSQUEDA_MOVER_ELEMENTOS);
		} else if (TIPO_ACCION_BUSQUEDA_CREAR_PRESTAMO.equals(id)) {
			accionVO = new AccionVO(TIPO_ACCION_BUSQUEDA_CREAR_PRESTAMO);
		} else if (TIPO_ACCION_BUSQUEDA_ANIADIR_A_PRESTAMO.equals(id)) {
			accionVO = new AccionVO(TIPO_ACCION_BUSQUEDA_ANIADIR_A_PRESTAMO);
		} else if (TIPO_ACCION_BUSQUEDA_CREAR_CONSULTA.equals(id)) {
			accionVO = new AccionVO(TIPO_ACCION_BUSQUEDA_CREAR_CONSULTA);
		} else if (TIPO_ACCION_BUSQUEDA_ANIADIR_A_CONSULTA.equals(id)) {
			accionVO = new AccionVO(TIPO_ACCION_BUSQUEDA_ANIADIR_A_CONSULTA);
		} else if (TIPO_ACCION_BUSQUEDA_BLOQUEAR.equals(id)) {
			accionVO = new AccionVO(TIPO_ACCION_BUSQUEDA_BLOQUEAR);
		} else if (TIPO_ACCION_BUSQUEDA_DESBLOQUEAR.equals(id)) {
			accionVO = new AccionVO(TIPO_ACCION_BUSQUEDA_DESBLOQUEAR);
		} else if (TIPO_ACCION_BUSQUEDA_NO_CONSERVAR.equals(id)) {
			accionVO = new AccionVO(TIPO_ACCION_BUSQUEDA_NO_CONSERVAR);
		}

		return accionVO;
	}

}
