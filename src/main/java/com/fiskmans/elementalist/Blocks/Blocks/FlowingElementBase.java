package com.fiskmans.elementalist.Blocks.Blocks;

import com.fiskmans.elementalist.ElementType;
import com.fiskmans.elementalist.ElementalistRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.TickPriority;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ToolType;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public abstract class FlowingElementBase extends Block {

    public static final IntegerProperty LEVEL = IntegerProperty.create("level",0,15);

    static final VoxelShape[] hitBoxes = {
            Block.box(0,0,0,16,1,16),
            Block.box(0,0,0,16,2,16),
            Block.box(0,0,0,16,3,16),
            Block.box(0,0,0,16,4,16),
            Block.box(0,0,0,16,5,16),
            Block.box(0,0,0,16,6,16),
            Block.box(0,0,0,16,7,16),
            Block.box(0,0,0,16,8,16),
            Block.box(0,0,0,16,9,16),
            Block.box(0,0,0,16,10,16),
            Block.box(0,0,0,16,11,16),
            Block.box(0,0,0,16,12,16),
            Block.box(0,0,0,16,13,16),
            Block.box(0,0,0,16,14,16),
            Block.box(0,0,0,16,15,16),
            Block.box(0,0,0,16,16,16)
    };

    static final List<Block> softBlocks =  Arrays.asList(
            Blocks.TALL_GRASS,
            Blocks.GRASS,
            Blocks.SNOW,
            Blocks.POWDER_SNOW,
            Blocks.VINE,
            Blocks.WATER
    );

    static final float EvaporationChance = 0.1f;
    static final int TicksPerFlow = 2;

    static final Direction[] EvaporationDirections = {
            Direction.UP,
            Direction.EAST,
            Direction.WEST,
            Direction.SOUTH,
            Direction.NORTH};


    public ElementType myType;

    public FlowingElementBase(ElementType type) {
        super(GetProperties());
        this.registerDefaultState(this.getStateDefinition().any().setValue(LEVEL,0));
        myType = type;
    }


    public static Block.Properties GetProperties() {
        return Block.Properties.of(Material.METAL)
                .harvestTool(ToolType.PICKAXE)
                .destroyTime(0.5f)
                .sound(SoundType.METAL)
                .noOcclusion();
    }

    public static Block BlockFromElement(ElementType type){
        switch (type)
        {
            case NATURE:
                return ElementalistRegister.FLOWING_NATURE_BLOCK.get();
            case ENDER:
                return ElementalistRegister.FLOWING_ENDER_BLOCK.get();
        }
        return null;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> blockState) {
        blockState.add(LEVEL);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext collisionContext) {
        return hitBoxes[state.getValue(LEVEL)];
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean bool) {
        if (CanFlow(level, pos, state))
        {
            level.getBlockTicks().scheduleTick(pos,this,TicksPerFlow, TickPriority.NORMAL);
        }
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos otherPos, boolean bool) {
        if (CanFlow(level, pos, state))
        {
            level.getBlockTicks().scheduleTick(pos,this,TicksPerFlow, TickPriority.NORMAL);
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, Random random) {
        Flow(level, state, pos);
        Evaporate(level, state, pos, random);
    }

    abstract void FlowInto(ServerLevel level, int currentLevel, BlockState target, BlockPos pos, BlockPos targetPos);

    void Flow(ServerLevel level, BlockState state, BlockPos pos)
    {
        int liquidLevel = state.getValue(LEVEL);
        if (CanFlowInto(level.getBlockState(pos.below()),15))
        {
            FlowInto(level, liquidLevel, level.getBlockState(pos.below()), pos, pos.below());
            if (liquidLevel == 0)
                return;
            liquidLevel -= 1;
        }

        if (liquidLevel == 0)
            return;

        if (CanFlowInto(level.getBlockState(pos.east()),liquidLevel - 1))
        {
            FlowInto(level, liquidLevel, level.getBlockState(pos.east()), pos, pos.east());
            liquidLevel -= 1;
        }

        if (liquidLevel == 0)
            return;

        if (CanFlowInto(level.getBlockState(pos.west()),liquidLevel - 1))
        {
            FlowInto(level, liquidLevel, level.getBlockState(pos.west()), pos, pos.west());
            liquidLevel -= 1;
        }

        if (liquidLevel == 0)
            return;


        if (CanFlowInto(level.getBlockState(pos.north()),liquidLevel - 1))
        {
            FlowInto(level, liquidLevel, level.getBlockState(pos.north()), pos, pos.north());
            liquidLevel -= 1;
        }

        if (liquidLevel == 0)
            return;

        if (CanFlowInto(level.getBlockState(pos.south()),liquidLevel - 1))
        {
            FlowInto(level, liquidLevel, level.getBlockState(pos.south()), pos, pos.south());
            liquidLevel -= 1;
        }

        if (liquidLevel == 0)
            return;

        if (CanFlow(level, pos, state))
        {
            level.getBlockTicks().scheduleTick(pos,this,TicksPerFlow, TickPriority.NORMAL);
        }
    }

    boolean CanFlow(LevelReader level, BlockPos pos, BlockState state)
    {
        if (CanFlowInto(level.getBlockState(pos.below()),15))
            return true;

        int liquidLevel = state.getValue(LEVEL);
         if (liquidLevel == 0)
            return false;

        if (CanFlowInto(level.getBlockState(pos.east()),liquidLevel - 1))
            return true;

        if (CanFlowInto(level.getBlockState(pos.west()),liquidLevel - 1))
            return true;

        if (CanFlowInto(level.getBlockState(pos.north()),liquidLevel - 1))
            return true;

        if (CanFlowInto(level.getBlockState(pos.south()),liquidLevel - 1))
            return true;

        return false;
    }

    boolean CanFlowInto(BlockState other, int maxHeight) {
        if (other.isAir())
            return true;

        if (softBlocks.contains(other.getBlock()))
            return true;

        Block otherBlock = other.getBlock();
        if (!(otherBlock instanceof FlowingElementBase))
            return false;

        if (((FlowingElementBase) otherBlock).myType != myType)
            return false;

        return other.getValue(LEVEL) < maxHeight;
    }

    void Evaporate(Level level, BlockState state, BlockPos pos, Random random)
    {
        if (random.nextFloat() < EvaporationChance)
        {
            Direction dir = EvaporationDirections[Math.abs(random.nextInt()) % 5];

            if (level.getBlockState(pos.relative(dir)).isAir())
            {
                int liquidLevel = state.getValue(LEVEL);
                if (liquidLevel == 0)
                {
                    level.removeBlock(pos, false);
                }
                else
                {
                    level.setBlock(pos, state.setValue(LEVEL,liquidLevel - 1), Block.UPDATE_NEIGHBORS | Block.UPDATE_CLIENTS);
                }
            }
        }
    }
}
