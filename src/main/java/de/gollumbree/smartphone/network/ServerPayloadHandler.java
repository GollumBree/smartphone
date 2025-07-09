package de.gollumbree.smartphone.network;

import de.gollumbree.smartphone.items.ServerUsing;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ServerPayloadHandler {
    public static void handleDataOnMain(final UsingData data, final IPayloadContext context) {
        ServerUsing.using.put(context.player(), data.itemStack());
    }
}
