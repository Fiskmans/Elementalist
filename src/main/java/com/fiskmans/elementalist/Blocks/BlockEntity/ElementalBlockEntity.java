package com.fiskmans.elementalist.Blocks.BlockEntity;

import com.fiskmans.elementalist.Blocks.Blocks.FlowingElementBase;
import com.fiskmans.elementalist.ElementType;
import com.fiskmans.elementalist.ElementTypeHelper;
import com.fiskmans.elementalist.ElementalistRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;


public abstract class ElementalBlockEntity extends BlockEntity {

    final int maxFailsPerCheck = 5;

    ElementType myElementType;
    int myCounter;


    public ElementalBlockEntity(BlockEntityType<?> aBlockEntityType,BlockPos pos, BlockState state, ElementType aElementType) {
        super(aBlockEntityType, pos, state);
        myElementType = aElementType;
        myCounter = 0;
    }

    public static void tick(Level level, BlockPos blockPos, BlockState state, ElementalBlockEntity blockEntity)
    {
        blockEntity.CheckNext(level);
    }

    void CheckNext(Level level)
    {
        for (int i = 0; i < maxFailsPerCheck; i++)
        {
            if(CheckBlock(level, OffsetFromIteration( worldPosition, myCounter)))
            {
                break;
            }
            myCounter++;
        }
    }

    boolean CheckBlock(Level level, BlockPos blockPos)
    {
        if(CanDissolve(level.getBlockState(blockPos).getBlock()))
        {
            Dissolve(level, blockPos);

            return true;
        }
        return false;
    }

    static BlockPos OffsetFromIteration(BlockPos origin, int iteration)
    {
        final int[] shellNumbers = { 6, 18, 38, 66, 102 };
        final int[] sliceNumber = { 1, 4, 8, 12, 16, 20 };

        int value = (iteration % 230);
        int x = 0;
        int y = 0;
        int z = 0;

        int shell = 0;
        for (;shell < 5; shell++)
        {
            if (value < shellNumbers[shell])
            {
                break;
            }
            value -= shellNumbers[shell];
        }

        y = -shell - 1;
        int thisSlice = 1;
        for(; y < shell + 1; y++)
        {
            thisSlice = sliceNumber[shell - Math.abs(y) + 1];
            if (value < thisSlice)
            {
                break;
            }
            value -= thisSlice;
        }

        int side = (value * 4) / thisSlice;
        int sideDepth = thisSlice >= 4 ? value % (thisSlice / 4) : value;

        int a = shell + 1 - sideDepth - Math.abs(y);
        int b = shell + 1 - a - Math.abs(y);
        switch (side)
        {
            case 0:
                x = a;
                z = b;
                break;
            case 1:
                z = a;
                x = -b;
                break;
            case 2:
                x = -a;
                z = -b;
                break;
            case 3:
                z = -a;
                x = b;
                break;
        }

        return origin.offset(x, y, z);
    }

    abstract boolean CanDissolve(Block aBlock);

    int DissolveAmount(Block aBlock)
    {
        return 0;
    }

    void Dissolve(Level level, BlockPos pos)
    {
        Block flowingBlock = FlowingElementBase.BlockFromElement(ElementTypeHelper.InvertElement(myElementType));
        if (flowingBlock == null)
        {
            level.removeBlock(pos,false);
            return;
        }

        level.setBlock(pos, flowingBlock.defaultBlockState().setValue(FlowingElementBase.LEVEL,DissolveAmount(level.getBlockState(pos).getBlock())), Block.UPDATE_CLIENTS + Block.UPDATE_NEIGHBORS);
    }
}
