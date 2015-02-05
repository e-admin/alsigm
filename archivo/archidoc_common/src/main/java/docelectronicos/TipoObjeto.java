package docelectronicos;
//TODO MIRAR ESTO Y CORREGIR !!!
/**
 * Constantes para los tipos de objetos.
 */
public class TipoObjeto
{
//    public static final int CL_FONDOS	= 1;
//    public static final int FONDO	= 2;
//    public static final int CL_SERIES	= 3;
//    public static final int SERIE	= 4;
//    public static final int CL_UDOC	= 5;
//    public static final int UDOC	= 6;

    public static final int ELEMENTO_CF	= 1;

	/** Objeto de tipo descriptor. */
	public static final int DESCRIPTOR 	= 2;

	public static final int DESCRIPTOR_EN_BD = 7;

	public static final int UNIDADDOC_RE = 8;

	public static int valueOf(int valueTipoInBD){
	    if (valueTipoInBD==6) return ELEMENTO_CF;
	    if (valueTipoInBD==7) return DESCRIPTOR;
	    return 0;
	}

}
