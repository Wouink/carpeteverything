package io.github.wouink.carpev.event;

import io.github.wouink.carpev.CarpetEverything;
import io.github.wouink.carpev.block.CarpetOnTrapdoor;
import net.minecraft.block.*;
import net.minecraft.state.properties.Half;
import net.minecraft.state.properties.StairsShape;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PlaceCarpet {

	@SubscribeEvent
	public void onCarpetPlaced(BlockEvent.EntityPlaceEvent event) {
		if(event.getWorld().isClientSide()) return;
		if(event.getPlacedBlock().getBlock() instanceof CarpetBlock) {
			BlockState stateBelow = event.getWorld().getBlockState(event.getPos().below());
			if(stateBelow.getBlock() instanceof StairsBlock && !event.getEntity().isCrouching()) {
				if(stateBelow.getValue(StairsBlock.HALF) == Half.BOTTOM && stateBelow.getValue(StairsBlock.SHAPE) == StairsShape.STRAIGHT) {
					String color = ((CarpetBlock) event.getPlacedBlock().getBlock()).getColor().getName();
					event.getWorld().setBlock(
							event.getPos(),
							CarpetEverything.Carpets_On_Stairs.get(color).defaultBlockState().setValue(
									HorizontalBlock.FACING, stateBelow.getValue(HorizontalBlock.FACING)),
							3
					);
				}
			} else if(stateBelow.getBlock() instanceof TrapDoorBlock) {
				if(stateBelow.getValue(TrapDoorBlock.HALF) == Half.TOP) {
					String color = ((CarpetBlock) event.getPlacedBlock().getBlock()).getColor().getName();
					event.getWorld().setBlock(
							event.getPos(),
							CarpetEverything.Carpets_On_Trapdoors.get(color).defaultBlockState().setValue(
									HorizontalBlock.FACING, stateBelow.getValue(HorizontalBlock.FACING))
									.setValue(CarpetOnTrapdoor.OPEN, stateBelow.getValue(TrapDoorBlock.OPEN)),
							3
					);
				}
			}
		}
	}
}
