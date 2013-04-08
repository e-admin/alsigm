package es.ieci.tecdoc.isicres.admin.database;

import org.apache.log4j.Logger;

import es.ieci.tecdoc.isicres.admin.estructura.keys.ISicresAdminUserDefsKeys;
import es.ieci.tecdoc.isicres.admin.sbo.acs.std.AcsDaoObjHdrTbl;
import es.ieci.tecdoc.isicres.admin.sbo.acs.std.AcsDaoObjPermTbl;
import es.ieci.tecdoc.isicres.admin.sbo.acs.std.AcsDaoUserTypeTbl;
import es.ieci.tecdoc.isicres.admin.sbo.uas.ldap.UasDaoLdapUserTbl;
import es.ieci.tecdoc.isicres.admin.sbo.uas.std.UasDaoGURelTbl;
import es.ieci.tecdoc.isicres.admin.sbo.uas.std.UasDaoSysTbl;
import es.ieci.tecdoc.isicres.admin.sbo.uas.std.UasDaoUserTbl;

public class UsersTable
{
   public UsersTable()
   {
   }


   /////////////////////////////////////////////////////////////////////////
   //                         Nombres de Tablas                           //
   /////////////////////////////////////////////////////////////////////////


   /**
    * Devuelve el nombre tabla base invesDoc de usuario invesDoc.
    *
    * @return Las tabla mencionada.
    */

   public String getBaseTableName()
   {
      return UasDaoUserTbl.TN;
   }

   public String getBaseTableNameWithPerfil()
   {
      return getBaseTableName() + "," + getProfilesTableName();
   }

   /**
    * Devuelve el nombre tabla de perfiles de un usuario.
    *
    * @return Las tabla mencionada.
    */

   public String getProfilesTableName()
   {
      return AcsDaoUserTypeTbl.TN;
   }

   /**
    * Devuelve el nombre de la tabla de permisos sobre objetos.
    *
    * @return Las tabla mencionada.
    */

   public String getObjPermsTableName()
   {
      return AcsDaoObjPermTbl.TN;
   }

   /**
    * Devuelve el nombre de la tabla de propietarios de objetos.
    *
    * @return Las tabla mencionada.
    */

   public String getOwnershipTableName()
   {
      return AcsDaoObjHdrTbl.TN;
   }

   /**
    * Devuelve el nombre de la tabla de relación grupos-usuarios
    * @return La tabla mencionada.
    */
   public String getGURelTableName()
   {
      return UasDaoGURelTbl.TN;
   }

   /**
    * Devuelve el nombre de la tabla de configuración de usuarios en invesDoc.
    *
    * @return La tabla mencionada.
    */
   public String getUserSysTableName()
	{
   	return UasDaoSysTbl.TN;
   }

   //////////////////////////////////////////////////////////////////////////
   //                      columns
   /////////////////////////////////////////////////////////////////////////
   /**
    * Devuelve el nombre de las tablas involucradas en la búsqueda de usuarios
    * administradores de objetos.
    *
    * @return Las tablas mencionadas.
    */

   public String getUserAdminTableNames()
   {
      return getBaseTableName() + "," + getProfilesTableName();
   }



   /**
    * Devuelve las columnas id y nombre de la tabla base de usuarios invesDoc.
    *
    * @return Las columnas mencionadas.
    */

   public String getAdminUserColumnNames()
   {
      String val;

      val = getBaseTableName() + "." +  UasDaoUserTbl.CD_ID.getName() +
            "," + getBaseTableName() + "." +
            UasDaoUserTbl.CD_NAME.getName();

      return val;
   }

   /**
    * Devuelve la columna identificador de departamento de la tabla base de
    * usuarios invesDoc.
    *
    * @return la columna mencionada.
    */
   public String getDeptIdUserColumnName()
   {
      String val;

      val = getBaseTableName() + "." + UasDaoUserTbl.CD_DEPTID.getName();

      return val;
   }

   /**
    * Devuelve las columnas de invesDoc de la tabla de propietarios
    * de objetos que se actualizan con un usuario.
    *
    * @return Las columnas mencionadas.
    */

   public String getUpdateOwnershipColumnNames()
   {
      String val;
      /*
      val = getOwnershipTableName() + "." + AcsDaoObjHdrTbl.CD_OWNERID.getName()
            + "," + getOwnershipTableName() + "." +
            AcsDaoObjHdrTbl.CD_UPDUSRID.getName() + "," +
            getOwnershipTableName() + "." + AcsDaoObjHdrTbl.CD_UPDTS.getName();
      */
  	/*
  	 * @SF-SEVILLA Modificacion para compatibilidad con postgresql
  	 * 02-may-2006 / antmaria
  	 */      val = AcsDaoObjHdrTbl.CD_OWNERID.getName() +
      		"," + AcsDaoObjHdrTbl.CD_UPDUSRID.getName() +
      		"," + AcsDaoObjHdrTbl.CD_UPDTS.getName();
      return val;
   }

   /**
    * Devuelve todas las columnas de invesDoc de la tabla base de usuarios
    * para su inserción.
    *
    * @return Las columnas mencionadas.
    */

   public String getInsertBaseColumnNames()
   {
      String val;


      // Codigo no válido para PostgreSQL (no se pueden cualificar las columnas)
      // El cambio no debe afectar a SQLServer u Oracle.
      /*
      val = getBaseTableName() + "." +  UasDaoUserTbl.CD_ID.getName() +
             "," + getBaseTableName() + "." +  UasDaoUserTbl.CD_NAME.getName() +
             "," + getBaseTableName() + "." +  UasDaoUserTbl.CD_PASSWORD.getName() +
             "," + getBaseTableName() + "." +  UasDaoUserTbl.CD_DEPTID.getName() +
             "," + getBaseTableName() + "." +  UasDaoUserTbl.CD_FLAGS.getName() +
             "," + getBaseTableName() + "." +  UasDaoUserTbl.CD_STAT.getName() +
             "," + getBaseTableName() + "." +  UasDaoUserTbl.CD_NUMBADCNTS.getName() +
             "," + getBaseTableName() + "." +  UasDaoUserTbl.CD_REMARKS.getName() +
             "," + getBaseTableName() + "." +  UasDaoUserTbl.CD_CRTRID.getName() +
             "," + getBaseTableName() + "." +  UasDaoUserTbl.CD_CRTNDATE.getName() +
             "," + getBaseTableName() + "." +  UasDaoUserTbl.CD_PWDLASTUPDTS.getName() +
             "," + getBaseTableName() + "." +  UasDaoUserTbl.CD_PWDMBC.getName() +
             "," + getBaseTableName() + "." +  UasDaoUserTbl.CD_PWDVPCHECK.getName();
      */
  	/*
  	 * @SF-SEVILLA Modificacion para compatibilidad con postgresql
  	 * 02-may-2006 / antmaria
  	 */
      val = UasDaoUserTbl.CD_ID.getName() +
      		"," + UasDaoUserTbl.CD_NAME.getName() +
      		"," + UasDaoUserTbl.CD_PASSWORD.getName() +
      		"," + UasDaoUserTbl.CD_DEPTID.getName() +
      		"," + UasDaoUserTbl.CD_FLAGS.getName() +
      		"," + UasDaoUserTbl.CD_STAT.getName() +
      		"," + UasDaoUserTbl.CD_NUMBADCNTS.getName() +
      		"," + UasDaoUserTbl.CD_REMARKS.getName() +
      		"," + UasDaoUserTbl.CD_CRTRID.getName() +
      		"," + UasDaoUserTbl.CD_CRTNDATE.getName() +
      		"," + UasDaoUserTbl.CD_PWDLASTUPDTS.getName() +
      		"," + UasDaoUserTbl.CD_PWDMBC.getName() +
      		"," + UasDaoUserTbl.CD_PWDVPCHECK.getName();

      return val;
   }

   /**
    * Devuelve todas las columnas de invesDoc de la tabla base de usuarios
    * para su actualización.
    *
    * @return Las columnas mencionadas.
    */
   public String getUpdateBaseColumnNames()
	{
   	String val;
   	/*
   	val = getBaseTableName() + "." +  UasDaoUserTbl.CD_NAME.getName() +
		      "," + getBaseTableName() + "." +  UasDaoUserTbl.CD_PASSWORD.getName() +
		      "," + getBaseTableName() + "." +  UasDaoUserTbl.CD_DEPTID.getName() +
		      "," + getBaseTableName() + "." +  UasDaoUserTbl.CD_STAT.getName() +
		      "," + getBaseTableName() + "." +  UasDaoUserTbl.CD_REMARKS.getName() +
		      "," + getBaseTableName() + "." +  UasDaoUserTbl.CD_UPDRID.getName() +
		      "," + getBaseTableName() + "." +  UasDaoUserTbl.CD_UPDATE.getName() +
		      "," + getBaseTableName() + "." +  UasDaoUserTbl.CD_PWDLASTUPDTS.getName() +
		      "," + getBaseTableName() + "." +  UasDaoUserTbl.CD_PWDMBC.getName() +
		      "," + getBaseTableName() + "." +  UasDaoUserTbl.CD_PWDVPCHECK.getName();
   	*/
	/*
	 * @SF-SEVILLA Modificacion para compatibilidad con postgresql
	 * 02-may-2006 / antmaria
	 */
 	  val = UasDaoUserTbl.CD_NAME.getName() +
	   		"," + UasDaoUserTbl.CD_PASSWORD.getName() +
		    "," + UasDaoUserTbl.CD_DEPTID.getName() +
		    "," + UasDaoUserTbl.CD_STAT.getName() +
		    "," + UasDaoUserTbl.CD_REMARKS.getName() +
		    "," + UasDaoUserTbl.CD_UPDRID.getName() +
		    "," + UasDaoUserTbl.CD_UPDATE.getName() +
		    "," + UasDaoUserTbl.CD_PWDLASTUPDTS.getName() +
		    "," + UasDaoUserTbl.CD_PWDMBC.getName() +
		    "," + UasDaoUserTbl.CD_PWDVPCHECK.getName();

   	return val;
   }

   /**
    * Devuelve todas las columnas de invesDoc de la tabla base de usuarios
    * utilizadas en la carga de un usuario excepto el identificador.
    *
    * @return Las columnas mencionadas.
    */

   public String getLoadBaseColumnNames()
   {
      String val;

      val = getBaseTableName() + "." +  UasDaoUserTbl.CD_ID.getName() +
            "," + getBaseTableName() + "." +  UasDaoUserTbl.CD_NAME.getName() +
		      "," + getBaseTableName() + "." +  UasDaoUserTbl.CD_PASSWORD.getName() +
		      "," + getBaseTableName() + "." +  UasDaoUserTbl.CD_DEPTID.getName() +
		      "," + getBaseTableName() + "." +  UasDaoUserTbl.CD_STAT.getName() +
		      "," + getBaseTableName() + "." +  UasDaoUserTbl.CD_REMARKS.getName() +
		      "," + getBaseTableName() + "." +  UasDaoUserTbl.CD_CRTRID.getName() +
		      "," + getBaseTableName() + "." +  UasDaoUserTbl.CD_CRTNDATE.getName() +
		      "," + getBaseTableName() + "." +  UasDaoUserTbl.CD_UPDRID.getName() +
		      "," + getBaseTableName() + "." +  UasDaoUserTbl.CD_UPDATE.getName() +
		      "," + getBaseTableName() + "." +  UasDaoUserTbl.CD_PWDLASTUPDTS.getName() +
		      "," + getBaseTableName() + "." +  UasDaoUserTbl.CD_PWDMBC.getName() +
		      "," + getBaseTableName() + "." +  UasDaoUserTbl.CD_PWDVPCHECK.getName();

      return val;
   }

   /**
    * Devuelve la columna de la longitud mínima para la contraseña.
    *
    * @return La columna mencionada.
    */
   public String getUserSysColumnName()
	{
   	String val;

   	val = getUserSysTableName() + "." + UasDaoSysTbl.CD_PWDMINLEN.getName();

   	return val;
   }

   /**
    * Devuelve la columna de la contraseña del usuario
    *
    * @return La columna mencionada.
    */
   public String getPasswordColumnName()
	{
   	String val;

   	val = getBaseTableName() + "." + UasDaoUserTbl.CD_PASSWORD.getName();

   	return val;
   }

   /**
    * Devuelve las columnas identificador y nombre de la tabla de usuarios.
    *
    * @return Las columnas mencionadas
    */
   public String getLoadBaseIdNameColumnNames()
	{
   	String val;

   	val = getBaseTableName() + "." + UasDaoUserTbl.CD_ID.getName() +
				"," + getBaseTableName() + "." + UasDaoUserTbl.CD_NAME.getName();

   	return val;
   }

   public String getLoadBaseIdColumnName()
	{
   	String val;

   	val = getGURelTableName() + "." + UasDaoGURelTbl.CD_USERID.getName();

   	return val;
   }

   public String getInsertGURelColumnNames()
   {
      String val;

      //val = getGURelTableName() + "." + UasDaoGURelTbl.CD_GROUPID.getName() +
      //		"," + getGURelTableName() + "." + UasDaoGURelTbl.CD_USERID.getName();
  	/*
  	 * @SF-SEVILLA Modificacion para compatibilidad con postgresql
  	 * 02-may-2006 / antmaria
  	 */
      val = UasDaoGURelTbl.CD_GROUPID.getName() +
			"," + UasDaoGURelTbl.CD_USERID.getName();

      return val;
   }

   public String getGURelGroupColumnName()
   {
      String val;

      val = getGURelTableName() + "." + UasDaoGURelTbl.CD_GROUPID.getName();

      return val;
   }

   /////////////////////////////////////////////////////////////////////////
   //                            Quals                                    //
   /////////////////////////////////////////////////////////////////////////

   /**
	 * Construye una expresión de búsqueda para calcular el número
	 * de usuarios que hay con el mismo nombre.
	 *
	 * @param name El nombre del usuario.
	 * @return La expresión mencionada.
	 */
	public String getCountNameQual(String name)
	{
		String qual;
		qual = "WHERE " + getBaseTableName() + "."
				+ UasDaoUserTbl.CD_NAME.getName() + "= '" + name + "'";

		if (_logger.isDebugEnabled())
			_logger.debug("getCountNameQual: " + qual);
		return qual;
	}

   /**
	 * Construye una expresión de búsqueda para calcular el número
	 * de usuarios que hay con el mismo nombre y distinto
	 * identificador.
	 *
	 * @param id Identificador del usuario.
	 * @param name El nombre del usuario.
	 * @return La expresión mencionada.
	 */
	public String getCountNameIdQual(int id, String name)
	{
		String qual;
		qual = "WHERE " + getBaseTableName() + "."
				+ UasDaoUserTbl.CD_NAME.getName() + "= '" + name + "' AND "
				+ getBaseTableName() + "." + UasDaoUserTbl.CD_ID.getName()
				+ "<>" + Integer.toString(id);

		if (_logger.isDebugEnabled())
			_logger.debug("getCountNameIdQual: " + qual);
		return qual;
	}

   /**
    * Construye una expresión de búsqueda para obtener todos los usuarios
    * susceptibles de ser administradores de algo de invesDoc.
    *
    * @return La expresión mencionada.
    */

   public String getLoadAminUsersQual(int productId)
   {
      String qual;

      qual = "WHERE " + getBaseTableName() + "." +
             UasDaoLdapUserTbl.CD_ID.getName() + " IN (SELECT " +
             getProfilesTableName() + "." + AcsDaoUserTypeTbl.CD_USERID.getName()
             + " FROM " + getProfilesTableName() + " WHERE (" +
             getProfilesTableName() + "." + AcsDaoUserTypeTbl.CD_TYPE.getName()
             + "=" + ISicresAdminUserDefsKeys.PROFILE_MANAGER + " OR " + getProfilesTableName()
             + "." + AcsDaoUserTypeTbl.CD_TYPE.getName() + "=" +
             ISicresAdminUserDefsKeys.PROFILE_SUPERUSER + ") AND (" + getProfilesTableName() +
             "." + AcsDaoUserTypeTbl.CD_PRODID.getName() + "=" +
             Integer.toString(productId) + " OR " + getProfilesTableName() + "."
             + AcsDaoUserTypeTbl.CD_PRODID.getName() + "=" +
             ISicresAdminUserDefsKeys.PRODUCT_SYSTEM + "))";

      if (_logger.isDebugEnabled())
         _logger.debug("getLoadAminUsersQual: " + qual);

      return qual;
   }


   /**
    * Construye una expresión de búsqueda con el identificador de usuario en la
    * tabla de usuarios de invesDoc.
    *@param id Identificador de usuario.
    * @return La expresión mencionada.
    */

   public String getLoadUserIdQual(int id)
   {
      String qual;

      qual = "WHERE " + getBaseTableName() + "." + UasDaoUserTbl.CD_ID.getName() +
      		"=" + Integer.toString(id);

      if (_logger.isDebugEnabled())
         _logger.debug("getLoadUserIdQual: " + qual);


      return qual;
   }


   /**
    * Contruye una expresión de búsqueda por el identificador de usuario y el
    * identificador de grupo en la tabla de relación de grupos- usuarios de
    * invesDoc.
    * @param usrId Identificador de usuario.
    * @param grpId Identificador de grupo.
    * @return La expresión mencionada.
    */
   public String getLoadGURelQual(int usrId, int grpId)
   {
      String qual;

      qual = "WHERE " + getGURelTableName() + "." + UasDaoGURelTbl.CD_USERID.getName() +
      		"=" + Integer.toString(usrId) + " AND " +
      		getGURelTableName() + "." + UasDaoGURelTbl.CD_GROUPID.getName() + "=" +
      		Integer.toString(grpId);

      if (_logger.isDebugEnabled())
         _logger.debug("getLoadGURelQual: " + qual);


      return qual;
   }

   /**
    * Contruye una expresión de búsqueda por el identificador de usuario
    * en la tabla de relación de grupos- usuarios de invesDoc.
    * @param usrId Identificador de usuario.
    *
    * @return La expresión mencionada.
    */
   public String getLoadGURelUserIdQual(int usrId)
   {
      String qual;

      qual = "WHERE " + getGURelTableName() + "." + UasDaoGURelTbl.CD_USERID.getName() +
      		"=" + Integer.toString(usrId);

      if (_logger.isDebugEnabled())
         _logger.debug("getLoadGURelUserIdQual: " + qual);


      return qual;
   }

   /**
    * Construye una expresión de búsqueda para eliminar los permisos sobre
    * objetos del usuario, grupo o departamento.
    *
    * @param userId El identificador del objeto.
    * @param dest El destinatario (usuario, grupo, departamento).
    * @return La expresión mencionada.
    */

   public String getDeleteObjPermsQual(int userId, int dest)
   {
      String qual;

      qual = "WHERE " + getObjPermsTableName() + "." +
             AcsDaoObjPermTbl.CD_DSTTYPE.getName() + "=" +
             Integer.toString(dest) + " AND " + getObjPermsTableName() + "." +
             AcsDaoObjPermTbl.CD_DSTID.getName() + "=" +
             Integer.toString(userId);

      if (_logger.isDebugEnabled())
         _logger.debug("getDeleteObjPermsQual: " + qual);

      return qual;
   }

   /**
    * Construye una expresión de búsqueda para todos los objetos de los que es
    * propietario el usuario, grupo o departamento.
    *
    * @param id El identificador de objeto.
    * @param ownerType El tipo de propietario (usuario, grupo, departamento).
    * archivador).
    * @return La expresión mencionada.
    */

   public String getUpdateOwnerQual(int id, int ownerType)
   {
      String qual;

      qual = "WHERE " + getOwnershipTableName() + "." +
             AcsDaoObjHdrTbl.CD_OWNERTYPE.getName() + "=" +
             Integer.toString(ownerType) + " AND " + getOwnershipTableName() +
             "." + AcsDaoObjHdrTbl.CD_OWNERID.getName() + "=" +
             Integer.toString(id);

      if (_logger.isDebugEnabled())
         _logger.debug("getUpdateOwnerQual: " + qual);

      return qual;
   }

   /**
    * Construye una expresión de búsqueda para eliminar los permisos sobre
    * objetos de los que era propietario el usuario, grupo o departamento.
    *
    * @param id El identificador de objeto.
    * @param dest El destinatario (usuario, grupo, departamento).
    * @param perm Permiso a quitar.
    * @param productId El identificador producto (iDoc, iFlow, iSicres).
    * @return La expresión mencionada.
    */

   public String getDeleteObjPermQual(int id, int dest, int perm,
                                       int productId)
   {
      String qual;

      qual = "WHERE " + getObjPermsTableName() + "." +
             AcsDaoObjPermTbl.CD_DSTTYPE.getName() + "=" +
             Integer.toString(dest) + " AND " + getObjPermsTableName() +
             "." + AcsDaoObjPermTbl.CD_DSTID.getName() + "=" +
             Integer.toString(id) + " AND " + getObjPermsTableName() +
             "." + AcsDaoObjPermTbl.CD_PERM.getName()
             + "=" + Integer.toString(perm) + " AND EXISTS (SELECT " +
             getOwnershipTableName() + "." + AcsDaoObjHdrTbl.CD_ID.getName() +
             " FROM " + getOwnershipTableName() + " WHERE " +
             getOwnershipTableName() + "." + AcsDaoObjHdrTbl.CD_PRODID.getName()
             + "=" + Integer.toString(productId) + " AND " +
             getOwnershipTableName() + "." + AcsDaoObjHdrTbl.CD_ID.getName() +
             "=" + getObjPermsTableName() + "." +
             AcsDaoObjPermTbl.CD_OBJID.getName() + ")";

      if (_logger.isDebugEnabled())
         _logger.debug("getDeleteObjPermQual: " + qual);

      return qual;
   }

   /**
    * Construye una expresión de búsqueda para eliminar el usuario de la tabla
    * principal.
    *
    * @param userId El identificador de usuario.
    * @return La expresión mencionada.
    */

   public String getDeleteBaseQual(int userId)
   {
      String qual;

      qual = "WHERE " + getBaseTableName() + "." +
      	UasDaoUserTbl.CD_ID.getName() + "=" +  Integer.toString(userId);

      if (_logger.isDebugEnabled())
         _logger.debug("getDeleteBaseQual: " + qual);

      return qual;
   }

   /**
    * Construye una expresión de búsqueda para la tabla base de usuarios a
    * partir del identificador.
    *
    * @param id El identificador de usuario.
    * @return La expresión mencionada.
    */

   public String getLoadBaseQual(int id)
   {
      String qual;

      qual = "WHERE " + getBaseTableName() + "." +
      	UasDaoUserTbl.CD_ID.getName() + "=" + Integer.toString(id);

      if (_logger.isDebugEnabled())
         _logger.debug("getLoadUserQual: " + qual);

      return qual;
   }

   /**
    * Construye una expresión de búsqueda para la tabla base de usuarios a
    * partir del nombre.
    *
    * @param name El nombre de usuario.
    * @return La expresión mencionada.
    */

   public String getLoadNameBaseQual(String name)
   {
      String qual;

      qual = "WHERE " + getBaseTableName() + "." +
      	UasDaoUserTbl.CD_NAME.getName() + "= '" + name + "'";

      if (_logger.isDebugEnabled())
         _logger.debug("getLoadNameBaseQual: " + qual);

      return qual;
   }

  /**
    * Construye una expresión de búsqueda para la tabla base de usuarios a
    * partir del un substring del nombre.
    *
    * @param name substring que tiene que contener el nombre de usuario.
    * @return La expresión mencionada.
    */

   public String getLoadBaseBySubNameQual(String subName)
   {
      String qual;

      qual = "WHERE " + getBaseTableName() + "." +
      	UasDaoUserTbl.CD_NAME.getName() + " LIKE '%" + subName + "%'";

      if (_logger.isDebugEnabled())
         _logger.debug("getLoadSubNameBaseQual: " + qual);

      return qual;
   }

   public String  getLoadBaseByAplicacion(int aplicacion, int deptId[], boolean sinPermisos, boolean usuarios, boolean superusuarios)
		   {
	      StringBuffer sb = new StringBuffer();
	      sb.append("WHERE ").append(AcsDaoUserTypeTbl.TN).append(".").append(AcsDaoUserTypeTbl.CD_PRODID.getName()).append(" = ").append(aplicacion);
	      sb.append(" AND ").append(AcsDaoUserTypeTbl.TN).append(".").append(AcsDaoUserTypeTbl.CD_USERID.getName()).append(" = ").
	      	append(UasDaoUserTbl.TN).append(".").append(UasDaoUserTbl.CD_ID.getName());
	      if(sinPermisos || usuarios || superusuarios) {
	    	  sb.append(" AND (");
	      }
	      if(sinPermisos) {
	    	  sb.append(AcsDaoUserTypeTbl.TN).append(".").append(AcsDaoUserTypeTbl.CD_TYPE.getName()).append(" = 0");
	    	  if(usuarios || superusuarios) {
	    		  sb.append(" OR ");
	    	  }
	      }
	      if(usuarios) {
	    	  sb.append(AcsDaoUserTypeTbl.TN).append(".").append(AcsDaoUserTypeTbl.CD_TYPE.getName()).append(" = 1");
	    	  if(superusuarios) {
	    		  sb.append(" OR ");
	    	  }
	      }
	      if(superusuarios) {
	    	  sb.append(AcsDaoUserTypeTbl.TN).append(".").append(AcsDaoUserTypeTbl.CD_TYPE.getName()).append(" = 3");
	      }
	      if(sinPermisos || usuarios || superusuarios) {
	    	  sb.append(")");
	      }

	      if(deptId.length > 0 && deptId[0] != -1){
	    	  sb.append(" AND ").append(UasDaoUserTbl.TN).append(".").append(UasDaoUserTbl.CD_DEPTID.getName()).append(" IN(");
		      for(int i=0; i<deptId.length; i++){
		    	  sb.append(deptId[i]);
		    	  if(i != deptId.length-1)
		    		  sb.append(",");
		      }
		      sb.append(")");
	      }

	      if (_logger.isDebugEnabled())
	         _logger.debug("getLoadSubNameBaseQual: " + sb.toString());

	      return sb.toString();
	   }

   /**
    * Construye una expresión de búsqueda para los directorios y archivadores
    * de los que es propietario el usuario.
    *
    * @param userId El identificador de usuario.
    * @param ownerType El tipo de propietario (usuario, grupo, departamento).
    * @param productId El identificador producto (iDoc, iFlow, iSicres).
    * @param objTypeDir El tipo de objeto directorio.
    * @param objTypeArch El tipo de objeto archivador.
    * @param extId2
    * @return La expresión mencionada.
    */

   public String getUpdateOwnerDirArchQual(int userId, int ownerType,
                                       int productId, int objTypeDir,
                                       int objTypeArch, int extId2)
   {
      String qual;

      qual = "WHERE " + getOwnershipTableName() + "." +
             AcsDaoObjHdrTbl.CD_OWNERTYPE.getName() + "=" +
             Integer.toString(ownerType) + " AND " + getOwnershipTableName() +
             "." + AcsDaoObjHdrTbl.CD_OWNERID.getName() + "=" +
             Integer.toString(userId) + " AND " + getOwnershipTableName() +
             "." + AcsDaoObjHdrTbl.CD_PRODID.getName()
             + "=" + Integer.toString(productId) + " AND (" +
             getOwnershipTableName() + "." + AcsDaoObjHdrTbl.CD_TYPE.getName() +
             "=" + Integer.toString(objTypeDir) + " OR (" +
             getOwnershipTableName() + "." + AcsDaoObjHdrTbl.CD_TYPE.getName() +
             "=" + Integer.toString(objTypeArch) + " AND " + getOwnershipTableName()
             + "." + AcsDaoObjHdrTbl.CD_EXTID2.getName() + "=" +
             Integer.toString(extId2) + "))";

      if (_logger.isDebugEnabled())
         _logger.debug("getUpdateOwnerArchQual: " + qual);

      return qual;
   }

   /**
    * Construye una expresión de búsqueda para obtener los usuarios
    * de un departamento.
    *
    * @param deptId El identificador de departamento.
    * @return La expresión mencionada.
    */

   public String getLoadBaseByDeptQual(int deptId)
   {
      String qual;

      qual = "WHERE " + getBaseTableName() + "." +
      	UasDaoUserTbl.CD_DEPTID.getName() + "=" + Integer.toString(deptId);

      if (_logger.isDebugEnabled())
         _logger.debug("getLoadBaseByDeptQual: " + qual);

      return qual;
   }

   public String getLoadUsersAsociation(int aplicacion, int deptId, boolean superusers) {
	  StringBuffer sb = new StringBuffer();
      sb.append("WHERE ").append(AcsDaoUserTypeTbl.TN).append(".").append(AcsDaoUserTypeTbl.CD_PRODID.getName()).append(" = ").append(aplicacion);
      sb.append(" AND ").append(AcsDaoUserTypeTbl.TN).append(".").append(AcsDaoUserTypeTbl.CD_USERID.getName()).append(" = ").
      	 append(UasDaoUserTbl.TN).append(".").append(UasDaoUserTbl.CD_ID.getName());
      sb.append(" AND (");
      if(superusers)
    	  sb.append(AcsDaoUserTypeTbl.TN).append(".").append(AcsDaoUserTypeTbl.CD_TYPE.getName()).append(" = 3");
      else
    	  sb.append(AcsDaoUserTypeTbl.TN).append(".").append(AcsDaoUserTypeTbl.CD_TYPE.getName()).append(" = 1");
      sb.append(")");
      sb.append(" AND ").append(UasDaoUserTbl.TN).append(".").append(UasDaoUserTbl.CD_DEPTID.getName()).append(" != ").append(deptId);

      if (_logger.isDebugEnabled())
         _logger.debug("getLoadUsersAsociation: " + sb.toString());

      return sb.toString();
   }

   public String getLoadUsersAssociated(int []idsUser, int idOfic) {
		  StringBuffer sb = new StringBuffer();
	      sb.append("WHERE ").append(UasDaoUserTbl.TN).append(".").append(UasDaoUserTbl.CD_ID.getName()).append(" IN(");
	      for(int i=0; i< idsUser.length; i++){
	    	  sb.append(idsUser[i]);
	    	  if(i!=idsUser.length-1)
	    		  sb.append(",");
	      }
	      sb.append(")");
	      sb.append(" AND ").append(UasDaoUserTbl.TN).append(".").append(UasDaoUserTbl.CD_ID.getName()).append(" NOT IN( ");
	      sb.append("SELECT iduser FROM scr_usrofic").append(" WHERE idofic").append("=").append(idOfic).append(")");

	      if (_logger.isDebugEnabled())
	         _logger.debug("getLoadUsersAsociation: " + sb.toString());

	      return sb.toString();
	   }

   public String getLoadBaseByIdsUser(int aplicacion, int idsUser[], boolean superusuarios) {
	   StringBuffer qual = new StringBuffer();

      qual.append("WHERE " + getBaseTableName() + "." + UasDaoUserTbl.CD_ID.getName() + " IN(");
      for(int i=0; i<idsUser.length;i++){
    	  qual.append(idsUser[i]);
    	  if(i!=idsUser.length-1)
    		  qual.append(",");
      }
      qual.append(")");

      qual.append(" AND ").append(AcsDaoUserTypeTbl.TN).append(".").append(AcsDaoUserTypeTbl.CD_PRODID.getName()).append(" = ").append(aplicacion);
      qual.append(" AND ").append(AcsDaoUserTypeTbl.TN).append(".").append(AcsDaoUserTypeTbl.CD_USERID.getName()).append(" = ").append(UasDaoUserTbl.TN).append(".").append(UasDaoUserTbl.CD_ID.getName());
   	  qual.append(" AND (");
      if(superusuarios)
    	  qual.append(AcsDaoUserTypeTbl.TN).append(".").append(AcsDaoUserTypeTbl.CD_TYPE.getName()).append(" = 3");
      else
    	  qual.append(AcsDaoUserTypeTbl.TN).append(".").append(AcsDaoUserTypeTbl.CD_TYPE.getName()).append(" = 1");
   	  qual.append(")");

      if (_logger.isDebugEnabled())
         _logger.debug("getLoadBaseByIdsUser: " + qual.toString());

      return qual.toString();
   }

   /**
    * Construye una expresión de búsqueda para la tabla que relaciona
    * usuarios y grupos.
    *
    * @param groupId El identificador de grupo.
    * @return La expresión mencionada.
    */

   public String getLoadBaseByGroupQual(int groupId)
   {
      String qual;

      qual = "WHERE " + getGURelTableName() + "." +
      	UasDaoGURelTbl.CD_GROUPID.getName() + "=" + Integer.toString(groupId);

      if (_logger.isDebugEnabled())
         _logger.debug("getLoadBaseByGroupQual: " + qual);

      return qual;
   }

   public static String TN_NEXTID = "IUSERNEXTID";
   private static final Logger _logger = Logger.getLogger(LdapUsersTable.class);
}


