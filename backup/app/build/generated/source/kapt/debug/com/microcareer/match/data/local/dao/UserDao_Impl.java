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
import com.microcareer.match.data.local.entity.UserEntity;
import java.lang.Class;
import java.lang.Double;
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
public final class UserDao_Impl implements UserDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<UserEntity> __insertionAdapterOfUserEntity;

  private final ListConverters __listConverters = new ListConverters();

  private final EntityDeletionOrUpdateAdapter<UserEntity> __updateAdapterOfUserEntity;

  private final SharedSQLiteStatement __preparedStmtOfUpdateSyncStatus;

  private final SharedSQLiteStatement __preparedStmtOfDeleteUser;

  private final SharedSQLiteStatement __preparedStmtOfClearAll;

  public UserDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUserEntity = new EntityInsertionAdapter<UserEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `users` (`id`,`email`,`fullName`,`role`,`phoneNumber`,`profilePictureUrl`,`location`,`latitude`,`longitude`,`availableHours`,`companyName`,`rating`,`completedJobs`,`skills`,`createdAt`,`updatedAt`,`isSynced`,`bio`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final UserEntity entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        if (entity.getEmail() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getEmail());
        }
        if (entity.getFullName() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getFullName());
        }
        if (entity.getRole() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getRole());
        }
        if (entity.getPhoneNumber() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getPhoneNumber());
        }
        if (entity.getProfilePictureUrl() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getProfilePictureUrl());
        }
        if (entity.getLocation() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getLocation());
        }
        if (entity.getLatitude() == null) {
          statement.bindNull(8);
        } else {
          statement.bindDouble(8, entity.getLatitude());
        }
        if (entity.getLongitude() == null) {
          statement.bindNull(9);
        } else {
          statement.bindDouble(9, entity.getLongitude());
        }
        if (entity.getAvailableHours() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getAvailableHours());
        }
        if (entity.getCompanyName() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getCompanyName());
        }
        statement.bindDouble(12, entity.getRating());
        statement.bindLong(13, entity.getCompletedJobs());
        final String _tmp = __listConverters.fromStringList(entity.getSkills());
        if (_tmp == null) {
          statement.bindNull(14);
        } else {
          statement.bindString(14, _tmp);
        }
        statement.bindLong(15, entity.getCreatedAt());
        statement.bindLong(16, entity.getUpdatedAt());
        final int _tmp_1 = entity.isSynced() ? 1 : 0;
        statement.bindLong(17, _tmp_1);
        if (entity.getBio() == null) {
          statement.bindNull(18);
        } else {
          statement.bindString(18, entity.getBio());
        }
      }
    };
    this.__updateAdapterOfUserEntity = new EntityDeletionOrUpdateAdapter<UserEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `users` SET `id` = ?,`email` = ?,`fullName` = ?,`role` = ?,`phoneNumber` = ?,`profilePictureUrl` = ?,`location` = ?,`latitude` = ?,`longitude` = ?,`availableHours` = ?,`companyName` = ?,`rating` = ?,`completedJobs` = ?,`skills` = ?,`createdAt` = ?,`updatedAt` = ?,`isSynced` = ?,`bio` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final UserEntity entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        if (entity.getEmail() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getEmail());
        }
        if (entity.getFullName() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getFullName());
        }
        if (entity.getRole() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getRole());
        }
        if (entity.getPhoneNumber() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getPhoneNumber());
        }
        if (entity.getProfilePictureUrl() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getProfilePictureUrl());
        }
        if (entity.getLocation() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getLocation());
        }
        if (entity.getLatitude() == null) {
          statement.bindNull(8);
        } else {
          statement.bindDouble(8, entity.getLatitude());
        }
        if (entity.getLongitude() == null) {
          statement.bindNull(9);
        } else {
          statement.bindDouble(9, entity.getLongitude());
        }
        if (entity.getAvailableHours() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getAvailableHours());
        }
        if (entity.getCompanyName() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getCompanyName());
        }
        statement.bindDouble(12, entity.getRating());
        statement.bindLong(13, entity.getCompletedJobs());
        final String _tmp = __listConverters.fromStringList(entity.getSkills());
        if (_tmp == null) {
          statement.bindNull(14);
        } else {
          statement.bindString(14, _tmp);
        }
        statement.bindLong(15, entity.getCreatedAt());
        statement.bindLong(16, entity.getUpdatedAt());
        final int _tmp_1 = entity.isSynced() ? 1 : 0;
        statement.bindLong(17, _tmp_1);
        if (entity.getBio() == null) {
          statement.bindNull(18);
        } else {
          statement.bindString(18, entity.getBio());
        }
        if (entity.getId() == null) {
          statement.bindNull(19);
        } else {
          statement.bindString(19, entity.getId());
        }
      }
    };
    this.__preparedStmtOfUpdateSyncStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE users SET isSynced = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteUser = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM users WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfClearAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM users";
        return _query;
      }
    };
  }

  @Override
  public Object insertUser(final UserEntity user, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfUserEntity.insert(user);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateUser(final UserEntity user, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfUserEntity.handle(user);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateSyncStatus(final String userId, final boolean synced,
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
        if (userId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, userId);
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
  public Object deleteUser(final String userId, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteUser.acquire();
        int _argIndex = 1;
        if (userId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, userId);
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
          __preparedStmtOfDeleteUser.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object clearAll(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfClearAll.acquire();
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
          __preparedStmtOfClearAll.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<UserEntity> getUser(final String userId) {
    final String _sql = "SELECT * FROM users WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (userId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, userId);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"users"}, new Callable<UserEntity>() {
      @Override
      @Nullable
      public UserEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfFullName = CursorUtil.getColumnIndexOrThrow(_cursor, "fullName");
          final int _cursorIndexOfRole = CursorUtil.getColumnIndexOrThrow(_cursor, "role");
          final int _cursorIndexOfPhoneNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneNumber");
          final int _cursorIndexOfProfilePictureUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "profilePictureUrl");
          final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfAvailableHours = CursorUtil.getColumnIndexOrThrow(_cursor, "availableHours");
          final int _cursorIndexOfCompanyName = CursorUtil.getColumnIndexOrThrow(_cursor, "companyName");
          final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
          final int _cursorIndexOfCompletedJobs = CursorUtil.getColumnIndexOrThrow(_cursor, "completedJobs");
          final int _cursorIndexOfSkills = CursorUtil.getColumnIndexOrThrow(_cursor, "skills");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final int _cursorIndexOfBio = CursorUtil.getColumnIndexOrThrow(_cursor, "bio");
          final UserEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpEmail;
            if (_cursor.isNull(_cursorIndexOfEmail)) {
              _tmpEmail = null;
            } else {
              _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            }
            final String _tmpFullName;
            if (_cursor.isNull(_cursorIndexOfFullName)) {
              _tmpFullName = null;
            } else {
              _tmpFullName = _cursor.getString(_cursorIndexOfFullName);
            }
            final String _tmpRole;
            if (_cursor.isNull(_cursorIndexOfRole)) {
              _tmpRole = null;
            } else {
              _tmpRole = _cursor.getString(_cursorIndexOfRole);
            }
            final String _tmpPhoneNumber;
            if (_cursor.isNull(_cursorIndexOfPhoneNumber)) {
              _tmpPhoneNumber = null;
            } else {
              _tmpPhoneNumber = _cursor.getString(_cursorIndexOfPhoneNumber);
            }
            final String _tmpProfilePictureUrl;
            if (_cursor.isNull(_cursorIndexOfProfilePictureUrl)) {
              _tmpProfilePictureUrl = null;
            } else {
              _tmpProfilePictureUrl = _cursor.getString(_cursorIndexOfProfilePictureUrl);
            }
            final String _tmpLocation;
            if (_cursor.isNull(_cursorIndexOfLocation)) {
              _tmpLocation = null;
            } else {
              _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
            }
            final Double _tmpLatitude;
            if (_cursor.isNull(_cursorIndexOfLatitude)) {
              _tmpLatitude = null;
            } else {
              _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            }
            final Double _tmpLongitude;
            if (_cursor.isNull(_cursorIndexOfLongitude)) {
              _tmpLongitude = null;
            } else {
              _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            }
            final String _tmpAvailableHours;
            if (_cursor.isNull(_cursorIndexOfAvailableHours)) {
              _tmpAvailableHours = null;
            } else {
              _tmpAvailableHours = _cursor.getString(_cursorIndexOfAvailableHours);
            }
            final String _tmpCompanyName;
            if (_cursor.isNull(_cursorIndexOfCompanyName)) {
              _tmpCompanyName = null;
            } else {
              _tmpCompanyName = _cursor.getString(_cursorIndexOfCompanyName);
            }
            final float _tmpRating;
            _tmpRating = _cursor.getFloat(_cursorIndexOfRating);
            final int _tmpCompletedJobs;
            _tmpCompletedJobs = _cursor.getInt(_cursorIndexOfCompletedJobs);
            final List<String> _tmpSkills;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfSkills)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfSkills);
            }
            _tmpSkills = __listConverters.toStringList(_tmp);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final boolean _tmpIsSynced;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp_1 != 0;
            final String _tmpBio;
            if (_cursor.isNull(_cursorIndexOfBio)) {
              _tmpBio = null;
            } else {
              _tmpBio = _cursor.getString(_cursorIndexOfBio);
            }
            _result = new UserEntity(_tmpId,_tmpEmail,_tmpFullName,_tmpRole,_tmpPhoneNumber,_tmpProfilePictureUrl,_tmpLocation,_tmpLatitude,_tmpLongitude,_tmpAvailableHours,_tmpCompanyName,_tmpRating,_tmpCompletedJobs,_tmpSkills,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsSynced,_tmpBio);
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
  public Object getUserOnce(final String userId,
      final Continuation<? super UserEntity> $completion) {
    final String _sql = "SELECT * FROM users WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (userId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, userId);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<UserEntity>() {
      @Override
      @Nullable
      public UserEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfFullName = CursorUtil.getColumnIndexOrThrow(_cursor, "fullName");
          final int _cursorIndexOfRole = CursorUtil.getColumnIndexOrThrow(_cursor, "role");
          final int _cursorIndexOfPhoneNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneNumber");
          final int _cursorIndexOfProfilePictureUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "profilePictureUrl");
          final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfAvailableHours = CursorUtil.getColumnIndexOrThrow(_cursor, "availableHours");
          final int _cursorIndexOfCompanyName = CursorUtil.getColumnIndexOrThrow(_cursor, "companyName");
          final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
          final int _cursorIndexOfCompletedJobs = CursorUtil.getColumnIndexOrThrow(_cursor, "completedJobs");
          final int _cursorIndexOfSkills = CursorUtil.getColumnIndexOrThrow(_cursor, "skills");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final int _cursorIndexOfBio = CursorUtil.getColumnIndexOrThrow(_cursor, "bio");
          final UserEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpEmail;
            if (_cursor.isNull(_cursorIndexOfEmail)) {
              _tmpEmail = null;
            } else {
              _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            }
            final String _tmpFullName;
            if (_cursor.isNull(_cursorIndexOfFullName)) {
              _tmpFullName = null;
            } else {
              _tmpFullName = _cursor.getString(_cursorIndexOfFullName);
            }
            final String _tmpRole;
            if (_cursor.isNull(_cursorIndexOfRole)) {
              _tmpRole = null;
            } else {
              _tmpRole = _cursor.getString(_cursorIndexOfRole);
            }
            final String _tmpPhoneNumber;
            if (_cursor.isNull(_cursorIndexOfPhoneNumber)) {
              _tmpPhoneNumber = null;
            } else {
              _tmpPhoneNumber = _cursor.getString(_cursorIndexOfPhoneNumber);
            }
            final String _tmpProfilePictureUrl;
            if (_cursor.isNull(_cursorIndexOfProfilePictureUrl)) {
              _tmpProfilePictureUrl = null;
            } else {
              _tmpProfilePictureUrl = _cursor.getString(_cursorIndexOfProfilePictureUrl);
            }
            final String _tmpLocation;
            if (_cursor.isNull(_cursorIndexOfLocation)) {
              _tmpLocation = null;
            } else {
              _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
            }
            final Double _tmpLatitude;
            if (_cursor.isNull(_cursorIndexOfLatitude)) {
              _tmpLatitude = null;
            } else {
              _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            }
            final Double _tmpLongitude;
            if (_cursor.isNull(_cursorIndexOfLongitude)) {
              _tmpLongitude = null;
            } else {
              _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            }
            final String _tmpAvailableHours;
            if (_cursor.isNull(_cursorIndexOfAvailableHours)) {
              _tmpAvailableHours = null;
            } else {
              _tmpAvailableHours = _cursor.getString(_cursorIndexOfAvailableHours);
            }
            final String _tmpCompanyName;
            if (_cursor.isNull(_cursorIndexOfCompanyName)) {
              _tmpCompanyName = null;
            } else {
              _tmpCompanyName = _cursor.getString(_cursorIndexOfCompanyName);
            }
            final float _tmpRating;
            _tmpRating = _cursor.getFloat(_cursorIndexOfRating);
            final int _tmpCompletedJobs;
            _tmpCompletedJobs = _cursor.getInt(_cursorIndexOfCompletedJobs);
            final List<String> _tmpSkills;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfSkills)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfSkills);
            }
            _tmpSkills = __listConverters.toStringList(_tmp);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final boolean _tmpIsSynced;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp_1 != 0;
            final String _tmpBio;
            if (_cursor.isNull(_cursorIndexOfBio)) {
              _tmpBio = null;
            } else {
              _tmpBio = _cursor.getString(_cursorIndexOfBio);
            }
            _result = new UserEntity(_tmpId,_tmpEmail,_tmpFullName,_tmpRole,_tmpPhoneNumber,_tmpProfilePictureUrl,_tmpLocation,_tmpLatitude,_tmpLongitude,_tmpAvailableHours,_tmpCompanyName,_tmpRating,_tmpCompletedJobs,_tmpSkills,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsSynced,_tmpBio);
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
  public Object getUnsyncedUsers(final Continuation<? super List<UserEntity>> $completion) {
    final String _sql = "SELECT * FROM users WHERE isSynced = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<UserEntity>>() {
      @Override
      @NonNull
      public List<UserEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfFullName = CursorUtil.getColumnIndexOrThrow(_cursor, "fullName");
          final int _cursorIndexOfRole = CursorUtil.getColumnIndexOrThrow(_cursor, "role");
          final int _cursorIndexOfPhoneNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneNumber");
          final int _cursorIndexOfProfilePictureUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "profilePictureUrl");
          final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfAvailableHours = CursorUtil.getColumnIndexOrThrow(_cursor, "availableHours");
          final int _cursorIndexOfCompanyName = CursorUtil.getColumnIndexOrThrow(_cursor, "companyName");
          final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
          final int _cursorIndexOfCompletedJobs = CursorUtil.getColumnIndexOrThrow(_cursor, "completedJobs");
          final int _cursorIndexOfSkills = CursorUtil.getColumnIndexOrThrow(_cursor, "skills");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final int _cursorIndexOfBio = CursorUtil.getColumnIndexOrThrow(_cursor, "bio");
          final List<UserEntity> _result = new ArrayList<UserEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final UserEntity _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpEmail;
            if (_cursor.isNull(_cursorIndexOfEmail)) {
              _tmpEmail = null;
            } else {
              _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            }
            final String _tmpFullName;
            if (_cursor.isNull(_cursorIndexOfFullName)) {
              _tmpFullName = null;
            } else {
              _tmpFullName = _cursor.getString(_cursorIndexOfFullName);
            }
            final String _tmpRole;
            if (_cursor.isNull(_cursorIndexOfRole)) {
              _tmpRole = null;
            } else {
              _tmpRole = _cursor.getString(_cursorIndexOfRole);
            }
            final String _tmpPhoneNumber;
            if (_cursor.isNull(_cursorIndexOfPhoneNumber)) {
              _tmpPhoneNumber = null;
            } else {
              _tmpPhoneNumber = _cursor.getString(_cursorIndexOfPhoneNumber);
            }
            final String _tmpProfilePictureUrl;
            if (_cursor.isNull(_cursorIndexOfProfilePictureUrl)) {
              _tmpProfilePictureUrl = null;
            } else {
              _tmpProfilePictureUrl = _cursor.getString(_cursorIndexOfProfilePictureUrl);
            }
            final String _tmpLocation;
            if (_cursor.isNull(_cursorIndexOfLocation)) {
              _tmpLocation = null;
            } else {
              _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
            }
            final Double _tmpLatitude;
            if (_cursor.isNull(_cursorIndexOfLatitude)) {
              _tmpLatitude = null;
            } else {
              _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            }
            final Double _tmpLongitude;
            if (_cursor.isNull(_cursorIndexOfLongitude)) {
              _tmpLongitude = null;
            } else {
              _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            }
            final String _tmpAvailableHours;
            if (_cursor.isNull(_cursorIndexOfAvailableHours)) {
              _tmpAvailableHours = null;
            } else {
              _tmpAvailableHours = _cursor.getString(_cursorIndexOfAvailableHours);
            }
            final String _tmpCompanyName;
            if (_cursor.isNull(_cursorIndexOfCompanyName)) {
              _tmpCompanyName = null;
            } else {
              _tmpCompanyName = _cursor.getString(_cursorIndexOfCompanyName);
            }
            final float _tmpRating;
            _tmpRating = _cursor.getFloat(_cursorIndexOfRating);
            final int _tmpCompletedJobs;
            _tmpCompletedJobs = _cursor.getInt(_cursorIndexOfCompletedJobs);
            final List<String> _tmpSkills;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfSkills)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfSkills);
            }
            _tmpSkills = __listConverters.toStringList(_tmp);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final boolean _tmpIsSynced;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp_1 != 0;
            final String _tmpBio;
            if (_cursor.isNull(_cursorIndexOfBio)) {
              _tmpBio = null;
            } else {
              _tmpBio = _cursor.getString(_cursorIndexOfBio);
            }
            _item = new UserEntity(_tmpId,_tmpEmail,_tmpFullName,_tmpRole,_tmpPhoneNumber,_tmpProfilePictureUrl,_tmpLocation,_tmpLatitude,_tmpLongitude,_tmpAvailableHours,_tmpCompanyName,_tmpRating,_tmpCompletedJobs,_tmpSkills,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsSynced,_tmpBio);
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
