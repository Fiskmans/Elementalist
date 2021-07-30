package com.Fiskmans.ElementalistMod.blocks;

import java.util.Collection;
import java.util.Random;

import com.Fiskmans.ElementalistMod.Chunk.ChunkData;
import com.Fiskmans.ElementalistMod.TileEntity.FlowingElement;
import com.Fiskmans.ElementalistMod.blocks.ElementBlockParent.elementType;
import com.Fiskmans.ElementalistMod.init.ModBlocks;
import com.google.common.collect.ImmutableMap;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDynamicLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;

public class FlowingEnder extends FlowingElementBase
{
	public FlowingEnder(String name,Material material) {
		super(name, material);
		element = elementType.ENDER;
	}
	
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
		super.updateTick(worldIn, pos, state, rand);

		TileEntity tile = worldIn.getTileEntity(pos);
		if(tile instanceof FlowingElement)
		{
			FlowingElement flow = (FlowingElement)tile;
			if(flow.GetType() != elementType.ENDER)
			{
				return;
			}
			
			flow.SetPower(flow.GetPower() - TryDissolveBlock(worldIn, flow.GetPower(), pos.offset(EnumFacing.UP),0));
			flow.SetPower(flow.GetPower() - TryDissolveBlock(worldIn, flow.GetPower(), pos.offset(EnumFacing.NORTH),10));
			flow.SetPower(flow.GetPower() - TryDissolveBlock(worldIn, flow.GetPower(), pos.offset(EnumFacing.WEST),10));
			flow.SetPower(flow.GetPower() - TryDissolveBlock(worldIn, flow.GetPower(), pos.offset(EnumFacing.SOUTH),10));
			flow.SetPower(flow.GetPower() - TryDissolveBlock(worldIn, flow.GetPower(), pos.offset(EnumFacing.EAST),10));
			flow.SetPower(flow.GetPower() - TryDissolveBlock(worldIn, flow.GetPower(), pos.offset(EnumFacing.DOWN),90));
			if(flow.GetPower() < 1)
			{
				worldIn.removeTileEntity(pos);
				worldIn.setBlockToAir(pos);
			}
		}
    }
    
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
    	updateLevel(worldIn,pos);
		worldIn.scheduleUpdate(pos, ModBlocks.FLOWING_ENDER, this.tickRate(worldIn));
    }
	
	public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state)
    {
		worldIn.removeTileEntity(pos);
		//System.out.println(state.getValue(POWER));
    }
	
	public int tickRate(World worldIn) 
    {
    	return _tickRate;
    }
    
    
    
    public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random)
    {
    	if(!worldIn.isUpdateScheduled(pos, ModBlocks.FLOWING_ENDER))
    	{
    		worldIn.scheduleUpdate(pos, ModBlocks.FLOWING_ENDER, tickRate(worldIn));
    	}
    }

    public int TryDissolveBlock(World worldIn,int power,BlockPos target,int directionalResistance)
    {
    	int resistance = getResistance(worldIn.getBlockState(target));
    	if( resistance + directionalResistance < power)
    	{
    		ChunkData.Add(target, worldIn.getBlockState(target));
    		worldIn.removeTileEntity(target);
    		worldIn.setBlockToAir(target);
    		return resistance;
    	}
    	
    	return 0;
    }
    
    public int getResistance(IBlockState state)
    {
    	return getResistance(state.getBlock());
    }
    
    public int getResistance(Block block)
    {
    	if(block == Blocks.DIRT || block == Blocks.GRASS || block == Blocks.GRASS_PATH)
    		return 20;
    	if(block == Blocks.STONE || block == Blocks.COBBLESTONE || block == Blocks.MOSSY_COBBLESTONE)
    		return 40;
    	
    	return FlowingElement.maxPower + 1; //immortal
    }

	@Override
	public int getPower(FlowingElement element) 
	{
		if(element.GetType() == elementType.ENDER)
		{
			return element.GetPower();
		}
		return 0;
	}

}
