/*
 * invesflow Java - ISPAC
 *
 * $Source: /TEST/SIGEM/ispaclib/src/ieci/tdw/ispac/ispaclib/dao/MemberDAO.java,v $
 * $Revision: 1.4 $
 * $Date: 2009/02/11 09:23:47 $
 * $Author: ildefong $
 */
package ieci.tdw.ispac.ispaclib.dao;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.dao.dbmgr.DBColumn;
import ieci.tdw.ispac.ispaclib.utils.XmlTag;

import java.io.Serializable;
import java.sql.PreparedStatement;

/**
 *
 * @version $Revision: 1.4 $ $Date: 2009/02/11 09:23:47 $
 */
public class MemberDAO implements Serializable
{
	private boolean mbDirty;
	private Object mvalue;
	private final DBColumn mdbcol;

	public MemberDAO(DBColumn col, boolean bDirty)
	{
		mdbcol=col;
		mbDirty=bDirty;
		mvalue=null;
	}

	public Object value()
	{
		return mvalue;
	}

	public void setValue(Object value)
	{
		mvalue=value;
		mbDirty=true;
	}

	public void setObject(PreparedStatement ps,int nIdx)
	throws ISPACException
	{
	
		mdbcol.setObject(ps, nIdx, mvalue);
	}

	public boolean isDirty()
	{
		return mbDirty;
	}

	public void markDirty()
	{
		mbDirty=true;
	}
	public void cleanDirty()
	{
		mbDirty=false;
	}

	public String getName()
	{
		return mdbcol.getName();
	}

	public String getRawName()
	{
		return mdbcol.getRawName();
	}

	public Object getValue() {
		return mvalue;
	}
	
	public DBColumn getColumn()
	{
		return mdbcol;
	}

	public String toString()
	{
		return mdbcol.getMemberString(mvalue);
//		String value;
//		String valueClass;
//
//		if (mvalue!=null)
//		{
//			valueClass=mvalue.getClass().getName();
//			value=mvalue.toString();
//		}
//		else
//		{
//			valueClass="null";
//			value="null ";
//		}
//
//		return "Dirty: "+Boolean.toString(mbDirty)+" Value["+valueClass+"]: "+value+"\n\t"+
//				" DBColumn: "+mdbcol.toString()+"\n";
	}

	public String toXml()
	{
		//return XmlTag.newTag("value",mdbcol.getMemberString(mvalue),mdbcol.getOrdinal());
		return XmlTag.newTag("value", XmlTag.newCDATA(mdbcol.getMemberString(mvalue)), XmlTag.newAttr("name", mdbcol.getName()));
	}
}
