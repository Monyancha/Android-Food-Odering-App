package com.example.mayankaggarwal.dcare.activities;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.content.res.AppCompatResources;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.mayankaggarwal.dcare.R;
import com.example.mayankaggarwal.dcare.fragments.MapFragment;
import com.example.mayankaggarwal.dcare.fragments.Setting;
import com.example.mayankaggarwal.dcare.fragments.CustomNavigationDrawer;
import com.example.mayankaggarwal.dcare.fragments.NotificationFragment;
import com.example.mayankaggarwal.dcare.fragments.OrderFragment;
import com.example.mayankaggarwal.dcare.fragments.ReportFragment;
import com.example.mayankaggarwal.dcare.fragments.ShiftFragment;
import com.example.mayankaggarwal.dcare.fragments.SupportFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView toolbarText;
    FloatingActionButton shift, order, report, notification;
    Fragment fragment = null;
    ActionBarDrawerToggle mActionDrawerToggle;
    DrawerLayout drawerLayout;
    CoordinatorLayout mainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initialize();
        setBottomBar();
    }

    private void initialize() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mainView = (CoordinatorLayout) findViewById(R.id.main_coordinate);

        drawerLayout.setScrimColor(getResources().getColor(R.color.nav_blur));


//        drawerLayout.setDrawerShadow(R.drawable.selectstar,GravityCompat.START);


        mActionDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                mainView.setTranslationX(slideOffset * drawerView.getWidth());
                drawerLayout.bringChildToFront(drawerView);
                drawerLayout.requestLayout();
            }
        };

        //tint change
        ColorStateList csl = AppCompatResources.getColorStateList(this, R.color.black);
        Drawable drawableone = ContextCompat.getDrawable(getApplicationContext(), R.drawable.navicon);
        DrawableCompat.setTintList(drawableone, csl);


        mActionDrawerToggle.setDrawerIndicatorEnabled(false);
        toolbar.setNavigationIcon(drawableone);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        drawerLayout.setDrawerListener(mActionDrawerToggle);
        mActionDrawerToggle.syncState();

        settingNavigationClickListener();


        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.context);
        toolbar.setOverflowIcon(drawable);


        toolbarText = (TextView) findViewById(R.id.titletext);

        shift = (FloatingActionButton) findViewById(R.id.shift);
        order = (FloatingActionButton) findViewById(R.id.order);
        report = (FloatingActionButton) findViewById(R.id.report);
        notification = (FloatingActionButton) findViewById(R.id.notification);

        //initailly shift fragment
        fragment = ShiftFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, fragment);
        transaction.commit();

    }

    private void settingNavigationClickListener() {
        CustomNavigationDrawer.nav_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        CustomNavigationDrawer.orderlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);
                fragment = OrderFragment.newInstance();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, fragment);
                transaction.commit();


                order.setImageResource(R.drawable.ordersiconselected);
                order.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.themered)));
                navigatingBottomBar(shift, report, notification, "ORDER");
            }
        });

        CustomNavigationDrawer.reportlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);
                fragment = ReportFragment.newInstance();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, fragment);
                transaction.commit();


                report.setImageResource(R.drawable.reportsiconselected);
                report.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.themered)));
                navigatingBottomBar(shift, order, notification, "REPORT");
            }
        });

        CustomNavigationDrawer.notificationlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);
                fragment = NotificationFragment.newInstance();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, fragment);
                transaction.commit();

                notification.setImageResource(R.drawable.notificationsiconselected);
                notification.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.themered)));
                navigatingBottomBar(shift, report, order, "NOTIFICATIONS");
            }
        });

        CustomNavigationDrawer.shiftlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);
                fragment = ShiftFragment.newInstance();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, fragment);
                transaction.commit();

                shift.setImageResource(R.drawable.shiftsiconselected);
                shift.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.themered)));
                navigatingBottomBar(order, report, notification, "SHIFT");
            }
        });

        CustomNavigationDrawer.settinglayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);
                fragment = Setting.newInstance();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, fragment);
                transaction.commit();

                shift.setImageResource(R.drawable.shiftsicon);
                shift.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                navigatingBottomBar(order, report, notification, "SETTING");
            }
        });

        CustomNavigationDrawer.supportlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);
                fragment = SupportFragment.newInstance();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, fragment);
                transaction.commit();

                shift.setImageResource(R.drawable.shiftsicon);
                shift.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                navigatingBottomBar(order, report, notification, "SUPPORT");
            }
        });


    }


    private void setBottomBar() {

        shift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragment = ShiftFragment.newInstance();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, fragment);
                transaction.commit();

                shift.setImageResource(R.drawable.shiftsiconselected);
                shift.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.themered)));
                navigatingBottomBar(order, report, notification, "SHIFT");
            }
        });


        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragment = OrderFragment.newInstance();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, fragment);
                transaction.commit();


                order.setImageResource(R.drawable.ordersiconselected);
                order.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.themered)));
                navigatingBottomBar(shift, report, notification, "ORDER");
            }
        });

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragment = ReportFragment.newInstance();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, fragment);
                transaction.commit();


                report.setImageResource(R.drawable.reportsiconselected);
                report.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.themered)));
                navigatingBottomBar(shift, order, notification, "REPORT");
            }
        });

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragment = NotificationFragment.newInstance();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, fragment);
                transaction.commit();

                notification.setImageResource(R.drawable.notificationsiconselected);
                notification.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.themered)));
                navigatingBottomBar(shift, report, order, "NOTIFICATIONS");
            }
        });

    }

    private void navigatingBottomBar(FloatingActionButton one, FloatingActionButton two, FloatingActionButton three, String title) {
        toolbarText.setText(title);

        final String s_one = getResources().getResourceEntryName(one.getId());
        final String s_two = getResources().getResourceEntryName(two.getId());
        final String s_three = getResources().getResourceEntryName(three.getId());

        checkingIDS(s_one);
        checkingIDS(s_two);
        checkingIDS(s_three);
    }

    private void checkingIDS(String string) {
        if (string.equals("shift")) {
            shift.setImageResource(R.drawable.shiftsicon);
            shift.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
        } else if (string.equals("order")) {
            order.setImageResource(R.drawable.ordersicon);
            order.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
        } else if (string.equals("report")) {
            report.setImageResource(R.drawable.reportsicon);
            report.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
        } else if (string.equals("notification")) {
            notification.setImageResource(R.drawable.notificationsicon);
            notification.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
        }
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
            fragment = MapFragment.newInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, fragment);
            transaction.commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

//    public int getWidth() {
//        Display display = getWindowManager().getDefaultDisplay();
//        Point size = new Point();
//        display.getSize(size);
//        int width = size.x;
//        int height = size.y;
//        return width;
//    }
}
