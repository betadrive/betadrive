package dev.matthy.betadrive.payload;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.ClientboundShowDialogPacket;
import net.minecraft.network.protocol.common.ClientboundResourcePackPushPacket;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.configuration.ClientboundUpdateEnabledFeaturesPacket;
import net.minecraft.resources.Identifier;
import net.minecraft.server.ReloadableServerRegistries;

public record IsAndroidPayload(String uuid, boolean isAndroid) implements CustomPacketPayload {
    public static final Identifier ID = Identifier.fromNamespaceAndPath("betadrive", "is_android");
    public static final CustomPacketPayload.Type<IsAndroidPayload> TYPE = new CustomPacketPayload.Type<>(ID);
    public static final StreamCodec<RegistryFriendlyByteBuf, IsAndroidPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, IsAndroidPayload::uuid,
            ByteBufCodecs.BOOL, IsAndroidPayload::isAndroid,
            IsAndroidPayload::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}