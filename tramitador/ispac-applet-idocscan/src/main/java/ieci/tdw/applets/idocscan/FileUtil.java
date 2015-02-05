package ieci.tdw.applets.idocscan;

import java.io.File;
import java.util.StringTokenizer;

public class FileUtil
{

   public String formatFileName(String fileName, String fileExt)
   {

      if(fileName.endsWith(fileExt))
      {
         return fileName;
      } else
      {
         int   lastDot  =  fileName.lastIndexOf(".");
         
         if(lastDot != -1)
         {
            String fileNameExtension = fileName.substring(lastDot, fileName.length());

            if(fileNameExtension.length() > 5)
            {
               return fileName += fileExt;
            } else
            {
               fileName = fileName.substring(0, lastDot);

               return fileName += fileExt;
            }
         } else
         {
            return fileName += fileExt;
         }
      }
   }

   public static String getFileExt(String fileName)
   {
      int      lastDot              = fileName.lastIndexOf(".");
      String   fileNameExtension;

      if(lastDot != -1)
      {
         fileNameExtension =  fileName.substring(lastDot, fileName.length());
         
         if(fileNameExtension.length() > 5)
         {
               fileNameExtension =  fileNameExtension.substring(0, 5);
         }
      } else 
      {
         return null;
      }
      
      if(fileNameExtension.startsWith("."))
      {
            fileNameExtension =  fileNameExtension.substring(1, fileNameExtension.length());
      }

      return fileNameExtension.trim();
   }
   
   public static String getPath(File file)
   {
       String  name  =  file.getName() ;
       
       return  file.getPath().substring(0, file.getPath().lastIndexOf(name) );
   }

	public static String getURLPath(String path)
	{
		StringTokenizer tokens   =  new StringTokenizer(path,"/");
		
      if (tokens.hasMoreElements())
		{
			return "/"+tokens.nextElement().toString()+"/" ;
		}
		else
		{
			return path;
		}

	}
	public static String getFileNameFromHeader(String header)
	{
      return header.substring(header.lastIndexOf("=")+1,header.length()-1 );
	}
}