package tv.turbik.screens;

import android.widget.Button;
import android.widget.EditText;
import com.actionbarsherlock.app.SherlockActivity;
import org.androidannotations.annotations.*;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.gigahub.turbofilm.R;
import org.gigahub.turbofilm.Settings_;
import tv.turbik.client.NotLoggedInException;
import tv.turbik.client.TurboFilmClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author Pavel Savinov aka swap_i
 * @since 1.0.0
 */
@EActivity(R.layout.auth)
public class AuthActivity extends SherlockActivity {

	private static final Logger L = LoggerFactory.getLogger(AuthActivity.class.getSimpleName());

	@ViewById EditText loginField;
	@ViewById EditText passwordField;
	@ViewById Button signinButton;

	@Bean TurboFilmClient client;
	@Pref Settings_ settings;

	@Click
	void signinButton() {
		signin();
	}

	@Background
	void signin() {

		try {
			client.singin(loginField.getText().toString(), passwordField.getText().toString());

			client.saveIdCookie(client.getCookie());

			setResult(RESULT_OK);
			finish();

		} catch (NotLoggedInException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

}
