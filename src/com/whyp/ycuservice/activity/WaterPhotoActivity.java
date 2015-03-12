package com.whyp.ycuservice.activity;

import com.whyp.ycuservice.R;
import com.whyp.ycuservice.util.SystemMethod;
import com.whyp.ycuservice.view.WaterView;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class WaterPhotoActivity extends Activity {
	private static final String TAG = "WaterPhotoActivity";
	private Context mContext;
	private ImageView mImageView;
	private TextView mTiaoZheng;
	private WaterView mWaterView;
	private RelativeLayout mWaterPhoto;
	private TextView mSure;
	private String path = "";
	private int type;
	private int scroll = 0;

	private static int left;
	private static int right;
	private static int top;
	private static int buttom;

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			int[] location = new int[2];
			mWaterView.getLocationInWindow(location);
			// Log.i(TAG, location[0]+"---"+location[1]);

			left = mWaterView.getLeft();
			right = mWaterView.getRight();
			top = mWaterView.getTop();
			buttom = mWaterView.getBottom();

			// Log.i(TAG,
			// "left:"+left+"right:"+right+"top:"+top+"buttom:"+buttom);
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_news_waterphoto_save);
		mContext = this;
		findView();
		init();
	}

	private void findView() {
		mImageView = (ImageView) findViewById(R.id.water_photo);
		mTiaoZheng = (TextView) findViewById(R.id.tiaozheng);
		mWaterView = (WaterView) findViewById(R.id.waterView);
		mSure = (TextView) findViewById(R.id.sure);
		mWaterPhoto=(RelativeLayout) findViewById(R.id.rl_water);
	}

	private void init() {
		path = getIntent().getExtras().getString("path");
		type = getIntent().getExtras().getInt("waterType");
		mWaterView.initView(type);

		final Bitmap bitmap = SystemMethod.getdecodeBitmap(path);
		scrollImageView(bitmap);

		mTiaoZheng.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (bitmap != null) {
					scrollImageView(bitmap);
				}
			}
		});

		mWaterView.setOnTouchListener(new MyOntaouchListener());

		mSure.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bitmap bitmap = getScreenShot(mWaterPhoto);
				int result = SystemMethod.saveBitmap(path, bitmap);
				if (result == 1) {
					Toast.makeText(mContext, "保存成功", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	private void scrollImageView(Bitmap bitmap) {
		scroll += 90;
		if (scroll >= 360) {
			scroll = 0;
		}
		Matrix matrix = new Matrix();
		matrix.preRotate(scroll);
		bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
				bitmap.getHeight(), matrix, true);
		mImageView.setImageBitmap(bitmap);
	}

	private class MyOntaouchListener implements OnTouchListener {
		int startX;
		int startY;
		int endX;
		int endY;
		int distanceX;
		int distanceY;

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (v.getId()) {
			case R.id.waterView:
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					startX = (int) event.getX();
					startY = (int) event.getY();

					Log.i(TAG, "startX:" + startX + "startY:" + startY);
					break;
				case MotionEvent.ACTION_MOVE:
					int x = (int) event.getX();
					int y = (int) event.getY();

					Log.i(TAG, "X:" + x + "Y:" + y);

					distanceX = x - startX;
					distanceY = y - startY;
					Log.i(TAG, "distanceX:" + distanceX + "distanceY:"
							+ distanceY);
					if (Math.abs(distanceX) > 10 || Math.abs(distanceY) > 10) {
						mWaterView.layout(left + distanceX, top + distanceY,
								right + distanceX, buttom + distanceY);
						left += distanceX;
						right += distanceX;
						top += distanceY;
						buttom += distanceY;
					}
					break;
				case MotionEvent.ACTION_UP:
					endX = (int) event.getX();
					endY = (int) event.getY();

					Log.i(TAG, "endX:" + endX + "endY:" + endY);
					distanceX = endX - startX;
					distanceY = endY - endY;

					left += distanceX;
					right += distanceX;
					top += distanceY;
					buttom += distanceY;
					break;
				}
				break;

			default:
				break;
			}
			return true;
		}

	}

	@Override
	protected void onResume() {
		super.onResume();
		handler.sendEmptyMessageDelayed(0, 1000);
	}

	// 获取指定Activity的截屏，保存到png文件
	private Bitmap getScreenShot(RelativeLayout waterPhoto) {
		// View是你需要截图的View
		View view = waterPhoto;
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap b1 = view.getDrawingCache();

		/*// 获取状态栏高度
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;
		System.out.println(statusBarHeight);*/

		/*// 获取屏幕长和高
		int width = activity.getWindowManager().getDefaultDisplay().getWidth();
		int height = activity.getWindowManager().getDefaultDisplay()
				.getHeight();*/
		
		//获取长和高
		int width=view.getWidth();
		int height=view.getHeight();

		// 去掉标题栏
		// Bitmap b = Bitmap.createBitmap(b1, 0, 25, 320, 455);
		Bitmap b = Bitmap.createBitmap(b1, 0, 0, width, height);
		view.destroyDrawingCache();
		return b;
	}

}
