package se.repositoriosECM.impl;

import ieci.tecdoc.core.db.DbConnectionConfig;
import ieci.tecdoc.idoc.admin.api.volume.VolumeList;
import ieci.tecdoc.idoc.admin.internal.VolumeListsImpl;

import java.util.LinkedHashMap;
import java.util.Properties;

import org.apache.log4j.Logger;

import se.ficheros.exceptions.GestorFicherosException;
import se.repositoriosECM.vo.MotorDocumental;
import se.repositoriosECM.vo.RepositorioEcmInternoVO;
import util.MultiEntityUtil;
import xml.config.ConfiguracionAlmacenamiento;
import xml.config.ConfiguracionSistemaArchivoFactory;

import common.MultiEntityConstants;
import common.util.StringUtils;

import docelectronicos.vos.IRepositorioEcmVO;

public class GestorRepositoriosECMInterno extends GestorRepositoriosECM {

	private DbConnectionConfig dbConnectionConfig;

	/** Logger de la clase. */
	private static final Logger logger = Logger
			.getLogger(GestorRepositoriosECMInterno.class);

	private Properties params;

	protected Properties getParams(){
		return params;
	}

	@Override
	public void initialize(Properties params) throws GestorFicherosException {

		this.params = params;
		try {

			String entity = null;
			// Buscar la propiedad entidad
			if ((params != null)
					&& (params.containsKey(MultiEntityConstants.ENTITY_PARAM))) {
				entity = (String) params.get(MultiEntityConstants.ENTITY_PARAM);
			}

			// Obtener el dataSource de almacenamiento
			String dataSource = ConfiguracionSistemaArchivoFactory
					.getConfiguracionSistemaArchivo()
					.getConfiguracionAlmacenamiento()
					.getDataSourceNameAlmacenamiento();
			if (StringUtils.isNotEmpty(entity)) {
				dataSource = MultiEntityUtil.composeDsName(dataSource, entity);
			}
			dbConnectionConfig = new DbConnectionConfig(dataSource, null, null);

			loadRepositoriosEcm();
		} catch (Exception e) {
			logger.error("Error al inicial el gestor de repositorios interno",
					e);
			throw new GestorFicherosException(e, this.getClass().getName(),
					"Error al inicial el gestor de repositorios interno");
		}
	}

	private void loadRepositoriosEcm() throws GestorFicherosException {
		try {
			VolumeListsImpl volLists = new VolumeListsImpl();

			volLists.setConnectionConfig(dbConnectionConfig);

			volLists.load();

			mapRepositoriosEcm = new LinkedHashMap<String, IRepositorioEcmVO>();

			ConfiguracionAlmacenamiento cfgAlmacenamiento = ConfiguracionSistemaArchivoFactory
					.getConfiguracionSistemaArchivo()
					.getConfiguracionAlmacenamiento();

			String dataSourceName = cfgAlmacenamiento
					.getDataSourceNameAlmacenamiento();
			String plataformaMotorDocumental = cfgAlmacenamiento
					.getPlataformaMotorDocumental();
			String rutaPlataformaMotorDocumental = cfgAlmacenamiento
					.getRutaPlataformaMotorDocumental();
			String extensionesMotorDocumental = cfgAlmacenamiento
					.getExtensionesMotorDocumental();
			MotorDocumental motorDocumental = new MotorDocumental(
					plataformaMotorDocumental, rutaPlataformaMotorDocumental,
					extensionesMotorDocumental);

			// Crear una lista de objetos
			for (int i = 0; i < volLists.count(); i++) {
				VolumeList volList = volLists.get(i);

				if (volList != null) {
					String idLista = "" + volList.getId();

					IRepositorioEcmVO listaVolumenes = new RepositorioEcmInternoVO(
							idLista, volList.getName(), dataSourceName,
							motorDocumental);

					addRepositorio(listaVolumenes.getId(), listaVolumenes);
				}
			}
		} catch (Exception e) {
			logger.error(e);
			throw new GestorFicherosException(e,
					"GestorListasVolumenesInvesdoc",
					"Error al obtener las listas de volumenes");
		}
	}
}
