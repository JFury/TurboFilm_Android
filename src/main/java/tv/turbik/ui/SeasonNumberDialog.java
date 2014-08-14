package tv.turbik.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import org.gigahub.turbofilm.R;

/**
 * @author Pavel Savinov
 * @version 16/01/14 13:59
 */
public class SeasonNumberDialog {

	public interface SeasonListener {

		public void seasonSelected(int season);

	}

	private final AlertDialog dialog;

	public SeasonNumberDialog(Context context, final SeasonListener listener) {

		TableLayout table = new TableLayout(context);
		TableRow row = new TableRow(context);

		for (int i = 1; i <= 30; i++) {
			TextView textView = (TextView) LayoutInflater.from(context).inflate(R.layout.season_dialog_item, null);
			textView.setText(String.valueOf(i));

			final int finalI = i;
			textView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();
					if (listener != null) listener.seasonSelected(finalI);
				}
			});

			row.addView(textView);

			if (i % 5 == 0) {
				table.addView(row);
				row = new TableRow(context);
			}
		}

		table.addView(row);

		dialog = new AlertDialog.Builder(context)
				.setView(table)
				.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				})
				.create();

		dialog.show();

	}

}
