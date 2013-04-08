package ieci.tdw.applets.ispacdocapplet.dto;

import java.io.File;

public class FileInfo {

	private String name = null;
	private String path = null;
	private long length = 0;
	private long lastModified = 0;
	
	public FileInfo() {
		super();
	}

	public FileInfo(File file) {
		this();
		
		if (file != null) {
			setName(file.getName());
			setPath(file.getAbsolutePath().replace('\\', '/'));
			setLength(file.length());
			setLastModified(file.lastModified());
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public long getLength() {
		return length;
	}

	public void setLength(long length) {
		this.length = length;
	}

	public long getLastModified() {
		return lastModified;
	}

	public void setLastModified(long lastModified) {
		this.lastModified = lastModified;
	}
}
