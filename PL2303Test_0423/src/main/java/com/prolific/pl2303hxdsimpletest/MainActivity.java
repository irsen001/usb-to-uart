package com.prolific.pl2303hxdsimpletest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by Administrator on 2016/8/1.
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        ImageView start_btn = (ImageView)findViewById(R.id.frame_image);
        start_btn.setOnClickListener(mOnClickListener);
    }

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.frame_image:
                    Intent intent = new Intent(getApplicationContext(), PL2303HXDSimpleTest.class);
                    startActivity(intent);
                    break;
            }
        }
    };
    private void checkdata(){
/*        do(){

        }while();*/
        if(false){
            Intent intent = new Intent(getApplicationContext(), MessageActivity.class);
            startActivity(intent);
        }
    }
}