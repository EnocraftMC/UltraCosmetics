package be.isach.ultracosmetics.cosmetics.particleeffects;

import be.isach.ultracosmetics.UltraCosmetics;
import be.isach.ultracosmetics.UltraCosmeticsData;
import be.isach.ultracosmetics.cosmetics.type.ParticleEffectType;
import be.isach.ultracosmetics.player.UltraPlayer;
import be.isach.ultracosmetics.util.ServerVersion;
import be.isach.ultracosmetics.util.UCMaterial;
import be.isach.ultracosmetics.util.UtilParticles;
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
        Location leftShoulder = UtilParticles.getLeftShoulderLocation(getPlayer().getLocation());
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
        Location leftShoulder = UtilParticles.getLeftShoulderLocation(player.getLocation());
        if (UltraCosmeticsData.get().getServerVersion().compareTo(ServerVersion.v1_14_R1) >= 0) {
            leftShoulder.getWorld().spawnParticle(Particle.ITEM_CRACK, leftShoulder, 2, 0, 0, 0, 0, UCMaterial.BARRIER.parseItem());
        }
    }

}
