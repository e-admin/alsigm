package ieci.tecdoc.sbo.util.nextid;

import ieci.tecdoc.core.db.DbColumnDef;
import ieci.tecdoc.core.db.DbDataType;
import ieci.tecdoc.core.db.DbSelectFns;
import ieci.tecdoc.core.db.DbUtil;

public final class DaoNextIdTbl
{

   // **************************************************************************

   private String              m_tblName;

   private final DbColumnDef   CD_TYPE = new DbColumnDef("TYPE",
                                             DbDataType.LONG_INTEGER, false);

   private final DbColumnDef   CD_ID   = new DbColumnDef("ID",
                                             DbDataType.LONG_INTEGER, false);

   private final DbColumnDef[] ACD     =
                                       { CD_TYPE, CD_ID};

   private final String        ACN     = DbUtil.getColumnNames(ACD);

   // **************************************************************************

   public DaoNextIdTbl(String tblName)
   {
      m_tblName = tblName;
   }

   // **************************************************************************

   private String getDefaultQual(int type)
   {
      return "WHERE " + CD_TYPE.getName() + "=" + type;
   }

   public void incrementNextId(int type) throws Exception
   {

      incrementNextId(type, (short) 1);

   }

   public void incrementNextId(int type, short incr) throws Exception
   {

      String stmtText;

      stmtText = "UPDATE " + m_tblName + " SET " + CD_ID.getName() + "="
            + CD_ID.getName() + "+" + incr + " WHERE " + CD_TYPE.getName()
            + "=" + type;

      DbUtil.executeStatement(stmtText);

   }

   public int getNextId(int type) throws Exception
   {

      int nextId;

      nextId = DbSelectFns.selectLongInteger(m_tblName, CD_ID.getName(),
            getDefaultQual(type));

      return nextId;

   }

} // class
