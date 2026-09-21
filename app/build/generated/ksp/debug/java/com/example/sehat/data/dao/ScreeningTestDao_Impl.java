package com.example.sehat.data.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.sehat.data.entity.ScreeningTest;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class ScreeningTestDao_Impl implements ScreeningTestDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ScreeningTest> __insertionAdapterOfScreeningTest;

  public ScreeningTestDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfScreeningTest = new EntityInsertionAdapter<ScreeningTest>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `screening_tests` (`id`,`episodeId`,`testName`,`result`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ScreeningTest entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getEpisodeId());
        statement.bindString(3, entity.getTestName());
        statement.bindString(4, entity.getResult());
      }
    };
  }

  @Override
  public Object insertAll(final List<ScreeningTest> tests,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfScreeningTest.insert(tests);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<ScreeningTest>> getForEpisode(final long episodeId) {
    final String _sql = "SELECT * FROM screening_tests WHERE episodeId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, episodeId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"screening_tests"}, new Callable<List<ScreeningTest>>() {
      @Override
      @NonNull
      public List<ScreeningTest> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfEpisodeId = CursorUtil.getColumnIndexOrThrow(_cursor, "episodeId");
          final int _cursorIndexOfTestName = CursorUtil.getColumnIndexOrThrow(_cursor, "testName");
          final int _cursorIndexOfResult = CursorUtil.getColumnIndexOrThrow(_cursor, "result");
          final List<ScreeningTest> _result = new ArrayList<ScreeningTest>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ScreeningTest _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpEpisodeId;
            _tmpEpisodeId = _cursor.getLong(_cursorIndexOfEpisodeId);
            final String _tmpTestName;
            _tmpTestName = _cursor.getString(_cursorIndexOfTestName);
            final String _tmpResult;
            _tmpResult = _cursor.getString(_cursorIndexOfResult);
            _item = new ScreeningTest(_tmpId,_tmpEpisodeId,_tmpTestName,_tmpResult);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
