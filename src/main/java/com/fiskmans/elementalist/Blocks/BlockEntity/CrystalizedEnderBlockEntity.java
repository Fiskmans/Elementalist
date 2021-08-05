package com.fiskmans.elementalist.Blocks.BlockEntity;

import com.fiskmans.elementalist.Blocks.ElementType;
import com.fiskmans.elementalist.ElementalistRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class CrystalizedEnderBlockEntity extends ElementalBlockEntity {

    public CrystalizedEnderBlockEntity(BlockPos pos, BlockState state) {
        super(ElementalistRegister.CRYSTALIZED_ENDER_BLOCK_ENTITY.get(), pos, state, ElementType.ENDER);
    }
}
