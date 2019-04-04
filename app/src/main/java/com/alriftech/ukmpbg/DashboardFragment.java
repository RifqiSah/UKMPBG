package com.alriftech.ukmpbg;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.MatrixCursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class DashboardFragment extends Fragment {
    Core core;
    View rv;

    private RecyclerView recyclerView;
    private TextView txtNoData;
    private UKMAdapter adapter;
    private ArrayList<UKM> ukmArrayList;

    private LinearLayoutManager mLlm;

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        core = new Core(getActivity());
        getActivity().setTitle(R.string.act_dashboard);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = rv = inflater.inflate(R.layout.fragment_dashboard, container, false);

        recyclerView = rootView.findViewById(R.id.lst_ukm_fragment);
        txtNoData = rootView.findViewById(R.id.txt_no_ukm);

        mLlm = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLlm);

        ukmArrayList = new ArrayList<>();
        adapter = new UKMAdapter(getActivity(), ukmArrayList);
        recyclerView.setAdapter(adapter);

        new getUkmData().execute();
        return rootView;
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

    private class getUkmData extends AsyncTask<String,String,String> {

        JSONObject jobj = null;

        @Override
        protected String doInBackground(String... params) {
            JSONParser jsonparser = new JSONParser();

            String url = core.API("ukm_list");
            jobj = jsonparser.makeHttpRequest(url);

            return jobj.toString();
        }

        protected void onPostExecute(String json){
            try {
                JSONObject jobj = new JSONObject(json);
                JSONArray jdata = jobj.getJSONArray("data");

                for (int i = 0; i < jdata.length(); i++) {
                    JSONObject c = jdata.getJSONObject(i);

                    UKM ukm = new UKM(
                            c.getInt("id_ukm"),
                            c.getString("nama_ukm"),
                            c.getString("alamat"),
                            c.getString("jam_buka"),
                            c.getString("jam_tutup"));

                    ukmArrayList.add(ukm);
                    adapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            cekCount();
        }
    }
}
