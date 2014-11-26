package tv.turbik.screens.main.series;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import tv.turbik.dao.Series;

/**
* @author Pavel Savinov
* @version 05/04/14 08:56
*/
class SeriesListAdapter extends RecyclerView.Adapter<SeriesViewHolder> {

	private List<Series> series = new ArrayList<>();

	@Override
	public SeriesViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
		return new SeriesViewHolder(viewGroup);
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
