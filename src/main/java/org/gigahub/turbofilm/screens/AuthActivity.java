package org.gigahub.turbofilm.screens;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import org.gigahub.turbofilm.R;
import org.gigahub.turbofilm.Settings;
import org.gigahub.turbofilm.client.NotLoggedInException;
import org.gigahub.turbofilm.client.TurboFilmClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

import javax.inject.Inject;
import java.io.IOException;

/**
 * @author Pavel Savinov aka swap_i
 * @since 1.0.0
 */
@ContentView(R.layout.auth)
public class AuthActivity extends RoboActivity {

	private static final Logger L = LoggerFactory.getLogger(AuthActivity.class.getSimpleName());

	@InjectView(R.id.loginField) EditText loginField;
	@InjectView(R.id.passwordField) EditText passField;
	@InjectView(R.id.signinButton) Button signButton;

	@Inject TurboFilmClient client;
	@Inject Settings settings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		L.debug("Auth activity create");

		signButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				signin();
			}
		});
	}

	private void signin() {
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {

				try {
					client.singin(loginField.getText().toString(), passField.getText().toString());

				} catch (NotLoggedInException e) {

					e.printStackTrace();

					cancel(true);

				} catch (IOException e) {

					e.printStackTrace();

					cancel(true);

				}

				return null;
			}

			@Override
			protected void onPostExecute(Void aVoid) {

				settings.saveIdCookie(client.getCookie());

				setResult(RESULT_OK);
				AuthActivity.this.finish();
			}

		}.execute();
	}

	@Override
	protected void onResume() {
		super.onResume();
		L.trace("Auth resume");
	}

}
