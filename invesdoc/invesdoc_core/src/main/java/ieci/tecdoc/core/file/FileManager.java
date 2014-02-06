package ieci.tecdoc.core.file;

import ieci.tecdoc.core.exception.IeciTdException;

import ieci.tecdoc.core.miscelanea.Goodies;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.Reader;

public final class FileManager
{

   private FileManager()
   {
   }

   // **************************************************************************

   public static void storeFile(String dstLoc, byte[] srcData)
      throws Exception
   {
      writeBytesToFile(dstLoc, srcData);
   }

   public static void writeBytesToFile(String loc, byte[] data)
      throws Exception
   {

      File file;
      FileOutputStream fos = null;

      try
      {

         file = new File(loc);
         fos = new FileOutputStream(file);

         fos.write(data);
         fos.flush();

         fos.close();
         fos = null;

      }
      catch (Exception e)
      {
         try
         {
            if (fos != null) fos.close();
            throw e;
         }
         catch (Exception e1)
         {
            throw e;
         }
      }

   }

   public static void writeCharsToFile(String loc, char[] data)
      throws Exception
   {

      File file;
      FileWriter fw = null;

      try
      {

         file = new File(loc);
         fw = new FileWriter(file);

         fw.write(data);
         fw.flush();

         fw.close();
         fw = null;

      }
      catch (Exception e)
      {
         try
         {
            if (fw != null) fw.close();
            throw e;
         }
         catch (Exception e1)
         {
            throw e;
         }
      }

   }

   public static void writeStringToFile(String loc, String data)
      throws Exception
   {

      File file;
      FileWriter fw = null;

      try
      {

         file = new File(loc);
         fw = new FileWriter(file);

         fw.write(data);
         fw.flush();

         fw.close();
         fw = null;

      }
      catch (Exception e)
      {
         try
         {
            if (fw != null) fw.close();
            throw e;
         }
         catch (Exception e1)
         {
            throw e;
         }
      }

   }

   // **************************************************************************

   public static byte[] retrieveFile(String srcLoc)
      throws Exception
   {
      return readBytesFromFile(srcLoc);
   }

   public static byte[] readBytesFromFile(String loc)
      throws Exception
   {

      int size;
      byte[] data;
      File file;
      FileInputStream fis = null;

      try
      {

         size = getFileSize(loc);
         data = new byte[size];

         file = new File(loc);
         fis = new FileInputStream(file);

         fis.read(data);

         fis.close();
         fis = null;

         return data;

      }
      catch (Exception e)
      {
         try
         {
            if (fis != null) fis.close();
            throw e;
         }
         catch (Exception e1)
         {
            throw e;
         }
      }

   }

   public static char[] readCharsFromFile(String loc)
      throws Exception
   {

      int size;
      char[] data;
      File file;
      FileReader fr = null;

      try
      {

         size = getFileSize(loc);
         data = new char[size];

         file = new File(loc);
         fr = new FileReader(file);

         fr.read(data);

         fr.close();
         fr = null;

         return data;

      }
      catch (Exception e)
      {
         try
         {
            if (fr != null) fr.close();
            throw e;
         }
         catch (Exception e1)
         {
            throw e;
         }
      }

   }

   public static String readCharsFromFile(Reader fr) 
   throws Exception
   {
     char[] data = null;
     StringBuffer dataBuffer = null;
  
     try
     {
       data = new char [40960];
       dataBuffer = new StringBuffer();
       
       int c;
       while((c = fr.read(data))!=-1)
       {
         dataBuffer.append(data, 0, c);
       }
       
       fr.close();       
  
     }
     catch (Exception e)
     {
        try
        {
           if (fr != null) fr.close();
           throw e;
        }
        catch (Exception e1)
        {
           throw e;
        }
     }

     if (dataBuffer == null)
       return null;
     else
       return dataBuffer.toString();
   }
   
   public static String readStringFromFile(String loc)
      throws Exception
   {

      String data;
      char[] chars;

      chars = readCharsFromFile(loc);
      data = new String(chars);

      return data;

   }
   
   public static String readStringFromFile(Reader loc)
   throws Exception
   {
     return readCharsFromFile(loc);
   }

   public static String readStringFromResourceFile(String fileName)
      throws Exception
   {

      InputStream inputStream = null;
      String data = null;

      try
      {
         inputStream = Thread.currentThread().getContextClassLoader().
                       getResourceAsStream(fileName);
         data = Goodies.getText(inputStream);
      }
      catch (Exception exc)
      {
         throw exc;
      }
      finally
      {
         if (inputStream != null)
            inputStream.close();
      }
      
      return data;

   }

   // **************************************************************************

   public static boolean fileExists(String loc)
   {

      File file;

      file = new File(loc);

      return file.exists();

   }

   public static int getFileSize(String loc)
   {

      int size;
      File file;

      file = new File(loc);
      size = (int) file.length();

      return size;

   }

   public static void deleteFile(String loc)
      throws Exception
   {

      File file;
      boolean deleted;

      file = new File(loc);

      deleted = file.delete();

      if (!deleted) { throw new IeciTdException(FileError.EC_NOT_DELETED,
            FileError.EM_NOT_DELETED); }

   }

   public static void createDirectory(String loc)
      throws Exception
   {

      File dir;
      boolean created = true;

      dir = new File(loc);

      if (!dir.exists()) created = dir.mkdir();

      if (!created) { throw new IeciTdException(FileError.EC_NOT_CREATED,
            FileError.EM_NOT_CREATED); }

   }

   public static void createDirectoryX(String loc)
      throws Exception
   {

      File dir;
      boolean created;

      dir = new File(loc);

      created = dir.mkdirs();

      if (!created) { throw new IeciTdException(FileError.EC_NOT_CREATED,
            FileError.EM_NOT_CREATED); }

   }

   
   // PARCHE P9.0.2. Permite recuperar el contenido del documento en un File.
   public static void retrieveFile(String srcLoc, File destFile) throws Exception
    {
       byte[] data = new byte [1024*8];
       File file;
       FileInputStream fis = null;
       FileOutputStream fos = null;

       try
       {

          file = new File(srcLoc);
          fis = new FileInputStream(file);

          fos = new FileOutputStream(destFile);
          
          int n;
          while ((n = fis.read(data)) != -1) {
              fos.write(data, 0, n);
          }

          fis.close();
          fis = null;
          
          fos.flush();
          fos.close();
          fos = null;
       }
       catch (Exception e)
       {
          try
          {
             if (fis != null) fis.close();
          }
          catch (Throwable e1)
          {
          }
          
          try
          {
             if (fos != null) fos.close();
          }
          catch (Throwable e1)
          {
          }
          
          throw e;
       }

    }
   
   // PARCHE P9.0.2. Permite guardar el contenido de un documento desde un File.
   public static void storeFile(String dstLoc, File inputFile) throws Exception
    {
       File file;
       FileOutputStream fos = null;
       FileInputStream fis = null;
       try
       {

          file = new File(dstLoc);
          fos = new FileOutputStream(file);
          fis = new FileInputStream(inputFile);

          byte [] data = new byte [1024*8];
          int num;
          while ((num = fis.read(data)) != -1) {
              fos.write(data, 0, num);
          }
          
          fos.flush();

       } finally {
           if (fos != null) {
               try {
                   fos.close();
                   fos = null;
               } catch (Throwable t) {
                   
               }
           }
           
           if (fis != null) {
               try {
                   fis.close();
                   fis = null;
               } catch (Throwable t) {
                   
               }
           }
       }
    }
   
} // class
