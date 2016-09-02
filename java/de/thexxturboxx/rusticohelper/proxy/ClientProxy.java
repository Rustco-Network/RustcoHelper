package de.thexxturboxx.rusticohelper.proxy;

import org.lwjgl.input.Keyboard;

import de.thexxturboxx.rusticohelper.RusticoHelper;
import de.thexxturboxx.rusticohelper.gui.GuiMain;
import de.thexxturboxx.rusticohelper.items.ResourceItem;
import de.thexxturboxx.rusticohelper.items.RusticoResourceManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.*;

public class ClientProxy extends CommonProxy {
	
	public static KeyBinding[] keyBindings;
	public static final String categoryKeys = "keys.rusticohelper.category";
	
	@Override
	public void preInit(FMLPreInitializationEvent e) {
		super.preInit(e);
	}
	
	@Override
	public void init(FMLInitializationEvent e) {
		super.init(e);
		keyBindings = new KeyBinding[] {
		//KeyBinding for opening the general GUI, id: 0
		new KeyBinding("key.guigeneral.desc", Keyboard.KEY_C, categoryKeys)
		};
		for(KeyBinding kb : keyBindings) {
		    ClientRegistry.registerKeyBinding(kb);
		}
		RusticoHelper.registerItems();
		IReloadableResourceManager irrm = (IReloadableResourceManager) Minecraft.getMinecraft().getResourceManager();
		for(ResourceItem ri : RusticoHelper.resourceList) {
			irrm.registerReloadListener(new RusticoResourceManager(ri.itemName, ri.toOverride, ri.meta));
		}
	}
	
	@Override
	public void postInit(FMLPostInitializationEvent e) {
		super.postInit(e);
		registerEventHandler(new GuiMain());
	}
	
	public static void registerEventHandler(Object eventclass) {
	    MinecraftForge.EVENT_BUS.register(eventclass);
	}
	
}