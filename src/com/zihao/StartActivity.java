package com.zihao;

import java.util.Random;

import com.zihao.db.GameDao;
import com.zihao.utils.Constant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract.Constants;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class StartActivity extends Activity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start);
		ImageView startImageView = (ImageView) findViewById(R.id.start);
		startImageView.setOnClickListener(this);
		
	/*	GameDao gameDao = new GameDao(this);
		Log.e("selseData", gameDao.selectData()+"");*/
		Constant.pass_count = new Random().nextInt(400)+1;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.start:
			Intent intent = new Intent(this,MenuActivity.class);
			this.startActivity(intent);
			break;
		default:
			break;
		}
	}
	
	
}
