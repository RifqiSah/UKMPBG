package com.alriftech.ukmpbg;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {
    Core core;

    ProgressDialog dialog;
    SharedPreferences sp;

    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        core = new Core(this);

        sp = getSharedPreferences("UKMPBG", MODE_PRIVATE);
        if (sp.getBoolean("isLogin", false)) {
            gotoMain();
        }

        // [Google Signin]
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    @Override
    public void onStart() {
        super.onStart();

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("UkmpbgLog", "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    public void signIn(View v) {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void updateUI(@Nullable GoogleSignInAccount account) {
        if (account != null) {
            if (core.cekInternet(findViewById(R.id.layout_login))) {
                dialog = ProgressDialog.show(this, "", "Mencocokan informasi login", true);
                new getUserLoginGoogle().execute(account.getId());
            }
            // Log.d("UkmpbgLog", account.getPhotoUrl().toString());

//            Toast.makeText(this, account.getDisplayName(), Toast.LENGTH_SHORT).show();
        }
    }

    public void registerUser(View v) {
        // startActivity(new Intent(this, RegisterActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        // finish();

        gotoMain();
    }

    private void gotoMain() {
        sp.edit().putBoolean("isLogin", true).apply();

        startActivity(new Intent(getApplicationContext(), DashboardActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
    }


    private class getUserLoginGoogle extends AsyncTask<String,String,String>{

        JSONObject jobj = null;

        @Override
        protected String doInBackground(String... params) {
            JSONParser jsonparser = new JSONParser();

            String url = core.API("user_login_google/" + params[0]);
            jobj = jsonparser.makeHttpRequest(url);

            return jobj.toString();
        }

        protected void onPostExecute(String json){
            dialog.dismiss();

            try {
                JSONObject jobj = new JSONObject(json);

                if (jobj.getString("status").equals("success")) {
                    sp.edit().putInt("id_user", jobj.getInt("data")).apply();
                    gotoMain();
                } else {
                    Snackbar.make(findViewById(R.id.layout_login), jobj.getString("data"), Snackbar.LENGTH_LONG).show();
                    mGoogleSignInClient.signOut();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
