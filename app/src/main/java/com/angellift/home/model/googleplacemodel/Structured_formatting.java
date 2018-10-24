package com.angellift.home.model.googleplacemodel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;
/**
 * Awesome Pojo Generator
 * */
public class Structured_formatting{
  @SerializedName("main_text_matched_substrings")
  @Expose
  private List<Main_text_matched_substrings> main_text_matched_substrings;
  @SerializedName("secondary_text")
  @Expose
  private String secondary_text;
  @SerializedName("main_text")
  @Expose
  private String main_text;
  public Structured_formatting(){
  }
  public Structured_formatting(List<Main_text_matched_substrings> main_text_matched_substrings,String secondary_text,String main_text){
   this.main_text_matched_substrings=main_text_matched_substrings;
   this.secondary_text=secondary_text;
   this.main_text=main_text;
  }
  public void setMain_text_matched_substrings(List<Main_text_matched_substrings> main_text_matched_substrings){
   this.main_text_matched_substrings=main_text_matched_substrings;
  }
  public List<Main_text_matched_substrings> getMain_text_matched_substrings(){
   return main_text_matched_substrings;
  }
  public void setSecondary_text(String secondary_text){
   this.secondary_text=secondary_text;
  }
  public String getSecondary_text(){
   return secondary_text;
  }
  public void setMain_text(String main_text){
   this.main_text=main_text;
  }
  public String getMain_text(){
   return main_text;
  }
}