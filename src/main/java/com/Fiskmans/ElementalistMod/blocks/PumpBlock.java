package com.Fiskmans.ElementalistMod.blocks;

import java.util.Random;

import com.Fiskmans.ElementalistMod.TileEntity.FlowingElement;
import com.Fiskmans.ElementalistMod.init.ModBlocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PumpBlock extends BlockBase
{

	public PumpBlock(String name, Material material) {
		super(name, material);
	}
	
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
		if(ModBlocks.PUMP_BLOCK == state.getBlock() && !worldIn.isUpdateScheduled(pos, ModBlocks.PUMP_BLOCK)) 
    	{
    		worldIn.scheduleUpdate(pos, ModBlocks.PUMP_BLOCK, this.tickRate(worldIn));
    	}
		tryPump(worldIn, pos, state);
    }
	
	public void tryPump(World worldIn,BlockPos pos,IBlockState state) 
	{
		IBlockState bottom = worldIn.getBlockState(pos.down());
		if(bottom.getBlock() instanceof FlowingElementBase) 
		{
			TileEntity tile = worldIn.getTileEntity(pos.down());
			if(tile instanceof FlowingElement)
			{
				FlowingEnder.tryFlowInto(worldIn, pos.down(),pos.up(),FlowingElementBase.getPowerOfTile((FlowingElement)tile),FlowingElement.maxPower);
			}
		}
	}
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
		worldIn.scheduleUpdate(pos, ModBlocks.PUMP_BLOCK, this.tickRate(worldIn));
    }

}
