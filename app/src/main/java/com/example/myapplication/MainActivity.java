package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.text.Html;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavDirections;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckedTextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
   Toolbar toolbar;
    private AppBarConfiguration mAppBarConfiguration;
DrawerLayout drawer;
    NavController navController;
   BottomNavigationView bottom;
   public void claimReward(View view){
       //claim reward code will be written here

       Snackbar.make(view,"congrats you have claimed 100 rupee",Snackbar.LENGTH_SHORT).show();

   }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
         drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        bottom = findViewById(R.id.bottomnav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
               R.id.home2,R.id.internships,R.id.missions)
                .setDrawerLayout(drawer)
                .build();
      navController= Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        NavigationUI.setupWithNavController(bottom, navController);
      bottom.setItemIconTintList(null);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if (destination.getId() != R.id.internships && destination.getId() != R.id.missions && destination.getId() != R.id.home2) {
                    // add code calling your old flow here
                   hideBottomNavigationView(bottom);
                }
                else {
                    showBottomNavigationView(bottom);

                }
            }
        });
      navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
          @Override
          public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
              switch(destination.getId()){
                  case R.id.home2:
                      set_toolbar_home();
                      toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                          @Override
                          public boolean onMenuItemClick(MenuItem item) {
                              navController.navigate(R.id.action_home2_to_search2);
                              return true;
                          }
                      });
                      break;
                  case R.id.missions:  set_toolbar_mission();
                      toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                          @Override
                          public boolean onMenuItemClick(MenuItem item) {
                              if(item.getItemId()==R.id.mymission)
                                  navController.navigate(R.id.action_missions_to_my_Missions);
                              else
                                  navController.navigate(R.id.action_missions_to_frappProfile);
                              return true;
                          }
                      });
                      break;

              }

          }
      });



    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        return NavigationUI.navigateUp(navController,mAppBarConfiguration) || super.onSupportNavigateUp();
    }
    private void hideBottomNavigationView(BottomNavigationView view) {
        view.clearAnimation();
        view.animate().translationY(view.getHeight()).setDuration(300);
    }

    public void showBottomNavigationView(BottomNavigationView view) {
        view.clearAnimation();
        view.animate().translationY(0).setDuration(300);
    }


    private void set_toolbar_home() {
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        }
    private void set_toolbar_mission() {
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        toolbar.setBackgroundColor(getResources().getColor(R.color.white));
        toolbar.setTitleTextColor(getResources().getColor(R.color.black));
    }

    private void set_toolbar_internship() {
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        toolbar.setBackgroundColor(getResources().getColor(R.color.white));
        toolbar.setTitleTextColor(getResources().getColor(R.color.black));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.search,menu);
        return super.onCreateOptionsMenu(menu);
    }

}
