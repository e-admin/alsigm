package se.repositoriosECM.impl;

import ieci.tecdoc.idoc.admin.api.volume.VolumeList;
import ieci.tecdoc.idoc.admin.internal.VolumeListsImpl;

import java.util.LinkedHashMap;
import java.util.Properties;

import org.apache.log4j.Logger;

import se.ficheros.exceptions.GestorFicherosException;
import se.repositoriosECM.vo.MotorDocumental;
import se.repositoriosECM.vo.RepositorioEcmInternoVO;
import xml.config.ConfiguracionAlmacenamiento;
import xml.config.ConfiguracionSistemaArchivoFactory;
import docelectronicos.vos.IRepositorioEcmVO;

public class GestorRepositoriosECMInternoInvesdoc8 extends GestorRepositoriosECM {


	/** Logger de la clase. */
	private static final Logger logger = Logger
			.getLogger(GestorRepositoriosECMInternoInvesdoc8.class);

	private Properties params;

	protected Properties getParams(){
		return params;
	}

	@Override
	public void initialize(Properties params) throws GestorFicherosException {
		this.params = params;
		try {
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

			//volLists.setConnectionConfig(dbConnectionConfig);

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
