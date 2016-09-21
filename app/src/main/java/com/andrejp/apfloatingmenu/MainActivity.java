package com.andrejp.apfloatingmenu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingMenu floatingMenu = (FloatingMenu) findViewById(R.id.floating_menu);
        floatingMenu.addItem(0, 0, null);
        floatingMenu.addItem(0, R.string.app_name, null);
    }
}
