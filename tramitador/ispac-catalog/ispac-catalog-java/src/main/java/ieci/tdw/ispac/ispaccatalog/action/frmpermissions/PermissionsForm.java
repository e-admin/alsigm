package ieci.tdw.ispac.ispaccatalog.action.frmpermissions;

import ieci.tdw.ispac.ispaccatalog.action.form.EntityForm;

public class PermissionsForm extends EntityForm
{
    private String[] selectedItems = new String[0];
//    private String[] colItems = {"Iniciar Casos","Administrar Casos","Editar/Modificar"}; 
    private String[] colItems = {"Iniciar expedientes"}; 
    
    /**
     * Devuelve un array de String's con los texto correspondientes a los identificadores de los permisos.
     *
     * @return String[] Contiene las cadenas que correspondes a los indentificadores de los permisos
     */
    public String[] getSelectedItems() { 
      return this.selectedItems;
    }
    
    /**
     * Establece el array de String's, selectedItems, con los textos de los permisos otorgados.
     *
     * @param String[] Contiene las cadenas correspondientes a los tipos de permisos que se desean establecer
     */
    public void setSelectedItems(String[] selectedItems) {
        this.selectedItems = selectedItems;
    }
    
    /**
     * Rellena el array de String's, selectedItems, estableciendo la relacion entre indentificador numerico
     * y el texto que le pertenece
     *
     * @param int[] Array con los identificadores numericos de los permisos
     */
    public void setSelectedItemsInt(int[] intSelectedItems) {
      String[] strSelectedItems = new String[1];
      this.selectedItems = new String[0];
      
      for (int ind=0; ind < intSelectedItems.length; ind++) //Se recorre el int[] con los identificadores de los elementos seleccionados
      {
          if (intSelectedItems[ind]!=0) { //Se descartan las posiciones que tengan valor 0
              strSelectedItems = this.selectedItems; //Se hace uan copia del array que deve de contener le texto de los elementos seleccionados
              this.selectedItems = new String[this.selectedItems.length +1]; //Aumentamos el tamaño del array en un espacio
              if (strSelectedItems.length > 0) { //Se trasladan los datos al array aumentado
                  for (int indi=0; indi<strSelectedItems.length; indi++) this.selectedItems[indi] = strSelectedItems[indi];
              }
              /*Establecemos el nuevo elemento con una correspondencia acordada, en la cual el identificador llegado
               * comienza en 1 y la posicion del array comienza en 0, por lo tanto aplicamos intSelectedItems[ind]-1*/
              this.selectedItems[this.selectedItems.length -1] = colItems[intSelectedItems[ind]-1];
          }
      }
    }
    
    /**
     * Devuelve un array de numeros enteros realizando la correspondencia con los textos contenidos en el
     * array de String's, selectedItems.
     *
     * @return int[] Array de numeros enteros con los identificadores de los permisos a establecer
     */
    public int[] getSelectedItemsInt() {
        int[] resItems = new int[0];
        int[] resItemsAux = null;
        
        for (int ind=0; ind < this.selectedItems.length; ind++) //Se recorre el array de String's  con los elementos seleccionados
        {
            for (int indi=0; indi < this.colItems.length; indi++) //Se recorre el array de String's con todos los tipos de permisos
            {
                if (this.selectedItems[ind].equals( this.colItems[indi])) { //Busca la correspondecia
                    resItemsAux = resItems; //Realiza un copia del array a devolver
                    resItems = new int[ind +1]; //Aumenta una espacio el array
                    for (int indc=0; indc < resItemsAux.length; indc++) resItems[indc] = resItemsAux[indc]; //Vuelca los numeros enteros en el array aumentado
                    /*Por la correspondencia establecida entre identificadores numericos y las posiciones del array de identificadores textuales
                     * de los permisos solo es necesario sumar la diferencia de comienzo de las numeraciones*/
                    resItems[ind] = indi + 1;
                }
            }
        }
        
        return resItems;
    }
    
    /**
     * Devuelve un array de String's con todos los textos de los permisos posibles a otorgar
     *
     * @return String[] Array de String's con los permisos posibles
     */
    public String[] getColItems() { 
        return this.colItems;
    }
}
