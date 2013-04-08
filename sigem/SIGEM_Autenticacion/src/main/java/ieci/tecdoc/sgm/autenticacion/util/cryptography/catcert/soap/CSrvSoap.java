package ieci.tecdoc.sgm.autenticacion.util.cryptography.catcert.soap;

import ieci.tecdoc.sgm.autenticacion.util.cryptography.catcert.soap.conf.SoapEnvInfo;
import ieci.tecdoc.sgm.core.config.ports.PortsConfig;

import java.net.URL;
import java.util.Enumeration;
import java.util.Vector;

import javax.xml.namespace.QName;
import javax.xml.rpc.Call;
import javax.xml.rpc.ServiceFactory;





/** 
 * Esta clase permite un soporte básico para encapsular servicios cliente Soap.
 *
 */
public class CSrvSoap implements ISoap {
    // valores por defecto.
    
    static String mServerhost= "localhost";
    static int mServerport;
    static String mSoapContext= "/axis/services/";
    static String mEndPoint;
    private String mService;
    private QName  mReturnType;
    private URL    mUrl;
    private Object mResp;
    
    static {
    	try {
			mServerport = Integer.valueOf(PortsConfig.getHttpPort()).intValue();
		} catch (NumberFormatException e) {
		}	
    }
    
    /**
     * @param urn  */
    public  CSrvSoap( SoapEnvInfo conf) throws Exception {
        
        // Inicializa los parámetros de conexión.
        mService = conf.getService();
        mServerhost= conf.getHost();
        mServerport= conf.getPort();
        mSoapContext = conf.getContext();
        mEndPoint = mSoapContext + mService;
        // Aqui se deberia hacer la lectura de un fichero de inicialización
        mUrl = new URL("http", getSoapHost(), getSoapPort(), mEndPoint);
        
    }
    
    
    /** Host Soap
     */
    public String getSoapHost() { return mServerhost; }
    /** Puerto Soap
     */
    public int getSoapPort() { return mServerport; }
    /** Router Soap
     */
    public String getSoapEndPoint() { return mEndPoint; }
    /** URN a implementar
     */
    
    public QName getReturnType() { return mReturnType;}
    
    /** Url del router Soap
     */
    public URL    getUrl() { return mUrl;}
    
    /** Utilizado para invocar a un método del servicio Soap
     * @param params Una colección con los parámetros de entrada del método.
     * @param nomMetodo Nombre del método que se va a invocar.
     * @return Código retornado por el método. En invesDoc siempre es un valor númerico
     * con el código de error.
     */
    public Object invokeMethod(  Vector parametersIn, QName returnValue, String method) throws Exception {
       
        // Obtiene un servicio genérico
        javax.xml.rpc.Service service = ServiceFactory.newInstance().createService(new QName(""));

        String url = mUrl.toString();  
        System.out.println("Url:" + url);
        
        // Service  service = new Service();
        Call     call    = (Call) service.createCall();
              
        call.setTargetEndpointAddress( url);
        
        Object values[] = new Object[ parametersIn.size()];
        int n=0;
        Object obj = null;
        SrvParameter param = null;
        for (Enumeration e = parametersIn.elements(); e.hasMoreElements();) {
            obj = e.nextElement();            
            param = (SrvParameter)obj;
            call.addParameter( param.getName(), param.getQname(), param.getMode());
            values[ n] = param.getValue();
            n++;
        }
                       
        call.setReturnType( returnValue);
        call.setOperationName( new QName( mService, method) );
        
        mResp = (Object) call.invoke(values);
        
        
        return mResp;
    }
    
    
    
    /** Recupera en Object genérico un único parametro de vuelta
     */
    public Object getParameter() {
        return  mResp;
    }
    
    
}


