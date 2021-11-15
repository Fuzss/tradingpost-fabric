package fuzs.tradingpost.client;

import fuzs.tradingpost.TradingPost;
import fuzs.tradingpost.client.gui.screens.inventory.TradingPostScreen;
import fuzs.tradingpost.client.renderer.blockentity.TradingPostRenderer;
import fuzs.tradingpost.mixin.client.accessor.MinecraftAccessor;
import fuzs.tradingpost.registry.ModRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.searchtree.MutableSearchTree;
import net.minecraft.client.searchtree.ReloadableSearchTree;
import net.minecraft.client.searchtree.SearchRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.trading.MerchantOffer;

import java.util.stream.Stream;

public class TradingPostClient implements ClientModInitializer {
    public static final ResourceLocation MAGNIFYING_GLASS_LOCATION = new ResourceLocation(TradingPost.MOD_ID, "item/magnifying_glass");
    public static final SearchRegistry.Key<MerchantOffer> OFFER_SEARCH_TREE = new SearchRegistry.Key<>();

    @Override
    public void onInitializeClient() {
        ScreenRegistry.register(ModRegistry.TRADING_POST_MENU_TYPE, TradingPostScreen::new);
        BlockEntityRendererRegistry.register(ModRegistry.TRADING_POST_BLOCK_ENTITY_TYPE, TradingPostRenderer::new);
        createSearchTree();
        ClientSpriteRegistryCallback.event(InventoryMenu.BLOCK_ATLAS).register((TextureAtlas atlasTexture, ClientSpriteRegistryCallback.Registry registry) -> {
            registry.register(MAGNIFYING_GLASS_LOCATION);
        });
    }

    private static void createSearchTree() {
        MutableSearchTree<MerchantOffer> offerSearchTree = new ReloadableSearchTree<>(offer -> Stream.of(offer.getBaseCostA(), offer.getCostB(), offer.getResult())
                .filter(itemStack -> !itemStack.isEmpty())
                .flatMap(itemStack -> itemStack.getTooltipLines(null, TooltipFlag.Default.NORMAL).stream())
                .map(tooltipLine -> ChatFormatting.stripFormatting(tooltipLine.getString()).trim())
                .filter(tooltipString -> !tooltipString.isEmpty()),
                offer -> Stream.of(offer.getBaseCostA(), offer.getCostB(), offer.getResult())
                        .filter(itemStack -> !itemStack.isEmpty())
                        .map(ItemStack::getItem)
                        .map(Registry.ITEM::getKey)
        );
        ((MinecraftAccessor) Minecraft.getInstance()).getSearchRegistry().register(OFFER_SEARCH_TREE, offerSearchTree);
    }
}
