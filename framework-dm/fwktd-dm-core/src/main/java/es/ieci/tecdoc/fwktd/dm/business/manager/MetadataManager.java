package es.ieci.tecdoc.fwktd.dm.business.manager;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ieci.tecdoc.fwktd.dm.business.config.ContentType;
import es.ieci.tecdoc.fwktd.dm.business.config.Destination;
import es.ieci.tecdoc.fwktd.dm.business.config.Mapping;
import es.ieci.tecdoc.fwktd.dm.business.config.Source;
import es.ieci.tecdoc.fwktd.dm.business.config.Token;
import es.ieci.tecdoc.fwktd.dm.business.config.TokenEvaluator;
import es.ieci.tecdoc.fwktd.dm.business.exception.GestionDocumentalException;
import es.ieci.tecdoc.fwktd.dm.business.util.ConvertUtils;
import es.ieci.tecdoc.fwktd.dm.business.vo.MetadatoVO;

/**
 * Interfaz para la gestión de mapeos de metadatos en el gestor documental.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public abstract class MetadataManager {

	private static final Logger logger = LoggerFactory.getLogger(MetadataManager.class);

	private ContentType contentType = null;

	public MetadataManager(ContentType contentType) {
		super();
		setContentType(contentType);
	}

	public ContentType getContentType() {
		return contentType;
	}

	public void setContentType(ContentType contentType) {
		this.contentType = contentType;
	}

	public List<MetadatoVO> getMetadatos() throws GestionDocumentalException {

		List<MetadatoVO> metadatos = new ArrayList<MetadatoVO>();

		if (getContentType() != null) {
			List<Mapping> mappings = getContentType().getMappings();
			if (!CollectionUtils.isEmpty(mappings)) {
				for (Mapping mapping : mappings) {
					MetadatoVO metadato = getMetadato(mapping);
					if (metadato != null) {
						metadatos.add(metadato);
					}
				}
			}
		}

		return metadatos;
	}

	public MetadatoVO getMetadato(Mapping mapping) throws GestionDocumentalException {

		MetadatoVO metadato = null;

		Destination destination = mapping.getDestination();
		if (destination != null) {

			Source source = mapping.getSource();
			Object valorMetadato = getValorMetadato(destination);

			if (StringUtils.isNotBlank(source.getType())) {
				try {
					valorMetadato = ConvertUtils.convert(valorMetadato,
							source.getType(), source.getFormat());
				} catch (ParseException e) {
					logger.warn("Error al parsear el valor del metadato: "
							+ valorMetadato + " [" + source.getType() + "]", e);
					throw new GestionDocumentalException(e);
				}
			}

			metadato = new MetadatoVO(source.getValue(), valorMetadato);
		}

		return metadato;
	}

	public void setMetadatos(List<MetadatoVO> metadatos)
			throws GestionDocumentalException {

		if (getContentType() != null) {

			List<Mapping> mappings = getContentType().getMappings();
			if (!CollectionUtils.isEmpty(mappings)) {
				for (Mapping mapping : mappings) {
					Destination destination = mapping.getDestination();
					if (destination != null) {

						Object valor = null;

						try {

							valor = getValorMetadato(metadatos, mapping.getSource());

							valor = ConvertUtils.convert(valor,
									destination.getType(),
									destination.getFormat());

						} catch (Exception e) {
							logger.error("Error al convertir el valor [" + valor
									+ "] del destino [" + destination.getValue() + "]", e);
							throw new GestionDocumentalException(e);
						}

						setMetadato(mapping, valor);
					}
				}
			}
		}
	}

	protected Object getValorMetadato(List<MetadatoVO> metadatos, Source source) throws Exception {
		Object valor = null;

		if (source != null) {
			String tokenName = source.getToken();
			if (StringUtils.isNotBlank(tokenName)) {
				Token token = getContentType().findToken(tokenName);
				if (token != null) {
					valor = TokenEvaluator.evaluateToken(getContentType(), token, metadatos);
				} else {
					logger.warn("No se ha encontrado el token [{}] para el tipo de contenido [{}/{}]",
							new Object[] { tokenName, getContentType().getId(), getContentType().getName() });
				}
			} else {
				for (MetadatoVO metadato : metadatos) {
					if (StringUtils.equalsIgnoreCase(source.getValue(), metadato.getNombre())) {
						valor = metadato.getValor();
						break;
					}
				}
			}
		}

		return valor;
	}

	public abstract Object getValorMetadato(Destination destination)
		throws GestionDocumentalException;

	public abstract void setMetadato(Mapping mapping, Object valorMetadato)
		throws GestionDocumentalException;

}
