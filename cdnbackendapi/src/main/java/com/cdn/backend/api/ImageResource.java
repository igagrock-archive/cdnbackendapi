package com.cdn.backend.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.cdn.backend.model.ImageModel;
import com.cdn.backend.model.ImageResponseModel;
import com.cdn.backend.service.CacheService;
import com.cdn.backend.service.ImageService;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("images")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ImageResource {
	
	   private ImageService service;
       private CacheService<ImageResponseModel> cs;
	   

	public ImageResource() {
		this.service = new ImageService();
		this.cs = new CacheService<ImageResponseModel>();
	}


	@POST
	public Response processImage(@Context UriInfo uriInfo,
									ImageModel model,
									@Context Request request
			                          ){
	  
    	//send model object to service 
    	//service would decode String to image
    	//save the image with a random name made with title base64String
    	// return an object {imageresponseModel} with name and url

        ImageResponseModel irmodel = service.saveimage(model, uriInfo);
		return cs.buildResponseWithCacheEtag(request, model.getBase64Image(), irmodel).build();
	}
	

	
}
