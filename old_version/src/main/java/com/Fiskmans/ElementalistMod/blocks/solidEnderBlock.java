package com.Fiskmans.ElementalistMod.blocks;

import java.io.Console;
import java.util.Random;

import com.Fiskmans.ElementalistMod.blocks.ElementBlockParent.elementType;
import com.Fiskmans.ElementalistMod.init.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentBase;
import net.minecraft.world.World;

public class solidEnderBlock extends ElementBlockParent
{
	public solidEnderBlock(String name, Material material) 
	{
		super(name, material);
	}

    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
    	if(ModBlocks.SOLID_ENDER_BLOCK == state.getBlock() && !worldIn.isUpdateScheduled(pos, ModBlocks.SOLID_ENDER_BLOCK)) 
    	{
    		worldIn.scheduleUpdate(pos, ModBlocks.SOLID_ENDER_BLOCK, this.tickRate(worldIn));
    	}
    	standardDissolve(worldIn, pos, elementType.ENDER);
    	standardAttract(worldIn, pos, elementType.ENDER);
    }
    
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
		worldIn.scheduleUpdate(pos, ModBlocks.SOLID_ENDER_BLOCK, this.tickRate(worldIn));
    }
    
    public int tickRate(World worldIn) 
    {
    	return 10;
    }
    
}
