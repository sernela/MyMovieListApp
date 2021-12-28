package com.example.bingewatchversion3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    RecyclerView mainRecyclerView, showsRecyclerView, moviesRecyclerView;
    ShowMainAdapter showMainAdapter;
    MovieMainAdapter movieMainAdapter;


    FloatingActionButton mAddFab, mAddWatchFab, mAddShowFab;
    TextView addShowActionText, addWatchActionText;
    Boolean isAllFabsVisible;

    Slider_Adapter slider_adapter;
    private List<Slider_Model> listItems;
    private ViewPager page;
    private TabLayout tabLayout;

    /*
    * TODO: Aoutoscroller
    *  TODO: Main RecyclerView
    *   TODO: Show and Movie text(unreadable right now)
    * */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        // try block to hide Action bar
        try {
            this.getSupportActionBar().hide();
        }
        // catch block to handle NullPointerException
        catch (NullPointerException e) {
        }

        tabLayout = findViewById(R.id.tab_indicator);


        listItems = new ArrayList<>() ;
        listItems.add(new Slider_Model(1, "", "", "https://wallpaper.dog/large/5445878.jpg"));
        listItems.add(new Slider_Model(2, "", "", "https://wallpaper.dog/large/20493499.jpg"));
        listItems.add(new Slider_Model(3, "", "", "https://wallpaperaccess.com/full/329583.jpg"));
        listItems.add(new Slider_Model(4, "", "", "https://cutewallpaper.org/21/wallpaper-from-movies/Game-of-Thrones-8-Season-Movies-Wallpaper-HD-2019-Movie-.jpg"));
        listItems.add(new Slider_Model(5, "", "", "https://wallpapercave.com/wp/wp2751453.jpg"));
        listItems.add(new Slider_Model(6, "", "", "https://images4.alphacoders.com/573/thumb-1920-57394.jpg"));
        listItems.add(new Slider_Model(7, "", "", "https://cdn.wallpapersafari.com/44/5/awNr9z.jpg"));
        listItems.add(new Slider_Model(8, "", "", "https://c4.wallpaperflare.com/wallpaper/779/691/639/movies-film-reel-technology-projector-8mm-wallpaper-preview.jpg"));
        listItems.add(new Slider_Model(9, "", "", "https://p4.wallpaperbetter.com/wallpaper/192/239/771/mad-max-mad-max-fury-road-movies-wallpaper-preview.jpg"));
        listItems.add(new Slider_Model(10, "", "", "https://c4.wallpaperflare.com/wallpaper/673/107/786/up-movie-pixar-animation-studios-movies-sky-wallpaper-preview.jpg"));
        listItems.add(new Slider_Model(11, "", "", "https://images.wallpapersden.com/image/wxl-tom-and-jerry-2021_75906.jpg"));
        listItems.add(new Slider_Model(12, "", "", "https://wallpapersqueen.com/wp-content/uploads/2021/08/Spider-Man-No-Way-Home-Movie-Wallpaper.jpg"));
        listItems.add(new Slider_Model(13, "", "", "https://images.moviesanywhere.com/movie-hero/126a33478557d77dbe5fab1afab81f24.jpeg?r=16x9&w=2560"));
        listItems.add(new Slider_Model(14, "", "", "https://www.wallpapertip.com/wmimgs/163-1637379_rock-4k-hd.jpg"));
        listItems.add(new Slider_Model(15, "", "", "https://wallpaper-mania.com/wp-content/uploads/2018/09/High_resolution_wallpaper_background_ID_77700540239.jpg"));
        listItems.add(new Slider_Model(16, "", "", "https://wallpapermemory.com/uploads/601/fantastic-four-movie-wallpaper-ultra-hd-4k-70140.jpg"));
        listItems.add(new Slider_Model(17, "", "", "https://images.hdqwalls.com/wallpapers/spiderman-no-way-home-ju.jpg"));


        setSlider_adapter(listItems);

        //--------------------------------
//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                switch (tab.getPosition()) {
//                    case 1:
//                    case 2:
//                    case 3:
//                        setSlider_adapter(listItems);
//                        return;
//                    default:
//                        setSlider_adapter(listItems);
//                }
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });

        mAddFab = findViewById(R.id.add_fab);
        mAddWatchFab = findViewById(R.id.add_watch_fab);
        mAddShowFab = findViewById(R.id.add_show_fab);

        addShowActionText = findViewById(R.id.add_show_action_text);
        addWatchActionText = findViewById(R.id.add_watch_action_text);

        mAddWatchFab.setVisibility(View.GONE);
        mAddShowFab.setVisibility(View.GONE);
        addShowActionText.setVisibility(View.GONE);
        addWatchActionText.setVisibility(View.GONE);

        isAllFabsVisible = false;

        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager layoutManagerMovies = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManagerShows = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);


        mainRecyclerView = (RecyclerView)findViewById(R.id.rv);
        mainRecyclerView.setLayoutManager(layoutManager);

        moviesRecyclerView = (RecyclerView)findViewById(R.id.watchlistrv);
        moviesRecyclerView.setLayoutManager(layoutManagerMovies);
        moviesRecyclerView.setItemAnimator(new DefaultItemAnimator());

        showsRecyclerView = (RecyclerView)findViewById(R.id.showsrv);
        showsRecyclerView.setLayoutManager(layoutManagerShows);
        showsRecyclerView.setItemAnimator(new DefaultItemAnimator());

        FirebaseRecyclerOptions<MainModel> options =
                new FirebaseRecyclerOptions.Builder<MainModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Shows"), MainModel.class)
                        .build();


        showMainAdapter = new ShowMainAdapter(options);
        showsRecyclerView.setAdapter(showMainAdapter);

        FirebaseRecyclerOptions<MovieMainModel> options2 =
                new FirebaseRecyclerOptions.Builder<MovieMainModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Movies"), MovieMainModel.class)
                        .build();




        movieMainAdapter = new MovieMainAdapter(options2);
        moviesRecyclerView.setAdapter(movieMainAdapter);


        mAddFab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!isAllFabsVisible) {

                            mAddShowFab.show();
                            mAddWatchFab.show();
                            addShowActionText.setVisibility(View.VISIBLE);
                            addWatchActionText.setVisibility(View.VISIBLE);


                            isAllFabsVisible = true;
                        } else {


                            mAddShowFab.hide();
                            mAddWatchFab.hide();
                            addShowActionText.setVisibility(View.GONE);
                            addWatchActionText.setVisibility(View.GONE);

                            isAllFabsVisible = false;
                        }
                    }
                });

        mAddShowFab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        startActivity(new Intent(getApplicationContext(), AddActivity.class));
                        mAddShowFab.hide();
                        mAddWatchFab.hide();
                        addShowActionText.setVisibility(View.INVISIBLE);
                        addWatchActionText.setVisibility(View.INVISIBLE);
                    }
                });


        mAddWatchFab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        startActivity(new Intent(getApplicationContext(), AddMovieActivity.class));
                        mAddShowFab.hide();
                        mAddWatchFab.hide();
                        addShowActionText.setVisibility(View.INVISIBLE);
                        addWatchActionText.setVisibility(View.INVISIBLE);
                    }
                });


    }


    private void setSlider_adapter(List<Slider_Model> listItems){

        page = findViewById(R.id.slider_viewPage);
        slider_adapter = new Slider_Adapter(this, listItems);
        page.setAdapter(slider_adapter);
        tabLayout.setupWithViewPager(page);

        Timer sliderTimer = new Timer();
        sliderTimer.scheduleAtFixedRate(new AutoSlider(), 4000, 6000);
        tabLayout.setupWithViewPager(page, true);

    }

    class AutoSlider extends TimerTask{

        @Override
        public void run() {

            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public  void run() {

                    if (page.getCurrentItem() < listItems.size() - 1){

                        page.setCurrentItem(page.getCurrentItem() + 1);
                    }
                    else {
                        page.setCurrentItem(0);
                    }
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        showMainAdapter.startListening();
        movieMainAdapter.startListening();

    }

    @Override
    protected void onStop() {
        super.onStop();
        showMainAdapter.stopListening();
        movieMainAdapter.stopListening();
    }

}