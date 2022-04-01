package com.trufflez.tsbrewcraft.block.custom;

import com.trufflez.tsbrewcraft.block.TsBlockEntities;
import com.trufflez.tsbrewcraft.recipe.KegProducts;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Objects;

public class KegBlockEntity extends BlockEntity {
    private int timeSinceSulfured;
    private int timeSinceSealed;
    private boolean sealed;
    private boolean lit;
    private boolean clean;
    private boolean hasProduct;
    private String product;
    private final DefaultedList<ItemStack> contents;
    
    
    public KegBlockEntity(BlockPos pos, BlockState state) {
        super(TsBlockEntities.KEG_BLOCKENTITY, pos, state);
        this.timeSinceSulfured = 0;
        this.timeSinceSealed = 0;
        this.sealed = false;
        this.lit = false;
        this.clean = false;
        this.hasProduct = false;
        this.product = KegProducts.NONE.toString();
        this.contents = DefaultedList.ofSize(4, ItemStack.EMPTY);
    }
    
    public boolean isSealed() { return this.sealed; }
    public boolean isLit() { return this.lit; }
    public boolean isClean() { return this.clean; }
    public boolean hasProduct() { return this.hasProduct; }
    public String getProduct() { return this.product; }
    public DefaultedList<ItemStack> getContents() { return this.contents; }
    
    public boolean addItem(ItemStack item) {
        if (!this.world.isClient()) {
            for (int i = 0; i < this.contents.size(); ++i) {
                ItemStack itemStack = this.contents.get(i);
                if (itemStack.isEmpty()) {
                    this.contents.set(i, item.split(1));
                    this.markChanged();
                    return true; // exits loop
                }
            }
            return false;
        }
        return false;
    }
    
    public void dropItems(World world, BlockPos pos, BlockState state, KegBlockEntity kegBlock) {
        DefaultedList<ItemStack> contents = kegBlock.getContents();
        boolean changed = false;
        
        for (int i = 0; i < contents.size(); ++i) { // loop through each item and drop them one by one
            ItemStack itemStack = kegBlock.contents.get(i);
            if (!itemStack.isEmpty()) {
                changed = true;
                ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), itemStack);
                kegBlock.contents.set(i, ItemStack.EMPTY);
            }
        }
        
        if (changed) markDirty(world, pos, state);
    }
    
    public void seal() {
        this.sealed = true;
        this.markDirty();
    }

    public void unseal() {
        this.sealed = false;
        this.clean = false;
        this.markDirty();
    }
    
    public void setProduct(KegProducts product) {
        this.product = product.toString();
        this.markDirty();
    }
    
    public void setHasProduct(boolean bl) {
        this.hasProduct = bl;
    }
    
    public void setClean(boolean bl) {
        this.clean = bl;
    }

    public void setLit(boolean bl) {
        this.lit = bl;
    }

    private void markChanged() {
        this.markDirty();
        Objects.requireNonNull(this.getWorld()).updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(), 3);
    }
    
    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.contents.clear();
        Inventories.readNbt(nbt, this.contents);
        this.timeSinceSulfured = nbt.getInt("timeSinceSulfured");
        this.timeSinceSealed = nbt.getInt("timeSinceSealed");
        this.sealed = nbt.getBoolean("sealed");
        this.lit = nbt.getBoolean("lit");
        this.clean = nbt.getBoolean("clean");
        this.hasProduct = nbt.getBoolean("hasProduct");
        this.product = nbt.getString("product");
    }
    
    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(new NbtCompound(), this.contents);
        nbt.putInt("timeSinceSulfured", this.timeSinceSulfured);
        nbt.putInt("timeSinceSealed", this.timeSinceSealed);
        nbt.putBoolean("sealed", this.sealed);
        nbt.putBoolean("lit", this.lit);
        nbt.putBoolean("clean", this.clean);
        nbt.putBoolean("hasProduct", this.hasProduct);
        nbt.putString("product", this.product);
    }
}
