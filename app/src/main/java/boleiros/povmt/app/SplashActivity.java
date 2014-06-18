package boleiros.povmt.app;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;

/**
 * Created by Walter on 18/06/2014.
 */
public class SplashActivity extends Activity {
    Context mContext = SplashActivity.this;
    AccountManager mAccountManager;
    String token;
    int serverCode;

    private static final String SCOPE = "oauth2:https://www.googleapis.com/auth/userinfo.profile";




}
