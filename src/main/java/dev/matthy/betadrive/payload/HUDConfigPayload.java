package dev.matthy.betadrive.payload;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record HUDConfigPayload(String uuid, String property, boolean active) implements CustomPacketPayload {

    public static final Identifier ID = Identifier.fromNamespaceAndPath("betadrive", "hud_config");
    public static final Type<HUDConfigPayload> TYPE = new Type<>(ID);
    public static final StreamCodec<RegistryFriendlyByteBuf, HUDConfigPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, HUDConfigPayload::uuid,
            ByteBufCodecs.STRING_UTF8, HUDConfigPayload::property,
            ByteBufCodecs.BOOL, HUDConfigPayload::active,
            HUDConfigPayload::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}