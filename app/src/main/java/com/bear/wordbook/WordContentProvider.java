package com.bear.wordbook;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.bear.wordbook.model.Word;

import org.litepal.LitePal;

import static org.litepal.LitePalBase.TAG;

public class WordContentProvider extends ContentProvider {

    private static final int USER_DIR = 0;
    private static final int USER_ITEM = 1;

    public static final String AUTHORITY = "com.bear.wordbook.provider";

    private static UriMatcher uriMatcher;

    static {
        // 实例化UriMatcher对象
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "Word", USER_DIR);
        uriMatcher.addURI(AUTHORITY, "Word/#", USER_ITEM);
    }

    public WordContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        int deleteInt = 0;
        switch (uriMatcher.match(uri)){
            case USER_DIR:
                LitePal.deleteAll(Word.class, "english = ?", selectionArgs[0]);
                break;
            case USER_ITEM:
                String deleteId = uri.getPathSegments().get(1);
                deleteInt = LitePal.delete(Word.class, Long.parseLong(deleteId));
                break;
            default:
                break;
        }
        return deleteInt;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        switch (uriMatcher.match(uri)){
            case USER_DIR:
                return "vnd.android.cursor.dir/vnd.com.bear.wordbook.provider.Word";
            case USER_ITEM:
                return "vnd.android.cursor.item/vnd.com.bear.wordbook.provider.Word";
            default:
                break;
        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        switch (uriMatcher.match(uri)){
            case USER_DIR:
            case USER_ITEM:
                String en = values.getAsString("english");
                if (!LitePal.isExist(Word.class, "english = ?", en)){
                    Word word = new Word();
                    word.setEnglish(en);
                    word.setChinese(values.getAsString("chinese"));
                    word.setEnglishExample(values.getAsString("enExample"));
                    word.setChineseExample(values.getAsString("chExample"));
                    word.save();
                }
                break;
            default:
                break;
        }
        return null;
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        LitePal.getDatabase();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        Cursor cursor = null;
        switch (uriMatcher.match(uri)){
            case USER_DIR:
                cursor = LitePal.findBySQL(selectionArgs[0], selectionArgs[1]);
                break;
            case USER_ITEM:
                String queryId = uri.getPathSegments().get(1);
                cursor = (Cursor) LitePal.find(Word.class, Long.parseLong(queryId));
                break;
            default:
                break;
        }
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        int updateRow = 0;
        switch (uriMatcher.match(uri)){
            case USER_DIR:
                updateRow = LitePal.updateAll(Word.class, values, "english = ?", selectionArgs[0]);
                break;
            case USER_ITEM:
                String updateId = uri.getPathSegments().get(1);
                updateRow = LitePal.updateAll(Word.class, values, "id = ?", updateId);
                break;
            default:
                break;
        }
        return updateRow;
    }
}
