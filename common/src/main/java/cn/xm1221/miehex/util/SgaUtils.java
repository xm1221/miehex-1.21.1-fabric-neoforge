package cn.xm1221.miehex.util;

import java.util.HashMap;
import java.util.Map;

public class SgaUtils {
    private static final Map<Character, Character> SGA_MAP = new HashMap<>();

    static {
        // 大写字母映射（与附魔台显示一致）
        sgaMap('A', 'ꓯ');
        sgaMap('B', 'B');
        sgaMap('C', 'Ↄ');
        sgaMap('D', '◖');
        sgaMap('E', 'Ǝ');
        sgaMap('F', 'Ⅎ');
        sgaMap('G', '⅁');
        sgaMap('H', 'H');
        sgaMap('I', 'I');
        sgaMap('J', 'ſ');
        sgaMap('K', '⋊');
        sgaMap('L', '⅂');
        sgaMap('M', 'W');
        sgaMap('N', 'N');
        sgaMap('O', 'O');
        sgaMap('P', 'Ԁ');
        sgaMap('Q', 'Ό');
        sgaMap('R', 'ᴚ');
        sgaMap('S', 'S');
        sgaMap('T', '⊥');
        sgaMap('U', '∩');
        sgaMap('V', 'ʌ');
        sgaMap('W', 'M');
        sgaMap('X', 'X');
        sgaMap('Y', '⅄');
        sgaMap('Z', 'Z');

        // 小写字母映射
        sgaMap('a', 'ɐ');
        sgaMap('b', 'q');
        sgaMap('c', 'ɔ');
        sgaMap('d', 'p');
        sgaMap('e', 'ǝ');
        sgaMap('f', 'ɟ');
        sgaMap('g', 'ƃ');
        sgaMap('h', 'ɥ');
        sgaMap('i', 'ı');
        sgaMap('j', 'ɾ');
        sgaMap('k', 'ʞ');
        sgaMap('l', 'ʃ');
        sgaMap('m', 'ɯ');
        sgaMap('n', 'u');
        sgaMap('o', 'o');
        sgaMap('p', 'd');
        sgaMap('q', 'b');
        sgaMap('r', 'ɹ');
        sgaMap('s', 's');
        sgaMap('t', 'ʇ');
        sgaMap('u', 'n');
        sgaMap('v', 'ʌ');
        sgaMap('w', 'ʍ');
        sgaMap('x', 'x');
        sgaMap('y', 'ʎ');
        sgaMap('z', 'z');
    }

    private static void sgaMap(char original, char target) {
        SGA_MAP.put(original, target);
    }

    /**
     * 将普通文本转换为标准银河字母（Standard Galactic Alphabet）样式。
     * 若字符不在映射表中，则保留原字符。
     * 注意：某些特殊字符可能需要游戏字体支持才能正确显示。
     *
     * @param text 原始文本
     * @return 转换后的 SGA 文本
     */
    public static String toStandardGalactic(String text) {
        if (text == null) return "";
        StringBuilder sb = new StringBuilder();
        for (char c : text.toCharArray()) {
            sb.append(SGA_MAP.getOrDefault(c, c));
        }
        return sb.toString();
    }
}