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
import net.minecraft.world.level.block.DoubleBlockCombiner;

public record BatteryPayload(double battery) implements CustomPacketPayload {

    public static final Identifier ID = Identifier.fromNamespaceAndPath("betadrive", "battery");
    public static final CustomPacketPayload.Type<BatteryPayload> TYPE = new CustomPacketPayload.Type<>(ID);
    public static final StreamCodec<RegistryFriendlyByteBuf, BatteryPayload> CODEC = StreamCodec.composite(ByteBufCodecs.DOUBLE, BatteryPayload::battery, BatteryPayload::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}