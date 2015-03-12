package com.whyp.ycuservice.view;

import com.whyp.ycuservice.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

public class WaterView extends RelativeLayout {

	public WaterView(Context context) {
		super(context);
	}
	
	public WaterView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void initView(int type){
		LayoutInflater inflater=LayoutInflater.from(getContext());
		switch (type) {
		case 0:
			inflater.inflate(R.layout.water_camera_page1, this);
			break;
		case 1:
			inflater.inflate(R.layout.water_camera_page2, this);
			break;
		case 2:
			inflater.inflate(R.layout.water_camera_page3, this);
			break;
		case 3:
			inflater.inflate(R.layout.water_camera_page4, this);
			break;
		case 4:
			inflater.inflate(R.layout.water_camera_page5, this);
			break;

		default:
			break;
		}
	}
}
