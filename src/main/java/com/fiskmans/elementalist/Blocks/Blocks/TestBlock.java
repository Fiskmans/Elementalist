package com.fiskmans.elementalist.Blocks.Blocks;

import com.fiskmans.elementalist.ElementalistRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.ToolType;

public class TestBlock extends Block {

    public TestBlock() {
        super(GetProperties());
    }

    public static Block.Properties GetProperties() {
        return Block.Properties.of(Material.METAL).harvestTool(ToolType.PICKAXE).destroyTime(0.5f).sound(SoundType.METAL);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {

        for (int x = -16;x < 16;x++)
        {
            for (int y = -16;y < 16;y++)
            {
                for (int z = -16;z < 16;z++)
                {
                    if (level.getBlockState(pos.offset(x,y,z)).getBlock() == ElementalistRegister.TEST_BLOCK.get())
                    {
                        level.destroyBlock(pos.offset(x,y,z), true);
                    }
                }
            }
        }

        return InteractionResult.SUCCESS;
    }
}