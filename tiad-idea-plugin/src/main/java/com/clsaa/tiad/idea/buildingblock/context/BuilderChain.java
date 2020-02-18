package com.clsaa.tiad.idea.buildingblock.context;

import com.clsaa.tiad.idea.buildingblock.builder.Builder;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;

import java.lang.reflect.Modifier;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author clsaa
 */
@Slf4j
public class BuilderChain {
    private List<Builder> builders;

    private BuilderChain() {
        this.builders = new CopyOnWriteArrayList<>();
        Reflections reflections = new Reflections(Builder.class.getPackage().getName());
        Set<Class<? extends Builder>> subTypesOf = reflections.getSubTypesOf(Builder.class);
        for (Class<? extends Builder> aClass : subTypesOf) {
            Builder builder;
            try {
                if (Modifier.isAbstract(aClass.getModifiers()) || Modifier.isInterface(aClass.getModifiers())) {
                    continue;
                }
                builder = aClass.newInstance();
            } catch (Exception e) {
                log.error("BuilderChain failed, ", e);
                throw new RuntimeException(e);
            }
            this.registry(builder);
        }
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

    public void startBuild(BuilderContext context, BuilderChain builderChain) {
        this.doBuild(context, builderChain);
    }
}
