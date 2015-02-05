
package es.ieci.tecdoc.isicres.admin.core.db;

public final class DbColumnDef
{

   private String  m_name;
   private int     m_dataType;
   private int     m_maxLen;
   private boolean m_nullable;
   private String  m_default = null; // @SF-SEVILLA - 23/3/2005

   public DbColumnDef(String name, int dataType, boolean nullable)
   {
      m_name     = name;
      m_dataType = dataType;
      m_maxLen   = 0;
      m_nullable = nullable;
   }

   public DbColumnDef(String name, int dataType, int maxLen, boolean nullable)
   {
      m_name     = name;
      m_dataType = dataType;
      m_maxLen   = maxLen;
      m_nullable = nullable;
   }

   /**
    * Constructor de definicion de campo al que se añade un parametro mas, 
    * que es si tiene un valor por defecto asociado.
    * SEVILLA - 23/3/2005
    * @param name
    * @param dataType
    * @param maxLen
    * @param nullable
    */
   public DbColumnDef(String name, int dataType, int maxLen, boolean nullable, String defaultValue)
   {
      this(name,dataType,maxLen,nullable);
      m_default = defaultValue;
   }
   /**
    * Constructor de definicion de campo al que se añade un parametro mas, 
    * que es si tiene un valor por defecto asociado. Es usado si el campo no es de tipo texto
    * SEVILLA - 23/3/2005
    * @param name
    * @param dataType
    * @param nullable
    * @param defaultValue
    */
   public DbColumnDef(String name, int dataType, boolean nullable, String defaultValue)
   {
	  this(name,dataType,0,nullable,defaultValue);      
   }
   

   public String getName()
   {
      return m_name;
   }
   
   public int getDataType()
   {
      return m_dataType;
   }
   
   public int getMaxLen()
   {
      return m_maxLen;
   }
   
   public boolean isNullable()
   {
      return m_nullable;
   }
   /**
    * Devuelve esta definicion de campo como un string. Al hacer llamada a
    * DbConnection.getEngine() para obtener el motor de la bd, se necesita que
    * exista una conexion abierta.
    * SEVILLA - 27/3/2005
    * @param isFull Si es true añade la subcadena NOT NULL, o NULL, si el cambio es obligatorio o no
    * @return
    */
   public String toStringValue(boolean isFull){
	   	StringBuffer buff = new StringBuffer();
	   	int engine;
	   	String version;
	   	int dataType = this.m_dataType;
	   	String type = null;
	   	try {
	   		engine = DbConnection.getEngine();
		   	version = DbConnection.getEngineVersion();
	   		// engine = DbEngine.ORACLE;
			// engine = DbEngine.POSTGRESQL;

			// version = DbEngine.ORACLE_VERSION_9;
	   		type = DbDataType.getNativeDbType(dataType,engine,version,9);
	   		buff.append(type);
	   		if (dataType == DbDataType.SHORT_TEXT ){
	   			buff.append("(");
	   			buff.append(m_maxLen);
	   			buff.append(")");
	   		}
	   		if (m_default != null ){
	   			buff.append(" DEFAULT ");
	   			buff.append(m_default);
	   		}
	   		if (isFull){
	   			if (!isNullable())
	   				buff.append(" NOT NULL ");
	   			else
	   				buff.append(" NULL ");
	   		}	   				   		
	   	}catch (Exception ex){
	   		ex.printStackTrace();
	   	}
	   	
	   	return buff.toString();
	   
   }
   
   /**
    * Devuelve esta definicion de campo como un string. Al hacer llamada a
    * DbConnection.getEngine() para obtener el motor de la bd, se necesita que
    * exista una conexion abierta.
    * SEVILLA - 23/3/2005
    */
   public String toString(){
	   return toStringValue(false);
   }

} // class
