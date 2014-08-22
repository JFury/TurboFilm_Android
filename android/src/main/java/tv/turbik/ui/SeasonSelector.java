package tv.turbik.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import tv.turbik.R;

/**
 * @author Pavel Savinov
 * @version 16/01/14 13:37
 */
public class SeasonSelector extends TextView {

	private SeasonNumberDialog.SeasonListener listener;

	private String seasonTextRes;
	private byte seasonCount = 1;

	public SeasonSelector(final Context context) {
		super(context);
		init();
	}

	public SeasonSelector(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public void setSeasonListener(SeasonNumberDialog.SeasonListener listener) {
		this.listener = listener;
	}

	public void setSeason(byte season) {
		setText(String.format(seasonTextRes, season));
	}

	public void setSeasonCount(byte seasonCount) {
		this.seasonCount = seasonCount;
	}

	private void init() {

		seasonTextRes = getResources().getString(R.string.season_text);

		setSeason((byte) 1);

		setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				new SeasonNumberDialog(getContext(), seasonCount, new SeasonNumberDialog.SeasonListener() {
					@Override
					public void seasonSelected(byte season) {
						setSeason(season);
						listener.seasonSelected(season);
					}
				}).show();

			}
		});
	}

}
