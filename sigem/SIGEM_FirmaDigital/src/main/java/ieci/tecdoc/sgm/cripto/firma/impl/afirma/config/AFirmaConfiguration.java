package ieci.tecdoc.sgm.cripto.firma.impl.afirma.config;

import ieci.tecdoc.sgm.cripto.firma.impl.bouncycastle.Constants;

import java.util.Properties;

public class AFirmaConfiguration {
	
	public static String FIRMA_AFIRMA_API_PATH_SERVICES="FIRMA_AFIRMA_API_PATH_SERVICES";
	public static String FIRMA_AFIRMA_API_SERVICE_FIRMAR="FIRMA_AFIRMA_API_SERVICE_FIRMAR";
	public static String FIRMA_AFIRMA_API_SERVICE_VERIFICAR="FIRMA_AFIRMA_API_SERVICE_VERIFICAR";
	public static String FIRMA_AFIRMA_API_SERVICE_STORAGE="FIRMA_AFIRMA_API_SERVICE_STORAGE";
	public static String FIRMA_AFIRMA_API_SERVICE_REGISTRAR_FIRMA="FIRMA_AFIRMA_API_SERVICE_REGISTRAR_FIRMA";
	public static String FIRMA_AFIRMA_API_APP_ID="FIRMA_AFIRMA_API_APP_ID";
	public static String FIRMA_AFIRMA_API_FIRMANTE="FIRMA_AFIRMA_API_FIRMANTE";
	public static String FIRMA_AFIRMA_API_REF_ID="FIRMA_AFIRMA_API_REF_ID";
	public static String FIRMA_AFIRMA_API_ALGO_HASH="FIRMA_AFIRMA_API_ALGO_HASH";
	public static String FIRMA_AFIRMA_API_FORMATO="FIRMA_AFIRMA_API_FORMATO";
	public static String FIRMA_AFIRMA_API_KEYSTORE="FIRMA_AFIRMA_API_KEYSTORE";
	public static String FIRMA_AFIRMA_API_PASSWORD="FIRMA_AFIRMA_API_PASSWORD";
	public static String FIRMA_AFIRMA_API_ALIAS="FIRMA_AFIRMA_API_ALIAS";
	public static String FIRMA_AFIRMA_API_KEYSTORE_TYPE="FIRMA_AFIRMA_API_KEYSTORE_TYPE";
	public static String FIRMA_AFIRMA_API_KEYSTORE_PROVIDER="FIRMA_AFIRMA_API_KEYSTORE_PROVIDER";
	public static String FIRMA_AFIRMA_API_CONFIG_FILE="FIRMA_AFIRMA_API_CONFIG_FILE";
	
	
    private String pathServicios;
    private String nombreServicioFirmar;
    private String nombreServicioVerificar;
    private String nombreServicioAlmacenarDocumento;
    public String nombreServicioRegistrarFirma;

    private String idAplicacion;
    private String firmante;
    private String idReferencia; // parametro deshabilitado
    private String algoritmoHash;
    private String formatoFirma;

    private Properties confSeguridad;
    
	private String keyStore	= Constants.KEY_STORE;
	private String password = Constants.PASSWORD;
	private String alias = 	  Constants.ALIAS;
	private String keyStoreType = Constants.KEY_STORE_TYPE;
	private String keyStoreProvider = Constants.KEY_STORE_PROVIDER;
	
	public String getPathServicios() {
		return pathServicios;
	}
	public void setPathServicios(String pathServicios) {
		this.pathServicios = pathServicios;
	}
	public String getNombreServicioFirmar() {
		return nombreServicioFirmar;
	}
	public void setNombreServicioFirmar(String nombreServicioFirmar) {
		this.nombreServicioFirmar = nombreServicioFirmar;
	}
	public String getNombreServicioVerificar() {
		return nombreServicioVerificar;
	}
	public void setNombreServicioVerificar(String nombreServicioVerificar) {
		this.nombreServicioVerificar = nombreServicioVerificar;
	}
	public String getNombreServicioAlmacenarDocumento() {
		return nombreServicioAlmacenarDocumento;
	}
	public void setNombreServicioAlmacenarDocumento(
			String nombreServicioAlmacenarDocumento) {
		this.nombreServicioAlmacenarDocumento = nombreServicioAlmacenarDocumento;
	}
	public String getNombreServicioRegistrarFirma() {
		return nombreServicioRegistrarFirma;
	}
	public void setNombreServicioRegistrarFirma(String nombreServicioRegistrarFirma) {
		this.nombreServicioRegistrarFirma = nombreServicioRegistrarFirma;
	}
	public String getIdAplicacion() {
		return idAplicacion;
	}
	public void setIdAplicacion(String idAplicacion) {
		this.idAplicacion = idAplicacion;
	}
	public String getFirmante() {
		return firmante;
	}
	public void setFirmante(String firmante) {
		this.firmante = firmante;
	}
	public String getIdReferencia() {
		return idReferencia;
	}
	public void setIdReferencia(String idReferencia) {
		this.idReferencia = idReferencia;
	}
	public String getAlgoritmoHash() {
		return algoritmoHash;
	}
	public void setAlgoritmoHash(String algoritmoHash) {
		this.algoritmoHash = algoritmoHash;
	}
	public String getFormatoFirma() {
		return formatoFirma;
	}
	public void setFormatoFirma(String formatoFirma) {
		this.formatoFirma = formatoFirma;
	}
	public Properties getConfSeguridad() {
		return confSeguridad;
	}
	public void setConfSeguridad(Properties confSeguridad) {
		this.confSeguridad = confSeguridad;
	}
	public String getKeyStore() {
		return keyStore;
	}
	public void setKeyStore(String keyStore) {
		this.keyStore = keyStore;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getKeyStoreType() {
		return keyStoreType;
	}
	public void setKeyStoreType(String keyStoreType) {
		this.keyStoreType = keyStoreType;
	}
	public String getKeyStoreProvider() {
		return keyStoreProvider;
	}
	public void setKeyStoreProvider(String keyStoreProvider) {
		this.keyStoreProvider = keyStoreProvider;
	}
}
