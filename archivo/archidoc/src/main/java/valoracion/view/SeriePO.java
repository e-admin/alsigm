package valoracion.view;

import java.util.List;

import se.usuarios.AppUser;
import util.CollectionUtils;
import valoracion.vos.ValoracionSerieVO;

import common.bi.GestionControlUsuariosBI;
import common.bi.GestionCuadroClasificacionBI;
import common.bi.GestionFondosBI;
import common.bi.GestionValoracionBI;
import common.bi.ServiceRepository;
import common.util.StringUtils;

import fondos.model.Serie;
import fondos.utils.FondosUtils;
import fondos.vos.ElementoCuadroClasificacionVO;
import fondos.vos.FondoVO;
import gcontrol.view.UsuarioPO;
import gcontrol.vos.UsuarioVO;

public class SeriePO extends Serie {

	private UsuarioPO gestor = null;
	private FondoVO fondo = null;
	private ValoracionSerieVO valoracion = null;

	ServiceRepository services = null;
	GestionCuadroClasificacionBI cuadroBI = null;
	GestionControlUsuariosBI usuariosBI = null;
	GestionFondosBI fondosBI = null;
	GestionValoracionBI valoracionBI = null;
	AppUser user = null;

	public SeriePO(ServiceRepository services) {
		super();
		this.services = services;
		this.cuadroBI = services.lookupGestionCuadroClasificacionBI();
		this.usuariosBI = services.lookupGestionControlUsuariosBI();
		this.fondosBI = services.lookupGestionFondosBI();
		this.valoracionBI = services.lookupGestionValoracionBI();
	}

	public ValoracionSerieVO getValoracion() {
		if (valoracion == null) {
			valoracion = (ValoracionSerieVO) valoracionBI
					.getValoracionDictaminada(getId());
		}
		return valoracion;
	}

	public UsuarioPO getGestor() {
		String idGestor = getIdusrgestor();
		if (idGestor != null && this.gestor == null) {
			UsuarioVO user = usuariosBI.getUsuario(idGestor);
			this.gestor = new UsuarioPO(user, services);
		}
		return this.gestor;
	}

	public FondoVO getFondo() {
		if (fondo == null)
			fondo = fondosBI.getFondoXId(getIdFondo());
		return fondo;
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

				codigo.append(StringUtils.repeat("  ", i)).append(
						ancestro.getCodigoTitulo());
			}

		}
		return codigo.toString();
	}

	public AppUser getUser() {
		return user;
	}

	public void setUser(AppUser user) {
		this.user = user;
	}

	public String getCodReferenciaPersonalizado() {
		try {
			return FondosUtils.getCodReferenciaPorSecciones(user, getIdFondo(),
					getCodReferencia(), services);
		} catch (Exception e) {
			logger.error(
					"Error al Obtener el código de referencia personalizado de la Serie.",
					e);
			return null;
		}
	}

}
