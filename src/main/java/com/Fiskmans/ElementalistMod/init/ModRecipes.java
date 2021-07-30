package com.Fiskmans.ElementalistMod.init;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModRecipes 
{
	public static void init() 
	{
		GameRegistry.addSmelting(Items.ENDER_PEARL, new ItemStack(ModItems.SOLID_ENDER,1), 3f);
		GameRegistry.addSmelting(Items.ENDER_EYE, new ItemStack(ModItems.SOLID_ENDER,2), 6f);
		
	}
}
