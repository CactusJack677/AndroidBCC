package com.can.store.androidbcc.dto;

import com.can.store.androidbcc.Const;
import com.can.store.androidbcc.Const.ParentNode;
import com.can.store.androidbcc.Const.SubCondition;
import com.can.store.androidbcc.util.CollectionUtils;
import com.can.store.androidbcc.util.DateUtil;
import com.can.store.androidbcc.util.StringUtil;
import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 最適化結果Dto
 * サーバ側で処理をさせた結果を格納するDtoです。
 * @author kitazawatakuya
 *
 */
public class  OptimazeResult implements Serializable{

    private static final long serialVersionUID = 1L;

    /** ASIN */
    @Expose(serialize = true, deserialize = true)
    private String asin;
    @Expose(serialize = true, deserialize = true)
    private String parentAsin;
    /** ean */
    @Expose(serialize = true, deserialize = true)
    private String ean;
    /** upc */
    @Expose(serialize = true, deserialize = true)
    private String upc;
    /** 画像パス */
    @Expose(serialize = true, deserialize = true)
    private String imageUrl;
    /** 画像パス */
    @Expose(serialize = true, deserialize = true)
    private String subImageUrl;
    /** サブ画像パス2 */
    @Expose(serialize = true, deserialize = true)
    private String subImageUrl2;
    /** サブ画像パス3 */
    @Expose(serialize = true, deserialize = true)
    private String subImageUrl3;
    @Expose(serialize = true, deserialize = true)
    private String brand;

    @Expose(serialize = true, deserialize = true)
    private SalesRankType rank;

    private String categoryRankName;

    private Integer categoryRankNo;

    @Expose(serialize = true, deserialize = true)
    private String path;
    
    @Expose(serialize = true, deserialize = true)
    private String productCategory;

    /** DBに存在するかどうか */
    private boolean exist;

    /** 在庫切れ厨かどうか */
    private boolean stockEmpty;

    @Expose(serialize = true, deserialize = true)
    private boolean pantry;

	/** 改定結果 */
    @Expose(serialize = true, deserialize = true)
    private Result result;
    /** 出品日 */
    private Date postedDate;
    /** 商品サイズ */
    @Expose(serialize = true, deserialize = true)
    private ProductSize productSize;

	private Integer pageNo;

	private boolean fba;

	/** 商品の仕様（モバイルから取得） */
	private String specification;

	/** 登録情報（モバイルから取得） */
	private String information;

	/** 商品の説明（モバイルから取得） */
	private String description;

	/** 製造番号（モバイルから取得） */
	private String reference;

	/** プロセスNo */
	private String processNo;

	/** 直販有無 */
	private boolean chokuhan;

	/** 直販価格 */
	private Integer chokuhanPrice;

	/** 直販コメント */
	private String chokuhanComment;

	/** 直販の出品者一覧上の順番 */
	private Integer chokuhanPosition;

    /** 発売日 */
    @Expose(serialize = true, deserialize = true)
    private Date releaseDate;

    /** 発売日 */
	private String releaseDateStr;

	/** タイトル */
    @Expose(serialize = true, deserialize = true)

    private String title;
    /** サブコンディション */
    private Const.SubCondition subCondition;

    /** 改定前の価格 */
    private float oldPrice;

    /** 送料 */
    private float shippingPrice;

    /** 価格改定結果の金額 */
    @Expose(serialize = true, deserialize = true)
    private Float newPrice;

    /** 経過日数 */
    private Integer periodDays;

    /** 価格差 */
    private float priceDifference;

    /** 改定時刻 */
    private Date changeDate;

    /** 対象のセラー情報 */
    @Expose(serialize = true, deserialize = true)
    private List<LowestOfferListingType> lowestOfferListingList;

    /** カート価格（毎のサブコンディション） */
    @Expose
    private CompetitivePriceType competitivePriceType;

    /** Amazonで販売されている商品点数 */
    private int competitiveCnt;

    /** 商品カテゴリー */
    @Expose(serialize = true, deserialize = true)
    private ParentNode parentNode;

    /** アダルトかどうか */
    @Expose(serialize = true, deserialize = true)
    private boolean adultFlg;

    /** 前回価格改定結果 */
    private Float beforePrice;

	/** カテゴリー（Scraping用） */
	private String categoryId;

	/** キーワード（Scraping用） */
	private String keywords;

	/** 参考価格 */
    @Expose(serialize = true, deserialize = true)
	private Float listPrice;

    @Expose(serialize = true, deserialize = true)
    private String merchantName;

    @Expose(serialize = true, deserialize = true)
    private String availability;

    @Expose(serialize = true, deserialize = true)
    private String attribute;

	private RegistStatus registStatus;

	private String ngKeyword;

    @Expose(serialize = true, deserialize = true)
	private Integer fbaFee;


	private int newCnt;

	private int usedCnt;


	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}


	/** スクレイプしたURL */
	private String searchUrl;

    //コンストラクタ
    public OptimazeResult() {
        this.result = Result.EXECUTE;
        this.lowestOfferListingList = new ArrayList<LowestOfferListingType>();
    }

    public String getDownloadPath(String path, String asin){
    	if(path.endsWith("/")){
    		return String.format("%s%s/", path, asin.substring(0, 4));
    	}else{
    		return String.format("%s/%s/", path, asin.substring(0, 4));
    	}
    }

    public static String toCsvHeader(){
    	String ret = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s\r\n",
    			"asin",
    			"upc",
    			"ean",
    			"adultFlg",
    			"title",
    			"brand",
    			"releaseDate",
    			"imageUrl",
    			"subImageUrl1",
    			"subImageUrl2",
    			"subImageUrl3",
    			"newPrice",
    			"merchantName",
    			"availability",
    			"parentNode",
    			"width",
    			"height",
    			"length",
    			"totalSize",
    			"weight",
    			"new lowest price",
    			"used lowest price"
    			);

    	return ret;
    }

    public String toCsv(){

    	ParentNode node = this.parentNode;
    	if(this.parentNode == null){
    		node = ParentNode.JP_UNKNOWN;
    	}
    	if(this.productSize == null){
    		this.productSize = new ProductSize();
    	}

    	String ret = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%.2f,%s,%s,%s,%s,%s,%s,%s,%s,%s,%.0f,%.2f,%.2f,%.2f,%s,%d,%s,%d,%s\r\n",
    			escape(this.parentAsin),
    			escape(this.asin),
				escape(this.upc),
				escape(this.ean),
    			this.adultFlg,
    			escape(this.title),
    			escape(this.brand),
    			DateUtil.formatDate(this.releaseDate, "yyyyMMdd"),
    			escape(getLargeImage(this.imageUrl)),
    			this.subImageUrl,
    			this.subImageUrl2,
    			this.subImageUrl3,
    			this.newPrice,
    			isFbaStock(),
    			escape(this.merchantName),
    			this.availability,
    			escape(node.getName()),
    			this.productSize.getWidth(),
    			this.productSize.getHeight(),
    			this.productSize.getLength(),
    			this.productSize.getTotalSize(),
    			escape(this.attribute),
    			this.productSize.getWeight(),
    			getNewLowestPrice(),
    			getUsedLowestPrice(),
    			this.listPrice,
    			this.rank.getProductCategoryId(),
    			this.rank.getRank(),
    			this.isChokuhan(),
    			this.getChokuhanPrice(),
    			escape(this.getChokuhanComment())
    			);

    	ret = StringUtil.escapeUtf8ToSjisCharacter(ret);

    	return ret;
    }

    private String isFbaStock(){
    	if(this.result == null || !this.result.revision){
    		return "N";
    	}

    	if(this.newPrice == null){
    		return "N";
    	}
    	return "Y";
    }

    public float getNewLowestPrice(){
    	if(CollectionUtils.isEmpty(this.lowestOfferListingList)){
    		return 0;
    	}

    	float lowestPrice = 1000000;
    	for (LowestOfferListingType t : this.lowestOfferListingList) {
    		if(SubCondition.NEW.equals(t.bccGetSubCondition())){
    			float price = t.getTotalPrice();
    			if(lowestPrice > price){
    				lowestPrice = price;
    			}
    		}
		}
    	return lowestPrice;
    }

    public float getUsedLowestPrice(){
    	if(CollectionUtils.isEmpty(this.lowestOfferListingList)){
    		return 0;
    	}

    	float lowestPrice = 1000000;
    	for (LowestOfferListingType t : this.lowestOfferListingList) {
    		if(!SubCondition.NEW.equals(t.bccGetSubCondition())){
    			float price = t.getTotalPrice();
    			if(lowestPrice > price){
    				lowestPrice = price;
    			}
    		}
		}
    	return lowestPrice;
    }

    public String toScrapeCsv(){

    	String ret = String.format("%s\t%s\t%s\t%s\t%s\t%s\r\n",
    			escape(this.asin),
    			escape(this.title),
    			escape(getLargeImage(this.imageUrl)),
    			escape(this.categoryId),
    			escape(this.keywords),
    			escape(this.searchUrl)
    			);

    	return ret;
    }



	private String escape(String str){
		if(StringUtil.isEmpty(str)){
			return "";
		}
		String tmp = str
		.replaceAll("\"", "\"\"")
//		.replaceAll("\r\n", "\n")
//		.replaceAll("\n", "")
		;
		return String.format("\"%s\"", tmp);
	}


    public Float getNewPrice() {
        return newPrice;
    }
    public void setNewPrice(Float newPrice) {
        this.newPrice = newPrice;
    }
    public float getPriceDifference() {
        return priceDifference;
    }
    public void setPriceDifference(float priceDifference) {
        this.priceDifference = priceDifference;
    }
    public Date getChangeDate() {
        return changeDate;
    }
    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }
    public String getAsin() {
        return asin;
    }
    public void setAsin(String asin) {
        this.asin = asin;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Const.SubCondition getSubCondition() {
        return subCondition;
    }
    public void setSubCondition(Const.SubCondition subCondition) {
        this.subCondition = subCondition;
    }
    public List<LowestOfferListingType> getLowestOfferListingList() {
        return lowestOfferListingList;
    }
    public void setLowestOfferListingList(List<LowestOfferListingType> lowestOfferListingList) {
        this.lowestOfferListingList = lowestOfferListingList;
    }
    public Date getPostedDate() {
        return postedDate;
    }
    public void setPostedDate(Date postedDate) {
        this.postedDate = postedDate;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public Integer getPeriodDays() {
        return periodDays;
    }
    public void setPeriodDays(Integer periodDays) {
        this.periodDays = periodDays;
    }


    public Result getResult() {
        return result;
    }
    public void setResult(Result result) {
        this.result = result;
    }


    public ParentNode getParentNode() {
        return parentNode;
    }


    public void setParentNode(ParentNode parentNode) {
        this.parentNode = parentNode;
    }


    public float getShippingPrice() {
        return shippingPrice;
    }


    public void setShippingPrice(float shippingPrice) {
        this.shippingPrice = shippingPrice;
    }


    public int getCompetitiveCnt() {
        return competitiveCnt;
    }


    public void setCompetitiveCnt(int competitiveCnt) {
        this.competitiveCnt = competitiveCnt;
    }


    public CompetitivePriceType getCompetitivePriceType() {
        return competitivePriceType;
    }


    public void setCompetitivePriceType(CompetitivePriceType competitivePriceType) {
        this.competitivePriceType = competitivePriceType;
    }


    public float getOldPrice() {
        return oldPrice;
    }


    public void setOldPrice(float oldPrice) {
        this.oldPrice = oldPrice;
    }


    public Date getReleaseDate() {
        return releaseDate;
    }


    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }


    public boolean isAdultFlg() {
        return adultFlg;
    }


    public void setAdultFlg(boolean adultFlg) {
        this.adultFlg = adultFlg;
    }


    public String getEan() {
        return ean;
    }


    public void setEan(String ean) {
        this.ean = ean;
    }


    public String getUpc() {
        return upc;
    }


    public void setUpc(String upc) {
        this.upc = upc;
    }


    public Float getBeforePrice() {
        return beforePrice;
    }


    public void setBeforePrice(Float beforePrice) {
        this.beforePrice = beforePrice;
    }


    public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}


	public boolean isFba() {
		return fba;
	}

	public void setFba(boolean fba) {
		this.fba = fba;
	}



    private String getLargeImage(String url){
    	if(url == null){
    		return null;
    	}
		//http://ecx.images-amazon.com/images/I/51v-6zrhEFL._AA160_.jpg
        String regex = "(http.*images/[A-Z]{1}\\/.*)\\.(.*)(\\.jpg)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(url);
        while (matcher.find()) {
            String str = matcher.group(1) + matcher.group(3);
            if(StringUtil.isNotEmpty(str)){
            	return str;
            }
        }
        return url;
    }


	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}


	public String getAvailability() {
		return availability;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
	}

	public String getReleaseDateStr() {
		return releaseDateStr;
	}

	public void setReleaseDateStr(String releaseDateStr) {
		this.releaseDateStr = releaseDateStr;
	}


	public ProductSize getProductSize() {
		return productSize;
	}

	public void setProductSize(ProductSize productSize) {
		this.productSize = productSize;
	}


	public String getSubImageUrl() {
		return subImageUrl;
	}

	public void setSubImageUrl(String subImageUrl) {
		this.subImageUrl = subImageUrl;
	}


	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}


	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}


	public String getSubImageUrl2() {
		return subImageUrl2;
	}

	public void setSubImageUrl2(String subImageUrl2) {
		this.subImageUrl2 = subImageUrl2;
	}


	public String getSubImageUrl3() {
		return subImageUrl3;
	}

	public void setSubImageUrl3(String subImageUrl3) {
		this.subImageUrl3 = subImageUrl3;
	}


	public String getSearchUrl() {
		return searchUrl;
	}

	public void setSearchUrl(String searchUrl) {
		this.searchUrl = searchUrl;
	}


	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}


	public SalesRankType getRank() {
		return rank;
	}

	public void setRank(SalesRankType rank) {
		this.rank = rank;
	}

	public String getParentAsin() {
		return parentAsin;
	}

	public void setParentAsin(String parentAsin) {
		this.parentAsin = parentAsin;
	}


	public Float getListPrice() {
		return listPrice;
	}

	public void setListPrice(Float listPrice) {
		this.listPrice = listPrice;
	}


	public boolean isChokuhan() {
		return chokuhan;
	}

	public void setChokuhan(boolean chokuhan) {
		this.chokuhan = chokuhan;
	}


	public Integer getChokuhanPrice() {
		return chokuhanPrice;
	}

	public void setChokuhanPrice(Integer chokuhanPrice) {
		this.chokuhanPrice = chokuhanPrice;
	}



	public String getChokuhanComment() {
		return chokuhanComment;
	}

	public void setChokuhanComment(String chokuhanComment) {
		this.chokuhanComment = chokuhanComment;
	}


	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}


	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public String getProcessNo() {
		return processNo;
	}

	public void setProcessNo(String processNo) {
		this.processNo = processNo;
	}


	public String getCategoryRankName() {
		return categoryRankName;
	}

	public void setCategoryRankName(String categoryRankName) {
		this.categoryRankName = categoryRankName;
	}


	public Integer getCategoryRankNo() {
		return categoryRankNo;
	}

	public void setCategoryRankNo(Integer categoryRankNo) {
		this.categoryRankNo = categoryRankNo;
	}


	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}


	public RegistStatus getRegistStatus() {
		return registStatus;
	}

	public void setRegistStatus(RegistStatus registStatus) {
		this.registStatus = registStatus;
	}


	public boolean isExist() {
		return exist;
	}

	public void setExist(boolean exist) {
		this.exist = exist;
	}


	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}


	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}


	public boolean isPantry() {
		return pantry;
	}

	public void setPantry(boolean pantry) {
		this.pantry = pantry;
	}


	public String getNgKeyword() {
		return ngKeyword;
	}

	public void setNgKeyword(String ngKeyword) {
		this.ngKeyword = ngKeyword;
	}


	public boolean isStockEmpty() {
		return stockEmpty;
	}

	public void setStockEmpty(boolean stockEmpty) {
		this.stockEmpty = stockEmpty;
	}


	public Integer getFbaFee() {
		return fbaFee;
	}

	public void setFbaFee(Integer fbaFee) {
		this.fbaFee = fbaFee;
	}


	public Integer getChokuhanPosition() {
		return chokuhanPosition;
	}

	public void setChokuhanPosition(Integer chokuhanPosition) {
		this.chokuhanPosition = chokuhanPosition;
	}


	public int getNewCnt() {
		return newCnt;
	}

	public void setNewCnt(int newCnt) {
		this.newCnt = newCnt;
	}


	public int getUsedCnt() {
		return usedCnt;
	}

	public void setUsedCnt(int usedCnt) {
		this.usedCnt = usedCnt;
	}


	/**
     * 最適化したかどうか、また最適化しなかった場合になぜ最適化しなかったのかを保持します。
     * @author kitazawatakuya
     *
     */
    public enum Result {
        EXECUTE("通常の最適化を行いました。" , true, true),
        COMPETITION_NOTHING_SKIP("他社がいないため最適化をスキップしました。" , true, false),
        BASE_NEW("上位ランキング設定で新品を対象に最適化を行いました。" , true, true),
        SKIP("改定条件にマッチしないため最適化しませんでした。" , false, false),
        SKIP_FIXED("固定価格のため最適化しませんでした。" , false, false),
        COMPETITIONLIMIT_RATE_SKIP("値下げ競争対策のため最適化しませんでした。" , false, false),
        DOWN_LIMIT_RATE_SKIP("急落したため最適化しませんでした。" , false, false),
        PERIOD_PLAN_LOWEST("経過期間の設定に合わせて最適化しました。" , true, true),
        STOPPER_SKIP("最適化を試みましたがストッパーより下限となったため最適化しませんでした。" , true, false),
        STOPPER_SKIP_EXECUTE("ストッパーよりも価格が安いためストッパー価格に合わせました。" , true, true),
        PERIOD_PLAN_NORMAL("長期売れてませんが通常の改定方法で最適化しました。" , true, true),
        ERROR("自社商品を検索しても存在しなかったため最適化出来ませんでした。" , false, false),
        REFERENCE_PRICE("参考価格で最適化を行いました。" , true, true),

        ;

        private String name;
        private boolean stopperCheck;
        private boolean revision;

        private Result(String name, boolean stopperCheck, boolean revision) {
            this.name = name;
            this.stopperCheck = stopperCheck;
            this.revision = revision;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isStopperCheck() {
            return stopperCheck;
        }

        public void setStopperCheck(boolean stopperCheck) {
            this.stopperCheck = stopperCheck;
        }

        public boolean isRevision() {
            return revision;
        }

        public void setRevision(boolean revision) {
            this.revision = revision;
        }


    }


	/**
     * 掲載ステータス
     * @author kitazawatakuya
     *
     */
    public enum RegistStatus {
        SUCCESS("掲載可能です" , true),
        NG_ASIN("ASINがNGです。" , false),
        NG_STRING("キーワードがNGです。" , false),
        NG_ADULT("アダルトでNGです。" , false),
        NG_JAN("JANがNGです。" , false),
        NG_PRICE("価格がNULLです。" , false),
        NG_PANTRY("パントリーです。" , false),

        ;

        private String name;
        private boolean regist;

        private RegistStatus(String name, boolean regist) {
            this.name = name;
            this.regist = regist;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

		public boolean isRegist() {
			return regist;
		}

		public void setRegist(boolean regist) {
			this.regist = regist;
		}


    }



}
