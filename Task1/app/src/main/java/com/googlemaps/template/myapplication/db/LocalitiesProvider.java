package com.googlemaps.template.myapplication.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by Алмаз on 22.09.2015.
 */
public class LocalitiesProvider extends ContentProvider {
    final String LOG_TAG = "myLogs";
    static final String DB_NAME = "localitiesBD";
    static final int DB_VERSION = 1;
    static final String LOCALITY_TABLE = "localities";
    static final String LOCALITY_ID = "_id";
    static final String LOCALITY_NAME = "name";
    static final String LOCALITY_LAT = "lat";
    static final String LOCALITY_LNG = "lng";
    static final String LOCALITY_INFO = "info";
    static final String DB_CREATE = "create table " + LOCALITY_TABLE + "("
            + LOCALITY_ID + " integer primary key autoincrement, "
            + LOCALITY_NAME + " text, " + LOCALITY_LAT + " double, "+  LOCALITY_LNG + " double, "+LOCALITY_INFO + " text" + ");";
    static final String AUTHORITY = "com.googlemaps.template.myapplication.localitiesprovider";
    static final String LOCALITY_PATH = "localities";
    public static final Uri LOCALITY_CONTENT_URI = Uri.parse("content://"
            + AUTHORITY + "/" + LOCALITY_PATH);
    static final String LOCALITY_CONTENT_TYPE = "vnd.android.cursor.dir/vnd."
            + AUTHORITY + "." + LOCALITY_PATH;
    static final String LOCALITY_CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd."
            + AUTHORITY + "." + LOCALITY_PATH;
    static final int URI_LOCALITYS = 1;
    static final int URI_LOCALITYS_ID = 2;
    private static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, LOCALITY_PATH, URI_LOCALITYS);
        uriMatcher.addURI(AUTHORITY, LOCALITY_PATH + "/#", URI_LOCALITYS_ID);
    }
    DBHelper dbHelper;
    SQLiteDatabase db;
    public boolean onCreate() {
        Log.d(LOG_TAG, "onCreate");
        dbHelper = new DBHelper(getContext());
        return true;
    }
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Log.d(LOG_TAG, "query, " + uri.toString());
        switch (uriMatcher.match(uri)) {
            case URI_LOCALITYS:
                Log.d(LOG_TAG, "URI_LOCALITYS");
                if (TextUtils.isEmpty(sortOrder)) {
                    sortOrder = LOCALITY_NAME + " ASC";
                }
                break;
            case URI_LOCALITYS_ID:
                String id = uri.getLastPathSegment();
                Log.d(LOG_TAG, "URI_LOCALITYS_ID, " + id);
                if (TextUtils.isEmpty(selection)) {
                    selection = LOCALITY_ID + " = " + id;
                } else {
                    selection = selection + " AND " + LOCALITY_ID + " = " + id;
                }
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(LOCALITY_TABLE, projection, selection,
                selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(),
                LOCALITY_CONTENT_URI);
        return cursor;
    }

    public Uri insert(Uri uri, ContentValues values) {
        Log.d(LOG_TAG, "insert, " + uri.toString());
        if (uriMatcher.match(uri) != URI_LOCALITYS)
            throw new IllegalArgumentException("Wrong URI: " + uri);

        db = dbHelper.getWritableDatabase();
        long rowID = db.insert(LOCALITY_TABLE, null, values);
        Uri resultUri = ContentUris.withAppendedId(LOCALITY_CONTENT_URI, rowID);
        getContext().getContentResolver().notifyChange(resultUri, null);
        return resultUri;
    }

    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.d(LOG_TAG, "delete, " + uri.toString());
        switch (uriMatcher.match(uri)) {
            case URI_LOCALITYS:
                Log.d(LOG_TAG, "URI_LOCALITYS");
                break;
            case URI_LOCALITYS_ID:
                String id = uri.getLastPathSegment();
                Log.d(LOG_TAG, "URI_LOCALITYS_ID, " + id);
                if (TextUtils.isEmpty(selection)) {
                    selection = LOCALITY_ID + " = " + id;
                } else {
                    selection = selection + " AND " + LOCALITY_ID + " = " + id;
                }
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        db = dbHelper.getWritableDatabase();
        int cnt = db.delete(LOCALITY_TABLE, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return cnt;
    }

    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        Log.d(LOG_TAG, "update, " + uri.toString());
        switch (uriMatcher.match(uri)) {
            case URI_LOCALITYS:
                Log.d(LOG_TAG, "URI_LOCALITYS");

                break;
            case URI_LOCALITYS_ID:
                String id = uri.getLastPathSegment();
                Log.d(LOG_TAG, "URI_LOCALITYS_ID, " + id);
                if (TextUtils.isEmpty(selection)) {
                    selection = LOCALITY_ID + " = " + id;
                } else {
                    selection = selection + " AND " + LOCALITY_ID + " = " + id;
                }
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        db = dbHelper.getWritableDatabase();
        int cnt = db.update(LOCALITY_TABLE, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return cnt;
    }

    public String getType(Uri uri) {
        Log.d(LOG_TAG, "getType, " + uri.toString());
        switch (uriMatcher.match(uri)) {
            case URI_LOCALITYS:
                return LOCALITY_CONTENT_TYPE;
            case URI_LOCALITYS_ID:
                return LOCALITY_CONTENT_ITEM_TYPE;
        }
        return null;
    }

    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_CREATE);
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}