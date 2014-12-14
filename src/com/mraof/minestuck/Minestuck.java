package com.mraof.minestuck;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.Random;

import org.lwjgl.opengl.GLContext;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.common.network.FMLEmbeddedChannel;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import codechicken.nei.NEIModContainer;

import com.mraof.minestuck.block.BlockChessTile;
import com.mraof.minestuck.block.BlockColoredDirt;
import com.mraof.minestuck.block.BlockComputerOff;
import com.mraof.minestuck.block.BlockComputerOn;
import com.mraof.minestuck.block.BlockFluidBlood;
import com.mraof.minestuck.block.BlockFluidBrainJuice;
import com.mraof.minestuck.block.BlockFluidOil;
import com.mraof.minestuck.block.BlockGatePortal;
import com.mraof.minestuck.block.BlockGoldSeeds;
import com.mraof.minestuck.block.BlockLayered;
import com.mraof.minestuck.block.BlockMachine;
import com.mraof.minestuck.block.BlockStorage;
import com.mraof.minestuck.block.BlockTransportalizer;
import com.mraof.minestuck.block.OreCruxite;
import com.mraof.minestuck.client.ClientProxy;
import com.mraof.minestuck.client.gui.GuiHandler;
import com.mraof.minestuck.client.util.MinestuckTextureManager;
import com.mraof.minestuck.editmode.ClientEditHandler;
import com.mraof.minestuck.editmode.DeployList;
import com.mraof.minestuck.editmode.ServerEditHandler;
import com.mraof.minestuck.entity.EntityDecoy;
import com.mraof.minestuck.entity.carapacian.EntityBlackBishop;
import com.mraof.minestuck.entity.carapacian.EntityBlackPawn;
import com.mraof.minestuck.entity.carapacian.EntityBlackRook;
import com.mraof.minestuck.entity.carapacian.EntityWhiteBishop;
import com.mraof.minestuck.entity.carapacian.EntityWhitePawn;
import com.mraof.minestuck.entity.carapacian.EntityWhiteRook;
import com.mraof.minestuck.entity.consort.EntityIguana;
import com.mraof.minestuck.entity.consort.EntityNakagator;
import com.mraof.minestuck.entity.consort.EntitySalamander;
import com.mraof.minestuck.entity.item.EntityGrist;
import com.mraof.minestuck.entity.underling.EntityBasilisk;
import com.mraof.minestuck.entity.underling.EntityGiclops;
import com.mraof.minestuck.entity.underling.EntityImp;
import com.mraof.minestuck.entity.underling.EntityOgre;
import com.mraof.minestuck.event.MinestuckFluidHandler;
import com.mraof.minestuck.inventory.captchalouge.CaptchaDeckHandler;
import com.mraof.minestuck.item.ItemCaptchaCard;
import com.mraof.minestuck.item.ItemComponent;
import com.mraof.minestuck.item.ItemCruxiteArtifact;
import com.mraof.minestuck.item.ItemCruxiteRaw;
import com.mraof.minestuck.item.ItemDisk;
import com.mraof.minestuck.item.ItemDowel;
import com.mraof.minestuck.item.ItemGoldSeeds;
import com.mraof.minestuck.item.ItemMinestuckBucket;
import com.mraof.minestuck.item.ItemModus;
import com.mraof.minestuck.item.block.ItemBlockLayered;
import com.mraof.minestuck.item.block.ItemChessTile;
import com.mraof.minestuck.item.block.ItemColoredDirt;
import com.mraof.minestuck.item.block.ItemMachine;
import com.mraof.minestuck.item.block.ItemOreCruxite;
import com.mraof.minestuck.item.block.ItemStorageBlock;
import com.mraof.minestuck.item.weapon.EnumBladeType;
import com.mraof.minestuck.item.weapon.EnumCaneType;
import com.mraof.minestuck.item.weapon.EnumClubType;
import com.mraof.minestuck.item.weapon.EnumHammerType;
import com.mraof.minestuck.item.weapon.EnumSickleType;
import com.mraof.minestuck.item.weapon.EnumSporkType;
import com.mraof.minestuck.item.weapon.ItemBlade;
import com.mraof.minestuck.item.weapon.ItemCane;
import com.mraof.minestuck.item.weapon.ItemClub;
import com.mraof.minestuck.item.weapon.ItemHammer;
import com.mraof.minestuck.item.weapon.ItemSickle;
import com.mraof.minestuck.item.weapon.ItemSpork;
import com.mraof.minestuck.nei.NEIMinestuckConfig;
import com.mraof.minestuck.network.MinestuckChannelHandler;
import com.mraof.minestuck.network.skaianet.SessionHandler;
import com.mraof.minestuck.network.skaianet.SkaianetHandler;
import com.mraof.minestuck.tileentity.TileEntityComputer;
import com.mraof.minestuck.tileentity.TileEntityGatePortal;
import com.mraof.minestuck.tileentity.TileEntityMachine;
import com.mraof.minestuck.tileentity.TileEntityTransportalizer;
import com.mraof.minestuck.tracker.MinestuckPlayerTracker;
import com.mraof.minestuck.util.ComputerProgram;
import com.mraof.minestuck.util.MinestuckAchievementHandler;
import com.mraof.minestuck.util.AlchemyRecipeHandler;
import com.mraof.minestuck.util.ComputerProgram;
import com.mraof.minestuck.util.Debug;
import com.mraof.minestuck.util.MinestuckPlayerData;
import com.mraof.minestuck.util.KindAbstratusList;
import com.mraof.minestuck.util.SburbClient;
import com.mraof.minestuck.util.SburbServer;
import com.mraof.minestuck.util.UpdateChecker;
import com.mraof.minestuck.world.WorldProviderLands;
import com.mraof.minestuck.world.WorldProviderSkaia;
import com.mraof.minestuck.world.gen.OreHandler;
import com.mraof.minestuck.world.gen.structure.StructureCastlePieces;
import com.mraof.minestuck.world.gen.structure.StructureCastleStart;
import com.mraof.minestuck.world.storage.MinestuckSaveHandler;

@Mod(modid = "Minestuck", name = "Minestuck", version = "@VERSION@")
public class Minestuck
{
	//these ids are in case I need to raise or lower ids for whatever reason
	public static int entityIdStart = 5050;
	public static int skaiaProviderTypeId = 2;
	public static int skaiaDimensionId = 2;
	public static int landProviderTypeId = 3;
	public static int landDimensionIdStart = 3;

	//hammers
	public static Item clawHammer;
	public static Item sledgeHammer;
	public static Item pogoHammer;
	public static Item telescopicSassacrusher;
	public static Item fearNoAnvil;
	public static Item zillyhooHammer;
	public static Item popamaticVrillyhoo;
	public static Item scarletZillyhoo;
	//blades
	public static Item sord;
	public static Item ninjaSword;
	public static Item katana;
	public static Item caledscratch;
	public static Item royalDeringer;
	public static Item regisword;
	public static Item scarletRibbitar;
	public static Item doggMachete;
	//sickles
	public static Item sickle;
	public static Item homesSmellYaLater;
	public static Item regiSickle;
	public static Item clawSickle;
	//clubs
	public static Item deuceClub;
	//canes
	public static Item cane;
	public static Item spearCane;
	public static Item dragonCane;
	//Spoons/forks
	public static ItemSpork crockerSpork;
	public static Item skaiaFork;
	//Other
	public static Item rawCruxite;
	public static Item cruxiteDowel;
	public static Item captchaCard;
	public static Item cruxiteArtifact;
	public static Item disk;
	public static Item component;
	public static ItemModus captchaModus;
	public static ItemMinestuckBucket minestuckBucket;
	public static ItemGoldSeeds goldSeeds;	//This item is pretty much only a joke
	
	//Blocks
	public static Block chessTile;
	public static BlockColoredDirt coloredDirt;
	public static Block gatePortal;
	public static OreCruxite oreCruxite;
	public static Block blockStorage;
	public static Block blockMachine;
	public static Block blockComputerOn;
	public static Block blockComputerOff;
	public static Block transportalizer;
	public static BlockGoldSeeds blockGoldSeeds;
	
	public static Block blockOil;	//TODO Use fluid-rendering code when implemented
	public static Block blockBlood;
	public static Block blockBrainJuice;
	public static Block layeredSand;

	public static Fluid fluidOil;
	public static Fluid fluidBlood;
	public static Fluid fluidBrainJuice;

	
	
	//Client config
	public static int clientOverworldEditRange;	//Edit range used by the client side.
	public static int clientLandEditRange;	//changed by a MinestuckConfigPacket sent by the server on login.
	public static byte clientTreeAutobalance;
	public static boolean clientHardMode;
	public static boolean clientGiveItems;
	public static boolean clientEasyDesignix;
	public static boolean clientInfTreeModus;
	
	//General
	public static boolean hardMode = false;	//Future config option. Currently alters how easy the entry items are accessible after the first time. The machines cost 100 build and there will only be one card if this is true.
	public static boolean generateCruxiteOre; //If set to false, Cruxite Ore will not generate
	public static boolean privateComputers;	//If a player should be able to use other players computers or not.
	//public static boolean acceptTitleCollision;	//Allows combinations like "Heir of Hope" and "Seer of Hope" to exist in the same session. Will try to avoid duplicates.
	//public static boolean generateSpecialClasses;	//Allow generation of the "Lord" and "Muse" classes.
	public static boolean globalSession;	//Makes only one session possible. Recommended to be true on small servers. Will be ignored when loading a world that already got 2+ sessions.
	public static boolean easyDesignix; //Makes it so you don't need to encode individual cards before combining them.
	public static boolean toolTipEnabled;
	public static boolean forceMaxSize;	//If it should prevent players from joining a session if there is no possible combinations left.
	public static boolean giveItems;
	public static boolean specialCardRenderer;
	public static boolean infiniteTreeModus;
	public static String privateMessage;
	public static int artifactRange; //The range of the Cruxite Artifact in teleporting zones over to the new land
	public static int overworldEditRange;
	public static int landEditRange;
	public static int cardResolution;
	public static int defaultModusSize;
	public static int defaultModusType;
	public static int modusMaxSize;
	/**
	 * 0: Make the player's new server player his/her old server player's server player
	 * 1: The player that lost his/her server player will have an idle main connection until someone without a client player connects to him/her.
	 * (Will try to put a better explanation somewhere else later)
	 */
	public static int escapeFailureMode;	//What will happen if someone's server player fails to escape the overworld in time,
	public static byte treeModusSetting;
	
	public static boolean[] deployConfigurations;
	
	// The instance of your mod that Forge uses.
	@Instance("Minestuck")
	public static Minestuck instance;

	// Says where the client and server 'proxy' code is loaded.
	@SidedProxy(clientSide="com.mraof.minestuck.client.ClientProxy", serverSide="com.mraof.minestuck.CommonProxy")

	//The proxy to be used by client and server
	public static CommonProxy proxy;
	public static CreativeTabs tabMinestuck;
	public static EnumMap<Side, FMLEmbeddedChannel> channels;

	public int currentEntityIdOffset = 0;
	public static long worldSeed = 0;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) 
	{
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		
		entityIdStart = config.getInt("entitydIdStart", "Entity Ids", 5050, 0, Integer.MAX_VALUE, "From what id that minestuck entites should be registered with."); //The number 5050 might make it seem like this is meant to match up with item/block IDs, but it is not
		skaiaProviderTypeId = config.getInt("skaiaProviderTypeId", "Provider Type Ids", 2, 2, Integer.MAX_VALUE, "The id to be registered for the skaia provider.");
		skaiaDimensionId = config.getInt("skaiaDimensionId", "Dimension Ids", 2, 2, Integer.MAX_VALUE, "The id for the skaia dimension.");
		landProviderTypeId = config.getInt("landProviderTypeId", "Provider Type Ids", 3, 2, Integer.MAX_VALUE, "The id for the land provider.");
		landDimensionIdStart = config.getInt("landDimensionIdStart", "Dimension Ids", 3, 2, Integer.MAX_VALUE, "The starting id for the land dimensions.");
		Debug.isDebugMode = config.getBoolean("printDebugMessages", "General", true, "Whenether the game should print debug messages or not.");
		generateCruxiteOre = config.getBoolean("generateCruxiteOre", "General", true, "If cruxite ore should be generated in the overworld.");
		//acceptTitleCollision = config.get("General", "acceptTitleCollision", false).getBoolean(false);	Unused
		//generateSpecialClasses = config.get("General", "generateSpecialClasses", false).getBoolean(false);
		globalSession = config.getBoolean("globalSession", "General", true, "Whenether all connetions should be put into a single session or not.");
		privateComputers = config.getBoolean("privateComputers", "General", false, "True if computers should only be able to be used by the owner.");
		privateMessage = config.getString("privateMessage", "General", "You are not allowed to access other players computers.", "The message sent when someone tries to access a computer that they aren't the owner of if 'privateComputers' is true.");
		easyDesignix  = config.getBoolean("easyDesignix", "General", true, "If this is true, you can do combination alchemy without first putting the item in a card.");
		overworldEditRange = config.getInt("overWorldEditRange", "General", 15, 3, 50, "A number that determines how far away from the computer an editmode player may be before entry.");
		landEditRange = config.getInt("landEditRange", "General", 30, 3, 50, "A number that determines how far away from the center of the brought land that an editmode player may be after entry.");
		artifactRange = config.getInt("artifactRange", "General", 30, 3, 50, "Radius of the land brought into the medium.");
		MinestuckAchievementHandler.idOffset = config.getInt("statisticIdOffset", "General", 413, 100, Integer.MAX_VALUE, "The id offset used when registering achievements and other statistics.");
		hardMode = config.getBoolean("hardMode", "General", false, "Used to determine if the editmode player can provide infinite cards. Will later also be used whenether there'll be a timer to enter the medium and things like that.");
		//forceMaxSize = config.getBoolean("forceMaxSize", "General", false); Unused for now.
		//escapeFailureMode = config.getInt("escapeFailureMode", "General", 0, 0, 2, "Used to determine what happens with related connections when a player fails to escape the meteor enabled by 'hardmode'.");
		giveItems = config.getBoolean("giveItems", "General", false, "Setting this to true replaces editmode with the old Give Items button.");
		defaultModusSize = config.getInt("defaultModusSize", "Modus", 5, 0, Integer.MAX_VALUE, "The initial size of a captchalouge deck.");
		defaultModusType = config.getInt("defaultModusType", "Modus", -1, -2, CaptchaDeckHandler.ModusType.values().length - 1,
				"The type of modus that is given to players without one. -1: Random modus given from a builtin list. -2: Any random modus. 0+: Certain modus given. Anything else: No modus given.");
		modusMaxSize = config.getInt("modusMaxSize", "Modus", 0, 0, Integer.MAX_VALUE, "The max size on a modus. Ignored if the value is 0.");
		if(defaultModusSize > modusMaxSize && modusMaxSize > 0)
			defaultModusSize = modusMaxSize;
		deployConfigurations = new boolean[1];
		deployConfigurations[0] = config.getBoolean("cardInDeploylist", "General", false, "Determines if a card with a captcha card punched on it should be added to the deploy list or not.");
		treeModusSetting = (byte) config.getInt("treeModusSetting", "Modus", 0, 0, 2, "This determines how autobalance should be. 0 if the player should choose, 1 if forced at on, and 2 if forced at off.");
		
		if(event.getSide().isClient()) {	//Client sided config values
			toolTipEnabled = config.getBoolean("editmodeToolTip", "General", true, "True if the grist cost on items should be shown. This only applies for editmode.");
			//specialCardRenderer = config.getBoolean("specialCardRenderer", "General", false, "Whenether to use the special render for cards or not.");
			if(Minestuck.specialCardRenderer && !GLContext.getCapabilities().GL_EXT_framebuffer_object)
			{
				specialCardRenderer = false;
				FMLLog.warning("[Minestuck] The FBO extension is not available and is required for the advanced rendering of captchalouge cards.");
			}
			//cardResolution = config.getInt("General", "cardResolution", 1, 0, 5, "The resulotion of the item inside of a card. The width/height is computed by '8*2^x', where 'x' is this config value.");
		}
		config.save();
		
		(new UpdateChecker()).start();
		
		//Register the Minestuck creative tab
		tabMinestuck = new CreativeTabs("tabMinestuck")
		{
			@Override
			public Item getTabIconItem() {
				return zillyhooHammer;
			}
		};
		
		//blocks
		chessTile = GameRegistry.registerBlock(new BlockChessTile(), ItemChessTile.class, "chess_tile");
		gatePortal = GameRegistry.registerBlock(new BlockGatePortal(Material.portal), "gate_portal");
		oreCruxite = (OreCruxite) GameRegistry.registerBlock(new OreCruxite(), ItemOreCruxite.class, "ore_cruxite");
		layeredSand = GameRegistry.registerBlock(new BlockLayered(Blocks.sand), ItemBlockLayered.class, "layered_sand").setUnlocalizedName("layeredSand");
		coloredDirt = (BlockColoredDirt) GameRegistry.registerBlock(new BlockColoredDirt(), ItemColoredDirt.class, "colored_dirt").setUnlocalizedName("coloredDirt").setHardness(0.5F);
		//machines
		blockStorage = GameRegistry.registerBlock(new BlockStorage(),ItemStorageBlock.class,"storage_block");
		blockMachine = GameRegistry.registerBlock(new BlockMachine(), ItemMachine.class,"machine_block");
		blockComputerOff = GameRegistry.registerBlock(new BlockComputerOff(),"computer_standard");
		blockComputerOn = GameRegistry.registerBlock(new BlockComputerOn(), null, "computer_standard_on");
		transportalizer = GameRegistry.registerBlock(new BlockTransportalizer(), "transportalizer");
		blockGoldSeeds = (BlockGoldSeeds) GameRegistry.registerBlock(new BlockGoldSeeds(), null, "gold_seeds");
		//fluids
		fluidOil = new Fluid("Oil");
		FluidRegistry.registerFluid(fluidOil);
		fluidBlood = new Fluid("Blood");
		FluidRegistry.registerFluid(fluidBlood);
		fluidBrainJuice = new Fluid("BrainJuice");
		FluidRegistry.registerFluid(fluidBrainJuice);
//		blockOil = GameRegistry.registerBlock(new BlockFluidOil(fluidOil, Material.water), null, "block_oil");
//		blockBlood = GameRegistry.registerBlock(new BlockFluidBlood(fluidBlood, Material.water), null, "block_blood");
//		blockBrainJuice = GameRegistry.registerBlock(new BlockFluidBrainJuice(fluidBrainJuice, Material.water), null, "block_brain_juice");

		//items
		//hammers
		clawHammer = new ItemHammer(EnumHammerType.CLAW);
		sledgeHammer = new ItemHammer(EnumHammerType.SLEDGE);
		pogoHammer = new ItemHammer(EnumHammerType.POGO);
		telescopicSassacrusher = new ItemHammer(EnumHammerType.TELESCOPIC);
		fearNoAnvil = new ItemHammer(EnumHammerType.FEARNOANVIL);
		zillyhooHammer = new ItemHammer(EnumHammerType.ZILLYHOO);
		popamaticVrillyhoo = new ItemHammer(EnumHammerType.POPAMATIC);
		scarletZillyhoo = new ItemHammer(EnumHammerType.SCARLET);
		//blades
		sord = new ItemBlade(EnumBladeType.SORD);
		ninjaSword = new ItemBlade(EnumBladeType.NINJA);
		katana = new ItemBlade(EnumBladeType.KATANA);
		caledscratch = new ItemBlade(EnumBladeType.CALEDSCRATCH);
		royalDeringer = new ItemBlade(EnumBladeType.DERINGER);
		regisword = new ItemBlade(EnumBladeType.REGISWORD);
		scarletRibbitar = new ItemBlade(EnumBladeType.SCARLET);
		doggMachete = new ItemBlade(EnumBladeType.DOGG);
		//sickles
		sickle = new ItemSickle(EnumSickleType.SICKLE);
		homesSmellYaLater = new ItemSickle(EnumSickleType.HOMES);
		regiSickle = new ItemSickle(EnumSickleType.REGISICKLE);
		clawSickle = new ItemSickle(EnumSickleType.CLAW);
		//clubs
		deuceClub = new ItemClub(EnumClubType.DEUCE);
		//canes
		cane = new ItemCane(EnumCaneType.CANE);
		spearCane = new ItemCane(EnumCaneType.SPEAR);
		dragonCane = new ItemCane(EnumCaneType.DRAGON);
		//Spoons/forks
		crockerSpork = new ItemSpork(EnumSporkType.CROCKER);
		skaiaFork = new ItemSpork(EnumSporkType.SKAIA);
		//items
		rawCruxite = new ItemCruxiteRaw();
		cruxiteDowel = new ItemDowel();
		captchaCard = new ItemCaptchaCard();
		cruxiteArtifact = new ItemCruxiteArtifact(1, false);
		disk = new ItemDisk();
		component = new ItemComponent();
		minestuckBucket = new ItemMinestuckBucket();
		captchaModus = new ItemModus();
		goldSeeds = new ItemGoldSeeds();
		
//		minestuckBucket.addBlock(blockBlood);
//		minestuckBucket.addBlock(blockOil);
//		minestuckBucket.addBlock(blockBrainJuice);
		
		GameRegistry.registerItem(clawHammer, "claw_hammer");
		GameRegistry.registerItem(sledgeHammer, "sledge_hammer");
		GameRegistry.registerItem(pogoHammer, "pogo_hammer");
		GameRegistry.registerItem(telescopicSassacrusher, "telescopic_sassacrusher");
		GameRegistry.registerItem(fearNoAnvil, "fear_no_anvil");
		GameRegistry.registerItem(zillyhooHammer, "zillyhoo_hammer");
		GameRegistry.registerItem(popamaticVrillyhoo, "popamatic_vrillyhoo");
		GameRegistry.registerItem(scarletZillyhoo, "scarlet_zillyhoo");
		
		GameRegistry.registerItem(sord, "sord");
		GameRegistry.registerItem(ninjaSword, "ninja_sword");
		GameRegistry.registerItem(katana, "katana");
		GameRegistry.registerItem(caledscratch, "caledscratch");
		GameRegistry.registerItem(royalDeringer, "royal_deringer");
		GameRegistry.registerItem(regisword, "regisword");
		GameRegistry.registerItem(scarletRibbitar, "scarlet_ribbitar");
		GameRegistry.registerItem(doggMachete, "dogg_machete");
		
		GameRegistry.registerItem(sickle, "sickle");
		GameRegistry.registerItem(homesSmellYaLater, "homes_smell_ya_later");
		GameRegistry.registerItem(regiSickle, "regi_sickle");
		GameRegistry.registerItem(clawSickle, "claw_sickle");
		
		GameRegistry.registerItem(deuceClub, "deuce_club");
		
		GameRegistry.registerItem(cane, "cane");
		GameRegistry.registerItem(spearCane, "spear_cane");
		GameRegistry.registerItem(dragonCane, "dragon_cane");
		
		GameRegistry.registerItem(crockerSpork, "crocker_spork");
		GameRegistry.registerItem(skaiaFork, "skaia_fork");
		
		GameRegistry.registerItem(rawCruxite, "cruxite_raw");
		GameRegistry.registerItem(cruxiteDowel, "cruxite_dowel");
		GameRegistry.registerItem(captchaCard, "captcha_card");
		GameRegistry.registerItem(cruxiteArtifact, "cruxite_artifact");
		GameRegistry.registerItem(disk, "computer_disk");
		GameRegistry.registerItem(component, "component");
		GameRegistry.registerItem(minestuckBucket, "minestuck_bucket");
		GameRegistry.registerItem(captchaModus, "modus_card");
		GameRegistry.registerItem(goldSeeds, "gold_seeds");
		
		if(event.getSide().isClient())
		{
			ClientProxy.registerSided();
			MinestuckTextureManager.registerVariants();
		}
		
		MinestuckAchievementHandler.prepareAchievementPage();
		
	}
	
	@EventHandler
	public void load(FMLInitializationEvent event) 
	{
		
//		fluidOil.setUnlocalizedName(blockOil.getUnlocalizedName());
//		fluidBlood.setUnlocalizedName(blockBlood.getUnlocalizedName());
//		fluidBrainJuice.setUnlocalizedName(blockBrainJuice.getUnlocalizedName());
		
		
		//register entities
		this.registerAndMapEntity(EntitySalamander.class, "Salamander", 0xffe62e, 0xfffb53);
		this.registerAndMapEntity(EntityNakagator.class, "Nakagator", 0xff0000, 0xff6a00);
		this.registerAndMapEntity(EntityIguana.class, "Iguana", 0x0026ff, 0x0094ff);
		this.registerAndMapEntity(EntityImp.class, "Imp", 0x000000, 0xffffff);
		this.registerAndMapEntity(EntityOgre.class, "Ogre", 0x000000, 0xffffff);
		this.registerAndMapEntity(EntityBasilisk.class, "Basilisk", 0x400040, 0xffffff);
		this.registerAndMapEntity(EntityGiclops.class, "Giclops", 0x000000, 0xffffff);
		this.registerAndMapEntity(EntityBlackPawn.class, "dersitePawn", 0x0f0f0f, 0xf0f0f0);
		this.registerAndMapEntity(EntityWhitePawn.class, "prospitianPawn", 0xf0f0f0, 0x0f0f0f);
		this.registerAndMapEntity(EntityBlackBishop.class, "dersiteBishop", 0x000000, 0xc121d9);
		this.registerAndMapEntity(EntityWhiteBishop.class, "prospitianBishop", 0xffffff, 0xfde500);
		this.registerAndMapEntity(EntityBlackRook.class, "dersiteRook", 0x000000, 0xc121d9);
		this.registerAndMapEntity(EntityWhiteRook.class, "prospitianRook", 0xffffff, 0xfde500);
		EntityRegistry.registerModEntity(EntityDecoy.class, "playerDecoy", currentEntityIdOffset, this, 80, 3, true);
		currentEntityIdOffset++;
		
		//register entities with fml
		EntityRegistry.registerModEntity(EntityGrist.class, "grist", currentEntityIdOffset, this, 512, 1, true);
		
		//register Tile Entities
		GameRegistry.registerTileEntity(TileEntityGatePortal.class, "gatePortal");
		GameRegistry.registerTileEntity(TileEntityMachine.class, "containerMachine");
		GameRegistry.registerTileEntity(TileEntityComputer.class, "computerSburb");
		GameRegistry.registerTileEntity(TileEntityTransportalizer.class, "transportalizer");
		//register world generators
		DimensionManager.registerProviderType(skaiaProviderTypeId, WorldProviderSkaia.class, true);
		DimensionManager.registerDimension(skaiaDimensionId, skaiaProviderTypeId);
		DimensionManager.registerProviderType(landProviderTypeId, WorldProviderLands.class, true);
		
		//register ore generation
		OreHandler oreHandler = new OreHandler();
		GameRegistry.registerWorldGenerator(oreHandler, 0);
		
		//register GUI handler
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
		
		//Register textures and renders
		if(event.getSide().isClient())
		{
			MinestuckTextureManager.registerTextures();
			ClientProxy.registerRenderers();
		}
		
		//Register event handlers
		MinecraftForge.EVENT_BUS.register(new MinestuckSaveHandler());
		MinecraftForge.EVENT_BUS.register(new MinestuckFluidHandler());
		MinecraftForge.EVENT_BUS.register(ServerEditHandler.instance);
		MinecraftForge.EVENT_BUS.register(MinestuckAchievementHandler.instance);
		
		FMLCommonHandler.instance().bus().register(MinestuckPlayerTracker.instance);
		FMLCommonHandler.instance().bus().register(ServerEditHandler.instance);
		FMLCommonHandler.instance().bus().register(MinestuckChannelHandler.instance);
		
		if(event.getSide().isClient())
		{
			MinecraftForge.EVENT_BUS.register(ClientEditHandler.instance);
			FMLCommonHandler.instance().bus().register(ClientEditHandler.instance);
		}
		
		//register channel handler
		channels = NetworkRegistry.INSTANCE.newChannel("Minestuck", MinestuckChannelHandler.instance);
		
		//Register structures
		MapGenStructureIO.registerStructure(StructureCastleStart.class, "SkaiaCastle");
		StructureCastlePieces.registerComponents();
		
		//register recipes
		AlchemyRecipeHandler.registerVanillaRecipes();
		AlchemyRecipeHandler.registerMinestuckRecipes();
		AlchemyRecipeHandler.registerModRecipes();
		
		KindAbstratusList.registerTypes();
		DeployList.registerItems();
		
		ComputerProgram.registerProgram(0, SburbClient.class, new ItemStack(disk, 1, 0));	//This idea was kind of bad and should be replaced
		ComputerProgram.registerProgram(1, SburbServer.class, new ItemStack(disk, 1, 1));
		
		SessionHandler.maxSize = Integer.MAX_VALUE;//acceptTitleCollision?(generateSpecialClasses?168:144):12;
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) 
	{
		AlchemyRecipeHandler.registerDynamicRecipes();

		//register NEI stuff
		if (Loader.isModLoaded("NotEnoughItems")) {
			NEIModContainer.plugins.add(new NEIMinestuckConfig());
		}
	}
	//registers entity with forge and minecraft, and increases currentEntityIdOffset by one in order to prevent id collision
	public void registerAndMapEntity(Class<? extends Entity> entityClass, String name, int eggColor, int eggSpotColor)
	{
		this.registerAndMapEntity(entityClass, name, eggColor, eggSpotColor, 80, 3, true);
	}

	public void registerAndMapEntity(Class<? extends Entity> entityClass, String name, int eggColor, int eggSpotColor, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates)
	{
		EntityList.addMapping(entityClass, name, entityIdStart + currentEntityIdOffset, eggColor, eggSpotColor);
		EntityRegistry.registerModEntity(entityClass, name, currentEntityIdOffset, this, trackingRange, updateFrequency, sendsVelocityUpdates);
		currentEntityIdOffset++;
	}

	@EventHandler 
	public void serverAboutToStart(FMLServerAboutToStartEvent event)
	{
		//unregister lands that may not be in this save
		for (Iterator<Byte> iterator = MinestuckSaveHandler.lands.iterator(); iterator.hasNext(); )
		{
			int dim = ((Number)iterator.next()).intValue();
			if(DimensionManager.isDimensionRegistered(dim))
			{
				DimensionManager.unregisterDimension(dim);
				//Debug.print("Server about to start, Unregistering " + dim);
			}
			iterator.remove();
		}
		TileEntityTransportalizer.transportalizers.clear();
		DeployList.applyConfigValues(deployConfigurations);
	}
	@EventHandler
	public void serverStarting(FMLServerStartingEvent event)
	{
		worldSeed = event.getServer().worldServers[0].getSeed();
		CaptchaDeckHandler.rand = new Random(worldSeed);	//Unsure whenether this will be better or not
		
		MinestuckSaveHandler.lands.clear();
		
		File dataFile = event.getServer().worldServers[0].getSaveHandler().getMapFileFromName("MinestuckData");
		if(dataFile != null && dataFile.exists()) {
			NBTTagCompound nbt = null;
			try {
				nbt = CompressedStreamTools.readCompressed(new FileInputStream(dataFile));
			} catch(IOException e) {
				e.printStackTrace();
			}
			if(nbt != null) {
				for(byte landId : nbt.getByteArray("landList")) {
					if(MinestuckSaveHandler.lands.contains((Byte)landId))
						continue;
					MinestuckSaveHandler.lands.add(landId);
					
					if(!DimensionManager.isDimensionRegistered(landId))
						DimensionManager.registerDimension(landId, Minestuck.landProviderTypeId);
				}
				
				SkaianetHandler.loadData(nbt.getCompoundTag("skaianet"));
				
				MinestuckPlayerData.readFromNBT(nbt);

				TileEntityTransportalizer.loadTransportalizers(nbt.getCompoundTag("transportalizers"));
				
				return;
			}
		}
		
		SkaianetHandler.loadData(null);
		MinestuckPlayerData.readFromNBT(null);
		
	}
	
	@EventHandler
	public void serverStopping(FMLServerStoppingEvent event)
	{
		ServerEditHandler.onServerStopping();
	}
}
