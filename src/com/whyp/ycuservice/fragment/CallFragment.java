package com.whyp.ycuservice.fragment;

import com.whyp.ycuservice.AsyncTaskBase;
import com.whyp.ycuservice.R;
import com.whyp.ycuservice.activity.QQconstactActivity;
import com.whyp.ycuservice.adapter.CallAdapter;
import com.whyp.ycuservice.view.CustomListView;
import com.whyp.ycuservice.view.LoadingView;
import com.whyp.ycuservice.view.CustomListView.OnRefreshListener;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

public class CallFragment extends Fragment {
	private Context mContext;
	private View mBaseView;
	private CustomListView mCustomListView;
	private View mSearchView;
	private RelativeLayout rlView;
	private LoadingView mLoadingView;
	private CallAdapter mAdapter;
	private Button constact;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContext=getActivity();
		mBaseView=inflater.inflate(R.layout.fragment_news_call, null);
		mSearchView=inflater.inflate(R.layout.common_search_xl, null);
		findView();
		init();
		return mBaseView;
	}
	
	private void findView(){
		mCustomListView=(CustomListView) mBaseView.findViewById(R.id.callListView);
		mLoadingView=(LoadingView) mSearchView.findViewById(R.id.loading);
		rlView=(RelativeLayout) mSearchView.findViewById(R.id.rl_call);
		constact=(Button) mSearchView.findViewById(R.id.btn_constact);
	}

	private void init(){
		mAdapter=new CallAdapter(mContext);
		mCustomListView.setAdapter(mAdapter);
		mCustomListView.addHeaderView(mSearchView);
		mCustomListView.setCanLoadMore(false);
		mCustomListView.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				new CallAsyncTask(mLoadingView).execute(0);
			}
		});
		
		constact.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(mContext, QQconstactActivity.class);
				startActivity(intent);
				getActivity().overridePendingTransition(R.anim.activity_up, R.anim.fade_out);
				
			}
		});
		
	}
	
	private class CallAsyncTask extends AsyncTaskBase{
		public CallAsyncTask(LoadingView loadingView) {
			super(loadingView);
			rlView.setVisibility(View.GONE);
		}

		@Override
		protected Integer doInBackground(Integer... params) {
			int result=-1;
			try{
				//模拟查询数据
				Thread.sleep(2000);
				result=1;
			}catch(Exception e){
				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			if(result==1){
				mCustomListView.onRefreshComplete();
				mAdapter.notifyDataSetChanged();
			}
			
			rlView.setVisibility(View.VISIBLE);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
		
	}
}
