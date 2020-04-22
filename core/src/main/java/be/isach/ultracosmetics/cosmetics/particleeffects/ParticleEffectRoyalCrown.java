package be.isach.ultracosmetics.cosmetics.particleeffects;

import be.isach.ultracosmetics.UltraCosmetics;
import be.isach.ultracosmetics.cosmetics.type.ParticleEffectType;
import be.isach.ultracosmetics.player.UltraPlayer;
import be.isach.ultracosmetics.util.MathUtils;
import be.isach.ultracosmetics.util.UtilParticles;
import org.bukkit.Location;

/**
 * Represents an instance of a royal crown summoned by a player.
 *
 * @author SinfulMentality
 * @since 04-19-20
 */
public class ParticleEffectRoyalCrown extends ParticleEffect {

    private int step = 0;

    public ParticleEffectRoyalCrown(UltraPlayer owner, UltraCosmetics ultraCosmetics) {
        super(ultraCosmetics, owner, ParticleEffectType.valueOf("royalcrown"));
        this.ignoreMove = true; // ignoreMove is actually stopping the animation on moving if false... change naming
    }

    @Override
    public void onUpdate() {
        Location location = getPlayer().getEyeLocation().add(0, 0.8, 0);
        float radius = 0.35f; // refresh radius value
        int numParticlesBase = 10;
        int numCrownSpokes = 5;
        int numParticlesInnerPadding = 5;
        int y = 0;

        // Rotation logic
        if(step == 720) step = 0; // at step = 720, (step * PI / 360.0f) = 2PI, equivalent to 0
        step += 4;

        // Draw inner circle of the red padding
        drawCrownPiece((radius-0.05f)/2, y + 0.2f, location, numParticlesInnerPadding,
                (inc, step, modifier) -> (float) (inc), 0, 0, 255, 0, 0);
        drawGoldTop(radius - 0.35f, y + 0.5f, location);
        // Draw gold circle for the crown base
        drawCrownPiece(radius, y, location, numParticlesBase,
                (inc, step, modifier) -> (float) ((inc) + (step * Math.PI / 360.0f)), step, 0, 255, 215, 0);
        drawCrownSpokes(radius, y, location, numCrownSpokes, step);
        drawCrownPadding(radius, y, location, numCrownSpokes);
    }

    private interface AngleCalc {
        float calc(double inc, float step, double modifier);
    }

    private void drawCrownPiece(float radius, float y, Location location, int numParticles, AngleCalc angleCalculation, int step, double modifier, int red, int green, int blue) {
        for (int i = 0; i < numParticles; i++) {
            double inc = (2 * Math.PI) / numParticles;
            float angle = angleCalculation.calc(i * inc, step, modifier);
            float x = MathUtils.cos(angle) * radius;
            float z = MathUtils.sin(angle) * radius;
            location.add(x, y, z);
            UtilParticles.display(red, green, blue, location);
            location.subtract(x, y, z);
        }
    }

    // Draw each spoke of the crown, builds the spokes layer by layer
    private void drawCrownSpokes(float radius, float y, Location location, int numParticles, int step) {
        AngleCalc angleCalc = (inc, angleStep, modifier) -> (float) (((inc) + (angleStep * Math.PI / 360.0f)));
        // Draw crown jewels at the base of each spoke
        drawCrownPiece(radius, y, location, numParticles, angleCalc, step, 0, 0, 215, 255); // lowest point of spoke has a jewel
        radius += 0.09f;
        y += 0.2f;
        // Draws a single layer of all the crown spokes
        drawCrownPiece(radius, y, location, numParticles, angleCalc, step, 0, 255, 215, 0);
        y += 0.2f;
        drawCrownPiece(radius, y, location, numParticles, angleCalc, step, 0, 255, 215, 0);
        radius -= 0.18f;
        y += 0.05f;
        drawCrownPiece(radius, y, location, numParticles, angleCalc, step, 0, 255, 215, 0);
        radius -= 0.18f;
        y -= 0.05f;
        drawCrownPiece(radius, y, location, numParticles, angleCalc, step, 0, 255, 215, 0);
    }

    // Draw the red padding of the crown, layer by layer
    private void drawCrownPadding(float radius, float y, Location location, int numParticles) {
        AngleCalc angleCalc = (double inc, float step, double modifier) -> (float) (((inc) + (step * Math.PI / 360.0f)) + modifier);
        radius += 0.09f;
        y += 0.2f;
        drawCrownPiece(radius, y, location, numParticles, angleCalc,  0, (Math.PI) / numParticles, 255, 0, 0);
        y += 0.2f;
        drawCrownPiece(radius, y, location, numParticles, angleCalc,  0, (Math.PI) / numParticles, 255, 0, 0);
    }

    // Draw the crown topper
    private void drawGoldTop(float radius, float y, Location location) {
        location.add(0, y + 0.2, 0);
        UtilParticles.display(255, 215, 0, location);
        location.subtract(0, y + 0.2, 0);
    }
}
