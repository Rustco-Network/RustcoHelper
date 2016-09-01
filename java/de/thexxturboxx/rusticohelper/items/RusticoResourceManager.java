package de.thexxturboxx.rusticohelper.items;

import java.lang.reflect.Field;
import java.util.*;

import de.thexxturboxx.rusticohelper.RusticoHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ItemOverride;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class RusticoResourceManager implements IResourceManagerReloadListener {
	
	@Override
	public void onResourceManagerReload(IResourceManager resourceManager) {
		ItemOverrideList i = Minecraft.getMinecraft()
				.getRenderItem().getItemModelMesher().getItemModel(new ItemStack(Items.STICK)).getOverrides();
		Map<ResourceLocation, Float> m = new HashMap<ResourceLocation, Float>();
		m.put(new ResourceLocation(RusticoHelper.ID, "crook"), 1F);
		try {
			Field d = ItemOverrideList.class.getDeclaredField("overrides");
			d.setAccessible(true);
			List<ItemOverride> l = (List<ItemOverride>) d.get(i);
			l.add(new ItemOverride(new ResourceLocation(RusticoHelper.ID, "crook"), m));
			d.set(i, l);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}