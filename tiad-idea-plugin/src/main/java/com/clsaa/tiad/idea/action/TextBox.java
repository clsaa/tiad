package com.clsaa.tiad.idea.action;

import com.clsaa.tiad.idea.service.TestService;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.StdFileTypes;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileEvent;
import com.intellij.openapi.vfs.VirtualFileListener;
import com.intellij.openapi.vfs.VirtualFileSystem;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.debugger.PsiVisitors;

public class TextBox extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {

        Project project = e.getData(PlatformDataKeys.PROJECT);
        TestService projectService = ServiceManager.getService(project, TestService.class);
        System.out.println("======");
        ProjectFileIndex.SERVICE.getInstance(project).iterateContent(fileOrDir -> {
            System.out.println(fileOrDir.getName());
            PsiFile file = PsiManager.getInstance(project).findFile(fileOrDir);
            PsiClass childOfAnyType = PsiTreeUtil.findChildOfAnyType(file, PsiClass.class);
//            childOfAnyType.getAnnotations();
            return true;
        }, file -> {
            FileType fileType = file.getFileType();
            return fileType == StdFileTypes.JAVA;
        });
        System.out.println("======");
        PsiFile file = e.getData(CommonDataKeys.PSI_FILE);

        PsiDirectory parent = file.getParent();
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
        file.accept(new PsiVisitors.FilteringPsiRecursiveElementWalkingVisitor() {
            @Override
            public void visitFile(PsiFile file) {
                super.visitFile(file);
                System.out.println(file.getName());
            }
        });
        Messages.showInputDialog(
                project,
                "What is your name?",
                "Input Your Name",
                Messages.getQuestionIcon());
    }
}
