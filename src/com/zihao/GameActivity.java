package com.zihao;

import java.io.UnsupportedEncodingException;
import java.util.IllegalFormatCodePointException;
import java.util.Random;

import org.w3c.dom.Text;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLayoutChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends Activity implements OnClickListener {
	LinearLayout linearLayout1, linearLayout2, linearLayout3;
	View textView1, textView2, textView3, textView4, textView5, textView6,
			textView7;
	TextView text1,text2,text3,text4,text5,text6,text7,answerTextView;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.play);
		answerTextView = (TextView) findViewById(R.id.textview_answer);
		initAnswer();
	}

	public void initAnswer() {
		linearLayout1 = (LinearLayout) findViewById(R.id.linearlayout_content_1);
		linearLayout2 = (LinearLayout) findViewById(R.id.linearlayout_content_2);
		linearLayout3 = (LinearLayout) findViewById(R.id.linearlayout_content_3);
		loadView(linearLayout1);
		loadView(linearLayout2);
		loadView(linearLayout3);
}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v instanceof TextView){
			TextView textView = (TextView) v;
			Toast.makeText(this, textView.getText(), Toast.LENGTH_LONG).show();
			answerTextView.setText(textView.getText());
		}
	}
	public void loadView(LinearLayout layout){
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
		text1.setText(createRandomChinse());
		text2.setText(createRandomChinse());
		text3.setText(createRandomChinse());
		text4.setText(createRandomChinse());
		text5.setText(createRandomChinse());
		text6.setText(createRandomChinse());
		text7.setText(createRandomChinse());
		text1.setOnClickListener(this);
		text2.setOnClickListener(this);
		text3.setOnClickListener(this);
		text4.setOnClickListener(this);
		text5.setOnClickListener(this);
		text6.setOnClickListener(this);
		text7.setOnClickListener(this);
	}	
	public String createRandomChinse(){
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
