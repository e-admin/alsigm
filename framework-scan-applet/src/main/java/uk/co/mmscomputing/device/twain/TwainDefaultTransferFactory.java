package uk.co.mmscomputing.device.twain;

/**
 * Provide the default TWAIN transfer mechanisms.
 * 
 * @author michael.lossos@softwaregoodness.com
 * 
 */
public class TwainDefaultTransferFactory implements TwainITransferFactory {

    public TwainITransfer createFileTransfer( TwainSource twainSource ) {
        return new TwainTransfer.FileTransfer( twainSource );
    }

    public TwainITransfer createMemoryTransfer( TwainSource twainSource ) {
        return new TwainTransfer.MemoryTransfer( twainSource );
    }

    public TwainITransfer createNativeTransfer( TwainSource twainSource ) {
        return new TwainTransfer.NativeTransfer( twainSource );
    }

}
