package deposito;

public class MarcaUInstalacionConstants {
	
	public static final int POSICION_BIT_MARCA_BLOQUEADA_TRANSFERENCIA=0;
	public static final int POSICION_BIT_MARCA_BLOQUEADA_DIVISIONFS=1;
	
	/**
	 * Prefijo para acceder a la causa del motivo de bloqueo, habría que añadir la posición (0 por ejemplo)
	 * para poder acceder al texto correspondiente. Para obtener por ejemplo el texto de bloqueada por
	 * transferencia lo habría que hacer a partir de  KEY_MESSAGES_MARCAS_UINSTALACION+"0"
	 */
	public static final String KEY_PREFIJO_MESSAGES_MARCAS_UINSTALACION = "archigest.marcas.uinstalacion.posicion.";
	
	public static final int marcas[]={POSICION_BIT_MARCA_BLOQUEADA_TRANSFERENCIA, POSICION_BIT_MARCA_BLOQUEADA_DIVISIONFS};
}
