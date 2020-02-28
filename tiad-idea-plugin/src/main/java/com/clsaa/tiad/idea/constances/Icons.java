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

    Icon BUILDING_BLOCKS = load("/icon/buildingblocks/buildingblocks.svg");
    Icon AGGREGATE = load("/icon/buildingblocks/aggregate.svg");
    Icon ENTITY = load("/icon/buildingblocks/entity.svg");
    Icon AGGREGATE_ROOT = load("/icon/buildingblocks/aggregateroot.svg");
    Icon CONTEXT_MAPPING = load("/icon/buildingblocks/contextmapping.svg");
    Icon SUBDOMAIN = load("/icon/buildingblocks/subdomain.svg");
    Icon REPOSITORY = load("/icon/buildingblocks/repository.svg");
    Icon BOUNDED_CONTEXT = load("/icon/buildingblocks/boundedcontext.svg");
    Icon APPLICATION_SERVICE = load("/icon/buildingblocks/applicationservice.svg");
    Icon DOMAIN_SERVICE = load("/icon/buildingblocks/domainservice.svg");
    Icon DOMAIN_EVENT = load("/icon/buildingblocks/domainevent.svg");
    Icon FACTORY = load("/icon/buildingblocks/factory.svg");
    Icon SPECIFICATION = load("/icon/buildingblocks/specification.svg");
    Icon VALUE_OBJECT = load("/icon/buildingblocks/valueobject.svg");
}
