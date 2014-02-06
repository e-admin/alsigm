package ieci.tecdoc.core.centera;

import ieci.tecdoc.core.exception.IeciTdException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import com.filepool.fplibrary.FPClip;
import com.filepool.fplibrary.FPLibraryConstants;
import com.filepool.fplibrary.FPLibraryException;
import com.filepool.fplibrary.FPTag;

public final class CenteraBasicFns
{
   private static int    m_errCode;
   private static String m_errMsg;
   private static String DEFAULT_CLIP_NAME = "iDocClipFile";
   private static String DEFAULT_TAG_NAME = "iDocFileTag";
   
   private CenteraBasicFns()
   {
   }
   
   public static int Centera_getErrCode()
   {
      return m_errCode;
   }
   
   public static String Centera_getErrMsg()
   {
      return m_errMsg;
   }
   
   public static String Centera_storeFile(CenteraCnnCfg cfg, 
                                          String fileName)throws Exception
   {
      String           fileId = "";
      CenteraConnection cnt = null;
      
      try
      {
         cnt = new CenteraConnection();
         cnt.open(cfg);
         
         fileId = Centera_storeFile(cnt,fileName);
         
         cnt.close();
        
         return fileId;
      }
      catch(Exception e)
      {
         throw e;         
      }
      finally
      {
         cnt.close();
      }
      
            
   }
   public static String Centera_storeFile(CenteraConnection cnt, 
         											String fileName)throws Exception
   {
      String          fileId = "";
      FPClip          imageClip = null;
      FPTag           topTag = null;
      FPTag           imageTag = null;
      FileInputStream imageStream = null;


      try
      {  
         imageClip = new FPClip(cnt.getPool(), DEFAULT_CLIP_NAME);
         topTag = imageClip.getTopTag();
         imageTag = new FPTag(topTag, DEFAULT_TAG_NAME);
         topTag.Close();
         topTag = null;

         imageStream = new FileInputStream(fileName);

         imageTag.BlobWrite(imageStream,FPLibraryConstants.FP_OPTION_CLIENT_CALCID_STREAMING);

         imageTag.Close();
         imageTag = null;

         fileId = imageClip.Write();

         imageStream.close();
         imageStream = null;
         imageClip.Close();
         imageClip = null;
        
      }
      catch(FPLibraryException e)
      {
         //	Obtener error de centera y guardarlo en las variables miembro
         m_errCode = e.getErrorCode();
         m_errMsg = e.getMessage();       

         throw e;
      }     
      catch(Exception e)
      {
         throw e;
      }
      finally
      {
         if (topTag != null)
            topTag.Close();
         if (imageTag != null)
            imageTag.Close();
         if (imageStream != null)
            imageStream.close();
         if (imageClip != null)
            imageClip.Close();                  
      }
      
      return fileId;

   }

   public static String Centera_storeBytes(CenteraCnnCfg cfg, 
         												byte[] fileCont)
   							throws Exception
   {
      String          fileId = "";      
      CenteraConnection cnt = null;

      try
      {
         cnt = new CenteraConnection();
         cnt.open(cfg);

         fileId = Centera_storeBytes(cnt,fileCont);
         
         cnt.close();

         return fileId;
      }
      catch(Exception e)
      {
         throw e;
      }
      finally
      {
         cnt.close();
      }

   }

   
   public static String Centera_storeBytes(CenteraConnection cnt, 
                                           byte[] fileCont)
   throws Exception
   {
      String          fileId = "";
      FPClip          imageClip = null;
      FPTag           topTag = null;
      FPTag           imageTag = null;
      ByteArrayInputStream imageStream = null;
      
      try
      {         
         
         imageClip = new FPClip(cnt.getPool(), DEFAULT_CLIP_NAME);
         topTag = imageClip.getTopTag();
         imageTag = new FPTag(topTag, DEFAULT_TAG_NAME);
         topTag.Close();
         topTag = null;

         imageStream = new ByteArrayInputStream(fileCont);

         imageTag.BlobWrite(imageStream,FPLibraryConstants.FP_OPTION_CLIENT_CALCID_STREAMING);

         imageTag.Close();
         imageTag = null;

         fileId = imageClip.Write();

         imageStream.close();
         imageStream = null;
         imageClip.Close();
         imageClip = null;         
                  
      }
      catch(FPLibraryException e)
      {
         //Obtener error de centera y guardarlo en las variables miembro
         m_errCode = e.getErrorCode();
         m_errMsg = e.getMessage();       

         throw e;
      }     
      catch(Exception e)
      {
         throw e;
      }
      finally
      {
         if (topTag != null)
            topTag.Close();
         if (imageTag != null)
            imageTag.Close();
         if (imageStream != null)
            imageStream.close();
         if (imageClip != null)
            imageClip.Close();                  
      }
      
      return fileId;
}
   
   
   public static void Centera_retrieveFile(CenteraCnnCfg cfg,String fileId,String fileName)
                 throws Exception
   {      
      FPClip           imageClip = null;
      FPTag            topTag = null;
      FPTag            nextTag = null;
      FPTag            imageTag = null;
      String           name;
      FileOutputStream imageStream = null;
      CenteraConnection cnt = null;
    
      try
      {
         cnt = new CenteraConnection();
         cnt.open(cfg);
         
         Centera_retrieveFile(cnt,fileId,fileName);
         
         cnt.close();           
      }     
      catch(Exception e)
      {
         throw e;
      }
      finally
      {
         cnt.close();
      }
      
   }
   
   public static void Centera_retrieveFile(CenteraConnection cnt,String fileId, String fileName)
   						throws Exception
   {      
      FPClip           imageClip = null;
      FPTag            topTag = null;
      FPTag            nextTag = null;
      FPTag            imageTag = null;
      String           name;
      FileOutputStream imageStream = null;

      try
      {
         imageClip = new FPClip(cnt.getPool(),fileId,FPLibraryConstants.FP_OPEN_ASTREE);

         topTag = imageClip.getTopTag();
         nextTag = topTag.getFirstChild();
         topTag.Close();
         topTag = null;

         while(nextTag != null)
         {
            name = nextTag.getTagName();
            if (name.equalsIgnoreCase(DEFAULT_TAG_NAME))
               break;

            FPTag prevTag = nextTag;
            nextTag = prevTag.getSibling();
            prevTag.Close();
         }
         if (nextTag == null)
            throw new IeciTdException(CenteraError.EC_NO_FOUND_TAG, 
                  							CenteraError.EM_NO_FOUND_TAG);

         imageTag = nextTag;

         imageStream = new FileOutputStream(new File(fileName));

         imageTag.BlobRead(imageStream);

         imageStream.close();
         imageStream = null;
         imageTag.Close();
         imageTag = null;
         imageClip.Close();
         imageClip = null;

      }
      catch(FPLibraryException e)
      {
         //Obtener error de centera y guardarlo en las variables miembro
         m_errCode = e.getErrorCode();
         m_errMsg = e.getMessage();
         throw e;
      }     
      catch(Exception e)
      {
         throw e;
      }
      finally
      {	
         if (topTag != null)
            topTag.Close();
         if (imageStream != null)        
            imageStream.close();
         if (imageTag != null)
            imageTag.Close();
         if (imageClip != null)
            imageClip.Close();
      }

   }
   
   public static byte[] Centera_retrieveBytes(CenteraCnnCfg cfg,String fileId)
   							throws Exception
   {    
      byte[]            fileCount;
      CenteraConnection cnt = null;

      try
      {
         cnt = new CenteraConnection();
         cnt.open(cfg);
         
         fileCount = Centera_retrieveBytes(cnt,fileId);
         
         cnt.close();

      }
      catch(Exception e)
      {
         throw e;
      }
      finally
      {
         cnt.close();
      }
      
      return fileCount;

   }
   
   public static byte[] Centera_retrieveBytes(CenteraConnection cnt,String fileId)
								throws Exception
	{    
     FPClip           imageClip = null;
      FPTag            topTag = null;
      FPTag            nextTag = null;
      FPTag            imageTag = null; 
      String           name;
      ByteArrayOutputStream imageStream = null;
      byte[]           fileCount;

      try
      {
         imageClip = new FPClip(cnt.getPool(),fileId,FPLibraryConstants.FP_OPEN_ASTREE);

         topTag = imageClip.getTopTag();
         nextTag = topTag.getFirstChild();
         topTag.Close();
         topTag = null;

         while(nextTag != null)
         {
            name = nextTag.getTagName();
            if (name.equalsIgnoreCase(DEFAULT_TAG_NAME))
               break;

            FPTag prevTag = nextTag;
            nextTag = prevTag.getSibling();
            prevTag.Close();
         }
         
         if (nextTag == null)
            throw new IeciTdException(CenteraError.EC_NO_FOUND_TAG, 
                  CenteraError.EM_NO_FOUND_TAG);

         imageTag = nextTag;

         imageStream = new ByteArrayOutputStream();

         imageTag.BlobRead(imageStream);

         fileCount = imageStream.toByteArray();

         imageStream.close();
         imageStream = null;
         imageTag.Close();
         imageTag = null;
         imageClip.Close();
         imageClip = null;


      }	
      catch(FPLibraryException e)
      {
         //	Obtener error de centera y guardarlo en las variables miembro
         m_errCode = e.getErrorCode();
         m_errMsg = e.getMessage();
         throw e;
      }     
      catch(Exception e)
      {
         throw e;
      }
      finally
      {
         if (topTag != null)
            topTag.Close();
         if (imageStream != null)        
            imageStream.close();
         if (imageTag != null)
            imageTag.Close();
         if (imageClip != null)
            imageClip.Close();               
      }

      return fileCount;

	}

   
   public static void Centera_deleteFile(CenteraCnnCfg cfg,String fileId)
         throws Exception
   {      
      CenteraConnection cnt = null;
      String            name;
      
      try
      {
         cnt = new CenteraConnection();
         cnt.open(cfg);
         Centera_deleteFile(cnt,fileId);
         
         cnt.close();                  
         
      }           
      catch(Exception e)
      {
         throw e;
      }
      finally
      {
         cnt.close();
      }
   }

   public static void Centera_deleteFile(CenteraConnection cnt,String fileId)
   	throws Exception
   {      

      String  name;

      try
      {         
         FPClip.Delete(cnt.getPool(),fileId);         
   
      }
      catch(FPLibraryException e)
      {
         //Obtener error de centera y guardarlo en las variables miembro
         m_errCode = e.getErrorCode();
         m_errMsg = e.getMessage();        
   
         throw e;
      }     
      catch(Exception e)
      {
         throw e;
      }
      
   }

}