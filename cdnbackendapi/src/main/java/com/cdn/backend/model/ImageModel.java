package com.cdn.backend.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="image")
public class ImageModel {

	@XmlElement(name="image")
	private String base64Image;
	
	@XmlElement(name="title")
	private String title;

	
	public String getBase64Image() {
		return base64Image;
	}
	public String getTitle() {
		return title;
	}
	public void setBase64Image(String base64Image) {
		this.base64Image = base64Image;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public ImageModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
