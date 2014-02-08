package org.gigahub.turbofilm.screens.season;

import android.content.Context;
import android.content.Intent;
import org.gigahub.turbofilm.client.model.BasicSeries;

/**
 * @author Pavel Savinov [swapii@gmail.com]
 * @version 23.11.13 12:51
 */
public class SeasonIntent extends Intent {

	public static final String ID = "id";
	public static final String ALIAS = "alias";
	public static final String NAME_EN = "nameEn";
	public static final String NAME_RU = "nameRu";

	public SeasonIntent(Context context, BasicSeries series) {
		super(context, SeasonActivity.class);
		putExtra(ID, series.getId());
		putExtra(ALIAS, series.getAlias());
		putExtra(NAME_EN, series.getNameEn());
		putExtra(NAME_RU, series.getNameRu());
	}

}
