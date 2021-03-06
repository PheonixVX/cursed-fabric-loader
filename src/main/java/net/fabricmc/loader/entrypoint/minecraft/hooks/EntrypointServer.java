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

package net.fabricmc.loader.entrypoint.minecraft.hooks;

import java.io.File;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.FabricLoader;

import io.github.minecraftcursedlegacy.api.ModPostInitializer;
import io.github.minecraftcursedlegacy.impl.Hacks;

public final class EntrypointServer {
	public static void start(File runDir, Object gameInstance) {
		if (runDir == null) {
			runDir = new File(".");
		}

		FabricLoader.preInit(runDir, gameInstance);
		EntrypointUtils.invoke("init", ModInitializer.class, ModInitializer::onInitialize);
		EntrypointUtils.invoke("server", DedicatedServerModInitializer.class, DedicatedServerModInitializer::onInitializeServer);
		Hacks.hack.run();
		EntrypointUtils.invoke("postInit", ModPostInitializer.class, ModPostInitializer::onPostInitialize);
	}
}
