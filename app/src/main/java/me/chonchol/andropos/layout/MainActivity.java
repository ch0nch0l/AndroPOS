package me.chonchol.andropos.layout;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import me.chonchol.andropos.R;
import me.chonchol.andropos.layout.fragment.HomeFragment;
import me.chonchol.andropos.layout.fragment.ProductFragment;
import me.chonchol.andropos.layout.fragment.ProfileFragment;
import me.chonchol.andropos.layout.fragment.ReportFragment;
import me.chonchol.andropos.layout.fragment.SaleFragment;
import me.chonchol.andropos.layout.fragment.UserFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        BottomNavigationView.OnNavigationItemSelectedListener,
        HomeFragment.OnFragmentInteractionListener, ProductFragment.OnFragmentInteractionListener,
        SaleFragment.OnFragmentInteractionListener, UserFragment.OnFragmentInteractionListener,
        ReportFragment.OnFragmentInteractionListener, ProfileFragment.OnFragmentInteractionListener{


    private int id;
    private BottomNavigationView bottomNavigationView;
    private NavigationView navigationView;
    private LinearLayout menu_one, menu_two;

    public BottomNavigationView getBottomNavigationView() {
        return bottomNavigationView;
    }

    public NavigationView getNavigationView() {
        return navigationView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        viewInitalization();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        bottomNavOnItemSelect();
        Fragment homeFragment = new HomeFragment();
        replaceFragment(homeFragment);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            bottomNavigationView.setSelectedItemId(R.id.action_home);
            Fragment homeFragment = new HomeFragment();
            replaceFragment(homeFragment);
        } else if (id == R.id.nav_product) {
            bottomNavigationView.setSelectedItemId(R.id.action_product);
            Fragment productFragment = new ProductFragment();
            replaceFragment(productFragment);
        } else if (id == R.id.nav_sell) {
            bottomNavigationView.setSelectedItemId(R.id.action_sell);
            Fragment saleFragment = new SaleFragment();
            replaceFragment(saleFragment);
        } else if (id == R.id.nav_user) {
            bottomNavigationView.setSelectedItemId(R.id.action_user);
            Fragment userFragment = new UserFragment();
            replaceFragment(userFragment);
        } else if (id == R.id.nav_report) {
            bottomNavigationView.setSelectedItemId(R.id.action_report);
            Fragment reportFragment = new ReportFragment();
            replaceFragment(reportFragment);
        } else if (id == R.id.nav_profile) {
            Fragment profileFragment = new ProfileFragment();
            replaceFragment(profileFragment);
        } else if (id == R.id.nav_exit) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    public void replaceFragment(Fragment fragment){
        FragmentManager manager = this.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_layout, fragment);
        //transaction.addToBackStack(null);
        transaction.commit();
    }

    private void viewInitalization(){

        bottomNavigationView = findViewById(R.id.bottom_nav);
        menu_one = findViewById(R.id.menu_one);
        menu_two = findViewById(R.id.menu_two);

    }


    private void bottomNavOnItemSelect(){
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                id = item.getItemId();

                if (id == R.id.action_home) {
                    navigationView.setCheckedItem(R.id.nav_home);
                    Fragment homeFragment = new HomeFragment();
                    replaceFragment(homeFragment);
                } else if (id == R.id.action_product) {
                    navigationView.setCheckedItem(R.id.nav_product);
                    Fragment productFragment = new ProductFragment();
                    replaceFragment(productFragment);
                } else if (id == R.id.action_sell) {
                    navigationView.setCheckedItem(R.id.nav_sell);
                    Fragment saleFragment = new SaleFragment();
                    replaceFragment(saleFragment);
                } else if (id == R.id.action_user) {
                    navigationView.setCheckedItem(R.id.nav_user);
                    Fragment userFragment = new UserFragment();
                    replaceFragment(userFragment);
                } else if (id == R.id.action_report) {
                    navigationView.setCheckedItem(R.id.nav_report);
                    Fragment reportFragment = new ReportFragment();
                    replaceFragment(reportFragment);
                }

                return true;
            }
        });
    }

}
