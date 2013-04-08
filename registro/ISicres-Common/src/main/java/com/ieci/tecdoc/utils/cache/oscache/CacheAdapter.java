//
// FileName: CacheAdapter.java
//
package com.ieci.tecdoc.utils.cache.oscache;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.keys.ConfigurationKeys;
import com.ieci.tecdoc.common.utils.Configurator;
import com.ieci.tecdoc.utils.cache.CacheBag;
import com.ieci.tecdoc.utils.cache.CacheFactory;
import com.ieci.tecdoc.utils.cache.CacheInterface;
import com.ieci.tecdoc.utils.cache.idgenerator.IDGeneratorFactory;
import com.opensymphony.oscache.base.Cache;
import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.base.events.CacheEntryEventListener;

/**
 * @author lmvicente
 * @version @since @creationDate 31-mar-2004
 */

public class CacheAdapter implements CacheInterface {

    /*******************************************************************************************************************
     * Attributes
     ******************************************************************************************************************/

    protected static final Logger log = Logger.getLogger(CacheAdapter.class);

    protected ExtendedCache cache = null;

    protected List generatedSessionsID = new ArrayList(50);

    private int sessionCount = 0;

    /*******************************************************************************************************************
     * Constructors
     ******************************************************************************************************************/

    public CacheAdapter() {
        cache = new ExtendedCache(true, true);

        if (log.isDebugEnabled()) {
            cache.addCacheEventListener(new CacheDebugger(), CacheEntryEventListener.class);
        }

        // Lanzamos el Thread de limpieza
        new Cleaner().start();
    }

    /*******************************************************************************************************************
     * Public methods
     ******************************************************************************************************************/

    public synchronized String createCacheEntry() throws SessionException {
        CacheBag cacheBag = new CacheBag();
        cacheBag.setSessionID(IDGeneratorFactory.getIDGenerator().getSessionID());

        cache.putInCache(cacheBag.getSessionID(), cacheBag, cacheBag);

        generatedSessionsID.add(cacheBag.getSessionID());

        sessionCount++;

        return cacheBag.getSessionID();
    }

    public synchronized CacheBag getCacheEntry(String sessionID) throws SessionException {
        if (sessionID == null) {
            throw new SessionException(SessionException.ERROR_SESSION_EXPIRED);
        }
        if (!generatedSessionsID.contains(sessionID)) {
            throw new SessionException(SessionException.ERROR_SESSION_EXPIRED);
        }

        CacheBag cacheBag = null;
        try {
            // Tratamos de recuperar la sesion, si no existe o esta caducada, se elevara una excepcion
            cacheBag = (CacheBag) cache.getFromCache(sessionID);
            // Esta comprobacion la hacemos por seguridad
            if (!cacheBag.isDeleted()) {
                // Refresca la entrada cuando se accede a ella, refresca el lastUpdate de CacheEntry
                cache.putInCache(cacheBag.getSessionID(), cacheBag, cacheBag);
            } else {
                throw new SessionException(SessionException.ERROR_SESSION_EXPIRED);
            }
        } catch (NeedsRefreshException e) {
            throw new SessionException(SessionException.ERROR_SESSION_EXPIRED);
        }

        return cacheBag;
    }

    public synchronized void removeCacheEntry(String sessionID) {
        if (sessionID != null) {
            try {
                CacheBag cacheBag = (CacheBag) cache.getFromCache(sessionID);
                cacheBag.setDeleted(true);
                //cache.flushEntry(sessionID);
                //cache.getFromCache(sessionID);
                cache.removeCacheBag(sessionID);
                generatedSessionsID.remove(sessionID);

                sessionCount--;

            } catch (NeedsRefreshException e) {
            }
        }
    }

    /*******************************************************************************************************************
     * Protected methods
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Private methods
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Inner classes
     ******************************************************************************************************************/

    /**
     * Extendemos la Cache para poder invocar al metodo removeEntry(key) que no es public.
     */
    public class ExtendedCache extends Cache {

        public ExtendedCache(boolean arg0, boolean arg1) {
            super(arg0, arg1);
        }

        public ExtendedCache(boolean arg0, boolean arg1, boolean arg2, String arg3, int arg4) {
            super(arg0, arg1, arg2, arg3, arg4);
        }

        public void removeCacheBag(String key) {
            super.removeEntry(key);
        }
    }

    public class Cleaner extends Thread {

        public Cleaner() {
            super("ISicres-SessionCache-Cleaner");
        }

        public void run() {
            StringBuffer buffer = null;
            List currentSessions = null;
            String sessionID = null;
            if (Integer.parseInt(Configurator.getInstance().getProperty(ConfigurationKeys.KEY_SERVER_CACHE_CLEANER_SLEEP_TIME)) != -1) {
                while (true) {
                    // Esperamos el tiempo de configuracion.
                    try {
                        Thread.sleep(CacheFactory.getCacheCleanerSleepTime());
                    } catch (InterruptedException e) {
                        log.fatal("can not stop ISicres-SessionCache-Cleaner", e);
                    }

                    if (log.isDebugEnabled()) {
                        log.debug("ISicres-SessionCache-Cleaner started.");
                    }

                    int deleted = 0;
                    int count = generatedSessionsID.size();
                    // Copiamos los sessionID para no incurrir en problemas de concurrencia
                    // No hacemos un clone por lo mismo
                    currentSessions = new ArrayList(count);
                    for (int i = 0; i < count; i++) {
                        currentSessions.add(generatedSessionsID.get(i));
                    }
                    // Proceso de borrado.
                    for (int i = 0; i < count; i++) {
                        sessionID = (String) currentSessions.get(i);
                        try {
                            cache.getFromCache(sessionID);
                        } catch (NeedsRefreshException sE) {
                            removeCacheEntry(sessionID);
                            deleted++;
                        } catch (Exception e) {
                            // Sacamos el mensaje, y el Thread sigue funcionando
                            log.error("Problems in ISicres-SessionCache-Cleaner.", e);
                        }
                    }
                    currentSessions.clear();

                    if (log.isDebugEnabled()) {
                        buffer = new StringBuffer();
                        buffer.append("ISicres-SessionCache-Cleaner stopped. Removed [");
                        buffer.append(deleted);
                        buffer.append("/");
                        buffer.append(count);
                        buffer.append("] sessions.");
                        log.debug(buffer.toString());
                    }
                }
            }
        }
    }

    /*******************************************************************************************************************
     * Test brench
     ******************************************************************************************************************/

}