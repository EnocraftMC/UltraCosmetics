package be.isach.ultracosmetics.util;

import be.isach.ultracosmetics.UltraCosmeticsData;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.awt.*;

/**
 * Created by sacha on 07/08/15.
 */
public class UtilParticles {

    public final static int DEF_RADIUS = 128;

    public static void drawParticleLine(Location from, Location to, Particles effect, int particles, Color color) {
        drawParticleLine(from, to, effect, particles, color.getRed(), color.getGreen(), color.getBlue());
    }

    public static void drawParticleLine(Location from, Location to, Particles effect, int particles, int r, int g, int b) {
        Location location = from.clone();
        Location target = to.clone();
        Vector link = target.toVector().subtract(location.toVector());
        float length = (float) link.length();
        link.normalize();

        float ratio = length / particles;
        Vector v = link.multiply(ratio);
        Location loc = location.clone().subtract(v);
        int step = 0;
        for (int i = 0; i < particles; i++) {
            if (step >= (double) particles)
                step = 0;
            step++;
            loc.add(v);
            if (effect == Particles.REDSTONE)
                effect.display(new Particles.OrdinaryColor(r, g, b), loc, 128);
            else
                effect.display(0, 0, 0, 0, 1, loc, 128);
        }
    }

    public static void playHelix(final Location loc, final float i, final Particles effect) {
        BukkitRunnable runnable = new BukkitRunnable() {
            double radius = 0;
            double step;
            double y = loc.getY();
            Location location = loc.clone().add(0, 3, 0);

            @Override
            public void run() {
                double inc = (2 * Math.PI) / 50;
                double angle = step * inc + i;
                Vector v = new Vector();
                v.setX(Math.cos(angle) * radius);
                v.setZ(Math.sin(angle) * radius);
                if (effect == Particles.REDSTONE)
                    drawColoredDust(0, 0, 255, location);
                else
                    drawParticle(effect, location);
                location.subtract(v);
                location.subtract(0, 0.1d, 0);
                if (location.getY() <= y) {
                    cancel();
                }
                step += 4;
                radius += 1 / 50f;
            }
        };
        runnable.runTaskTimer(UltraCosmeticsData.get().getPlugin(), 0, 1);
    }

    public static void drawParticlesWithSpeed(Particles effect, Location location, int amount, float speed) {
        effect.display(0, 0, 0, speed, amount, location, 128);
    }

    public static void drawParticles(Particles effect, Location location, int amount) {
        effect.display(0, 0, 0, 0, amount, location, 128);
    }

    public static void drawParticle(Particles effect, Location location) {
        drawParticles(effect, location, 1);
    }

    public static void drawParticlesWithOffset(Particles effect, double offsetX, double offsetY, double offsetZ, Location location, int amount) {
        effect.display((float) offsetX, (float) offsetY, (float) offsetZ, 0f, amount, location, 128);
    }

    public static void drawColoredParticles(Particles effect, int red, int green, int blue, Location location, int amount) {
        for (int i = 0; i < amount; i++)
            effect.display(new Particles.OrdinaryColor(red, green, blue), location, DEF_RADIUS);
    }

    public static void drawColoredParticlesWithSize(Particles effect, int red, int green, int blue, Location location, int amount, float size) {
        for (int i = 0; i < amount; i++)
            effect.display(new Particles.OrdinaryColor(red, green, blue), location, DEF_RADIUS, size);
    }

    public static void drawColoredParticle(Particles effect, int red, int green, int blue, Location location) {
        drawColoredParticles(effect, red, green, blue, location, 1);
    }

    public static void drawColoredDust(int red, int green, int blue, Location location) {
        drawColoredParticles(Particles.REDSTONE, red, green, blue, location, 1);
    }

    public static void drawColoredDustWithSize(int red, int green, int blue, Location location, float size) {
        drawColoredParticlesWithSize(Particles.REDSTONE, red, green, blue, location, 1, size);
    }
}
