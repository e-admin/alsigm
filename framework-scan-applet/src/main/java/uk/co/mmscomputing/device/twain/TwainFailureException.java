package uk.co.mmscomputing.device.twain;

import uk.co.mmscomputing.device.scanner.*;

public class TwainFailureException extends TwainResultException{

/* Condition Codes: Application gets these by doing DG_CONTROL DAT_STATUS MSG_GET.  */
/*
#define TWCC_CUSTOMBASE         0x8000

#define TWCC_SUCCESS            0 // It worked!                                
#define TWCC_BUMMER             1 // Failure due to unknown causes             
#define TWCC_LOWMEMORY          2 // Not enough memory to perform operation    
#define TWCC_NODS               3 // No Data Source                            
#define TWCC_MAXCONNECTIONS     4 // DS is connected to max possible applications      
#define TWCC_OPERATIONERROR     5 // DS or DSM reported error, application shouldn't   
#define TWCC_BADCAP             6 // Unknown capability                        
#define TWCC_BADPROTOCOL        9 // Unrecognized MSG DG DAT combination       
#define TWCC_BADVALUE           10 // Data parameter out of range              
#define TWCC_SEQERROR           11 // DG DAT MSG out of expected sequence      
#define TWCC_BADDEST            12 // Unknown destination Application/Source in DSM_Entry 
#define TWCC_CAPUNSUPPORTED     13 // Capability not supported by source            
#define TWCC_CAPBADOPERATION    14 // Operation not supported by capability         
#define TWCC_CAPSEQERROR        15 // Capability has dependancy on other capability 
*/

  private int cc;

  public TwainFailureException(int cc){
    super(createMessage("Failed during call to twain source.",cc),TwainConstants.TWRC_FAILURE);
  }

  public TwainFailureException(String msg,int cc){
    super(createMessage(msg,cc),TwainConstants.TWRC_FAILURE);
  }

  public int getConditionCode(){return cc;}

  static private String createMessage(String msg,int cc){
    try{
      return msg+"\n\tcc="+TwainConstants.info[cc];
    }catch(Exception e){
      return msg+"\n\tcc=0x"+Integer.toHexString(cc);
    }
  }

  static public TwainFailureException create(int cc){
    switch(cc){
    case TWCC_BADCAP:         return new TwainFailureException.BadCap();
    case TWCC_CAPUNSUPPORTED: return new TwainFailureException.CapUnsupported();
    }
    return new TwainFailureException(cc);
  }

  static public class BadCap extends TwainFailureException{
    public BadCap(){super(TWCC_BADCAP);}
  }

  static public class CapUnsupported extends TwainFailureException{
    public CapUnsupported(){super(TWCC_CAPUNSUPPORTED);}
  }

}

