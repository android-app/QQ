package com.whyp.ycuservice.fragment;

import com.whyp.ycuservice.R;
import com.whyp.ycuservice.activity.ErcodeScanActivity;
import com.whyp.ycuservice.activity.WaterCameraActivity;
import com.whyp.ycuservice.view.TitleBarView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.PopupWindow.OnDismissListener;

public class NewsFatherFragment extends Fragment {
	private static final String TAG = "NewsFatherFragment";
	private Context mContext;
	private View mBaseView;
	private View mPopView;
	private TitleBarView mTitleBarView;
	private PopupWindow mPopupWindow;
	private ImageView mChats, mShare, mCamera, mScan;
	private RelativeLayout mCanversLayout;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContext=getActivity();
		mBaseView=inflater.inflate(R.layout.fragment_news_father, null);
		mPopView = inflater.inflate(R.layout.fragment_news_pop, null);
		findView();
		init();
		return mBaseView;
	}
	
	private void findView(){
		mTitleBarView=(TitleBarView) mBaseView.findViewById(R.id.title_bar);
		mChats = (ImageView) mPopView.findViewById(R.id.pop_chat);
		mShare = (ImageView) mPopView.findViewById(R.id.pop_sangzhao);
		mCamera = (ImageView) mPopView.findViewById(R.id.pop_camera);
		mScan = (ImageView) mPopView.findViewById(R.id.pop_scan);
		mCanversLayout=(RelativeLayout) mBaseView.findViewById(R.id.rl_canvers);
	}
	
	private void init(){
		mTitleBarView.setCommonTitle(View.GONE, View.GONE, View.VISIBLE,
				View.VISIBLE);
		mTitleBarView.setBtnRight(R.drawable.skin_conversation_title_right_btn);
		mTitleBarView.setBtnRightOnclickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mTitleBarView.setPopWindow(mPopupWindow, mTitleBarView);
				mCanversLayout.setVisibility(View.VISIBLE);
			}
		});

		mPopupWindow = new PopupWindow(mPopView, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT, true);
		mPopupWindow.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
				mTitleBarView
						.setBtnRight(R.drawable.skin_conversation_title_right_btn);
				mCanversLayout.setVisibility(View.GONE);
			}
		});


		mTitleBarView.setTitleLeft(R.string.cnews);
		mTitleBarView.setTitleRight(R.string.call);
		
		

		mTitleBarView.getTitleLeft().setOnClickListener(new OnClickListener() {
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				if (mTitleBarView.getTitleLeft().isEnabled()) {
					mTitleBarView.getTitleLeft().setEnabled(false);
					mTitleBarView.getTitleRight().setEnabled(true);
					
					FragmentTransaction ft=getFragmentManager().beginTransaction();
					NewsFragment newsFragment=new NewsFragment();
					ft.replace(R.id.child_fragment, newsFragment,NewsFatherFragment.TAG);
					//ft.addToBackStack(TAG);
					ft.commit();
				}
			}
		});

		mTitleBarView.getTitleRight().setOnClickListener(new OnClickListener() {
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				if (mTitleBarView.getTitleRight().isEnabled()) {
					mTitleBarView.getTitleLeft().setEnabled(true);
					mTitleBarView.getTitleRight().setEnabled(false);
					
					FragmentTransaction ft=getFragmentManager().beginTransaction();
					CallFragment callFragment=new CallFragment();
					ft.replace(R.id.child_fragment, callFragment,NewsFatherFragment.TAG);
					//ft.addToBackStack(TAG);
					ft.commit();
					
					
				}

			}
		});
		
		mTitleBarView.getTitleLeft().performClick();
		
		mScan.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, ErcodeScanActivity.class);
				startActivity(intent);
				mPopupWindow.dismiss();
			}
		});

		mCamera.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, WaterCameraActivity.class);
				startActivity(intent);
				mPopupWindow.dismiss();

			}
		});
	}

}
