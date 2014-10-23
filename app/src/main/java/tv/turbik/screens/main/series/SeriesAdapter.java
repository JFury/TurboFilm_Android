package tv.turbik.screens.main.series;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import tv.turbik.R;
import tv.turbik.dao.Series;

/**
* @author Pavel Savinov
* @version 05/04/14 08:56
*/
class SeriesAdapter extends RecyclerView.Adapter<SeriesViewHolder> {

	private final LayoutInflater inflater;

	private List<Series> series = new ArrayList<Series>();

	public SeriesAdapter(Context context) {
		inflater = LayoutInflater.from(context);
	}

	@Override
	public SeriesViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
		return new SeriesViewHolder(inflater.inflate(R.layout.main_series_list_item, viewGroup, false));
	}

	@Override
	public void onBindViewHolder(SeriesViewHolder holder, int i) {
		Series seriesItem = series.get(i);
		holder.setSeries(seriesItem);
	}

	@Override
	public int getItemCount() {
		return series.size();
	}

	public void addSeries(List<Series> seriesList) {
		this.series.clear();
		this.series.addAll(seriesList);
		notifyDataSetChanged();
	}

}
