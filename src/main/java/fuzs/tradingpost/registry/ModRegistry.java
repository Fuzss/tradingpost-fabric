package fuzs.tradingpost.registry;

import fuzs.puzzleslib.registry.RegistryManager;
import fuzs.tradingpost.TradingPost;
import fuzs.tradingpost.world.inventory.TradingPostMenu;
import fuzs.tradingpost.world.level.block.TradingPostBlock;
import fuzs.tradingpost.world.level.block.entity.TradingPostBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

public class ModRegistry {
    private static final RegistryManager REGISTRY = RegistryManager.of(TradingPost.MOD_ID);
    public static final Block TRADING_POST_BLOCK = REGISTRY.registerBlockWithItem("trading_post", () -> new TradingPostBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.5F).sound(SoundType.WOOD)), CreativeModeTab.TAB_DECORATIONS);
    public static final BlockEntityType<TradingPostBlockEntity> TRADING_POST_BLOCK_ENTITY_TYPE = REGISTRY.registerRawBlockEntityType("trading_post", () -> FabricBlockEntityTypeBuilder.create(TradingPostBlockEntity::new, TRADING_POST_BLOCK));
    public static final MenuType<TradingPostMenu> TRADING_POST_MENU_TYPE = REGISTRY.registerRawMenuType("trading_post", () -> TradingPostMenu::new);

    public static final TagKey<EntityType<?>> BLACKLISTED_TRADERS_TAG = TagKey.create(Registry.ENTITY_TYPE_REGISTRY, new ResourceLocation(TradingPost.MOD_ID, "blacklisted_traders"));

    public static void touch() {

    }
}
