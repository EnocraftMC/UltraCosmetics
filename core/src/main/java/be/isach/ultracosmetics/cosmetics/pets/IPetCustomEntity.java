package be.isach.ultracosmetics.cosmetics.pets;

import org.bukkit.entity.Entity;

/**
 * Custom pet entity interface.
 *
 * @author iSach
 * @since 03-06-2016
 */
public interface IPetCustomEntity {

    IPetCustomEntity getEntity();

    public void remove();

    public void equip();
}
