package com.clsaa.tiad.idea.util;

import com.clsaa.tiad.buidlingblock.constance.DefaultStrings;
import com.clsaa.tiad.buidlingblock.entity.descriptor.Location;
import com.clsaa.tiad.buidlingblock.entity.descriptor.UserSpecification;
import com.intellij.psi.*;
import com.intellij.psi.util.MethodSignature;
import com.intellij.psi.util.PsiTreeUtil;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Objects;

/**
 * @author clsaa
 */
public interface PsiTreeUtils {

    /**
     * find children annotation of the element
     *
     * @param element         element
     * @param annotationClass target annotation
     * @return target annotation list
     */
    static Collection<PsiAnnotation> findChildrenOfAnnotation(PsiElement element,
                                                              Class<? extends Annotation> annotationClass) {
        Collection<PsiAnnotation> psiAnnotations = PsiTreeUtil.findChildrenOfType(element, PsiAnnotation.class);
        String annotationName = annotationClass.getName();
        psiAnnotations.removeIf(psiAnnotation -> annotationName.equals(psiAnnotation.getQualifiedName()));
        return psiAnnotations;
    }

    /**
     * return attribute of user specification
     *
     * @param psiAnnotation psiAnnotation
     * @return user specification
     */
    static UserSpecification getUserSpecification(PsiAnnotation psiAnnotation) {
        final String code = Objects.requireNonNull(psiAnnotation.findAttributeValue("code")).getText();
        final String name = Objects.requireNonNull(psiAnnotation.findAttributeValue("name")).getText();
        final String desc = Objects.requireNonNull(psiAnnotation.findAttributeValue("description")).getText();
        return new UserSpecification(code, name, desc);
    }

    static Location getLocation(PsiModifierListOwner psiModifierListOwner) {
        if (psiModifierListOwner instanceof PsiPackage) {
            final PsiPackage psiPackage = (PsiPackage) psiModifierListOwner;
            final Location location = Location.builder().packageName(psiPackage.getQualifiedName()).build();
            return location;
        }
        if (psiModifierListOwner instanceof PsiClass) {
            final PsiClass psiClass = (PsiClass) psiModifierListOwner;
            final PsiJavaFile psiJavaFile = (PsiJavaFile) psiClass.getContainingFile();
            final Location location = Location.builder()
                    .packageName(psiJavaFile.getPackageName())
                    .simpleClassName(psiClass.getName())
                    .fullClassName(psiJavaFile.getPackageName() + DefaultStrings.DOT + psiClass.getName())
                    .build();
            return location;
        }
        if (psiModifierListOwner instanceof PsiMethod) {
            final PsiMethod psiMethod = (PsiMethod) psiModifierListOwner;
            final PsiClass psiClass = psiMethod.getContainingClass();
            final PsiJavaFile psiJavaFile = (PsiJavaFile) Objects.requireNonNull(psiClass).getContainingFile();
            final Location location = Location.builder()
                    .packageName(psiJavaFile.getPackageName())
                    .simpleClassName(psiClass.getName())
                    .fullClassName(psiJavaFile.getPackageName() + DefaultStrings.DOT + psiClass.getName())
                    .methodName(getSignature(psiMethod))
                    .build();
            return location;
        }
        throw new IllegalStateException("unsupported PsiModifierListOwner");
    }

    static String getSignature(PsiMethod psiMethod) {
        StringBuilder stringBuilder = new StringBuilder(32);

        final MethodSignature signature = psiMethod.getSignature(new EmptySubstitutor());
        stringBuilder.append(signature.getName());

        final PsiType[] parameterTypes = signature.getParameterTypes();
        for (PsiType parameterType : parameterTypes) {
            stringBuilder.append(parameterType.getCanonicalText());
        }
        return stringBuilder.toString();
    }
}