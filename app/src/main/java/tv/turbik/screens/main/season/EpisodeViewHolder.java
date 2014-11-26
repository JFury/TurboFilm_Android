package tv.turbik.screens.main.season;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import tv.turbik.R;
import tv.turbik.beans.EventBus_;
import tv.turbik.dao.Episode;
import tv.turbik.events.EpisodeSelectedEvent;
import tv.turbik.ui.PosterTarget;

/**
 * Created by pavel on 27/11/14.
 */
public class EpisodeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

	private final Context context;
	private final TextView nameEn;
	private final TextView nameRu;
	private final TextView episodeText;
	private final PosterTarget picassoTarget;

	private Episode episode;

	public EpisodeViewHolder(ViewGroup parentView) {
		super(LayoutInflater.from(parentView.getContext()).inflate(R.layout.episode_card, parentView, false));

		context = itemView.getContext();

		int defaultColor = context.getResources().getColor(R.color.blue);
		int defaultBackColor = context.getResources().getColor(R.color.back_gray);

		nameEn = (TextView) itemView.findViewById(R.id.name_en_text);
		nameRu = (TextView) itemView.findViewById(R.id.name_ru_text);
		episodeText = (TextView) itemView.findViewById(R.id.episode_number);

		View container = itemView.findViewById(R.id.container);
		ImageView poster = (ImageView) itemView.findViewById(R.id.poster);

		picassoTarget = new PosterTarget(container, poster, nameEn, defaultColor, defaultBackColor);
	}

	public void setEpisode(Episode episode) {

		this.episode = episode;

		nameEn.setText(Html.fromHtml(episode.getNameEn()));
		nameRu.setText(Html.fromHtml(episode.getNameRu()));
		episodeText.setText(String.valueOf(episode.getEpisode()));

		Picasso.with(context)
				.load(episode.getSmallPosterUrl())
				.into(picassoTarget);
	}

	@Override
	public void onClick(View v) {
		EventBus_.getInstance_(context).post(new EpisodeSelectedEvent(episode));
	}

}
