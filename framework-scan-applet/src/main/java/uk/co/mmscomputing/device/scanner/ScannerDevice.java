package uk.co.mmscomputing.device.scanner;

public interface ScannerDevice{

  // show User Interface
  public void setShowUserInterface(boolean show)throws ScannerIOException;
  public void setShowProgressBar(boolean show)throws ScannerIOException;

  // setResolution expects dots per inch
  public void setResolution(double dpi)throws ScannerIOException;

  // setRegionOfInterest expects pixels
  public void setRegionOfInterest(int x, int y, int width, int height)throws ScannerIOException;

  // setRegionOfInterest expects millimeters
  public void setRegionOfInterest(double x, double y, double width, double height)throws ScannerIOException;

  public void select(String name)throws ScannerIOException;

  public void    setCancel(boolean cancel);
  public boolean getCancel();

  public boolean isBusy();
}