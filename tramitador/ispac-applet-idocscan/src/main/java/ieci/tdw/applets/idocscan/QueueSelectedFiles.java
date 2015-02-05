package ieci.tdw.applets.idocscan;

import java.util.Vector;
import java.io.File;


public class QueueSelectedFiles
{

   private Vector   queueListeners;

   public QueueSelectedFiles()
   {
      queueListeners  =  new Vector();
   }

   public synchronized void addFile(File newFile)
      throws Exception
   {
      if ( newFile instanceof MyFile)
		{
         Debug.logToFile(this, "newFile: "+((MyFile)newFile).getDocId() );
			if ( queueListeners.size()!=0 )
			{
				for (int i = 0; i < queueListeners.size() ;i++ )
				{
					MyFile file     =  (MyFile)queueListeners.get(i);
					String newDocId =  ((MyFile)newFile).getDocId();
					
               Debug.logToFile(this, "file: "+file.getDocId() );
					
               if ( !file.getDocId().equals( newDocId ) )
					{
						Debug.logToFile(this, "adding new File: "+newFile);
						queueListeners.addElement(newFile);
						return;
					}else
					{
						Debug.logToFile(this, "preventing duplicate file in queue:" + newFile);
					}
				}

			}else
			{
				queueListeners.addElement(newFile);
				return;
			}

		}else
		{
			if(queueListeners.contains(newFile))
			{
				Debug.logToFile(this, "preventing duplicate file in queue:" + newFile);
				throw new Exception(ConfigMessages.getString("QueueSelectedFiles.0"));
			}
			else
			{
				Debug.logToFile(this, "adding new File: "+newFile);
				queueListeners.addElement(newFile);
				return;
			}
		}

   }

	
   public synchronized void removeFile(File file)
   {
      queueListeners.removeElement(file);
   }

   public synchronized void removeFileAt(int i)
   {
      queueListeners.removeElementAt(i);
   }

   public synchronized File getFileAt(int index)
   {
      if(queueListeners.size() == 0)
      {
         return null;
      }
      if(index >= queueListeners.size())
      {
         return null; 
      }
      else
      {
         return (File) queueListeners.get(index);
      }
   }

   public synchronized int size()
   {
      return queueListeners.size();
   }

   public void removeUploadedFiles()
   {
      int   numFiles =  queueListeners.size();
      
      if(numFiles > 0)
      {
         int  i  = queueListeners.size() - 1;
      
         for(;i >= 0; i--)
         {
            queueListeners.remove(i);
         }
      }

   }

   public void removeAllFiles()
   {
      queueListeners.clear();
   }

	public void setOnlyModified( File path )
	{
		File[] files          =  path.listFiles() ;
      
      Debug.logToFile(this, "paths: "+ files);
      
      if(files != null)
      {
      	Vector modifiedFiles  =  new Vector();
      	int    fileCount      =  this.queueListeners.size() ;
      	int    j              =  0;
		
      	int newFilesCount = files.length ;
      	while ( j < newFilesCount )
      	{
      		compare(fileCount, files, j, modifiedFiles);
      		j++;
      	}
      	this.queueListeners = modifiedFiles;
      }
	}

	private void compare(int fileCount, File[] files, int j, Vector modifiedFiles)
	{
		MyFile oldFile ;
		File newFile ;
		for ( int i = 0; i <fileCount; i++ )
		{
			oldFile = (MyFile)this.queueListeners.get(i);
			newFile = files[j];
			if ( oldFile.getName().equals( newFile.getName() ) && oldFile.getModified()!=newFile.lastModified() )
			{
				Debug.logToFile(this, "File modified : "+oldFile.getName() );
				modifiedFiles.add(oldFile);
				break;
			}else if ( oldFile.getName().equals( newFile.getName() ) )
			{
				break;
			}
		}
	}
}