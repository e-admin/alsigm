package ieci.tecdoc.sgm.cripto.validacion.impl.bouncycastle.config;

public class ValidacionBCConfiguration {
	public static String  VALIDA_SIGEM_API_PROVIDER="VALIDA_SIGEM_API_PROVIDER";
	public static String  VALIDA_SIGEM_API_ALGO="VALIDA_SIGEM_API_ALGO";
    // configurable con spring
    private String provider;
    private String algorithm;
    
    
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
    
    
	
}
