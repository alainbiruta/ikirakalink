package com.microcareer.match.data.local.seeder

import com.microcareer.match.data.local.dao.JobDao
import com.microcareer.match.data.local.dao.LearningModuleDao
import com.microcareer.match.data.local.dao.UserDao
import com.microcareer.match.data.local.entity.JobEntity
import com.microcareer.match.data.local.entity.LearningModuleEntity
import com.microcareer.match.data.local.entity.UserEntity
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Singleton
class DatabaseSeeder @Inject constructor(
    private val jobDao: JobDao,
    private val learningModuleDao: LearningModuleDao,
    private val userDao: UserDao,
    private val firebaseAuth: FirebaseAuth
) {

    suspend fun seedDatabase() = withContext(Dispatchers.IO) {
        val existingJobs = jobDao.getJobOnce("job_001")
        if (existingJobs != null) {
            android.util.Log.d("DatabaseSeeder", "Database already seeded, skipping")
            return@withContext
        }
        android.util.Log.d("DatabaseSeeder", "Seeding database with sample data...")
        seedJobs()
        seedLearningModules()
        seedCurrentUser()
        android.util.Log.d("DatabaseSeeder", "Database seeding complete ✅")
    }

    suspend fun clearAndReseed() = withContext(Dispatchers.IO) {
        android.util.Log.d("DatabaseSeeder", "Clearing and reseeding database...")
        seedJobs()
        seedLearningModules()
        android.util.Log.d("DatabaseSeeder", "Database reset complete ✅")
    }

    private suspend fun seedJobs() {
        val now = System.currentTimeMillis()
        val day = 24 * 60 * 60 * 1000L

        val jobs = listOf(
            JobEntity(
                id             = "job_001",
                title          = "Construction Helper Needed",
                description    = "Assist with building a residential house in Kimironko. No experience required, will train. Must be physically fit and willing to work outdoors.",
                employerId     = "employer_001",
                employerName   = "Kimironko Builders Ltd",
                location       = "Kimironko, Kigali",
                latitude       = -1.9536,
                longitude      = 30.1047,
                requiredSkills = listOf("Physical fitness", "Teamwork", "Reliability"),
                payment        = 5000.0,
                currency       = "RWF",
                duration       = 1L,          // 1 day
                category       = "Construction",
                matchScore     = 0.85f,
                status         = "OPEN",
                deadline       = now + 7 * day,
                createdAt      = now - 2 * 60 * 60 * 1000L,
                updatedAt      = now - 2 * 60 * 60 * 1000L
            ),
            JobEntity(
                id             = "job_002",
                title          = "Painting Job - 2 Days",
                description    = "Need experienced painter for interior house painting in Nyarutarama. Must have own brushes and rollers. Paint will be provided.",
                employerId     = "employer_002",
                employerName   = "Home Renovations RW",
                location       = "Nyarutarama, Kigali",
                latitude       = -1.9447,
                longitude      = 30.1114,
                requiredSkills = listOf("Painting", "Attention to detail", "Time management"),
                payment        = 15000.0,
                currency       = "RWF",
                duration       = 2L,          // 2 days
                category       = "Construction",
                matchScore     = 0.75f,
                status         = "OPEN",
                deadline       = now + 5 * day,
                createdAt      = now - 5 * 60 * 60 * 1000L,
                updatedAt      = now - 5 * 60 * 60 * 1000L
            ),
            JobEntity(
                id             = "job_003",
                title          = "Market Stall Assistant",
                description    = "Help manage vegetable stall at Kimironko Market. Must be honest and good with numbers.",
                employerId     = "employer_003",
                employerName   = "Fresh Produce Market",
                location       = "Kimironko Market, Kigali",
                latitude       = -1.9436,
                longitude      = 30.1048,
                requiredSkills = listOf("Customer service", "Basic math", "Honesty"),
                payment        = 3000.0,
                currency       = "RWF",
                duration       = 1L,          // 1 day (half-day shift)
                category       = "Retail",
                matchScore     = 0.92f,
                status         = "OPEN",
                deadline       = now + 2 * day,
                createdAt      = now - 1 * 60 * 60 * 1000L,
                updatedAt      = now - 1 * 60 * 60 * 1000L
            ),
            JobEntity(
                id             = "job_004",
                title          = "Phone Accessories Seller",
                description    = "Sell phone cases, chargers, and accessories at City Center kiosk. Commission-based pay plus daily allowance.",
                employerId     = "employer_004",
                employerName   = "TechMobile Rwanda",
                location       = "City Center, Kigali",
                latitude       = -1.9500,
                longitude      = 30.0588,
                requiredSkills = listOf("Sales", "Communication", "Tech knowledge"),
                payment        = 8000.0,
                currency       = "RWF",
                duration       = 1L,
                category       = "Retail",
                matchScore     = 0.68f,
                status         = "OPEN",
                deadline       = now + 3 * day,
                createdAt      = now - 30 * 60 * 1000L,
                updatedAt      = now - 30 * 60 * 1000L
            ),
            JobEntity(
                id             = "job_005",
                title          = "Event Server - Wedding",
                description    = "Serve food and drinks at wedding reception in Kacyiru. Event runs 2pm-10pm Saturday.",
                employerId     = "employer_005",
                employerName   = "Elite Events Rwanda",
                location       = "Kacyiru, Kigali",
                latitude       = -1.9628,
                longitude      = 30.0945,
                requiredSkills = listOf("Customer service", "Professional appearance", "Stamina"),
                payment        = 12000.0,
                currency       = "RWF",
                duration       = 1L,
                category       = "Hospitality",
                matchScore     = 0.88f,
                status         = "OPEN",
                deadline       = now + 4 * day,
                createdAt      = now - 3 * 60 * 60 * 1000L,
                updatedAt      = now - 3 * 60 * 60 * 1000L
            ),
            JobEntity(
                id             = "job_006",
                title          = "Restaurant Helper - Weekend",
                description    = "Assist in busy restaurant kitchen. Tasks include washing dishes, prep work, keeping kitchen clean. Meals provided.",
                employerId     = "employer_006",
                employerName   = "Tasty Bites Restaurant",
                location       = "Remera, Kigali",
                latitude       = -1.9581,
                longitude      = 30.0996,
                requiredSkills = listOf("Speed", "Cleanliness", "Teamwork"),
                payment        = 6000.0,
                currency       = "RWF",
                duration       = 1L,
                category       = "Hospitality",
                matchScore     = 0.79f,
                status         = "OPEN",
                deadline       = now + 6 * day,
                createdAt      = now - 4 * 60 * 60 * 1000L,
                updatedAt      = now - 4 * 60 * 60 * 1000L
            ),
            JobEntity(
                id             = "job_007",
                title          = "Farm Workers - Harvesting",
                description    = "Harvest beans and vegetables on small farm in Kicukiro. Early morning start (6am). Lunch included.",
                employerId     = "employer_007",
                employerName   = "Green Valley Farms",
                location       = "Kicukiro, Kigali",
                latitude       = -1.9894,
                longitude      = 30.1036,
                requiredSkills = listOf("Physical fitness", "Early riser", "Agriculture knowledge"),
                payment        = 4500.0,
                currency       = "RWF",
                duration       = 1L,
                category       = "Agriculture",
                matchScore     = 0.71f,
                status         = "OPEN",
                deadline       = now + 1 * day,
                createdAt      = now - 6 * 60 * 60 * 1000L,
                updatedAt      = now - 6 * 60 * 60 * 1000L
            ),
            JobEntity(
                id             = "job_008",
                title          = "Motorcycle Delivery Driver",
                description    = "Deliver food orders from restaurants to customers. Must have own motorcycle, valid license, and smartphone. Earn per delivery.",
                employerId     = "employer_008",
                employerName   = "QuickEats Delivery",
                location       = "Citywide, Kigali",
                latitude       = -1.9436,
                longitude      = 30.0619,
                requiredSkills = listOf("Motorcycle license", "Navigation", "Customer service"),
                payment        = 1500.0,
                currency       = "RWF",
                duration       = 0L,          // ongoing — no fixed duration
                category       = "Transport",
                matchScore     = 0.62f,
                status         = "OPEN",
                deadline       = null,
                createdAt      = now - 24 * 60 * 60 * 1000L,
                updatedAt      = now - 24 * 60 * 60 * 1000L
            ),
            JobEntity(
                id             = "job_009",
                title          = "Data Entry Assistant",
                description    = "Enter customer information into database system. Must be accurate typist with basic computer skills. Flexible 3-4 hour shifts available.",
                employerId     = "employer_009",
                employerName   = "Digital Solutions RW",
                location       = "Kacyiru, Kigali",
                latitude       = -1.9628,
                longitude      = 30.0945,
                requiredSkills = listOf("Computer skills", "Typing", "Attention to detail"),
                payment        = 10000.0,
                currency       = "RWF",
                duration       = 1L,
                category       = "Office Work",
                matchScore     = 0.95f,
                status         = "OPEN",
                deadline       = now + 10 * day,
                createdAt      = now - 12 * 60 * 60 * 1000L,
                updatedAt      = now - 12 * 60 * 60 * 1000L
            ),
            JobEntity(
                id             = "job_010",
                title          = "Office Cleaner - Morning Shift",
                description    = "Clean small office building before staff arrival (5am-8am). Monday to Friday. Must be trustworthy and reliable.",
                employerId     = "employer_010",
                employerName   = "CleanPro Services",
                location       = "Gikondo, Kigali",
                latitude       = -2.0069,
                longitude      = 30.0588,
                requiredSkills = listOf("Cleaning", "Reliability", "Time management"),
                payment        = 60000.0,
                currency       = "RWF",
                duration       = 7L,          // 1 week = 7 days
                category       = "Cleaning",
                matchScore     = 0.83f,
                status         = "OPEN",
                deadline       = now + 14 * day,
                createdAt      = now - 2 * day,
                updatedAt      = now - 2 * day
            )
        )

        jobs.forEach { jobDao.insertJob(it) }
        android.util.Log.d("DatabaseSeeder", "✅ Seeded ${jobs.size} jobs")
    }

    private suspend fun seedLearningModules() {
        val now = System.currentTimeMillis()
        val day = 24 * 60 * 60 * 1000L

        val modules = listOf(
            LearningModuleEntity(
                id                  = "module_001",
                title               = "Customer Service Basics",
                description         = "Learn how to greet customers professionally, handle complaints, and provide excellent service.",
                skillName           = "Customer Service",
                duration            = 15,
                contentUrl          = "https://learn.microcareer.app/customer-service-basics",
                contentType         = "VIDEO",
                difficulty          = "BEGINNER",
                prerequisites       = emptyList(),
                isCompleted         = false,
                progress            = 0,
                recommendationScore = 0.95f,
                createdAt           = now - 7 * day
            ),
            LearningModuleEntity(
                id                  = "module_002",
                title               = "Safe Construction Practices",
                description         = "Basic safety guidelines for construction sites. Learn about protective equipment and hazard awareness.",
                skillName           = "Construction Safety",
                duration            = 20,
                contentUrl          = "https://learn.microcareer.app/construction-safety",
                contentType         = "INTERACTIVE",
                difficulty          = "BEGINNER",
                prerequisites       = emptyList(),
                isCompleted         = false,
                progress            = 0,
                recommendationScore = 0.88f,
                createdAt           = now - 6 * day
            ),
            LearningModuleEntity(
                id                  = "module_003",
                title               = "Basic Smartphone Skills",
                description         = "Master essential smartphone features: calls, SMS, mobile money, photos, and maps.",
                skillName           = "Digital Literacy",
                duration            = 25,
                contentUrl          = "https://learn.microcareer.app/smartphone-basics",
                contentType         = "VIDEO",
                difficulty          = "BEGINNER",
                prerequisites       = emptyList(),
                isCompleted         = false,
                progress            = 0,
                recommendationScore = 0.92f,
                createdAt           = now - 5 * day
            ),
            LearningModuleEntity(
                id                  = "module_004",
                title               = "Time Management for Workers",
                description         = "Learn to plan your day, prioritize tasks, be punctual, and manage multiple jobs.",
                skillName           = "Time Management",
                duration            = 10,
                contentUrl          = "https://learn.microcareer.app/time-management",
                contentType         = "ARTICLE",
                difficulty          = "BEGINNER",
                prerequisites       = emptyList(),
                isCompleted         = false,
                progress            = 0,
                recommendationScore = 0.85f,
                createdAt           = now - 4 * day
            ),
            LearningModuleEntity(
                id                  = "module_005",
                title               = "Effective Communication Skills",
                description         = "Improve your speaking, listening, and professional communication.",
                skillName           = "Communication",
                duration            = 18,
                contentUrl          = "https://learn.microcareer.app/communication-skills",
                contentType         = "VIDEO",
                difficulty          = "INTERMEDIATE",
                prerequisites       = listOf("Customer Service"),
                isCompleted         = false,
                progress            = 0,
                recommendationScore = 0.79f,
                createdAt           = now - 3 * day
            )
        )

        modules.forEach { learningModuleDao.insertModule(it) }
        android.util.Log.d("DatabaseSeeder", "✅ Seeded ${modules.size} learning modules")
    }

    private suspend fun seedCurrentUser() {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser == null) {
            android.util.Log.d("DatabaseSeeder", "⚠️  No Firebase user logged in, skipping user seed")
            return
        }

        val userEntity = UserEntity(
            id                = firebaseUser.uid,
            email             = firebaseUser.email ?: "demo@microcareer.app",
            fullName          = firebaseUser.displayName ?: "Demo User",
            role              = "EMPLOYEE",
            phoneNumber       = firebaseUser.phoneNumber,
            profilePictureUrl = firebaseUser.photoUrl?.toString(),
            location          = "Kigali, Rwanda",
            latitude          = -1.9536,
            longitude         = 30.0606,
            availableHours    = null,
            companyName       = null,
            rating            = 4.5f,
            completedJobs     = 0,
            skills            = listOf("Customer Service", "Teamwork", "Reliability"),
            createdAt         = System.currentTimeMillis(),
            updatedAt         = System.currentTimeMillis(),
            isSynced          = false
        )

        userDao.insertUser(userEntity)
        android.util.Log.d("DatabaseSeeder", "✅ Seeded current user: ${userEntity.fullName}")
    }
}