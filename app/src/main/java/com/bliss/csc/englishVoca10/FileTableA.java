package com.bliss.csc.englishVoca10;   //던전

import com.bliss.csc.englishVoca10.R;

import java.io.IOException;
import java.io.InputStream;

public class FileTableA {

    InputStream fi;

    FileSplit0 word;

    // 단어공부 파일 읽어오기
    public void loadFile(int num) {

        fi = StudyViewA.mContext.getResources().openRawResource(R.raw.toefl03 + num-1);

        try {
            byte[] data = new byte[fi.available()];
            fi.read(data);
            fi.close();
            String s = new String(data, "UTF-8");
            word = new FileSplit0(s);

        } catch (IOException e) {
        }
    }
}
