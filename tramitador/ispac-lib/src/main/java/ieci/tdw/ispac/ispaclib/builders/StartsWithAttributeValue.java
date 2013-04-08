package ieci.tdw.ispac.ispaclib.builders;

import java.util.Locale;

import org.htmlparser.Attribute;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Tag;

public class StartsWithAttributeValue implements NodeFilter
{
    /**
     * The attribute to check for.
     */
    protected String mAttribute;

    /**
     * The value to check for.
     */
    protected String mValue;

    /**
     * Creates a new instance of StartsWithAttributeValue.
     * With no attribute name, this would always return <code>false</code>
     * from {@link #accept}.
     */
    public StartsWithAttributeValue()
    {
        this("", null);
    }

    /**
     * Creates a new instance of StartsWithAttributeValue that accepts tags
     * with the given attribute.
     * @param attribute The attribute to search for.
     */
    public StartsWithAttributeValue(String attribute)
    {
        this(attribute, null);
    }

    /**
     * Creates a new instance of StartsWithAttributeValue that accepts tags
     * with the given attribute and value.
     * @param attribute The attribute to search for.
     * @param value The value that must be matched,
     * or null if any value will match.
     */
    public StartsWithAttributeValue(String attribute, String value)
    {
        mAttribute = attribute.toUpperCase(Locale.ENGLISH);
        mValue = value;
    }

    /**
     * Get the attribute name.
     * @return Returns the name of the attribute that is acceptable.
     */
    public String getAttributeName()
    {
        return (mAttribute);
    }

    /**
     * Set the attribute name.
     * @param name The name of the attribute to accept.
     */
    public void setAttributeName(String name)
    {
        mAttribute = name;
    }

    /**
     * Get the attribute value.
     * @return Returns the value of the attribute that is acceptable.
     */
    public String getAttributeValue()
    {
        return (mValue);
    }

    /**
     * Set the attribute value.
     * @param value The value of the attribute to accept.
     * If <code>null</code>, any tag with the attribute,
     * no matter what it's value is acceptable.
     */
    public void setAttributeValue(String value)
    {
        mValue = value;
    }

    /**
     * Accept tags with a certain attribute.
     * @param node The node to check.
     * @return <code>true</code> if the node has the attribute
     * (and value if that is being checked too), <code>false</code> otherwise.
     */
    public boolean accept(Node node)
    {
        Tag tag;
        Attribute attribute;
        boolean ret;

        ret = false;
        if (node instanceof Tag)
        {
            tag = (Tag)node;
            attribute = tag.getAttributeEx(mAttribute);
            ret = null != attribute;
            if (ret && (null != mValue))
                ret = attribute.getValue().startsWith(mValue);
        }

        return (ret);
    }
}

