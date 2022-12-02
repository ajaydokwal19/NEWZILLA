package com.example.newzilla.fragments;

import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TableLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.newzilla.R;
import com.example.newzilla.adapter.AgriNewsAdapter;
import com.example.newzilla.adapter.UsersAdapter;
import com.example.newzilla.bean.AccountBean;
import com.example.newzilla.bean.NewsBean;
import com.example.newzilla.database.DataBaseHandlerAccount;
import com.example.newzilla.network.MySingleton;
import com.example.newzilla.utils.Methods;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UsersFragment extends Fragment {


    RecyclerView recycler_view_users;
    ProgressBar progressBar;

    View view;
    ArrayList<AccountBean> accountBeanArrayList = new ArrayList<>();
    UsersAdapter usersAdapter;

    public UsersFragment() {
    }

    public static UsersFragment getInstance(String data1, String data2) {
        UsersFragment usersFragment = new UsersFragment();
        Bundle bundle = new Bundle();
        bundle.putString("data1", data1);
        bundle.putString("data1", data1);
        usersFragment.setArguments(bundle);
        return usersFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = LayoutInflater.from(getActivity()).inflate(R.layout.users_fragment, container, false);
        action();
        setting();
        return view;
    }

    public void action() {

        progressBar = view.findViewById(R.id.splash_screen_progress_bar);
        recycler_view_users = view.findViewById(R.id.recycler_view_users);

    }

    public void setting() {

        recycler_view_users.setLayoutManager(new LinearLayoutManager(getActivity()));

        accountBeanArrayList = DataBaseHandlerAccount.getInstance(getActivity()).getCustomerDetails();
        usersAdapter = new UsersAdapter(getActivity(), accountBeanArrayList);
        recycler_view_users.setAdapter(usersAdapter);
        recycler_view_users.setItemAnimator(new DefaultItemAnimator());

    }


}
