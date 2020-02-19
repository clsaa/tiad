package com.clsaa.tiad.idea.constances;

import com.intellij.lang.annotation.HighlightSeverity;

/**
 * @author clsaa
 */
public interface HighlightSeverities {
    HighlightSeverity MAJOR = new HighlightSeverity("MAJOR", 397);

    /**
     * The standard severity level for warning annotations.
     */
    HighlightSeverity CRITICAL = new HighlightSeverity("CRITICAL", 398);

    /**
     * The standard severity level for error annotations.
     */
    HighlightSeverity BLOCKER = new HighlightSeverity("BLOCKER", 399);
}
