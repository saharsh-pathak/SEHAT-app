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
import com.example.sehat.data.entity.Referral;
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
public final class ReferralDao_Impl implements ReferralDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Referral> __insertionAdapterOfReferral;

  public ReferralDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfReferral = new EntityInsertionAdapter<Referral>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `referrals` (`referralId`,`episodeId`,`patientAbhaId`,`destinationFacility`,`priority`,`notes`,`createdAt`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Referral entity) {
        statement.bindLong(1, entity.getReferralId());
        statement.bindLong(2, entity.getEpisodeId());
        statement.bindString(3, entity.getPatientAbhaId());
        statement.bindString(4, entity.getDestinationFacility());
        statement.bindString(5, entity.getPriority());
        statement.bindString(6, entity.getNotes());
        statement.bindLong(7, entity.getCreatedAt());
      }
    };
  }

  @Override
  public Object insert(final Referral referral, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfReferral.insertAndReturnId(referral);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<Referral> getForEpisode(final long episodeId) {
    final String _sql = "SELECT * FROM referrals WHERE episodeId = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, episodeId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"referrals"}, new Callable<Referral>() {
      @Override
      @Nullable
      public Referral call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfReferralId = CursorUtil.getColumnIndexOrThrow(_cursor, "referralId");
          final int _cursorIndexOfEpisodeId = CursorUtil.getColumnIndexOrThrow(_cursor, "episodeId");
          final int _cursorIndexOfPatientAbhaId = CursorUtil.getColumnIndexOrThrow(_cursor, "patientAbhaId");
          final int _cursorIndexOfDestinationFacility = CursorUtil.getColumnIndexOrThrow(_cursor, "destinationFacility");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final Referral _result;
          if (_cursor.moveToFirst()) {
            final long _tmpReferralId;
            _tmpReferralId = _cursor.getLong(_cursorIndexOfReferralId);
            final long _tmpEpisodeId;
            _tmpEpisodeId = _cursor.getLong(_cursorIndexOfEpisodeId);
            final String _tmpPatientAbhaId;
            _tmpPatientAbhaId = _cursor.getString(_cursorIndexOfPatientAbhaId);
            final String _tmpDestinationFacility;
            _tmpDestinationFacility = _cursor.getString(_cursorIndexOfDestinationFacility);
            final String _tmpPriority;
            _tmpPriority = _cursor.getString(_cursorIndexOfPriority);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result = new Referral(_tmpReferralId,_tmpEpisodeId,_tmpPatientAbhaId,_tmpDestinationFacility,_tmpPriority,_tmpNotes,_tmpCreatedAt);
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

  @Override
  public Object getById(final long referralId, final Continuation<? super Referral> $completion) {
    final String _sql = "SELECT * FROM referrals WHERE referralId = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, referralId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Referral>() {
      @Override
      @Nullable
      public Referral call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfReferralId = CursorUtil.getColumnIndexOrThrow(_cursor, "referralId");
          final int _cursorIndexOfEpisodeId = CursorUtil.getColumnIndexOrThrow(_cursor, "episodeId");
          final int _cursorIndexOfPatientAbhaId = CursorUtil.getColumnIndexOrThrow(_cursor, "patientAbhaId");
          final int _cursorIndexOfDestinationFacility = CursorUtil.getColumnIndexOrThrow(_cursor, "destinationFacility");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final Referral _result;
          if (_cursor.moveToFirst()) {
            final long _tmpReferralId;
            _tmpReferralId = _cursor.getLong(_cursorIndexOfReferralId);
            final long _tmpEpisodeId;
            _tmpEpisodeId = _cursor.getLong(_cursorIndexOfEpisodeId);
            final String _tmpPatientAbhaId;
            _tmpPatientAbhaId = _cursor.getString(_cursorIndexOfPatientAbhaId);
            final String _tmpDestinationFacility;
            _tmpDestinationFacility = _cursor.getString(_cursorIndexOfDestinationFacility);
            final String _tmpPriority;
            _tmpPriority = _cursor.getString(_cursorIndexOfPriority);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result = new Referral(_tmpReferralId,_tmpEpisodeId,_tmpPatientAbhaId,_tmpDestinationFacility,_tmpPriority,_tmpNotes,_tmpCreatedAt);
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
  public Flow<List<Referral>> getAllReferrals() {
    final String _sql = "SELECT * FROM referrals ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"referrals"}, new Callable<List<Referral>>() {
      @Override
      @NonNull
      public List<Referral> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfReferralId = CursorUtil.getColumnIndexOrThrow(_cursor, "referralId");
          final int _cursorIndexOfEpisodeId = CursorUtil.getColumnIndexOrThrow(_cursor, "episodeId");
          final int _cursorIndexOfPatientAbhaId = CursorUtil.getColumnIndexOrThrow(_cursor, "patientAbhaId");
          final int _cursorIndexOfDestinationFacility = CursorUtil.getColumnIndexOrThrow(_cursor, "destinationFacility");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<Referral> _result = new ArrayList<Referral>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Referral _item;
            final long _tmpReferralId;
            _tmpReferralId = _cursor.getLong(_cursorIndexOfReferralId);
            final long _tmpEpisodeId;
            _tmpEpisodeId = _cursor.getLong(_cursorIndexOfEpisodeId);
            final String _tmpPatientAbhaId;
            _tmpPatientAbhaId = _cursor.getString(_cursorIndexOfPatientAbhaId);
            final String _tmpDestinationFacility;
            _tmpDestinationFacility = _cursor.getString(_cursorIndexOfDestinationFacility);
            final String _tmpPriority;
            _tmpPriority = _cursor.getString(_cursorIndexOfPriority);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new Referral(_tmpReferralId,_tmpEpisodeId,_tmpPatientAbhaId,_tmpDestinationFacility,_tmpPriority,_tmpNotes,_tmpCreatedAt);
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
