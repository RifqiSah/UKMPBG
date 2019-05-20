package com.alriftech.ukmpbg;

import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DetailUKMActivity extends AppCompatActivity {
    private String id_ukm;
    Core core;

    TextView txtNama, txtAlamat;
    RelativeLayout layProduk, layLowongan;

    private RecyclerView recyclerViewProduk, recyclerViewLowongan;
    private TextView txtNoProduk, txtNoLowongan;
    private ProdukAdapter produkAdapter;
    private LowonganAdapter lowonganAdapter;
    private ArrayList<Produk> produkArrayList;
    private ArrayList<Lowongan> lowonganArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_ukm);

        core = new Core(this);

        Bundle extra = getIntent().getExtras();
        id_ukm = String.valueOf(extra.getInt("id_ukm"));

        txtNama = findViewById(R.id.txt_des_nama);
        txtAlamat = findViewById(R.id.txt_des_alamat);

//      Start
        recyclerViewProduk = findViewById(R.id.lst_ukm_produk);
        txtNoProduk = findViewById(R.id.txt_no_produk);

        recyclerViewLowongan = findViewById(R.id.lst_ukm_lowongan);
        txtNoLowongan = findViewById(R.id.txt_no_lowongan);

        recyclerViewProduk.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewLowongan.setLayoutManager(new LinearLayoutManager(this));

        produkArrayList = new ArrayList<>();
        produkAdapter = new ProdukAdapter(this, produkArrayList);
        recyclerViewProduk.setAdapter(produkAdapter);

        lowonganArrayList = new ArrayList<>();
        lowonganAdapter = new LowonganAdapter(this, lowonganArrayList);
        recyclerViewLowongan.setAdapter(lowonganAdapter);

        layProduk   = findViewById(R.id.lay_block_produk);
        layLowongan = findViewById(R.id.lay_block_lowongan);

        initTab();

        new getUKMData().execute(id_ukm);
        new getProdukData().execute(id_ukm);
        new getLowonganData().execute(id_ukm);
    }

    private void initTab() {
        TabLayout tab = findViewById(R.id.tab_ukm);

        tab.setTabMode(TabLayout.MODE_FIXED);
        tab.setTabMode(TabLayout.MODE_SCROLLABLE);

        tab.addTab(tab.newTab().setText("Produk"));
        tab.addTab(tab.newTab().setText("Lowongan"));

        layProduk.setVisibility(View.VISIBLE);

        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                switch (pos) {
                    case 0:
                        layProduk.setVisibility(View.VISIBLE);
                        layLowongan.setVisibility(View.INVISIBLE);

                        break;

                    case 1:
                        layProduk.setVisibility(View.INVISIBLE);
                        layLowongan.setVisibility(View.VISIBLE);

                        break;

                    default:
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void cekCount() {
        if (produkAdapter.getItemCount() > 0) {
            txtNoProduk.setVisibility(View.INVISIBLE);
            recyclerViewProduk.setVisibility(View.VISIBLE);
        }
        else {
            txtNoProduk.setVisibility(View.VISIBLE);
            recyclerViewProduk.setVisibility(View.INVISIBLE);
        }

        if (lowonganAdapter.getItemCount() > 0) {
            txtNoLowongan.setVisibility(View.INVISIBLE);
            recyclerViewLowongan.setVisibility(View.VISIBLE);
        }
        else {
            txtNoLowongan.setVisibility(View.VISIBLE);
            recyclerViewLowongan.setVisibility(View.INVISIBLE);
        }
    }

    private class getLowonganData extends AsyncTask<String,String,String> {

        JSONObject jobj = null;

        @Override
        protected String doInBackground(String... params) {
            JSONParser jsonparser = new JSONParser();

            String url = core.API("ukm_lowongan/" + params[0]);
            jobj = jsonparser.makeHttpRequest(url);

            return jobj.toString();
        }

        protected void onPostExecute(String json){
            try {
                JSONObject jobj = new JSONObject(json);
                JSONArray jdata = jobj.getJSONArray("data");

                for (int i = 0; i < jdata.length(); i++) {
                    JSONObject c = jdata.getJSONObject(i);

                    Lowongan lowongan = new Lowongan(
                            c.getInt("id_lowongan"),
                            c.getString("judul"),
                            c.getString("deskripsi"),
                            c.getString("gaji"));

                    lowonganArrayList.add(lowongan);
                    lowonganAdapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            cekCount();
        }
    }

    private class getProdukData extends AsyncTask<String,String,String> {

        JSONObject jobj = null;

        @Override
        protected String doInBackground(String... params) {
            JSONParser jsonparser = new JSONParser();

            String url = core.API("ukm_produk_list/" + params[0]);
            jobj = jsonparser.makeHttpRequest(url);

            return jobj.toString();
        }

        protected void onPostExecute(String json){
            try {
                JSONObject jobj = new JSONObject(json);
                JSONArray jdata = jobj.getJSONArray("data");

                for (int i = 0; i < jdata.length(); i++) {
                    JSONObject c = jdata.getJSONObject(i);

                    Produk produk = new Produk(
                            c.getInt("id_produk"),
                            c.getInt("id_ukm"),
                            c.getString("nama_produk"),
                            c.getString("foto_produk"),
                            c.getString("deskripsi"),
                            c.getString("harga"));

                    produkArrayList.add(produk);
                    produkAdapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            cekCount();
        }
    }

    private class getUKMData extends AsyncTask<String,String,String> {

        JSONObject jobj = null;

        @Override
        protected String doInBackground(String... params) {
            JSONParser jsonparser = new JSONParser();

            String url = core.API("ukm_detail/" + params[0]);
            jobj = jsonparser.makeHttpRequest(url);

            return jobj.toString();
        }

        protected void onPostExecute(String json){
            try {
                JSONObject jobj = new JSONObject(json);
                JSONArray jdata = jobj.getJSONArray("data");

                for (int i = 0; i < jdata.length(); i++) {
                    JSONObject c = jdata.getJSONObject(i);

                    txtNama.setText(c.getString("nama_ukm"));
                    txtAlamat.setText(c.getString("alamat"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
