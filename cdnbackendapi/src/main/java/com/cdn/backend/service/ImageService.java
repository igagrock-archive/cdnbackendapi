package com.cdn.backend.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.codec.binary.Base64;

import com.cdn.backend.api.ImageResource;
import com.cdn.backend.model.ErrorModel;
import com.cdn.backend.model.ImageModel;
import com.cdn.backend.model.ImageResponseModel;

public class ImageService {
	
	private final static String PATH_TO_IMAGES = System.getenv("OPENSHIFT_DATA_DIR")+"images";
	
	
	public ImageResponseModel saveimage(ImageModel model,UriInfo uriInfo){
	   
		String fileType = model.getBase64Image().split(":")[1].split(";")[0].split("/")[1];
		if(fileType == null) fileType = "png";
		
		 String fileName = generateUniquename(model.getTitle(),fileType);
		 String fullInPath = PATH_TO_IMAGES+fileName;
		 File file = new File(fullInPath);
		
		 try {
			writeImageToFile(model.getBase64Image(),fileType,file);
		} catch (IOException | IllegalArgumentException e ) {
			e.printStackTrace();
			ErrorModel ermodel = new ErrorModel("IOEXCEPTION", 501, "Unable to save the file");
			throw new WebApplicationException(Response.status(501).entity(ermodel).build());
			
		}

		 String outPath = getpath(uriInfo)+fileName;
		 ImageResponseModel imd = new ImageResponseModel();
		 imd.setName(fileName);
		 imd.setUrl(outPath);
		
		return imd;
	}
	
	private void writeImageToFile(String base64Image,String type,File file) throws IOException, IllegalArgumentException{
		String newImageString;
		String[] imageStringarr  = base64Image.split(",");
		
		//check if the image data is attached and remove that from string
		if( imageStringarr!= null && imageStringarr.length > 1)
			newImageString = imageStringarr[1];
		else newImageString = base64Image;
	  //decode the image to binary data
		BufferedImage image = decodeToImage(newImageString);
		if(image != null)
		ImageIO.write(image, type,file );
		else throw new IllegalArgumentException();
	}
	
	//generate the unique name
	private String generateUniquename(String title,String type){
		 String[] titlearr = title.split(" ");
		 String newTitle;
			if( titlearr != null && titlearr.length > 1)
			   newTitle =titlearr[1]; 
			
			else newTitle = title;
			
		 return "/"+newTitle+"_"+System.currentTimeMillis()+"."+type;
	}
	private  BufferedImage decodeToImage(String imageString) throws IOException{
		
		
		  BufferedImage image = null;
		  byte[] imageByte = null;
		  ByteArrayInputStream bis= null;
		
			try{
			  imageByte = Base64.decodeBase64(imageString);
			  System.out.println(imageByte);
			   bis = new ByteArrayInputStream(imageByte);
			  image = ImageIO.read(bis);
			
			}finally{
				if(bis != null) bis.close();
				}
		
		
		
		return image;
	}
	
	@SuppressWarnings("unused")
	private  String encodeToString(BufferedImage image, String type){
		
		String imageString = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
				
		try {
			ImageIO.write(image, type, bos);
			byte[] byteArray = bos.toByteArray();
			
			imageString = Base64.encodeBase64String(byteArray);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return imageString;
	}

	private String getpath(UriInfo uriInfo){
		
		String path = uriInfo.getBaseUriBuilder().path(ImageResource.class).build().toString().replaceAll("/api", "");
		System.out.println(path );
		return path;
	}

}
