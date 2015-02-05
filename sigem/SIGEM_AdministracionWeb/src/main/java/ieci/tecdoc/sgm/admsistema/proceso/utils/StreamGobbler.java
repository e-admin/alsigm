package ieci.tecdoc.sgm.admsistema.proceso.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

/** 
 * @author Iecisa 
 * @version $Revision$ 
 *
 */

public class StreamGobbler extends Thread {
		
    private InputStream in = null;
    private String type = null;
    private Logger logger = null;
    
    public StreamGobbler(InputStream in, String type) {
        this(in, type, Logger.getLogger(StreamGobbler.class));
    }

    public StreamGobbler(InputStream in, String type, Logger logger) {
        this.in = in;
        this.type = type;
        this.logger = logger;
    }

    public void run() {
        try {
        	
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line = null;
			while ((line = br.readLine()) != null) {
				logger.debug(type + "> " + line);
			}
			
		} catch (IOException ioe) {
			logger.error(type + "> ", ioe);
		}
    }
}

