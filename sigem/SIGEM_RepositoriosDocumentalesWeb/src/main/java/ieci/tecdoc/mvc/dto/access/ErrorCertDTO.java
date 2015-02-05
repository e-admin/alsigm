package ieci.tecdoc.mvc.dto.access;

public class ErrorCertDTO extends Object{
	private int num;

	public ErrorCertDTO(){
	}
	public ErrorCertDTO(int num){
		this.num = num;
	}
	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
	
	public String toString(){
		return String.valueOf(this.num);
	}
	
	public boolean equals(Object x){
		boolean is = false;
		ErrorCertDTO obj = (ErrorCertDTO) x;
		if (obj.num == this.num )
			is = true;
		return is;
	}
	


}
