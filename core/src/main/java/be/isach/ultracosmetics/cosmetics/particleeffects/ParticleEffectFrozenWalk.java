package be.isach.ultracosmetics.cosmetics.particleeffects;

import be.isach.ultracosmetics.UltraCosmetics;
import be.isach.ultracosmetics.UltraCosmeticsData;
import be.isach.ultracosmetics.cosmetics.type.ParticleEffectType;
import be.isach.ultracosmetics.player.UltraPlayer;
import be.isach.ultracosmetics.util.MathUtils;
import be.isach.ultracosmetics.util.ServerVersion;
import be.isach.ultracosmetics.util.UCMaterial;
import be.isach.ultracosmetics.util.UtilParticles;
import org.bukkit.Location;
import org.bukkit.Particle;

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
        Location locationLeft = UtilParticles.getLeftLegLocation(getPlayer().getLocation());
        Location locationRight = UtilParticles.getRightLegLocation(getPlayer().getLocation());

        if (UltraCosmeticsData.get().getServerVersion().compareTo(ServerVersion.v1_14_R1) >= 0) {
            locationLeft.getWorld().spawnParticle(Particle.ITEM_CRACK, locationLeft, 0, 0, 0, 0, 0, UCMaterial.BLUE_ICE.parseItem());
            locationLeft.getWorld().spawnParticle(Particle.ITEM_CRACK, locationRight, 0, 0, 0, 0, 0, UCMaterial.BLUE_ICE.parseItem());
            UtilParticles.drawColoredDustWithSize(255,255,255, getPlayer().getLocation().add(MathUtils.randomDouble(-0.8, 0.8), 1 + MathUtils.randomDouble(-0.8, 0.8), MathUtils.randomDouble(-0.8, 0.8)), 0.6f);
            UtilParticles.drawColoredDustWithSize(255,255,255, getPlayer().getLocation().add(MathUtils.randomDouble(-0.8, 0.8), 1 + MathUtils.randomDouble(-0.8, 0.8), MathUtils.randomDouble(-0.8, 0.8)), 0.6f);
        }
    }
}
