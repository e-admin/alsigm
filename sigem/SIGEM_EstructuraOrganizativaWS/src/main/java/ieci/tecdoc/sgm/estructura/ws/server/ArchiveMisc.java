package ieci.tecdoc.sgm.estructura.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;


public class ArchiveMisc  extends RetornoServicio{
   private String fdrName;
   private int volListType;
   private int volListId;
   
   public ArchiveMisc(){  
  
	   }
   
   public ArchiveMisc(String fdrName, int volListType, int volListId){  
      this.fdrName     = fdrName;
      this.volListType = volListType;
      this.volListId   = volListId;  
   }
   
   public String getFdrName(){
      return fdrName;
   }
   
   public void setFdrName(String fdrName){
      this.fdrName = fdrName;
   }
   
   public int getVolListId(){
      return volListId;
   }
   
   public void setVolListId(int volListId){
      this.volListId = volListId; 
   }
   
   public int getVolListType(){
      return volListType;
   }
   
   public void setVolListType(int volListType){
      this.volListType = volListType;
   }

   public void setMisc(String fdrName, int volListId, int volListType){
      this.fdrName = fdrName;
      this.volListId = volListId;
      this.volListType = volListType;
   }
   
}

