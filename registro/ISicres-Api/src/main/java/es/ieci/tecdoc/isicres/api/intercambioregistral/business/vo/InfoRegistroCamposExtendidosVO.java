package es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo;

/**
 * Clase que almacena los campos extendidos de un registro
 * @author 66542549
 *
 */
public class InfoRegistroCamposExtendidosVO {

	public static final int COMENTARIO_ID = 18;
	public static final int EXPONE_ID = 501;
	public static final int SOLICITA_ID = 502;

	private Integer id;
	private Integer fdrid;
	private Integer fldid;
	private String value;


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getFdrid() {
		return fdrid;
	}
	public void setFdrid(Integer fdrid) {
		this.fdrid = fdrid;
	}
	public Integer getFldid() {
		return fldid;
	}
	public void setFldid(Integer fldid) {
		this.fldid = fldid;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

}
