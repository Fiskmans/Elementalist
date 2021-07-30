package com.Fiskmans.ElementalistMod.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.Fiskmans.ElementalistMod.Chunk.ChunkData;
import com.Fiskmans.ElementalistMod.init.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class SolidifierBlock extends BlockBase
{
	
	public SolidifierBlock(String name, Material material) {
		super(name, material);
	}
	
	@Override
	public int tickRate(World worldIn) {
		return 20;
	}
	
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
		if(ModBlocks.SOLIDIFIER_BLOCK == state.getBlock() && !worldIn.isUpdateScheduled(pos, ModBlocks.SOLIDIFIER_BLOCK)) 
    	{
    		worldIn.scheduleUpdate(pos, ModBlocks.SOLIDIFIER_BLOCK, this.tickRate(worldIn));
    	}
		materialize(worldIn, pos);
    }
	
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
		worldIn.scheduleUpdate(pos, ModBlocks.SOLIDIFIER_BLOCK, this.tickRate(worldIn));
    }
	
	public void materialize(World worldIn,BlockPos pos)
	{
		if(!worldIn.isRemote)
		{
			if(!ChunkData.IsEmpty(pos))
			{
				if(worldIn.isAirBlock(pos.up()))
				{
					worldIn.setBlockState(pos.up(), ChunkData.Get(pos));
					ChunkData.Pop(pos);
				}
			}
		}
	}

	@Override
	public boolean requiresUpdates() {
		return true;
	}
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) 
	{
		if(!worldIn.isUpdateScheduled(pos, ModBlocks.SOLIDIFIER_BLOCK)) 
    	{
    		worldIn.scheduleUpdate(pos, ModBlocks.SOLIDIFIER_BLOCK, this.tickRate(worldIn));
    	}
	}

}
