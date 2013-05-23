package com.sks.demo.filter.listview;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

public class TestFilterListViewActivity extends Activity {
	FrameLayout historyContainer;
	ViewStub viewStub;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.history_container);
		historyContainer = (FrameLayout) findViewById(R.id.historyContainerLayout);
		EditText filterEditText = (EditText) findViewById(R.id.filter_text);
		filterEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

				historyContainer.removeAllViews();
				final List<String> tempHistoryList = new ArrayList<String>();
				tempHistoryList.addAll(historyList);
				for (String data : historyList) {
					if (data.indexOf((s.toString())) == -1) {
						tempHistoryList.remove(data);
					}
				}
				viewStub = new ViewStub(TestFilterListViewActivity.this, R.layout.history_schedule);
				viewStub.setOnInflateListener(new ViewStub.OnInflateListener() {
					public void onInflate(ViewStub stub, View inflated) {

						setUIElements(inflated, tempHistoryList);
					}
				});
				historyContainer.addView(viewStub);
				viewStub.inflate();

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

		setViewStub();
	}

	private void setViewStub() {
		historyList.add("first");
		historyList.add("second");
		historyList.add("third");
		historyList.add("fourth");
		historyList.add("fifth");
		historyList.add("sixth");
		historyList.add("seventh");

		viewStub = new ViewStub(TestFilterListViewActivity.this, R.layout.history_schedule);
		viewStub.setOnInflateListener(new ViewStub.OnInflateListener() {
			public void onInflate(ViewStub stub, View inflated) {

				setUIElements(inflated, historyList);
			}
		});
		historyContainer.addView(viewStub);
		viewStub.inflate();
	}

	final List<String> historyList = new ArrayList<String>();
	String displayName = "";
	ListView historyListView;

	private void setUIElements(View v, List<String> historyLists) {

		if (v != null) {
			historyScheduleData.clear();
			// historyList.clear();

			historyScheduleData.addAll(historyLists);
			historyListView = (ListView) findViewById(R.id.historylist);
			historyListView.setAdapter(new BeatListAdapter(this));

			registerForContextMenu(historyListView);

		}
	}

	private static class BeatListAdapter extends BaseAdapter {
		private LayoutInflater mInflater;

		public BeatListAdapter(Context context) {
			mInflater = LayoutInflater.from(context);

		}

		public int getCount() {
			return historyScheduleData.size();
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.history_list_view, null);
				holder = new ViewHolder();
				holder.historyData = (TextView) convertView.findViewById(R.id.historytext);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.historyData.setText(historyScheduleData.get(position));

			return convertView;
		}

		static class ViewHolder {

			TextView historyData;
		}
	}

	private static final List<String> historyScheduleData = new ArrayList<String>();

}