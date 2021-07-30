package com.Fiskmans.ElementalistMod.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class Annihilator extends BlockBase
{

	public Annihilator(String name, Material material) 
	{
		super(name, material);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) 
	{
		if(!worldIn.isAirBlock(pos.up()))
		{
			worldIn.removeTileEntity(pos.up());
			worldIn.setBlockToAir(pos.up());
		}
	}
}
