package net.fabricmc.loader.entrypoint.minecraft;

import net.fabricmc.loader.entrypoint.EntrypointPatch;
import net.fabricmc.loader.entrypoint.EntrypointTransformer;
import net.fabricmc.loader.launch.common.FabricLauncher;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.util.ListIterator;
import java.util.function.Consumer;

public class EntrypointInfdev extends EntrypointPatch {

	public EntrypointInfdev(EntrypointTransformer transformer) {
		super(transformer);
	}

	@Override
	public void process (FabricLauncher launcher, Consumer<ClassNode> classEmitter) {
		try {
			String entryPoint = launcher.getEntrypoint();
			ClassNode mainClass = loadClass(launcher, entryPoint);

			MethodNode mainMethod = findMethod(mainClass, (method) -> method.name.equals("init"));
			if (mainMethod == null) {
				throw new RuntimeException("Could not find main method in " + entryPoint + "!");
			}

			ListIterator<AbstractInsnNode> it = mainMethod.instructions.iterator();
			moveBefore(it, Opcodes.RETURN);

			it.add(new InsnNode(Opcodes.ACONST_NULL));
			it.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "net/fabricmc/loader/entrypoint/applet/AppletMain", "hookGameDir", "(Ljava/io/File;)Ljava/io/File;", false));
			it.add(new VarInsnNode(Opcodes.ALOAD, 0));
			it.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "net/fabricmc/loader/entrypoint/minecraft/hooks/EntrypointClient", "start", "(Ljava/io/File;Ljava/lang/Object;)V", false));

			classEmitter.accept(mainClass);

			EntrypointTransformer.appletMainClass = entryPoint;
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
