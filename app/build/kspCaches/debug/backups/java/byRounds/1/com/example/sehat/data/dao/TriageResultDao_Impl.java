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
import com.example.sehat.data.entity.TriageResult;
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
public final class TriageResultDao_Impl implements TriageResultDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<TriageResult> __insertionAdapterOfTriageResult;

  public TriageResultDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTriageResult = new EntityInsertionAdapter<TriageResult>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `triage_results` (`id`,`episodeId`,`probableCondition`,`severity`,`recommendedFacility`,`recommendedProfessional`,`justification`,`clinicalNotes`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TriageResult entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getEpisodeId());
        statement.bindString(3, entity.getProbableCondition());
        statement.bindString(4, entity.getSeverity());
        statement.bindString(5, entity.getRecommendedFacility());
        statement.bindString(6, entity.getRecommendedProfessional());
        statement.bindString(7, entity.getJustification());
        statement.bindString(8, entity.getClinicalNotes());
      }
    };
  }

  @Override
  public Object insert(final TriageResult triageResult,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfTriageResult.insertAndReturnId(triageResult);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<TriageResult> getForEpisode(final long episodeId) {
    final String _sql = "SELECT * FROM triage_results WHERE episodeId = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, episodeId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"triage_results"}, new Callable<TriageResult>() {
      @Override
      @Nullable
      public TriageResult call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfEpisodeId = CursorUtil.getColumnIndexOrThrow(_cursor, "episodeId");
          final int _cursorIndexOfProbableCondition = CursorUtil.getColumnIndexOrThrow(_cursor, "probableCondition");
          final int _cursorIndexOfSeverity = CursorUtil.getColumnIndexOrThrow(_cursor, "severity");
          final int _cursorIndexOfRecommendedFacility = CursorUtil.getColumnIndexOrThrow(_cursor, "recommendedFacility");
          final int _cursorIndexOfRecommendedProfessional = CursorUtil.getColumnIndexOrThrow(_cursor, "recommendedProfessional");
          final int _cursorIndexOfJustification = CursorUtil.getColumnIndexOrThrow(_cursor, "justification");
          final int _cursorIndexOfClinicalNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "clinicalNotes");
          final TriageResult _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpEpisodeId;
            _tmpEpisodeId = _cursor.getLong(_cursorIndexOfEpisodeId);
            final String _tmpProbableCondition;
            _tmpProbableCondition = _cursor.getString(_cursorIndexOfProbableCondition);
            final String _tmpSeverity;
            _tmpSeverity = _cursor.getString(_cursorIndexOfSeverity);
            final String _tmpRecommendedFacility;
            _tmpRecommendedFacility = _cursor.getString(_cursorIndexOfRecommendedFacility);
            final String _tmpRecommendedProfessional;
            _tmpRecommendedProfessional = _cursor.getString(_cursorIndexOfRecommendedProfessional);
            final String _tmpJustification;
            _tmpJustification = _cursor.getString(_cursorIndexOfJustification);
            final String _tmpClinicalNotes;
            _tmpClinicalNotes = _cursor.getString(_cursorIndexOfClinicalNotes);
            _result = new TriageResult(_tmpId,_tmpEpisodeId,_tmpProbableCondition,_tmpSeverity,_tmpRecommendedFacility,_tmpRecommendedProfessional,_tmpJustification,_tmpClinicalNotes);
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
