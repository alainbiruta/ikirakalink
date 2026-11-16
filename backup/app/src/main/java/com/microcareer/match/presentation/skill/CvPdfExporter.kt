package com.microcareer.match.presentation.skill

import android.content.ContentValues
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

object CvPdfExporter {

    private const val PAGE_WIDTH = 595   // A4 at 72 dpi
    private const val PAGE_HEIGHT = 842
    private const val MARGIN = 50f
    private const val LINE_HEIGHT = 16f
    private const val FONT_SIZE_BODY = 11f
    private const val FONT_SIZE_HEADING = 13f

    /**
     * Writes [cvText] to a PDF and saves it to the public Downloads folder.
     * Returns the display path/URI string on success, throws on failure.
     */
    fun exportToDownloads(context: Context, cvText: String, fileName: String): String {
        val pdfBytes = buildPdf(cvText)

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Scoped storage (Android 10+)
            val values = ContentValues().apply {
                put(MediaStore.Downloads.DISPLAY_NAME, fileName)
                put(MediaStore.Downloads.MIME_TYPE, "application/pdf")
                put(MediaStore.Downloads.IS_PENDING, 1)
            }
            val resolver = context.contentResolver
            val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values)
                ?: throw IllegalStateException("Could not create Downloads entry")
            resolver.openOutputStream(uri)!!.use { it.write(pdfBytes) }
            values.clear()
            values.put(MediaStore.Downloads.IS_PENDING, 0)
            resolver.update(uri, values, null, null)
            uri.toString()
        } else {
            // Legacy storage (Android 9 and below)
            val dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            dir.mkdirs()
            val file = File(dir, fileName)
            FileOutputStream(file).use { it.write(pdfBytes) }
            file.absolutePath
        }
    }

    private fun buildPdf(cvText: String): ByteArray {
        val document = PdfDocument()
        val bodyPaint = Paint().apply {
            textSize = FONT_SIZE_BODY
            typeface = Typeface.MONOSPACE
            isAntiAlias = true
        }
        val headingPaint = Paint().apply {
            textSize = FONT_SIZE_HEADING
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            isAntiAlias = true
        }

        val maxWidth = PAGE_WIDTH - MARGIN * 2
        val lines = cvText.lines().flatMap { line ->
            wrapLine(line, bodyPaint, maxWidth)
        }

        var pageNumber = 1
        var pageInfo = PdfDocument.PageInfo.Builder(PAGE_WIDTH, PAGE_HEIGHT, pageNumber).create()
        var page = document.startPage(pageInfo)
        var canvas: Canvas = page.canvas
        var y = MARGIN + FONT_SIZE_BODY

        for (rawLine in lines) {
            if (y + LINE_HEIGHT > PAGE_HEIGHT - MARGIN) {
                document.finishPage(page)
                pageNumber++
                pageInfo = PdfDocument.PageInfo.Builder(PAGE_WIDTH, PAGE_HEIGHT, pageNumber).create()
                page = document.startPage(pageInfo)
                canvas = page.canvas
                y = MARGIN + FONT_SIZE_BODY
            }
            val isSectionHeader = rawLine.startsWith("===") || rawLine.endsWith("===")
                    || rawLine.all { it == '─' || it == '-' || it == '=' }
            val paint = if (isSectionHeader) headingPaint else bodyPaint
            canvas.drawText(rawLine, MARGIN, y, paint)
            y += LINE_HEIGHT
        }
        document.finishPage(page)

        val output = java.io.ByteArrayOutputStream()
        document.writeTo(output)
        document.close()
        return output.toByteArray()
    }

    /** Breaks a single line into wrapped sub-lines that fit within [maxWidth]. */
    private fun wrapLine(line: String, paint: Paint, maxWidth: Float): List<String> {
        if (paint.measureText(line) <= maxWidth) return listOf(line)
        val result = mutableListOf<String>()
        var start = 0
        while (start < line.length) {
            val count = paint.breakText(line, start, line.length, true, maxWidth, null)
            val end = if (count == 0) start + 1 else start + count
            result.add(line.substring(start, end))
            start = end
        }
        return result
    }
}