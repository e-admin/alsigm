package common.lang;

/**
 * Entero Largo cuyo valor puede ser modificado
 *
 */
public class MutableLong {

   long value;

   public MutableLong(int value) {
      super();
      this.value = value;
   }
   public long getValue() {
      return value;
   }
   public void setValue(long value) {
      this.value = value;
   }
   public void incrementValue() {
       this.value ++;
    }
   public void decrementValue() {
       this.value --;
   }

   public String toString() {
	   return ""+value;
   }
   
   
   
}
