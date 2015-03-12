package com.whyp.ycuservice.fragment;

import java.sql.NClob;

import com.whyp.ycuservice.R;
import com.whyp.ycuservice.view.TitleBarView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;

public class ConstactFatherFragment extends Fragment {

	private Context mContext;
	private View mBaseView;
	private TitleBarView mTitleBarView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContext=getActivity();
		mBaseView=inflater.inflate(R.layout.fragment_constact_father, null);
		findView();
		initTitleView();
		return mBaseView;
	}
	
	private void findView(){
		mTitleBarView=(TitleBarView) mBaseView.findViewById(R.id.title_bar);
	}
	
	private void initTitleView(){
		mTitleBarView.setCommonTitle(View.VISIBLE, View.GONE, View.VISIBLE,
				View.VISIBLE);
		mTitleBarView.setBtnLeft(R.string.control);
		mTitleBarView.setBtnRight(R.drawable.qq_constact);
		mTitleBarView.setBtnRightOnclickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});

		mTitleBarView.setTitleLeft(R.string.group);
		mTitleBarView.setTitleRight(R.string.all);

		mTitleBarView.getTitleLeft().setOnClickListener(new OnClickListener() {
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				if (mTitleBarView.getTitleLeft().isEnabled()) {
					mTitleBarView.getTitleLeft().setEnabled(false);
					mTitleBarView.getTitleRight().setEnabled(true);
					
					FragmentTransaction ft=getFragmentManager().beginTransaction();
					ConstactFragment constactFragment=new ConstactFragment();
					ft.replace(R.id.rl_content, constactFragment);
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
					QQfriendFragment qqfriendFragment=new QQfriendFragment();
					ft.replace(R.id.rl_content, qqfriendFragment);
					ft.commit();
				}

			}
		});
		
		mTitleBarView.getTitleLeft().performClick();
	}
	
	private void init(){
		
	}

}
