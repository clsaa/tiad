package com.clsaa.tiad.idea.service.impl;

import com.clsaa.tiad.idea.service.TestService;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ContentIterator;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

public class TestServiceImpl implements TestService {
    public TestServiceImpl(Project project) {
        ProjectFileIndex.SERVICE.getInstance(project).iterateContent(new ContentIterator() {
            @Override
            public boolean processFile(@NotNull VirtualFile fileOrDir) {
                return false;
            }
        });
    }
}
