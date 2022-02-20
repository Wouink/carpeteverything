package io.github.wouink.carpev.event;

import io.github.wouink.carpev.CarpetEverything;
import io.github.wouink.carpev.block.CarpetOnTrapdoor;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WoolCarpetBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.StairsShape;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PlaceCarpet {

	@SubscribeEvent
	public void onCarpetPlaced(BlockEvent.EntityPlaceEvent event) {
		if(event.getWorld().isClientSide()) return;
		if(event.getPlacedBlock().getBlock() instanceof WoolCarpetBlock) {
			BlockState stateBelow = event.getWorld().getBlockState(event.getPos().below());
			if(stateBelow.getBlock() instanceof StairBlock && !event.getEntity().isCrouching()) {
				if(stateBelow.getValue(StairBlock.HALF) == Half.BOTTOM && stateBelow.getValue(StairBlock.SHAPE) == StairsShape.STRAIGHT) {
					String color = ((WoolCarpetBlock) event.getPlacedBlock().getBlock()).getColor().getName();
					event.getWorld().setBlock(
							event.getPos(),
							CarpetEverything.Carpets_On_Stairs.get(color).defaultBlockState().setValue(
									HorizontalDirectionalBlock.FACING, stateBelow.getValue(HorizontalDirectionalBlock.FACING)),
							3
					);
				}
			} else if(stateBelow.getBlock() instanceof TrapDoorBlock) {
				if(stateBelow.getValue(TrapDoorBlock.HALF) == Half.TOP) {
					String color = ((WoolCarpetBlock) event.getPlacedBlock().getBlock()).getColor().getName();
					event.getWorld().setBlock(
							event.getPos(),
							CarpetEverything.Carpets_On_Trapdoors.get(color).defaultBlockState().setValue(
									HorizontalDirectionalBlock.FACING, stateBelow.getValue(HorizontalDirectionalBlock.FACING))
									.setValue(CarpetOnTrapdoor.OPEN, stateBelow.getValue(TrapDoorBlock.OPEN)),
							3
					);
				}
			}
		}
	}
}
