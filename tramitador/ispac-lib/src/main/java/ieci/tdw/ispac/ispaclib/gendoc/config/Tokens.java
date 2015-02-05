package ieci.tdw.ispac.ispaclib.gendoc.config;

import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Tokens {

	protected List tokens = new ArrayList();

	public Tokens() {
		super();
	}

	public List getTokens() {
		return this.tokens;
	}

	public void addToken(Token token) {
		tokens.add(token);
	}

	public Token getToken(String tokenName) {
		for (Iterator iterator = tokens.iterator(); iterator.hasNext();) {
			Token token = (Token) iterator.next();
			if (StringUtils.equals(tokenName, token.getName())) {
				return token;
			}
		}
		return null;
	}

}
