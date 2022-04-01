package com.trufflez.tsbrewcraft.util;

public class ItemTypes {
    public static boolean isFlammable(String product) {
        return product.matches("rum|tequila|vodka|brandy|whiskey|bourbon|shochu");
    }
    public static boolean isExplosive(String product) {
        return product.matches("moonshine");
    }
}
