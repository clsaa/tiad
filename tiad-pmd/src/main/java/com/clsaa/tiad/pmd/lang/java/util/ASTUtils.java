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

import net.sourceforge.pmd.lang.java.ast.ASTAnnotation;
import net.sourceforge.pmd.lang.java.ast.Annotatable;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/**
 * @author clsaa
 */
public interface ASTUtils {

    static ASTAnnotation findFirstAnnotation(Annotatable node, Class<? extends Annotation> targetAnnotation) {
        for (ASTAnnotation declaredAnnotation : node.getDeclaredAnnotations()) {
            if (declaredAnnotation.getType().getName().equals(targetAnnotation.getName())) {
                return declaredAnnotation;
            }
        }
        return null;
    }

    static List<ASTAnnotation> findAnnotations(Annotatable node, Class<? extends Annotation> targetAnnotation) {
        List<ASTAnnotation> result = new ArrayList<>(8);
        for (ASTAnnotation declaredAnnotation : node.getDeclaredAnnotations()) {
            if (declaredAnnotation.getType().getName().equals(targetAnnotation.getName())) {
                result.add(declaredAnnotation);
            }
        }
        return result;
    }


    static boolean existAnnotation(Annotatable node, Class<? extends Annotation> targetAnnotation) {
        for (ASTAnnotation declaredAnnotation : node.getDeclaredAnnotations()) {
            if (declaredAnnotation.getType().getName().equals(targetAnnotation.getName())) {
                return true;
            }
        }
        return false;
    }

    static List<ASTAnnotation> findDescendantsAnnotations(Annotatable node, Class<? extends Annotation> targetAnnotation) {
        List<ASTAnnotation> result = new ArrayList<>(8);
        for (ASTAnnotation declaredAnnotation : node.findDescendantsOfType(ASTAnnotation.class)) {
            final Class<?> type = declaredAnnotation.getType();
            if (type != null && type.getName().equals(targetAnnotation.getName())) {
                result.add(declaredAnnotation);
            }
        }
        return result;
    }
}
