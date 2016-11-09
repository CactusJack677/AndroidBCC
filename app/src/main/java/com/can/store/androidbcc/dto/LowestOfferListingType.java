
package com.can.store.androidbcc.dto;

import com.amazonservices.mws.products.model.QualifiersType;
import com.can.store.androidbcc.Const;
import com.can.store.androidbcc.Const.Currency;
import com.can.store.androidbcc.Const.DelFlg;
import com.can.store.androidbcc.Const.FeedbackRating;
import com.can.store.androidbcc.Const.SalesRoute;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.logging.Logger;


/**
 * <p>Java class for LowestOfferListingType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="LowestOfferListingType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Qualifiers" type="{http://mws.amazonservices.com/schema/Products/2011-10-01}QualifiersType"/>
 *         &lt;element name="NumberOfOfferListingsConsidered" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="SellerFeedbackCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Price" type="{http://mws.amazonservices.com/schema/Products/2011-10-01}PriceType"/>
 *         &lt;element name="MultipleOffersAtLowestPrice" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
public class LowestOfferListingType implements Serializable {

    private static final long serialVersionUID = 1L;
    public final static Logger log = Logger.getLogger(LowestOfferListingType.class.getName());

    protected QualifiersType qualifiers;
    protected Integer numberOfOfferListingsConsidered;
    protected int sellerFeedbackCount;
    protected PriceType price;
    protected String multipleOffersAtLowestPrice;

    //===========================
    // BCC拡張プロパティ
    /** 削除Flg */
    private DelFlg delFlg;

    /** コンディションのズレを調整した金額 */
    private float conditionAdjustPrice;
    
    public float getConditionAdjustPrice() {
        return conditionAdjustPrice;
    }

    public void setConditionAdjustPrice(float conditionAdjustPrice) {
        this.conditionAdjustPrice = conditionAdjustPrice;
    }

    private String delReason;

    public Const.SubCondition getSubCondition(){
        return bccGetSubCondition();
    }

    public SalesRoute getSalesRoute(){
        return bccGetSalesRoute();
    }
    public float getTotalPrice(){
        return bccGetTotalPrice();
    }
    public FeedbackRating getFeedbackRating(){
        return bccGetFeedbackRating();
    }
    public float getShippingPrice(){
        return bccGetShippingPrice();
    }



    /**
     * 出荷経路を返却します。存在しない場合NULLを返します。<br/>
     *  ASIN SKU
     *  商品検索？　？
     *  自社検索？　？
     *  最安検索？　？
     *  カート検索？　？
     * </table>
     * @return
     */
    public SalesRoute bccGetSalesRoute(){
        SalesRoute salesRoute = SalesRoute.find(getQualifiers().getFulfillmentChannel());
        return salesRoute;
    }

    /**
     * FBAかどうかを判定します。<br/>
     *  ASIN SKU
     *  商品検索？　？
     *  自社検索？　？
     *  最安検索？　？
     *  カート検索？　？
     * </table>
     * @return
     */
    public boolean isFba(){
        SalesRoute salesRoute = bccGetSalesRoute();
        if(salesRoute == null || salesRoute.equals(SalesRoute.MERCHANT)){
            return false;
        }
        return true;
    }

    /**
     * FBAかどうかを判定します。<br/>
     *  ASIN SKU
     *  商品検索？　？
     *  自社検索？　？
     *  最安検索？　？
     *  カート検索？　？
     * </table>
     * @return
     */
    public Const.SubCondition bccGetSubCondition(){
        if(isSetQualifiers()){
            return Const.SubCondition.findCode(getQualifiers().getItemSubcondition());
        }
        return null;
    }


    /**
     * 通貨を返却します。存在しない場合NULLを返します。<br/>
     *  ASIN SKU
     *  商品検索？　？
     *  自社検索？　？
     *  最安検索？　？
     *  カート検索？　？
     * </table>
     * @return
     */
    public Currency bccGetCurrency(){
        if(isSetPrice() && getPrice().isSetListingPrice()){
            Currency[] currencies = Currency.values();
            for (Currency t : currencies) {
                if(t.equals(getPrice().getListingPrice().currencyCode)){
                    return t;
                }
            }
        }
        return null;
    }


    /**
     * 送料を含む販売金額を取得します。<br/>
     *  ASIN SKU
     *  商品検索？　？
     *  自社検索？　？
     *  最安検索？　？
     *  カート検索？　？
     * </table>
     * @return
     */
    public float bccGetTotalPrice(){
        if(isSetPrice()){
            BigDecimal landedPrice = getPrice().landedPrice.amount;
            return landedPrice.floatValue();
        }
        return 0F;
    }


    /**
     * 合計金額を取得します。<br/>
     *  ASIN SKU
     *  商品検索？　？
     *  自社検索？　？
     *  最安検索？　？
     *  カート検索？　？
     * </table>
     * @return
     */
    public Float bccGetShippingPrice(){
        if(isSetPrice()){
            BigDecimal amount = getPrice().shipping.amount;
            return amount.floatValue();
        }
        return null;
    }
    /**
     * フィードバック件数を返却します。存在しない場合0を返します。<br/>
     *  ASIN SKU
     *  商品検索？　？
     *  自社検索？　？
     *  最安検索？　？
     *  カート検索？　？
     * </table>
     * @return
     */
    public int bccGetFeedBackCount(){
        if(isSetSellerFeedbackCount()){
            return getSellerFeedbackCount();
        }else{
            return 0;
        }
    }

    /**
     * 評価を返却します。存在しない場合NULLを返します。<br/>
     *  ASIN SKU
     *  商品検索？　？
     *  自社検索？　？
     *  最安検索？　？
     *  カート検索？　？
     * </table>
     * @return
     */
    public FeedbackRating bccGetFeedbackRating(){
        if(isSetQualifiers() && getQualifiers().isSetSellerPositiveFeedbackRating()){
            return FeedbackRating.find(getQualifiers().getSellerPositiveFeedbackRating());
        }
        return null;
    }

    /**
     * Default constructor
     *
     */
    public LowestOfferListingType() {
        super();
    }

    /**
     * Value constructor
     *
     */
    public LowestOfferListingType(final QualifiersType qualifiers, final Integer numberOfOfferListingsConsidered, final int sellerFeedbackCount, final PriceType price, final String multipleOffersAtLowestPrice) {
        this.qualifiers = qualifiers;
        this.numberOfOfferListingsConsidered = numberOfOfferListingsConsidered;
        this.sellerFeedbackCount = sellerFeedbackCount;
        this.price = price;
        this.multipleOffersAtLowestPrice = multipleOffersAtLowestPrice;
    }

    /**
     * Gets the value of the qualifiers property.
     *
     * @return
     *     possible object is
     *     {@link QualifiersType }
     *
     */
    public QualifiersType getQualifiers() {
        return qualifiers;
    }

    /**
     * Sets the value of the qualifiers property.
     *
     * @param value
     *     allowed object is
     *     {@link QualifiersType }
     *
     */
    public void setQualifiers(QualifiersType value) {
        this.qualifiers = value;
    }

    public boolean isSetQualifiers() {
        return (this.qualifiers!= null);
    }

    /**
     * Gets the value of the numberOfOfferListingsConsidered property.
     *
     * @return
     *     possible object is
     *     {@link Integer }
     *
     */
    public Integer getNumberOfOfferListingsConsidered() {
        return numberOfOfferListingsConsidered;
    }

    /**
     * Sets the value of the numberOfOfferListingsConsidered property.
     *
     * @param value
     *     allowed object is
     *     {@link Integer }
     *
     */
    public void setNumberOfOfferListingsConsidered(Integer value) {
        this.numberOfOfferListingsConsidered = value;
    }

    public boolean isSetNumberOfOfferListingsConsidered() {
        return (this.numberOfOfferListingsConsidered!= null);
    }

    /**
     * Gets the value of the sellerFeedbackCount property.
     *
     */
    public int getSellerFeedbackCount() {
        return sellerFeedbackCount;
    }

    /**
     * Sets the value of the sellerFeedbackCount property.
     *
     */
    public void setSellerFeedbackCount(int value) {
        this.sellerFeedbackCount = value;
    }

    public boolean isSetSellerFeedbackCount() {
        return true;
    }

    /**
     * Gets the value of the price property.
     *
     * @return
     *     possible object is
     *     {@link PriceType }
     *
     */
    public PriceType getPrice() {
        return price;
    }

    /**
     * Sets the value of the price property.
     *
     * @param value
     *     allowed object is
     *     {@link PriceType }
     *
     */
    public void setPrice(PriceType value) {
        this.price = value;
    }

    public boolean isSetPrice() {
        return (this.price!= null);
    }

    /**
     * Gets the value of the multipleOffersAtLowestPrice property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getMultipleOffersAtLowestPrice() {
        return multipleOffersAtLowestPrice;
    }

    /**
     * Sets the value of the multipleOffersAtLowestPrice property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setMultipleOffersAtLowestPrice(String value) {
        this.multipleOffersAtLowestPrice = value;
    }

    public boolean isSetMultipleOffersAtLowestPrice() {
        return (this.multipleOffersAtLowestPrice!= null);
    }

    /**
     * Sets the value of the Qualifiers property.
     *
     * @param value
     * @return
     *     this instance
     */
    public LowestOfferListingType withQualifiers(QualifiersType value) {
        setQualifiers(value);
        return this;
    }

    /**
     * Sets the value of the NumberOfOfferListingsConsidered property.
     *
     * @param value
     * @return
     *     this instance
     */
    public LowestOfferListingType withNumberOfOfferListingsConsidered(Integer value) {
        setNumberOfOfferListingsConsidered(value);
        return this;
    }

    /**
     * Sets the value of the SellerFeedbackCount property.
     *
     * @param value
     * @return
     *     this instance
     */
    public LowestOfferListingType withSellerFeedbackCount(int value) {
        setSellerFeedbackCount(value);
        return this;
    }

    /**
     * Sets the value of the Price property.
     *
     * @param value
     * @return
     *     this instance
     */
    public LowestOfferListingType withPrice(PriceType value) {
        setPrice(value);
        return this;
    }

    /**
     * Sets the value of the MultipleOffersAtLowestPrice property.
     *
     * @param value
     * @return
     *     this instance
     */
    public LowestOfferListingType withMultipleOffersAtLowestPrice(String value) {
        setMultipleOffersAtLowestPrice(value);
        return this;
    }



    /**
     *
     * XML fragment representation of this object
     *
     * @return XML fragment for this object. Name for outer
     * tag expected to be set by calling method. This fragment
     * returns inner properties representation only
     */
    public String toXMLFragment() {
        StringBuffer xml = new StringBuffer();
        if (isSetQualifiers()) {
            QualifiersType  qualifiers = getQualifiers();
            xml.append("<Qualifiers>");
            xml.append(qualifiers.toXMLFragment());
            xml.append("</Qualifiers>");
        }
        if (isSetNumberOfOfferListingsConsidered()) {
            xml.append("<NumberOfOfferListingsConsidered>");
            xml.append(getNumberOfOfferListingsConsidered() + "");
            xml.append("</NumberOfOfferListingsConsidered>");
        }
        if (isSetSellerFeedbackCount()) {
            xml.append("<SellerFeedbackCount>");
            xml.append(getSellerFeedbackCount() + "");
            xml.append("</SellerFeedbackCount>");
        }
        if (isSetPrice()) {
            PriceType  price = getPrice();
            xml.append("<Price>");
            xml.append(price.toXMLFragment());
            xml.append("</Price>");
        }
        if (isSetMultipleOffersAtLowestPrice()) {
            xml.append("<MultipleOffersAtLowestPrice>");
            xml.append(escapeXML(getMultipleOffersAtLowestPrice()));
            xml.append("</MultipleOffersAtLowestPrice>");
        }
        return xml.toString();
    }

    /**
     *
     * Escape XML special characters
     */
    private String escapeXML(String string) {
        if (string == null)
            return "null";
        StringBuffer sb = new StringBuffer();
        int length = string.length();
        for (int i = 0; i < length; ++i) {
            char c = string.charAt(i);
            switch (c) {
            case '&':
                sb.append("&amp;");
                break;
            case '<':
                sb.append("&lt;");
                break;
            case '>':
                sb.append("&gt;");
                break;
            case '\'':
                sb.append("&#039;");
                break;
            case '"':
                sb.append("&quot;");
                break;
            default:
                sb.append(c);
            }
        }
        return sb.toString();
    }



    /**
     *
     * JSON fragment representation of this object
     *
     * @return JSON fragment for this object. Name for outer
     * object expected to be set by calling method. This fragment
     * returns inner properties representation only
     *
     */
    protected String toJSONFragment() {
        StringBuffer json = new StringBuffer();
        boolean first = true;
        if (isSetQualifiers()) {
            if (!first) json.append(", ");
            json.append("\"Qualifiers\" : {");
            QualifiersType  qualifiers = getQualifiers();


            json.append(qualifiers.toJSONFragment());
            json.append("}");
            first = false;
        }
        if (isSetNumberOfOfferListingsConsidered()) {
            if (!first) json.append(", ");
            json.append(quoteJSON("NumberOfOfferListingsConsidered"));
            json.append(" : ");
            json.append(quoteJSON(getNumberOfOfferListingsConsidered() + ""));
            first = false;
        }
        if (isSetSellerFeedbackCount()) {
            if (!first) json.append(", ");
            json.append(quoteJSON("SellerFeedbackCount"));
            json.append(" : ");
            json.append(quoteJSON(getSellerFeedbackCount() + ""));
            first = false;
        }
        if (isSetPrice()) {
            if (!first) json.append(", ");
            json.append("\"Price\" : {");
            PriceType  price = getPrice();


            json.append(price.toJSONFragment());
            json.append("}");
            first = false;
        }
        if (isSetMultipleOffersAtLowestPrice()) {
            if (!first) json.append(", ");
            json.append(quoteJSON("MultipleOffersAtLowestPrice"));
            json.append(" : ");
            json.append(quoteJSON(getMultipleOffersAtLowestPrice()));
            first = false;
        }
        return json.toString();
    }

    /**
     *
     * Quote JSON string
     */
    private String quoteJSON(String string) {
        if (string == null)
            return "null";
        StringBuffer sb = new StringBuffer();
        sb.append("\"");
        int length = string.length();
        for (int i = 0; i < length; ++i) {
            char c = string.charAt(i);
            switch (c) {
            case '"':
                sb.append("\\\"");
                break;
            case '\\':
                sb.append("\\\\");
                break;
            case '/':
                sb.append("\\/");
                break;
            case '\b':
                sb.append("\\b");
                break;
            case '\f':
                sb.append("\\f");
                break;
            case '\n':
                sb.append("\\n");
                break;
            case '\r':
                sb.append("\\r");
                break;
            case '\t':
                sb.append("\\t");
                break;
            default:
                if (c <  ' ') {
                    sb.append("\\u" + String.format("%03x", Integer.valueOf(c)));
                } else {
                sb.append(c);
            }
        }
        }
        sb.append("\"");
        return sb.toString();
    }

    public DelFlg getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(DelFlg delFlg) {
        this.delFlg = delFlg;
    }

    public String getDelReason() {
        return delReason;
    }

    public void setDelReason(String delReason) {
        this.delReason = delReason;
    }


}
