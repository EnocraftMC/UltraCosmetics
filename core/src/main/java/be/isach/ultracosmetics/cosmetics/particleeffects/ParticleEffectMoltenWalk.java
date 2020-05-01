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
        Vector vectorLeft = getLeftVector(getPlayer().getLocation()).normalize().multiply(0.15);
        Vector vectorRight = getRightVector(getPlayer().getLocation()).normalize().multiply(0.15);
        Location locationLeft = getPlayer().getLocation().add(vectorLeft);
        Location locationRight = getPlayer().getLocation().add(vectorRight);
        locationLeft.setY(getPlayer().getLocation().getY());
        locationRight.setY(getPlayer().getLocation().getY());
        UtilParticles.drawParticlesWithOffset(Particles.FALLING_LAVA,0,0,0, locationLeft, 1);
        UtilParticles.drawParticlesWithOffset(Particles.LAVA,0,0,0, locationLeft, 1);
        UtilParticles.drawParticlesWithOffset(Particles.FALLING_LAVA,0,0,0, locationRight, 1);
        UtilParticles.drawParticlesWithOffset(Particles.LAVA,0,0,0, locationRight, 1);
    }

    public static Vector getLeftVector(Location loc) {
        final float newX = (float) (loc.getX() + (1 * Math.cos(Math.toRadians(loc.getYaw() + 0))));
        final float newZ = (float) (loc.getZ() + (1 * Math.sin(Math.toRadians(loc.getYaw() + 0))));
        return new Vector(newX - loc.getX(), 0, newZ - loc.getZ());
    }

    public static Vector getRightVector(Location loc) {
        final float newX = (float) (loc.getX() + (-1 * Math.cos(Math.toRadians(loc.getYaw() + 0))));
        final float newZ = (float) (loc.getZ() + (-1 * Math.sin(Math.toRadians(loc.getYaw() + 0))));
        return new Vector(newX - loc.getX(), 0, newZ - loc.getZ());
    }
}
