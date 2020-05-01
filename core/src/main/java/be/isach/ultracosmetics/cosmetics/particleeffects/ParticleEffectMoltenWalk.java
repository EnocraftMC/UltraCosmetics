package be.isach.ultracosmetics.cosmetics.particleeffects;

import be.isach.ultracosmetics.UltraCosmetics;
import be.isach.ultracosmetics.cosmetics.type.ParticleEffectType;
import be.isach.ultracosmetics.player.UltraPlayer;
import be.isach.ultracosmetics.util.Particles;
import be.isach.ultracosmetics.util.UtilParticles;
import org.bukkit.Location;
import org.bukkit.util.Vector;

/**
 * Represents an instance of molten walk particles summoned by a player.
 *
 * @author SinfulMentality
 * @since 04-30-20
 */
public class ParticleEffectMoltenWalk extends ParticleEffect {

    public ParticleEffectMoltenWalk(UltraPlayer owner, UltraCosmetics ultraCosmetics) {
        super(ultraCosmetics, owner, ParticleEffectType.valueOf("moltenwalk"));
    }

    @Override
    public void onUpdate() {
        Location locationLeft = UtilParticles.getLeftLegLocation(getPlayer().getLocation());
        Location locationRight = UtilParticles.getRightLegLocation(getPlayer().getLocation());
        UtilParticles.drawParticlesWithOffset(Particles.FALLING_LAVA,0,0,0, locationLeft, 1);
        UtilParticles.drawParticlesWithOffset(Particles.LAVA,0,0,0, locationLeft, 1);
        UtilParticles.drawParticlesWithOffset(Particles.FALLING_LAVA,0,0,0, locationRight, 1);
        UtilParticles.drawParticlesWithOffset(Particles.LAVA,0,0,0, locationRight, 1);
    }
}
