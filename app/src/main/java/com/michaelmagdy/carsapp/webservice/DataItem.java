package com.michaelmagdy.carsapp.webservice;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("constructionYear")
	private String constructionYear;

	@SerializedName("imageUrl")
	private String imageUrl;

	@SerializedName("id")
	private int id;

	@SerializedName("brand")
	private String brand;

	@SerializedName("isUsed")
	private boolean isUsed;

	public String getConstructionYear(){
		return constructionYear;
	}

	public String getImageUrl(){
		return imageUrl;
	}

	public int getId(){
		return id;
	}

	public String getBrand(){
		return brand;
	}

	public boolean isIsUsed(){
		return isUsed;
	}
}