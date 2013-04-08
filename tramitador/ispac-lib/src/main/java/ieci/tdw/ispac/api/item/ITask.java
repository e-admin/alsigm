/*
 * Created on 27-jul-2004
 *
 */
package ieci.tdw.ispac.api.item;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.db.DbCnt;


/**
 *
 *
 */
public interface ITask extends IItem
{
	public int OPEN = 1;
	public int CANCELED = 3;
	public int SIMPLE_TASK_TYPE = 1;
	public int COMPLEX_TASK_TYPE = 2;
	public String getDeadlineXML(DbCnt cnt) throws ISPACException;
	
	public boolean isSimple() throws ISPACException;
	public boolean isComplex() throws ISPACException;
}
