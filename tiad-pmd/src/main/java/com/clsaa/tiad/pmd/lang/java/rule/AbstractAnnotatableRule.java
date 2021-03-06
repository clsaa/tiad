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
import net.sourceforge.pmd.lang.java.ast.ASTCompilationUnit;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * @author clsaa
 */
public abstract class AbstractAnnotatableRule extends AbstractTiadRule {
    public abstract Class<? extends Annotation> getTargetAnnotation();

    @Override
    public abstract Object visit(ASTAnnotation node, Object data);

    @Override
    public Object visit(ASTCompilationUnit node, Object data) {
        final List<ASTAnnotation> annotations = ASTUtils.findDescendantsAnnotations(node, this.getTargetAnnotation());
        for (ASTAnnotation annotation : annotations) {
            return annotation.jjtAccept(this, data);
        }
        return data;
    }
}
