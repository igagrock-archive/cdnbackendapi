package com.cdn.backend.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="image")
public class ImageResponseModel {
	
	private String name;
	private String url;
	
	
	public String getName() {
		return name;
	}
	public String getUrl() {
		return url;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public ImageResponseModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
