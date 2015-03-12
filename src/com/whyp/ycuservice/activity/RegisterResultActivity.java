package com.whyp.ycuservice.activity;

import com.whyp.ycuservice.R;
import com.whyp.ycuservice.view.TextURLView;
import com.whyp.ycuservice.view.TitleBarView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class RegisterResultActivity extends Activity {
	private Context mContext;
	private TitleBarView mTitleBarView;
	private TextURLView url;
	private Button complete;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_result);
		
		mContext=this;
		findView();
		initTitleView();
		initTvUrl();
		init();
	}
	
	private void findView(){
		mTitleBarView=(TitleBarView) findViewById(R.id.title_bar);
		url=(TextURLView) findViewById(R.id.tv_tiaokuan);
		complete=(Button) findViewById(R.id.register_success);
	}

	private void init(){
		complete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(mContext, LoginActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}
	
	private void initTitleView(){
		mTitleBarView.setCommonTitle(View.VISIBLE, View.VISIBLE,View.GONE, View.GONE);
		mTitleBarView.setTitleText(R.string.tv_register_success);
		mTitleBarView.setBtnLeft(R.drawable.boss_unipay_icon_back, R.string.back);
		mTitleBarView.setBtnLeftOnclickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
	}
	
	private void initTvUrl(){
		url.setText(R.string.tv_tiaokuan);
		url.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				
				
			}
		});
	}
}
