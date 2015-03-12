package com.whyp.ycuservice.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ConstactAdapter extends BaseAdapter {

	@Override
	public int getCount() {
		return 0;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return null;
	}

}
