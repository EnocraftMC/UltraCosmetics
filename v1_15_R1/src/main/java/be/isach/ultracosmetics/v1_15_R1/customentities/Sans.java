package be.isach.ultracosmetics.v1_15_R1.customentities;

import be.isach.ultracosmetics.UltraCosmetics;
import be.isach.ultracosmetics.cosmetics.pets.IPetCustomEntity;
import be.isach.ultracosmetics.player.UltraPlayer;
import be.isach.ultracosmetics.util.ItemFactory;
import be.isach.ultracosmetics.util.UtilParticles;
import be.isach.ultracosmetics.v1_15_R1.pets.CustomEntityPet;
import net.minecraft.server.v1_15_R1.EntityArmorStand;
import net.minecraft.server.v1_15_R1.EntityTypes;
import net.minecraft.server.v1_15_R1.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftArmorStand;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;

import java.util.HashMap;

/**
 * @author SinfulMentality
 */
public class Sans extends EntityArmorStand implements IPetCustomEntity {

    private CustomEntityPet pet = null;
    private final HashMap<String, ArmorStand> parts = new HashMap<>();
    private final UltraPlayer owner;
    private final UltraCosmetics plugin;
    private int followTaskId;
    private int frame;
    private static final double MAX_FRAMES = 30;

    public Sans(UltraCosmetics uc, UltraPlayer owner, CustomEntityPet pet) {
        super(EntityTypes.ARMOR_STAND,((CraftPlayer) owner.getBukkitPlayer()).getHandle().getWorld());
        this.pet = pet;
        this.owner = owner;
        plugin = uc;
        Bukkit.getPluginManager().registerEvents(this, plugin); // Allows us to listen for player interactions
    }

    private ArmorStand getNewArmorStand(Location location, boolean visible, boolean mini) {

        ArmorStand as = location.getWorld().spawn(location, ArmorStand.class);

        disableSlots(as); // Only possible via NMS

        as.setBasePlate(false);
        as.setArms(true);
        as.setVisible(visible);
        as.setInvulnerable(true);
        as.setCanPickupItems(false);
        as.setGravity(false);
        as.setSmall(mini);

        return as;
    }

    // This function accesses net.minecraft.server
    private void disableSlots(ArmorStand as) {
        CraftArmorStand e = (CraftArmorStand) as;
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInt("DisabledSlots", 2097151); // DisabledSlots is a bit field, disable ALL slots.
        e.getHandle().a(tag);
    }

    @Override
    public void equip() {
        ArmorStand sans = getNewArmorStand(owner.getBukkitPlayer().getLocation().clone().add(0, 1.3f, 0.2), true, true);

        // Set Sans' Helmet
        sans.getEquipment().setHelmet(getSkull());

        // Set Sans' Chestplate
        ItemStack sansChest = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta sansChestMeta = (LeatherArmorMeta) sansChest.getItemMeta();
        sansChestMeta.setColor(Color.fromRGB(30,70,180));
        sansChest.setItemMeta(sansChestMeta);
        sans.getEquipment().setChestplate(sansChest);

        // Set Sans' Leggings
        sans.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));

        // Set Sans' Boots
        ItemStack sansBoots = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta sansBootsMeta = (LeatherArmorMeta) sansBoots.getItemMeta();
        sansBootsMeta.setColor(Color.fromRGB(	243, 180, 180));
        sansBoots.setItemMeta(sansBootsMeta);
        sans.getEquipment().setBoots(sansBoots);

        // Set Sans' Name Tag
        sans.setCustomName(pet.getType().getEntityName(getOwner().getBukkitPlayer()));
        if (getOwner().getPetName(pet.getType()) != null) {
            sans.setCustomName("§l" + getOwner().getPetName(pet.getType()));
        }
        sans.setCustomNameVisible(true);

        parts.put("sans", sans);
        followTaskId = follow();
    }

    private int follow() { // "AI" for following the player and animations
        return new BukkitRunnable() {
            public void run() {

            // Check owner is still valid
            if(getOwner().getBukkitPlayer() == null) {
                remove();
                return;
            }

            ArmorStand stand = parts.get("sans");
            Location playerLoc = getOwner().getBukkitPlayer().getLocation().add(0,1.3,0);
            Location standLoc = stand.getLocation();

            // Check Player is still in world
            if (!getOwner().getBukkitPlayer().getWorld().equals(stand.getWorld())) {
                stand.teleport(playerLoc);
                return;
            }

            // Teleport if too far from Player
            if (playerLoc.distance(standLoc) > 10 && getOwner().getBukkitPlayer().isOnGround()) {
                standLoc = playerLoc.clone().add(0.3f,0,0.3f);
            }

            // Move closer to Player
            else if (playerLoc.distanceSquared(standLoc) >= 8) {
                standLoc = standLoc.add(playerLoc.toVector()
                        .subtract(standLoc.toVector()).normalize().multiply(0.3));
            }

            // Find next height in floating animation
            Double height = newFloatingHeight(standLoc);
            standLoc.setY(height);

            // Find next yaw, this controls the direction the body faces
            standLoc.setYaw(getYaw(playerLoc, standLoc));

            // Teleport stand to desired location + yaw
            stand.teleport(standLoc);

            // Update head tilt + eye flame
            newHeadPosition(owner.getBukkitPlayer(), stand);

            // Advance animation by one frame
            advanceAnimationFrame();

            }
        }.runTaskTimer(plugin, 0, 1).getTaskId(); // To run the Runnable
    }

    public UltraPlayer getOwner() {
        return owner;
    }

    private ItemStack getSkull() {
        // This long string is the texture metadata for Sans, created with mineskin.org
        return ItemFactory.createSkull("ewogICJ0aW1lc3RhbXAiIDogMTU4ODYzODYxMzUzNSwKICAicHJvZmlsZUlkIiA6ICI3ZGEyYWIzYTkzY2E0OGVlODMwNDhhZmMzYjgwZTY4ZSIsCiAgInByb2ZpbGVOYW1lIiA6ICJHb2xkYXBmZWwiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWY0OGU0YzUzNzM2ODNmYWE2MTZlYzlmYjM5NzY3YmQ2M2Q5NDgxNTlkMWJhMGQ0ZTFhYmFkMTY2MDI1MjkzOCIKICAgIH0KICB9Cn0=", ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "SansSkull");
    }

    // This allows Sans to turn his body to look at the Player, find next yaw
    private float getYaw(Location playerLoc, Location standLoc) {
        return (float) Math
                .toDegrees(Math.atan2(playerLoc.getZ() - standLoc.getZ(), playerLoc.getX() - standLoc.getX()))
                - 90;
    }

    // This allows Sans to tilt his head to look at the Player, find next pitch
    private float getPitch(Location playerEyeLoc, Location standEyeLoc) {
        final float MAX_PITCH = 15f; // Can't tilt head more than 15 degrees
        float pitch = (float) (90f - Math.toDegrees(Math.acos(standEyeLoc.getY() - playerEyeLoc.getY())));
        if(pitch < -1 * MAX_PITCH || (Float.isNaN(pitch) && standEyeLoc.getY() < playerEyeLoc.getY()))
            return -1f * MAX_PITCH;
        else if(pitch > MAX_PITCH || (Float.isNaN(pitch) && standEyeLoc.getY() > playerEyeLoc.getY()))
            return MAX_PITCH;
        return pitch;
    }

    // Handles the floating animation height
    private double newFloatingHeight(Location standLoc) {
        double freq = 1f/MAX_FRAMES;
        return standLoc.getY() + (0.05f * Math.sin(2 * Math.PI * freq * frame));
    }

    // Handles the head tilting + eye flame
    private void newHeadPosition(Player player, ArmorStand stand) {
        double angle = getPitch(player.getEyeLocation(), stand.getEyeLocation());
        stand.setHeadPose(new EulerAngle(Math.toRadians(angle),0,0));

        // Find neck joint of armor stand
        Location neckJoint = stand.getEyeLocation().clone().add(0,0.3f,0);

        // Find center of both eyes (spherical to cartesian coordinates)
        float eyeYaw = (float) Math.toRadians(stand.getEyeLocation().getYaw() + 90);
        float eyePitch = (float) Math.toRadians(55 + angle);
        float eyePosX = (float) (0.33f * Math.sin(eyePitch) * Math.cos(eyeYaw));
        float eyePosZ = (float) (0.33f * Math.sin(eyePitch) * Math.sin(eyeYaw));
        float eyePosY = (float) (0.33f * Math.cos(eyePitch));
        Location eyeCenterLoc = neckJoint.clone().add(eyePosX, eyePosY, eyePosZ);

        // Offset to left eye
        eyeCenterLoc.setYaw(stand.getEyeLocation().getYaw() - 90);
        Location leftEyeLoc = eyeCenterLoc.clone().add(eyeCenterLoc.clone().getDirection().normalize().multiply(0.13f));
        UtilParticles.drawColoredDustWithSize(0,0,255, leftEyeLoc, 0.5f);
    }

    // Advance the floating animation by one frame
    private void advanceAnimationFrame() {
        if(frame == MAX_FRAMES) frame = 1;
        else frame++;
    }

    @Override
    // Allows us to pass a handle to this entity
    public IPetCustomEntity getEntity() {
        return this;
    }

    @Override
    // Clean up and remove the entity
    public void remove() {
        Bukkit.getScheduler().cancelTask(followTaskId);
        for (ArmorStand stand : parts.values()) {
            stand.remove();
        }
    }

    @EventHandler
    // Handles the breaking of custom armorstand pet from a player in creative mode
    public void onBreak(EntityDamageByEntityEvent e) {
        if(e.getEntity() instanceof ArmorStand && parts.containsValue(e.getEntity())) {
            if(e.getDamager() == getOwner().getBukkitPlayer()) { // EASTER EGG: The owner of sans can break him in one hit if in creative mode
                getOwner().removePet();
            } else {
                e.setCancelled(true);
            }
        }
    }

}
