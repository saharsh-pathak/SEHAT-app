package com.example.sehat.data.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.sehat.data.entity.Vitals;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class VitalsDao_Impl implements VitalsDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Vitals> __insertionAdapterOfVitals;

  public VitalsDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfVitals = new EntityInsertionAdapter<Vitals>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `vitals` (`id`,`episodeId`,`bloodPressure`,`heartRate`,`spo2`,`temperature`,`bloodGlucose`,`hemoglobin`,`weight`,`height`,`clinicalNotes`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Vitals entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getEpisodeId());
        statement.bindString(3, entity.getBloodPressure());
        statement.bindLong(4, entity.getHeartRate());
        statement.bindLong(5, entity.getSpo2());
        statement.bindDouble(6, entity.getTemperature());
        statement.bindLong(7, entity.getBloodGlucose());
        statement.bindDouble(8, entity.getHemoglobin());
        statement.bindDouble(9, entity.getWeight());
        statement.bindDouble(10, entity.getHeight());
        statement.bindString(11, entity.getClinicalNotes());
      }
    };
  }

  @Override
  public Object insert(final Vitals vitals, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfVitals.insertAndReturnId(vitals);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<Vitals> getForEpisode(final long episodeId) {
    final String _sql = "SELECT * FROM vitals WHERE episodeId = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, episodeId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"vitals"}, new Callable<Vitals>() {
      @Override
      @Nullable
      public Vitals call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfEpisodeId = CursorUtil.getColumnIndexOrThrow(_cursor, "episodeId");
          final int _cursorIndexOfBloodPressure = CursorUtil.getColumnIndexOrThrow(_cursor, "bloodPressure");
          final int _cursorIndexOfHeartRate = CursorUtil.getColumnIndexOrThrow(_cursor, "heartRate");
          final int _cursorIndexOfSpo2 = CursorUtil.getColumnIndexOrThrow(_cursor, "spo2");
          final int _cursorIndexOfTemperature = CursorUtil.getColumnIndexOrThrow(_cursor, "temperature");
          final int _cursorIndexOfBloodGlucose = CursorUtil.getColumnIndexOrThrow(_cursor, "bloodGlucose");
          final int _cursorIndexOfHemoglobin = CursorUtil.getColumnIndexOrThrow(_cursor, "hemoglobin");
          final int _cursorIndexOfWeight = CursorUtil.getColumnIndexOrThrow(_cursor, "weight");
          final int _cursorIndexOfHeight = CursorUtil.getColumnIndexOrThrow(_cursor, "height");
          final int _cursorIndexOfClinicalNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "clinicalNotes");
          final Vitals _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpEpisodeId;
            _tmpEpisodeId = _cursor.getLong(_cursorIndexOfEpisodeId);
            final String _tmpBloodPressure;
            _tmpBloodPressure = _cursor.getString(_cursorIndexOfBloodPressure);
            final int _tmpHeartRate;
            _tmpHeartRate = _cursor.getInt(_cursorIndexOfHeartRate);
            final int _tmpSpo2;
            _tmpSpo2 = _cursor.getInt(_cursorIndexOfSpo2);
            final float _tmpTemperature;
            _tmpTemperature = _cursor.getFloat(_cursorIndexOfTemperature);
            final int _tmpBloodGlucose;
            _tmpBloodGlucose = _cursor.getInt(_cursorIndexOfBloodGlucose);
            final float _tmpHemoglobin;
            _tmpHemoglobin = _cursor.getFloat(_cursorIndexOfHemoglobin);
            final float _tmpWeight;
            _tmpWeight = _cursor.getFloat(_cursorIndexOfWeight);
            final float _tmpHeight;
            _tmpHeight = _cursor.getFloat(_cursorIndexOfHeight);
            final String _tmpClinicalNotes;
            _tmpClinicalNotes = _cursor.getString(_cursorIndexOfClinicalNotes);
            _result = new Vitals(_tmpId,_tmpEpisodeId,_tmpBloodPressure,_tmpHeartRate,_tmpSpo2,_tmpTemperature,_tmpBloodGlucose,_tmpHemoglobin,_tmpWeight,_tmpHeight,_tmpClinicalNotes);
          } else {
            _result = null;
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
