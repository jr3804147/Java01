package com.bliss.csc.englishVoca10;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bliss.csc.englishVoca10.R;

import java.io.File;

public class StartCsc extends Activity {

    int Exp1 = 0;

    public void onButton3Clicked(View v)
    {
        EditText Input1 = (EditText) findViewById(R.id.Input1);
        Exp1 = Exp1 + Integer.parseInt(Input1.getText().toString());
        TextView ExpAccum1 = (TextView) findViewById(R.id.ExpAccum1);
        ExpAccum1.setText(Integer.toString(Exp1));
    }
    public void onButton4Clicked(View v)
    {
        EditText Input4 = (EditText) findViewById(R.id.Input4);
        if (Input4.getText().toString().equals("Victory"))
        {
            Exp1 = Exp1 + 100;
        }
        else if (Input4.getText().toString().equals("Defeat"))
        {
            Exp1 = Exp1 - 1000;
        }
        else
        {
            Exp1 = Exp1 - 1;
        }
        TextView ExpAccum1 = (TextView) findViewById(R.id.ExpAccum1);
        ExpAccum1.setText(Integer.toString(Exp1));
        TextView Check1 = (TextView) findViewById(R.id.Check1);
        Check1.setText(Input4.getText());
    }


    TextFileManager mTextFileManager = new TextFileManager(this);
    Button mLoadButton,mSaveButton,mDeleteButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.startstudy);

        findViewById(R.id.mainA).setOnClickListener(myClick);
        findViewById(R.id.main4).setOnClickListener(myClick);
        findViewById(R.id.main5).setOnClickListener(myClick);
        findViewById(R.id.main6).setOnClickListener(myClick);
        findViewById(R.id.main7).setOnClickListener(myClick);

        //path 부분엔 파일 경로를 지정해주세요.
        String path=getFilesDir().getAbsolutePath()+"/Memo.txt";
        File files = new File(path);
        //파일 유무를 확인합니다.
        TextView ExpAccum2 = (TextView) findViewById(R.id.ExpAccum1);
        TextView AccumO = (TextView) findViewById(R.id.AccumO);
        TextView AccumX = (TextView) findViewById(R.id.AccumX);
        if(files.exists()==true) {
        //파일이 있을시
            String memoData = mTextFileManager.load();
            String memoDat1 = memoData.substring(1,4);
            String memoDat2 = memoData.substring(4,8);
            String memoDat3 = memoData.substring(8,12);
            int test1 = Integer.parseInt(memoDat1);
            int test2 = Integer.parseInt(memoDat2);
            int test3 = Integer.parseInt(memoDat3);
            memoDat1 = String.valueOf(test1);
            memoDat2 = String.valueOf(test2);
            memoDat3 = String.valueOf(test3);
            ExpAccum2.setText(memoDat1);
            AccumO.setText(memoDat2);
            AccumX.setText(memoDat3);
            Exp1 = Integer.parseInt(ExpAccum2.getText().toString());
            Toast.makeText(getApplicationContext(), "불러오기 완료 : " + Exp1, Toast.LENGTH_LONG).show();
        } else {
        //파일이 없을시
            mTextFileManager.save("A00000000000");
            String memoData = mTextFileManager.load();
            memoData = memoData.substring(1,4);
            int test1 = Integer.parseInt(memoData);
            memoData = String.valueOf(test1);
            ExpAccum2.setText(memoData);
            Exp1 = Integer.parseInt(ExpAccum2.getText().toString());
            Toast.makeText(getApplicationContext(), "초기값 설정 : " + Exp1, Toast.LENGTH_LONG).show();
        }

        mLoadButton=(Button)findViewById(R.id.load_btn);
        mSaveButton=(Button)findViewById(R.id.save_btn);
        mDeleteButton=(Button)findViewById(R.id.delete_btn);

        mLoadButton.setOnClickListener(new View.OnClickListener() {
            TextView ExpAccum2 = (TextView) findViewById(R.id.ExpAccum1);
            TextView AccumO = (TextView) findViewById(R.id.AccumO);
            TextView AccumX = (TextView) findViewById(R.id.AccumX);

            @Override
            public void onClick(View view) {

                //path 부분엔 파일 경로를 지정해주세요.
                String path=getFilesDir().getAbsolutePath()+"/Memo.txt";
                File files = new File(path);
                //파일 유무를 확인합니다.
                if(files.exists()==false)   //파일이 없을시
                    mTextFileManager.save("A00000000000");

                String memoData = mTextFileManager.load();
                String memoDat1 = memoData.substring(1,4);
                String memoDat2 = memoData.substring(4,8);
                String memoDat3 = memoData.substring(8,12);
                int test1 = Integer.parseInt(memoDat1);
                int test2 = Integer.parseInt(memoDat2);
                int test3 = Integer.parseInt(memoDat3);
                memoDat1 = String.valueOf(test1);
                memoDat2 = String.valueOf(test2);
                memoDat3 = String.valueOf(test3);
                ExpAccum2.setText(memoDat1);
                AccumO.setText(memoDat2);
                AccumX.setText(memoDat3);

                Exp1 = Integer.parseInt(ExpAccum2.getText().toString());
                Toast.makeText(getApplicationContext(), "불러오기 완료", Toast.LENGTH_LONG).show();
            }
        });

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            TextView ExpAccum2 = (TextView) findViewById(R.id.ExpAccum1);

            @Override
            public void onClick(View view) {
                //path 부분엔 파일 경로를 지정해주세요.
                String path=getFilesDir().getAbsolutePath()+"/Memo.txt";
                File files = new File(path);
                //파일 유무를 확인합니다.
                if(files.exists()==false)   //파일이 없을시
                    mTextFileManager.save("A00000000000");

                String memoData = mTextFileManager.load();
                String memoDat2 = memoData.substring(4,8);
                String memoDat3 = memoData.substring(8,12);
                int test2 = Integer.parseInt(memoDat2);
                int test3 = Integer.parseInt(memoDat3);

                String memoDat1 = ExpAccum2.getText().toString();
                if (Integer.parseInt(memoDat1)>999) memoData = "999";
                memoData = "A"+String.format("%03d",Integer.parseInt(memoDat1))+String.format("%04d",test2)+String.format("%04d",test3);
                mTextFileManager.save(memoData);
                Toast.makeText(getApplicationContext(), "저장 완료", Toast.LENGTH_LONG).show();
            }
        });

        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            TextView ExpAccum2 = (TextView) findViewById(R.id.ExpAccum1);

            @Override
            public void onClick(View view) {
                mTextFileManager.delete();
                ExpAccum2.setText("");

                Toast.makeText(getApplicationContext(), "삭제 완료", Toast.LENGTH_LONG).show();
            }
        });
    }

    //---------------------------------
    // OnClick Listener
    //---------------------------------
    Button.OnClickListener myClick = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.mainA:
                    startActivity(new Intent(StartCsc.this, ActivityStudyA .class));
                    break;

                case R.id.main4:
                    startActivity(new Intent(StartCsc.this, ActivityStudy5 .class));
                    break;

                //test
                case R.id.main5:
                    startActivity(new Intent(StartCsc.this, ActivityStudy2.class));
                    break;

                //도움말
                case R.id.main6:
                    startActivity(new Intent(StartCsc.this, About.class));
                    break;

                //exit
                case R.id.main7:
                    finish();
                    break;

            }
        }
    };

}

