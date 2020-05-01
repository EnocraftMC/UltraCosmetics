package be.isach.ultracosmetics.cosmetics.particleeffects;

import be.isach.ultracosmetics.UltraCosmetics;
import be.isach.ultracosmetics.UltraCosmeticsData;
import be.isach.ultracosmetics.cosmetics.type.ParticleEffectType;
import be.isach.ultracosmetics.player.UltraPlayer;
import be.isach.ultracosmetics.util.*;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.util.Vector;

/**
 * Represents an instance of frozen walk particles summoned by a player.
 *
 * @author iSach
 * @since 10-12-2015
 */
public class ParticleEffectFrozenWalk extends ParticleEffect {

    public ParticleEffectFrozenWalk(UltraPlayer owner, UltraCosmetics ultraCosmetics) {
        super(ultraCosmetics, owner, ParticleEffectType.valueOf("frozenwalk"));
    }

    @Override
    public void onUpdate() {
        Vector vectorLeft = getLeftVector(getPlayer().getLocation()).normalize().multiply(0.15);
        Vector vectorRight = getRightVector(getPlayer().getLocation()).normalize().multiply(0.15);
        Location locationLeft = getPlayer().getLocation().add(vectorLeft);
        Location locationRight = getPlayer().getLocation().add(vectorRight);
        locationLeft.setY(getPlayer().getLocation().getY());
        locationRight.setY(getPlayer().getLocation().getY());
        if (UltraCosmeticsData.get().getServerVersion().compareTo(ServerVersion.v1_14_R1) >= 0) {
            locationLeft.getWorld().spawnParticle(Particle.ITEM_CRACK, locationLeft, 0, 0, 0, 0, 0, UCMaterial.BLUE_ICE.parseItem());
            locationLeft.getWorld().spawnParticle(Particle.ITEM_CRACK, locationRight, 0, 0, 0, 0, 0, UCMaterial.BLUE_ICE.parseItem());
            UtilParticles.drawColoredDustWithSize(255,255,255, getPlayer().getLocation().add(MathUtils.randomDouble(-0.8, 0.8), 1 + MathUtils.randomDouble(-0.8, 0.8), MathUtils.randomDouble(-0.8, 0.8)), 0.6f);
            UtilParticles.drawColoredDustWithSize(255,255,255, getPlayer().getLocation().add(MathUtils.randomDouble(-0.8, 0.8), 1 + MathUtils.randomDouble(-0.8, 0.8), MathUtils.randomDouble(-0.8, 0.8)), 0.6f);
        }
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
