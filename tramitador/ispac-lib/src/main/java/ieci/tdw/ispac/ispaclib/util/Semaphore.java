package ieci.tdw.ispac.ispaclib.util;

public class Semaphore
{

    private int mCount;
    private int mWaiting;

    public Semaphore(int count)
    {
        mCount = count;
        mWaiting = 0;
    }

    /**
     * Obtiene acceso al semáforo esperando como mucho <b>timeout </b>
     * milisegundos
     *
     * @param timeout. Tiempo máximo de espera en milisegundos
     * @return <ul>
     * <li><b>true </b> si se ha obtenido acceso al recurso
     * <li><b>false </b> si ha dado timeout o la hebra ha sido interrumpida
     * </ul>
     */
    public synchronized boolean acquire(long timeout)
    {
        if (mCount <= 0 || mWaiting>0)
        {
            try
            {
                mWaiting++;
                wait(timeout);
                mWaiting--;
            }
            catch (InterruptedException e)
            {
                return false;
            }
            if (mCount == 0)
                return false;
        }
        mCount--;
        return true;
    }

    public synchronized void release()
    {
        mCount++;
        if (mWaiting>0)
            notify();
    }
}