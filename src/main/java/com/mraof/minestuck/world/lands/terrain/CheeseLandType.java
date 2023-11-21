package com.mraof.minestuck.world.lands.terrain;

import com.mraof.minestuck.block.MSBlocks;
import com.mraof.minestuck.entity.MSEntityTypes;
import com.mraof.minestuck.util.MSSoundEvents;
import com.mraof.minestuck.util.MSTags;
import com.mraof.minestuck.world.biome.LandBiomeType;
import com.mraof.minestuck.world.gen.feature.MSPlacedFeatures;
import com.mraof.minestuck.world.gen.structure.blocks.StructureBlockRegistry;
import com.mraof.minestuck.world.gen.structure.village.SalamanderVillagePieces;
import com.mraof.minestuck.world.lands.LandBiomeGenBuilder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

public class CheeseLandType extends TerrainLandType
{
	public static final String CHEESE = "minestuck.cheese";
	
	public CheeseLandType()
	{
		super(new Builder(MSEntityTypes.SALAMANDER).names(CHEESE)
				.skyColor(255, 245, 222)
				.fogColor(255, 245, 222)
				.skylight(10f)
				.music(MSSoundEvents.MUSIC_SHADE));
	}
	
	@Override
	public void registerBlocks(StructureBlockRegistry registry)
	{
		registry.setBlock("ground", MSBlocks.CHHURPI);
		registry.setBlock("upper", MSBlocks.SMOOTH_SWISS_CHEESE);
		registry.setBlock("surface_rough", MSBlocks.RIND);
		registry.setBlock("ocean", Blocks.WATER);
		registry.setBlock("structure_primary", MSBlocks.CHHURPI_BRICKS);
		registry.setBlock("structure_primary_decorative", MSBlocks.CHISELED_CHHURPI_BRICKS);
		registry.setBlock("structure_primary_cracked", MSBlocks.CRACKED_CHHURPI_BRICKS);
		registry.setBlock("structure_primary_mossy", MSBlocks.CHEESY_CHHURPI_BRICKS);
		registry.setBlock("structure_primary_column", MSBlocks.CHHURPI_PILLAR);
		registry.setBlock("structure_primary_stairs", MSBlocks.CHHURPI_BRICK_STAIRS);
		registry.setBlock("structure_secondary", MSBlocks.RIND_BRICKS);
		registry.setBlock("structure_secondary_decorative", MSBlocks.CHISELED_RIND_BRICKS);
		registry.setBlock("structure_secondary_stairs", MSBlocks.RIND_BRICK_STAIRS);
		registry.setBlock("village_path", MSBlocks.CHEESE_PATH);
		registry.setBlock("light_block", Blocks.SHROOMLIGHT);
		registry.setBlock("torch", Blocks.REDSTONE_TORCH);
		registry.setBlock("wall_torch", Blocks.REDSTONE_WALL_TORCH);
		registry.setBlock("mushroom_1", MSBlocks.GLOWING_MUSHROOM);
		registry.setBlock("mushroom_2", MSBlocks.GLOWING_MUSHROOM);
		registry.setBlock("bush", MSBlocks.GLOWING_MUSHROOM);
		registry.setBlock("structure_wool_1", Blocks.YELLOW_WOOL);
		registry.setBlock("structure_wool_3", Blocks.ORANGE_WOOL);
		registry.setBlock("cruxite_ore", MSBlocks.CHHURPI_CRUXITE_ORE);
		registry.setBlock("uranium_ore", MSBlocks.CHHURPI_URANIUM_ORE);
	}
	
	@Override
	public void addBiomeGeneration(LandBiomeGenBuilder builder, StructureBlockRegistry blocks)
	{
		RuleTest surfaceBlocks = new TagMatchTest(MSTags.Blocks.CHEESE_LANDS_SURFACE);
		
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, MSPlacedFeatures.inline(Feature.ORE,
						new OreConfiguration(surfaceBlocks, MSBlocks.SWISS_CHEESE.get().defaultBlockState(), 45),
						CountPlacement.of(30), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(256)), BiomeFilter.biome()),
				LandBiomeType.anyExcept(LandBiomeType.OCEAN));
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, MSPlacedFeatures.inline(Feature.ORE,
						new OreConfiguration(surfaceBlocks, MSBlocks.SMOOTH_SWISS_CHEESE.get().defaultBlockState(), 45),
						CountPlacement.of(30), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(256)), BiomeFilter.biome()),
				LandBiomeType.ROUGH);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, MSPlacedFeatures.inline(Feature.ORE,
						new OreConfiguration(surfaceBlocks, MSBlocks.AMERICAN_CHEESE.get().defaultBlockState(), 45),
						CountPlacement.of(30), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(256)), BiomeFilter.biome()),
				LandBiomeType.anyExcept(LandBiomeType.OCEAN));
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, MSPlacedFeatures.inline(Feature.ORE,
						new OreConfiguration(surfaceBlocks, MSBlocks.SMOOTH_AMERICAN_CHEESE.get().defaultBlockState(), 45),
						CountPlacement.of(30), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(256)), BiomeFilter.biome()),
				LandBiomeType.anyExcept(LandBiomeType.OCEAN));
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, MSPlacedFeatures.inline(Feature.ORE,
						new OreConfiguration(surfaceBlocks, MSBlocks.FETA_CHEESE.get().defaultBlockState(), 45),
						CountPlacement.of(90), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(256)), BiomeFilter.biome()),
				LandBiomeType.OCEAN);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, MSPlacedFeatures.inline(Feature.ORE,
						new OreConfiguration(blocks.getGroundType(), MSBlocks.CHHURPI_CRUXITE_ORE.get().defaultBlockState(), 4),
						CountPlacement.of(30), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(64)), BiomeFilter.biome()),
				LandBiomeType.any());
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, MSPlacedFeatures.inline(Feature.ORE,
						new OreConfiguration(blocks.getGroundType(), MSBlocks.CHHURPI_URANIUM_ORE.get().defaultBlockState(), 2),
						CountPlacement.of(12), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(32)), BiomeFilter.biome()),
				LandBiomeType.any());
	}
	
	@Override
	public void addVillageCenters(CenterRegister register)
	{
		SalamanderVillagePieces.addCenters(register);
	}
	
	@Override
	public void addVillagePieces(PieceRegister register, RandomSource random)
	{
		SalamanderVillagePieces.addPieces(register, random);
	}
}
