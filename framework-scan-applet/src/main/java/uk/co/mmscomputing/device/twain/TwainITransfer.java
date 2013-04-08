package uk.co.mmscomputing.device.twain;

/**
 * Interface representing a TWAIN transfer method, such as native or file.
 * 
 * @author michael.lossos@softwaregoodness.com
 * 
 */
public interface TwainITransfer {
    void initiate() throws TwainIOException;
    void finish() throws TwainIOException;
    void setCancel(boolean isCancelled);
    void cancel() throws TwainIOException;
    void cleanup() throws TwainIOException;
}
