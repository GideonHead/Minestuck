package com.mraof.minestuck.world.lands.terrain;

import com.mraof.minestuck.block.MinestuckBlocks;
import com.mraof.minestuck.entity.ModEntityTypes;
import com.mraof.minestuck.entity.consort.ConsortEntity;
import com.mraof.minestuck.world.biome.LandBiomeHolder;
import com.mraof.minestuck.world.biome.ModBiomes;
import com.mraof.minestuck.world.lands.LandAspectRegistry;
import com.mraof.minestuck.world.lands.decorator.BlockBlobDecorator;
import com.mraof.minestuck.world.lands.decorator.ILandDecorator;
import com.mraof.minestuck.world.lands.decorator.LeaflessTreeDecorator;
import com.mraof.minestuck.world.lands.decorator.SurfaceDecoratorVein;
import com.mraof.minestuck.world.lands.decorator.SurfaceMushroomGenerator;
import com.mraof.minestuck.world.lands.decorator.UndergroundDecoratorVein;
import com.mraof.minestuck.world.lands.gen.LandGenSettings;
import com.mraof.minestuck.world.lands.structure.blocks.StructureBlockRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.biome.Biome;

import java.util.ArrayList;
import java.util.List;

public class LandAspectRock extends TerrainLandAspect
{
	private final Variant type;
	private final List<TerrainLandAspect> variations;

	public LandAspectRock()
	{
		this(Variant.ROCK);
	}
	
	public LandAspectRock(Variant variation)
	{
		variations = new ArrayList<>();
		type = variation;
		
		if(type == Variant.ROCK)
		{
			variations.add(this);
			variations.add(new LandAspectRock(Variant.PETRIFICATION));
		}
	}

	@Override
	public void registerBlocks(StructureBlockRegistry registry)
	{
		if(type == Variant.PETRIFICATION) {
			registry.setBlockState("surface", Blocks.STONE.getDefaultState());
		} else {
			registry.setBlockState("surface", Blocks.GRAVEL.getDefaultState());
		}
		registry.setBlockState("upper", Blocks.COBBLESTONE.getDefaultState());
		registry.setBlockState("structure_primary_decorative", Blocks.CHISELED_STONE_BRICKS.getDefaultState());
		registry.setBlockState("structure_primary_stairs", Blocks.STONE_BRICK_STAIRS.getDefaultState());
		registry.setBlockState("structure_secondary", MinestuckBlocks.COARSE_STONE.getDefaultState());
		registry.setBlockState("structure_secondary_decorative", MinestuckBlocks.CHISELED_COARSE_STONE.getDefaultState());
		registry.setBlockState("structure_secondary_stairs", MinestuckBlocks.COARSE_STONE_STAIRS.getDefaultState());
		registry.setBlockState("structure_planks_slab", Blocks.BRICK_SLAB.getDefaultState());
		registry.setBlockState("village_path", Blocks.MOSSY_COBBLESTONE.getDefaultState());
		registry.setBlockState("village_fence", Blocks.COBBLESTONE_WALL.getDefaultState());
		registry.setBlockState("structure_wool_1", Blocks.BROWN_WOOL.getDefaultState());
		registry.setBlockState("structure_wool_3", Blocks.GRAY_WOOL.getDefaultState());
	}
	
	@Override
	public String getPrimaryName()
	{
		return type.getName();
	}
	
	@Override
	public String[] getNames()
	{
		if(type == Variant.PETRIFICATION) {
			return new String[] {"petrification"};
		} else {
			return new String[] {"rock", "stone", "ore"};
		}
	}
	
	@Override
	public void setBiomeSettings(LandBiomeHolder settings)
	{
		settings.category = Biome.Category.EXTREME_HILLS;
		settings.downfall = 0.2F;
		settings.temperature = 0.3F;
	}
	
	@Override
	public void setGenSettings(LandGenSettings settings)
	{
		settings.oceanChance = 1/4F;
	}
	
	@Override
	public List<ILandDecorator> getDecorators()
	{
		List<ILandDecorator> list = new ArrayList<ILandDecorator>();
		list.add(new UndergroundDecoratorVein(Blocks.COAL_ORE.getDefaultState(), 20, 17, 128));
		list.add(new UndergroundDecoratorVein(Blocks.IRON_ORE.getDefaultState(), 20, 9, 64));
		list.add(new UndergroundDecoratorVein(Blocks.REDSTONE_ORE.getDefaultState(), 10, 8, 32));
		list.add(new UndergroundDecoratorVein(Blocks.LAPIS_ORE.getDefaultState(), 4, 7, 24));
		list.add(new UndergroundDecoratorVein(Blocks.GOLD_ORE.getDefaultState(), 4, 9, 32));
		list.add(new UndergroundDecoratorVein(Blocks.DIAMOND_ORE.getDefaultState(), 2, 6, 24));
		list.add(new UndergroundDecoratorVein(Blocks.GRAVEL.getDefaultState(), 10, 33, 256));
		list.add(new UndergroundDecoratorVein(Blocks.INFESTED_STONE.getDefaultState(), 7, 9, 64));
		list.add(new SurfaceDecoratorVein(Blocks.CLAY.getDefaultState(), 25, 20, ModBiomes.mediumOcean));
		
		list.add(new BlockBlobDecorator(Blocks.COBBLESTONE.getDefaultState(), 0, 3, ModBiomes.mediumNormal));
		list.add(new BlockBlobDecorator(Blocks.COBBLESTONE.getDefaultState(), 1, 4, ModBiomes.mediumRough));
		if(type == Variant.ROCK) {
			list.add(new SurfaceMushroomGenerator(MinestuckBlocks.PETRIFIED_GRASS, true, 25, 32, ModBiomes.mediumRough));
			list.add(new SurfaceMushroomGenerator(MinestuckBlocks.PETRIFIED_GRASS, true, 10, 48, ModBiomes.mediumNormal));
			list.add(new LeaflessTreeDecorator(MinestuckBlocks.PETRIFIED_LOG.getDefaultState(), 0.05F, ModBiomes.mediumRough));
			list.add(new BlockBlobDecorator(Blocks.COBBLESTONE.getDefaultState(), 0, 3, ModBiomes.mediumNormal));
			list.add(new BlockBlobDecorator(Blocks.COBBLESTONE.getDefaultState(), 1, 4, ModBiomes.mediumRough));
		} else {
			list.add(new SurfaceMushroomGenerator(MinestuckBlocks.PETRIFIED_POPPY, true, 10, 25, ModBiomes.mediumNormal));
			list.add(new SurfaceMushroomGenerator(MinestuckBlocks.PETRIFIED_POPPY, true, 5, 25, ModBiomes.mediumRough));
			list.add(new SurfaceMushroomGenerator(MinestuckBlocks.PETRIFIED_GRASS, true, 35, 35, ModBiomes.mediumNormal));
			list.add(new SurfaceMushroomGenerator(MinestuckBlocks.PETRIFIED_GRASS, true, 55, 55, ModBiomes.mediumRough));
			list.add(new LeaflessTreeDecorator(MinestuckBlocks.PETRIFIED_LOG.getDefaultState(), 0.1F, ModBiomes.mediumNormal));
			list.add(new LeaflessTreeDecorator(MinestuckBlocks.PETRIFIED_LOG.getDefaultState(), 2.5F, ModBiomes.mediumRough));
		}
		return list;
	}
	
	@Override
	public float getSkylightBase()
	{
		return 7/8F;
	}
	
	@Override
	public Vec3d getFogColor()
	{
		return new Vec3d(0.5, 0.5, 0.55);
	}
	
	@Override
	public Vec3d getSkyColor()
	{
		return new Vec3d(0.6D, 0.6D, 0.7D);
	}
	
	@Override
	public List<TerrainLandAspect> getVariations()
	{
		return variations;
	}
	
	@Override
	public TerrainLandAspect getPrimaryVariant()
	{
		return LandAspectRegistry.fromNameTerrain("rock");
	}
	
	@Override
	public EntityType<? extends ConsortEntity> getConsortType()
	{
		return ModEntityTypes.NAKAGATOR;
	}
	
	public static enum Variant
	{
		ROCK,
		PETRIFICATION;
		public String getName()
		{
			return this.toString().toLowerCase();
		}
	}
}