package uk.co.mmscomputing.device.twain;

public class TwainResultException extends TwainIOException{

  private int rc=-1;

  public TwainResultException(String msg,int rc){
    super(msg+"\n\trc="+rc);
    this.rc=rc;
  }

  public int getResultCode(){return rc;}

  static public class CheckStatus extends TwainResultException{
    public CheckStatus(){super("Source could not fulfill request.",TWRC_CHECKSTATUS);}
  }

  static public class Cancel extends TwainResultException{
    public Cancel(){super("User cancelled twain operation.",TWRC_CANCEL);}
  }

  static public class DSEvent extends TwainResultException{
    public DSEvent(){super("Data source event.",TWRC_DSEVENT);}
  }

  static public class NotDSEvent extends TwainResultException{
    public NotDSEvent(){super("No data source event.",TWRC_NOTDSEVENT);}
  }

  static public class TransferDone extends TwainResultException{
    public TransferDone(){super("Image transfer done.",TWRC_XFERDONE);}
  }

  static public class EndOfList extends TwainResultException{
    public EndOfList(){super("End of List.",TWRC_ENDOFLIST);}
  }

  static public class InfoNotSupported extends TwainResultException{
    public InfoNotSupported(){super("Info not supported.",TWRC_INFONOTSUPPORTED);}
  }

  static public class DataNotAvailable extends TwainResultException{
    public DataNotAvailable(){super("Info not supported.",TWRC_DATANOTAVAILABLE);}
  }
}

