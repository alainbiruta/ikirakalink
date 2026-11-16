package com.microcareer.match.data.sync;

import android.content.Context;
import androidx.hilt.work.HiltWorker;
import androidx.work.*;
import com.google.firebase.auth.FirebaseAuth;
import com.microcareer.match.data.local.dao.JobDao;
import com.microcareer.match.data.local.dao.LearningModuleDao;
import com.microcareer.match.data.local.dao.SkillProfileDao;
import com.microcareer.match.data.local.entity.JobEntity;
import com.microcareer.match.data.local.entity.LearningModuleEntity;
import com.microcareer.match.data.remote.api.MicroCareerApiService;
import com.microcareer.match.data.remote.dto.*;
import dagger.assisted.Assisted;
import dagger.assisted.AssistedInject;
import kotlinx.coroutines.Dispatchers;
import java.util.concurrent.TimeUnit;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000\u0014\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\f\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0002\u001a\f\u0010\u0000\u001a\u00020\u0003*\u00020\u0004H\u0002\u00a8\u0006\u0005"}, d2 = {"toEntity", "Lcom/microcareer/match/data/local/entity/JobEntity;", "Lcom/microcareer/match/data/remote/dto/JobDto;", "Lcom/microcareer/match/data/local/entity/LearningModuleEntity;", "Lcom/microcareer/match/data/remote/dto/LearningModuleDto;", "app_debug"})
public final class SyncManagerKt {
    
    private static final com.microcareer.match.data.local.entity.JobEntity toEntity(com.microcareer.match.data.remote.dto.JobDto $this$toEntity) {
        return null;
    }
    
    private static final com.microcareer.match.data.local.entity.LearningModuleEntity toEntity(com.microcareer.match.data.remote.dto.LearningModuleDto $this$toEntity) {
        return null;
    }
}