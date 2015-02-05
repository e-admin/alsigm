package ieci.tecdoc.sgm.rpadmin.beans;
/*$Id*/

public class SicresContadorOficinaImpl {
	
	public static final int LIBRO_SALIDA = 1;
	public static final int LIBRO_ENTRADA = 2;
	private int an;
	private int oficina;
	private int numReg;
	private int idArch;

	public int getAn() {
		return an;
	}
	public void setAn(int an) {
		this.an = an;
	}
	public int getIdArch() {
		return idArch;
	}
	public void setIdArch(int idArch) {
		this.idArch = idArch;
	}
	public int getNumReg() {
		return numReg;
	}
	public void setNumReg(int numReg) {
		this.numReg = numReg;
	}
	public int getOficina() {
		return oficina;
	}
	public void setOficina(int oficina) {
		this.oficina = oficina;
	}
}
