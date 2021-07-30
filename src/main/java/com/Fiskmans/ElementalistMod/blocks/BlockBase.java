package com.Fiskmans.ElementalistMod.blocks;

import com.Fiskmans.ElementalistMod.Main;
import com.Fiskmans.ElementalistMod.init.ModBlocks;
import com.Fiskmans.ElementalistMod.init.ModItems;
import com.Fiskmans.ElementalistMod.util.IHasModel;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class BlockBase extends Block implements IHasModel
{
	public BlockBase(String name,Material material) 
	{
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(CreativeTabs.BUILDING_BLOCKS);		
	}

	@Override
	public void registerModels() 
	{
		Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}
	
}
