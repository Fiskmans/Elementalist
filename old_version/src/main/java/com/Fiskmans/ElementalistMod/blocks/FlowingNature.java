package com.Fiskmans.ElementalistMod.blocks;

import com.Fiskmans.ElementalistMod.TileEntity.FlowingElement;
import com.Fiskmans.ElementalistMod.blocks.ElementBlockParent.elementType;
import com.Fiskmans.ElementalistMod.init.ModBlocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;

public class FlowingNature extends FlowingElementBase
{

	public FlowingNature(String name, Material material) 
	{
		super(name, material);
		element = elementType.NATURE;
	}
	
	public int getPower(FlowingElement element) 
    {
    	if(element.GetType() == elementType.NATURE)
    	{
    		return element.GetPower();
    	}
    	return 0;
    }
	
	public int tickRate(World worldIn) 
    {
    	return _tickRate;
    }
}
