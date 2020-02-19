package com.clsaa.tiad.idea.action;

import com.clsaa.tiad.idea.service.BuildingBlockStructureService;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.StdFileTypes;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.*;
import com.intellij.openapi.vfs.newvfs.BulkFileListener;
import com.intellij.openapi.vfs.newvfs.events.VFileEvent;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TextBox extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {

        Project project = e.getData(PlatformDataKeys.PROJECT);
        BuildingBlockStructureService projectService = BuildingBlockStructureService.getInstance(project);
        projectService.refresh();
        System.out.println("======");
        final ProjectFileIndex projectFileIndex = ProjectFileIndex.SERVICE.getInstance(project);
        projectFileIndex.iterateContent(fileOrDir -> {
            System.out.println(fileOrDir.getName());
            PsiFile file = PsiManager.getInstance(project).findFile(fileOrDir);
            PsiClass childOfAnyType = PsiTreeUtil.findChildOfAnyType(file, PsiClass.class);
            if (childOfAnyType == null) {
                System.out.println("is null " + fileOrDir.getName());
                projectFileIndex.isInContent(fileOrDir);
                file.accept(new JavaRecursiveElementVisitor() {
                    @Override
                    public void visitElement(PsiElement element) {
                        super.visitElement(element);
                    }
                });
            }
//            childOfAnyType.getAnnotations();
            return true;
        }, file -> {
            FileType fileType = file.getFileType();
            return fileType == StdFileTypes.JAVA;
        });

        project.getMessageBus().connect().subscribe(VirtualFileManager.VFS_CHANGES, new BulkFileListener() {
            @Override
            public void after(@NotNull List<? extends VFileEvent> events) {
                for (VFileEvent event : events) {
                    final VirtualFile file = event.getFile();
                    final boolean inSource = projectFileIndex.isInSource(file);
                    final boolean inContent = projectFileIndex.isInContent(file);
                    System.out.println("getMessageBus: " + file.getName());
                }
            }
        });
        System.out.println("======");
        PsiFile file = e.getData(CommonDataKeys.PSI_FILE);
        if (file == null) {
            return;
        }

        VirtualFile virtualFile = file.getVirtualFile();
        VirtualFileSystem fileSystem = virtualFile.getFileSystem();
        fileSystem.addVirtualFileListener(new VirtualFileListener() {
            @Override
            public void fileCreated(@NotNull VirtualFileEvent event) {
                VirtualFile file1 = event.getFile();
                System.out.println("fileCreated:" + file1.getName());
            }

            @Override
            public void contentsChanged(@NotNull VirtualFileEvent event) {
                VirtualFile file1 = event.getFile();
                System.out.println("contentsChanged:" + file1.getName());
            }
        });
        PsiManager manager = file.getManager();

        manager.addPsiTreeChangeListener(new PsiTreeChangeListener() {
            @Override
            public void beforeChildAddition(@NotNull PsiTreeChangeEvent event) {
            }

            @Override
            public void beforeChildRemoval(@NotNull PsiTreeChangeEvent event) {

            }

            @Override
            public void beforeChildReplacement(@NotNull PsiTreeChangeEvent event) {

            }

            @Override
            public void beforeChildMovement(@NotNull PsiTreeChangeEvent event) {

            }

            @Override
            public void beforeChildrenChange(@NotNull PsiTreeChangeEvent event) {
                System.out.println("beforeChildrenChange" + event.getFile().getName());
            }

            @Override
            public void beforePropertyChange(@NotNull PsiTreeChangeEvent event) {
                System.out.println("beforePropertyChange" + event.getFile().getName());
            }

            @Override
            public void childAdded(@NotNull PsiTreeChangeEvent event) {
                System.out.println("childAdded" + event.getFile().getName());
            }

            @Override
            public void childRemoved(@NotNull PsiTreeChangeEvent event) {

            }

            @Override
            public void childReplaced(@NotNull PsiTreeChangeEvent event) {

            }

            @Override
            public void childrenChanged(@NotNull PsiTreeChangeEvent event) {

            }

            @Override
            public void childMoved(@NotNull PsiTreeChangeEvent event) {

            }

            @Override
            public void propertyChanged(@NotNull PsiTreeChangeEvent event) {

            }
        });

        Messages.showInputDialog(
                project,
                "What is your name?",
                "Input Your Name",
                Messages.getQuestionIcon());
    }
}
