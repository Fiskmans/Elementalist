package com.fiskmans.elementalist.Blocks.Blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.TickPriority;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.ToolType;

import java.util.Random;

public class ElementalPump extends Block{

    static final int TicksPerPump = 25;

    public ElementalPump() {
        super(GetProperties());
    }

    public static Block.Properties GetProperties() {
        return Block.Properties.of(Material.METAL).harvestTool(ToolType.PICKAXE).destroyTime(0.5f).sound(SoundType.METAL);
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean bool) {
        if (canPump(level, state, pos))
        {
            level.getBlockTicks().scheduleTick(pos,this,TicksPerPump, TickPriority.NORMAL);
        }
    }


    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos otherPos, boolean bool) {
        if (canPump(level, state, pos))
        {
            level.getBlockTicks().scheduleTick(pos,this,TicksPerPump, TickPriority.NORMAL);
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, Random random) {
        LOGGER.info("Ticked: " + pos.toString());
        Pump(level, state, pos);
    }

    void Pump(ServerLevel level, BlockState state,BlockPos pos)
    {
        if (!canPump(level,state,pos))
            return;

        BlockState below = level.getBlockState(pos.below());
        ((FlowingElementBase)below.getBlock()).FlowInto(level, below.getValue(FlowingElementBase.LEVEL), level.getBlockState(pos.above()),pos.below(), pos.above());

        level.getBlockTicks().scheduleTick(pos,this,TicksPerPump, TickPriority.NORMAL);
    }

    boolean canPump(Level level, BlockState state, BlockPos pos)
    {
        BlockState below = level.getBlockState(pos.below());
        BlockState above = level.getBlockState(pos.above());
        if (below.getBlock() instanceof FlowingElementBase)
        {
            if (above.isAir())
                return true;

            if (above.getBlock() instanceof FlowingElementBase)
            {
                if (above.getValue(FlowingElementBase.LEVEL) == 15)
                    return false;

                if(((FlowingElementBase)above.getBlock()).myType == ((FlowingElementBase)below.getBlock()).myType)
                    return true;
            }
        }

        return false;
    }
}
