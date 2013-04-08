package uk.co.mmscomputing.device.sane;

import uk.co.mmscomputing.device.sane.gui.SaneSelectPanel;


public class SaneDeviceManager implements SaneConstants{

  private SaneDevice device;
  private String     devicename;
  private boolean    onlyLocal;

  SaneDeviceManager(){
    device     = null;
    devicename = null;
    onlyLocal  = true;
  }

  public void setDevice(String name){
    devicename = name;
  }

  public void setLocalOnly(boolean local){onlyLocal=local;}

  public String[] getDevices()throws SaneIOException{
    return jsane.getDevices(onlyLocal);
  }

  synchronized SaneDevice getDevice()throws SaneIOException{
    if((device!=null)&&(device.isBusy()||device.getName().equals(devicename))){
      return device;
    }
    if((devicename==null)||(devicename.equals(""))){
      jsane.init();
      try{
        String[] devices=getDevices();
        if(devices.length==0){
          throw new SaneIOException(getClass(),"getDevice","SaneDeviceManager.ex.nodevicesinstalled");
        }
        devicename=devices[0];
      }finally{
        jsane.exit();
      }
    }
    device = new SaneDevice(devicename);
    return device;
  }

  void selectDevice()throws SaneIOException{
    jsane.init();
    try{
      String[] devices=jsane.getDevices(onlyLocal);
      new SaneSelectPanel(this,devices).showDialog();
    }finally{
      jsane.exit();
    }
  }

  void selectDevice(String name)throws SaneIOException{
    jsane.init();
    try{
      String[] devices=jsane.getDevices(onlyLocal);
      for(int i=0;i<devices.length;i++){                           
//        System.out.println(getClass().getName()+".selectDevice:\n\t"+devices[i]);
        if(devices[i].equals(name)){
          devicename=devices[i];
          return;
        }
      }
      throw new SaneIOException(getClass(),"selectDevice","SaneDeviceManager.ex.cannotfinddevice",name);
    }finally{
      jsane.exit();
    }
  }
}