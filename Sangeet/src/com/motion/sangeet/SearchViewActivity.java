package com.motion.sangeet;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.motion.actionbar.BaseActivity;
import com.motion.adapter.SearchViewListItemAdapter;

public class SearchViewActivity extends BaseActivity {
	private ImageView searchImageView;
	private EditText editTextSearch;
	private ImageView imageViewSearchTitle;
	private ListView listSearchView;
	private ArrayList<String> songSearchItem;
	private SearchViewListItemAdapter searchViewListItemAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getLayoutInflater().inflate(R.layout.search_view_layout, contenFrameLayout);
		initView();

		setAdapterToListView();

	}

	private void setAdapterToListView() {
		// TODO Auto-generated method stub
		searchViewListItemAdapter = new SearchViewListItemAdapter(this, songSearchItem);
		listSearchView.setAdapter(searchViewListItemAdapter);
		listSearchView.setTextFilterEnabled(true);

		editTextSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub

				System.out.println("Text [" + s + "]");
				if (searchViewListItemAdapter != null)
					searchViewListItemAdapter.getFilter().filter(s.toString());

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (editTextSearch.getText().toString().length() == 0) {
					searchViewListItemAdapter = new SearchViewListItemAdapter(getApplicationContext(), songSearchItem);
					listSearchView.setAdapter(searchViewListItemAdapter);
					
				}
			}
		});

	}

	private void initView() {
		// TODO Auto-generated method stub

		listSearchView = (ListView) findViewById(R.id.listSearchView);
		songSearchItem = new ArrayList<String>();
		songSearchItem.add("sad");
		songSearchItem.add("love");
		songSearchItem.add("action");
		songSearchItem.add("hotfilling");
		songSearchItem.add("happy");
		songSearchItem.add("telugu");
		songSearchItem.add("tamil");
		songSearchItem.add("kanada");
		songSearchItem.add("malayalam");
		songSearchItem.add("bhojpuri");
		songSearchItem.add("qqqq");
		songSearchItem.add("gggg");
		songSearchItem.add("jjjj");
		songSearchItem.add("oooo");
		songSearchItem.add("ttttt");
		songSearchItem.add("jjjjj");
		songSearchItem.add("kkkk");
		songSearchItem.add("eeeee");
		songSearchItem.add("vvvv");
		songSearchItem.add("zzzzz");
		songSearchItem.add("ssss");
		songSearchItem.add("dddd");
		songSearchItem.add("bbbb");
		songSearchItem.add("mmmmm");
		songSearchItem.add("nnnn");
		songSearchItem.add("rrrrr");
		songSearchItem.add("nnnn");
		songSearchItem.add("xxxx");
		songSearchItem.add("cccc");

		ActionBar actionBar = getSupportActionBar();
		searchImageView = (ImageView) actionBar.getCustomView().findViewById(R.id.searchImageView);
		editTextSearch = (EditText) actionBar.getCustomView().findViewById(R.id.editTextSearch);
		imageViewSearchTitle = (ImageView) actionBar.getCustomView().findViewById(R.id.imageViewSearchTitle);
		searchImageView.setVisibility(View.GONE);
		imageViewSearchTitle.setVisibility(View.GONE);
		editTextSearch.setVisibility(View.VISIBLE);
	}

}
