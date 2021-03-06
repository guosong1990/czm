package com.zihao;

import com.zihao.utils.Constant;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.SyncStateContract.Constants;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MenuActivity extends Activity implements OnClickListener {
	/** 退出按钮 **/
	View chView;
	LinearLayout adlLayout;
	ImageView leftImageView;
	ImageView rightImageView;
	TextView grade_tv_1,grade_tv_2,grade_tv_3,grade_tv_4;
	ImageView imageview_1,imageview_2,imageview_3,imageview_4;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.grade);
		initgrade(Constant.COUNT);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v instanceof TextView){
			TextView textView = (TextView) v;
			int count = Integer.parseInt(textView.getText().toString());
			if(count<=Constant.pass_count){
				Intent intent = new Intent(this,GameActivity.class);
				this.startActivity(intent);
				Toast.makeText(this, "第"+count+"关", Toast.LENGTH_LONG).show();
			}else{
				Toast.makeText(this, "亲，此关口还没解锁。", Toast.LENGTH_LONG).show();
			}
		}
	
		switch (v.getId()) {
		case 1:
			
			break;

		default:
			break;
		}
	}
	/**
	 * 初始化关卡
	 * @param count
	 */
	public void initgrade(int count){
		int gameCount = 0; 
		int raw = Constant.pass_count/4;
		int over = Constant.pass_count%4;
		for(int i = 0; i <count ; i++){
			adlLayout = (LinearLayout) findViewById(R.id.all_grade);
			chView = View.inflate(this, R.layout.grade_item, null);
			adlLayout.addView(chView);
			
			//数字显示
			grade_tv_1 = (TextView) chView.findViewById(R.id.grade_tv_1);
			grade_tv_2 = (TextView) chView.findViewById(R.id.grade_tv_2);
			grade_tv_3 = (TextView) chView.findViewById(R.id.grade_tv_3);
			grade_tv_4 = (TextView) chView.findViewById(R.id.grade_tv_4);
			grade_tv_1.setText(++gameCount+"");
			grade_tv_2.setText(++gameCount+"");
			grade_tv_3.setText(++gameCount+"");
			grade_tv_4.setText(++gameCount+"");
			
			//设置监听
			grade_tv_1.setOnClickListener(this);
			grade_tv_2.setOnClickListener(this);
			grade_tv_3.setOnClickListener(this);
			grade_tv_4.setOnClickListener(this);
			
			//已通过的显示不同背景
			if(i<=raw){
				Resources resources=getBaseContext().getResources(); 
				Drawable drawable=resources.getDrawable(R.drawable.grade_selected); 
				grade_tv_1.setBackground(drawable);
				grade_tv_2.setBackground(drawable);
				grade_tv_3.setBackground(drawable);
				grade_tv_4.setBackground(drawable);
			}
			
			if(i == raw+1 && over !=0){
				Resources resources=getBaseContext().getResources(); 
				Drawable drawable=resources.getDrawable(R.drawable.grade_selected); 
				switch (over) {
				case 3:
					grade_tv_1.setBackground(drawable);
					grade_tv_2.setBackground(drawable);
					grade_tv_3.setBackground(drawable);
					break;
				case 2:
					grade_tv_1.setBackground(drawable);
					grade_tv_2.setBackground(drawable);
					break;
				case 1:
					grade_tv_1.setBackground(drawable);
					break;
				default:
					break;
				}


			}
			//连线显示控制
			if(i == count-1 ){
				rightImageView = (ImageView)chView.findViewById(R.id.imageview_line_right);
				rightImageView.setVisibility(View.GONE);
				leftImageView = (ImageView)chView.findViewById(R.id.imageview_line_left);
				leftImageView.setVisibility(View.GONE);
				break;
			}
			
			if(i%2==1){
				rightImageView = (ImageView)chView.findViewById(R.id.imageview_line_right);
				rightImageView.setVisibility(View.GONE);
			}else {
				leftImageView = (ImageView)chView.findViewById(R.id.imageview_line_left);
				leftImageView.setVisibility(View.GONE);
			}

		}
	}
	
	
}
