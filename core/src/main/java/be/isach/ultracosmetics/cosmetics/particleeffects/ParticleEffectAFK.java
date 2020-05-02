package be.isach.ultracosmetics.cosmetics.particleeffects;

import be.isach.ultracosmetics.UltraCosmetics;
import be.isach.ultracosmetics.cosmetics.type.ParticleEffectType;
import be.isach.ultracosmetics.player.UltraPlayer;
import be.isach.ultracosmetics.util.Particles;
import be.isach.ultracosmetics.util.UtilParticles;
import org.bukkit.Location;

/**
 * Represents an instance of AFK particles summoned when a player goes AFK.
 *
 * @author SinfulMentality
 * @since 04-19-20
 */
public class ParticleEffectAFK extends ParticleEffect {

    public ParticleEffectAFK(UltraPlayer owner, UltraCosmetics ultraCosmetics) {
        super(ultraCosmetics, owner, ParticleEffectType.valueOf("afk"));
        this.stoponMove = true;
    }

    @Override
    public void onUpdate() {
        UtilParticles.drawParticle(Particles.BARRIER, getPlayer().getLocation().add(0, 2.5f, 0));
    }

}
