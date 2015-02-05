package es.ieci.tecdoc.fwktd.dm.invesdoc.manager.impl;

import ieci.tecdoc.sbo.idoc.api.ArchiveObject;
import ieci.tecdoc.sbo.idoc.api.FolderObject;
import ieci.tecdoc.sbo.idoc.archive.base.ArchiveTokenFld;
import ieci.tecdoc.sbo.idoc.archive.base.ArchiveTokenFlds;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ieci.tecdoc.fwktd.dm.business.config.ContentType;
import es.ieci.tecdoc.fwktd.dm.business.config.Destination;
import es.ieci.tecdoc.fwktd.dm.business.config.Mapping;
import es.ieci.tecdoc.fwktd.dm.business.exception.GestionDocumentalException;
import es.ieci.tecdoc.fwktd.dm.business.manager.MetadataManager;

/**
 * Implementación de la gestión de mapeos de metadatos en invesDoc.
 * @author Iecisa
 * @version $Revision$
 *
 */
public class InvesdocMetadataManagerImpl extends MetadataManager {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = LoggerFactory.getLogger(InvesdocMetadataManagerImpl.class);

	/**
	 * Información del archivador del documento.
	 */
    private ArchiveObject archive = null;

    /**
     * Información de la carpeta del documento.
     */
    private FolderObject folder = null;


	/**
	 * Constructor.
	 * @param archive Información del archivador.
	 * @param folder Información de la carpeta.
	 */
	public InvesdocMetadataManagerImpl(ContentType contentType, ArchiveObject archive, FolderObject folder) {
		super(contentType);
		setArchive(archive);
		setFolder(folder);
	}

	public ArchiveObject getArchive() {
		return archive;
	}

	public void setArchive(ArchiveObject archive) {
		this.archive = archive;
	}

	public FolderObject getFolder() {
		return folder;
	}

	public void setFolder(FolderObject folder) {
		this.folder = folder;
	}

	public Object getValorMetadato(Destination destination) throws GestionDocumentalException {

		Object valorMetadato = null;

		try {

	        // Información del campo del archivador
			ArchiveTokenFld archiveTokenFld = findArchiveTokenFld(destination);
			if (archiveTokenFld != null) {
				valorMetadato = getFolder().getFieldValue(archiveTokenFld.getId());
			}

		} catch (Exception e) {
			logger.error("Error al obtener la información del metadato ["
					+ destination + "]", e);
			throw new GestionDocumentalException(e);
		}

		return valorMetadato;
	}

	public void setMetadato(Mapping mapping, Object valorMetadato) throws GestionDocumentalException {

		Destination destination = mapping.getDestination();

		try {

	        // Información del campo del archivador
			ArchiveTokenFld archiveTokenFld = findArchiveTokenFld(destination);
    		if (archiveTokenFld != null) {
    			//setFieldValue(folder, archiveTokenFld, valorMetadato);
    			folder.setFieldValue(archiveTokenFld.getId(), valorMetadato);
    		}

		} catch (Exception e) {
			logger.error("Error al establecer la información del metadato ["
					+ destination + "]", e);
			throw new GestionDocumentalException(e);
		}
	}

	protected ArchiveTokenFld findArchiveTokenFld(Destination destination) throws Exception {

		ArchiveTokenFld archiveFld = null;

		// Metadatos del archivador
        ArchiveTokenFlds archiveFlds = getArchive().getArchiveToken().getFlds();

		String fld = destination.getAttribute("fld");
		if (StringUtils.isNotBlank(fld)) {
			archiveFld = archiveFlds.findById(Integer.parseInt(fld));
		} else {
			archiveFld = archiveFlds.findByName(destination.getValue());
		}

		return archiveFld;
	}

//	protected void setFieldValue(FolderObject folder, ArchiveTokenFld archiveTokenFld, Object valorMetadato) throws Exception {
//
//        Object valor = null;
//
//        if (valorMetadato != null) {
//            switch (archiveTokenFld.getType()) {
//
//                case ArchiveFldType.SHORT_TEXT:
//                case ArchiveFldType.LONG_TEXT:
//                	valor = String.valueOf(valorMetadato);
//                    break;
//
//                case ArchiveFldType.SHORT_INTEGER:
//                case ArchiveFldType.LONG_INTEGER:
//                    if (valorMetadato instanceof Number) {
//                        valor = new Integer(((Number)valorMetadato).intValue());
//                    } else {
//                        valor = Integer.valueOf(String.valueOf(valorMetadato));
//                    }
//                    break;
//
//                case ArchiveFldType.SHORT_DECIMAL:
//                    if (valorMetadato instanceof Number) {
//                        valor = new Double(((Number)valorMetadato).doubleValue());
//                    } else {
//                        valor = Double.valueOf(String.valueOf(valorMetadato));
//                    }
//                    break;
//
//                case ArchiveFldType.LONG_DECIMAL:
//                    if (valorMetadato instanceof Number) {
//                        valor = new Float(((Number)valorMetadato).floatValue());
//                    } else {
//                        valor = Float.valueOf(String.valueOf(valorMetadato));
//                    }
//                    break;
//
//                case ArchiveFldType.DATE:
//                case ArchiveFldType.TIME:
//                case ArchiveFldType.DATE_TIME:
//                    if (valorMetadato instanceof Date) {
//                        valor = valorMetadato;
//                    } else {
//                        valor = DateUtils.parseDateStrictly(String.valueOf(valorMetadato),
//                        		new String[] { "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd", "HH:mm:ss" });
//                    }
//                    break;
//
//                default:
//                    valor = valorMetadato;
//                    break;
//            }
//        }
//
//        folder.setFieldValue(archiveTokenFld.getId(), valor);
//    }
}
