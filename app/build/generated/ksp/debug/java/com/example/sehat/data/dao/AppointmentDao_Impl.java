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
import com.example.sehat.data.entity.Appointment;
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
public final class AppointmentDao_Impl implements AppointmentDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Appointment> __insertionAdapterOfAppointment;

  public AppointmentDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfAppointment = new EntityInsertionAdapter<Appointment>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `appointments` (`appointmentId`,`appointmentCode`,`referralId`,`patientAbhaId`,`facility`,`dateTime`,`consultationType`,`queueToken`,`createdAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Appointment entity) {
        statement.bindLong(1, entity.getAppointmentId());
        statement.bindString(2, entity.getAppointmentCode());
        statement.bindLong(3, entity.getReferralId());
        statement.bindString(4, entity.getPatientAbhaId());
        statement.bindString(5, entity.getFacility());
        statement.bindString(6, entity.getDateTime());
        statement.bindString(7, entity.getConsultationType());
        statement.bindString(8, entity.getQueueToken());
        statement.bindLong(9, entity.getCreatedAt());
      }
    };
  }

  @Override
  public Object insert(final Appointment appointment,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfAppointment.insertAndReturnId(appointment);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<Appointment> getForReferral(final long referralId) {
    final String _sql = "SELECT * FROM appointments WHERE referralId = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, referralId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"appointments"}, new Callable<Appointment>() {
      @Override
      @Nullable
      public Appointment call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfAppointmentId = CursorUtil.getColumnIndexOrThrow(_cursor, "appointmentId");
          final int _cursorIndexOfAppointmentCode = CursorUtil.getColumnIndexOrThrow(_cursor, "appointmentCode");
          final int _cursorIndexOfReferralId = CursorUtil.getColumnIndexOrThrow(_cursor, "referralId");
          final int _cursorIndexOfPatientAbhaId = CursorUtil.getColumnIndexOrThrow(_cursor, "patientAbhaId");
          final int _cursorIndexOfFacility = CursorUtil.getColumnIndexOrThrow(_cursor, "facility");
          final int _cursorIndexOfDateTime = CursorUtil.getColumnIndexOrThrow(_cursor, "dateTime");
          final int _cursorIndexOfConsultationType = CursorUtil.getColumnIndexOrThrow(_cursor, "consultationType");
          final int _cursorIndexOfQueueToken = CursorUtil.getColumnIndexOrThrow(_cursor, "queueToken");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final Appointment _result;
          if (_cursor.moveToFirst()) {
            final long _tmpAppointmentId;
            _tmpAppointmentId = _cursor.getLong(_cursorIndexOfAppointmentId);
            final String _tmpAppointmentCode;
            _tmpAppointmentCode = _cursor.getString(_cursorIndexOfAppointmentCode);
            final long _tmpReferralId;
            _tmpReferralId = _cursor.getLong(_cursorIndexOfReferralId);
            final String _tmpPatientAbhaId;
            _tmpPatientAbhaId = _cursor.getString(_cursorIndexOfPatientAbhaId);
            final String _tmpFacility;
            _tmpFacility = _cursor.getString(_cursorIndexOfFacility);
            final String _tmpDateTime;
            _tmpDateTime = _cursor.getString(_cursorIndexOfDateTime);
            final String _tmpConsultationType;
            _tmpConsultationType = _cursor.getString(_cursorIndexOfConsultationType);
            final String _tmpQueueToken;
            _tmpQueueToken = _cursor.getString(_cursorIndexOfQueueToken);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result = new Appointment(_tmpAppointmentId,_tmpAppointmentCode,_tmpReferralId,_tmpPatientAbhaId,_tmpFacility,_tmpDateTime,_tmpConsultationType,_tmpQueueToken,_tmpCreatedAt);
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
  public Object getById(final long id, final Continuation<? super Appointment> $completion) {
    final String _sql = "SELECT * FROM appointments WHERE appointmentId = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Appointment>() {
      @Override
      @Nullable
      public Appointment call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfAppointmentId = CursorUtil.getColumnIndexOrThrow(_cursor, "appointmentId");
          final int _cursorIndexOfAppointmentCode = CursorUtil.getColumnIndexOrThrow(_cursor, "appointmentCode");
          final int _cursorIndexOfReferralId = CursorUtil.getColumnIndexOrThrow(_cursor, "referralId");
          final int _cursorIndexOfPatientAbhaId = CursorUtil.getColumnIndexOrThrow(_cursor, "patientAbhaId");
          final int _cursorIndexOfFacility = CursorUtil.getColumnIndexOrThrow(_cursor, "facility");
          final int _cursorIndexOfDateTime = CursorUtil.getColumnIndexOrThrow(_cursor, "dateTime");
          final int _cursorIndexOfConsultationType = CursorUtil.getColumnIndexOrThrow(_cursor, "consultationType");
          final int _cursorIndexOfQueueToken = CursorUtil.getColumnIndexOrThrow(_cursor, "queueToken");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final Appointment _result;
          if (_cursor.moveToFirst()) {
            final long _tmpAppointmentId;
            _tmpAppointmentId = _cursor.getLong(_cursorIndexOfAppointmentId);
            final String _tmpAppointmentCode;
            _tmpAppointmentCode = _cursor.getString(_cursorIndexOfAppointmentCode);
            final long _tmpReferralId;
            _tmpReferralId = _cursor.getLong(_cursorIndexOfReferralId);
            final String _tmpPatientAbhaId;
            _tmpPatientAbhaId = _cursor.getString(_cursorIndexOfPatientAbhaId);
            final String _tmpFacility;
            _tmpFacility = _cursor.getString(_cursorIndexOfFacility);
            final String _tmpDateTime;
            _tmpDateTime = _cursor.getString(_cursorIndexOfDateTime);
            final String _tmpConsultationType;
            _tmpConsultationType = _cursor.getString(_cursorIndexOfConsultationType);
            final String _tmpQueueToken;
            _tmpQueueToken = _cursor.getString(_cursorIndexOfQueueToken);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result = new Appointment(_tmpAppointmentId,_tmpAppointmentCode,_tmpReferralId,_tmpPatientAbhaId,_tmpFacility,_tmpDateTime,_tmpConsultationType,_tmpQueueToken,_tmpCreatedAt);
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
