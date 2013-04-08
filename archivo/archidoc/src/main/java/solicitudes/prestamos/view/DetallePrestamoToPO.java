package solicitudes.prestamos.view;

import org.apache.commons.collections.Transformer;

import common.bi.GestionCuadroClasificacionBI;
import common.bi.GestionDescripcionBI;
import common.bi.GestionPrestamosBI;
import common.bi.ServiceRepository;
import common.view.POUtils;

public class DetallePrestamoToPO implements Transformer {

	GestionCuadroClasificacionBI cuadroBI = null;
	GestionDescripcionBI descripcionBI = null;
	GestionPrestamosBI prestamoBI = null;
	boolean inicializarDatos = false;

	DetallePrestamoToPO(GestionCuadroClasificacionBI cuadroBI,
			GestionDescripcionBI descripcionBI, GestionPrestamosBI prestamoBI,
			boolean inicializarDatos) {
		this.cuadroBI = cuadroBI;
		this.descripcionBI = descripcionBI;
		this.prestamoBI = prestamoBI;
		this.inicializarDatos = inicializarDatos;
	}

	public Object transform(Object vo) {
		DetallePrestamoPO po = new DetallePrestamoPO(cuadroBI, descripcionBI,
				prestamoBI);
		POUtils.copyVOProperties(po, vo);

		if (po != null && inicializarDatos)
			po.getNombreRangos();

		return po;
	}

	public static DetallePrestamoToPO getInstance(ServiceRepository services,
			boolean inicializarDatos) {
		return new DetallePrestamoToPO(
				services.lookupGestionCuadroClasificacionBI(),
				services.lookupGestionDescripcionBI(),
				services.lookupGestionPrestamosBI(), inicializarDatos);
	}

}
