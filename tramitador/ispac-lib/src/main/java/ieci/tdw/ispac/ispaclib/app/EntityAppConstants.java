package ieci.tdw.ispac.ispaclib.app;

public abstract class EntityAppConstants {

	//Tipos de entidades secundarias a asociar
	public final static String ENTITY_TYPE_COMPOSITE = "COMPOSITE";
	public final static String ENTITY_TYPE_VALIDATION = "VALIDATION";
	public final static String ENTITY_TYPE_DETAILS = "DETAIL";
	public final static String ENTITY_TYPE_MULTIPLE_RELATION = "MULTIPLE";
	
	//Tipo de relacion para el calculo
	public final static String RELATION_TYPE_FIELD = "FLD";
	public final static String RELATION_TYPE_PRIMARY_KEY = "PK";
	public final static String RELATION_TYPE_NUMEXP = "EXP";
	public final static String RELATION_TYPE_QUERY = "QUERY";
	
	private static final String REF_INIT_TAG = "[";
	private static final String REF_END_TAG = "]";

	public final static String REF_PRIMARY_ENTITY = REF_INIT_TAG + "PRIMARY_ENTITY" + REF_END_TAG;
	public final static String REF_SECONDARY_ENTITY = REF_INIT_TAG + "SECONDARY_ENTITY" + REF_END_TAG;
	public final static String REF_PRIMARY_FIELD_NUMEXP = REF_INIT_TAG + "PRIMARY_FIELD_NUMEXP" + REF_END_TAG;
	public final static String REF_SECONDARY_FIELD_NUMEXP = REF_INIT_TAG + "SECONDARY_FIELD_NUMEXP" + REF_END_TAG;
	public final static String REF_PRIMARY_FIELD_PK = REF_INIT_TAG + "PRIMARY_FIELD_PK" + REF_END_TAG;
	public final static String REF_SECONDARY_FIELD_PK = REF_INIT_TAG + "SECONDARY_FIELD_PK" + REF_END_TAG;
	
	public final static String REF_NUMEXP = REF_INIT_TAG + "NUMEXP" + REF_END_TAG;
	
	
	//Constantes del xml asociado a la aplicacion
	//Nodos
	public final static String NODE_PARAMETERS = "parameters";
	public final static String NODE_LIST_ORDER = "list-order";
	public final static String NODE_ENTITY = "entity";
	public final static String NODE_TABLE = "table";
//	public final static String NODE_READONLY = "readonly";
	public final static String NODE_RELATION = "relation";
	public final static String NODE_PRIMARY_FIELD = "primary-field";
	public final static String NODE_SECONDARY_FIELD = "secondary-field";
	public final static String NODE_QUERY = "query";

	//Atributos
	public final static String ATTRIBUTE_TYPE = "type";
	public final static String ATTRIBUTE_PRIMARY_TABLE = "primaryTable";
	public final static String ATTRIBUTE_READONLY = "readonly";
	public final static String ATTRIBUTE_NO_DELETE = "noDelete";
	
	//Rutas de acceso via XPath
	// /parameters/order
	public final static String PATH_LIST_ORDER = "/" + NODE_PARAMETERS + "/" + NODE_LIST_ORDER;
	// /parameters/entity
	public final static String PATH_ENTITY = "/" + NODE_PARAMETERS + "/" + NODE_ENTITY;
	// /parameters/entity/id
	public final static String PATH_SECONDARY_TABLE = PATH_ENTITY + "/" + NODE_TABLE;
	// /parameters/entity/relation
	public final static String PATH_RELATION = PATH_ENTITY + "/" + NODE_RELATION;
	// /parameters/entity/relation/primary-field
	public final static String PATH_PRIMARY_FIELD = PATH_RELATION + "/" + NODE_PRIMARY_FIELD;
	// /parameters/entity/relation/secondary-field
	public final static String PATH_SECONDARY_FIELD = PATH_RELATION + "/" + NODE_SECONDARY_FIELD;
	// relation/primary-field
	public final static String PATH_SHORT_PRIMARY_FIELD = NODE_RELATION + "/" + NODE_PRIMARY_FIELD; 
	// relation/secondary-field
	public final static String PATH_SHORT_SECONDARY_FIELD = NODE_RELATION + "/" + NODE_SECONDARY_FIELD;
	// relation/query
	public final static String PATH_SHORT_QUERY = NODE_RELATION + "/" + NODE_QUERY;
}
