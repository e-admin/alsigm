package ieci.core.guid;

import ieci.core.db.DbConnection;

/*

 GUID: timestamp + clocksequence + node + pid

 Unidad de tiempo (UT): Diezmilésimas de milisegundo.

 timestamp. UT-s transcurridas desde 01-01-2000. 
 7 bytes. Unos 228 años.

 clocksequence. Por si se atrasa el reloj. 4 bits.

 node. MAC address. 6 bytes.

 pid. Identificador de proceso. 20 bits.

 En total, 16 bytes. Representados por 32 caracteres.

 System.currentTimeMillis() devuelve los milisegundos trasncurridos
 desde 01-01-1970.
 Tendremos que generar 10000 (TS_SCALE) GUID-s "artificiales" por cada
 milisegundo (clock counter).
 En lugar de 1970, partiremos de 2000. Habrá que aplicar un 
 offset conveniente (TS_OFFSET).
 Cuando se hayan generado 10000 GUID-s en el mismo milisegundo, 
 habrá que esperar a que cambie el milisegundo (SLEEP_TIME).

 */

/** Representa un GUID. */

final class Guid {

	private static final int MAX_PID = 0x100000; // 20 bits: 1.048.576

	private static final long TS_SCALE = 10000L;
	private static final long TS_OFFSET = 9466776000000000L;
	private static final long SLEEP_TIME = 1L;

	private static Object m_lockObj = new Object();
	private static boolean m_initialized = false;
	private static String m_node;
	private static long m_pid;
	private static long m_lastTimeMillis;
	private static long m_lastClockCounter;
	private static long m_lastClockSequence;

	private long m_tm;
	private long m_cc;
	private long m_cs;

	/** Construye un objeto de la clase. */

	Guid() {
	}

	void create(DbConnection conn) throws Exception {

		synchronized (m_lockObj) {

			if (!m_initialized)
				initialize(conn);

			generate();

		}

	}

	String getText() {

		long ts;
		StringBuffer buf1, buf2;

		buf1 = new StringBuffer(32);
		buf2 = new StringBuffer(32);

		ts = (m_tm * TS_SCALE) - TS_OFFSET + m_cc;
		buf2.append(Long.toHexString(ts));
		while (buf2.length() < 14) {
			buf2.insert(0, '0');
		}
		buf1.append(buf2.toString());

		buf1.append(Long.toHexString(m_cs));

		buf1.append(m_node);

		buf2.setLength(0);
		buf2.append(Long.toHexString(m_pid));
		while (buf2.length() < 5) {
			buf2.insert(0, '0');
		}
		buf1.append(buf2.toString());

		return buf1.toString();

	}

	private static void initialize(DbConnection conn) throws Exception {
		initializeNodeAndPid(conn);
		m_lastTimeMillis = 0L;
		m_lastClockCounter = 0L;
		m_lastClockSequence = 0L;
		m_initialized = true;
	}

	private static void initializeNodeAndPid(DbConnection conn)
			throws Exception {

		GuidDaoGenRecAc rec;

		rec = selectUpdatedRow(conn);

		m_node = rec.m_node;
		m_pid = rec.m_lpid;

	}

	private void generate() {

		long ctm;

		ctm = System.currentTimeMillis();

		if (ctm > m_lastTimeMillis) {
			m_lastTimeMillis = ctm;
			m_lastClockCounter = 0L;
		} else if (ctm == m_lastTimeMillis) {

			if (m_lastClockCounter < TS_SCALE) {
				m_lastClockCounter++;
			} else {

				do {

					try {
						Thread.sleep(SLEEP_TIME);
					} catch (InterruptedException e) {
					}

					ctm = System.currentTimeMillis();

				} while (ctm == m_lastTimeMillis);

				m_lastTimeMillis = ctm;
				m_lastClockCounter = 0L;

			}

		} else {
			m_lastTimeMillis = ctm;
			m_lastClockCounter = 0L;
			if (m_lastClockSequence < 15L)
				m_lastClockSequence++;
			else
				m_lastClockSequence = 0L;
		}

		m_tm = m_lastTimeMillis;
		m_cc = m_lastClockCounter;
		m_cs = m_lastClockSequence;

	}

	private static GuidDaoGenRecAc selectUpdatedRow(DbConnection conn)
			throws Exception {

		GuidDaoGenRecAc rec = null;
		boolean teniaTransaccion = conn.inTransaction();

		try {

			if (!teniaTransaccion) {
				conn.beginTransaction();
			}

			GuidDaoGenTbl.incrementLpid(conn);
			rec = GuidDaoGenTbl.selectRecAc(conn);
			if (rec.m_lpid == MAX_PID) {
				GuidDaoGenTbl.resetLpid(conn);
				rec.m_lpid = 0;
			}

			if (!teniaTransaccion)
				conn.endTransaction(true);

			return rec;

		} catch (Exception e) {
			try {
				if (!teniaTransaccion)
					conn.endTransaction(false);
				throw e;
			} catch (Exception e1) {
				throw e;
			}
		}

	}

} // class Guid
