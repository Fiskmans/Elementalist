package com.Fiskmans.ElementalistMod.blocks;

import java.util.Random;

import javax.vecmath.AxisAngle4d;

import com.Fiskmans.ElementalistMod.TileEntity.FlowingElement;
import com.Fiskmans.ElementalistMod.blocks.ElementBlockParent.elementType;
import com.Fiskmans.ElementalistMod.init.ModBlocks;
import com.Fiskmans.ElementalistMod.init.ModItems;
import com.Fiskmans.ElementalistMod.items.ItemBase;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDynamicLiquid;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class FlowingElementBase extends BlockBase implements ITileEntityProvider
{    
	public static final int _tickRate = 3;
	public static final PropertyInteger LEVEL = PropertyInteger.create("level", 0, 15);
	public elementType element = elementType.NONE;
	public int DropChance = 50;
	public int DissapationRate = 50;

	public FlowingElementBase(String name,Material material) 
	{
		super(name, material);
	}

	public abstract int getPower(FlowingElement element);

	public static int getPowerOfTile(FlowingElement tile)
	{
		Block block = blockFromElement(((FlowingElement)tile).ElementType);
		
		if(block instanceof FlowingElementBase)
		{
			return ((FlowingElementBase)block).getPower((FlowingElement)tile);
		}
		return 0;
	}
	
	//Logic
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
		if(!worldIn.isUpdateScheduled(pos,blockFromElement(element) ))
    	{
    		worldIn.scheduleUpdate(pos, blockFromElement(element), this.tickRate(worldIn));
    	}
    	tryFlow(worldIn, pos);
    	
    	if(rand.nextInt() % DissapationRate == 0)
    	{
        	tryDissapate(worldIn, pos, state,rand);
    	}
    }

    public void tryDissapate(World worldIn,BlockPos pos,IBlockState state, Random rand) 
    {
    	if(worldIn.isAirBlock(pos.up()) && !worldIn.isAirBlock(pos.down()))
    	{
    		TileEntity tile = worldIn.getTileEntity(pos);
    		if(tile instanceof FlowingElement)
    		{
    			FlowingElement flow = (FlowingElement)tile;
    			int power = flow.GetPower();
    			if(power > 1)
    			{
    				flow.SetPower(power-1);
    			}
    			else
    			{
    				worldIn.removeTileEntity(pos);
    				worldIn.setBlockToAir(pos);
    			}
    			
    			if(rand.nextInt() % DropChance == 0)
    			{
    				System.out.println("Spawned item");
    				worldIn.spawnEntity(new EntityItem(worldIn,pos.getX(),pos.getY(),pos.getZ(),new ItemStack(ItemBase.ItemFromElement(flow.ElementType))));
    			}
    		}
    	}
    }
    
	public static void updateLevel(World worldIn,BlockPos pos) 
	{
		TileEntity tile = worldIn.getTileEntity(pos);
		if(tile instanceof FlowingElement)
		{
			FlowingElement flow = (FlowingElement)tile;
			int level = (flow.GetPower()*15)/flow.maxPower;
			if(level > 15) {level = 15;}
			if(level < 0) {level = 0;}
			worldIn.notifyBlockUpdate(pos,worldIn.getBlockState(pos) ,worldIn.getBlockState(pos) , 2);
			worldIn.setBlockState(pos, worldIn.getBlockState(pos).withProperty(LEVEL,level));
		}
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) 
	{
		worldIn.removeTileEntity(pos);
	}
	
	public boolean tryFlow(World worldIn,BlockPos pos) 
    {
		if(!worldIn.isRemote)
		{
	    	if(worldIn.getBlockState(pos).getBlock() instanceof FlowingElementBase)
	    	{
	    		TileEntity tile = worldIn.getTileEntity(pos);
	    		if(tile instanceof FlowingElement)
	    		{
	    			
	        		int power = ((FlowingElement) tile).GetPower();
	        		int startpower = power;
	        		if(power == 0)
	        		{
	        			worldIn.removeTileEntity(pos);
	        			worldIn.setBlockToAir(pos);
	        		}
	        		if(pos.getY() > 0)
	        		{
	        			power -= tryFlowInto(worldIn, pos, pos.down(), power, FlowingElement.maxPower);
	        		}
	        		else
	        		{
	        			worldIn.setBlockToAir(pos);
	        			power = 0;
	        		}
	        		power -= tryFlowInto(worldIn, pos, pos.north(), power, 5);
	        		power -= tryFlowInto(worldIn, pos, pos.east(), power, 5);
	        		power -= tryFlowInto(worldIn, pos, pos.south(), power, 5);
	        		power -= tryFlowInto(worldIn, pos, pos.west(), power, 5);
	        		if(power != startpower) 
	        		{
	        			worldIn.notifyNeighborsOfStateChange(pos, ModBlocks.FLOWING_ENDER, false);
	        			return true;
	        		}
	    		}
	    	}
		}
    	
    	return false;
    }
	
	public static int tryFlowInto(World worldIn,BlockPos pos,BlockPos target,int power,int force)
    {

    	if (power > 0)
    	{
	    	IBlockState targetState = worldIn.getBlockState(target);
			if(canFlowInto(targetState.getBlock(),elementFromState(worldIn.getBlockState(pos)))) 
			{
				TileEntity tile = worldIn.getTileEntity(pos);
				if(tile instanceof FlowingElement)
				{
					int targetPower = 0;
					TileEntity targetTile = worldIn.getTileEntity(target);
					if(targetTile instanceof FlowingElement)
					{
						targetPower = ((FlowingElement)targetTile).GetPower();
					}
					
		    		if(force == FlowingElement.maxPower) 
		    		{
		    			if(targetPower < FlowingElement.maxPower)
			    		{
			    			Flow(worldIn,pos,target,Math.min(power,FlowingElement.maxPower - targetPower));
			        		return Math.min(power,FlowingElement.maxPower - targetPower);
			    		}
			    		else
			    		{
			    			return 0;
			    		}
		    		}
		    		if(power > targetPower-force) 
		    		{
		    			Flow(worldIn,pos,target,(power-targetPower)/8);
		    			return (power-targetPower)/8;
		    		}
				}
			}
    	}
    	
    	return 0;
    }
	
	public static boolean canFlowInto(Block blockIn,elementType element) 
    {
    	//Common
    	if(blockIn == Blocks.AIR)
    		return true;
    	if(blockIn == Blocks.TALLGRASS)
    		return true;
    	if(blockIn == Blocks.WATER)
    		return true;
    	if(blockIn == Blocks.FLOWING_WATER)
    		return true;
    	if(blockIn == Blocks.LAVA)
    		return true;
    	if(blockIn == Blocks.FLOWING_LAVA)
    		return true;
    	
    	//Specific
    	if(blockIn == ModBlocks.FLOWING_ENDER && element == elementType.ENDER)
    		return true;
    	if(blockIn == ModBlocks.FLOWING_NATURE && element == elementType.NATURE)
    		return true;
    	
    	return false;
    }
	
	public static int AddBlockElement(World worldIn,BlockPos pos,elementType type, int Power)
	{
		int delta = Power;
		IBlockState bs = worldIn.getBlockState(pos);
		if(bs.getBlock() == blockFromElement(type))
		{
			TileEntity tile = worldIn.getTileEntity(pos);
			if(tile instanceof FlowingElement)
			{
				FlowingElement flow = (FlowingElement)tile;
				if(flow.GetType() == type)
				{
					if(flow.GetPower() + Power > flow.maxPower)
					{
						delta = flow.maxPower - flow.GetPower();
						Power = flow.maxPower;
					}
				}
				else
				{
					return 0;
				}
			}
		}
		MakeBlockElement(worldIn,pos,type,Power);
		return delta;
	}
	
	
	public static void print(String message, EntityPlayer player)
	{
		if(player != null)
		{
			player.sendMessage(new TextComponentString(message));
		}
		System.out.print(message);
	}
	
	public static void MakeBlockElement(World worldIn,BlockPos pos,elementType type, int Power)
	{
		IBlockState bs = worldIn.getBlockState(pos);
		
		if(!(bs.getBlock() instanceof FlowingElementBase))
		{
	    	worldIn.setBlockState(pos,blockFromElement(type).getDefaultState());
		}

		TileEntity tile = worldIn.getTileEntity(pos);
		if(tile instanceof FlowingElement)
		{
			FlowingElement flow = (FlowingElement)tile;
			
			flow.ElementType = type;
			flow.SetPower(flow.GetPower() + Power);
		}
		worldIn.notifyBlockUpdate(pos, blockFromElement(type).getDefaultState(), blockFromElement(type).getDefaultState(), 2);
	}
	
	
	
	public static void Flow(World worldIn,BlockPos pos,BlockPos Target,int delta) 
    {
    	//System.out.println(pos.getX() + ":" + pos.getY() + ":" + pos.getZ() + "_" + worldIn.getBlockState(pos).getBlock().toString() + ":" + getPower(worldIn.getBlockState(pos)) +  " flowing into " + Target.getX() + ":" + Target.getY() + ":" + Target.getZ() + "_" + worldIn.getBlockState(Target).getBlock().toString() + ":" + getPower(worldIn.getBlockState(Target)) + " delta:" + delta);
    	IBlockState state = worldIn.getBlockState(pos);
		elementType element = elementFromState(state);
		if(element != elementType.NONE && delta > 0)
    	{
			TileEntity tile = worldIn.getTileEntity(pos);
			if(tile instanceof FlowingElement)
			{
				FlowingElement flow = (FlowingElement)tile;
		    	if(flow.GetPower() > delta)
		    	{

		    		flow.SetPower(flow.GetPower() - delta);
			    	updateLevel(worldIn, pos);
			    	worldIn.notifyNeighborsOfStateChange(pos, blockFromElement(element), false);
		    	}
		    	else
		    	{
		    		worldIn.removeTileEntity(pos);
	    			worldIn.setBlockToAir(pos);
	    	    	worldIn.notifyNeighborsOfStateChange(pos, Blocks.AIR, false);
		    	}
	    		AddBlockElement(worldIn,Target,element,delta);
		    	updateLevel(worldIn, Target);
		    	worldIn.scheduleUpdate(Target, blockFromElement(element), blockFromElement(element).tickRate(worldIn));
			}
    	}
    }
	
	public static elementType elementFromState(IBlockState state)
	{
		return elementFromBlock(state.getBlock());
	}
	
	public static elementType elementFromBlock(Block block)
	{
		if(block == ModBlocks.FLOWING_ENDER)
		{
			return elementType.ENDER;
		}
		if(block == ModBlocks.FLOWING_NATURE)
		{
			return elementType.NATURE;
		}
		
		return elementType.NONE;
	}
	
	public static Block blockFromElement(elementType element)
	{
		if(element != null)
		{
			switch(element)
			{
			case ENDER:
				return ModBlocks.FLOWING_ENDER;
			case NATURE:
				return ModBlocks.FLOWING_NATURE;
			case NONE:
				break;
			case NORMAL:
				break;
			default:
				break;
			}
		}
		return Blocks.AIR;
	}
	
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
    	worldIn.scheduleUpdate(pos, state.getBlock(), tickRate(worldIn));
    }
    
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
    		EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) 
    {
    	if(worldIn.isRemote)
    	{
    		TileEntity tile = worldIn.getTileEntity(pos);
    		if(tile instanceof FlowingElement)
    		{
    			FlowingElement flow = (FlowingElement)tile;
    			
    			print("type: " + flow.ElementType + " power: " + flow.GetPower() + " id: " + flow.UniqueId,playerIn);
    		}
    		else
    		{
    			print("No tile entity, this block is broken",playerIn);
    		}
    		int count = 0;
    		for(int i = 0;i< worldIn.loadedTileEntityList.size();i++)
    		{
    			if(worldIn.loadedTileEntityList.get(i) instanceof FlowingElement)
    			{
    				count++;
    			}
    		}
			print("there are " + count + " loaded flowing element tilentities loaded",playerIn);
    		
    	}
    	return true ;
    }
	
	
    
    public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos)
    {
        return true;
    }
    
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(LEVEL);
    }

    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(LEVEL, meta);
    }

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		TileEntity tileEntity = source.getTileEntity(pos);
		if(tileEntity instanceof FlowingElement)
		{
			FlowingElement element = (FlowingElement)tileEntity;
			return element.getBoundingBox();
		}
    	return new AxisAlignedBB(0,0,0,1,1,1);
    }
	
	protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {LEVEL} );
    }
	
    //rendering
    public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face)
    {
    	if(face == EnumFacing.DOWN) { return true; }
        return state.getValue(LEVEL) == 15;
    }
    
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }
    
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

	@Override
	public boolean hasTileEntity() {
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) 
	{
		return new FlowingElement();
	}
    
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) 
	{
		return new FlowingElement();
	}
}
