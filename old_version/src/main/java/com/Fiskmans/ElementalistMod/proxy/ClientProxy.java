package com.Fiskmans.ElementalistMod.proxy;

import com.Fiskmans.ElementalistMod.init.ModBlocks;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public class ClientProxy extends CommonProxy
{
	public void registerItemRenderer(Item item,int meta,String id) 
	{
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(),id));
	}
	
	@Override
	public void preInit()
	{
	}
}
