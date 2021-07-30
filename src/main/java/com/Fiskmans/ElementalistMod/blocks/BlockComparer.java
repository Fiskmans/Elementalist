package com.Fiskmans.ElementalistMod.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockComparer extends BlockBase
{

	public static final PropertyInteger POWERED = PropertyInteger.create("powered", 0, 1);
	
	public BlockComparer(String string, Material materialIn) {
		super(string, materialIn);
		// TODO Auto-generated constructor stub
		
	}
	
	public boolean canProvidePower(IBlockState state)
    {
        return true;
    }

	@Override
	public int tickRate(World worldIn) {
		return 5;
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(POWERED);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		if(meta != 0 && meta != 1) {meta = 0;}
		return getDefaultState().withProperty(POWERED, meta);
	}
	
	@Override
	public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		// TODO Auto-generated method stub
		if(blockState.getValue(POWERED) == 1)
		{
			return 15;
		}
		return 0;
	}
	@Override
	public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		// TODO Auto-generated method stub
		return getStrongPower(blockState, blockAccess, pos, side);
	}
	
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) 
	{
		IBlockState top = worldIn.getBlockState(pos.up());
		IBlockState bottom = worldIn.getBlockState(pos.down());
		if(top.getBlock() == Blocks.AIR) {return;}
		if(top == bottom || (bottom.getBlock() == Blocks.DIRT && top.getBlock() == Blocks.GRASS))
		{
			if(state.getValue(POWERED) == 0)
			{
				worldIn.setBlockState(pos, getDefaultState().withProperty(POWERED, 1));
			}
		}
		else
		{
			if(state.getValue(POWERED) == 1)
			{
				worldIn.setBlockState(pos, getDefaultState().withProperty(POWERED, 0));
			}
		}
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {POWERED} );
	}
}
