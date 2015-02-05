package gcontrol.model;

/**
 * Tipos de lista de control de acceso. El tipo de una lista de control de
 * acceso determina el tipo de objeto al que puede ser asignada
 */
public class TipoListaControlAcceso {
	/*
	 * 1 Elemento del cuadro de clasificación 2 Descriptor 3 Formato de ficha
	 */
	private static int i = 1;

	public final static int ELEMENTO_CUADRO_CLASIFICACION = i++;

	public final static int DESCRIPTOR = i++;

	public final static int FORMATO_FICHA = i++;

}