package net.monke.abrakadavra.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import net.monke.abrakadavra.block.entity.ModBlockEntities;
import net.monke.abrakadavra.block.entity.RuneTableBlockEntity;
import net.monke.abrakadavra.item.function.Wand;
import net.monke.abrakadavra.networking.ModMessages;
import net.monke.abrakadavra.networking.packet.CheckSpellPacket;
import org.jetbrains.annotations.Nullable;

public class RuneTable extends BaseEntityBlock {

    public RuneTable(Properties properties) {
        super(properties);
    }
    private String LEARNED_SPELLS_KEY = "LearnedSpells";
    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }
    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof RuneTableBlockEntity) {
                ((RuneTableBlockEntity) blockEntity).drops();
            }
        }
    }
    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            if(entity instanceof RuneTableBlockEntity) {
                NetworkHooks.openGui(((ServerPlayer)pPlayer), (RuneTableBlockEntity)entity, pPos);
            } else {
                throw new IllegalStateException("Hey you idiot, the Container is freaking missing!");
            }
            }
            ModMessages.sendToServer(new CheckSpellPacket());
        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new RuneTableBlockEntity(pPos, pState);
    }
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, ModBlockEntities.RUNE_TABLE.get(), RuneTableBlockEntity::tick);
    }
}
