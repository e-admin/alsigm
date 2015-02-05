package solicitudes.consultas.view;

import org.apache.commons.collections.Transformer;

import common.bi.GestionCuadroClasificacionBI;
import common.bi.GestionDescripcionBI;
import common.bi.ServiceRepository;
import common.view.POUtils;

public class DetalleConsultaToPO implements Transformer {

	GestionCuadroClasificacionBI cuadroBI = null;
	GestionDescripcionBI descripcionBI = null;
	boolean inicializarDatos = false;

	DetalleConsultaToPO(GestionCuadroClasificacionBI cuadroBI,
			GestionDescripcionBI descripcionBI, boolean inicializarDatos) {
		this.cuadroBI = cuadroBI;
		this.descripcionBI = descripcionBI;
		this.inicializarDatos = inicializarDatos;
	}

	public Object transform(Object vo) {
		DetalleConsultaPO po = new DetalleConsultaPO(cuadroBI, descripcionBI);
		POUtils.copyVOProperties(po, vo);

		if (po != null && inicializarDatos)
			po.getNombreRangos();

		return po;
	}

	public static DetalleConsultaToPO getInstance(ServiceRepository services,
			boolean inicializarDatos) {
		return new DetalleConsultaToPO(
				services.lookupGestionCuadroClasificacionBI(),
				services.lookupGestionDescripcionBI(), inicializarDatos);
	}

}
