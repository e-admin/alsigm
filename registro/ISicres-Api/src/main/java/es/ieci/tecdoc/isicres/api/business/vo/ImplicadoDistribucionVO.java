package es.ieci.tecdoc.isicres.api.business.vo;

public class ImplicadoDistribucionVO extends BaseIsicresApiVO {

	private static final long serialVersionUID = -8392612469589190453L;

	/**
	 * identificador del implicado (id de {@link UsuarioVO}) (id de
	 * {@link GrupoUsuarioVO}) (id de {@link DepartamentoUsuarioVO})
	 */
	protected String id;

	protected String name;

	/**
	 * 1 usuario {@link UsuarioVO}) 2 Grupo tipo Departamento/Oficina (id de
	 * {@link DepartamentoUsuarioVO}) 3 Grupo {@link GrupoUsuario}
	 * 
	 */
	protected String tipo;

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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
