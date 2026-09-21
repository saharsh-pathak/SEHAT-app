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
import com.example.sehat.data.entity.Medicine;
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
public final class MedicineDao_Impl implements MedicineDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Medicine> __insertionAdapterOfMedicine;

  public MedicineDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMedicine = new EntityInsertionAdapter<Medicine>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `medicines` (`id`,`name`,`form`,`facility`,`stockCount`,`status`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Medicine entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getForm());
        statement.bindString(4, entity.getFacility());
        statement.bindLong(5, entity.getStockCount());
        statement.bindString(6, entity.getStatus());
      }
    };
  }

  @Override
  public Object insertAll(final List<Medicine> medicines,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfMedicine.insert(medicines);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Medicine>> getAllMedicines() {
    final String _sql = "SELECT * FROM medicines ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"medicines"}, new Callable<List<Medicine>>() {
      @Override
      @NonNull
      public List<Medicine> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfForm = CursorUtil.getColumnIndexOrThrow(_cursor, "form");
          final int _cursorIndexOfFacility = CursorUtil.getColumnIndexOrThrow(_cursor, "facility");
          final int _cursorIndexOfStockCount = CursorUtil.getColumnIndexOrThrow(_cursor, "stockCount");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final List<Medicine> _result = new ArrayList<Medicine>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Medicine _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpForm;
            _tmpForm = _cursor.getString(_cursorIndexOfForm);
            final String _tmpFacility;
            _tmpFacility = _cursor.getString(_cursorIndexOfFacility);
            final int _tmpStockCount;
            _tmpStockCount = _cursor.getInt(_cursorIndexOfStockCount);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            _item = new Medicine(_tmpId,_tmpName,_tmpForm,_tmpFacility,_tmpStockCount,_tmpStatus);
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

  @Override
  public Flow<List<Medicine>> searchMedicines(final String query) {
    final String _sql = "SELECT * FROM medicines WHERE name LIKE '%' || ? || '%' OR facility LIKE '%' || ? || '%'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, query);
    _argIndex = 2;
    _statement.bindString(_argIndex, query);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"medicines"}, new Callable<List<Medicine>>() {
      @Override
      @NonNull
      public List<Medicine> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfForm = CursorUtil.getColumnIndexOrThrow(_cursor, "form");
          final int _cursorIndexOfFacility = CursorUtil.getColumnIndexOrThrow(_cursor, "facility");
          final int _cursorIndexOfStockCount = CursorUtil.getColumnIndexOrThrow(_cursor, "stockCount");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final List<Medicine> _result = new ArrayList<Medicine>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Medicine _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpForm;
            _tmpForm = _cursor.getString(_cursorIndexOfForm);
            final String _tmpFacility;
            _tmpFacility = _cursor.getString(_cursorIndexOfFacility);
            final int _tmpStockCount;
            _tmpStockCount = _cursor.getInt(_cursorIndexOfStockCount);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            _item = new Medicine(_tmpId,_tmpName,_tmpForm,_tmpFacility,_tmpStockCount,_tmpStatus);
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

  @Override
  public Flow<List<Medicine>> filterByStatus(final String status) {
    final String _sql = "SELECT * FROM medicines WHERE status = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, status);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"medicines"}, new Callable<List<Medicine>>() {
      @Override
      @NonNull
      public List<Medicine> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfForm = CursorUtil.getColumnIndexOrThrow(_cursor, "form");
          final int _cursorIndexOfFacility = CursorUtil.getColumnIndexOrThrow(_cursor, "facility");
          final int _cursorIndexOfStockCount = CursorUtil.getColumnIndexOrThrow(_cursor, "stockCount");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final List<Medicine> _result = new ArrayList<Medicine>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Medicine _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpForm;
            _tmpForm = _cursor.getString(_cursorIndexOfForm);
            final String _tmpFacility;
            _tmpFacility = _cursor.getString(_cursorIndexOfFacility);
            final int _tmpStockCount;
            _tmpStockCount = _cursor.getInt(_cursorIndexOfStockCount);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            _item = new Medicine(_tmpId,_tmpName,_tmpForm,_tmpFacility,_tmpStockCount,_tmpStatus);
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
