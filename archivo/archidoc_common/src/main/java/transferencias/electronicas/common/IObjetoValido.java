package transferencias.electronicas.common;


public interface IObjetoValido {

	/**
	 * Comprueba si un objeto es válido.
	 * @return
	 */
	public void validate();

	public boolean isConErrores();
}
