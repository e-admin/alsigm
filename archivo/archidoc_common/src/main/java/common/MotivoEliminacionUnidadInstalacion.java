package common;


public class MotivoEliminacionUnidadInstalacion {
	public static final Integer COMPACTACION_UNIDADES_DOCUMENTALES = new Integer(1);
	public static final Integer REUBICACION_UNIDADES_INSTALACION = new Integer(2);
	public static final Integer SELECCION = new Integer(3);
	public static final Integer TRANSFERENCIA_ENTRE_ARCHIVOS = new Integer(4);
	public static final Integer WS_PRESTADAS_NO_DEVUELTAS = new Integer(5);
	public static final Integer CAMBIO_SIGNATURA = new Integer(6);

	public static final String[] MOTIVOS_ELIMINACION = new String[]{
		COMPACTACION_UNIDADES_DOCUMENTALES.toString(),
		REUBICACION_UNIDADES_INSTALACION.toString(),
		SELECCION.toString(),
		TRANSFERENCIA_ENTRE_ARCHIVOS.toString(),
		WS_PRESTADAS_NO_DEVUELTAS.toString(),
		CAMBIO_SIGNATURA.toString()
	};
}
