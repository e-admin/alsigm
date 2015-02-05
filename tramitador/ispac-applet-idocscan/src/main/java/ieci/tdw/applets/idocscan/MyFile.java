package ieci.tdw.applets.idocscan;

import java.io.File;

/**
 * User: RobertoBas Date: 27-ene-2005 Time: 16:03:44
 */
public class MyFile extends File
{

	private String	docId;
   private long	modified;

	public MyFile(String s)
	{
		super(s);
	}

	public static File rename(String path)
	{
		File oldFile = new File(path);
		File newFile = new File(FileUtil.getPath(oldFile) + new FssGuid() + "."
				+ FileUtil.getFileExt(oldFile.getName()));
		oldFile.renameTo(newFile);
		return newFile;
	}

	public String getDocId()
	{
		return docId;
	}

	public void setDocId(String docId)
	{
		this.docId = docId;
	}
	public String toString()
	{
		  return "docId: "+this.docId;
	}

	public long getModified()
	{
		return modified;
	}

	public void setModified(long modified)
	{
		this.modified = modified;
	}
}