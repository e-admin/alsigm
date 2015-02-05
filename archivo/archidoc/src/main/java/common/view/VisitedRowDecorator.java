package common.view;

import org.displaytag.decorator.TableDecorator;

public class VisitedRowDecorator extends TableDecorator {

	/**
	 * Cambiar el estilo de una fila para el display tag.
	 */
	public String addRowClass() {
		// Interface para generalizar el objeto de la fila
		IVisitedRowVO fila = (IVisitedRowVO) this.getCurrentRowObject();
		// Clase de estilo CSS de la fila
		return fila.getRowStyle();
	}
}