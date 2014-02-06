package ieci.tecdoc.sbo.fss.core;

import java.io.File;
import java.rmi.dgc.VMID;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *  Abstract from IETF Specification:
 *  <p>
 *  This specification defines the format of UUIDs (Universally Unique
 *  IDentifier), also known as GUIDs (Globally Unique IDentifier). A UUID
 *  is 128 bits long, and if generated according to the one of the
 *  mechanisms in this document, is either guaranteed to be different
 *  from all other UUIDs/GUIDs generated until 3400 A.D. or extremely
 *  likely to be different (depending on the mechanism chosen). UUIDs
 *  were originally used in the Network Computing System (NCS) [1] and
 *  later in the Open Software Foundation's (OSF) Distributed Computing
 *  Environment [2].
 *  <p>
 *  References:
 *  <ul>
 *  <li>Lisa Zahn, et. al., Network Computing Architecture, Prentice
 *      Hall, Englewood Cliffs, NJ, 1990</li>
 *  <li>DCE: Remote Procedure Call, Open Group CAE Specification C309
 *      ISBN 1-85912-041-5 28cm. 674p. pbk. 1,655g. 8/94</li>
 *  <li>R. Rivest, RFC 1321, "The MD5 Message-Digest Algorithm",
 *      04/16/1992.</li>
 *  <li>NIST FIPS PUB 180-1, "Secure Hash Standard," National Institute
 *      of Standards and Technology, U.S. Department of Commerce, DRAFT, May
 *      31, 1994.</li>
 *  </ul>
 *  <p>
 *  This specification is derived from the latter specification with the
 *  kind permission of the OSF.
 *  <p>
 *  Copyright (c) 1990- 1993, 1996 Open Software Foundation, Inc.<br>
 *  Copyright (c) 1989 by Hewlett-Packard Company, Palo Alto, Ca. &
 *                     Digital Equipment Corporation, Maynard, Mass. <br>
 *  Copyright (c) 1998 Microsoft.<br>
 *  <p>
 *
 * 
 * @version  1.0
 */
final public class FssGuid  
{   
    /**
     *  Valid values for HEX characters
     */
   private static final char[] HEX_CHARS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
   private static final char[] STRING_BUFFER = { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', };

    /**
     * Offset of time bewteen January 1, 1970.  and October 15, 1582 in milliseconds
     */  
    
   private final static long   UTC_OFFSET           = 0x01B21DD213814000L;
   private final static short  GUID_VERSION_1       = 0x1000;
   private final static byte   GUID_RESERVED        = (byte) 0x80;
   private final static int    MAX_CLOCK_SEQ_ADJUST = 9999;
   private final static Object _internalsLock       = new Object();
   private static byte[]       _ieee802Addr         = new byte[6];
   private static boolean      _internalsSet        = false;
   private static long         _lastTimestamp       = 0;
   private static int          _clock_seq_adjust    = 0;
   private static int          _osProcessId         = 0;
   private static int          _rand_ia             = 0;
   private static int          _rand_ib             = 0;
   private static int          _rand_irand          = 0;
   private static int          _rand_m              = 0;
   private static final char[] hexChars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
    
    /**
     * The low order 32 bits of the adjusted timestamp.
     */
   private int   time_low = 0;

    /**
     * The middle order 16 bits of the adjusted timestamp.
     */
   private short time_mid = 0;

    /**
     * The high order 12 bits of the adjusted timestamp multiplexed with the 
     * version number.  The version number is in the most significant 4 bits.
     *
     * The following table lists currently defined versions of the UUID.
     * <p><blockquote><pre>
     *   Msb0  Msb1  Msb2  Msb3  Version  Description
     *
     *    0     0     0     1       1     The IEEE 802 address, time-based version.
     *
     *    0     0     1     0       2     Reserved for DCE Security version, with
     *                                    embedded POSIX UIDs.
     *
     *    0     0     1     1       3     The name-based version.
     *
     *    0     1     0     0       4     The pseudo-randomly generated version
     * </pre></blockquote>
     */
   private short time_hi_and_version = 0;

    /**
     * The high order 14 bits of the clock sequence multiplexed with two
     * reserved bits.
     * <p>
     * For Guid version 1, the clock sequence is used to help avoid
     * duplicates that could arise when the clock is set backwards in time
     * or if the node ID changes.
     * <p>
     * If the clock is set backwards, or even might have been set backwards
     * (e.g., while the system was powered off), and the Guid generator can
     * not be sure that no Guids were generated with timestamps larger than
     * the value to which the clock was set, then the clock sequence has to
     * be changed. If the previous value of the clock sequence is known, it
     * can be just incremented; otherwise it should be set to a random or
     * high-quality pseudo random value.
     * <p>
     * Similarly, if the node ID changes (e.g. because a network card has
     * been moved between machines), setting the clock sequence to a random
     * number minimizes the probability of a duplicate due to slight
     * differences in the clock settings of the machines. (If the value of
     * clock sequence associated with the changed node ID were known, then
     * the clock sequence could just be incremented, but that is unlikely.)
     * <p>
     * The clock sequence MUST be originally (i.e., once in the lifetime of
     * a system) initialized to a random number to minimize the correlation
     * across systems. This provides maximum protection against node
     * identifiers that may move or switch from system to system rapidly.
     * The initial value MUST NOT be correlated to the node identifier.
     * <p>
     * For Guid version 3, it is a 14 bit value constructed from a name.
     * <p>
     * For Guid version 4, it is a randomly or pseudo-randomly generated 14
     * bit value.
     */
   private byte clock_seq_hi_and_reserved = 0;

    /**
     * The low order 16 bits of the clock sequence.
     *
     * @see #clock_seq_hi_and_reserved
     *
     */
   private byte clock_seq_low = 0;

    /**
     * For Guid version 1, the node field consists of the IEEE address,
     * usually the host address. For systems with multiple IEEE 802
     * addresses, any available address can be used. The lowest addressed
     * octet (octet number 10) contains the global/local bit and the
     * unicast/multicast bit, and is the first octet of the address
     * transmitted on an 802.3 LAN.
     * <p>
     * For systems with no IEEE address, a randomly or pseudo-randomly
     * generated value may be used (see section 4). The multicast bit must
     * be set in such addresses, in order that they will never conflict with
     * addresses obtained from network cards.
     * <p>
     * For Guid version 3, the node field is a 48 bit value constructed from
     * a name.
     * <p>
     * For Guid version 4, the node field is a randomly or pseudo-randomly
     * generated 48 bit value.
     */
   private byte[] node = new byte[6];
   
    /** 
     * Creates a variant #1 style identifier and return the instance.  The system
     * executing this factory should have access to a LAN Adapter card for its
     * IEEE 802 address.
     * <p>
     * The lowest addressed octet contains the global/local bit and the
     * unicast/multicast bit, and is the first octet of the address transmitted
     * on an 802.3 LAN.
     * <p>
     * The adjusted time stamp is split into three fields, and the clockSeq
     * is split into two fields.
     * <pre>
     * |<------------------------- 32 bits -------------------------->|
     *
     * +--------------------------------------------------------------+
     * |                     low 32 bits of time                      |  0-3  .time_low
     * +-------------------------------+-------------------------------
     * |     mid 16 bits of time       |  4-5               .time_mid
     * +-------+-----------------------+
     * | vers. |   hi 12 bits of time  |  6-7               .time_hi_and_version
     * +-------+-------+---------------+
     * |Res|  clkSeqHi |  8                                 .clock_seq_hi_and_reserved
     * +---------------+
     * |   clkSeqLow   |  9                                 .clock_seq_low
     * +---------------+----------...-----+
     * |            node ID               |  8-16           .node
     * +--------------------------...-----+
     * </pre>
     * 
     */
   public FssGuid() 
   {
      long adjustedTimestamp = 0;
      long clockSeq = 0;

        // Set fields to null
      initialize();

        // Instead of having small synchronized pieces as it was done before,
        // it is necessary to have one long uninterrupted synchronized block.
        // Thus we prevent multiple access to _clock_seq_adjust and _lastTimestamp
        // values.
      synchronized (_internalsLock)
      {
            // The first time this method is called, it will need to obtain the
            // operating system internals.  It uses the JNI functions defined earlier,
            // and will set a static variable to avoid this in subsequent calls.
            
         if (_internalsSet == false)
         {
            getPseudoIEEE802Address(_ieee802Addr);

            _osProcessId = getPseudoOSProcessId();

            adjustedTimestamp = getAdjustedTimestamp();
            initTrueRandom(adjustedTimestamp);
            clockSeq = getTrueRandom();

            _internalsSet = true;
         }
         
            // Get the adjusted timestamp and use to generate a random clock sequence.
            // We must handle the situation where the current time is the same as the
            // previous generation or has actually gone backwards.
         boolean timeIsValid = true;

         do
         {
            adjustedTimestamp = getAdjustedTimestamp();
                
                // Clock has been reset, generate a new random clockSeq.  The two
                // most significant bits will be set to the reserved value later.
            if (adjustedTimestamp < _lastTimestamp)
            {
               clockSeq = getTrueRandom();
               _clock_seq_adjust = 0;
            }

                // Normal situation, reset the adjustment and leave the do-while loop.
            if (adjustedTimestamp > _lastTimestamp)
            {
               _clock_seq_adjust = 0;
            }

                // Clock hasn't changed resolution, adjust the clock sequence for uniqueness.
                // If we hit the maximum adjustment, we must "spin" until the clock is
                // incremented.
            if (adjustedTimestamp == _lastTimestamp)
            {
               if (_clock_seq_adjust < MAX_CLOCK_SEQ_ADJUST)
               {
                  _clock_seq_adjust++;
               }
               else
               {
                  timeIsValid = false;
               };
            }
         }
         while (!timeIsValid);

         _lastTimestamp = adjustedTimestamp;
        
         if (_clock_seq_adjust != 0)
         {
            adjustedTimestamp += _clock_seq_adjust;
         }
      }

        // Construct a Version 1 GUID from the component pieces and constants.  The
        // variable tempValue allows us to perform masking and bit shifting
        // operations separate from casting the value into the appropriate size.
      long tempValue;
      
      tempValue                 = adjustedTimestamp & 0x00000000FFFFFFFFL;
      time_low                  = (int) tempValue;
      tempValue                 = (adjustedTimestamp >> 32) & 0x0FFF;
      time_mid                  = (short) tempValue;
      tempValue                 = (adjustedTimestamp >> 48) & 0x0FFF;
      time_hi_and_version       = (short) tempValue;
      time_hi_and_version      += GUID_VERSION_1;
      tempValue                 = clockSeq & 0xFF;
      clock_seq_low             = (byte) tempValue;
      tempValue                 = (clockSeq & 0x3F00) >> 8;
      clock_seq_hi_and_reserved = (byte) tempValue;
      clock_seq_hi_and_reserved += GUID_RESERVED;

        // Copy contains of _ieee802Addr into each Guid object
      System.arraycopy(_ieee802Addr, 0, node, 0, node.length);
   }

    //~ Methods ····················································································

    /**
     * Obtains the current timestamp and adjust its value
     */
   private static long getAdjustedTimestamp()
   {
        // The system time is recorded as the number of milliseconds since
        // January 1, 1970.  The coordinated universal time is measured from
        // the begininning of the Gregorian Calendar, so we must adjust back
        // with the number of milliseconds between October 15, 1582 and
        // January 1, 1970.  Then we convert into 100-nanosecond resolution by
        // multiplying by 10**4.
      long currentTimestamp  = System.currentTimeMillis();
      long adjustedTimestamp = (currentTimestamp * 10000L) + UTC_OFFSET;
       
      return adjustedTimestamp;
   }

    /**
     * Initializes instance of Guid with null values.
     */
   private void initialize()
   {
      time_low = 0;
      time_mid = 0;
      time_hi_and_version = 0;
      clock_seq_low = 0;
      clock_seq_hi_and_reserved = 0;

      for (int j = 0; j < node.length; j++)
      {
         node[j] = 0x00;
      }
   }

    /**
     * Initializes true number values generator.
     */
   private static synchronized void initTrueRandom(long adjustedTimestamp)
   {
        // This random number generator (RNG) is based on Note 16.7, July 7, 1989
        // by Robert Gries, Cambridge Research Lab, Computational Quality Group
        //
        // It is really a "Multiple Prime Random Number Generator" (MPRNG) and is
        // completely discussed in Reference 1.
        //
        //   References:
        //   1) "The Multiple Prime Random Number Generator" by Alexander Hass
        //      pp. 368 to 381 in ACM Transactions on Mathematical Software,
        //      December, 1987
        //   2) "The Art of Computer Programming: Seminumerical Algorithms
        //      (vol 2)" by Donald E. Knuth, pp. 39 to 113.
        //
        // A summary of the notesfile entry follows:
        //
        // Gries discusses the two RNG's available for ULTRIX-C.  The default RNG
        // uses a Linear Congruential Method (very popular) and the second RNG uses
        // a technique known as a linear feedback shift register.
        //
        // The first (default) RNG suffers from bit-cycles (patterns/repetition),
        // ie. it's "not that random."
        //
        // While the second RNG passes all the emperical tests, there are "states"
        // that become "stable", albeit contrived.
        //
        // Gries then presents the MPRNG and says that it passes all emperical
        // tests listed in Reference 2.  In addition, the number of calls to the
        // MPRNG before a sequence of bit position repeats appears to have a normal
        // distribution.
        //
        // The code uses the Gries's MPRNG with the same constants that were
        // presented in his paper.  These may not be "ideal" for the range of
        // numbers we are dealing with.
      _rand_m     = 971;
      _rand_ia    = 11113;
      _rand_ib    = 104322;
      _rand_irand = 4181;

        // Generating our 'seed' value
        //
        // We start with the current time, but, since the resolution of clocks is
        // system hardware dependent and most likely coarser than our resolution
        // (100 nanosecond increments), we 'mixup' the bits by xor'ing all the
        // bits together in 16-bit chunks.  This will have the effect of
        // involving all of the bits in the determination of the seed value while
        // remaining system independent.  To ensure a unique seed when there
        // are multiple processes creating Guid's on a system, we add in the PID.
      int seed = (int) (adjustedTimestamp >>> 48) ^ (int) (adjustedTimestamp >>> 32) ^ (int) (adjustedTimestamp >>> 16) ^ (int) (adjustedTimestamp & 0x000000000000FFFF);

      _rand_irand = _rand_irand + seed + _osProcessId;
   }

    /**
     * Get true random value.
     */
   private static synchronized short getTrueRandom()
   {
        // Increment the Multiplier and Adders
      _rand_m  = _rand_m + 7;
      _rand_ia = _rand_ia + 1907;
      _rand_ib = _rand_ib + 73939;

      // Keep the Multiplier and Adders within the specified range
      if (_rand_m >= 9973)
      {
         _rand_m = _rand_m - 9871;
      }

      if (_rand_ia >= 99991)
      {
         _rand_ia = _rand_ia - 89989;
      }

      if (_rand_ib >= 224729)
      {
         _rand_ib = _rand_ib - 96233;
      }

      // Calculate the new Random Value (from previous or seed)
      _rand_irand = (_rand_irand * _rand_m) + _rand_ia + _rand_ib;
      _rand_irand = (_rand_irand >>> 16) ^ (_rand_irand & 0x00003FFF);

      return (short) _rand_irand;
   }

    /**
     * This call originally would use JNI to return the 
     * process ID of the java program. Because we do not want to support
     * JNI on all the platforms we will simply get the time stamp.
     * Because the value that is being returned should be an int and not a long we 
     * will devide by 1000 and return the numbe rof seconds not milliseconds
     * 
     * @return the current time in seconds
     */
    private int getPseudoOSProcessId()
    {
        return (int) (System.currentTimeMillis() / 1000);
    }

    /**
      * This call originally would use JNI to return the 
      * IEEE 802 addrsss from the system. Because we do not want to support
      * JNI on all the platforms we be using an alternative method
      * of generating a value for an IEEE 802 that is describe in the internet draft.
      * 
      * The following is the excert from the draft:
      * 
      * 4. Node IDs when no IEEE 802 network card is available
      * 
      * If a system wants to generate UUIDs but has no IEE 802 compliant
      * network card or other source of IEEE 802 addresses, then this section
      * describes how to generate one.
      * 
      * The ideal solution is to obtain a 47 bit cryptographic quality random
      * number, and use it as the low 47 bits of the node ID, with the most
      * significant bit of the first octet of the node ID set to 1. This bit
      * is the unicast/multicast bit, which will never be set in IEEE 802
      * addresses obtained from network cards; hence, there can never be a
      * conflict between UUIDs generated by machines with and without network
      * cards.
      * 
      * If a system does not have a primitive to generate cryptographic
      * quality random numbers, then in most systems there are usually a
      * fairly large number of sources of randomness available from which one
      * can be generated. Such sources are system specific, but often
      * include:
      * 
      * - the percent of memory in use
      * - the size of main memory in bytes
      * - the amount of free main memory in bytes
      * - the size of the paging or swap file in bytes
      * - free bytes of paging or swap file
      * - the total size of user virtual address space in bytes
      * - the total available user address space bytes
      * - the size of boot disk drive in bytes
      * - the free disk space on boot drive in bytes
      * - the current time
      * - the amount of time since the system booted
      * - the individual sizes of files in various system directories
      * - the creation, last read, and modification times of files in various
      *   system directories
      * - the utilization factors of various system resources (heap, etc.)
      * - current mouse cursor position
      * - current caret position
      * - current number of running processes, threads
      * - handles or IDs of the desktop window and the active window
      * - the value of stack pointer of the caller
      * - the process and thread ID of caller
      * - various processor architecture specific performance counters
      *   (instructions executed, cache misses, TLB misses)
      * 
      * (Note that it precisely the above kinds of sources of randomness that
      * are used to seed cryptographic quality random number generators on
      * systems without special hardware for their construction.)
      * 
      * In addition, items such as the computer's name and the name of the
      * operating system, while not strictly speaking random, will help
      * differentiate the results from those obtained by other systems.
      * 
      * The exact algorithm to generate a node ID using these data is system
      * specific, because both the data available and the functions to obtain
      * them are often very system specific. However, assuming that one can
      * concatenate all the values from the randomness sources into a buffer,
      * and that a cryptographic hash function such as MD5 [3] is available,
      * then any 6 bytes of the MD5 hash of the buffer, with the multicast
      * bit (the high bit of the first byte) set will be an appropriately
      * random node ID.
      */
   private void getPseudoIEEE802Address(byte[] ieee802Addr)
   {
      byte[] freeMemory      = getByteArrayFromString(String.valueOf(Runtime.getRuntime().freeMemory()));
      byte[] totalMemory     = getByteArrayFromString(String.valueOf(Runtime.getRuntime().totalMemory()));
      byte[] numberOfRoots   = getByteArrayFromString(String.valueOf(File.listRoots().length));
      byte[] currentTime     = getByteArrayFromString(String.valueOf(System.currentTimeMillis()));
      byte[] numberOfThreads = getByteArrayFromString(String.valueOf(Thread.currentThread().getThreadGroup().getParent().activeCount()));
      byte[] vmid            = new VMID().toString().getBytes();

      // count number of bytes in all the files in the root directory
      long count                = 0;
      File rootDirectory        = new File("/");
      File[] files              = rootDirectory.listFiles();
      byte[] totalRootFilesSize = { 0 };

      if (files != null)
      {
         for (int i = 0; i < files.length; i++)
         {
            count += files[i].length();
         }

         totalRootFilesSize = getByteArrayFromString(String.valueOf(count));
      }

      byte[] hashcode = null;
      byte[] bytes    = new byte[freeMemory.length + totalMemory.length + numberOfRoots.length + currentTime.length + numberOfThreads.length + vmid.length + totalRootFilesSize.length];

      int bytesPos = 0;

      System.arraycopy(freeMemory, 0, bytes, bytesPos, freeMemory.length);
      bytesPos += freeMemory.length;
      System.arraycopy(totalMemory, 0, bytes, bytesPos, totalMemory.length);
      bytesPos += totalMemory.length;
      System.arraycopy(numberOfRoots, 0, bytes, bytesPos, numberOfRoots.length);
      bytesPos += numberOfRoots.length;
      System.arraycopy(currentTime, 0, bytes, bytesPos, currentTime.length);
      bytesPos += currentTime.length;
      System.arraycopy(numberOfThreads, 0, bytes, bytesPos, numberOfThreads.length);
      bytesPos += numberOfThreads.length;
      System.arraycopy(vmid, 0, bytes, bytesPos, vmid.length);
      bytesPos += vmid.length;
      System.arraycopy(totalRootFilesSize, 0, bytes, bytesPos, totalRootFilesSize.length);
      bytesPos += totalRootFilesSize.length;

      try
      {
         MessageDigest md5 = MessageDigest.getInstance("MD5");
         md5.reset();
         hashcode = md5.digest(bytes); /* Compute hashcode       */
      }
      catch (NoSuchAlgorithmException nsax)
      {
         nsax.printStackTrace();
      }

      System.arraycopy(hashcode, 0, ieee802Addr, 0, 6);

      // turn on the highorder bit of the 1 st byte
      ieee802Addr[0] = (byte) (ieee802Addr[0] | (byte) 0x80);

      //     return ieee802Addr;
   }

   private static byte[] getByteArrayFromString(String s)
   {
      int dataLength = s.length();
      s += "  ";

      String hcs = new String(hexChars);
      byte[] data = new byte[dataLength / 2];

      for (int j = 0;(j < (dataLength - 1)); j += 2)
      {
         data[j / 2] = (byte) (hcs.indexOf(s.substring(j, j + 1)) << 4);
         data[j / 2] += hcs.indexOf(s.substring(j + 1, j + 2));
      }

      return data;
   }

    /**
     * Returns the hexidecimal string representation of the Guid.
     * <p>
     * The <code>toString</code> method will take the binary data in each of
     * the internal components of the Guid, covert each byte to its hexidecimal
     * character equivalent, and return the string.
     * <p> Each internal component representation, however, is separated by a
     * hyphen character, so the total string length is 36 bytes (16 bytes at 2
     * hexidecimal characters per byte plus four hyphens).  Here is an example:
     * <p>
     * <pre>
     *       012345678901234567890123456789012345
     *
     *       6ba7b814-9dad-11d1-80b4-00c04fd430c8</pre>
     * <p>
     * This is the preferred format for visual display, which can include use in
     * <ul>
     * <li>trace records and diagnostic tools,
     * <li>inclusion in print or standard output,
     * <li>storage in configuration files and definitions,
     * <li>user interface displays (not generally recommended).
     * </ul>
     *
     * @return  the hexidecimal string array, which is 36 bytes long.
     * @see     #toByteArray
     */
   public String toString()
   {
      int pos = 0; // Position in the STRING_BUFFER
      int shift = 28; // Number of bits to shift the value being converted

      for (pos = 0; pos < 8; pos++)
      {

         //The first character of the GUID must be a letter:
         if (pos == 0)
            STRING_BUFFER[pos] = 'N';
         else
            STRING_BUFFER[pos] = HEX_CHARS[(time_low >> shift & 0xF)];

         shift = shift - 4;
      }

      //    STRING_BUFFER[8] = '-';
      shift = 12;

      for (pos = 8; pos < 12; pos++)
      {
         STRING_BUFFER[pos] = HEX_CHARS[(time_mid >> shift & 0xF)];
         shift = shift - 4;
      }

        //    STRING_BUFFER[13] = '-';
      shift = 12;

      for (pos = 12; pos < 16; pos++)
      {
         STRING_BUFFER[pos] = HEX_CHARS[(time_hi_and_version >> shift & 0xF)];
         shift = shift - 4;
      }

        //    STRING_BUFFER[18] = '-';
      STRING_BUFFER[16] = HEX_CHARS[(clock_seq_hi_and_reserved >> 4 & 0xF)];
      STRING_BUFFER[17] = HEX_CHARS[(clock_seq_hi_and_reserved & 0xF)];
      STRING_BUFFER[18] = HEX_CHARS[(clock_seq_low >> 4 & 0xF)];
      STRING_BUFFER[19] = HEX_CHARS[(clock_seq_low & 0xF)];

        //    STRING_BUFFER[23] = '-';
      int i = 0;

      for (pos = 20; pos < 32; pos = pos + 2)
      {
         STRING_BUFFER[pos] = HEX_CHARS[(node[i] >> 4 & 0xF)];
         STRING_BUFFER[pos + 1] = HEX_CHARS[(node[i] & 0xF)];
         i++;
      }

      String hexString = new String(STRING_BUFFER);

      return (hexString.trim());
   }
         
}
