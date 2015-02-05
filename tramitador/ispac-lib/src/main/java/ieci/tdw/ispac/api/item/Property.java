/*
 * Created on 08-jul-2004
 *
 */
package ieci.tdw.ispac.api.item;

import ieci.tdw.ispac.ispaclib.utils.XmlTag;

import java.io.Serializable;
import java.sql.Types;

/**
 * La clase Property especifica cada una de las propiedades de un IItem.
 * Además también se utiliza para definir ObjectDAOs que no se correspondan
 * directamente con la definición de una tabla.
 */

public class Property implements Serializable
{
	private int mOrdinal;
	private String msName;
	private int mType;
	private int mSize;
	private int mDecimalDigits;
	private String msTitle;
	private boolean mIsNullable;
	private boolean mIsMultivalue;
	private String msRawName;
	private int reference;

	public Property(int ordinal, String name,int type,int size,
			int decimalDigits, String title,boolean isNullable, boolean isMultivalue)
	{
		this.mOrdinal=ordinal;
		this.msName=name;
		this.mType=type;
		this.mSize=size;
		this.mDecimalDigits=decimalDigits;
		this.msTitle=title;
		this.mIsNullable=isNullable;
		this.mIsMultivalue=isMultivalue;
		this.msRawName=null;
		this.reference=0;
	}

	public Property(int ordinal, String name,int type)
	{
		this.mOrdinal=ordinal;
		this.msName=name;
		this.mType=type;
		this.mSize=0;
		this.mDecimalDigits=0;
		this.msTitle=name;
		this.mIsNullable=true;
		this.msRawName=null;
		this.reference=0;
	}

	public Property(int ordinal, String name,int type, boolean isMultivalue)
	{
		this(ordinal, name, type);
		this.mIsMultivalue=isMultivalue;
	}
	
	
	public Property(int ordinal, String name)
	{
		this.mOrdinal=ordinal;
		this.msName=name;
		this.mType=0;
		this.mSize=0;
		this.mDecimalDigits=0;
		this.msTitle=name;
		this.mIsNullable=true;
		this.msRawName=null;
		this.reference=0;
	}

	public Property(int ordinal, String name,String rawname)
	{
		this.mOrdinal=ordinal;
		this.msName=name;
		this.mType=0;
		this.mSize=0;
		this.mDecimalDigits=0;
		this.msTitle=name;
		this.mIsNullable=true;
		this.msRawName=rawname;
		this.reference=0;
	}

	public Property(int ordinal, String name,String rawname,int type)
	{
		this.mOrdinal=ordinal;
		this.msName=name;
		this.mType=type;
		this.mSize=0;
		this.mDecimalDigits=0;
		this.msTitle=name;
		this.mIsNullable=true;
		this.msRawName=rawname;
		this.reference=0;
	}

	public Property(int ordinal, String name,String rawname,int type,int size,
			int decimalDigits, String title,boolean isNullable)
	{
		this.mOrdinal=ordinal;
		this.msName=name;
		this.mType=type;
		this.mSize=size;
		this.mDecimalDigits=decimalDigits;
		this.msTitle=title;
		this.mIsNullable=isNullable;
		this.msRawName=rawname;
		this.reference=0;
	}

	public boolean isDefined()
	{
	    return (this.msName!=null && this.mType!=0);
	}

	public String getRawName()
	{
		if (msRawName==null)
			return msName;
		return msRawName;
	}

	public int getReference()
	{
		return reference;
	}

	public void setReference(int ref)
	{
		reference=ref;
	}

	protected void translate(String newname)
	{
		msRawName=msName;
		msName=newname;
	}

	public int getOrdinal()
	{
		return mOrdinal;
	}
	public String getName()
	{
		return msName;
	}

	public void setName( String sName)
	{
		msName = sName;
	}
	public String getTitle()
	{
		return msTitle;
	}
	public int getType()
	{
		return mType;
	}
	public int getSize()
	{
		return mSize;
	}
	public int getDecimalDigits()
	{
		return mDecimalDigits;
	}
	public boolean getIsNullable()
	{
		return mIsNullable;
	}
	public boolean isMultivalue(){
		return mIsMultivalue;
	}
	
	public boolean isNumeric() 
	{
		return (mType == Types.BIGINT)
			|| (mType == Types.BIT)
			|| (mType == Types.DECIMAL)
			|| (mType == Types.DOUBLE)
			|| (mType == Types.FLOAT)
			|| (mType == Types.INTEGER)
			|| (mType == Types.NUMERIC)
			|| (mType == Types.REAL)
			|| (mType == Types.SMALLINT)
			|| (mType == Types.TINYINT);
	}

	public String toXML() {

		String sXml = null;

		sXml  = XmlTag.newTag("type", mType);
		sXml += XmlTag.newTag("name", msName);
		sXml += XmlTag.newTag("rawname", msRawName);
		sXml += XmlTag.newTag("title", msTitle);
		sXml += XmlTag.newTag("size", mSize);
		sXml += XmlTag.newTag("decimal-digits", mDecimalDigits);
		sXml += XmlTag.newTag("nullable", mIsNullable? "YES" : "NO");

		return XmlTag.newTag("property", sXml, mOrdinal);
	}
}
