package ieci.tecdoc.mvc.dto.access;

public class TypeCertDTO {
	private int id;
	private String label;
	private String token;
		
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public TypeCertDTO(){
		
	}
	public String toString(){
		return "id: " + id + " " + label + " " + token;
	}

}
