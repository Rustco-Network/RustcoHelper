package de.thexxturboxx.rusticohelper;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.thexxturboxx.rusticohelper.items.*;
import de.thexxturboxx.rusticohelper.proxy.CommonProxy;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import scala.actors.threadpool.Arrays;

@Mod(modid = RusticoHelper.ID, name = RusticoHelper.NAME, version = RusticoHelper.VERSION, clientSideOnly = true,
updateJSON = RusticoHelper.UPDATE_JSON)
public class RusticoHelper {
	
	public static final String NAME = "Rustico Helper";
	public static final String VERSION = "0.0.1";
	public static final String ID = "rusticohelper";
	public static final String UPDATE_JSON = "https://raw.githubusercontent.com/Rustico-Network/Rustico-Network.github.io/master/checker.json";
	public static final Logger LOG = LogManager.getLogger(NAME);
	public static final ResourceLocation PLACEHOLER_RECIPE = new ResourceLocation(ID, "textures/crafting/none.png");
	
	public static List<InvItem> itemList;
	
	@Instance(ID)
	public static RusticoHelper instance;
	
	@SidedProxy(modId = ID,
			clientSide = "de.thexxturboxx.rusticohelper.proxy.ClientProxy",
			serverSide = "de.thexxturboxx.rusticohelper.proxy.CommonProxy")
	public static CommonProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.preInit(event);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
	}
	
	public static void registerItems() {
		itemList = new ArrayList<InvItem>();
		addItem("Crook", "crook", Items.STICK, 2);
		addResources(Items.STICK, "crook", 0);
		/*addItem("Silk Mesh", "silk_mesh", Blocks.WEB, 1);
		addResources(Blocks.WEB, "silk_mesh", 0);
		addItem("Sieve", "sieve", Blocks.NOTEBLOCK, 6);
		addResources(Blocks.NOTEBLOCK, "sieve", 0);
		addItem("Barrel", "barrel", Items.CAULDRON, 6);
		addResources(Items.CAULDRON, "barrel", 0);
		addItem("Porcelain Clay", "porcelain_clay", Items.CLAY_BALL, 1);
		addResources(Items.CLAY_BALL, "porcelain_clay", 0);
		addItem("Crucible", "crucible", Items.CAULDRON, 1);
		addResources(Items.CAULDRON, "crucible", 1);
		addItem("Stones", "stones", Blocks.STONE_BUTTON, 0);
		addResources(Blocks.STONE_BUTTON, "stones", 0);
		addItem("Ancient Spores", "ancient_spores", Items.DYE, 0);
		addResources(Items.DYE, "ancient_spores", 0, EnumDyeColor.BROWN.getMetadata());
		addItem("Grass Seeds", "grass_seeds", Items.DYE, 0);
		addResources(Items.DYE, "grass_seeds", 1, EnumDyeColor.BROWN.getMetadata());
		addItem("Silkworm", "silkworm", Items.FLOWER_POT, 0);
		addResources(Items.FLOWER_POT, "silkworm", 0);
		addItem("Hammer", "hammer", 5, Items.DIAMOND_PICKAXE, 5);
		addResources(Items.WOODEN_PICKAXE, "wooden_hammer", 0);
		addResources(Items.STONE_PICKAXE, "stone_hammer", 0);
		addResources(Items.IRON_PICKAXE, "iron_hammer", 0);
		addResources(Items.GOLDEN_PICKAXE, "gold_hammer", 0);
		addResources(Items.DIAMOND_PICKAXE, "diamond_hammer", 0);
		addItem("Water (Single-Use)", "water", Items.WATER_BUCKET, 0);
		addResources(Items.WATER_BUCKET, "water", 0);
		addItem("Lava (Single-Use)", "lava", Items.LAVA_BUCKET, 0);
		addResources(Items.LAVA_BUCKET, "lava", 0);*/
	}
	
	public static void addResources(Block toOverride, String itemName, int getter) {
		addResources(Item.getItemFromBlock(toOverride), itemName, getter);
	}
	
	public static void addResources(Block toOverride, String itemName, int getter, int meta) {
		addResources(Item.getItemFromBlock(toOverride), itemName, getter, meta);
	}
	
	public static void addResources(Item toOverride, String itemName, int getter) {
		addResources(toOverride, itemName, getter, 0);
	}
	
	public static void addResources(Item toOverride, String itemName, int getter, int meta) {
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().
		register(toOverride, meta, new ModelResourceLocation(RusticoHelper.ID + ":" + itemName, "inventory"));
		switch(getter) {
		case 0:
			toOverride.addPropertyOverride(new ResourceLocation(itemName), new RusticoPropertyGetter());
			break;
		case 1:
			toOverride.addPropertyOverride(new ResourceLocation(itemName + "_1"), new RusticoPropertyGetter1());
			break;
		}
	}
	
	public static void addItem(String name, String texture, Item material, int rls, String ... lore) {
		addItem(name, texture, 1, material, rls, lore);
	}
	
	public static void addItem(String name, String texture, Block material, int rls, String ... lore) {
		addItem(name, texture, 1, material, rls, lore);
	}
	
	public static void addItem(String name, String texture, int bigAnimations, Item material, int rls, String ... lore) {
		itemList.add(new InvItem(name, texture, bigAnimations - 1, material, rls - 1, Arrays.asList(lore)));
	}
	
	public static void addItem(String name, String texture, int bigAnimations, Block material, int rls, String ... lore) {
		itemList.add(new InvItem(name, texture, bigAnimations - 1, material, rls - 1, Arrays.asList(lore)));
	}
	
}