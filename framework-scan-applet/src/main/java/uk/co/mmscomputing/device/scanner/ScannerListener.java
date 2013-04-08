package uk.co.mmscomputing.device.scanner;

public interface ScannerListener{
  public void update(ScannerIOMetadata.Type type, ScannerIOMetadata metadata);
}