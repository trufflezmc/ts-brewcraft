package com.trufflez.tsbrewcraft.client;

import com.trufflez.tsbrewcraft.block.TsBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

@Environment(EnvType.CLIENT)
public class TsBrewcraftClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Block Render Layers
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), TsBlocks.SULFUR_STICK);
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), TsBlocks.WALL_SULFUR_STICK);
    }
}
