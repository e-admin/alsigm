package deposito.vos;

import common.vos.BaseVO;

public class ReubicacionUDocsVO extends BaseVO {
	private static final long serialVersionUID = -5655698694832899487L;

	private int posicionOrigen;
	private int posicionDestino;
	private String signaturaOrigen;
	private String signaturaDestino;
	private String titulo;

	public ReubicacionUDocsVO(UDocEnUiDepositoVO udocOrigen,
			UDocEnUiDepositoVO udocDestino) {
		if (udocOrigen != null) {
			this.posicionOrigen = udocOrigen.getPosudocenui();
			this.signaturaOrigen = udocOrigen.getSignaturaudoc();

		}
		if (udocDestino != null) {
			this.posicionDestino = udocDestino.getPosudocenui();
			this.signaturaDestino = udocDestino.getSignaturaudoc();
			this.titulo = udocOrigen.getTitulo();
		}
	}

	public int getPosicionOrigen() {
		return posicionOrigen;
	}

	public void setPosicionOrigen(int posicionOrigen) {
		this.posicionOrigen = posicionOrigen;
	}

	public int getPosicionDestino() {
		return posicionDestino;
	}

	public void setPosicionDestino(int posicionDestino) {
		this.posicionDestino = posicionDestino;
	}

	public String getSignaturaOrigen() {
		return signaturaOrigen;
	}

	public void setSignaturaOrigen(String signaturaOrigen) {
		this.signaturaOrigen = signaturaOrigen;
	}

	public String getSignaturaDestino() {
		return signaturaDestino;
	}

	public void setSignaturaDestino(String signaturaDestino) {
		this.signaturaDestino = signaturaDestino;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
}
