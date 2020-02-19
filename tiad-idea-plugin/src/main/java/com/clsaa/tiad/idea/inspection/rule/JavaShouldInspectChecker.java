package com.clsaa.tiad.idea.inspection.rule;

import com.intellij.openapi.fileTypes.StdFileTypes;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiCompiledFile;
import com.intellij.psi.PsiFile;

public class JavaShouldInspectChecker implements ShouldInspectChecker {

    private JavaShouldInspectChecker() {
    }

    @Override
    public boolean shouldInspect(PsiFile psiFile) {
        boolean basicInspect = psiFile.getFileType().equals(StdFileTypes.JAVA) && !(psiFile instanceof PsiCompiledFile);
        if (!basicInspect) {
            return false;
        }
        return validScope(psiFile);
    }

    private boolean validScope(PsiFile file) {
        final VirtualFile virtualFile = file.getVirtualFile();
        final ProjectFileIndex projectFileIndex = ProjectRootManager.getInstance(file.getProject()).getFileIndex();
        return projectFileIndex.isInSource(virtualFile)
                && !projectFileIndex.isInTestSourceContent(virtualFile)
                && !projectFileIndex.isInLibraryClasses(virtualFile)
                && !projectFileIndex.isInLibrarySource(virtualFile);
    }

    public static ShouldInspectChecker getInstance() {
        return Holder.shouldInspectChecker;
    }

    private static class Holder {
        private static ShouldInspectChecker shouldInspectChecker = new JavaShouldInspectChecker();
    }

}