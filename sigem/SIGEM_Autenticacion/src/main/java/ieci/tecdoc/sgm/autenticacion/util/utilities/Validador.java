/*
 * Creado el 31-may-2006
 *
 */
package ieci.tecdoc.sgm.autenticacion.util.utilities;


/**
 * Clase general de validaciones.
 */
public class Validador {

	/**
	 * Identifica el tipo de documento de identificación del solicitante y lo válida.
	 *
	 * Un CIF debe tener 9 cifras.
	 * La primera cifra (una letra), indica el tipo de sociedad al que hace referencia:
	 *
	 * A - Sociedades Anónimas
	 * B - Sociedades de responsabilidad limitada
	 * C - Sociedades colectivas
	 * D - Sociedades comanditarias
	 * E - Comunidades de bienes
	 * F - Sociedades cooperativas
	 * G - Asociaciones y otros tipos no definidos
	 * H - Comunidades de propietarios
	 * P - Corporaciones locales
	 * Q - Organismos autónomos
	 * S - Organos de la administración
	 * K, L, M - seguramente para compatibilidad con formatos antiguos
	 * X, Y, Z - Extranjeros, que en lugar del D.N.I. tienen el N.I.E.
	 *
	 * La ultima cifra es el dígito de control, que puede ser o bien un número o bien una letra, en función del tipo de sociedad.
	 * A las categorías K, P, Q, S y X, Y, Z (Extranjeros) les corresponde una letra en lugar de un número.
	 * A las categorías A, B, E, H les corresponde siempre un número.
	 *
	 * http://es.wikipedia.org/wiki/C%C3%B3digo_de_identificaci%C3%B3n_fiscal
	 *
	 * El dígito de control se calcula con las 7 cifras restantes del CIF (quitando la primera y la ultima),
	 * siguiendo el algoritmo explicado en:
	 *
	 * http://www.trucomania.org/trucomania/truco.cgi?337
	 *
	 * @param document Número de identificación.
	 * @return el tipo de documento o 0 si no es válido.
	 */
	public static int validateDocumentType(String document) {

	   int docType = TipoDocumento.DOC_ERROR;
	   boolean greenCard = false;

	   if(!document.equals("")){

	   	 if (document.length() == 10){

	   		 String first = document.substring(0,1);
	   		 if (first.equalsIgnoreCase("X") || first.equalsIgnoreCase("Y") || first.equalsIgnoreCase("Z")){
	   			//NIE
	   	   	  	document = document.substring(1);
	   	   	  	greenCard=true;
	   		 }
	   	 }

	     if(document.length()==9){

		     char [] cif = document.toUpperCase().toCharArray();

			 if(Character.isLetter(cif[0])){

				 //Ejemplo: CIF A58818501

				 //CIF-NIE
				 int oddAddition=0;
				 int globalAddition=0;

				 //No se procesan la primera y la ultima cifra

				 //Sumar las cifras pares
				 //8 + 1 + 5 = 14
			     int evenAddition = Integer.parseInt(String.valueOf(cif[2])) +
			     					Integer.parseInt(String.valueOf(cif[4])) +
			     					Integer.parseInt(String.valueOf(cif[6]));

			     //Sumar cada cifra impar multiplicada por dos
			     //5 * 2 = 10 -> 1 + 0 = 1
			     //8 * 2 = 16 -> 1 + 6 = 7
			     //8 * 2 = 16 -> 1 + 6 = 7
			     //0 * 2 =  0 -> 0
			     //Y sumar las cifras del resultado
			     //1 + 7 + 7 + 0 = 15
			     for (int i=1;i<=4;i++){
			       int value = ((2*Integer.parseInt(String.valueOf(cif[2*i-1])))%10) +
                   			   ((2*Integer.parseInt(String.valueOf(cif[2*i-1])))/10);
			       oddAddition += value;
			     }

			     //Sumar esos resultados a la suma anterior
			     //14 + 15 = 29
			     globalAddition = evenAddition + oddAddition;

			     //Al final de este proceso globalAddition = 29
			     //quedarse con la cifra de las unidades -> 9 y
			     //restar esta cifra de las unidades de 10 -> 1 que es el código de control
			     //para todos los tipos de sociedades exceptuando:
			     //para los extranjeros (X, Y, Z) y ayuntamientos (P)
			     //que hay que sumar un 64 al digito de control calculado para hallar el ASCII de la letra de control

			     //NIE
			     if (cif[0] == 'X' || cif[0] == 'Y' || cif[0] == 'Z') {
			         if (cif[8] == (char)(64+ (10-(globalAddition%10))))
			        	 docType = TipoDocumento.DOC_NIE;
			     }
			     //CIF
			     else if ((cif[0] == 'K') || (cif[0] == 'P') || (cif[0] == 'Q') || (cif[0] == 'S')) {
			         if (cif[8] == (char)(64+ (10-(globalAddition%10))))
				         docType = TipoDocumento.DOC_CIF;
				 }
			     else {
			    	 // Corrección:
			    	 // para el CIF B14507610 el valor final de (10 - (Suma mod 10)) es 10,
			    	 // con lo que el resultado de salida de la función sería 0 (error), pero este CIF es correcto.
			    	 int digControl = 10-(globalAddition%10);
			    	 if (digControl == 10)
			    		 digControl = 0;

				     //CIF con número
				     if ((cif[0] == 'A') || (cif[0] == 'B') || (cif[0] == 'E') || (cif[0] == 'H')) {
				    	 if (Character.isDigit(cif[8])) {
				    		 if (Integer.parseInt(String.valueOf(cif[8])) == digControl)
				        	 docType = TipoDocumento.DOC_CIF;
				    	 }
				     }
				     //Para otros CIFs podrá ser tanto número como letra
				     else {
				    	 if (Character.isDigit(cif[8])) {
				    		 if (Integer.parseInt(String.valueOf(cif[8])) == digControl)
				        	 docType = TipoDocumento.DOC_CIF;
				    	 }
				    	 else {
				    		 char[] letterControl = new String("JABCDEFGHI").toCharArray();
				    		 if (String.valueOf(cif[8]).equalsIgnoreCase(String.valueOf(letterControl[digControl]))) {
				    			 docType = TipoDocumento.DOC_CIF;
				    		 }
				    	 }
				     }
			     }
			 }
			 else if(Character.isLetterOrDigit(cif[cif.length-1])) {

			     //DNI-NIE
			     char [] letters="TRWAGMYFPDXBNJZSQVHLCKE".toCharArray();
			     String sNumber=document.substring(0,document.length()-1); //Antes ponía de 1 a length - 1
			     long lNumber=Long.parseLong(sNumber);
			     char letra=cif[cif.length-1];
			     if(letters[Integer.parseInt(Long.toString(lNumber%23))]==letra){
				    if (greenCard)
				  	   docType =TipoDocumento.DOC_NIE;
				  	else
		   		       docType =TipoDocumento.DOC_NIF;
			     }
			 }
	      }
	   }

	   return docType;
   }

}
