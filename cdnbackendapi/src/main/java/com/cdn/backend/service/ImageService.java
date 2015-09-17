package com.cdn.backend.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.apache.commons.codec.binary.Base64;

import com.cdn.backend.model.ErrorModel;
import com.cdn.backend.model.ImageModel;
import com.cdn.backend.model.ImageResponseModel;

public class ImageService {
	
	/*
	public static void fetchFromApi(File file) throws IOException{
		
		 Client client = ClientBuilder.newClient();
		   WebTarget target = client.target("http://backendapi-vbr.rhcloud.com/api").path("users").path("irshsheikh").path("articles/107");
		   System.out.println(target.getUri().toURL().toString());
		   Invocation.Builder  builder = target.request(MediaType.APPLICATION_JSON);
		   builder.header("Authorization", "Base "+new String(Base64.encodeBase64("irshsheikh:International0401".getBytes())));
		   
		   Response response = builder.get();
		   System.out.println(response.getStatus());
		   
		 //   ArticleModel model = response.readEntity(ArticleModel.class);
		//    String base64imageString = model.getImage();
		    System.out.println(base64imageString.split(",")[1]);
		    
		    BufferedImage newimage = decodeToImage(base64imageString.split(",")[1]);
			
		      ImageIO.write(newimage, "png", file);
		
	}*/
	
	
	public ImageResponseModel saveimage(ImageModel model,String contextPath){
	
		String inPath = System.getenv("OPENSHIFT_DATA_DIR")+"images";
		String fileName = generateUniquename(model.getTitle());
		String fullInPath = inPath+fileName;
		 File file = new File(fullInPath);
		
		 try {
			writeImageToFile(model.getBase64Image(), file);
		} catch (IOException | IllegalArgumentException e ) {
			e.printStackTrace();
			ErrorModel ermodel = new ErrorModel("IOEXCEPTION", 500, "Unable to save the file");
			throw new WebApplicationException(Response.status(501).entity(ermodel).build());
			
		}

		 String outPath = contextPath+fileName;
		 ImageResponseModel imd = new ImageResponseModel();
		 imd.setName(fileName);
		 imd.setUrl(outPath);
		
		return imd;
	}
	
	private void writeImageToFile(String base64Image,File file) throws IOException, IllegalArgumentException{
		String newImageString;
		String[] imageStringarr  = base64Image.split(",");
		
		//check if the image data is attached and remove that from string
		if( imageStringarr!= null && imageStringarr.length > 1)
			newImageString = imageStringarr[1];
		else newImageString = base64Image;
	  //decode the image to binary data
		BufferedImage image = decodeToImage(newImageString);
		if(image != null)
		ImageIO.write(image, "png",file );
		else throw new IllegalArgumentException();
	}
	
	//generate the unique name
	private String generateUniquename(String title){
		 String[] titlearr = title.split(" ");
		 String newTitle;
			if( titlearr != null && titlearr.length > 1)
			   newTitle =titlearr[1]; 
			
			else newTitle = title;
			
		 return "/"+newTitle+"_"+System.currentTimeMillis()+".png";
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


}
