package com.angellift.home.model.googleplacemodel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Types;
import java.util.List;
/**
 * Awesome Pojo Generator
 * */
public class Predictions{
  @SerializedName("reference")
  @Expose
  private String reference;

  @SerializedName("matched_substrings")
  @Expose
  private List<Matched_substrings> matched_substrings;
  @SerializedName("terms")
  @Expose
  private List<Terms> terms;
  @SerializedName("structured_formatting")
  @Expose
  private Structured_formatting structured_formatting;
  @SerializedName("description")
  @Expose
  private String description;
  @SerializedName("id")
  @Expose
  private String id;
  @SerializedName("place_id")
  @Expose
  private String place_id;
  public Predictions(){
  }
  public Predictions(String reference,List<Matched_substrings> matched_substrings,List<Terms> terms,Structured_formatting structured_formatting,String description,String id,String place_id){
   this.reference=reference;

   this.matched_substrings=matched_substrings;
   this.terms=terms;
   this.structured_formatting=structured_formatting;
   this.description=description;
   this.id=id;
   this.place_id=place_id;
  }
  public void setReference(String reference){
   this.reference=reference;
  }
  public String getReference(){
   return reference;
  }

  public void setMatched_substrings(List<Matched_substrings> matched_substrings){
   this.matched_substrings=matched_substrings;
  }
  public List<Matched_substrings> getMatched_substrings(){
   return matched_substrings;
  }
  public void setTerms(List<Terms> terms){
   this.terms=terms;
  }
  public List<Terms> getTerms(){
   return terms;
  }
  public void setStructured_formatting(Structured_formatting structured_formatting){
   this.structured_formatting=structured_formatting;
  }
  public Structured_formatting getStructured_formatting(){
   return structured_formatting;
  }
  public void setDescription(String description){
   this.description=description;
  }
  public String getDescription(){
   return description;
  }
  public void setId(String id){
   this.id=id;
  }
  public String getId(){
   return id;
  }
  public void setPlace_id(String place_id){
   this.place_id=place_id;
  }
  public String getPlace_id(){
   return place_id;
  }
}