package valoracion.view;

import java.util.List;

import se.usuarios.ServiceClient;
import valoracion.utils.XMLtoCriterio;
import valoracion.vos.EliminacionSerieVO;
import valoracion.vos.ValoracionSerieVO;

import common.bi.GestionSeriesBI;
import common.bi.GestionValoracionBI;
import common.bi.ServiceRepository;

import fondos.vos.SerieVO;

public class EliminacionPO extends EliminacionSerieVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private SerieVO serie = null;
	private ValoracionSerieVO valoracion = null;

	GestionSeriesBI serieBI = null;
	GestionValoracionBI valoracionBI = null;
	ServiceRepository services = null;

	public EliminacionPO(GestionSeriesBI serieBI,
			GestionValoracionBI valoracionBI, ServiceRepository services) {
		super();

		this.valoracionBI = valoracionBI;
		this.serieBI = serieBI;
		this.services = services;
	}

	public ValoracionSerieVO getValoracion() {
		if (valoracion == null) {
			valoracion = valoracionBI.getValoracion(getIdValoracion());
		}

		return valoracion;
	}

	public SerieVO getSerie() {
		if (serie == null) {
			serie = serieBI.getSerie(getIdSerie());
		}

		return serie;
	}

	public List getListaCriterios() {
		ServiceClient client = services.getServiceClient();
		XMLtoCriterio criterio = new XMLtoCriterio(client.getEngine());
		return criterio.transform(getCondicionBusqueda());
	}
}
