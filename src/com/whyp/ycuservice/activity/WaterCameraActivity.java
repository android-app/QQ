package com.whyp.ycuservice.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.youmi.android.spot.SpotManager;

import com.whyp.ycuservice.R;
import com.whyp.ycuservice.util.FileUtil;
import com.whyp.ycuservice.util.SystemMethod;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.SurfaceHolder.Callback;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

public class WaterCameraActivity extends Activity {

	private Context mContext;
	private ImageView mBack;
	private SurfaceView mSurfaceView;
	private ImageView mCamera_sd;
	private ImageView mTackPic;
	private ViewPager mViewPager;
	
	private Camera camera;
	private Camera.Parameters parameters=null;
	private List<View> views=new ArrayList<View>();
	private int waterType=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_news_camera);
		mContext=this;
		
		findView();
		init();
	}
	
	private void findView(){
		mBack=(ImageView) findViewById(R.id.back);
		mSurfaceView=(SurfaceView) findViewById(R.id.surfaceView);
		mCamera_sd=(ImageView) findViewById(R.id.camera_sd);
		mTackPic=(ImageView) findViewById(R.id.tack_pic);
		mViewPager=(ViewPager) findViewById(R.id.viewPager);
	}
	
	private void init(){
		mSurfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		mSurfaceView.getHolder().setFixedSize(176, 144);//surfaceView 分辨率
		mSurfaceView.getHolder().setKeepScreenOn(true);
		mSurfaceView.getHolder().addCallback(new MySurfaceViewCallback());
		SpotManager.getInstance(this).loadSpotAds();
		SpotManager.getInstance(this).setSpotTimeout(3000);
		SpotManager.getInstance(this).showSpotAds(this);
		mTackPic.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(camera!=null){
					camera.takePicture(null, null, new MyPictureCallback());
				}
			}
		});
		
		
		//viewPager
		LayoutInflater inflater=LayoutInflater.from(mContext);
		views.add(inflater.inflate(R.layout.water_camera_page1, null));
		views.add(inflater.inflate(R.layout.water_camera_page2, null));
		views.add(inflater.inflate(R.layout.water_camera_page3, null));
		views.add(inflater.inflate(R.layout.water_camera_page4, null));
		views.add(inflater.inflate(R.layout.water_camera_page5, null));
		
		mViewPager.setAdapter(new MyViewPagerAdapter());
		mViewPager.setOnPageChangeListener(new MyOnPagerChangeListener());
		
	}
	
	private class MyPictureCallback implements PictureCallback{

		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			String photoName=System.currentTimeMillis()+".jpg";
			String path=FileUtil.getWaterPhotoPath()+photoName;
			int result=SystemMethod.saveBitmap(path, data);
			if(result==1){
				Intent intent=new Intent(mContext,WaterPhotoActivity.class);
				intent.putExtra("path", path);
				intent.putExtra("waterType", waterType);
				startActivity(intent);
				finish();
			}else{
				camera.startPreview();
			}
		}
		
	}
	
	private class MySurfaceViewCallback implements Callback{

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
			parameters=camera.getParameters();//设置相机参数
			parameters.setPictureFormat(PixelFormat.JPEG);
			parameters.setPreviewSize(width, height);//预览大小
			parameters.setPreviewFrameRate(5);//帧
			parameters.setPictureSize(width, height);//保存图片大小
			parameters.setJpegQuality(80);//图片质量
			
			
			
		}

		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			try {
				camera=Camera.open();
				camera.setPreviewDisplay(holder);
				camera.setDisplayOrientation(getPreviewDegree(WaterCameraActivity.this));
				camera.startPreview();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			
			if(camera!=null){
				camera.release();
				camera=null;
			}
		}
		
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(camera!=null){
			camera.release();
			camera=null;
		}
	}
	
    public static int getPreviewDegree(Activity activity) {  
        // 获得手机的方向  
        int rotation = activity.getWindowManager().getDefaultDisplay()  
                .getRotation();  
        int degree = 0;  
        // 根据手机的方向计算相机预览画面应该选择的角度  
        switch (rotation) {  
        case Surface.ROTATION_0:  
            degree = 90;  
            break;  
        case Surface.ROTATION_90:  
            degree = 0;  
            break;  
        case Surface.ROTATION_180:  
            degree = 270;  
            break;  
        case Surface.ROTATION_270:  
            degree = 180;  
            break;  
        }  
        return degree;  
    }  
    
    
    //////////////////////////////////////////////////////////////////////
    //viewPager
    private class MyViewPagerAdapter extends PagerAdapter{
		@Override
		public int getCount() {
			return views.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0==arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(views.get(position));
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(views.get(position));
			return views.get(position);
		}
    	
    }
    
    
    private class MyOnPagerChangeListener implements OnPageChangeListener{

		@Override
		public void onPageScrollStateChanged(int arg0) {
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

			
		}

		@Override
		public void onPageSelected(int arg0) {
			waterType=arg0;
		}
    	
    }

}
