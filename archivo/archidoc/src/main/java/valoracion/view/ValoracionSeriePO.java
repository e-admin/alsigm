package valoracion.view;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import util.CollectionUtils;
import valoracion.model.ValoracionSerie;
import valoracion.vos.ValoracionSerieVO;

import common.bi.GestionControlUsuariosBI;
import common.bi.GestionSeriesBI;
import common.bi.GestionValoracionBI;
import common.bi.ServiceRepository;
import common.exceptions.CheckedArchivoException;

import fondos.model.IdentificacionSerie;
import gcontrol.vos.UsuarioVO;

/**
 * Aglutina los datos necesarios para la presentación de valoraciones de serie
 */
public class ValoracionSeriePO extends ValoracionSerieVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	static final Logger logger = Logger.getLogger(ValoracionSeriePO.class);

	ServiceRepository services = null;
	GestionSeriesBI serieBI = null;
	GestionControlUsuariosBI usuarioBI = null;
	GestionValoracionBI valoracionBI = null;

	SeriePO serie = null;
	IdentificacionSerie serieExtendida = null;
	// ValoracionSerie serieExtendida = null;
	List seriesPrecedentes = null;
	List seriesDescendientes = null;
	List seriesRelacionadas = null;
	UsuarioVO gestor = null;
	SAXReader saxReader = null;
	boolean hasBeenChanged = false;
	// Información de la serie en el momento de cierre de la valoración actual
	ValoracionSerie serieExtendidaValoracion = null;

	public ValoracionSeriePO(ServiceRepository services) {
		super();
		this.services = services;
		this.serieBI = services.lookupGestionSeriesBI();
		this.usuarioBI = services.lookupGestionControlUsuariosBI();
		this.valoracionBI = services.lookupGestionValoracionBI();
	}

	public SeriePO getSerie() {
		if (this.serie == null)
			serie = (SeriePO) SerieToPO.getInstance(services).transform(
					serieBI.getSerie(getIdSerie()));
		return this.serie;
	}

	public ValoracionSerie getSerieExtendidaValoracion() {
		if (this.serieExtendidaValoracion == null)
			serieExtendidaValoracion = valoracionBI
					.getIdentificacionSerieValoracion(getInfoSerie(),
							getSerie());

		return this.serieExtendidaValoracion;
	}

	public IdentificacionSerie getSerieExtendida() {
		if (this.serieExtendida == null)
			serieExtendida = serieBI.getIdentificacionSerie(getSerie());

		return this.serieExtendida;
	}

	public List getListaSeriesPrecedentes() {
		if (seriesPrecedentes == null)
			try {
				seriesPrecedentes = parseListaSeries(getSeriesPrecedentes());
			} catch (CheckedArchivoException cae) {
				logger.error("Error obteniendo series precedentes para valoracion: "
						+ getId());
			}
		return seriesPrecedentes;
	}

	public List getListaSeriesDescendientes() {
		if (seriesDescendientes == null)
			try {
				seriesDescendientes = parseListaSeries(getSeriesDescendientes());
			} catch (CheckedArchivoException cae) {
				logger.error("Error obteniendo series descendientes para valoracion: "
						+ getId());
			}
		return seriesDescendientes;
	}

	public List getListaSeriesRelacionadas() {
		if (seriesRelacionadas == null)
			try {
				seriesRelacionadas = parseListaSeries(getSeriesRelacionadas());
			} catch (CheckedArchivoException cae) {
				logger.error("Error obteniendo series relacionadas para valoracion: "
						+ getId());
			}
		return seriesRelacionadas;
	}

	public List getListaSeriesRelacionadasConFondo() {
		List seriesConFondo = new ArrayList();

		List series = getListaSeriesRelacionadas();
		if (!CollectionUtils.isEmpty(series)) {
			InfoSerie serie;
			for (int i = 0; i < series.size(); i++) {
				serie = (InfoSerie) series.get(i);
				if (StringUtils.isNotBlank(serie.getId()))
					seriesConFondo.add(serie);
			}
		}

		return seriesConFondo;
	}

	public List getListaSeriesRelacionadasSinFondo() {
		List seriesSinFondo = new ArrayList();

		List series = getListaSeriesRelacionadas();
		if (!CollectionUtils.isEmpty(series)) {
			InfoSerie serie;
			for (int i = 0; i < series.size(); i++) {
				serie = (InfoSerie) series.get(i);
				if (StringUtils.isBlank(serie.getId()))
					seriesSinFondo.add(serie);
			}
		}

		return seriesSinFondo;
	}

	protected List parseListaSeries(String xml) throws CheckedArchivoException {
		SAXReader saxReader = getSAXReader();
		Document xinfoSeries = null;
		try {
			ArrayList series = new ArrayList();
			if (xml != null) {
				xinfoSeries = saxReader.read(new StringReader(xml));
				List nodes = xinfoSeries.selectNodes("/Series/Serie");
				Node aNode = null;
				for (Iterator i = nodes.iterator(); i.hasNext();) {
					aNode = (Node) i.next();
					series.add(new InfoSerie(aNode.valueOf("Id_Serie"), aNode
							.valueOf("Cod_Referencia"), aNode.valueOf("Titulo")));
				}
			}
			return series;
		} catch (DocumentException e) {
			logger.error("Error extrayendo lista de series de xml: " + xml);
			throw new CheckedArchivoException(e);
		}

	}

	private SAXReader getSAXReader() {
		if (saxReader == null)
			saxReader = new SAXReader();
		return saxReader;
	}

	public boolean getHasBeenChanged() {
		return hasBeenChanged;
	}

	public void setHasBeenChanged(boolean hasBeenChanged) {
		this.hasBeenChanged = hasBeenChanged;
	}

	public UsuarioVO getGestor() {
		if (this.gestor == null)
			this.gestor = usuarioBI.getUsuario(getIdUsuarioGestor());
		return gestor;
	}

	/*
	 * public ValoracionSerie getSerieExtendidaValoracion() { //if
	 * (this.serieExtendidaValoracion == null) // serieExtendidaValoracion =
	 * return this.serieExtendidaValoracion; }
	 */

	public String getContextoSerieValoracion() {
		StringBuffer codigo = new StringBuffer();

		if (!CollectionUtils.isEmpty(serieExtendidaValoracion
				.getAncestrosSerie())) {
			List ancestros = serieExtendidaValoracion.getAncestrosSerie();
			for (int i = 0; i < ancestros.size(); i++) {
				String ancestro = (String) ancestros.get(i);

				if (i > 0)
					codigo.append("\r\n");

				codigo.append(StringUtils.repeat("  ", i)).append(ancestro);

			}
		}
		return codigo.toString();
	}

}