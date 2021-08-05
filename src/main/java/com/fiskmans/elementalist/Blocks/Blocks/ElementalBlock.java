package com.fiskmans.elementalist.Blocks.Blocks;

import com.fiskmans.elementalist.Blocks.BlockEntity.ElementalBlockEntity;
import com.fiskmans.elementalist.Blocks.ElementType;
import com.fiskmans.elementalist.ElementalistRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;

public class ElementalBlock extends BaseEntityBlock {

    public ElementType myElementType;

    public ElementalBlock(ElementType aElementType) {
        super(GetProperties());
        this.myElementType = aElementType;
    }

    @Override
    public RenderShape getRenderShape(BlockState aState) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos aPos, BlockState aState) {

        BlockEntityType<?> blockEntityType = BlockEntityFromElementType(myElementType);
        if (blockEntityType == null)
            return null;

        return new ElementalBlockEntity(blockEntityType, aPos, aState, myElementType);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, BlockEntityFromElementType(myElementType), ElementalBlockEntity::tick);
    }


    public static Block.Properties GetProperties() {
        return BlockBehaviour.Properties.of(Material.METAL).harvestTool(ToolType.PICKAXE).destroyTime(3.f).sound(SoundType.METAL);
    }

    @Nullable
    BlockEntityType<? extends ElementalBlockEntity> BlockEntityFromElementType(ElementType aType)
    {
        switch (myElementType)
        {
            case ENDER:
                return ElementalistRegister.CRYSTALIZED_ENDER_BLOCK_ENTITY.get();

            case NATURE:
                return ElementalistRegister.CRYSTALIZED_NATURE_BLOCK_ENTITY.get();
        }

        LOGGER.error(myElementType + " does not have a block entity mapping");
        return null;
    }
}
