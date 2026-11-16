package com.microcareer.match.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomDatabaseKt;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.microcareer.match.data.local.converter.ListConverters;
import com.microcareer.match.data.local.entity.JobEntity;
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
public final class JobDao_Impl implements JobDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<JobEntity> __insertionAdapterOfJobEntity;

  private final ListConverters __listConverters = new ListConverters();

  private final EntityDeletionOrUpdateAdapter<JobEntity> __updateAdapterOfJobEntity;

  private final SharedSQLiteStatement __preparedStmtOfUpdateSavedStatus;

  private final SharedSQLiteStatement __preparedStmtOfUpdateAppliedStatus;

  private final SharedSQLiteStatement __preparedStmtOfUpdateSyncStatus;

  private final SharedSQLiteStatement __preparedStmtOfDeleteJob;

  private final SharedSQLiteStatement __preparedStmtOfDeleteOldJobs;

  private final SharedSQLiteStatement __preparedStmtOfUpdateJobStatus;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllJobs;

  private final SharedSQLiteStatement __preparedStmtOfClearAllJobs;

  private final SharedSQLiteStatement __preparedStmtOfUpdateMatchScore;

  public JobDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfJobEntity = new EntityInsertionAdapter<JobEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `jobs` (`id`,`title`,`description`,`employerId`,`employerName`,`location`,`latitude`,`longitude`,`requiredSkills`,`payment`,`currency`,`duration`,`category`,`matchScore`,`status`,`deadline`,`createdAt`,`updatedAt`,`isSaved`,`isApplied`,`isSynced`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final JobEntity entity) {
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
        if (entity.getEmployerId() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getEmployerId());
        }
        if (entity.getEmployerName() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getEmployerName());
        }
        if (entity.getLocation() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getLocation());
        }
        statement.bindDouble(7, entity.getLatitude());
        statement.bindDouble(8, entity.getLongitude());
        final String _tmp = __listConverters.fromStringList(entity.getRequiredSkills());
        if (_tmp == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, _tmp);
        }
        statement.bindDouble(10, entity.getPayment());
        if (entity.getCurrency() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getCurrency());
        }
        statement.bindLong(12, entity.getDuration());
        if (entity.getCategory() == null) {
          statement.bindNull(13);
        } else {
          statement.bindString(13, entity.getCategory());
        }
        statement.bindDouble(14, entity.getMatchScore());
        if (entity.getStatus() == null) {
          statement.bindNull(15);
        } else {
          statement.bindString(15, entity.getStatus());
        }
        if (entity.getDeadline() == null) {
          statement.bindNull(16);
        } else {
          statement.bindLong(16, entity.getDeadline());
        }
        statement.bindLong(17, entity.getCreatedAt());
        statement.bindLong(18, entity.getUpdatedAt());
        final int _tmp_1 = entity.isSaved() ? 1 : 0;
        statement.bindLong(19, _tmp_1);
        final int _tmp_2 = entity.isApplied() ? 1 : 0;
        statement.bindLong(20, _tmp_2);
        final int _tmp_3 = entity.isSynced() ? 1 : 0;
        statement.bindLong(21, _tmp_3);
      }
    };
    this.__updateAdapterOfJobEntity = new EntityDeletionOrUpdateAdapter<JobEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `jobs` SET `id` = ?,`title` = ?,`description` = ?,`employerId` = ?,`employerName` = ?,`location` = ?,`latitude` = ?,`longitude` = ?,`requiredSkills` = ?,`payment` = ?,`currency` = ?,`duration` = ?,`category` = ?,`matchScore` = ?,`status` = ?,`deadline` = ?,`createdAt` = ?,`updatedAt` = ?,`isSaved` = ?,`isApplied` = ?,`isSynced` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final JobEntity entity) {
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
        if (entity.getEmployerId() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getEmployerId());
        }
        if (entity.getEmployerName() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getEmployerName());
        }
        if (entity.getLocation() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getLocation());
        }
        statement.bindDouble(7, entity.getLatitude());
        statement.bindDouble(8, entity.getLongitude());
        final String _tmp = __listConverters.fromStringList(entity.getRequiredSkills());
        if (_tmp == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, _tmp);
        }
        statement.bindDouble(10, entity.getPayment());
        if (entity.getCurrency() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getCurrency());
        }
        statement.bindLong(12, entity.getDuration());
        if (entity.getCategory() == null) {
          statement.bindNull(13);
        } else {
          statement.bindString(13, entity.getCategory());
        }
        statement.bindDouble(14, entity.getMatchScore());
        if (entity.getStatus() == null) {
          statement.bindNull(15);
        } else {
          statement.bindString(15, entity.getStatus());
        }
        if (entity.getDeadline() == null) {
          statement.bindNull(16);
        } else {
          statement.bindLong(16, entity.getDeadline());
        }
        statement.bindLong(17, entity.getCreatedAt());
        statement.bindLong(18, entity.getUpdatedAt());
        final int _tmp_1 = entity.isSaved() ? 1 : 0;
        statement.bindLong(19, _tmp_1);
        final int _tmp_2 = entity.isApplied() ? 1 : 0;
        statement.bindLong(20, _tmp_2);
        final int _tmp_3 = entity.isSynced() ? 1 : 0;
        statement.bindLong(21, _tmp_3);
        if (entity.getId() == null) {
          statement.bindNull(22);
        } else {
          statement.bindString(22, entity.getId());
        }
      }
    };
    this.__preparedStmtOfUpdateSavedStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE jobs SET isSaved = ?, updatedAt = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateAppliedStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE jobs SET isApplied = ?, updatedAt = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateSyncStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE jobs SET isSynced = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteJob = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM jobs WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteOldJobs = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM jobs WHERE status != 'APPLIED' AND updatedAt < ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateJobStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE jobs SET status = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAllJobs = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM jobs WHERE isApplied = 0";
        return _query;
      }
    };
    this.__preparedStmtOfClearAllJobs = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM jobs";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateMatchScore = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE jobs SET matchScore = ? WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertJob(final JobEntity job, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfJobEntity.insert(job);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertJobs(final List<JobEntity> jobs,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfJobEntity.insert(jobs);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateJob(final JobEntity job, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfJobEntity.handle(job);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object replaceAllJobs(final List<JobEntity> jobs,
      final Continuation<? super Unit> $completion) {
    return RoomDatabaseKt.withTransaction(__db, (__cont) -> JobDao.DefaultImpls.replaceAllJobs(JobDao_Impl.this, jobs, __cont), $completion);
  }

  @Override
  public Object updateSavedStatus(final String jobId, final boolean saved, final long timestamp,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateSavedStatus.acquire();
        int _argIndex = 1;
        final int _tmp = saved ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, timestamp);
        _argIndex = 3;
        if (jobId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, jobId);
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
          __preparedStmtOfUpdateSavedStatus.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateAppliedStatus(final String jobId, final boolean applied, final long timestamp,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateAppliedStatus.acquire();
        int _argIndex = 1;
        final int _tmp = applied ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, timestamp);
        _argIndex = 3;
        if (jobId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, jobId);
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
          __preparedStmtOfUpdateAppliedStatus.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateSyncStatus(final String jobId, final boolean synced,
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
        if (jobId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, jobId);
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
  public Object deleteJob(final String jobId, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteJob.acquire();
        int _argIndex = 1;
        if (jobId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, jobId);
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
          __preparedStmtOfDeleteJob.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteOldJobs(final long timestamp, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteOldJobs.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, timestamp);
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
          __preparedStmtOfDeleteOldJobs.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateJobStatus(final String jobId, final String status,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateJobStatus.acquire();
        int _argIndex = 1;
        if (status == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, status);
        }
        _argIndex = 2;
        if (jobId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, jobId);
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
          __preparedStmtOfUpdateJobStatus.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAllJobs(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllJobs.acquire();
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
          __preparedStmtOfDeleteAllJobs.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object clearAllJobs(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfClearAllJobs.acquire();
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
          __preparedStmtOfClearAllJobs.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateMatchScore(final String jobId, final float score,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateMatchScore.acquire();
        int _argIndex = 1;
        _stmt.bindDouble(_argIndex, score);
        _argIndex = 2;
        if (jobId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, jobId);
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
          __preparedStmtOfUpdateMatchScore.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getAllJobs(final Continuation<? super List<JobEntity>> $completion) {
    final String _sql = "SELECT * FROM jobs";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<JobEntity>>() {
      @Override
      @NonNull
      public List<JobEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfEmployerId = CursorUtil.getColumnIndexOrThrow(_cursor, "employerId");
          final int _cursorIndexOfEmployerName = CursorUtil.getColumnIndexOrThrow(_cursor, "employerName");
          final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfRequiredSkills = CursorUtil.getColumnIndexOrThrow(_cursor, "requiredSkills");
          final int _cursorIndexOfPayment = CursorUtil.getColumnIndexOrThrow(_cursor, "payment");
          final int _cursorIndexOfCurrency = CursorUtil.getColumnIndexOrThrow(_cursor, "currency");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfMatchScore = CursorUtil.getColumnIndexOrThrow(_cursor, "matchScore");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfDeadline = CursorUtil.getColumnIndexOrThrow(_cursor, "deadline");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfIsSaved = CursorUtil.getColumnIndexOrThrow(_cursor, "isSaved");
          final int _cursorIndexOfIsApplied = CursorUtil.getColumnIndexOrThrow(_cursor, "isApplied");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final List<JobEntity> _result = new ArrayList<JobEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final JobEntity _item;
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
            final String _tmpEmployerId;
            if (_cursor.isNull(_cursorIndexOfEmployerId)) {
              _tmpEmployerId = null;
            } else {
              _tmpEmployerId = _cursor.getString(_cursorIndexOfEmployerId);
            }
            final String _tmpEmployerName;
            if (_cursor.isNull(_cursorIndexOfEmployerName)) {
              _tmpEmployerName = null;
            } else {
              _tmpEmployerName = _cursor.getString(_cursorIndexOfEmployerName);
            }
            final String _tmpLocation;
            if (_cursor.isNull(_cursorIndexOfLocation)) {
              _tmpLocation = null;
            } else {
              _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
            }
            final double _tmpLatitude;
            _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            final double _tmpLongitude;
            _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            final List<String> _tmpRequiredSkills;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfRequiredSkills)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfRequiredSkills);
            }
            _tmpRequiredSkills = __listConverters.toStringList(_tmp);
            final double _tmpPayment;
            _tmpPayment = _cursor.getDouble(_cursorIndexOfPayment);
            final String _tmpCurrency;
            if (_cursor.isNull(_cursorIndexOfCurrency)) {
              _tmpCurrency = null;
            } else {
              _tmpCurrency = _cursor.getString(_cursorIndexOfCurrency);
            }
            final long _tmpDuration;
            _tmpDuration = _cursor.getLong(_cursorIndexOfDuration);
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            }
            final float _tmpMatchScore;
            _tmpMatchScore = _cursor.getFloat(_cursorIndexOfMatchScore);
            final String _tmpStatus;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmpStatus = null;
            } else {
              _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            }
            final Long _tmpDeadline;
            if (_cursor.isNull(_cursorIndexOfDeadline)) {
              _tmpDeadline = null;
            } else {
              _tmpDeadline = _cursor.getLong(_cursorIndexOfDeadline);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final boolean _tmpIsSaved;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsSaved);
            _tmpIsSaved = _tmp_1 != 0;
            final boolean _tmpIsApplied;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsApplied);
            _tmpIsApplied = _tmp_2 != 0;
            final boolean _tmpIsSynced;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp_3 != 0;
            _item = new JobEntity(_tmpId,_tmpTitle,_tmpDescription,_tmpEmployerId,_tmpEmployerName,_tmpLocation,_tmpLatitude,_tmpLongitude,_tmpRequiredSkills,_tmpPayment,_tmpCurrency,_tmpDuration,_tmpCategory,_tmpMatchScore,_tmpStatus,_tmpDeadline,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsSaved,_tmpIsApplied,_tmpIsSynced);
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

  @Override
  public Flow<List<JobEntity>> getAllAvailableJobs() {
    final String _sql = "SELECT * FROM jobs WHERE status = 'OPEN' ORDER BY matchScore DESC, createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"jobs"}, new Callable<List<JobEntity>>() {
      @Override
      @NonNull
      public List<JobEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfEmployerId = CursorUtil.getColumnIndexOrThrow(_cursor, "employerId");
          final int _cursorIndexOfEmployerName = CursorUtil.getColumnIndexOrThrow(_cursor, "employerName");
          final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfRequiredSkills = CursorUtil.getColumnIndexOrThrow(_cursor, "requiredSkills");
          final int _cursorIndexOfPayment = CursorUtil.getColumnIndexOrThrow(_cursor, "payment");
          final int _cursorIndexOfCurrency = CursorUtil.getColumnIndexOrThrow(_cursor, "currency");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfMatchScore = CursorUtil.getColumnIndexOrThrow(_cursor, "matchScore");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfDeadline = CursorUtil.getColumnIndexOrThrow(_cursor, "deadline");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfIsSaved = CursorUtil.getColumnIndexOrThrow(_cursor, "isSaved");
          final int _cursorIndexOfIsApplied = CursorUtil.getColumnIndexOrThrow(_cursor, "isApplied");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final List<JobEntity> _result = new ArrayList<JobEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final JobEntity _item;
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
            final String _tmpEmployerId;
            if (_cursor.isNull(_cursorIndexOfEmployerId)) {
              _tmpEmployerId = null;
            } else {
              _tmpEmployerId = _cursor.getString(_cursorIndexOfEmployerId);
            }
            final String _tmpEmployerName;
            if (_cursor.isNull(_cursorIndexOfEmployerName)) {
              _tmpEmployerName = null;
            } else {
              _tmpEmployerName = _cursor.getString(_cursorIndexOfEmployerName);
            }
            final String _tmpLocation;
            if (_cursor.isNull(_cursorIndexOfLocation)) {
              _tmpLocation = null;
            } else {
              _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
            }
            final double _tmpLatitude;
            _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            final double _tmpLongitude;
            _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            final List<String> _tmpRequiredSkills;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfRequiredSkills)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfRequiredSkills);
            }
            _tmpRequiredSkills = __listConverters.toStringList(_tmp);
            final double _tmpPayment;
            _tmpPayment = _cursor.getDouble(_cursorIndexOfPayment);
            final String _tmpCurrency;
            if (_cursor.isNull(_cursorIndexOfCurrency)) {
              _tmpCurrency = null;
            } else {
              _tmpCurrency = _cursor.getString(_cursorIndexOfCurrency);
            }
            final long _tmpDuration;
            _tmpDuration = _cursor.getLong(_cursorIndexOfDuration);
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            }
            final float _tmpMatchScore;
            _tmpMatchScore = _cursor.getFloat(_cursorIndexOfMatchScore);
            final String _tmpStatus;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmpStatus = null;
            } else {
              _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            }
            final Long _tmpDeadline;
            if (_cursor.isNull(_cursorIndexOfDeadline)) {
              _tmpDeadline = null;
            } else {
              _tmpDeadline = _cursor.getLong(_cursorIndexOfDeadline);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final boolean _tmpIsSaved;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsSaved);
            _tmpIsSaved = _tmp_1 != 0;
            final boolean _tmpIsApplied;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsApplied);
            _tmpIsApplied = _tmp_2 != 0;
            final boolean _tmpIsSynced;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp_3 != 0;
            _item = new JobEntity(_tmpId,_tmpTitle,_tmpDescription,_tmpEmployerId,_tmpEmployerName,_tmpLocation,_tmpLatitude,_tmpLongitude,_tmpRequiredSkills,_tmpPayment,_tmpCurrency,_tmpDuration,_tmpCategory,_tmpMatchScore,_tmpStatus,_tmpDeadline,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsSaved,_tmpIsApplied,_tmpIsSynced);
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
  public Flow<JobEntity> getJob(final String jobId) {
    final String _sql = "SELECT * FROM jobs WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (jobId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, jobId);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"jobs"}, new Callable<JobEntity>() {
      @Override
      @Nullable
      public JobEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfEmployerId = CursorUtil.getColumnIndexOrThrow(_cursor, "employerId");
          final int _cursorIndexOfEmployerName = CursorUtil.getColumnIndexOrThrow(_cursor, "employerName");
          final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfRequiredSkills = CursorUtil.getColumnIndexOrThrow(_cursor, "requiredSkills");
          final int _cursorIndexOfPayment = CursorUtil.getColumnIndexOrThrow(_cursor, "payment");
          final int _cursorIndexOfCurrency = CursorUtil.getColumnIndexOrThrow(_cursor, "currency");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfMatchScore = CursorUtil.getColumnIndexOrThrow(_cursor, "matchScore");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfDeadline = CursorUtil.getColumnIndexOrThrow(_cursor, "deadline");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfIsSaved = CursorUtil.getColumnIndexOrThrow(_cursor, "isSaved");
          final int _cursorIndexOfIsApplied = CursorUtil.getColumnIndexOrThrow(_cursor, "isApplied");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final JobEntity _result;
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
            final String _tmpEmployerId;
            if (_cursor.isNull(_cursorIndexOfEmployerId)) {
              _tmpEmployerId = null;
            } else {
              _tmpEmployerId = _cursor.getString(_cursorIndexOfEmployerId);
            }
            final String _tmpEmployerName;
            if (_cursor.isNull(_cursorIndexOfEmployerName)) {
              _tmpEmployerName = null;
            } else {
              _tmpEmployerName = _cursor.getString(_cursorIndexOfEmployerName);
            }
            final String _tmpLocation;
            if (_cursor.isNull(_cursorIndexOfLocation)) {
              _tmpLocation = null;
            } else {
              _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
            }
            final double _tmpLatitude;
            _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            final double _tmpLongitude;
            _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            final List<String> _tmpRequiredSkills;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfRequiredSkills)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfRequiredSkills);
            }
            _tmpRequiredSkills = __listConverters.toStringList(_tmp);
            final double _tmpPayment;
            _tmpPayment = _cursor.getDouble(_cursorIndexOfPayment);
            final String _tmpCurrency;
            if (_cursor.isNull(_cursorIndexOfCurrency)) {
              _tmpCurrency = null;
            } else {
              _tmpCurrency = _cursor.getString(_cursorIndexOfCurrency);
            }
            final long _tmpDuration;
            _tmpDuration = _cursor.getLong(_cursorIndexOfDuration);
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            }
            final float _tmpMatchScore;
            _tmpMatchScore = _cursor.getFloat(_cursorIndexOfMatchScore);
            final String _tmpStatus;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmpStatus = null;
            } else {
              _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            }
            final Long _tmpDeadline;
            if (_cursor.isNull(_cursorIndexOfDeadline)) {
              _tmpDeadline = null;
            } else {
              _tmpDeadline = _cursor.getLong(_cursorIndexOfDeadline);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final boolean _tmpIsSaved;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsSaved);
            _tmpIsSaved = _tmp_1 != 0;
            final boolean _tmpIsApplied;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsApplied);
            _tmpIsApplied = _tmp_2 != 0;
            final boolean _tmpIsSynced;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp_3 != 0;
            _result = new JobEntity(_tmpId,_tmpTitle,_tmpDescription,_tmpEmployerId,_tmpEmployerName,_tmpLocation,_tmpLatitude,_tmpLongitude,_tmpRequiredSkills,_tmpPayment,_tmpCurrency,_tmpDuration,_tmpCategory,_tmpMatchScore,_tmpStatus,_tmpDeadline,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsSaved,_tmpIsApplied,_tmpIsSynced);
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
  public Object getJobOnce(final String jobId, final Continuation<? super JobEntity> $completion) {
    final String _sql = "SELECT * FROM jobs WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (jobId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, jobId);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<JobEntity>() {
      @Override
      @Nullable
      public JobEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfEmployerId = CursorUtil.getColumnIndexOrThrow(_cursor, "employerId");
          final int _cursorIndexOfEmployerName = CursorUtil.getColumnIndexOrThrow(_cursor, "employerName");
          final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfRequiredSkills = CursorUtil.getColumnIndexOrThrow(_cursor, "requiredSkills");
          final int _cursorIndexOfPayment = CursorUtil.getColumnIndexOrThrow(_cursor, "payment");
          final int _cursorIndexOfCurrency = CursorUtil.getColumnIndexOrThrow(_cursor, "currency");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfMatchScore = CursorUtil.getColumnIndexOrThrow(_cursor, "matchScore");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfDeadline = CursorUtil.getColumnIndexOrThrow(_cursor, "deadline");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfIsSaved = CursorUtil.getColumnIndexOrThrow(_cursor, "isSaved");
          final int _cursorIndexOfIsApplied = CursorUtil.getColumnIndexOrThrow(_cursor, "isApplied");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final JobEntity _result;
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
            final String _tmpEmployerId;
            if (_cursor.isNull(_cursorIndexOfEmployerId)) {
              _tmpEmployerId = null;
            } else {
              _tmpEmployerId = _cursor.getString(_cursorIndexOfEmployerId);
            }
            final String _tmpEmployerName;
            if (_cursor.isNull(_cursorIndexOfEmployerName)) {
              _tmpEmployerName = null;
            } else {
              _tmpEmployerName = _cursor.getString(_cursorIndexOfEmployerName);
            }
            final String _tmpLocation;
            if (_cursor.isNull(_cursorIndexOfLocation)) {
              _tmpLocation = null;
            } else {
              _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
            }
            final double _tmpLatitude;
            _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            final double _tmpLongitude;
            _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            final List<String> _tmpRequiredSkills;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfRequiredSkills)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfRequiredSkills);
            }
            _tmpRequiredSkills = __listConverters.toStringList(_tmp);
            final double _tmpPayment;
            _tmpPayment = _cursor.getDouble(_cursorIndexOfPayment);
            final String _tmpCurrency;
            if (_cursor.isNull(_cursorIndexOfCurrency)) {
              _tmpCurrency = null;
            } else {
              _tmpCurrency = _cursor.getString(_cursorIndexOfCurrency);
            }
            final long _tmpDuration;
            _tmpDuration = _cursor.getLong(_cursorIndexOfDuration);
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            }
            final float _tmpMatchScore;
            _tmpMatchScore = _cursor.getFloat(_cursorIndexOfMatchScore);
            final String _tmpStatus;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmpStatus = null;
            } else {
              _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            }
            final Long _tmpDeadline;
            if (_cursor.isNull(_cursorIndexOfDeadline)) {
              _tmpDeadline = null;
            } else {
              _tmpDeadline = _cursor.getLong(_cursorIndexOfDeadline);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final boolean _tmpIsSaved;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsSaved);
            _tmpIsSaved = _tmp_1 != 0;
            final boolean _tmpIsApplied;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsApplied);
            _tmpIsApplied = _tmp_2 != 0;
            final boolean _tmpIsSynced;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp_3 != 0;
            _result = new JobEntity(_tmpId,_tmpTitle,_tmpDescription,_tmpEmployerId,_tmpEmployerName,_tmpLocation,_tmpLatitude,_tmpLongitude,_tmpRequiredSkills,_tmpPayment,_tmpCurrency,_tmpDuration,_tmpCategory,_tmpMatchScore,_tmpStatus,_tmpDeadline,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsSaved,_tmpIsApplied,_tmpIsSynced);
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
  public Flow<List<JobEntity>> getSavedJobs() {
    final String _sql = "SELECT * FROM jobs WHERE isSaved = 1 ORDER BY updatedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"jobs"}, new Callable<List<JobEntity>>() {
      @Override
      @NonNull
      public List<JobEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfEmployerId = CursorUtil.getColumnIndexOrThrow(_cursor, "employerId");
          final int _cursorIndexOfEmployerName = CursorUtil.getColumnIndexOrThrow(_cursor, "employerName");
          final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfRequiredSkills = CursorUtil.getColumnIndexOrThrow(_cursor, "requiredSkills");
          final int _cursorIndexOfPayment = CursorUtil.getColumnIndexOrThrow(_cursor, "payment");
          final int _cursorIndexOfCurrency = CursorUtil.getColumnIndexOrThrow(_cursor, "currency");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfMatchScore = CursorUtil.getColumnIndexOrThrow(_cursor, "matchScore");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfDeadline = CursorUtil.getColumnIndexOrThrow(_cursor, "deadline");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfIsSaved = CursorUtil.getColumnIndexOrThrow(_cursor, "isSaved");
          final int _cursorIndexOfIsApplied = CursorUtil.getColumnIndexOrThrow(_cursor, "isApplied");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final List<JobEntity> _result = new ArrayList<JobEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final JobEntity _item;
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
            final String _tmpEmployerId;
            if (_cursor.isNull(_cursorIndexOfEmployerId)) {
              _tmpEmployerId = null;
            } else {
              _tmpEmployerId = _cursor.getString(_cursorIndexOfEmployerId);
            }
            final String _tmpEmployerName;
            if (_cursor.isNull(_cursorIndexOfEmployerName)) {
              _tmpEmployerName = null;
            } else {
              _tmpEmployerName = _cursor.getString(_cursorIndexOfEmployerName);
            }
            final String _tmpLocation;
            if (_cursor.isNull(_cursorIndexOfLocation)) {
              _tmpLocation = null;
            } else {
              _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
            }
            final double _tmpLatitude;
            _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            final double _tmpLongitude;
            _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            final List<String> _tmpRequiredSkills;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfRequiredSkills)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfRequiredSkills);
            }
            _tmpRequiredSkills = __listConverters.toStringList(_tmp);
            final double _tmpPayment;
            _tmpPayment = _cursor.getDouble(_cursorIndexOfPayment);
            final String _tmpCurrency;
            if (_cursor.isNull(_cursorIndexOfCurrency)) {
              _tmpCurrency = null;
            } else {
              _tmpCurrency = _cursor.getString(_cursorIndexOfCurrency);
            }
            final long _tmpDuration;
            _tmpDuration = _cursor.getLong(_cursorIndexOfDuration);
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            }
            final float _tmpMatchScore;
            _tmpMatchScore = _cursor.getFloat(_cursorIndexOfMatchScore);
            final String _tmpStatus;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmpStatus = null;
            } else {
              _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            }
            final Long _tmpDeadline;
            if (_cursor.isNull(_cursorIndexOfDeadline)) {
              _tmpDeadline = null;
            } else {
              _tmpDeadline = _cursor.getLong(_cursorIndexOfDeadline);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final boolean _tmpIsSaved;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsSaved);
            _tmpIsSaved = _tmp_1 != 0;
            final boolean _tmpIsApplied;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsApplied);
            _tmpIsApplied = _tmp_2 != 0;
            final boolean _tmpIsSynced;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp_3 != 0;
            _item = new JobEntity(_tmpId,_tmpTitle,_tmpDescription,_tmpEmployerId,_tmpEmployerName,_tmpLocation,_tmpLatitude,_tmpLongitude,_tmpRequiredSkills,_tmpPayment,_tmpCurrency,_tmpDuration,_tmpCategory,_tmpMatchScore,_tmpStatus,_tmpDeadline,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsSaved,_tmpIsApplied,_tmpIsSynced);
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
  public Flow<List<JobEntity>> getAppliedJobs() {
    final String _sql = "SELECT * FROM jobs WHERE isApplied = 1 ORDER BY updatedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"jobs"}, new Callable<List<JobEntity>>() {
      @Override
      @NonNull
      public List<JobEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfEmployerId = CursorUtil.getColumnIndexOrThrow(_cursor, "employerId");
          final int _cursorIndexOfEmployerName = CursorUtil.getColumnIndexOrThrow(_cursor, "employerName");
          final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfRequiredSkills = CursorUtil.getColumnIndexOrThrow(_cursor, "requiredSkills");
          final int _cursorIndexOfPayment = CursorUtil.getColumnIndexOrThrow(_cursor, "payment");
          final int _cursorIndexOfCurrency = CursorUtil.getColumnIndexOrThrow(_cursor, "currency");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfMatchScore = CursorUtil.getColumnIndexOrThrow(_cursor, "matchScore");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfDeadline = CursorUtil.getColumnIndexOrThrow(_cursor, "deadline");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfIsSaved = CursorUtil.getColumnIndexOrThrow(_cursor, "isSaved");
          final int _cursorIndexOfIsApplied = CursorUtil.getColumnIndexOrThrow(_cursor, "isApplied");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final List<JobEntity> _result = new ArrayList<JobEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final JobEntity _item;
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
            final String _tmpEmployerId;
            if (_cursor.isNull(_cursorIndexOfEmployerId)) {
              _tmpEmployerId = null;
            } else {
              _tmpEmployerId = _cursor.getString(_cursorIndexOfEmployerId);
            }
            final String _tmpEmployerName;
            if (_cursor.isNull(_cursorIndexOfEmployerName)) {
              _tmpEmployerName = null;
            } else {
              _tmpEmployerName = _cursor.getString(_cursorIndexOfEmployerName);
            }
            final String _tmpLocation;
            if (_cursor.isNull(_cursorIndexOfLocation)) {
              _tmpLocation = null;
            } else {
              _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
            }
            final double _tmpLatitude;
            _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            final double _tmpLongitude;
            _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            final List<String> _tmpRequiredSkills;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfRequiredSkills)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfRequiredSkills);
            }
            _tmpRequiredSkills = __listConverters.toStringList(_tmp);
            final double _tmpPayment;
            _tmpPayment = _cursor.getDouble(_cursorIndexOfPayment);
            final String _tmpCurrency;
            if (_cursor.isNull(_cursorIndexOfCurrency)) {
              _tmpCurrency = null;
            } else {
              _tmpCurrency = _cursor.getString(_cursorIndexOfCurrency);
            }
            final long _tmpDuration;
            _tmpDuration = _cursor.getLong(_cursorIndexOfDuration);
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            }
            final float _tmpMatchScore;
            _tmpMatchScore = _cursor.getFloat(_cursorIndexOfMatchScore);
            final String _tmpStatus;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmpStatus = null;
            } else {
              _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            }
            final Long _tmpDeadline;
            if (_cursor.isNull(_cursorIndexOfDeadline)) {
              _tmpDeadline = null;
            } else {
              _tmpDeadline = _cursor.getLong(_cursorIndexOfDeadline);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final boolean _tmpIsSaved;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsSaved);
            _tmpIsSaved = _tmp_1 != 0;
            final boolean _tmpIsApplied;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsApplied);
            _tmpIsApplied = _tmp_2 != 0;
            final boolean _tmpIsSynced;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp_3 != 0;
            _item = new JobEntity(_tmpId,_tmpTitle,_tmpDescription,_tmpEmployerId,_tmpEmployerName,_tmpLocation,_tmpLatitude,_tmpLongitude,_tmpRequiredSkills,_tmpPayment,_tmpCurrency,_tmpDuration,_tmpCategory,_tmpMatchScore,_tmpStatus,_tmpDeadline,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsSaved,_tmpIsApplied,_tmpIsSynced);
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
  public Flow<List<JobEntity>> getJobsByCategory(final String category) {
    final String _sql = "SELECT * FROM jobs WHERE category = ? AND status = 'OPEN' ORDER BY matchScore DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (category == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, category);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"jobs"}, new Callable<List<JobEntity>>() {
      @Override
      @NonNull
      public List<JobEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfEmployerId = CursorUtil.getColumnIndexOrThrow(_cursor, "employerId");
          final int _cursorIndexOfEmployerName = CursorUtil.getColumnIndexOrThrow(_cursor, "employerName");
          final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfRequiredSkills = CursorUtil.getColumnIndexOrThrow(_cursor, "requiredSkills");
          final int _cursorIndexOfPayment = CursorUtil.getColumnIndexOrThrow(_cursor, "payment");
          final int _cursorIndexOfCurrency = CursorUtil.getColumnIndexOrThrow(_cursor, "currency");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfMatchScore = CursorUtil.getColumnIndexOrThrow(_cursor, "matchScore");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfDeadline = CursorUtil.getColumnIndexOrThrow(_cursor, "deadline");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfIsSaved = CursorUtil.getColumnIndexOrThrow(_cursor, "isSaved");
          final int _cursorIndexOfIsApplied = CursorUtil.getColumnIndexOrThrow(_cursor, "isApplied");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final List<JobEntity> _result = new ArrayList<JobEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final JobEntity _item;
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
            final String _tmpEmployerId;
            if (_cursor.isNull(_cursorIndexOfEmployerId)) {
              _tmpEmployerId = null;
            } else {
              _tmpEmployerId = _cursor.getString(_cursorIndexOfEmployerId);
            }
            final String _tmpEmployerName;
            if (_cursor.isNull(_cursorIndexOfEmployerName)) {
              _tmpEmployerName = null;
            } else {
              _tmpEmployerName = _cursor.getString(_cursorIndexOfEmployerName);
            }
            final String _tmpLocation;
            if (_cursor.isNull(_cursorIndexOfLocation)) {
              _tmpLocation = null;
            } else {
              _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
            }
            final double _tmpLatitude;
            _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            final double _tmpLongitude;
            _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            final List<String> _tmpRequiredSkills;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfRequiredSkills)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfRequiredSkills);
            }
            _tmpRequiredSkills = __listConverters.toStringList(_tmp);
            final double _tmpPayment;
            _tmpPayment = _cursor.getDouble(_cursorIndexOfPayment);
            final String _tmpCurrency;
            if (_cursor.isNull(_cursorIndexOfCurrency)) {
              _tmpCurrency = null;
            } else {
              _tmpCurrency = _cursor.getString(_cursorIndexOfCurrency);
            }
            final long _tmpDuration;
            _tmpDuration = _cursor.getLong(_cursorIndexOfDuration);
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            }
            final float _tmpMatchScore;
            _tmpMatchScore = _cursor.getFloat(_cursorIndexOfMatchScore);
            final String _tmpStatus;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmpStatus = null;
            } else {
              _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            }
            final Long _tmpDeadline;
            if (_cursor.isNull(_cursorIndexOfDeadline)) {
              _tmpDeadline = null;
            } else {
              _tmpDeadline = _cursor.getLong(_cursorIndexOfDeadline);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final boolean _tmpIsSaved;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsSaved);
            _tmpIsSaved = _tmp_1 != 0;
            final boolean _tmpIsApplied;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsApplied);
            _tmpIsApplied = _tmp_2 != 0;
            final boolean _tmpIsSynced;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp_3 != 0;
            _item = new JobEntity(_tmpId,_tmpTitle,_tmpDescription,_tmpEmployerId,_tmpEmployerName,_tmpLocation,_tmpLatitude,_tmpLongitude,_tmpRequiredSkills,_tmpPayment,_tmpCurrency,_tmpDuration,_tmpCategory,_tmpMatchScore,_tmpStatus,_tmpDeadline,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsSaved,_tmpIsApplied,_tmpIsSynced);
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
  public Flow<List<JobEntity>> searchJobs(final String query) {
    final String _sql = "\n"
            + "        SELECT * FROM jobs\n"
            + "        WHERE status = 'OPEN'\n"
            + "          AND (\n"
            + "               title        LIKE '%' || ? || '%'\n"
            + "            OR employerName LIKE '%' || ? || '%'\n"
            + "            OR category     LIKE '%' || ? || '%'\n"
            + "            OR location     LIKE '%' || ? || '%'\n"
            + "          )\n"
            + "        ORDER BY matchScore DESC, createdAt DESC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 4);
    int _argIndex = 1;
    if (query == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, query);
    }
    _argIndex = 2;
    if (query == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, query);
    }
    _argIndex = 3;
    if (query == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, query);
    }
    _argIndex = 4;
    if (query == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, query);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"jobs"}, new Callable<List<JobEntity>>() {
      @Override
      @NonNull
      public List<JobEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfEmployerId = CursorUtil.getColumnIndexOrThrow(_cursor, "employerId");
          final int _cursorIndexOfEmployerName = CursorUtil.getColumnIndexOrThrow(_cursor, "employerName");
          final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfRequiredSkills = CursorUtil.getColumnIndexOrThrow(_cursor, "requiredSkills");
          final int _cursorIndexOfPayment = CursorUtil.getColumnIndexOrThrow(_cursor, "payment");
          final int _cursorIndexOfCurrency = CursorUtil.getColumnIndexOrThrow(_cursor, "currency");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfMatchScore = CursorUtil.getColumnIndexOrThrow(_cursor, "matchScore");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfDeadline = CursorUtil.getColumnIndexOrThrow(_cursor, "deadline");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfIsSaved = CursorUtil.getColumnIndexOrThrow(_cursor, "isSaved");
          final int _cursorIndexOfIsApplied = CursorUtil.getColumnIndexOrThrow(_cursor, "isApplied");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final List<JobEntity> _result = new ArrayList<JobEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final JobEntity _item;
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
            final String _tmpEmployerId;
            if (_cursor.isNull(_cursorIndexOfEmployerId)) {
              _tmpEmployerId = null;
            } else {
              _tmpEmployerId = _cursor.getString(_cursorIndexOfEmployerId);
            }
            final String _tmpEmployerName;
            if (_cursor.isNull(_cursorIndexOfEmployerName)) {
              _tmpEmployerName = null;
            } else {
              _tmpEmployerName = _cursor.getString(_cursorIndexOfEmployerName);
            }
            final String _tmpLocation;
            if (_cursor.isNull(_cursorIndexOfLocation)) {
              _tmpLocation = null;
            } else {
              _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
            }
            final double _tmpLatitude;
            _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            final double _tmpLongitude;
            _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            final List<String> _tmpRequiredSkills;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfRequiredSkills)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfRequiredSkills);
            }
            _tmpRequiredSkills = __listConverters.toStringList(_tmp);
            final double _tmpPayment;
            _tmpPayment = _cursor.getDouble(_cursorIndexOfPayment);
            final String _tmpCurrency;
            if (_cursor.isNull(_cursorIndexOfCurrency)) {
              _tmpCurrency = null;
            } else {
              _tmpCurrency = _cursor.getString(_cursorIndexOfCurrency);
            }
            final long _tmpDuration;
            _tmpDuration = _cursor.getLong(_cursorIndexOfDuration);
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            }
            final float _tmpMatchScore;
            _tmpMatchScore = _cursor.getFloat(_cursorIndexOfMatchScore);
            final String _tmpStatus;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmpStatus = null;
            } else {
              _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            }
            final Long _tmpDeadline;
            if (_cursor.isNull(_cursorIndexOfDeadline)) {
              _tmpDeadline = null;
            } else {
              _tmpDeadline = _cursor.getLong(_cursorIndexOfDeadline);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final boolean _tmpIsSaved;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsSaved);
            _tmpIsSaved = _tmp_1 != 0;
            final boolean _tmpIsApplied;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsApplied);
            _tmpIsApplied = _tmp_2 != 0;
            final boolean _tmpIsSynced;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp_3 != 0;
            _item = new JobEntity(_tmpId,_tmpTitle,_tmpDescription,_tmpEmployerId,_tmpEmployerName,_tmpLocation,_tmpLatitude,_tmpLongitude,_tmpRequiredSkills,_tmpPayment,_tmpCurrency,_tmpDuration,_tmpCategory,_tmpMatchScore,_tmpStatus,_tmpDeadline,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsSaved,_tmpIsApplied,_tmpIsSynced);
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
  public Object getUnsyncedJobs(final Continuation<? super List<JobEntity>> $completion) {
    final String _sql = "SELECT * FROM jobs WHERE isSynced = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<JobEntity>>() {
      @Override
      @NonNull
      public List<JobEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfEmployerId = CursorUtil.getColumnIndexOrThrow(_cursor, "employerId");
          final int _cursorIndexOfEmployerName = CursorUtil.getColumnIndexOrThrow(_cursor, "employerName");
          final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfRequiredSkills = CursorUtil.getColumnIndexOrThrow(_cursor, "requiredSkills");
          final int _cursorIndexOfPayment = CursorUtil.getColumnIndexOrThrow(_cursor, "payment");
          final int _cursorIndexOfCurrency = CursorUtil.getColumnIndexOrThrow(_cursor, "currency");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfMatchScore = CursorUtil.getColumnIndexOrThrow(_cursor, "matchScore");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfDeadline = CursorUtil.getColumnIndexOrThrow(_cursor, "deadline");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfIsSaved = CursorUtil.getColumnIndexOrThrow(_cursor, "isSaved");
          final int _cursorIndexOfIsApplied = CursorUtil.getColumnIndexOrThrow(_cursor, "isApplied");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final List<JobEntity> _result = new ArrayList<JobEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final JobEntity _item;
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
            final String _tmpEmployerId;
            if (_cursor.isNull(_cursorIndexOfEmployerId)) {
              _tmpEmployerId = null;
            } else {
              _tmpEmployerId = _cursor.getString(_cursorIndexOfEmployerId);
            }
            final String _tmpEmployerName;
            if (_cursor.isNull(_cursorIndexOfEmployerName)) {
              _tmpEmployerName = null;
            } else {
              _tmpEmployerName = _cursor.getString(_cursorIndexOfEmployerName);
            }
            final String _tmpLocation;
            if (_cursor.isNull(_cursorIndexOfLocation)) {
              _tmpLocation = null;
            } else {
              _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
            }
            final double _tmpLatitude;
            _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            final double _tmpLongitude;
            _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            final List<String> _tmpRequiredSkills;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfRequiredSkills)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfRequiredSkills);
            }
            _tmpRequiredSkills = __listConverters.toStringList(_tmp);
            final double _tmpPayment;
            _tmpPayment = _cursor.getDouble(_cursorIndexOfPayment);
            final String _tmpCurrency;
            if (_cursor.isNull(_cursorIndexOfCurrency)) {
              _tmpCurrency = null;
            } else {
              _tmpCurrency = _cursor.getString(_cursorIndexOfCurrency);
            }
            final long _tmpDuration;
            _tmpDuration = _cursor.getLong(_cursorIndexOfDuration);
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            }
            final float _tmpMatchScore;
            _tmpMatchScore = _cursor.getFloat(_cursorIndexOfMatchScore);
            final String _tmpStatus;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmpStatus = null;
            } else {
              _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            }
            final Long _tmpDeadline;
            if (_cursor.isNull(_cursorIndexOfDeadline)) {
              _tmpDeadline = null;
            } else {
              _tmpDeadline = _cursor.getLong(_cursorIndexOfDeadline);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final boolean _tmpIsSaved;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsSaved);
            _tmpIsSaved = _tmp_1 != 0;
            final boolean _tmpIsApplied;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsApplied);
            _tmpIsApplied = _tmp_2 != 0;
            final boolean _tmpIsSynced;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp_3 != 0;
            _item = new JobEntity(_tmpId,_tmpTitle,_tmpDescription,_tmpEmployerId,_tmpEmployerName,_tmpLocation,_tmpLatitude,_tmpLongitude,_tmpRequiredSkills,_tmpPayment,_tmpCurrency,_tmpDuration,_tmpCategory,_tmpMatchScore,_tmpStatus,_tmpDeadline,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsSaved,_tmpIsApplied,_tmpIsSynced);
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

  @Override
  public Object getJobsByEmployer(final String employerId,
      final Continuation<? super List<JobEntity>> $completion) {
    final String _sql = "SELECT * FROM jobs WHERE employerId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (employerId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, employerId);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<JobEntity>>() {
      @Override
      @NonNull
      public List<JobEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfEmployerId = CursorUtil.getColumnIndexOrThrow(_cursor, "employerId");
          final int _cursorIndexOfEmployerName = CursorUtil.getColumnIndexOrThrow(_cursor, "employerName");
          final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfRequiredSkills = CursorUtil.getColumnIndexOrThrow(_cursor, "requiredSkills");
          final int _cursorIndexOfPayment = CursorUtil.getColumnIndexOrThrow(_cursor, "payment");
          final int _cursorIndexOfCurrency = CursorUtil.getColumnIndexOrThrow(_cursor, "currency");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfMatchScore = CursorUtil.getColumnIndexOrThrow(_cursor, "matchScore");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfDeadline = CursorUtil.getColumnIndexOrThrow(_cursor, "deadline");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfIsSaved = CursorUtil.getColumnIndexOrThrow(_cursor, "isSaved");
          final int _cursorIndexOfIsApplied = CursorUtil.getColumnIndexOrThrow(_cursor, "isApplied");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final List<JobEntity> _result = new ArrayList<JobEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final JobEntity _item;
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
            final String _tmpEmployerId;
            if (_cursor.isNull(_cursorIndexOfEmployerId)) {
              _tmpEmployerId = null;
            } else {
              _tmpEmployerId = _cursor.getString(_cursorIndexOfEmployerId);
            }
            final String _tmpEmployerName;
            if (_cursor.isNull(_cursorIndexOfEmployerName)) {
              _tmpEmployerName = null;
            } else {
              _tmpEmployerName = _cursor.getString(_cursorIndexOfEmployerName);
            }
            final String _tmpLocation;
            if (_cursor.isNull(_cursorIndexOfLocation)) {
              _tmpLocation = null;
            } else {
              _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
            }
            final double _tmpLatitude;
            _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            final double _tmpLongitude;
            _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            final List<String> _tmpRequiredSkills;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfRequiredSkills)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfRequiredSkills);
            }
            _tmpRequiredSkills = __listConverters.toStringList(_tmp);
            final double _tmpPayment;
            _tmpPayment = _cursor.getDouble(_cursorIndexOfPayment);
            final String _tmpCurrency;
            if (_cursor.isNull(_cursorIndexOfCurrency)) {
              _tmpCurrency = null;
            } else {
              _tmpCurrency = _cursor.getString(_cursorIndexOfCurrency);
            }
            final long _tmpDuration;
            _tmpDuration = _cursor.getLong(_cursorIndexOfDuration);
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            }
            final float _tmpMatchScore;
            _tmpMatchScore = _cursor.getFloat(_cursorIndexOfMatchScore);
            final String _tmpStatus;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmpStatus = null;
            } else {
              _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            }
            final Long _tmpDeadline;
            if (_cursor.isNull(_cursorIndexOfDeadline)) {
              _tmpDeadline = null;
            } else {
              _tmpDeadline = _cursor.getLong(_cursorIndexOfDeadline);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final boolean _tmpIsSaved;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsSaved);
            _tmpIsSaved = _tmp_1 != 0;
            final boolean _tmpIsApplied;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsApplied);
            _tmpIsApplied = _tmp_2 != 0;
            final boolean _tmpIsSynced;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp_3 != 0;
            _item = new JobEntity(_tmpId,_tmpTitle,_tmpDescription,_tmpEmployerId,_tmpEmployerName,_tmpLocation,_tmpLatitude,_tmpLongitude,_tmpRequiredSkills,_tmpPayment,_tmpCurrency,_tmpDuration,_tmpCategory,_tmpMatchScore,_tmpStatus,_tmpDeadline,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsSaved,_tmpIsApplied,_tmpIsSynced);
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
