package net.optifine.Modules.movement;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.optifine.Modules.ModuleType;
import net.optifine.Modules.Module;
import net.optifine.Values.Mode;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Scaffold extends Module {
    ArrayList<String> sites = new ArrayList<String>();
    public int godBridgeTimer;
    private Mode<Enum> mode = new Mode("Mode", "mode", (Enum[]) ScaffoldMode.values(), (Enum) ScaffoldMode.Legit);
    public Scaffold() {
        super("Scaffold", Keyboard.KEY_NONE, ModuleType.Movement,"Make you bridge faster");
        this.addValues(this.mode);
        Chinese="搭路辅助";
    }
    static enum ScaffoldMode {
        Legit,
        GodBridge
    }
    public Block getBlock(BlockPos pos) {
        return mc.theWorld.getBlockState(pos).getBlock();
    }

    public Block getBlockUnderPlayer(EntityPlayer player) {
        return getBlock(new BlockPos(player.posX, player.posY - 1.0d, player.posZ));
    }

    @SubscribeEvent
    public void onTick(TickEvent.PlayerTickEvent event) {
        if(this.mode.getValue() == ScaffoldMode.Legit){
            if (getBlockUnderPlayer(mc.thePlayer) instanceof BlockAir) {
                if (mc.thePlayer.onGround) {
                    KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), true);
                }
            } else {
                if (mc.thePlayer.onGround) {
                    KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), false);
                }
            }
        } else if (this.mode.getValue() == ScaffoldMode.GodBridge) {
            doBridge();
        }
    }

    public void enable() {
        mc.thePlayer.setSneaking(false);
        super.enable();
    }

    public void disable() {
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), false);
        super.disable();
    }


    void doBridge(){
        if (godBridgeTimer > 0) {
            ReflectionHelper.setPrivateValue(Minecraft.class, mc, 0, "rightClickDelayTimer", "field_71467_ac");
            godBridgeTimer--;
        }

        if (mc.theWorld == null || mc.thePlayer == null) return;
        WorldClient world = mc.theWorld;
        EntityPlayerSP player = mc.thePlayer;
        MovingObjectPosition movingObjectPosition = player.rayTrace(mc.playerController.getBlockReachDistance(), 1);
        boolean isKeyUseDown = false;
        int keyCode = mc.gameSettings.keyBindUseItem.getKeyCode();
        if (keyCode >= 0) {
            isKeyUseDown = Keyboard.isKeyDown(keyCode);
        } else {
            isKeyUseDown = Mouse.isButtonDown(keyCode + 100);
        }
        if (movingObjectPosition != null && movingObjectPosition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && movingObjectPosition.sideHit == EnumFacing.UP && isKeyUseDown ) {

            ItemStack itemstack = player.inventory.getCurrentItem();
            int i = itemstack != null ? itemstack.stackSize : 0;

            if (itemstack != null && itemstack.getItem() instanceof ItemBlock)
            {
                ItemBlock itemblock = (ItemBlock)itemstack.getItem();

                if (!itemblock.canPlaceBlockOnSide((net.minecraft.world.World) world, movingObjectPosition.getBlockPos(), movingObjectPosition.sideHit, (EntityPlayer) player, itemstack))
                {
                    BlockPos blockPos = movingObjectPosition.getBlockPos();
                    IBlockState blockState = world.getBlockState(blockPos);
                    AxisAlignedBB axisalignedbb = blockState.getBlock().getSelectedBoundingBox(world, blockPos);

                    if (axisalignedbb == null || world.isAirBlock(blockPos)) return;

                    double x1, x2, y1, y2, z1, z2;
                    Vec3 targetVec3 = null;
                    Vec3 eyeVec3 = player.getPositionEyes((float) 1);
                    x1 = axisalignedbb.minX;
                    x2 = axisalignedbb.maxX;
                    y1 = axisalignedbb.minY;
                    y2 = axisalignedbb.maxY;
                    z1 = axisalignedbb.minZ;
                    z2 = axisalignedbb.maxZ;
                    class Data implements Comparable<Data> {
                        public BlockPos blockPos;
                        public EnumFacing enumFacing;
                        public double cost;
                        public Data( BlockPos blockPos, EnumFacing enumFacing, double cost ) {
                            this.blockPos = blockPos;
                            this.enumFacing = enumFacing;
                            this.cost = cost;
                        }
                        @Override
                        public int compareTo(Data data) {
                            return (this.cost - data.cost) > 0 ? -1 : (this.cost - data.cost) < 0 ? 1 : 0 ;
                        }

                    }

                    List<Data> list = new ArrayList<Data>();


                    if ( x1 <= eyeVec3.xCoord && eyeVec3.xCoord <= x2 && y1 <= eyeVec3.yCoord && eyeVec3.yCoord <= y2 && z1 <= eyeVec3.zCoord && eyeVec3.zCoord <= z2) {
                    } else {
                        double xCost = Math.abs(eyeVec3.xCoord - 0.5*(axisalignedbb.minX+axisalignedbb.maxX));
                        double yCost = Math.abs(eyeVec3.yCoord - 0.5*(axisalignedbb.minY+axisalignedbb.maxY));
                        double zCost = Math.abs(eyeVec3.zCoord - 0.5*(axisalignedbb.minZ+axisalignedbb.maxZ));
                        double sumCost= xCost + yCost + zCost;
                        if ( eyeVec3.xCoord < x1 ) {
                            list.add(new Data( blockPos.west(), EnumFacing.WEST, xCost ));
                        } else if ( eyeVec3.xCoord > x2 ) {
                            list.add(new Data( blockPos.east(), EnumFacing.EAST, xCost));
                        }
                        if ( eyeVec3.zCoord < z1 ) {
                            list.add(new Data( blockPos.north(), EnumFacing.NORTH, zCost ));
                        } else if ( eyeVec3.zCoord > z2 ) {
                            list.add(new Data( blockPos.south(), EnumFacing.SOUTH, zCost ));
                        }
                        Collections.sort(list);
                        double border = 0.05;
                        double x = MathHelper.clamp_double(eyeVec3.xCoord, x1+border, x2-border);
                        double y = MathHelper.clamp_double(eyeVec3.yCoord, y1+border, y2-border);
                        double z = MathHelper.clamp_double(eyeVec3.zCoord, z1+border, z2-border);
                        for (Data data : list) {
                            if (!world.isAirBlock(data.blockPos)) continue;
                            if (data.enumFacing == EnumFacing.WEST || data.enumFacing == EnumFacing.EAST) {
                                x = MathHelper.clamp_double(eyeVec3.xCoord, x1, x2);
                            } else if (data.enumFacing == EnumFacing.UP || data.enumFacing == EnumFacing.DOWN) {
                                y = MathHelper.clamp_double(eyeVec3.yCoord, y1, y2);
                            } else {
                                z = MathHelper.clamp_double(eyeVec3.zCoord, z1, z2);
                            }
                            targetVec3 = new Vec3(x,y,z);
                            break;
                        }

                        if (targetVec3 != null) {
                            double d0 = targetVec3.xCoord - eyeVec3.xCoord;
                            double d1 = targetVec3.yCoord - eyeVec3.yCoord;
                            double d2 = targetVec3.zCoord - eyeVec3.zCoord;
                            double d3 = MathHelper.sqrt_double(d0 * d0 + d2 * d2);
                            float f = (float)(MathHelper.atan2(d2, d0) * 180.0D / Math.PI) - 90.0F;
                            float f1 = (float)(-(MathHelper.atan2(d1, d3) * 180.0D / Math.PI));
                            float f2, f3;
                            f2 = player.rotationYaw;
                            f3 = player.rotationPitch;
                            player.rotationYaw = f;
                            player.rotationPitch = f1;
                            MovingObjectPosition movingObjectPosition1 = player.rayTrace(mc.playerController.getBlockReachDistance(), 1);
                            if (movingObjectPosition1.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && movingObjectPosition1.getBlockPos().getX()==blockPos.getX() && movingObjectPosition1.getBlockPos().getY()==blockPos.getY() && movingObjectPosition1.getBlockPos().getZ()==blockPos.getZ()) {
                                if (mc.playerController.onPlayerRightClick(player, mc.theWorld, itemstack, blockPos, movingObjectPosition1.sideHit, movingObjectPosition1.hitVec))
                                {
                                    player.swingItem();
                                }
                                if (itemstack == null) {
                                } else if (itemstack.stackSize == 0) {
                                    player.inventory.mainInventory[player.inventory.currentItem] = null;
                                }
                                else if (itemstack.stackSize != i || mc.playerController.isInCreativeMode())
                                {
                                    mc.entityRenderer.itemRenderer.resetEquippedProgress();
                                }
                            }
                            player.rotationYaw = f2;
                            player.rotationPitch = f3;
                            double pitchDelta = 2.5;
                            double targetPitch = 75.5;
                            if (targetPitch - pitchDelta < player.rotationPitch && player.rotationPitch < targetPitch + pitchDelta) {
                                double mod = player.rotationYaw % 45.0;
                                if (mod < 0) {mod += 45.0;}
                                double delta = 5.0;

                                if (mod < delta) {
                                    player.rotationYaw -= mod;
                                    player.rotationPitch = (float) targetPitch;
                                } else if (45.0-mod < delta) {
                                    player.rotationYaw += 45.0-mod;
                                    player.rotationPitch = (float) targetPitch;
                                }
                            }

                            ReflectionHelper.setPrivateValue(Minecraft.class, mc, new Integer(1), new String[] {"rightClickDelayTimer", "field_71467_ac"});
                            godBridgeTimer = 10;

                        }
                    }
                }
            }

        }
    }


}
