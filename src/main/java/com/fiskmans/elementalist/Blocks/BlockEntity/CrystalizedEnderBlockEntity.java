package com.fiskmans.elementalist.Blocks.BlockEntity;

import com.fiskmans.elementalist.ElementType;
import com.fiskmans.elementalist.ElementalistRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Arrays;
import java.util.List;

public class CrystalizedEnderBlockEntity extends ElementalBlockEntity {

    static final List<Block> BlocksWithNature = Arrays.asList(
            Blocks.GRASS,
            Blocks.VINE,
            Blocks.AZALEA,
            Blocks.COCOA,
            Blocks.ACACIA_LEAVES,
            Blocks.ACACIA_LOG,
            Blocks.ALLIUM,
            Blocks.BAMBOO,
            Blocks.BIRCH_LEAVES,
            Blocks.BIRCH_LOG,
            Blocks.BLUE_ORCHID,
            Blocks.BROWN_MUSHROOM_BLOCK,
            Blocks.MUSHROOM_STEM,
            Blocks.CACTUS,
            Blocks.CAVE_VINES,
            Blocks.CAVE_VINES_PLANT,
            Blocks.CHORUS_PLANT,
            Blocks.OAK_LEAVES,
            Blocks.OAK_LOG,
            Blocks.AZALEA_LEAVES,
            Blocks.DARK_OAK_LEAVES,
            Blocks.DARK_OAK_LOG,
            Blocks.FLOWERING_AZALEA_LEAVES,
            Blocks.JUNGLE_LEAVES,
            Blocks.JUNGLE_LOG,
            Blocks.SPRUCE_LEAVES,
            Blocks.SPRUCE_LOG);

    public CrystalizedEnderBlockEntity(BlockPos pos, BlockState state) {
        super(ElementalistRegister.CRYSTALIZED_ENDER_BLOCK_ENTITY.get(), pos, state, ElementType.ENDER);
    }

    @Override
    boolean CanDissolve(Block aBlock) {
        return BlocksWithNature.contains(aBlock);
    }
}
