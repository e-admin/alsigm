/*
 * Created on 14-feb-2005
 *
 */
package transferencias.vos;

/**
 * Value Object con la informacion referente los numeros de secuencia manejados
 * en el modulo de transferencias. Los tipos de numero de secuencia posibles son
 * tres: 1 (Previsión) 2 (Relación) 3 (Registro de entrada de una relación)
 */
public class NSec {
	String ano;
	int numsec;
	int tipo;
	String idarchivo;

	public NSec(String ano, int numsec, int tipo, String idarchivo) {
		this.ano = ano;
		this.numsec = numsec;
		this.tipo = tipo;
		this.idarchivo = idarchivo;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public int getNumsec() {
		return numsec;
	}

	public void setNumsec(int numsec) {
		this.numsec = numsec;
	}

	public String getIdarchivo() {
		return idarchivo;
	}

	public void setIdarchivo(String idarchivo) {
		this.idarchivo = idarchivo;
	}

}