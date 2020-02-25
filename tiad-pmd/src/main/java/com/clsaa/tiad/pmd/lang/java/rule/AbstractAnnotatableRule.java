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

package com.clsaa.tiad.pmd.lang.java.rule;

import com.clsaa.tiad.pmd.lang.java.util.ASTUtils;
import net.sourceforge.pmd.lang.java.ast.ASTAnnotation;
import net.sourceforge.pmd.lang.java.ast.Annotatable;
import net.sourceforge.pmd.lang.java.ast.JavaNode;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * @author clsaa
 */
public abstract class AbstractAnnotatableRule extends AbstractTiadRule {
    public abstract Class<? extends Annotation> getTargetAnnotation();

    @Override
    public Object visit(JavaNode node, Object data) {
        final List<Annotatable> annotatables = node.findDescendantsOfType(Annotatable.class);
        int annotationCount = 0;
        for (Annotatable annotatable : annotatables) {
            final ASTAnnotation annotation = ASTUtils.findFirstAnnotation(annotatable, this.getTargetAnnotation());
            if (annotation != null) {
                annotationCount++;
            }
        }
        if (annotationCount == 0) {
            return null;
        }
        node.childrenAccept(this, data);
        return null;
    }
}
