package dev.vatuu.vertex.entities.armorstand;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;

public final class ArmorStandBuilder {

    private final ArmorStand armorStand;

    public ArmorStandBuilder(World w) { w.spawn; }
    public ArmorStandBuilder(Player w) { armorStand = EntityType.ARMOR_STAND.create(NMSUtils.level(w.getWorld())); }

    public ArmorStandBuilder pos(Location loc) {
        armorStand.teleport(loc);
        return this;
    }

    public ArmorStandBuilder small(boolean small) {
        armorStand.setSmall(small);
        return this;
    }

    public ArmorStandBuilder marker(boolean marker) {
        armorStand.setMarker(marker);
        return this;
    }

    public ArmorStandBuilder invisible(boolean invisible) {
        armorStand.setInvisible(invisible);
        return this;
    }

    public ArmorStandBuilder basePlate(boolean basePlate) {
        armorStand.setBasePlate(basePlate);
        return this;
    }

    public ArmorStandBuilder gravity(boolean gravity) {
        armorStand.setGravity(gravity);
        return this;
    }

    public ArmorStandBuilder zeroPose() {
        armorStand.set
        armorStand.setYBodyRot(0);
        armorStand.setYHeadRot(0);
        armorStand.setYRot(0);
        return headPose(0, 0, 0)
                .bodyPose(0, 0, 0)
                .leftArmPose(0, 0, 0)
                .rightArmPose(0, 0, 0)
                .leftLegPose(0, 0, 0)
                .rightLegPose(0, 0, 0);
    }

    public ArmorStandBuilder headPose(float x, float y, float z) { return headPose(x, y, z, true); }
    public ArmorStandBuilder headPose(float x, float y, float z, boolean degrees) {
        armorStand.setHeadPose(getAngles(x, y, z, degrees));
        return this;
    }

    public ArmorStandBuilder bodyPose(float x, float y, float z) { return bodyPose(x, y, z, true); }
    public ArmorStandBuilder bodyPose(float x, float y, float z, boolean degrees) {
        armorStand.setBodyPose(getAngles(x, y, z, degrees));
        return this;
    }

    public ArmorStandBuilder leftArmPose(float x, float y, float z) { return leftArmPose(x, y, z, true); }
    public ArmorStandBuilder leftArmPose(float x, float y, float z, boolean degrees) {
        armorStand.setLeftArmPose(getAngles(x, y, z, degrees));
        return this;
    }

    public ArmorStandBuilder rightArmPose(float x, float y, float z) { return rightArmPose(x, y, z, true); }
    public ArmorStandBuilder rightArmPose(float x, float y, float z, boolean degrees) {
        armorStand.setRightArmPose(getAngles(x, y, z, degrees));
        return this;
    }

    public ArmorStandBuilder leftLegPose(float x, float y, float z) { return leftLegPose(x, y, z, true); }
    public ArmorStandBuilder leftLegPose(float x, float y, float z, boolean degrees) {
        armorStand.setLeftLegPose(getAngles(x, y, z, degrees));
        return this;
    }

    public ArmorStandBuilder rightLegPose(float x, float y, float z) { return rightLegPose(x, y, z, true); }
    public ArmorStandBuilder rightLegPose(float x, float y, float z, boolean degrees) {
        armorStand.setRightArmPose(getAngles(x, y, z, degrees));
        return this;
    }

    public ArmorStandBuilder name(String name) { return name(name, true); }
    public ArmorStandBuilder name(TextComponent name) { return name(name, true); }
    public ArmorStandBuilder name(String name, boolean visible) { return name(new TextComponent(name), visible); }

    public ArmorStandBuilder name(TextComponent name, boolean visible) {
        armorStand.setCustomName(name);
        armorStand.setCustomNameVisible(visible);
        return this;
    }

    public ArmorStandBuilder helmet(ItemStack stack) { return helmet(stack, false); }
    public ArmorStandBuilder helmet(ItemStack stack, boolean silent) { return equipment(EquipmentSlot.HEAD, stack, silent); }
    public ArmorStandBuilder chestPlate(ItemStack stack) { return chestPlate(stack, false); }
    public ArmorStandBuilder chestPlate(ItemStack stack, boolean silent) { return equipment(EquipmentSlot.CHEST, stack, silent); }
    public ArmorStandBuilder leggings(ItemStack stack) { return leggings(stack, false); }
    public ArmorStandBuilder leggings(ItemStack stack, boolean silent) { return equipment(EquipmentSlot.LEGS, stack, silent); }
    public ArmorStandBuilder boots(ItemStack stack) { return boots(stack, false); }
    public ArmorStandBuilder boots(ItemStack stack, boolean silent) { return equipment(EquipmentSlot.FEET, stack, silent); }
    public ArmorStandBuilder mainHand(ItemStack stack) { return mainHand(stack, false); }
    public ArmorStandBuilder mainHand(ItemStack stack, boolean silent) { return equipment(EquipmentSlot.HAND, stack, silent); }
    public ArmorStandBuilder offHand(ItemStack stack) { return offHand(stack, false); }
    public ArmorStandBuilder offHand(ItemStack stack, boolean silent) { return equipment(EquipmentSlot.OFF_HAND, stack, silent); }

    @SuppressWarnings("ConstantConditions")
    private ArmorStandBuilder equipment(EquipmentSlot slot, ItemStack stack, boolean silent) {
        armorStand.getEquipment().setItem(slot, stack, silent);
        return this;
    }

    public ArmorStand build() {
        return armorStand;
    }

    public static ArmorStand nametagArmorStand(TextComponent component, Location loc) {
        return new ArmorStandBuilder(loc.getWorld())
                .marker(true).gravity(false)
                .invisible(true).basePlate(false).zeroPose()
                .name(component, true).pos(loc)
                .build();
    }

    private EulerAngle getAngles(double x, double y, double z, boolean degrees) {
        if(degrees)
            return new EulerAngle(Math.toRadians(x), Math.toRadians(y), Math.toRadians(z));
        else
            return new EulerAngle(x, y, z);
    }
}
