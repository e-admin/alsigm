package descripcion.model.eventos;

/**
 * Clase que contiene las constantes de los tipos de eventos.
 */
public class TipoEvento {

	/** Evento lanzado antes de crear la descripción. */
	public static final int ANTES_DE_CREAR_DESCRIPCION = 1;

	/** Evento lanzado antes de validar la descripción. */
	public static final int AL_VALIDAR_DESCRIPCION = 2;

	/** Evento lanzado antes de guardar la descripción. */
	public static final int ANTES_DE_SALVAR_DESCRIPCION = 3;

	/** Evento lanzado despues de guardar la descripción. */
	public static final int DESPUES_DE_SALVAR_DESCRIPCION = 4;
}
