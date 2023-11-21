package com.mraof.minestuck.block.plant;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class CustomPlacementBushBlock extends BushBlock
{
	private final TagKey<Block> placementTag;
	
	public CustomPlacementBushBlock(TagKey<Block> pPlacementTag, Properties pProperties)
	{
		super(pProperties);
		this.placementTag = pPlacementTag;
	}
	
	@Override
	protected boolean mayPlaceOn(BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos)
	{
		return pState.is(placementTag);
	}
}
