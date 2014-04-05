package org.gigahub.turbofilm.screens.season;

import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import org.gigahub.turbofilm.R;
import org.gigahub.turbofilm.client.Images;
import org.gigahub.turbofilm.client.model.BasicEpisode;

/**
* @author Pavel Savinov
* @version 05/04/14 09:10
*/
class BasicEpisodeArrayAdapter extends ArrayAdapter<BasicEpisode> {

	private SeasonActivity seasonActivity;
	private String seriesAlias;

	public BasicEpisodeArrayAdapter(SeasonActivity seasonActivity, String seriesAlias) {
		super(seasonActivity, 0);
		this.seasonActivity = seasonActivity;
		this.seriesAlias = seriesAlias;
	}

	@Override
	public View getView(int position, View v, ViewGroup parent) {

		BasicEpisode e = getItem(position);

		if (v == null) v = seasonActivity.getLayoutInflater().inflate(R.layout.season_episode, parent, false);

		ImageLoader.getInstance().displayImage(Images.episodePosterBig(seriesAlias, e.getPosterHash()), (ImageView) v.findViewById(R.id.frameImage));

		((TextView) v.findViewById(R.id.nameEnText)).setText(Html.fromHtml(e.getNameEn()));
		((TextView) v.findViewById(R.id.nameRuText)).setText(Html.fromHtml(e.getNameRu()));
		((TextView) v.findViewById(R.id.episodeText)).setText(String.valueOf(e.getEpisode()));

		return v;
	}
}
