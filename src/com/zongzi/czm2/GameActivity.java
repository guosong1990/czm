package com.zongzi.czm2;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import u.aly.m;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.waps.AppConnect;
import cn.waps.AppListener;
import cn.waps.UpdatePointsNotifier;

import com.umeng.analytics.MobclickAgent;
import com.zongzi.czm2.bean.Message;
import com.zongzi.czm2.service.DataService;
import com.zongzi.czm2.utils.Constant;
import com.zongzi.czm2.utils.CustomDialog;

public class GameActivity extends Activity implements OnClickListener,
		UpdatePointsNotifier {
	LinearLayout linearLayout1, linearLayout2, linearLayout3;
	View textView1, textView2, textView3, textView4, textView5, textView6,
			textView7;
	TextView text1, text2, text3, text4, text5, text6, text7, answerTextView,
			textview_content, tileView;
	public TextView money;
	Message message = null;
	DataService service;
	int col, raw;
	int initCount = 1;
	public static GameActivity instance;
	boolean tipSwith = false;
	int showadCout = 0;
	ImageView share, tip;
	public Handler handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				initAnswer();
				break;
			case 1:
				money.setText("" + Constant.myMoney);
				break;
			default:
				break;
			}
		};
	};

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.play);
		answerTextView = (TextView) findViewById(R.id.textview_answer);
		service = new DataService();
		textview_content = (TextView) findViewById(R.id.textview_content);
		textview_content.setTypeface(Constant.setFonts(this));
		linearLayout1 = (LinearLayout) findViewById(R.id.linearlayout_content_1);
		linearLayout2 = (LinearLayout) findViewById(R.id.linearlayout_content_2);
		linearLayout3 = (LinearLayout) findViewById(R.id.linearlayout_content_3);
		share = (ImageView) findViewById(R.id.imageview_share);
		tip = (ImageView) findViewById(R.id.imageview_tip);
		// clear = (ImageView) findViewById(R.id.imageview_clear);
		tileView = (TextView) findViewById(R.id.title_level);
		tip.setOnClickListener(this);
		share.setOnClickListener(this);
		// clear.setOnClickListener(this);
		tileView.setTypeface(Constant.setFonts(this));
		// answerTextView.setTypeface(Constant.setFonts(this));
		money = (TextView) findViewById(R.id.mymoney);
		money.setTypeface(Constant.setFonts(this));
		money.setVisibility(View.VISIBLE);
		initAnswer();
		instance = this;
		// ad
		// 设置关闭积分墙癿监听接口，必须在showOffers接口之前调用
		AppConnect.getInstance(this).setOffersCloseListener(new AppListener() {
			@Override
			public void onOffersClose() {
				// TODO 关闭积分墙时癿操作代码
				AppConnect.getInstance(GameActivity.this).getPoints(
						GameActivity.this);
			}
		});

		
	}

	public void initAnswer() {
		if(showadCout%5==0){
			Constant.showpop(this);
		}
		showadCout++;
		
		tipSwith = false;
		initCount = 1;
		money.setText("" + Constant.myMoney);
		findViewById(R.id.title_back).setVisibility(View.VISIBLE);
		answerTextView.setText("猜");
		tileView.setText("第" + Constant.select_count + "关");
		message = service.getMessageById(this, Constant.select_count);
		Constant.message = message;
		Constant.answer = message.getAnswer();
		textview_content.setText(message.getMessage());
		col = new Random().nextInt(3) + 1;
		raw = new Random().nextInt(7) + 1;
		Log.e("answer",
				"答案在" + col + ":" + raw + ",题目为：" + message.getMessage() + ":"
						+ message.getAnswer());
		linearLayout1.removeAllViews();
		linearLayout2.removeAllViews();
		linearLayout3.removeAllViews();
		loadView(linearLayout1, col, raw);
		loadView(linearLayout2, col, raw);
		loadView(linearLayout3, col, raw);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		AppConnect.getInstance(this).getPoints(this);
		MobclickAgent.onResume(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v instanceof TextView) {
			TextView textView = (TextView) v;
			answerTextView.setText(textView.getText());
			if (textView.getText().equals(message.getAnswer())) {

				++Constant.select_count;
				CustomDialog dialog = new CustomDialog(this, R.style.mystyle,
						R.layout.customdialog, 1);
				dialog.show();
				if(Constant.select_count>=Constant.pass_count){
					Constant.myMoney += 10;
					AppConnect.getInstance(this).awardPoints(10);
				}
			} else {
				if (Constant.myMoney >= 5) {
					Toast.makeText(this, "错了扣除5个金币", Toast.LENGTH_SHORT).show();
					Constant.myMoney -= 5;
					AppConnect.getInstance(this).spendPoints(5);
					money.setText("" + Constant.myMoney);
				} else {
					CustomDialog dialog = new CustomDialog(this,
							R.style.mystyle, R.layout.customdialog, 2);
					dialog.show();
					Toast.makeText(this, "金币已使用完毕！", Toast.LENGTH_SHORT).show();
				}
			}
		}
		switch (v.getId()) {
		case R.id.imageview_tip:
			if (tipSwith) {
				break;
			}
			if (Constant.myMoney >= 20) {
				Toast.makeText(this, "答案是：" + Constant.answer + ",扣除金币20个",
						Toast.LENGTH_SHORT).show();
				Constant.myMoney -= 20;
				AppConnect.getInstance(this).spendPoints(20);
				money.setText("" + Constant.myMoney);
				tipSwith = true;
			} else {
				CustomDialog dialog = new CustomDialog(this, R.style.mystyle,
						R.layout.customdialog, 2);
				dialog.show();
				Toast.makeText(this, "金币已使用完毕！", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.imageview_share:
			Intent intent = new Intent(Intent.ACTION_SEND);
			intent.putExtra(Intent.EXTRA_TEXT, message.getMessage()
					+ Constant.endString);
			intent.setType("text/plain");
			startActivity(intent);
			break;
		default:
			break;
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if (Constant.pass_count < Constant.select_count) {
			Constant.pass_count = Constant.select_count;
			service.alertAdConfig(Constant.pass_count);
		}
		MobclickAgent.onPause(this);
	}

	public void loadView(LinearLayout layout, int col, int raw) {
		int localRaw = 1;
		textView1 = View.inflate(this, R.layout.answer_item, null);
		textView2 = View.inflate(this, R.layout.answer_item, null);
		textView3 = View.inflate(this, R.layout.answer_item, null);
		textView4 = View.inflate(this, R.layout.answer_item, null);
		textView5 = View.inflate(this, R.layout.answer_item, null);
		textView6 = View.inflate(this, R.layout.answer_item, null);
		textView7 = View.inflate(this, R.layout.answer_item, null);
		layout.addView(textView1);
		layout.addView(textView2);
		layout.addView(textView3);
		layout.addView(textView4);
		layout.addView(textView5);
		layout.addView(textView6);
		layout.addView(textView7);
		text1 = (TextView) textView1.findViewById(R.id.answer_item);
		text2 = (TextView) textView2.findViewById(R.id.answer_item);
		text3 = (TextView) textView3.findViewById(R.id.answer_item);
		text4 = (TextView) textView4.findViewById(R.id.answer_item);
		text5 = (TextView) textView5.findViewById(R.id.answer_item);
		text6 = (TextView) textView6.findViewById(R.id.answer_item);
		text7 = (TextView) textView7.findViewById(R.id.answer_item);

		text1.setText((initCount == col && (localRaw++) == raw) ? message
				.getAnswer() : createRandomChinse());
		text2.setText((initCount == col && (localRaw++) == raw) ? message
				.getAnswer() : createRandomChinse());
		text3.setText((initCount == col && (localRaw++) == raw) ? message
				.getAnswer() : createRandomChinse());
		text4.setText((initCount == col && (localRaw++) == raw) ? message
				.getAnswer() : createRandomChinse());
		text5.setText((initCount == col && (localRaw++) == raw) ? message
				.getAnswer() : createRandomChinse());
		text6.setText((initCount == col && (localRaw++) == raw) ? message
				.getAnswer() : createRandomChinse());
		text7.setText((initCount == col && (localRaw++) == raw) ? message
				.getAnswer() : createRandomChinse());
		text1.setOnClickListener(this);
		text2.setOnClickListener(this);
		text3.setOnClickListener(this);
		text4.setOnClickListener(this);
		text5.setOnClickListener(this);
		text6.setOnClickListener(this);
		text7.setOnClickListener(this);
		initCount++;
	}

	int count = 0;

	public String createRandomChinse() {
		try {
			String str = null;
			int hightPos, lowPos; // 定义高低位
			Random random = new Random();
			hightPos = (176 + Math.abs(random.nextInt(39)));// 获取高位值
			lowPos = (161 + Math.abs(random.nextInt(93)));// 获取低位值
			byte[] b = new byte[2];
			b[0] = (new Integer(hightPos).byteValue());
			b[1] = (new Integer(lowPos).byteValue());
			str = new String(b, "GBk");
			return str;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// 转成中文

		return null;
	}

	@Override
	public void getUpdatePoints(String arg0, int arg1) {
		// TODO Auto-generated method stub
		Constant.myMoney = arg1;
		android.os.Message message = new android.os.Message();
		message.what = 1;
		handler.sendMessage(message);
	}

	@Override
	public void getUpdatePointsFailed(String arg0) {
		// TODO Auto-generated method stub

	}


}
