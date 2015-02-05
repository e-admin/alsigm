package common.util;

/**
 * An object of type RomanNumeral is an integer between 1 and 3999.  It can
 * be constructed either from an integer or from a string that represents
 * a Roman numeral in this range.  The function toString() will return a
 * standardized Roman numeral representation of the number.  The function
 * toInt() will return the number as a value of type int.
*/
public class RomanNumeral 
{

	/** The number represented by this Roman numeral. */
	private final int num;

	/** The following arrays are used by the toString() function to construct
        the standard Roman numeral representation of the number.  For each i,
        the number numbers[i] is represented by the corresponding string, letters[i].*/
	private static int[]    numbers = { 1000,  900,  500,  400,  100,   90,  
                                      50,   40,   10,    9,    5,    4,    1 };
                                   
	private static String[] letters = { "M",  "CM",  "D",  "CD", "C",  "XC",
                                        "L",  "XL",  "X",  "IX", "V",  "IV", "I" };
   

	/**
	 * Constructor.
	 * @param arabic Número arábigo.
	 */
	public RomanNumeral(int arabic) 
	{
		if (arabic < 1)
			throw new NumberFormatException("Value of RomanNumeral must be positive.");
		if (arabic > 3999)
			throw new NumberFormatException("Value of RomanNumeral must be 3999 or less.");
		
		num = arabic;
	}


	/**
	 * Constructor.
	 * @param roman Número romano.
	 */
	public RomanNumeral(String roman) 
	{
		if (roman.length() == 0)
			throw new NumberFormatException("An empty string does not define a Roman numeral.");
      
		roman = roman.toUpperCase();  // Convert to upper case letters.
   
		int i = 0;     		// A position in the string, roman;
		int arabic = 0;		// Arabic numeral equivalent of the part of the string that has
							//    been converted so far.
   
		while (i < roman.length()) 
		{
			char letter = roman.charAt(i);        // Letter at current position in string.
			int number = letterToNumber(letter);  // Numerical equivalent of letter.
      
			if (number < 0)
				throw new NumberFormatException("Illegal character \"" + letter + "\" in roman numeral.");
         
			i++;  // Move on to next position in the string
      
			if (i == roman.length()) 
			{
				// There is no letter in the string following the one we have just processed.
				// So just add the number corresponding to the single letter to arabic.
				arabic += number;
			}
			else 
			{
				// Look at the next letter in the string.  If it has a larger Roman numeral
				// equivalent than number, then the two letters are counted together as
				// a Roman numeral with value (nextNumber - number).
				int nextNumber = letterToNumber(roman.charAt(i));
				if (nextNumber > number) 
				{
					// Combine the two letters to get one value, and move on to next position in string.
					arabic += (nextNumber - number);
					i++;
				}
				else
				{
					// Don't combine the letters.  Just add the value of the one letter onto the number.
					arabic += number;
				}
			}
		}
   
		if (arabic > 3999)
			throw new NumberFormatException("Roman numeral must have value 3999 or less.");
      
		num = arabic;
	}


	/**
	 * Obtiene el valor entero de la letra considerada como número romano.
	 * Devuelve -1 si la letra es inválida. La letra debe estar en mayúsculas.
	 * @param letter Letra romana.
	 * @return Valor de la letra romana.
	 */
	private int letterToNumber(char letter) 
	{
		switch (letter) 
		{
			case 'I':  return 1;
			case 'V':  return 5;
			case 'X':  return 10;
			case 'L':  return 50;
			case 'C':  return 100;
			case 'D':  return 500;
			case 'M':  return 1000;
			default:   return -1;
		}
	}


	/**
	 * Obtiene el valor del número romano.
	 * @return Número romano.
	 */
	public String toString() 
	{
		String roman = "";
		int N = num;
		
		for (int i = 0; i < numbers.length; i++) 
		{
			while (N >= numbers[i]) 
			{
				roman += letters[i];
				N -= numbers[i];
			}
		}
		
		return roman;
	}


	/**
	 * Obtiene el valor del número romano como un entero.
	 * @return Número arábigo.
	 */
	public int toInt() 
	{
		return num;
	}


	/**
	 * Obtiene el valor del número romano como un entero.
	 * @return Número arábigo.
	 */
	public static int toInt(String roman) 
	{
		RomanNumeral romanNumber = new RomanNumeral(roman);
		return romanNumber.toInt();
	}

}