package com.fiskmans.elementalist.Blocks.Blocks;

import com.fiskmans.elementalist.ElementType;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Arrays;
import java.util.List;

public class FlowingNature extends FlowingElementBase {

    public FlowingNature()
    {
        super(ElementType.NATURE);
    }

    static final List<Block> weakNatureBlocks = Arrays.asList(
            Blocks.TALL_GRASS,
            Blocks.VINE,
            Blocks.GRASS);

    @Override
    void FlowInto(ServerLevel level, int currentLevel, BlockState target, BlockPos pos, BlockPos targetPos) {

        if (target.getBlock() != Blocks.WATER)
        {
            BlockState newTargetState;
            if (target.getBlock() instanceof FlowingNature)
            {
                newTargetState = this.defaultBlockState().setValue(LEVEL,target.getValue(LEVEL) + 1);
            }
            else if(weakNatureBlocks.contains(target.getBlock()))
            {
                newTargetState = this.defaultBlockState().setValue(LEVEL,7);
            }
            else
            {
                newTargetState = this.defaultBlockState().setValue(LEVEL,0);
            }
            level.setBlock(targetPos,newTargetState, Block.UPDATE_CLIENTS + Block.UPDATE_NEIGHBORS);
        }

        if (currentLevel == 0)
        {
            level.removeBlock(pos,false);
        }
        else
        {
            level.setBlock(pos, this.defaultBlockState().setValue(LEVEL,currentLevel - 1),Block.UPDATE_CLIENTS + Block.UPDATE_NEIGHBORS);
        }
    }
}
