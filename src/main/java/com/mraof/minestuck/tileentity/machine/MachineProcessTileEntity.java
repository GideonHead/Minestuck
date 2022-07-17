package com.mraof.minestuck.tileentity.machine;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.BiPredicate;

public abstract class MachineProcessTileEntity extends BlockEntity
{
	protected final ItemStackHandler itemHandler = createItemHandler();
	private final LazyOptional<IItemHandler> itemOptional = LazyOptional.of(() -> itemHandler);
	protected final ContainerData parameters = new ProgressIntArray(this);
	public static final int DEFAULT_MAX_PROGRESS = 100;

	public int progress = 0;
	public int maxProgress = DEFAULT_MAX_PROGRESS;
	public boolean ready = false;
	public boolean overrideStop = false;
	
	public static final int FUEL_INCREASE = 32; //how many units of fuel a chunk of uranium adds to a machine powered by it, used by Sendificator and UraniumCooker
	
	protected MachineProcessTileEntity(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state)
	{
		super(tileEntityTypeIn, pos, state);
	}
	
	protected abstract ItemStackHandler createItemHandler();
	
	public abstract RunType getRunType();
	
	public boolean getOverrideStop()
	{
		return getRunType() == RunType.BUTTON_OVERRIDE && overrideStop;
	}
	
	@Override
	public void load(CompoundTag nbt)
	{
		super.load(nbt);

		this.progress = nbt.getInt("progress");
		if(getRunType() == RunType.BUTTON_OVERRIDE)
			this.overrideStop = nbt.getBoolean("overrideStop");
		if(nbt.contains("inventory", Tag.TAG_COMPOUND))
			itemHandler.deserializeNBT(nbt.getCompound("inventory"));
		else itemHandler.deserializeNBT(nbt);	//TODO reads save format from before the item handler. Remove when we don't care about backwards-compability to early mc1.15 versions
	}
	
	@Override
	protected void saveAdditional(CompoundTag compound)
	{
		super.saveAdditional(compound);

		compound.putInt("progress", this.progress);
		if(getRunType() == RunType.BUTTON_OVERRIDE)
			compound.putBoolean("overrideStop", this.overrideStop);
		compound.put("inventory", itemHandler.serializeNBT());
	}
	
	@Nonnull
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side)
	{
		if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return itemOptional.cast();
		return super.getCapability(cap, side);
	}
	
	public static void serverTick(Level level, BlockPos pos, BlockState state, MachineProcessTileEntity blockEntity)
	{
		if ((!blockEntity.ready && blockEntity.getRunType() != RunType.AUTOMATIC) || !blockEntity.contentsValid())
		{
			boolean b = blockEntity.progress == 0;
			blockEntity.progress = 0;
			blockEntity.ready = blockEntity.getOverrideStop();
			if (!b)
				level.sendBlockUpdated(pos, state, state, 3);
			return;
		}
		
		blockEntity.progress++;

		if (blockEntity.progress >= blockEntity.maxProgress)
		{
			blockEntity.progress = 0;
			blockEntity.ready = blockEntity.getOverrideStop();
			blockEntity.processContents();
		}
		
		blockEntity.tick();
	}
	
	protected void tick()
	{}

	public abstract boolean contentsValid();

	public abstract void processContents();
	
	public enum RunType
	{
		AUTOMATIC,
		BUTTON,
		BUTTON_OVERRIDE
	}
	
	protected class CustomHandler extends ItemStackHandler
	{
		private final BiPredicate<Integer, ItemStack> isValidPredicate;
		
		protected CustomHandler(int size)
		{
			this(size, (slot, stack) -> true);
		}
		
		protected CustomHandler(int size, BiPredicate<Integer, ItemStack> isValidPredicate)
		{
			super(size);
			this.isValidPredicate = isValidPredicate;
		}
		
		@Override
		public boolean isItemValid(int slot, @Nonnull ItemStack stack)
		{
			return isValidPredicate.test(slot, stack);
		}
		
		@Override
		protected void onContentsChanged(int slot)
		{
			MachineProcessTileEntity.this.setChanged();
		}
	}
	
	private static class ProgressIntArray implements ContainerData
	{
		private final MachineProcessTileEntity tileEntity;
		
		private ProgressIntArray(MachineProcessTileEntity tileEntity)
		{
			this.tileEntity = tileEntity;
		}
		
		@Override
		public int get(int index)
		{
			if(index == 0)
				return tileEntity.progress;
			else if(index == 1)
				return tileEntity.overrideStop ? 1 : 0;
			else if(index == 2)
				return tileEntity.ready ? 1 : 0;
			return 0;
		}
		
		@Override
		public void set(int index, int value)
		{
			if(index == 0)
				tileEntity.progress = value;
			else if(index == 1)
				tileEntity.overrideStop = value != 0;
			else if(index == 2)
				tileEntity.ready = value != 0;
		}
		
		@Override
		public int getCount()
		{
			return 3;
		}
	}
}