package uk.co.mmscomputing.device.twain;

/**
 * Used by jtwain for loading native libraries. Allow clients of jtwain to
 * change the native library loading strategy before jtwain is first loaded.
 * 
 * @author michael.lossos@softwaregoodness.com
 * 
 */
public class TwainNativeLoadStrategySingleton {
    private TwainINativeLoadStrategy nativeLoadStrategy = new TwainDefaultNativeLoadStrategy();

    /**
     * Ensure thread safe lazy singleton initialization
     */
    private static class TwainNativeLoadStrategyInstance {
        public static TwainNativeLoadStrategySingleton instance = new TwainNativeLoadStrategySingleton();
    }

    public static TwainNativeLoadStrategySingleton getInstance() {
        return TwainNativeLoadStrategyInstance.instance;
    }

    public TwainINativeLoadStrategy getNativeLoadStrategy() {
        return nativeLoadStrategy;
    }

    public void setNativeLoadStrategy( TwainINativeLoadStrategy nativeLoadStrategy ) {
        this.nativeLoadStrategy = nativeLoadStrategy;
    }
}
