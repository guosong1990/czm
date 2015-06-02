package com.zongzi.czm2;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.sdm.ehf.Kdn;
import com.umeng.analytics.MobclickAgent;
import com.zongzi.czm2.service.DataService;
import com.zongzi.czm2.utils.Constant;

public class StartActivity extends Activity implements OnClickListener {
	private DataService dataService;
	private TextView loading, version;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start);
		ImageView startImageView = (ImageView) findViewById(R.id.start);
		startImageView.setOnClickListener(this);
		loading = (TextView) findViewById(R.id.loading);
		version = (TextView) findViewById(R.id.version);
		loading.setTypeface(Constant.setFonts(this));
		version.setTypeface(Constant.setFonts(this));
		dataService = new DataService();
		dataService.initData();
		
		//ad
		Constant.initAd(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.start:
			showProcessgress(true);
			Thread thread = new Thread(new Runnable() {
				public void run() {
					// do...
					Message message = new Message();
					message.what = 0;
					handler.sendMessage(message);
				}
			});
			thread.start();
			break;
		default:
			break;
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		showProcessgress(false);
		MobclickAgent.onResume(this);
	}

	public void showProcessgress(boolean swith) {
		if (swith) {
			findViewById(R.id.loading_pic).setVisibility(View.VISIBLE);
			findViewById(R.id.loading).setVisibility(View.VISIBLE);
		} else {
			findViewById(R.id.loading_pic).setVisibility(View.GONE);
			findViewById(R.id.loading).setVisibility(View.GONE);
		}
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message message) {
			Log.e("message", message.what+"");
			switch (message.what) {
			case 0:
				Intent intent = new Intent(StartActivity.this, MenuActivity.class);
				startActivity(intent);
				showProcessgress(false);
				break;

			default:
				break;
			}

		}
	};
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
				// 调用�?��广告
			// �?��广告
			Kdn.type2(this,
					new DialogInterface.OnClickListener() {
						// 点击事件
						@Override
						public void onClick(DialogInterface dialog,
								int which) {
							switch (which) {
							case Dialog.BUTTON_POSITIVE:// yes
								finish();
								break;
							case Dialog.BUTTON_NEGATIVE:// no
								break;
							}
						}
					});
		
		}
		return true;
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
}
