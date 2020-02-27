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
package com.clsaa.tiad.idea.action.creator;

import com.intellij.CommonBundle;
import com.intellij.ide.IdeView;
import com.intellij.ide.actions.CreateFileFromTemplateDialog;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.application.WriteActionAware;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Ref;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNameIdentifierOwner;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Collections;
import java.util.Map;

/**
 * @author Eugene.Kudelevsky
 */
public abstract class TiadCreateFromTemplateAction<T extends PsiElement> extends AnAction implements WriteActionAware {
    protected static final Logger LOG = Logger.getInstance("#com.intellij.ide.actions.CreateFromTemplateAction");

    public TiadCreateFromTemplateAction(String text, String description, Icon icon) {
        super(text, description, icon);
    }

    @Override
    public final void actionPerformed(@NotNull final AnActionEvent e) {
        final DataContext dataContext = e.getDataContext();

        final IdeView view = LangDataKeys.IDE_VIEW.getData(dataContext);
        if (view == null) {
            return;
        }

        final Project project = CommonDataKeys.PROJECT.getData(dataContext);

        final PsiDirectory dir = view.getOrChooseDirectory();
        if (dir == null || project == null) {
            return;
        }

        final CreateFileFromTemplateDialog.Builder builder = CreateFileFromTemplateDialog.createDialog(project);
        buildDialog(project, dir, builder);

        final Ref<String> selectedTemplateName = Ref.create(null);
        builder.show(getErrorTitle(), getDefaultTemplateName(dir),
                new CreateFileFromTemplateDialog.FileCreator<T>() {

                    @Override
                    public T createFile(@NotNull String name, @NotNull String templateName) {
                        selectedTemplateName.set(templateName);
                        return TiadCreateFromTemplateAction.this.createFile(name, templateName, dir);
                    }

                    @Override
                    public boolean startInWriteAction() {
                        return TiadCreateFromTemplateAction.this.startInWriteAction();
                    }

                    @Override
                    @NotNull
                    public String getActionName(@NotNull String name, @NotNull String templateName) {
                        return TiadCreateFromTemplateAction.this.getActionName(dir, name, templateName);
                    }
                },
                createdElement -> {
                    if (createdElement != null) {
                        view.selectElement(createdElement);
                        postProcess(createdElement, selectedTemplateName.get(), builder.getCustomProperties());
                    }
                });
    }

    protected void postProcess(T createdElement, String templateName, Map<String, String> customProperties) {
    }

    @Nullable
    protected T createFile(String name, String templateName, PsiDirectory dir) {
        return this.createFile(name, templateName, dir, this.getProperties());
    }

    @Nullable
    protected abstract T createFile(String name, String templateName, PsiDirectory dir, Map<String, String> properties);

    private Map<String, String> getProperties() {
        return Collections.EMPTY_MAP;
    }

    protected abstract void buildDialog(Project project, PsiDirectory directory, CreateFileFromTemplateDialog.Builder builder);

    @Nullable
    protected String getDefaultTemplateName(@NotNull PsiDirectory dir) {
        String property = getDefaultTemplateProperty();
        return property == null ? null : PropertiesComponent.getInstance(dir.getProject()).getValue(property);
    }

    @Nullable
    protected String getDefaultTemplateProperty() {
        return null;
    }

    @Override
    public void update(@NotNull final AnActionEvent e) {
        final DataContext dataContext = e.getDataContext();
        final Presentation presentation = e.getPresentation();

        final boolean enabled = isAvailable(dataContext);

        presentation.setEnabledAndVisible(enabled);
    }

    protected boolean isAvailable(DataContext dataContext) {
        final Project project = CommonDataKeys.PROJECT.getData(dataContext);
        final IdeView view = LangDataKeys.IDE_VIEW.getData(dataContext);
        return project != null && view != null && view.getDirectories().length != 0;
    }

    protected abstract String getActionName(PsiDirectory directory, @NotNull String newName, String templateName);

    @NotNull
    protected String getErrorTitle() {
        return CommonBundle.getErrorTitle();
    }

    //todo append $END variable to templates?
    public static void moveCaretAfterNameIdentifier(PsiNameIdentifierOwner createdElement) {
        final Project project = createdElement.getProject();
        final Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
        if (editor != null) {
            final VirtualFile virtualFile = createdElement.getContainingFile().getVirtualFile();
            if (virtualFile != null) {
                if (FileDocumentManager.getInstance().getDocument(virtualFile) == editor.getDocument()) {
                    final PsiElement nameIdentifier = createdElement.getNameIdentifier();
                    if (nameIdentifier != null) {
                        editor.getCaretModel().moveToOffset(nameIdentifier.getTextRange().getEndOffset());
                    }
                }
            }
        }
    }
}
