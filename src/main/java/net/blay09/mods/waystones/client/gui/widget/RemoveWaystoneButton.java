package net.blay09.mods.waystones.client.gui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

import java.util.Collections;
import java.util.List;

public class RemoveWaystoneButton extends Button implements ITooltipProvider {

    private static final ResourceLocation BEACON = new ResourceLocation("textures/gui/container/beacon.png");

    private final List<String> tooltip;
    private final List<String> activeTooltip;
    private final int visibleRegionStart;
    private final int visibleRegionHeight;
    private static boolean shiftGuard;

    public RemoveWaystoneButton(int x, int y, int visibleRegionStart, int visibleRegionHeight, IPressable pressable) {
        super(x, y, 13, 13, "", pressable);
        this.visibleRegionStart = visibleRegionStart;
        this.visibleRegionHeight = visibleRegionHeight;
        tooltip = Collections.singletonList(I18n.format("gui.waystones.waystone_selection.hold_shift_to_delete"));
        activeTooltip = Collections.singletonList(I18n.format("gui.waystones.waystone_selection.click_to_delete"));
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (super.mouseClicked(mouseX, mouseY, button)) {
            shiftGuard = true;
            return true;
        }

        return false;
    }

    @Override
    public void renderButton(int mouseX, int mouseY, float partial) {
        boolean shiftDown = Screen.hasShiftDown();
        if (!shiftDown) {
            shiftGuard = false;
        }
        active = !shiftGuard && shiftDown;

        if (mouseY >= visibleRegionStart && mouseY < visibleRegionStart + visibleRegionHeight) {
            RenderSystem.color4f(1f, 1f, 1f, 1f);
            Minecraft.getInstance().getTextureManager().bindTexture(BEACON);
            if (isHovered && active) {
                RenderSystem.color4f(1f, 1f, 1f, 1f);
            } else {
                RenderSystem.color4f(0.5f, 0.5f, 0.5f, 0.5f);
            }
            blit(x, y, 114, 223, 13, 13);
            RenderSystem.color4f(1f, 1f, 1f, 1f);
        }
    }

    @Override
    public boolean shouldShowTooltip() {
        return isHovered;
    }

    @Override
    public List<String> getTooltip() {
        return active ? activeTooltip : tooltip;
    }
}
