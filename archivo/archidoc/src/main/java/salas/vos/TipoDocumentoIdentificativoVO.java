/**
 *
 */
package salas.vos;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class TipoDocumentoIdentificativoVO {

	private static final String TEXTO_KEY = "archigest.archivo.tipodoc.";

	private String id;
	private String nombre;

	public TipoDocumentoIdentificativoVO(String id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public static String getKey(String id) {
		return TEXTO_KEY + id;
	}

}
