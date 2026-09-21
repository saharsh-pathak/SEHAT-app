package com.example.sehat.data.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.sehat.data.entity.Patient;
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
public final class PatientDao_Impl implements PatientDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Patient> __insertionAdapterOfPatient;

  public PatientDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPatient = new EntityInsertionAdapter<Patient>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `patients` (`abhaId`,`aadhaarRef`,`name`,`age`,`gender`,`village`,`mobile`,`createdAt`) VALUES (?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Patient entity) {
        statement.bindString(1, entity.getAbhaId());
        if (entity.getAadhaarRef() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getAadhaarRef());
        }
        statement.bindString(3, entity.getName());
        statement.bindLong(4, entity.getAge());
        statement.bindString(5, entity.getGender());
        statement.bindString(6, entity.getVillage());
        if (entity.getMobile() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getMobile());
        }
        statement.bindLong(8, entity.getCreatedAt());
      }
    };
  }

  @Override
  public Object insert(final Patient patient, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfPatient.insert(patient);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertAll(final List<Patient> patients,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfPatient.insert(patients);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Patient>> getAllPatients() {
    final String _sql = "SELECT * FROM patients ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"patients"}, new Callable<List<Patient>>() {
      @Override
      @NonNull
      public List<Patient> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfAbhaId = CursorUtil.getColumnIndexOrThrow(_cursor, "abhaId");
          final int _cursorIndexOfAadhaarRef = CursorUtil.getColumnIndexOrThrow(_cursor, "aadhaarRef");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfAge = CursorUtil.getColumnIndexOrThrow(_cursor, "age");
          final int _cursorIndexOfGender = CursorUtil.getColumnIndexOrThrow(_cursor, "gender");
          final int _cursorIndexOfVillage = CursorUtil.getColumnIndexOrThrow(_cursor, "village");
          final int _cursorIndexOfMobile = CursorUtil.getColumnIndexOrThrow(_cursor, "mobile");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<Patient> _result = new ArrayList<Patient>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Patient _item;
            final String _tmpAbhaId;
            _tmpAbhaId = _cursor.getString(_cursorIndexOfAbhaId);
            final String _tmpAadhaarRef;
            if (_cursor.isNull(_cursorIndexOfAadhaarRef)) {
              _tmpAadhaarRef = null;
            } else {
              _tmpAadhaarRef = _cursor.getString(_cursorIndexOfAadhaarRef);
            }
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final int _tmpAge;
            _tmpAge = _cursor.getInt(_cursorIndexOfAge);
            final String _tmpGender;
            _tmpGender = _cursor.getString(_cursorIndexOfGender);
            final String _tmpVillage;
            _tmpVillage = _cursor.getString(_cursorIndexOfVillage);
            final String _tmpMobile;
            if (_cursor.isNull(_cursorIndexOfMobile)) {
              _tmpMobile = null;
            } else {
              _tmpMobile = _cursor.getString(_cursorIndexOfMobile);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new Patient(_tmpAbhaId,_tmpAadhaarRef,_tmpName,_tmpAge,_tmpGender,_tmpVillage,_tmpMobile,_tmpCreatedAt);
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
  public Flow<List<Patient>> searchPatients(final String query) {
    final String _sql = "SELECT * FROM patients WHERE abhaId = ? OR name LIKE '%' || ? || '%' OR mobile LIKE '%' || ? || '%'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    _statement.bindString(_argIndex, query);
    _argIndex = 2;
    _statement.bindString(_argIndex, query);
    _argIndex = 3;
    _statement.bindString(_argIndex, query);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"patients"}, new Callable<List<Patient>>() {
      @Override
      @NonNull
      public List<Patient> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfAbhaId = CursorUtil.getColumnIndexOrThrow(_cursor, "abhaId");
          final int _cursorIndexOfAadhaarRef = CursorUtil.getColumnIndexOrThrow(_cursor, "aadhaarRef");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfAge = CursorUtil.getColumnIndexOrThrow(_cursor, "age");
          final int _cursorIndexOfGender = CursorUtil.getColumnIndexOrThrow(_cursor, "gender");
          final int _cursorIndexOfVillage = CursorUtil.getColumnIndexOrThrow(_cursor, "village");
          final int _cursorIndexOfMobile = CursorUtil.getColumnIndexOrThrow(_cursor, "mobile");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<Patient> _result = new ArrayList<Patient>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Patient _item;
            final String _tmpAbhaId;
            _tmpAbhaId = _cursor.getString(_cursorIndexOfAbhaId);
            final String _tmpAadhaarRef;
            if (_cursor.isNull(_cursorIndexOfAadhaarRef)) {
              _tmpAadhaarRef = null;
            } else {
              _tmpAadhaarRef = _cursor.getString(_cursorIndexOfAadhaarRef);
            }
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final int _tmpAge;
            _tmpAge = _cursor.getInt(_cursorIndexOfAge);
            final String _tmpGender;
            _tmpGender = _cursor.getString(_cursorIndexOfGender);
            final String _tmpVillage;
            _tmpVillage = _cursor.getString(_cursorIndexOfVillage);
            final String _tmpMobile;
            if (_cursor.isNull(_cursorIndexOfMobile)) {
              _tmpMobile = null;
            } else {
              _tmpMobile = _cursor.getString(_cursorIndexOfMobile);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new Patient(_tmpAbhaId,_tmpAadhaarRef,_tmpName,_tmpAge,_tmpGender,_tmpVillage,_tmpMobile,_tmpCreatedAt);
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
  public Object getByAbhaId(final String abhaId, final Continuation<? super Patient> $completion) {
    final String _sql = "SELECT * FROM patients WHERE abhaId = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, abhaId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Patient>() {
      @Override
      @Nullable
      public Patient call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfAbhaId = CursorUtil.getColumnIndexOrThrow(_cursor, "abhaId");
          final int _cursorIndexOfAadhaarRef = CursorUtil.getColumnIndexOrThrow(_cursor, "aadhaarRef");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfAge = CursorUtil.getColumnIndexOrThrow(_cursor, "age");
          final int _cursorIndexOfGender = CursorUtil.getColumnIndexOrThrow(_cursor, "gender");
          final int _cursorIndexOfVillage = CursorUtil.getColumnIndexOrThrow(_cursor, "village");
          final int _cursorIndexOfMobile = CursorUtil.getColumnIndexOrThrow(_cursor, "mobile");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final Patient _result;
          if (_cursor.moveToFirst()) {
            final String _tmpAbhaId;
            _tmpAbhaId = _cursor.getString(_cursorIndexOfAbhaId);
            final String _tmpAadhaarRef;
            if (_cursor.isNull(_cursorIndexOfAadhaarRef)) {
              _tmpAadhaarRef = null;
            } else {
              _tmpAadhaarRef = _cursor.getString(_cursorIndexOfAadhaarRef);
            }
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final int _tmpAge;
            _tmpAge = _cursor.getInt(_cursorIndexOfAge);
            final String _tmpGender;
            _tmpGender = _cursor.getString(_cursorIndexOfGender);
            final String _tmpVillage;
            _tmpVillage = _cursor.getString(_cursorIndexOfVillage);
            final String _tmpMobile;
            if (_cursor.isNull(_cursorIndexOfMobile)) {
              _tmpMobile = null;
            } else {
              _tmpMobile = _cursor.getString(_cursorIndexOfMobile);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result = new Patient(_tmpAbhaId,_tmpAadhaarRef,_tmpName,_tmpAge,_tmpGender,_tmpVillage,_tmpMobile,_tmpCreatedAt);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
