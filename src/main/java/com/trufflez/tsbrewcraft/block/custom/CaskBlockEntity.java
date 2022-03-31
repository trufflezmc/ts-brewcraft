package com.trufflez.tsbrewcraft.block.custom;

import com.trufflez.tsbrewcraft.block.TsBlockEntities;
import com.trufflez.tsbrewcraft.recipe.CaskProducts;
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

public class CaskBlockEntity extends BlockEntity {
    private int timeSinceSulfured;
    private int timeSinceSealed;
    private boolean open;
    
    private boolean hasProduct;
    private String product;
    
    private final DefaultedList<ItemStack> contents;
    
    
    public CaskBlockEntity(BlockPos pos, BlockState state) {
        super(TsBlockEntities.CASK_BLOCKENTITY, pos, state);
        this.contents = DefaultedList.ofSize(4, ItemStack.EMPTY);
        this.timeSinceSulfured = 0;
        this.timeSinceSealed = 0;
        this.open = true;
        this.hasProduct = false;
        this.product = CaskProducts.NONE.toString();
    }
    
    public DefaultedList<ItemStack> getContents() {
        return this.contents;
    }
    
    public boolean addItem(ItemStack item) {
        for(int i = 1; i < this.contents.size(); ++i) { // item position 0 is used for last item
            ItemStack itemStack = this.contents.get(i);
            if (itemStack.isEmpty()) {
                this.contents.set(i, item.split(1));
                this.markChanged();
                return true; // exits loop
            }
        }

        return false;
    }
    
    public void dropItems(World world, BlockPos pos, BlockState state, CaskBlockEntity caskBlock) {
        DefaultedList<ItemStack> contents = caskBlock.getContents();
        boolean changed = false;
        
        for (int i = 0; i < contents.size(); ++i) { // loop through each item and drop them one by one
            ItemStack itemStack = caskBlock.contents.get(i);
            if (!itemStack.isEmpty()) {
                changed = true;
                ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), itemStack);
                caskBlock.contents.set(i, ItemStack.EMPTY);
            }
        }
        
        if (changed) markDirty(world, pos, state);
    }
    
    public void setOpened() {
        this.open = true;
        this.markDirty();
    }

    public void setClosed() {
        // check orientation of cask. If it is on its side, dump the contents.
        this.open = false;
        this.markDirty();
    }

    private void markChanged() {
        this.markDirty();
        Objects.requireNonNull(this.getWorld()).updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(), 3);
    }
    
    public boolean getOpenedState() {
        return this.open;
    }
    
    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.contents.clear();
        Inventories.readNbt(nbt, this.contents);
        this.timeSinceSulfured = nbt.getInt("timeSinceSulfured");
        this.timeSinceSealed = nbt.getInt("timeSinceSealed");
        this.open = nbt.getBoolean("sealed");
        this.hasProduct = nbt.getBoolean("hasProduct");
        this.product = nbt.getString("product");
    }
    
    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(new NbtCompound(), this.contents);
        nbt.putInt("timeSinceSulfured", this.timeSinceSulfured);
        nbt.putInt("timeSinceSealed", this.timeSinceSealed);
        nbt.putBoolean("sealed", this.open);
        nbt.putBoolean("hasProduct", this.hasProduct);
        nbt.putString("product", this.product);
    }
}
