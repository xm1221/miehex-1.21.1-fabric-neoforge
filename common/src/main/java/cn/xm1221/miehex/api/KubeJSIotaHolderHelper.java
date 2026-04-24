package cn.xm1221.miehex.api;

import cn.xm1221.miehex.MieHexMod;
import cn.xm1221.miehex.item.CustomIotaHolderItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

/**
 * 供 KubeJS 调用的 IotaHolderItem 注册辅助类。
 * 在 Java 端完成物品注册，KubeJS 只需调用静态方法即可。
 */
public class KubeJSIotaHolderHelper {

    /**
     * 注册一个自定义的 Iota 存储物品，默认 8 个变种，可读写，不可翻页（单页模式）。
     *
     * @param id          物品 ID（如 "thought_wool"），自动加上命名空间 "miehex"
     * @param displayName 物品显示名称（仅用于日志，实际需提供语言文件）
     * @param texturePath 纹理路径（相对于 assets/miehex/textures/item/，不含扩展名）
     * @return 注册后的物品实例
     */
    public static Item register(String id, String displayName, String texturePath) {
        return register(id, displayName, texturePath, 8, true, false);
    }

    /**
     * 注册一个自定义的 Iota 存储物品，指定变种数量，默认可读写，不可翻页。
     *
     * @param id          物品 ID
     * @param displayName 显示名称
     * @param texturePath 纹理路径
     * @param numVariants 变种数量（至少 1）
     * @return 注册后的物品实例
     */
    public static Item register(String id, String displayName, String texturePath, int numVariants) {
        return register(id, displayName, texturePath, numVariants, true, false);
    }

    /**
     * 注册一个自定义的 Iota 存储物品，完全自定义配置。
     *
     * @param id          物品 ID
     * @param displayName 显示名称
     * @param texturePath 纹理路径
     * @param numVariants 变种数量
     * @param writable    是否允许写入 Iota
     * @param pageable    是否支持多页（翻页功能）
     * @return 注册后的物品实例
     */
    public static Item register(String id, String displayName, String texturePath, int numVariants,
                                boolean writable, boolean pageable) {
        ResourceLocation itemId = ResourceLocation.fromNamespaceAndPath(MieHexMod.MOD_ID, id);
        Item.Properties properties = new Item.Properties().stacksTo(1);
        Item item = new CustomIotaHolderItem(properties, numVariants, writable, pageable);
        // 使用 Registry.register 静态方法注册物品
        Registry.register(BuiltInRegistries.ITEM, itemId, item);
        System.out.println("Registered item: " + BuiltInRegistries.ITEM.getKey(item) +
                " | writable=" + writable + " | pageable=" + pageable);
        return item;
    }
}