package com.whyp.ycuservice.adapter;

import com.whyp.ycuservice.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class CallAdapter extends BaseAdapter {

	private Context mContext;
	public CallAdapter(Context context){
		this.mContext=context;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView=View.inflate(mContext, R.layout.fragment_news_item, null);
		return convertView;
	}

}
