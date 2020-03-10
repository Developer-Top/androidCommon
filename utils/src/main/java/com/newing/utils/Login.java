package com.newing.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.Serializable;

public class Login implements Serializable {

	public static Login mLogin;
	public static final String TAG = "Login";
	public static int BUY_CARD_NOT = 0;
	public static int BUY_CARD_YES = 2;
	static final String sharePreferenceId = "com.yumei.android.ymcommon.Login";
	public String account;
	public long id;
	public String token;
	public long tokenExpireMillion;
	public int buyCard = 0;
	public String bindedMobile;
	public String bindedEmail;
	public String avantarUrl;
	public String blueName;
	public String blueAddress;
	public static String deviceId;
	public static String ID_DEVICE_ID = "DEVICE_ID";

	public String realName;  //真实姓名
	public String certNo;   //身份证号码
	public String supply_stage;  //是否补全信息
	public String cert_status;  // 实名认证状态
	public String user_id;
	public String user_type;
	public String share_url;
	public String recommend_code;
	public String share_code;

	public String wechat_share_url;//微信支付宝收款二维码


	public String extra_status;  //实名认证状态
	public String photo_status;  //实名认证错误图片
	public String certify_fail_reason;
	public Login() {
	}

	public Login(String account, long id, String token) {
		this.account = account;
		this.id = id;
		this.token = token;
	}

	public static Login loadLoginInfo(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				"com.yumei.android.ymcommon.Login", 0);
		Login login = new Login();
		login.id = preferences.getLong("id", 0L);
		login.token = preferences.getString("token", "");
		login.tokenExpireMillion = preferences
				.getLong("tokenExpireMillion", 0L);
		login.bindedMobile = preferences.getString("bindedMobile", "");
		login.account = preferences.getString("account", "");
		if (login.account == null || login.account.isEmpty()) {
			login.account = preferences.getString("name", "");
		}

		login.buyCard = preferences.getInt("buyCard", 0);
		login.avantarUrl = preferences.getString("avantarUrl", "");
		login.blueName = preferences.getString("blueName", (String) null);
		login.blueAddress = preferences.getString("blueAddress", (String) null);

		login.realName = preferences.getString("realName", "");
		login.certNo = preferences.getString("certNo", "");
		login.supply_stage = preferences.getString("supply_stage", "");
		login.cert_status = preferences.getString("cert_status", "");
		login.user_id = preferences.getString("user_id", "");
		login.user_type = preferences.getString("user_type", "");
		login.share_url = preferences.getString("share_url", "");
		login.recommend_code = preferences.getString("recommend_code", "");
		login.share_code = preferences.getString("share_code", "");
		login.wechat_share_url=preferences.getString("wechat_share_url", "");
		login.extra_status=preferences.getString("extra_status", "");
		login.photo_status=preferences.getString("photo_status", "");
		login.certify_fail_reason=preferences.getString("certify_fail_reason", "");


		Login.mLogin=login;
		return login;
	}

	public static void write(Context context, Login login) {
		SharedPreferences preferences = context.getSharedPreferences(
				"com.yumei.android.ymcommon.Login", 0);
		Editor editor = preferences.edit();
		editor.putString("account", login.account);
		editor.putString("token", login.token);
		editor.putLong("id", login.id);
		editor.putLong("tokenExpireMillion", login.tokenExpireMillion);
		editor.putString("bindedMobile", login.bindedMobile);
		editor.putInt("buyCard", login.buyCard);
		editor.putString("recommend_code", login.recommend_code);
		editor.putString("avantarUrl", login.avantarUrl);
		editor.putString("blueName", login.blueName);
		editor.putString("blueAddress", login.blueAddress);

		editor.putString("realName", login.realName);
		editor.putString("certNo", login.certNo);
		editor.putString("supply_stage", login.supply_stage);
		editor.putString("cert_status", login.cert_status);
		editor.putString("user_id", login.user_id);
		editor.putString("user_type", login.user_type);
		editor.putString("share_url", login.share_url);
		editor.putString("recommend_code", login.recommend_code);
		editor.putString("share_code", login.share_code);
		editor.putString("wechat_share_url", login.wechat_share_url);
		editor.putString("extra_status", login.extra_status);
		editor.putString("photo_status", login.photo_status);
		editor.putString("certify_fail_reason", login.certify_fail_reason);

		editor.commit();
	}

	public static void clear(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				"com.yumei.android.ymcommon.Login", 0);
		Editor editor = preferences.edit();
		editor.clear();
		editor.commit();
	}

	public static String getDeviceId(Context context) {
		String deviceId = getDeviceIdFromSharedPreferences(context);
		Log.v("Login", "p=1");
		if (!deviceId.isEmpty()) {
			Log.v("Login", "p=2");
			return deviceId;
		} else {
			deviceId = getDeviceIdFromService(context);
			SharedPreferences preferences = context.getSharedPreferences(
					"com.yumei.android.ymcommon.Login", 0);
			Editor editor = preferences.edit();
			editor.putString(ID_DEVICE_ID, deviceId);
			editor.commit();
			Log.v("Login", "p=3");
			return deviceId;
		}
	}

	public static String getDeviceIdFromSharedPreferences(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				Login.class.getCanonicalName().toString(), 0);
		String deviceId = preferences.getString(ID_DEVICE_ID, "");
		return deviceId;
	}

	public static String getDeviceIdFromService(Context context) {
		TelephonyManager manager = (TelephonyManager) context
				.getSystemService("phone");
		return manager.getDeviceId();
	}
}
