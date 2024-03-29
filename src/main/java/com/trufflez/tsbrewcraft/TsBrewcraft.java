package com.trufflez.tsbrewcraft;

import com.trufflez.tsbrewcraft.block.TsBlockEntities;
import com.trufflez.tsbrewcraft.block.TsBlocks;
import com.trufflez.tsbrewcraft.item.TsItemGroups;
import com.trufflez.tsbrewcraft.item.TsItems;
import com.trufflez.tsbrewcraft.statuseffect.TsStatusEffects;
import com.trufflez.tsbrewcraft.util.ClientRenderLayer;
import com.trufflez.tsbrewcraft.util.PropertyRegistry;
import net.fabricmc.api.ModInitializer;
import net.minecraft.screen.Property;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TsBrewcraft implements ModInitializer {
	public static final String MOD_ID = "tsbrewcraft";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	
	@Override
	public void onInitialize() {

		TsItemGroups.init();
		
		TsItems.init();
		TsBlocks.init();

		TsBlockEntities.init();

		PropertyRegistry.init();

		ClientRenderLayer.init();

		TsStatusEffects.init();
		
		LOGGER.info("T's Brewcraft is installed.");
	}
}
