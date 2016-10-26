package com.can.store.androidbcc.model;

import com.can.store.androidbcc.Const.Country;

import java.io.Serializable;

/**
 * MWSの情報を持つエンティティです。 システム、会員問わず入ります。
 *
 * @author hokuto
 *
 */
public class BmMwsProperty implements Serializable {
	private static final long serialVersionUID = 1L;


	/** 出品者ID */
	private String sellerId;
	/** アクセスキー */
	private String accessKeyId;
	/** シークレットキー */
	private String secretAccessKey;
	/** Gmail用アクセストークン */
	private String mwsAuthToken;

	private Country country;




	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public String getAccessKeyId() {
		return accessKeyId;
	}

	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}

	public String getSecretAccessKey() {
		return secretAccessKey;
	}

	public void setSecretAccessKey(String secretAccessKey) {
		this.secretAccessKey = secretAccessKey;
	}


	public String getMwsAuthToken() {
		return mwsAuthToken;
	}

	public void setMwsAuthToken(String mwsAuthToken) {
		this.mwsAuthToken = mwsAuthToken;
	}


	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}
}
