package ieci.tdw.ispac.ispacpublicador.business.reader;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.util.ISPACConfiguration;
import ieci.tdw.ispac.ispaclib.utils.ClassLoaderUtil;
import ieci.tdw.ispac.ispacpublicador.business.config.ConfigurationParams;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * Clase para la gestión de un Lector. Se encargará de obtenerlo
 * según la configuración del fichero de propiedades, que indica
 * el nombre de la clase que implementa el lector.
 */
public class ReaderMgr {

    /** Instancia de la clase. */
    private static ReaderMgr instance = null;
    
    /** Cache con los lectores que se van utilizandocon los lectuLector. */
    private Map mapReaders = new LinkedHashMap();
    
    
    /**
     * Constructor.
     *
     */
    private ReaderMgr() {}
    
    /**
     * Obtiene una instancia de la clase.
     * @return Instancia de la clase.
     */
    public static synchronized ReaderMgr getInstance() {
    	if (instance == null) {
    		instance = new ReaderMgr();
    	}
    	
    	return instance;
    }
    
	/**
	 * Obtiene la instancia de un lector.
	 * @param className Nombre de la clase del lector a instanciar.
	 * @return una instancia del Lector cuya clase es <code>'classNmae'</code>. 
	 * Se comprueba si ya se ha obtenido una instancia anterior, en cuyo caso 
	 * estaría almacenada en la cache, si no lo está se crea una nueva y se 
	 * cachea para futuros usos.
	 * @throws ISPACException si ocurre algún error.
	 */
	public IReader instanceReader(String className) throws ISPACException {
		IReader reader = (IReader) mapReaders.get(className);
		if (reader == null) {
		    reader = (IReader) ClassLoaderUtil.getInstance(className);
		    mapReaders.put(className, reader);
		}
		return reader;             
	}

  
	/**
	 * Obtiene la lista de nombres de clases de los lectores.
	 * @return Lista de nombres de clases.
	 * @throws ISPACException si ocurre algún error.
	 */
	private List getClassReaders() throws ISPACException {
		List classReaders = new LinkedList();
		ISPACConfiguration config = ISPACConfiguration.getInstance();
		Enumeration names = config.propertyNames();
		String propName;
		while (names.hasMoreElements()) {
			propName = (String) names.nextElement();
			if (propName.startsWith(ConfigurationParams.READER_PREFIX)) {
				classReaders.add(config.get(propName));
			}
		}
		
		return classReaders;
	}

    /**
     * Obtiene la lista de lectores dados de alta en la aplicación.
     * @return una lista que contiene instancias de todos los lectores dados de 
     * alta para la aplicación.
     * @throws ISPACException si ocurre algún error.
     */
    public List getReaders() throws ISPACException {
        List readers = new LinkedList();
        List classReaders = getClassReaders();
        String className;
        for (Iterator iter = classReaders.iterator(); iter.hasNext();) {
            className = (String) iter.next();
		    readers.add((IReader)ClassLoaderUtil.getInstance(className));
        }
        return readers;
    }  
}
