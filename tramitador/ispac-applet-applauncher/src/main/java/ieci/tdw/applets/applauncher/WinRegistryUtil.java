package ieci.tdw.applets.applauncher;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.logging.Logger;

final class WinRegistryUtil {

	private static final Logger LOGGER = Logger.getLogger("es.gob.minetur.sigm"); //$NON-NLS-1$

	private static final String REG_SZ = "REG_SZ"; //$NON-NLS-1$
	private static final String REG_EXPAND_SZ = "REG_EXPAND_SZ"; //$NON-NLS-1$
	private static final String DOT_EXE = ".exe"; //$NON-NLS-1$
	private static final String HKEY_CLASSES_ROOT_PATH = "HKCR\\"; //$NON-NLS-1$

	private WinRegistryUtil() {
		// Prohibida la instanciacion
	}

    private static String readRegistry(final String keyPath) {
        try {
		ProcessBuilder pb = new ProcessBuilder("reg", "QUERY", keyPath, "/ve"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
            try {
		final Class<?> redirectClass = Class.forName("java.lang.ProcessBuilder$Redirect"); //$NON-NLS-1$
		final Object pipeRedirectValue = redirectClass.getField("PIPE").get(null); //$NON-NLS-1$
		ProcessBuilder.class.getMethod("redirectOutput", redirectClass).invoke(pb, pipeRedirectValue); //$NON-NLS-1$
		ProcessBuilder.class.getMethod("redirectInput", redirectClass).invoke(pb, pipeRedirectValue); //$NON-NLS-1$
            }
            catch (final Exception e) {
		// Se ignora
            }

		Process process = pb.start();

            StreamReader reader = new StreamReader(process.getInputStream());
            reader.start();
            process.waitFor();
            reader.join();
            String output = reader.getResult().trim();

            if (output.contains(REG_EXPAND_SZ)) {
		output = output.replace("%SystemRoot%", System.getenv("SystemRoot")) //$NON-NLS-1$ //$NON-NLS-2$
				.replace("%ProgramFiles%", "ProgramFiles") //$NON-NLS-1$ //$NON-NLS-2$
				.replace(REG_EXPAND_SZ, REG_SZ);
            }

            if (!output.contains(REG_SZ)) {
		return null;
            }

            output = output.substring(output.indexOf(REG_SZ) + REG_SZ.length(), output.length()).trim();

            return output;

        }
        catch (Exception e) {
		LOGGER.severe("Ha ocurrido un error al obtener la asociacion del registro: " + e); //$NON-NLS-1$
            return null;
        }

    }

    private static class StreamReader extends Thread {
        private final InputStream is;
        private final StringWriter sw= new StringWriter();

        StreamReader(final InputStream is) {
            this.is = is;
        }

        @Override
		public void run() {
            try {
                int c;
                while ((c = this.is.read()) != -1) {
					this.sw.write(c);
				}
            }
            catch (final IOException e) {
		// Se ignora
            }
        }

        String getResult() {
            return this.sw.toString();
        }
    }

    static String getAssociatedProgram(final String extensionIncludingDot) {
	if (!System.getProperty("os.name").contains("indows")) {  //$NON-NLS-1$//$NON-NLS-2$
		return null;
	}
	if (extensionIncludingDot == null) {
		return null;
	}
	String proc = WinRegistryUtil.readRegistry(HKEY_CLASSES_ROOT_PATH + extensionIncludingDot);
	if (proc == null) {
		return null;
	}
	String comm = WinRegistryUtil.readRegistry(HKEY_CLASSES_ROOT_PATH + extensionIncludingDot + "\\shell\\open\\command"); //$NON-NLS-1$
		if (comm == null) {
			return getAssociatedProgram(proc);
		}
		if (!comm.toLowerCase().contains(DOT_EXE)) {
			return null;
		}
		comm = comm.replace("\"", "").substring(0, comm.toLowerCase().indexOf(DOT_EXE) + DOT_EXE.length()); //$NON-NLS-1$ //$NON-NLS-2$
		if (!new File(comm).exists()) {
			return null;
		}
		if (comm.toLowerCase().endsWith("rundll32.exe")) { //$NON-NLS-1$
			return null;
		}
		return comm;
    }
}
