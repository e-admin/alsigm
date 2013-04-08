package fondos.view;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.tiles.ComponentContext;
import org.apache.struts.tiles.Controller;

import util.CollectionUtils;
import valoracion.vos.EliminacionSerieVO;
import valoracion.vos.ValoracionSerieVO;

import common.bi.GestionControlUsuariosBI;
import common.bi.GestionCuadroClasificacionBI;
import common.bi.GestionSeriesBI;
import common.bi.ServiceRepository;
import common.util.StringUtils;

import fondos.FondosConstants;
import fondos.model.EstadoSerie;
import fondos.model.Serie;
import fondos.vos.ElementoCuadroClasificacionVO;
import fondos.vos.OpcionesDescripcionSerieVO;
import fondos.vos.SerieVO;
import gcontrol.view.UsuarioPO;
import gcontrol.vos.ListaAccesoVO;
import gcontrol.vos.UsuarioVO;

public class SeriePO extends Serie implements Controller {

	String codigoReferencia = null;
	UsuarioPO gestor = null;
	OpcionesDescripcionSerieVO opcionesDescripcion = null;
	List volumenSerie = null;

	ServiceRepository services = null;
	GestionCuadroClasificacionBI cuadroBI = null;
	GestionControlUsuariosBI usuariosBI = null;
	GestionSeriesBI serieBI = null;
	ListaAccesoVO listaControlAcceso = null;

	ValoracionSerieVO valoracion = null;
	EliminacionSerieVO seleccion = null;

	public SeriePO(SerieVO vo, ServiceRepository services) {
		try {
			BeanUtils.copyProperties(this, vo);
			this.services = services;
			this.cuadroBI = services.lookupGestionCuadroClasificacionBI();
			this.usuariosBI = services.lookupGestionControlUsuariosBI();

		} catch (Exception e) {
		}
	}

	public void perform(ComponentContext tilesContext,
			HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext) throws ServletException, IOException {
		SerieVO serie = (SerieVO) request
				.getAttribute(FondosConstants.SERIE_KEY);
		try {
			BeanUtils.copyProperties(this, serie);
			request.setAttribute(FondosConstants.SERIE_KEY, this);
		} catch (Exception e) {

		}
	}

	public UsuarioPO getGestor() {
		String idGestor = getIdusrgestor();
		if (idGestor != null && this.gestor == null) {
			UsuarioVO user = usuariosBI.getUsuario(idGestor);
			if (user != null)
				this.gestor = new UsuarioPO(user, services);
		}
		return this.gestor;
	}

	public boolean getContieneUnidadesDocumentales() {
		boolean contieneUnidadesDocumentales = false;
		if (getEstadoserie() != EstadoSerie.NO_VIGENTE)
			contieneUnidadesDocumentales = getSerieBI()
					.getCountUnidadesDocumentales(getId()) > 0;
		return contieneUnidadesDocumentales;
	}

	public OpcionesDescripcionSerieVO getOpcionesDescripcion() {
		if (opcionesDescripcion == null)
			this.opcionesDescripcion = getSerieBI()
					.getOpcionesDescripcionSerie(this);
		return opcionesDescripcion;
	}

	private GestionSeriesBI getSerieBI() {
		if (serieBI == null)
			serieBI = services.lookupGestionSeriesBI();
		return serieBI;
	}

	public ValoracionSerieVO getValoracionDictaminada() {
		if (valoracion == null)
			valoracion = services.lookupGestionValoracionBI()
					.getValoracionDictaminada(getId());
		return valoracion;
	}

	public EliminacionSerieVO getSeleccion() {
		if (seleccion == null)
			seleccion = services.lookupGestionEliminacionBI()
					.getEliminacionSerie(getId());
		return seleccion;
	}

	public List getVolumenSerie() {
		if (volumenSerie == null)
			volumenSerie = serieBI.getVolumenesYSoporteSerie(getId());
		return volumenSerie;
	}

	public ListaAccesoVO getListaControlAcceso() {
		if ((listaControlAcceso == null) && StringUtils.isNotBlank(getIdLCA()))
			listaControlAcceso = usuariosBI.getListaAcceso(getIdLCA());
		return listaControlAcceso;
	}

	public String getContexto() {
		StringBuffer codigo = new StringBuffer();
		List ancestros = cuadroBI.getAncestors(getId());
		if (!CollectionUtils.isEmpty(ancestros)) {
			for (int i = 0; i < ancestros.size(); i++) {
				ElementoCuadroClasificacionVO ancestro = (ElementoCuadroClasificacionVO) ancestros
						.get(i);

				if (i > 0)
					codigo.append("\r\n");

				codigo.append(StringUtils.repeat("  ", i));
				codigo.append(ancestro.getCodigoTitulo());
			}

		}
		return codigo.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.struts.tiles.Controller#execute(org.apache.struts.tiles.
	 * ComponentContext, javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, javax.servlet.ServletContext)
	 */
	public void execute(ComponentContext arg0, HttpServletRequest arg1,
			HttpServletResponse arg2, ServletContext arg3) throws Exception {
		// TODO Auto-generated method stub

	}

}
