package com.microcareer.match.presentation.employer;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.compose.foundation.layout.*;
import androidx.compose.foundation.text.KeyboardOptions;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.ContentScale;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.input.KeyboardType;
import androidx.compose.ui.text.input.PasswordVisualTransformation;
import androidx.compose.ui.text.input.VisualTransformation;
import com.microcareer.match.presentation.profile.ProfileEvent;
import com.microcareer.match.presentation.profile.ProfileViewModel;
import com.microcareer.match.utils.LocaleHelper;
import com.microcareer.match.R;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u00002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0003\u001a4\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\t2\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00010\u000bH\u0003\u001a.\u0010\f\u001a\u00020\u00012\b\b\u0002\u0010\r\u001a\u00020\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00010\u00102\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00010\u0010H\u0007\u001a2\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u00032\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00010\u0010H\u0003\u00a8\u0006\u0015"}, d2 = {"EmployerHelpItem", "", "label", "", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "EmployerNotifRow", "sub", "checked", "", "onCheckedChange", "Lkotlin/Function1;", "EmployerProfileScreen", "viewModel", "Lcom/microcareer/match/presentation/profile/ProfileViewModel;", "onBack", "Lkotlin/Function0;", "onLogout", "ProfileMenuItem", "subtitle", "onClick", "app_debug"})
public final class EmployerProfileScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void EmployerProfileScreen(@org.jetbrains.annotations.NotNull()
    com.microcareer.match.presentation.profile.ProfileViewModel viewModel, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onLogout) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ProfileMenuItem(androidx.compose.ui.graphics.vector.ImageVector icon, java.lang.String label, java.lang.String subtitle, kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void EmployerNotifRow(java.lang.String label, java.lang.String sub, boolean checked, kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> onCheckedChange) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void EmployerHelpItem(java.lang.String label, androidx.compose.ui.graphics.vector.ImageVector icon) {
    }
}