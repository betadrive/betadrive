package dev.matthy.betadrive;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record IsAndroidPayload(boolean isAndroid) implements CustomPayload {
    public static final Id<IsAndroidPayload> ID = new Id<>(Identifier.of("betadrive", "is_android"));
    public static final PacketCodec<RegistryByteBuf, IsAndroidPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.BOOLEAN, IsAndroidPayload::isAndroid,
            IsAndroidPayload::new);
    @Override
    public Id<? extends CustomPayload> getId() {
        return new Id<>(ID.id());
    }
}