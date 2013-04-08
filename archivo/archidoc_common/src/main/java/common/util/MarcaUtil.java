package common.util;

import java.util.ArrayList;
import java.util.List;

import common.util.ArrayUtils;
import common.util.StringUtils;

public class MarcaUtil {
	public static final char BIT_ACTIVADO='1';

	/**
	 * Devuelve el entero resultado, de pasar a decimal, el binario generado poniendo las posiciones de los
	 * bits (empezando desde el 0) a 1 (bitsActivos)
	 * Ej. generarMarcasBis(new int[]{1,3})  genera 10
	 * 	   generarMarcasBis(new int[]{3,2})  genera 12
	 *
	 * @param	Un array de enteros con las posiciones de los bits activos
	 * @return  Un entero con el valor en base decimal del binario generado a partir del parametro
	 */
	public static int generarMarcas(int[] bitsActivos){
		int result=0;
		for(int i=0;i<bitsActivos.length;i++){
			result+=(int)Math.pow(2,bitsActivos[i]);
		}
		return result;
	}

	/**
	 * Retorna un array con las posiciones (empezando a contar desde 0) activas en el binario generado a partir
	 * el numero en base decimal que recibe como parametro
	 * Ej.  obtenerMarcasBis(8)	  {3}
	 *      obtenerMarcasBis(9)   {0,3}
	 *
	 * @param	Un entero con el numero en base decimal
	 * @return
	 */
	public static int[] obtenerMarcas(int value){
		String binary=Integer.toBinaryString(value);
		binary=StringUtils.reverse(binary);

		List listaBitsActivos=new ArrayList();
		for(int i=0;i<binary.length();i++){
			if(binary.charAt(i)==BIT_ACTIVADO)
				listaBitsActivos.add(new Integer(i));
		}
		return ArrayUtils.IntegerToInt(listaBitsActivos.toArray());
	}

	/**
	 * Devuelve true si esta activo el bit, indicado por el parametro posBit, en el numero binario generado
	 * a partir del numero en base decimal recibido como segundo parametro.
	 * Ej. isBitActivoInMarca(3, 8)		true
	 *
	 * @param  un entero con la posicion (empezando a contar desde 0) a comprobar
	 * @param  un entero con la marca en base decimal
	 * @return un boolean indicando si la posicion, esta activada en el binario obtenido a partir de la marca
	 */
	public static boolean isBitActivoInMarca(int posBit, int marca){
		String marcaBinaryString=Integer.toBinaryString(marca);
		if(posBit<0 || posBit>=marcaBinaryString.length()) return false;
		marcaBinaryString=StringUtils.reverse(marcaBinaryString);

		return (marcaBinaryString.charAt(posBit)==BIT_ACTIVADO)?true:false;
	}
	
	/**
	 * Establece un bit como activo en una marca. Para ello obtiene los bits activos en una marca y si no
	 * lo está entre los activos lo añade y vuelve a generar la marca.
	 *
	 * @param  un entero con la posicion (empezando a contar desde 0)
	 * @param  un entero con la marca en base decimal
	 * @return un entero con la marca en base decimal y el bit en cuestión activo
	 */
	public static int setBitActivoInMarca(int posBit, int marca){
		int [] marcasBloqueo = obtenerMarcas(marca);
		if (!ArrayUtils.contains(marcasBloqueo, posBit)){
			marcasBloqueo = ArrayUtils.add(marcasBloqueo,posBit);
		}
		
		return generarMarcas(marcasBloqueo);
	}
	
	/**
	 * Establece un bit como inactivo en una marca. Para ello obtiene los bits activos en una marca y si
	 * está entre los activos lo elimina y vuelve a generar la marca.
	 *
	 * @param  un entero con la posicion (empezando a contar desde 0)
	 * @param  un entero con la marca en base decimal
	 * @return un entero con la marca en base decimal y el bit en cuestión inactivo
	 */
	public static int setBitInactivoInMarca(int posBit, int marca){
		int [] marcasBloqueo = obtenerMarcas(marca);
		if (ArrayUtils.contains(marcasBloqueo, posBit)){
			marcasBloqueo = ArrayUtils.removeElement(marcasBloqueo,posBit);
		}
		
		return generarMarcas(marcasBloqueo);
	}
}