package boleiros.povmt.app;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONException;
import com.google.android.gms.auth.GoogleAuthUtil;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by Walter on 18/06/2014.
 */
public abstract class AbstractGetNameTask extends AsyncTask<Void, Void, Void> {

    private static final String TAG = "TokenInfoTask";
    protected SplashActivity mActivity;
    public static String GOOGLE_USER_DATA = "No_data";
    protected String mScope;
    protected String mEmail;
    protected int mRequestCode;

    AbstractGetNameTask(SplashActivity activity, String email, String scope) {

        this.mActivity = activity;
        this.mScope = scope;
        this.mEmail = email;
    }

    @Override
    protected Void doInBackground(Void... params) {

        try {
            fetchNameFromProfileServer();
        } catch (IOException ex) {
            onError("Following Error occured, please try again. " + ex.getMessage(), ex);
        } catch (JSONException e) {
            onError("Bad response: " + e.getMessage(), e);
        }
        return null;
    }

    protected void onError(String msg, Exception e) {
        if (e != null) {
            Log.e(TAG, "Exception: ", e);
        }
    }



    protected abstract String fetchToken() throws IOException;



    private void fetchNameFromProfileServer() throws IOException, JSONException {

        String token = fetchToken();

        URL url = new URL("https://www.googleapis.com/oauth2/v1/userinfo?access_token=" + token);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        int sc = con.getResponseCode();

        if (sc == 200) {
            InputStream is = con.getInputStream();
            GOOGLE_USER_DATA = readResponse(is);
            is.close();
            Intent intent = new Intent(mActivity, MainActivity.class);
            intent.putExtra("email_id", mEmail);
            mActivity.startActivity(intent);
            //mActivity.finish();
            return;
        } else if (sc == 401) {
            GoogleAuthUtil.invalidateToken(mActivity, token);
            onError("Server auth error, please try again.", null);
            //Toast.makeText(mActivity, "Please try again", Toast.LENGTH_SHORT).show();
            //mActivity.finish(); return;
        } else {
            onError("Server returned the following error code: " + sc, null); return;
        }
    }

    /** * Ler a rsposta a partir do input stream e retorna como uma string */
    private static String readResponse(InputStream is) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] data = new byte[2048];
        int len = 0;

        while ((len = is.read(data, 0, data.length)) >= 0) {
            bos.write(data, 0, len);
        }

        return new String(bos.toByteArray(), "UTF-8");
    }

}
