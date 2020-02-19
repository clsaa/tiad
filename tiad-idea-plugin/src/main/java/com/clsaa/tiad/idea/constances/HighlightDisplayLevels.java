package com.clsaa.tiad.idea.constances;

import com.intellij.codeHighlighting.HighlightDisplayLevel;

public interface HighlightDisplayLevels {
    HighlightDisplayLevel BLOCKER = new HighlightDisplayLevel(HighlightSeverities.BLOCKER, HighlightDisplayLevel.ERROR.getIcon());
    HighlightDisplayLevel CRITICAL = new HighlightDisplayLevel(HighlightSeverities.CRITICAL, HighlightDisplayLevel.WARNING.getIcon());
    HighlightDisplayLevel MAJOR = new HighlightDisplayLevel(HighlightSeverities.MAJOR, HighlightDisplayLevel.WEAK_WARNING.getIcon());
}
