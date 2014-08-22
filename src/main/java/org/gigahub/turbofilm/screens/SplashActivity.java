package org.gigahub.turbofilm.screens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import org.gigahub.turbofilm.screens.home.HomeActivity;

/**
 * @author Pavel Savinov aka swap_i
 * @since 1.0.0
 */
public class SplashActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		startActivity(new Intent(this, HomeActivity.class));
	}

}
