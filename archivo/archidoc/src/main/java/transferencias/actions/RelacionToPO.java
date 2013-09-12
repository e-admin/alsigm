package transferencias.actions;

import java.util.Properties;

import org.apache.commons.collections.Transformer;
import org.apache.log4j.Logger;

import se.procedimientos.GestorCatalogo;
import se.procedimientos.GestorCatalogoFactory;
import se.procedimientos.exceptions.GestorCatalogoException;
import se.usuarios.ServiceClient;

import common.MultiEntityConstants;
import common.bi.GestionControlUsuariosBI;
import common.bi.GestionCuadroClasificacionBI;
import common.bi.GestionDescripcionBI;
import common.bi.GestionFondosBI;
import common.bi.GestionPrevisionesBI;
import common.bi.GestionRelacionesEACRBI;
import common.bi.GestionRelacionesEntregaBI;
import common.bi.GestionSeriesBI;
import common.bi.GestionSistemaBI;
import common.bi.ServiceRepository;
import common.exceptions.UncheckedArchivoException;
import common.util.StringUtils;
import common.view.POUtils;

import deposito.model.GestorEstructuraDepositoBI;

public class RelacionToPO implements Transformer {

	private final static Logger logger = Logger.getLogger(RelacionToPO.class);

	GestionControlUsuariosBI controlAccesoBI = null;
	GestorCatalogo catalogoProcedimientos = null;
	GestorEstructuraDepositoBI depositoBI = null;
	GestionPrevisionesBI previsionBI = null;
	GestionFondosBI fondoBI = null;
	GestionSeriesBI serieBI = null;
	GestionCuadroClasificacionBI cclfBI = null;
	GestionRelacionesEntregaBI relacionBI = null;
	GestionSistemaBI sistemaBI = null;
	GestionDescripcionBI descripcionBI = null;
	ServiceRepository services = null;
	GestionRelacionesEACRBI relacionEABI = null;

	RelacionToPO(GestionRelacionesEntregaBI relacionBI,
			GestionControlUsuariosBI controlAccesoBI,
			GestorCatalogo catalogoProcedimientos,
			GestorEstructuraDepositoBI depositoBI,
			GestionPrevisionesBI previsionBI, GestionFondosBI fondoBI,
			GestionSeriesBI serieBI, GestionCuadroClasificacionBI cclfBI,
			GestionSistemaBI sistemaBI, GestionDescripcionBI descripcionBI,
			ServiceRepository services, GestionRelacionesEACRBI relacionEABI) {
		this.relacionBI = relacionBI;
		this.controlAccesoBI = controlAccesoBI;
		this.catalogoProcedimientos = catalogoProcedimientos;
		this.depositoBI = depositoBI;
		this.previsionBI = previsionBI;
		this.fondoBI = fondoBI;
		this.serieBI = serieBI;
		this.cclfBI = cclfBI;
		this.sistemaBI = sistemaBI;
		this.descripcionBI = descripcionBI;
		this.services = services;
		this.relacionEABI = relacionEABI;
	}

	public Object transform(Object vo) {
		RelacionEntregaPO po = new RelacionEntregaPO(relacionBI,
				controlAccesoBI, catalogoProcedimientos, depositoBI,
				previsionBI, fondoBI, serieBI, cclfBI, sistemaBI,
				descripcionBI, services, relacionEABI);
		POUtils.copyVOProperties(po, vo);
		return po;
	}

	public static RelacionToPO getInstance(ServiceRepository services) {


			// Obtener información de la entidad
			ServiceClient serviceClient = services.getServiceClient();

			// Obtener la entidad para el usuario conectado
			Properties params = null;

			if ((serviceClient != null)
					&& (StringUtils.isNotEmpty(serviceClient.getEntity()))) {
				params = new Properties();
				params.put(MultiEntityConstants.ENTITY_PARAM,
						serviceClient.getEntity());
			}

			GestorCatalogo catalogoService = null;
			try {
				catalogoService = GestorCatalogoFactory
				.getConnector(params);
			} catch (GestorCatalogoException e) {
				logger.error("Error al obtener el gestor de catálogo");
			}

			return new RelacionToPO(services.lookupGestionRelacionesBI(),
					services.lookupGestionControlUsuariosBI(), catalogoService,
					services.lookupGestorEstructuraDepositoBI(),
					services.lookupGestionPrevisionesBI(),
					services.lookupGestionFondosBI(),
					services.lookupGestionSeriesBI(),
					services.lookupGestionCuadroClasificacionBI(),
					services.lookupGestionSistemaBI(),
					services.lookupGestionDescripcionBI(), services,
					services.lookupGestionRelacionesEACRBI());

	}

}
