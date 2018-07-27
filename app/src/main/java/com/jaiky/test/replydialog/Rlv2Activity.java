package com.jaiky.test.replydialog;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import static com.jaiky.test.replydialog.Rlv1Activity.getWindowHeight;

public class Rlv2Activity extends AppCompatActivity implements View.OnLayoutChangeListener, MyAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private LinearLayoutManager layout;
    private MyAdapter adapter;
    private ReplyDialog replyDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rlv2);
        recyclerView = (RecyclerView) findViewById(R.id.rlv);


        layout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layout);
        adapter = new MyAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.addOnLayoutChangeListener(this);
        adapter.setOnItemClickListener(this);
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        //old是改变前的左上右下坐标点值，没有old的是改变后的左上右下坐标点值 //现在认为只要控件将Activity向上推的高度超过了1/4屏幕高，就认为软键盘弹起
        int keyHeight = getWindowHeight(this) / 4;
        if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {
            //软键盘弹起状态
        } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
            //软键盘关闭状态
            replyDialog.dismiss();
        }
    }

    @Override
    public void ItemClickListener(final View view, int position) {

        replyDialog = new ReplyDialog(Rlv2Activity.this);
        replyDialog.setHintText("回复某人的评论...")
                .setOnBtnCommitClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        replyDialog.dismiss();
                    }
                })
                .show();

        //评论条目定位到输入框上面
        final int[] coord = new int[2];
        view.getLocationOnScreen(coord);//获取当前被点击的条目在屏幕中  左上角的坐标  x  coord[0]   ,y  coord[1]
        System.out.println("view : Y" + coord[1]);
        //延时300毫秒滑动   为了使键盘完全弹出后计算滑动高度
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int[] coord1 = new int[2];
                replyDialog.getLayout().getLocationOnScreen(coord1);//  获取输入框的总布局坐标
                System.out.println("输入框 : Y" + coord1[1]);
                int span = view.getHeight();//获取当前条目的高度
                //滑动距离= 被点击view Y值 + 自身高度 - 输入框布局的Y值
                recyclerView.smoothScrollBy(0, coord[1] + span - coord1[1]);
                System.out.println("滑动距离 : " + (coord[1]  + span - coord1[1]));

            }
        }, 300);
    }
}
