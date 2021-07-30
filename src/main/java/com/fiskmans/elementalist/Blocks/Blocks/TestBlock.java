package com.fiskmans.elementalist.Blocks.Blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.ToolType;

public class TestBlock extends Block {

    public TestBlock() {
        super(GetProperties());
    }

    public static Block.Properties GetProperties() {
        return Block.Properties.of(Material.METAL).harvestTool(ToolType.PICKAXE).destroyTime(0.5f).sound(SoundType.METAL);
    }
}