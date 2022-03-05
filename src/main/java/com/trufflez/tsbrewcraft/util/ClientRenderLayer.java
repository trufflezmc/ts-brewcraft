package com.trufflez.tsbrewcraft.util;

import com.trufflez.tsbrewcraft.TsBrewcraft;
import com.trufflez.tsbrewcraft.block.TsBlocks;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class ClientRenderLayer {
    static {
        BlockRenderLayerMap.INSTANCE.putBlock(TsBlocks.AGAVE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TsBlocks.BARLEY, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TsBlocks.CORN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TsBlocks.GRAPE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TsBlocks.RICE, RenderLayer.getCutout());
    }
    
    public static void init() {
        TsBrewcraft.LOGGER.debug("Setting render layers");
    }
}
