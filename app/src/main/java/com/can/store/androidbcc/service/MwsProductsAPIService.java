package com.can.store.androidbcc.service;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.lang.StringUtils;
import org.slim3.datastore.Datastore;
import org.slim3.util.ArraySet;
import org.slim3.util.BeanUtil;
import org.slim3.util.FloatUtil;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import slim3.jackpot.constant.AmazonConst;
import slim3.jackpot.constant.AmazonConst.IdType;
import slim3.jackpot.constant.AmazonConst.MwsApiThrottle;
import slim3.jackpot.controller.Const;
import slim3.jackpot.controller.Const.Country;
import slim3.jackpot.controller.Const.Currency;
import slim3.jackpot.controller.adm.mws.model.ASINIdentifier;
import slim3.jackpot.controller.adm.mws.model.GetCompetitivePricingForASINResponse;
import slim3.jackpot.controller.adm.mws.model.GetCompetitivePricingForASINResult;
import slim3.jackpot.controller.adm.mws.model.GetCompetitivePricingForSKUResponse;
import slim3.jackpot.controller.adm.mws.model.GetCompetitivePricingForSKUResult;
import slim3.jackpot.controller.adm.mws.model.GetLowestOfferListingsForASINResponse;
import slim3.jackpot.controller.adm.mws.model.GetLowestOfferListingsForASINResult;
import slim3.jackpot.controller.adm.mws.model.GetLowestOfferListingsForSKUResponse;
import slim3.jackpot.controller.adm.mws.model.GetLowestOfferListingsForSKUResult;
import slim3.jackpot.controller.adm.mws.model.GetMatchingProductForIdResponse;
import slim3.jackpot.controller.adm.mws.model.GetMatchingProductForIdResult;
import slim3.jackpot.controller.adm.mws.model.GetMatchingProductResponse;
import slim3.jackpot.controller.adm.mws.model.GetMyFeesEstimateResponse;
import slim3.jackpot.controller.adm.mws.model.GetMyPriceForASINResponse;
import slim3.jackpot.controller.adm.mws.model.GetMyPriceForSKUResponse;
import slim3.jackpot.controller.adm.mws.model.GetProductCategoriesForASINResponse;
import slim3.jackpot.controller.adm.mws.model.GetProductCategoriesForSKUResponse;
import slim3.jackpot.controller.adm.mws.model.GetServiceStatusResponse;
import slim3.jackpot.controller.adm.mws.model.IdentifierType;
import slim3.jackpot.controller.adm.mws.model.ListMatchingProductsResponse;
import slim3.jackpot.controller.adm.mws.model.Product;
import slim3.jackpot.controller.adm.mws.model.ProductList;
import slim3.jackpot.controller.adm.mws.model.SalesRankList;
import slim3.jackpot.controller.adm.mws.model.SalesRankType;
import slim3.jackpot.controller.adm.mws.model.SellerSKUIdentifier;
import slim3.jackpot.controller.dto.SalesRankDto;
import slim3.jackpot.dto.amaze.PackageDimensionDto;
import slim3.jackpot.dto.mws.lowest.LowestPriceInfoDto;
import slim3.jackpot.dto.mws.lowest.LowestRequestDto;
import slim3.jackpot.dto.mws.lowest.LowestResponseDto;
import slim3.jackpot.dto.mws.lowest.ProductInfoDto;
import slim3.jackpot.dto.mws.product.ProductResponseDto;
import slim3.jackpot.exception.MyException;
import slim3.jackpot.meta.bm.BmAsinIdRelationMeta;
import slim3.jackpot.model.BmMwsProperty;
import slim3.jackpot.model.BtStockDetail.SalesRoute;
import slim3.jackpot.model.bm.BmAsinIdRelation;
import slim3.jackpot.model.bt.AbstractMwsProductFeedModel;
import slim3.jackpot.model.bt.AbstractStockDetailModel;
import slim3.jackpot.model.bt.BtCanStockDetail;
import slim3.jackpot.model.bt.BtCustomerStockDetail;
import slim3.jackpot.model.cm.CmShipmentProduct;
import slim3.jackpot.model.rm.RmPaApiCache;
import slim3.jackpot.model.tracky.AmazonCatalog;
import slim3.jackpot.service.AmazonProductAdvertisingService;
import slim3.jackpot.service.AmazonProductAdvertisingService.AwsAccount;
import slim3.jackpot.service.CacheService;
import slim3.jackpot.service.CacheService.ExpireKbn;
import slim3.jackpot.util.CollectionUtils;
import slim3.jackpot.util.DateUtil;
import slim3.jackpot.util.IntegerUtil;
import slim3.jackpot.util.MwsKeyUtil;
import slim3.jackpot.util.StackTraceUtil;
import slim3.jackpot.util.StringUtil;

import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;

/**
 * MWSの商品に関するサービスです。
 *
 * 商品APIセクションリファレンス https://images-na.ssl-images-amazon.com/images/G/09/mwsportal /doc/ja_JP/products/MWSProductsApiReference._V378832252_.pdf
 *
 * @author hokuto
 */

public class MwsProductsAPIService extends AbstractMwsService {
	private static final String PRODUCTS_API_SERVICE_VERSION = "2011-10-01";
	private static final int MAX_ERROR_RETRY_COUNT = 3;
	/** memcacheサービス */
	protected CacheService cacheService = new CacheService();
	private AmazonProductAdvertisingService amazonService = new AmazonProductAdvertisingService();

	private static JAXBContext singJaxbContextGetCompetitivePricingForASINResponse = null;
	private static JAXBContext singJaxbContextGetCompetitivePricingForSKUResponse = null;
	private static JAXBContext singJaxbContextGetLowestOfferListingsForASINResponse = null;
	private static JAXBContext singJaxbContextGetLowestOfferListingsForSKUResponse = null;
	private static JAXBContext singJaxbContextGetMatchingProductForIdResponse = null;
	private static JAXBContext singJaxbContextGetMatchingProductResponse = null;
	private static JAXBContext singJaxbContextGetMyPriceForASINResponse = null;
	private static JAXBContext singJaxbContextGetMyPriceForSKUResponse = null;
	private static JAXBContext singJaxbContextGetProductCategoriesForASINResponse = null;
	private static JAXBContext singJaxbContextGetProductCategoriesForSKUResponse = null;
	private static JAXBContext singJaxbContextGetServiceStatusResponse = null;
	private static JAXBContext singJaxbContextListMatchingProductsResponse = null;

	// コンストラクタ
	public MwsProductsAPIService() {
		BmMwsProperty bmMwsProperty = MwsKeyUtil.getBmMwsProperty(Country.JAPAN);
		this.sellerId = bmMwsProperty.getSellerId();
		this.accessKeyId = bmMwsProperty.getAccessKeyId();
		this.secretAccessKey = bmMwsProperty.getSecretAccessKey();
		this.country = bmMwsProperty.getCountry();
		this.marketplaceId = country.getMarketplaceId();
	}

	public MwsProductsAPIService(BmMwsProperty bmMwsProperty) {
		this.sellerId = bmMwsProperty.getSellerId();
		this.accessKeyId = bmMwsProperty.getAccessKeyId();
		this.secretAccessKey = bmMwsProperty.getSecretAccessKey();
		this.country = bmMwsProperty.getCountry();
		this.marketplaceId = bmMwsProperty.getCountry().getMarketplaceId();
        if(StringUtil.isNotEmpty(bmMwsProperty.getMwsAuthToken())){
            this.mwsAuthToken = bmMwsProperty.getMwsAuthToken();
            this.accessKeyId = DEV_ACCESS_KEY_ID;
            this.secretAccessKey = DEV_SECRET_ACCESS_KEY_ID;
            this.country = Country.JAPAN;
            this.marketplaceId = Country.JAPAN.getMarketplaceId();
        }
	}

	public MwsProductsAPIService(String sellerId, String accessKeyId, String secretAccessKey, Country country) {
		this.sellerId = sellerId;
		this.accessKeyId = accessKeyId;
		this.secretAccessKey = secretAccessKey;
		this.country = country;
		this.marketplaceId = country.getMarketplaceId();
	}

    public MwsProductsAPIService(String authToken, String sellerId) {
        this.sellerId = sellerId;
        this.accessKeyId = DEV_ACCESS_KEY_ID;
        this.secretAccessKey = DEV_SECRET_ACCESS_KEY_ID;
        this.country = Country.JAPAN;
        this.marketplaceId = Country.JAPAN.getMarketplaceId();
        this.mwsAuthToken = authToken;
    }

	/**
	 *
	 * @return
	 * @throws JAXBException
	 */
	private static JAXBContext getJAXBContext(Class<?> clazz) throws JAXBException {

		if(clazz.equals(GetCompetitivePricingForASINResponse.class)) {
			if(singJaxbContextGetCompetitivePricingForASINResponse == null) {
				singJaxbContextGetCompetitivePricingForASINResponse = JAXBContext.newInstance(clazz);
			}
			return singJaxbContextGetCompetitivePricingForASINResponse;

		} else if(clazz.equals(GetCompetitivePricingForSKUResponse.class)) {
			if(singJaxbContextGetCompetitivePricingForSKUResponse == null) {
				singJaxbContextGetCompetitivePricingForSKUResponse = JAXBContext.newInstance(clazz);
			}
			return singJaxbContextGetCompetitivePricingForSKUResponse;

		} else if(clazz.equals(GetLowestOfferListingsForASINResponse.class)) {
			if(singJaxbContextGetLowestOfferListingsForASINResponse == null) {
				singJaxbContextGetLowestOfferListingsForASINResponse = JAXBContext.newInstance(clazz);
			}
			return singJaxbContextGetLowestOfferListingsForASINResponse;

		} else if(clazz.equals(GetLowestOfferListingsForSKUResponse.class)) {
			if(singJaxbContextGetLowestOfferListingsForSKUResponse == null) {
				singJaxbContextGetLowestOfferListingsForSKUResponse = JAXBContext.newInstance(clazz);
			}
			return singJaxbContextGetLowestOfferListingsForSKUResponse;
		} else if(clazz.equals(GetMatchingProductForIdResponse.class)) {

			if(singJaxbContextGetMatchingProductForIdResponse == null) {
			    long start = System.currentTimeMillis();
				singJaxbContextGetMatchingProductForIdResponse = JAXBContext.newInstance(clazz);
				long end = System.currentTimeMillis();
				log.info("singJaxbContextGetMatchingProductForIdResponse newInstance " + (end - start)  + "ms");
			}
			return singJaxbContextGetMatchingProductForIdResponse;

		} else if(clazz.equals(GetMatchingProductResponse.class)) {
			if(singJaxbContextGetMatchingProductResponse == null) {
				singJaxbContextGetMatchingProductResponse = JAXBContext.newInstance(clazz);
			}
			return singJaxbContextGetMatchingProductResponse;

		} else if(clazz.equals(GetMyPriceForASINResponse.class)) {
			if(singJaxbContextGetMyPriceForASINResponse == null) {
				singJaxbContextGetMyPriceForASINResponse = JAXBContext.newInstance(clazz);
			}
			return singJaxbContextGetMyPriceForASINResponse;

		} else if(clazz.equals(GetMyPriceForSKUResponse.class)) {
			if(singJaxbContextGetMyPriceForSKUResponse == null) {
				singJaxbContextGetMyPriceForSKUResponse = JAXBContext.newInstance(clazz);
			}
			return singJaxbContextGetMyPriceForSKUResponse;

		} else if(clazz.equals(GetProductCategoriesForASINResponse.class)) {
			if(singJaxbContextGetProductCategoriesForASINResponse == null) {
				singJaxbContextGetProductCategoriesForASINResponse = JAXBContext.newInstance(clazz);
			}
			return singJaxbContextGetProductCategoriesForASINResponse;

		} else if(clazz.equals(GetProductCategoriesForSKUResponse.class)) {
			if(singJaxbContextGetProductCategoriesForSKUResponse == null) {
				singJaxbContextGetProductCategoriesForSKUResponse = JAXBContext.newInstance(clazz);
			}
			return singJaxbContextGetProductCategoriesForSKUResponse;

		} else if(clazz.equals(GetServiceStatusResponse.class)) {
			if(singJaxbContextGetServiceStatusResponse == null) {
				singJaxbContextGetServiceStatusResponse = JAXBContext.newInstance(clazz);
			}
			return singJaxbContextGetServiceStatusResponse;
		} else if(clazz.equals(ListMatchingProductsResponse.class)) {
			if(singJaxbContextListMatchingProductsResponse == null) {
				singJaxbContextListMatchingProductsResponse = JAXBContext.newInstance(clazz);
			}
			return singJaxbContextListMatchingProductsResponse;
		}

		throw new MyException("存在しませんでした。" + clazz.getSimpleName());
	}

	@Override
	protected String getServiceVersion() {
		return PRODUCTS_API_SERVICE_VERSION;
	}

	@Override
	protected String getServicePath() {
		return "Products/" + PRODUCTS_API_SERVICE_VERSION;
	}

	/**
	 * 指定した検索クエリーに応じた商品およびその属性のリストを返します。
	 *
	 * ListMatchingProductsオペレーションは、指定した検索クエリーに応じた商品およびその属性のリストを関連性の高い順に返します。 検索クエリーには、商品を表す文字列、またはGCID、UPC、EAN、ISBN、JANなどの商品IDの指定が可能です。 商品に関連付けられたASINが明らかな場合、GetMatchingProductオペレーションを使用します。 商品IDとしてSellerSKUは指定できません 。条件に一致した商品がない場合、キーワードのスペル訂正またはキーワードを減らすことでクエリーの検索対象が拡大されます。
	 * 最大で10商品を返し、購入不可商品は含まれません。
	 *
	 * @param marketplaceId
	 *            必須
	 * @param query
	 *            必須
	 * @param queryContextId
	 *            指定した検索が実行されるコンテキストのID。 マーケットプレイスによっては、制約を加えて商品の一部を検索する仕組みが提供されていることがある。 例えばAmazonマーケットプレイスでは 、特定のカテゴリーに絞ったクエリーが可能で、QueryContextIdパラメーターによりそれを指定する。 省略した場合、そのマーケットプレイスのデフォルトのコンテキスト(通常、最大の商品一式が含まれる)を用いて検索を行う。 Amazonの各マーケットプレイスで使用可能なQueryContextId値の一覧はQueryContextId Valuesを参照。
	 */
	public ListMatchingProductsResponse listMatchingProducts(String query, String queryContextId) {
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("Action", "ListMatchingProducts");

			if(StringUtil.isNotEmpty(query)) {
				params.put("Query", query);
			}
			if(StringUtil.isNotEmpty(queryContextId)) {
				params.put("QueryContextId", queryContextId);
			}

			// 共通パラメータを追加。
			setParameter(params);

			// log.info("ListMatchingProductsのリクエストを生成します。");
			HTTPRequest httpRequest = super.createHttpRequest(params);

			// log.info("リクエストを送信します。");
			// log.info(new String(httpRequest.getPayload()));
			HTTPResponse httpResponse = fetch(httpRequest);

			checkHttpStatus(httpResponse);

			String content = new String(httpResponse.getContent(), DEFAULT_ENCODING);

			Unmarshaller unmarshaller = getJAXBContext(ListMatchingProductsResponse.class).createUnmarshaller();
			return (ListMatchingProductsResponse) unmarshaller.unmarshal(new StringReader(content));

		} catch (Exception e) {
			throw new MyException(e);
		}
	}

	/**
	 * 指定したASIN のリストに応じた商品およびその属性を返します。
	 *
	 * GetMatchingProductオペレーションは、指定したASINのリストに応じた商品およびその属性を返し、最高で10の商品を返します。 重要: GetMatchingProductオペレーションのすべての機能は、 新しいGetMatchingProductForIdオペレーションにあります。 GetMatchingProductオペレーションは、互換性のために商品APIセクションに含まれていますが、 GetMatchingProductの代わりに可能な限りGetMatchingProductForIdオペレーションを使用してください。
	 * 詳しくは、GetMatchingProductForIdを参照してください。
	 *
	 * @param marketplaceId
	 * @param asinList
	 */
	public GetMatchingProductResponse getMatchingProduct(AmazonConst.IdType idType, List<String> asinList) {
		String content = null;
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("Action", "GetMatchingProduct");

			if(idType != null) {
				params.put("IdType", idType.toString());
			}
			if(CollectionUtils.isNotEmpty(asinList)) {
				int cnt = 1;
				for(String asin : asinList) {
					params.put(String.format("ASINList.ASIN.%s", cnt), asin);
					cnt++;
				}
			}
			// 共通パラメータを追加。
			setParameter(params);

			// log.info("GetMatchingProductのリクエストを生成します。");
			HTTPRequest httpRequest = super.createHttpRequest(params);

			// log.info("リクエストを送信します。");
			HTTPResponse httpResponse = fetch(httpRequest);

			checkHttpStatus(httpResponse);

			content = new String(httpResponse.getContent(), DEFAULT_ENCODING);

			Unmarshaller unmarshaller = getJAXBContext(GetMatchingProductResponse.class).createUnmarshaller();
			return (GetMatchingProductResponse) unmarshaller.unmarshal(new StringReader(content));

		} catch (Exception e) {
			if(e instanceof ClassCastException) {
				log.warning("GetMatchingProductResponseへのキャストに失敗しました。\r\n" + content);
				throw new MyException("GetMatchingProductResponseへのキャストに失敗しました。\r\n" + content);
			}
			throw new MyException(e);
		}
	}

	/**
	 * 商品情報を取得します。ASINがあればASINで検索し、なければSKUで検索します。 呼び出し元が、20件ごとリクエストを送るため、5件ごとに分割してAPIを実行します。
	 *
	 * @param idType
	 * @param btCanStockDetailList
	 * @return
	 */
	public <T extends AbstractStockDetailModel> List<GetMatchingProductForIdResult> getMatchingProductForIdByBtCanStockDetail(List<T> btCanStockDetailList) {
		List<GetMatchingProductForIdResult> retList = new ArrayList<>();
		// ASINの5セットのリストに分割
		List<List<T>> asinListList = convertAsinListList(btCanStockDetailList, AmazonConst.MwsApiThrottle.GetMatchingProductForId.getBulkSize());
		// ASINがない場合はSKUを5セットのリストに分割
		List<List<String>> skuListList = convertSkuListList(btCanStockDetailList, AmazonConst.MwsApiThrottle.GetMatchingProductForId.getBulkSize(), false);

		// =========================
		// ASINをもとに実行
		for(List<T> list : asinListList) {
			if(CollectionUtils.isNotEmpty(list) && list.size() > 0) {
				GetMatchingProductForIdResponse ret = getMatchingProductForId(AmazonConst.IdType.ASIN, convertAsinList(list, false), true);
				List<GetMatchingProductForIdResult> getMatchingProductForIdResult = ret.getGetMatchingProductForIdResult();
				// log.info(String.format("ASIN検索結果[%d件]", getMatchingProductForIdResult.size()));
				// SKUをセットする
				for(GetMatchingProductForIdResult t : getMatchingProductForIdResult) {
					for(T y : list) {
						if(y.getAsin().equals(t.getId())) {
							// 存在しないASINで検索すると、NULLを返す。
							t.setSku(y.getSku());
							break;
						}
					}
				}
				if(ret.isSetGetMatchingProductForIdResult()) {
					retList.addAll(ret.getGetMatchingProductForIdResult());
				}
			}
		}

		// =========================
		// SKUをもとに実行
		for(List<String> list : skuListList) {
			if(CollectionUtils.isNotEmpty(list) && list.size() > 0) {
				// log.info(String.format("SKUで検索します。[%s]", list.toString()));
				GetMatchingProductForIdResponse ret = getMatchingProductForId(AmazonConst.IdType.SellerSKU, list, false);
				// log.info(String.format("SKU検索結果[%d件]", ret.getGetMatchingProductForIdResult().size()));

				if(ret.isSetGetMatchingProductForIdResult()) {
					List<GetMatchingProductForIdResult> tmpList = ret.getGetMatchingProductForIdResult();
					for(GetMatchingProductForIdResult t : tmpList) {
						t.setSku(t.getId());
					}
					retList.addAll(ret.getGetMatchingProductForIdResult());
				}
				try {
					// SKUの場合はセラーを切り替えれないため1秒Sleepする
					sleep(1000L);
				} catch (Exception e) {
					log.warning(StackTraceUtil.toString(e));
				}
			}
		}

		return retList;
	}

	/**
	 * 商品情報を取得します。ASINがあればASINで検索し、なければSKUで検索します。 呼び出し元が、20件ごとリクエストを送るため、5件ごとに分割してAPIを実行します。
	 *
	 * @param idType
	 * @param btCanStockDetailList
	 * @return
	 */
	public List<GetMatchingProductForIdResult> getMatchingProductForIdAsin(List<String> asinList) {

		List<GetMatchingProductForIdResult> retList = new ArrayList<>();
		for(int i = 0; i * 5 < asinList.size(); i++) {
			int startIndex = i * 5;
			int lastIndex = (i + 1) * 5 > asinList.size() ? asinList.size() : (i + 1) * 5;
			List<String> asinSubList = asinList.subList(startIndex, lastIndex);

			try {
				GetMatchingProductForIdResponse ret = getMatchingProductForId(AmazonConst.IdType.ASIN, asinSubList, true);
				if(ret.isSetGetMatchingProductForIdResult()) {
					retList.addAll(ret.getGetMatchingProductForIdResult());
				}
			} catch (Exception e) {
				// エラーが出たAsinを1つずつ単独で実行する（５つのうちエラーが出たもの以外を拾う）
				for(int j = 0; j < asinSubList.size(); j++) {
					try {
						GetMatchingProductForIdResponse ret = getMatchingProductForId(AmazonConst.IdType.ASIN, Arrays.asList(asinSubList.get(j)), true);
						if(ret.isSetGetMatchingProductForIdResult()) {
							retList.addAll(ret.getGetMatchingProductForIdResult());
						}
					} catch (Exception e2) {
						// 何もしない
					}
				}

			}
		}

		return retList;
	}

	/**
	 * ASIN、GCID、SellerSKU、UPC、EAN、ISBN、JAN の値のリストに応じた商品およびその属性を返します。 GetMatchingProductForIdオペレーションは、指定された商品IDのリストに基づき、商品一覧とその属性を返します。 商品IDとは、ASIN、GCID、SellerSKU、UPC、EAN、ISBN、JANになります。 最大5件
	 *
	 * @param marketplaceId
	 * @param idType
	 * @param idList
	 * @return
	 */
	public GetMatchingProductForIdResponse getMatchingProductForId(AmazonConst.IdType idType, List<String> idList, boolean random) {
		for(int i = 0; i < 3; i++) {

			try {
				Map<String, String> params = new HashMap<String, String>();
				params.put("Action", "GetMatchingProductForId");

				if(idType != null) {
					params.put("IdType", idType.toString());
				}
				if(CollectionUtils.isNotEmpty(idList)) {
					int cnt = 1;
					for(String id : idList) {
						params.put(String.format("IdList.Id.%s", cnt), id);
						cnt++;
					}
				}
				// 共通パラメータを追加。
				if(idType.equals(AmazonConst.IdType.SellerSKU) || !random) {
					setParameter(params);
				} else {
					// APIキーをランダムに取得
					setParameterForRandom(country, params);
				}

				// log.info("GetMatchingProductForIdのリクエストを生成します。");
				HTTPRequest httpRequest = super.createHttpRequest(params);

				// log.info("リクエストを送信します。");
				HTTPResponse httpResponse = fetch(httpRequest);

				checkHttpStatus(httpResponse);

				String content = new String(httpResponse.getContent(), DEFAULT_ENCODING);

				Unmarshaller unmarshaller = getJAXBContext(GetMatchingProductForIdResponse.class).createUnmarshaller();
				GetMatchingProductForIdResponse result = (GetMatchingProductForIdResponse) unmarshaller.unmarshal(new StringReader(content));

				for(GetMatchingProductForIdResult t : result.getGetMatchingProductForIdResult()) {
					if(t.isSetError()) {
						String msg = t.getError().getMessage();
						// JANの不正は入力フォームをそのまま実行してるため定常的に発生するためWARNは出さない。
						if(!msg.startsWith("Invalid JAN identifier") || !msg.startsWith("Invalid ASIN identifier")) {
							log.info(String.format("product unknown error[%s][%s][%s]", this.accessKeyId, idList.toString(), msg));
						}
						if(idType.equals(IdType.ASIN)) {
							t.setAsin(t.getId());
						}
					}
					if(!t.isSetError() && t.isSetProducts()) {
						List<Product> productList = t.getProducts().getProduct();
						// ASINをセット
						for(Product product : productList) {
							t.setAsin(product.bccGetAsin());
						}
						// IdTypeに応じて各フィールドに詰める。
						if(idType.equals(IdType.SellerSKU)) {
							t.setSku(t.getId());
						} else if(idType.equals(IdType.JAN)) {
							t.setJan(t.getId());
						} else if(idType.equals(IdType.EAN)) {
							t.setEan(t.getId());
						} else if(idType.equals(IdType.ISBN)) {
							t.setIsbn(t.getId());
						}
					}
				}

				return result;

			} catch (Exception e) {
				log.warning(i + "失敗しました。\r\n" + StackTraceUtil.toString(e));

			}
		}
		throw new MyException("3回APIの実行に失敗しました。");
	}

	/**
	 * ASIN、GCID、SellerSKU、UPC、EAN、ISBN、JAN の値のリストに応じた商品およびその属性を返します。 GetMatchingProductForIdオペレーションは、指定された商品IDのリストに基づき、商品一覧とその属性を返します。 商品IDとは、ASIN、GCID、SellerSKU、UPC、EAN、ISBN、JANになります。 最大5件
	 *
	 * @param marketplaceId
	 * @param idType
	 * @param idList
	 * @return
	 */
	public Map<Country, GetMatchingProductForIdResponse> getMatchingProductForIdAsyncCountries(AmazonConst.IdType idType, List<String> idList, List<Country> allCountryList) {
		Map<Country, GetMatchingProductForIdResponse> countryResponseMap = new HashMap<Country, GetMatchingProductForIdResponse>();
		try {
			// **************************************************
			// URLフェッチ（非同期）に変更
			// **************************************************
			URLFetchService urlFetchSearvice = URLFetchServiceFactory.getURLFetchService();
			List<Future<HTTPResponse>> futures = new ArrayList<Future<HTTPResponse>>();

			for(Country country : allCountryList) {
				this.country = country;
				Map<String, String> params = new HashMap<String, String>();
				params.put("Action", "GetMatchingProductForId");
				if(idType != null) {
					params.put("IdType", idType.toString());
				}
				if(CollectionUtils.isNotEmpty(idList)) {
					int cnt = 1;
					for(String id : idList) {
						params.put(String.format("IdList.Id.%s", cnt), id);
						cnt++;
					}
				}
				// params.put("MarketplaceId",
				// bmMwsProperty.getCountry().getMarketplaceId());
				// 共通パラメータを追加。
				setParameterForRandom(country, params);
				// setParameter(params);
				// setParameter(
				// params,
				// bmMwsProperty.getCountry().getMarketplaceId(),
				// bmMwsProperty.getAccessKeyId(),
				// bmMwsProperty.getSellerId(),
				// bmMwsProperty.getSecretAccessKey(),
				// bmMwsProperty.getCountry().getProductServiceUrl()
				// );

				// log.info("GetMatchingProductForIdのリクエストを生成します。");

				HTTPRequest httpRequest = super.createHttpRequest(country, params);

				futures.add(urlFetchSearvice.fetchAsync(httpRequest));
			}
			// レスポンスをリストに追加
			for(int i = 0; i < futures.size(); i++) {

				int status = -1;
				boolean shouldRetry = true;
				int retries = 0;
				do {
					try {
						Future<HTTPResponse> future = futures.get(i);
						HTTPResponse httpResponse = future.get();
						status = httpResponse.getResponseCode();
						log.info("HTTP STATUS=" + status);
						if(status == HttpStatus.SC_OK) {
							shouldRetry = false;

							Unmarshaller unmarshaller = getJAXBContext(GetMatchingProductForIdResponse.class).createUnmarshaller();
							countryResponseMap.put(allCountryList.get(i), (GetMatchingProductForIdResponse) unmarshaller.unmarshal(new StringReader(new String(httpResponse.getContent(), DEFAULT_ENCODING))));
						} else {
							log.warning(String.format("APIの実行に失敗しました。[%s][%s]", this.accessKeyId, this.shopName));
							log.warning(new String(httpResponse.getContent(), DEFAULT_ENCODING));
							log.warning("Country=" + allCountryList.get(i).getName());
							if((status == HttpStatus.SC_INTERNAL_SERVER_ERROR && pauseIfRetryNeeded(++retries))) {
								shouldRetry = true;
							} else {
								shouldRetry = false;
								// throw new MyException(new
								// String(httpResponse.getContent(),
								// DEFAULT_ENCODING));
							}
						}
					} catch (IOException e) {
						log.warning(StackTraceUtil.toString(e));
					}
				} while(shouldRetry);
			}
			return countryResponseMap;

		} catch (Exception e) {
			throw new MyException(e);
		}

	}

	/**
	 * 非同期でマルチスレッドでリクエストを行います。 ASIN以外の場合はテストしてない。
	 *
	 * @param btCanStockDetailList
	 * @return
	 */
	public <T extends AbstractStockDetailModel> List<GetMatchingProductForIdResult> getMatchingProductForIdByBtCanStockDetailByMulti(BmMwsProperty mwsProperty, List<T> btCanStockDetailList) {
		boolean isFirstError = true;
		List<GetMatchingProductForIdResult> matchingProductForIdResponse = new ArrayList<>();

		try {
			URLFetchService urlFetchSearvice = URLFetchServiceFactory.getURLFetchService();
			List<Future<HTTPResponse>> futures = new ArrayList<Future<HTTPResponse>>();

			// ASINの5セットのリストに分割
			List<List<T>> asinListList = convertAsinListList(btCanStockDetailList, AmazonConst.MwsApiThrottle.GetMatchingProductForId.getBulkSize());
			// ASINがない場合はSKUを5セットのリストに分割
			List<List<String>> skuListList = convertSkuListList(btCanStockDetailList, AmazonConst.MwsApiThrottle.GetMatchingProductForId.getBulkSize(), false);

			StringBuffer errorLog = new StringBuffer();
			// =========================
			// ASINをFutureに追加
			for(List<T> list : asinListList) {
				if(CollectionUtils.isNotEmpty(list) && list.size() > 0) {

					Map<String, String> params = new HashMap<String, String>();
					params.put("Action", "GetMatchingProductForId");
					params.put("IdType", IdType.ASIN.toString());
					int cnt = 1;
					for(T dto : list) {
						params.put(String.format("IdList.Id.%s", cnt), dto.getAsin());
						errorLog.append(dto.getAsin() + ",");
						cnt++;
					}
					setParameterForRandom(Country.JAPAN, params);
					HTTPRequest httpRequest = super.createHttpRequest(country, params);
					futures.add(urlFetchSearvice.fetchAsync(httpRequest));
				}
			}

			// =========================
			// SKUをもとにFutureに追加
			for(List<String> list : skuListList) {
				if(CollectionUtils.isNotEmpty(list) && list.size() > 0) {

					Map<String, String> params = new HashMap<String, String>();
					params.put("Action", "GetMatchingProductForId");
					params.put("IdType", AmazonConst.IdType.SellerSKU.toString());
					int cnt = 1;
					for(String str : list) {
						params.put(String.format("IdList.Id.%s", cnt), str);
						errorLog.append(str + ",");
						cnt++;
					}
					setParameter(params);
					HTTPRequest httpRequest = super.createHttpRequest(country, params);
					futures.add(urlFetchSearvice.fetchAsync(httpRequest));
				}
			}

			// ======================
			// レスポンスをリストに追加
			for(int i = 0; i < futures.size(); i++) {
				int status = -1;
				boolean shouldRetry = true;
				int retries = 0;
				HTTPResponse httpResponse = null;
				do {
					try {
						Future<HTTPResponse> future = futures.get(i);

						for(int j = 0; j < 3; j++) {
							try {
								httpResponse = future.get();
								break;

							} catch (Exception e) {
								try {
									Thread.sleep(5000L);
								} catch (Exception e2) {
									e2.printStackTrace();
									log.warning("futureでエラーになりました。リトライします。");
								}
							}
						}

						// Success
						if(httpResponse != null && httpResponse.getResponseCode() == HttpStatus.SC_OK) {
							shouldRetry = false;

							Unmarshaller unmarshaller = getJAXBContext(GetMatchingProductForIdResponse.class).createUnmarshaller();
							GetMatchingProductForIdResponse tmp = (GetMatchingProductForIdResponse) unmarshaller.unmarshal(new StringReader(new String(httpResponse.getContent(), DEFAULT_ENCODING)));

							if(i == 0) {
								matchingProductForIdResponse = tmp.getGetMatchingProductForIdResult();
							} else {
								matchingProductForIdResponse.addAll(tmp.getGetMatchingProductForIdResult());
							}
						} else {
							log.warning(String.format("APIの実行に失敗しました。[%d][%s][%s]", status, this.accessKeyId, this.shopName));
							log.warning(new String(httpResponse.getContent(), DEFAULT_ENCODING));
							if((status == HttpStatus.SC_INTERNAL_SERVER_ERROR && pauseIfRetryNeeded(++retries))) {
								shouldRetry = true;
							} else {
								shouldRetry = false;
							}
						}
					} catch (IOException e) {
						if(httpResponse != null) {
							log.warning(new String(httpResponse.getContent(), DEFAULT_ENCODING));
						}
						log.warning(StackTraceUtil.toString(e));
						if(isFirstError) {
							log.warning(e.getClass().getSimpleName() + "のエラーが発生しました。\r\n商品コード\r\n" + errorLog.toString());
							isFirstError = false;
						}
						sleep(2000L);
					} catch (Exception e) {
						log.warning(StackTraceUtil.toString(e));
						if(isFirstError) {
							log.warning(e.getClass().getSimpleName() + "のエラーが発生しました。\r\n商品コード\r\n" + errorLog.toString());
							isFirstError = false;
						}
						sleep(2000L);

					}
				} while(shouldRetry);
			}

			for(GetMatchingProductForIdResult t : matchingProductForIdResponse) {
				if(t.isSetError()) {
					String msg = t.getError().getMessage();
					if(!msg.startsWith("Invalid ASIN identifier")) {
						log.warning(String.format("response error[%s][%s]", this.accessKeyId, msg));
					}
				}
				if(!t.isSetError() && t.isSetProducts()) {
					List<Product> productList = t.getProducts().getProduct();
					// ASINと定価をセット
					for(Product product : productList) {
						t.setAsin(product.bccGetAsin());
						t.setListPrice(product.bccGetListPrice());
					}

					String idType = t.getIdType();
					// IdTypeに応じて各フィールドに詰める。
					if(idType.equals(IdType.SellerSKU.toString())) {
						t.setSku(t.getId());
					} else if(idType.equals(IdType.JAN.toString())) {
						t.setJan(t.getId());
					} else if(idType.equals(IdType.EAN.toString())) {
						t.setEan(t.getId());
					} else if(idType.equals(IdType.ISBN.toString())) {
						t.setIsbn(t.getId());
					}
				}
			}

			return matchingProductForIdResponse;

		} catch (Exception e) {
			throw new MyException(e);
		}

	}

	private void sleep(Long millis) {
		try {
			Thread.sleep(millis);
		} catch (Exception e) {
		}
	}

	/**
	 * 商品情報/国ごとの最低価格情報を取得します。(AWS用)
	 *
	 * @param lowestRequestDto
	 *            商品情報/国ごとの最低価格情報リクエストDto
	 * @return 商品情報/国ごとの最低価格情報
	 */
	public List<ProductResponseDto> getLowestOfferListingsForAws(LowestRequestDto lowestRequestDto) {
		Date takeInDate = DateUtil.getDate();

		if(!AmazonConst.IdType.ASIN.equals(lowestRequestDto.getIdType())) {
			// ASIN以外はサーチインデックスの指定が必要 未対応です。
			throw new MyException("ASIN以外のID指定は未対応です。");
		}

		List<ProductResponseDto> productResponseDtoList = new ArrayList<ProductResponseDto>();
		String asinJoined = StringUtils.join(lowestRequestDto.getIdList(), ',');

		// 使用するアカウント
		HashMap<String, String> mapForItemLookup = new HashMap<String, String>();
		amazonService.initItemLookupQueryMapExAccountInfo(mapForItemLookup);
		AwsAccount useAwsAccount = AwsAccount.AF1;
		mapForItemLookup.put("AssociateTag", useAwsAccount.getAssociateTag());

		try {

			// **************************************************
			// 商品情報,ランキング情報,価格情報取得（複数の国で取得）
			// **************************************************
			ArrayList<HashMap<String, String>> requestOfferSummaryList = new ArrayList<HashMap<String, String>>();
			for(Country country : lowestRequestDto.getCountryList()) {
				HashMap<String, String> mapOfferSummary = new HashMap<String, String>();
				mapOfferSummary.putAll(mapForItemLookup);
				mapOfferSummary.put("ResponseGroup", "ItemAttributes,SalesRank,OfferSummary,Images");
				mapOfferSummary.put("ItemId", asinJoined);
				mapOfferSummary.put("Country", country.name());
				requestOfferSummaryList.add(mapOfferSummary);
			}
			// 検索
			HashMap<Country, String> responseOfferSummaryMap = amazonService.awsRequestAsync(requestOfferSummaryList, useAwsAccount);
			Iterator<Map.Entry<Country, String>> itOfferSummary = responseOfferSummaryMap.entrySet().iterator();

			List<RmPaApiCache> responseOfferSummaryList = new ArrayList<RmPaApiCache>();
			while(itOfferSummary.hasNext()) {
				Map.Entry<Country, String> countryResponse = itOfferSummary.next();
				List<RmPaApiCache> awsDtoList = amazonService.parseAwsXml(countryResponse.getValue());
				for(RmPaApiCache awsDto : awsDtoList) {
					awsDto.setCountry(countryResponse.getKey());
					responseOfferSummaryList.add(awsDto);
				}
			}

			Map<String, Country> asinProductInfoMap = new HashMap<String, Country>();
			for(RmPaApiCache responseOfferSummary : responseOfferSummaryList) {
				// 商品情報
				if(responseOfferSummary.getCountry() == Country.JAPAN) {
					ProductResponseDto productResponseDto = new ProductResponseDto();
					productResponseDto.setAsin(responseOfferSummary.getAsin());
					productResponseDto.setTitle(responseOfferSummary.getTitle());
					productResponseDto.setSmallImage(responseOfferSummary.getSamllImageUrl());
					productResponseDto.setReleaseDate(responseOfferSummary.getReleaseDate());
					productResponseDto.setProductGroup(responseOfferSummary.getProductGroup());
					productResponseDto.setPackageDimensionDto(responseOfferSummary.getPackageDimensionDto());
					productResponseDtoList.add(productResponseDto);
					asinProductInfoMap.put(responseOfferSummary.getAsin(), responseOfferSummary.getCountry());
				}
			}
			for(RmPaApiCache responseOfferSummary : responseOfferSummaryList) {
				// 商品情報（日本で取れなかった場合）
				if(responseOfferSummary.getCountry() != Country.JAPAN && !asinProductInfoMap.containsKey(responseOfferSummary.getAsin())) {
					ProductResponseDto productResponseDto = new ProductResponseDto();
					productResponseDto.setAsin(responseOfferSummary.getAsin());
					productResponseDto.setTitle(responseOfferSummary.getTitle());
					productResponseDto.setSmallImage(responseOfferSummary.getSamllImageUrl());
					productResponseDto.setReleaseDate(responseOfferSummary.getReleaseDate());
					productResponseDto.setProductGroup(responseOfferSummary.getProductGroup());
					productResponseDto.setPackageDimensionDto(responseOfferSummary.getPackageDimensionDto());
					productResponseDtoList.add(productResponseDto);
					asinProductInfoMap.put(responseOfferSummary.getAsin(), responseOfferSummary.getCountry());
				}
			}

			for(ProductResponseDto productResponseDto : productResponseDtoList) {
				// ランキング情報,価格情報取得
				List<LowestResponseDto> countryDtoList = new ArrayList<LowestResponseDto>();
				for(RmPaApiCache awsDto : responseOfferSummaryList) {
					if(awsDto.getAsin().equals(productResponseDto.getAsin())) {
						LowestResponseDto lowestResponseDto = new LowestResponseDto();
						lowestResponseDto.setAsin(awsDto.getAsin());
						lowestResponseDto.setCountry(awsDto.getCountry());
						lowestResponseDto.setCreateDate(takeInDate);
						lowestResponseDto.setLowestNewPrice(awsDto.getLowestNewPriceMoneyType());
						lowestResponseDto.setLowestUsedPrice(awsDto.getLowestUsedPriceMoneyType());
						SalesRankDto salesRankDto = new SalesRankDto();
						salesRankDto.setCategoryName("");
						salesRankDto.setRank(awsDto.getSalesRank());
						lowestResponseDto.setSalesRankDto(salesRankDto);
						countryDtoList.add(lowestResponseDto);
					}
				}
				productResponseDto.setCountryDtoList(countryDtoList);
			}

		} catch (Exception e) {
			log.warning(e.getMessage());
			writeSellerLog();
			throw new MyException("商品名が取得できませんでした。", e);
		}

		return productResponseDtoList;
	}

	/**
	 * 商品情報/国ごとの最低価格情報を取得します。
	 *
	 * @param lowestRequestDto
	 *            商品情報/国ごとの最低価格情報リクエストDto
	 * @return 商品情報/国ごとの最低価格情報
	 */
	public List<ProductResponseDto> getLowestOfferListings(LowestRequestDto lowestRequestDto) {
		Date takeInDate = DateUtil.getDate();

		// **************************************************
		// 商品情報取得
		// **************************************************
		ArrayList<String> allAsinList = new ArrayList<String>(); // 引数のIDに紐づくすべてのASIN
		ArrayList<String> cacheProductExistsAsinList = new ArrayList<String>(); // キャッシュに商品情報が存在するリスト（ここにはASINを入れます。）
		ArrayList<String> cacheProductNotExistsList = new ArrayList<String>(); // キャッシュに商品情報が存在しないリスト（ここにはパラメータのIdTypeのまま入ります。）
		for(String id : lowestRequestDto.getIdList()) {
			ArrayList<String> checkCacheAsinList = new ArrayList<String>(); // キャッシュに商品情報が存在するかチェック対象のリスト
			if(lowestRequestDto.getIdType() != AmazonConst.IdType.ASIN) {
				// ASIN以外の場合に、関連するASINをひっぱってくる（DBから）
				List<String> relAsinList = getAsinList(lowestRequestDto.getIdType(), id);
				if(relAsinList == null) {
					// BmAsinIdRelationに存在しないIDはキャッシュにもないはず
					cacheProductNotExistsList.add(id);
					continue;
				} else {
					checkCacheAsinList.addAll(relAsinList); // DBからヒモ付がとれても、キャッシュが消えているかもしれないので、チェック対象に追加
				}
			} else {
				checkCacheAsinList.add(id);
			}
			boolean isExistsCache = true;
			for(String asin : checkCacheAsinList) {
				// キャッシュに存在するか調べる
				for(Country country : lowestRequestDto.getCountryList()) {
					String productCacheName = CacheService.MWS_PRODUCT_COUNTRIES_CACHE + asin + country.toString();
					// cacheService.delete(productCacheName);
					if(!cacheService.exist(productCacheName)) {
						// 何れかの国が無ければMWS取得対象
						isExistsCache = false;
						break;
					}
				}
			}
			if(isExistsCache) {
				log.info("id:" + id + " の商品情報は、キャッシュから取得します。");
				cacheProductExistsAsinList.addAll(checkCacheAsinList);
			} else {
				// キャッシュに商品情報が存在しないリストにidを入れる
				cacheProductNotExistsList.add(id);
			}
		}

		List<ProductInfoDto> productInfoDtoList = new ArrayList<ProductInfoDto>();
		// キャッシュから商品情報を取得
		for(String asin : cacheProductExistsAsinList) {
			for(Country country : lowestRequestDto.getCountryList()) {
				String productCacheName = CacheService.MWS_PRODUCT_COUNTRIES_CACHE + asin + country.toString();
				productInfoDtoList.add(cacheService.getSingle(productCacheName, ProductInfoDto.class));
			}
		}

		// MWSサービスから商品情報を取得
		List<String> asinOfProductInfoList = new ArrayList<String>();
		if(CollectionUtils.isNotEmpty(cacheProductNotExistsList)) {

			// 回復レート単位で問い合わせる
			List<List<String>> divideIdList = CollectionUtils.divide(cacheProductNotExistsList, AmazonConst.MwsApiThrottle.GetMatchingProductForId.getBulkSize());

			for(List<String> requestIdList : divideIdList) {
				Map<Country, GetMatchingProductForIdResponse> productResponseMap = getMatchingProductForIdAsyncCountries(lowestRequestDto.getIdType(), requestIdList, lowestRequestDto.getCountryList());

				Iterator<Map.Entry<Country, GetMatchingProductForIdResponse>> itProduct = productResponseMap.entrySet().iterator();
				List<BmAsinIdRelation> bmAsinIdRelationList = new ArrayList<BmAsinIdRelation>();
				// 国でループ
				while(itProduct.hasNext()) {
					Map.Entry<Country, GetMatchingProductForIdResponse> countryResponse = itProduct.next();
					// レスポンス取得
					List<GetMatchingProductForIdResult> resultList = countryResponse.getValue().getGetMatchingProductForIdResult();

					// <GetMatchingProductForIdResponse>
					// <GetMatchingProductForIdResult Id="ID1" IdType="ISBN"
					// ...>
					// <Products ..>
					// <Product>ASINの商品情報</Product>
					// <Product>ASINの商品情報</Product> ※ID次第で複数ASIN返るかも
					// </Products>
					// </GetMatchingProductForIdResult>
					// <GetMatchingProductForIdResult Id="ID2" IdType="ISBN"
					// ...>
					// <Products ..>...</Products>
					// </GetMatchingProductForIdResult>
					// </GetMatchingProductForIdResponse>

					// IDでループ
					for(GetMatchingProductForIdResult result : resultList) {

						ProductList products = result.getProducts();
						if(products != null) {
							if(products.isSetProduct()) {
								List<String> idRelAsinList = new ArrayList<String>();
								// Product（asin単位）でループ
								for(Product product : products.getProduct()) {

									ProductInfoDto productInfoDto = new ProductInfoDto();
									String asin = product.getIdentifiers().getMarketplaceASIN().getASIN();
									idRelAsinList.add(asin);
									productInfoDto.setAsin(asin);
									productInfoDto.setCountry(countryResponse.getKey());
									productInfoDto.setCreateDate(takeInDate);
									List<Object> any = product.getAttributeSets().getAny();
									for(Object object : any) {
										Node attribute = (Node) object;

										NodeList childNodes = attribute.getChildNodes();
										for(int n = 0; n < childNodes.getLength(); n++) {
											Node item = childNodes.item(n);
											String nodeName = item.getNodeName();
											String nodeValue = item.getTextContent();

											// Title
											if("ns2:Title".equals(nodeName)) {
												// log.info("Title:" +
												// nodeValue);
												productInfoDto.setTitle(nodeValue);
											}
											// ProductGroup
											if("ns2:ProductGroup".equals(nodeName)) {
												// log.info("ProductGroup:" +
												// nodeValue);
												productInfoDto.setProductGroup(nodeValue);
											}
											// 画像
											if("ns2:SmallImage".equals(nodeName)) {
												NodeList childChildNodes = item.getChildNodes();
												for(int no = 0; no < childChildNodes.getLength(); no++) {
													Node tmp = childChildNodes.item(no);
													String name = tmp.getNodeName();
													String value = tmp.getTextContent();

													if("ns2:URL".equals(name)) {
														productInfoDto.setSmallImage(value);
													}
												}
											}
											// 発売日
											if("ns2:ReleaseDate".equals(nodeName)) {
												// log.info("ProductGroup:" +
												// nodeValue);
												productInfoDto.setReleaseDate(DateUtil.parseDate(nodeValue, "yyyy-MM-dd"));
											}
											// サイズ
											if("ns2:PackageDimensions".equals(nodeName)) {
												NodeList childChildNodes = item.getChildNodes();
												PackageDimensionDto dto = new PackageDimensionDto();
												for(int no = 0; no < childChildNodes.getLength(); no++) {
													Node tmp = childChildNodes.item(no);
													String name = tmp.getNodeName();
													String value = tmp.getTextContent();

													// 高さ
													if("ns2:Height".equals(name)) {
														dto.setHeight(FloatUtil.toPrimitiveFloat(value));
														// log.info("Height:" +
														// value);
													}
													// 長さ
													if("ns2:Length".equals(name)) {
														dto.setLength(FloatUtil.toPrimitiveFloat(value));
														// log.info("Length:" +
														// value);
													}
													// 幅
													if("ns2:Width".equals(name)) {
														dto.setWidth(FloatUtil.toPrimitiveFloat(value));
														// log.info("Width:" +
														// value);
													}
													// 重さ
													if("ns2:Weight".equals(name)) {
														dto.setWeight(FloatUtil.toPrimitiveFloat(value));
														// log.info("Weight:" +
														// value);
													}

												}
												productInfoDto.setPackageDimensionDto(dto);
											}

										}
									}

									// ランキング
									SalesRankList salesRankings = product.getSalesRankings();
									SalesRankDto salesRankDto = new SalesRankDto();
									for(SalesRankType salesRankType : salesRankings.getSalesRank()) {
										try {
											IntegerUtil.parseInt(salesRankType.getProductCategoryId());
										} catch (Exception e) {
											salesRankDto.setCategoryName(salesRankType.getProductCategoryId());
											salesRankDto.setRank(salesRankType.getRank());
											break;
										}
										// log.info(String.format("rank[%s][%s]",
										// salesRankType.getProductCategoryId(),
										// salesRankType.getRank()));
									}
									productInfoDto.setSalesRankDto(salesRankDto);

									// asin,国別にキャッシュに保存する
									String productCacheName = CacheService.MWS_PRODUCT_COUNTRIES_CACHE + asin + countryResponse.getKey().toString();
									log.info("■商品情報をキャッシュします。 asin:" + asin + ", country:" + countryResponse.getKey() + ", cacheName:" + productCacheName);
									cacheService.put(productCacheName, productInfoDto, ExpireKbn.HOUR, 24 * 12);
									productInfoDtoList.add(productInfoDto);

								}
								// idとasin関連マスタに登録する
								if(lowestRequestDto.getIdType() != AmazonConst.IdType.ASIN) {
									if(getAsinList(lowestRequestDto.getIdType(), result.getId()) == null) {
										// キャッシュだけが消えて関連マスタがある場合は、マスタには登録しない。
										BmAsinIdRelation bmAsinIdRelation = new BmAsinIdRelation();
										bmAsinIdRelation.setId(result.getId());
										bmAsinIdRelation.setIdType(lowestRequestDto.getIdType());
										bmAsinIdRelation.setAsinList(idRelAsinList);
										bmAsinIdRelationList.add(bmAsinIdRelation);
									}
								}
								for(String idRelAsin : idRelAsinList) {
									// 国によって紐づくASIN増えるかもしれないので全部の国まわす
									if(!asinOfProductInfoList.contains(idRelAsin)) {
										asinOfProductInfoList.add(idRelAsin);
									}
								}
							}
						}
					}
				}
			}
		}
		allAsinList.addAll(cacheProductExistsAsinList);
		allAsinList.addAll(asinOfProductInfoList);

		// **************************************************
		// 最低価格情報取得
		// **************************************************
		ArrayList<String> cacheLowestExistsAsinList = new ArrayList<String>(); // キャッシュに最低価格情報が存在するASINリスト
		ArrayList<String> cacheLowestNotExistsAsinList = new ArrayList<String>(); // キャッシュに最低価格情報が存在しないASINリスト
		for(String asin : allAsinList) {
			boolean isExistsCache = true;
			for(Country country : lowestRequestDto.getCountryList()) {
				String lowestCacheName = CacheService.MWS_LOWEST_PRICE_FOR_ASIN_COUNTRIES_CACHE + asin + country.toString();
				// cacheService.delete(lowestCacheName);
				if(!cacheService.exist(lowestCacheName)) {
					// 何れかの国が無ければMWS取得対象
					isExistsCache = false;
					break;
				}
			}
			if(isExistsCache) {
				cacheLowestExistsAsinList.add(asin);
			} else {
				cacheLowestNotExistsAsinList.add(asin);
			}
		}

		List<LowestPriceInfoDto> lowestPriceInfoDtoList = new ArrayList<LowestPriceInfoDto>();
		// キャッシュから最低価格情報を取得
		for(String asin : cacheProductExistsAsinList) {
			for(Country country : lowestRequestDto.getCountryList()) {
				String lowestCacheName = CacheService.MWS_LOWEST_PRICE_FOR_ASIN_COUNTRIES_CACHE + asin + country.toString();
				lowestPriceInfoDtoList.add(cacheService.getSingle(lowestCacheName, LowestPriceInfoDto.class));
			}
		}

		if(CollectionUtils.isNotEmpty(cacheLowestNotExistsAsinList)) {

			// 回復レート単位で問い合わせる
			List<List<String>> divideIdList = CollectionUtils.divide(cacheLowestNotExistsAsinList, AmazonConst.MwsApiThrottle.GetLowestOfferListingsForASIN.getRecoveryRateQuota());

			for(List<String> requestIdList : divideIdList) {

				List<BmMwsProperty> countryList = new ArrayList<BmMwsProperty>();
				for(Country country : lowestRequestDto.getCountryList()) {
					BmMwsProperty bmMwsProperty = MwsKeyUtil.getBmMwsProperty(country);
					log.info(bmMwsProperty.getShopName());
					countryList.add(bmMwsProperty);
				}
				Map<Country, GetLowestOfferListingsForASINResponse> lowestResponseMap = getLowestOfferListingsForASINAsyncCountries(requestIdList, countryList);

				Iterator<Map.Entry<Country, GetLowestOfferListingsForASINResponse>> itLowest = lowestResponseMap.entrySet().iterator();

				// 最安値セット
				// 国でループ
				while(itLowest.hasNext()) {
					Map.Entry<Country, GetLowestOfferListingsForASINResponse> countryResponse = itLowest.next();
					// レスポンス取得
					List<GetLowestOfferListingsForASINResult> resultList = countryResponse.getValue().getGetLowestOfferListingsForASINResult();
					// asinでループ
					for(GetLowestOfferListingsForASINResult result : resultList) {
						LowestPriceInfoDto lowestPriceInfoDto = new LowestPriceInfoDto();
						lowestPriceInfoDto.setAsin(result.getASIN());
						lowestPriceInfoDto.setCountry(countryResponse.getKey());
						lowestPriceInfoDto.setCreateDate(takeInDate);
						lowestPriceInfoDto.setLowestOfferListingList(result.getProduct().getLowestOfferListings().getLowestOfferListing());
						lowestPriceInfoDtoList.add(lowestPriceInfoDto);

						// asin,国別にキャッシュに保存する
						// ※最低価格が取れていない国もキャッシュに入れています。（入れないと、毎回検索に行ってしまうため）
						String lowestCacheName = CacheService.MWS_LOWEST_PRICE_FOR_ASIN_COUNTRIES_CACHE + lowestPriceInfoDto.getAsin() + lowestPriceInfoDto.getCountry().toString();
						log.info("■最低価格情報をキャッシュします。 asin:" + lowestPriceInfoDto.getAsin() + ", country:" + lowestPriceInfoDto.getCountry().toString() + ", lowestCacheName:" + lowestCacheName);
						cacheService.put(lowestCacheName, lowestPriceInfoDto, ExpireKbn.HOUR, 24 * 12);
					}
				}
			}
		}

		List<ProductResponseDto> productResponseDtoList = new ArrayList<ProductResponseDto>();
		for(String asin : allAsinList) {
			ProductResponseDto productResponseDto = new ProductResponseDto();
			productResponseDto.setAsin(asin);

			List<LowestResponseDto> countryDtoList = new ArrayList<LowestResponseDto>();

			// 商品情報セット
			for(ProductInfoDto productInfoDto : productInfoDtoList) {
				if(productInfoDto.getAsin().equals(asin)) {
					if(productInfoDto.getCountry() == Country.JAPAN) {
						productResponseDto.setTitle(productInfoDto.getTitle());
						productResponseDto.setProductGroup(productInfoDto.getProductGroup());
						productResponseDto.setSmallImage(productInfoDto.getSmallImage());
						productResponseDto.setReleaseDate(productInfoDto.getReleaseDate());
						productResponseDto.setPackageDimensionDto(productInfoDto.getPackageDimensionDto());
						// productResponseDto.setSalesRankDto(productInfoDto.getSalesRankDto());
						break;
					}
				}
			}

			// 最低価格情報セット
			for(LowestPriceInfoDto lowestPriceInfoDto : lowestPriceInfoDtoList) {
				if(lowestPriceInfoDto != null) {
					if(asin.equals(lowestPriceInfoDto.getAsin())) {
						if(CollectionUtils.isNotEmpty(lowestPriceInfoDto.getLowestOfferListingList())) {
							// 最低価格が取れていない国はスキップ
							LowestResponseDto lowestResponseDto = new LowestResponseDto();
							lowestResponseDto.setAsin(asin);
							lowestResponseDto.setCountry(lowestPriceInfoDto.getCountry());
							lowestResponseDto.setCreateDate(lowestPriceInfoDto.getCreateDate());
							lowestResponseDto.setLowestOfferListingList(lowestPriceInfoDto.getLowestOfferListingList());
							// title等商品情報はセットしない。（LowestResponseDtoから消してよい？）
							for(ProductInfoDto productInfoDto : productInfoDtoList) {
								if(productInfoDto.getAsin().equals(asin) && lowestPriceInfoDto.getCountry() == productInfoDto.getCountry()) {
									lowestResponseDto.setSalesRankDto(productInfoDto.getSalesRankDto());
								}
							}
							countryDtoList.add(lowestResponseDto);
						}
					}
				}
			}
			productResponseDto.setCountryDtoList(countryDtoList);
			productResponseDtoList.add(productResponseDto);
		}
		return productResponseDtoList;
	}

	/**
	 * GCID, SellerSKU, UPC, EAN, ISBN, JANそれぞれに対応するASINを取得する。
	 *
	 * @param idType
	 * @param id
	 * @return ASINリスト
	 */
	public List<String> getAsinList(AmazonConst.IdType idType, String id) {
		BmAsinIdRelationMeta bmAsinIdRelationMeta = new BmAsinIdRelationMeta();
		List<BmAsinIdRelation> bmAsinIdRelationList = Datastore.query(bmAsinIdRelationMeta).filter(bmAsinIdRelationMeta.idType.equal(idType), bmAsinIdRelationMeta.id.equal(id)).asList();
		if(CollectionUtils.isNotEmpty(bmAsinIdRelationList)) {
			return bmAsinIdRelationList.get(0).getAsinList();
		}
		return null;
	}

	/**
	 * 指定したSellerSKUに応じた商品の現在の競合価格を返します。
	 *
	 * GetCompetitivePricingForSKUオペレーションは、指定したSellerSKU および MarketplaceId に応じた商品の現在の競合価格を返します。 SellerSKUは、出品者のSellerIdにより限定されます。 SellerIdは、送信する各Amazon マーケットプレイスWebサービス(Amazon MWS) オペレーションに含まれます。 このオペレーションは、ショッピングカートボックスを獲得した新品の価格、 およびショッピングカートボックスを獲得した中古品の価格の2種類の価格モデルに応じたアクティブな出品一覧を返します。
	 * これらの価格モデルは、Amazon マーケットプレイスWebサイトの商品詳細ページ上の主要ショッピングカートボックス獲得価格とその下位の獲得価格にそれぞれ相当します。 アクティブな出品一覧の商品が、このどちらの価格も返さない場合があります。 例えば、ある商品の出品一覧の出品者が、新品または中古品のショッピングカートボックスを獲得する要件を満たしていない場合などに起こります。 また指定した SellerSKU に対する出品者自身の価格もレスポンスに含まれるため、それが最低の出品価格である場合、出品者自身の価格が返されます。 また指定した SellerSKU
	 * に対する出品一覧数やtrade-in値(使用可能な場合)、売上げランキング情報も返されます。
	 *
	 * @param marketplaceId
	 * @param sellerSkuList
	 */
	public GetCompetitivePricingForSKUResponse getCompetitivePricingForSKU(List<String> sellerSkuList) {
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("Action", "GetCompetitivePricingForSKU");
			if(StringUtil.isNotEmpty(marketplaceId)) {
				params.put("MarketplaceId", marketplaceId);
			}

			if(CollectionUtils.isNotEmpty(sellerSkuList)) {
				int cnt = 1;
				for(String sellerSku : sellerSkuList) {
					params.put(String.format("SellerSKUList.SellerSKU.%s", cnt), sellerSku);
					cnt++;
				}
			}

			// 共通パラメータを追加。
			setParameter(params);

			// log.info("GetCompetitivePricingForSKUのリクエストを生成します。");
			HTTPRequest httpRequest = super.createHttpRequest(params);

			// log.info("リクエストを送信します。");
			HTTPResponse httpResponse = fetch(httpRequest);

			checkHttpStatus(httpResponse);

			String content = new String(httpResponse.getContent(), DEFAULT_ENCODING);

			Unmarshaller unmarshaller = getJAXBContext(GetCompetitivePricingForSKUResponse.class).createUnmarshaller();
			return (GetCompetitivePricingForSKUResponse) unmarshaller.unmarshal(new StringReader(content));

		} catch (Exception e) {
			throw new MyException(e);
		}
	}

	/**
	 * 指定したASINに応じた商品の現在の競合価格を返します。
	 *
	 * GetCompetitivePricingForASINオペレーションは、商品を一意に識別するためにMarketplaceIdとASINを指定し、 SKUIdentifier要素を返さない点を除き、GetCompetitivePricingForSKUオペレーションと同様です。 商品のASINが不明な場合、それを明らかにするため、まず始めにListMatchingProductsオペレーションを送信する必要があります。
	 *
	 * @param marketplaceId
	 * @param asinList
	 */
	public GetCompetitivePricingForASINResponse getCompetitivePricingForASINByAmazonCatalog(List<AmazonCatalog> list) {
		List<String> asinList = new ArrayList<>();
		for(AmazonCatalog amazonCatalog : list) {
			asinList.add(amazonCatalog.getAsin());
		}
		return getCompetitivePricingForASIN(asinList);
	}

	/**
	 * 指定したASINに応じた商品の現在の競合価格を返します。 featureだと、エラーが結構な確率で発生するため、非推奨。
	 *
	 * GetCompetitivePricingForASINオペレーションは、商品を一意に識別するためにMarketplaceIdとASINを指定し、 SKUIdentifier要素を返さない点を除き、GetCompetitivePricingForSKUオペレーションと同様です。 商品のASINが不明な場合、それを明らかにするため、まず始めにListMatchingProductsオペレーションを送信する必要があります。
	 *
	 * @param marketplaceId
	 * @param asinList
	 * @deprecated
	 */
	public GetCompetitivePricingForASINResponse getCompetitivePricingForASINFeature(List<String> asinList) {
		GetCompetitivePricingForASINResponse ret = new GetCompetitivePricingForASINResponse();
		ret.setGetCompetitivePricingForASINResult(new ArrayList<GetCompetitivePricingForASINResult>());
		List<Future<HTTPResponse>> futures = new ArrayList<Future<HTTPResponse>>();
		URLFetchService urlFetchSearvice = URLFetchServiceFactory.getURLFetchService();
		int cnt = 0;

		// 最初に並列分のリストに分割をする
		List<List<String>> threadList = divideIdList(asinList, 100);
		for(List<String> thread : threadList) {
			// ASINの20セットのリストに分割
			List<List<String>> futureList = divideIdList(thread, MwsApiThrottle.GetCompetitivePricingForASIN.getBulkSize());

			try {
				// =========================
				// ASINをFutureに追加
				for(List<String> list : futureList) {
					if(CollectionUtils.isNotEmpty(list) && list.size() > 0) {
						Map<String, String> params = new HashMap<String, String>();
						params.put("Action", "GetCompetitivePricingForASIN");
						if(StringUtil.isNotEmpty(marketplaceId)) {
							params.put("MarketplaceId", marketplaceId);
						}

						if(CollectionUtils.isNotEmpty(asinList)) {
							int no = 1;
							for(String asin : list) {
								params.put(String.format("ASINList.ASIN.%s", no), asin);
								no++;
							}
						}
						// 共通パラメータを追加。
						setParameterForRandom(country, params);

						// log.info("GetLowestOfferListingsForASINのリクエストを生成します。");
						HTTPRequest httpRequest = super.createHttpRequest(params);

						futures.add(urlFetchSearvice.fetchAsync(httpRequest));
					}
				}

				// ======================
				// レスポンスをリストに追加
				for(int i = 0; i < futures.size(); i++) {
					int status = -1;
					boolean shouldRetry = true;
					int retries = 0;
					do {
						try {
							Future<HTTPResponse> future = futures.get(i);
							HTTPResponse httpResponse = future.get();
							status = httpResponse.getResponseCode();

							// Success
							if(status == HttpStatus.SC_OK) {
								shouldRetry = false;
								String content = new String(httpResponse.getContent(), DEFAULT_ENCODING);
								Unmarshaller unmarshaller = getJAXBContext(GetCompetitivePricingForASINResponse.class).createUnmarshaller();
								GetCompetitivePricingForASINResponse tmp = (GetCompetitivePricingForASINResponse) unmarshaller.unmarshal(new StringReader(content));

								if(cnt == 0) {
									ret = tmp;
								} else {
									ret.getGetCompetitivePricingForASINResult().addAll(tmp.getGetCompetitivePricingForASINResult());
								}

							} else {
								log.warning(String.format("APIの実行に失敗しました。[%s][%s]", this.accessKeyId, this.shopName));
								log.warning(new String(httpResponse.getContent(), DEFAULT_ENCODING));
								if((status == HttpStatus.SC_INTERNAL_SERVER_ERROR && pauseIfRetryNeeded(++retries))) {
									shouldRetry = true;
								} else {
									shouldRetry = false;
								}
							}
						} catch (IOException e) {
							log.warning("IOExceptionが発生しました。");
							if((status == HttpStatus.SC_INTERNAL_SERVER_ERROR && pauseIfRetryNeeded(++retries))) {
								shouldRetry = true;
								log.info("リトライします。" + retries);
								sleep(5000L);
							} else {
								throw new MyException("リトライ上限に抵触しました。");
							}
						}
						cnt++;
					} while(shouldRetry);
				}

			} catch (Exception e) {
				throw new MyException(e);
			}

		}
		return ret;
	}

	/**
	 * カート価格を取得します。
	 *
	 * @param btCanStockDetailList
	 * @return
	 */
	public <T extends AbstractStockDetailModel> List<GetCompetitivePricingForASINResult> getCompetitivePricingForBtCanStockDetail(List<T> btCanStockDetailList) {

		List<GetCompetitivePricingForASINResult> retList = new ArrayList<>();
		// ASINの20セットのリストに分割
		// TODO taku 2016/10/03 ベンチマーク対応はここに入れると良さそう
		List<List<T>> asinListList = convertAsinListList(btCanStockDetailList, 200);
		// ASINがない場合はSKUを5セットのリストに分割
        // TODO taku 2016/10/03 ベンチマーク対応はここに入れると良さそう
		List<List<String>> skuListList = convertSkuListList(btCanStockDetailList, 200, false);

		// =========================
		// ASINをもとに実行
		for(List<T> list : asinListList) {
			if(CollectionUtils.isNotEmpty(list) && list.size() > 0) {
				// log.info(String.format("ASINで検索します。[%s]", convertAsinList(list)));
				GetCompetitivePricingForASINResponse ret = getCompetitivePricingForASIN(convertAsinList(list, true));
				List<GetCompetitivePricingForASINResult> getMatchingProductForIdResult = ret.getGetCompetitivePricingForASINResult();
				// log.info(String.format("ASIN検索結果[%d件]", getMatchingProductForIdResult.size()));
				// SKUをセットする
				for(GetCompetitivePricingForASINResult t : getMatchingProductForIdResult) {
					for(T y : list) {
                        if(t.getASIN() != null) {
                            if(y.getAsin().equals(t.getASIN()) || t.getASIN().equals(y.getBenchmarkAsin())) {
    							// ASINがマッチしたらSKUも詰めておく。
    							SellerSKUIdentifier sellerSKUIdentifier = new SellerSKUIdentifier();
    							sellerSKUIdentifier.setSellerSKU(y.getSku());
    							IdentifierType identifierType = new IdentifierType();
    							identifierType.setSKUIdentifier(sellerSKUIdentifier);
    							ASINIdentifier asinIdentifier = new ASINIdentifier();
    							asinIdentifier.setASIN(y.getAsin());
    							identifierType.setMarketplaceASIN(asinIdentifier);
    							// Productがあればセット
    							if(t.isSetProduct()) {
    								t.getProduct().setIdentifiers(identifierType);
    								break;
    							}
                            }
                        }
					}
				}
				if(ret.isSetGetCompetitivePricingForASINResult()) {
					retList.addAll(ret.getGetCompetitivePricingForASINResult());
				}
			}
		}

		// =========================
		// SKUをもとに実行
		for(List<String> list : skuListList) {
			if(CollectionUtils.isNotEmpty(list) && list.size() > 0) {
				log.info(String.format("SKUで検索します。[%s]", list.toString()));
				GetCompetitivePricingForSKUResponse ret = getCompetitivePricingForSKU(list);
				log.info(String.format("SKU検索結果[%d件]", ret.getGetCompetitivePricingForSKUResult().size()));

				if(ret.isSetGetCompetitivePricingForSKUResult()) {
					List<GetCompetitivePricingForSKUResult> tmpList = ret.getGetCompetitivePricingForSKUResult();
					for(GetCompetitivePricingForSKUResult t : tmpList) {
						if(t.isSetProduct()) {
							GetCompetitivePricingForASINResult asinResult = new GetCompetitivePricingForASINResult();

							BeanUtil.copy(t, asinResult);
							asinResult.getProduct().getIdentifiers().getSKUIdentifier().setSellerSKU(t.getSellerSKU());
							asinResult.setASIN(asinResult.getProduct().getIdentifiers().getMarketplaceASIN().getASIN());
							retList.add(asinResult);
						}
					}
				}
			}
		}

		return retList;

	}

	/**
	 * カート価格を取得します。
	 *
	 * @param asinList
	 * @return
	 */
	public GetCompetitivePricingForASINResponse getCompetitivePricingForASIN(List<String> asinList) {

		try {
			GetCompetitivePricingForASINResponse ret = new GetCompetitivePricingForASINResponse();
			// ASINの20セットのリストに分割
			List<List<String>> asinListList = CollectionUtils.divide(asinList, 20);

			// =========================
			// ASINをもとに実行
			for(List<String> list : asinListList) {
				if(CollectionUtils.isNotEmpty(list) && list.size() > 0) {
					Map<String, String> params = new HashMap<String, String>();
					params.put("Action", "GetCompetitivePricingForASIN");

					if(CollectionUtils.isNotEmpty(list)) {
						int cnt = 1;
						for(String asin : list) {
							params.put(String.format("ASINList.ASIN.%s", cnt), asin);
							cnt++;
						}
					}
					// 共通パラメータを追加。
					setParameter(params);

					// log.info("GetMatchingProductのリクエストを生成します。");
					HTTPRequest httpRequest = super.createHttpRequest(params);

					// log.info("リクエストを送信します。");
					HTTPResponse httpResponse = fetch(httpRequest);

					checkHttpStatus(httpResponse);

					String content = new String(httpResponse.getContent(), DEFAULT_ENCODING);

					Unmarshaller unmarshaller = getJAXBContext(GetCompetitivePricingForASINResponse.class).createUnmarshaller();
					GetCompetitivePricingForASINResponse tmp = (GetCompetitivePricingForASINResponse) unmarshaller.unmarshal(new StringReader(content));

					if(tmp.isSetGetCompetitivePricingForASINResult()) {
						ret.getGetCompetitivePricingForASINResult().addAll(tmp.getGetCompetitivePricingForASINResult());
					}
				}
			}
			return ret;
		} catch (Exception e) {
			StackTraceUtil.toString(e);
			throw new MyException(e);
		}

	}

	/**
	 * 指定したSellerSKUに応じた出品中の商品の最低価格を返します。
	 *
	 * GetLowestOfferListingsForSKUオペレーションは、指定した商品のコンディションごとに最低価格の出品一覧を返します。 指定した商品およびItemConditionの一覧は、以下の6つの条件の組み合わせで定義される出品一覧グループに分類されます。 • ItemCondition (New, Used, Collectible, Refurbished, Club) • ItemSubcondition (New, Mint, Very Good, Good, Acceptable, Poor, Club, OEM, Warranty, Refurbished
	 * Warranty,Refurbished, Open Box, Other) • FulfillmentChannel (Amazon または Merchant) • ShipsDomestically (True, False, Unknown) – リクエストに指定されたマーケットプレイスと、商品の発送元国が同じかどうかを示す • ShippingTime (0-2 days, 3-7 days, 8-13 days, 14 or more days) – 注文されてから商品が出荷されるまでの通常の最長準備期間を表す •
	 * SellerPositiveFeedbackRating (98-100%, 95-97%, 90-94%, 80-89%, 70-79%, Less than 70%, Just launched ) – 過去12カ月以上の良い評価の割合を%で表す。 指定した商品およびItemConditionに対する一部の(必ずしも全部ではない)アクティブな出品一覧は、 まず配送料を含む最低価格でソートされ、対応する出品一覧グループに分類され、グループごとの最低価格を返します。 グループ内に最低価格で出品している出品者が複数いる場合は、フィードバック数が最も多い出品者の出品一覧が返されます。
	 * 出品一覧が存在しないグループは返されません。 またこのオペレーションは、指定した商品およびItemConditionに応じたアクティブな出品一覧のすべてが、 対応する出品一覧グループに分類される際に考慮されたかどうかを示すレスポンス要素、AllOfferListingsConsideredを返します。 もしすべてが考慮されなかった場合でも、以下の結果が期待できます。 • 返された最低価格は、対応する出品一覧グループにおける最低配送料込み価格である。 • 返された最低価格は、考慮されなかった全ての出品の最低配送料込み価格より低い。
	 *
	 * @param marketplaceId
	 * @param sellerSkuList
	 * @param itemCondition
	 * @param excludeMe
	 */
	public GetLowestOfferListingsForSKUResponse getLowestOfferListingsForSKU(BmMwsProperty bmMwsProperty, List<String> sellerSkuList, Const.Condition itemCondition, Boolean excludeMe) {
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("Action", "GetLowestOfferListingsForSKU");
			if(StringUtil.isNotEmpty(marketplaceId)) {
				params.put("MarketplaceId", marketplaceId);
			}
			if(CollectionUtils.isNotEmpty(sellerSkuList)) {
				int cnt = 1;
				for(String sellerSku : sellerSkuList) {
					params.put(String.format("SellerSKUList.SellerSKU.%s", cnt), sellerSku);
					cnt++;
				}
			}
			if(itemCondition != null) {
				params.put("ItemCondition", itemCondition.getCode());
			}
			if(excludeMe != null) {
				params.put("ExcludeMe", String.valueOf(excludeMe));
			}
			// 共通パラメータを追加。
			setParameter(bmMwsProperty, params);

			// log.info("GetLowestOfferListingsForSKUのリクエストを生成します。");
			HTTPRequest httpRequest = super.createHttpRequest(params);

			// log.info("リクエストを送信します。");
			HTTPResponse httpResponse = fetch(httpRequest);

			checkHttpStatus(httpResponse);

			String content = new String(httpResponse.getContent(), DEFAULT_ENCODING);

			Unmarshaller unmarshaller = getJAXBContext(GetLowestOfferListingsForSKUResponse.class).createUnmarshaller();
			return (GetLowestOfferListingsForSKUResponse) unmarshaller.unmarshal(new StringReader(content));
		} catch (Exception e) {
			throw new MyException(e);
		}
	}

	/**
	 * BtCanStockDetailのリストのSKUを利用して最低価格を取得します。 自社セラーの価格を除外するためセラーの切り替えはできません。
	 *
	 * @param btCanStockDetailList
	 * @param itemCondition
	 * @param excludeMe
	 * @return
	 */
	public <T extends AbstractStockDetailModel> GetLowestOfferListingsForSKUResponse getLowestOfferListingsForBtCanStockDetail(BmMwsProperty bmMwsProperty, List<T> btCanStockDetailList, Const.Condition itemCondition, Boolean excludeMe) {
		List<String> skuList = new ArrayList<>();
		for(T t : btCanStockDetailList) {
			skuList.add(t.getSku());
		}
		return getLowestOfferListingsForSKU(bmMwsProperty, skuList, itemCondition, excludeMe);
	}

	/**
	 * AmazonCatalogのリストのASINを利用して最低価格を取得します。
	 *
	 * @param btCanStockDetailList
	 * @param itemCondition
	 * @param excludeMe
	 * @return
	 */
	public GetLowestOfferListingsForASINResponse getLowestOfferListingsForAmazonCatalog(List<AmazonCatalog> list, Const.Condition itemCondition) {
		List<String> skuList = new ArrayList<>();
		for(AmazonCatalog dto : list) {
			skuList.add(dto.getAsin());
		}
		return getLowestOfferListingsForASINbyFuture(skuList, itemCondition);
	}

	/**
	 * ASINをもとに最低価格を取得します。
	 *
	 * @param btCanStockDetailList
	 * @param itemCondition
	 * @return
	 */
	public <T extends AbstractStockDetailModel> GetLowestOfferListingsForSKUResponse getLowestOfferListingsForAsin(List<T> btCanStockDetailList) {

		List<String> asinList = new ArrayList<>();
		boolean isUsedExecute = true;
		for(T t : btCanStockDetailList) {
			asinList.add(t.getAsin());
		}
		// 新品で処理を実行
		GetLowestOfferListingsForASINResponse asinResult = getLowestOfferListingsForASINbyFuture(asinList, Const.Condition.NEW);

		List<GetLowestOfferListingsForSKUResult> offerList = new ArrayList<>();

		List<GetLowestOfferListingsForASINResult> newList = asinResult.getGetLowestOfferListingsForASINResult();

		if(isUsedExecute) {
			// 中古で処理を実行
			GetLowestOfferListingsForASINResponse usedResult = getLowestOfferListingsForASINbyFuture(asinList, Const.Condition.USED);
			List<GetLowestOfferListingsForASINResult> usedList = usedResult.getGetLowestOfferListingsForASINResult();

			// 新品と中古の最安値を合体させる
			for(GetLowestOfferListingsForASINResult t : newList) {
				for(GetLowestOfferListingsForASINResult y : usedList) {
					// ASINかSKUがマッチした場合にマージする。
					if(t.getASIN().equals(y.getASIN())) {
						// ない場合はコピー
						if(!t.isSetProduct() || !t.getProduct().isSetLowestOfferListings()) {
							t.setProduct(y.getProduct());
							break;
						} else {
							// 存在していた場合にコピーする
							if(y.isSetProduct() && y.getProduct().getLowestOfferListings() != null) {
								t.getProduct().getLowestOfferListings().getLowestOfferListing().addAll(y.getProduct().getLowestOfferListings().getLowestOfferListing());
							}
							break;
						}
					}
				}
			}

		} else {
			// log.info("全て新品商品のため中古の最安値所得はスキップしました。");
		}

		for(GetLowestOfferListingsForASINResult t : newList) {
			GetLowestOfferListingsForSKUResult offer = new GetLowestOfferListingsForSKUResult();
			offer.setProduct(t.getProduct());
			offerList.add(offer);
		}

		GetLowestOfferListingsForSKUResponse ret = new GetLowestOfferListingsForSKUResponse();
		ret.setGetLowestOfferListingsForSKUResult(offerList);

		return ret;
	}


    /**
     * 指定したASINに応じた出品中の商品の最低価格を返します。 asinList
     *
     * @param marketplaceId
     * @param asinList
     * @param itemCondition
     */
    public GetLowestOfferListingsForASINResponse getLowestOfferListingsForASIN(List<String> asinList, Const.Condition itemCondition) {

        for(int retry = 0; retry < 3; retry++) {
            try {
                GetLowestOfferListingsForASINResponse ret = new GetLowestOfferListingsForASINResponse();
                ret.setGetLowestOfferListingsForASINResult(new ArrayList<GetLowestOfferListingsForASINResult>());
                int cnt = 0;

                // ASINの20セットのリストに分割
                List<List<String>> futureList = divideIdList(asinList, MwsApiThrottle.GetLowestOfferListingsForASIN.getBulkSize());

                // =========================
                // ASINをFutureに追加
                for(List<String> list : futureList) {
                    if(CollectionUtils.isNotEmpty(list) && list.size() > 0) {

                        Map<String, String> params = new HashMap<String, String>();
                        params.put("Action", "GetLowestOfferListingsForASIN");
                        if(StringUtil.isNotEmpty(super.marketplaceId)) {
                            params.put("MarketplaceId", super.marketplaceId);
                        }
                        int no = 1;
                        for(String asin : list) {
                            params.put(String.format("ASINList.ASIN.%s", no), asin);
                            no++;
                        }
                        if(itemCondition != null) {
                            params.put("ItemCondition", itemCondition.getCode());
                        }

                        // 共通パラメータを追加。
                        setParameterForRandom(country, params);

                        // log.info("GetLowestOfferListingsForASINのリクエストを生成します。");
                        HTTPRequest httpRequest = super.createHttpRequest(params);


                        HTTPResponse httpResponse = fetch(httpRequest);

                        checkHttpStatus(httpResponse);

                        int status = httpResponse.getResponseCode();

                        // Success
                        if(status == HttpStatus.SC_OK) {
                            String content = new String(httpResponse.getContent(), DEFAULT_ENCODING);
                            Unmarshaller unmarshaller = getJAXBContext(GetLowestOfferListingsForASINResponse.class).createUnmarshaller();
                            GetLowestOfferListingsForASINResponse tmp = (GetLowestOfferListingsForASINResponse) unmarshaller.unmarshal(new StringReader(content));
                            if(cnt == 0) {
                                ret = tmp;
                            } else {
                                ret.getGetLowestOfferListingsForASINResult().addAll(tmp.getGetLowestOfferListingsForASINResult());
                            }

                        } else {
                            checkHttpStatus(httpResponse);
                            throw new MyException("http status error");
                        }
                    }
                }

                return ret;

            } catch (Exception e) {
                log.warning(StackTraceUtil.toString(e));
                sleep(2000L);
            }
        }
        throw new MyException("エラーが発生しました。");
    }
	/**
	 * 指定したASINに応じた出品中の商品の最低価格を返します。 asinList
	 *
	 * @param marketplaceId
	 * @param asinList
	 * @param itemCondition
	 */
	public GetLowestOfferListingsForASINResponse getLowestOfferListingsForASINbyFuture(List<String> asinList, Const.Condition itemCondition) {

		for(int retry = 0; retry < 3; retry++) {
			try {
				GetLowestOfferListingsForASINResponse ret = new GetLowestOfferListingsForASINResponse();
				ret.setGetLowestOfferListingsForASINResult(new ArrayList<GetLowestOfferListingsForASINResult>());
				List<Future<HTTPResponse>> futures = new ArrayList<Future<HTTPResponse>>();
				URLFetchService urlFetchSearvice = URLFetchServiceFactory.getURLFetchService();
				int cnt = 0;

				// 最初に並列分のリストに分割をする
				List<List<String>> threadList = divideIdList(asinList, 100);
				for(List<String> thread : threadList) {
					// ASINの20セットのリストに分割
					List<List<String>> futureList = divideIdList(thread, MwsApiThrottle.GetLowestOfferListingsForASIN.getBulkSize());

					// =========================
					// ASINをFutureに追加
					for(List<String> list : futureList) {
						if(CollectionUtils.isNotEmpty(list) && list.size() > 0) {

							Map<String, String> params = new HashMap<String, String>();
							params.put("Action", "GetLowestOfferListingsForASIN");
							if(StringUtil.isNotEmpty(super.marketplaceId)) {
								params.put("MarketplaceId", super.marketplaceId);
							}
							if(CollectionUtils.isNotEmpty(list)) {
								int no = 1;
								for(String asin : list) {
									params.put(String.format("ASINList.ASIN.%s", no), asin);
									no++;
								}
							}
							if(itemCondition != null) {
								params.put("ItemCondition", itemCondition.getCode());
							}

							// 共通パラメータを追加。
							setParameterForRandom(country, params);

							// log.info("GetLowestOfferListingsForASINのリクエストを生成します。");
							HTTPRequest httpRequest = super.createHttpRequest(params);

							futures.add(urlFetchSearvice.fetchAsync(httpRequest));
						}
					}

					// ======================
					// レスポンスをリストに追加
					for(int i = 0; i < futures.size(); i++) {
						int status = -1;
						Future<HTTPResponse> future = futures.get(i);
						HTTPResponse httpResponse = null;
						for(int j = 0; j < 3; j++) {
							try {
								httpResponse = future.get();
								break;

							} catch (Exception e) {
								try {
									Thread.sleep(5000L);
								} catch (Exception e2) {
									e2.printStackTrace();
									log.warning("futureでエラーになりました。リトライします。");
								}
							}
						}
						status = httpResponse.getResponseCode();

						// Success
						if(status == HttpStatus.SC_OK) {
							String content = new String(httpResponse.getContent(), DEFAULT_ENCODING);
							Unmarshaller unmarshaller = getJAXBContext(GetLowestOfferListingsForASINResponse.class).createUnmarshaller();
							GetLowestOfferListingsForASINResponse tmp = (GetLowestOfferListingsForASINResponse) unmarshaller.unmarshal(new StringReader(content));
							if(cnt == 0) {
								ret = tmp;
							} else {
								ret.getGetLowestOfferListingsForASINResult().addAll(tmp.getGetLowestOfferListingsForASINResult());
							}

						} else {
							checkHttpStatus(httpResponse);
							throw new MyException("http status error");
						}

						cnt++;
					}
				}
				return ret;

			} catch (Exception e) {
				log.warning(StackTraceUtil.toString(e));
				sleep(2000L);
			}
		}
		throw new MyException("エラーが発生しました。");
	}

	/**
	 * StackTraceをStringで返します。
	 *
	 * @param e
	 * @return
	 */
	protected String getStackTraceString(Throwable e) {
		StackTraceElement[] elements = e.getStackTrace();
		String content = String.format("%s: %s", e.getClass().getName(), e.getMessage());
		for(StackTraceElement element : elements) {
			content += String.format("\r\n\tat %s", element.toString());
		}
		return content;
	}

	/**
	 * 指定したASINに応じた出品中の商品の最低価格を返します。 GetLowestOfferListingsForASINオペレーションは、 商品を一意に識別するためにMarketplaceIdとASINを指定し、 SKUIdentifier要素を返さない点を除き、GetLowestOfferListingsForSKUオペレーションと同様です。
	 *
	 * @param marketplaceId
	 * @param asinList
	 * @param itemCondition
	 */
	public Map<Country, GetLowestOfferListingsForASINResponse> getLowestOfferListingsForASINAsyncCountries(List<String> asinList, List<BmMwsProperty> allCountryList) {
		Map<Country, GetLowestOfferListingsForASINResponse> countryResponseMap = new HashMap<Country, GetLowestOfferListingsForASINResponse>();
		try {
			// **************************************************
			// URLフェッチ（非同期）に変更
			// **************************************************
			URLFetchService urlFetchSearvice = URLFetchServiceFactory.getURLFetchService();
			List<Future<HTTPResponse>> futures = new ArrayList<Future<HTTPResponse>>();

			for(BmMwsProperty bmMwsProperty : allCountryList) {
				Map<String, String> params = new HashMap<String, String>();
				params.put("Action", "GetLowestOfferListingsForASIN");
				if(CollectionUtils.isNotEmpty(asinList)) {
					int cnt = 1;
					for(String asin : asinList) {
						params.put(String.format("ASINList.ASIN.%s", cnt), asin);
						cnt++;
					}
				}
				params.put("MarketplaceId", bmMwsProperty.getCountry().getMarketplaceId());
				setParameterForRandom(bmMwsProperty.getCountry(), params);
				//
				// log.info(bmMwsProperty.getCountry().getName());
				// log.info(bmMwsProperty.getCountry().getMarketplaceId());
				// log.info(bmMwsProperty.getAccessKeyId());
				// log.info(bmMwsProperty.getSellerId());
				// log.info(bmMwsProperty.getSecretAccessKey());
				// log.info(bmMwsProperty.getCountry().getProductServiceUrl());
				//
				// log.info(bmMwsProperty.getShopName() +
				// "でGetLowestOfferListingsForASINのリクエストを生成します。");
				HTTPRequest httpRequest = super.createHttpRequest(bmMwsProperty.getCountry(), params);

				futures.add(urlFetchSearvice.fetchAsync(httpRequest));
			}
			// レスポンスをリストに追加
			for(int i = 0; i < futures.size(); i++) {

				int status = -1;
				boolean shouldRetry = true;
				int retries = 0;
				do {
					try {
						Future<HTTPResponse> future = futures.get(i);
						HTTPResponse httpResponse = future.get();
						status = httpResponse.getResponseCode();
						if(status == HttpStatus.SC_OK) {
							shouldRetry = false;

							Unmarshaller unmarshaller = getJAXBContext(GetLowestOfferListingsForASINResponse.class).createUnmarshaller();
							countryResponseMap.put(allCountryList.get(i).getCountry(), (GetLowestOfferListingsForASINResponse) unmarshaller.unmarshal(new StringReader(new String(httpResponse.getContent(), DEFAULT_ENCODING))));
						} else {
							log.warning(String.format("APIの実行に失敗しました。[%s][%s]", this.accessKeyId, this.shopName));
							log.warning(new String(httpResponse.getContent(), DEFAULT_ENCODING));
							if((status == HttpStatus.SC_INTERNAL_SERVER_ERROR && pauseIfRetryNeeded(++retries))) {
								shouldRetry = true;
							} else {
								shouldRetry = false;
								// throw new MyException(new
								// String(httpResponse.getContent(),
								// DEFAULT_ENCODING));
							}
						}
					} catch (IOException e) {
						log.warning(StackTraceUtil.toString(e));
					}
				} while(shouldRetry);
			}
			return countryResponseMap;

		} catch (Exception e) {
			throw new MyException(e);
		}
	}

	public <T extends AbstractStockDetailModel> GetMyPriceForSKUResponse getMyPriceForSKUForBtCanStockDetail(List<T> list, Const.SubCondition itemCondition) {
		List<String> convertList = convertSkuList(list);
		return getMyPriceForSKU(convertList, null);
	}

	public <T extends AbstractMwsProductFeedModel> void getMyPointForSKUForAbstractMwsProductFeedModel(List<T> list, Const.SubCondition itemCondition) {

		List<List<T>> listList = CollectionUtils.divide(list, 20);
		for(List<T> ddd : listList) {
			Set<String> convertList = new ArraySet<>();
			for(AbstractMwsProductFeedModel t : ddd) {
				if(t instanceof BtCanStockDetail || t instanceof BtCustomerStockDetail) {
					AbstractStockDetailModel dto = (AbstractStockDetailModel) t;
					convertList.add(dto.getSku());
				} else if(t instanceof CmShipmentProduct) {
					CmShipmentProduct dto = (CmShipmentProduct) t;
					convertList.add(dto.getSellerSku());
				}
			}
			GetMyPriceForSKUResponse myPriceForSKU = getMyPriceForSKU(new ArrayList<String>(convertList), null);
			for(AbstractMwsProductFeedModel t : ddd) {
				String sku = null;
				if(t instanceof BtCanStockDetail || t instanceof BtCustomerStockDetail) {
					AbstractStockDetailModel dto = (AbstractStockDetailModel) t;
					sku = dto.getSku();
				} else if(t instanceof CmShipmentProduct) {
					CmShipmentProduct dto = (CmShipmentProduct) t;
					sku = dto.getSellerSku();
				}

				// 現状のポイントをセットする
				Integer point = myPriceForSKU.bccGetPoint(sku);

				// NULLの場合
				if(t.getModifyAmazonPoint() == null) {
					log.info(String.format("%sに%dポイントをセットしました。(1)", sku, 0));
					t.setModifyAmazonPoint(0);
					t.setAmazonPoint(0);

				} else if(0 == t.getModifyAmazonPoint()) {
					log.info(String.format("%sに%dポイントをセットしました。(2)", sku, point));
					t.setModifyAmazonPoint(point);
					t.setAmazonPoint(point);
				} else if(0 > t.getModifyAmazonPoint()) {
					log.info(String.format("%sは%dポイントのママにします。", sku, t.getModifyAmazonPoint()));
				}
			}
		}
	}


//	POST /Products/2011-10-01?AWSAccessKeyId=AKIAICEMYFD5ELDO5SFQ
//	        &Action=GetMyFeesEstimate
//	        &SellerId=A2SFOEUFUIPMQE
//	        &SignatureVersion=2
//	        &Timestamp=2016-10-18T08%3A11%3A07Z
//	        &Version=2011-10-01
//	        &Signature=w7%2BWQwrRfRMU9eyCk06qBJoX7JEB6hdTKHeabQgLtls%3D
//	        &SignatureMethod=HmacSHA256
//          &FeesEstimateRequestList.FeesEstimateRequest.1.MarketplaceId=A1VC38T7YXB528
//	         &FeesEstimateRequestList.FeesEstimateRequest.1.IdType=SellerSKU
//	          &FeesEstimateRequestList.FeesEstimateRequest.1.IdValue=AZ-SIRE-H102
//	          &FeesEstimateRequestList.FeesEstimateRequest.1.IsAmazonFulfilled=true
//	          &FeesEstimateRequestList.FeesEstimateRequest.1.Identifier=AZ-SIRE-H102
//	          &FeesEstimateRequestList.FeesEstimateRequest.1.PriceToEstimateFees.ListingPrice.Amount=2809
//	          &FeesEstimateRequestList.FeesEstimateRequest.1.PriceToEstimateFees.ListingPrice.CurrencyCode=JPY
//	          &FeesEstimateRequestList.FeesEstimateRequest.1.PriceToEstimateFees.Shipping.Amount=300
//	          &FeesEstimateRequestList.FeesEstimateRequest.1.PriceToEstimateFees.Shipping.CurrencyCode=JPY
//	          &FeesEstimateRequestList.FeesEstimateRequest.1.PriceToEstimateFees.Points.PointsNumber=100 HTTP/1.1

//	      Host: mws.amazonservices.jp
//	      x-amazon-user-agent: AmazonJavascriptScratchpad/1.0 (Language=Javascript)
//	      Content-Type: text/xml
	/**
	 * 対象商品の手数料を計算します。
	 * @param list
	 * @return
	 */
    public <T extends AbstractStockDetailModel> GetMyFeesEstimateResponse GetMyFeesEstimate(List<T> list) {

        String content = null;
        try {
            Map<String, String> params = new HashMap<String, String>();
            params.put("Action", "GetMyFeesEstimate");
            if(StringUtil.isNotEmpty(marketplaceId)) {
                params.put("MarketplaceId", marketplaceId);
            }
            if(CollectionUtils.isNotEmpty(list)) {
                int cnt = 1;
                for(AbstractStockDetailModel model : list) {
                    params.put(String.format("FeesEstimateRequestList.FeesEstimateRequest.%s.MarketplaceId", cnt), super.marketplaceId);
                    params.put(String.format("FeesEstimateRequestList.FeesEstimateRequest.%s.IdType", cnt), IdType.SellerSKU.toString());
                    params.put(String.format("FeesEstimateRequestList.FeesEstimateRequest.%s.IdValue", cnt), StringUtil.xmlEncode(model.getSku()));
                    params.put(String.format("FeesEstimateRequestList.FeesEstimateRequest.%s.Identifier", cnt), StringUtil.xmlEncode(model.getSku()));
                    if(SalesRoute.AMAZON.equals(model.getSalesRoute())){
                        params.put(String.format("FeesEstimateRequestList.FeesEstimateRequest.%s.IsAmazonFulfilled", cnt), "true");
                    }else{
                        params.put(String.format("FeesEstimateRequestList.FeesEstimateRequest.%s.IsAmazonFulfilled", cnt), "false");
                    }
                    //商品代金
                    params.put(String.format("FeesEstimateRequestList.FeesEstimateRequest.%s.PriceToEstimateFees.ListingPrice.Amount", cnt), String.format("%.0f", model.getPrice()));
                    params.put(String.format("FeesEstimateRequestList.FeesEstimateRequest.%s.PriceToEstimateFees.ListingPrice.CurrencyCode", cnt), Currency.JPY.toString());
                    //送料
                    if(model.getShippingPrice() != null){
                        params.put(String.format("FeesEstimateRequestList.FeesEstimateRequest.%s.PriceToEstimateFees.Shipping.Amount", cnt), String.format("%.0f", model.getShippingPrice()));
                        params.put(String.format("FeesEstimateRequestList.FeesEstimateRequest.%s.PriceToEstimateFees.Shipping.CurrencyCode", cnt), Currency.JPY.toString());
                    }
                    //Amazonポイント
                    if(model.getAmazonPoint() != null){
                        params.put(String.format("FeesEstimateRequestList.FeesEstimateRequest.%s.PriceToEstimateFees.Points.PointsNumber", cnt), String.format("%d", model.getAmazonPoint()));
                    }
                    cnt++;
                }
            }
            // 共通パラメータを追加。
            setParameter(params);

            HTTPRequest httpRequest = super.createHttpRequest(params);

            // log.info("リクエストを送信します。");
            HTTPResponse httpResponse = fetch(httpRequest);

            checkHttpStatus(httpResponse);

            if(httpResponse.getResponseCode() > 400){
                log.info("ステータスエラー：" + httpResponse.getResponseCode() );
            }

            content = new String(httpResponse.getContent(), DEFAULT_ENCODING);
            GetMyFeesEstimateResponse response = stringToMwsResponseObject(content, GetMyFeesEstimateResponse.class);

            return response;

        } catch (Exception e) {
            if(content != null) {
                log.warning(content);
            }
            throw new MyException(e);
        }
    }

	/**
	 * 指定したSellerSKUに応じた出品者が出品している商品の価格情報を返します。 GetMyPriceForSKU オペレーションは、指定したSellerSKU と MarketplaceId の値に基づき、 出品者が出品している商品の価格情報を返します。出品していない商品のSellerSKUを送信した場合は、 Offers要素が空の状態で返されます。 このオペレーションは、商品の価格情報を最大20件返します。
	 *
	 * @param marketplaceId
	 * @param sellerSKUList
	 * @param itemCondition
	 */
	public GetMyPriceForSKUResponse getMyPriceForSKU(List<String> sellerSkuList, Const.SubCondition itemCondition) {

		String content = null;
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("Action", "GetMyPriceForSKU");
			if(StringUtil.isNotEmpty(marketplaceId)) {
				params.put("MarketplaceId", marketplaceId);
			}
			if(CollectionUtils.isNotEmpty(sellerSkuList)) {
				int cnt = 1;
				for(String sellerSku : sellerSkuList) {
					params.put(String.format("SellerSKUList.SellerSKU.%s", cnt), sellerSku);
					cnt++;
				}
			}
			if(itemCondition != null) {
				params.put("ItemCondition", itemCondition.getCode());
			}
			// 共通パラメータを追加。
			setParameter(params);

			// log.info("GetMyPriceForSKUのリクエストを生成します。");
			HTTPRequest httpRequest = super.createHttpRequest(params);

			// log.info("リクエストを送信します。");
			HTTPResponse httpResponse = fetch(httpRequest);

			checkHttpStatus(httpResponse);

			content = new String(httpResponse.getContent(), DEFAULT_ENCODING);

			// キャストで例外が発生する。
			for(int i = 0; i < 3; i++) {
				try {
					Unmarshaller unmarshaller = getJAXBContext(GetMyPriceForSKUResponse.class).createUnmarshaller();
					GetMyPriceForSKUResponse ret = (GetMyPriceForSKUResponse) unmarshaller.unmarshal(new StringReader(content));
					return ret;
				} catch (ClassCastException e) {
					if(content != null) {
						log.warning(content);
					}
				}
			}
			throw new MyException("失敗しました。");

		} catch (Exception e) {
			if(content != null) {
				log.warning(content);
			}
			throw new MyException(e);
		}
	}

	/**
	 * 指定したASINに応じた出品者が出品している商品の価格情報を返します。 GetMyPriceForASIN オペレーションは、商品を一意に識別するためにMarketplaceId と ASINを指定し、 SKUIdentifier要素を返さない点を除き、GetMyPriceForSKU オペレーションと同様です。
	 *
	 * @param marketplaceId
	 * @param asinList
	 * @param itemCondition
	 */
	public GetMyPriceForASINResponse getMyPriceForASIN(List<String> asinList, Const.SubCondition itemCondition) {
		String content = null;
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("Action", "GetMyPriceForASIN");
			if(StringUtil.isNotEmpty(marketplaceId)) {
				params.put("MarketplaceId", marketplaceId);
			}
			if(CollectionUtils.isNotEmpty(asinList)) {
				int cnt = 1;
				for(String asin : asinList) {
					params.put(String.format("ASINList.ASIN.%s", cnt), asin);
					cnt++;
				}
			}
			if(itemCondition != null) {
				params.put("ItemCondition", itemCondition.getCode());
			}
			// 共通パラメータを追加。
			setParameter(params);

			// log.info("GetMyPriceForASINのリクエストを生成します。");
			HTTPRequest httpRequest = super.createHttpRequest(params);

			// log.info("リクエストを送信します。");
			HTTPResponse httpResponse = fetch(httpRequest);

			checkHttpStatus(httpResponse);

			content = new String(httpResponse.getContent(), DEFAULT_ENCODING);

			Unmarshaller unmarshaller = getJAXBContext(GetMyPriceForASINResponse.class).createUnmarshaller();
			return (GetMyPriceForASINResponse) unmarshaller.unmarshal(new StringReader(content));

		} catch (Exception e) {
			throw new MyException(e);
		}
	}

	/**
	 * 指定したSellerSKUに応じた、親カテゴリーも含む商品が属する商品カテゴリーを返します。 GetProductCategoriesForSKUオペレーションは 、マーケットプレイスのルートまでさかのぼった親カテゴリーも含めた、商品が属する商品カテゴリーを返します。
	 *
	 * @param marketplaceId
	 * @param sku
	 */
	public GetProductCategoriesForSKUResponse getProductCategoriesForSKU(String sku) {
		String content = null;
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("Action", "GetProductCategoriesForSKU");
			if(StringUtil.isNotEmpty(marketplaceId)) {
				params.put("MarketplaceId", marketplaceId);
			}
			if(StringUtil.isNotEmpty(sku)) {
				params.put("SellerSKU", sku);
			}
			// 共通パラメータを追加。
			setParameter(params);

			// log.info("GetProductCategoriesForSKUのリクエストを生成します。");
			HTTPRequest httpRequest = super.createHttpRequest(params);

			// log.info("リクエストを送信します。");
			HTTPResponse httpResponse = fetch(httpRequest);

			checkHttpStatus(httpResponse);

			content = new String(httpResponse.getContent(), DEFAULT_ENCODING);

			Unmarshaller unmarshaller = getJAXBContext(GetProductCategoriesForSKUResponse.class).createUnmarshaller();
			return (GetProductCategoriesForSKUResponse) unmarshaller.unmarshal(new StringReader(content));

		} catch (Exception e) {
			log.info(content);
			throw new MyException(e);
		}
	}

	/**
	 * 指定したASINに応じた、親カテゴリーも含む商品が属する商品カテゴリーを返します。 GetProductCategoriesForASINオペレーションは 、商品を一意に識別するためにMarketplaceIdとASINを指定する点を除き、 GetProductCategoriesForSKU オペレーションと同様です。
	 *
	 * @param marketplaceId
	 * @param asin
	 */
	public GetProductCategoriesForASINResponse getProductCategoriesForASIN(String asin) {
		for(int i = 0; i < 3; i++) {
			try {
				Map<String, String> params = new HashMap<String, String>();
				params.put("Action", "GetProductCategoriesForASIN");
				if(StringUtil.isNotEmpty(marketplaceId)) {
					params.put("MarketplaceId", marketplaceId);
				}
				if(StringUtil.isNotEmpty(asin)) {
					params.put("ASIN", asin);
				}
				// 共通パラメータを追加。
				setParameterForRandom(country, params);

				// log.info("GetProductCategoriesForASINのリクエストを生成します。");
				HTTPRequest httpRequest = super.createHttpRequest(params);

				// log.info("リクエストを送信します。");
				HTTPResponse httpResponse = fetch(httpRequest);

				checkHttpStatus(httpResponse);

				String content = new String(httpResponse.getContent(), DEFAULT_ENCODING);

				Unmarshaller unmarshaller = getJAXBContext(GetProductCategoriesForASINResponse.class).createUnmarshaller();
				return (GetProductCategoriesForASINResponse) unmarshaller.unmarshal(new StringReader(content));

			} catch (Exception e) {
				log.warning(StackTraceUtil.toString(e));
			}
		}
		throw new MyException("getProductCategoriesForASINが3回失敗しました。");
	}

	/**
	 * 商品APIセクションの動作ステータスを返します。 MWS API GetServiceStatus標準に従った標準的メソッドです。GREEN、GREEN_I、YELLOW またはREDのいずれかのステータスを返します。
	 */
	public GetServiceStatusResponse getServiceStatus() {
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("Action", "GetServiceStatus");

			// 共通パラメータを追加。
			super.setParameter(params);

			// log.info("GetServiceStatusのリクエストを生成します。");
			HTTPRequest httpRequest = super.createHttpRequest(params);

			// log.info("リクエストを送信します。");
			HTTPResponse httpResponse = fetch(httpRequest);

			checkHttpStatus(httpResponse);

			String content = new String(httpResponse.getContent(), DEFAULT_ENCODING);

			Unmarshaller unmarshaller = getJAXBContext(GetServiceStatusResponse.class).createUnmarshaller();
			return (GetServiceStatusResponse) unmarshaller.unmarshal(new StringReader(content));

		} catch (Exception e) {
			throw new MyException(e);
		}
	}

	/**
	 * HTTPのステータスコードを解析します。エラーの場合はログに出力します。
	 *
	 * @param httpResponse
	 */
	private void checkHttpStatus(HTTPResponse httpResponse) {
		if(HttpStatus.SC_OK != httpResponse.getResponseCode()) {
			try {
				log.warning(String.format("APIの実行に失敗しました。[%s][%s][%s][%s][%s]", this.shopName, this.accessKeyId, this.secretAccessKey, this.sellerId, this.country));
				log.warning(new String(httpResponse.getContent(), DEFAULT_ENCODING));
			} catch (UnsupportedEncodingException e) {
				log.warning(StackTraceUtil.toString(e));
			}
		}

	}

	/**
	 * 共通のリクエストをセットします。
	 *
	 * @param params
	 */
	protected void setParameterForRandom(Country country, Map<String, String> params) {

		BmMwsProperty bmMwsProperty = MwsKeyUtil.getBmMwsProperty(country);
		// this.accessKeyId = bmMwsProperty.getAccessKeyId();
		// this.secretAccessKey = bmMwsProperty.getSecretAccessKey();
		// this.sellerId = bmMwsProperty.getSellerId();
		// this.country = bmMwsProperty.getCountry();
		// this.marketplaceId = bmMwsProperty.getCountry().getMarketplaceId();
		// this.shopName = bmMwsProperty.getShopName();

		try {
			params.put("MarketplaceId", bmMwsProperty.getCountry().getMarketplaceId());
            params.put("SellerId", bmMwsProperty.getSellerId());

			params.put("Version", PRODUCTS_API_SERVICE_VERSION);
			params.put("SignatureVersion", SIGNATURE_VERSION);
			params.put("Timestamp", getFormattedTimestamp());
			params.put("SignatureMethod", SIGNATURE_METHOD);

			String signature = null;
			if(StringUtil.isNotEmpty(bmMwsProperty.getMwsAuthToken())){
	            params.put("AWSAccessKeyId", DEV_ACCESS_KEY_ID);
	            params.put("MWSAuthToken", bmMwsProperty.getMwsAuthToken());
	            signature = createSignature(bmMwsProperty.getCountry(), DEV_SECRET_ACCESS_KEY_ID, params);
			}else{
	            params.put("AWSAccessKeyId", bmMwsProperty.getAccessKeyId());
			    signature = createSignature(bmMwsProperty.getCountry(), bmMwsProperty.getSecretAccessKey(), params);
			}

			signature = URLEncoder.encode(signature, DEFAULT_ENCODING);
			params.put("Signature", signature);
		} catch (Exception e) {
			throw new MyException(e);
		}
	}


	/*
	 * @Override protected String calculateStringToSignV2(Map<String, String> parameters) throws SignatureException { StringBuilder data = new StringBuilder(); data.append("POST");// POST data.append("\n"); //POST\n URI endpoint = null; try { endpoint = new
	 * URI(country.getProductServiceUrl().toLowerCase()); } catch (URISyntaxException ex) { throw new SignatureException( "URI Syntax Exception thrown while constructing string to sign", ex); } data.append(endpoint.getHost()); data.append("\n"); String uri = "/Products/2011-10-01";
	 * data.append(urlEncode(uri, true)); data.append("\n"); Map<String, String> sorted = new TreeMap<String, String>(); sorted.putAll(parameters); Iterator<Map.Entry<String, String>> pairs = sorted.entrySet().iterator(); while (pairs.hasNext()) { Map.Entry<String, String> pair = pairs.next();
	 * String key = pair.getKey(); data.append(urlEncode(key, false)); data.append("="); String value = pair.getValue(); data.append(urlEncode(value, false)); if (pairs.hasNext()) { data.append("&"); } } return data.toString(); }
	 */

	private boolean pauseIfRetryNeeded(int retries) throws InterruptedException {
		if(retries <= MAX_ERROR_RETRY_COUNT) {
			long delay = (long) (Math.pow(4, retries) * 100L);
			log.info("Retriable error detected, will retry in " + delay + "ms, attempt number: " + retries);
			sleep(delay);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * BtCanStockDetailリストをListListに変換します。 分割時に同一のASINが存在する場合は、APIが例外を吐くためListを別にします。
	 *
	 * @param btCanStockDetailList
	 * @param divide
	 * @return
	 */
	private <T extends AbstractStockDetailModel> List<List<T>> convertAsinListList(List<T> btCanStockDetailList, int divide) {
		List<List<T>> listList = new ArrayList<>();
		List<T> list = new ArrayList<>();
		for(T t : btCanStockDetailList) {
			if(StringUtil.isNotEmpty(t.getAsin()) && t.getAsin().length() == 10) {

				boolean exist = false;
				for(T tmp : list) {
					if(tmp.getAsin().equals(t.getAsin())) {
						exist = true;
						break;
					}
				}
				// 既に同一のASINが登録されている場合はListを分ける。
				if(exist) {
					log.info("既に同一のASINが登録されているためListを分割します。");
					listList.add(list);
					list = new ArrayList<>();

					list.add(t);
					listList.add(list);
					list = new ArrayList<>();
				} else {
					list.add(t);
					if(list.size() == divide) {
						listList.add(list);
						list = new ArrayList<>();
					}
				}

			}
		}
		if(list.size() > 0) {
			listList.add(list);
		}
		return listList;
	}

	/**
	 * BtCanStockDetailリストからSKUのListListに変換します。 分割時に同一のSKUが存在する場合は、APIが例外を吐くためListを別にします。
	 *
	 * @param btCanStockDetailList
	 * @param divide
	 * @param asinExistInclude
	 * @return
	 */
	private <T extends AbstractStockDetailModel> List<List<String>> convertSkuListList(List<T> btCanStockDetailList, int divide, boolean asinExistInclude) {
		List<List<String>> listList = new ArrayList<>();
		List<String> list = new ArrayList<>();
		for(T t : btCanStockDetailList) {
			if(asinExistInclude) {
				// 既に同一のSKUが登録されている場合はListを分ける。
				if(list.contains(t.getSku())) {
					listList.add(list);
					list = new ArrayList<>();
					list.add(t.getSku());
					listList.add(list);
					list = new ArrayList<>();
				} else {
					list.add(t.getSku());
					if(list.size() == divide) {
						listList.add(list);
						list = new ArrayList<>();
					}
				}
			} else {
				if(StringUtil.isEmpty(t.getAsin()) || t.getAsin().length() != 10) {
					if(t.getSku() != null) {
						if(list.contains(t.getSku())) {
							listList.add(list);
							list = new ArrayList<>();
							list.add(t.getSku());
							listList.add(list);
							list = new ArrayList<>();
						} else {
							list.add(t.getSku());
							if(list.size() == divide) {
								listList.add(list);
								list = new ArrayList<>();
							}
						}

					}
				}

			}
		}
		if(CollectionUtils.isNotEmpty(list)) {
			listList.add(list);
		}

		return listList;
	}

	/**
	 * ASINのリストにコンバートします。
	 *
	 * @param list
	 * @return
	 */
	private <T extends AbstractStockDetailModel> List<String> convertAsinList(List<T> list, boolean benchmark) {
		List<String> strList = new ArrayList<>();
		for(T t : list) {
		    if(StringUtil.isNotEmpty(t.getBenchmarkAsin()) && benchmark){
		        strList.add(t.getBenchmarkAsin());
		    }else{
		        strList.add(t.getAsin());
		    }
		}
		// log.info("ASIN = " + strList.toString());
		return strList;
	}

	private <T extends AbstractStockDetailModel> List<String> convertSkuList(List<T> list) {
		List<String> strList = new ArrayList<>();
		for(T t : list) {
			strList.add(t.getSku());
		}
		return strList;
	}

	/**
	 * APIを実行したセラー情報をログに書き込みます。
	 */
	private void writeSellerLog() {
		log.warning(String.format("[%s][%s][%s]", this.country.getName(), this.accessKeyId, this.shopName));
	}

	/**
	 * 受け取ったListを5個単位でリストを詰め直します。
	 *
	 * @param idList
	 * @return
	 */
	private List<List<String>> divideIdList(List<String> idList, int devide) {
		List<List<String>> strListList = new ArrayList<>();
		List<String> strList = new ArrayList<>();
		for(String str : idList) {

			// 既に同一のASINが登録されている場合はListを分ける。SKUの場合はこのタイミングじゃ分からない。
			if(strList.contains(str)) {
				// log.info("既に同一のASINが登録されているためListを分割します。");
				strListList.add(strList);
				strList = new ArrayList<>();

				strList.add(str);
				strListList.add(strList);
				strList = new ArrayList<>();
			} else {
				strList.add(str);
				if(strList.size() == devide) {
					strListList.add(strList);
					strList = new ArrayList<>();
				}
			}

		}
		// 残りがあれば詰める
		if(strList.size() > 0) {
			strListList.add(strList);
		}

		return strListList;
	}

}
