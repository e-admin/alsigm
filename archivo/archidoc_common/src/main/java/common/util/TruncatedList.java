package common.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Utilidad para disponer de una lista cuyo tamaño está limitado a una determinada cantidad
 *
 */
public class TruncatedList extends ArrayList {

   /**
	 *
	 */
	private static final long serialVersionUID = 1L;
boolean incomplete = false;

   public TruncatedList(List items, int maxSize) {
      if (items.size() > maxSize) {
         this.addAll(items.subList(0, maxSize - 1));
         incomplete = true;
      } else
         this.addAll(items);
   }

   public boolean isIncomplete() {
      return incomplete;
   }
}