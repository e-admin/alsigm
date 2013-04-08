package common.lang;

/**
 * 
 * Booleano cuyo valor puede ser modificado
 */
public class MutableBoolean {
   boolean value;

   public MutableBoolean(boolean value) {
      super();
      this.value = value;
   }
   public boolean getValue() {
      return value;
   }
   public void setValue(boolean value) {
      this.value = value;
   }
}
