/**
 *
 */
package fondos.vos;

import common.vos.BaseVO;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class TablaTemporalFondosVO extends BaseVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String nombreTabla;
	private String idUsuario;
	private String identificador;
	private long total;

	// private String[] ids;

	public TablaTemporalFondosVO(Integer id, String nombreTabla,
			String identificador, String idUsuario) {
		super();
		this.id = id;
		this.nombreTabla = nombreTabla;
		this.identificador = identificador;
		this.idUsuario = idUsuario;
	}

	public void setNombreTabla(String nombreTabla) {
		this.nombreTabla = nombreTabla;
	}

	public String getNombreTabla() {
		return nombreTabla;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setNumeroTabla(Integer numeroTabla) {
		this.id = numeroTabla;
	}

	public Integer getNumeroTabla() {
		return id;
	}

	public int getNumeroTablaAsInt() {
		if (id != null) {
			return id.intValue();
		}
		return 0;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public long getTotal() {
		return total;
	}

}
