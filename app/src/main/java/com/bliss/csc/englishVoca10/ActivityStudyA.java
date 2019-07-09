package com.bliss.csc.englishVoca10; //던전

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import com.bliss.csc.englishVoca10.R;

public class ActivityStudyA extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wordstudya);
    }

    //-------------------------------------
    //  Option Menu
    //-------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, "sound on");
        menu.add(0, 2, 0, "sound off");
        menu.add(0, 3, 0, "");
        menu.add(0, 4, 0, "");
        return true;
    }

    //-------------------------------------
    //  onOptions ItemSelected
    //-------------------------------------
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                StudyViewA.soundOk=1;
                break;
            case 2:
                StudyViewA.soundOk=0;
                break;
            case 3:
                break;
            case 4:
                break;
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode== KeyEvent.KEYCODE_BACK) {
            System.exit(0);   //메인화면으로 돌아가기
            return false;
        }

        return false;
    }
}

