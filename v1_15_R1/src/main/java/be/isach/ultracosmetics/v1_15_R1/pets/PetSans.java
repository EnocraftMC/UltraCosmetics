package be.isach.ultracosmetics.v1_15_R1.pets;

import be.isach.ultracosmetics.UltraCosmetics;
import be.isach.ultracosmetics.UltraCosmeticsData;
import be.isach.ultracosmetics.cosmetics.type.PetType;
import be.isach.ultracosmetics.player.UltraPlayer;
import be.isach.ultracosmetics.util.ItemFactory;
import be.isach.ultracosmetics.util.UCMaterial;

/**
 * @author SinfulMentality
 */
public class PetSans extends CustomEntityPet {
    public PetSans(UltraPlayer owner, UltraCosmetics ultraCosmetics) {
        super(owner, ultraCosmetics, PetType.getByName("sans"), ItemFactory.create(UCMaterial.BONE, UltraCosmeticsData.get().getItemNoPickupString()));
    }
}
