/*
 * Created on 17-feb-2005
 *
 */
package transferencias.vos;

public class NSecReVO {
	String ano;
	int numsec;
	String idorgremitente;

	public NSecReVO(String ano, int numsec, String idorgremitente) {
		this.ano = ano;
		this.numsec = numsec;
		this.idorgremitente = idorgremitente;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public String getIdorgremitente() {
		return idorgremitente;
	}

	public void setIdorgremitente(String idorgremitente) {
		this.idorgremitente = idorgremitente;
	}

	public int getNumsec() {
		return numsec;
	}

	public void setNumsec(int numsec) {
		this.numsec = numsec;
	}
}
