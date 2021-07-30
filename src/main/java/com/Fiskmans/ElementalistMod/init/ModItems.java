package com.Fiskmans.ElementalistMod.init;

import java.util.ArrayList;
import java.util.List;

import com.Fiskmans.ElementalistMod.items.ItemBase;

import net.minecraft.item.Item;

public class ModItems 
{
	public static final List<Item> ITEMS = new ArrayList<Item>();
	
	public static final Item SOLID_ENDER = new ItemBase("solid_ender");
	public static final Item SOLID_NATURE = new ItemBase("solid_nature");
	
	public static void GatherItems() 
	{
		ITEMS.add(SOLID_ENDER);
		ITEMS.add(SOLID_NATURE);
	}
}
