package de.gollumbree.smartphone.compat;

import java.util.List;
import java.util.stream.Stream;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import de.gollumbree.smartphone.items.SmartphoneItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;

public class ItemFinder {
    public static @Nullable <T extends Item> ItemStack findItem(@Nonnull Inventory inv, Class<T> classT) {
        List<ItemStack> smartphoneContents = inv.items.stream() // Stream all items in the inventory
                .filter(itemStack -> itemStack.getItem() instanceof SmartphoneItem) // Filter Smartphones
                .map(item -> item.get(DataComponents.CONTAINER)) // get their contents
                .filter(contents -> contents != null).flatMap(ItemContainerContents::stream).toList(); // Discard null
                                                                                                       // contents
        // and flatten the stream
        for (ItemStack itemStack : smartphoneContents) {
            if (classT.isInstance(itemStack.getItem())) {
                return itemStack;
            }
        }

        return null;
    }
}
