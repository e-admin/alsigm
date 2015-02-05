package ieci.tdw.ispac.ispaclib.dao.cat;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.db.DbQuery;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.Types;

public class TemplateDataDAO extends ObjectDAO {

	private final static int CHUNK_SIZE = 10240;

	static final String TBNAME	= "SPAC_P_BLP";
	static final String PKNAME	= "ID";

	/**
	 * 
	 * @throws ISPACException
	 */
	public TemplateDataDAO() throws ISPACException {
		super(getVisibleProperties());
	}
	
	/**
	 * @param cnt
	 * @throws ISPACException
	 */
	public TemplateDataDAO(DbCnt cnt) throws ISPACException {
		super(cnt, getVisibleProperties());
	}

	/**
	 * 
	 * @param cnt
	 * @param id
	 * @throws ISPACException
	 */
	public TemplateDataDAO(DbCnt cnt, int id) throws ISPACException	{
		super(cnt, id, getVisibleProperties());
	}

	private static Property[] getVisibleProperties ()
	{
		Property[] properties = new Property[3];
		Property property = new Property (0, "ID", Types.INTEGER);
		properties[0] = property;
		property = new Property (1, "NBYTES", Types.INTEGER);
		properties[1] = property;
		property = new Property (2, "MIMETYPE", Types.VARCHAR);
		properties[2] = property;
		return properties;
	}

	public String getTableName() {
		return TBNAME;
	}

	protected String getDefaultSQL(int nActionDAO)
	throws ISPACException
	{	return " WHERE " + PKNAME + " = " + getInt(PKNAME);	}

	protected void newObject(DbCnt cnt)
	throws ISPACException
	{	set(PKNAME, 0);	}

	public String getKeyName()
	throws ISPACException
	{	return PKNAME;}

	public static void getBLOB(DbCnt cnt, int identifier, OutputStream stream)
	throws ISPACException
	{
		DbQuery rs = null;
		BufferedInputStream in = null;
		BufferedOutputStream out = new BufferedOutputStream(stream);

		try
		{
			String sSQL = "SELECT BLOQUE FROM " + TBNAME + " WHERE ID = " + identifier;
			rs = cnt.executeDbQuery(sSQL);
			if (rs.next())
			{
				byte[] chunk = new byte[CHUNK_SIZE];
				int bytes;
				if (cnt.isPostgreSQLDbEngine()){
					in = new BufferedInputStream(rs.getBinaryStream("BLOQUE"));
				}else{
					Blob blob = rs.getBLOB("BLOQUE");
					in = new BufferedInputStream(blob.getBinaryStream());
				}
				while ((bytes = in.read(chunk)) != -1)
				{
					out.write(chunk, 0, bytes);
				}
				out.flush();
			}
		}
		catch (Exception e)
		{
			throw new ISPACException(e);
		}
		finally
		{
			try
			{
				if (rs != null) rs.close();
				if (in != null)	in.close();
			}
			catch (Exception e)
			{}
		}
	}

	public static void setBLOB (DbCnt cnt, int id, InputStream stream,int length, String mimetype)
	throws ISPACException
	{
		PreparedStatement ps=null;
		boolean bTX = false;
		boolean creadorTX = false;

		try
		{
			if (cnt.ongoingTX())
				creadorTX = false;
			else{
				creadorTX = true;
			}
			cnt.openTX();

			StringBuffer sBuffer = new StringBuffer();
			sBuffer.append("DELETE FROM SPAC_P_BLP WHERE ID = ");
			sBuffer.append(id);
			cnt.directExec(sBuffer.toString());

			sBuffer.setLength(0);
			sBuffer.append("INSERT INTO SPAC_P_BLP (ID,NBYTES,MIMETYPE, BLOQUE) VALUES(");
			sBuffer.append(id);
			sBuffer.append(","+length);
			sBuffer.append(", '" + mimetype + "'");
			sBuffer.append(", ? )");

			ps=cnt.prepareStatement(sBuffer.toString());
			ps.setBinaryStream(1, stream, length);
			ps.execute();

			bTX = true;
		}
		catch (Exception e)
		{
			bTX = false;
			throw new ISPACException(e);
		}
		finally
		{
			try
			{
				if (ps != null)
				    ps.close();
//				if (cnt.ongoingTX())
//				    cnt.closeTX( bTX);
//				MODIFICADO !!!!!
				if (cnt.ongoingTX() && creadorTX==true)
				    cnt.closeTX( bTX);
			}
			catch (Exception e)
			{}
		}
	}
}
