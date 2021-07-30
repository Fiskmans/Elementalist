package com.Fiskmans.ElementalistMod.blocks;

import java.util.Random;

import com.Fiskmans.ElementalistMod.TileEntity.FlowingElement;
import com.Fiskmans.ElementalistMod.blocks.ElementBlockParent.elementType;
import com.Fiskmans.ElementalistMod.init.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CondenserBlock extends BlockBase
{

	public CondenserBlock(String name, Material material) {
		super(name, material);
	}
	
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
    	if(ModBlocks.CONDENSER_BLOCK == state.getBlock() && !worldIn.isUpdateScheduled(pos, ModBlocks.CONDENSER_BLOCK)) 
    	{
    		worldIn.scheduleUpdate(pos, ModBlocks.CONDENSER_BLOCK, this.tickRate(worldIn));
    	}
    	condense(worldIn,pos);
    }
	
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
		worldIn.scheduleUpdate(pos, ModBlocks.CONDENSER_BLOCK, this.tickRate(worldIn));
    }
	
	public int tickRate(World worldIn) 
    {
    	return 10;
    }
	
	// Material1 : Material2
	// ConvertionRatio : 1
	static int ConvertionRatio = 2; 
	
	public void condense(World worldIn,BlockPos pos)
	{
		if(!worldIn.getBlockState(pos.up()).isSideSolid(worldIn, pos.up(), EnumFacing.DOWN))
		{
			for(EnumFacing face : EnumFacing.HORIZONTALS)
			{
				elementType sourceElement =  FlowingElementBase.elementFromState(worldIn.getBlockState(pos.offset(face)));
				if(FlowingElementBase.canFlowInto(worldIn.getBlockState(pos.down()).getBlock(), sourceElement))
				{
					TileEntity tile = worldIn.getTileEntity(pos.offset(face));
					if(tile instanceof FlowingElement)
					{
						IBlockState source = worldIn.getBlockState(pos.offset(face));
						int sourcePower = FlowingElementBase.getPowerOfTile((FlowingElement)tile);
						if(sourcePower > ConvertionRatio)
						{
							//Haha i don't even know, good luck
							
							elementType targetType = ElementBlockParent.InvertedElement(sourceElement);
							
							int delta = FlowingElementBase.AddBlockElement(worldIn, pos.down(), targetType, sourcePower / ConvertionRatio);
							
							
							Block targetBlock = FlowingElementBase.blockFromElement(targetType);
							FlowingElementBase.updateLevel(worldIn, pos.down());
							worldIn.scheduleUpdate(pos.down(), targetBlock, targetBlock.tickRate(worldIn));
							
							sourcePower -= delta * ConvertionRatio;
							if(sourcePower > 0)
							{
								FlowingElementBase.MakeBlockElement(worldIn, pos.offset(face), sourceElement, sourcePower);
							}
							else
							{
								worldIn.removeTileEntity(pos.offset(face));
								worldIn.setBlockToAir(pos.offset(face));
							}
						}
					}
				}
			}
		}
	}
}
