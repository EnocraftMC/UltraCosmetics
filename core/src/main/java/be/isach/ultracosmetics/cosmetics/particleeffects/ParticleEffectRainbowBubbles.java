package be.isach.ultracosmetics.cosmetics.particleeffects;

import be.isach.ultracosmetics.UltraCosmetics;
import be.isach.ultracosmetics.cosmetics.type.ParticleEffectType;
import be.isach.ultracosmetics.player.UltraPlayer;

/**
 * Represents an instance of rainbow bubble particles summoned by a player.
 *
 * @author SinfulMentality
 * @since 04-19-20
 */
public class ParticleEffectRainbowBubbles extends ParticleEffect {

    public ParticleEffectRainbowBubbles(UltraPlayer owner, UltraCosmetics ultraCosmetics) {
        super(ultraCosmetics, owner, ParticleEffectType.valueOf("rainbowbubbles"));
        this.stoponMove = true;
    }

    @Override
    public void onUpdate() {
        // Do nothing, this effect has no idle animation.
    }

}
