package com.hollingsworth.arsnouveau.client.gui.buttons;

import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import com.hollingsworth.arsnouveau.api.spell.SpellValidationError;
import com.hollingsworth.arsnouveau.client.gui.book.GuiSpellBook;
import com.hollingsworth.arsnouveau.client.gui.utils.RenderUtils;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;

public class CraftingButton extends GuiImageButton {
    public @Nullable ResourceLocation spellTag;
    private AbstractSpellPart abstractSpellPart;
    public List<SpellValidationError> validationErrors;

    public CraftingButton(GuiSpellBook parent, int x, int y, Button.OnPress onPress) {
        super(x, y, 0, 0, 22, 20, 22, 20, "textures/gui/spell_glyph_slot.png", onPress);
        this.spellTag = null;
        this.resourceIcon = "";
        this.validationErrors = new LinkedList<>();
        this.parent = parent;
    }

    public void clear() {
        this.spellTag = null;
        this.resourceIcon = "";
        this.validationErrors.clear();
        this.abstractSpellPart = null;
    }

    public @Nullable AbstractSpellPart getAbstractSpellPart() {
        return this.abstractSpellPart;
    }

    public void setAbstractSpellPart(AbstractSpellPart abstractSpellPart) {
        this.abstractSpellPart = abstractSpellPart;
    }

    @Override
    public void render(GuiGraphics graphics, int parX, int parY, float partialTicks) {
        if (visible) {
            if (validationErrors.isEmpty()) {
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            } else {
                RenderSystem.setShaderColor(1.0F, 0.7F, 0.7F, 1.0F);
            }
            if (this.abstractSpellPart != null) {
                RenderUtils.drawSpellPart(this.abstractSpellPart, graphics, x + 3, y + 2, 16, !validationErrors.isEmpty(), 0);
            }
            if (parent.isMouseInRelativeRange(parX, parY, x, y, width, height)) {

                if (abstractSpellPart != null) {
                    List<Component> tooltip = new LinkedList<>();
                    tooltip.add(Component.translatable(abstractSpellPart.getLocalizationKey()));
                    for (SpellValidationError ve : validationErrors) {
                        tooltip.add(ve.makeTextComponentExisting().withStyle(ChatFormatting.RED));
                    }
                    parent.tooltip = tooltip;
                }
            }
        }
        super.render(graphics, parX, parY, partialTicks);
    }
}
