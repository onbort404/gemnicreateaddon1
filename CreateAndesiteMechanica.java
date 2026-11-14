package com.example.andesitemechanica;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(CreateAndesiteMechanica.MODID)
public class CreateAndesiteMechanica {
    public static final String MODID = "create_andesite_mechanica";

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    // 1. Wzmocniona Obudowa Andezytowa (odporna na wybuchy)
    public static final DeferredBlock<Block> REINFORCED_ANDESITE_CASING = BLOCKS.register(
            "reinforced_andesite_casing",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.WOOD)
                    .strength(4.0F, 1200.0F)
                    .sound(SoundType.WOOD)
                    .requiresCorrectToolForDrops())
    );

    public static final DeferredItem<BlockItem> REINFORCED_ANDESITE_CASING_ITEM = ITEMS.registerSimpleBlockItem(
            "reinforced_andesite_casing", REINFORCED_ANDESITE_CASING);

    // 2. Andezytowy Przesiewacz Kinetyczny
    public static final DeferredBlock<AndesiteSifterBlock> ANDESITE_SIFTER = BLOCKS.register(
            "andesite_sifter",
            () -> new AndesiteSifterBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.WOOD)
                    .strength(2.5F, 6.0F)
                    .sound(SoundType.WOOD)
                    .noOcclusion())
    );

    public static final DeferredItem<BlockItem> ANDESITE_SIFTER_ITEM = ITEMS.registerSimpleBlockItem(
            "andesite_sifter", ANDESITE_SIFTER);

    // Rejestracja BlockEntity dla Przesiewacza
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AndesiteSifterBlockEntity>> SIFTER_BE =
            BLOCK_ENTITIES.register("andesite_sifter", () ->
                    BlockEntityType.Builder.of(
                            (pos, state) -> new AndesiteSifterBlockEntity(CreateAndesiteMechanica.SIFTER_BE.get(), pos, state),
                            ANDESITE_SIFTER.get()
                    ).build(null)
            );

    // Zakładka w trybie kreatywnym
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MOD_TAB = CREATIVE_TABS.register("main_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.create_andesite_mechanica"))
                    .icon(() -> ANDESITE_SIFTER_ITEM.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        output.accept(REINFORCED_ANDESITE_CASING_ITEM.get());
                        output.accept(ANDESITE_SIFTER_ITEM.get());
                    }).build());

    public CreateAndesiteMechanica(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        BLOCK_ENTITIES.register(modEventBus);
        CREATIVE_TABS.register(modEventBus);
    }
}
