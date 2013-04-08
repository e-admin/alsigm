
package ieci.tecdoc.sbo.idoc.folder.std;


public final class FolderLockInfo 
{
   private boolean  m_locked;
   private int      m_lockerId;
  
   public FolderLockInfo(boolean locked, int lockerId)
   {  
      m_locked   = locked;
      m_lockerId = lockerId;    
   }   
   
   public boolean isLocked()
   {
      return m_locked;
   }
   
   public int getLockerId()
   {
      return m_lockerId;
   }
   
} // class
