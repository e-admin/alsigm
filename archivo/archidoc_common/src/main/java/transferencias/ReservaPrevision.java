package transferencias;


/**
 * Estados por los que puede pasar una solicitud de reserva
 */
public class ReservaPrevision {
   private static int nextNumOrden = 1;

   private final int identificador = nextNumOrden++;

   private String nombre = null;

   private static final ReservaPrevision[] reservasPrevision = new ReservaPrevision[4];

   private ReservaPrevision(String nombre) {
      this.nombre = nombre;
      reservasPrevision[identificador - 1] = this;
   }

   public static final ReservaPrevision NO_RESERVADA = new ReservaPrevision("No reservada");

   public static final ReservaPrevision PENDIENTE = new ReservaPrevision("Pendiente de reserva");

   public static final ReservaPrevision RESERVADA = new ReservaPrevision("Reservada");

   public static final ReservaPrevision NO_SE_HA_PODIDO = new ReservaPrevision("No se ha podido");

   public int getIdentificador() {
      return identificador;
   }

   public String getNombre() {
      return nombre;
   }

   public static ReservaPrevision getReservaPrevision(int identificador) {
      return reservasPrevision[identificador - 1];
   }

   public boolean equals(Object otroObjeto) {
      return identificador == ((TipoTransferencia) otroObjeto).getIdentificador();
   }

}