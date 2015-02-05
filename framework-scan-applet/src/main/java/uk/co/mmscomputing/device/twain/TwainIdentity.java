package uk.co.mmscomputing.device.twain;

import java.util.*;

public class TwainIdentity implements TwainConstants{

// DAT_IDENTITY. Identifies the program/library/code resource.
/*
typedef struct {
   TW_UINT32  Id;                    // 0:    Unique number.  In Windows, application hWnd
   TW_VERSION Version;               // 4:    Identifies the piece of code
   TW_UINT16  ProtocolMajor;         // 46:   Application and DS must set to TWON_PROTOCOLMAJOR
   TW_UINT16  ProtocolMinor;         // 48:   Application and DS must set to TWON_PROTOCOLMINOR
   TW_UINT32  SupportedGroups;       // 50:   Bit field OR combination of DG_ constants
   TW_STR32   Manufacturer;          // 54:   Manufacturer name, e.g. "Hewlett-Packard"
   TW_STR32   ProductFamily;         // 88:   Product family name, e.g. "ScanJet"
   TW_STR32   ProductName;           // 122:  Product name, e.g. "ScanJet Plus"
} TW_IDENTITY, FAR * pTW_IDENTITY;   // 156:
*/
// No DAT needed.  Describes version of software currently running.
/*
typedef struct {
   TW_UINT16  MajorNum;              // 4+0:    Major revision number of the software.
   TW_UINT16  MinorNum;              // 4+2:    Incremental revision number of the software.
   TW_UINT16  Language;              // 4+4:    e.g. TWLG_SWISSFRENCH
   TW_UINT16  Country;               // 4+6:    e.g. TWCY_SWITZERLAND
   TW_STR32   Info;                  // 4+8:    e.g. "1.0b3 Beta release"
} TW_VERSION, FAR * pTW_VERSION;     // 4+42:
*/

  private   TwainSourceManager manager;
  protected byte[]             identity;

  TwainIdentity(TwainSourceManager manager){
    this.manager=manager;
    this.identity=new byte[156];
  }

  TwainIdentity(TwainSourceManager manager,byte[] identity){
    this.manager=manager;
    this.identity=identity;
  }

  boolean isTwain20Source(){
    if(!manager.isTwain20Manager()){return false;}         // if manager is not 2.0 then we cannot use callback mechanism anyway.
    int supportedGroups = jtwain.getINT32(identity,50);
    return ((supportedGroups&DF_DS2)==DF_DS2);
  }

  void maskTwain20Source(){
    int supportedGroups = jtwain.getINT32(identity,50);
    jtwain.setINT32(identity,50,supportedGroups&(~DF_DS2));
  }

// DG_CONTROL,DAT_IDENTITY,MSG_GET                         // [1] 7-178 valid state 3 - 7
// called only from source manager!

  void getDefault(){                                       // [1] 7-179 valid state 3 - 7
    try{
      manager.call(DG_CONTROL,DAT_IDENTITY,MSG_GETDEFAULT,identity);
    }catch(TwainIOException tioe){
    }
  }

  void userSelect()throws TwainIOException{                // [1] 7-188 valid states 3 - 7
//    jtwain.setINT32(identity,0,0);
    manager.call(DG_CONTROL,DAT_IDENTITY,MSG_USERSELECT,identity);
  }

  void open()throws TwainIOException{                      // [1] 7-184 state 3: 3 -> 4
    manager.call(DG_CONTROL,DAT_IDENTITY,MSG_OPENDS,identity);
  }

  void getFirst()throws TwainIOException{                  // [1] 7-180 state 3 - 7
    manager.call(DG_CONTROL,DAT_IDENTITY,MSG_GETFIRST,identity);
  }

  void getNext()throws TwainIOException{                   // [1] 7-182 state 3 - 7
    manager.call(DG_CONTROL,DAT_IDENTITY,MSG_GETNEXT,identity);
  }

  public int getId(){return jtwain.getINT32(identity,0);}
  public int getMajorNum(){return jtwain.getINT16(identity,4);}
  public int getMinorNum(){return jtwain.getINT16(identity,6);}
  public int getLanguage(){return jtwain.getINT16(identity,8);}
  public int getCountry(){return jtwain.getINT16(identity,10);}

  public String getInfo(){
    String info="";
    for(int i=12;(identity[i]!=0)&&(i<46);i++){
      info+=(char)identity[i];
    }
    return info;
  }

  public int getProtocolMajor(){return jtwain.getINT16(identity,46);}
  public int getProtocolMinor(){return jtwain.getINT16(identity,48);}
  public int getSupportedGroups(){return jtwain.getINT32(identity,50);}

  public String getManufacturer(){
    String manufacturer="";
    for(int i=54;(identity[i]!=0)&&(i<88);i++){
      manufacturer+=(char)identity[i];
    }
    return manufacturer;
  }

  public String getProductFamily(){
    String productFamily="";
    for(int i=88;(identity[i]!=0)&&(i<122);i++){
      productFamily+=(char)identity[i];
    }
    return productFamily;
  }

  public String getProductName(){
    String productName="";
    for(int i=122;(identity[i]!=0)&&(i<156);i++){
      productName+=(char)identity[i];
    }
    return productName;
  }

  public String toString(){
    String s="TwainIdentity\n";
    s+="\tid               = 0x"+Integer.toHexString(getId())+"\n";
    s+="\tmajorNum         = 0x"+Integer.toHexString(getMajorNum())+"\n";
    s+="\tminorNum         = 0x"+Integer.toHexString(getMinorNum())+"\n";
    s+="\tlanguage         = 0x"+Integer.toHexString(getLanguage())+"\n";
    s+="\tcountry          = 0x"+Integer.toHexString(getCountry())+"\n";
    s+="\tinfo             = "+getInfo()+"\n";
    s+="\tprotocol major   = 0x"+Integer.toHexString(getProtocolMajor())+"\n";
    s+="\tprotocol minor   = 0x"+Integer.toHexString(getProtocolMinor())+"\n";
    s+="\tsupported groups = 0x"+Integer.toHexString(getSupportedGroups())+"\n";
    s+="\tmanufacturer     = "+getManufacturer()+"\n";
    s+="\tproduct family   = "+getProductFamily()+"\n";
    s+="\tproduct name     = "+getProductName()+"\n";

    s+="\ttwain 2.0 source = "+isTwain20Source()+"\n";
    return s;
  }

  static public TwainIdentity[] getIdentities()throws TwainIOException{
    TwainSourceManager manager=jtwain.getSourceManager();
    Vector identities=new Vector();
    try{
      TwainIdentity identity=new TwainIdentity(manager);
      identity.getFirst();                                 // get first identity
      identities.add(identity);
      while(true){                                         // while(not EndOfList Exception thrown)
        identity=new TwainIdentity(manager);
        identity.getNext();                                // get next identity
        identities.add(identity);
      }
    }catch(TwainResultException.EndOfList treeol){
    }catch(TwainIOException tioe){
      System.out.println("uk.co.mmscomputing.device.twain.TwainIdentity.getIdentities:\n\t"+tioe);
    }
    return (TwainIdentity[])identities.toArray(new TwainIdentity[0]);
  }

  static public String[] getProductNames()throws TwainIOException{
    TwainSourceManager manager=jtwain.getSourceManager();
    Vector identities=new Vector();
    try{
      TwainIdentity identity=new TwainIdentity(manager);
      identity.getFirst();                                 // get first identity
      identities.add(identity.getProductName());
      while(true){                                         // while(not EndOfList Exception thrown)
        identity=new TwainIdentity(manager);
        identity.getNext();                                // get next identity
        identities.add(identity.getProductName());
      }
    }catch(TwainResultException.EndOfList treeol){
    }catch(TwainIOException tioe){
      System.out.println("uk.co.mmscomputing.device.twain.TwainIdentity.getProductNames:\n\t"+tioe);
    }
    return (String[])identities.toArray(new String[0]);
  }

}