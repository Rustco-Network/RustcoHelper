package de.thexxturboxx.rusticohelper.items;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.world.World;

public class RusticoPropertyGetter implements IItemPropertyGetter {
	
	@Override
	public float apply(ItemStack stack, World worldIn, EntityLivingBase entityIn) {
		if(stack.getItem() == Items.STICK) {
			if(stack.getDisplayName().equals("Crook")) {
				return 1F;
			}
		} else if(stack.getItem() == Item.getItemFromBlock(Blocks.WEB)) {
			if(stack.getDisplayName().equals("Silk Mesh")) {
				return 1;
			}
		} else if(stack.getItem() == Item.getItemFromBlock(Blocks.NOTEBLOCK)) {
			if(stack.getDisplayName().equals("§fSieve")) {
				return 1;
			}
		} else if(stack.getItem() == Items.CAULDRON) {
			if(stack.getDisplayName().equals("§fBarrel")) {
				return 1;
			}
		} else if(stack.getItem() == Item.getItemFromBlock(Blocks.STONE_BUTTON)) {
			if(stack.getDisplayName().equals("§fStones")) {
				return 1;
			}
		} else if(stack.getItem() == Items.DYE && stack.getMetadata() == EnumDyeColor.BROWN.getMetadata()) {
			if(stack.getDisplayName().equals("§fAncient Spores")) {
				return 1;
			}
		} else if(stack.getItem() == Items.FLOWER_POT) {
			if(stack.getDisplayName().equals("§fSilkworm")) {
				return 1;
			}
		} else if(stack.getItem() == Items.WOODEN_PICKAXE) {
			if(stack.getDisplayName().equals("§fWooden Hammer")) {
				return 1;
			}
		} else if(stack.getItem() == Items.STONE_PICKAXE) {
			if(stack.getDisplayName().equals("§fStone Hammer")) {
				return 1;
			}
		} else if(stack.getItem() == Items.GOLDEN_PICKAXE) {
			if(stack.getDisplayName().equals("§fGolden Hammer")) {
				return 1;
			}
		} else if(stack.getItem() == Items.IRON_PICKAXE) {
			if(stack.getDisplayName().equals("§fIron Hammer")) {
				return 1;
			}
		} else if(stack.getItem() == Items.DIAMOND_PICKAXE) {
			if(stack.getDisplayName().equals("§fDiamond Hammer")) {
				return 1;
			}
		} else if(stack.getItem() == Items.WATER_BUCKET) {
			if(stack.getDisplayName().equals("§fWater (Single-Use)")) {
				return 1;
			}
		} else if(stack.getItem() == Items.LAVA_BUCKET) {
			if(stack.getDisplayName().equals("§fLava (Single-Use)")) {
				return 1;
			}
		}
		return -1F;
	}
	
}