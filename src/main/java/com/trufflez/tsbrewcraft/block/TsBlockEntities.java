package com.trufflez.tsbrewcraft.block;

import com.trufflez.tsbrewcraft.TsBrewcraft;
import com.trufflez.tsbrewcraft.block.custom.CaskBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TsBlockEntities {
    public static final BlockEntityType<CaskBlockEntity> CASK_BLOCKENTITY;
    
    private static <T extends BlockEntity> BlockEntityType<T> register(String id, BlockEntityType<T> blockEntityType) {
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(TsBrewcraft.MOD_ID, id), blockEntityType);
    }
    
    static {
        CASK_BLOCKENTITY = register("cask", FabricBlockEntityTypeBuilder.create(CaskBlockEntity::new, TsBlocks.CASK).build(null));
    }
    
    public static void init() {
        TsBrewcraft.LOGGER.debug("Registering BlockEntities");
    }
}
