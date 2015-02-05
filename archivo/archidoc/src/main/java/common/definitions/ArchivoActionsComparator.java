package common.definitions;

import java.util.Comparator;

import auditoria.vos.ArchivoAction;

/**
 * Clase para comparar acciones de búsqueda
 * 
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class ArchivoActionsComparator implements Comparator {
	public int compare(Object emp1, Object emp2) {

		ArchivoAction action1 = ((ArchivoAction) emp1);
		ArchivoAction action2 = ((ArchivoAction) emp2);
		if (action1.getId() > action2.getId())
			return 1;
		else if (action1.getId() < action2.getId())
			return -1;
		else
			return 0;
	}
}