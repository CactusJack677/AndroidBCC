package com.can.store.androidbcc;

import com.amazonservices.mws.products.MarketplaceWebServiceProductsConfig;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Const {

	// ================================
	// 環境系
	public final static String DEFAULT_CONTENT_TYPE = "UTF-8";
	public final static String JSON_CONTENT_TYPE = "text/html; charset=UTF-8;";

	// ================================
	// ADM管理ログインID
	public final static String[] ADM_ACOUNTS = { "" +
			// "storecan37@gmail.com",
			// "jackpot667@gmail.com",
		"test@example.com",
			// "mentanpin129@gmail.com",
			// "katumsa2@gmail.com"
	};

	// チソウメールアドレス
	public final static String CHISO_MAIL_ADDRESS = "world39fba@gmail.com";

	// ================================
	// 非営業日
	public final static String[] holidays = { "2016/04/30", "2016/05/01", "2016/05/02", "2016/05/03", "2016/05/04", "2016/05/05" };

	// ================================
	// URL

	public final static String BCC_DOMAIN = "store-can.appspot.com";
	public final static String BCC_SITE_URL = "https://" + BCC_DOMAIN;
	public final static String BCC_LOGIN_RELATIVE_URL = "/bcc/login";
	public final static String MEGAPON_LOGIN_RELATIVE_URL = "/megapon/login";
	public final static String BCC_LOGIN_URL = BCC_SITE_URL + BCC_LOGIN_RELATIVE_URL;
	public final static String THANKS_MAIL_DISCOUNT_PERIOD = "12";

	// ================================
	// LPサイト

	/** LPサイトのURL */
	public final static String LP_SITE_URL = "http://creative-adventure-network.appspot.com/lp/bcc/";

	/** LPサイトのURL */
	public final static String LP_SITE_URL2 = "https://store-can.appspot.com/lp/sales";
	/** LPサイトのURL */
	public final static String LP_CC_SITE_URL = "https://store-can.appspot.com/lp/cc";

	/** OPTYのLPサイトのURL */
	public final static String LP_OPTY_SITE_URL = "https://store-can.appspot.com/lp/opty";

	/** OPTYのLPサイトのURL */
	public final static String LP_CAPPY_SITE_URL = "https://store-can.appspot.com/lp/cappy";

	/** LPサイトのURL */
	public final static String LP_THANKSMAIL_URL = "https://store-can.appspot.com/lp/thanksmail";

	/** LPサイトのURL */
	public final static String LP_TOOL_LIMITLESS_URL = "https://store-can.appspot.com/lp/toolLimitless";

	/** バーコードリーダーLPのURL */
	public final static String LP__BERCODE_SITE_URL = "http://creative-adventure-network.appspot.com/lp/bercode/";
	/** LP→会員登録の中継Cookie吐き出しコントローラーのURL */
	public final static String LP_REDIRECT_URL = "https://store-can.appspot.com/bcc/detail/";

	public final static String LP_FURIKOMISAKI = "浅草支店（618）\r\n" + "普通\r\n" + "1004159\r\n" + "株式会社Creative Adventure Network\r\n";

	// ================================
	// HTTPレスポンス
	/** 正常 */
	public final static int HTTP_STATUS_SUCCESS = 200;
	/** 異常 */
	public final static int HTTP_STATUS_SYSTEM_ERROR = 500;

	// ================================
	// メール系
	// ADM宛メール受信アドレス
	public final static String MAIL_ADMIN_SEND_ADDR = "bcc-info@store-can.com";

	// ================================
	// クッキー
	public final static String BCC_AUTH_COOKIE_NAME = "oiniaesfs";
	public final static String MEGAPON_AUTH_COOKIE_NAME = "oiniaesfs";
	public final static String COKKIE_CIPHER = "s8j34kd7";
	public final static String BCC_AUTH_COOKIE_DISP_TIME = "1sjr74mv";
	public final static String MEGAPON_AUTH_COOKIE_DISP_TIME = "sg39d9nl";

	public static final Map<String, String> SHELF_NO_MAP = new LinkedHashMap<String, String>() {
		private static final long serialVersionUID = 1L;
		{
			put("未定", "未定");
		}
		{
			put("1redA", "1階red Aエリア");
		}
		{
			put("1blueA", "1階blue Aエリア");
		}
		{
			put("1yellowA", "1階yellow Aエリア");
		}
		{
			put("1greenA", "1階green Aエリア");
		}
		{
			put("1pinkA", "1階pink Aエリア");
		}
		{
			put("1orangeA", "1階orange Aエリア");
		}
		{
			put("1purpleA", "1階purple Aエリア");
		}

		{
			put("2redA", "2階red Aエリア");
		}
		{
			put("2blueA", "2階blue Aエリア");
		}
		{
			put("2yellowA", "2階yellow Aエリア");
		}
		{
			put("2greenA", "2階green Aエリア");
		}
		{
			put("2pinkA", "2階pink Aエリア");
		}
		{
			put("2orangeA", "2階orange Aエリア");
		}
		{
			put("2purpleA", "2階purple Aエリア");
		}

		{
			put("3redA", "3階red Aエリア");
		}
		{
			put("3blueA", "3階blue Aエリア");
		}
		{
			put("3yellowA", "3階yellow Aエリア");
		}
		{
			put("3greenA", "3階green Aエリア");
		}
		{
			put("3pinkA", "3階pink Aエリア");
		}
		{
			put("3orangeA", "3階orange Aエリア");
		}
		{
			put("3purpleA", "3階purple Aエリア");
		}
		{
			put("1redB", "1階red Bエリア");
		}
		{
			put("1blueB", "1階blue Bエリア");
		}
		{
			put("1yellowB", "1階yellow Bエリア");
		}
		{
			put("1greenB", "1階green Bエリア");
		}
		{
			put("1pinkB", "1階pink Bエリア");
		}
		{
			put("1orangeB", "1階orange Bエリア");
		}
		{
			put("1purpleB", "1階purple Bエリア");
		}

		{
			put("2redB", "2階red Bエリア");
		}
		{
			put("2blueB", "2階blue Bエリア");
		}
		{
			put("2yellowB", "2階yellow Bエリア");
		}
		{
			put("2greenB", "2階green Bエリア");
		}
		{
			put("2pinkB", "2階pink Bエリア");
		}
		{
			put("2orangeB", "2階orange Bエリア");
		}
		{
			put("2purpleB", "2階purple Bエリア");
		}

		{
			put("3redB", "3階red Bエリア");
		}
		{
			put("3blueB", "3階blue Bエリア");
		}
		{
			put("3yellowB", "3階yellow Bエリア");
		}
		{
			put("3greenB", "3階green Bエリア");
		}
		{
			put("3pinkB", "3階pink Bエリア");
		}
		{
			put("3orangeB", "3階orange Bエリア");
		}
		{
			put("3purpleB", "3階purple Bエリア");
		}
		{
			put("1redC", "1階red Cエリア");
		}
		{
			put("1blueC", "1階blue Cエリア");
		}
		{
			put("1yellowC", "1階yellow Cエリア");
		}
		{
			put("1greenC", "1階green Cエリア");
		}
		{
			put("1pinkC", "1階pink Cエリア");
		}
		{
			put("1orangeC", "1階orange Cエリア");
		}
		{
			put("1purpleC", "1階purple Cエリア");
		}

		{
			put("2redC", "2階red Cエリア");
		}
		{
			put("2blueC", "2階blue Cエリア");
		}
		{
			put("2yellowC", "2階yellow Cエリア");
		}
		{
			put("2greenC", "2階green Cエリア");
		}
		{
			put("2pinkC", "2階pink Cエリア");
		}
		{
			put("2orangeC", "2階orange Cエリア");
		}
		{
			put("2purpleC", "2階purple Cエリア");
		}

		{
			put("3redC", "3階red Cエリア");
		}
		{
			put("3blueC", "3階blue Cエリア");
		}
		{
			put("3yellowC", "3階yellow Cエリア");
		}
		{
			put("3greenC", "3階green Cエリア");
		}
		{
			put("3pinkC", "3階pink Cエリア");
		}
		{
			put("3orangeC", "3階orange Cエリア");
		}
		{
			put("3purpleC", "3階purple Cエリア");
		}
		{
			put("1redD", "1階red Dエリア");
		}
		{
			put("1blueD", "1階blue Dエリア");
		}
		{
			put("1yellowD", "1階yellow Dエリア");
		}
		{
			put("1greenD", "1階green Dエリア");
		}
		{
			put("1pinkD", "1階pink Dエリア");
		}
		{
			put("1orangeD", "1階orange Dエリア");
		}
		{
			put("1purpleD", "1階purple Dエリア");
		}

		{
			put("2redD", "2階red Dエリア");
		}
		{
			put("2blueD", "2階blue Dエリア");
		}
		{
			put("2yellowD", "2階yellow Dエリア");
		}
		{
			put("2greenD", "2階green Dエリア");
		}
		{
			put("2pinkD", "2階pink Dエリア");
		}
		{
			put("2orangeD", "2階orange Dエリア");
		}
		{
			put("2purpleD", "2階purple Dエリア");
		}

		{
			put("3redD", "3階red Dエリア");
		}
		{
			put("3blueD", "3階blue Dエリア");
		}
		{
			put("3yellowD", "3階yellow Dエリア");
		}
		{
			put("3greenD", "3階green Dエリア");
		}
		{
			put("3pinkD", "3階pink Dエリア");
		}
		{
			put("3orangeD", "3階orange Dエリア");
		}
		{
			put("3purpleD", "3階purple Dエリア");
		}
		{
			put("1redE", "1階red Eエリア");
		}
		{
			put("1blueE", "1階blue Eエリア");
		}
		{
			put("1yellowE", "1階yellow Eエリア");
		}
		{
			put("1greenE", "1階green Eエリア");
		}
		{
			put("1pinkE", "1階pink Eエリア");
		}
		{
			put("1orangeE", "1階orange Eエリア");
		}
		{
			put("1purpleE", "1階purple Eエリア");
		}

		{
			put("2redE", "2階red Eエリア");
		}
		{
			put("2blueE", "2階blue Eエリア");
		}
		{
			put("2yellowE", "2階yellow Eエリア");
		}
		{
			put("2greenE", "2階green Eエリア");
		}
		{
			put("2pinkE", "2階pink Eエリア");
		}
		{
			put("2orangeE", "2階orange Eエリア");
		}
		{
			put("2purpleE", "2階purple Eエリア");
		}

		{
			put("3redE", "3階red Eエリア");
		}
		{
			put("3blueE", "3階blue Eエリア");
		}
		{
			put("3yellowE", "3階yellow Eエリア");
		}
		{
			put("3greenE", "3階green Eエリア");
		}
		{
			put("3pinkE", "3階pink Eエリア");
		}
		{
			put("3orangeE", "3階orange Eエリア");
		}
		{
			put("3purpleE", "3階purple Eエリア");
		}
		{
			put("1redF", "1階red Fエリア");
		}
		{
			put("1blueF", "1階blue Fエリア");
		}
		{
			put("1yellowF", "1階yellow Fエリア");
		}
		{
			put("1greenF", "1階green Fエリア");
		}
		{
			put("1pinkF", "1階pink Fエリア");
		}
		{
			put("1orangeF", "1階orange Fエリア");
		}
		{
			put("1purpleF", "1階purple Fエリア");
		}

		{
			put("2redF", "2階red Fエリア");
		}
		{
			put("2blueF", "2階blue Fエリア");
		}
		{
			put("2yellowF", "2階yellow Fエリア");
		}
		{
			put("2greenF", "2階green Fエリア");
		}
		{
			put("2pinkF", "2階pink Fエリア");
		}
		{
			put("2orangeF", "2階orange Fエリア");
		}
		{
			put("2purpleF", "2階purple Fエリア");
		}

		{
			put("3redF", "3階red Fエリア");
		}
		{
			put("3blueF", "3階blue Fエリア");
		}
		{
			put("3yellowF", "3階yellow Fエリア");
		}
		{
			put("3greenF", "3階green Fエリア");
		}
		{
			put("3pinkF", "3階pink Fエリア");
		}
		{
			put("3orangeF", "3階orange Fエリア");
		}
		{
			put("3purpleF", "3階purple Fエリア");
		}
		{
			put("1redG", "1階red Gエリア");
		}
		{
			put("1blueG", "1階blue Gエリア");
		}
		{
			put("1yellowG", "1階yellow Gエリア");
		}
		{
			put("1greenG", "1階green Gエリア");
		}
		{
			put("1pinkG", "1階pink Gエリア");
		}
		{
			put("1orangeG", "1階orange Gエリア");
		}
		{
			put("1purpleG", "1階purple Gエリア");
		}

		{
			put("2redG", "2階red Gエリア");
		}
		{
			put("2blueG", "2階blue Gエリア");
		}
		{
			put("2yellowG", "2階yellow Gエリア");
		}
		{
			put("2greenG", "2階green Gエリア");
		}
		{
			put("2pinkG", "2階pink Gエリア");
		}
		{
			put("2orangeG", "2階orange Gエリア");
		}
		{
			put("2purpleG", "2階purple Gエリア");
		}

		{
			put("3redG", "3階red Gエリア");
		}
		{
			put("3blueG", "3階blue Gエリア");
		}
		{
			put("3yellowG", "3階yellow Gエリア");
		}
		{
			put("3greenG", "3階green Gエリア");
		}
		{
			put("3pinkG", "3階pink Gエリア");
		}
		{
			put("3orangeG", "3階orange Gエリア");
		}
		{
			put("3purpleG", "3階purple Gエリア");
		}
		{
			put("1redH", "1階red Hエリア");
		}
		{
			put("1blueH", "1階blue Hエリア");
		}
		{
			put("1yellowH", "1階yellow Hエリア");
		}
		{
			put("1greenH", "1階green Hエリア");
		}
		{
			put("1pinkH", "1階pink Hエリア");
		}
		{
			put("1orangeH", "1階orange Hエリア");
		}
		{
			put("1purpleH", "1階purple Hエリア");
		}

		{
			put("2redH", "2階red Hエリア");
		}
		{
			put("2blueH", "2階blue Hエリア");
		}
		{
			put("2yellowH", "2階yellow Hエリア");
		}
		{
			put("2greenH", "2階green Hエリア");
		}
		{
			put("2pinkH", "2階pink Hエリア");
		}
		{
			put("2orangeH", "2階orange Hエリア");
		}
		{
			put("2purpleH", "2階purple Hエリア");
		}

		{
			put("3redH", "3階red Hエリア");
		}
		{
			put("3blueH", "3階blue Hエリア");
		}
		{
			put("3yellowH", "3階yellow Hエリア");
		}
		{
			put("3greenH", "3階green Hエリア");
		}
		{
			put("3pinkH", "3階pink Hエリア");
		}
		{
			put("3orangeH", "3階orange Hエリア");
		}
		{
			put("3purpleH", "3階purple Hエリア");
		}
		{
			put("1redI", "1階red Iエリア");
		}
		{
			put("1blueI", "1階blue Iエリア");
		}
		{
			put("1yellowI", "1階yellow Iエリア");
		}
		{
			put("1greenI", "1階green Iエリア");
		}
		{
			put("1pinkI", "1階pink Iエリア");
		}
		{
			put("1orangeI", "1階orange Iエリア");
		}
		{
			put("1purpleI", "1階purple Iエリア");
		}

		{
			put("2redI", "2階red Iエリア");
		}
		{
			put("2blueI", "2階blue Iエリア");
		}
		{
			put("2yellowI", "2階yellow Iエリア");
		}
		{
			put("2greenI", "2階green Iエリア");
		}
		{
			put("2pinkI", "2階pink Iエリア");
		}
		{
			put("2orangeI", "2階orange Iエリア");
		}
		{
			put("2purpleI", "2階purple Iエリア");
		}

		{
			put("3redI", "3階red Iエリア");
		}
		{
			put("3blueI", "3階blue Iエリア");
		}
		{
			put("3yellowI", "3階yellow Iエリア");
		}
		{
			put("3greenI", "3階green Iエリア");
		}
		{
			put("3pinkI", "3階pink Iエリア");
		}
		{
			put("3orangeI", "3階orange Iエリア");
		}
		{
			put("3purpleI", "3階purple Iエリア");
		}
		{
			put("1redJ", "1階red Jエリア");
		}
		{
			put("1blueJ", "1階blue Jエリア");
		}
		{
			put("1yellowJ", "1階yellow Jエリア");
		}
		{
			put("1greenJ", "1階green Jエリア");
		}
		{
			put("1pinkJ", "1階pink Jエリア");
		}
		{
			put("1orangeJ", "1階orange Jエリア");
		}
		{
			put("1purpleJ", "1階purple Jエリア");
		}

		{
			put("2redJ", "2階red Jエリア");
		}
		{
			put("2blueJ", "2階blue Jエリア");
		}
		{
			put("2yellowJ", "2階yellow Jエリア");
		}
		{
			put("2greenJ", "2階green Jエリア");
		}
		{
			put("2pinkJ", "2階pink Jエリア");
		}
		{
			put("2orangeJ", "2階orange Jエリア");
		}
		{
			put("2purpleJ", "2階purple Jエリア");
		}

		{
			put("3redJ", "3階red Jエリア");
		}
		{
			put("3blueJ", "3階blue Jエリア");
		}
		{
			put("3yellowJ", "3階yellow Jエリア");
		}
		{
			put("3greenJ", "3階green Jエリア");
		}
		{
			put("3pinkJ", "3階pink Jエリア");
		}
		{
			put("3orangeJ", "3階orange Jエリア");
		}
		{
			put("3purpleJ", "3階purple Jエリア");
		}
		{
			put("1redK", "1階red Kエリア");
		}
		{
			put("1blueK", "1階blue Kエリア");
		}
		{
			put("1yellowK", "1階yellow Kエリア");
		}
		{
			put("1greenK", "1階green Kエリア");
		}
		{
			put("1pinkK", "1階pink Kエリア");
		}
		{
			put("1orangeK", "1階orange Kエリア");
		}
		{
			put("1purpleK", "1階purple Kエリア");
		}

		{
			put("2redK", "2階red Kエリア");
		}
		{
			put("2blueK", "2階blue Kエリア");
		}
		{
			put("2yellowK", "2階yellow Kエリア");
		}
		{
			put("2greenK", "2階green Kエリア");
		}
		{
			put("2pinkK", "2階pink Kエリア");
		}
		{
			put("2orangeK", "2階orange Kエリア");
		}
		{
			put("2purpleK", "2階purple Kエリア");
		}

		{
			put("3redK", "3階red Kエリア");
		}
		{
			put("3blueK", "3階blue Kエリア");
		}
		{
			put("3yellowK", "3階yellow Kエリア");
		}
		{
			put("3greenK", "3階green Kエリア");
		}
		{
			put("3pinkK", "3階pink Kエリア");
		}
		{
			put("3orangeK", "3階orange Kエリア");
		}
		{
			put("3purpleK", "3階purple Kエリア");
		}
		{
			put("1redL", "1階red Lエリア");
		}
		{
			put("1blueL", "1階blue Lエリア");
		}
		{
			put("1yellowL", "1階yellow Lエリア");
		}
		{
			put("1greenL", "1階green Lエリア");
		}
		{
			put("1pinkL", "1階pink Lエリア");
		}
		{
			put("1orangeL", "1階orange Lエリア");
		}
		{
			put("1purpleL", "1階purple Lエリア");
		}

		{
			put("2redL", "2階red Lエリア");
		}
		{
			put("2blueL", "2階blue Lエリア");
		}
		{
			put("2yellowL", "2階yellow Lエリア");
		}
		{
			put("2greenL", "2階green Lエリア");
		}
		{
			put("2pinkL", "2階pink Lエリア");
		}
		{
			put("2orangeL", "2階orange Lエリア");
		}
		{
			put("2purpleL", "2階purple Lエリア");
		}

		{
			put("3redL", "3階red Lエリア");
		}
		{
			put("3blueL", "3階blue Lエリア");
		}
		{
			put("3yellowL", "3階yellow Lエリア");
		}
		{
			put("3greenL", "3階green Lエリア");
		}
		{
			put("3pinkL", "3階pink Lエリア");
		}
		{
			put("3orangeL", "3階orange Lエリア");
		}
		{
			put("3purpleL", "3階purple Lエリア");
		}
		{
			put("1redM", "1階red Mエリア");
		}
		{
			put("1blueM", "1階blue Mエリア");
		}
		{
			put("1yellowM", "1階yellow Mエリア");
		}
		{
			put("1greenM", "1階green Mエリア");
		}
		{
			put("1pinkM", "1階pink Mエリア");
		}
		{
			put("1orangeM", "1階orange Mエリア");
		}
		{
			put("1purpleM", "1階purple Mエリア");
		}

		{
			put("2redM", "2階red Mエリア");
		}
		{
			put("2blueM", "2階blue Mエリア");
		}
		{
			put("2yellowM", "2階yellow Mエリア");
		}
		{
			put("2greenM", "2階green Mエリア");
		}
		{
			put("2pinkM", "2階pink Mエリア");
		}
		{
			put("2orangeM", "2階orange Mエリア");
		}
		{
			put("2purpleM", "2階purple Mエリア");
		}

		{
			put("3redM", "3階red Mエリア");
		}
		{
			put("3blueM", "3階blue Mエリア");
		}
		{
			put("3yellowM", "3階yellow Mエリア");
		}
		{
			put("3greenM", "3階green Mエリア");
		}
		{
			put("3pinkM", "3階pink Mエリア");
		}
		{
			put("3orangeM", "3階orange Mエリア");
		}
		{
			put("3purpleM", "3階purple Mエリア");
		}

	};

	// ================================
	// 集荷

	// 戸田 =====================
	public final static String TODA_PLACE = "335-0035　埼玉県戸田市笹目南町18-18";
	public final static String TODA_PHONE = "048-423-0201";

	public final static String DELIVERY_TO_ADDR_TODA_FORMAT = TODA_PLACE + "\r\n" + "　<font color=\"#f00\">%s %s %s</font>宛\r\n" + "　" + TODA_PHONE + "\r\n";

	public final static String DELIVERY_TO_ADDR_TEXT_TODA_FORMAT = TODA_PLACE + "\r\n" + "　Store can %s %s %s宛\r\n" + "　" + TODA_PHONE + "\r\n";

	public final static String DELIVERY_TO_DENNOU_ADDR_TODA_FORMAT = TODA_PLACE + "\r\n" + "　<font color=\"#f00\">%s %s %s</font>宛\r\n" + "　%s\r\n";

	// HPS =====================
	public final static String HPS_PLACE = "290-0225 千葉県市原市牛久881−1 株式会社アクティベイト　アマゾンFBA荷受係";
	public final static String HPS_PHONE = "0436-67-1234";

	public final static String DELIVERY_TO_ADDR_HPS_FORMAT = HPS_PLACE + "\r\n" + "　<font color=\"#f00\">%s %s %s</font>宛\r\n" + "　" + HPS_PHONE + "\r\n";

	public final static String DELIVERY_TO_ADDR_TEXT_HPS_FORMAT = HPS_PLACE + "\r\n" + "　Store can %s %s %s宛\r\n" + "　" + HPS_PHONE + "\r\n";

	public final static String DELIVERY_TO_DENNOU_ADDR_HPS_FORMAT = HPS_PLACE + "\r\n" + "　<font color=\"#f00\">%s %s %s</font>宛\r\n" + "　%s\r\n";

	/** eFax用の顧客CD（現状eFaxを利用していないため不要） */
	public final static String DELIVERY_KOKYAKU_CD = "13287441000";

	/** キャンポイントの振込先。 */
	public final static String BANK_ACCOUNT_NUMBER = "　楽天銀行 <br/>" + "　第一営業支店(251) <br/>" + "　普通 7283374 <br/>" + "　カブシキガイシャクリエイティブアドベンチャーネットワーク";

	/** 一度の集荷上限数 */
	public final static int DELIVERY_MAX_CNT = 3000;

	// ================================
	// エラーメッセージ系
	public final static String ERR_MSG_NINSHO_ERROR_CD = "authError";
	public final static String ERR_MSG_NINSHO_ERROR_MSG = "認証エラーです。ログインしてからやり直してください。<a href=\"/bcc/login\">ログインはこちら</a>" + "<SCRIPT type=\"text/javascript\">\r\n" + "<!-- \r\n" + "setTimeout(\"link()\", 3000);\r\n" + "function link(){location.href='/bcc/logout/';\r\n" + "}\r\n" + "-->\r\n" + "</SCRIPT>";
	public final static String ERR_MSG_COORDINATE_ERROR_MSG = "コーディネーター権限がありません。";

	public final static String ERR_MSG_KENGEN_ERROR_CD = "kengenError";
	public final static String ERR_MSG_KENGEN_ERROR_MSG = "権限エラーです。こちらの機能を利用できる権限がありません。";
	public final static String ERR_MSG_KENGEN_ERROR_EXCEPTION_MSG = "権限がありません。";

	// ================================
	// Ajax系
	public final static String JSON_STATUS_SUCSESS = "success";
	public final static String JSON_STATUS_WARN = "warn";
	public final static String JSON_STATUS_ERROR = "error";
	public final static String JSON_ERROR_FORMAT = "エラーが発生しました。[%s]";

	// ================================
	// せどり風神・雷神
	public final static String FUJIN_DOMAIN = "http://system-sedori.com";
	public final static String FUJIN_ADM_URL = FUJIN_DOMAIN + "/admin/";

	// ================================
	// 入力チェック正規表現
	/**
	 * 正規表現-日付 ○20130101 ☓201311
	 */
	public final static String REGEX_YYYYMMDD_SIMPLE = "^(19|20)[0-9]{2}(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01])$";
	/**
	 * 正規表現-日付 ○2011/01/32 ○2011/01/1 ○2013/1/1
	 */
	public final static String REGEX_YYYYMMDD_SLASH = "^[0-9]{4}/[01]?[0-9]/[0123]?[0-9]$";
	/**
	 * 正規表現-日付 ○2011-01-32 ○2011-01-1 ○2013-1-1(HTML5の日付型用に追加 taka)
	 */
	public final static String REGEX_YYYYMMDD_HYPHEN = "^[0-9]{4}-[01]?[0-9]-[0123]?[0-9]$";
	/** ひらがな・カタカナ・数値・記号（全ての文字がOK） */
	public final static String REGEX_KANA_HIRA = "^[\\u30A0-\\u30FF]+[ 　]*[\\u30A0-\\u30FF]+$|^[\\u3040-\\u309F]+[ 　]*[\\u3040-\\u309F]+$|";
	/** 整数(プラスのみ) */
	public final static String REGEX_NUMBER = "^[0-9]+$";
	/** 整数＋少数どちらも可 */
	public final static String REGEX_NUMBER_DECIMAL_ALL = "^(([0-9]+)|0)(\\.([0-9]+))?$";
	/** 整数3ケタ＋少数4ケタの数値用 */
	public final static String REGEX_NUMBER_DECIMAL = "^([1-9]\\d{0,3}|0)(\\.\\d{0,4})?$";
	/** (プラス、マイナス可、少数不可) */
	public final static String REGEX_NUMBER_ALL = "^[-]?[0-9]+(¥.[0-9]+)?$";

	public final static String REGEX_YEAR = "^(19|20)[\\d]{2}+$";
	public final static String REGEX_MONTH = "^(0[1-9]|1[0-2])|[1-9]";
	public final static String REGEX_DAY = "^(0[1-3]|1[0-9]|2[0-9]|3[0-1])|[1-9]";
	public final static String REGEX_PHONE = "^[0-9]+$|^[0-9]+-+[0-9]+-+[0-9]+$";
	public final static String REGEX_MAIL_ADDRESS = "[\\w\\.\\-]+@(?:[\\w\\-]+\\.)+[\\w\\-]+";
	public final static String REGEX_PASSWORD = "^[a-zA-Z0-9]+$";
	public final static String REGEX_AZ = "^[A-Z]+$";
	public final static String REGEX_AZ09 = "^[A-Z0-9]+$";
	public final static String REGEX_azAZ09 = "^[a-zA-Z0-9]+$";
	public final static String REGEX_FUJIN_ID = "^id[\\d]{6}+$";
	public final static String REGEX_1BYTE_CHAR = "[^\\x01-\\x7E]+";

	public final static String REGEX_BOOL = "true|false";
	public final static String REGEX_SEX = "man|woman";

	public final static String REGEX_SKU = "^([A-Z0-9]+-?)+$";
	public final static String REGEX_ASIN = "^[A-Z0-9]{10}$";

	/**
	 * 正規表現管理Enum
	 *
	 * @author kitazawatakuya
	 *
	 */
	public static enum RegexType {
		DATE(new String[] { REGEX_YYYYMMDD_SIMPLE + "|" + REGEX_YYYYMMDD_SLASH + "|" + REGEX_YYYYMMDD_HYPHEN }), //
		KANA_HIRA(new String[] { REGEX_KANA_HIRA }), //
		NUMBER_DECIMAL_ALL(new String[] { REGEX_NUMBER_DECIMAL_ALL }), //
		NUMBER_DECIMAL(new String[] { REGEX_NUMBER_DECIMAL }), //
		NUMBER(new String[] { REGEX_NUMBER }), //
		NUMBER_ALL(new String[] { REGEX_NUMBER_ALL }), //
		YEAR(new String[] { REGEX_YEAR }), //
		MONTH(new String[] { REGEX_MONTH }), //
		DAY(new String[] { REGEX_DAY }), //
		PHONE(new String[] { REGEX_PHONE }), //
		MAIL_ADDRESS(new String[] { REGEX_MAIL_ADDRESS }), //
		PASSWORD(new String[] { REGEX_PASSWORD }), //
		SEX(new String[] { REGEX_SEX }), //
		FUJIN_ID(new String[] { REGEX_FUJIN_ID }), //
		BOOL(new String[] { REGEX_BOOL }), //
		AZ(new String[] { REGEX_AZ }), //
		AZ09(new String[] { REGEX_AZ09 }), //
		azAZ09(new String[] { REGEX_azAZ09 }), //
		SKU(new String[] { REGEX_SKU }), //
		ASIN(new String[] { REGEX_ASIN }),
		/** 半角英数 */
		HALF_WIDTH_ALPHANUMERIC(new String[] { "^[a-zA-Z0-9]+$" });

		private RegexType(String[] s) {
			this.values = s;
		}

		private String[] values;

		public String[] getValues() {
			return values;
		}

		public void setValues(String[] values) {
			this.values = values;
		}
	}

	// ================================
	// 風神連携サービス
	public final static String FUJIN_KEY = "v0qhef0d";
	public final static String FUJIN_ENTRY = "http://system-sedori.com/regist7/index.php?name=%s&email=%s";
	public final static int FUJIN_STATUS_CD_SUCCESS = 1;
	public final static int FUJIN_STATUS_CD_USER_ERROR = 2;
	public final static int FUJIN_STATUS_CD_SYSTEM_ERROR = 3;

	/**
	 * 削除フラグ
	 */
	public static enum DelFlg implements BaseEnum {
		UNDELETE("利用可能", "利用可能"), DELETE("停止中", "停止中");

		private String status;
		private String name;

		private DelFlg(String status, String name) {
			this.setStatus(status);
			this.setName(name);
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getStatus() {
			return status;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		@Override
		public String getJsonDispName() {
			return this.getName();
		}

		@Override
		public String getJsonDescription() {
			return null;
		}

		@Override
		public String getJsonLabelClass() {
			return null;
		}

		@Override
		public String getJsonButtonClass() {
			return null;
		}
	}

	// ================================
	// 権限種別
	public static enum Role implements BaseEnum {
		ALL_ADM("管理者（全体）", true, 6), SERVICE_ADM("管理者（塾講師）", true, 5), AFFILIATOR("アフィリエーター", false, 4), BCC("BCCユーザー", false, 3), MEGAPON("めがポン", false, 2), EC("ECサイトユーザー", false, 1), SAGAWA("被ログイン者(佐川配送用)", false, 0), NONE("権限なし", false, 0);

		private String name;
		private boolean adm;
		private int role;

		// コンストラクタ
		private Role(String name, boolean isAdm, int role) {
			this.setName(name);
			this.setAdm(isAdm);
			this.setRole(role);
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setAdm(boolean adm) {
			this.adm = adm;
		}

		public boolean isAdm() {
			return adm;
		}

		public void setRole(int role) {
			this.role = role;
		}

		public int getRole() {
			return role;
		}

		@Override
		public String getJsonDispName() {
			return this.getName();
		}

		@Override
		public String getJsonDescription() {
			return null;
		}

		@Override
		public String getJsonLabelClass() {
			return null;
		}

		@Override
		public String getJsonButtonClass() {
			return null;
		}

	}

	// ================================
	// レポート作成状況 旧在庫、注文レポートで利用してるため削除できない。
	public static enum ReportStatus implements BaseEnum {
		NONE("未作成"), EXECUTE("処理中"), STEP1_COMP("1.amazon作成完了"), STEP2_COMP("2.非利用"), STEP3_COMP("3.在庫レポート取り込み完了"), STEP4_COMP("4.自社価格取得完了"), STEP5_COMP("5.カート価格取得完了"), STEP6_COMP("6.最低価格取得完了");

		private String name;

		// コンストラクタ
		private ReportStatus(String name) {
			this.setName(name);
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		@Override
		public String getJsonDispName() {
			return this.getName();
		}

		@Override
		public String getJsonDescription() {
			return null;
		}

		@Override
		public String getJsonLabelClass() {
			return null;
		}

		@Override
		public String getJsonButtonClass() {
			return null;
		}
	}

	// ================================
	// レポート作成進行状態
	public static enum ReportDetailStatus implements BaseEnum {
		STEP2_COMP("ASIN登録完了"), STEP3_COMP("タイトル・画像登録完了"), NNOTHING("画像・タイトルが取れない");

		private String name;

		// コンストラクタ
		private ReportDetailStatus(String name) {
			this.setName(name);
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		@Override
		public String getJsonDispName() {
			return this.getName();
		}

		@Override
		public String getJsonDescription() {
			return null;
		}

		@Override
		public String getJsonLabelClass() {
			return null;
		}

		@Override
		public String getJsonButtonClass() {
			return null;
		}
	}


	// ストア
	public enum Store implements BaseEnum {

		ACTIVE_PT("リサイクルストリート", "#DBFF71", "AKIAJOJ7V6TNKXH3JNNQ", "WjO2UPNAuCqCj1bIHsZOCP8cKK2l6LbR7p9MH9yk", "StoreCan", "1.0", "A3USB4R7XNEHV3", "A1VC38T7YXB528", "https://mws.amazonservices.jp/", "https://mws.amazonservices.jp/Products/2011-10-01", Country.JAPAN, "", "", DelFlg.DELETE, true), //
		GARIMAKE("ガリバーマーケット", "#93FFAB", "AKIAJQW63FQH5MXPOQBQ", "jvY5mqc13G7wq9Y8b/ni9oCT/ResdaB8YW1xb9es", "StoreCan", "1.0", "A18KVJM1KMEMKL", "A1VC38T7YXB528", "https://mws.amazonservices.jp/", "https://mws.amazonservices.jp/Products/2011-10-01", Country.JAPAN, "", "", DelFlg.DELETE, true), //
		AKANE("あかねいろ", "#E4FF8D", "AKIAJROAS3LGTCVQR24Q", "P3BEh2IA/0fiyCM4jrMpkC02IgX2Rr8zxUyyfn4W", "StoreCan", "1.0", "AJH3IJH376TF2", "A1VC38T7YXB528", "https://mws.amazonservices.jp/", "https://mws.amazonservices.jp/Products/2011-10-01", Country.JAPAN, "36147", "8326", DelFlg.DELETE, false), //
		TOKURO("トクロー", "#E4FF8D", "AKIAIUR3PY76O3RNRPXA", "uODCXeuL9fN1PFPP6eigNLbFtf4LWGQz0F4USyBf", "StoreCan", "1.0", "A30JYCHIXAVFMB", "A1VC38T7YXB528", "https://mws.amazonservices.jp/", "https://mws.amazonservices.jp/Products/2011-10-01", Country.JAPAN, "36147", "8326", DelFlg.DELETE, true), //
		N_STORE("n-store", "#d0b0ff", "AKIAIBRLUKYV5PZRBV3A", "DqTFt6fxEmgfcgw09cEMAwNXCK4ZzdGQOvOfNOl7", "StoreCan", "1.0", "A2KLINKMOZJJ71", "A1VC38T7YXB528", "https://mws.amazonservices.jp/", "https://mws.amazonservices.jp/Products/2011-10-01", Country.JAPAN, "36147", "8326", DelFlg.DELETE, true),

		KADEN_PC("家電・PC・カメラ・エレクトニックス", "#8EF1FF", "AKIAJQ32Z6V6MT776FZQ", "blIhybs+2sRlz7cFIUsxXAPoA/Cun7DEg/CpBgKS", "StoreCan", "1.0", "A38HNY2KZCS4O6", "A1VC38T7YXB528", "https://mws.amazonservices.jp/", "https://mws.amazonservices.jp/Products/2011-10-01", Country.JAPAN, "", "", DelFlg.DELETE, true), //
		SERENO("Sereno Education College", "#7DC0FF", "AKIAJLHGCYMVNTEWKZTA", "QnTEWjqxAEgrfVWGRl7nH3eH5dsPt6uUzgWUeTOA", "StoreCan", "1.0", "A3VRJIF5RSCRPN", "A1VC38T7YXB528", "https://mws.amazonservices.jp/", "https://mws.amazonservices.jp/Products/2011-10-01", Country.JAPAN, "", "", DelFlg.DELETE, true), //
		DARA("DORA DORA BAZAR", "#FFA9A7", "AKIAJSG4M5NR7O4UARXQ", "AOaLjVrcFAtn2VRFzgIeQKwSO7LiOmyetuRLCgs/", "StoreCan", "1.0", "A29RXHB35YWN17", "A1VC38T7YXB528", "https://mws.amazonservices.jp/", "https://mws.amazonservices.jp/Products/2011-10-01", Country.JAPAN, "", "", DelFlg.DELETE, false), //
		HARRY("HARY MARKET", "#FF9872", "AKIAIYURJS2TD3WCOXQQ", "hHkoNwvMdcF/5kOZfNMLvqu/ZISjVJU7Sp8QsO8R", "StoreCan", "1.0", "A303SPALZ8UAP1", "A1VC38T7YXB528", "https://mws.amazonservices.jp/", "https://mws.amazonservices.jp/Products/2011-10-01", Country.JAPAN, "", "", DelFlg.DELETE, false), //
		DAS("MEDIA MALL DAS", "#77EEFF", "AKIAIQB5DKIJCUJ7I4TA", "INSKfvuD1eNsahtnTpDH1UFhQYGEIZOp8j/8FibN", "StoreCan", "1.0", "A3UPQAE9XEU5BD", "A1VC38T7YXB528", "https://mws.amazonservices.jp/", "https://mws.amazonservices.jp/Products/2011-10-01", Country.JAPAN, "", "", DelFlg.DELETE, false), //
		GREEN("Ever Green Japan Shop", "#AEFFB3", "AKIAIOR6RSJ6LEKNBH5A", "okcATAohyLlJ5j1zEPN5lnjHszkaaJdV5U/CwgTt", "StoreCan", "1.0", "A2SHDXXHU8BHW4", "A1VC38T7YXB528", "https://mws.amazonservices.jp/", "https://mws.amazonservices.jp/Products/2011-10-01", Country.JAPAN, "", "", DelFlg.DELETE, true), //
		STORE_CAN("日本1号店（停止中）", "#ccc", "AKIAIZAUVZL6TS3JBU7A", "YiP7d1JVPDxRESWIzjDVtdczBMMgTJP9UUSfOM3o", "StoreCan", "1.0", "AS5NO87BGOT05", "A1VC38T7YXB528", "https://mws.amazonservices.jp/", "https://mws.amazonservices.jp/Products/2011-10-01", Country.JAPAN, "017437", "9999", DelFlg.DELETE, false), //
		STORE_CAN2("日本2号店（停止中）", "#ccc", "AKIAJYZ6OG424EPW4V6A", "uNnc49BTYiF5JchGoKWt0ArjcNH3Wlux8ZZp04xf", "StoreCan", "1.0", "A1E8I30N73N089", "A1VC38T7YXB528", "https://mws.amazonservices.jp/", "https://mws.amazonservices.jp/Products/2011-10-01", Country.JAPAN, "021917", "9999", DelFlg.DELETE, false), //
		STORE_CAN3("日本3号店（停止中）", "#CEFFA6", "AKIAJ7H56GNYV2XRV4YA", "RJvRQiESc2M0AfQMateeDCY4vuwfphraRAbaAhtQ", "StoreCan", "1.0", "A1FMGAFS8I59K4", "A1VC38T7YXB528", "https://mws.amazonservices.jp/", "https://mws.amazonservices.jp/Products/2011-10-01", Country.JAPAN, "021917", "9999", DelFlg.DELETE, false), //
		Z_US("カプセルZ店", "#fdd", "AKIAIRDKVJPFHMCIN4DQ", "nxxpo4QVMV2JYr5zO6ybntUT1O4wPxG7bSPejOVY", "StoreCan", "1.0", "A3AQODM1LBH6RX", "ATVPDKIKX0DER", "https://mws.amazonservices.com/", "https://mws.amazonservices.com/Products/2011-10-01", Country.USA, "", "", DelFlg.DELETE, false),
		// ====================================================================
		// ↑削除
		// ====================================================================
		COM_STOCK("COM STOCK", "#DBFF71", "AKIAICEMYFD5ELDO5SFQ", "H1DEcyehlqWkpLML+aPa1/vhQe5C+LgqwOB7Uy36", "StoreCan", "1.0", "A2SFOEUFUIPMQE", "A1VC38T7YXB528", "https://mws.amazonservices.jp/", "https://mws.amazonservices.jp/Products/2011-10-01", Country.JAPAN, "", "", DelFlg.UNDELETE, true), //
		RYU("蓮月マート", "#C299FF", "AKIAJORZOJJLJCTYKJLA", "TQ5yd7rg8xoXh5yOmO1Ynvi+HZyapXZJpXpA5y/2", "StoreCan", "1.0", "A3TURJJNXT7WJV", "A1VC38T7YXB528", "https://mws.amazonservices.jp/", "https://mws.amazonservices.jp/Products/2011-10-01", Country.JAPAN, "", "", DelFlg.UNDELETE, true),

		MONO("monojapan", "#FCCDFA", "AKIAJNSRMLHOLKUCJYWQ", "5uJfqfG+fqClHfZrJKn53Tt+ll5/ZQPhK6e9ac2F", "StoreCan", "1.0", "A1IYSQZE8ZMUAF", "A1VC38T7YXB528", "https://mws.amazonservices.jp/", "https://mws.amazonservices.jp/Products/2011-10-01", Country.JAPAN, "", "", DelFlg.UNDELETE, true),

		WHITE("ホワイトホース", "#FCCDFF", "AKIAJBLREXH3XC2ZENBQ", "eDCohvwR74k9Xho0xbs1tL/V7RhLGp3vab73ewNp", "StoreCan", "1.0", "A2SXM6ORUUIUNI", "A1VC38T7YXB528", "https://mws.amazonservices.jp/", "https://mws.amazonservices.jp/Products/2011-10-01", Country.JAPAN, "", "", DelFlg.UNDELETE, true),

		SANCHU("さんちゅうアカウント", "#faa", "AKIAI5MR2T527XQ6MV6A", "mRERe6c2b6qVUj0C9MKxiRIQm7tr/NaujnoJKiqD", "StoreCan", "1.0", "A2YXVXCD8K71G9", "A1VC38T7YXB528", "https://mws.amazonservices.jp/", "https://mws.amazonservices.jp/Products/2011-10-01", Country.JAPAN, "", "", DelFlg.UNDELETE, false),

		STORE_CAN_US("US1号店", "#fdd", "AKIAIWHQLHYG6I6P3BQQ", "LsoMJU9A3ugNkmQVwkOvlZ53jlI6kf8YDrZO0gMo", "StoreCan", "1.0", "A32Z8HSH4P30K5", "ATVPDKIKX0DER", "https://mws.amazonservices.com/", "https://mws.amazonservices.com/Products/2011-10-01", Country.USA, "", "", DelFlg.UNDELETE, true),

		// 口座が海外にないとスペインとイタリアはセラーセントラル上エラーがでているが、他のEU圏で登録したため全部取れるようになりました。
		STORE_CAN_ES("スペイン1号店", "#fdd", "AKIAI27LZY4SB2HYFUQQ", "BQgfhP6oU58fo5Z5/QlDS4/5pwRt9iuPyX4Momcm", "bcc", "1.0", "A1M8A9SEIVRNNS", "A1F83G8C2ARO7P", "https://mws.amazonservices.es/", "https://mws-eu.amazonservices.com/Products/2011-10-01", Country.ES, "", "", DelFlg.UNDELETE, false), // 4173-8996-6040

		STORE_CAN_DE("ドイツ1号店", "#fdd", "AKIAI27LZY4SB2HYFUQQ", "BQgfhP6oU58fo5Z5/QlDS4/5pwRt9iuPyX4Momcm", "bcc", "1.0", "A1M8A9SEIVRNNS", "A1F83G8C2ARO7P", "https://mws.amazonservices.de/", "https://mws-eu.amazonservices.com/Products/2011-10-01", Country.DE, "", "", DelFlg.UNDELETE, false),
		// 停止中
		STORE_CAN_FR("フランス1号店", "#fdd", "AKIAI27LZY4SB2HYFUQQ", "BQgfhP6oU58fo5Z5/QlDS4/5pwRt9iuPyX4Momcm", "bcc", "1.0", "A1M8A9SEIVRNNS", "A1F83G8C2ARO7P", "https://mws.amazonservices.fr/", "https://mws-eu.amazonservices.com/Products/2011-10-01", Country.FR, "", "", DelFlg.UNDELETE, false), //
		STORE_CAN_IT("イタリア1号店", "#fdd", "AKIAI27LZY4SB2HYFUQQ", "BQgfhP6oU58fo5Z5/QlDS4/5pwRt9iuPyX4Momcm", "bcc", "1.0", "A1M8A9SEIVRNNS", "A1F83G8C2ARO7P", "https://mws.amazonservices.it/", "https://mws-eu.amazonservices.com/Products/2011-10-01", Country.IT, "", "", DelFlg.UNDELETE, false), //
		STORE_CAN_UK("イギリス1号店", "#fdd", "AKIAI27LZY4SB2HYFUQQ", "BQgfhP6oU58fo5Z5/QlDS4/5pwRt9iuPyX4Momcm", "bcc", "1.0", "A1M8A9SEIVRNNS", "A1F83G8C2ARO7P", "https://mws.amazonservices.uk/", "https://mws-eu.amazonservices.com/Products/2011-10-01", Country.UK, "", "", DelFlg.UNDELETE, false), //
		STORE_CAN_CA("カナダ1号店", "#fdd", "AKIAIHIHHK463GV7M5NQ", "sZ/gsiiEUWbwWmcPC8jt6IUnK3hwACIXE4IMfEre", "bcc", "1.0", "A4UO260O7NC7S", "A2EUQ1WTGCTBG2", "https://mws.amazonservices.ca/", "https://mws.amazonservices.ca/Products/2011-10-01", Country.CA, "", "", DelFlg.UNDELETE, false),

		/*
		 * テンプレ STORE_CAN3( "", //店舗名 "", //カラー "", //アクセスキーID "", //秘密キー "", //アプリケーション名 "", //バージョン "", //出品者ID "", //マーケットプレイスID "", //ConfigUrl "", //ProductConfigUrl Country.USA, //カントリー "", //風神ID "", //風神パスワード DelFlg.UNDELETE //デリートフラグ ),
		 */
		;
		// 国ごとのProductConfigUrl
		// IMPORTANT: Uncomment out the appropriate line for the country you
		// wish
		// to sell in:
		//
		// United States:
		// config.setServiceURL("https://mws.amazonservices.com/Products/2011-10-01");
		//
		// Canada:
		// config.setServiceURL("https://mws.amazonservices.ca/Products/2011-10-01");
		//
		// Europe:
		// config.setServiceURL("https://mws-eu.amazonservices.com/Products/2011-10-01");
		//
		// Japan:
		// config.setServiceURL("https://mws.amazonservices.jp/Products/2011-10-01");
		//
		// China:
		// config.setServiceURL("https://mws.amazonservices.com.cn/Products/2011-10-01");
		//

		private String name;
		private String color;
		private String accessKeyId;
		private String secretAccessKey;
		private String applicationName;
		private String applicationVersion;
		private String sellerId;
		private String marketplaceId;
		private String fujinId;
		private String fujinPassword;
		private Country country;
		private DelFlg delFlg;
		private boolean paymentImportDispFlg;
		private MarketplaceWebServiceProductsConfig productsConfig = new MarketplaceWebServiceProductsConfig();
		private String productsConfigUrl;

		// コンストラクタ
		private Store(String name, String color, String accessKeyId, String secretAccessKey, String applicationName, String applicationVersion, String sellerId, String marketplaceId, String configUrl, String productsConfigUrl, Country country, String fujinId, String fujinPassword, DelFlg delFlg, boolean paymentImportDispFlg) {
			this.setName(name);
			this.setColor(color);
			this.setAccessKeyId(accessKeyId);
			this.setSecretAccessKey(secretAccessKey);
			this.setApplicationName(applicationName);
			this.setApplicationVersion(applicationVersion);
			this.setSellerId(sellerId);
			this.setMarketplaceId(marketplaceId);
			this.setCountry(country);
			this.setFujinId(fujinId);
			this.setFujinPassword(fujinPassword);
			this.setDelFlg(delFlg);
			this.setProductsConfigUrl(productsConfigUrl);
			this.setPaymentImportDispFlg(paymentImportDispFlg);

			productsConfig.setServiceURL(productsConfigUrl);
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

		public String getApplicationName() {
			return applicationName;
		}

		public void setApplicationName(String applicationName) {
			this.applicationName = applicationName;
		}

		public String getApplicationVersion() {
			return applicationVersion;
		}

		public void setApplicationVersion(String applicationVersion) {
			this.applicationVersion = applicationVersion;
		}

		public String getSellerId() {
			return sellerId;
		}

		public void setSellerId(String sellerId) {
			this.sellerId = sellerId;
		}

		public String getMarketplaceId() {
			return marketplaceId;
		}

		public void setMarketplaceId(String marketplaceId) {
			this.marketplaceId = marketplaceId;
		}

		public MarketplaceWebServiceProductsConfig getProductsConfig() {
			return productsConfig;
		}

		public void setProductsConfig(MarketplaceWebServiceProductsConfig productsConfig) {
			this.productsConfig = productsConfig;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getColor() {
			return color;
		}

		public void setColor(String color) {
			this.color = color;
		}

		public String getFujinPassword() {
			return fujinPassword;
		}

		public void setFujinPassword(String fujinPassword) {
			this.fujinPassword = fujinPassword;
		}

		public String getFujinId() {
			return fujinId;
		}

		public void setFujinId(String fujinId) {
			this.fujinId = fujinId;
		}

		public Country getCountry() {
			return country;
		}

		public void setCountry(Country country) {
			this.country = country;
		}

		public DelFlg getDelFlg() {
			return delFlg;
		}

		public void setDelFlg(DelFlg delFlg) {
			this.delFlg = delFlg;
		}

		public String getProductsConfigUrl() {
			return productsConfigUrl;
		}

		public void setProductsConfigUrl(String productsConfigUrl) {
			this.productsConfigUrl = productsConfigUrl;
		}

		public boolean isPaymentImportDispFlg() {
			return paymentImportDispFlg;
		}

		public void setPaymentImportDispFlg(boolean paymentImportDispFlg) {
			this.paymentImportDispFlg = paymentImportDispFlg;
		}

		/**
		 * 日本以外のストアのリストを返します。
		 *
		 * @return
		 */
		public static List<Store> getOverSeaStore() {
			List<Store> list = new ArrayList<Store>();
			for(Store store : Store.values()) {
				if(!store.getCountry().equals(Country.JAPAN)) {
					if(store.isPaymentImportDispFlg()) {
						list.add(store);
					}
				}
			}
			return list;
		}

		@Override
		public String getJsonDispName() {
			return this.getName();
		}

		@Override
		public String getJsonDescription() {
			return null;
		}

		@Override
		public String getJsonLabelClass() {
			return null;
		}

		@Override
		public String getJsonButtonClass() {
			return null;
		}
	}

	public enum Currency implements BaseEnum {
		JPY("円", 110F, "￥"), //
		USD("米ドル", 1F, "$"), //
		EUR("ユーロ", 1.2F, "€"), //
		CAD("カナダドル", 0.9F, "$"), //
		GBP("ポンド", 1.1F, "£"), //
		CNY("元", 0.2F, "￥"), //
		INR("ルピー", 0.0149F, "₨"), //
		MXN("ペソ", 0.0538F, "$"),;

		private String name;
		private float defaultRate;
		private String symbol;

		private Currency(String name, float defaultRate, String symbol) {
			this.setName(name);
			this.setDefaultRate(defaultRate);
			this.setSymbol(symbol);
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public String getJsonDispName() {
			return this.getName();
		}

		@Override
		public String getJsonDescription() {
			return this.getSymbol();
		}

		@Override
		public String getJsonLabelClass() {
			return null;
		}

		@Override
		public String getJsonButtonClass() {
			return null;
		}

		public float getDefaultRate() {
			return defaultRate;
		}

		public void setDefaultRate(float defaultRate) {
			this.defaultRate = defaultRate;
		}

		public String getSymbol() {
			return symbol;
		}

		public void setSymbol(String symbol) {
			this.symbol = symbol;
		}
	}

	public static enum Country implements BaseEnum {
		JAPAN("日本", "A1VC38T7YXB528", "https://mws.amazonservices.jp/", "https://mws.amazonservices.jp/Products/2011-10-01", "https://mws.amazonservices.jp", "flag flag-jp", "Asia/Tokyo", Locale.JAPAN, Currency.JPY, "http://www.amazon.co.jp", "ecs.amazonaws.jp", "JP", "/img/country/jp.png", 5, "Solid", GraphColor.RED), //
		USA("アメリカ", "ATVPDKIKX0DER", "https://mws.amazonservices.com/", "https://mws.amazonservices.com/Products/2011-10-01", "https://mws.amazonservices.com", "flag flag-us", "America/Los_Angeles", Locale.US, Currency.USD, "http://www.amazon.com", "ecs.amazonaws.com", "US", "/img/country/us.png", 1, "ShortDash", GraphColor.BLUE), //
		DE("ドイツ", "A1F83G8C2ARO7P", "https://mws.amazonservices.de/", "https://mws-eu.amazonservices.com/Products/2011-10-01", "https://mws-eu.amazonservices.com", "flag flag-de", "Europe/Berlin", Locale.GERMANY, Currency.EUR, "http://www.amazon.de", "ecs.amazonaws.de", "DE", "/img/country/de.png", 3, "ShortDashDot", GraphColor.GREEN), //
		ES("スペイン", "A1F83G8C2ARO7P", "https://mws.amazonservices.es/", "https://mws-eu.amazonservices.com/Products/2011-10-01", "https://mws-eu.amazonservices.com", "flag flag-es", "Europe/Madrid", Locale.ITALY, Currency.EUR, "http://www.amazon.es", "webservices.amazon.es", "ES", "/img/country/es.png", 9, "LongDash", GraphColor.ORANGE), //
		FR("フランス", "A1F83G8C2ARO7P", "https://mws.amazonservices.fr/", "https://mws-eu.amazonservices.com/Products/2011-10-01", "https://mws-eu.amazonservices.com", "flag flag-fr", "Europe/Paris", Locale.FRANCE, Currency.EUR, "http://www.amazon.fr", "ecs.amazonaws.fr", "FR", "/img/country/fr.png", 4, "DashDot", GraphColor.PINK), //
		IT("イタリア", "A1F83G8C2ARO7P", "https://mws.amazonservices.it/", "https://mws-eu.amazonservices.com/Products/2011-10-01", "https://mws-eu.amazonservices.com", "flag flag-it", "Europe/Rome", Locale.ITALY, Currency.EUR, "http://www.amazon.it", "webservices.amazon.it", "IT", "/img/country/it.png", 8, "LongDashDot", GraphColor.PURPLE), //
		UK("イギリス", "A1F83G8C2ARO7P", "https://mws.amazonservices.co.uk/", "https://mws.amazonservices.co.uk/Products/2011-10-01", "https://mws.amazonservices.co.uk", "flag flag-gb", "Europe/London", Locale.UK, Currency.JPY, "http://www.amazon.co.uk", "ecs.amazonaws.co.uk", "UK", "/img/country/uk.jpg", 2, "LongDashDotDot", GraphColor.YELLOW), //
		CA("カナダ", "A2EUQ1WTGCTBG2", "https://mws.amazonservices.ca/", "https://mws.amazonservices.ca/Products/2011-10-01", "https://mws.amazonservices.ca", "flag flag-ca", "Canada/Yukon", Locale.CANADA, Currency.CAD, "http://www.amazon.ca", "ecs.amazonaws.ca", "CA", "/img/country/ca.png", 6, "ShortDot", GraphColor.CYAN), //
		CN("中国", "", "https://mws.amazonservices.cn/", "https://mws.amazonservices.cn/Products/2011-10-01", "https://mws.amazonservices.cn", "flag flag-cn", "Asia/Chongqing", Locale.CHINA, Currency.CNY, "http://www.amazon.cn", "ecs.amazonaws.cn", "CN", "/img/country/cn.gif", 7, "Solid", GraphColor.DARK_BLUE), //
		IN("インド", "", "https://mws.amazonservices.in/", "https://mws.amazonservices.in/Products/2011-10-01", "https://mws.amazonservices.in", "flag flag-cn", "Asia/Chongqing", Locale.CHINA, Currency.CNY, "http://www.amazon.in", "ecs.amazonaws.in", "IN", "/img/country/in.png", 7, "Solid", GraphColor.DARK_GREEN),;

		private String name;
		private String serviceUrl;
		private String productServiceUrl;
		private String mwsEndpoint;
		private String marketplaceId;
		private String spliteClassName;
		private String timeZone;
		private Locale locale;
		private Currency currency;
		private String sellerDomain;
		private String awsEndpoint;
		private String code;
		private String flagImagePath;
		private int keepaCode;
		private String graphDashStyle;
		private GraphColor graphColor;

		private Country(String name, String marketplaceId, String serviceUrl, String productServiceUrl, String mwsEndpoint, String spliteClassName, String timeZone, Locale locale, Currency currency, String sellerDomain, String awsEndpoint, String code, String flagImagePath, int keepaCode, String graphDashStyle, GraphColor graphColor) {
			setMarketplaceId(marketplaceId);
			setName(name);
			setServiceUrl(serviceUrl);
			setProductServiceUrl(productServiceUrl);
			setSpliteClassName(spliteClassName);
			setTimeZone(timeZone);
			setLocale(locale);
			setCurrency(currency);
			setSellerDomain(sellerDomain);
			setAwsEndpoint(awsEndpoint);
			setCode(code);
			setMwsEndpoint(mwsEndpoint);
			setFlagImagePath(flagImagePath);
			setKeepaCode(keepaCode);
			setGraphDashStyle(graphDashStyle);
			setGraphColor(graphColor);
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getServiceUrl() {
			return serviceUrl;
		}

		public void setServiceUrl(String serviceUrl) {
			this.serviceUrl = serviceUrl;
		}

		public String getProductServiceUrl() {
			return productServiceUrl;
		}

		public void setProductServiceUrl(String productServiceUrl) {
			this.productServiceUrl = productServiceUrl;
		}

		public String getMarketplaceId() {
			return marketplaceId;
		}

		public void setMarketplaceId(String marketplaceId) {
			this.marketplaceId = marketplaceId;
		}

		public String getSpliteClassName() {
			return spliteClassName;
		}

		public void setSpliteClassName(String spliteClassName) {
			this.spliteClassName = spliteClassName;
		}

		public String getTimeZone() {
			return timeZone;
		}

		public void setTimeZone(String timeZone) {
			this.timeZone = timeZone;
		}

		public Locale getLocale() {
			return locale;
		}

		public void setLocale(Locale locale) {
			this.locale = locale;
		}

		public Currency getCurrency() {
			return currency;
		}

		public void setCurrency(Currency currency) {
			this.currency = currency;
		}

		public String getSellerDomain() {
			return sellerDomain;
		}

		public void setSellerDomain(String sellerDomain) {
			this.sellerDomain = sellerDomain;
		}

		public String getAwsEndpoint() {
			return awsEndpoint;
		}

		public void setAwsEndpoint(String awsEndpoint) {
			this.awsEndpoint = awsEndpoint;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getMwsEndpoint() {
			return mwsEndpoint;
		}

		public void setMwsEndpoint(String mwsEndpoint) {
			this.mwsEndpoint = mwsEndpoint;
		}

		/**
		 *
		 * @param code
		 * @return
		 */
		public static Country findCode(String code) {
			if(code == null) {
				return null;
			}
			for(Country country : Country.values()) {
				if(country.getCode().equals(code)) {
					return country;
				}
				if(country.getSellerDomain().indexOf(code) != -1) {
					return country;
				}
			}
			return null;
		}

		@Override
		public String getJsonDispName() {
			return this.getName();
		}

		@Override
		public String getJsonDescription() {
			return this.currency.toString();
		}

		@Override
		public String getJsonLabelClass() {
			return null;
		}

		@Override
		public String getJsonButtonClass() {
			return this.flagImagePath;
		}

		public String getFlagImagePath() {
			return flagImagePath;
		}

		public void setFlagImagePath(String flagImagePath) {
			this.flagImagePath = flagImagePath;
		}

		public int getKeepaCode() {
			return keepaCode;
		}

		public void setKeepaCode(int keepaCode) {
			this.keepaCode = keepaCode;
		}

		public String getGraphDashStyle() {
			return graphDashStyle;
		}

		public void setGraphDashStyle(String graphDashStyle) {
			this.graphDashStyle = graphDashStyle;
		}

		public GraphColor getGraphColor() {
			return graphColor;
		}

		public void setGraphColor(GraphColor graphColor) {
			this.graphColor = graphColor;
		}

	}



	/**
	 * グラフカラー
	 */
	public static enum GraphColor implements BaseEnum {
		RED("#f00"),
		ORANGE("#FFB400"),
		GREEN("#CCFF00"),
		BLUE("#00BAFF"),
		PURPLE("#BA00FF"),
		PINK("#FF00B4"),
		YELLOW("#E8E500"),
		CYAN("#00E8E5"),
		DARK_GREEN("#47E800"),
		DARK_BLUE("#1000E8"),


		;

		private String value;

		private GraphColor(String value) {
			this.value = value;
		}


		@Override
		public String getJsonDispName() {
			return this.getValue();
		}

		@Override
		public String getJsonDescription() {
			return null;
		}

		@Override
		public String getJsonLabelClass() {
			return null;
		}

		@Override
		public String getJsonButtonClass() {
			return null;
		}


		public String getValue() {
			return value;
		}


		public void setValue(String value) {
			this.value = value;
		}
	}


	/**
	 * 共通Enum用のインターフェースです。 Meta#modelToJsonする際に、name,valueとして出力する場合はこの インターフェースを継承して実装します。
	 *
	 * @author kitazawatakuya
	 *
	 */
	public interface BaseEnum {
		public String getJsonDispName();

		public String getJsonDescription();

		public String getJsonLabelClass();

		public String getJsonButtonClass();
	}
	/**
	 * 本番開発者用ライセンス
	 *
	 * @author kitazawa.takuya
	 *
	 */
	public enum Developer implements BaseEnum {
		DEVELOPER1("北沢拓也", "jackpot667@gmail.com", "jackpot667@gmail.com"), //
		DEVELOPER2("田中崇輝", "shichiria.island@gmail.com", "shichiria.island@gmail.com"), //
		SUPPROT("松沢夏苗", "k09027229147@gmail.com", "k09027229147@gmail.com"),;
		private String name;
		private String mailAddress;
		private String gmailAddress;

		private Developer(String name, String mailAddress, String gmailAddress) {
			setName(name);
			setMailAddress(mailAddress);
			setGmailAddress(gmailAddress);
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getMailAddress() {
			return mailAddress;
		}

		public void setMailAddress(String mailAddress) {
			this.mailAddress = mailAddress;
		}

		public String getGmailAddress() {
			return gmailAddress;
		}

		public void setGmailAddress(String gmailAddress) {
			this.gmailAddress = gmailAddress;
		}

		@Override
		public String getJsonDispName() {
			return this.getName();
		}

		@Override
		public String getJsonDescription() {
			return null;
		}

		@Override
		public String getJsonLabelClass() {
			return null;
		}

		@Override
		public String getJsonButtonClass() {
			return null;
		}
	}

	/**
	 * 商品コメント
	 *
	 * @author kitazawa.takuya
	 *
	 */
	public enum ProductComment implements BaseEnum {
		COMMENT_1(true, "全体", true, "全体", "中身", "%sは書き込み等無く、綺麗です。|%sの状態は問題ありません。", "36"), //
		COMMENT_2(false, "全体", false, "全体", "全体に曲げぐせが", "%sわずかにあります。|%s少しあります。|%sあります。", "156"), //
		COMMENT_3(false, "全体", false, "全体", "全体に水濡れによる曲げぐせが", "%sわずかにあります。|%s少しあります。|%sあります。", "159"), //
		COMMENT_4(false, "全体", false, "全体", "全体の状態に難が", "%s少しあります。|%s若干あります。|%sあります。", "162"), //
		COMMENT_5(true, "帯", true, "有無", "本：帯無し|本：帯付き", "", "1"), //
		COMMENT_6(false, "帯", true, "破れ", "帯に破れが", "%s少しありますので、帯はおまけとお考え下さい。|%s数箇所にありますので、帯はおまけとお考え下さい。|%s目立ちますので、帯はおまけとお考え下さい。", "44"), //
		COMMENT_7(false, "帯", true, "折れ", "帯に折れが", "%s少しありますので、帯はおまけとお考え下さい。|%s若干ありますので、帯はおまけとお考え下さい。|%s強くありますので、帯はおまけとお考え下さい。", "47"), //
		COMMENT_8(false, "帯", true, "汚れ", "帯に汚れが", "%s一部ありますので、帯はおまけとお考え下さい。|%s若干ありますので、帯はおまけとお考え下さい。|%s目立ちますので、帯はおまけとお考え下さい。", "50"), //
		COMMENT_9(false, "帯", true, "シワ", "帯にシワが", "%s少しありますので、帯はおまけとお考え下さい。|%s若干ありますので、帯はおまけとお考え下さい。|%s目立ちますので、帯はおまけとお考え下さい。", "53"), //
		COMMENT_10(false, "帯", true, "色落ち", "帯に色落ちが", "%s少しありますので、帯はおまけとお考え下さい。|%s若干ありますので、帯はおまけとお考え下さい。|%s目立ちますので、帯はおまけとお考え下さい。", "56"), //
		COMMENT_11(true, "表紙", true, "使用感", "%sカバーの状態は綺麗です、問題ありません|%sカバーにやや使用感がありますが、中身は書き込みなど無く非常に綺麗です|%sカバーにやや使用感がありますが、中身は書き込みなど無く綺麗です|%sカバーにやや使用感がありますが、中身は書き込みも無く、問題ありません", "", "38"), //
		COMMENT_12(false, "表紙", true, "汚れ", "カバーに汚れが", "%sわずかにあります。|%s少しあります。|%sあります。", "59"), //
		COMMENT_13(false, "表紙", true, "破れ", "カバーに破れが", "%sわずかにあります。|%s少しあります。|%sあります。", "62"), //
		COMMENT_14(false, "表紙", true, "折れ", "カバーに折れた跡が", "%sわずかにあります。|%s少しあります。|%sあります。", "65"), //
		COMMENT_15(false, "表紙", true, "痛み", "カバーに痛みが", "%s少しあります。|%sややあります。|%sあります。", "68"), //
		COMMENT_16(false, "表紙", true, "経年劣化", "カバーに経年による日焼けが", "%s少しあります。|%sややあります。|%sあります。", "71"), //
		COMMENT_17(false, "表紙", true, "ヨレ", "カバー上部にヨレが", "%sわずかにあります。|%s少しあります。|%sあります。", "74"), //
		COMMENT_18(false, "表紙", false, "ヨレ", "カバー下部にヨレが", "%sわずかにあります。|%s少しあります。|%sあります。", "77"), //
		COMMENT_19(false, "表紙", false, "ヨレ", "カバー上下部にヨレが", "%sわずかにあります。|%s少しあります。|%sあります。", "80"), //
		COMMENT_20(true, "ページ", true, "側面", "ページ側面に擦れた跡が", "%sわずかにあります。|%s少しあります。|%sあります。", "83"), //
		COMMENT_21(false, "ページ", false, "側面", "ページ側面にシミが", "%sわずかにあります。|%s少しあります。|%sあります。", "86"), //
		COMMENT_22(false, "ページ", false, "側面", "ページ側面にヨゴレが", "%sわずかにあります。|%s少しあります。|%sあります。", "89"), //
		COMMENT_23(false, "ページ", false, "側面", "ページ側面に水に濡れてヨレた跡が", "%sわずかにあります。|%s少しあります。|%sあります。", "92"), //
		COMMENT_24(false, "ページ", true, "最初", "最初のページに書き込みが", "%sわずかにあります。|%s少しあります。|%sあります。", "95"), //
		COMMENT_25(false, "ページ", false, "最初", "最初のページにヨゴレが", "%sわずかにあります。|%s少しあります。|%sあります。", "97"), //
		COMMENT_26(false, "ページ", true, "最後", "最後のページに書き込みが", "%sわずかにあります。|%s少しあります。|%sあります。", "99"), //
		COMMENT_27(false, "ページ", false, "最後", "最後のページにヨゴレが", "%sわずかにあります。|%s少しあります。|%sあります。", "118"), //
		COMMENT_28(false, "ページ", true, "紙面", "紙面の折れが", "%sごく一部にあります。|%s数ページにあります。|%s多くのページにあります。", "150"), //
		COMMENT_29(false, "ページ", true, "中身", "中身に書き込み（主に鉛筆）が", "%sごく一部にあります。|%s数ページにあります。|%s多くのページにあります。", "120"), //
		COMMENT_30(false, "ページ", false, "中身", "中身に書き込み（主に蛍光ペン）が", "%sごく一部にあります。|%s数ページにあります。|%s多くのページにあります。", "123"), //
		COMMENT_31(false, "ページ", false, "中身", "中身に書き込み（主にボールペン）が", "%sごく一部にあります。|%s数ページにあります。|%s多くのページにあります。", "126"), //
		COMMENT_32(false, "ページ", false, "中身", "中身に書き込みが", "%sごく一部にあります。|%s数ページにあります。|%s多くのページにあります。", "129"), //
		COMMENT_33(false, "ページ", false, "中身", "中身の状態に難が", "%sごく一部にあります。|%s数ページにあります。|%s多くのページにあります。", "153"), //
		COMMENT_34(false, "ページ", false, "中身", "中身に汚れが", "%sごく一部にあります。|%s数ページにあります。|%s多くのページにあります。", "138"), //
		COMMENT_35(false, "ページ", false, "中身", "中身に破れが", "%sごく一部にあります。|%s数ページにあります。|%s多くのページにあります。", "141"), //
		COMMENT_36(false, "ページ", false, "中身", "中身に破損が", "%sごく一部にあります。|%s数ページにあります。|%s多くのページにあります。", "144"), //
		COMMENT_37(false, "ページ", false, "中身", "中身に経年による日焼けが", "%sうっすらあります。|%s若干あります。|%sあります。", "135"), //
		COMMENT_38(false, "ページ", false, "中身", "ページ角に三角形の折れが", "%sごく一部にあります。|%s数ページにあります。|%s多くのページにあります。", "147"), //
		COMMENT_39(false, "ページ", false, "中身", "ページ周辺に経年による日焼けが", "%sうっすらあります。|%sややあります。|%sあります。", "132"), //
		COMMENT_40(true, "付録", true, "付録", "付録", "%s揃っています。|%sが足りません。|%sはありません。", "165"), //
		COMMENT_41(false, "付録", true, "使用感", "付録に使用感が", "%sややあります。|%s若干あります。|%s目立ちます。", "168"), //
		COMMENT_42(true, "付属品", true, "付属ディスク", "付属ディスクが", "足りません|ありません|足りません。", "9"), //
		COMMENT_43(false, "付属品", true, "付属品", "付属品が", "揃ってます|足りません|ありません。", "171"), //
		COMMENT_44(false, "付属品", true, "使用感", "付属品に使用感が", "少しあります|若干あります|目立ちます。", "173"), //
		COMMENT_45(true, "その他", true, "-", "その他の状態は", "非常に良好です|綺麗です|問題ありません。", "41"), //
		COMMENT_46(true, "付加価値", true, "著者直筆サイン入り", "", "", "30"), COMMENT_47(false, "付加価値", true, "宛名入り著者直筆サイン入り", "", "", "31"),;

		private boolean categoryDispFlg;
		private String categoryName;
		private boolean subCategoryDispFlg;
		private String subCategoryName;
		private String word1;
		private String word2;
		private String oldNo;

		private ProductComment(boolean categoryDispFlg, String categoryName, boolean subCategoryDispFlg, String subCategoryName, String word1, String word2, String oldNo) {
			this.categoryDispFlg = categoryDispFlg;
			this.setSubCategoryDispFlg(subCategoryDispFlg);
			this.categoryName = categoryName;
			this.subCategoryName = subCategoryName;
			this.word1 = word1;
			this.word2 = word2;
			this.setOldNo(oldNo);

		}

		public String getCategoryName() {
			return categoryName;
		}

		public void setCategoryName(String categoryName) {
			this.categoryName = categoryName;
		}

		public String getSubCategoryName() {
			return subCategoryName;
		}

		public void setSubCategoryName(String subCategoryName) {
			this.subCategoryName = subCategoryName;
		}

		public String getWord1() {
			return word1;
		}

		public void setWord1(String word1) {
			this.word1 = word1;
		}

		public String getWord2() {
			return word2;
		}

		public void setWord2(String word2) {
			this.word2 = word2;
		}

		public String getOldNo() {
			return oldNo;
		}

		public void setOldNo(String oldNo) {
			this.oldNo = oldNo;
		}

		public boolean isCategoryDispFlg() {
			return categoryDispFlg;
		}

		public void setCategoryDispFlg(boolean categoryDispFlg) {
			this.categoryDispFlg = categoryDispFlg;
		}

		public boolean isSubCategoryDispFlg() {
			return subCategoryDispFlg;
		}

		public void setSubCategoryDispFlg(boolean subCategoryDispFlg) {
			this.subCategoryDispFlg = subCategoryDispFlg;
		}

		@Override
		public String getJsonDispName() {
			return this.getWord1();
		}

		@Override
		public String getJsonDescription() {
			return null;
		}

		@Override
		public String getJsonLabelClass() {
			return null;
		}

		@Override
		public String getJsonButtonClass() {
			return null;
		}
	}

	/**
	 * 配送状況
	 *
	 * @author kitazawatakuya
	 *
	 */
	public static enum DeliveryStatus implements BaseEnum {
		NONE("配送手続き完了", "", true), //
		PICKUP_REQUEST_DONE("集荷依頼完了", "label-warning", true), //
		BCC_ARRIVAL("CAN倉庫に到着", "label-warning", true), //
		CNT_OK("点数確認OK", "label-info", true), //
		KENPIN_OK("検品OK", "label-important", false), //
		KENPIN_NG("検品NG", "label-important", true), //
		AMAZON_SHIPPIING("amazonへ配送", "", false), //
		NOT_BILLING("非請求対象", "", true), //
		RETURN_TO_SENDER("返送", "", true),;

		private String name;
		private String labelClassName;
		private boolean cappyUseFlg;

		// コンストラクタ
		private DeliveryStatus(String name, String labelClassName, boolean cappyUseFlg) {
			this.setName(name);
			this.setLabelClassName(labelClassName);
			this.setCappyUseFlg(cappyUseFlg);
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public String getLabelClassName() {
			return labelClassName;
		}

		public void setLabelClassName(String labelClassName) {
			this.labelClassName = labelClassName;
		}

		public boolean isCappyUseFlg() {
			return cappyUseFlg;
		}

		public void setCappyUseFlg(boolean cappyUseFlg) {
			this.cappyUseFlg = cappyUseFlg;
		}

		@Override
		public String getJsonDispName() {
			return this.getName();
		}

		@Override
		public String getJsonDescription() {
			return null;
		}

		@Override
		public String getJsonLabelClass() {
			return this.getLabelClassName();
		}

		@Override
		public String getJsonButtonClass() {
			return null;
		}
	}

	// ================================
	// コンディション
	public static enum SubCondition implements BaseEnum {
		// New, Mint, Very Good, Good, Acceptable, Poor, Club, OEM, Warranty, Refurbished Warranty, Refurbished, Open Box, Other
		// code feedCode name description shortName weight onlineReportName labelColor cappyDispFlg amazeDispFlg revisionWeight revisionAverageWeight revisionMedianWeight numberCode condition conditionCoefficient
		NEW("New", "New", "新品", "XXX", "新品", 1000, "NewItem", "label-important", true, true, 100F, 100F, 100F, 11, Condition.NEW, 100, "New"), //
		MINT("Mint", "UsedLikeNew", "ほぼ新品", "XXX", "ほ新", 700, "UsedLikeNew", "label-warning", true, true, 98F, 90F, 90F, 1, Condition.USED, 90, "like_new"), //
		VERY_GOOD("VeryGood", "UsedVeryGood", "非常に良い", "XXX", "非良", 600, "UsedVeryGood", "label-info", true, true, 90F, 82F, 80F, 2, Condition.USED, 82, "very_good"), //
		GOOD("Good", "UsedGood", "良い", "XXX", "良", 500, "UsedGood", "label-success", true, true, 80F, 72F, 65F, 3, Condition.USED, 72, "good"), //
		ACCEPTABLE("Acceptable", "UsedAcceptable", "可", "XXX", "可", 200, "UsedAcceptable", "", true, true, 70F, 65F, 50F, 4, Condition.USED, 65, "acceptable"),
		// コレクション codeとweight修正。
		COLLECTIBLE_LIKE_NEW("Mint", "CollectibleLikeNew", "コレクター ほぼ新品", "XXX", "コほ新", 200, "CollectibleLikeNew", "label-warning", true, false, 300F, 300F, 300F, 5, Condition.COLLECTIBLE, 300, ""), //
		COLLECTIBLE_VERY_GOOD("VeryGood", "CollectibleVeryGood", "コレクター 非常に良い", "XXX", "コ非良", 200, "CollectibleVeryGood", "label-info", true, true, 250F, 250F, 250F, 6, Condition.COLLECTIBLE, 250, ""), //
		COLLECTIBLE_GOOD("Good", "CollectibleGood", "コレクター 良い", "XXX", "コ良", 200, "CollectibleGood", "label-success", true, false, 200F, 200F, 200F, 7, Condition.COLLECTIBLE, 200, ""), //
		COLLECTIBLE_ACCEPTABLE("Acceptable", "CollectibleAcceptable", "コレクター 可", "XXX", "コ可", 200, "CollectibleAcceptable", "", true, false, 150F, 150F, 150F, 8, Condition.COLLECTIBLE, 150, ""),

		POOR("Poor", "Poor", "？？？？", "XXX", "？", 0, "-", "label-warning", false, false, 100F, 100F, 100F, 999, Condition.USED, 100, ""), //
		CLUB("Club", "Club", "クラブ", "XXX", "Club", 0, "-", "label-warning", false, false, 100F, 100F, 100F, 999, Condition.USED, 100, ""), //
		OEM("OEM", "-", "OEM", "XXX", "OEM", 0, "-", "label-warning", false, false, 100F, 100F, 100F, 999, Condition.USED, 100, ""), //
		WARRANTY("Warranty", "-", "保証", "XXX", "保", 0, "-", "label-warning", false, false, 100F, 100F, 100F, 999, Condition.USED, 100, ""), //
		REFURBISHED_WARRANTY("Refurbished Warranty", "-", "改装された保証", "XXX", "改保", 0, "-", "label-warning", false, false, 100F, 100F, 100F, 999, Condition.USED, 100, ""), //
		REFURBISHED("Refurbished", "Refurbished", "改装", "XXX", "改", 0, "-", "label-warning", false, false, 100F, 100F, 100F, 999, Condition.USED, 100, ""), //
		OPEN_BOX("OpenBox", "-", "開封済み", "XXX", "開封", 0, "-", "label-warning", false, false, 100F, 100F, 100F, 999, Condition.USED, 100, ""), //
		OTHER("Other", "-", "その他", "XXX", "etc", 0, "-", "label-warning", false, false, 100F, 100F, 100F, 999, Condition.USED, 100, "");

		/** コード */
		private String code;
		/** 名称 */
		private String name;
		/** 説明文 */
		private String description;
		/** 短縮名称 */
		private String shortName;

		private float weight;
		/***/
		private String onlineReportName;
		/** ラベルの色 */
		private String labelColor;
		/** Cappy表示フラグ */
		private boolean cappyDispFlg;
		/** Amaze表示フラグ */
		private boolean amazeDispFlg;

		private float revisionWeight;
		/** 価格改定 利益重視用重み */
		private float revisionAverageWeight;
		/** 価格改定 回転重視用重み */
		private float revisionMedianWeight;

		private String feedCode;

		private int numberCode;

		private Condition condition;

		private int conditionCoefficient;

        /** サブスクリプションAPI用の名前 */
        private String subscriptionName;

		// コンストラクタ
		private SubCondition(String code, String feedCode, String name, String description, String shortName, int weight, String onlineReportName, String labelColor, boolean cappyDispFlg, boolean amazeDispFlg, float revisionWeight, float revisionAverageWeight, float revisionMedianWeight, int numberCode, Condition condition, int conditionCoefficient, String subscriptionName) {
			this.setCode(code);
			this.setFeedCode(feedCode);
			this.setName(name);
			this.setDescription(description);
			this.setShortName(shortName);
			this.setWeight(weight);
			this.setOnlineReportName(onlineReportName);
			this.setLabelColor(labelColor);
			this.setAmazeDispFlg(amazeDispFlg);
			this.setCappyDispFlg(cappyDispFlg);
			this.setRevisionWeight(revisionWeight);
			this.setRevisionAverageWeight(revisionAverageWeight);
			this.setRevisionMedianWeight(revisionMedianWeight);
			this.setNumberCode(numberCode);
			this.setCondition(condition);
			this.setConditionCoefficient(conditionCoefficient);
			this.setSubscriptionName(subscriptionName);
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getDescription() {
			return description;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getCode() {
			return code;
		}

		public void setShortName(String shortName) {
			this.shortName = shortName;
		}

		public String getShortName() {
			return shortName;
		}

		public float getWeight() {
			return weight;
		}

		public void setWeight(float weight) {
			this.weight = weight;
		}

		public void setOnlineReportName(String onlineReportName) {
			this.onlineReportName = onlineReportName;
		}

		public String getOnlineReportName() {
			return onlineReportName;
		}

		public boolean isRegisterDispFlg() {
			return cappyDispFlg;
		}

		public void setRegisterDispFlg(boolean registerDispFlg) {
			this.cappyDispFlg = registerDispFlg;
		}

		public boolean isAmazeDispFlg() {
			return amazeDispFlg;
		}

		public void setAmazeDispFlg(boolean amazeDispFlg) {
			this.amazeDispFlg = amazeDispFlg;
		}

		public String getLabelColor() {
			return labelColor;
		}

		public void setLabelColor(String labelColor) {
			this.labelColor = labelColor;
		}

		public boolean isCappyDispFlg() {
			return cappyDispFlg;
		}

		public void setCappyDispFlg(boolean cappyDispFlg) {
			this.cappyDispFlg = cappyDispFlg;
		}

		public float getRevisionWeight() {
			return revisionWeight;
		}

		public void setRevisionWeight(float revisionWeight) {
			this.revisionWeight = revisionWeight;
		}

		public float getRevisionAverageWeight() {
			return revisionAverageWeight;
		}

		public void setRevisionAverageWeight(float revisionAverageWeight) {
			this.revisionAverageWeight = revisionAverageWeight;
		}

		public float getRevisionMedianWeight() {
			return revisionMedianWeight;
		}

		public void setRevisionMedianWeight(float revisionMedianWeight) {
			this.revisionMedianWeight = revisionMedianWeight;
		}

		public String getFeedCode() {
			return feedCode;
		}

		public void setFeedCode(String feedCode) {
			this.feedCode = feedCode;
		}

		public int getNumberCode() {
			return numberCode;
		}

		public void setNumberCode(int numberCode) {
			this.numberCode = numberCode;
		}

		/**
		 * BCCで利用可能なサブコンディションを返却します。
		 *
		 * @return
		 */
		public static List<SubCondition> getUseList() {
			List<SubCondition> list = new ArrayList<SubCondition>();
			SubCondition[] values = SubCondition.values();
			for(SubCondition subCondition : values) {
				if(subCondition.isCappyDispFlg()) {
					list.add(subCondition);
				}
			}
			return list;
		}

		/**
		 * codeまたはonlineReportNameのサブコンディションを返します。
		 *
		 * @param code
		 * @return
		 */
		public static SubCondition findCode(String code) {
			SubCondition[] values = SubCondition.values();
			for(SubCondition subCondition : values) {
				if(
				        subCondition.getCode().equals(code)
				        || subCondition.getName().equals(code)
				        || subCondition.getShortName().equals(code)
                        || subCondition.getCode().toLowerCase().equals(code)
                        || subCondition.getFeedCode().toLowerCase().equals(code)
                        || subCondition.getOnlineReportName().equals(code)
                        || subCondition.getSubscriptionName().equals(code)
				        ) {
					return subCondition;
				}
			}
			return null;
		}

		/**
		 * codeまたはonlineReportNameのサブコンディションを返します。
		 *
		 * @return
		 */
		public static SubCondition findCode(String conditionCode, String code) {
			Condition condition = Condition.getCondition(conditionCode);
			SubCondition[] values = SubCondition.values();
			for(SubCondition subCondition : values) {
				if(condition.equals(subCondition.getCondition())) {
					if(subCondition.getCode().equals(code) || subCondition.getOnlineReportName().equals(code)) {
						return subCondition;
					}
				}
			}
			return null;
		}

		/**
		 *
		 * @param code
		 * @return
		 */
		public static SubCondition findCode(int code) {
			if(code == 999 || code == 0) {
				return null;
			}
			SubCondition[] values = SubCondition.values();
			for(SubCondition subCondition : values) {
				if(code == subCondition.getNumberCode()) {
					return subCondition;
				}
			}
			return null;
		}

		public Condition getCondition() {
			return condition;
		}

		public void setCondition(Condition condition) {
			this.condition = condition;
		}

		@Override
		public String getJsonDispName() {
			return this.getName();
		}

		@Override
		public String getJsonDescription() {
			return this.getDescription();
		}

		@Override
		public String getJsonLabelClass() {
			return getLabelColor();
		}

		@Override
		public String getJsonButtonClass() {
			return null;
		}

		public int getConditionCoefficient() {
			return conditionCoefficient;
		}

		public void setConditionCoefficient(int conditionCoefficient) {
			this.conditionCoefficient = conditionCoefficient;
		}

        public String getSubscriptionName() {
            return subscriptionName;
        }

        public void setSubscriptionName(String subscriptionName) {
            this.subscriptionName = subscriptionName;
        }

	}

	// ================================
	// コンディション
	public static enum Condition implements BaseEnum {
		// New, Used, Collectible, Refurbished, Club
		NEW("New", "新品", "XXX", true), //
		USED("Used", "中古", "XXX", true), //
		COLLECTIBLE("Collectible", "コレクター", "XXX", false), //
		REFURBISHED("Refurbished", "改装", "XXX", false), //
		CLUB("Club", "クラブ", "XXX", false);

		/** コード */
		private String code;
		/** 名称 */
		private String name;
		/** 説明文 */
		private String description;
		/** Amaze(AWS)表示フラグ */
		private boolean amazeAwsDispFlg;

		// コンストラクタ
		private Condition(String code, String name, String description, boolean amazeAwsDispFlg) {
			this.setCode(code);
			this.setName(name);
			this.setDescription(description);
			this.setAmazeAwsDispFlg(amazeAwsDispFlg);
		}

		/**
		 * codeまたはonlineReportNameのサブコンディションを返します。
		 *
		 * @return
		 */
		public static Condition findCode(String conditionCode) {
			Condition[] values = Condition.values();
			for(Condition cond : values) {
				if(cond.getCode().equals(conditionCode)) {
					return cond;
				}
			}
			return null;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getDescription() {
			return description;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getCode() {
			return code;
		}

		public void setAmazeAwsDispFlg(boolean amazeAwsDispFlg) {
			this.amazeAwsDispFlg = amazeAwsDispFlg;
		}

		public boolean getAmazeAwsDispFlg() {
			return amazeAwsDispFlg;
		}

		/**
		 * codeのコンディションを返します。
		 *
		 * @param code
		 * @return
		 */
		public static Condition getCondition(String code) {
			Condition[] conditions = Condition.values();
			for(Condition condition : conditions) {
				if(code.equals(condition.getCode())) {
					return condition;
				}
			}
			return null;
		}

		@Override
		public String getJsonDispName() {
			return this.getName();
		}

		@Override
		public String getJsonDescription() {
			return null;
		}

		@Override
		public String getJsonLabelClass() {
			return null;
		}

		@Override
		public String getJsonButtonClass() {
			return null;
		}
	}

	/**
	 * 利用ステータス
	 *
	 * @author kitazawatakuya
	 *
	 */
	public enum UseStatus implements BaseEnum {
		/**  */
		USE("使用中", "利用する", "label-info"), UNUSE("停止中", "利用しない", ""),;

		private String name;
		private String nameB;
		private String cssLabelName;

		private UseStatus(String name, String nameB, String cssLabelName) {
			setName(name);
			setNameB(nameB);
			setCssLabelName(cssLabelName);
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getCssLabelName() {
			return cssLabelName;
		}

		public void setCssLabelName(String cssLabelName) {
			this.cssLabelName = cssLabelName;
		}

		public String getNameB() {
			return nameB;
		}

		public void setNameB(String nameB) {
			this.nameB = nameB;
		}

		@Override
		public String getJsonDispName() {
			return this.getName();
		}

		@Override
		public String getJsonDescription() {
			return null;
		}

		@Override
		public String getJsonLabelClass() {
			return this.getCssLabelName();
		}

		@Override
		public String getJsonButtonClass() {
			return null;
		}
	}

	/**
	 * オーダーステータス
	 *
	 * @author kitazawatakuya
	 *
	 */
	public enum ExecuteStatus implements BaseEnum {
		ON("実行する"), OFF("実行しない"),;
		private String name;

		private ExecuteStatus(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public String getJsonDispName() {
			return this.getName();
		}

		@Override
		public String getJsonDescription() {
			return null;
		}

		@Override
		public String getJsonLabelClass() {
			return null;
		}

		@Override
		public String getJsonButtonClass() {
			return null;
		}
	}

	// 出品代行モニターの方々SKU、氏名
	public enum OptyMonitorSKU implements BaseEnum {
		MONITOR_1("KGTR", "川口トクロウ"), //
		MONITOR_2("GOTK", "後藤ケイタ"), //
		MONITOR_3("NHZK", "中島　宏和"), //
		MONITOR_4("MATW", "松永　順一"), //
		MONITOR_5("BUSU", "毒島章一郎"), //
		MONITOR_6("YORU", "吉屋龍一"), //
		MONITOR_7("OGAW", "小川孝子"), //
		MONITOR_8("MAMU", "今村　優"), //
		MONITOR_9("SATS", "斉藤篤"), //
		MONITOR_10("TDDK", "竹田 直樹"), //
		MONITOR_11("SENG", "末永　成司"), //
		MONITOR_12("FKYU", "福元　嘉之"), //
		MONITOR_13("TAKT", "高橋　勉"), //
		MONITOR_14("MOLI", "森伸仁"), //
		MONITOR_15("KNKZ", "こうの康士"), //
		MONITOR_16("SONE", "曽根友視"), //
		MONITOR_17("", "NANA"), // RmUserにいない
		MONITOR_18("SGMT", "杉浦 元規"), //
		MONITOR_19("OOKU", "大倉誠"), //
		MONITOR_20("YMOM", "矢守崇宏"), //
		MONITOR_21("TDEJ", "戸田英司"), //
		MONITOR_22("KIMS", "岸本　健司"), //
		MONITOR_23("GOUD", "合田尚志"), //
		MONITOR_24("SION", "塩田宣弘"), //
		MONITOR_25("SGYH", "菅良穂"), //
		MONITOR_26("MEKO", "前田浩一"), //
		MONITOR_27("HAND", "羽根田政則"), //
		MONITOR_28("KOMR", "小室　純一"), //
		MONITOR_29("HADR", "原田昌昭"), //
		MONITOR_30("TOKS", "ショッピングガイド"), //
		MONITOR_31("MZTU", "松澤夏苗"), //
		MONITOR_32("HADA", "秦　齊崇"), //
		MONITOR_33("", "原口たぁ"), // RmUserにいない
		MONITOR_34("HADA", "秦　齊崇"), //
		MONITOR_35("TASU", "安斎龍男"), //
		MONITOR_36("", "たなぴー"), // RmUserにいない
		MONITOR_37("NAHI", "仲田ひで"), //
		MONITOR_38("FUJN", "藤田　良尚"),
		// MONITOR_39("MEIS", "吉田明正"),
		MONITOR_40("YAMS", "山中 繁幸"), //
		MONITOR_41("YSMA", "山中　雅恵"), //
		MONITOR_42("", "うのはま"), // RmUserにいない
		MONITOR_43("SBIA", "柴田光玲"), //
		MONITOR_44("MZOY", "水尾吉孝"), //
		MONITOR_45("UBKT", "生方 宏明"), //
		MONITOR_46("OBSK", "株式会社オーバーカムラス"),;
		private String sku;
		private String name;

		private OptyMonitorSKU(String sku, String name) {
			this.setSku(sku);
			this.setName(name);
		}

		public void setSku(String sku) {
			this.sku = sku;
		}

		public String getSku() {
			return sku;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		@Override
		public String getJsonDispName() {
			return this.getName();
		}

		@Override
		public String getJsonDescription() {
			return this.getSku();
		}

		@Override
		public String getJsonLabelClass() {
			return null;
		}

		@Override
		public String getJsonButtonClass() {
			return null;
		}

	}

	// レポート種別
	public static enum StockReportType implements BaseEnum {
		SALES("販売代行"), CUSTOMER("出品代行"),;
		private String name;

		private StockReportType(String name) {
			this.setName(name);
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		@Override
		public String getJsonDispName() {
			return this.getName();
		}

		@Override
		public String getJsonDescription() {
			return null;
		}

		@Override
		public String getJsonLabelClass() {
			return null;
		}

		@Override
		public String getJsonButtonClass() {
			return null;
		}

	}

	/**
	 * CC便の荷物の状態
	 *
	 * @author taka
	 *
	 */
	public enum CcPackageStatus implements BaseEnum {
		REQUESTED("未対応", "処理中", 1), //
		PICKUP_READY("集荷待ち", "依頼済み", 2), //
		PICKUPED("集荷済み", "弊社輸送中", 3), //
		CNCT_ARRIVED("コネクト到着済み", "弊社倉庫到着", 4), //
		SEND_READY("出荷待ち", "出荷作業中", 5), //
		NOT_SEND("滞留中", "出荷作業中", 6), // コネクト到着後、なかなかステータスが動かない場合
		SENDED("出荷済み", "FBA輸送中", 7), //
		DST_ARRIVED("到着済み", "到着済み", 8), //
		FINISHED("完了", "到着済み", 9), //
		CANCELED("キャンセル", "キャンセル", 10),;

		private String name;
		private String publicName;
		private int packageStatusNum;

		CcPackageStatus(String name, String publicName, int packageStatusNum) {
			this.setName(name);
			this.setPublicName(publicName);
			this.setPackageStatusNum(packageStatusNum);
		}

		@Override
		public String getJsonDispName() {
			return this.getName();
		}

		@Override
		public String getJsonDescription() {
			return null;
		}

		@Override
		public String getJsonLabelClass() {
			return null;
		}

		@Override
		public String getJsonButtonClass() {
			return null;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getPublicName() {
			return publicName;
		}

		public void setPublicName(String publicName) {
			this.publicName = publicName;
		}

		public int getPackageStatusNum() {
			return packageStatusNum;
		}

		public void setPackageStatusNum(int packageStatusNum) {
			this.packageStatusNum = packageStatusNum;
		}

		public static CcPackageStatus numToStatus(int packageStatusNum) {
			for(CcPackageStatus status : CcPackageStatus.values()) {
				if(packageStatusNum == status.getPackageStatusNum()) {
					return status;
				}
			}
			return null;
		}

	}

	/**
	 * CC便の配送会社
	 *
	 * @author taka
	 *
	 */
	public enum CcDeliveryCompany implements BaseEnum {
		NONE("業者なし"), //
		YAMATO("ヤマト運輸"), //
		SEINO("西濃運輸"), //
		SAGAWA("佐川急便"), //
		CONNECT("自社チャーター便"),;
		private String name;

		CcDeliveryCompany(String name) {
			this.setName(name);
		}

		@Override
		public String getJsonDispName() {
			return this.getName();
		}

		@Override
		public String getJsonDescription() {
			return null;
		}

		@Override
		public String getJsonLabelClass() {
			return null;
		}

		@Override
		public String getJsonButtonClass() {
			return null;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	/**
	 * ユーザーエージェント
	 *
	 * @author ueda.daiki
	 *
	 */
	public static enum UA {
		A20("Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0)"), //
		A21("Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0; Sleipnir/2.9.8)"), //
		A22("Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.0; Trident/5.0)"), //
		A23("Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; Trident/6.0)"), //
		A24("Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.2; Win64; x64; Trident/6.0)"), //
		A25("Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.2; Trident/6.0)"), //
		A26("Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.2; WOW64; Trident/6.0)"), //
		A27("Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.2; Win64; x64; Trident/6.0)"), //
		A28("Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.2; ARM; Trident/6.0)"), //
		A29("Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; Touch; rv:11.0) like Gecko"), //
		A30("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.52 Safari/537.36"), //
		A31("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.63 Safari/537.36"), //
		A32("Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_3; ja-jp) AppleWebKit/533.16 (KHTML, like Gecko) Version/5.0 Safari/533.16"), //
		A33("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_6_8) AppleWebKit/534.52.7 (KHTML, like Gecko) Version/5.1.2 Safari/534.52.7"), //
		A34("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_6_8) AppleWebKit/534.57.2 (KHTML, like Gecko) Version/5.1.7 Safari/534.57.2"), //
		A35("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8) AppleWebKit/536.25 (KHTML, like Gecko) Version/6.0 Safari/536.25"),;

		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		private UA(String name) {
			this.name = name;
		}

		// ランダムで取得します。
		public static UA getRandom() {
			UA[] uas = UA.values();
			int size = uas.length;
			int ran = (int) (Math.random() * (size));

			return uas[ran];
		}
	}

	/**
	 * Mail配信状態(ベースはThanksMailのSendFlg)
	 *
	 * @author taka
	 *
	 */
	public enum MailSendStatus implements BaseEnum {
		NO_SEND("配信しない"), RESERVE("配信予定"), SEND("配信済み"), SKIP("スキップ"),;
		private String name;

		private MailSendStatus(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public String getJsonDispName() {
			return this.name;
		}

		@Override
		public String getJsonDescription() {
			return null;
		}

		@Override
		public String getJsonLabelClass() {
			return this.name;
		}

		@Override
		public String getJsonButtonClass() {
			return null;
		}
	}

	/**
	 * Mail配信状態(ベースはThanksMailのSendFlg)
	 *
	 * @author taka
	 *
	 */
	public enum RmSystemPropertyName implements BaseEnum {
		TERM_UPD_DATE("規約更新日"), TERM_UPD_MSG("規約変更メッセージ");
		private String name;

		private RmSystemPropertyName(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public String getJsonDispName() {
			return this.name;
		}

		@Override
		public String getJsonDescription() {
			return null;
		}

		@Override
		public String getJsonLabelClass() {
			return this.name;
		}

		@Override
		public String getJsonButtonClass() {
			return null;
		}
	}

	/**
	 * CC便のレコードがどこから作られたか
	 *
	 * @author taka
	 *
	 */
	public enum CcInflowSourceKbn implements BaseEnum {
		CC("CC便"), CAPPY_CNCT("CAPPY（コネクト）"), CAPPY_CAN("CAPPY（CAN）");

		private String name;

		private CcInflowSourceKbn(String name) {
			this.name = name;
		}

		@Override
		public String getJsonDispName() {
			return this.name;
		}

		@Override
		public String getJsonDescription() {
			return null;
		}

		@Override
		public String getJsonLabelClass() {
			return this.name;
		}

		@Override
		public String getJsonButtonClass() {
			return null;
		}
	}

	public static final int ecFrontPageLimit = 100;
}