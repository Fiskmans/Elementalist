package com.fiskmans.elementalist.Blocks.BlockEntity;

import com.fiskmans.elementalist.ElementType;
import com.fiskmans.elementalist.ElementalistRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class CrystalizedNatureBlockEntity extends ElementalBlockEntity {

    public CrystalizedNatureBlockEntity(BlockPos pos, BlockState state) {
        super(ElementalistRegister.CRYSTALIZED_NATURE_BLOCK_ENTITY.get(), pos, state, ElementType.ENDER);
    }

    @Override
    boolean CanDissolve(Block aBlock) {
        return false;
    }
}
