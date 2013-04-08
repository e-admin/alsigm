package descripcion.actions;

import java.util.List;

import common.bi.GestionDescripcionBI;
import common.bi.GestionDocumentosElectronicosBI;
import common.bi.ServiceRepository;
import common.util.ListUtils;
import common.util.StringUtils;
import common.view.POUtils;

import descripcion.vos.DescriptorVO;
import descripcion.vos.ListaDescrVO;
import docelectronicos.TipoObjeto;
import docelectronicos.vos.IRepositorioEcmVO;

public class DescriptorPO extends DescriptorVO {
	private static final long serialVersionUID = 7895843224469816527L;
	private Boolean conDocumentos = null;
	private Boolean conDescripcion = null;
	transient GestionDocumentosElectronicosBI documentosElectronicos = null;
	transient GestionDescripcionBI gestionDescripcion = null;
	private List repositoriosEcm = null;
	private String nombreListaVol = null;
	private String nombreLista = null;

	ServiceRepository services = null;

	public DescriptorPO(DescriptorVO descriptor, ServiceRepository services) {
		POUtils.copyVOProperties(this, descriptor);
		this.services = services;
	}

	private GestionDocumentosElectronicosBI getGestionDocumentosElectronicos() {
		if (documentosElectronicos == null)
			documentosElectronicos = services
					.lookupGestionDocumentosElectronicosBI();
		return documentosElectronicos;
	}

	private GestionDescripcionBI getGestionDescripcion() {
		if (gestionDescripcion == null)
			gestionDescripcion = services.lookupGestionDescripcionBI();
		return gestionDescripcion;
	}

	/**
	 * @return the conDocumentos
	 */
	public boolean isConDocumentos() {
		if (this.conDocumentos == null) {
			this.conDocumentos = new Boolean(getGestionDocumentosElectronicos()
					.tieneDocumentosAsociados(TipoObjeto.DESCRIPTOR,
							this.getId()));
		}

		return this.conDocumentos.booleanValue();
	}

	/**
	 * @return the conDescripcion
	 */
	public boolean isConDescripcion() {
		if (this.conDescripcion == null) {
			boolean conDescr = getGestionDescripcion().isConDescripcion(
					this.getId());
			this.conDescripcion = new Boolean(conDescr);
		}
		return this.conDescripcion.booleanValue();
	}

	public String getNombreRepEcm() {
		if (StringUtils.isNotBlank(getIdRepEcm())) {
			if (nombreListaVol == null) {
				nombreListaVol = getNombreRepositorioEcm(getIdRepEcm());
				setNombreRepEcm(nombreListaVol);
			}
		}

		return nombreListaVol;
	}

	/**
	 * Obtiene el nombre de repositorio ecm
	 * 
	 * @param repositoriosEcm
	 *            repositorio ecm.
	 * @param id
	 *            Identificador de repositorio ecm.
	 * @return Nombre del repositorio ecm
	 */
	private String getNombreRepositorioEcm(String id) {
		String nombre = null;
		if (repositoriosEcm == null) {
			repositoriosEcm = getGestionDocumentosElectronicos()
					.getRepositoriosEcm();
		}

		if (!ListUtils.isEmpty(repositoriosEcm)) {
			for (int i = 0; (nombre == null) && (i < repositoriosEcm.size()); i++) {
				IRepositorioEcmVO repositorioEcmVO = (IRepositorioEcmVO) repositoriosEcm
						.get(i);
				if (StringUtils.equals(id, repositorioEcmVO.getId()))
					nombre = repositorioEcmVO.getNombre();
			}
		}

		return nombre;
	}

	/**
	 * Metodo que devuelve el nombre de la lista
	 */
	public String getNombreLista() {
		ListaDescrVO lista;
		if (StringUtils.isNotBlank(getIdLista())) {
			if (nombreLista == null) {
				lista = getGestionDescripcion().getListaDescriptora(
						getIdLista());
				if (lista != null)
					nombreLista = lista.getNombre();
			}
		}
		return nombreLista;
	}
}
