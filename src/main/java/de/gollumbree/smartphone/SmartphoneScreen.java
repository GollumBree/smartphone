package de.gollumbree.smartphone;

import javax.annotation.Nonnull;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SmartphoneScreen extends AbstractContainerScreen<SmartphoneMenu> {

    private static final ResourceLocation BG = ResourceLocation.fromNamespaceAndPath(Smartphone.MODID,
            "textures/gui/container/my_screen.png");

    public SmartphoneScreen(SmartphoneMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
    }

    @Override
    protected void renderBg(@Nonnull GuiGraphics gfx, float partial, int mouseX, int mouseY) {
        gfx.blit(BG, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
    }
}
