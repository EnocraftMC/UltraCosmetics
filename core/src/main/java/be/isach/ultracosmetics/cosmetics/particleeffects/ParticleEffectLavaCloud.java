package be.isach.ultracosmetics.cosmetics.particleeffects;

import be.isach.ultracosmetics.UltraCosmetics;
import be.isach.ultracosmetics.cosmetics.type.ParticleEffectType;
import be.isach.ultracosmetics.player.UltraPlayer;
import be.isach.ultracosmetics.util.Particles;
import be.isach.ultracosmetics.util.UtilParticles;

/**
 * Represents an instance of lava cloud particles summoned by a player.
 *
 * @author SinfulMentality
 * @since 04-30-2020
 */
public class ParticleEffectLavaCloud extends ParticleEffect {

    public ParticleEffectLavaCloud(UltraPlayer owner, UltraCosmetics ultraCosmetics) {
        super(ultraCosmetics, owner, ParticleEffectType.valueOf("lavacloud"));
    }

    @Override
    public void onUpdate() {
        drawCloud();
        //UtilParticles.drawParticlesWithOffset(Particles.CLOUD, 0.5F, 0.1f, 0.5f, getPlayer().getLocation().add(0, 3, 0), 10);
        UtilParticles.drawParticlesWithOffset(getType().getEffect(), 0.25F, 0.05f, 0.25f, getPlayer().getLocation().add(0, 3, 0), 1);

    }

    private void drawCloud() {
        int red = 120;
        int green = 120;
        int blue = 120;
        float size = 3f;
        int i;
        for(i = 0 ; i < 6 ; i++) {
            UtilParticles.drawColoredDustWithSize(red, green, blue, getPlayer().getLocation().add(Math.random() * 2f - 1f, Math.random() * 0.5f + 2.8f, Math.random() * 2f - 1f), size);
        }
    }
}
