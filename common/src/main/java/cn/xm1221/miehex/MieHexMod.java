package cn.xm1221.miehex;

import cn.xm1221.miehex.registry.ActionRegisry;
import cn.xm1221.miehex.registry.IotaRegistry;
import cn.xm1221.miehex.registry.MieHexAttributes;

public final class MieHexMod  {
	public static final String MOD_ID = "miehex";

	public static void init() {
		IotaRegistry.init();
		MieHexAttributes.register();
		ActionRegisry.init();

	}

}