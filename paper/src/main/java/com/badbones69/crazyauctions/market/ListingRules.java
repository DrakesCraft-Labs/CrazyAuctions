package com.badbones69.crazyauctions.market;

import java.util.List;
import java.util.Locale;

/**
 * Reglas puras de admision de un item en la subasta.
 *
 * <p>Se aislan del comando para poder cubrirlas con pruebas sin arrancar el servidor.</p>
 */
public final class ListingRules {

    private static final String VANILLA_NAMESPACE = "minecraft:";

    private ListingRules() {
    }

    /**
     * Comprueba si un material esta vetado.
     *
     * <p>La lista negra del config se escribe historicamente en mayusculas y con el nombre del
     * {@link org.bukkit.Material} ({@code BEDROCK}), mientras que la clave del item siempre llega en
     * minusculas ({@code bedrock}). Comparar en crudo dejaba la lista negra inerte, asi que aqui se
     * normalizan ambos lados y se admite tambien la forma con espacio de nombres
     * ({@code minecraft:bedrock}).</p>
     *
     * @param blackList entradas configuradas en {@code Settings.BlackList}
     * @param materialKey clave del material ofrecido
     * @return {@code true} si el item no puede venderse
     */
    public static boolean isBlackListed(final List<String> blackList, final String materialKey) {
        if (blackList == null || blackList.isEmpty() || materialKey == null) {
            return false;
        }

        final String candidate = normalize(materialKey);

        if (candidate.isEmpty()) {
            return false;
        }

        for (final String entry : blackList) {
            if (candidate.equals(normalize(entry))) {
                return true;
            }
        }

        return false;
    }

    private static String normalize(final String value) {
        if (value == null) {
            return "";
        }

        String normalized = value.trim().toLowerCase(Locale.ROOT);

        if (normalized.startsWith(VANILLA_NAMESPACE)) {
            normalized = normalized.substring(VANILLA_NAMESPACE.length());
        }

        return normalized;
    }
}
