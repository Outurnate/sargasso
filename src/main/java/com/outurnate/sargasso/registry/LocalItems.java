/* (C)2026 */
package com.outurnate.sargasso.registry;

import com.outurnate.sargasso.Config;
import com.outurnate.sargasso.Rational;
import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.effects.AddGeneratorConsumeEffect;
import com.outurnate.sargasso.item.BedrockCreamItem;
import com.outurnate.sargasso.item.EnergyItem;
import com.outurnate.sargasso.item.LightningBottleItem;
import com.outurnate.sargasso.item.PersonalVoltmeterItem;

import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import net.minecraft.world.item.equipment.ArmorMaterials;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.Equippable;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.event.entity.living.EnderManAngerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.energy.EnergyHandlerUtil;
import net.neoforged.neoforge.transfer.energy.ItemAccessEnergyHandler;
import net.neoforged.neoforge.transfer.energy.SimpleEnergyHandler;

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

    public static final DeferredItem<BlockItem> PYLON = REGISTRY.registerSimpleBlockItem(
        "pylon",
        LocalBlocks.PYLON,
        p -> p
            .component(
                DataComponents.EQUIPPABLE,
                Equippable.builder(ArmorType.HELMET.getSlot())
                    .setDispensable(true)
                    .setEquipSound(LocalSoundEvents.PYLON)
                    .setCameraOverlay(SuperSargassoSea.ID("misc/pylonblur")).build()));

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
        props -> new Item(props.humanoidArmor(LocalArmorMaterials.STUDDED_LEATHER, ArmorType.BOOTS)));

    public static final DeferredItem<Item> STUDDED_LEATHER_UPGRADE_SMITHING_TEMPLATE = REGISTRY
        .registerSimpleItem(
            "studded_leather_upgrade_smithing_template");

    public static final DeferredItem<Item> BLACK_FOX_EARS = REGISTRY.registerItem(
        "black_fox_ears",
        props -> new Item(
            props
                .component(
                    DataComponents.EQUIPPABLE,
                    Equippable.builder(ArmorType.HELMET.getSlot()).setAsset(ArmorMaterials.LEATHER.assetId())
                        .build())));
    public static final DeferredItem<Item> TWO_COLOR_FOX_EARS = REGISTRY.registerItem(
        "two_color_fox_ears",
        props -> new Item(
            props
                .component(
                    DataComponents.EQUIPPABLE,
                    Equippable.builder(ArmorType.HELMET.getSlot()).setAsset(ArmorMaterials.LEATHER.assetId())
                        .build())));
    public static final DeferredItem<Item> COMICALLY_TALL_FOX_EARS = REGISTRY.registerItem(
        "comically_tall_fox_ears",
        props -> new Item(
            props
                .component(
                    DataComponents.EQUIPPABLE,
                    Equippable.builder(ArmorType.HELMET.getSlot()).setAsset(ArmorMaterials.LEATHER.assetId())
                        .build())));
    public static final DeferredItem<Item> ORANGE_FOX_EARS = REGISTRY.registerItem(
        "orange_fox_ears",
        props -> new Item(
            props
                .component(
                    DataComponents.EQUIPPABLE,
                    Equippable.builder(ArmorType.HELMET.getSlot()).setAsset(ArmorMaterials.LEATHER.assetId())
                        .build())));

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

    @SubscribeEvent
    public static void onPostPlayerTickEvent(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            if (serverPlayer.hasData(LocalAttachmentTypes.GENERATOR_COUNT)) {
                Rational generatorRatio = serverPlayer.getData(LocalAttachmentTypes.GENERATOR_COUNT);
                if (generatorRatio.numerator() == 0
                    && (serverPlayer.level().getGameTime() % generatorRatio.denominator()) != 0) {
                    return;
                }
                int generatedAmount = generatorRatio.numerator();

                // transfer power into inventory items
                // SlotAccess?
                EnergyHandler generatedPower = new SimpleEnergyHandler(
                    generatedAmount,
                    0,
                    generatedAmount,
                    generatedAmount);
                Inventory inventory = serverPlayer.getInventory();
                for (ItemStack itemStack : inventory) {
                    if (!itemStack.isEmpty()) {
                        ItemAccess slot = ItemAccess.forStack(itemStack);
                        EnergyHandler chargableItem = slot.getCapability(Capabilities.Energy.ITEM);
                        EnergyHandlerUtil.move(generatedPower, chargableItem, generatedAmount, null);
                        if (generatedPower.getAmountAsInt() == 0) {
                            break;
                        }
                        inventory.setChanged();
                        serverPlayer.containerMenu.broadcastChanges();
                    }
                }
            }
        }
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
