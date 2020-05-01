package be.isach.ultracosmetics.cosmetics.particleeffects;

import be.isach.ultracosmetics.UltraCosmetics;
import be.isach.ultracosmetics.UltraCosmeticsData;
import be.isach.ultracosmetics.cosmetics.type.ParticleEffectType;
import be.isach.ultracosmetics.player.UltraPlayer;
import be.isach.ultracosmetics.util.ServerVersion;
import be.isach.ultracosmetics.util.UCMaterial;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

/**
 * Represents an instance of wounded particles summoned by a player.
 *
 * @author SinfulMentality
 * @since 04-30-20
 */
public class ParticleEffectWounded extends ParticleEffect {

    public ParticleEffectWounded(UltraPlayer owner, UltraCosmetics ultraCosmetics) {
        super(ultraCosmetics, owner, ParticleEffectType.valueOf("wounded"));
    }

    /**
     * Called when player is idle
     */
    @Override
    public void onUpdate() {
        Vector vectorLeftShoulder = getLeftShoulderVector(getPlayer().getLocation());
        Location leftShoulder = getPlayer().getLocation().add(vectorLeftShoulder);
        if (UltraCosmeticsData.get().getServerVersion().compareTo(ServerVersion.v1_14_R1) >= 0) {
            leftShoulder.getWorld().spawnParticle(Particle.ITEM_CRACK, leftShoulder, 2, 0, 0, 0, 0, UCMaterial.BARRIER.parseItem());
            leftShoulder.getWorld().spawnParticle(Particle.ITEM_CRACK, getPlayer().getLocation(), 20, 0.2, 0, 0.2, 0, UCMaterial.BARRIER.parseItem());
        }
    }

    /**
     * Called when player is moving
     * @param player the player
     */
    public static void onUpdateMoving(Player player) {
        Vector vectorLeftShoulder = getLeftShoulderVector(player.getLocation());
        Location leftShoulder = player.getLocation().add(vectorLeftShoulder);
        if (UltraCosmeticsData.get().getServerVersion().compareTo(ServerVersion.v1_14_R1) >= 0) {
            leftShoulder.getWorld().spawnParticle(Particle.ITEM_CRACK, leftShoulder, 2, 0, 0, 0, 0, UCMaterial.BARRIER.parseItem());
        }
    }

    /**
     * Gets the vector pointing to the player's left shoulder
     * @param loc player's location
     * @return vector pointing to the player's left shoulder
     */
    public static Vector getLeftShoulderVector(Location loc) {
        final float newX = (float) (0.3f * (Math.cos(Math.toRadians(loc.getYaw()))));
        final float newZ = (float) (0.3f * (Math.sin(Math.toRadians(loc.getYaw()))));
        return new Vector(newX, 1.35f, newZ);
    }
}
