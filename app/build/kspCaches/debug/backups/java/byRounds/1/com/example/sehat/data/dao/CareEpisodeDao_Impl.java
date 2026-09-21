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
import com.example.sehat.data.entity.CareEpisode;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class CareEpisodeDao_Impl implements CareEpisodeDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<CareEpisode> __insertionAdapterOfCareEpisode;

  public CareEpisodeDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCareEpisode = new EntityInsertionAdapter<CareEpisode>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `care_episodes` (`episodeId`,`patientAbhaId`,`facilityName`,`status`,`createdAt`,`closedAt`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final CareEpisode entity) {
        statement.bindLong(1, entity.getEpisodeId());
        statement.bindString(2, entity.getPatientAbhaId());
        statement.bindString(3, entity.getFacilityName());
        statement.bindString(4, entity.getStatus());
        statement.bindLong(5, entity.getCreatedAt());
        if (entity.getClosedAt() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getClosedAt());
        }
      }
    };
  }

  @Override
  public Object insert(final CareEpisode episode, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfCareEpisode.insertAndReturnId(episode);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<CareEpisode>> getEpisodesForPatient(final String abhaId) {
    final String _sql = "SELECT * FROM care_episodes WHERE patientAbhaId = ? ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, abhaId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"care_episodes"}, new Callable<List<CareEpisode>>() {
      @Override
      @NonNull
      public List<CareEpisode> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfEpisodeId = CursorUtil.getColumnIndexOrThrow(_cursor, "episodeId");
          final int _cursorIndexOfPatientAbhaId = CursorUtil.getColumnIndexOrThrow(_cursor, "patientAbhaId");
          final int _cursorIndexOfFacilityName = CursorUtil.getColumnIndexOrThrow(_cursor, "facilityName");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfClosedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "closedAt");
          final List<CareEpisode> _result = new ArrayList<CareEpisode>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CareEpisode _item;
            final long _tmpEpisodeId;
            _tmpEpisodeId = _cursor.getLong(_cursorIndexOfEpisodeId);
            final String _tmpPatientAbhaId;
            _tmpPatientAbhaId = _cursor.getString(_cursorIndexOfPatientAbhaId);
            final String _tmpFacilityName;
            _tmpFacilityName = _cursor.getString(_cursorIndexOfFacilityName);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final Long _tmpClosedAt;
            if (_cursor.isNull(_cursorIndexOfClosedAt)) {
              _tmpClosedAt = null;
            } else {
              _tmpClosedAt = _cursor.getLong(_cursorIndexOfClosedAt);
            }
            _item = new CareEpisode(_tmpEpisodeId,_tmpPatientAbhaId,_tmpFacilityName,_tmpStatus,_tmpCreatedAt,_tmpClosedAt);
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
  public Object getById(final long episodeId, final Continuation<? super CareEpisode> $completion) {
    final String _sql = "SELECT * FROM care_episodes WHERE episodeId = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, episodeId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<CareEpisode>() {
      @Override
      @Nullable
      public CareEpisode call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfEpisodeId = CursorUtil.getColumnIndexOrThrow(_cursor, "episodeId");
          final int _cursorIndexOfPatientAbhaId = CursorUtil.getColumnIndexOrThrow(_cursor, "patientAbhaId");
          final int _cursorIndexOfFacilityName = CursorUtil.getColumnIndexOrThrow(_cursor, "facilityName");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfClosedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "closedAt");
          final CareEpisode _result;
          if (_cursor.moveToFirst()) {
            final long _tmpEpisodeId;
            _tmpEpisodeId = _cursor.getLong(_cursorIndexOfEpisodeId);
            final String _tmpPatientAbhaId;
            _tmpPatientAbhaId = _cursor.getString(_cursorIndexOfPatientAbhaId);
            final String _tmpFacilityName;
            _tmpFacilityName = _cursor.getString(_cursorIndexOfFacilityName);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final Long _tmpClosedAt;
            if (_cursor.isNull(_cursorIndexOfClosedAt)) {
              _tmpClosedAt = null;
            } else {
              _tmpClosedAt = _cursor.getLong(_cursorIndexOfClosedAt);
            }
            _result = new CareEpisode(_tmpEpisodeId,_tmpPatientAbhaId,_tmpFacilityName,_tmpStatus,_tmpCreatedAt,_tmpClosedAt);
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

  @Override
  public Flow<List<CareEpisode>> getOpenEpisodes() {
    final String _sql = "SELECT * FROM care_episodes WHERE status = 'OPEN'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"care_episodes"}, new Callable<List<CareEpisode>>() {
      @Override
      @NonNull
      public List<CareEpisode> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfEpisodeId = CursorUtil.getColumnIndexOrThrow(_cursor, "episodeId");
          final int _cursorIndexOfPatientAbhaId = CursorUtil.getColumnIndexOrThrow(_cursor, "patientAbhaId");
          final int _cursorIndexOfFacilityName = CursorUtil.getColumnIndexOrThrow(_cursor, "facilityName");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfClosedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "closedAt");
          final List<CareEpisode> _result = new ArrayList<CareEpisode>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CareEpisode _item;
            final long _tmpEpisodeId;
            _tmpEpisodeId = _cursor.getLong(_cursorIndexOfEpisodeId);
            final String _tmpPatientAbhaId;
            _tmpPatientAbhaId = _cursor.getString(_cursorIndexOfPatientAbhaId);
            final String _tmpFacilityName;
            _tmpFacilityName = _cursor.getString(_cursorIndexOfFacilityName);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final Long _tmpClosedAt;
            if (_cursor.isNull(_cursorIndexOfClosedAt)) {
              _tmpClosedAt = null;
            } else {
              _tmpClosedAt = _cursor.getLong(_cursorIndexOfClosedAt);
            }
            _item = new CareEpisode(_tmpEpisodeId,_tmpPatientAbhaId,_tmpFacilityName,_tmpStatus,_tmpCreatedAt,_tmpClosedAt);
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
