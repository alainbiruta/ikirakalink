package com.microcareer.match.presentation.learning;

import androidx.lifecycle.ViewModel;
import com.microcareer.match.data.local.entity.LearningModuleEntity;
import com.microcareer.match.data.repository.LearningRepository;
import com.microcareer.match.data.repository.LearningRepositoryImpl;
import com.microcareer.match.domain.model.Resource;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.FlowPreview;
import kotlinx.coroutines.flow.*;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\t\u00a8\u0006\n"}, d2 = {"Lcom/microcareer/match/presentation/learning/LearningFilter;", "", "label", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getLabel", "()Ljava/lang/String;", "ALL", "RECOMMENDED", "COMPLETED", "app_debug"})
public enum LearningFilter {
    /*public static final*/ ALL /* = new ALL(null) */,
    /*public static final*/ RECOMMENDED /* = new RECOMMENDED(null) */,
    /*public static final*/ COMPLETED /* = new COMPLETED(null) */;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String label = null;
    
    LearningFilter(java.lang.String label) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getLabel() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static kotlin.enums.EnumEntries<com.microcareer.match.presentation.learning.LearningFilter> getEntries() {
        return null;
    }
}