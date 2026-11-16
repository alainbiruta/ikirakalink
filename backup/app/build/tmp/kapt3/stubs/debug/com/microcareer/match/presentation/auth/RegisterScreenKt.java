package com.microcareer.match.presentation.auth;

import androidx.compose.foundation.layout.*;
import androidx.compose.foundation.text.KeyboardOptions;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.input.KeyboardType;
import androidx.compose.ui.text.input.PasswordVisualTransformation;
import androidx.compose.ui.text.input.VisualTransformation;
import com.microcareer.match.R;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000R\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a\b\u0010\u0000\u001a\u00020\u0001H\u0003\u001aX\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00042\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00010\u00062\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\u000fH\u0003\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0010\u0010\u0011\u001a&\u0010\u0012\u001a\u00020\u00012\u001c\u0010\u0013\u001a\u0018\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00010\u0006\u00a2\u0006\u0002\b\u0015\u00a2\u0006\u0002\b\u0016H\u0003\u001ap\u0010\u0017\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00042\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00010\u00062\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\r2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00010\u001a2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u001b\u001a\u00020\r2\b\b\u0002\u0010\u001c\u001a\u00020\rH\u0003\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u001d\u0010\u001e\u001a4\u0010\u001f\u001a\u00020\u00012\u0006\u0010 \u001a\u00020\u00042\u0012\u0010!\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00010\u00062\u0006\u0010\"\u001a\u00020\u00042\u0006\u0010#\u001a\u00020\u0004H\u0003\u001a,\u0010$\u001a\u00020\u00012\u0006\u0010%\u001a\u00020&2\f\u0010\'\u001a\b\u0012\u0004\u0012\u00020\u00010\u001a2\f\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00010\u001aH\u0007\u001a\u0010\u0010)\u001a\u00020\u00012\u0006\u0010*\u001a\u00020\u0004H\u0003\u0082\u0002\u0007\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006+"}, d2 = {"AppleDivider", "", "AppleField", "value", "", "onValueChange", "Lkotlin/Function1;", "label", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "fieldBg", "Landroidx/compose/ui/graphics/Color;", "isLast", "", "keyboardOptions", "Landroidx/compose/foundation/text/KeyboardOptions;", "AppleField-qFjXxE8", "(Ljava/lang/String;Lkotlin/jvm/functions/Function1;Ljava/lang/String;Landroidx/compose/ui/graphics/vector/ImageVector;JZLandroidx/compose/foundation/text/KeyboardOptions;)V", "AppleFieldGroup", "content", "Landroidx/compose/foundation/layout/ColumnScope;", "Landroidx/compose/runtime/Composable;", "Lkotlin/ExtensionFunctionType;", "ApplePasswordField", "passwordVisible", "onToggleVisible", "Lkotlin/Function0;", "showToggle", "isError", "ApplePasswordField-V-9fs2A", "(Ljava/lang/String;Lkotlin/jvm/functions/Function1;Ljava/lang/String;ZLkotlin/jvm/functions/Function0;JZZZ)V", "AppleSegmentedControl", "selectedRole", "onRoleSelected", "employeeLabel", "employerLabel", "RegisterScreen", "viewModel", "Lcom/microcareer/match/presentation/auth/AuthViewModel;", "onNavigateToLogin", "onRegisterSuccess", "SectionLabel", "text", "app_debug"})
public final class RegisterScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void RegisterScreen(@org.jetbrains.annotations.NotNull()
    com.microcareer.match.presentation.auth.AuthViewModel viewModel, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToLogin, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onRegisterSuccess) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void AppleSegmentedControl(java.lang.String selectedRole, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onRoleSelected, java.lang.String employeeLabel, java.lang.String employerLabel) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void SectionLabel(java.lang.String text) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void AppleFieldGroup(kotlin.jvm.functions.Function1<? super androidx.compose.foundation.layout.ColumnScope, kotlin.Unit> content) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void AppleDivider() {
    }
}