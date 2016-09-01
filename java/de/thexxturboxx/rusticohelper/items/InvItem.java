package de.thexxturboxx.rusticohelper.items;

import java.util.ArrayList;
import java.util.List;

import de.thexxturboxx.rusticohelper.RusticoHelper;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class InvItem {
	
	String texture, name;
	Item material;
	int rls, bigAnimations;
	List<String> lore;
	
	public InvItem(String name, String texture, int bigAnimations, Item material, int rls, List<String> lore) {
		this.texture = texture;
		this.name = name;
		this.material = material;
		this.lore = lore;
		this.rls = rls;
		this.bigAnimations = bigAnimations;
		ModelBakery.registerItemVariants(material, new ResourceLocation(RusticoHelper.ID, "textures/items/" + texture + ".png"));
	}
	
	public InvItem(String name, String texture, int bigAnimations, Block material, int rls, List<String> lore) {
		this(name, texture, bigAnimations, Item.getItemFromBlock(material), rls, lore);
	}
	
	public String getName() {
		return name;
	}
	
	public String getTName() {
		return texture;
	}
	
	public Item getMaterial() {
		return material;
	}
	
	public List<String> getLore() {
		return lore;
	}
	
	public List<ResourceLocation> getCraftingTextures() {
		List<ResourceLocation> list = new ArrayList<ResourceLocation>();
		int i = 0;
		while(i <= rls) {
			if(i == 0) {
				list.add(new ResourceLocation(RusticoHelper.ID, "textures/crafting/" + texture + ".png"));
			} else {
				list.add(new ResourceLocation(RusticoHelper.ID, "textures/crafting/" + texture + "_" + i + ".png"));
			}
			i++;
		}
		return list;
	}
	
	public List<ResourceLocation> getItemTextures() {
		List<ResourceLocation> list = new ArrayList<ResourceLocation>();
		int i = 0;
		while(i <= bigAnimations) {
			if(i == 0) {
				list.add(new ResourceLocation(RusticoHelper.ID, "textures/items/" + texture + ".png"));
			} else {
				list.add(new ResourceLocation(RusticoHelper.ID, "textures/items/" + texture + "_" + i + ".png"));
			}
			i++;
		}
		return list;
	}
	
}