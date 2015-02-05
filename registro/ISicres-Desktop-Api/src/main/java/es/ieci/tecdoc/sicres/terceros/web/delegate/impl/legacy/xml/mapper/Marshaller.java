package es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper;

import java.util.Map;

import es.ieci.tecdoc.fwktd.core.model.Entity;

public interface Marshaller {

	public abstract String marshall(Entity entity, Map<String, Object> context);

}