package de.gollumbree.smartphone.network;

import de.gollumbree.smartphone.Main;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public record UsingData(ItemStack itemStack) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<UsingData> TYPE = new CustomPacketPayload.Type<>(
            ResourceLocation.fromNamespaceAndPath(Main.MODID, "reset_data"));

    public static final StreamCodec<RegistryFriendlyByteBuf, UsingData> STREAM_CODEC = StreamCodec.composite(
            ItemStack.OPTIONAL_STREAM_CODEC,
            UsingData::itemStack, UsingData::new);

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
