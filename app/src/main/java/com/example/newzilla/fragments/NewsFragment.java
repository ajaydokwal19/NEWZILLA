package com.example.newzilla.fragments;

import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.newzilla.R;
import com.example.newzilla.adapter.AgriNewsAdapter;
import com.example.newzilla.bean.NewsBean;
import com.example.newzilla.network.ConnectionDetector;
import com.example.newzilla.network.MySingleton;
import com.example.newzilla.utils.Methods;
import com.example.newzilla.utils.Tools;
import com.google.android.material.tabs.TabLayout;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewsFragment extends Fragment {

    EditText et_search;
    ImageView iv_submit;
    TabLayout tl_tabs;
    RecyclerView recycler_view_news;
    ProgressBar progressBar;
    ConnectionDetector connectionDetector;

    View view;

    ArrayList<NewsBean> newsBeanArrayList = new ArrayList<>();
    AgriNewsAdapter agriNewsAdapter;
    String result, address_list = null;



    public NewsFragment() {
    }

    public static NewsFragment getInstance(String data1, String data2) {
        NewsFragment newsFragment = new NewsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("data1", data1);
        bundle.putString("data2", data2);
        newsFragment.setArguments(bundle);
        return newsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = LayoutInflater.from(getActivity()).inflate(R.layout.news_fragment, container, false);
        action();
        setting();
        return view;
    }

    public void action() {
        progressBar = view.findViewById(R.id.splash_screen_progress_bar);
        et_search = view.findViewById(R.id.et_search);
        iv_submit = view.findViewById(R.id.iv_submit);
        tl_tabs = view.findViewById(R.id.tl_tabs);
        recycler_view_news = view.findViewById(R.id.recycler_view_news);

    }

    public void setting() {

        connectionDetector = new ConnectionDetector(getActivity());

        tl_tabs.addTab(tl_tabs.newTab().setText("Home"));
        tl_tabs.addTab(tl_tabs.newTab().setText("World"));
        tl_tabs.addTab(tl_tabs.newTab().setText("Science"));
        tl_tabs.addTab(tl_tabs.newTab().setText("Sports"));

        tl_tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (connectionDetector.isConnectedToInternet()) {
                    newsBeanArrayList.clear();
                    if (position == 0) {

                        progressBar.setVisibility(View.VISIBLE);
                        String ProductsUrl = "https://newsapi.org/v2/everything?apiKey=3dde7d72125c405aa30da1188df9d655&q=Breaking News";

                        getNews(ProductsUrl);
                    }
                    if (position == 1) {

                        progressBar.setVisibility(View.VISIBLE);
                        String ProductsUrl = "https://newsapi.org/v2/everything?apiKey=3dde7d72125c405aa30da1188df9d655&q=World";

                        getNews(ProductsUrl);
                    }
                    if (position == 2) {

                        progressBar.setVisibility(View.VISIBLE);
                        String ProductsUrl = "https://newsapi.org/v2/everything?apiKey=3dde7d72125c405aa30da1188df9d655&q=Science";

                        getNews(ProductsUrl);
                    }
                    if (position == 3) {

                        progressBar.setVisibility(View.VISIBLE);
                        String ProductsUrl = "https://newsapi.org/v2/everything?apiKey=3dde7d72125c405aa30da1188df9d655&q=Sports";

                        getNews(ProductsUrl);
                    }
                }else {
                    Toast.makeText(getActivity(),"Please check your internet connection",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        recycler_view_news.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (connectionDetector.isConnectedToInternet()) {
            progressBar.setVisibility(View.VISIBLE);
            String ProductsUrl ="https://newsapi.org/v2/everything?apiKey=3dde7d72125c405aa30da1188df9d655&q=Breaking News";

            getNews(ProductsUrl);

        }else {
            Toast.makeText(getActivity(),"Please check your internet connection",Toast.LENGTH_SHORT).show();
        }

        iv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (connectionDetector.isConnectedToInternet()) {
                    // clear old data
                    tl_tabs.getTabAt(0).select();
                    newsBeanArrayList.clear();
                    Methods.hideKeyboard(getActivity());
                    String query = et_search.getText().toString().trim();

                    if(query.length() == 0){

                        et_search.requestFocus();
                        et_search.setError("Required");

                        return;
                    }

                    progressBar.setVisibility(View.VISIBLE);
                    String ProductsUrl ="https://newsapi.org/v2/everything?apiKey=3dde7d72125c405aa30da1188df9d655&q="+query;

                    getNews(ProductsUrl);

                }else {
                    Toast.makeText(getActivity(),"Please check your internet connection",Toast.LENGTH_SHORT).show();
                }

            }
        });


    }


    private void getNews(String ProductsUrl)
    {

        StringRequest mStringRequest = new StringRequest(Request.Method.GET, ProductsUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                parseAllData(response);
                progressBar.setVisibility(View.GONE);
                Log.e("Response",response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressBar.setVisibility(View.GONE);
                Log.e("Error", error.toString());
            }

        }
        )
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> headers = new HashMap<String, String>();
                headers.put("User-Agent","Mozilla/5.0");
                return headers;
            }
        };


        mStringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(getActivity()).addToRequestQueue(mStringRequest);

    }



    public void parseAllData(String response)
    {
        try
        {
            JSONObject jsonObject = new JSONObject(response);
            String status = jsonObject.getString("status");
            if(status.equals("ok")){

                JSONArray productJsonArray = jsonObject.getJSONArray("articles");

                if (productJsonArray.length() >= 0)
                {

                    for (int j = 0; j < productJsonArray.length(); j++) {

                        NewsBean newsBean = new NewsBean();
                        JSONObject productJsonObject = productJsonArray.getJSONObject(j);

                        if (!productJsonObject.getJSONObject("source").isNull("name")) {
                            newsBean.setSource_name(productJsonObject.getJSONObject("source").getString("name"));
                        } else {
                            newsBean.setSource_name(null);
                        }
                        if (!productJsonObject.isNull("author")) {
                            newsBean.setAuthor(productJsonObject.getString("author"));
                        } else {
                            newsBean.setAuthor(null);
                        }
                        if (!productJsonObject.isNull("title")) {
                            newsBean.setTitle(productJsonObject.getString("title"));
                        } else {
                            newsBean.setTitle(null);
                        }
                        if (!productJsonObject.isNull("description")) {
                            newsBean.setDescription(productJsonObject.getString("description"));
                        } else {
                            newsBean.setDescription(null);
                        }
                        if (!productJsonObject.isNull("url")) {
                            newsBean.setUrl(productJsonObject.getString("url"));
                        } else {
                            newsBean.setUrl(null);
                        }
                        if (!productJsonObject.isNull("urlToImage")) {
                            newsBean.setUrlToImage(productJsonObject.getString("urlToImage"));
                        } else {
                            newsBean.setUrlToImage(null);
                        }
                        if (!productJsonObject.isNull("publishedAt")) {
                            newsBean.setPublishedAt(productJsonObject.getString("publishedAt"));
                        } else {
                            newsBean.setPublishedAt(null);
                        }
                        if (!productJsonObject.isNull("content")) {
                            newsBean.setContent(productJsonObject.getString("content"));
                        } else {
                            newsBean.setContent(null);
                        }

                        newsBeanArrayList.add(newsBean);

                    }

                    agriNewsAdapter = new AgriNewsAdapter(getActivity(), newsBeanArrayList);
                    recycler_view_news.setAdapter(agriNewsAdapter);
                    recycler_view_news.setItemAnimator(new DefaultItemAnimator());

                }
            }

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
}
