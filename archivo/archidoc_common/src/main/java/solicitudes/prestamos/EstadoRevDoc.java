package solicitudes.prestamos;

import common.Constants;

/**
 * Enumeracion con los diferentes estados por los que puede pasar una revisión de documentación de una unidad documental
 */
public class EstadoRevDoc {
   private static int nextNumOrden = 1;

   private final int identificador = nextNumOrden++;
   private final String nombre;
   
   private static final String key =  Constants.TIPO_REVDOC_KEY;

   private static final EstadoRevDoc[] estadosRevDoc = new EstadoRevDoc[9];

   private EstadoRevDoc() {
      int posicion = identificador - 1;
      this.nombre = key + "." + String.valueOf(identificador); 
      estadosRevDoc[posicion] = this;
   }

   /**
    * Valor: 1
    */
   public static final EstadoRevDoc ABIERTA = new EstadoRevDoc();

   /**
    * Valor 2
    */
   public static final EstadoRevDoc FINALIZADA = new EstadoRevDoc();
   
   /**
    * Valor 3
    */
   public static final EstadoRevDoc RECHAZADA = new EstadoRevDoc();
   

   public int getIdentificador() {
      return identificador;
   }
   
	public String getNombre() {
		return nombre;
	}

	public static EstadoRevDoc getEstadoRevDoc(int identificador) {
      return estadosRevDoc[identificador - 1];
   }

   public boolean equals(Object otroObjeto) {
      return identificador == ((EstadoRevDoc) otroObjeto).getIdentificador();
   }

}