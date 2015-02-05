package configuracion.model;

import java.lang.reflect.Constructor;

import common.bi.ServiceBase;
import common.reports.PdfReportPageEventHelper;
import common.util.StringUtils;
import common.vos.DatosGeograficosVO;
import common.vos.PaisesVO;

import configuracion.bi.GestionInfoSistemaBI;
import configuracion.db.IInfoSistemaDBEntity;

public class GestionInfoSistemaBIImpl extends ServiceBase implements
		GestionInfoSistemaBI {

	IInfoSistemaDBEntity infoSistema = null;
	private final String DEFAULT_MANEJADOR_ITEXT = "common.reports.PdfReportPageEventHelper";

	public GestionInfoSistemaBIImpl(IInfoSistemaDBEntity infoSistema) {
		this.infoSistema = infoSistema;
	}

	public PaisesVO getPaises() {
		return infoSistema.getMapPaises();
	}

	public DatosGeograficosVO getDatosGeograficosDefecto() {
		DatosGeograficosVO datosGeograficosVO;

		datosGeograficosVO = infoSistema.getDatosGeograficos();

		// if(datosGeograficosVO == null) {
		//
		// //Obtener los Geograficos del Archivo-cfg.xml
		// String codigoPais = (String) ((DefaultMapEntry)
		// ConfiguracionSistemaArchivoFactory.getConfiguracionSistemaArchivo().getConfiguracionTransferencias().getPais()).getKey();
		// String nombrePais = (String) ((DefaultMapEntry)
		// ConfiguracionSistemaArchivoFactory.getConfiguracionSistemaArchivo().getConfiguracionTransferencias().getPais()).getValue();
		// String codigoProvincia = (String) ((DefaultMapEntry)
		// ConfiguracionSistemaArchivoFactory.getConfiguracionSistemaArchivo().getConfiguracionTransferencias().getProvincia()).getKey();
		// String nombreProvincia = (String) ((DefaultMapEntry)
		// ConfiguracionSistemaArchivoFactory.getConfiguracionSistemaArchivo().getConfiguracionTransferencias().getProvincia()).getValue();
		//
		// datosGeograficosVO = new DatosGeograficosVO();
		// datosGeograficosVO.setCodigoPais(codigoPais);
		// datosGeograficosVO.setNombrePais(nombrePais);
		// datosGeograficosVO.setCodigoProvincia(codigoProvincia);
		// datosGeograficosVO.setNombreProvincia(nombreProvincia);
		// }

		return datosGeograficosVO;

	}

	public PdfReportPageEventHelper getManejadorIText() {
		String manejadorIText = infoSistema.getManejadorIText();
		Class claseManejador = null;
		Object manejador = null;
		Class[] constructorArgs = new Class[] { String.class };
		try {
			if (StringUtils.isEmpty(manejadorIText)) {
				claseManejador = Class.forName(DEFAULT_MANEJADOR_ITEXT);
			} else {
				claseManejador = Class.forName(manejadorIText);
			}
			Constructor constructor = claseManejador
					.getConstructor(constructorArgs);
			manejador = (PdfReportPageEventHelper) constructor
					.newInstance(new Object[] { getServiceClient().getEntity() });
		} catch (Throwable e) {
			// si se lanza alguna excepcion
			try {
				claseManejador = Class.forName(DEFAULT_MANEJADOR_ITEXT);
				Constructor constructor = claseManejador
						.getConstructor(constructorArgs);
				manejador = constructor
						.newInstance(new Object[] { getServiceClient()
								.getEntity() });
			} catch (Throwable e2) {
				return new PdfReportPageEventHelper(getServiceClient()
						.getEntity());
			}
		}
		return (PdfReportPageEventHelper) manejador;
	}
}
