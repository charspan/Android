package com.charspan.defaultwidget.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.charspan.defaultwidget.R;

/**
 * 显示结果
 */
public class SqliteDisplayActivity extends SqliteActivity{
    private String result = null;
    private TextView display = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_display);
        Bundle extras = getIntent().getExtras();
       result = extras.getString("searchResult");
       display = (TextView)findViewById(R.id.display_txt);
       display.setText(result);
        
    }
}
