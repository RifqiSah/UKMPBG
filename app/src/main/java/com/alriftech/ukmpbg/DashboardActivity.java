package com.alriftech.ukmpbg;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.pes.androidmaterialcolorpickerdialog.ColorPicker;
import com.pes.androidmaterialcolorpickerdialog.ColorPickerCallback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class DashboardActivity extends AppCompatActivity {
    Core core;

    SharedPreferences sp;
    android.support.v7.app.AlertDialog alertDialog;

    private DrawerLayout rLay;

    private View navHeader;
    private NavigationView navView;
    private ActionBarDrawerToggle dt;

    private boolean init_user = true;
    private boolean doubleBackToExitPressedOnce = false;

    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        core = new Core(this);
        sp = getSharedPreferences("UKMPBG", MODE_PRIVATE);

        rLay = findViewById(R.id.layout_dashboard);
        dt = new ActionBarDrawerToggle(this, rLay, R.string.openDrawer, R.string.closeDrawer);

        rLay.addDrawerListener(dt);
        dt.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        displaySelectedFragment(new DashboardFragment());

        navView = findViewById(R.id.navigation_view);
        navHeader = navView.getHeaderView(0);

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                rLay.closeDrawers();

                switch (menuItem.getItemId()){
                    case R.id.mnu_nav_beranda:
                        displaySelectedFragment(new DashboardFragment());
                        return true;

                    case R.id.mnu_nav_logout:
                        new AlertDialog.Builder(DashboardActivity.this, R.style.Sikbk_Dialog)
                                .setTitle(R.string.t_konfirmasi)
                                .setMessage(R.string.b_konfirmasi_keluar)
                                .setPositiveButton(R.string.btn_yes, new DialogInterface.OnClickListener(){
                                    @Override
                                    public void onClick(DialogInterface dialog, int button) {
                                        signOut();
                                    }
                                })
                                .setNegativeButton(R.string.btn_no, null).show();

                        return true;

                    default:
                        Toast.makeText(getApplicationContext(),"Kesalahan Terjadi ",Toast.LENGTH_SHORT).show();
                        return true;
                }
            }
        });

        // [Google Signin]
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        sp.edit().clear().commit();

                        Toast.makeText(DashboardActivity.this, "Anda berhasil keluar dari sesi.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(DashboardActivity.this, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        finish();
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // getUserDetail(sp.getInt("id_user", 0));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void displaySelectedFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_dashboard, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (rLay.isDrawerOpen(GravityCompat.START)) {
            rLay.closeDrawer(GravityCompat.START);
            return;
        }

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, R.string.b_exitapp, Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(dt.onOptionsItemSelected(item))
            return true;

        return true;
    }
}
