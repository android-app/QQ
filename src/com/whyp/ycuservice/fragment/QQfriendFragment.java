package com.whyp.ycuservice.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.whyp.ycuservice.AsyncTaskBase;
import com.whyp.ycuservice.R;
import com.whyp.ycuservice.activity.QQconstactActivity;
import com.whyp.ycuservice.adapter.QQfriendAdapter;
import com.whyp.ycuservice.sort.CharacterParser;
import com.whyp.ycuservice.sort.ClearEditText;
import com.whyp.ycuservice.sort.PinyinComparator;
import com.whyp.ycuservice.sort.SideBar;
import com.whyp.ycuservice.sort.SortModel;
import com.whyp.ycuservice.sort.SideBar.OnTouchingLetterChangedListener;
import com.whyp.ycuservice.test.TestData;
import com.whyp.ycuservice.util.ConstactUtil;
import com.whyp.ycuservice.view.CustomListView;
import com.whyp.ycuservice.view.LoadingView;
import com.whyp.ycuservice.view.CustomListView.OnRefreshListener;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class QQfriendFragment extends Fragment {
	private Context mContext;
	private View mBaseView;
	private CustomListView mCustomListView;
	private LoadingView mLoadingView;
	private View mSearchView;
	private SideBar sideBar;
	private TextView dialog;
	private QQfriendAdapter adapter;
	private ClearEditText mClearEditText;
	private Map<String, String> callRecords;
	private RelativeLayout constacts;

	/**
	 * 汉字转换成拼音的类
	 */
	private CharacterParser characterParser;
	private List<SortModel> SourceDateList;

	/**
	 * 根据拼音来排列ListView里面的数据类
	 */
	private PinyinComparator pinyinComparator;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContext = getActivity();
		mBaseView = inflater.inflate(R.layout.fragment_qq_friend, null);
		mSearchView = inflater.inflate(R.layout.common_search_xxl, null);
		findView();
		init();
		return mBaseView;
	}

	private void findView() {
		mCustomListView = (CustomListView) mBaseView
				.findViewById(R.id.listview);
		mLoadingView = (LoadingView) mBaseView.findViewById(R.id.loading);
		sideBar = (SideBar) mBaseView.findViewById(R.id.sidrbar);
		dialog = (TextView) mBaseView.findViewById(R.id.dialog);
		mClearEditText = (ClearEditText) mSearchView
				.findViewById(R.id.filter_edit);
		constacts=(RelativeLayout) mSearchView.findViewById(R.id.rl_group);

	}

	private void init() {
		mCustomListView.setCanLoadMore(false);
		mCustomListView.addHeaderView(mSearchView);
		mCustomListView.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
			   new AsyncTaskQQConstact(mLoadingView).execute(0);
			}
		});
		
		constacts.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(mContext,QQconstactActivity.class);
				startActivity(intent);
				getActivity().overridePendingTransition(R.anim.activity_up, R.anim.fade_out);
				
			}
		});

		// 实例化汉字转拼音类
		characterParser = CharacterParser.getInstance();

		pinyinComparator = new PinyinComparator();

		sideBar.setTextView(dialog);

		// 设置右侧触摸监听
		sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {

			@SuppressLint("NewApi")
			@Override
			public void onTouchingLetterChanged(String s) {
				// 该字母首次出现的位置
				int position = adapter.getPositionForSection(s.charAt(0));
				if (position != -1) {
					mCustomListView.setSelection(position);
				}
			}
		});

		mCustomListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 这里要利用adapter.getItem(position)来获取当前position所对应的对象
				// Toast.makeText(getApplication(),
				// ((SortModel)adapter.getItem(position)).getName(),
				// Toast.LENGTH_SHORT).show();
				/*String number = callRecords.get(((SortModel) adapter
						.getItem(position)).getName());*/

			}
		});

		new AsyncTaskQQConstact(mLoadingView).execute(0);
	}

	private class AsyncTaskQQConstact extends AsyncTaskBase {
		public AsyncTaskQQConstact(LoadingView loadingView) {
			super(loadingView);
		}

		@Override
		protected Integer doInBackground(Integer... params) {
			int result = -1;
			callRecords = TestData.getFriends();
			result = 1;
			return result;
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			mCustomListView.onRefreshComplete();
			if (result == 1) {
				List<String> constact = new ArrayList<String>();
				for (Iterator<String> keys = callRecords.keySet().iterator(); keys
						.hasNext();) {
					String key = keys.next();
					constact.add(key);
				}
				String[] names = new String[] {};
				names = constact.toArray(names);
				SourceDateList = filledData(names);

				// 根据a-z进行排序源数据
				Collections.sort(SourceDateList, pinyinComparator);
				adapter = new QQfriendAdapter(mContext, SourceDateList,callRecords,mCustomListView);
				mCustomListView.setAdapter(adapter);

				mClearEditText = (ClearEditText) mBaseView
						.findViewById(R.id.filter_edit);

				// 根据输入框输入值的改变来过滤搜索
				mClearEditText.addTextChangedListener(new TextWatcher() {

					@Override
					public void onTextChanged(CharSequence s, int start,
							int before, int count) {
						// 当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
						filterData(s.toString());
					}

					@Override
					public void beforeTextChanged(CharSequence s, int start,
							int count, int after) {

					}

					@Override
					public void afterTextChanged(Editable s) {
					}
				});
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

	}

	/**
	 * 为ListView填充数据
	 * 
	 * @param date
	 * @return
	 */
	private List<SortModel> filledData(String[] date) {
		List<SortModel> mSortList = new ArrayList<SortModel>();

		for (int i = 0; i < date.length; i++) {
			SortModel sortModel = new SortModel();
			sortModel.setName(date[i]);
			// 汉字转换成拼音
			String pinyin = characterParser.getSelling(date[i]);
			String sortString = pinyin.substring(0, 1).toUpperCase();

			// 正则表达式，判断首字母是否是英文字母
			if (sortString.matches("[A-Z]")) {
				sortModel.setSortLetters(sortString.toUpperCase());
			} else {
				sortModel.setSortLetters("#");
			}

			mSortList.add(sortModel);
		}
		return mSortList;

	}

	/**
	 * 根据输入框中的值来过滤数据并更新ListView
	 * 
	 * @param filterStr
	 */
	private void filterData(String filterStr) {
		List<SortModel> filterDateList = new ArrayList<SortModel>();

		if (TextUtils.isEmpty(filterStr)) {
			filterDateList = SourceDateList;
		} else {
			filterDateList.clear();
			for (SortModel sortModel : SourceDateList) {
				String name = sortModel.getName();
				if (name.indexOf(filterStr.toString()) != -1
						|| characterParser.getSelling(name).startsWith(
								filterStr.toString())) {
					filterDateList.add(sortModel);
				}
			}
		}

		// 根据a-z进行排序
		Collections.sort(filterDateList, pinyinComparator);
		adapter.updateListView(filterDateList);
	}

}
