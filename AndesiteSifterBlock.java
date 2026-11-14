package com.example.andesitemechanica;

import com.simibubi.create.content.kinetics.base.KineticBlock;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class AndesiteSifterBlock extends KineticBlock implements IBE<AndesiteSifterBlockEntity> {

    public AndesiteSifterBlock(Properties properties) {
        super(properties);
    }

    @Override
    public Axis getRotationAxis(BlockState state) {
        return Axis.Y;
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return face == Direction.DOWN;
    }

    @Override
    public Class<AndesiteSifterBlockEntity> getBlockEntityClass() {
        return AndesiteSifterBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends AndesiteSifterBlockEntity> getBlockEntityType() {
        return CreateAndesiteMechanica.SIFTER_BE.get();
    }

    @Override
    public SpeedLevel getMinimumRequiredSpeedLevel() {
        return SpeedLevel.SLOW;
    }
}
