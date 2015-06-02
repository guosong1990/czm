package com.zongzi.czm2.utils;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import cn.waps.AppConnect;

import com.zongzi.czm2.GameActivity;
import com.zongzi.czm2.R;

public class CustomDialog extends Dialog implements
		android.view.View.OnClickListener {

	/** 布局文件 **/
	int layoutRes;
	/** 上下文对�?**/
	Context context;
	/** 确定按钮 **/
	private Button confirmBtn;
	/** 取消按钮 **/
	private Button cancelBtn;
	/** 离线消息按钮 **/
	private RadioButton myRadioButton;
	/** 点击次数 **/
	private int check_count = 0;
	/** Toast时间 **/
	public static final int TOAST_TIME = 1000;
	private TextView tipTitle,tipContent;
	public int type;
	public CustomDialog(Context context) {
		super(context);
		this.context = context;
	}

	/**
	 * 自定义布�?��构�?方法
	 * 
	 * @param context
	 * @param resLayout
	 */
	public CustomDialog(Context context, int resLayout) {
		super(context);
		this.context = context;
		this.layoutRes = resLayout;
	}

	/**
	 * 自定义主题及布局的构造方�?
	 * 
	 * @param context
	 * @param theme
	 * @param resLayout
	 */
	public CustomDialog(Context context, int theme, int resLayout,int type) {
		super(context, theme);
		this.context = context;
		this.layoutRes = resLayout;
		this.setCancelable(false);
		this.type = type;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 指定布局
		this.setContentView(layoutRes);

		// 根据id在布�?��找到控件对象
		confirmBtn = (Button) findViewById(R.id.confirm_btn);
		cancelBtn = (Button) findViewById(R.id.cancel_btn);
		tipTitle  = (TextView) findViewById(R.id.tipTitle);
		tipContent = (TextView) findViewById(R.id.tipContent);
		//myRadioButton = (RadioButton) findViewById(R.id.my_rbtn);

		// 设置按钮的文本颜�?
		confirmBtn.setTextColor(0xff1E90FF);
		cancelBtn.setTextColor(0xff1E90FF);

		// 为按钮绑定点击事件监听器
		confirmBtn.setOnClickListener(this);
		cancelBtn.setOnClickListener(this);
		//myRadioButton.setOnClickListener(this);
		switch (type) {
		case 1:
			
			break;
		case 2:
			tipContent.setText(R.string.getMoney);
			tipTitle.setText("温馨提示");
			confirmBtn.setText("给评获");
			cancelBtn.setText("下应用");
			break;
		default:
			break;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.confirm_btn:
			switch (type) {
			case 1:
				Message msg = new Message();
				msg.what = 0;
				GameActivity.instance.handler.sendMessage(msg);
				this.dismiss();
				break;
			case 2:
				if(Constant.good==1){
					// 点击了确认按�?
					Toast.makeText(context, "感谢你的支持", TOAST_TIME).show();
					this.dismiss();
					String mAddress = "market://details?id="+context.getPackageName();
					//Log.e("包名", getPackageName());
					Intent intent = new Intent("android.intent.action.VIEW");
					intent.setData(Uri.parse(mAddress));
					context.startActivity(intent);
					Constant.myMoney+=100;
					GameActivity.instance.money.setText(""+Constant.myMoney);
					Constant.good = 2;
					AppConnect.getInstance(context).awardPoints(100);
					this.dismiss();
					//AppConnect.getInstance(context).spendPoints();
				}else {
					Toast.makeText(context, "亲，你已经好评过了，只能通过下应用来赚金币了！", TOAST_TIME).show();
				}

				break;
			default:
				break;
			}
			
			break;

		case R.id.cancel_btn:
			switch (type) {
			case 1:
				// 点击了取消
				Intent intent1 = new Intent(Intent.ACTION_SEND);   
		        intent1.putExtra(Intent.EXTRA_TEXT, Constant.message.getMessage()+Constant.endString);    
		        intent1.setType("text/plain"); 
		        context.startActivity(intent1); 
				Message msg2 = new Message();
				msg2.what = 0;
				GameActivity.instance.handler.sendMessage(msg2);
				break;
			case 2:
				Constant.showWard(GameActivity.instance);
				break;
			default:
				break;
			}
			this.dismiss();
			break;

		default:
			break;
		}
	}
}