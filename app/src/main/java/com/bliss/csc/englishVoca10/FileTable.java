package com.bliss.csc.englishVoca10;

import com.bliss.csc.englishVoca10.R;

import java.io.IOException;
import java.io.InputStream;

public class FileTable {

    InputStream fi;

    FileSplit0 word;
    FileSplit1 word2;

    //for test
    public void loadFile2(int num) {

        InputStream fi = StudyView2.mContext.getResources().openRawResource(R.raw.test01 + num);

        try {
            byte[] data = new byte[fi.available()];
            fi.read(data);
            fi.close();
            String s = new String(data, "UTF-8");
            word2 = new FileSplit1(s);
        } catch (IOException e) {
        }
    }

}
