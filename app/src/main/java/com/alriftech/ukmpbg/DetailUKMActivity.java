package com.alriftech.ukmpbg;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DetailUKMActivity extends AppCompatActivity {
    private String id_ukm;
    Core core;

    TextView txtNama, txtAlamat;

    private RecyclerView recyclerView;
    private TextView txtNoData;
    private ProdukAdapter adapter;
    private ArrayList<Produk> produkArrayList;

    private LinearLayoutManager mLlm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_ukm);

        core = new Core(this);

        Bundle extra = getIntent().getExtras();
        id_ukm = String.valueOf(extra.getInt("id_ukm"));

        txtNama = findViewById(R.id.txt_des_nama);
        txtAlamat = findViewById(R.id.txt_des_alamat);

        recyclerView = findViewById(R.id.lst_ukm_produk);
        txtNoData = findViewById(R.id.txt_no_produk);

        mLlm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLlm);

        produkArrayList = new ArrayList<>();
        adapter = new ProdukAdapter(this, produkArrayList);
        recyclerView.setAdapter(adapter);

        new getUKMData().execute(id_ukm);
        new getProdukData().execute(id_ukm);
    }

    private void cekCount() {
        if (adapter.getItemCount() > 0) {
            txtNoData.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
        }
        else {
            txtNoData.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
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
                    adapter.notifyDataSetChanged();
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
