package be.isach.ultracosmetics.cosmetics.particleeffects;

import be.isach.ultracosmetics.util.Particles;
import be.isach.ultracosmetics.util.UtilParticles;

/**
 * A package to contain relevant Particle data.
 *
 * @author SinfulMentality
 * @since 04-30-20
 */
public class ParticlePackage {

    /**
     * The particle this package is for
     */
    private Particles particle;

    /**
     * How far the particle can move from its center
     */
    private float offsetX, offsetY, offsetZ;

    /**
     * The animation speed of the particle
     */
    private float speed;

    /**
     * How far players can be from the particle and still see it
     */
    private double range = UtilParticles.DEF_RADIUS;

    /**
     * The color of the particle, if the particle is colorable.
     */
    private Particles.ParticleColor color;

    public ParticlePackage(Particles particle) {
        this.particle = particle;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public double getRange() {
        return range;
    }

    public void setRange(double range) {
        this.range = range;
    }

    public Particles.ParticleColor getColor() {
        return color;
    }

    public void setColor(int red, int green, int blue) {
        if (!particle.hasProperty(Particles.ParticleProperty.COLORABLE))
            throw new RuntimeException("This particle effect is not colorable");
        else
            this.color = new Particles.OrdinaryColor(red, green, blue);
    }

}
