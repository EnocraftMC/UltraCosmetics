package be.isach.ultracosmetics.cosmetics.particleeffects;

import be.isach.ultracosmetics.UltraCosmetics;
import be.isach.ultracosmetics.cosmetics.type.ParticleEffectType;
import be.isach.ultracosmetics.player.UltraPlayer;
import be.isach.ultracosmetics.util.UtilParticles;

/**
 * Represents an instance of in love particles summoned by a player.
 *
 * @author iSach
 * @since 08-13-2015
 */
public class ParticleEffectInLove extends ParticleEffect {

    private int cycleCount = 0;

    public ParticleEffectInLove(UltraPlayer owner, UltraCosmetics ultraCosmetics) {
        super(ultraCosmetics, owner, ParticleEffectType.valueOf("inlove"));
    }

    @Override
    public void onUpdate() {
        final int CYCLES_BEFORE_UPDATE = 4; // update every fourth cycle
        cycleCount = (cycleCount < CYCLES_BEFORE_UPDATE) ? cycleCount + 1 : 0; // account for possible overflow
        if(cycleCount % CYCLES_BEFORE_UPDATE == 0)
            UtilParticles.drawParticlesWithOffset(getType().getEffect(), 0.5f, 0.3f, 0.5f, getPlayer().getLocation().add(0, 2.5, 0), 1);
    }
}
