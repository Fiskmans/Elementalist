package com.Fiskmans.ElementalistMod.items;

import com.Fiskmans.ElementalistMod.Main;
import com.Fiskmans.ElementalistMod.blocks.ElementBlockParent.elementType;
import com.Fiskmans.ElementalistMod.init.ModItems;
import com.Fiskmans.ElementalistMod.util.IHasModel;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class ItemBase extends Item implements IHasModel
{

	public ItemBase(String name) 
	{
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(CreativeTabs.MATERIALS);
	}
	
	@Override
	public void registerModels() 
	{
		Main.proxy.registerItemRenderer(this,0,"inventory");
	}
	
	static public Item ItemFromElement(elementType element)
	{
		switch (element) {
		case ENDER: return ModItems.SOLID_ENDER;
		case NATURE: return ModItems.SOLID_NATURE;

		default: return Items.AIR;
		}
	}
	
}
