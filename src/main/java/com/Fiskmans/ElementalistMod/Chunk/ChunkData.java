package com.Fiskmans.ElementalistMod.Chunk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.Fiskmans.ElementalistMod.util.Reference;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.event.terraingen.BiomeEvent.GetVillageBlockID;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ChunkData extends WorldSavedData {

	public static Map<ChunkPos,List<IBlockState>> DisolvedBlocks = new HashMap<ChunkPos,List<IBlockState>>();
	public static final String NAME = Reference.NAME + "_WorldData";
	public ChunkData(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void readFromNBT(NBTTagCompound nbt) 
	{
		// TODO Auto-generated method stub
	}
	
	public static void Add(BlockPos pos,IBlockState state)
	{
		ChunkPos key = new ChunkPos(pos);
		if(state.getBlock() == Blocks.AIR) { return; }
		Verify(key);
		ChunkData.DisolvedBlocks.get(key).add(state);
	}
	public static void Pop(BlockPos pos)
	{
		ChunkPos key = new ChunkPos(pos);
		Verify(key);
		ChunkData.DisolvedBlocks.get(key).remove(0);
	}
	public static IBlockState Get(BlockPos pos)
	{
		ChunkPos key = new ChunkPos(pos);
		Verify(key);
		while(ChunkData.DisolvedBlocks.get(key).get(0).getBlock() == Blocks.AIR)
		{
			ChunkData.DisolvedBlocks.get(key).remove(0);
		}
		return ChunkData.DisolvedBlocks.get(key).get(0);
	}
	public static boolean IsEmpty(BlockPos pos)
	{
		ChunkPos key = new ChunkPos(pos);
		Verify(key);
		return ChunkData.DisolvedBlocks.get(key).isEmpty();
	}
	
	public static void Verify(ChunkPos key)
	{
		if(!DisolvedBlocks.containsKey(key))
		{
			DisolvedBlocks.put(key, new ArrayList<IBlockState>());
			System.out.println("Added list for X: " + key.x + " Y: " + key.z);
		}
	}
	
	public static List<IBlockState> DecompressNBT(NBTTagCompound nbt)
	{
		List<IBlockState> blocks = new ArrayList<IBlockState>();
		int current = 0;
		while(nbt.hasKey(""+current))
		{
			NBTTagCompound block = nbt.getCompoundTag(""+current);
			current++;
			blocks.add(NBTUtil.readBlockState(block));
		}
		if(blocks.size() > 0)
		{
			System.out.println("unpacked " + blocks.size() + " blocks");
		}

		return blocks;
	}
	
	public static void CompressToNBT(NBTTagCompound compound,List<IBlockState> list)
	{
		for(int i = 0;i < list.size();i++)
		{
			NBTTagCompound tag = new NBTTagCompound();
			
			NBTUtil.writeBlockState(tag, list.get(i));
			compound.setTag(""+i, tag);
		}
	}
	
	@SubscribeEvent
	public static void onChunkLoad(ChunkDataEvent.Load event) 
	{
		if(!event.getWorld().isRemote)
		{
			if(!DisolvedBlocks.containsKey(event.getChunk().getPos()))
			{
				DisolvedBlocks.put(event.getChunk().getPos(),DecompressNBT(event.getData()));
			}
		}
	}
	
	@SubscribeEvent
	public static void onChunkSave(ChunkDataEvent.Save event) 
	{
		if(!event.getWorld().isRemote)
		{
			if(DisolvedBlocks.containsKey(event.getChunk().getPos()))
			{
				CompressToNBT(event.getData(), DisolvedBlocks.get(event.getChunk().getPos()));
			}
		}
	}
	
	
	@SubscribeEvent
	public static void onChunkUnload(ChunkEvent.Unload event) 
	{
		if(!event.getWorld().isRemote)
		{
			if(DisolvedBlocks.containsKey(event.getChunk().getPos()))
			{
				DisolvedBlocks.remove(event.getChunk().getPos());
			}
		}
	}
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		// TODO Auto-generated method stub
		return null;
	}
}
