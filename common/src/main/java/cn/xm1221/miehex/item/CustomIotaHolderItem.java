package cn.xm1221.miehex.item;

import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.IotaType;
import at.petrak.hexcasting.api.item.IotaHolderItem;
import at.petrak.hexcasting.api.item.PigmentItem;
import at.petrak.hexcasting.api.item.VariantItem;
import at.petrak.hexcasting.api.pigment.ColorProvider;
import at.petrak.hexcasting.common.lib.HexDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Stream;

public class CustomIotaHolderItem extends Item implements IotaHolderItem, VariantItem, PigmentItem {
    private final int numVariants;
    private final boolean writable;
    private final boolean pageable;

    public static final int MAX_PAGES = 64;

    public CustomIotaHolderItem(Properties properties, int numVariants, boolean writable, boolean pageable) {
        super(properties);
        this.numVariants = numVariants;
        this.writable = writable;
        this.pageable = pageable;
    }

    // ==================== 辅助方法（多页模式） ====================

    private static boolean arePagesEmpty(ItemStack stack) {
        if (!(stack.getItem() instanceof CustomIotaHolderItem)) return true;
        Map<String, Iota> pages = stack.get(HexDataComponents.PAGES);
        return pages == null || pages.isEmpty();
    }

    private static int getPage(ItemStack stack, int ifEmpty) {
        if (arePagesEmpty(stack)) {
            return ifEmpty;
        } else if (stack.has(HexDataComponents.SELECTED_PAGE)) {
            Integer index = stack.get(HexDataComponents.SELECTED_PAGE);
            if (index == null) return 1;
            if (index == 0) index = 1;
            return index;
        } else {
            return 1;
        }
    }

    private static int highestPage(ItemStack stack) {
        Map<String, Iota> pages = stack.get(HexDataComponents.PAGES);
        if (pages == null) return 0;
        return pages.keySet().stream()
                .flatMap(s -> { try { return Stream.of(Integer.parseInt(s)); } catch (NumberFormatException e) { return Stream.empty(); } })
                .max(Integer::compare).orElse(0);
    }

    private static boolean isPageSealed(ItemStack stack, int page) {
        Map<String, Boolean> seals = stack.get(HexDataComponents.PAGE_SEALS);
        if (seals == null) return false;
        Boolean v = seals.get(String.valueOf(page));
        return v != null && v;
    }

    private static void setPageSealed(ItemStack stack, int page, boolean sealed) {
        String key = String.valueOf(page);
        Map<String, Boolean> seals = stack.get(HexDataComponents.PAGE_SEALS);
        HashMap<String, Boolean> sealsMut = seals == null ? new HashMap<>() : new HashMap<>(seals);
        if (sealed) {
            sealsMut.put(key, true);
        } else {
            sealsMut.remove(key);
        }
        if (sealsMut.isEmpty()) {
            stack.remove(HexDataComponents.PAGE_SEALS);
        } else {
            stack.set(HexDataComponents.PAGE_SEALS, sealsMut);
        }
    }

    private static boolean isSealed(ItemStack stack) {
        int idx = getPage(stack, 1);
        return isPageSealed(stack, idx);
    }

    private static void setPageData(ItemStack stack, int page, @Nullable Iota iota) {
        String key = String.valueOf(page);
        Map<String, Iota> pages = stack.get(HexDataComponents.PAGES);
        HashMap<String, Iota> pagesMut = pages == null ? new HashMap<>() : new HashMap<>(pages);
        if (iota == null) {
            pagesMut.remove(key);
            // 清除该页密封状态
            setPageSealed(stack, page, false);
        } else {
            pagesMut.put(key, iota);
        }
        if (pagesMut.isEmpty()) {
            stack.remove(HexDataComponents.PAGES);
        } else {
            stack.set(HexDataComponents.PAGES, pagesMut);
        }
    }

    private static Iota getPageData(ItemStack stack, int page) {
        Map<String, Iota> pages = stack.get(HexDataComponents.PAGES);
        if (pages == null) return null;
        return pages.get(String.valueOf(page));
    }

    // ==================== 公开静态 API（便于 KubeJS 调用） ====================

    public boolean isPageable() {
        return pageable;
    }

    public static int GetCurrentPage(ItemStack stack) {
        if (!(stack.getItem() instanceof CustomIotaHolderItem)) return 1;
        return getPage(stack, 1);
    }

    public static void SetCurrentPage(ItemStack stack, int page) {
        if (!(stack.getItem() instanceof CustomIotaHolderItem)) return;
        int clamped = Mth.clamp(page, 1, MAX_PAGES);
        stack.set(HexDataComponents.SELECTED_PAGE, clamped);
        // 恢复页面名称
        int shifted = Math.max(1, clamped);
        String nameKey = String.valueOf(shifted);
        Map<String, Component> names = stack.get(HexDataComponents.PAGE_NAMES);
        if (names != null && names.containsKey(nameKey)) {
            Component name = names.get(nameKey);
            if (name != null) stack.set(DataComponents.CUSTOM_NAME, name);
        } else {
            stack.remove(DataComponents.CUSTOM_NAME);
        }
    }

    public static void RotatePage(ItemStack stack, boolean increase) {
        if (!(stack.getItem() instanceof CustomIotaHolderItem item)) return;
        int idx = getPage(stack, 0);
        if (idx != 0) {
            idx += increase ? 1 : -1;
            idx = Math.max(1, idx);
        }
        idx = Mth.clamp(idx, 0, MAX_PAGES);
        stack.set(HexDataComponents.SELECTED_PAGE, idx);
        int shifted = Math.max(1, idx);
        String nameKey = String.valueOf(shifted);
        Map<String, Component> names = stack.get(HexDataComponents.PAGE_NAMES);
        if (names != null && names.containsKey(nameKey)) {
            Component name = names.get(nameKey);
            if (name != null) stack.set(DataComponents.CUSTOM_NAME, name);
        } else {
            stack.remove(DataComponents.CUSTOM_NAME);
        }
    }

    public static int GetHighestPage(ItemStack stack) {
        return highestPage(stack);
    }

    public static boolean IsPageSealed(ItemStack stack, int page) {
        return isPageSealed(stack, page);
    }

    public static void SetPageSealed(ItemStack stack, int page, boolean sealed) {
        setPageSealed(stack, page, sealed);
    }

    public static boolean IsCurrentPageSealed(ItemStack stack) {
        return isSealed(stack);
    }

    public static void SetCurrentPageSealed(ItemStack stack, boolean sealed) {
        setPageSealed(stack, getPage(stack, 1), sealed);
    }

    // ==================== IotaHolderItem 实现 ====================

    @Override
    public @Nullable Iota readIota(ItemStack stack) {
        if (!(stack.getItem() instanceof CustomIotaHolderItem)) return null;

        if (!pageable) {
            // 单页模式：直接从 IOTA 组件读取
            return stack.get(HexDataComponents.IOTA);
        } else {
            // 多页模式：从当前页读取
            int page = getPage(stack, 1);
            return getPageData(stack, page);
        }
    }

    @Override
    public boolean writeable(ItemStack stack) {
        if (!writable) return false;
        if (!pageable) return true;
        return !isSealed(stack);
    }

    @Override
    public boolean canWrite(ItemStack stack, @Nullable Iota iota) {
        if (!writeable(stack)) return false;
        return iota != null;
    }

    @Override
    public void writeDatum(ItemStack stack, @Nullable Iota iota) {
        if (!writable) return;

        if (!pageable) {
            // 单页模式：直接设置 IOTA 组件
            if (iota == null) {
                stack.remove(HexDataComponents.IOTA);
            } else {
                stack.set(HexDataComponents.IOTA, iota);
            }
        } else {
            if (isSealed(stack)) return;
            int page = getPage(stack, 1);
            setPageData(stack, page, iota);
        }
    }

    @Override
    public int getColor(ItemStack stack) {
        // 优先视觉覆盖组件
        Optional<IotaType<?>> override = stack.get(HexDataComponents.VISUAL_OVERRIDE);
        if (override != null && override.isPresent()) {
            return override.get().color();
        }
        Iota iota = readIota(stack);
        return iota == null ? 0xFFAACCFF : iota.getType().color();
    }

    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        if (pageable) {
            boolean sealed = isSealed(stack);
            boolean empty = false;
            if (stack.has(HexDataComponents.SELECTED_PAGE)) {
                int pageIdx = stack.get(HexDataComponents.SELECTED_PAGE);
                int highest = highestPage(stack);
                if (highest != 0) {
                    if (sealed) {
                        components.add(Component.translatable("hexcasting.tooltip.spellbook.page.sealed",
                                        Component.literal(String.valueOf(pageIdx)).withStyle(ChatFormatting.WHITE),
                                        Component.literal(String.valueOf(highest)).withStyle(ChatFormatting.WHITE),
                                        Component.translatable("hexcasting.tooltip.spellbook.sealed").withStyle(ChatFormatting.GOLD))
                                .withStyle(ChatFormatting.GRAY));
                    } else {
                        components.add(Component.translatable("hexcasting.tooltip.spellbook.page",
                                        Component.literal(String.valueOf(pageIdx)).withStyle(ChatFormatting.WHITE),
                                        Component.literal(String.valueOf(highest)).withStyle(ChatFormatting.WHITE))
                                .withStyle(ChatFormatting.GRAY));
                    }
                } else {
                    empty = true;
                }
            } else {
                empty = true;
            }

            if (empty) {
                boolean overridden = stack.has(HexDataComponents.VISUAL_OVERRIDE);
                if (sealed) {
                    if (overridden) {
                        components.add(Component.translatable("hexcasting.tooltip.spellbook.sealed").withStyle(ChatFormatting.GOLD));
                    } else {
                        components.add(Component.translatable("hexcasting.tooltip.spellbook.empty.sealed",
                                        Component.translatable("hexcasting.tooltip.spellbook.sealed").withStyle(ChatFormatting.GOLD))
                                .withStyle(ChatFormatting.GRAY));
                    }
                } else if (!overridden) {
                    components.add(Component.translatable("hexcasting.tooltip.spellbook.empty").withStyle(ChatFormatting.GRAY));
                }
            }
        }

        IotaHolderItem.appendHoverText(this, stack, components, flag);
        super.appendHoverText(stack, (TooltipContext) level, components, flag);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (pageable) {
            // 同步页面索引与名称
            int idx = getPage(stack, 0);
            stack.set(HexDataComponents.SELECTED_PAGE, idx);
            int shifted = Math.max(1, idx);
            String nameKey = String.valueOf(shifted);
            Component customName = stack.get(DataComponents.CUSTOM_NAME);
            Map<String, Component> names = stack.get(HexDataComponents.PAGE_NAMES);
            if (customName != null) {
                if (names != null) {
                    if (!names.containsKey(nameKey) || !Objects.equals(names.get(nameKey), customName)) {
                        HashMap<String, Component> mutNames = new HashMap<>(names);
                        mutNames.put(nameKey, customName);
                        stack.set(HexDataComponents.PAGE_NAMES, mutNames);
                    }
                } else {
                    HashMap<String, Component> mutNames = new HashMap<>();
                    mutNames.put(nameKey, customName);
                    stack.set(HexDataComponents.PAGE_NAMES, mutNames);
                }
            } else if (names != null) {
                HashMap<String, Component> mutNames = new HashMap<>(names);
                mutNames.remove(nameKey);
                if (mutNames.isEmpty()) {
                    stack.remove(HexDataComponents.PAGE_NAMES);
                } else {
                    stack.set(HexDataComponents.PAGE_NAMES, mutNames);
                }
            }
        }
        super.inventoryTick(stack, level, entity, slotId, isSelected);
    }

    // ==================== VariantItem 实现 ====================
    @Override
    public int numVariants() {
        return numVariants;
    }

    @Override
    public int getVariant(ItemStack stack) {
        Integer v = stack.get(HexDataComponents.VARIANT);
        return v != null ? v : 0;
    }

    @Override
    public void setVariant(ItemStack stack, int variant) {
        if (pageable && isSealed(stack)) return;
        stack.set(HexDataComponents.VARIANT, clampVariant(variant));
    }

    // ==================== PigmentItem 实现 ====================
    @Override
    public ColorProvider provideColor(ItemStack stack, UUID owner) {
        int variant = getVariant(stack);
        float hue = (variant % numVariants) / (float) numVariants;
        int baseColor = 0xFF000000 | Mth.hsvToRgb(hue, 0.8f, 1.0f);
        return new ColorProvider() {
            @Override
            protected int getRawColor(float time, Vec3 position) {
                return baseColor;
            }
        };
    }
}