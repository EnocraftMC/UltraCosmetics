package be.isach.ultracosmetics.v1_15_R1;

import be.isach.ultracosmetics.cosmetics.pets.Pet;
import be.isach.ultracosmetics.v1_15_R1.pets.PetPumpling;
import be.isach.ultracosmetics.v1_15_R1.pets.PetSans;
import be.isach.ultracosmetics.version.IPets;

/**
 * @author RadBuilder
 */
public class Pets implements IPets {
    @Override
    public Class<? extends Pet> getPumplingClass() {
        return PetPumpling.class;
    }

    @Override
    public Class<? extends Pet> getSansClass() {
        return PetSans.class;
    }
}
