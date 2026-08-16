package com.badbones69.crazyauctions.market;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MarketResolverTest {

    private final MarketResolver resolver = new MarketResolver(
            "survival",
            Map.of(
                    "survival", List.of("world", "world_nether"),
                    "clasico", List.of("clasico", "clasico_nether"),
                    "oneblock", List.of("oneblock_world")
            ),
            Map.of("survival", "Survival", "clasico", "Clasico"),
            Set.of("boss_arena")
    );

    @Test
    void resolvesWorldsIntoIndependentMarkets() {
        assertEquals("survival", this.resolver.resolve("WORLD").orElseThrow());
        assertEquals("clasico", this.resolver.resolve("clasico_nether").orElseThrow());
        assertEquals("oneblock", this.resolver.resolve("oneblock_world").orElseThrow());
    }

    @Test
    void deniesTechnicalAndUnknownWorlds() {
        assertTrue(this.resolver.resolve("boss_arena").isEmpty());
        assertTrue(this.resolver.resolve("unmapped_world").isEmpty());
    }

    @Test
    void legacyListingsBelongOnlyToConfiguredLegacyMarket() {
        assertTrue(this.resolver.matches("world", null));
        assertFalse(this.resolver.matches("clasico", null));
        assertTrue(this.resolver.matches("clasico", "clasico"));
        assertFalse(this.resolver.matches("world", "clasico"));
    }
}
