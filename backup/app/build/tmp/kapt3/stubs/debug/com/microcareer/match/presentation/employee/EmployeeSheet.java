package com.microcareer.match.presentation.employee;

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
import com.microcareer.match.presentation.profile.ProfileViewModel;
import com.microcareer.match.presentation.profile.ProfileEvent;
import com.microcareer.match.utils.LocaleHelper;
import com.microcareer.match.R;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0007\b\u0082\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007\u00a8\u0006\b"}, d2 = {"Lcom/microcareer/match/presentation/employee/EmployeeSheet;", "", "(Ljava/lang/String;I)V", "EditInfo", "Notifications", "Language", "Security", "Help", "app_debug"})
enum EmployeeSheet {
    /*public static final*/ EditInfo /* = new EditInfo() */,
    /*public static final*/ Notifications /* = new Notifications() */,
    /*public static final*/ Language /* = new Language() */,
    /*public static final*/ Security /* = new Security() */,
    /*public static final*/ Help /* = new Help() */;
    
    EmployeeSheet() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public static kotlin.enums.EnumEntries<com.microcareer.match.presentation.employee.EmployeeSheet> getEntries() {
        return null;
    }
}