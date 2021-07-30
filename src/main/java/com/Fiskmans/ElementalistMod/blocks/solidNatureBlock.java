package com.Fiskmans.ElementalistMod.blocks;

import java.io.Console;
import java.util.Random;

import com.Fiskmans.ElementalistMod.blocks.ElementBlockParent.elementType;
import com.Fiskmans.ElementalistMod.init.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentBase;
import net.minecraft.world.World;

public class solidNatureBlock extends ElementBlockParent
{

	public solidNatureBlock(String name, Material material) 
	{
		super(name, material);
	}
	

    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
    	if(ModBlocks.SOLID_NATURE_BLOCK == state.getBlock() && !worldIn.isUpdateScheduled(pos, ModBlocks.SOLID_NATURE_BLOCK)) 
    	{
    		worldIn.scheduleUpdate(pos, ModBlocks.SOLID_NATURE_BLOCK, this.tickRate(worldIn));
    	}
    	standardDissolve(worldIn, pos, elementType.NATURE);
    	standardAttract(worldIn, pos, elementType.NATURE);
    }
    
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
		worldIn.scheduleUpdate(pos, ModBlocks.SOLID_NATURE_BLOCK, this.tickRate(worldIn));
    }

    public int tickRate(World worldIn) 
    {
    	return 10;
    }
}
