package com.microcareer.match.data.local.database;

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
import com.microcareer.match.data.local.dao.JobDao;
import com.microcareer.match.data.local.dao.JobDao_Impl;
import com.microcareer.match.data.local.dao.LearningModuleDao;
import com.microcareer.match.data.local.dao.LearningModuleDao_Impl;
import com.microcareer.match.data.local.dao.OfflinePendingActionDao;
import com.microcareer.match.data.local.dao.OfflinePendingActionDao_Impl;
import com.microcareer.match.data.local.dao.SkillProfileDao;
import com.microcareer.match.data.local.dao.SkillProfileDao_Impl;
import com.microcareer.match.data.local.dao.UserDao;
import com.microcareer.match.data.local.dao.UserDao_Impl;
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
public final class MicroCareerDatabase_Impl extends MicroCareerDatabase {
  private volatile UserDao _userDao;

  private volatile JobDao _jobDao;

  private volatile LearningModuleDao _learningModuleDao;

  private volatile SkillProfileDao _skillProfileDao;

  private volatile OfflinePendingActionDao _offlinePendingActionDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(5) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `users` (`id` TEXT NOT NULL, `email` TEXT NOT NULL, `fullName` TEXT NOT NULL, `role` TEXT, `phoneNumber` TEXT, `profilePictureUrl` TEXT, `location` TEXT, `latitude` REAL, `longitude` REAL, `availableHours` TEXT, `companyName` TEXT, `rating` REAL NOT NULL, `completedJobs` INTEGER NOT NULL, `skills` TEXT NOT NULL, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL, `isSynced` INTEGER NOT NULL, `bio` TEXT, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `jobs` (`id` TEXT NOT NULL, `title` TEXT NOT NULL, `description` TEXT NOT NULL, `employerId` TEXT NOT NULL, `employerName` TEXT, `location` TEXT NOT NULL, `latitude` REAL NOT NULL, `longitude` REAL NOT NULL, `requiredSkills` TEXT NOT NULL, `payment` REAL NOT NULL, `currency` TEXT NOT NULL, `duration` INTEGER NOT NULL, `category` TEXT NOT NULL, `matchScore` REAL NOT NULL, `status` TEXT NOT NULL, `deadline` INTEGER, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL, `isSaved` INTEGER NOT NULL, `isApplied` INTEGER NOT NULL, `isSynced` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `learning_modules` (`id` TEXT NOT NULL, `title` TEXT NOT NULL, `description` TEXT NOT NULL, `skillName` TEXT NOT NULL, `duration` INTEGER NOT NULL, `contentUrl` TEXT, `contentType` TEXT NOT NULL, `difficulty` TEXT NOT NULL, `prerequisites` TEXT NOT NULL, `isCompleted` INTEGER NOT NULL, `progress` INTEGER NOT NULL, `recommendationScore` REAL NOT NULL, `createdAt` INTEGER NOT NULL, `completedAt` INTEGER, `isSynced` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `skill_profiles` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `userId` TEXT NOT NULL, `skillName` TEXT NOT NULL, `proficiencyLevel` TEXT NOT NULL, `yearsOfExperience` INTEGER NOT NULL, `sourceType` TEXT NOT NULL, `rawInput` TEXT, `confidence` REAL NOT NULL, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL, `isSynced` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `offline_pending_actions` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `actionType` TEXT NOT NULL, `jobId` TEXT NOT NULL, `payloadJson` TEXT, `createdAt` INTEGER NOT NULL, `retryCount` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'd3285e1e8019918a9645a201b345a076')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `users`");
        db.execSQL("DROP TABLE IF EXISTS `jobs`");
        db.execSQL("DROP TABLE IF EXISTS `learning_modules`");
        db.execSQL("DROP TABLE IF EXISTS `skill_profiles`");
        db.execSQL("DROP TABLE IF EXISTS `offline_pending_actions`");
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
        final HashMap<String, TableInfo.Column> _columnsUsers = new HashMap<String, TableInfo.Column>(18);
        _columnsUsers.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("email", new TableInfo.Column("email", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("fullName", new TableInfo.Column("fullName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("role", new TableInfo.Column("role", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("phoneNumber", new TableInfo.Column("phoneNumber", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("profilePictureUrl", new TableInfo.Column("profilePictureUrl", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("location", new TableInfo.Column("location", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("latitude", new TableInfo.Column("latitude", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("longitude", new TableInfo.Column("longitude", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("availableHours", new TableInfo.Column("availableHours", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("companyName", new TableInfo.Column("companyName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("rating", new TableInfo.Column("rating", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("completedJobs", new TableInfo.Column("completedJobs", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("skills", new TableInfo.Column("skills", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("isSynced", new TableInfo.Column("isSynced", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("bio", new TableInfo.Column("bio", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUsers = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUsers = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUsers = new TableInfo("users", _columnsUsers, _foreignKeysUsers, _indicesUsers);
        final TableInfo _existingUsers = TableInfo.read(db, "users");
        if (!_infoUsers.equals(_existingUsers)) {
          return new RoomOpenHelper.ValidationResult(false, "users(com.microcareer.match.data.local.entity.UserEntity).\n"
                  + " Expected:\n" + _infoUsers + "\n"
                  + " Found:\n" + _existingUsers);
        }
        final HashMap<String, TableInfo.Column> _columnsJobs = new HashMap<String, TableInfo.Column>(21);
        _columnsJobs.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsJobs.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsJobs.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsJobs.put("employerId", new TableInfo.Column("employerId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsJobs.put("employerName", new TableInfo.Column("employerName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsJobs.put("location", new TableInfo.Column("location", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsJobs.put("latitude", new TableInfo.Column("latitude", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsJobs.put("longitude", new TableInfo.Column("longitude", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsJobs.put("requiredSkills", new TableInfo.Column("requiredSkills", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsJobs.put("payment", new TableInfo.Column("payment", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsJobs.put("currency", new TableInfo.Column("currency", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsJobs.put("duration", new TableInfo.Column("duration", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsJobs.put("category", new TableInfo.Column("category", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsJobs.put("matchScore", new TableInfo.Column("matchScore", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsJobs.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsJobs.put("deadline", new TableInfo.Column("deadline", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsJobs.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsJobs.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsJobs.put("isSaved", new TableInfo.Column("isSaved", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsJobs.put("isApplied", new TableInfo.Column("isApplied", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsJobs.put("isSynced", new TableInfo.Column("isSynced", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysJobs = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesJobs = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoJobs = new TableInfo("jobs", _columnsJobs, _foreignKeysJobs, _indicesJobs);
        final TableInfo _existingJobs = TableInfo.read(db, "jobs");
        if (!_infoJobs.equals(_existingJobs)) {
          return new RoomOpenHelper.ValidationResult(false, "jobs(com.microcareer.match.data.local.entity.JobEntity).\n"
                  + " Expected:\n" + _infoJobs + "\n"
                  + " Found:\n" + _existingJobs);
        }
        final HashMap<String, TableInfo.Column> _columnsLearningModules = new HashMap<String, TableInfo.Column>(15);
        _columnsLearningModules.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLearningModules.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLearningModules.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLearningModules.put("skillName", new TableInfo.Column("skillName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLearningModules.put("duration", new TableInfo.Column("duration", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLearningModules.put("contentUrl", new TableInfo.Column("contentUrl", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLearningModules.put("contentType", new TableInfo.Column("contentType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLearningModules.put("difficulty", new TableInfo.Column("difficulty", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLearningModules.put("prerequisites", new TableInfo.Column("prerequisites", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLearningModules.put("isCompleted", new TableInfo.Column("isCompleted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLearningModules.put("progress", new TableInfo.Column("progress", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLearningModules.put("recommendationScore", new TableInfo.Column("recommendationScore", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLearningModules.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLearningModules.put("completedAt", new TableInfo.Column("completedAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLearningModules.put("isSynced", new TableInfo.Column("isSynced", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysLearningModules = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesLearningModules = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoLearningModules = new TableInfo("learning_modules", _columnsLearningModules, _foreignKeysLearningModules, _indicesLearningModules);
        final TableInfo _existingLearningModules = TableInfo.read(db, "learning_modules");
        if (!_infoLearningModules.equals(_existingLearningModules)) {
          return new RoomOpenHelper.ValidationResult(false, "learning_modules(com.microcareer.match.data.local.entity.LearningModuleEntity).\n"
                  + " Expected:\n" + _infoLearningModules + "\n"
                  + " Found:\n" + _existingLearningModules);
        }
        final HashMap<String, TableInfo.Column> _columnsSkillProfiles = new HashMap<String, TableInfo.Column>(11);
        _columnsSkillProfiles.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSkillProfiles.put("userId", new TableInfo.Column("userId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSkillProfiles.put("skillName", new TableInfo.Column("skillName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSkillProfiles.put("proficiencyLevel", new TableInfo.Column("proficiencyLevel", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSkillProfiles.put("yearsOfExperience", new TableInfo.Column("yearsOfExperience", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSkillProfiles.put("sourceType", new TableInfo.Column("sourceType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSkillProfiles.put("rawInput", new TableInfo.Column("rawInput", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSkillProfiles.put("confidence", new TableInfo.Column("confidence", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSkillProfiles.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSkillProfiles.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSkillProfiles.put("isSynced", new TableInfo.Column("isSynced", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSkillProfiles = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSkillProfiles = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoSkillProfiles = new TableInfo("skill_profiles", _columnsSkillProfiles, _foreignKeysSkillProfiles, _indicesSkillProfiles);
        final TableInfo _existingSkillProfiles = TableInfo.read(db, "skill_profiles");
        if (!_infoSkillProfiles.equals(_existingSkillProfiles)) {
          return new RoomOpenHelper.ValidationResult(false, "skill_profiles(com.microcareer.match.data.local.entity.SkillProfileEntity).\n"
                  + " Expected:\n" + _infoSkillProfiles + "\n"
                  + " Found:\n" + _existingSkillProfiles);
        }
        final HashMap<String, TableInfo.Column> _columnsOfflinePendingActions = new HashMap<String, TableInfo.Column>(6);
        _columnsOfflinePendingActions.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOfflinePendingActions.put("actionType", new TableInfo.Column("actionType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOfflinePendingActions.put("jobId", new TableInfo.Column("jobId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOfflinePendingActions.put("payloadJson", new TableInfo.Column("payloadJson", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOfflinePendingActions.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOfflinePendingActions.put("retryCount", new TableInfo.Column("retryCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysOfflinePendingActions = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesOfflinePendingActions = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoOfflinePendingActions = new TableInfo("offline_pending_actions", _columnsOfflinePendingActions, _foreignKeysOfflinePendingActions, _indicesOfflinePendingActions);
        final TableInfo _existingOfflinePendingActions = TableInfo.read(db, "offline_pending_actions");
        if (!_infoOfflinePendingActions.equals(_existingOfflinePendingActions)) {
          return new RoomOpenHelper.ValidationResult(false, "offline_pending_actions(com.microcareer.match.data.local.entity.OfflinePendingActionEntity).\n"
                  + " Expected:\n" + _infoOfflinePendingActions + "\n"
                  + " Found:\n" + _existingOfflinePendingActions);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "d3285e1e8019918a9645a201b345a076", "190615d756a1e355e56661958e3af107");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "users","jobs","learning_modules","skill_profiles","offline_pending_actions");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `users`");
      _db.execSQL("DELETE FROM `jobs`");
      _db.execSQL("DELETE FROM `learning_modules`");
      _db.execSQL("DELETE FROM `skill_profiles`");
      _db.execSQL("DELETE FROM `offline_pending_actions`");
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
    _typeConvertersMap.put(UserDao.class, UserDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(JobDao.class, JobDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(LearningModuleDao.class, LearningModuleDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(SkillProfileDao.class, SkillProfileDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(OfflinePendingActionDao.class, OfflinePendingActionDao_Impl.getRequiredConverters());
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
  public UserDao userDao() {
    if (_userDao != null) {
      return _userDao;
    } else {
      synchronized(this) {
        if(_userDao == null) {
          _userDao = new UserDao_Impl(this);
        }
        return _userDao;
      }
    }
  }

  @Override
  public JobDao jobDao() {
    if (_jobDao != null) {
      return _jobDao;
    } else {
      synchronized(this) {
        if(_jobDao == null) {
          _jobDao = new JobDao_Impl(this);
        }
        return _jobDao;
      }
    }
  }

  @Override
  public LearningModuleDao learningModuleDao() {
    if (_learningModuleDao != null) {
      return _learningModuleDao;
    } else {
      synchronized(this) {
        if(_learningModuleDao == null) {
          _learningModuleDao = new LearningModuleDao_Impl(this);
        }
        return _learningModuleDao;
      }
    }
  }

  @Override
  public SkillProfileDao skillProfileDao() {
    if (_skillProfileDao != null) {
      return _skillProfileDao;
    } else {
      synchronized(this) {
        if(_skillProfileDao == null) {
          _skillProfileDao = new SkillProfileDao_Impl(this);
        }
        return _skillProfileDao;
      }
    }
  }

  @Override
  public OfflinePendingActionDao offlinePendingActionDao() {
    if (_offlinePendingActionDao != null) {
      return _offlinePendingActionDao;
    } else {
      synchronized(this) {
        if(_offlinePendingActionDao == null) {
          _offlinePendingActionDao = new OfflinePendingActionDao_Impl(this);
        }
        return _offlinePendingActionDao;
      }
    }
  }
}
