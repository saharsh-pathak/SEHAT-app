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
import com.example.sehat.data.entity.Symptom;
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
public final class SymptomDao_Impl implements SymptomDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Symptom> __insertionAdapterOfSymptom;

  public SymptomDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSymptom = new EntityInsertionAdapter<Symptom>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `symptoms` (`id`,`episodeId`,`voiceTranscript`,`manualText`,`selectedChips`,`existingConditions`,`currentMedicines`,`allergies`,`consentGiven`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Symptom entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getEpisodeId());
        statement.bindString(3, entity.getVoiceTranscript());
        statement.bindString(4, entity.getManualText());
        statement.bindString(5, entity.getSelectedChips());
        statement.bindString(6, entity.getExistingConditions());
        statement.bindString(7, entity.getCurrentMedicines());
        statement.bindString(8, entity.getAllergies());
        final int _tmp = entity.getConsentGiven() ? 1 : 0;
        statement.bindLong(9, _tmp);
      }
    };
  }

  @Override
  public Object insert(final Symptom symptom, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfSymptom.insertAndReturnId(symptom);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<Symptom> getForEpisode(final long episodeId) {
    final String _sql = "SELECT * FROM symptoms WHERE episodeId = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, episodeId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"symptoms"}, new Callable<Symptom>() {
      @Override
      @Nullable
      public Symptom call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfEpisodeId = CursorUtil.getColumnIndexOrThrow(_cursor, "episodeId");
          final int _cursorIndexOfVoiceTranscript = CursorUtil.getColumnIndexOrThrow(_cursor, "voiceTranscript");
          final int _cursorIndexOfManualText = CursorUtil.getColumnIndexOrThrow(_cursor, "manualText");
          final int _cursorIndexOfSelectedChips = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedChips");
          final int _cursorIndexOfExistingConditions = CursorUtil.getColumnIndexOrThrow(_cursor, "existingConditions");
          final int _cursorIndexOfCurrentMedicines = CursorUtil.getColumnIndexOrThrow(_cursor, "currentMedicines");
          final int _cursorIndexOfAllergies = CursorUtil.getColumnIndexOrThrow(_cursor, "allergies");
          final int _cursorIndexOfConsentGiven = CursorUtil.getColumnIndexOrThrow(_cursor, "consentGiven");
          final Symptom _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpEpisodeId;
            _tmpEpisodeId = _cursor.getLong(_cursorIndexOfEpisodeId);
            final String _tmpVoiceTranscript;
            _tmpVoiceTranscript = _cursor.getString(_cursorIndexOfVoiceTranscript);
            final String _tmpManualText;
            _tmpManualText = _cursor.getString(_cursorIndexOfManualText);
            final String _tmpSelectedChips;
            _tmpSelectedChips = _cursor.getString(_cursorIndexOfSelectedChips);
            final String _tmpExistingConditions;
            _tmpExistingConditions = _cursor.getString(_cursorIndexOfExistingConditions);
            final String _tmpCurrentMedicines;
            _tmpCurrentMedicines = _cursor.getString(_cursorIndexOfCurrentMedicines);
            final String _tmpAllergies;
            _tmpAllergies = _cursor.getString(_cursorIndexOfAllergies);
            final boolean _tmpConsentGiven;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfConsentGiven);
            _tmpConsentGiven = _tmp != 0;
            _result = new Symptom(_tmpId,_tmpEpisodeId,_tmpVoiceTranscript,_tmpManualText,_tmpSelectedChips,_tmpExistingConditions,_tmpCurrentMedicines,_tmpAllergies,_tmpConsentGiven);
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
