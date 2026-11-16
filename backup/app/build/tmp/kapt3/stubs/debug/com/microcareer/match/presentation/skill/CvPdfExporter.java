package com.microcareer.match.presentation.skill;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u001e\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\u000eJ&\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00142\u0006\u0010\u0015\u001a\u00020\u000e2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0004H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0019"}, d2 = {"Lcom/microcareer/match/presentation/skill/CvPdfExporter;", "", "()V", "FONT_SIZE_BODY", "", "FONT_SIZE_HEADING", "LINE_HEIGHT", "MARGIN", "PAGE_HEIGHT", "", "PAGE_WIDTH", "buildPdf", "", "cvText", "", "exportToDownloads", "context", "Landroid/content/Context;", "fileName", "wrapLine", "", "line", "paint", "Landroid/graphics/Paint;", "maxWidth", "app_debug"})
public final class CvPdfExporter {
    private static final int PAGE_WIDTH = 595;
    private static final int PAGE_HEIGHT = 842;
    private static final float MARGIN = 50.0F;
    private static final float LINE_HEIGHT = 16.0F;
    private static final float FONT_SIZE_BODY = 11.0F;
    private static final float FONT_SIZE_HEADING = 13.0F;
    @org.jetbrains.annotations.NotNull()
    public static final com.microcareer.match.presentation.skill.CvPdfExporter INSTANCE = null;
    
    private CvPdfExporter() {
        super();
    }
    
    /**
     * Writes [cvText] to a PDF and saves it to the public Downloads folder.
     * Returns the display path/URI string on success, throws on failure.
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String exportToDownloads(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String cvText, @org.jetbrains.annotations.NotNull()
    java.lang.String fileName) {
        return null;
    }
    
    private final byte[] buildPdf(java.lang.String cvText) {
        return null;
    }
    
    /**
     * Breaks a single line into wrapped sub-lines that fit within [maxWidth].
     */
    private final java.util.List<java.lang.String> wrapLine(java.lang.String line, android.graphics.Paint paint, float maxWidth) {
        return null;
    }
}