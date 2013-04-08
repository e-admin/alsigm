package es.ieci.tecdoc.fwktd.sir.api.manager.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import es.ieci.tecdoc.fwktd.sir.api.manager.CompresionManager;
import es.ieci.tecdoc.fwktd.sir.api.vo.FicheroVO;
import es.ieci.tecdoc.fwktd.sir.core.exception.SIRException;

/**
 * Implementación del manager de compresión de contenidos siguiendo
 * la normativa SICRES 3.0.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class CompresionManagerImpl implements CompresionManager {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = LoggerFactory.getLogger(CompresionManagerImpl.class);

	/**
	 * Directorio temporal.
	 */
	private String directorioTemporal = null;

	/**
	 * Constructor.
	 */
	public CompresionManagerImpl() {
		super();
	}

	public String getDirectorioTemporal() {
		return directorioTemporal;
	}

	public void setDirectorioTemporal(String directorioTemporal) {
		this.directorioTemporal = directorioTemporal;
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.sir.api.manager.CompresionManager#comprimirFichero(es.ieci.tecdoc.fwktd.sir.api.vo.FicheroVO)
	 */
	public File comprimirFichero(FicheroVO fichero) {

		logger.info("Comprimiendo el fichero: [{}]", (fichero != null ? fichero.getNombre() : null));

		List<FicheroVO> ficheros = new ArrayList<FicheroVO>();
		if (fichero != null) {
			ficheros.add(fichero);
		}

		return comprimir(ficheros);
	}
	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.sir.api.manager.CompresionManager#comprimirFicheros(java.util.List)
	 */
	public File comprimirFicheros(List<FicheroVO> ficheros) {

		logger.info("Comprimiendo ficheros");

		return comprimir(ficheros);
	}

	private File comprimir(List<FicheroVO> ficheros) {

		File ficheroComprimido = new File(getDirectorioTemporal(), "tmp" + new Date().getTime() + ".zip");
		FileOutputStream fos = null;

		try {

			fos = new FileOutputStream(ficheroComprimido);
			ZipOutputStream zos = new ZipOutputStream(fos);

			if (CollectionUtils.isNotEmpty(ficheros)) {
				for (FicheroVO fichero : ficheros) {
					zos.putNextEntry(new ZipEntry(fichero.getNombre()));
					zos.write(fichero.getContenido());
				}
			}

			zos.close();
			fos.flush();

			logger.info("Fichero comprimido: [{}]", ficheroComprimido.getName());

		} catch (Exception e) {
			logger.error("Error al comprimir el fichero [" + ficheroComprimido.getName() + "]", e);
			throw new SIRException("error.sir.comprimirFicheros", null, e.getMessage());
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					logger.error("Error al cerrar el fichero comprimido [" + ficheroComprimido.getName() + "]", e);
				}
			}
		}

		return ficheroComprimido;
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.sir.api.manager.CompresionManager#descomprimirFichero(java.io.File, java.io.File)
	 */
	public void descomprimirFichero(File compressedFile, File destDir) {

		logger.info("Descomprimiento el fichero [{}] al directorio [{}]", compressedFile.getName(), destDir.getName());

		Assert.notNull(compressedFile, "'src' must not be null");
		Assert.isTrue(compressedFile.isFile(), "'src' is not a file");

		Assert.notNull(destDir, "'destDir' must not be null");

		// Asegurarse que el directorio existe
		if (!destDir.exists()) {
			if (destDir.mkdirs()) {
				logger.info("Directorio [{}] creado", destDir.getName());
			}
		}

		try {

			ZipInputStream zin = new ZipInputStream(new FileInputStream(compressedFile));
			ZipEntry ze = null;

			while ((ze = zin.getNextEntry()) != null) {

				logger.info("Descomprimiendo: {}", ze.getName());

				if (ze.isDirectory()) {
					File directory = new File(destDir, ze.getName());
					logger.info("Creando directorio: {}", directory.getName());
					directory.mkdir();
				} else {
					File file = new File(destDir, ze.getName());
					logger.info("Creando fichero: {}", file.getName());
					FileOutputStream out = new FileOutputStream(file);
					copy(zin, out);
					zin.closeEntry();
					out.flush();
					out.close();
				}
			}

			zin.close();

			logger.info("Fichero [{}] descomprimido en el directorio [{}]", compressedFile.getName(), destDir.getName());

		} catch (Exception e) {
			logger.error("Error al descomprimir el fichero [" + compressedFile.getName() + "]", e);
			throw new SIRException("error.sir.descomprimirFichero", new String[] { compressedFile.toString(), destDir.toString() }, e.getMessage());
		}
	}

	private static void copy(InputStream in, OutputStream out)
			throws IOException {
		byte buffer[] = new byte[4096];
		for (int count = in.read(buffer, 0, buffer.length); count > 0; count = in .read(buffer, 0, buffer.length)) {
			out.write(buffer, 0, count);
		}
	}
}
