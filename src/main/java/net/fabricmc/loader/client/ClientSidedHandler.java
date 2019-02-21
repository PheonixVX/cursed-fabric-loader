/*
 * Copyright 2016 FabricMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.fabricmc.loader.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.EnvironmentHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;

@SuppressWarnings("deprecation")
public final class ClientSidedHandler implements EnvironmentHandler {
	@Override
	public EnvType getEnvironmentType() {
		return EnvType.CLIENT;
	}

	@Override
	@Deprecated
	public PlayerEntity getClientPlayer() {
		return MinecraftClient.getInstance().player;
	}

	@Override
	public void runOnMainThread(Runnable runnable) {
		MinecraftClient.getInstance().execute(runnable);
	}

	@Override
	public MinecraftServer getServerInstance() {
		return MinecraftClient.getInstance().getServer();
	}
}