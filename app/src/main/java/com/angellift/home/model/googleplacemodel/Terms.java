package com.angellift.home.model.googleplacemodel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Awesome Pojo Generator
 * */
public class Terms{
  @SerializedName("offset")
  @Expose
  private Integer offset;
  @SerializedName("value")
  @Expose
  private String value;
  public Terms(){
  }
  public Terms(Integer offset,String value){
   this.offset=offset;
   this.value=value;
  }
  public void setOffset(Integer offset){
   this.offset=offset;
  }
  public Integer getOffset(){
   return offset;
  }
  public void setValue(String value){
   this.value=value;
  }
  public String getValue(){
   return value;
  }
}