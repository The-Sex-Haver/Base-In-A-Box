/*
 * Repository: Base In A Box
 * Module: IntegratedServerLoaderMixins
 * Author: The Sex Haver
 * Date: 3/16/2024
 * License: GPL V3
 * Description: Disables the silly experimental features warning when creating or loading a new world with this mod.
 */

package com.pyotr_bagration.base_in_a_box.mixins;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.server.integrated.IntegratedServerLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Environment(EnvType.CLIENT)
@Mixin(IntegratedServerLoader.class)
public final class IntegratedServerLoaderMixins {

    private IntegratedServerLoaderMixins() throws InstantiationException {
        throw new InstantiationException("Class \"IntegratedServerLoaderMixins\" cannot be instantiated.");
    }

    @ModifyVariable( method = "start(Lnet/minecraft/world/level/storage/LevelStorage$Session;Lcom/mojang/serialization/Dynamic;ZZLjava/lang/Runnable;)V", at = @At("HEAD"), argsOnly = true, index = 4)
    private boolean removeAdviceOnLoad(boolean original) {
        return false;
    }

    @ModifyVariable(method = "tryLoad", at = @At("HEAD"), argsOnly = true, index = 4)
    private static boolean removeAdviceOnCreation(boolean original) {
        return true;
    }

}
