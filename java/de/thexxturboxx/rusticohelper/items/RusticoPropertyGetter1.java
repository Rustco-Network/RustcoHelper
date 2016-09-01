package de.thexxturboxx.rusticohelper.items;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.world.World;

public class RusticoPropertyGetter1 implements IItemPropertyGetter {
	
	@Override
	public float apply(ItemStack stack, World worldIn, EntityLivingBase entityIn) {
		if(stack.getItem() == Items.CAULDRON) {
			if(stack.getDisplayName().equals("§fCrucible")) {
				return 1;
			}
		} else if(stack.getItem() == Items.DYE && stack.getMetadata() == EnumDyeColor.BROWN.getMetadata()) {
			if(stack.getDisplayName().equals("§fGrass Seeds")) {
				return 1;
			}
		}
		return 0;
	}
	
}