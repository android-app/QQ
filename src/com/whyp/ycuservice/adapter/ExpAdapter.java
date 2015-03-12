package com.whyp.ycuservice.adapter;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.whyp.ycuservice.R;
import com.whyp.ycuservice.bean.RecentChat;
import com.whyp.ycuservice.util.FileUtil;
import com.whyp.ycuservice.util.ImgUtil;
import com.whyp.ycuservice.util.SystemMethod;
import com.whyp.ycuservice.util.ImgUtil.OnLoadBitmapListener;
import com.whyp.ycuservice.view.IphoneTreeView;
import com.whyp.ycuservice.view.IphoneTreeView.IphoneTreeHeaderAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class ExpAdapter extends BaseExpandableListAdapter implements
		IphoneTreeHeaderAdapter {

	private static final String TAG = "ExpAdapter";
	private Context mContext;
	private HashMap<String, List<RecentChat>> maps;
	private IphoneTreeView mIphoneTreeView;
	private View mSearchView;
	private HashMap<String, SoftReference<Bitmap>> hashMaps = new HashMap<String, SoftReference<Bitmap>>();
	private String dir = FileUtil.getRecentChatPath();

	// 伪数据
	private HashMap<Integer, Integer> groupStatusMap;
	private String[] groups = { "我的好友", "家人", "S2S76班", "S2S73班", "S1S24班",
			"S1S5班", "亲戚" };
	private String[][] children = {
			{ "宋慧乔", "章泽天", "宋茜", "韩孝珠", "景甜", "刘亦菲", "康逸琨", "邓紫棋" },
			{ "宋慧乔", "章泽天", "宋茜", "韩孝珠", "景甜", "刘亦菲", "康逸琨", "邓紫棋" },
			{ "宋慧乔", "章泽天", "宋茜", "韩孝珠", "景甜", "刘亦菲", "康逸琨", "邓紫棋" },
			{ "宋慧乔", "章泽天", "宋茜", "韩孝珠", "景甜", "刘亦菲", "康逸琨", "邓紫棋" },
			{ "宋慧乔", "章泽天", "宋茜", "韩孝珠", "景甜", "刘亦菲", "康逸琨", "邓紫棋" },
			{ "宋慧乔", "章泽天", "宋茜", "韩孝珠", "景甜", "刘亦菲", "康逸琨", "邓紫棋" },
			{ "宋慧乔", "章泽天", "宋茜", "韩孝珠", "景甜", "刘亦菲", "康逸琨", "邓紫棋" } };
	private String[][] childPath = {
			{ dir + "songhuiqiao.jpg", dir + "zhangzetian.jpg",
					dir + "songqian.jpg", dir + "hangxiaozhu.jpg",
					dir + "jingtian.jpg", dir + "liuyifei.jpg",
					dir + "kangyikun.jpg", dir + "dengziqi.jpg" },
			{ dir + "songhuiqiao.jpg", dir + "zhangzetian.jpg",
					dir + "songqian.jpg", dir + "hangxiaozhu.jpg",
					dir + "jingtian.jpg", dir + "liuyifei.jpg",
					dir + "kangyikun.jpg", dir + "dengziqi.jpg" },
			{ dir + "songhuiqiao.jpg", dir + "zhangzetian.jpg",
					dir + "songqian.jpg", dir + "hangxiaozhu.jpg",
					dir + "jingtian.jpg", dir + "liuyifei.jpg",
					dir + "kangyikun.jpg", dir + "dengziqi.jpg" },
			{ dir + "songhuiqiao.jpg", dir + "zhangzetian.jpg",
					dir + "songqian.jpg", dir + "hangxiaozhu.jpg",
					dir + "jingtian.jpg", dir + "liuyifei.jpg",
					dir + "kangyikun.jpg", dir + "dengziqi.jpg" },
			{ dir + "songhuiqiao.jpg", dir + "zhangzetian.jpg",
					dir + "songqian.jpg", dir + "hangxiaozhu.jpg",
					dir + "jingtian.jpg", dir + "liuyifei.jpg",
					dir + "kangyikun.jpg", dir + "dengziqi.jpg" },
			{ dir + "songhuiqiao.jpg", dir + "zhangzetian.jpg",
					dir + "songqian.jpg", dir + "hangxiaozhu.jpg",
					dir + "jingtian.jpg", dir + "liuyifei.jpg",
					dir + "kangyikun.jpg", dir + "dengziqi.jpg" },
			{ dir + "songhuiqiao.jpg", dir + "zhangzetian.jpg",
					dir + "songqian.jpg", dir + "hangxiaozhu.jpg",
					dir + "jingtian.jpg", dir + "liuyifei.jpg",
					dir + "kangyikun.jpg", dir + "dengziqi.jpg" }, };

	public ExpAdapter(Context context, HashMap<String, List<RecentChat>> maps,
			IphoneTreeView mIphoneTreeView, View searchView) {
		this.mContext = context;
		this.maps = maps;
		this.mIphoneTreeView = mIphoneTreeView;
		groupStatusMap = new HashMap<Integer, Integer>();
		dir = FileUtil.getRecentChatPath();
		mSearchView = searchView;
	}

	public Object getChild(int groupPosition, int childPosition) {
		return children[groupPosition][childPosition];
	}

	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	public int getChildrenCount(int groupPosition) {
		return children[groupPosition].length;
	}

	public Object getGroup(int groupPosition) {
		return groups[groupPosition];
	}

	public int getGroupCount() {
		return groups.length;
	}

	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	public boolean hasStableIds() {
		return true;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		GroupHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.fragment_constact_child, null);
			holder = new GroupHolder();
			holder.nameView = (TextView) convertView
					.findViewById(R.id.contact_list_item_name);
			holder.feelView = (TextView) convertView
					.findViewById(R.id.cpntact_list_item_state);
			holder.iconView = (ImageView) convertView.findViewById(R.id.icon);
			convertView.setTag(holder);
		} else {
			holder = (GroupHolder) convertView.getTag();
		}

		String path = childPath[groupPosition][childPosition];
		if (hashMaps.containsKey(path)) {
			holder.iconView.setImageBitmap(hashMaps.get(path).get());
			// 另一个地方缓存释放资源
			ImgUtil.getInstance().reomoveCache(path);
		} else {
			holder.iconView.setTag(path);
			ImgUtil.getInstance().loadBitmap(path, new OnLoadBitmapListener() {
				@Override
				public void loadImage(Bitmap bitmap, String path) {
					ImageView iv = (ImageView) mIphoneTreeView
							.findViewWithTag(path);
					if (bitmap != null && iv != null) {
						bitmap = SystemMethod.toRoundCorner(bitmap, 15);
						iv.setImageBitmap(bitmap);

						if (!hashMaps.containsKey(path)) {
							hashMaps.put(path,
									new SoftReference<Bitmap>(bitmap));
						}
					}
				}
			});

		}
		holder.nameView.setText(getChild(groupPosition, childPosition)
				.toString());
		holder.feelView.setText("爱生活...爱Android...");
		return convertView;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		ChildHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.fragment_constact_group, null);
			holder = new ChildHolder();
			holder.nameView = (TextView) convertView
					.findViewById(R.id.group_name);
			holder.onLineView = (TextView) convertView
					.findViewById(R.id.online_count);
			holder.iconView = (ImageView) convertView
					.findViewById(R.id.group_indicator);
			convertView.setTag(holder);
		} else {
			holder = (ChildHolder) convertView.getTag();
		}
		holder.nameView.setText(groups[groupPosition]);
		holder.onLineView.setText(getChildrenCount(groupPosition) + "/"
				+ getChildrenCount(groupPosition));
		if (isExpanded) {
			holder.iconView.setImageResource(R.drawable.qb_down);
		} else {
			holder.iconView.setImageResource(R.drawable.qb_right);
		}
		return convertView;
	}

	@Override
	public int getTreeHeaderState(int groupPosition, int childPosition) {
		final int childCount = getChildrenCount(groupPosition);
		if (childPosition == childCount - 1) {
			//mSearchView.setVisibility(View.GONE);
			return PINNED_HEADER_PUSHED_UP;
		} else if (childPosition == -1
				&& !mIphoneTreeView.isGroupExpanded(groupPosition)) {
			//mSearchView.setVisibility(View.VISIBLE);
			return PINNED_HEADER_GONE;
		} else {
			//mSearchView.setVisibility(View.GONE);
			return PINNED_HEADER_VISIBLE;
		}
	}

	@Override
	public void configureTreeHeader(View header, int groupPosition,
			int childPosition, int alpha) {
		((TextView) header.findViewById(R.id.group_name))
				.setText(groups[groupPosition]);
		((TextView) header.findViewById(R.id.online_count))
				.setText(getChildrenCount(groupPosition) + "/"
						+ getChildrenCount(groupPosition));
	}

	@Override
	public void onHeadViewClick(int groupPosition, int status) {
		groupStatusMap.put(groupPosition, status);
	}

	@Override
	public int getHeadViewClickStatus(int groupPosition) {
		if (groupStatusMap.containsKey(groupPosition)) {
			return groupStatusMap.get(groupPosition);
		} else {
			return 0;
		}
	}

	class GroupHolder {
		TextView nameView;
		TextView feelView;
		ImageView iconView;
	}

	class ChildHolder {
		TextView nameView;
		TextView onLineView;
		ImageView iconView;
	}

}
