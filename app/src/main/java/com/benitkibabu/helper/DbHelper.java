package com.benitkibabu.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.benitkibabu.models.NewsItem;
import com.benitkibabu.models.ReminderItem;
import com.benitkibabu.models.Timetable;
import com.benitkibabu.models.User;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ben on 05/10/2015.
 */
public class DbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "ncigodb";
    private static final int DB_VERSION = 1;

    private static final String TB_REMINDER = "reminder";
    private static final String TB_TIMETABLE = "timetable";
    private static final String TB_USER = "user";
    private static final String TB_NEWS = "news";

    private static final String KEY_ID = "id";

    private static final String KEY_STUDENT_NO = "student_no";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_COURSE_NAME = "c_name";
    private static final String KEY_COURSE_CODE = "c_code";

    private static final String KEY_R_NAME = "name";
    private static final String KEY_R_SHORT_DESC = "short_desc";
    private static final String KEY_DUE_DATE = "due_date";

    private static final String KEY_TITLE = "title";
    private static final String KEY_BODY = "body";
    private static final String KEY_DATE = "date";
    private static final String KEY_TYPE = "type";

    private static final String KEY_ROOM = "room";
    private static final String KEY_START_TIME = "start";
    private static final String KEY_FINISH_TIME = "finish";
    private static final String KEY_STATUS = "status";
    private static final String KEY_DAY = "day";
    private static final String KEY_LECTURER_NAME = "lecturer_name";
    private static final String KEY_MODULE = "module";


    private static final String CREATE_TIMETABLE_TABLE = "CREATE TABLE "
            + TB_TIMETABLE + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_DAY + " TEXT,"
            + KEY_MODULE + " TEXT,"
            + KEY_LECTURER_NAME + " TEXT,"
            + KEY_START_TIME + " TEXT,"
            + KEY_FINISH_TIME + " TEXT,"
            + KEY_ROOM
            + " TEXT )" ;

    private static final String CREATE_USER_TABLE = "CREATE TABLE "
            + TB_USER + "("
            + KEY_STUDENT_NO + " TEXT PRIMARY KEY,"
            + KEY_EMAIL + " TEXT,"
            + KEY_COURSE_NAME + " TEXT,"
            + KEY_COURSE_CODE + " TEXT )" ;

    private static final String CREATE_REMINDER_TABLE = "CREATE TABLE "
            + TB_REMINDER + "("+KEY_ID + " TEXT PRIMARY KEY, "
            + KEY_R_NAME + " TEXT, " + KEY_R_SHORT_DESC + " TEXT,"
            + KEY_DUE_DATE + " DATETIME " + ")";

    private static final String CREATE_NEWS_TABLE = "CREATE TABLE "
            + TB_NEWS + "("
            + KEY_ID + " TEXT PRIMARY KEY, "
            + KEY_TITLE + " TEXT, "
            + KEY_BODY + " TEXT, "
            + KEY_TYPE + " TEXT, "
            + KEY_DATE + " DATE " + ")";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_REMINDER_TABLE);
        db.execSQL(CREATE_NEWS_TABLE);
        db.execSQL(CREATE_TIMETABLE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TB_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TB_REMINDER);
        db.execSQL("DROP TABLE IF EXISTS " + TB_NEWS);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TIMETABLE_TABLE);

        onCreate(db);
    }

    public long setReminder(ReminderItem item){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, item.getId());
        values.put(KEY_R_NAME, item.getModuleName());
        values.put(KEY_R_SHORT_DESC, item.getDescription());
        values.put(KEY_DUE_DATE, item.getDueDate());

        long id =  db.insert(TB_REMINDER,null, values);
        return id;
    }

    public long setTimetable(Timetable t){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put(KEY_DAY, t.getDay());
        v.put(KEY_MODULE, t.getModule());
        v.put(KEY_LECTURER_NAME, t.getLecturer());
        v.put(KEY_START_TIME, t.getStart());
        v.put(KEY_FINISH_TIME, t.getFinish());
        v.put(KEY_ROOM, t.getRoom());

        return db.insert(TB_TIMETABLE, null, v);
    }

    public long addNews(NewsItem item){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, item.getId());
        values.put(KEY_TITLE, item.getTitle());
        values.put(KEY_BODY, item.getBody());
        values.put(KEY_TYPE, item.getNewsType());
        values.put(KEY_DATE, item.getDate());
        return db.insert(TB_NEWS, null, values);
    }

    public long addUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_STUDENT_NO, user.getStudentNo());
        values.put(KEY_EMAIL, user.getEmail());
        values.put(KEY_COURSE_NAME, user.getCourseName());
        values.put(KEY_COURSE_CODE, user.getCourseCode());

        return  db.insert(TB_USER, null, values);
    }

    public User getUser(){
        SQLiteDatabase db = this.getReadableDatabase();
        User u = null;
        Cursor c = db.rawQuery("SELECT * FROM " + TB_USER , null);

        if (c != null) {
            if(c.getCount() > 0) {
                c.moveToFirst();
                u = new User(c.getString(0), c.getString(1), c.getString(2), c.getString(3));
                c.close();
            }
        }

        return u;
    }

    public NewsItem getNews(String id){
        NewsItem item;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TB_NEWS + " WHERE " + KEY_ID + " = " + id, null);

        if(c!=null && c.getCount() > 0 && c.moveToFirst()){
            item = new NewsItem(c.getString(0), c.getString(1), c.getString(2),
                    c.getString(3), c.getString(4));
            c.close();
        }else{
            item = null;
        }
        return item;
    }

    public List<NewsItem> getAllNews(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<NewsItem> items = new ArrayList<>();

        Cursor c = db.rawQuery("SELECT * FROM " + TB_NEWS + " ORDER BY " + KEY_DATE + " ASC", null);
        if(c!= null &&  c.getCount() > 0 && c.moveToFirst()){
            do{
                NewsItem item = new NewsItem(c.getString(0), c.getString(1), c.getString(2),
                        c.getString(3), c.getString(4));

                items.add(item);
            }while (c.moveToNext());

            c.close();
        }
        return items;
    }

    public List<ReminderItem> getReminders(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<ReminderItem> items = new ArrayList<>();

        Cursor c = db.rawQuery("SELECT * FROM " + TB_REMINDER+ " ORDER BY " + KEY_DUE_DATE+ " ASC", null);
        if(c != null &&  c.getCount() > 0 && c.moveToFirst()){
            do{
                ReminderItem item = new ReminderItem(c.getString(0),
                        c.getString(1), c.getString(2), c.getString(3));
                items.add(item);
            }while (c.moveToNext());
            c.close();
        }
        return items;
    }

    public List<Timetable> getTimetable(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Timetable> items = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM " +TB_TIMETABLE, null);
        if(c != null && c.getCount() > 0 && c.moveToFirst()){
            do{
                Timetable t= new Timetable(c.getInt(0), c.getString(1), c.getString(2), c.getString(3)
                        , c.getString(4), c.getString(5), c.getString(6));
                items.add(t);
            }while(c.moveToNext());
        }

        return items;
    }


    public int deleteUser(){
        SQLiteDatabase db = this.getWritableDatabase();
        int c = db.delete(TB_USER, null, null);
        return c;
    }

    public int removeReminder(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        int c = db.delete(TB_REMINDER, KEY_ID + "=?", new String[]{String.valueOf(id)});
        return c;
    }
    public int removeNews(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        int c = db.delete(TB_NEWS, KEY_ID + "=?", new String[]{String.valueOf(id)});
        return c;
    }

    public int removeAllNews(){
        SQLiteDatabase db = this.getWritableDatabase();
        int c = db.delete(TB_NEWS,null, null);
        return c;
    }

    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

}