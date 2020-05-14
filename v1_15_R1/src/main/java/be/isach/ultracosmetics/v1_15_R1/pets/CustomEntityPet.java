package be.isach.ultracosmetics.v1_15_R1.pets;

import be.isach.ultracosmetics.UltraCosmetics;
import be.isach.ultracosmetics.cosmetics.pets.IPetCustomEntity;
import be.isach.ultracosmetics.cosmetics.pets.Pet;
import be.isach.ultracosmetics.cosmetics.type.PetType;
import be.isach.ultracosmetics.player.UltraPlayer;
import be.isach.ultracosmetics.util.EntitySpawningManager;
import be.isach.ultracosmetics.v1_15_R1.customentities.Sans;
import net.minecraft.server.v1_15_R1.Entity;
import org.bukkit.Difficulty;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftEntity;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;

/**
 * @author RadBuilder
 */
public abstract class CustomEntityPet extends Pet {

    /**
     * Custom Entity.
     */
    public IPetCustomEntity customEntity;

    public CustomEntityPet(UltraPlayer owner, UltraCosmetics ultraCosmetics, PetType petType, ItemStack dropItem) {
        super(owner, ultraCosmetics, petType, dropItem);

    }

    @Override
    public void onEquip() {

        if (getOwner().getCurrentPet() != null)
            getOwner().removePetWithoutSaving();

        getOwner().setCurrentPet(this);

        if (this instanceof PetSans) { // TODO: Generalize this with a CustomEntityType class
            EntitySpawningManager.setBypass(true);
            customEntity = new Sans(getUltraCosmetics(), getOwner(), this);
            customEntity.equip();
            EntitySpawningManager.setBypass(false);
        }
        if(getPlayer().getWorld().getDifficulty() == Difficulty.PEACEFUL) {
            getOwner().sendMessage("§c§lUltraCosmetics > Monsters can't spawn here!");
            getOwner().removePet();
        }
    }



    @Override
    protected void removeEntity() {
        customEntity.remove();
    }

    @Override
    public boolean isCustomEntity() {
        return true;
    }

    public Entity getCustomEntity() {
        return ((CraftEntity) customEntity.getEntity()).getHandle();
    }

    @Override
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        // Custom entities handle teleport in their follow AI
    }

}
