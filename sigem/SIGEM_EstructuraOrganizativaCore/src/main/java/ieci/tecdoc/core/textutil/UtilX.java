package ieci.tecdoc.core.textutil;

import ieci.tecdoc.core.exception.IeciTdException;

import java.text.StringCharacterIterator;

import java.util.StringTokenizer;


public final class UtilX
{

	//~ Constructors -----------------------------------------------------------

	private UtilX(){}

	//~ Methods ----------------------------------------------------------------

	public static String parseStringNextToken(StringTokenizer tokens,
											  boolean remQuotes)
									   throws Exception
	{

		String token = tokens.nextToken();

		if(remQuotes)
		{

			if(token.startsWith("\"") && token.endsWith("\""))
				token = token.substring(1, token.length()-1);

		}

		return token;

	}

	public static String parseStringNextToken(StringTokenizer tokens)
									   throws Exception
	{

		return parseStringNextToken(tokens, false);

	}

	public static int parseIntegerNextToken(StringTokenizer tokens)
									 throws Exception
	{

		String token = tokens.nextToken();

		return Integer.parseInt(token);

	}

	/*
	 * Se busca un String que empiece y termine por comillas dobles
	 */
	public static String parseString(StringCharacterIterator str)
							  throws Exception
	{

		String token     = new String();
		char   character;
		char   sep 		 = '"';
		
		character = str.current();

		if(character != sep) 
		{

			throw new IeciTdException(UtilError.EC_INVALID_FORMATED_STRING,
									  UtilError.EM_INVALID_FORMATED_STRING);
			
		}
		
		character = str.next();

		while((character != sep) &&
				  (character != StringCharacterIterator.DONE))
		{

			token     = token+character;
			character = str.next();

		}

		if(character != sep)
		{

			throw new IeciTdException(UtilError.EC_INVALID_FORMATED_STRING,
									  UtilError.EM_INVALID_FORMATED_STRING);
			
		}
		
		str.next();

		return token;

	}

	public static String parseString(StringCharacterIterator str, char sep)
							  throws Exception
	{

		String token     = new String();
		char   character;
		character 		 = str.current();

		while((character != sep) &&
				  (character != StringCharacterIterator.DONE))
		{

			token     = token+character;
			character = str.next();

		}

		return token;

	}

	public static int parseInteger(StringCharacterIterator str, char sep)
							throws Exception
	{

		String token     = new String();
		char   character;
		character 		 = str.current();

		while((character != sep) &&
				  (character != StringCharacterIterator.DONE))
		{

			token     = token+character;
			character = str.next();

		}

		return Integer.parseInt(token);

	}

	public static StringCharacterIterator iteratorIncrementIndex(StringCharacterIterator iterator,
																 int incr)
		throws Exception
	{

		iterator.setIndex(iterator.getIndex()+incr);

		return iterator;

	}

}
 // class
