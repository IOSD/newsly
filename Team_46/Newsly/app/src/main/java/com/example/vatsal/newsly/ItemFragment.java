package com.example.vatsal.newsly;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vatsal.newsly.dummy.DummyContent;
import com.example.vatsal.newsly.dummy.DummyContent.DummyItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ItemFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    public static int i=2;
    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    static MyItemRecyclerViewAdapter adapter;
    static LinearLayoutManager layoutManager;
    static DividerItemDecoration dividerItemDecoration;
    ArrayList<NewsItem> LatestNewsItems=new ArrayList<>();
//    ArrayList<NewsItem> NationalNewsItems=new ArrayList<>();
//    ArrayList<NewsItem> InternationalNewsItems=new ArrayList<>();
    public ItemFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ItemFragment newInstance(int columnCount) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount-i);
        i--;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NewsItem newsItem=new NewsItem();
        newsItem.title="adsadad";
        newsItem.description="skjdnaskjdn";
        LatestNewsItems.add(newsItem);
//        newsItem.title="adsadad1";
//        newsItem.description="skjdnaskjdn1";
//        NationalNewsItems.add(newsItem);
//        newsItem.title="adsadad2";
//        newsItem.description="skjdnaskjdn2";
//        InternationalNewsItems.add(newsItem);


        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView) view;
        adapter=new MyItemRecyclerViewAdapter(context,LatestNewsItems);
        Retrofit.Builder builder=new Retrofit.Builder()
                .baseUrl("https://newsapi.org/v2/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit=builder.build();
        NewsService service=retrofit.create(NewsService.class);
        Call<Result> item=service.getLatestNews(SignInActivity.API_KEY,"in");
        item.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Result result=response.body();
                LatestNewsItems.clear();
                LatestNewsItems.addAll(result.articles);
                adapter.notifyDataSetChanged();


            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });
        dividerItemDecoration=new DividerItemDecoration(context,DividerItemDecoration.VERTICAL);
        layoutManager=new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(adapter);

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);
    }
}
