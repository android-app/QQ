package com.whyp.ycuservice.activity;

import net.youmi.android.spot.SpotManager;

import com.whyp.ycuservice.R;
import com.whyp.ycuservice.fragment.QQconstactFragment;
import com.whyp.ycuservice.view.TitleBarView;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

public class QQconstactActivity extends FragmentActivity {
	private Context mContext;
	private TitleBarView mTitleBarView;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext=this;
		setContentView(R.layout.activity_qq_constact);
		
		findView();
		init();
	}
	
	private void findView(){
		mTitleBarView=(TitleBarView) findViewById(R.id.title_bar);
	}
	
	private void init(){
		mTitleBarView.setCommonTitle(View.GONE, View.VISIBLE, View.GONE, View.GONE);
		mTitleBarView.setTitleText(R.string.choose_constact);
		SpotManager.getInstance(this).loadSpotAds();
		SpotManager.getInstance(this).setSpotTimeout(3000);
		SpotManager.getInstance(this).showSpotAds(this);
		FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
		QQconstactFragment qQconstactFragment=new QQconstactFragment();
		ft.replace(R.id.rl_constact, qQconstactFragment);
		ft.commit();
	}

}
