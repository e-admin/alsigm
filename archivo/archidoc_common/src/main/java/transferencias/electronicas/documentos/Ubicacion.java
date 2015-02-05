package transferencias.electronicas.documentos;

import transferencias.TransferenciasElectronicasConstants;

public class Ubicacion {
	private String tipo;
	private IRepositorio repositorio;
	private Binario binario;
	/**
	 * @return el tipo
	 */
	public String getTipo() {
		return tipo;
	}
	/**
	 * @param tipo el tipo a fijar
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	/**
	 * @return el repositorio
	 */
	public IRepositorio getRepositorio() {
		return repositorio;
	}
	/**
	 * @param repositorio el repositorio a fijar
	 */
	public void setRepositorio(IRepositorio repositorio) {
		this.repositorio = repositorio;
	}
	/**
	 * @return el binario
	 */
	public Binario getBinario() {
		return binario;
	}
	/**
	 * @param binario el binario a fijar
	 */
	public void setBinario(Binario binario) {
		this.binario = binario;
	}

	public boolean isBinario(){
		return TransferenciasElectronicasConstants.BINARIO.equalsIgnoreCase(tipo);
	}

	public boolean isRepositorio(){
		return TransferenciasElectronicasConstants.REPOSITORIO.equalsIgnoreCase(tipo);
	}

	public boolean isDepositoElectronico(){

		if(isRepositorio() && getRepositorio().isDepositoElectronico()){
			return true;
		}
		return false;
	}
}
