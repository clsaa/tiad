package com.clsaa.tiad.idea.activity;

import com.clsaa.tiad.idea.service.BuildingBlockStructureService;
import com.intellij.openapi.application.ReadAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.StartupActivity;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

@Slf4j
public class StartUpActivity implements StartupActivity {
    @Override
    public void runActivity(@NotNull Project project) {
        log.info("taid start running...");
        ReadAction.nonBlocking(() -> {
            final BuildingBlockStructureService structureService = BuildingBlockStructureService.getInstance(project);
            structureService.refresh();
            return structureService;
        }).inSmartMode(project).submit(com.intellij.util.concurrency.NonUrgentExecutor.getInstance());
    }
}
