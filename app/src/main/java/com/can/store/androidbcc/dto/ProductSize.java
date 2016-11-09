package com.can.store.androidbcc.dto;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * 商品サイズDtoです。
 * @author kitazawa.takuya
 *
 */
public class ProductSize implements Serializable {

	 private static final long serialVersionUID = 1L;
	 @Expose(serialize = true, deserialize = true)
     private float width;
	 @Expose(serialize = true, deserialize = true)
     private float height;
	 @Expose(serialize = true, deserialize = true)
     private float length;
	 @Expose(serialize = true, deserialize = true)
     private float weight;
	 @Expose(serialize = true, deserialize = true)
     private float totalSize;
	 
     public float getTotalSize() {
         return totalSize;
     }
     public void setTotalSize(float totalSize) {
         this.totalSize = totalSize;
     }
     public float getWidth() {
         return width;
     }
     public void setWidth(float width) {
         this.width = width;
     }
     public float getHeight() {
         return height;
     }
     public void setHeight(float height) {
         this.height = height;
     }
     public float getLength() {
         return length;
     }
     public void setLength(float length) {
         this.length = length;
     }
     public float getWeight() {
         return weight;
     }
     public void setWeight(float weight) {
         this.weight = weight;
     }
}