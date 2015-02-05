package valoracion.view;

public class InfoSerie {
	String id = null;
	String codigo = null;
	String titulo = null;

	public InfoSerie(String id, String codigo, String titulo) {
		this.id = id;
		this.codigo = codigo;
		this.titulo = titulo;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
}