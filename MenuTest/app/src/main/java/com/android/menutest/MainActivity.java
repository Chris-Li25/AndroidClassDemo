package com.android.menutest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.PopupMenu;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    private TextView  tvShowOpt;
    private TextView  tvShowCtx;
    private TextView  tvShowPop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvShowOpt = (TextView)findViewById(R.id.tvShowOpt);
        tvShowPop = (TextView)findViewById(R.id.tvShowPop);
        tvShowCtx = (TextView)findViewById(R.id.tvShowCtx);

        registerForContextMenu(tvShowCtx);
    }



    //选项菜单

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu_opt,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuOpt1:
                tvShowOpt.setText("选中：选项菜单一");
                return true;
            case R.id.menuOpt2:
                tvShowOpt.setText("选中：选项菜单二");
                return true;
            case R.id.menuOpt2_1:
                tvShowOpt.setText("选中：子菜单2_1");
                return true;
            case R.id.menuOpt2_2:
                tvShowOpt.setText("选中：子菜单2_2");
                return true;
            case R.id.menuOpt3:
                tvShowOpt.setText("选中：选项菜单三");
                return true;
            case R.id.menuOpt3_1:
                tvShowOpt.setText("选中：选项菜单3_1");
                return true;
            case R.id.menuOpt3_2:
                tvShowOpt.setText("选中：选项菜单3_2");
                return true;
            default:
                return super.onOptionsItemSelected(item);


        }
    }

    //上下文菜单

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("请选择：");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu_ctx, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuCtx1:
                tvShowCtx.setText("选中：上下文菜单一");
                return true;
            case R.id.menuCtx2:
                tvShowCtx.setText("选中：上下文菜单二");
                return true;
            case R.id.menuCtx3:
                tvShowCtx.setText("选中：上下文菜单三");
                return true;
            default:
                return super.onContextItemSelected(item);

        }
    }

    //弹出菜单

    public void showPop(View view){
        PopupMenu popup = new PopupMenu(this,view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.my_menu_pop,popup.getMenu());

        popup.setOnMenuItemClickListener(this);
        popup.show();

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuPop1:
                tvShowPop.setText("选中：弹出菜单一");
                return true;
            case R.id.menuPop2:
                tvShowPop.setText("选中：弹出菜单二");
                return true;
            case R.id.menuPop3:
                tvShowPop.setText("选中：弹出菜单三");
                return true;
            default:
                return false;
        }
    }
}
