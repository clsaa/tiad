package com.clsaa.tiad.idea.buildingblock.builder;

import com.clsaa.tiad.buidlingblock.entity.buildingblock.BuildingBlock;
import com.clsaa.tiad.buidlingblock.entity.descriptor.Location;
import com.clsaa.tiad.buidlingblock.entity.descriptor.UserSpecification;
import com.clsaa.tiad.buidlingblock.entity.param.BuildingBlockDataKeys;
import com.clsaa.tiad.common.data.DataContext;
import com.clsaa.tiad.common.data.MapDataContext;
import com.clsaa.tiad.idea.buildingblock.context.BuilderChain;
import com.clsaa.tiad.idea.buildingblock.context.BuilderContext;
import com.clsaa.tiad.idea.util.PsiTreeUtils;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.newvfs.impl.VirtualFileImpl;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.searches.AnnotationTargetsSearch;
import com.intellij.util.Query;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * @author clsaa
 */
public abstract class AbstractAnnotationBuilder extends Builder {

    @Override
    public List<BuildingBlock> doBuild(BuilderContext context, BuilderChain chain) {
        PsiFile psiFile = context.getPsiFile();
        final VirtualFileImpl virtualFile = (VirtualFileImpl) psiFile.getVirtualFile();
        final String fileId = String.valueOf(virtualFile.getId());
        Project project = psiFile.getProject();
        JavaPsiFacade javaPsiFacade = JavaPsiFacade.getInstance(project);
        final String annotationName = getAnnotationClass().getName();
        PsiClass annotationPsiClass = javaPsiFacade.findClass(annotationName, GlobalSearchScope.allScope(project));

        GlobalSearchScope searchScope = GlobalSearchScope.fileScope(psiFile);
        Query<PsiModifierListOwner> search = AnnotationTargetsSearch.search(Objects.requireNonNull(annotationPsiClass), searchScope);
        Collection<PsiModifierListOwner> owners = search.findAll();
        if (owners.isEmpty()) {
            chain.doBuild(context, chain);
            return Collections.emptyList();
        }
        List<BuildingBlock> result = new ArrayList<>(32);
        for (PsiModifierListOwner owner : owners) {
            final PsiAnnotation psiAnnotation = owner.getAnnotation(annotationName);
            final Location location = PsiTreeUtils.getLocation(owner);
            final UserSpecification userSpecification = getUserSpecification(psiAnnotation);

            MapDataContext mapDataContext = new MapDataContext();
            mapDataContext.putData(BuildingBlockDataKeys.RequiredToBuild.FILE_ID_DATA_KEY, fileId);
            mapDataContext.putData(BuildingBlockDataKeys.RequiredToBuild.LOCATION_DATA_KEY, location);
            mapDataContext.putData(BuildingBlockDataKeys.RequiredToBuild.USER_SPECIFICATION_DATA_KEY, userSpecification);

            final BuildingBlock buildingBlock = doBuild(mapDataContext);
            result.add(buildingBlock);
        }
        chain.doBuild(context, chain);
        return result;
    }

    public UserSpecification getUserSpecification(PsiAnnotation psiAnnotation) {
        return PsiTreeUtils.getUserSpecification(psiAnnotation);
    }

    abstract Class<? extends Annotation> getAnnotationClass();

    abstract BuildingBlock doBuild(DataContext dataContext);
}
