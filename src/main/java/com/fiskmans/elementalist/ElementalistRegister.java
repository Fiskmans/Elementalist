package com.fiskmans.elementalist;

import com.fiskmans.elementalist.Blocks.BlockEntity.CrystalizedEnderBlockEntity;
import com.fiskmans.elementalist.Blocks.BlockEntity.CrystalizedNatureBlockEntity;
import com.fiskmans.elementalist.Blocks.BlockEntity.ElementalBlockEntity;
import com.fiskmans.elementalist.Blocks.Blocks.Condenser;
import com.fiskmans.elementalist.Blocks.Blocks.ElementalBlock;
import com.fiskmans.elementalist.Blocks.Blocks.ElementalPump;
import com.fiskmans.elementalist.Blocks.Blocks.TestBlock;

import com.fiskmans.elementalist.Blocks.ElementType;
import com.google.common.collect.Sets;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ElementalistRegister {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, "elementalist");
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, "elementalist");
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "elementalist");

    public static final RegistryObject<Block> TEST_BLOCK = BLOCKS.register("test_block",() -> new TestBlock());
    public static final RegistryObject<Block> ELEMENTAL_PUMP_BLOCK = BLOCKS.register("elemental_pump",() -> new ElementalPump());
    public static final RegistryObject<Block> CONDENSER_BLOCK = BLOCKS.register("condenser",() -> new Condenser());

    public static final RegistryObject<Block> CRYSTALIZED_NATURE_BLOCK = BLOCKS.register("crystalized_nature",() -> new ElementalBlock(ElementType.NATURE));
    public static final RegistryObject<Block> CRYSTALIZED_ENDER_BLOCK = BLOCKS.register("crystalized_ender",() -> new ElementalBlock(ElementType.ENDER));

    public static final RegistryObject<BlockEntityType<CrystalizedEnderBlockEntity>> CRYSTALIZED_ENDER_BLOCK_ENTITY = BLOCK_ENTITY.register("crystalized_ender",() -> new BlockEntityType<CrystalizedEnderBlockEntity>(CrystalizedEnderBlockEntity::new, Sets.newHashSet(CRYSTALIZED_ENDER_BLOCK.get()), null));
    public static final RegistryObject<BlockEntityType<CrystalizedNatureBlockEntity>> CRYSTALIZED_NATURE_BLOCK_ENTITY = BLOCK_ENTITY.register("crystalized_nature",() -> new BlockEntityType<CrystalizedNatureBlockEntity>(CrystalizedNatureBlockEntity::new, Sets.newHashSet(CRYSTALIZED_NATURE_BLOCK.get()), null));

    public static final RegistryObject<BlockItem> TEST_BLOCK_ITEM = ITEMS.register("test_block", () -> new BlockItem(TEST_BLOCK.get(), new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE)));
    public static final RegistryObject<BlockItem> ELEMENTAL_PUMP_ITEM = ITEMS.register("elemental_pump", () -> new BlockItem(ELEMENTAL_PUMP_BLOCK.get(), new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE)));
    public static final RegistryObject<BlockItem> CONDENSER_ITEM = ITEMS.register("condenser", () -> new BlockItem(CONDENSER_BLOCK.get(), new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE)));

    public static final RegistryObject<BlockItem> CRYSTALIZED_NATURE_ITEM = ITEMS.register("crystalized_nature", () -> new BlockItem(CRYSTALIZED_NATURE_BLOCK.get(), new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE)));
    public static final RegistryObject<BlockItem> CRYSTALIZED_ENDER_ITEM = ITEMS.register("crystalized_ender", () -> new BlockItem(CRYSTALIZED_ENDER_BLOCK.get(), new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE)));


}