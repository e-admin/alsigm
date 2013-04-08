/*
 * invesflow Java - ISPAC
 * Fuente: ISchedulerAPI.java
 * Creado el 09-may-2005 por juanin
 *
 */
package ieci.tdw.ispac.api;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.context.ExpedientContext;

/**
 * @author juanin
 *
 */
public interface ISchedulerAPI
{
    public void notifyOutdatedProcess() throws ISPACException;

    public void notifyOutdatedStages() throws ISPACException;

    public void notifyOutdatedTask() throws ISPACException;

    public void executeOutdatedEvent(ExpedientContext expctx)
            throws ISPACException;
}