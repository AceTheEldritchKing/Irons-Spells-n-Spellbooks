package com.example.testmod.capabilities.magic.network;

import com.example.testmod.player.ClientMagicData;
import com.example.testmod.spells.SpellType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketSyncCooldownToClient {
    private final int spellId;
    private final int duration;

    public PacketSyncCooldownToClient(int spellId,int duration) {

        this.spellId=spellId;
        this.duration=duration;
    }

    public PacketSyncCooldownToClient(FriendlyByteBuf buf) {
        spellId = buf.readInt();
        duration = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(spellId);
        buf.writeInt(duration);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            ClientMagicData.getCooldowns().addCooldown(SpellType.values()[spellId], duration);
        });
        return true;
    }
}
