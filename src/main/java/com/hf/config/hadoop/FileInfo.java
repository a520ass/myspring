package com.hf.config.hadoop;


public class FileInfo {
	private String name;
	private  String path;
	private long size;
	private long modified;

	public FileInfo(String name, String path, long size, long modified) {
		this.name = name;
		this.path = path;
		this.size = size;
		this.modified = modified;
	}

    public FileInfo() {
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public long getSize() {
        return size;
    }

    public long getModified() {
        return modified;
    }
}
