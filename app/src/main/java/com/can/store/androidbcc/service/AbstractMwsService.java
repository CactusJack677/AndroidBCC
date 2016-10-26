package com.can.store.androidbcc.service;

import com.amazonservices.mws.client.MwsObject;
import com.amazonservices.mws.client.MwsReader;
import com.amazonservices.mws.client.MwsUtl;
import com.amazonservices.mws.client.MwsXmlReader;
import com.can.store.androidbcc.Const;
import com.can.store.androidbcc.Const.Country;
import com.can.store.androidbcc.exception.MyException;
import com.can.store.androidbcc.model.BmMwsProperty;
import com.can.store.androidbcc.util.Base64Util;
import com.can.store.androidbcc.util.DateUtil;
import com.can.store.androidbcc.util.StackTraceUtil;
import com.can.store.androidbcc.util.StringUtil;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.logging.Logger;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;


/**
 * 各MwsServiceの親クラス
 *
 * @author hokuto
 *
 */
public abstract class AbstractMwsService {
	public static final Logger log = Logger.getLogger(AbstractMwsService.class.getName());
	// 定数
    protected static final String DEV_ACCESS_KEY_ID = "AKIAIRXBMZGN4T2GGEKA";
    protected static final String DEV_SECRET_ACCESS_KEY_ID = "bB9s8SyC+3q6bxq4vnlip2ukgB0xVCl9hRfdD4oC";
	protected static final String DEFAULT_ENCODING = "UTF-8";
	protected static final String RESPONSE_ENCODING = "MS932";
	protected static final String SIGNATURE_METHOD = "HmacSHA256";
	protected static final String SIGNATURE_VERSION = "2";
    protected static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    /** 無償製品時のUserAgent（コンストラクタで、userAgentを書き換えるための定数です。） */
    protected static final String USER_AGENT_FREE = "/CreativeAdventureNetworkBccFree/1.0/(Language=Java/7;Platform=GoogleAppEngine";

    /** 通信時のUserAgent */
    protected String userAgent = "/CreativeAdventureNetworkBcc/1.0/(Language=Java/7;Platform=GoogleAppEngine";

	// フィールド
	protected String sellerId;
	protected String shopName;
	protected String marketplaceId;
	protected String accessKeyId;
	protected String secretAccessKey;
	protected Country country;
	protected String mwsAuthToken;

	/**
	 * Tokenなしで実行するパターンも考慮してTokenは手動で消せるようにGetter/Setterを用意する
	 *
	 * @return
	 */
	public String getMwsAuthToken() {
		return mwsAuthToken;
	}
	public void setMwsAuthToken(String mwsAuthToken) {
		this.mwsAuthToken = mwsAuthToken;
	}

	/**
	 * 商用環境かどうかをチェックします。
	 *
	 * @return
	 */
	public boolean isCommerce() {
		return true;
	}

	/**
	 * サービスのバージョンを返します。
	 *
	 * @return
	 */
	abstract protected String getServiceVersion();

	/**
	 * サービスのパスを返します。 レポートAPI なし。 フィードAPI なし。 商品API Products 商品登録API FulfillmentInboundShipment
	 *
	 * @return
	 */
	abstract protected String getServicePath();

	/**
	 * シグニチャメソッドを返します
	 *
	 * @return
	 */
	protected String getSignitureMethod() {
		return SIGNATURE_METHOD;
	}

	/**
	 * シグニチャバージョンを返します。
	 *
	 * @return
	 */
	protected String getSignitureVersion() {
		return SIGNATURE_VERSION;
	}


	/**
	 * リクエストMapからHTTPリクエストオブジェクトを生成します。
	 *
	 * @return
	 */
	protected String createHttpRequest(Country country, Map<String, String> params) {
		try {
			String parameter = "";
			for(String param : params.keySet()) {
				if(StringUtil.isNotEmpty(parameter)) {
					parameter += "&";
				}
				// 検索の場合はURLエンコードを行う。
				if(!param.equals("Signature")) {
					parameter += String.format("%s=%s", param, urlEncode(params.get(param)));
					// log.info(String.format("[パラメーター][%s=%s]", param, urlEncode(params.get(param))));
				} else {
					parameter += String.format("%s=%s", param, params.get(param));
					// log.info(String.format("[パラメーター][%s=%s]", param, params.get(param)));
				}
			}

			String url = String.format("%s/%s", country.getMwsEndpoint(), this.getServicePath());

			HttpURLConnection conn = null;
			try {
				conn = (HttpURLConnection) new URL(url).openConnection();
				conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
				conn.setRequestProperty("Pragma", "no-cache");
				conn.setRequestProperty("Cache-Control", "no-cache");
				conn.setRequestProperty("max-age", "0");

				// ヘッダーにAmazonデベロッパー向けのUserAgentをセットする。
				if(StringUtil.isNotEmpty(params.get("MWSAuthToken"))){
					conn.setRequestProperty("User-Agent", userAgent);
				}
				conn.setDoInput(true);
				conn.setDoOutput(true);
				conn.setReadTimeout(10000);
				conn.setConnectTimeout(15000);
				conn.setRequestMethod("POST");
				conn.setDoInput(true);
				conn.connect();

				OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream(), Const.DEFAULT_CONTENT_TYPE);
				writer.write(parameter);
				writer.close();
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				StringBuffer jsonString = new StringBuffer();
				String line;
				while ((line = br.readLine()) != null) {
					jsonString.append(line);
				}
				br.close();
				conn.disconnect();

				int resp = conn.getResponseCode();

				InputStream in = conn.getInputStream();
				byte bodyByte[] = new byte[1024];
				in.read(bodyByte);
				in.close();
				String content = new String(bodyByte, DEFAULT_ENCODING);

				return content;
			} catch(IOException e) {
				e.printStackTrace();
			} finally {
				if(conn != null) {
					conn.disconnect();
				}
			}
		} catch (Exception e) {
			throw new MyException(e);
		}
		return null;
	}

	/**
	 * 共通のリクエストをセットします。
	 *
	 * @param params
	 */
	protected void setParameter(Map<String, String> params) {
		try {
			params.put("SellerId", sellerId);
			params.put("MarketplaceId", country.getMarketplaceId());
			params.put("Version", getServiceVersion());
			params.put("SignatureVersion", SIGNATURE_VERSION);
			params.put("Timestamp", getFormattedTimestamp());
			params.put("SignatureMethod", SIGNATURE_METHOD);
			if(StringUtil.isNotEmpty(mwsAuthToken)) {
                params.put("MWSAuthToken", mwsAuthToken);
                params.put("AWSAccessKeyId", accessKeyId);
			}else{
                params.put("AWSAccessKeyId", accessKeyId);
			}

			String signature = URLEncoder.encode(createSignature(this.country, secretAccessKey, params), DEFAULT_ENCODING);
			params.put("Signature", signature);
		} catch (Exception e) {
			throw new MyException(e);
		}
	}

	/**
	 * 共通のリクエストをセットします。セラー指定版
	 *
	 */
	protected void setParameter(BmMwsProperty bmMwsProperty, Map<String, String> params) {
		try {


		    params.put("Version", this.getServiceVersion());
		    params.put("SignatureVersion", this.getSignitureVersion());
		    params.put("Timestamp", this.getFormattedTimestamp());
		    params.put("SignatureMethod", this.getSignitureMethod());

		    String signature = null;
	        if(StringUtil.isNotEmpty(bmMwsProperty.getMwsAuthToken())){
	            params.put("SellerId", bmMwsProperty.getSellerId());
                params.put("AWSAccessKeyId", DEV_ACCESS_KEY_ID);
                params.put("MWSAuthToken", bmMwsProperty.getMwsAuthToken());
                signature = createSignature(bmMwsProperty.getCountry(), DEV_SECRET_ACCESS_KEY_ID, params);
	        }else{
	            params.put("SellerId", bmMwsProperty.getSellerId());
	            params.put("AWSAccessKeyId", bmMwsProperty.getAccessKeyId());
	            signature = createSignature(bmMwsProperty.getCountry(), bmMwsProperty.getSecretAccessKey(), params);

	        }

			signature = URLEncoder.encode(signature, DEFAULT_ENCODING);
			params.put("Signature", signature);
		} catch (Exception e) {
			throw new MyException(e);
		}
	}


	/**
	 * 共通のリクエストをセットします。
	 *
	 */
	protected HTTPResponse fetch(HTTPRequest httpRequest, int retryCnt, long sleepMills) {
		HTTPResponse httpResponse = null;
		for(int i = 1; i <= retryCnt; i++) {
			try {
				httpResponse = urlFetchSearvice.fetch(httpRequest);

				String content = new String(httpResponse.getContent(), DEFAULT_ENCODING);
				if(httpResponse.getResponseCode() >= 400){
				    log.info(content);
				}

				// リクエストスロットルの問題以外は返却
				if(content.indexOf("RequestThrottled") == -1 && content.indexOf("Request is throttled") == -1 && content.indexOf("Service temporarily unavailable. Please try again") == -1) {
					return httpResponse;
				} else {
					// リクエストスロットルの場合は、10秒まってリトライする。
					try {
						if(i == 5) {
							throw new MyException("5回IOExceptionでエラーが発生しました。");
						}
						log.info("リクエスト制限にかかりました。");
						log.warning(content);
						Thread.sleep(sleepMills);
					} catch (Exception e) {

					}
				}

			} catch (Exception e) {
				log.warning(i + "回目のエラーが発生しました。");
				log.warning(StackTraceUtil.toString(e));
				try {
					Thread.sleep(1000L * i);
				} catch (InterruptedException e1) {
					throw new MyException(e);
				}
			}
		}

		return httpResponse;
	}

	/**
	 * リクエストのMapにSignatureMeethodを積めてSignatureを返します。
	 *
	 * @return
	 * @throws SignatureException
	 */
	protected String createSignature(Country country, String secretAccessKey, Map<String, String> parameters) throws SignatureException {
		String stringToSign = calculateStringToSignV2(country, parameters);
		String signiture = sign(stringToSign, secretAccessKey);

		return signiture;
	}

	/**
	 *
	 * @param parameters
	 * @return
	 * @throws SignatureException
	 */
	protected String calculateStringToSignV2(Country country, Map<String, String> parameters) throws SignatureException {
		StringBuilder data = new StringBuilder();
		data.append("POST"); // POST
		data.append("\n"); // POST\n
		URI endpoint = null;
		try {
			endpoint = new URI(country.getMwsEndpoint().toLowerCase());
		} catch (URISyntaxException ex) {
			log.warning(StackTraceUtil.toString(ex));
			throw new SignatureException("URI Syntax Exception thrown while constructing string to sign", ex);
		}
		data.append(endpoint.getHost());
		data.append("\n");

		String uri = "/";
		if(StringUtil.isNotEmpty(this.getServicePath())) {
			uri += getServicePath();
		}
		data.append(urlEncode(uri, true));
		data.append("\n");

		// パラメーターをソートする。
		Map<String, String> sorted = new TreeMap<String, String>();
		sorted.putAll(parameters);

		// イテレーターで回してURIにアペンドしていく。
		Iterator<Map.Entry<String, String>> pairs = sorted.entrySet().iterator();
		while(pairs.hasNext()) {
			Map.Entry<String, String> pair = pairs.next();
			// String param = String.format("%s=%s", urlEncode(pair.getKey()), urlEncode(pair.getValue()));
			// log.info(param);
			data.append(urlEncode(pair.getKey()));
			data.append("=");
			data.append(urlEncode(pair.getValue()));
			if(pairs.hasNext()) {
				data.append("&");
			}
		}
		return data.toString();
	}

	/**
	 *
	 * @param value
	 * @param path
	 * @return
	 */
	protected String urlEncode(String value, boolean path) {
		String encoded = null;
		try {
			encoded = URLEncoder.encode(value, DEFAULT_ENCODING).replace("+", "%20").replace("*", "%2A").replace("%7E", "~");
			if(path) {
				encoded = encoded.replace("%2F", "/");
			}
		} catch (UnsupportedEncodingException ex) {
			throw new RuntimeException(ex);
		}
		return encoded;
	}

	/**
	 *
	 * @param rawValue
	 * @return
	 */
	protected String urlEncode(String rawValue) {
		String value = rawValue == null ? "" : rawValue;
		String encoded = null;
		try {
			encoded = URLEncoder.encode(value, DEFAULT_ENCODING).replace("+", "%20").replace("*", "%2A").replace("%7E", "~");
		} catch (UnsupportedEncodingException ex) {
			log.info("Unsupported Encoding Exception");
			throw new RuntimeException(ex);
		}
		return encoded;
	}

	/**
	 *
	 * @param data
	 * @return Signature
	 * @throws SignatureException
	 */
	protected String sign(String data, String secretAccessKey) throws SignatureException {
		String signature;
		try {
			Mac mac = Mac.getInstance(this.getSignitureMethod());
			mac.init(new SecretKeySpec(secretAccessKey.getBytes(), this.getSignitureMethod()));
			signature = Base64Util.encode(mac.doFinal(data.getBytes(DEFAULT_ENCODING)));
		} catch (Exception e) {
			throw new SignatureException("Failed to generate signature: " + e.getMessage(), e);
		}
		return signature;
	}

	/**
	 * Formats date as ISO 8601 timestamp
	 */
	protected String getFormattedTimestamp() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		df.setTimeZone(TimeZone.getTimeZone("UTC"));
		return df.format(DateUtil.getDate());
	}

	/**
	 * レスポンスのMD5をチェックします。
	 *
	 * @param httpResponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	protected boolean checkResponseMD5(HTTPResponse httpResponse) throws UnsupportedEncodingException {
		// log.info("レスポンスのMD5をチェックします。");
		List<HTTPHeader> headers = httpResponse.getHeaders();

		String responseMD5 = null;
		for(HTTPHeader httpHeader : headers) {
			String name = httpHeader.getName();
			String value = httpHeader.getValue();
			// log.info(String.format("%s = %s", name, value));
			if("Content-MD5".equals(name)) {
				// log.info(String.format("Content-MD5:%s", value));
				responseMD5 = value;
			}
		}

		String content = new String(httpResponse.getContent());
		// log.info(content.getBytes().length+"byte");

		String md5 = computeContentMD5Header(new ByteArrayInputStream(httpResponse.getContent()));
		// log.info("CalcMD5:"+md5);
		if(md5 == null || responseMD5 == null) {
			return false;
		}

		boolean isSuccess = responseMD5.equals(md5);

		if(isSuccess) {
			return true;
		} else {
			log.info("MD5チェックサムが一致しませんでした。");

			log.info(content);
			return false;
		}
	}

	/**
	 * ダウンロードされるレポートのMD5チェックサムの計算 Consume the stream and return its Base-64 encoded MD5 checksum.
	 */
	protected String computeContentMD5Header(InputStream inputStream) {
		// Consume the stream to compute the MD5 as a side effect.
		DigestInputStream s;
		try {
			s = new DigestInputStream(inputStream, MessageDigest.getInstance("MD5"));
			// drain the buffer, as the digest is computed as a side-effect
			byte[] buffer = new byte[8192];
			while(s.read(buffer) > 0)
				;
			return new String(org.apache.commons.codec.binary.Base64.encodeBase64(s.getMessageDigest().digest()), "UTF-8");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}


	public <T extends MwsObject> T stringToMwsResponseObject(String respData, Class<T> responseClass) {
		MwsReader reader = new MwsXmlReader(respData);
		T response = MwsUtl.newInstance(responseClass);
		response.readFragmentFrom(reader);

		return response;
	}
}
