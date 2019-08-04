package com.bliss.csc.englishVoca10;  //던전

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.Toast;

import com.bliss.csc.englishVoca10.R;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class StudyViewA extends SurfaceView implements Callback{

    static int soundOk = 1;

    int questionNumber = 0;
    int numberOfquestion = 99;

    int textSizeForG4 = 0;
    int textSizeChanging = 0;
    int textSizeChanging2 = 0;

    int answerButton = 0;
    int answerUser = 0;

    int starIng = 0;
    int starIndex = 0;
    int starX, starY;

    TextFileManager mTextFileManager = new TextFileManager(getContext());
    String memoData = mTextFileManager.load();
    String ToData = memoData.substring(4,8);
    String TxData = memoData.substring(8,12);

    int oNumber = Integer.parseInt(ToData);
    int xNumber = Integer.parseInt(TxData);

    int whatStudy = 0;

    int submenuOk = 0;
    int submenuOk2 = 0;

    //값이 1이면 [단어장등록] 아이콘이 화면에 제시된다.
    int wordSave = 0;

    double rand;
    int btnPressed=0;

    String[] wordForDelete = {"", "", "", "", ""};
    String wordToDelete = "";

    // SurfaceView
    static StudyThread mThread;
    SurfaceHolder mHolder;
    static Context mContext;

    FileTableA mFile;

    //db
    MyDBHelper m_helper;

    Cursor cursor;
    int dicOk = 0;
    int movePosition = 0;

    MyButtonA btnWordSelection;
    MyButtonA btnMyNote;
    MyButtonA btnExit;
    MyButtonA btnNum1;   //btn : number1
    MyButtonA btnNum2;   //btn : number2
    MyButtonA btnNum3;  //btn : number3
    MyButtonA btnNum4;  //btn : number4
    MyButtonA btnNum5;  //btn : number5 사용안함
    MyButtonA btnPreviousQuestion;

    String whichSubject="선택단어 1";;

    //sub menu
    MyButtonA btnSub1;
    MyButtonA btnSub2;
    MyButtonA btnSub3;
    MyButtonA btnSub4;
    MyButtonA btnSub5;
    MyButtonA btnSub6;
    MyButtonA btnSub7;
    MyButtonA btnSub8;
    MyButtonA btnSub9;
    MyButtonA btnSub10;
    MyButtonA btnSub11;
    MyButtonA btnSub12;
    MyButtonA btnSub13;
    MyButtonA btnSub14;


    MyButtonA btnWordSave;

    MyButtonA btnKakaoQSending;

    MyButtonA btnLeftArrow;   //left
    MyButtonA btnRightArrow;   //right
    MyButtonA btnClose;   //close button in circle

    MyButtonA btnForDictionary[];

    //단어 전체 삭제 버튼: 여기서는 사용안함
    MyButtonA btnAllDelete;

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    int btnPreCount = 0;
    int btnPreviousCount = 0;
    int btnSelectCount = 0;
    int btnMyNoteCount = 0;
    int btnRanCount = 0 ;

    int btnNum1Count = 0;
    int btnNum2Count = 0;
    int btnNum3Count = 0;
    int btnNum4Count = 0;

    static int Width, Height;                    // View
    int subNumber=1;

    Bitmap answerx;
    Bitmap answero;

    Bitmap cap;
    Bitmap explain;
    Bitmap star[] = new Bitmap[4];   //1,2,3,4 버튼 클릭시 원이 나옴.
    static SoundPool sdPool;
    static int dingdongdaeng, taeng;

    public StudyViewA(Context context, AttributeSet attrs) {
        super(context, attrs);
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);

        mHolder = holder;
        mContext = context;
        mThread = new StudyThread(holder, context);

        double rand2 = Math.random();
        questionNumber = (int) ((rand2 * (numberOfquestion + 2)));

        initAll();
        makeQuestion(subNumber);
        setFocusable(true);
    }


    private void initAll() {
        m_helper = new MyDBHelper(mContext, "testforeng.db", null, 1);
        Display display = ((WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Width = display.getWidth();
        Height = display.getHeight();
        textSizeChanging = (int) (Width * 64 / 1280);
        if (Width > 1700) textSizeForG4 = 120;

        mFile = new FileTableA();

        btnWordSelection = new MyButtonA(30, 14 + 20, 2,0);  //단어선택

        btnMyNote = new MyButtonA(btnWordSelection.x + btnWordSelection.w * 2, 14 + 20, 4,0);  //내노트
        btnExit = new MyButtonA(Width - btnWordSelection.w * 2 - btnWordSelection.w / 3, 14 + 20, 5,0);  //exit

        btnNum1 = new MyButtonA(btnWordSelection.x + 70 + 50, btnWordSelection.y + btnWordSelection.h * 2 + 93, 7, 0); // number 1
        btnNum2 = new MyButtonA(btnWordSelection.x + 70 + 50, btnNum1.y + btnNum1.h * 2 + 8, 8, 0); // number 2
        btnNum3 = new MyButtonA(btnWordSelection.x + 70 + 50, btnNum2.y + btnNum2.h * 2 + 8, 9, 0); // number 3
        btnNum4 = new MyButtonA(btnWordSelection.x + 70 + 50, btnNum3.y + btnNum3.h * 2 + 8, 10, 0); // number 4
        btnNum5 = new MyButtonA(btnWordSelection.x + 70 + 50, btnNum4.y + btnNum4.h * 2 + 8, 11, 0); // number 5  여기서는 사용안함

        btnPreviousQuestion = new MyButtonA(Width - btnWordSelection.w * 6, btnNum1.y + btnNum1.h * 2 + 1, 12, 0); //다음문제

        btnWordSave = new MyButtonA(Width - btnWordSelection.w * 6, btnPreviousQuestion.y + btnPreviousQuestion.h * 2 + 1, 23, 0); //단어장등록

        // 카카오 문제보내기 버튼
        btnKakaoQSending = new MyButtonA(Width - btnWordSelection.w * 11, btnNum1.y + btnNum1.h * 2 + 1, 33, 0);
//        btnKakaoQSending = new MyButtonA(btnWordSelection.x + 70 + 50, btnWordSelection.y + btnWordSelection.h * 15, 33, 0);

        btnSub1 = new MyButtonA(30 + 10, btnWordSelection.y + btnWordSelection.h * 2 + 5, 0,1);
        btnSub2 = new MyButtonA(btnSub1.x + btnSub1.w * 2, btnSub1.y, 1,1);
        btnSub3 = new MyButtonA(btnSub2.x + btnSub2.w * 2, btnSub1.y, 2,1);
        btnSub4 = new MyButtonA(btnSub3.x + btnSub3.w * 2, btnSub1.y, 3,1);
        btnSub5 = new MyButtonA(btnSub4.x + btnSub4.w * 2, btnSub1.y, 4,1);
        btnSub6 = new MyButtonA(btnSub5.x + btnSub4.w * 2 , btnSub1.y, 5,1);
        btnSub7 = new MyButtonA(btnSub6.x + btnSub6.w * 2, btnSub1.y, 6,1);
        btnSub8 = new MyButtonA(btnSub7.x + btnSub2.w * 2, btnSub1.y, 7,1);

        btnSub9 = new MyButtonA(btnSub1.x, btnSub1.y + btnSub1.h * 2, 8,1);
        btnSub10 = new MyButtonA(btnSub2.x, btnSub1.y + btnSub1.h * 2, 9,1);
        btnSub11 = new MyButtonA(btnSub3.x, btnSub1.y + btnSub1.h * 2, 10,1);
        btnSub12 = new MyButtonA(btnSub4.x, btnSub1.y + btnSub1.h * 2, 11,1);
        btnSub13 = new MyButtonA(btnSub5.x, btnSub1.y + btnSub1.h * 2, 12,1);
        btnSub14 = new MyButtonA(btnSub6.x, btnSub1.y + btnSub1.h * 2, 13,1);

        // 내사전에서 왼쪽, 오른쪽 버튼
        btnLeftArrow = new MyButtonA(btnWordSelection.x, Height - btnWordSelection.h * 2, 26, 0);    // left arrow
        btnRightArrow = new MyButtonA(btnWordSelection.x + 150, Height - btnWordSelection.h * 2, 27, 0);  //right arrow

        // 닫기 버튼
        btnClose = new MyButtonA(Width - btnWordSelection.w * 2, Height - btnWordSelection.h * 2, 28, 0); //close button in dic
        btnForDictionary = new MyButtonA[5];

        // 삭제버튼
        for (int i = 0; i < 5; i++)
            btnForDictionary[i] = new MyButtonA(Width - btnNum1.w*4, btnNum1.h*4 + i * btnNum1.h*2 + btnNum1.h/12 * i, 29, 0);

        answerx = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.answerx);

        int xxx = Width / 6;
        answerx = Bitmap.createScaledBitmap(answerx, xxx, xxx, true);
        answero = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.answero);
        answero = Bitmap.createScaledBitmap(answero, xxx, xxx, true);
        explain = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.explain);

        explain = Bitmap.createScaledBitmap(explain, Width / 11, Height / 7, true);

        cap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.cap);
        cap = Bitmap.createScaledBitmap(cap, Width / 12, Height / 14, true);

        for (int i = 0; i < 4; i++) {
            star[i] = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.circlewhite);
            star[i] = Bitmap.createScaledBitmap(star[i], btnNum1.w * 2 + i * 2, btnNum1.w * 2 + i * 2, true);
        }

        sdPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        dingdongdaeng = sdPool.load(mContext, R.raw.dingdongdaeng, 1);
        taeng = sdPool.load(mContext, R.raw.taeng, 2);

    }


    public void  makeQuestion(int x) {

        mFile.loadFile(x);

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mThread.setRunning(true);
        try {

            if (mThread.getState() == Thread.State.TERMINATED) {

                mThread = new StudyThread(getHolder(), mContext);
                mThread.setRunning(true);
                setFocusable(true);
                mThread.start();
            } else {
                mThread.start();
            }
        } catch (Exception ex) {
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        StopStudy();

        boolean retry = true;
        mThread.setRunning(false);
        while (retry) {
            try {
                mThread.join();
                retry = false;
            } catch (Exception e) {
            }
        }

    }

    public static void StopStudy() {
        mThread.StopThread();
    }

    class StudyThread extends Thread {
        boolean canRun = true;
        boolean isWait = false;
        Paint paint = new Paint();
        Paint paint2 = new Paint();

        Paint paint3 = new Paint();


        public StudyThread(SurfaceHolder holder, Context context) {

            paint.setColor(Color.WHITE);
            paint.setAntiAlias(true);
            paint.setTypeface(Typeface.create("", Typeface.BOLD));

            paint2.setColor(Color.WHITE);
            paint2.setAntiAlias(true);
            paint2.setTypeface(Typeface.create("", Typeface.BOLD));

            paint3.setColor(Color.WHITE);
            paint3.setAntiAlias(true);
            paint3.setTypeface(Typeface.create("", Typeface.BOLD));

            paint.setTextSize(TypedValue.COMPLEX_UNIT_DIP);
            paint2.setTextSize(35);
            paint3.setTextSize(40);
        }

        public void setRunning(boolean b) {
            // TODO Auto-generated method stub

        }

        public void DrawAll(Canvas canvas) {

            Paint pp = new Paint();
            pp.setColor(0xff331b00);
            Paint frame = new Paint();
            frame.setColor(0xff664b00);
            canvas.drawRect(0,0,Width,Height,frame);
            canvas.drawRect(btnNum1.w/2,btnNum1.w/2,Width-btnNum1.w/2,Height-btnNum1.w/2,pp);

            textSizeChanging = (int) (Width * 58 / 1280);  // aha here I have to adjust
            textSizeChanging2 = (int) (Width * 40 / 1280);

            //텍스트 크기 설정
            paint.setTextSize(Width/23);   //영어글씨
            paint2.setTextSize(Width/40);  //맞은 개수, 틀린 개수 글씨
            paint3.setTextSize(Width/34);  //영어단어 해설에 나오는 글씨

            if (submenuOk == 0 && submenuOk2 == 0) {

                if (questionNumber > numberOfquestion) questionNumber = numberOfquestion;
                if (questionNumber < 0) questionNumber = 0;

                canvas.drawText(FileSplit0.questionNum[questionNumber][0],
                        btnWordSelection.x + btnNum1.w, btnWordSelection.y + btnWordSelection.h * 3, paint); //문제번호
                canvas.drawText(FileSplit0.questionNum[questionNumber][1],
                        btnWordSelection.x + btnNum1.w * 3, btnWordSelection.y + btnWordSelection.h * 3, paint); //문제

                canvas.drawText(FileSplit0.questionNum[questionNumber][2],
                        btnWordSelection.x + btnNum1.w * 6, btnNum1.y + btnNum1.w + btnNum1.w/2, paint); //1
                canvas.drawText(FileSplit0.questionNum[questionNumber][3],
                        btnWordSelection.x + btnNum1.w * 6, btnNum2.y + btnNum1.w + btnNum1.w/2, paint); //2
                canvas.drawText(FileSplit0.questionNum[questionNumber][4],
                        btnWordSelection.x + btnNum1.w * 6, btnNum3.y + btnNum1.w + btnNum1.w/2, paint); //3
                canvas.drawText(FileSplit0.questionNum[questionNumber][5],
                        btnWordSelection.x + btnNum1.w * 6, btnNum4.y + btnNum1.w + btnNum1.w/2, paint);  //4

            }

            if (submenuOk == 0 && submenuOk2 == 0) {
                // 1~4
                canvas.drawBitmap(btnNum1.button_img, btnNum1.x, btnNum1.y, null);
                canvas.drawBitmap(btnNum2.button_img, btnNum2.x, btnNum2.y, null);
                canvas.drawBitmap(btnNum3.button_img, btnNum3.x, btnNum3.y, null);
                canvas.drawBitmap(btnNum4.button_img, btnNum4.x, btnNum4.y, null);

                canvas.drawBitmap(btnKakaoQSending.button_img, btnKakaoQSending.x, btnKakaoQSending.y, null);
            }

            if (submenuOk == 0 && submenuOk2 == 0) {
                canvas.drawBitmap(btnWordSelection.button_img, btnWordSelection.x, btnWordSelection.y, null);
                canvas.drawBitmap(btnWordSelection.button_img, btnWordSelection.x, btnWordSelection.y, null);
                canvas.drawBitmap(btnWordSelection.button_img, btnWordSelection.x, btnWordSelection.y, null);

                canvas.drawBitmap(btnMyNote.button_img, btnMyNote.x, btnMyNote.y, null);
                canvas.drawBitmap(btnExit.button_img, btnExit.x, btnExit.y, null);
            }

            int imsy;

            // sub menu
            if (submenuOk == 1) {

                canvas.drawRect(0,0,Width,Height,frame);
                canvas.drawRect(btnNum1.w/2,btnNum1.w/2,Width-btnNum1.w/2,Height-btnNum1.w/2,pp);
                canvas.drawText("각 서브메뉴에는 100개의 단어가 있습니다.", btnWordSelection.x + 50 + 50, btnWordSelection.h * 2, paint);
                canvas.drawBitmap(btnClose.button_img, btnClose.x, btnClose.y, null);

                canvas.drawBitmap(btnSub3.button_img, btnSub3.x, btnSub3.y, null);
                canvas.drawBitmap(btnSub4.button_img, btnSub4.x, btnSub4.y, null);
                canvas.drawBitmap(btnSub5.button_img, btnSub5.x, btnSub5.y, null);
                canvas.drawBitmap(btnSub6.button_img, btnSub6.x, btnSub6.y, null);
                canvas.drawBitmap(btnSub7.button_img, btnSub7.x, btnSub7.y, null);
                canvas.drawBitmap(btnSub8.button_img, btnSub8.x, btnSub8.y, null);
                canvas.drawBitmap(btnSub9.button_img, btnSub9.x, btnSub9.y, null);
                canvas.drawBitmap(btnSub10.button_img, btnSub10.x, btnSub10.y, null);
                canvas.drawBitmap(btnSub11.button_img, btnSub11.x, btnSub11.y, null);
                canvas.drawBitmap(btnSub12.button_img, btnSub12.x, btnSub12.y, null);
                canvas.drawBitmap(btnSub13.button_img, btnSub13.x, btnSub13.y, null);
                canvas.drawBitmap(btnSub14.button_img, btnSub14.x, btnSub14.y, null);



            }


            if (submenuOk == 0 && submenuOk2 == 0) {
                canvas.drawText(whichSubject, Width / 2, btnExit.h, paint2);
                canvas.drawText("맞은 개수: " + Integer.toString(oNumber) + "개  틀린 개수: " + Integer.toString(xNumber) + "개", Width / 2, btnExit.h * 2, paint2);
            }

            if (answerButton == 1 && dicOk == 0) {
                int sss = 0;
                sss = Integer.parseInt(FileSplit0.questionNum[questionNumber][6].trim());

                if (sss == answerUser) {
                    canvas.drawBitmap(answero, Width/2, btnNum1.y + 20, null);
                } else canvas.drawBitmap(answerx, Width/2, btnNum1.y + 20, null);

                canvas.drawBitmap(btnPreviousQuestion.button_img, btnPreviousQuestion.x, btnPreviousQuestion.y, null);  //다음문제

                if (answerButton == 1 && wordSave == 1)
                    canvas.drawBitmap(btnWordSave.button_img, btnWordSave.x, btnWordSave.y, null);   // 단어장풀기

                //답 해설
                canvas.drawBitmap(explain, 8, btnNum4.y + btnNum4.h * 2 - 50, null);  //해설그림
                canvas.drawText(FileSplit0.questionNum[questionNumber][7],  btnNum1.w, btnNum4.y + btnNum4.h * 4, paint3);
                canvas.drawText(FileSplit0.questionNum[questionNumber][8],  btnNum1.w, btnNum4.y + btnNum4.h * 4 + btnNum1.h , paint3);
            }

            if (starIng == 1) {

                starIndex += 1;
                if (starIndex >= 15) {
                    starIng = 0;
                    starIndex = 0;
                } else
                    canvas.drawBitmap(star[starIndex / 4], starX - starIndex / 4, starY - starIndex / 4, null);
            }

            // 1~5
            if(btnPressed==1) {
                btnPreCount++;
                btnPreviousCount++;
                btnSelectCount++;
                btnMyNoteCount++;
                btnRanCount++;

                btnNum1Count++;
                btnNum2Count++;
                btnNum3Count++;
                btnNum4Count++;

            }

            if (btnSelectCount == 15) {
                btnSelectCount = 0;
                btnWordSelection.btn_released();
            }

            if (btnMyNoteCount == 15) {
                btnMyNoteCount = 0;
                btnMyNote.btn_released();
            }

            if (btnNum1Count == 15) {
                btnNum1Count = 0;
                btnNum1.btn_released();
            }
            if (btnNum2Count == 15) {
                btnNum2Count = 0;
                btnNum2.btn_released();
            }
            if (btnNum3Count == 15) {
                btnNum3Count = 0;
                btnNum3.btn_released();
            }
            if (btnNum4Count == 15) {
                btnNum4Count = 0;
                btnNum4.btn_released();
            }

            // 내노트 나타나기
            if (dicOk == 1) {
                canvas.drawRect(0,0,Width,Height,frame);
                canvas.drawRect(btnNum1.w/2,btnNum1.w/2,Width-btnNum1.w/2,Height-btnNum1.w/2,pp);
                SQLiteDatabase db = m_helper.getReadableDatabase();

                cursor = db.query("englishWordTable", null, null, null, null, null, null);

                int numofdb = cursor.getCount();

                if (movePosition > numofdb) movePosition -= 5;
                else if (movePosition == numofdb) movePosition -= 5;

                if (movePosition <= 0) movePosition = 0;

                for (int i = 0; i < 5; i++) {
                    if (cursor.moveToPosition(movePosition + i) == false) break;
                    canvas.drawText((movePosition + i + 1) + " " + cursor.getString(1) + " : "
                            + cursor.getString(2), btnExit.w * 3, btnExit.h * 4 + btnExit.w * 3 / 2 * i, paint);
                    //현재 내노트에 있는 단어 5개 대한 영어단어를 삭제를 위해 wordForDelete[]에담는다.
                    wordForDelete[i] = cursor.getString(1);
                }

                //reft, right arrow  and close button in circle format
                canvas.drawBitmap(btnLeftArrow.button_img, btnLeftArrow.x, btnLeftArrow.y, null);
                canvas.drawBitmap(btnRightArrow.button_img, btnRightArrow.x, btnRightArrow.y, null);
                canvas.drawBitmap(btnClose.button_img, btnClose.x, btnClose.y, null);


                int x = 0;
                for (int i = 0; i < 5; i++) {
                    canvas.drawText("저장된 단어수 : " + Integer.toString(numofdb), 100, 100, paint2);
                    imsy = 0;
                    if (numofdb == 0) {
                        canvas.drawText("단어가 없습니다!", 70, 180 + 90 * i, paint);
                        break;
                    }

                    canvas.drawBitmap(btnForDictionary[i].button_img, btnForDictionary[i].x, btnForDictionary[i].y + btnExit.h / 3, null);

                    x = (numofdb - 1) / 5;
                    if ((movePosition) / 5 < x) imsy = 1;
                    else imsy = 0;

                    if (imsy == 0) {
                        if (numofdb % 5 == 4 && i == 3) break;
                        if (numofdb % 5 == 3 && i == 2) break;
                        if (numofdb % 5 == 2 && i == 1) break;
                        if (numofdb % 5 == 1 && i == 0) break;
                    }
                }
                cursor.close();
                db.close();
            }
        }               // end of drawall


        public void run() {
            Canvas canvas = null;
            while (canRun) {
                canvas = mHolder.lockCanvas();
                try {
                    synchronized (mHolder) {
                        DrawAll(canvas);
                    } // sync
                } finally {
                    if (canvas != null)
                        mHolder.unlockCanvasAndPost(canvas);
                } // try


                synchronized (this) {
                    if (isWait)
                        try {
                            wait();
                        } catch (Exception e) {
                            // nothing
                        }
                } // sync

            } // while
        } // run


        public void StopThread() {
            canRun = false;
            synchronized (this) {
                this.notify();
            }
        }


    } // Thread

    // keykey
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = 0, y = 0;

        synchronized (mHolder) {

            if (event.getAction() == MotionEvent.ACTION_DOWN) {

                x = (int) event.getX();
                y = (int) event.getY();

            } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            } else if (event.getAction() == MotionEvent.ACTION_UP) {

            }
        }   // end of sync

        if (x > btnWordSelection.x && x < (btnWordSelection.x + btnWordSelection.w * 2) && y > btnWordSelection.y && y < (btnWordSelection.y + btnWordSelection.h * 2)) {
            btnWordSelection.btn_press();
//          questionNumber = 0;
            rand = Math.random();
            questionNumber = (int) ((rand * (numberOfquestion + 2)));

            answerButton = 0;
            btnSelectCount = 0;
            btnPressed=1;
            submenuOk = 1;
            dicOk = 0;
            whatStudy = 0;
            submenuOk2 = 0;
        }

        if (x > btnMyNote.x && x < (btnMyNote.x + btnMyNote.w * 2) && y > btnMyNote.y && y < (btnMyNote.y + btnMyNote.h * 2)) {
            btnMyNote.btn_press();
            btnMyNoteCount = 0;
            btnPressed=1;
            dicOk = 1;
            submenuOk = 0;
        }

        //exit button
        if (dicOk != 1)
            if (x > btnExit.x && x < (btnExit.x + btnExit.w * 2) && y > btnExit.y && y < (btnExit.y + btnExit.h * 2)) {
                btnPressed=1;
                System.exit(0);
                submenuOk = 0;
                dicOk = 0;
            }

        if (answerButton == 0) {
            // 1,2,3,4 선택 버튼
            if (dicOk == 0 && submenuOk == 0 && submenuOk2 == 0)
                if (x > btnNum1.x && x < (btnNum1.x + btnNum1.w * 2) && y > btnNum1.y && y < (btnNum1.y + btnNum1.h * 2)) {

                    int sss = 0;
                    submenuOk = 0;
                    dicOk = 0;
                    answerUser = 1;
                    btnNum1.btn_press();
                    answerButton = 1;
                    wordSave = 1;
                    btnNum1Count = 0;
                    btnPressed=1;
                    starIng = 1;
                    starX = btnNum1.x;
                    starY = btnNum1.y;

                    sss = Integer.parseInt(FileSplit0.questionNum[questionNumber][6].trim());
                    if (sss == answerUser) {
                        if (soundOk == 1)
                            StudyViewA.sdPool.play(StudyViewA.dingdongdaeng, 1, 1, 9, 0, 1);
                        oNumber++;

                        String memoData = mTextFileManager.load();
                        String ToData = memoData.substring(4,8);
                        int test1 = Integer.parseInt(ToData);
                        ToData = String.valueOf(++test1);

                        if (Integer.parseInt(ToData)>9999) ToData = "9999";
                        memoData = memoData.substring(0,4)+String.format("%04d",Integer.parseInt(ToData))+memoData.substring(8,12);
                        mTextFileManager.save(memoData);
                    } else {
                        if (soundOk == 1)
                            StudyViewA.sdPool.play(StudyViewA.taeng, 1, 1, 9, 0, 1);
                        xNumber++;

                        String memoData = mTextFileManager.load();
                        String TxData = memoData.substring(8,12);
                        int test1 = Integer.parseInt(TxData);
                        TxData = String.valueOf(++test1);

                        if (Integer.parseInt(TxData)>9999) TxData = "9999";
                        memoData = memoData.substring(0,4)+memoData.substring(4,8)+String.format("%04d",Integer.parseInt(TxData));
                        mTextFileManager.save(memoData);
                    }
                }


            if (dicOk == 0 && submenuOk == 0 && submenuOk2 == 0)
                if (x > btnNum2.x && x < (btnNum2.x + btnNum2.w * 2) && y > btnNum2.y && y < (btnNum2.y + btnNum2.h * 2)) {

                    int sss = 0;
                    submenuOk = 0;
                    dicOk = 0;
                    btnNum2.btn_press();
                    answerButton = 1;
                    wordSave = 1;
                    answerUser = 2;
                    btnNum2Count = 0;
                    btnPressed=1;
                    starIng = 1;
                    starX = btnNum2.x;
                    starY = btnNum2.y;

                    sss = Integer.parseInt(FileSplit0.questionNum[questionNumber][6].trim());

                    if (sss == answerUser) {
                        if (soundOk == 1)
                            StudyViewA.sdPool.play(StudyViewA.dingdongdaeng, 1, 1, 9, 0, 1);
                        oNumber++;

                        String memoData = mTextFileManager.load();
                        String ToData = memoData.substring(4,8);
                        int test1 = Integer.parseInt(ToData);
                        ToData = String.valueOf(++test1);

                        if (Integer.parseInt(ToData)>9999) ToData = "9999";
                        memoData = memoData.substring(0,4)+String.format("%04d",Integer.parseInt(ToData))+memoData.substring(8,12);
                        mTextFileManager.save(memoData);
                    } else {
                        if (soundOk == 1)
                            StudyViewA.sdPool.play(StudyViewA.taeng, 1, 1, 9, 0, 1);
                        xNumber++;

                        String memoData = mTextFileManager.load();
                        String TxData = memoData.substring(8,12);
                        int test1 = Integer.parseInt(TxData);
                        TxData = String.valueOf(++test1);

                        if (Integer.parseInt(TxData)>9999) TxData = "9999";
                        memoData = memoData.substring(0,4)+memoData.substring(4,8)+String.format("%04d",Integer.parseInt(TxData));
                        mTextFileManager.save(memoData);
                    }

                }


            if (dicOk == 0 && submenuOk == 0 && submenuOk2 == 0)
                if (x > btnNum3.x && x < (btnNum3.x + btnNum3.w * 2) && y > btnNum3.y && y < (btnNum3.y + btnNum3.h * 2)) {
                    btnNum3.btn_press();
                    btnPressed=1;
                    answerButton = 1;
                    wordSave = 1;
                    answerUser = 3;

                    submenuOk = 0;
                    dicOk = 0;
                    btnNum3Count = 0;
                    starIng = 1;
                    starX = btnNum3.x;
                    starY = btnNum3.y;

                    int sss = 0;
                    sss = Integer.parseInt(FileSplit0.questionNum[questionNumber][6].trim());

                    if (sss == answerUser) {
                        if (soundOk == 1)
                            StudyViewA.sdPool.play(StudyViewA.dingdongdaeng, 1, 1, 9, 0, 1);
                        oNumber++;

                        String memoData = mTextFileManager.load();
                        String ToData = memoData.substring(4,8);
                        int test1 = Integer.parseInt(ToData);
                        ToData = String.valueOf(++test1);

                        if (Integer.parseInt(ToData)>9999) ToData = "9999";
                        memoData = memoData.substring(0,4)+String.format("%04d",Integer.parseInt(ToData))+memoData.substring(8,12);
                        mTextFileManager.save(memoData);
                    } else {
                        xNumber++;
                        if (soundOk == 1)
                            StudyViewA.sdPool.play(StudyViewA.taeng, 1, 1, 9, 0, 1);

                        String memoData = mTextFileManager.load();
                        String TxData = memoData.substring(8,12);
                        int test1 = Integer.parseInt(TxData);
                        TxData = String.valueOf(++test1);

                        if (Integer.parseInt(TxData)>9999) TxData = "9999";
                        memoData = memoData.substring(0,4)+memoData.substring(4,8)+String.format("%04d",Integer.parseInt(TxData));
                        mTextFileManager.save(memoData);
                    }

                }


            if (dicOk == 0 && submenuOk == 0 && submenuOk2 == 0)
                if (x > btnNum4.x && x < (btnNum4.x + btnNum4.w * 2) && y > btnNum4.y && y < (btnNum4.y + btnNum4.h * 2)) {

                    int sss = 0;
                    btnPressed=1;
                    btnNum4.btn_press();
                    answerButton = 1;
                    wordSave = 1;
                    answerUser = 4;

                    btnNum4Count = 0;
                    submenuOk = 0;
                    dicOk = 0;
                    starIng = 1;
                    starX = btnNum4.x;
                    starY = btnNum4.y;
                    sss = Integer.parseInt(FileSplit0.questionNum[questionNumber][6].trim());

                    if (sss == answerUser) {
                        if (soundOk == 1)
                            StudyViewA.sdPool.play(StudyViewA.dingdongdaeng, 1, 1, 9, 0, 1);
                        oNumber++;

                        String memoData = mTextFileManager.load();
                        String ToData = memoData.substring(4,8);
                        int test1 = Integer.parseInt(ToData);
                        ToData = String.valueOf(++test1);

                        if (Integer.parseInt(ToData)>9999) ToData = "9999";
                        memoData = memoData.substring(0,4)+String.format("%04d",Integer.parseInt(ToData))+memoData.substring(8,12);
                        mTextFileManager.save(memoData);
                    } else {
                        xNumber++;
                        if (soundOk == 1)
                            StudyViewA.sdPool.play(StudyViewA.taeng, 1, 1, 9, 0, 1);

                        String memoData = mTextFileManager.load();
                        String TxData = memoData.substring(8,12);
                        int test1 = Integer.parseInt(TxData);
                        TxData = String.valueOf(++test1);

                        if (Integer.parseInt(TxData)>9999) TxData = "9999";
                        memoData = memoData.substring(0,4)+memoData.substring(4,8)+String.format("%04d",Integer.parseInt(TxData));
                        mTextFileManager.save(memoData);
                    }

                }

        } // end of answerButton


        if (answerButton == 1)
            if (x > btnPreviousQuestion.x && x < (btnPreviousQuestion.x + btnPreviousQuestion.w * 2) && y > btnPreviousQuestion.y && y < (btnPreviousQuestion.y + btnPreviousQuestion.h * 2)) {
                btnPreviousQuestion.btn_press();
                rand = Math.random();
                questionNumber = (int) ((rand * (numberOfquestion + 2)));
//              questionNumber += 1;
                answerButton = 0;
                submenuOk = 0;
            }

        if (answerButton == 1 && wordSave == 1)
            if (x > btnWordSave.x && x < (btnWordSave.x + btnWordSave.w * 2) && y > btnWordSave.y && y < (btnWordSave.y + btnWordSave.h * 2)) {
                wordSave = 0;
                btnWordSave.btn_press();
                submenuOk = 0;

                //sss 는 정답 숫자를 입력받는다.
                int sss = Integer.parseInt(FileSplit0.questionNum[questionNumber][6].trim());

                dicOk = 0;
                SQLiteDatabase db = m_helper.getWritableDatabase();
                String sql = String.format("INSERT INTO englishWordTable VALUES(NULL, '%s', '%s');",
                        // sss+1 은 정답에 해당되는 영어단어
                        //questionNum[questionNumber][1] 에서 첨자 1에 들어가는 것은 단어뜻이다.(한글)
                        FileSplit0.questionNum[questionNumber][sss + 1], FileSplit0.questionNum[questionNumber][1]);
                db.execSQL(sql);
                db.close();

                Toast toast = Toast.makeText(mContext, "단어가 저장되었습니다.", Toast.LENGTH_SHORT);
                toast.show();
            }

        //카카오톡으로 문제 보내기
        if (submenuOk == 0 && answerButton == 0)
            if (x > btnKakaoQSending.x && x < (btnKakaoQSending.x + btnKakaoQSending.w * 2) && y > btnKakaoQSending.y && y
                    < (btnKakaoQSending.y + btnKakaoQSending.h * 2)) {

                controlButton();
            }


        // submenu 3
        if (submenuOk == 1)
            if (x > btnSub3.x && x < (btnSub3.x + btnSub3.w * 2) && y > btnSub3.y && y < (btnSub3.y + btnSub3.h * 2)) {
                whichSubject = "선택단어 3";
                btnSub3.btn_press();
                submenuOk = 0;
                int subNumber = 1;
                makeQuestion(subNumber);
            }

        if (submenuOk == 1)
            if (x > btnSub4.x && x < (btnSub4.x + btnSub4.w * 2) && y > btnSub4.y && y < (btnSub4.y + btnSub4.h * 2)) {
                whichSubject = "선택단어 4";
                btnSub4.btn_press();
                submenuOk = 0;
                int subNumber = 2;
                makeQuestion(subNumber);
            }

        if (submenuOk == 1)
            if (x > btnSub5.x && x < (btnSub5.x + btnSub5.w * 2) && y > btnSub5.y && y < (btnSub5.y + btnSub5.h * 2)) {
                whichSubject = "선택단어 5";
                btnSub5.btn_press();
                submenuOk = 0;
                subNumber = 3;
                makeQuestion(subNumber);
            }

        if (submenuOk == 1)
            if (x > btnSub6.x && x < (btnSub6.x + btnSub6.w * 2) && y > btnSub6.y && y < (btnSub6.y + btnSub6.h * 2)) {
                whichSubject = "선택단어 6";
                btnSub6.btn_press();
                submenuOk = 0;
                subNumber = 4;
                makeQuestion(subNumber);
            }

        if (submenuOk == 1)
            if (x > btnSub7.x && x < (btnSub7.x + btnSub7.w * 2) && y > btnSub7.y && y < (btnSub7.y + btnSub7.h * 2)) {
                whichSubject = "선택단어 7";
                btnSub7.btn_press();
                submenuOk = 0;
                subNumber = 5;
                makeQuestion(subNumber);
            }

        if (submenuOk == 1)
            if (x > btnSub8.x && x < (btnSub8.x + btnSub8.w * 2) && y > btnSub8.y && y < (btnSub8.y + btnSub8.h * 2)) {
                whichSubject = "선택단어 8";
                btnSub8.btn_press();
                submenuOk = 0;
                subNumber = 6;
                makeQuestion(subNumber);
            }

        if (submenuOk == 1)
            if (x > btnSub9.x && x < (btnSub9.x + btnSub9.w * 2) && y > btnSub9.y && y < (btnSub9.y + btnSub9.h * 2)) {
                whichSubject = "선택단어 9";
                btnSub9.btn_press();
                submenuOk = 0;
                subNumber = 7;
                makeQuestion(subNumber);
            }

        if (submenuOk == 1)
            if (x > btnSub10.x && x < (btnSub10.x + btnSub10.w * 2) && y > btnSub10.y && y < (btnSub10.y + btnSub10.h * 2)) {
                whichSubject = "선택단어 10";
                btnSub10.btn_press();
                submenuOk = 0;
                subNumber = 8;
                makeQuestion(subNumber);
            }

        if (submenuOk == 1)
            if (x > btnSub11.x && x < (btnSub11.x + btnSub11.w * 2) && y > btnSub11.y && y < (btnSub11.y + btnSub11.h * 2)) {
                whichSubject = "선택단어 11";
                btnSub11.btn_press();
                submenuOk = 0;
                subNumber = 9;
                makeQuestion(subNumber);
            }


        if (submenuOk == 1)
            if (x > btnSub12.x && x < (btnSub12.x + btnSub12.w * 2) && y > btnSub12.y && y < (btnSub12.y + btnSub12.h * 2)) {
                whichSubject = "선택단어 12";
                btnSub12.btn_press();
                submenuOk = 0;
                subNumber = 10;
                makeQuestion(subNumber);
            }


        if (submenuOk == 1)
            if (x > btnSub13.x && x < (btnSub13.x + btnSub13.w * 2) && y > btnSub13.y && y < (btnSub13.y + btnSub13.h * 2)) {
                whichSubject = "선택단어 13";
                btnSub13.btn_press();
                submenuOk = 0;
                subNumber = 11;
                makeQuestion(subNumber);
            }

        if (submenuOk == 1)
            if (x > btnSub14.x && x < (btnSub14.x + btnSub14.w * 2) && y > btnSub14.y && y < (btnSub14.y + btnSub14.h * 2)) {
                whichSubject = "선택단어 14";
                btnSub14.btn_press();
                submenuOk = 0;
                subNumber = 12;
                makeQuestion(subNumber);
            }



        // left arrow button in circle
        if (dicOk == 1)
            if (x > btnLeftArrow.x && x < (btnLeftArrow.x + btnLeftArrow.w * 2) && y > btnLeftArrow.y && y < (btnLeftArrow.y + btnLeftArrow.h * 2)) {
                btnLeftArrow.btn_press();
                submenuOk = 0;
                dicOk = 1;
                movePosition -= 5;
                if (movePosition < 0) movePosition = 0;
            }

        // right arrow button in circle
        if (dicOk == 1)
            if (x > btnRightArrow.x && x < (btnRightArrow.x + btnRightArrow.w * 2) && y > btnRightArrow.y && y < (btnRightArrow.y + btnRightArrow.h * 2)) {
                btnRightArrow.btn_press();
                submenuOk = 0;
                movePosition += 5;
            }

        if (dicOk == 1 || submenuOk == 1 || submenuOk2 == 1)
            if (x > btnClose.x && x < (btnClose.x + btnClose.w * 2) && y > btnClose.y && y < (btnClose.y + btnClose.h * 2)) {

                try {
                    Thread.sleep(120);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                btnClose.btn_press();
                submenuOk = 0;
                submenuOk2 = 0;
                dicOk = 0;
            }


        if (dicOk == 1) {
            if (x > btnForDictionary[0].x && x < (btnForDictionary[0].x + btnForDictionary[0].w * 2) && y > btnForDictionary[0].y && y < (btnForDictionary[0].y + btnForDictionary[0].h * 2)) {
                if (wordForDelete[0] != null) wordToDelete = wordForDelete[0];
            }
            if (x > btnForDictionary[1].x && x < (btnForDictionary[1].x + btnForDictionary[1].w * 2) && y > btnForDictionary[1].y && y < (btnForDictionary[1].y + btnForDictionary[1].h * 2)) {
                if (wordForDelete[1] != null) wordToDelete = wordForDelete[1];
            }
            if (x > btnForDictionary[2].x && x < (btnForDictionary[2].x + btnForDictionary[2].w * 2) && y > btnForDictionary[2].y && y < (btnForDictionary[2].y + btnForDictionary[2].h * 2))
                if (wordForDelete[2] != null) wordToDelete = wordForDelete[2];
            if (x > btnForDictionary[3].x && x < (btnForDictionary[3].x + btnForDictionary[3].w * 2) && y > btnForDictionary[3].y && y < (btnForDictionary[3].y + btnForDictionary[3].h * 2))
                if (wordForDelete[3] != null) wordToDelete = wordForDelete[3];
            if (x > btnForDictionary[4].x && x < (btnForDictionary[4].x + btnForDictionary[4].w * 2) && y > btnForDictionary[4].y && y < (btnForDictionary[4].y + btnForDictionary[4].h * 2))
                if (wordForDelete[4] != null) wordToDelete = wordForDelete[4];

            SQLiteDatabase db = m_helper.getWritableDatabase();

            String sql = String.format("DELETE FROM englishWordTable WHERE eWord = '%s'", wordToDelete);
            db.execSQL(sql);

            try {
                Thread.sleep(130);
            } catch (InterruptedException e) {

            }
            db.close();
        }


        return false;
    }  //End of onTouchEvent


    public void controlButton() {

        ArrayList<Map<String, String>> metaInfoArray = new ArrayList<Map<String, String>>();
        Map<String, String> metaInfoAndroid = new Hashtable<String, String>(1);
        metaInfoAndroid.put("os", "android");
        metaInfoAndroid.put("devicetype", "phone");
        metaInfoAndroid.put("installurl", "m");
        metaInfoAndroid.put("executeurl", "csc");
        Map<String, String> metaInfoIOS = new Hashtable<String, String>(1);
        metaInfoIOS.put("os", "ios");
        metaInfoIOS.put("devicetype", "phone");
        metaInfoIOS.put("installurl", "your iOS app install url");
        metaInfoIOS.put("executeurl", "");
        metaInfoArray.add(metaInfoAndroid);
        metaInfoArray.add(metaInfoIOS);
        KakaoLink kakaoLink = KakaoLink.getLink(mContext);
        if (!kakaoLink.isAvailableIntent()) {
            //	Toast toast = Toast.makeText(mContext,"īī������ ���������� ��ġ���� �ʾҽ��ϴ�.", 1000);
            //  Toast toast = Toast.makeText(mContext, "", 1000);
            //  toast.show();
            return;
        }
        try {

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, "친구야 영어단어문제를 풀어 보렴~" + '\n');
            intent.putExtra(Intent.EXTRA_TEXT, "(문제)  " + FileSplit0.questionNum[questionNumber][1] + "?" + '\n' +
                    "1번: " + FileSplit0.questionNum[questionNumber][2] + "  " + '\n'
                    + "2번: " + FileSplit0.questionNum[questionNumber][3] + "  " + '\n'
                    + "3번: " + FileSplit0.questionNum[questionNumber][4] + "  " + '\n'
                    + "4번: " + FileSplit0.questionNum[questionNumber][5]);
            intent.setPackage("com.kakao.talk");
            mContext.startActivity(intent);
        } catch (Exception ex) {

        }
        return;
    }


    public void controlButton2() {
        ArrayList<Map<String, String>> metaInfoArray = new ArrayList<Map<String, String>>();
        Map<String, String> metaInfoAndroid = new Hashtable<String, String>(1);
        metaInfoAndroid.put("os", "android");
        metaInfoAndroid.put("devicetype", "phone");
        metaInfoAndroid.put("installurl", "m");
        metaInfoAndroid.put("executeurl", "csc");
        Map<String, String> metaInfoIOS = new Hashtable<String, String>(1);
        metaInfoIOS.put("os", "ios");
        metaInfoIOS.put("devicetype", "phone");
        metaInfoIOS.put("installurl", "your iOS app install url");
        metaInfoIOS.put("executeurl", "");
        metaInfoArray.add(metaInfoAndroid);
        metaInfoArray.add(metaInfoIOS);
        KakaoLink kakaoLink = KakaoLink.getLink(mContext);
        if (!kakaoLink.isAvailableIntent()) {
            //Toast toast = Toast.makeText(mContext,"īī������ ���������� ��ġ���� �ʾҽ��ϴ�.", 1000);
            // Toast toast = Toast.makeText(mContext, "", 1000);
            // toast.show();
            return;
        }
        try {

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, "정답을 보내요" + '\n');
            intent.putExtra(Intent.EXTRA_TEXT, "(정답) " + FileSplit0.questionNum[questionNumber][1] + "?" + "  " +
                    "" + FileSplit0.questionNum[questionNumber][6] + "  " + '\n'
                    + "  " + FileSplit0.questionNum[questionNumber][7] + "  " + '\n'
                    + "  " + FileSplit0.questionNum[questionNumber][8] + "  " + '\n'
            );
            intent.setPackage("com.kakao.talk");
            mContext.startActivity(intent);
        } catch (Exception ex) {

        }
        return;
    }

    //-------------------------------------
    //  onKeyDown
    //-------------------------------------
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        synchronized (mHolder) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_DPAD_LEFT:
                    break;
                case KeyEvent.KEYCODE_DPAD_RIGHT:
                    break;
                case KeyEvent.KEYCODE_DPAD_UP:
                    break;
                default:

            }
        }
        return false;
    }

    class MyDBHelper extends SQLiteOpenHelper {

        public MyDBHelper(Context context, String name, CursorFactory factory, int version) {
            super(context, name, factory, version);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL("CREATE TABLE englishWordTable (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " eWord TEXT, kWord TEXT);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL("DROP TABLE IF EXISTS englishWordTable");
            onCreate(db);
        }
    }          //end of MyDBHelper


} // End of SurfaceView
