package es.ieci.tecdoc.isicres.api.business.vo;

public class BaseGrupoUsuarioVO extends BaseIsicresApiVO {

	private static final long serialVersionUID = -8386784507852348671L;

	protected String id;
	protected String name;
	protected String descripcion;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
