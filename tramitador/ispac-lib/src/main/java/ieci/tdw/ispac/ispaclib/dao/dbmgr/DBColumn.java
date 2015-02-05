package ieci.tdw.ispac.ispaclib.dao.dbmgr;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.ispaclib.utils.TypeConverter;

import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringReader;
import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;

import org.apache.log4j.Logger;

/**
 * Clase inmutable que representa una columna de una tabla de BBDD.
 */
public final class DBColumn implements Serializable
{

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(DBColumn.class);
	
	private static final int DEFAULT_CLOB_BUFFER_LENGTH = 1024;
	
	public static final String PREFIX_ALIAS_EVEN = "A_0";
	public static final String PREFIX_ALIAS_ODD = "A_1";
	
	private final String msName;
	private final String msRawName;
	private final int mColType;
	private final int mnWidth;
	private final int mnOrdinal;
	private final int mnDecDigits;
	private final String msDescription;
	private final boolean mIsNullable;
	private final boolean mIsMultivalue ;
	private final String msTablename;

	public DBColumn(String tablename, int nOrdinal, String sName, int colType,
			int nWidth,int nDecDigits, String sDescription,boolean isNullable)
	{
		msTablename = tablename;
		mnOrdinal = nOrdinal;
		msName = sName.toUpperCase();
		msRawName = msName;
		mColType = colType;
		mnWidth = nWidth;
		mnDecDigits=nDecDigits;
		msDescription = sDescription;
		mIsNullable = isNullable;
		mIsMultivalue = false;
	}

	public DBColumn(String tablename,int nOrdinal, String sName,String sRawName, int colType,
			int nWidth,int nDecDigits, String sDescription,boolean isNullable, boolean isMultivalue)
	{
		msTablename = tablename;
		mnOrdinal = nOrdinal;
		msName = sName.toUpperCase();

		if (sRawName ==null)
		    msRawName = msName;
		else
		    msRawName = sRawName.toUpperCase();

		mColType = colType;
		mnWidth = nWidth;
		mnDecDigits=nDecDigits;
		msDescription = sDescription;
		mIsNullable = isNullable;
		mIsMultivalue = isMultivalue;
	}

	public DBColumn(String tablename, int nOrdinal,Property property)
	{
		msTablename = tablename;
		mnOrdinal = nOrdinal;
		msName = property.getName().toUpperCase();
		msRawName = property.getRawName().toUpperCase();
		mColType = property.getType();
		mnWidth = property.getSize();
		mnDecDigits = property.getDecimalDigits();
		msDescription = property.getTitle();
		mIsNullable = property.getIsNullable();
		mIsMultivalue = false;
	}

	public DBColumn shiftOrdinal(String tablename, int shift)
	{
	    if (shift==0)
	        return this;

	    return new DBColumn(tablename, mnOrdinal+shift,
				    		msName,
				    		msRawName,
				    		mColType,
				    		mnWidth,
				    		mnDecDigits,
				    		msDescription,
				    		mIsNullable, 
				    		mIsMultivalue);
	}

	public DBColumn rebuild(String tablename, int ordinal,String name)
	{

	    if (ordinal == mnOrdinal && name.equalsIgnoreCase(msName))
	        return this;

	    return new DBColumn(tablename, ordinal,
	            			name,
	            			msRawName,
				    		mColType,
				    		mnWidth,
				    		mnDecDigits,
				    		msDescription,
				    		mIsNullable, 
				    		mIsMultivalue);
	}
	/**
	 *
	 * @return El nombre del campo tal y como se mostrará en el ObjectDAO
	 */
	public String getName()
	{
		return msName;
	}

	/**
	 * El nombre de BD del campo para operar con JDBC.
	 * Si no se especifica lo contrario  getRawname() == getName()
	 * @return El nombre de BD del campo para operar con JDBC.
	 */

	public String getRawName()
	{
		return msRawName;
	}

	public String getDescription()
	{
		return msName;
	}
	public int getType()
	{
		return mColType;
	}
	public int getOrdinal()
	{
		return mnOrdinal;
	}
	public int getWidth()
	{
		return mnWidth;
	}
	public int getDecimalDigits()
	{
		return mnDecDigits;
	}
	public boolean getIsNullable()
	{
		return mIsNullable;
	}

	public void setObject(PreparedStatement ps,int nIdx,Object value)
	throws ISPACException
	{
		try
		{
			if (value==null)
			{
				ps.setNull(nIdx,mColType);
				return;
			}

			switch(mColType)
			{

				case Types.TIME:
				{
					Time tm=new Time(((Date)value).getTime());
					ps.setTime(nIdx,tm);
					break;
				}

				//TODO: Se trata de igual forma tipo DATE que TIMESTAMP.
				// Revisar tipos de datos de fecha
/*
				case Types.DATE:
				{
					java.sql.Date dt=new java.sql.Date(((Date)value).getTime());
					ps.setDate(nIdx,dt);
					break;
				}
*/
				case Types.DATE:
				case Types.TIMESTAMP:
				{
					Timestamp tms=new Timestamp(((Date)value).getTime());
					tms.setNanos(0);
					ps.setTimestamp(nIdx,tms);
					break;
				}
				case Types.BIT:
				case Types.TINYINT:
				case Types.SMALLINT:
				case Types.NUMERIC:
				case Types.INTEGER:
				case Types.BIGINT:
				case Types.DECIMAL:
				case Types.REAL:
				case Types.FLOAT:
				{
					if (value instanceof Short) {
                        ps.setShort(nIdx, ((Short) value).shortValue());
					} else if (value instanceof Integer) {
                        ps.setInt(nIdx, ((Integer) value).intValue());
                    } else if (value instanceof Long) {
                        ps.setLong(nIdx, ((Long) value).longValue());
                    } else if (value instanceof Float) {
                        ps.setFloat(nIdx, ((Float) value).floatValue());
                    } else if (value instanceof Double) {
                        ps.setDouble(nIdx, ((Double) value).doubleValue());
                    } else {
                        ps.setObject(nIdx,value,mColType);
                    }
                    break;
				}
                case Types.LONGVARCHAR:
                case Types.CLOB:
                {
                    String strvalue=value.toString();
                    Reader reader=new StringReader(strvalue);
                    ps.setCharacterStream(nIdx,reader,strvalue.length());
                    break;
                }
				default:
				{
					ps.setObject(nIdx,value,mColType);
					break;
				}
			}
		}
		catch(SQLException e)
		{
			throw new ISPACException(e);
		}
	}

	public Object getMember(ResultSet rs, String columnName)
	throws ISPACException
	{
		try
		{
		    if (rs.getObject(columnName)==null)
		        return null;
	
		    if (logger.isDebugEnabled()) {
		    	logger.debug("getMember: member=[" + this.toString()
		    			+ "], columnName=[" + columnName
		    			+ "], value=[" + rs.getObject(columnName) + "]");
		    }
		    
			switch(mColType)
			{
			case Types.BIT:
			case Types.TINYINT:
			case Types.SMALLINT:
				return new Short(rs.getShort(columnName));
	
			case Types.INTEGER:
				return new Integer(rs.getInt(columnName));
	
			case Types.BIGINT:
				return new Long(rs.getLong(columnName));
	
			case Types.REAL:
				//return new Float(rs.getDouble(columnName));
			case Types.FLOAT:
			case Types.DOUBLE:
				return new Double(rs.getDouble(columnName));
	
			case Types.NUMERIC:
			case Types.DECIMAL:
	        {
				if (mnDecDigits>0)
					return rs.getBigDecimal(columnName);
	
				return new Long(rs.getLong(columnName));
	        }
	
			case Types.CHAR:
			case Types.VARCHAR:
			case Types.LONGVARCHAR:
				return rs.getString(columnName);

			case Types.CLOB:
				return getClobString((Clob) rs.getObject(columnName));
	
			//TODO: Se trata de igual forma tipo DATE que TIMESTAMP.
			// Revisar tipos de datos de fecha
			case Types.DATE:
			    return rs.getTimestamp(columnName);
				//return rs.getDate(mnOrdinal);
			case Types.TIME:
				return rs.getTime(columnName);
			case Types.TIMESTAMP:
				return rs.getTimestamp(columnName);
	
			default:
				return rs.getObject(columnName);
			}
		}
		catch(Exception e)
		{
			logger.error("Error al obtener el member [" + columnName + "] para el mColType=[" + mColType + "]", e);
			throw new ISPACException(e);
		}
	}
	
	public String getMemberString(Object value)
	{
		return TypeConverter.getString(mColType,value);
	}

	public int getMemberInt(Object value)
	{
		return ((Integer)value).intValue();
	}

	public long getMemberLong(Object value)
	{
		return ((Long)value).longValue();
	}

	public short getMemberShort(Object value)
	{
		return ((Short)value).shortValue();
	}

	public float getMemberFloat(Object value)
	{
		return ((Float)value).floatValue();
	}

	public double getMemberDouble(Object value)
	{
		return ((Double)value).doubleValue();
	}

	public Date getMemberDate(Object value)
	{
		return (Date)value;
	}

	public boolean isMultivalue()
	{
		return mIsMultivalue;
	}

	public String getTablename(){
		return msTablename;
	}
	
	public String toString()
	{
		return 	" Ordinal: "+mnOrdinal +
				" Name: "+msName +
				" RawName: "+msRawName+
				" ColType: "+ mColType +
				" Width: "+ mnWidth +
				" DecDigits: "+ mnDecDigits +
				" Desc: " + msDescription+
				" IsNullable "+ Boolean.toString(mIsNullable)+
				" IsMultivalue "+ Boolean.toString(mIsMultivalue);
	}
	
	protected String getClobString(Clob clob) throws SQLException, IOException {
		
//		InputStream in = clob.getAsciiStream();
//		byte []buffer = new byte[4096];
//		ByteArrayOutputStream out = new ByteArrayOutputStream();
//		while (true)
//		{
//			int count = in.read( buffer);
//			if (count == -1) break;
//			out.write( buffer, 0, count);
//			 }
//		return out.toString();

//		Reader reader = clob.getCharacterStream();
//    	char [] buffer = new char[(int) clob.length()];
//    	int count = reader.read(buffer);
//    	reader.close();
//    	return new String(buffer, 0, count);

    	StringBuffer result = new StringBuffer();
		
		if (clob != null) {
			Reader reader = clob.getCharacterStream();
			
			// Calcular el tamaño del buffer
			int bufferLength = (int) clob.length();
			if (bufferLength <= 0) {
				bufferLength = DEFAULT_CLOB_BUFFER_LENGTH;
			}
			
	    	char [] buffer = new char[bufferLength];
	    	for (int count = reader.read(buffer); count > -1; count = reader.read(buffer)) {
	    		result.append(buffer, 0, count);
	    	}
	    	reader.close();
		}
		
		return result.toString();
	}
}
