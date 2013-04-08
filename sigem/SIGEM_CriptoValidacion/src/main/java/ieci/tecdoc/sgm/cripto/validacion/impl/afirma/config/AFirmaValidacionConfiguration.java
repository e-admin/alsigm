package ieci.tecdoc.sgm.cripto.validacion.impl.afirma.config;

import java.util.Properties;

public class AFirmaValidacionConfiguration {

	public static String  VALIDA_AFIRMA_API_PATH_SERVICES="VALIDA_AFIRMA_API_PATH_SERVICES";
	public static String  VALIDA_AFIRMA_API_SERVICE_VALIDAR="VALIDA_AFIRMA_API_SERVICE_VALIDAR";
	public static String  VALIDA_AFIRMA_API_APP_ID="VALIDA_AFIRMA_API_APP_ID";
	public static String  VALIDA_AFIRMA_API_VALID_MODE="VALIDA_AFIRMA_API_VALID_MODE";
	public static String  VALIDA_AFIRMA_API_ALGO_HASH="VALIDA_AFIRMA_API_ALGO_HASH";
	public static String  VALIDA_AFIRMA_API_CONFIG_FILE="VALIDA_AFIRMA_API_CONFIG_FILE";

    private String provider=    "BC";
    private String algorithm=	"SHA1";

    private String idAplicacion;
    private String modoValidacion;

    private String pathServicios;
    private String nombreServicioValidarCertificado;

    private Properties confSeguridad;

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public String getIdAplicacion() {
		return idAplicacion;
	}

	public void setIdAplicacion(String idAplicacion) {
		this.idAplicacion = idAplicacion;
	}

	public String getModoValidacion() {
		return modoValidacion;
	}

	public void setModoValidacion(String modoValidacion) {
		this.modoValidacion = modoValidacion;
	}

	public String getPathServicios() {
		return pathServicios;
	}

	public void setPathServicios(String pathServicios) {
		this.pathServicios = pathServicios;
	}

	public String getNombreServicioValidarCertificado() {
		return nombreServicioValidarCertificado;
	}

	public void setNombreServicioValidarCertificado(
			String nombreServicioValidarCertificado) {
		this.nombreServicioValidarCertificado = nombreServicioValidarCertificado;
	}

	public Properties getConfSeguridad() {
		return confSeguridad;
	}

	public void setConfSeguridad(Properties confSeguridad) {
		this.confSeguridad = confSeguridad;
	}
}
