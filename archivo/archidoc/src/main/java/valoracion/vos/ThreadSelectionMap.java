package valoracion.vos;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ThreadSelectionMap {

	private Map idsEliminacion = Collections.synchronizedMap(new HashMap());

	public synchronized void insertarEliminacion(final String idEliminacion) {
		if (!idsEliminacion.containsKey(idEliminacion)) {
			idsEliminacion.put(idEliminacion, idEliminacion);
		}
	}

	public synchronized void borrarEliminacion(final String idEliminacion) {
		if (idsEliminacion.containsKey(idEliminacion)) {
			idsEliminacion.remove(idEliminacion);
		}
	}

	public synchronized boolean existeEliminacion(final String idEliminacion) {
		if (idsEliminacion.containsKey(idEliminacion)) {
			return true;
		}
		return false;
	}
}
