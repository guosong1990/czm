package com.zongzi.czm2;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zongzi.czm2.bean.Message;
import com.zongzi.czm2.service.DataService;
import com.zongzi.czm2.utils.Constant;

public class GameActivity extends Activity implements OnClickListener {
	LinearLayout linearLayout1, linearLayout2, linearLayout3;
	View textView1, textView2, textView3, textView4, textView5, textView6,
			textView7;
	TextView text1, text2, text3, text4, text5, text6, text7, answerTextView,
			textview_content, tileView, money;
	Message message = null;
	DataService service;
	int col, raw;
	int initCount = 1;
	private Handler handler = new Handler();

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
		tileView = (TextView) findViewById(R.id.title_level);
		tileView.setTypeface(Constant.setFonts(this));
		answerTextView.setTypeface(Constant.setFonts(this));
		money = (TextView) findViewById(R.id.mymoney);
		money.setTypeface(Constant.setFonts(this));
		money.setVisibility(View.VISIBLE);
		/*
		 * Thread thread = new Thread(myRunnable); thread.start();
		 */
		handler.post(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				initAnswer();
			}

		});

	}

	public void initAnswer() {
		money.setText(""+Constant.myMoney);
		findViewById(R.id.title_back).setVisibility(View.VISIBLE);
		answerTextView.setText("猜");
		tileView.setText("第" + Constant.select_count + "关");
		message = service.getMessageById(this, Constant.select_count);
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
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v instanceof TextView) {
			TextView textView = (TextView) v;
			answerTextView.setText(textView.getText());
			if (textView.getText().equals(message.getAnswer())) {
				Toast.makeText(this, "恭喜你答对了,已经进入下一题", Toast.LENGTH_LONG)
						.show();
				++Constant.select_count;

				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
				}
				initAnswer();
			}
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
/*		text1.setTypeface(Constant.setFonts(GameActivity.this));
		text2.setTypeface(Constant.setFonts(GameActivity.this));
		text3.setTypeface(Constant.setFonts(GameActivity.this));
		text4.setTypeface(Constant.setFonts(GameActivity.this));
		text5.setTypeface(Constant.setFonts(GameActivity.this));
		text6.setTypeface(Constant.setFonts(GameActivity.this));
		text7.setTypeface(Constant.setFonts(GameActivity.this));*/

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

}
