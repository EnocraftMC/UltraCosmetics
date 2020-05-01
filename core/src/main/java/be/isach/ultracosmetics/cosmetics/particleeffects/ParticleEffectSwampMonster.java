package be.isach.ultracosmetics.cosmetics.particleeffects;

import be.isach.ultracosmetics.UltraCosmetics;
import be.isach.ultracosmetics.cosmetics.type.ParticleEffectType;
import be.isach.ultracosmetics.player.UltraPlayer;
import be.isach.ultracosmetics.util.Particles;
import be.isach.ultracosmetics.util.UCMaterial;
import be.isach.ultracosmetics.util.UtilParticles;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.util.Vector;

/**
 * Represents an instance of swamp monster particles summoned by a player.
 *
 * @author SinfulMentality
 * @since 04-30-20
 */
public class ParticleEffectSwampMonster extends ParticleEffect {

    public ParticleEffectSwampMonster(UltraPlayer owner, UltraCosmetics ultraCosmetics) {
        super(ultraCosmetics, owner, ParticleEffectType.valueOf("swampmonster"));
    }

    @Override
    public void onUpdate() {
        Location locationLeft = UtilParticles.getLeftLegLocation(getPlayer().getLocation());
        Location locationRight = UtilParticles.getRightLegLocation(getPlayer().getLocation());
        locationLeft.getWorld().spawnParticle(Particle.ITEM_CRACK, getPlayer().getLocation().add(0,1,0), 1, 0.2, 0, 0.2, 0, UCMaterial.SLIME_BLOCK.parseItem());
        UtilParticles.drawParticlesWithOffset(Particles.SLIME,0,0,0, locationLeft, 1);
        UtilParticles.drawParticlesWithOffset(Particles.SLIME,0,0,0, locationRight, 1);
    }

}
