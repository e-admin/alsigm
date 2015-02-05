package ieci.tecdoc.sgm.antivirus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

public class Informacion extends Thread {
	
	private static final Logger logger = Logger.getLogger(Informacion.class);
	
	private InputStream is;
    private String type;
    
    public Informacion(InputStream is, String type)
    {
        this.is = is;
        this.type = type;
    }
    
    public void run() {
    	try {
    		InputStreamReader isr = new InputStreamReader(is);
    		BufferedReader br = new BufferedReader(isr);
    		String line=null;
    		while ( (line = br.readLine()) != null)
                logger.debug(type + " >>> " + line);    
    	} catch (IOException ioe) {
    		logger.debug("Error en la ejecucion del proceso de antivirus [run][IOException]", ioe.fillInStackTrace());  
    	}
	}
}
