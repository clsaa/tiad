/*
 *    Copyright 2019 Clsaa Group
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.clsaa.tiad.idea.service.impl;

import com.clsaa.tiad.buidlingblock.entity.descriptor.Cvs;
import com.clsaa.tiad.buidlingblock.entity.structure.ProjectDescriptor;
import com.clsaa.tiad.idea.service.ProjectDescriptorService;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.ProjectLevelVcsManager;
import com.intellij.openapi.vcs.VcsDirectoryMapping;
import com.intellij.openapi.vcs.impl.ProjectLevelVcsManagerImpl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProjectDescriptorServiceImpl implements ProjectDescriptorService {
    private Project project;
    private ProjectDescriptor projectDescriptor;

    public ProjectDescriptorServiceImpl(Project project) {
        this.project = project;
    }

    @Override
    public void refresh() {
        ProjectLevelVcsManagerImpl vcsManager = (ProjectLevelVcsManagerImpl) ProjectLevelVcsManager.getInstance(project);
        final List<VcsDirectoryMapping> directoryMappings = vcsManager.getDirectoryMappings();
        final Map<String, Cvs> cvsMap = directoryMappings.stream()
                .map(v -> Cvs.builder().id(v.getVcs() + v.getDirectory()).rootPath(v.getDirectory()).type(v.getVcs()).build())
                .collect(Collectors.toMap(Cvs::getId, c -> c));
        this.projectDescriptor = ProjectDescriptor.builder().idCsvIndex(cvsMap).build();
    }

    @Override
    public ProjectDescriptor get() {
        if (projectDescriptor == null) {
            this.refresh();
        }
        return projectDescriptor;
    }
}
