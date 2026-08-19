package com.outurnate.sargasso.datagen.book.things;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.outurnate.sargasso.datagen.book.BaseEntry;
import com.outurnate.sargasso.registry.LocalItems;

public class BatteriesEntry extends BaseEntry {
    public BatteriesEntry(CategoryProviderBase parent) {
        super(parent, "batteries", "Batteries", LocalItems.AA_BATTERY);
    }

    @Override
    protected void generatePages() {
        this.spotlightPage(
            "aa_battery",
            "AA Battery",
            LocalItems.AA_BATTERY,
            "This item can supply power to items in the inventory, but, once it's out of charge, it's useless");
        this.spotlightPage(
            "rechargable_aa_battery",
            "Rechargable AA Battery",
            LocalItems.RECHARGABLE_AA_BATTERY,
            "There are rechargable variants. It can be recharged with $(l:things/potato_battery)Potato Batteries");
    }
}
