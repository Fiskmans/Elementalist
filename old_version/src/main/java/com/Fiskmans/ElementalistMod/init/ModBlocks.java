package com.Fiskmans.ElementalistMod.init;

import java.util.ArrayList;
import java.util.List;

import com.Fiskmans.ElementalistMod.blocks.Annihilator;
import com.Fiskmans.ElementalistMod.blocks.BlockBase;
import com.Fiskmans.ElementalistMod.blocks.BlockComparer;
import com.Fiskmans.ElementalistMod.blocks.CondenserBlock;
import com.Fiskmans.ElementalistMod.blocks.FlowingElementBase;
import com.Fiskmans.ElementalistMod.blocks.PumpBlock;
import com.Fiskmans.ElementalistMod.blocks.SolidifierBlock;
import com.Fiskmans.ElementalistMod.blocks.FlowingEnder;
import com.Fiskmans.ElementalistMod.blocks.FlowingNature;
import com.Fiskmans.ElementalistMod.blocks.solidEnderBlock;
import com.Fiskmans.ElementalistMod.blocks.solidNatureBlock;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModBlocks 
{
	public static final List<Block> BLOCKS = new ArrayList<Block>();

	public static final Block SOLID_ENDER_BLOCK = new solidEnderBlock("solid_ender_block",Material.ROCK);
	public static final Block SOLID_NATURE_BLOCK = new solidNatureBlock("solid_nature_block",Material.ROCK);
	public static final Block FACE_BLOCK_MANS = new BlockBase("face_block_mans",Material.ROCK);
	public static final Block FACE_BLOCK_MATILDA = new BlockBase("face_block_matilda",Material.ROCK);
	public static final Block FLOWING_ENDER = new FlowingEnder("flowing_ender",Material.ROCK);
	public static final Block FLOWING_NATURE = new FlowingNature("flowing_nature",Material.ROCK);
	public static final Block PUMP_BLOCK = new PumpBlock("pump_block",Material.ROCK);
	public static final Block CONDENSER_BLOCK = new CondenserBlock("condenser_block",Material.ROCK);
	public static final Block SOLIDIFIER_BLOCK = new SolidifierBlock("solidifier_block",Material.ROCK);
	public static final Block BLOCK_COMPARER_BLOCK = new BlockComparer("block_comparer_block",Material.ROCK);
	public static final Block ANNIHILATOR_BLOCK = new Annihilator("annihilator_block",Material.ROCK);
	
	public static void GatherBlocks() 
	{
		BLOCKS.add(SOLID_ENDER_BLOCK);
		BLOCKS.add(SOLID_NATURE_BLOCK);
		BLOCKS.add(FACE_BLOCK_MANS);
		BLOCKS.add(FACE_BLOCK_MATILDA);
		BLOCKS.add(FLOWING_ENDER);
		BLOCKS.add(FLOWING_NATURE);
		BLOCKS.add(PUMP_BLOCK);
		BLOCKS.add(CONDENSER_BLOCK);
		BLOCKS.add(SOLIDIFIER_BLOCK);
		BLOCKS.add(BLOCK_COMPARER_BLOCK);
		BLOCKS.add(ANNIHILATOR_BLOCK);
	}
	
	public static void GatherItems() 
	{
		//current model issue for FLOWING_ENDER and FLOWING_NATURE i'll deal with it later
		ModItems.ITEMS.add(new ItemBlock(SOLID_ENDER_BLOCK).setRegistryName(SOLID_ENDER_BLOCK.getRegistryName()));
		ModItems.ITEMS.add(new ItemBlock(SOLID_NATURE_BLOCK).setRegistryName(SOLID_NATURE_BLOCK.getRegistryName()));
		ModItems.ITEMS.add(new ItemBlock(FACE_BLOCK_MANS).setRegistryName(FACE_BLOCK_MANS.getRegistryName()));
		ModItems.ITEMS.add(new ItemBlock(FACE_BLOCK_MATILDA).setRegistryName(FLOWING_ENDER.getRegistryName()));
		/*
		ModItems.ITEMS.add(new ItemBlock(FLOWING_ENDER).setRegistryName(FLOWING_ENDER.getRegistryName()));
		ModItems.ITEMS.add(new ItemBlock(FLOWING_NATURE).setRegistryName(FLOWING_NATURE.getRegistryName()));
		*/
		ModItems.ITEMS.add(new ItemBlock(PUMP_BLOCK).setRegistryName(PUMP_BLOCK.getRegistryName()));
		ModItems.ITEMS.add(new ItemBlock(CONDENSER_BLOCK).setRegistryName(CONDENSER_BLOCK.getRegistryName()));
		ModItems.ITEMS.add(new ItemBlock(SOLIDIFIER_BLOCK).setRegistryName(SOLIDIFIER_BLOCK.getRegistryName()));
		ModItems.ITEMS.add(new ItemBlock(BLOCK_COMPARER_BLOCK).setRegistryName(BLOCK_COMPARER_BLOCK.getRegistryName()));
		ModItems.ITEMS.add(new ItemBlock(ANNIHILATOR_BLOCK).setRegistryName(ANNIHILATOR_BLOCK.getRegistryName()));

	}
	
	@SideOnly(Side.CLIENT)
	public static void createStateMappers() 
	{
		ModelLoader.setCustomStateMapper(FLOWING_ENDER, (new StateMap.Builder()).build());
		ModelLoader.setCustomStateMapper(FLOWING_NATURE, (new StateMap.Builder()).build());
	}
}
