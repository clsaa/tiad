package com.clsaa.tiad.buidlingblock.entity.structure;

import com.clsaa.tiad.buidlingblock.entity.buildingblock.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author clsaa
 */
public class BuildingBlockStructure {

    /**
     * building block class name -> fileId ->OneFileAndTypeBuildingBlocks
     */
    private Map<String, Map<String, BuildingBlocks>> buildingBlockMap = new ConcurrentHashMap<>(64);

    public void put(List<BuildingBlock> buildingBlocks) {
        for (BuildingBlock buildingBlock : buildingBlocks) {
            this.put(buildingBlock);
        }
    }

    public void put(BuildingBlock buildingBlock) {
        String className = buildingBlock.getClass().getName();
        Map<String, BuildingBlocks> fileIdIndexMap = buildingBlockMap.get(className);
        if (fileIdIndexMap == null) {
            synchronized (className) {
                fileIdIndexMap = buildingBlockMap.get(className);
                if (fileIdIndexMap == null) {
                    fileIdIndexMap = new ConcurrentHashMap<>(1024);
                    buildingBlockMap.put(className, fileIdIndexMap);
                }
            }
        }
        String fileId = buildingBlock.getTiadSpecification().getFileId();
        BuildingBlocks buildingBlocks = fileIdIndexMap.get(fileId);
        if (buildingBlocks == null) {
            synchronized (fileId) {
                buildingBlocks = fileIdIndexMap.get(fileId);
                if (buildingBlocks == null) {
                    buildingBlocks = new BuildingBlocks();
                    fileIdIndexMap.put(fileId, buildingBlocks);
                }
            }
        }
        buildingBlocks.add(buildingBlock);
    }

    public void removeByFileId(String fileId) {
        for (Map<String, BuildingBlocks> idBuildingBlockMap : buildingBlockMap.values()) {
            idBuildingBlockMap.remove(fileId);
        }
    }

    public List<BuildingBlock> getByClass(Class buildingBlockClass) {
        List<BuildingBlock> result = new ArrayList<>(32);
        Map<String, BuildingBlocks> fileIdIndexMap = buildingBlockMap.get(buildingBlockClass.getName());
        if (fileIdIndexMap != null) {
            Collection<BuildingBlocks> values = fileIdIndexMap.values();
            for (BuildingBlocks value : values) {
                result.addAll(value.getBuildingBlocks());
            }
        }
        return result;
    }


    private static class BuildingBlocks {
        private ConcurrentMap<String, BuildingBlock> idIndexMap = new ConcurrentHashMap<>();

        public void add(BuildingBlock buildingBlock) {
            idIndexMap.put(buildingBlock.getId(), buildingBlock);
        }

        public Collection<BuildingBlock> getBuildingBlocks() {
            return idIndexMap.values();
        }
    }
}
