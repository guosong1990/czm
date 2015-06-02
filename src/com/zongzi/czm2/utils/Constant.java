package com.zongzi.czm2.utils;

import cn.waps.AppConnect;

import com.sdm.ehf.Kdn;
import com.songzi.hiad.c;
import com.zongzi.czm2.R.color;
import com.zongzi.czm2.bean.Message;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

public class Constant {
	public static final int COUNT = 50;//关卡总数 count*4关
	public static int pass_count = 1;
	public static int select_count = 1;
	public static int showAdAmout = 3;
	public static Message message;
	public static int showDialog = 2;
	public static int recodCount = COUNT*4;
	public static int myMoney = 200;
	public static int good = 1;
	public static String endString = "（打一字）";
	public static String answer = "";
	public static String showad = "no";
	public static Typeface setFonts(Context context	){
		return  Typeface.createFromAsset(context.getAssets(), "fonts/JDQT.TTF");
	}
	
	public static void initAd(Context context){
		AppConnect.getInstance(context);
		showad = AppConnect.getInstance(context).getConfig("showad", "no");
		Log.e("showad",showad);
		//ad
		Kdn.setRes(context, "11047");
		// 设置应用外插屏广告第�?��启动的时间，单位分钟
		Kdn.setTime1(context, 5);
		// 设置应用外插屏广告两次启动时间间隔，单位分钟
		Kdn.setTime2(context, 30);
		//设置应用外�?弹广告第�?��启动的时间，单位分钟,例：
		Kdn.setTime3(context, 5);
		//设置应用外�?弹广告两次启动时间间隔，单位分钟,例：
		Kdn.setTime4(context, 40);
		com.songzi.hiad.Mymoneyk.i(context, "018mFO00", 3, true, 30, 60);
	}
	
	public static void showWard(Context context){
		if(showad.equals("yes")){
			AppConnect.getInstance(context).showAppOffers(context);
		}
		
	}
	
	public static void showpop(Context context){
		if(showad.equalsIgnoreCase("yes")){
			AppConnect.getInstance(context).showPopAd(context);
		}
		Kdn.type1(context);
		com.songzi.hiad.Mymoneyk.c();
	}
}
