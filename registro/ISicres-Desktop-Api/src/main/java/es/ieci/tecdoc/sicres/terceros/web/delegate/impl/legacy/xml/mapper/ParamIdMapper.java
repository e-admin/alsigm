package es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper;

import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ieci.tecdoc.fwktd.core.model.Entity;
import es.ieci.tecdoc.fwktd.util.velocity.VelocityEngine;

/**
 *
 * @author IECISA
 *
 */
public class ParamIdMapper implements Marshaller {

	public String marshall(Entity entity, Map<String, Object> context) {
		VelocityEngine engine = new VelocityEngine();
		VelocityContext ctx = new VelocityContext(context);
		ctx.put("entity", entity);
		return engine
				.merge("es/ieci/tecdoc/isicres/terceros/web/delegate/impl/legacy/xml/paramid.vm",
						ctx);
	}

	protected static final Logger logger = LoggerFactory
			.getLogger(ParamIdMapper.class);

}
