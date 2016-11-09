package com.can.store.androidbcc.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * 最適化結果Dto
 * サーバ側と合わせています。
 * @author kitazawatakuya
 *
 */
public class  ProductInfo implements Serializable{

    private static final long serialVersionUID = 1L;

    @Expose(serialize = true, deserialize = true)
    @SerializedName("obj")
    private List<OptimazeResult> optimazeResultList;

	public List<OptimazeResult> getOptimazeResultList() {
		return optimazeResultList;
	}

	public void setOptimazeResultList(List<OptimazeResult> optimazeResultList) {
		this.optimazeResultList = optimazeResultList;
	}
}
