package com.microcareer.match.presentation.skill

/* ============================================================
   ====================== CV GENERATOR ========================
   Pure utility — no Android dependencies, fully unit-testable.
   Generates a clean, share-ready plain-text CV from SkillUiState.
   ============================================================ */

object CvGenerator {

    private const val WIDE_DIVIDER_LENGTH = 50
    private const val NARROW_DIVIDER_LENGTH = 30
    private const val SKILLS_PER_ROW = 4

    fun generateCvText(state: SkillUiState): String {
        val sb = StringBuilder()

        appendPersonalInfo(sb, state.personalInfo)
        appendSkills(sb, state.skills)
        appendWorkExperience(sb, state.workExperiences)
        appendEducation(sb, state.education)

        return sb.toString().trimEnd()
    }

    private fun appendPersonalInfo(sb: StringBuilder, info: PersonalInfoUiModel) {
        with(info) {
            if (fullName.isNotBlank()) {
                sb.appendLine(fullName.uppercase())
                sb.appendLine("═".repeat(WIDE_DIVIDER_LENGTH))
            }

            val contacts = listOfNotNull(
                email.takeIf { it.isNotBlank() },
                phone.takeIf { it.isNotBlank() },
                location.takeIf { it.isNotBlank() }
            )
            if (contacts.isNotEmpty()) {
                sb.appendLine(contacts.joinToString("  │  "))
            }
            if (linkedIn.isNotBlank()) {
                sb.appendLine("LinkedIn: $linkedIn")
            }

            if (summary.isNotBlank()) {
                sb.appendLine()
                sb.appendLine("PROFESSIONAL SUMMARY")
                sb.appendLine("─".repeat(NARROW_DIVIDER_LENGTH))
                sb.appendLine(summary)
            }
        }
    }

    private fun appendSkills(sb: StringBuilder, skills: List<String>) {
        if (skills.isEmpty()) return
        sb.appendLine()
        sb.appendLine("SKILLS")
        sb.appendLine("─".repeat(NARROW_DIVIDER_LENGTH))
        skills.chunked(SKILLS_PER_ROW).forEach { row ->
            sb.appendLine(row.joinToString("  •  "))
        }
    }

    private fun appendWorkExperience(sb: StringBuilder, experiences: List<WorkExperienceUiModel>) {
        if (experiences.isEmpty()) return
        sb.appendLine()
        sb.appendLine("WORK EXPERIENCE")
        sb.appendLine("─".repeat(NARROW_DIVIDER_LENGTH))
        experiences.forEach { exp ->
            sb.appendLine("${exp.jobTitle} at ${exp.company}")
            val period = if (exp.isCurrent) {
                "${exp.startDate} – Present"
            } else {
                "${exp.startDate} – ${exp.endDate}"
            }
            sb.appendLine(period)
            if (exp.description.isNotBlank()) {
                exp.description.lines().forEach { line ->
                    if (line.isNotBlank()) sb.appendLine("  • $line")
                }
            }
            sb.appendLine()
        }
    }

    private fun appendEducation(sb: StringBuilder, education: List<EducationUiModel>) {
        if (education.isEmpty()) return
        sb.appendLine()
        sb.appendLine("EDUCATION")
        sb.appendLine("─".repeat(NARROW_DIVIDER_LENGTH))
        education.forEach { edu ->
            val degreeField = listOfNotNull(
                edu.degree.takeIf { it.isNotBlank() },
                edu.fieldOfStudy.takeIf { it.isNotBlank() }
            ).joinToString(" in ")
            if (degreeField.isNotBlank()) sb.appendLine(degreeField)
            if (edu.institution.isNotBlank()) sb.appendLine(edu.institution)
            val years = listOfNotNull(
                edu.startDate.takeIf { it.isNotBlank() },
                edu.endDate.takeIf { it.isNotBlank() }
            ).joinToString(" – ")
            if (years.isNotBlank()) sb.appendLine(years)
            sb.appendLine()
        }
    }
}
