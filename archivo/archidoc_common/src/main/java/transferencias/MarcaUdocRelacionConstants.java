package transferencias;

public class MarcaUdocRelacionConstants {
	
	public static final int POSICION_BIT_MARCA_BLOQUEADA_TRANSFERENCIA_EA_EXPURGO=0;
	
	/**
	 * Prefijo para acceder a la causa del motivo de bloqueo, habría que añadir la posición (0 por ejemplo)
	 * para poder acceder al texto correspondiente. Para obtener por ejemplo el texto de bloqueada por
	 * transferencia lo habría que hacer a partir de  KEY_PREFIJO_MESSAGES_MARCAS_UDOCRELACION+"0"
	 */
	public static final String KEY_PREFIJO_MESSAGES_MARCAS_UDOCRELACION = "archigest.marcas.udocrelacion.posicion.";
	
	public static final int marcas[]={POSICION_BIT_MARCA_BLOQUEADA_TRANSFERENCIA_EA_EXPURGO};
}
