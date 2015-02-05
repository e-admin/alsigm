package common.lang;

/**
 * Entero cuyo valor puede ser modificado
 *
 */
public class MutableInt {

   int value;

   public MutableInt(int value) {
      super();
      this.value = value;
   }
   public int getValue() {
      return value;
   }
   public void setValue(int value) {
      this.value = value;
   }
   public void incrementValue() {
       this.value ++;
    }
   public void decrementValue() {
       this.value --;
    }
}
