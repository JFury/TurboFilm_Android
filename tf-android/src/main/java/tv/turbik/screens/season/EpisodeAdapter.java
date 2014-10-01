package tv.turbik.screens.season;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import tv.turbik.R;
import tv.turbik.dao.Episode;

/**
* @author Pavel Savinov
* @version 05/04/14 09:10
*/
class EpisodeAdapter extends ArrayAdapter<Episode> {

	private final LayoutInflater inflater;

	public EpisodeAdapter(Context context) {
		super(context, 0);
		inflater = LayoutInflater.from(context);
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {

		ViewHolder holder;
		if (view == null) {
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.season_episode, parent, false);
			view.setTag(holder);
			holder.poster = (ImageView) view.findViewById(R.id.frameImage);
			holder.nameEn = (TextView) view.findViewById(R.id.nameEnText);
			holder.nameRu = (TextView) view.findViewById(R.id.nameRuText);
			holder.episodeText = (TextView) view.findViewById(R.id.episodeText);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		Episode episode = getItem(position);

		ImageLoader.getInstance().displayImage(episode.getSmallPosterUrl(), holder.poster);

		holder.nameEn.setText(Html.fromHtml(episode.getNameEn()));
		holder.nameRu.setText(Html.fromHtml(episode.getNameRu()));
		holder.episodeText.setText(String.valueOf(episode.getEpisode()));

		return view;
	}

	class ViewHolder {
		ImageView poster;
		TextView nameEn;
		TextView nameRu;
		TextView episodeText;
	}

}
