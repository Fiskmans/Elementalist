package com.Fiskmans.ElementalistMod;

import com.Fiskmans.ElementalistMod.Chunk.ChunkData;
import com.Fiskmans.ElementalistMod.Commands.Dissolve;
import com.Fiskmans.ElementalistMod.TileEntity.FlowingElement;
import com.Fiskmans.ElementalistMod.blocks.FlowingElementBase;
import com.Fiskmans.ElementalistMod.init.ModRecipes;
import com.Fiskmans.ElementalistMod.proxy.CommonProxy;
import com.Fiskmans.ElementalistMod.util.Reference;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = Reference.MOD_ID,name = Reference.NAME,version = Reference.VERSION)
public class Main {

	@Instance
	public static Main instance;
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS,serverSide = Reference.COMMON_PROXY_CLASS)
	public static CommonProxy proxy;
	
	@EventHandler
	public static void PreInit(FMLPreInitializationEvent event) 
	{
		proxy.preInit();
	}
	
	@EventHandler
	public static void init(FMLInitializationEvent event) 
	{
		ModRecipes.init();
		
		GameRegistry.registerTileEntity(FlowingElement.class, Reference.MOD_ID + "FlowingElementTile");
		MinecraftForge.EVENT_BUS.register(ChunkData.class);
		
	}
	
	@EventHandler
	public static void init(FMLServerStartingEvent event) 
	{
		event.registerServerCommand(new Dissolve());
	}
	
	@EventHandler
	public static void Postinit(FMLPostInitializationEvent event) 
	{
		
	}
	
	
}
