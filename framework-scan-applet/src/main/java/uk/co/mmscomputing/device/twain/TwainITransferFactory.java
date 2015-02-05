package uk.co.mmscomputing.device.twain;

/**
 * Factory for creating twain transfer methods. This is used by TwainSource when
 * acquiring.
 * 
 * @author michael.lossos@softwaregoodness.com
 * 
 */
public interface TwainITransferFactory {
    TwainITransfer createNativeTransfer( TwainSource twainSource );
    TwainITransfer createFileTransfer(   TwainSource twainSource );
    TwainITransfer createMemoryTransfer( TwainSource twainSource );
}
