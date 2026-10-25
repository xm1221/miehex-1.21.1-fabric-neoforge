package cn.xm1221.miehex;

import cn.xm1221.miehex.registry.ActionRegisry;
import cn.xm1221.miehex.registry.IotaRegistry;
import net.minecraft.resources.ResourceLocation;

public final class MieHexMod  {
	public static final String MOD_ID = "miehex";

	public static ResourceLocation id(String path) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
	}

	public static void init() {
		IotaRegistry.init();
		ActionRegisry.init();
		MiehexWorkers.init();
	}

}