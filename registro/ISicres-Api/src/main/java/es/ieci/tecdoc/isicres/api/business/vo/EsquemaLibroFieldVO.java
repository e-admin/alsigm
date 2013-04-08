package es.ieci.tecdoc.isicres.api.business.vo;

public class EsquemaLibroFieldVO extends BaseIsicresApiVO {

	private static final long serialVersionUID = 2230159684225103384L;

	protected String id;
	protected String name;
	protected String label;
	protected String tipo;
	protected int length;
	protected boolean hasValidation;

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

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public boolean isHasValidation() {
		return hasValidation;
	}

	public void setHasValidation(boolean hasValidation) {
		this.hasValidation = hasValidation;
	}

}
