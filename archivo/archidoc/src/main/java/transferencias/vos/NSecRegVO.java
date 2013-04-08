/*
 * Created on 07-mar-2005
 *
 */
package transferencias.vos;

public class NSecRegVO {
	String ano;
	int numsec;

	public NSecRegVO(String ano, int numsec) {
		super();
		this.ano = ano;
		this.numsec = numsec;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public int getNumsec() {
		return numsec;
	}

	public void setNumsec(int numsec) {
		this.numsec = numsec;
	}
}
