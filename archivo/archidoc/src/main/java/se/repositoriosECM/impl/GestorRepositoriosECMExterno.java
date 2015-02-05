package se.repositoriosECM.impl;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import se.ficheros.exceptions.GestorFicherosException;
import se.repositoriosECM.EcmSprinUtils;
import se.repositoriosECM.vo.RepositorioEcmInternoVO;

import common.util.ListUtils;

import docelectronicos.vos.IRepositorioEcmVO;
import docelectronicos.vos.RepositorioEcmVO;
import es.ieci.tecdoc.fwktd.dm.business.config.Configuration;
import es.ieci.tecdoc.fwktd.dm.business.config.ContentType;

public class GestorRepositoriosECMExterno extends GestorRepositoriosECM {

	/** Logger de la clase. */
	private static final Logger logger = Logger
			.getLogger(GestorRepositoriosECMExterno.class);

	private Properties params;

	@Override
	public void initialize(Properties params) {
		this.params = params;
		loadRepositoriosEcm();
	}

	public Properties getParams(){
		return params;
	}

	private void loadRepositoriosEcm() throws GestorFicherosException {
		establecerEntidad();
		List<ContentType> listaContentTypes = getConfiguration()
				.getContentTypes();

		mapRepositoriosEcm = new LinkedHashMap<String, IRepositorioEcmVO>();

		if (ListUtils.isNotEmpty(listaContentTypes)) {
			for (Iterator<ContentType> iterator = listaContentTypes.iterator(); iterator
					.hasNext();) {
				ContentType contentType = iterator.next();

				IRepositorioEcmVO repositorioEcm = getRepositorioFromContent(contentType);

				if (repositorioEcm != null) {
					addRepositorio(repositorioEcm.getId(), repositorioEcm);
				}
			}
		}

	}

	private IRepositorioEcmVO getRepositorioFromContent(ContentType contentType) {
		if (contentType != null) {
			if (IRepositorioEcmVO.REPOSITORIO_ECM_INTERNO.equals(contentType
					.getType())) {
				return new RepositorioEcmInternoVO(contentType);
			} else {
				return new RepositorioEcmVO(contentType);
			}
		}
		return null;
	}

	private Configuration getConfiguration() {
		return EcmSprinUtils.getConfiguration();
	}

}
