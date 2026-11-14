package com.example.andesitemechanica;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class AndesiteSifterBlockEntity extends KineticBlockEntity {

    private int processingTimer = 0;

    public AndesiteSifterBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }

    @Override
    public float calculateStressApplied() {
        float impact = 4.0f;
        this.lastStressApplied = impact;
        return impact;
    }

    @Override
    public void tick() {
        super.tick();

        if (level == null || isOverStressed())
            return;

        float absSpeed = Math.abs(getSpeed());
        if (absSpeed == 0)
            return;

        AABB searchBox = new AABB(worldPosition.above()).deflate(0.1, 0.4, 0.1);
        List<ItemEntity> items = level.getEntitiesOfClass(ItemEntity.class, searchBox);

        if (items.isEmpty()) {
            processingTimer = 0;
            return;
        }

        ItemEntity targetEntity = items.get(0);
        ItemStack stack = targetEntity.getItem();

        if (!canProcess(stack)) {
            processingTimer = 0;
            return;
        }

        if (level.isClientSide) {
            if (level.random.nextFloat() < 0.3f) {
                level.addParticle(
                        new BlockParticleOption(ParticleTypes.BLOCK, Blocks.SPRUCE_PLANKS.defaultBlockState()),
                        worldPosition.getX() + 0.2 + level.random.nextDouble() * 0.6,
                        worldPosition.getY() + 1.05,
                        worldPosition.getZ() + 0.2 + level.random.nextDouble() * 0.6,
                        0, 0.1, 0
                );
            }
            return;
        }

        int step = Mth.clamp((int) (absSpeed / 16f), 1, 16);
        processingTimer += step;

        if (processingTimer % 10 == 0) {
            level.playSound(null, worldPosition, SoundEvents.GRAVEL_HIT, SoundSource.BLOCKS, 0.3f, 1.2f);
        }

        if (processingTimer >= 100) {
            processingTimer = 0;
            processItem(stack);
        }
    }

    private boolean canProcess(ItemStack stack) {
        return stack.is(Items.GRAVEL) || stack.is(Items.SAND);
    }

    private void processItem(ItemStack input) {
        if (level == null) return;

        ItemStack result = ItemStack.EMPTY;
        float roll = level.random.nextFloat();

        if (input.is(Items.GRAVEL)) {
            if (roll < 0.40f) result = new ItemStack(Items.FLINT);
            else if (roll < 0.75f) result = new ItemStack(Items.IRON_NUGGET, 2);
            else result = new ItemStack(Items.ANDESITE);
        } else if (input.is(Items.SAND)) {
            if (roll < 0.35f) result = new ItemStack(Items.CLAY_BALL, 2);
            else if (roll < 0.60f) result = new ItemStack(Items.GOLD_NUGGET);
            else result = new ItemStack(Items.BONE_MEAL);
        }

        input.shrink(1);

        if (!result.isEmpty()) {
            ItemEntity drop = new ItemEntity(
                    level,
                    worldPosition.getX() + 0.5,
                    worldPosition.getY() + 1.15,
                    worldPosition.getZ() + 0.5,
                    result
            );
            drop.setDeltaMovement(0, 0.15, 0);
            level.addFreshEntity(drop);
            level.playSound(null, worldPosition, SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.BLOCKS, 0.2f, 1.5f);
        }
    }
}
