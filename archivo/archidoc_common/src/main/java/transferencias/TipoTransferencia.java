/*
 * Created on 10-feb-2005
 *
 */
package transferencias;

/**
 * Enumeracion con los diferentes tipos de transferencia contemplados en el sistema
 *
 * Transferencia ordinarias son aquellas en las que se incorporan al Archivo
 * Unidades Documentales procedentes de los Sistemas Automatizados SIGAP, ASTURCON, PACA.
 *
 * Son aquellas cuyas Unidades Documentales no han sido producidas por alguno
 * de los Sistemas de Gestión Automatizada de procedimientos. Las transferencia extraordinarias
 * pueden ser de unidades documentales previamente signaturadas o de unidades documentales sin signatura
 *
 * Transferencias entre archivos son transferencias de unidades validadas en un archivo a otro archivo
 *
 * Ingreso directo son transferencias de unidades documentales directamente a un archivo sin previsión ni relación
 */
public class TipoTransferencia {

   private static int nextNumOrden = 1;

   private final int identificador = nextNumOrden ++;
   private String nombre = null;

   private static final TipoTransferencia[] tiposTransferencia = new TipoTransferencia[5];

   private TipoTransferencia(String nombre) {
      this.nombre = nombre;
      tiposTransferencia[identificador-1] = this;
   }

   /**
    * Tipo de transferencia ordinaria
    * <b>1</b>
    */
   public static final TipoTransferencia ORDINARIA = new TipoTransferencia("Transferencia ordinaria");

   /**
    * Tipo de transferencia extraordinaria sin signatura
    * <b>2</b>
    */
   public static final TipoTransferencia EXTRAORDINARIA_SIN_SIGNATURAR = new TipoTransferencia("Transferencia extraordinaria no signaturada");
   /**
    * Tipo de transferencia extraordinaria con signatura
    * <b>3</b>
    */
   public static final TipoTransferencia EXTRAORDINARIA_SIGNATURADA = new TipoTransferencia("Transferencia extraordinaria signaturada");
   /**
    * Tipo de transferencia entre archivos
    * <b>4</b>
    */
   public static final TipoTransferencia ENTRE_ARCHIVOS = new TipoTransferencia("Transferencia entre archivos");
   /**
    * Tipo de transferencia ingreso directo
    * <b>5</b>
    */
   public static final TipoTransferencia INGRESO_DIRECTO = new TipoTransferencia("Ingreso Directo");

   public int getIdentificador() {
      return identificador;
   }

   public String getNombre() {
      return nombre;
   }

   public static TipoTransferencia getTipoTransferencia(int identificador) {
      return tiposTransferencia[identificador-1];
   }

   public boolean equals(Object otroObjeto) {
      return identificador == ((TipoTransferencia)otroObjeto).getIdentificador();
   }
}
