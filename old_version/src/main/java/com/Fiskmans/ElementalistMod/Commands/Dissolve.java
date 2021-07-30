package com.Fiskmans.ElementalistMod.Commands;

import com.Fiskmans.ElementalistMod.Chunk.ChunkData;
import com.Fiskmans.ElementalistMod.blocks.SolidifierBlock;

import net.minecraft.block.state.IBlockState;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.text.TextComponentString;
import scala.swing.TextComponent;

public class Dissolve extends CommandBase{

	@Override
	public String getName() 
	{
		// TODO Auto-generated method stub
		return "dissolve";
	}
	public int getRequiredPermissionLevel()
    {
        return 2;
    }
	
	@Override
	public String getUsage(ICommandSender sender) {
		// TODO Auto-generated method stub
		return "command.dissolve.usage";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
		if(args.length < 2) 
		{ 
            throw new WrongUsageException("commands.dissolve.usage", new Object[0]);
		}
		
		IBlockState b = getBlockByText(sender, args[0]).getDefaultState();
		int count = parseInt(args[1]);
		
		BlockPos pos;
		if(args.length > 4)
		{
			pos = new BlockPos(parseInt(args[2]), 0,parseInt(args[3]));
		}
		else
		{
			pos = sender.getPosition();
		}
		
		for(int i = 0;i < count;i++)
		{
			 ChunkData.Add(pos, b);
		}
		try
		{
			EntityPlayer p = getCommandSenderAsPlayer(sender);
			p.sendMessage(new TextComponentString("Dissolved " + count + " " + b));
		}
		finally
		{
			
		}
	}

}
