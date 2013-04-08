package es.ieci.tecdoc.fwktd.time.ntp;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.NtpUtils;
import org.apache.commons.net.ntp.NtpV3Packet;
import org.apache.commons.net.ntp.TimeInfo;
import org.apache.commons.net.ntp.TimeStamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import es.ieci.tecdoc.fwktd.time.TimeService;
import es.ieci.tecdoc.fwktd.time.exception.TimeException;


public class NTPTimeServiceImpl implements TimeService {

    private static final NumberFormat numberFormat = new java.text.DecimalFormat("0.00");
	private static final Logger logger = LoggerFactory.getLogger(NTPTimeServiceImpl.class);

	/**
	 * Valor por defecto para el tiempo maximo de espera del servidor NTP
	 */
	private static final int DEFAULT_TIMEOUT = 10000;

	/**
	 * Valor por defecto del puerto a utilizar. Significa que no se le indica puerto
	 * si no que se utilizara el determinado como puerto por defecto
	 */
	private final int DEFAULT_PORT = 0;
	/**
	 * Direccion del servidor NTP
	 */
	private String host;

	/**
	 * Puerto del servidor NTP
	 */
	private int port = DEFAULT_PORT;

	private final String PROPERTY_NTP_HOST = 	"fwktd-time.ntp.host";
	private final String PROPERTY_NTP_PORT = 	"fwktd-time.ntp.port";
	private final String PROPERTY_NTP_TIMEOUT = "fwktd-time.ntp.timeout";


	/**
	 * Tiempo maximo de espera del servidor NTP, expresado en milisegundos
	 */
	private int timeout = DEFAULT_TIMEOUT;

//	public NTPTimeServiceImpl(String host) {
//		super();
//		timeout = DEFAULT_TIMEOUT;
//		this.host = host;
//		this.port = 0;
//	}
//
//	public NTPTimeServiceImpl(String host, int port) {
//		super();
//		timeout = DEFAULT_TIMEOUT;
//		this.host = host;
//		this.port = port;
//	}


	public NTPTimeServiceImpl(Properties properties){
		super();
		String host = properties.getProperty(PROPERTY_NTP_HOST);
		Assert.isTrue(StringUtils.isNotEmpty(host), "No se ha definido host del servidor NTP");
		String port = properties.getProperty(PROPERTY_NTP_PORT);
		String timeout = properties.getProperty(PROPERTY_NTP_TIMEOUT);

		this.host = host;
		if (StringUtils.isNotEmpty(port)){
			this.port = Integer.parseInt(port);
		}
		if (StringUtils.isNotEmpty(timeout)){
			this.port = Integer.parseInt(timeout);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.time.TimeService#getCurrentDate()
	 */
	public Date getCurrentDate() throws TimeException {
		TimeInfo timeInfo = getTimeInfo();
		Date date = timeInfo.getMessage().getReceiveTimeStamp().getDate();
		return date;
	}

	/**
	 * @return Objeto en el que se incorpora informacion de la hora actual del servidor consultado
	 * @throws TimeException
	 */
	private TimeInfo getTimeInfo() throws TimeException{
        Assert.notNull(host);
		NTPUDPClient client = new NTPUDPClient();
        client.setDefaultTimeout(timeout);
        try {
            client.open();
            InetAddress hostAddr = InetAddress.getByName(getHost());
            if (logger.isDebugEnabled()){
            	logger.debug("Consultando la hora del servidor: '" + getHost() + "'");
            }
            TimeInfo timeInfo = null;
            if (port != DEFAULT_PORT){
            	timeInfo = client.getTime(hostAddr, port);
            }else{
            	timeInfo = client.getTime(hostAddr);
            }

            if (logger.isDebugEnabled()){
            	processResponse(timeInfo);
            }

            return timeInfo;
        } catch (Exception e) {
        	logger.error("Error al consultar servidor de NTP",e);
        	throw new TimeException(e);
        }finally{
            client.close();
        }
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}



	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}


	/**
     * Procesa <code>TimeInfo</code> imprimiendo sus detalles.
     * @param info <code>TimeInfo</code> object.
     */
    public static void processResponse(TimeInfo info)
    {
        NtpV3Packet message = info.getMessage();
        int stratum = message.getStratum();
        String refType;
        if (stratum <= 0)
            refType = "(Unspecified or Unavailable)";
        else if (stratum == 1)
            refType = "(Primary Reference; e.g., GPS)"; // GPS, radio clock, etc.
        else
            refType = "(Secondary Reference; e.g. via NTP or SNTP)";
        // stratum should be 0..15...
        logger.debug(" Stratum: " + stratum + " " + refType);
        int version = message.getVersion();
        int li = message.getLeapIndicator();
        logger.debug(" leap=" + li + ", version="
                + version + ", precision=" + message.getPrecision());

        logger.debug(" mode: " + message.getModeName() + " (" + message.getMode() + ")");
        int poll = message.getPoll();
        // poll value typically btwn MINPOLL (4) and MAXPOLL (14)
        logger.debug(" poll: " + (poll <= 0 ? 1 : (int) Math.pow(2, poll))
                + " seconds" + " (2 ** " + poll + ")");
        double disp = message.getRootDispersionInMillisDouble();
        logger.debug(" rootdelay=" + numberFormat.format(message.getRootDelayInMillisDouble())
                + ", rootdispersion(ms): " + numberFormat.format(disp));

        int refId = message.getReferenceId();
        String refAddr = NtpUtils.getHostAddress(refId);
        String refName = null;
        if (refId != 0) {
            if (refAddr.equals("127.127.1.0")) {
                refName = "LOCAL"; // This is the ref address for the Local Clock
            } else if (stratum >= 2) {
                // If reference id has 127.127 prefix then it uses its own reference clock
                // defined in the form 127.127.clock-type.unit-num (e.g. 127.127.8.0 mode 5
                // for GENERIC DCF77 AM; see refclock.htm from the NTP software distribution.
                if (!refAddr.startsWith("127.127")) {
                    try {
                        InetAddress addr = InetAddress.getByName(refAddr);
                        String name = addr.getHostName();
                        if (name != null && !name.equals(refAddr))
                            refName = name;
                    } catch (UnknownHostException e) {
                        // some stratum-2 servers sync to ref clock device but fudge stratum level higher... (e.g. 2)
                        // ref not valid host maybe it's a reference clock name?
                        // otherwise just show the ref IP address.
                        refName = NtpUtils.getReferenceClock(message);
                    }
                }
            } else if (version >= 3 && (stratum == 0 || stratum == 1)) {
                refName = NtpUtils.getReferenceClock(message);
                // refname usually have at least 3 characters (e.g. GPS, WWV, LCL, etc.)
            }
            // otherwise give up on naming the beast...
        }
        if (refName != null && refName.length() > 1)
            refAddr += " (" + refName + ")";
        logger.debug(" Reference Identifier:\t" + refAddr);

        TimeStamp refNtpTime = message.getReferenceTimeStamp();
        logger.debug(" Reference Timestamp:\t" + refNtpTime + "  " + refNtpTime.toDateString());

        // Originate Time is time request sent by client (t1)
        TimeStamp origNtpTime = message.getOriginateTimeStamp();
        logger.debug(" Originate Timestamp:\t" + origNtpTime + "  " + origNtpTime.toDateString());

        long destTime = info.getReturnTime();
        // Receive Time is time request received by server (t2)
        TimeStamp rcvNtpTime = message.getReceiveTimeStamp();
        logger.debug(" Receive Timestamp:\t" + rcvNtpTime + "  " + rcvNtpTime.toDateString());

        // Transmit time is time reply sent by server (t3)
        TimeStamp xmitNtpTime = message.getTransmitTimeStamp();
        logger.debug(" Transmit Timestamp:\t" + xmitNtpTime + "  " + xmitNtpTime.toDateString());

        // Destination time is time reply received by client (t4)
        TimeStamp destNtpTime = TimeStamp.getNtpTime(destTime);
        logger.debug(" Destination Timestamp:\t" + destNtpTime + "  " + destNtpTime.toDateString());

        info.computeDetails(); // compute offset/delay if not already done
        Long offsetValue = info.getOffset();
        Long delayValue = info.getDelay();
        String delay = (delayValue == null) ? "N/A" : delayValue.toString();
        String offset = (offsetValue == null) ? "N/A" : offsetValue.toString();

        logger.debug(" Roundtrip delay(ms)=" + delay
                + ", clock offset(ms)=" + offset); // offset in ms
    }

}
