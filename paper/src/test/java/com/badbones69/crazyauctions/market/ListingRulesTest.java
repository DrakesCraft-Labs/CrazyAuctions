package com.badbones69.crazyauctions.market;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ListingRulesTest {

    private final List<String> blackList = List.of(
            "BEDROCK",
            "END_PORTAL_FRAME",
            "SPAWNER",
            "minecraft:barrier",
            "  DEBUG_STICK  ");

    @Test
    void vetaLasEntradasEnMayusculasContraLaClaveEnMinusculas() {
        assertTrue(ListingRules.isBlackListed(this.blackList, "bedrock"));
        assertTrue(ListingRules.isBlackListed(this.blackList, "end_portal_frame"));
        assertTrue(ListingRules.isBlackListed(this.blackList, "spawner"));
    }

    @Test
    void aceptaEspacioDeNombresYEspaciosSobrantesEnAmbosLados() {
        assertTrue(ListingRules.isBlackListed(this.blackList, "barrier"));
        assertTrue(ListingRules.isBlackListed(this.blackList, "minecraft:bedrock"));
        assertTrue(ListingRules.isBlackListed(this.blackList, "debug_stick"));
    }

    @Test
    void dejaPasarLoQueNoEstaVetado() {
        assertFalse(ListingRules.isBlackListed(this.blackList, "crimson_fungus"));
        assertFalse(ListingRules.isBlackListed(this.blackList, "diamond"));
    }

    @Test
    void toleraListasYClavesAusentes() {
        List<String> conNulos = new ArrayList<>();
        conNulos.add(null);
        conNulos.add("BEDROCK");

        assertFalse(ListingRules.isBlackListed(null, "bedrock"));
        assertFalse(ListingRules.isBlackListed(List.of(), "bedrock"));
        assertFalse(ListingRules.isBlackListed(this.blackList, null));
        assertFalse(ListingRules.isBlackListed(this.blackList, "   "));
        assertTrue(ListingRules.isBlackListed(conNulos, "bedrock"));
    }
}
