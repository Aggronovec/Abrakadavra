package net.monke.abrakadavra.block.entity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.monke.abrakadavra.Abrakadavra;
import net.monke.abrakadavra.block.ModBlocks;

public class ModBlockEntities {
        public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
                DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Abrakadavra.MOD_ID);
        public static final RegistryObject<BlockEntityType<RuneTableBlockEntity>> RUNE_TABLE = BLOCK_ENTITIES.register("rune_table",
                () -> BlockEntityType.Builder.of(RuneTableBlockEntity::new, ModBlocks.RUNE_TABLE.get()).build(null));

        public static void register(IEventBus eventBus) {
            BLOCK_ENTITIES.register(eventBus);
        }

    }
