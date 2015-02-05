package transferencias;


public class EstadoPrevisionConstants {
		private static int nextNumOrden = 1;

	   private final int identificador = nextNumOrden ++;
	   private String nombre = null;

	   public static final EstadoPrevisionConstants[] estadosPrevision = new EstadoPrevisionConstants[6];

	   public EstadoPrevisionConstants() {
	      estadosPrevision[identificador-1] = this;
	   }

	   public EstadoPrevisionConstants(String nombre) {
	      this.nombre = nombre;
	      estadosPrevision[identificador-1] = this;
	   }

	   /**
	    * Valor: 1
	    */
	   public static final EstadoPrevisionConstants ABIERTA = new EstadoPrevisionConstants("Abierta");

	   /**
	    * Valor: 2
	    */
	   public static final EstadoPrevisionConstants ENVIADA = new EstadoPrevisionConstants("Enviada");

	   /**
	    * Valor: 3
	    */
	   public static final EstadoPrevisionConstants ACEPTADA = new EstadoPrevisionConstants("Aceptada");

	   /**
	    * Valor: 4
	    */
	   public static final EstadoPrevisionConstants RECHAZADA = new EstadoPrevisionConstants("Rechazada");

	   /**
	    * Valor: 5
	    */
	   public static final EstadoPrevisionConstants CERRADA = new EstadoPrevisionConstants("Cerrada");

	   /**
	    * Valor: 6
	    */
	   public static final EstadoPrevisionConstants AUTOMATIZADA = new EstadoPrevisionConstants("Automatizada");

	   public int getIdentificador() {
	      return identificador;
	   }
	   public String getNombre() {
	      return nombre;
	   }

	   public static EstadoPrevisionConstants getEstadoPrevision(int identificador) {
	      return estadosPrevision[identificador-1];
	   }

	   public boolean equals(Object otroObjeto) {
	      return identificador == ((EstadoPrevisionConstants)otroObjeto).getIdentificador();
	   }
}
