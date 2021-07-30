package com.Fiskmans.ElementalistMod.blocks;

import java.util.Random;

import com.Fiskmans.ElementalistMod.TileEntity.FlowingElement;
import com.Fiskmans.ElementalistMod.blocks.ElementBlockParent.elementType;
import com.Fiskmans.ElementalistMod.init.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class ElementBlockParent extends BlockBase
{
	public static final int range = 12;
	public enum elementType {NATURE,ENDER,NORMAL,NONE};
	
	
	
	static public elementType UnpackElement(short aValue)
	{
		switch(aValue)
		{
		case 1:
			return elementType.NATURE;
		case 2:
			return elementType.ENDER;
		default:
			return elementType.NORMAL;
		}
	}
	
	static public short PackElement(elementType aType)
	{
		switch(aType)
		{
		case NATURE:
			return 1;
		case ENDER:
			return 2;
		default:
			return 0;
		}
	}
	
    public ElementBlockParent(String name, Material material) {
		super(name, material);
		setSoundType(SoundType.METAL);
		setHardness(3.0f);
		setResistance(80.0f);
		setHarvestLevel("pickaxe",1);
		setLightLevel(4f);
	}
    
    public void standardDissolve(World worldIn, BlockPos pos,elementType sourceElement) 
    {
    	for(int depth = 1; depth < range; depth++)
		{
			for(int y = -depth-1;y < depth+1;y++)
			{
				for(int x = -depth-1;x < depth+1;x++)
				{
					if(Math.abs(x) + Math.abs(y) < depth)
					{
	    		    	if(tryDissolve(worldIn,pos.add(x, y, depth-(Math.abs(x) + Math.abs(y))),sourceElement)) {return;}
	    		    	if(tryDissolve(worldIn,pos.add(x, y, -depth+(Math.abs(x) + Math.abs(y))),sourceElement)) {return;}
					}
					else if(Math.abs(x) + Math.abs(y) == depth)
					{
	    		    	if(tryDissolve(worldIn,pos.add(x, y, 0),sourceElement)) {return;}
					}
				}
			}
		}
    }

    public void standardAttract(World worldIn, BlockPos pos,elementType sourceElement)
    {
    	for(int depth = 1; depth < range-4; depth++)
		{
			for(int y = -depth-1;y < depth+1;y++)
			{
				for(int x = -depth-1;x < depth+1;x++)
				{
					if(Math.abs(x) + Math.abs(y) < depth)
					{
	    		    	attract(worldIn,pos,pos.add(x, y, depth-(Math.abs(x) + Math.abs(y))),sourceElement);
	    		    	attract(worldIn,pos,pos.add(x, y, -depth+(Math.abs(x) + Math.abs(y))),sourceElement);
					}
					else if(Math.abs(x) + Math.abs(y) == depth)
					{
	    		    	attract(worldIn,pos,pos.add(x, y, 0),sourceElement);
					}
				}
			}
		}
    }
    
    public void attract(World worldIn, BlockPos pos,BlockPos target,elementType sourceElement)
    {
    	//System.out.println("attracting");
    	IBlockState targetstate = worldIn.getBlockState(target);
    	if(targetstate.getBlock() instanceof FlowingElementBase)
    	{
    		BlockPos delta = pos.subtract(target);
    		if(Math.abs(delta.getX()) > Math.abs(delta.getZ()))
    		{
    			//x biggest
    			delta = new BlockPos(Compare(delta.getX(),0), 0, 0);
    		}
    		else
    		{
    			// z biggest
    			delta = new BlockPos(0, 0, Compare(delta.getZ(),0));
    		}

			TileEntity tile = worldIn.getTileEntity(target);
			if(tile instanceof FlowingElement)
			{
	    		//System.out.println(delta.getX() + ":" + delta.getY() + ":" + delta.getZ());
	        	FlowingElementBase.tryFlowInto(worldIn, target, target.add(delta), FlowingElementBase.getPowerOfTile((FlowingElement)tile), FlowingElement.maxPower);
	    	}
    	}
    }
    
   	private int Compare(int x, int i) 
   	{
   		if(x > i)
   		{
   			return 1;
   		}
   		else if (x < i)
   		{
   			return -1;
   		}
		return 0;
	}

	public boolean tryDissolve(World worldIn, BlockPos pos,elementType sourceElement) 
    {
    	if(containsElement(worldIn.getBlockState(pos).getBlock(),InvertedElement(sourceElement))) 
    	{
    		FlowingElementBase.AddBlockElement(worldIn, pos, InvertedElement(sourceElement), 30);
    		worldIn.scheduleUpdate(pos, stateFromElement(InvertedElement(sourceElement)).getBlock(), 5);
    		return true;
    	}
    	return false;
    }
	
	public IBlockState stateFromElement(elementType element) 
	{
		switch(element) {
		case NATURE:
			return ModBlocks.FLOWING_NATURE.getDefaultState();
		case ENDER:
			return ModBlocks.FLOWING_ENDER.getDefaultState();
		case NONE:
			break;
		case NORMAL:
			break;
		default:
			break;
		}
		return Blocks.AIR.getDefaultState();
	}
	
    public static elementType InvertedElement(elementType element) 
    {
    	switch(element) 
    	{
		case ENDER:
			return elementType.NATURE;
		case NATURE:
			return elementType.ENDER;
		case NONE:
	    	return elementType.NONE;
		case NORMAL:
	    	return elementType.NONE;
		default:
	    	return elementType.NONE;
    	}
    }
    
    public boolean containsElement(Block blockIn, elementType element) 
    {
    	switch(element) 
    	{
    	case NATURE:
        	return blockIn == Blocks.LEAVES || blockIn == Blocks.LEAVES2 || blockIn == Blocks.LOG || blockIn == Blocks.LOG2;
    	case ENDER:
    		return blockIn == Blocks.END_STONE;
		default:
			return false;
    	}
    }
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
    	
    }
    
    public int tickRate(World worldIn)
    {
        return 1;
    }
}
