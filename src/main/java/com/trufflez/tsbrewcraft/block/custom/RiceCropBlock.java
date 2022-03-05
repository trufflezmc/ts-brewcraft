package com.trufflez.tsbrewcraft.block.custom;

import com.trufflez.tsbrewcraft.item.TsItems;
import net.minecraft.block.CropBlock;
import net.minecraft.item.ItemConvertible;

public class RiceCropBlock extends CropBlock {
    public RiceCropBlock(Settings settings) { super(settings); }
    
    @Override
    public ItemConvertible getSeedsItem() {
        return TsItems.RICE;
    }
}
