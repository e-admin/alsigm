package com.ieci.tecdoc.common.extension;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.UserType;
import org.apache.commons.lang.StringUtils;

public class StringClobType implements UserType
{
    public int[] sqlTypes()
    {
        return new int[] { Types.CLOB };
    }

    public Class returnedClass()
    {
        return String.class;
    }

    public boolean equals(Object x, Object y)
    {
        return (x == y)
            || (x != null
                && y != null
                && (x.equals(y)));
    }

    public Object nullSafeGet(ResultSet rs, String[] names, Object owner)
        throws HibernateException, SQLException
    {
        String line;
		String str = "";
		Reader reader = rs.getCharacterStream(names[0]);
		if (reader == null){
			return null;
		}
		BufferedReader b = new BufferedReader(reader);
		try {
			while ((line = b.readLine()) != null) {
				if(StringUtils.isNotEmpty(str)){
					str += "\n";
				}
				str += line;
			}
		} catch (IOException e) {
			throw new SQLException(e.toString());
		}
		return str;

		/*
		 * Clob clob = rs.getClob(names[0]); String text = ""; if (clob !=
		 * null){ text = clob.getSubString(1, (int) clob.length()); }
		 *
		 * return text;
		 */
	}

    public void nullSafeSet(PreparedStatement st, Object value, int index)
        throws HibernateException, SQLException
    {
	if (value==null){
		value="";
	}

        StringReader r = new StringReader( (String)value );
        st.setCharacterStream( index, r, ((String)value).length() );
    }

    public void nullSafeSet1(PreparedStatement st, Object value, int index)
			throws HibernateException, SQLException {

	if (value==null){
		value="";
	}

		StringReader r = new StringReader((String) value);
		st.setString(index, (String) value);
	}

    public Object deepCopy(Object value)
    {
        if (value == null) return null;
        return new String((String) value);
    }

    public boolean isMutable()
    {
        return false;
    }
}

