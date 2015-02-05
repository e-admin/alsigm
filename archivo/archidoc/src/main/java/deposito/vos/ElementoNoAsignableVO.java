package deposito.vos;

/**
 * Value Object en el que mantener la información referente a los elementos no
 * asignables que integran el depósito físico gestionado por el sistema
 */
public class ElementoNoAsignableVO extends ElementoVO {

	public boolean isAsignable() {
		return false;
	}
}
