package com.zongzi.czm2.utils;

import android.content.Context;
import android.graphics.Typeface;

public class Constant {
	public static final int COUNT = 50;//关卡总数 count*4关
	public static int pass_count = 1;
	public static int select_count = 1;
	public static int showAdAmout = 3;
	public static int showDialog = 2;
	public static int recodCount = COUNT*4;
	public static int myMoney = 9999;
	public static Typeface setFonts(Context context	){
		return  Typeface.createFromAsset(context.getAssets(), "fonts/JDQT.TTF");
	}
}
