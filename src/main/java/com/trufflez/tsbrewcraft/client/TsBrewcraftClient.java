package com.trufflez.tsbrewcraft.client;

import com.trufflez.tsbrewcraft.block.TsBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;

@Environment(EnvType.CLIENT)
public class TsBrewcraftClient implements ClientModInitializer {
    
    public void setCutout(Block block){
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), block);
    }
    
    public void setBlockRenderLayers(){
        setCutout(TsBlocks.SULFUR_STICK);
        setCutout(TsBlocks.WALL_SULFUR_STICK);
        setCutout(TsBlocks.TRELLIS);
    }
    
    @Override
    public void onInitializeClient() {
        // Block Render Layers
        setBlockRenderLayers();
    }
}
