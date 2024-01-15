package com.johnnette.whereitssnap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private ListView mNavDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavDrawerList = (ListView) findViewById(R.id.navList);
        mDrawerLayout  = (DrawerLayout) findViewById(R.id.drawerLayout);
        mActivityTitle = getTitle().toString();

        // Initialize an array with our titles from strings.xml
        String[] navMenuTitles = getResources().
                getStringArray(R.array.nav_drawer_items);

        // Initialize our ArrayAdapter
        mAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, navMenuTitles);

        // Set the adapter to the ListView
        mNavDrawerList.setAdapter(mAdapter);
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mNavDrawerList.setOnItemClickListener
                (new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapter,
                                            View view, int whichItem, long id) {
                        switchFragment(whichItem);
                    }
                });
        switchFragment(0);

    }

    private void switchFragment(int position){
        Fragment fragment = null;
        String fragmentID = "";
        switch (position){
            case 0:
                fragmentID = "TITLES";
                Bundle args = new Bundle();
                args.putString("Tag", "_NO_TAG");
                fragment = new TitlesFragment();
                fragment.setArguments(args);
                break;

            case 1:
                fragmentID = "TAGS";
                fragment = new TagsFragment();
                break;

            case 2:
                fragmentID = "CAPTURE";
                fragment = new CaptureFragment();
                break;


            default:
                break;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentHolder, fragment, fragmentID)
                .commit();

        mDrawerLayout.closeDrawer(mNavDrawerList);
    }


    private void setupDrawer(){

        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                R.string.drawer_open,
                R.string.drawer_close){

            // Called when drawer is opened
            public void onDrawerOpened(View drawerView){
                super.onDrawerOpened(drawerView);

                getSupportActionBar().setTitle("Make selection");

                // triggers call to onPrepareOpitonMenu
                invalidateOptionsMenu();
            }

            // Called when drawer is closed
            public void onDrawerClosed(View view){
                super.onDrawerClosed(view);

                getSupportActionBar().setTitle(mActivityTitle);

                // triggers call to onPrepareOptionMenu
                invalidateOptionsMenu();
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)){

            // drawer is open so close it
            mDrawerLayout.closeDrawer(mNavDrawerList);
        }
        else {

            // Go back to titles fragment
            // Quit if already at titles fragment
            Fragment f = getSupportFragmentManager().
                    findFragmentById(R.id.fragmentHolder);

            if(f instanceof TitlesFragment){

                finish();
                System.exit(0);
            }
            else {
                switchFragment(0);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
         super.onOptionsItemSelected(item);

         int id = item.getItemId();

//         if(id == R.id.action_setttings){
//             return true ;
//         }

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}