package com.motion.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.motion.sangeet.R;

public class SearchViewListItemAdapter extends BaseAdapter implements Filterable {

	private Context context;
	private ArrayList<String> songSearchItem;
	private List<String> filteredData = null;

	private ItemFilter mFilter = new ItemFilter();

	public SearchViewListItemAdapter() {
		super();
	}

	public SearchViewListItemAdapter(Context context, ArrayList<String> songSearchItem) {
		super();
		this.context = context;
		this.songSearchItem = songSearchItem;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return songSearchItem.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.search_view_list_layout, null);
		}
		TextView searchViewTextView = (TextView) convertView.findViewById(R.id.searchViewListTextView);
		searchViewTextView.setText(songSearchItem.get(position).toString());

		return convertView;
	}

	@Override
	public Filter getFilter() {
		// TODO Auto-generated method stub
		return mFilter;
	}

	private class ItemFilter extends Filter {
		@Override
		protected FilterResults performFiltering(CharSequence constraint) {

			String filterString = constraint.toString().toLowerCase();

			FilterResults results = new FilterResults();

			final List<String> list = songSearchItem;

			int count = list.size();
			final ArrayList<String> nlist = new ArrayList<String>(count);

			String filterableString;

			for (int i = 0; i < count; i++) {
				filterableString = list.get(i);
				if (filterableString.toLowerCase().contains(filterString)) {
					nlist.add(filterableString);
				}
			}

			results.values = nlist;
			results.count = nlist.size();

			return results;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence constraint, FilterResults results) {
			if (results.count == 0) {
				notifyDataSetInvalidated();
			} else {
				songSearchItem = (ArrayList<String>) results.values;
				notifyDataSetChanged();
			}
		}

	}

}
