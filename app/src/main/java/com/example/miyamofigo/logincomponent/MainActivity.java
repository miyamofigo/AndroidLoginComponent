package com.example.miyamofigo.logincomponent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by miyamofigo on 2015/06/09.
 */
public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
