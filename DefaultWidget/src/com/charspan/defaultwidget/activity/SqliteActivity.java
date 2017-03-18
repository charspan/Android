package com.charspan.defaultwidget.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.charspan.defaultwidget.R;
import com.charspan.defaultwidget.model.DBManager;
import com.charspan.defaultwidget.model.MemberInfo;
import com.charspan.defaultwidget.model.WirelessQA;

public class SqliteActivity extends Activity {

    private EditText  edit_name    = null;
    private EditText  edit_age     = null;
    private EditText  edit_website = null;
    private EditText  edit_weibo   = null;

    private Button    searchAll;
    private Button    clear;
    private Button    add;
    private Button    delete;
    private Button    update;
    private Button    search;

    private String    name         = null;
    private int       age          = 0;
    private String    website      = null;
    private String    weibo        = null;

    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);
        // 初始化DBManager
        dbManager = new DBManager(this);

        edit_name = (EditText) findViewById(R.id.name_edit);
        edit_age = (EditText) findViewById(R.id.age_edit);
        edit_website = (EditText) findViewById(R.id.website_edit);
        edit_weibo = (EditText) findViewById(R.id.weibo_edit);

        add = (Button) findViewById(R.id.add);
        // 监听增加会员按钮
        add.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                name = edit_name.getText().toString();
                age = Integer.valueOf(edit_age.getText().toString());
                website = edit_website.getText().toString();
                weibo = edit_weibo.getText().toString();
                ArrayList<MemberInfo> infoList = new ArrayList<MemberInfo>();
                MemberInfo m = new MemberInfo();
                m.age = age;
                m.name = name;
                m.website = website;
                m.weibo = weibo;
                infoList.add(m);
                dbManager.add(infoList);
            }
        });

        // 查询数据库里的所有数据
        searchAll = (Button) findViewById(R.id.all);
        searchAll.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                ArrayList<MemberInfo> infoList = new ArrayList<MemberInfo>();
                infoList = dbManager.searchAllData();

                String result = "";
                for (MemberInfo info : infoList) {
                    result = result + String.valueOf(info._id) + "|" + info.name + "|" + String.valueOf(info.age) + "|"
                             + info.website + "|" + info.weibo;
                    result = result + "\n" + "------------------------------------------" + "\n";
                }
                Log.i(WirelessQA.TAG, result);
                startDisplayActivity("searchResult", result);
            }
        });
        // 通过一个会员的名字来删除一个会员信息
        delete = (Button) findViewById(R.id.del);
        delete.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                name = edit_name.getText().toString();
                dbManager.delData(name);
            }
        });
     // 清空会员信息
        clear = (Button) findViewById(R.id.clear);
        clear.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                dbManager.clearData();
            }
        });
        // 更新会员信息
        update = (Button) findViewById(R.id.update);
        update.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                name = edit_name.getText().toString();
                age = Integer.valueOf(edit_age.getText().toString());
                website = edit_website.getText().toString();
                weibo = edit_weibo.getText().toString();
                if (name == null) {
                    Toast.makeText(getApplicationContext(), "name不能为空", Toast.LENGTH_LONG).show();
                } else {
                    dbManager.updateData("age", String.valueOf(age), name);
                    dbManager.updateData("website", website, name);
                    dbManager.updateData("weibo", weibo, name);
                }
            }
        });
        // 搜索会员通过姓名
        search = (Button) findViewById(R.id.search);
        search.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                name = edit_name.getText().toString();
                if (name == null) {
                    Toast.makeText(getApplicationContext(), "name不能为空", Toast.LENGTH_LONG).show();
                } else {
                    ArrayList<MemberInfo> infoList = new ArrayList<MemberInfo>();
                    infoList = dbManager.searchData(name);

                    String result = "";
                    for (MemberInfo info : infoList) {
                        result = result + String.valueOf(info._id) + "|" + info.name + "|" + String.valueOf(info.age)
                                 + "|" + info.website + "|" + info.weibo;
                        result = result + "\n" + "------------------------------------------" + "\n";
                    }
                    Log.i(WirelessQA.TAG, result);
                    startDisplayActivity("searchResult", result);
                }

            }
        });
    }

    private void startDisplayActivity(String intentName, String intentValue) {
        Intent intent = new Intent(SqliteActivity.this, SqliteDisplayActivity.class);
        intent.putExtra(intentName, intentValue);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbManager.closeDB();// 关闭数据库
    }

}
