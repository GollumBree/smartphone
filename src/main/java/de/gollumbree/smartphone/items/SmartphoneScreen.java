package de.gollumbree.smartphone.items;

import javax.annotation.Nonnull;

import de.gollumbree.smartphone.Main;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SmartphoneScreen extends AbstractContainerScreen<SmartphoneMenu> {

    private static final ResourceLocation BG = ResourceLocation.fromNamespaceAndPath(Main.MODID,
            "textures/gui/container/smartphone_screen.png");

    public SmartphoneScreen(SmartphoneMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
    }

    @Override
    protected void renderBg(@Nonnull GuiGraphics gfx, float partial, int mouseX, int mouseY) {
        gfx.blit(BG, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    public void render(@Nonnull GuiGraphics gfx, int mouseX, int mouseY, float partial) {
        super.render(gfx, mouseX, mouseY, partial);
        this.renderTooltip(gfx, mouseX, mouseY);
    }
}
