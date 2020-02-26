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

package com.clsaa.tiad.pmd.lang.java.util;

import com.clsaa.tiad.buidlingblock.entity.buildingblock.BuildingBlock;
import com.clsaa.tiad.buidlingblock.entity.descriptor.Location;
import com.clsaa.tiad.buidlingblock.entity.structure.BuildingBlockStructure;
import net.sourceforge.pmd.lang.java.ast.*;
import net.sourceforge.pmd.lang.java.typeresolution.typedefinition.JavaTypeDefinition;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/**
 * @author clsaa
 */
public interface ASTUtils {

    static ASTAnnotation findFirstAnnotation(Annotatable node, Class<? extends Annotation> targetAnnotation) {
        for (ASTAnnotation declaredAnnotation : node.getDeclaredAnnotations()) {
            final Class<?> type = declaredAnnotation.getType();
            if (type != null && type.getName().equals(targetAnnotation.getName())) {
                return declaredAnnotation;
            }
        }
        return null;
    }

    static List<ASTAnnotation> findAnnotations(Annotatable node, Class<? extends Annotation> targetAnnotation) {
        List<ASTAnnotation> result = new ArrayList<>(8);
        for (ASTAnnotation declaredAnnotation : node.getDeclaredAnnotations()) {
            final Class<?> type = declaredAnnotation.getType();
            if (type != null && type.getName().equals(targetAnnotation.getName())) {
                result.add(declaredAnnotation);
            }
        }
        return result;
    }


    static boolean existAnnotation(Annotatable node, Class<? extends Annotation> targetAnnotation) {
        for (ASTAnnotation declaredAnnotation : node.getDeclaredAnnotations()) {
            final Class<?> type = declaredAnnotation.getType();
            if (type != null && type.getName().equals(targetAnnotation.getName())) {
                return true;
            }
        }
        return false;
    }

    static List<ASTAnnotation> findDescendantsAnnotations(JavaNode node, Class<? extends Annotation> targetAnnotation) {
        List<ASTAnnotation> result = new ArrayList<>(8);
        for (ASTAnnotation declaredAnnotation : node.findDescendantsOfType(ASTAnnotation.class)) {
            final Class<?> type = declaredAnnotation.getType();
            if (type != null && type.getName().equals(targetAnnotation.getName())) {
                result.add(declaredAnnotation);
            }
            if (type == null) {
                if (!declaredAnnotation.getAnnotationName().equals(targetAnnotation.getSimpleName())) {
                    continue;
                }
                ASTCompilationUnit compilationUnit;
                if (node instanceof ASTCompilationUnit) {
                    compilationUnit = (ASTCompilationUnit) node;
                } else {
                    compilationUnit = node.getFirstParentOfAnyType(ASTCompilationUnit.class);
                }
                if (compilationUnit != null) {
                    List<ASTImportDeclaration> descendantsOfType = compilationUnit.findDescendantsOfType(ASTImportDeclaration.class);
                    for (ASTImportDeclaration importDeclaration : descendantsOfType) {
                        final String importedName = importDeclaration.getImportedName();
                        if (importedName.equals(targetAnnotation.getName())
                                || importedName.equals(targetAnnotation.getPackage().getName())) {
                            result.add(declaredAnnotation);
                        }
                    }
                }
            }
        }
        return result;
    }

    static ASTAnnotation findFirstDescendantsAnnotation(JavaNode node, Class<? extends Annotation> targetAnnotation) {
        for (ASTAnnotation declaredAnnotation : node.findDescendantsOfType(ASTAnnotation.class)) {
            final Class<?> type = declaredAnnotation.getType();
            if (type != null && type.getName().equals(targetAnnotation.getName())) {
                return declaredAnnotation;
            }
            if (type == null) {
                if (!declaredAnnotation.getAnnotationName().equals(targetAnnotation.getSimpleName())) {
                    continue;
                }
                ASTCompilationUnit compilationUnit;
                if (node instanceof ASTCompilationUnit) {
                    compilationUnit = (ASTCompilationUnit) node;
                } else {
                    compilationUnit = node.getFirstParentOfAnyType(ASTCompilationUnit.class);
                }
                if (compilationUnit != null) {
                    List<ASTImportDeclaration> descendantsOfType = compilationUnit.findDescendantsOfType(ASTImportDeclaration.class);
                    for (ASTImportDeclaration importDeclaration : descendantsOfType) {
                        final String importedName = importDeclaration.getImportedName();
                        if (importedName.equals(targetAnnotation.getName())
                                || importedName.equals(targetAnnotation.getPackage().getName())) {
                            return declaredAnnotation;
                        }
                    }
                }
            }

        }
        return null;
    }

    static ASTPackageDeclaration getPackageDeclaration(JavaNode node) {
        final ASTCompilationUnit compilationUnit = node.getFirstParentOfType(ASTCompilationUnit.class);
        final List<ASTPackageDeclaration> packageDeclarations = compilationUnit.findDescendantsOfType(ASTPackageDeclaration.class);
        for (ASTPackageDeclaration packageDeclaration : packageDeclarations) {
            return packageDeclaration;
        }
        return null;
    }

    static <T extends BuildingBlock> T findInStructure(BuildingBlockStructure structure, Class<T> target, JavaNode node) {
        final List<ASTType> resultTypes = node.findChildrenOfType(ASTType.class);
        if (!resultTypes.isEmpty()) {
            final ASTType type = resultTypes.get(0);
            final JavaTypeDefinition typeDefinition = type.getTypeDefinition();
            final List<T> buildingBlocks = structure.getByClass(target);
            for (BuildingBlock buildingBlock : buildingBlocks) {
                final Location location = buildingBlock.getLocation();
                final String packageName = location.getPackageName();
                final String simpleClassName = location.getSimpleClassName();
                final String fullClassName = location.getFullClassName();
                if (typeDefinition != null) {
                    if (fullClassName != null && fullClassName.equals(typeDefinition.getType().getName())) {
                        return target.cast(buildingBlock);
                    }
                } else {
                    final String className = type.getTypeImage();
                    if (simpleClassName != null && simpleClassName.equals(className)) {
                        final ASTCompilationUnit compilationUnit = node.getFirstParentOfType(ASTCompilationUnit.class);
                        final List<ASTImportDeclaration> importDeclarations = compilationUnit.findDescendantsOfType(ASTImportDeclaration.class);
                        for (ASTImportDeclaration importDeclaration : importDeclarations) {
                            if (importDeclaration != null && importDeclaration.getPackageName().equals(packageName)) {
                                return target.cast(buildingBlock);
                            }
                        }
                    }
                }
            }
        }
        return null;
    }


}
