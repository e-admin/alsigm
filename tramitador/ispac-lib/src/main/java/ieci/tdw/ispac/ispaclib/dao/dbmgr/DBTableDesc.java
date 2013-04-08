/*
 * Created on 06-abr-2004
 *
 */
package ieci.tdw.ispac.ispaclib.dao.dbmgr;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.Properties;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.ispaclib.util.PrefixBuilder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author juanin
 *
 */
public class DBTableDesc extends LinkedHashMap
{
	private static final long serialVersionUID = 1L;
	
	protected final String mtablename;
	protected final Date mtimestamp = new Date();

	public DBTableDesc(String tablename)
	{
		mtablename=tablename;
	}

	public DBTableDesc(String tablename, ResultSet rsTableDesc)
	throws ISPACException
	{
		mtablename=tablename;
		processColumns(tablename,rsTableDesc,null);
	}

	public DBTableDesc(String tablename, ResultSet rsTableDesc,String prefix)
	throws ISPACException
	{
		mtablename=tablename;
		processColumns(tablename,rsTableDesc,prefix);
	}

	public DBTableDesc(String tablename, ResultSet rsTableDesc, String prefix, Property[] tableColumns)
	throws ISPACException
	{
		mtablename=tablename;
		processColumns(tablename, rsTableDesc,prefix,tableColumns);
	}

	public DBTableDesc(String tablename, Property[] tableColumns)
	{
		mtablename=tablename;
		processColumns(tablename, tableColumns);
	}

	public String getName()
	{
		return mtablename;
	}
	
	public Date getTimestamp()
	{	
		return mtimestamp;
	}

	private void processColumns(String tablename, ResultSet rsTableDesc,String prefix)
		throws ISPACException
	{
		DBColumn column=null;
		try
		{
			while (rsTableDesc.next())
			{
				column=createColumn(tablename, rsTableDesc,prefix);
				put(column.getName().toUpperCase(),column);
			}
		}
		catch(SQLException e)
		{
		    throw new ISPACException("DBTableDesc.processColumns() - ",e);
		}
	}

	private void processColumns(String tablename, ResultSet rsTableDesc,String prefix,Property[] tableColumns)
	throws ISPACException
	{
		LinkedHashMap columns = new LinkedHashMap();
		DBColumn column = null;

		try
		{
			while (rsTableDesc.next())
			{
		        String name = rsTableDesc.getString("COLUMN_NAME");
		        column = createColumn(tablename, rsTableDesc,prefix);
		        if (name!=null)
		            name=name.toUpperCase();
		        columns.put(name,column);
			}
		}
		catch(SQLException e)
		{
		    throw new ISPACException("DBTableDesc.processColumns() - ",e);
		}

		for (int i = 0; i < tableColumns.length; i++)
		{
			Property property = tableColumns[i];

			if (!property.isDefined())
			{
			    // Propiedad parcialmente definida.
				// Utilizar la columna que se indica de la tabla.
			    column=processProperty(tablename, i +1,prefix,property,columns);
			}
			else
			{
				column = new DBColumn(tablename, i +1, property);
			}
			put( column.getName(), column);
		}
	}

	private DBColumn processProperty(String tablename, int ordinal,String prefix,Property property,Map columnsmap)
	throws ISPACException
	{
	    String name=property.getName();

	    //Para poder renombrar una propiedad y seguir sin especificar su tipo en BBDD.
	    //Para ello se especifica rawname y name al crear la propiedad sin definir.
	    String sKey = property.getRawName();
	    if (sKey!=null)
	        sKey=sKey.toUpperCase();
		if (!columnsmap.containsKey(sKey))
		    throw new ISPACException( "No existe la columna " + getName() + "." + sKey);

		DBColumn column = (DBColumn) columnsmap.get( sKey);

		if (prefix!=null)
		    name=PrefixBuilder.buildName(prefix,name);

		return column.rebuild(tablename, ordinal,name);
	}

	private void processColumns(String tablename, Property[] tableColumns)
	{
		DBColumn column=null;
		for (int i = 0; i < tableColumns.length; i++)
		{
			column=new DBColumn(tablename, i+1,tableColumns[i]);
			put(column.getName(),column);
		}
	}

	private DBColumn createColumn(String tablename,ResultSet rsTableDesc,String prefix)
		throws SQLException
	{
	    String name=null;
	    String rawname=null;
	    if (prefix ==null)
	    {
	        name=rsTableDesc.getString("COLUMN_NAME");
		    rawname=name;
	    }
	    else
	    {
	        name=PrefixBuilder.buildName(prefix,rsTableDesc.getString("COLUMN_NAME"));
	        rawname=PrefixBuilder.buildRawName(prefix,rsTableDesc.getString("COLUMN_NAME"));
	        //name=prefix+":"+rsTableDesc.getString("COLUMN_NAME");
		    //rawname=prefix.replace(':','_')+"."+rsTableDesc.getString("COLUMN_NAME");
	    }

		return new DBColumn(tablename,
				rsTableDesc.getInt("ORDINAL_POSITION"),
				name,
				rawname,
				rsTableDesc.getInt("DATA_TYPE"),
				rsTableDesc.getInt("COLUMN_SIZE"),
				rsTableDesc.getInt("DECIMAL_DIGITS"),
				rsTableDesc.getString("REMARKS"),
				rsTableDesc.getString("NULLABLE").compareToIgnoreCase("YES") == 0,false);
	}

	public DBColumn getColumn(String sColName)
	{
		return (DBColumn)get(sColName);
	}

	public Iterator iterator()
	{
		return entrySet().iterator();
	}

	public DBColumn getColumn(Iterator it)
	{
		return (DBColumn)(((Map.Entry)it.next()).getValue());
	}

	public String getColsSQL() {
		String cols="";
		String separator = "";
		Iterator it = iterator();
		while (it.hasNext())
		{
			DBColumn col = getColumn(it);
			if (!col.isMultivalue()){
				int hashCode = col.getRawName().hashCode();
				String prefix = DBColumn.PREFIX_ALIAS_EVEN;
				if (hashCode > 0)
					prefix = DBColumn.PREFIX_ALIAS_ODD;
				String alias = prefix + Math.abs(hashCode);
				cols += separator + col.getRawName() + " AS " + alias;
				separator = ", ";
			}
		}
		return cols;
	}

	/**
	 * @return
	 */
	public Properties getProperties()
	{
		Properties properties = new Properties();

		Iterator it = iterator();
		while (it.hasNext())
		{
			DBColumn col=getColumn(it);
			properties.add(new Property(col.getOrdinal(),col.getName(),col.getType(),col.getWidth(),
					col.getDecimalDigits(),col.getDescription(),col.getIsNullable(),col.isMultivalue()));
		}
		return properties;
	}

	/**
	 * @return
	 */
	public Property getProperty(String name)
	{
		Property property = null;

		DBColumn col=getColumn(name);
		if (col != null)
		{
		    property =  new Property(col.getOrdinal(),col.getName(),col.getType(),col.getWidth(),
					col.getDecimalDigits(),col.getDescription(),col.getIsNullable(),col.isMultivalue());
		}
		return property;
	}

	public List getMultivalueCols() {
		return null;
	}

}