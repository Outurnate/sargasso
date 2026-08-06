/* (C)2026 */
package com.outurnate.sargasso.registry;

import com.outurnate.sargasso.Config;
import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.effects.AddGeneratorConsumeEffect;
import com.outurnate.sargasso.item.BedrockCreamItem;
import com.outurnate.sargasso.item.EnergyItem;
import com.outurnate.sargasso.item.LightningBottleItem;
import com.outurnate.sargasso.item.PersonalVoltmeterItem;
import com.outurnate.sargasso.item.RedstoneEMPItem;
import com.outurnate.sargasso.item.SnowBootsItem;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.Equippable;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.event.entity.living.EnderManAngerEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.transfer.energy.ItemAccessEnergyHandler;

@EventBusSubscriber(modid = SuperSargassoSea.MODID)
public class LocalItems {
    public static final DeferredRegister.Items REGISTRY = DeferredRegister
        .createItems(SuperSargassoSea.MODID);

    public static final DeferredItem<BlockItem> FLOTSAM = REGISTRY.registerSimpleBlockItem(
        "flotsam",
        LocalBlocks.FLOTSAM);

    public static final DeferredItem<BlockItem> DEBRIS = REGISTRY.registerSimpleBlockItem(
        "debris",
        LocalBlocks.DEBRIS);

    public static final DeferredItem<BlockItem> TOASTER = REGISTRY.registerSimpleBlockItem(
        "toaster",
        LocalBlocks.TOASTER);

    public static final DeferredItem<BlockItem> SHOCK_THERAPIST = REGISTRY.registerSimpleBlockItem(
        "shock_therapist",
        LocalBlocks.SHOCK_THERAPIST);

    public static final DeferredItem<BlockItem> PYLON = REGISTRY.registerSimpleBlockItem(
        "pylon",
        LocalBlocks.PYLON,
        p -> p.component(DataComponents.EQUIPPABLE, pylon()));

    public static final DeferredItem<Item> BEDROCK_SLOP = REGISTRY.registerSimpleItem(
        "bedrock_slop",
        p -> p.food(
            new FoodProperties.Builder()
                .alwaysEdible()
                .nutrition(1)
                .saturationModifier(18.0F)
                .build(),
            Consumable.builder()
                .onConsume(
                    new ApplyStatusEffectsConsumeEffect(
                        new MobEffectInstance(
                            MobEffects.SLOWNESS,
                            1200,
                            1)))
                .build()));

    public static final DeferredItem<Item> BREADROCK = REGISTRY.registerSimpleItem(
        "breadrock",
        p -> p.food(
            new FoodProperties.Builder()
                .alwaysEdible()
                .nutrition(2)
                .saturationModifier(0.2F)
                .build(),
            Consumable.builder()
                .onConsume(
                    new ApplyStatusEffectsConsumeEffect(
                        new MobEffectInstance(
                            MobEffects.SLOWNESS,
                            1200,
                            0)))
                .build()));

    public static final DeferredItem<Item> BEDROCK_CREAM = REGISTRY.registerItem(
        "bedrock_cream",
        BedrockCreamItem::new,
        p -> p);

    public static final DeferredItem<Item> LIGHTNING_BOTTLE = REGISTRY.registerItem(
        "lightning_bottle",
        LightningBottleItem::new,
        p -> p
            .useCooldown(2.0F));

    public static final DeferredItem<Item> POTATO_BATTERY = REGISTRY.registerSimpleItem(
        "potato_battery",
        p -> p.food(
            new FoodProperties.Builder()
                .alwaysEdible()
                .nutrition(1)
                .saturationModifier(0.2F)
                .build(),
            Consumable.builder()
                .onConsume(new AddGeneratorConsumeEffect())
                .build()));

    public static final DeferredItem<Item> PERSONAL_VOLTMETER = REGISTRY.registerItem(
        "personal_voltmeter",
        PersonalVoltmeterItem::new,
        p -> p
            .useCooldown(1.0F));

    public static final DeferredItem<Item> AA_BATTERY = REGISTRY.registerItem(
        "aa_battery",
        EnergyItem::new,
        p -> p
            .component(LocalDataComponentTypes.ENERGY.get(), getBatteryCapacity()));

    public static final DeferredItem<Item> RECHARGABLE_AA_BATTERY = REGISTRY.registerItem(
        "rechargable_aa_battery",
        EnergyItem::new,
        p -> p
            .component(LocalDataComponentTypes.ENERGY.get(), 0));

    public static final DeferredItem<Item> STUDDED_LEATHER_HELMET = REGISTRY.registerItem(
        "studded_leather_helmet",
        props -> new Item(props.humanoidArmor(LocalArmorMaterials.STUDDED_LEATHER, ArmorType.HELMET)));
    public static final DeferredItem<Item> STUDDED_LEATHER_CHESTPLATE = REGISTRY.registerItem(
        "studded_leather_chestplate",
        props -> new Item(props.humanoidArmor(LocalArmorMaterials.STUDDED_LEATHER, ArmorType.CHESTPLATE)));
    public static final DeferredItem<Item> STUDDED_LEATHER_LEGGINGS = REGISTRY.registerItem(
        "studded_leather_leggings",
        props -> new Item(props.humanoidArmor(LocalArmorMaterials.STUDDED_LEATHER, ArmorType.LEGGINGS)));
    public static final DeferredItem<Item> STUDDED_LEATHER_BOOTS = REGISTRY.registerItem(
        "studded_leather_boots",
        props -> new SnowBootsItem(
            props.humanoidArmor(LocalArmorMaterials.STUDDED_LEATHER, ArmorType.BOOTS)));

    public static final DeferredItem<Item> STUDDED_LEATHER_UPGRADE_SMITHING_TEMPLATE = REGISTRY
        .registerSimpleItem(
            "studded_leather_upgrade_smithing_template");

    public static final DeferredItem<Item> FOX_EARS = REGISTRY.registerItem(
        "fox_ears",
        props -> new Item(
            props
                .stacksTo(1)
                .component(
                    DataComponents.EQUIPPABLE,
                    Equippable.builder(ArmorType.HELMET.getSlot()).build())));

    public static final DeferredItem<Item> COMICALLY_TALL_FOX_EARS = REGISTRY.registerItem(
        "comically_tall_fox_ears",
        props -> new Item(
            props
                .stacksTo(1)
                .component(
                    DataComponents.EQUIPPABLE,
                    Equippable.builder(ArmorType.HELMET.getSlot()).build())));

    public static final DeferredItem<Item> RECORD_UNCHECKED = REGISTRY.registerItem(
        "unchecked",
        props -> new Item(
            props
                .stacksTo(1)
                .rarity(Rarity.RARE)
                .jukeboxPlayable(LocalJukeboxSongs.UNCHECKED)));

    public static final DeferredItem<Item> REDSTONE_EMP = REGISTRY.registerItem(
        "redstone_emp",
        RedstoneEMPItem::new,
        p -> p
            .useCooldown(2.0F));

    public static final DeferredItem<Item> QUARTER = REGISTRY.registerItem(
        "quarter",
        Item::new,
        p -> p);

    private static int getBatteryCapacity() {
        try {
            return Config.BATTERY_CAPACITY.getAsInt();
        } catch (Exception e) {
            // during datagen, our config won't be loaded
            // so just return the default
            return 10000;
        }
    }

    @SubscribeEvent
    public static void onEnderManAngerEvent(EnderManAngerEvent event) {
        event.setCanceled(event.getPlayer().getItemBySlot(EquipmentSlot.HEAD).is(PYLON));
    }

    public static Equippable pylon() {
        HolderGetter<EntityType<?>> entityGetter = BuiltInRegistries
            .acquireBootstrapRegistrationLookup(BuiltInRegistries.ENTITY_TYPE);
        return Equippable.builder(ArmorType.HELMET.getSlot())
            .setEquipSound(LocalSoundEvents.PYLON)
            .setDispensable(true)
            .setAllowedEntities(entityGetter.getOrThrow(LocalTags.CAN_WEAR_PYLON))
            .setEquipOnInteract(true)
            .setCameraOverlay(SuperSargassoSea.ID("misc/pylonblur"))
            .setEquipOnInteract(true)
            .build();
    }

    public static void register(IEventBus modEventBus) {
        REGISTRY.register(modEventBus);
    }

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerItem(
            Capabilities.Energy.ITEM,
            (itemStack, itemAccess) -> new ItemAccessEnergyHandler(
                itemAccess,
                LocalDataComponentTypes.ENERGY.get(),
                getBatteryCapacity(),
                0,
                getBatteryCapacity()),
            AA_BATTERY.get());
        event.registerItem(
            Capabilities.Energy.ITEM,
            (itemStack, itemAccess) -> new ItemAccessEnergyHandler(
                itemAccess,
                LocalDataComponentTypes.ENERGY.get(),
                getBatteryCapacity(),
                getBatteryCapacity(),
                getBatteryCapacity()),
            RECHARGABLE_AA_BATTERY.get());
    }
}
