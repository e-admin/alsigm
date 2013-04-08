package deposito.exceptions;

import common.exceptions.ActionNotAllowedException;

/**
 * Excepción lanzada cuando se intenta crear un depósito electrónico con un
 * identificador externo que ya existe.
 */
public class NumeracionHuecoRepetidaException extends ActionNotAllowedException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	String numeracionRepetida;

	/**
	 * Constructor.
	 */
	public NumeracionHuecoRepetidaException(String numeracionRepetida) {
		super(-1);
		this.numeracionRepetida = numeracionRepetida;
	}

	public String getNumeracionRepetida() {
		return numeracionRepetida;
	}

	public void setNumeracionRepetida(String numeracionRepetida) {
		this.numeracionRepetida = numeracionRepetida;
	}
}
