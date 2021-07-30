package com.fiskmans.elementalist;

import com.fiskmans.elementalist.Blocks.TestBlock.TestBlock;
import com.google.common.collect.Sets;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ElementalistRegister {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, "elementalist");
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "elementalist");

    public static final RegistryObject<Block> sorterHopperBlock = BLOCKS.register("elementalist",() -> new TestBlock(
            Block.Properties.of(Material.METAL).
            harvestTool(ToolType.PICKAXE).
            sound(SoundType.METAL)));


}