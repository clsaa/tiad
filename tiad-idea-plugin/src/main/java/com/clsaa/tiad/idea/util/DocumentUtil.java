package com.clsaa.tiad.idea.util;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.util.TextRange;

public interface DocumentUtil {
    int PMD_TAB_SIZE = 8;

    static int calculateRealOffset(Document document, int line, int pmdColumn) {
        int maxLine = document.getLineCount();
        if (maxLine < line) {
            return -1;
        }
        int lineOffset = document.getLineStartOffset(line - 1);
        return lineOffset + calculateRealColumn(document, line, pmdColumn);
    }

    static int calculateRealColumn(Document document, int line, int pmdColumn) {
        int realColumn = pmdColumn - 1;
        int minusSize = PMD_TAB_SIZE - 1;
        int docLine = line - 1;
        int lineStartOffset = document.getLineStartOffset(docLine);
        int lineEndOffset = document.getLineEndOffset(docLine);
        String text = document.getText(new TextRange(lineStartOffset, lineEndOffset));

        CharSequence charSequence = text;
        for (int i = 0; i < charSequence.length(); i++) {
            char c = charSequence.charAt(i);
            if (c == '\t') {
                realColumn -= minusSize;
            }
            if (i >= realColumn) {

            }
        }
        return realColumn;
    }
}
