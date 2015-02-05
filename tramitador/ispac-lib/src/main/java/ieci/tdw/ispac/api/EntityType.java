package ieci.tdw.ispac.api;

public class EntityType {
	
	private static int numInstances = 0;
	private final static int MAX_NUM_INSTANCES = 6;

	private int id;

	private static EntityType[] instances = new EntityType[MAX_NUM_INSTANCES]; 

	/**
	 * Tabla de sistema para ser usada solo en codigo
	 */
	public static final EntityType SYSTEM_TABLE_ENTITY_TYPE = new EntityType(numInstances++);

	public static final EntityType PROCESS_ENTITY_TYPE = new EntityType(numInstances++);

	public static final EntityType VALIDATION_TABLE_SIMPLE_ENTITY_TYPE = new EntityType(numInstances++);

	public static final EntityType VALIDATION_TABLE_SUSTITUTE_ENTITY_TYPE = new EntityType(numInstances++);
    
	
	/**
	 * Tablas de validacion cuyos valores se sacan de una tabla que ya está creada en el sistema.
	 * (Pensado hacer con vistas)
	 */
	public static final EntityType SYSTEM_VALIDATION_TABLE_SIMPLE_ENTITY_TYPE = new EntityType(numInstances++);

	public static final EntityType SYSTEM_VALIDATION_TABLE_SUSTITUTE_ENTITY_TYPE = new EntityType(numInstances++);

	private EntityType(int numInstances) {
		this.id = numInstances;
		instances[id] = this;
	}

	public static EntityType getInstance(int id){
		return instances[id];
	}

	public int getId(){
		return id;
	}
	
	public boolean equals(Object obj) {
		return id == ((EntityType) obj).id;
	}

    public static boolean isValidationTableType(int id){
        return id>1;
    }
	
	public boolean isValidationTableType(){
		return getId()>1;
	}

	public boolean isSystemValidationTableType(){
		return getId()>3;
	}
    
    public static int MIN_ID_VALIDATION_TABLE = VALIDATION_TABLE_SIMPLE_ENTITY_TYPE.getId();
    
    public static int MAX_ID_VALIDATION_TABLE = SYSTEM_VALIDATION_TABLE_SUSTITUTE_ENTITY_TYPE.getId();

	

}

