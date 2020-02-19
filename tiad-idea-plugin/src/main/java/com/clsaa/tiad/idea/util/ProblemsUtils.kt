package com.clsaa.tiad.idea.util

import com.clsaa.tiad.idea.constances.ObjectConstants
import com.clsaa.tiad.idea.quickfix.TiadQuickFix
import com.intellij.codeInspection.InspectionManager
import com.intellij.codeInspection.LocalQuickFix
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.openapi.util.TextRange
import com.intellij.psi.*
import com.intellij.psi.impl.source.tree.ElementType


object ProblemsUtils {

    fun createProblemDescriptorForPmdRule(psiFile: PsiFile, manager: InspectionManager, isOnTheFly: Boolean,
                                          ruleName: String, desc: String, start: Int, end: Int,
                                          checkLine: Int = 0,
                                          quickFix: (PsiElement) -> LocalQuickFix? = {
                                              TiadQuickFix.getQuickFix(ruleName, isOnTheFly)
                                          }): ProblemDescriptor? {

        if (psiFile.virtualFile.canonicalPath!!.endsWith(".vm")) {
            return createTextRangeProblem(manager, TextRange(start, end), isOnTheFly, psiFile, ruleName, desc)
        }
        var psiElement = psiFile.findElementAt(start) ?: return null

        psiElement = transform(psiElement) ?: return null
        var endElement = if (start == end) psiElement else getEndElement(psiFile, psiElement, end)
        if (psiElement != endElement && endElement.parent is PsiField) {
            psiElement = endElement
        }
        if (endElement is PsiWhiteSpace) {
            endElement = psiElement
        }
        if (psiElement is PsiWhiteSpace) {
            val textRange = TextRange(start, end)
            return createTextRangeProblem(manager, textRange, isOnTheFly, psiFile, ruleName, desc)
        }

        if (psiElement.textRange.startOffset >= endElement.textRange.endOffset) {
            if (!(psiElement is PsiFile && endElement is PsiFile)) {
                return null
            }
            endElement = psiElement
        }
        return manager.createProblemDescriptor(psiElement, endElement,
                desc, ProblemHighlightType.GENERIC_ERROR_OR_WARNING, isOnTheFly,
                quickFix(psiElement))
    }

    private fun getEndElement(psiFile: PsiFile, psiElement: PsiElement, endOffset: Int): PsiElement {
        var endElement = psiFile.findElementAt(endOffset)
        if (endElement is PsiJavaToken && endElement.tokenType === ElementType.SEMICOLON) {
            endElement = psiFile.findElementAt(endOffset - 1)
        }
        if (endElement is PsiIdentifier) {
            return endElement
        }
        if (psiElement is PsiIdentifier) {
            return psiElement
        }
        if (endElement == null || endElement is PsiWhiteSpace
                || psiElement.textRange.startOffset >= endElement.textRange.endOffset) {
            endElement = psiElement
        }
        return endElement
    }

    private fun transform(element: PsiElement): PsiElement? {
        var psiElement: PsiElement? = element
        while (psiElement is PsiWhiteSpace) {
            psiElement = psiElement.getNextSibling()
        }
        if (psiElement == null) {
            return null
        }
        if (psiElement is PsiKeyword && psiElement.text != null && (ObjectConstants.CLASS_LITERAL == psiElement.text
                        || ObjectConstants.INTERFACE_LITERAL == psiElement.text
                        || ObjectConstants.ENUM_LITERAL == psiElement.text) && psiElement.parent is PsiClass) {
            val parent = psiElement.parent as PsiClass
            val identifier = parent.nameIdentifier
            return identifier ?: psiElement
        }
        return psiElement
    }

    private fun createTextRangeProblem(manager: InspectionManager, textRange: TextRange, isOnTheFly: Boolean,
                                       psiFile: PsiFile, ruleName: String, desc: String,
                                       quickFix: () -> LocalQuickFix? = {
                                           TiadQuickFix.getQuickFix(ruleName, isOnTheFly)
                                       }): ProblemDescriptor {

        return manager.createProblemDescriptor(psiFile, textRange,
                desc, ProblemHighlightType.GENERIC_ERROR_OR_WARNING,
                isOnTheFly, quickFix())
    }
}
