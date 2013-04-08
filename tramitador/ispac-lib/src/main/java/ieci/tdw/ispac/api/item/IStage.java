/*
 * Created on 27-jul-2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package ieci.tdw.ispac.api.item;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.db.DbCnt;


/**
 *
 */
public interface IStage extends IItem
{
	public int OPEN = 1;
	public int CANCELED = 3;
	public int STAGE_TYPE = 1;
	public int ACTIVITY_TYPE = 2;
	
	public String getDeadlineXML(DbCnt cnt) throws ISPACException;
	
	public boolean isActivity() throws ISPACException;

	public boolean isStage() throws ISPACException;
}
