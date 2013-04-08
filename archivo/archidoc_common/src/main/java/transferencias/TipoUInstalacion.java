/*
 * Created on 10-feb-2005
 *
 */
package transferencias;

/**
 * Enumeracion con los diferentes tipos de unidades de instalación
 */
public class TipoUInstalacion {

   private static int nextNumOrden = 1;
   
   private final int identificador = nextNumOrden ++;
   private String nombre = null;
   
   private static final TipoUInstalacion[] tiposUInstalacion = new TipoUInstalacion[5]; 
   
   private TipoUInstalacion(String nombre) {
      this.nombre = nombre;
      tiposUInstalacion[identificador-1] = this;
   }
   
   /**
    * Todas las Unidades de Instalación (creadas y asociadas)
    */
   public static final TipoUInstalacion ALL = new TipoUInstalacion("Unidades Instalación");
   /**
    * Tipo de unidad de instalación asignada a una caja existente
    */
   public static final TipoUInstalacion ASIGNADAS = new TipoUInstalacion("Unidad de Instalación Asignada");
   /**
    * Tipo de unidad de instalación asignada a una nueva caja
    */
   public static final TipoUInstalacion CREADAS = new TipoUInstalacion("Unidad de Instalación Creada");
    
   
   public int getIdentificador() {
      return identificador;
   }
   public String getNombre() {
      return nombre;
   }
   
   public static TipoUInstalacion getTipoUInstalacion(int identificador) {
      return tiposUInstalacion[identificador-1];
   }
   
   public boolean equals(Object otroObjeto) {
      return identificador == ((TipoUInstalacion)otroObjeto).getIdentificador();
   }
}