package com.clsaa.tiad.idea.activity;

import com.clsaa.tiad.idea.executors.TiadReadExecutor;
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

        //init BuildingBlockStructure
        ReadAction.nonBlocking(() -> BuildingBlockStructureService.getInstance(project).refresh())
                .inSmartMode(project).submit(TiadReadExecutor.getInstance());

//        final PsiManagerImpl psiManager = (PsiManagerImpl) PsiManager.getInstance(project);
//        psiManager.addTreeChangePreprocessor(new PsiTreeChangePreprocessor() {
//            @Override
//            public void treeChanged(@NotNull PsiTreeChangeEventImpl event) {
//                final PsiFile file = event.getFile();
//                if (file == null) {
//                    return;
//                }
//                System.out.println("addTreeChangePreprocessor:" + file.getName());
//                if (file.getFileType().equals(StdFileTypes.JAVA)) {
//                    ReadAction.nonBlocking(() -> BuildingBlockStructureService.getInstance(project).update(file))
//                            .inSmartMode(project).submit(TiadReadExecutor.getInstance());
//                }
//            }
//        });

//        final ProjectFileIndex projectFileIndex = ProjectFileIndex.SERVICE.getInstance(project);
//        project.getMessageBus().connect().subscribe(VirtualFileManager.VFS_CHANGES, new BulkFileListener() {
//            @Override
//            public void after(@NotNull List<? extends VFileEvent> events) {
//                for (VFileEvent event : events) {
//                    final VirtualFile fileOrDir = event.getFile();
//                    final boolean isJavaFile = Objects.requireNonNull(fileOrDir).getFileType().equals(StdFileTypes.JAVA);
//                    final boolean inContent = projectFileIndex.isInContent(fileOrDir);
//                    if (isJavaFile && inContent) {
//                        log.info("VirtualFileManager.VFS_CHANGES: " + fileOrDir.getName());
//                        PsiFile file = PsiManager.getInstance(project).findFile(fileOrDir);
//                        ReadAction.nonBlocking(() -> BuildingBlockStructureService.getInstance(project).update(file))
//                                .inSmartMode(project).submit(TiadReadExecutor.getInstance());
//                    }
//                }
//            }
//        });

    }
}
