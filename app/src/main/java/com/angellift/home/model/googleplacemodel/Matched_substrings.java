package com.angellift.home.model.googleplacemodel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Awesome Pojo Generator
 * */
public class Matched_substrings{
  @SerializedName("offset")
  @Expose
  private Integer offset;
  @SerializedName("length")
  @Expose
  private Integer length;
  public Matched_substrings(){
  }
  public Matched_substrings(Integer offset,Integer length){
   this.offset=offset;
   this.length=length;
  }
  public void setOffset(Integer offset){
   this.offset=offset;
  }
  public Integer getOffset(){
   return offset;
  }
  public void setLength(Integer length){
   this.length=length;
  }
  public Integer getLength(){
   return length;
  }
}