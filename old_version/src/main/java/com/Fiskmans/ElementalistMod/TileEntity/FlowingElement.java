package com.Fiskmans.ElementalistMod.TileEntity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.Fiskmans.ElementalistMod.blocks.ElementBlockParent;
import com.Fiskmans.ElementalistMod.blocks.ElementBlockParent.*;

public class FlowingElement extends TileEntity
{
	static int OpenId = 0;
	public int UniqueId = OpenId++;
	
	static public int maxPower = 255;

	int ElementalPower;
	public elementType ElementType;
	
	
	
	public FlowingElement()
	{
		ElementalPower = 0;
		ElementType = elementType.NORMAL;
	}
	
	
	public elementType GetType()
	{
		return ElementType;
	}
	
	public int GetPower()
	{
		return ElementalPower;
	}
	
	public void SetPower(int aPower)
	{
		ElementalPower = aPower;
	}
	
	
	
	void AddToNBT(NBTTagCompound compound)
	{
		compound.setInteger("ElementalPower", ElementalPower);
		compound.setShort("ElementalType",ElementBlockParent.PackElement(ElementType));
	}
	
	void GetFromNTB(NBTTagCompound compound)
	{
		this.ElementalPower = compound.getInteger("ElementalPower");
		this.ElementType = ElementBlockParent.UnpackElement(compound.getShort("ElementalType"));
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) 
	{
		super.writeToNBT(compound);
		AddToNBT(compound);
		return compound;
	}
	@Override
	public void readFromNBT(NBTTagCompound compound) 
	{
		super.readFromNBT(compound);
		GetFromNTB(compound);
	}
	
	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) 
	{
		return oldState.getBlock() != newSate.getBlock();
	}
	public AxisAlignedBB getBoundingBox()
	{
		return new AxisAlignedBB(0,0,0,1,(float)(ElementalPower+1)/(float)(maxPower+1),1);
	}
	
	@Override
	public SPacketUpdateTileEntity getUpdatePacket(){
	    NBTTagCompound nbtTag = new NBTTagCompound();
	    AddToNBT(nbtTag);
	    return new SPacketUpdateTileEntity(getPos(), 1, nbtTag);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt){
	    NBTTagCompound tag = pkt.getNbtCompound();
	    GetFromNTB(tag);
	}
}
