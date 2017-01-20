package com.prolific.pl2303hxdsimpletest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

/**
 * Created by Administrator on 2016/8/1.
 */
public class MessageActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.message);

        new AlertDialog.Builder(this)//should use activity, not context
                .setTitle("错误提示")
                .setMessage("长时间未检测到流量信号，请重新启动测量")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int which) {
                       finish();}}).show();
    }

/*    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.frame_image:
                    Intent intent = new Intent(getApplicationContext(), PL2303HXDSimpleTest.class);
                    startActivity(intent);
                    break;
            }
        }
    };*/
}