package ieci.tdw.ispac.ispaclib.messages;


public class MessagesFormatter {
	
    public static final String PREFIX = "{";
    public static final String SUFFIX = "}";


    public static String replace(String text, String param, String value) {
		int pos = text.indexOf(param);
		if (pos < 0) {
			return text;
		} else {
			return new StringBuffer()
				.append(text.substring(0, pos))
				.append(value)
				.append(text.substring(pos + param.length(), text.length()))
				.toString();
		}
	}

	public static String format(String text, Object params[]) {
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				Object obj = params[i];
				if (obj != null) {
					text = replace(text, "{" + String.valueOf(i) + "}", 
							obj.toString());
				}
			}
		}

		return text;
	}
}
