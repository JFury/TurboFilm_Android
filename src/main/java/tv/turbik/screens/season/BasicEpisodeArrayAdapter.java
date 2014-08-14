package tv.turbik.screens.season;

import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import org.gigahub.turbofilm.R;
import tv.turbik.client.model.BasicEpisode;

/**
* @author Pavel Savinov
* @version 05/04/14 09:10
*/
class BasicEpisodeArrayAdapter extends ArrayAdapter<BasicEpisode> {

	private SeasonActivity seasonActivity;

	public BasicEpisodeArrayAdapter(SeasonActivity seasonActivity) {
		super(seasonActivity, 0);
		this.seasonActivity = seasonActivity;
	}

	@Override
	public View getView(int position, View v, ViewGroup parent) {

		BasicEpisode e = getItem(position);

		if (v == null) v = seasonActivity.getLayoutInflater().inflate(R.layout.season_episode, parent, false);

		ImageLoader.getInstance().displayImage(e.getSmallPosterUrl(), (ImageView) v.findViewById(R.id.frameImage));

		((TextView) v.findViewById(R.id.nameEnText)).setText(Html.fromHtml(e.getNameEn()));
		((TextView) v.findViewById(R.id.nameRuText)).setText(Html.fromHtml(e.getNameRu()));
		((TextView) v.findViewById(R.id.episodeText)).setText(String.valueOf(e.getEpisode()));

		return v;
	}
}
