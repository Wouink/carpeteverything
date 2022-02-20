package io.github.wouink.carpev;

import io.github.wouink.carpev.block.CarpetOnStairs;
import io.github.wouink.carpev.block.CarpetOnTrapdoor;
import io.github.wouink.carpev.event.PlaceCarpet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.HashMap;

@Mod(CarpetEverything.MODID)
@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class CarpetEverything {
	public static final String MODID = "carpeteverything";

	public static HashMap<String, Block> Carpets_On_Stairs = new HashMap<>(16);
	public static HashMap<String, Block> Carpets_On_Trapdoors = new HashMap<>(16);

	public CarpetEverything() {
		for(DyeColor dyeColor : DyeColor.values()) {
			String color = dyeColor.getName();
			Block coloredCarpet = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(String.format("minecraft:%s_carpet", color)));
			Carpets_On_Stairs.put(color, new CarpetOnStairs(BlockBehaviour.Properties.copy(coloredCarpet).dropsLike(coloredCarpet), String.format("%s_carpet_on_stairs", color), coloredCarpet));
			Carpets_On_Trapdoors.put(color, new CarpetOnTrapdoor(BlockBehaviour.Properties.copy(coloredCarpet).dropsLike(coloredCarpet), String.format("%s_carpet_on_trapdoor", color), coloredCarpet));
		}
		if(ModList.get().getModFileById("furnish") == null) {
			MinecraftForge.EVENT_BUS.register(new PlaceCarpet());
		} else System.err.println("!!! Furnish and Carpet Everything are installed at the same time. Disabling Carpet Everything placement event.");
	}

	@SubscribeEvent
	public static void registerBlocks(final RegistryEvent.Register<Block> event) {
		IForgeRegistry<Block> blockRegistry = event.getRegistry();
		for(Block b : CarpetEverything.Carpets_On_Stairs.values()) blockRegistry.register(b);
		for(Block b : CarpetEverything.Carpets_On_Trapdoors.values()) blockRegistry.register(b);
	}
}
