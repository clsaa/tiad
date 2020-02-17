package com.clsaa.tiad.idea.buildingblock.builder;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author clsaa
 */
public class BuilderChain {
    private List<Builder> builders;

    private BuilderChain() {
        this.builders = new CopyOnWriteArrayList<>();
    }

    public void registry(Builder builder) {
        builders.add(builder);
        builders.sort(Comparator.comparing(Builder::getOrder));
    }

    public Builder getBuilder(int index) {
        if (index >= builders.size()) {
            return null;
        }
        return builders.get(index);
    }

    public void doBuild(BuilderContext context, BuilderChain builderChain) {
        Builder builder = context.nextBuilder();
        if (builder != null) {
            builder.build(context, builderChain);
        }
    }

    public static BuilderChain getInstance() {
        return SingleHolder.builderChain;
    }

    private static class SingleHolder {
        private static BuilderChain builderChain = new BuilderChain();
    }
}
