package common.lang;

import java.util.Date;

/**
 *
 * Date cuyo valor puede ser modificado
 */
public class MutableDate {
   Date value;

   public MutableDate(Date value) {
      super();
      this.value = value;
   }
   public Date getValue() {
      return value;
   }
   public void setValue(Date value) {
      this.value = value;
   }
}
