package com.clsaa.tiad.idea.constances;

import com.intellij.ui.IconManager;

import javax.swing.*;

/**
 * @author Konstantin Bulenkov
 */
public interface Icons {
    static Icon load(String path) {
        return IconManager.getInstance().getIcon(path, Icons.class);
    }

    Icon AGGREGATE = load("/icon/buildingblocks/aggregate.png");
}
