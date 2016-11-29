package com.prodigious.festivitiesapp.jerseyapp;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

import com.prodigious.festivitiesapp.filters.CORSResponseFilter;

@ApplicationPath("/")
public class FestivitiesApp extends ResourceConfig
{
	public FestivitiesApp()
	{
		packages("com.prodigious.festivitiesapp.jerseyapp.FestivitiesApp");
		packages("com.prodigious.festivitiesapp.rest");
		
		register(CORSResponseFilter.class);
		register(JacksonFeature.class);
		register(MultiPartFeature.class);
	}
}