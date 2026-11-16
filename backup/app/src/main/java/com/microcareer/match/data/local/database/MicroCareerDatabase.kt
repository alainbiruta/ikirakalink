package com.microcareer.match.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.microcareer.match.data.local.converter.ListConverters
import com.microcareer.match.data.local.dao.*
import com.microcareer.match.data.local.entity.*

@Database(
    entities = [
        UserEntity::class,
        JobEntity::class,
        LearningModuleEntity::class,
        SkillProfileEntity::class,
        OfflinePendingActionEntity::class   // ← Day 18 addition
    ],
    version = 5,                            // ← Bumped from 1 to 2
    exportSchema = true
)
@TypeConverters(ListConverters::class)
abstract class MicroCareerDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun jobDao(): JobDao
    abstract fun learningModuleDao(): LearningModuleDao
    abstract fun skillProfileDao(): SkillProfileDao
    abstract fun offlinePendingActionDao(): OfflinePendingActionDao   // ← Day 18 addition

    companion object {
        const val DATABASE_NAME = "micro_career.db"

        /**
         * Migration 1 → 2
         * Adds the offline_pending_actions table.
         * All existing data is preserved — no destructive changes.
         *
         * Wire this into Room builder in your DI module:
         *
         *   Room.databaseBuilder(...)
         *       .addMigrations(MIGRATION_1_2)
         *       .build()
         */
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `offline_pending_actions` (
                        `id`          INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `actionType`  TEXT    NOT NULL,
                        `jobId`       TEXT    NOT NULL,
                        `payloadJson` TEXT,
                        `createdAt`   INTEGER NOT NULL,
                        `retryCount`  INTEGER NOT NULL DEFAULT 0
                    )
                    """.trimIndent()
                )
            }
            val MIGRATION_4_5 = object : Migration(4, 5) {
                override fun migrate(database: SupportSQLiteDatabase) {
                    database.execSQL(
                        "ALTER TABLE `users` ADD COLUMN `bio` TEXT DEFAULT NULL"
                    )
                }
            }
        }
    }
}