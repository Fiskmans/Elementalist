package com.fiskmans.elementalist;

import com.fiskmans.elementalist.Blocks.Blocks.ElementalPump;
import com.fiskmans.elementalist.Blocks.Blocks.TestBlock;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ElementalistRegister {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, "elementalist");
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "elementalist");

    public static final RegistryObject<Block> TEST_BLOCK = BLOCKS.register("test_block",() -> new TestBlock());
    public static final RegistryObject<Block> ELEMENTAL_PUMP_BLOCK = BLOCKS.register("elemental_pump",() -> new ElementalPump());

    public static final RegistryObject<BlockItem> TEST_BLOCK_ITEM = ITEMS.register("test_block", () -> new BlockItem(TEST_BLOCK.get(), new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE)));
    public static final RegistryObject<BlockItem> ELEMENTAL_PUMP_ITEM = ITEMS.register("elemental_pump", () -> new BlockItem(ELEMENTAL_PUMP_BLOCK.get(), new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE)));


}