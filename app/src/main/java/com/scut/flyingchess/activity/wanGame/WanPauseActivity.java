package com.scut.flyingchess.activity.wanGame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.scut.flyingchess.Global;
import com.scut.flyingchess.R;
import com.scut.flyingchess.activity.BaseActivity;
import com.scut.flyingchess.dataPack.DataPack;


public class WanPauseActivity extends BaseActivity {

    Button resume, robot, exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //ui setting
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pause);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);//Activity切换动画
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //init
        resume = (Button) findViewById(R.id.resume);
        robot = (Button) findViewById(R.id.robot);
        exit = (Button) findViewById(R.id.exit);
        //trigger
        resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        robot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Global.dataManager.isGiveUp()) {
                    robot.setText("Cancel auto");
                    Global.dataManager.giveUp(true);
                } else {
                    robot.setText("Auto");
                    Global.dataManager.giveUp(false);
                }
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Global.socketManager.send(DataPack.R_GAME_EXIT, Global.dataManager.getMyId(), Global.dataManager.getRoomId());

                if (Global.replayManager.isReplay == false) {
                    Global.replayManager.closeRecord();
                    Global.replayManager.clearRecord();
                }
                Global.wanGameManager.gameOver();
                Global.replayManager.stopReplay();
                    startActivity(new Intent(getApplicationContext(), WanHallActivity.class));


                Global.dataManager.giveUp(false);
            }
        });
        if (Global.dataManager.isGiveUp()) {
            robot.setText("Cancel auto");
        }
        resume.setTypeface(Global.getFont());
        robot.setTypeface(Global.getFont());
        exit.setTypeface(Global.getFont());
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
