package dev.matthy.betadrive;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record BatteryPayload(double battery) implements CustomPayload {

    public static final Id<BatteryPayload> ID = new Id<>(Identifier.of("betadrive", "battery"));
    public static final PacketCodec<RegistryByteBuf, BatteryPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.DOUBLE, BatteryPayload::battery,
            BatteryPayload::new);
    @Override
    public Id<? extends CustomPayload> getId() {
        return new Id<>(ID.id());
    }
}