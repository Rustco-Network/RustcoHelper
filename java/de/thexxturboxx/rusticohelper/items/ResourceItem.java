package de.thexxturboxx.rusticohelper.items;

import net.minecraft.item.Item;

public class ResourceItem {
	
	public Item toOverride;
	public String itemName;
	public int getter;
	public int meta;
	
	public ResourceItem(Item toOverride, String itemName, int getter) {
		this(toOverride, itemName, getter, 0);
	}
	
	public ResourceItem(Item toOverride, String itemName, int getter, int meta) {
		this.toOverride = toOverride;
		this.itemName = itemName;
		this.getter = getter;
		this.meta = meta;
	}
	
}