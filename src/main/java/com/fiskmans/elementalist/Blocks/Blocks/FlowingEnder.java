package com.fiskmans.elementalist.Blocks.Blocks;

import com.fiskmans.elementalist.ElementType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.logging.log4j.core.jmx.Server;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class FlowingEnder extends FlowingElementBase {

    static final int ticksBetweenDissolveAttempts = 5;

    static final List<Block> dissolvableBlocks = Arrays.asList(
            Blocks.GRASS_BLOCK,
            Blocks.SAND,
            Blocks.DIRT,
            Blocks.SANDSTONE,
            Blocks.STONE,
            Blocks.ANDESITE,
            Blocks.DIORITE,
            Blocks.GRANITE,
            Blocks.COAL_ORE,
            Blocks.DEEPSLATE_COAL_ORE);

    public FlowingEnder()
    {
        super(ElementType.ENDER);
    }

    @Override
    void FlowInto(ServerLevel level, int currentLevel, BlockState target, BlockPos pos, BlockPos targetPos) {

        if (target.getBlock() != Blocks.WATER)
        {
            BlockState newTargetState;
            if (target.getBlock() instanceof FlowingEnder)
            {
                newTargetState = this.defaultBlockState().setValue(LEVEL,target.getValue(LEVEL) + 1);
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

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, Random random) {
        super.tick(state, level, pos, random);
        TryDissolve(level, state, pos, random);
        if (CanDissolve(level, state, pos))
        {
            level.getBlockTicks().scheduleTick(pos, this, ticksBetweenDissolveAttempts);
        }
    }

    void TryDissolve(ServerLevel level, BlockState state, BlockPos pos, Random random)
    {
        Direction dir = Direction.values()[Math.abs(random.nextInt() % Direction.values().length)];
        BlockState target = level.getBlockState(pos.relative(dir));
        if (!dissolvableBlocks.contains(target.getBlock()))
            return;

        int chance = target.getHarvestLevel() * 50 / (state.getValue(FlowingElementBase.LEVEL) + 1) * (dir == Direction.DOWN ? 5 : 1) * (CanFlow(level,pos,state) ? 5 : 1);
        if (Math.abs(random.nextInt()) % chance == 0)
        {
            Dissolve(level, pos.relative(dir));
        }
    }

    void Dissolve(ServerLevel level, BlockPos pos)
    {
        level.removeBlock(pos,false);
    }

    boolean CanDissolve(ServerLevel level, BlockState state, BlockPos pos)
    {
        for (Direction dir : Direction.values())
        {
            if (dissolvableBlocks.contains(level.getBlockState(pos.relative(dir))))
                return true;
        }

        return true;
    }
}
