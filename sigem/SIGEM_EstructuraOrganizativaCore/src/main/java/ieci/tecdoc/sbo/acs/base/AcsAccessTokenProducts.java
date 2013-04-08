package ieci.tecdoc.sbo.acs.base;



import java.util.ArrayList;

public class AcsAccessTokenProducts
{
   private ArrayList _list;
   
   public AcsAccessTokenProducts()
   {
      _list = new ArrayList();
   }

   public int count() 
   {
      return _list.size();
   }
    
   public AcsAccessTokenProduct get(int index) 
   {
      return (AcsAccessTokenProduct)_list.get(index);
   }

   public void add(AcsAccessTokenProduct acsTokenProduct) 
   {
      _list.add(acsTokenProduct);
   }

}
