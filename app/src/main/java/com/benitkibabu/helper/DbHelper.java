package com.benitkibabu.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.benitkibabu.models.ReminderItem;
import com.benitkibabu.models.Student;
import com.benitkibabu.models.Timetable;
import com.benitkibabu.models.UpdateItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ben on 05/10/2015.
 */
public class DbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "ncigodb";
    private static final int DB_VERSION = 5;

    private static final String TB_REMINDER = "reminder";
    private static final String TB_TIMETABLE = "timetable";
    private static final String TB_STUDENT = "student";
    private static final String TB_UPDATE = "ltupdates";
    private static final String TB_SERVICE = "service";
    private static final String TB_APPOINTMENT = "appointment";

    private static final String KEY_ID = "id";

    private static final String KEY_STUDENT_NO = "student_no";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_REGID = "reg_id";
    private static final String KEY_COURSE_NAME = "course";
    private static final String KEY_FIRSTNAME = "first_name";
    private static final String KEY_LASTNAME = "last_name";

    private static final String KEY_R_NAME = "name";
    private static final String KEY_R_SHORT_DESC = "short_desc";
    private static final String KEY_DUE_DATE = "due_date";

    private static final String KEY_TITLE = "title";
    private static final String KEY_BODY = "body";
    private static final String KEY_DATE = "date";
    private static final String KEY_TYPE = "target";

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

    private static final String CREATE_STUDENT_TABLE = "CREATE TABLE "
            + TB_STUDENT + "("
            + KEY_STUDENT_NO + " TEXT PRIMARY KEY,"
            + KEY_EMAIL + " TEXT,"
            + KEY_PASSWORD + " TEXT,"
            + KEY_REGID + " TEXT,"
            + KEY_COURSE_NAME + " TEXT,"
            + KEY_STATUS + " TEXT, "
            + KEY_FIRSTNAME + " TEXT, "
            + KEY_LASTNAME + " TEXT )" ;

    private static final String CREATE_REMINDER_TABLE = "CREATE TABLE "
            + TB_REMINDER + "("+KEY_ID + " TEXT PRIMARY KEY, "
            + KEY_R_NAME + " TEXT, " + KEY_R_SHORT_DESC + " TEXT,"
            + KEY_DUE_DATE + " DATETIME " + ")";

    private static final String CREATE_UPDATE_TABLE = "CREATE TABLE "
            + TB_UPDATE + "("
            + KEY_ID + " TEXT PRIMARY KEY, "
            + KEY_TITLE + " TEXT, "
            + KEY_BODY + " TEXT, "
            + KEY_TYPE + " TEXT, "
            + KEY_DATE + " DATETIME " + ")";

    private static final String CREATE_SERVICE_TABLE = "CREATE TABLE "
            + TB_SERVICE + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_STUDENT_NO + " TEXT, "
            + KEY_TITLE + " TEXT, "
            + KEY_BODY + " TEXT, "
            + KEY_STATUS + " TEXT, "
            + KEY_DATE + " DATE " + ")";

    private static final String CREATE_APPOINTMENT_TABLE = "CREATE TABLE "
            + TB_APPOINTMENT + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_STUDENT_NO + " TEXT, "
            + KEY_TITLE + " TEXT, "
            + KEY_BODY + " TEXT, "
            + KEY_STATUS + " TEXT, "
            + KEY_DATE + " DATE " + ")";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STUDENT_TABLE);
        db.execSQL(CREATE_REMINDER_TABLE);
        db.execSQL(CREATE_UPDATE_TABLE);
        db.execSQL(CREATE_TIMETABLE_TABLE);
        db.execSQL(CREATE_SERVICE_TABLE);
        db.execSQL(CREATE_APPOINTMENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TB_STUDENT);
        db.execSQL("DROP TABLE IF EXISTS " + TB_REMINDER);
        db.execSQL("DROP TABLE IF EXISTS " + TB_UPDATE);
        db.execSQL("DROP TABLE IF EXISTS " + TB_TIMETABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TB_SERVICE);
        db.execSQL("DROP TABLE IF EXISTS " + TB_APPOINTMENT);

        onCreate(db);
    }

    public long setReminder(ReminderItem item){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, item.getId());
        values.put(KEY_R_NAME, item.getModuleName());
        values.put(KEY_R_SHORT_DESC, item.getDescription());
        values.put(KEY_DUE_DATE, item.getDueDate());
        long id = db.insert(TB_REMINDER,null, values);
        closeDB();
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
        long id = db.insert(TB_TIMETABLE, null, v);
        closeDB();
        return id;
    }

    public long addUpdate(UpdateItem item){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, item.getId());
        values.put(KEY_TITLE, item.getTitle());
        values.put(KEY_BODY, item.getBody());
        values.put(KEY_TYPE, item.getTarget());
        values.put(KEY_DATE, item.getDate());
        long id = db.insert(TB_UPDATE, null, values);
        closeDB();
        return id;
    }

    public long addUser(Student student){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_STUDENT_NO, student.getStudentID());
        values.put(KEY_EMAIL, student.getStudentEmail());
        values.put(KEY_PASSWORD, student.getPassword());
        values.put(KEY_REGID, student.getReg_id());
        values.put(KEY_COURSE_NAME, student.getCourse());
        values.put(KEY_STATUS, student.getStatus());
        values.put(KEY_FIRSTNAME, student.getFirstName());
        values.put(KEY_LASTNAME, student.getLastName());
        long id = db.insert(TB_STUDENT, null, values);
        closeDB();
        return  id;
    }

    public int updateUser(Student student){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_FIRSTNAME, student.getFirstName());
        values.put(KEY_LASTNAME, student.getLastName());

        int id = db.update(TB_STUDENT, values, KEY_STUDENT_NO+"=?", new String[]{student.getStudentID()});
        closeDB();
        return  id;
    }

    public Student getUser(){
        SQLiteDatabase db = this.getReadableDatabase();
        Student u = null;
        Cursor c = db.rawQuery("SELECT * FROM " + TB_STUDENT, null);

        if (c != null) {
            if(c.getCount() > 0) {
                c.moveToFirst();
                u = new Student(
                        c.getString(c.getColumnIndex(KEY_STUDENT_NO)),
                        c.getString(c.getColumnIndex(KEY_EMAIL)),
                        c.getString(c.getColumnIndex(KEY_PASSWORD)),
                        c.getString(c.getColumnIndex(KEY_REGID)),
                        c.getString(c.getColumnIndex(KEY_COURSE_NAME)),
                        c.getString(c.getColumnIndex(KEY_STATUS)),
                        c.getString(c.getColumnIndex(KEY_FIRSTNAME)),
                        c.getString(c.getColumnIndex(KEY_LASTNAME)));
                c.close();
            }
        }
        closeDB();
        return u;
    }

    public UpdateItem getUpdate(String id){
        UpdateItem item;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TB_UPDATE + " WHERE " + KEY_ID + " = " + id, null);

        if(c!=null && c.getCount() > 0 && c.moveToFirst()){
            item = new UpdateItem(
                    c.getString(0),
                    c.getString(1),
                    c.getString(2),
                    c.getString(3),
                    c.getString(4));
            c.close();
        }else{
            item = null;
        }
        closeDB();
        return item;
    }

    public List<UpdateItem> getUpdates(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<UpdateItem> items = new ArrayList<>();

        Cursor c = db.rawQuery("SELECT * FROM " + TB_UPDATE + " ORDER BY " + KEY_DATE + " ASC", null);
        if(c!= null &&  c.getCount() > 0 && c.moveToFirst()){
            do{
                UpdateItem item = new UpdateItem(c.getString(0), c.getString(1), c.getString(2),
                        c.getString(3), c.getString(4));

                items.add(item);
            }while (c.moveToNext());

            c.close();
        }
        closeDB();
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
        closeDB();
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
        closeDB();
        return items;
    }


    public int deleteUser(){
        SQLiteDatabase db = this.getWritableDatabase();
        int c = db.delete(TB_STUDENT, null, null);
        closeDB();
        return c;
    }

    public int removeReminder(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        int c = db.delete(TB_REMINDER, KEY_ID + "=?", new String[]{String.valueOf(id)});
        closeDB();
        return c;
    }
    public int removeUpdate(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        int c = db.delete(TB_UPDATE, KEY_ID + "=?", new String[]{String.valueOf(id)});
        closeDB();
        return c;
    }

    public int removeUpdates(){
        SQLiteDatabase db = this.getWritableDatabase();
        int c = db.delete(TB_UPDATE,null, null);
        closeDB();
        return c;
    }

    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

}
