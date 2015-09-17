package com.cdn.backend.service;

import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

public class CacheService<T> {
	
	/* @method addCacheControlWithEtag
	 * @params Request, List, genericEntity
	 * 
	 * 
	 */

	
	public ResponseBuilder buildResponseWithCacheEtag(Request request,
			String image, T model){

		CacheControl cc =  new CacheControl();
		cc.setMaxAge(60);

		EntityTag eTag = new EntityTag(Integer.toString(image.hashCode()));
		ResponseBuilder builder = request.evaluatePreconditions(eTag);

		if(builder == null) 	builder = Response.ok(model).tag(eTag);

		builder.cacheControl(cc);
		return builder;
	}
	 


}
