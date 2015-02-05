package transferencias;


public class EstadoREntregaConstants {

	   private static int nextNumOrden = 1;

	   private final int identificador = nextNumOrden++;

	   public static final EstadoREntregaConstants[] estadosREntrega = new EstadoREntregaConstants[10];

	   public EstadoREntregaConstants() {
	      int posicion = identificador - 1;
	      estadosREntrega[posicion] = this;
	   }

	   /**
	    * Valor: 1
	    */
	   public static final EstadoREntregaConstants ABIERTA = new EstadoREntregaConstants();

	   /**
	    * Valor: 2
	    */
	   public static final EstadoREntregaConstants ENVIADA = new EstadoREntregaConstants();

	   /**
	    * Valor: 3
	    */
	   public static final EstadoREntregaConstants RECIBIDA = new EstadoREntregaConstants();

	   /**
	    * Valor: 4
	    */
	   public static final EstadoREntregaConstants SIGNATURIZADA = new EstadoREntregaConstants();

	   /**
	    * Valor: 5
	    */
	   public static final EstadoREntregaConstants CON_ERRORES_COTEJO = new EstadoREntregaConstants();

	   /**
	    * Valor: 6
	    */
	   public static final EstadoREntregaConstants CORREGIDA_ERRORES = new EstadoREntregaConstants();

	   /**
	    * Valor 7
	    */
	   public static final EstadoREntregaConstants VALIDADA = new EstadoREntregaConstants();

	   /**
	    * Valor 8
	    */
	   public static final EstadoREntregaConstants RECHAZADA = new EstadoREntregaConstants();

	   /**
	    * Valor 9
	    */
	   public static final EstadoREntregaConstants COTEJADA = new EstadoREntregaConstants();

	   /**
	    * Valor 10
	    */
	   public static final EstadoREntregaConstants VALIDADA_AUTOMATIZADA = new EstadoREntregaConstants();


	   public int getIdentificador() {
	      return identificador;
	   }

	   public static EstadoREntregaConstants getEstadoREntrega(int identificador) {
	      return estadosREntrega[identificador - 1];
	   }

	   public boolean equals(Object otroObjeto) {
	      return identificador == ((EstadoREntregaConstants) otroObjeto).getIdentificador();
	   }
}