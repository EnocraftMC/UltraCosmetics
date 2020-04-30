package be.isach.ultracosmetics.cosmetics.particleeffects;

import be.isach.ultracosmetics.UltraCosmetics;
import be.isach.ultracosmetics.cosmetics.type.ParticleEffectType;
import be.isach.ultracosmetics.player.UltraPlayer;
import be.isach.ultracosmetics.util.MathUtils;
import be.isach.ultracosmetics.util.UtilParticles;
import org.bukkit.Location;

import java.util.Objects;

/**
 * Represents an instance of an angel halo summoned by a player.
 *
 * @author SinfulMentality
 * @since 04-30-20
 */
public class ParticleEffectAngelHalo extends ParticleEffect {

    public ParticleEffectAngelHalo(UltraPlayer owner, UltraCosmetics ultraCosmetics) {
        super(ultraCosmetics, owner, ParticleEffectType.valueOf("angelhalo"));
        this.ignoreMove = true; // ignoreMove is actually stopping the animation on moving if false... change naming
    }

    @Override
    public void onUpdate() {
        Location location = Objects.requireNonNull(getPlayer()).getEyeLocation().add(0, 0.6, 0);
        float radius = 0.3f; // refresh radius value
        drawHalo(radius, location);
    }

    // Draw halo out of colored redstone dust particles
    private void drawHalo(float radius, Location location) {
        // Number of particles the halo is composed of
        int numParticlesHalo = 25;
        for (int i = 0; i < numParticlesHalo; i++) {
            double inc = (2 * Math.PI) / numParticlesHalo;
            float angle = (float) (i * inc);

            // Draw a circle in the x-z plane
            float x = MathUtils.cos(angle) * radius; // x = rcos(theta)
            float z = MathUtils.sin(angle) * radius; // z = rsin(theta)
            location.add(x, 0f, z);

            UtilParticles.drawColoredDustWithSize(255,240,125, location, (float) 0.6);
            location.subtract(x, 0f, z);
        }
    }

}
