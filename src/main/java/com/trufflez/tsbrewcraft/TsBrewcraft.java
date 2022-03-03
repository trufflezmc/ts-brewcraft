package com.trufflez.tsbrewcraft;

import com.trufflez.tsbrewcraft.block.TsBlocks;
import com.trufflez.tsbrewcraft.item.TsItems;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TsBrewcraft implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("modid");
	public static final String MOD_ID = "tsbrewcraft";
	
	@Override
	public void onInitialize() {
		
		TsItems.init();
		TsBlocks.init();
		
		LOGGER.info("T's Brewcraft is installed.");
	}
}
