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
import com.microcareer.match.data.local.entity.SkillProfileEntity;
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
public final class SkillProfileDao_Impl implements SkillProfileDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<SkillProfileEntity> __insertionAdapterOfSkillProfileEntity;

  private final EntityDeletionOrUpdateAdapter<SkillProfileEntity> __updateAdapterOfSkillProfileEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteSkill;

  private final SharedSQLiteStatement __preparedStmtOfUpdateSyncStatus;

  public SkillProfileDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSkillProfileEntity = new EntityInsertionAdapter<SkillProfileEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `skill_profiles` (`id`,`userId`,`skillName`,`proficiencyLevel`,`yearsOfExperience`,`sourceType`,`rawInput`,`confidence`,`createdAt`,`updatedAt`,`isSynced`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SkillProfileEntity entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getUserId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getUserId());
        }
        if (entity.getSkillName() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getSkillName());
        }
        if (entity.getProficiencyLevel() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getProficiencyLevel());
        }
        statement.bindLong(5, entity.getYearsOfExperience());
        if (entity.getSourceType() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getSourceType());
        }
        if (entity.getRawInput() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getRawInput());
        }
        statement.bindDouble(8, entity.getConfidence());
        statement.bindLong(9, entity.getCreatedAt());
        statement.bindLong(10, entity.getUpdatedAt());
        final int _tmp = entity.isSynced() ? 1 : 0;
        statement.bindLong(11, _tmp);
      }
    };
    this.__updateAdapterOfSkillProfileEntity = new EntityDeletionOrUpdateAdapter<SkillProfileEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `skill_profiles` SET `id` = ?,`userId` = ?,`skillName` = ?,`proficiencyLevel` = ?,`yearsOfExperience` = ?,`sourceType` = ?,`rawInput` = ?,`confidence` = ?,`createdAt` = ?,`updatedAt` = ?,`isSynced` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SkillProfileEntity entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getUserId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getUserId());
        }
        if (entity.getSkillName() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getSkillName());
        }
        if (entity.getProficiencyLevel() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getProficiencyLevel());
        }
        statement.bindLong(5, entity.getYearsOfExperience());
        if (entity.getSourceType() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getSourceType());
        }
        if (entity.getRawInput() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getRawInput());
        }
        statement.bindDouble(8, entity.getConfidence());
        statement.bindLong(9, entity.getCreatedAt());
        statement.bindLong(10, entity.getUpdatedAt());
        final int _tmp = entity.isSynced() ? 1 : 0;
        statement.bindLong(11, _tmp);
        statement.bindLong(12, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteSkill = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM skill_profiles WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateSyncStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE skill_profiles SET isSynced = ? WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertSkill(final SkillProfileEntity skill,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfSkillProfileEntity.insert(skill);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertSkills(final List<SkillProfileEntity> skills,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfSkillProfileEntity.insert(skills);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateSkill(final SkillProfileEntity skill,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfSkillProfileEntity.handle(skill);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteSkill(final long skillId, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteSkill.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, skillId);
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
          __preparedStmtOfDeleteSkill.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateSyncStatus(final long skillId, final boolean synced,
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
        _stmt.bindLong(_argIndex, skillId);
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
  public Flow<List<SkillProfileEntity>> getUserSkills(final String userId) {
    final String _sql = "SELECT * FROM skill_profiles WHERE userId = ? ORDER BY updatedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (userId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, userId);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"skill_profiles"}, new Callable<List<SkillProfileEntity>>() {
      @Override
      @NonNull
      public List<SkillProfileEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfSkillName = CursorUtil.getColumnIndexOrThrow(_cursor, "skillName");
          final int _cursorIndexOfProficiencyLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "proficiencyLevel");
          final int _cursorIndexOfYearsOfExperience = CursorUtil.getColumnIndexOrThrow(_cursor, "yearsOfExperience");
          final int _cursorIndexOfSourceType = CursorUtil.getColumnIndexOrThrow(_cursor, "sourceType");
          final int _cursorIndexOfRawInput = CursorUtil.getColumnIndexOrThrow(_cursor, "rawInput");
          final int _cursorIndexOfConfidence = CursorUtil.getColumnIndexOrThrow(_cursor, "confidence");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final List<SkillProfileEntity> _result = new ArrayList<SkillProfileEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SkillProfileEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUserId;
            if (_cursor.isNull(_cursorIndexOfUserId)) {
              _tmpUserId = null;
            } else {
              _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            }
            final String _tmpSkillName;
            if (_cursor.isNull(_cursorIndexOfSkillName)) {
              _tmpSkillName = null;
            } else {
              _tmpSkillName = _cursor.getString(_cursorIndexOfSkillName);
            }
            final String _tmpProficiencyLevel;
            if (_cursor.isNull(_cursorIndexOfProficiencyLevel)) {
              _tmpProficiencyLevel = null;
            } else {
              _tmpProficiencyLevel = _cursor.getString(_cursorIndexOfProficiencyLevel);
            }
            final int _tmpYearsOfExperience;
            _tmpYearsOfExperience = _cursor.getInt(_cursorIndexOfYearsOfExperience);
            final String _tmpSourceType;
            if (_cursor.isNull(_cursorIndexOfSourceType)) {
              _tmpSourceType = null;
            } else {
              _tmpSourceType = _cursor.getString(_cursorIndexOfSourceType);
            }
            final String _tmpRawInput;
            if (_cursor.isNull(_cursorIndexOfRawInput)) {
              _tmpRawInput = null;
            } else {
              _tmpRawInput = _cursor.getString(_cursorIndexOfRawInput);
            }
            final float _tmpConfidence;
            _tmpConfidence = _cursor.getFloat(_cursorIndexOfConfidence);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final boolean _tmpIsSynced;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp != 0;
            _item = new SkillProfileEntity(_tmpId,_tmpUserId,_tmpSkillName,_tmpProficiencyLevel,_tmpYearsOfExperience,_tmpSourceType,_tmpRawInput,_tmpConfidence,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsSynced);
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
  public Object getSkill(final String userId, final String skillName,
      final Continuation<? super SkillProfileEntity> $completion) {
    final String _sql = "SELECT * FROM skill_profiles WHERE userId = ? AND skillName = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (userId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, userId);
    }
    _argIndex = 2;
    if (skillName == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, skillName);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<SkillProfileEntity>() {
      @Override
      @Nullable
      public SkillProfileEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfSkillName = CursorUtil.getColumnIndexOrThrow(_cursor, "skillName");
          final int _cursorIndexOfProficiencyLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "proficiencyLevel");
          final int _cursorIndexOfYearsOfExperience = CursorUtil.getColumnIndexOrThrow(_cursor, "yearsOfExperience");
          final int _cursorIndexOfSourceType = CursorUtil.getColumnIndexOrThrow(_cursor, "sourceType");
          final int _cursorIndexOfRawInput = CursorUtil.getColumnIndexOrThrow(_cursor, "rawInput");
          final int _cursorIndexOfConfidence = CursorUtil.getColumnIndexOrThrow(_cursor, "confidence");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final SkillProfileEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUserId;
            if (_cursor.isNull(_cursorIndexOfUserId)) {
              _tmpUserId = null;
            } else {
              _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            }
            final String _tmpSkillName;
            if (_cursor.isNull(_cursorIndexOfSkillName)) {
              _tmpSkillName = null;
            } else {
              _tmpSkillName = _cursor.getString(_cursorIndexOfSkillName);
            }
            final String _tmpProficiencyLevel;
            if (_cursor.isNull(_cursorIndexOfProficiencyLevel)) {
              _tmpProficiencyLevel = null;
            } else {
              _tmpProficiencyLevel = _cursor.getString(_cursorIndexOfProficiencyLevel);
            }
            final int _tmpYearsOfExperience;
            _tmpYearsOfExperience = _cursor.getInt(_cursorIndexOfYearsOfExperience);
            final String _tmpSourceType;
            if (_cursor.isNull(_cursorIndexOfSourceType)) {
              _tmpSourceType = null;
            } else {
              _tmpSourceType = _cursor.getString(_cursorIndexOfSourceType);
            }
            final String _tmpRawInput;
            if (_cursor.isNull(_cursorIndexOfRawInput)) {
              _tmpRawInput = null;
            } else {
              _tmpRawInput = _cursor.getString(_cursorIndexOfRawInput);
            }
            final float _tmpConfidence;
            _tmpConfidence = _cursor.getFloat(_cursorIndexOfConfidence);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final boolean _tmpIsSynced;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp != 0;
            _result = new SkillProfileEntity(_tmpId,_tmpUserId,_tmpSkillName,_tmpProficiencyLevel,_tmpYearsOfExperience,_tmpSourceType,_tmpRawInput,_tmpConfidence,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsSynced);
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
  public Object getUnsyncedSkills(
      final Continuation<? super List<SkillProfileEntity>> $completion) {
    final String _sql = "SELECT * FROM skill_profiles WHERE isSynced = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<SkillProfileEntity>>() {
      @Override
      @NonNull
      public List<SkillProfileEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfSkillName = CursorUtil.getColumnIndexOrThrow(_cursor, "skillName");
          final int _cursorIndexOfProficiencyLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "proficiencyLevel");
          final int _cursorIndexOfYearsOfExperience = CursorUtil.getColumnIndexOrThrow(_cursor, "yearsOfExperience");
          final int _cursorIndexOfSourceType = CursorUtil.getColumnIndexOrThrow(_cursor, "sourceType");
          final int _cursorIndexOfRawInput = CursorUtil.getColumnIndexOrThrow(_cursor, "rawInput");
          final int _cursorIndexOfConfidence = CursorUtil.getColumnIndexOrThrow(_cursor, "confidence");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final List<SkillProfileEntity> _result = new ArrayList<SkillProfileEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SkillProfileEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUserId;
            if (_cursor.isNull(_cursorIndexOfUserId)) {
              _tmpUserId = null;
            } else {
              _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            }
            final String _tmpSkillName;
            if (_cursor.isNull(_cursorIndexOfSkillName)) {
              _tmpSkillName = null;
            } else {
              _tmpSkillName = _cursor.getString(_cursorIndexOfSkillName);
            }
            final String _tmpProficiencyLevel;
            if (_cursor.isNull(_cursorIndexOfProficiencyLevel)) {
              _tmpProficiencyLevel = null;
            } else {
              _tmpProficiencyLevel = _cursor.getString(_cursorIndexOfProficiencyLevel);
            }
            final int _tmpYearsOfExperience;
            _tmpYearsOfExperience = _cursor.getInt(_cursorIndexOfYearsOfExperience);
            final String _tmpSourceType;
            if (_cursor.isNull(_cursorIndexOfSourceType)) {
              _tmpSourceType = null;
            } else {
              _tmpSourceType = _cursor.getString(_cursorIndexOfSourceType);
            }
            final String _tmpRawInput;
            if (_cursor.isNull(_cursorIndexOfRawInput)) {
              _tmpRawInput = null;
            } else {
              _tmpRawInput = _cursor.getString(_cursorIndexOfRawInput);
            }
            final float _tmpConfidence;
            _tmpConfidence = _cursor.getFloat(_cursorIndexOfConfidence);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final boolean _tmpIsSynced;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp != 0;
            _item = new SkillProfileEntity(_tmpId,_tmpUserId,_tmpSkillName,_tmpProficiencyLevel,_tmpYearsOfExperience,_tmpSourceType,_tmpRawInput,_tmpConfidence,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsSynced);
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
