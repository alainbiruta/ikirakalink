package com.microcareer.match.data.local.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.microcareer.match.data.local.converter.ListConverters;
import com.microcareer.match.data.local.dao.*;
import com.microcareer.match.data.local.entity.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\'\u0018\u0000 \r2\u00020\u0001:\u0001\rB\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H&J\b\u0010\u0007\u001a\u00020\bH&J\b\u0010\t\u001a\u00020\nH&J\b\u0010\u000b\u001a\u00020\fH&\u00a8\u0006\u000e"}, d2 = {"Lcom/microcareer/match/data/local/database/MicroCareerDatabase;", "Landroidx/room/RoomDatabase;", "()V", "jobDao", "Lcom/microcareer/match/data/local/dao/JobDao;", "learningModuleDao", "Lcom/microcareer/match/data/local/dao/LearningModuleDao;", "offlinePendingActionDao", "Lcom/microcareer/match/data/local/dao/OfflinePendingActionDao;", "skillProfileDao", "Lcom/microcareer/match/data/local/dao/SkillProfileDao;", "userDao", "Lcom/microcareer/match/data/local/dao/UserDao;", "Companion", "app_debug"})
@androidx.room.Database(entities = {com.microcareer.match.data.local.entity.UserEntity.class, com.microcareer.match.data.local.entity.JobEntity.class, com.microcareer.match.data.local.entity.LearningModuleEntity.class, com.microcareer.match.data.local.entity.SkillProfileEntity.class, com.microcareer.match.data.local.entity.OfflinePendingActionEntity.class}, version = 5, exportSchema = true)
@androidx.room.TypeConverters(value = {com.microcareer.match.data.local.converter.ListConverters.class})
public abstract class MicroCareerDatabase extends androidx.room.RoomDatabase {
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String DATABASE_NAME = "micro_career.db";
    
    /**
     * Migration 1 → 2
     * Adds the offline_pending_actions table.
     * All existing data is preserved — no destructive changes.
     *
     * Wire this into Room builder in your DI module:
     *
     *  Room.databaseBuilder(...)
     *      .addMigrations(MIGRATION_1_2)
     *      .build()
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.room.migration.Migration MIGRATION_1_2 = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.microcareer.match.data.local.database.MicroCareerDatabase.Companion Companion = null;
    
    public MicroCareerDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.microcareer.match.data.local.dao.UserDao userDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.microcareer.match.data.local.dao.JobDao jobDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.microcareer.match.data.local.dao.LearningModuleDao learningModuleDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.microcareer.match.data.local.dao.SkillProfileDao skillProfileDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.microcareer.match.data.local.dao.OfflinePendingActionDao offlinePendingActionDao();
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b\u00a8\u0006\t"}, d2 = {"Lcom/microcareer/match/data/local/database/MicroCareerDatabase$Companion;", "", "()V", "DATABASE_NAME", "", "MIGRATION_1_2", "Landroidx/room/migration/Migration;", "getMIGRATION_1_2", "()Landroidx/room/migration/Migration;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        /**
         * Migration 1 → 2
         * Adds the offline_pending_actions table.
         * All existing data is preserved — no destructive changes.
         *
         * Wire this into Room builder in your DI module:
         *
         *  Room.databaseBuilder(...)
         *      .addMigrations(MIGRATION_1_2)
         *      .build()
         */
        @org.jetbrains.annotations.NotNull()
        public final androidx.room.migration.Migration getMIGRATION_1_2() {
            return null;
        }
    }
}