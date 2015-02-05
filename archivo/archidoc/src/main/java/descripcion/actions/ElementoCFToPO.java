package descripcion.actions;

import java.util.Map;

import org.apache.commons.collections.Transformer;

import xml.config.Busqueda;

import common.bi.GestionCuadroClasificacionBI;
import common.bi.GestionDescripcionBI;
import common.bi.ServiceRepository;
import common.view.POUtils;

import descripcion.vos.ElementoCFPO;

/**
 * Transforma value objects de prevision en objetos para presentacion
 * 
 */
public class ElementoCFToPO implements Transformer {
	GestionCuadroClasificacionBI cuadroBI = null;
	GestionDescripcionBI descripcionBI = null;
	ServiceRepository services = null;
	Busqueda busqueda = null;
	Map mapProductores = null;
	Map mapInteresados = null;

	ElementoCFToPO(GestionCuadroClasificacionBI cuadroBI,
			GestionDescripcionBI descripcionBI, ServiceRepository services,
			Busqueda busqueda, Map mapProductores, Map mapInteresados) {
		this.cuadroBI = cuadroBI;
		this.descripcionBI = descripcionBI;
		this.services = services;
		this.busqueda = busqueda;
		this.mapProductores = mapProductores;
		this.mapInteresados = mapInteresados;
	}

	public Object transform(Object vo) {
		ElementoCFPO po = new ElementoCFPO(services, busqueda, mapProductores,
				mapInteresados);
		POUtils.copyVOProperties(po, vo);
		return po;
	}

	public static ElementoCFToPO getInstance(ServiceRepository services,
			Busqueda busqueda, Map mapProductores, Map mapInteresados) {
		return new ElementoCFToPO(
				services.lookupGestionCuadroClasificacionBI(),
				services.lookupGestionDescripcionBI(), services, busqueda,
				mapProductores, mapInteresados);
	}
}