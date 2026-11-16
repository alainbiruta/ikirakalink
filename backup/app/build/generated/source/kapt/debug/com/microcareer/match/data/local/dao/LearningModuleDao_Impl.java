package com.microcareer.match.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.microcareer.match.data.local.converter.ListConverters;
import com.microcareer.match.data.local.entity.LearningModuleEntity;
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
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class LearningModuleDao_Impl implements LearningModuleDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<LearningModuleEntity> __insertionAdapterOfLearningModuleEntity;

  private final ListConverters __listConverters = new ListConverters();

  private final EntityDeletionOrUpdateAdapter<LearningModuleEntity> __updateAdapterOfLearningModuleEntity;

  private final SharedSQLiteStatement __preparedStmtOfUpdateProgress;

  private final SharedSQLiteStatement __preparedStmtOfMarkAsCompleted;

  private final SharedSQLiteStatement __preparedStmtOfUpdateSyncStatus;

  private final SharedSQLiteStatement __preparedStmtOfDeleteModule;

  public LearningModuleDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfLearningModuleEntity = new EntityInsertionAdapter<LearningModuleEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `learning_modules` (`id`,`title`,`description`,`skillName`,`duration`,`contentUrl`,`contentType`,`difficulty`,`prerequisites`,`isCompleted`,`progress`,`recommendationScore`,`createdAt`,`completedAt`,`isSynced`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final LearningModuleEntity entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        if (entity.getTitle() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getTitle());
        }
        if (entity.getDescription() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getDescription());
        }
        if (entity.getSkillName() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getSkillName());
        }
        statement.bindLong(5, entity.getDuration());
        if (entity.getContentUrl() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getContentUrl());
        }
        if (entity.getContentType() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getContentType());
        }
        if (entity.getDifficulty() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getDifficulty());
        }
        final String _tmp = __listConverters.fromStringList(entity.getPrerequisites());
        if (_tmp == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, _tmp);
        }
        final int _tmp_1 = entity.isCompleted() ? 1 : 0;
        statement.bindLong(10, _tmp_1);
        statement.bindLong(11, entity.getProgress());
        statement.bindDouble(12, entity.getRecommendationScore());
        statement.bindLong(13, entity.getCreatedAt());
        if (entity.getCompletedAt() == null) {
          statement.bindNull(14);
        } else {
          statement.bindLong(14, entity.getCompletedAt());
        }
        final int _tmp_2 = entity.isSynced() ? 1 : 0;
        statement.bindLong(15, _tmp_2);
      }
    };
    this.__updateAdapterOfLearningModuleEntity = new EntityDeletionOrUpdateAdapter<LearningModuleEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `learning_modules` SET `id` = ?,`title` = ?,`description` = ?,`skillName` = ?,`duration` = ?,`contentUrl` = ?,`contentType` = ?,`difficulty` = ?,`prerequisites` = ?,`isCompleted` = ?,`progress` = ?,`recommendationScore` = ?,`createdAt` = ?,`completedAt` = ?,`isSynced` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final LearningModuleEntity entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        if (entity.getTitle() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getTitle());
        }
        if (entity.getDescription() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getDescription());
        }
        if (entity.getSkillName() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getSkillName());
        }
        statement.bindLong(5, entity.getDuration());
        if (entity.getContentUrl() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getContentUrl());
        }
        if (entity.getContentType() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getContentType());
        }
        if (entity.getDifficulty() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getDifficulty());
        }
        final String _tmp = __listConverters.fromStringList(entity.getPrerequisites());
        if (_tmp == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, _tmp);
        }
        final int _tmp_1 = entity.isCompleted() ? 1 : 0;
        statement.bindLong(10, _tmp_1);
        statement.bindLong(11, entity.getProgress());
        statement.bindDouble(12, entity.getRecommendationScore());
        statement.bindLong(13, entity.getCreatedAt());
        if (entity.getCompletedAt() == null) {
          statement.bindNull(14);
        } else {
          statement.bindLong(14, entity.getCompletedAt());
        }
        final int _tmp_2 = entity.isSynced() ? 1 : 0;
        statement.bindLong(15, _tmp_2);
        if (entity.getId() == null) {
          statement.bindNull(16);
        } else {
          statement.bindString(16, entity.getId());
        }
      }
    };
    this.__preparedStmtOfUpdateProgress = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE learning_modules SET progress = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfMarkAsCompleted = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE learning_modules SET isCompleted = 1, completedAt = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateSyncStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE learning_modules SET isSynced = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteModule = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM learning_modules WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertModule(final LearningModuleEntity module,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfLearningModuleEntity.insert(module);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertModules(final List<LearningModuleEntity> modules,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfLearningModuleEntity.insert(modules);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateModule(final LearningModuleEntity module,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfLearningModuleEntity.handle(module);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateProgress(final String moduleId, final int progress,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateProgress.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, progress);
        _argIndex = 2;
        if (moduleId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, moduleId);
        }
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateProgress.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object markAsCompleted(final String moduleId, final long completedAt,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfMarkAsCompleted.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, completedAt);
        _argIndex = 2;
        if (moduleId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, moduleId);
        }
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfMarkAsCompleted.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateSyncStatus(final String moduleId, final boolean synced,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateSyncStatus.acquire();
        int _argIndex = 1;
        final int _tmp = synced ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp);
        _argIndex = 2;
        if (moduleId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, moduleId);
        }
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateSyncStatus.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteModule(final String moduleId, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteModule.acquire();
        int _argIndex = 1;
        if (moduleId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, moduleId);
        }
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteModule.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<LearningModuleEntity>> getAllLearningModules() {
    final String _sql = "SELECT * FROM learning_modules ORDER BY recommendationScore DESC, createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"learning_modules"}, new Callable<List<LearningModuleEntity>>() {
      @Override
      @NonNull
      public List<LearningModuleEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfSkillName = CursorUtil.getColumnIndexOrThrow(_cursor, "skillName");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfContentUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "contentUrl");
          final int _cursorIndexOfContentType = CursorUtil.getColumnIndexOrThrow(_cursor, "contentType");
          final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
          final int _cursorIndexOfPrerequisites = CursorUtil.getColumnIndexOrThrow(_cursor, "prerequisites");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final int _cursorIndexOfProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "progress");
          final int _cursorIndexOfRecommendationScore = CursorUtil.getColumnIndexOrThrow(_cursor, "recommendationScore");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completedAt");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final List<LearningModuleEntity> _result = new ArrayList<LearningModuleEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final LearningModuleEntity _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpSkillName;
            if (_cursor.isNull(_cursorIndexOfSkillName)) {
              _tmpSkillName = null;
            } else {
              _tmpSkillName = _cursor.getString(_cursorIndexOfSkillName);
            }
            final int _tmpDuration;
            _tmpDuration = _cursor.getInt(_cursorIndexOfDuration);
            final String _tmpContentUrl;
            if (_cursor.isNull(_cursorIndexOfContentUrl)) {
              _tmpContentUrl = null;
            } else {
              _tmpContentUrl = _cursor.getString(_cursorIndexOfContentUrl);
            }
            final String _tmpContentType;
            if (_cursor.isNull(_cursorIndexOfContentType)) {
              _tmpContentType = null;
            } else {
              _tmpContentType = _cursor.getString(_cursorIndexOfContentType);
            }
            final String _tmpDifficulty;
            if (_cursor.isNull(_cursorIndexOfDifficulty)) {
              _tmpDifficulty = null;
            } else {
              _tmpDifficulty = _cursor.getString(_cursorIndexOfDifficulty);
            }
            final List<String> _tmpPrerequisites;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfPrerequisites)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfPrerequisites);
            }
            _tmpPrerequisites = __listConverters.toStringList(_tmp);
            final boolean _tmpIsCompleted;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp_1 != 0;
            final int _tmpProgress;
            _tmpProgress = _cursor.getInt(_cursorIndexOfProgress);
            final float _tmpRecommendationScore;
            _tmpRecommendationScore = _cursor.getFloat(_cursorIndexOfRecommendationScore);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final Long _tmpCompletedAt;
            if (_cursor.isNull(_cursorIndexOfCompletedAt)) {
              _tmpCompletedAt = null;
            } else {
              _tmpCompletedAt = _cursor.getLong(_cursorIndexOfCompletedAt);
            }
            final boolean _tmpIsSynced;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp_2 != 0;
            _item = new LearningModuleEntity(_tmpId,_tmpTitle,_tmpDescription,_tmpSkillName,_tmpDuration,_tmpContentUrl,_tmpContentType,_tmpDifficulty,_tmpPrerequisites,_tmpIsCompleted,_tmpProgress,_tmpRecommendationScore,_tmpCreatedAt,_tmpCompletedAt,_tmpIsSynced);
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
  public Flow<List<LearningModuleEntity>> getRecommendedModules(final int limit) {
    final String _sql = "SELECT * FROM learning_modules WHERE isCompleted = 0 ORDER BY recommendationScore DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, limit);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"learning_modules"}, new Callable<List<LearningModuleEntity>>() {
      @Override
      @NonNull
      public List<LearningModuleEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfSkillName = CursorUtil.getColumnIndexOrThrow(_cursor, "skillName");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfContentUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "contentUrl");
          final int _cursorIndexOfContentType = CursorUtil.getColumnIndexOrThrow(_cursor, "contentType");
          final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
          final int _cursorIndexOfPrerequisites = CursorUtil.getColumnIndexOrThrow(_cursor, "prerequisites");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final int _cursorIndexOfProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "progress");
          final int _cursorIndexOfRecommendationScore = CursorUtil.getColumnIndexOrThrow(_cursor, "recommendationScore");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completedAt");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final List<LearningModuleEntity> _result = new ArrayList<LearningModuleEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final LearningModuleEntity _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpSkillName;
            if (_cursor.isNull(_cursorIndexOfSkillName)) {
              _tmpSkillName = null;
            } else {
              _tmpSkillName = _cursor.getString(_cursorIndexOfSkillName);
            }
            final int _tmpDuration;
            _tmpDuration = _cursor.getInt(_cursorIndexOfDuration);
            final String _tmpContentUrl;
            if (_cursor.isNull(_cursorIndexOfContentUrl)) {
              _tmpContentUrl = null;
            } else {
              _tmpContentUrl = _cursor.getString(_cursorIndexOfContentUrl);
            }
            final String _tmpContentType;
            if (_cursor.isNull(_cursorIndexOfContentType)) {
              _tmpContentType = null;
            } else {
              _tmpContentType = _cursor.getString(_cursorIndexOfContentType);
            }
            final String _tmpDifficulty;
            if (_cursor.isNull(_cursorIndexOfDifficulty)) {
              _tmpDifficulty = null;
            } else {
              _tmpDifficulty = _cursor.getString(_cursorIndexOfDifficulty);
            }
            final List<String> _tmpPrerequisites;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfPrerequisites)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfPrerequisites);
            }
            _tmpPrerequisites = __listConverters.toStringList(_tmp);
            final boolean _tmpIsCompleted;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp_1 != 0;
            final int _tmpProgress;
            _tmpProgress = _cursor.getInt(_cursorIndexOfProgress);
            final float _tmpRecommendationScore;
            _tmpRecommendationScore = _cursor.getFloat(_cursorIndexOfRecommendationScore);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final Long _tmpCompletedAt;
            if (_cursor.isNull(_cursorIndexOfCompletedAt)) {
              _tmpCompletedAt = null;
            } else {
              _tmpCompletedAt = _cursor.getLong(_cursorIndexOfCompletedAt);
            }
            final boolean _tmpIsSynced;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp_2 != 0;
            _item = new LearningModuleEntity(_tmpId,_tmpTitle,_tmpDescription,_tmpSkillName,_tmpDuration,_tmpContentUrl,_tmpContentType,_tmpDifficulty,_tmpPrerequisites,_tmpIsCompleted,_tmpProgress,_tmpRecommendationScore,_tmpCreatedAt,_tmpCompletedAt,_tmpIsSynced);
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
  public Flow<LearningModuleEntity> getModule(final String moduleId) {
    final String _sql = "SELECT * FROM learning_modules WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (moduleId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, moduleId);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"learning_modules"}, new Callable<LearningModuleEntity>() {
      @Override
      @Nullable
      public LearningModuleEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfSkillName = CursorUtil.getColumnIndexOrThrow(_cursor, "skillName");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfContentUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "contentUrl");
          final int _cursorIndexOfContentType = CursorUtil.getColumnIndexOrThrow(_cursor, "contentType");
          final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
          final int _cursorIndexOfPrerequisites = CursorUtil.getColumnIndexOrThrow(_cursor, "prerequisites");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final int _cursorIndexOfProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "progress");
          final int _cursorIndexOfRecommendationScore = CursorUtil.getColumnIndexOrThrow(_cursor, "recommendationScore");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completedAt");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final LearningModuleEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpSkillName;
            if (_cursor.isNull(_cursorIndexOfSkillName)) {
              _tmpSkillName = null;
            } else {
              _tmpSkillName = _cursor.getString(_cursorIndexOfSkillName);
            }
            final int _tmpDuration;
            _tmpDuration = _cursor.getInt(_cursorIndexOfDuration);
            final String _tmpContentUrl;
            if (_cursor.isNull(_cursorIndexOfContentUrl)) {
              _tmpContentUrl = null;
            } else {
              _tmpContentUrl = _cursor.getString(_cursorIndexOfContentUrl);
            }
            final String _tmpContentType;
            if (_cursor.isNull(_cursorIndexOfContentType)) {
              _tmpContentType = null;
            } else {
              _tmpContentType = _cursor.getString(_cursorIndexOfContentType);
            }
            final String _tmpDifficulty;
            if (_cursor.isNull(_cursorIndexOfDifficulty)) {
              _tmpDifficulty = null;
            } else {
              _tmpDifficulty = _cursor.getString(_cursorIndexOfDifficulty);
            }
            final List<String> _tmpPrerequisites;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfPrerequisites)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfPrerequisites);
            }
            _tmpPrerequisites = __listConverters.toStringList(_tmp);
            final boolean _tmpIsCompleted;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp_1 != 0;
            final int _tmpProgress;
            _tmpProgress = _cursor.getInt(_cursorIndexOfProgress);
            final float _tmpRecommendationScore;
            _tmpRecommendationScore = _cursor.getFloat(_cursorIndexOfRecommendationScore);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final Long _tmpCompletedAt;
            if (_cursor.isNull(_cursorIndexOfCompletedAt)) {
              _tmpCompletedAt = null;
            } else {
              _tmpCompletedAt = _cursor.getLong(_cursorIndexOfCompletedAt);
            }
            final boolean _tmpIsSynced;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp_2 != 0;
            _result = new LearningModuleEntity(_tmpId,_tmpTitle,_tmpDescription,_tmpSkillName,_tmpDuration,_tmpContentUrl,_tmpContentType,_tmpDifficulty,_tmpPrerequisites,_tmpIsCompleted,_tmpProgress,_tmpRecommendationScore,_tmpCreatedAt,_tmpCompletedAt,_tmpIsSynced);
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
  public Flow<List<LearningModuleEntity>> getModulesForSkill(final String skillName) {
    final String _sql = "SELECT * FROM learning_modules WHERE skillName = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (skillName == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, skillName);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"learning_modules"}, new Callable<List<LearningModuleEntity>>() {
      @Override
      @NonNull
      public List<LearningModuleEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfSkillName = CursorUtil.getColumnIndexOrThrow(_cursor, "skillName");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfContentUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "contentUrl");
          final int _cursorIndexOfContentType = CursorUtil.getColumnIndexOrThrow(_cursor, "contentType");
          final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
          final int _cursorIndexOfPrerequisites = CursorUtil.getColumnIndexOrThrow(_cursor, "prerequisites");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final int _cursorIndexOfProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "progress");
          final int _cursorIndexOfRecommendationScore = CursorUtil.getColumnIndexOrThrow(_cursor, "recommendationScore");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completedAt");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final List<LearningModuleEntity> _result = new ArrayList<LearningModuleEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final LearningModuleEntity _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpSkillName;
            if (_cursor.isNull(_cursorIndexOfSkillName)) {
              _tmpSkillName = null;
            } else {
              _tmpSkillName = _cursor.getString(_cursorIndexOfSkillName);
            }
            final int _tmpDuration;
            _tmpDuration = _cursor.getInt(_cursorIndexOfDuration);
            final String _tmpContentUrl;
            if (_cursor.isNull(_cursorIndexOfContentUrl)) {
              _tmpContentUrl = null;
            } else {
              _tmpContentUrl = _cursor.getString(_cursorIndexOfContentUrl);
            }
            final String _tmpContentType;
            if (_cursor.isNull(_cursorIndexOfContentType)) {
              _tmpContentType = null;
            } else {
              _tmpContentType = _cursor.getString(_cursorIndexOfContentType);
            }
            final String _tmpDifficulty;
            if (_cursor.isNull(_cursorIndexOfDifficulty)) {
              _tmpDifficulty = null;
            } else {
              _tmpDifficulty = _cursor.getString(_cursorIndexOfDifficulty);
            }
            final List<String> _tmpPrerequisites;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfPrerequisites)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfPrerequisites);
            }
            _tmpPrerequisites = __listConverters.toStringList(_tmp);
            final boolean _tmpIsCompleted;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp_1 != 0;
            final int _tmpProgress;
            _tmpProgress = _cursor.getInt(_cursorIndexOfProgress);
            final float _tmpRecommendationScore;
            _tmpRecommendationScore = _cursor.getFloat(_cursorIndexOfRecommendationScore);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final Long _tmpCompletedAt;
            if (_cursor.isNull(_cursorIndexOfCompletedAt)) {
              _tmpCompletedAt = null;
            } else {
              _tmpCompletedAt = _cursor.getLong(_cursorIndexOfCompletedAt);
            }
            final boolean _tmpIsSynced;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp_2 != 0;
            _item = new LearningModuleEntity(_tmpId,_tmpTitle,_tmpDescription,_tmpSkillName,_tmpDuration,_tmpContentUrl,_tmpContentType,_tmpDifficulty,_tmpPrerequisites,_tmpIsCompleted,_tmpProgress,_tmpRecommendationScore,_tmpCreatedAt,_tmpCompletedAt,_tmpIsSynced);
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
  public Flow<List<LearningModuleEntity>> getCompletedModules() {
    final String _sql = "SELECT * FROM learning_modules WHERE isCompleted = 1 ORDER BY completedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"learning_modules"}, new Callable<List<LearningModuleEntity>>() {
      @Override
      @NonNull
      public List<LearningModuleEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfSkillName = CursorUtil.getColumnIndexOrThrow(_cursor, "skillName");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfContentUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "contentUrl");
          final int _cursorIndexOfContentType = CursorUtil.getColumnIndexOrThrow(_cursor, "contentType");
          final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
          final int _cursorIndexOfPrerequisites = CursorUtil.getColumnIndexOrThrow(_cursor, "prerequisites");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final int _cursorIndexOfProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "progress");
          final int _cursorIndexOfRecommendationScore = CursorUtil.getColumnIndexOrThrow(_cursor, "recommendationScore");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completedAt");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final List<LearningModuleEntity> _result = new ArrayList<LearningModuleEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final LearningModuleEntity _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpSkillName;
            if (_cursor.isNull(_cursorIndexOfSkillName)) {
              _tmpSkillName = null;
            } else {
              _tmpSkillName = _cursor.getString(_cursorIndexOfSkillName);
            }
            final int _tmpDuration;
            _tmpDuration = _cursor.getInt(_cursorIndexOfDuration);
            final String _tmpContentUrl;
            if (_cursor.isNull(_cursorIndexOfContentUrl)) {
              _tmpContentUrl = null;
            } else {
              _tmpContentUrl = _cursor.getString(_cursorIndexOfContentUrl);
            }
            final String _tmpContentType;
            if (_cursor.isNull(_cursorIndexOfContentType)) {
              _tmpContentType = null;
            } else {
              _tmpContentType = _cursor.getString(_cursorIndexOfContentType);
            }
            final String _tmpDifficulty;
            if (_cursor.isNull(_cursorIndexOfDifficulty)) {
              _tmpDifficulty = null;
            } else {
              _tmpDifficulty = _cursor.getString(_cursorIndexOfDifficulty);
            }
            final List<String> _tmpPrerequisites;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfPrerequisites)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfPrerequisites);
            }
            _tmpPrerequisites = __listConverters.toStringList(_tmp);
            final boolean _tmpIsCompleted;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp_1 != 0;
            final int _tmpProgress;
            _tmpProgress = _cursor.getInt(_cursorIndexOfProgress);
            final float _tmpRecommendationScore;
            _tmpRecommendationScore = _cursor.getFloat(_cursorIndexOfRecommendationScore);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final Long _tmpCompletedAt;
            if (_cursor.isNull(_cursorIndexOfCompletedAt)) {
              _tmpCompletedAt = null;
            } else {
              _tmpCompletedAt = _cursor.getLong(_cursorIndexOfCompletedAt);
            }
            final boolean _tmpIsSynced;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp_2 != 0;
            _item = new LearningModuleEntity(_tmpId,_tmpTitle,_tmpDescription,_tmpSkillName,_tmpDuration,_tmpContentUrl,_tmpContentType,_tmpDifficulty,_tmpPrerequisites,_tmpIsCompleted,_tmpProgress,_tmpRecommendationScore,_tmpCreatedAt,_tmpCompletedAt,_tmpIsSynced);
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
  public Object getUnsyncedModules(
      final Continuation<? super List<LearningModuleEntity>> $completion) {
    final String _sql = "SELECT * FROM learning_modules WHERE isSynced = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<LearningModuleEntity>>() {
      @Override
      @NonNull
      public List<LearningModuleEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfSkillName = CursorUtil.getColumnIndexOrThrow(_cursor, "skillName");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfContentUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "contentUrl");
          final int _cursorIndexOfContentType = CursorUtil.getColumnIndexOrThrow(_cursor, "contentType");
          final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
          final int _cursorIndexOfPrerequisites = CursorUtil.getColumnIndexOrThrow(_cursor, "prerequisites");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final int _cursorIndexOfProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "progress");
          final int _cursorIndexOfRecommendationScore = CursorUtil.getColumnIndexOrThrow(_cursor, "recommendationScore");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completedAt");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final List<LearningModuleEntity> _result = new ArrayList<LearningModuleEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final LearningModuleEntity _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpSkillName;
            if (_cursor.isNull(_cursorIndexOfSkillName)) {
              _tmpSkillName = null;
            } else {
              _tmpSkillName = _cursor.getString(_cursorIndexOfSkillName);
            }
            final int _tmpDuration;
            _tmpDuration = _cursor.getInt(_cursorIndexOfDuration);
            final String _tmpContentUrl;
            if (_cursor.isNull(_cursorIndexOfContentUrl)) {
              _tmpContentUrl = null;
            } else {
              _tmpContentUrl = _cursor.getString(_cursorIndexOfContentUrl);
            }
            final String _tmpContentType;
            if (_cursor.isNull(_cursorIndexOfContentType)) {
              _tmpContentType = null;
            } else {
              _tmpContentType = _cursor.getString(_cursorIndexOfContentType);
            }
            final String _tmpDifficulty;
            if (_cursor.isNull(_cursorIndexOfDifficulty)) {
              _tmpDifficulty = null;
            } else {
              _tmpDifficulty = _cursor.getString(_cursorIndexOfDifficulty);
            }
            final List<String> _tmpPrerequisites;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfPrerequisites)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfPrerequisites);
            }
            _tmpPrerequisites = __listConverters.toStringList(_tmp);
            final boolean _tmpIsCompleted;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp_1 != 0;
            final int _tmpProgress;
            _tmpProgress = _cursor.getInt(_cursorIndexOfProgress);
            final float _tmpRecommendationScore;
            _tmpRecommendationScore = _cursor.getFloat(_cursorIndexOfRecommendationScore);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final Long _tmpCompletedAt;
            if (_cursor.isNull(_cursorIndexOfCompletedAt)) {
              _tmpCompletedAt = null;
            } else {
              _tmpCompletedAt = _cursor.getLong(_cursorIndexOfCompletedAt);
            }
            final boolean _tmpIsSynced;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp_2 != 0;
            _item = new LearningModuleEntity(_tmpId,_tmpTitle,_tmpDescription,_tmpSkillName,_tmpDuration,_tmpContentUrl,_tmpContentType,_tmpDifficulty,_tmpPrerequisites,_tmpIsCompleted,_tmpProgress,_tmpRecommendationScore,_tmpCreatedAt,_tmpCompletedAt,_tmpIsSynced);
            _result.add(_item);
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
