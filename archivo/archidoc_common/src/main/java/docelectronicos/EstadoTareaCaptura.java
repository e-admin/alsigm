package docelectronicos;


/**
 * Estados de la tarea de captura
 */
public class EstadoTareaCaptura {
    private static int nextNumOrden = 1;
	   
	   private final int identificador = nextNumOrden ++;
	   private int value = -1;
	   
	   private EstadoTareaCaptura(int value) {
	      this.value = value;
	   }

	   public static final EstadoTareaCaptura PENDIENTE = new EstadoTareaCaptura(1);

	   public static final EstadoTareaCaptura FINALIZADA = new EstadoTareaCaptura(2);

	   public static final EstadoTareaCaptura CON_ERRORES = new EstadoTareaCaptura(3);
	   
	   public static final EstadoTareaCaptura VALIDADA = new EstadoTareaCaptura(4);
	   
	   public static final EstadoTareaCaptura[] estados = new EstadoTareaCaptura[] { PENDIENTE,
            FINALIZADA, CON_ERRORES, VALIDADA };
	   
	   public int getValue() {
	      return value;
	   }
	   
	   public static EstadoTareaCaptura getFromValue(int value) {
	      return estados[value-1];
	   }
	   
	   public boolean equals(Object otroObjeto) {
	      return identificador == ((EstadoTareaCaptura)otroObjeto).getValue();
	   }
}
