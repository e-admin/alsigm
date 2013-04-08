package es.ieci.tecdoc.isicres.admin.database;


import es.ieci.tecdoc.isicres.admin.core.db.DbConnectionConfig;
import es.ieci.tecdoc.isicres.admin.estructura.keys.ISicresAdminDefsKeys;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.dao.DaoArchDetTbl;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.dao.DaoArchHdrTbl;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.dao.DaoArchListTbl;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.dao.DaoDATNodeTbl;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.dao.DaoXNextIdTbl;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.vldtable.std.VldDaoBTblCtlgTbl;


public class ArchivesTable
{
   public ArchivesTable()
   {

   }

   public static String TN_NEXTID = DbConnectionConfig.getSchema() + "IDOCNEXTID";
   public static String TN_FMTS = DbConnectionConfig.getSchema() + "IDOCFMT";
   public static String TN_RPTS = DbConnectionConfig.getSchema() +  "IDOCRPT";
   public static String TN_COMPSUSG = DbConnectionConfig.getSchema() +  "IDOCCOMPUSG";
   public static String TN_PREFFMT = DbConnectionConfig.getSchema() +  "IDOCPREFFMT";
   public static String TN_PREFWFMT = DbConnectionConfig.getSchema() +  "IDOCPREFWFMT";
   public static String TN_IDOCDBINFO = DbConnectionConfig.getSchema() +  "IDOCDBINFO";
   /*
 * @SF-SEVILLA No cualificar el nombre de los campos pq en postgresql no los reconoce
 * 12-jun-2006 / antmaria
 */
   public static String CN_MISC = DbConnectionConfig.getSchema() +  "MISC";
   public static String CN_NAME = DbConnectionConfig.getSchema() + "NAME";
   //public static String CN_MISC = DbConnectionConfig.getSchema() +  "IDOCDBINFO.MISC";
   //public static String CN_NAME = DbConnectionConfig.getSchema() + "IDOCDBINFO.NAME";

   /////////////////////////////////////////////////////////////////////////
   //                         Nombres de Tablas                           //
   /////////////////////////////////////////////////////////////////////////

   /**
    * Devuelve el nombre de la tabla base invesDoc de archivador.
    *
    * @return La tabla mencionada.
    */

   public String getArchHdrTableName()
   {
      return DaoArchHdrTbl.getTblName();
   }

   /**
    * Devuelve el nombre de la tabla detalle invesDoc de archivador.
    *
    * @return La tabla mencionada.
    */

   public String getArchDetTableName()
   {
      return DaoArchDetTbl.getTblName();
   }

   /**
    * Devuelve el nombre de la tabla IDOCXNEXTID para inicializar
    * identificadores de carpeta, campos extendidos y documentos
    *
    * @return la tabla mencionada
    */

   public String getXNextIdTableName()
   {
      return DaoXNextIdTbl.getTblName();
   }

   /**
    * Devuelve el nombre de la tabla IVOLARCHLIST para la asociación
    * de una lista de volúmenes al archivador
    *
    * @return la tabla mencionada
    */

   public String getArchListTableName()
   {
      return DaoArchListTbl.getTblName();
   }

   /**
    * Devuelve el nombre de la tabla IDOCBTBLCTLG
    * que guarda tablas de validación
    *
    * @return la tabla mencionada
    */
   public String getBTblCtlgTableName()
   {
      return  VldDaoBTblCtlgTbl.getTblName();
   }


   /**
    * Devuelve el nombre de la tabla de formatos
    *
    * @return la tabla mencionada
    */
   public String getFmtsTableName()
   {
      return TN_FMTS;
   }

   /**
    * Devuelve el nombre de la tabla de REPORTS
    *
    * @return la tabla mencionada
    */
   public String getRptsTableName()
   {
      return TN_RPTS;
   }

   /**
    * Devuelve el nombre de la tabla de COMPONENTES EN USO
    *
    * @return la tabla mencionada
    */
   public String getCompsUsgTableName()
   {
      return TN_COMPSUSG;
   }

   /**
    * Devuelve el nombre de la tabla de fomatos preferentes
    *
    * @return la tabla mencionada
    */
   public String getPrefFmtTableName()
   {
      return TN_PREFFMT;
   }

   /**
    * Devuelve el nombre de la tabla de formatos preferentes
    * en web
    *
    * @return la tabla mencionada
    */
   public String getPrefWFmtTableName()
   {
      return TN_PREFWFMT;
   }

   /////////////////////////////////////////////////////////////////////////
   //                         Nombres de Columnas                         //
   /////////////////////////////////////////////////////////////////////////

   /**
    * Devuelve todas las columnas de la tabla base de archivadores.
    *
    * @return Las columnas mencionadas.
    */

   public String getAllArchHdrColumnNames()
   {
	   String val;
	  /*
      val = DaoArchHdrTbl.getIdColName(true) + "," +
            DaoArchHdrTbl.getNameColName(true) + "," +
            DaoArchHdrTbl.getTblPrefixColName(true) + "," +
            DaoArchHdrTbl.getTypeColName(true) + "," +
            DaoArchHdrTbl.getFlagsColName(true) + "," +
            DaoArchHdrTbl.getRemarksColName(true) + "," +
            DaoArchHdrTbl.getAccessTypeColName(true) + "," +
            DaoArchHdrTbl.getAcsIdColName(true) + "," +
            DaoArchHdrTbl.getCrtUserIdColName(true) + "," +
            DaoArchHdrTbl.getCrtTSColName(true) + "," +
            DaoArchHdrTbl.getUpdUserIdColName(true) + "," +
            DaoArchHdrTbl.getUpdTSColName(true);

      return val;
      */
	  //	Revision para para SQLServer, Oracle y PostgreSQL
      val = DaoArchHdrTbl.getIdColName(false) + "," +
      		DaoArchHdrTbl.getNameColName(false) + "," +
      		DaoArchHdrTbl.getTblPrefixColName(false) + "," +
      		DaoArchHdrTbl.getTypeColName(false) + "," +
      		DaoArchHdrTbl.getFlagsColName(false) + "," +
      		DaoArchHdrTbl.getRemarksColName(false) + "," +
      		DaoArchHdrTbl.getAccessTypeColName(false) + "," +
      		DaoArchHdrTbl.getAcsIdColName(false) + "," +
      		DaoArchHdrTbl.getCrtUserIdColName(false) + "," +
      		DaoArchHdrTbl.getCrtTSColName(false) + "," +
      		DaoArchHdrTbl.getUpdUserIdColName(false) + "," +
      		DaoArchHdrTbl.getUpdTSColName(false);
	  // 2005/09/09 FIN SUSTITUCION

      return val;
   }

   /**
    * Obtiene el nombre de las columnas de la tabla base de archivadores
    * a actualizar (Nombre,Descripción,Flags,UpdUserId, UpdDate).
    * @return La información mencionada.
    */
   public String getUpdateArchHdrColNames()
   {
      String val;
      /*
      val = DaoArchHdrTbl.getNameColName(true) + "," +
      		DaoArchHdrTbl.getRemarksColName(true) + "," +
      		DaoArchHdrTbl.getFlagsColName(true) + "," +
      		DaoArchHdrTbl.getUpdUserIdColName(true) + "," +
            DaoArchHdrTbl.getUpdTSColName(true);
      */
      /*
	 * @SF-SEVILLA Modificacion para posgres 17/3/2006
	 * 02-may-2006 / antmaria
	 */
      val = DaoArchHdrTbl.getNameColName(false) + "," +
		DaoArchHdrTbl.getRemarksColName(false) + "," +
		DaoArchHdrTbl.getFlagsColName(false) + "," +
		DaoArchHdrTbl.getUpdUserIdColName(false) + "," +
      DaoArchHdrTbl.getUpdTSColName(false);
      return val;
   }


   /**
    * Obtiene el nombre de las columnas de la tabla base de archivadores
    * de Identificador y nombre.
    * @return La información mencionada.
    */
   public String getIdNameArchHdrColNames()
   {
      String val;

      val = DaoArchHdrTbl.getIdColName(true) + "," +
      		DaoArchHdrTbl.getNameColName(true);

      return val;
   }

   /**
    * Obtine el nombre de la columna de la tabla base de archivadores
    * de identiticador de acceso.
    * @return La información mencionada.
    */
   public String getAcsIdArchHdrColName()
   {
      String val;

      val = DaoArchHdrTbl.getAcsIdColName(true);

      return val;
   }

   /**
    * Obtiene el nombre de todas las columnas de la tabla de detalle del archivador
    *
    * @return La información mencionada.
    */
   public String getAllArchDetColumnNames()
   {
      String val;
	  /*
      val = DaoArchDetTbl.getArchIdColName(true) + "," +
            DaoArchDetTbl.getDetTypeColName(true) + "," +
            DaoArchDetTbl.getDetValColName(true);
      */
      /*
  	 * @SF-SEVILLA Modificacion para posgres 17/3/2006
  	 * El cambio no debe afectar a SQLServer u Oracle.
  	 * 02-may-2006 / antmaria
  	 */
      // Esto debe ser válido para SQLServer, Oracle y PostgreSQL
      val = DaoArchDetTbl.getArchIdColName(false) + "," +
      		DaoArchDetTbl.getDetTypeColName(false) + "," +
      		DaoArchDetTbl.getDetValColName(false);
	  // 2005/09/09 FIN SUSTITUCION
      return val;
   }

   /**
    * Obtiene el nombre de la columna de valor de la tabla de detalle
    * del archivador.
    *
    * @return La información mencionada.
    */
   public String getupdateArchDetColumnNames()
   {
      String val;

      // val = DaoArchDetTbl.getDetValColName(true);

      /*
       * @SF-SEVILLA Modificacion para postgresql
       * 02-may-2006 / antmaria
       */
      val = DaoArchDetTbl.getDetValColName(false);

      return val;
   }

   /**
    * Obtiene el nombre de todas las columnas de la tabla idocxnextid
    *
    * @return La información mencionada.
    */
   public String getAllXNextIdColumnNames()
   {
      String val;


      // Codigo no válido para PostgreSQL (no se pueden cualificar las columnas)
	  // El cambio no debe afectar a SQLServer u Oracle.
	  /*
      val = DaoXNextIdTbl.getTypeColName(true) + "," +
      		DaoXNextIdTbl.getParentIdColName(true) + "," +
      		DaoXNextIdTbl.getIdColName(true);
      */
      /*
       * @SF-SEVILLA Modificacion para postgresql
       * 02-may-2006 / antmaria
       */
      val = DaoXNextIdTbl.getTypeColName(false) + "," +
			DaoXNextIdTbl.getParentIdColName(false) + "," +
			DaoXNextIdTbl.getIdColName(false);
      return val;
   }

   /**
    * Obtiene el nombre de todas las columnas de la tabla ivolarchlist.
    *
    * @return La información mencionada.
    */
   public String getAllArchListColumnNames()
   {
      String val;

	  // Codigo no válido para PostgreSQL (no se pueden cualificar las columnas)
	  // El cambio no debe afectar a SQLServer u Oracle.
	  /*
      val = DaoArchListTbl.getArchIdColName(true) + "," +
      		DaoArchListTbl.getListIdColName(true);
      */
      /*
       * @SF-SEVILLA Modificacion para postgresql
       * 02-may-2006 / antmaria
       */
      val = DaoArchListTbl.getArchIdColName(false) + "," +
			DaoArchListTbl.getListIdColName(false);

      return val;
   }

   /**
    * Obtiene el nombre de la columna ListId de la tabla ivolarchlist.
    *
    * @return La información mencionada.
    */
   public String getUpdateArchListColumnNames()
   {
      String val;

      //val = DaoArchListTbl.getListIdColName(true);
      /*
       * @SF-SEVILLA Modificacion para postgresql
       * 02-may-2006 / antmaria
       */
      val = DaoArchListTbl.getListIdColName(false);

      return val;
   }



   /////////////////////////////////////////////////////////////////////////
   //                            Quals                                    //
   /////////////////////////////////////////////////////////////////////////

   /**
    * Construye una expresión de carga de archivador por su id
    *
    * @param id El identificador del archivador.
    *
    * @return La expresión mencionada.
    */
   public String getLoadArchIdQual(int id)
   {
      String qual;

      qual = "WHERE " + DaoArchHdrTbl.getIdColName(true) + "= " +
             Integer.toString(id);

      return qual;
   }
   /**
    * Construye una expresión de comprobación de existencia previa de nombre de
    * archivador a partir del id del mismo.
    *
    * @param id El identificador del archivador.
    * @param archName El nombre del archivador.
    * @return La expresión mencionada.
    */

   public String getCountArchIdQual(int id, String archName)
   {
      String qual;

      qual = "WHERE " + DaoArchHdrTbl.getNameColName(true) + "='" + archName +
           "' AND " + DaoArchHdrTbl.getIdColName(true) + "<>" + Integer.toString(id);

      return qual;
   }

   /**
    * Construye una expresión de búsqueda por el nombre de tabla
    * en la tabla IDOCBTBLCTLG
    *
    * @param tblName El nombre de la tabla de cabecera de carpetas del
    * archivador (ej: A3FDRH)
    * @return la expresión de búsqueda
    */
   public String getLoadBTblCtrlTableNameQual(String tblName)
   {
      String colName = VldDaoBTblCtlgTbl.getNameColName(true);

      return "WHERE " + colName + "= '" + tblName + "'";

   }

   /**
    * Construye una expresión de búsqueda para obtener los
    * formatos de un archivador
    *
    * @param id El identificador del archivador.
    *
    * @return la expresión de búsqueda
    */
   public String getLoadFmtsArchIdQual(int id)
   {
      String colName = TN_FMTS + "." + "ARCHID";

      return "WHERE " + colName + "= " + Integer.toString(id) ;
   }

   /**
    * Construye una expresión de búsqueda para obtener los informes
    * de un archivador
    *
    * @param id El identificador del archivador.
    *
    * @return la expresión de búsqueda
    */
   public String getLoadRptsArchIdQual(int id)
   {
      String colName = TN_RPTS + "." + "ARCHID";

      return "WHERE " + colName + "= " + Integer.toString(id) ;
   }

   /**
    * Construye una expresión de búsqueda para obtener componentes
    * en uso de un archivador
    *
    * @param id El identificador del archivador.
    *
    * @return la expresión de búsqueda
    */
   public String getLoadCompsUsgArchIdQual(int id)
   {
      String colName = TN_COMPSUSG + "." + "ARCHID";

      return "WHERE " + colName + "= " + Integer.toString(id) ;
   }

   /**
    * Construye una expresión de búsqueda para obtener todas
    * las filas de detalle del archivador
    *
    * @param id El identificador del archivador.
    *
    * @return la expresión de búsqueda
    */
   public String getLoadDetailsArchIdQual(int id)
   {
      String qual;

      qual = "WHERE " + DaoArchDetTbl.getArchIdColName(true) +
             "= " + Integer.toString(id);

      return qual ;
   }

   /**
    * Construye una expresión de búsqueda para obtener una fila
    * de detalle en concreto
    *
    * @param id El identificador del archivador.
    * @param detType El identificador del tipo de detalle del archivador.
    *
    * @return la expresión de búsqueda
    */
   public String getLoadDetailArchiveQual(int id, int detType)
   {
      String qual;

      qual = "WHERE " + DaoArchDetTbl.getArchIdColName(true) +
             "= " + Integer.toString(id) + " AND " +  DaoArchDetTbl.getDetTypeColName(true) +
             "= " + Integer.toString(detType);

      return qual ;
   }

   /**
    * Construye una expresión de búsqueda para obtener el identificador
    * del padre del archivador a partir de su id.
    *
    * @param id Identificador del archivador.
    * @return La expresión de búsqueda.
    */
   public String getLoadNodeArchIdQual(int id)
   {
      String qual;

      qual = "WHERE " + DaoDATNodeTbl.getIdColName(true) + "=" +
      		Integer.toString(id) + " AND " +
      		DaoDATNodeTbl.getTypeColName(true) + "=" + ISicresAdminDefsKeys.NODE_TYPE_ARCHIVE;

      return qual;
   }

   /**
    * Contruye una expresión de búsqueda para obtener un
    * identificador para el archivador a partir de su padre.
    *
    * @param id Identificador del padre
    * @return La expresión de búsqueda
    */
   public String getLoadNextIdArchIdQual(int id)
   {
      String qual;

      qual = "WHERE " + DaoXNextIdTbl.getParentIdColName(true) +
             "= " + Integer.toString(id);

      return qual;
   }

   /**
    * Contruye una expresión de búsqueda para obtener el identificadores y
    * nombres de archivadores partir de un padre .
    *
    * @param parentid Identificador del padre
    * @return La expresión de búsqueda
    */

   public String getLoadArchHdrParentIdQual(int parentid)
   {
      String qual;
      //
      qual ="WHERE " + DaoArchHdrTbl.getIdColName(true) +
         	" IN (SELECT " + DaoDATNodeTbl.getIdColName(true) +
      		" FROM " + DaoDATNodeTbl.getTblName() +
      		" WHERE " + DaoDATNodeTbl.getParentIdColName(true) + "=" +
      		Integer.toString(parentid) + " AND " +
      		DaoDATNodeTbl.getTypeColName(true) + "=" +
      		ISicresAdminDefsKeys.NODE_TYPE_ARCHIVE + ")";

      return qual;
   }

   /**
    * Construye una expresión de búsqueda para obtener los
    * formatos preferente del archivador
    *
    * @param id El identificador del archivador.
    *
    * @return la expresión de búsqueda
    */
   public String getLoadPrefFmtsArchIdQual(int id)
   {
      String colName = TN_PREFFMT + "." + "ARCHID";

      return "WHERE " + colName + "= " + id ;
   }

   /**
    * Construye una expresión de búsqueda para obtener los
    * formatos preferentes en web del archivador
    *
    * @param id El identificador del archivador.
    *
    * @return la expresión de búsqueda
    */
   public String getLoadPrefWFmtsArchIdQual(int id)
   {
      String colName = TN_PREFWFMT + "." + "ARCHID";

      return "WHERE " + colName + "= " + id ;
   }

   /**
    * Contruye una expresión de búsqueda para obtener la
    * lista de volúmenes asociada al archivador.
    *
    * @param id Identificador del archivador.
    * @return La expresión de búsqueda.
    */
   public String getLoadArchListArchIdQual(int id)
   {
      String qual;

      qual = "WHERE " + DaoArchListTbl.getArchIdColName(true) +
             "= " + Integer.toString(id);

      return qual;
   }



}