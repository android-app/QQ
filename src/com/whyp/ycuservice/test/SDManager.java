package com.whyp.ycuservice.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.whyp.ycuservice.util.FileUtil;

import android.content.Context;
import android.content.res.AssetManager;

public class SDManager {
	private Context mContext;
	private String[] names={"songhuiqiao.jpg","zhangzetian.jpg","songqian.jpg","hangxiaozhu.jpg","jingtian.jpg"
			,"liuyifei.jpg","kangyikun.jpg","dengziqi.jpg"};
	public SDManager(Context contex){
		this.mContext=contex;
	}
	
	public void moveUserIcon(){
		String path=FileUtil.getRecentChatPath();
		InputStream is=null;
		FileOutputStream out=null;
		for(int i=0;i<8;i++){
			try {
				is=mContext.getResources().getAssets().open(names[i]);
				out=new FileOutputStream(new File(path+names[i]));
				int len=0;
				byte[] buffer=new byte[1024];
				while((len=is.read(buffer))!=-1){
					out.write(buffer, 0, len);
					out.flush();
				}
				is.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
