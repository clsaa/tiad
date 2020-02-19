package com.clsaa.tiad.idea.util

import com.intellij.openapi.editor.Document
import com.intellij.openapi.util.TextRange

object DocumentUtils {
    private val PMD_TAB_SIZE = 8
    fun calculateRealOffset(document: Document, line: Int, pmdColumn: Int): Int {
        val maxLine = document.lineCount
        if (maxLine < line) {
            return -1
        }
        val lineOffset = document.getLineStartOffset(line - 1)
        return lineOffset + calculateRealColumn(document, line, pmdColumn)
    }

    fun calculateLineStart(document: Document, line: Int): Int {
        val maxLine = document.lineCount
        if (maxLine < line) {
            return -1
        }
        return document.getLineStartOffset(line - 1)
    }

    fun calculateRealColumn(document: Document, line: Int, pmdColumn: Int): Int {
        var realColumn = pmdColumn - 1
        val minusSize = PMD_TAB_SIZE - 1
        val docLine = line - 1
        val lineStartOffset = document.getLineStartOffset(docLine)
        val lineEndOffset = document.getLineEndOffset(docLine)
        val text = document.getText(TextRange(lineStartOffset, lineEndOffset))

        text.forEachIndexed { i, c ->
            if (c == '\t') {
                realColumn -= minusSize
            }
            if (i >= realColumn) {
                return@forEachIndexed
            }
        }
        return realColumn
    }
}