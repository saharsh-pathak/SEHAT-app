package com.example.sehat.data;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.example.sehat.data.dao.AppointmentDao;
import com.example.sehat.data.dao.AppointmentDao_Impl;
import com.example.sehat.data.dao.CareEpisodeDao;
import com.example.sehat.data.dao.CareEpisodeDao_Impl;
import com.example.sehat.data.dao.FollowUpTaskDao;
import com.example.sehat.data.dao.FollowUpTaskDao_Impl;
import com.example.sehat.data.dao.MedicineDao;
import com.example.sehat.data.dao.MedicineDao_Impl;
import com.example.sehat.data.dao.PatientDao;
import com.example.sehat.data.dao.PatientDao_Impl;
import com.example.sehat.data.dao.ReferralDao;
import com.example.sehat.data.dao.ReferralDao_Impl;
import com.example.sehat.data.dao.ScreeningTestDao;
import com.example.sehat.data.dao.ScreeningTestDao_Impl;
import com.example.sehat.data.dao.SymptomDao;
import com.example.sehat.data.dao.SymptomDao_Impl;
import com.example.sehat.data.dao.SyncQueueDao;
import com.example.sehat.data.dao.SyncQueueDao_Impl;
import com.example.sehat.data.dao.TriageResultDao;
import com.example.sehat.data.dao.TriageResultDao_Impl;
import com.example.sehat.data.dao.VitalsDao;
import com.example.sehat.data.dao.VitalsDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class SehatDatabase_Impl extends SehatDatabase {
  private volatile PatientDao _patientDao;

  private volatile CareEpisodeDao _careEpisodeDao;

  private volatile SymptomDao _symptomDao;

  private volatile VitalsDao _vitalsDao;

  private volatile ScreeningTestDao _screeningTestDao;

  private volatile TriageResultDao _triageResultDao;

  private volatile ReferralDao _referralDao;

  private volatile AppointmentDao _appointmentDao;

  private volatile MedicineDao _medicineDao;

  private volatile FollowUpTaskDao _followUpTaskDao;

  private volatile SyncQueueDao _syncQueueDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `patients` (`abhaId` TEXT NOT NULL, `aadhaarRef` TEXT, `name` TEXT NOT NULL, `age` INTEGER NOT NULL, `gender` TEXT NOT NULL, `village` TEXT NOT NULL, `mobile` TEXT, `createdAt` INTEGER NOT NULL, PRIMARY KEY(`abhaId`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `care_episodes` (`episodeId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `patientAbhaId` TEXT NOT NULL, `facilityName` TEXT NOT NULL, `status` TEXT NOT NULL, `createdAt` INTEGER NOT NULL, `closedAt` INTEGER)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `symptoms` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `episodeId` INTEGER NOT NULL, `voiceTranscript` TEXT NOT NULL, `manualText` TEXT NOT NULL, `selectedChips` TEXT NOT NULL, `existingConditions` TEXT NOT NULL, `currentMedicines` TEXT NOT NULL, `allergies` TEXT NOT NULL, `consentGiven` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `vitals` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `episodeId` INTEGER NOT NULL, `bloodPressure` TEXT NOT NULL, `heartRate` INTEGER NOT NULL, `spo2` INTEGER NOT NULL, `temperature` REAL NOT NULL, `bloodGlucose` INTEGER NOT NULL, `hemoglobin` REAL NOT NULL, `weight` REAL NOT NULL, `height` REAL NOT NULL, `clinicalNotes` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `screening_tests` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `episodeId` INTEGER NOT NULL, `testName` TEXT NOT NULL, `result` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `triage_results` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `episodeId` INTEGER NOT NULL, `probableCondition` TEXT NOT NULL, `severity` TEXT NOT NULL, `recommendedFacility` TEXT NOT NULL, `recommendedProfessional` TEXT NOT NULL, `justification` TEXT NOT NULL, `clinicalNotes` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `referrals` (`referralId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `episodeId` INTEGER NOT NULL, `patientAbhaId` TEXT NOT NULL, `destinationFacility` TEXT NOT NULL, `priority` TEXT NOT NULL, `notes` TEXT NOT NULL, `createdAt` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `appointments` (`appointmentId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `appointmentCode` TEXT NOT NULL, `referralId` INTEGER NOT NULL, `patientAbhaId` TEXT NOT NULL, `facility` TEXT NOT NULL, `dateTime` TEXT NOT NULL, `consultationType` TEXT NOT NULL, `queueToken` TEXT NOT NULL, `createdAt` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `medicines` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `form` TEXT NOT NULL, `facility` TEXT NOT NULL, `stockCount` INTEGER NOT NULL, `status` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `follow_up_tasks` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `episodeId` INTEGER NOT NULL, `patientAbhaId` TEXT NOT NULL, `patientName` TEXT NOT NULL, `village` TEXT NOT NULL, `taskTitle` TEXT NOT NULL, `dueDate` TEXT NOT NULL, `status` TEXT NOT NULL, `checklistJson` TEXT NOT NULL, `notes` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `sync_queue` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `entityType` TEXT NOT NULL, `entityId` TEXT NOT NULL, `action` TEXT NOT NULL, `createdAt` INTEGER NOT NULL, `synced` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '327cc08b0efae561be2b49a623d7e8f0')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `patients`");
        db.execSQL("DROP TABLE IF EXISTS `care_episodes`");
        db.execSQL("DROP TABLE IF EXISTS `symptoms`");
        db.execSQL("DROP TABLE IF EXISTS `vitals`");
        db.execSQL("DROP TABLE IF EXISTS `screening_tests`");
        db.execSQL("DROP TABLE IF EXISTS `triage_results`");
        db.execSQL("DROP TABLE IF EXISTS `referrals`");
        db.execSQL("DROP TABLE IF EXISTS `appointments`");
        db.execSQL("DROP TABLE IF EXISTS `medicines`");
        db.execSQL("DROP TABLE IF EXISTS `follow_up_tasks`");
        db.execSQL("DROP TABLE IF EXISTS `sync_queue`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsPatients = new HashMap<String, TableInfo.Column>(8);
        _columnsPatients.put("abhaId", new TableInfo.Column("abhaId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPatients.put("aadhaarRef", new TableInfo.Column("aadhaarRef", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPatients.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPatients.put("age", new TableInfo.Column("age", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPatients.put("gender", new TableInfo.Column("gender", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPatients.put("village", new TableInfo.Column("village", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPatients.put("mobile", new TableInfo.Column("mobile", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPatients.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPatients = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPatients = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPatients = new TableInfo("patients", _columnsPatients, _foreignKeysPatients, _indicesPatients);
        final TableInfo _existingPatients = TableInfo.read(db, "patients");
        if (!_infoPatients.equals(_existingPatients)) {
          return new RoomOpenHelper.ValidationResult(false, "patients(com.example.sehat.data.entity.Patient).\n"
                  + " Expected:\n" + _infoPatients + "\n"
                  + " Found:\n" + _existingPatients);
        }
        final HashMap<String, TableInfo.Column> _columnsCareEpisodes = new HashMap<String, TableInfo.Column>(6);
        _columnsCareEpisodes.put("episodeId", new TableInfo.Column("episodeId", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCareEpisodes.put("patientAbhaId", new TableInfo.Column("patientAbhaId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCareEpisodes.put("facilityName", new TableInfo.Column("facilityName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCareEpisodes.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCareEpisodes.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCareEpisodes.put("closedAt", new TableInfo.Column("closedAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCareEpisodes = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCareEpisodes = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCareEpisodes = new TableInfo("care_episodes", _columnsCareEpisodes, _foreignKeysCareEpisodes, _indicesCareEpisodes);
        final TableInfo _existingCareEpisodes = TableInfo.read(db, "care_episodes");
        if (!_infoCareEpisodes.equals(_existingCareEpisodes)) {
          return new RoomOpenHelper.ValidationResult(false, "care_episodes(com.example.sehat.data.entity.CareEpisode).\n"
                  + " Expected:\n" + _infoCareEpisodes + "\n"
                  + " Found:\n" + _existingCareEpisodes);
        }
        final HashMap<String, TableInfo.Column> _columnsSymptoms = new HashMap<String, TableInfo.Column>(9);
        _columnsSymptoms.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSymptoms.put("episodeId", new TableInfo.Column("episodeId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSymptoms.put("voiceTranscript", new TableInfo.Column("voiceTranscript", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSymptoms.put("manualText", new TableInfo.Column("manualText", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSymptoms.put("selectedChips", new TableInfo.Column("selectedChips", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSymptoms.put("existingConditions", new TableInfo.Column("existingConditions", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSymptoms.put("currentMedicines", new TableInfo.Column("currentMedicines", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSymptoms.put("allergies", new TableInfo.Column("allergies", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSymptoms.put("consentGiven", new TableInfo.Column("consentGiven", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSymptoms = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSymptoms = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoSymptoms = new TableInfo("symptoms", _columnsSymptoms, _foreignKeysSymptoms, _indicesSymptoms);
        final TableInfo _existingSymptoms = TableInfo.read(db, "symptoms");
        if (!_infoSymptoms.equals(_existingSymptoms)) {
          return new RoomOpenHelper.ValidationResult(false, "symptoms(com.example.sehat.data.entity.Symptom).\n"
                  + " Expected:\n" + _infoSymptoms + "\n"
                  + " Found:\n" + _existingSymptoms);
        }
        final HashMap<String, TableInfo.Column> _columnsVitals = new HashMap<String, TableInfo.Column>(11);
        _columnsVitals.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVitals.put("episodeId", new TableInfo.Column("episodeId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVitals.put("bloodPressure", new TableInfo.Column("bloodPressure", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVitals.put("heartRate", new TableInfo.Column("heartRate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVitals.put("spo2", new TableInfo.Column("spo2", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVitals.put("temperature", new TableInfo.Column("temperature", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVitals.put("bloodGlucose", new TableInfo.Column("bloodGlucose", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVitals.put("hemoglobin", new TableInfo.Column("hemoglobin", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVitals.put("weight", new TableInfo.Column("weight", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVitals.put("height", new TableInfo.Column("height", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVitals.put("clinicalNotes", new TableInfo.Column("clinicalNotes", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysVitals = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesVitals = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoVitals = new TableInfo("vitals", _columnsVitals, _foreignKeysVitals, _indicesVitals);
        final TableInfo _existingVitals = TableInfo.read(db, "vitals");
        if (!_infoVitals.equals(_existingVitals)) {
          return new RoomOpenHelper.ValidationResult(false, "vitals(com.example.sehat.data.entity.Vitals).\n"
                  + " Expected:\n" + _infoVitals + "\n"
                  + " Found:\n" + _existingVitals);
        }
        final HashMap<String, TableInfo.Column> _columnsScreeningTests = new HashMap<String, TableInfo.Column>(4);
        _columnsScreeningTests.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsScreeningTests.put("episodeId", new TableInfo.Column("episodeId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsScreeningTests.put("testName", new TableInfo.Column("testName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsScreeningTests.put("result", new TableInfo.Column("result", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysScreeningTests = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesScreeningTests = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoScreeningTests = new TableInfo("screening_tests", _columnsScreeningTests, _foreignKeysScreeningTests, _indicesScreeningTests);
        final TableInfo _existingScreeningTests = TableInfo.read(db, "screening_tests");
        if (!_infoScreeningTests.equals(_existingScreeningTests)) {
          return new RoomOpenHelper.ValidationResult(false, "screening_tests(com.example.sehat.data.entity.ScreeningTest).\n"
                  + " Expected:\n" + _infoScreeningTests + "\n"
                  + " Found:\n" + _existingScreeningTests);
        }
        final HashMap<String, TableInfo.Column> _columnsTriageResults = new HashMap<String, TableInfo.Column>(8);
        _columnsTriageResults.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTriageResults.put("episodeId", new TableInfo.Column("episodeId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTriageResults.put("probableCondition", new TableInfo.Column("probableCondition", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTriageResults.put("severity", new TableInfo.Column("severity", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTriageResults.put("recommendedFacility", new TableInfo.Column("recommendedFacility", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTriageResults.put("recommendedProfessional", new TableInfo.Column("recommendedProfessional", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTriageResults.put("justification", new TableInfo.Column("justification", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTriageResults.put("clinicalNotes", new TableInfo.Column("clinicalNotes", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTriageResults = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTriageResults = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTriageResults = new TableInfo("triage_results", _columnsTriageResults, _foreignKeysTriageResults, _indicesTriageResults);
        final TableInfo _existingTriageResults = TableInfo.read(db, "triage_results");
        if (!_infoTriageResults.equals(_existingTriageResults)) {
          return new RoomOpenHelper.ValidationResult(false, "triage_results(com.example.sehat.data.entity.TriageResult).\n"
                  + " Expected:\n" + _infoTriageResults + "\n"
                  + " Found:\n" + _existingTriageResults);
        }
        final HashMap<String, TableInfo.Column> _columnsReferrals = new HashMap<String, TableInfo.Column>(7);
        _columnsReferrals.put("referralId", new TableInfo.Column("referralId", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReferrals.put("episodeId", new TableInfo.Column("episodeId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReferrals.put("patientAbhaId", new TableInfo.Column("patientAbhaId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReferrals.put("destinationFacility", new TableInfo.Column("destinationFacility", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReferrals.put("priority", new TableInfo.Column("priority", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReferrals.put("notes", new TableInfo.Column("notes", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReferrals.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysReferrals = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesReferrals = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoReferrals = new TableInfo("referrals", _columnsReferrals, _foreignKeysReferrals, _indicesReferrals);
        final TableInfo _existingReferrals = TableInfo.read(db, "referrals");
        if (!_infoReferrals.equals(_existingReferrals)) {
          return new RoomOpenHelper.ValidationResult(false, "referrals(com.example.sehat.data.entity.Referral).\n"
                  + " Expected:\n" + _infoReferrals + "\n"
                  + " Found:\n" + _existingReferrals);
        }
        final HashMap<String, TableInfo.Column> _columnsAppointments = new HashMap<String, TableInfo.Column>(9);
        _columnsAppointments.put("appointmentId", new TableInfo.Column("appointmentId", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppointments.put("appointmentCode", new TableInfo.Column("appointmentCode", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppointments.put("referralId", new TableInfo.Column("referralId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppointments.put("patientAbhaId", new TableInfo.Column("patientAbhaId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppointments.put("facility", new TableInfo.Column("facility", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppointments.put("dateTime", new TableInfo.Column("dateTime", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppointments.put("consultationType", new TableInfo.Column("consultationType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppointments.put("queueToken", new TableInfo.Column("queueToken", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppointments.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysAppointments = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesAppointments = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoAppointments = new TableInfo("appointments", _columnsAppointments, _foreignKeysAppointments, _indicesAppointments);
        final TableInfo _existingAppointments = TableInfo.read(db, "appointments");
        if (!_infoAppointments.equals(_existingAppointments)) {
          return new RoomOpenHelper.ValidationResult(false, "appointments(com.example.sehat.data.entity.Appointment).\n"
                  + " Expected:\n" + _infoAppointments + "\n"
                  + " Found:\n" + _existingAppointments);
        }
        final HashMap<String, TableInfo.Column> _columnsMedicines = new HashMap<String, TableInfo.Column>(6);
        _columnsMedicines.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedicines.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedicines.put("form", new TableInfo.Column("form", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedicines.put("facility", new TableInfo.Column("facility", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedicines.put("stockCount", new TableInfo.Column("stockCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedicines.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMedicines = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesMedicines = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoMedicines = new TableInfo("medicines", _columnsMedicines, _foreignKeysMedicines, _indicesMedicines);
        final TableInfo _existingMedicines = TableInfo.read(db, "medicines");
        if (!_infoMedicines.equals(_existingMedicines)) {
          return new RoomOpenHelper.ValidationResult(false, "medicines(com.example.sehat.data.entity.Medicine).\n"
                  + " Expected:\n" + _infoMedicines + "\n"
                  + " Found:\n" + _existingMedicines);
        }
        final HashMap<String, TableInfo.Column> _columnsFollowUpTasks = new HashMap<String, TableInfo.Column>(10);
        _columnsFollowUpTasks.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFollowUpTasks.put("episodeId", new TableInfo.Column("episodeId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFollowUpTasks.put("patientAbhaId", new TableInfo.Column("patientAbhaId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFollowUpTasks.put("patientName", new TableInfo.Column("patientName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFollowUpTasks.put("village", new TableInfo.Column("village", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFollowUpTasks.put("taskTitle", new TableInfo.Column("taskTitle", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFollowUpTasks.put("dueDate", new TableInfo.Column("dueDate", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFollowUpTasks.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFollowUpTasks.put("checklistJson", new TableInfo.Column("checklistJson", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFollowUpTasks.put("notes", new TableInfo.Column("notes", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysFollowUpTasks = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesFollowUpTasks = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoFollowUpTasks = new TableInfo("follow_up_tasks", _columnsFollowUpTasks, _foreignKeysFollowUpTasks, _indicesFollowUpTasks);
        final TableInfo _existingFollowUpTasks = TableInfo.read(db, "follow_up_tasks");
        if (!_infoFollowUpTasks.equals(_existingFollowUpTasks)) {
          return new RoomOpenHelper.ValidationResult(false, "follow_up_tasks(com.example.sehat.data.entity.FollowUpTask).\n"
                  + " Expected:\n" + _infoFollowUpTasks + "\n"
                  + " Found:\n" + _existingFollowUpTasks);
        }
        final HashMap<String, TableInfo.Column> _columnsSyncQueue = new HashMap<String, TableInfo.Column>(6);
        _columnsSyncQueue.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSyncQueue.put("entityType", new TableInfo.Column("entityType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSyncQueue.put("entityId", new TableInfo.Column("entityId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSyncQueue.put("action", new TableInfo.Column("action", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSyncQueue.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSyncQueue.put("synced", new TableInfo.Column("synced", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSyncQueue = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSyncQueue = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoSyncQueue = new TableInfo("sync_queue", _columnsSyncQueue, _foreignKeysSyncQueue, _indicesSyncQueue);
        final TableInfo _existingSyncQueue = TableInfo.read(db, "sync_queue");
        if (!_infoSyncQueue.equals(_existingSyncQueue)) {
          return new RoomOpenHelper.ValidationResult(false, "sync_queue(com.example.sehat.data.entity.SyncQueue).\n"
                  + " Expected:\n" + _infoSyncQueue + "\n"
                  + " Found:\n" + _existingSyncQueue);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "327cc08b0efae561be2b49a623d7e8f0", "1ffafed29b2ea6b3219e7af9eb8c5df4");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "patients","care_episodes","symptoms","vitals","screening_tests","triage_results","referrals","appointments","medicines","follow_up_tasks","sync_queue");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `patients`");
      _db.execSQL("DELETE FROM `care_episodes`");
      _db.execSQL("DELETE FROM `symptoms`");
      _db.execSQL("DELETE FROM `vitals`");
      _db.execSQL("DELETE FROM `screening_tests`");
      _db.execSQL("DELETE FROM `triage_results`");
      _db.execSQL("DELETE FROM `referrals`");
      _db.execSQL("DELETE FROM `appointments`");
      _db.execSQL("DELETE FROM `medicines`");
      _db.execSQL("DELETE FROM `follow_up_tasks`");
      _db.execSQL("DELETE FROM `sync_queue`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(PatientDao.class, PatientDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(CareEpisodeDao.class, CareEpisodeDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(SymptomDao.class, SymptomDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(VitalsDao.class, VitalsDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ScreeningTestDao.class, ScreeningTestDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(TriageResultDao.class, TriageResultDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ReferralDao.class, ReferralDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(AppointmentDao.class, AppointmentDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(MedicineDao.class, MedicineDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(FollowUpTaskDao.class, FollowUpTaskDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(SyncQueueDao.class, SyncQueueDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public PatientDao patientDao() {
    if (_patientDao != null) {
      return _patientDao;
    } else {
      synchronized(this) {
        if(_patientDao == null) {
          _patientDao = new PatientDao_Impl(this);
        }
        return _patientDao;
      }
    }
  }

  @Override
  public CareEpisodeDao careEpisodeDao() {
    if (_careEpisodeDao != null) {
      return _careEpisodeDao;
    } else {
      synchronized(this) {
        if(_careEpisodeDao == null) {
          _careEpisodeDao = new CareEpisodeDao_Impl(this);
        }
        return _careEpisodeDao;
      }
    }
  }

  @Override
  public SymptomDao symptomDao() {
    if (_symptomDao != null) {
      return _symptomDao;
    } else {
      synchronized(this) {
        if(_symptomDao == null) {
          _symptomDao = new SymptomDao_Impl(this);
        }
        return _symptomDao;
      }
    }
  }

  @Override
  public VitalsDao vitalsDao() {
    if (_vitalsDao != null) {
      return _vitalsDao;
    } else {
      synchronized(this) {
        if(_vitalsDao == null) {
          _vitalsDao = new VitalsDao_Impl(this);
        }
        return _vitalsDao;
      }
    }
  }

  @Override
  public ScreeningTestDao screeningTestDao() {
    if (_screeningTestDao != null) {
      return _screeningTestDao;
    } else {
      synchronized(this) {
        if(_screeningTestDao == null) {
          _screeningTestDao = new ScreeningTestDao_Impl(this);
        }
        return _screeningTestDao;
      }
    }
  }

  @Override
  public TriageResultDao triageResultDao() {
    if (_triageResultDao != null) {
      return _triageResultDao;
    } else {
      synchronized(this) {
        if(_triageResultDao == null) {
          _triageResultDao = new TriageResultDao_Impl(this);
        }
        return _triageResultDao;
      }
    }
  }

  @Override
  public ReferralDao referralDao() {
    if (_referralDao != null) {
      return _referralDao;
    } else {
      synchronized(this) {
        if(_referralDao == null) {
          _referralDao = new ReferralDao_Impl(this);
        }
        return _referralDao;
      }
    }
  }

  @Override
  public AppointmentDao appointmentDao() {
    if (_appointmentDao != null) {
      return _appointmentDao;
    } else {
      synchronized(this) {
        if(_appointmentDao == null) {
          _appointmentDao = new AppointmentDao_Impl(this);
        }
        return _appointmentDao;
      }
    }
  }

  @Override
  public MedicineDao medicineDao() {
    if (_medicineDao != null) {
      return _medicineDao;
    } else {
      synchronized(this) {
        if(_medicineDao == null) {
          _medicineDao = new MedicineDao_Impl(this);
        }
        return _medicineDao;
      }
    }
  }

  @Override
  public FollowUpTaskDao followUpTaskDao() {
    if (_followUpTaskDao != null) {
      return _followUpTaskDao;
    } else {
      synchronized(this) {
        if(_followUpTaskDao == null) {
          _followUpTaskDao = new FollowUpTaskDao_Impl(this);
        }
        return _followUpTaskDao;
      }
    }
  }

  @Override
  public SyncQueueDao syncQueueDao() {
    if (_syncQueueDao != null) {
      return _syncQueueDao;
    } else {
      synchronized(this) {
        if(_syncQueueDao == null) {
          _syncQueueDao = new SyncQueueDao_Impl(this);
        }
        return _syncQueueDao;
      }
    }
  }
}
