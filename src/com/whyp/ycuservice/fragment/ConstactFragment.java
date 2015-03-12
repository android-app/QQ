package com.whyp.ycuservice.fragment;

import java.util.HashMap;
import java.util.List;

import com.whyp.ycuservice.AsyncTaskBase;
import com.whyp.ycuservice.R;
import com.whyp.ycuservice.activity.QQconstactActivity;
import com.whyp.ycuservice.adapter.ExpAdapter;
import com.whyp.ycuservice.bean.RecentChat;
import com.whyp.ycuservice.test.TestData;
import com.whyp.ycuservice.view.IphoneTreeView;
import com.whyp.ycuservice.view.LoadingView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class ConstactFragment extends Fragment {
	private Context mContext;
	private View mBaseView;
	private LoadingView mLoadingView;
	private IphoneTreeView mIphoneTreeView;
	private View mSearchView;
	private ExpAdapter mExpAdapter;
	private RelativeLayout constacts;
	private HashMap<String, List<RecentChat>> maps = new HashMap<String, List<RecentChat>>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContext = getActivity();
		mBaseView = inflater.inflate(R.layout.fragment_constact, null);
		findView();
		init();
		return mBaseView;
	}

	private void findView() {
		mSearchView=mBaseView.findViewById(R.id.search);
		mLoadingView = (LoadingView) mBaseView.findViewById(R.id.loadingView);
		mIphoneTreeView = (IphoneTreeView) mBaseView.findViewById(R.id.iphone_tree_view);
		constacts=(RelativeLayout) mBaseView.findViewById(R.id.rl_tonxunru);
	}

	private void init() {
		mIphoneTreeView.setHeaderView(LayoutInflater.from(mContext).inflate(
				R.layout.fragment_constact_head_view, mIphoneTreeView, false));
		mIphoneTreeView.setGroupIndicator(null);
		mExpAdapter = new ExpAdapter(mContext, maps, mIphoneTreeView,mSearchView);
		mIphoneTreeView.setAdapter(mExpAdapter);
		
		constacts.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent  intent=new Intent(mContext, QQconstactActivity.class);
				startActivity(intent);
				getActivity().overridePendingTransition(R.anim.activity_up, R.anim.fade_out);
				
			}
		});
		
		new AsyncTaskLoading(mLoadingView).execute(0);
	}

	private class AsyncTaskLoading extends AsyncTaskBase {
		public AsyncTaskLoading(LoadingView loadingView) {
			super(loadingView);
		}

		@Override
		protected Integer doInBackground(Integer... params) {
			int result = -1;
			maps.put("我的同学", TestData.getRecentChats());
			maps.put("我的朋友", TestData.getRecentChats());
			maps.put("家人", TestData.getRecentChats());
			result = 1;
			return result;
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			
			
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

	}
}
