package com.fiskmans.elementalist.Blocks.BlockEntity;

import com.fiskmans.elementalist.Blocks.ElementType;
import com.fiskmans.elementalist.ElementalistRegister;
import com.mojang.datafixers.types.Type;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.ai.util.RandomPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.logging.log4j.LogManager;


public class ElementalBlockEntity extends BlockEntity {

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
            level.setBlock(blockPos, ElementalistRegister.TEST_BLOCK.get().defaultBlockState(), 0);

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

    boolean CanDissolve(Block aBlock)
    {
        if(aBlock == Blocks.OAK_LEAVES)
        {
            return true;
        }

        return false;
    }
}
