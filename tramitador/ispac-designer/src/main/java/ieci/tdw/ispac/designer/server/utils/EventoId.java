package ieci.tdw.ispac.designer.server.utils;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.TypeConverter;

public class EventoId {

	private static final String SEPARATOR = "_";
	
	private int objId = -1;
	private int objType = -1;
	private int eventCode = -1;
	private int order = -1;
	private int ruleId = -1;
	
	private EventoId() {}
	
	public static EventoId getInstance(String eventRuleId) {
		EventoId id = null;
		
		if (StringUtils.isNotBlank(eventRuleId)) {
			id = new EventoId();
			String [] tokens = StringUtils.split(eventRuleId, SEPARATOR);

			if (tokens.length > 0) {
				id.setObjId(TypeConverter.parseInt(tokens[0], -1));
			}

			if (tokens.length > 1) {
				id.setObjType(TypeConverter.parseInt(tokens[1], -1));
			}

			if (tokens.length > 2) {
				id.setEventCode(TypeConverter.parseInt(tokens[2], -1));
			}
			
			if (tokens.length > 3) {
				id.setOrder(TypeConverter.parseInt(tokens[3], -1));
			}
			
			if (tokens.length > 4) {
				id.setRuleId(TypeConverter.parseInt(tokens[4], -1));
			}
		}
		
		return id;
	}

	public static EventoId getInstance(IItem pevent) throws ISPACException {
		EventoId id = null;
		
		if (pevent != null) {
			id = new EventoId();
			id.setObjId(pevent.getInt("ID_OBJ"));
			id.setObjType(pevent.getInt("TP_OBJ"));
			id.setEventCode(pevent.getInt("EVENTO"));
			id.setOrder(pevent.getInt("ORDEN"));
			id.setRuleId(pevent.getInt("ID_REGLA"));
		}
		
		return id;
	}

	public int getObjId() {
		return objId;
	}

	public void setObjId(int objId) {
		this.objId = objId;
	}

	public int getObjType() {
		return objType;
	}

	public void setObjType(int objType) {
		this.objType = objType;
	}

	public int getEventCode() {
		return eventCode;
	}

	public void setEventCode(int eventCode) {
		this.eventCode = eventCode;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public int getRuleId() {
		return ruleId;
	}

	public void setRuleId(int ruleId) {
		this.ruleId = ruleId;
	}
	
	public String getEventRuleId() {
		return objId + SEPARATOR + objType + SEPARATOR + eventCode + SEPARATOR + order + SEPARATOR + ruleId;
	}

}
