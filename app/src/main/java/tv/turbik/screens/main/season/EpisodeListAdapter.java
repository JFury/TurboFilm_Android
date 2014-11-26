package tv.turbik.screens.main.season;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.List;

import tv.turbik.dao.Episode;

/**
* @author Pavel Savinov
* @version 05/04/14 09:10
*/
class EpisodeListAdapter extends RecyclerView.Adapter<EpisodeViewHolder> {

	private final List<Episode> episodes = new LinkedList<>();

	@Override
	public EpisodeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return new EpisodeViewHolder(parent);
	}

	@Override
	public void onBindViewHolder(EpisodeViewHolder holder, int position) {
		holder.setEpisode(episodes.get(position));
	}

	@Override
	public int getItemCount() {
		return episodes.size();
	}

	public void clear() {
		episodes.clear();
	}

	public void addAll(List<Episode> episodeList) {
		episodes.addAll(episodeList);
	}

}
