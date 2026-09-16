/*
 * This class is distributed as part of the Super Sargasso Sea mod.
 * Complete source on GitHub:
 * https://github.com/Outurnate/sargasso
 *
 * Super Sargasso Sea is free software and distributed
 * under the MIT License: https://opensource.org/license/mit
 *
 * © 2026 the authors of the Super Sargasso Sea mod
 */
package com.outurnate.sargasso.datagen.util;

import com.outurnate.sargasso.SuperSargassoSea;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.zip.GZIPOutputStream;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;

@EventBusSubscriber(modid = SuperSargassoSea.MODID, value = Dist.CLIENT)
public class JavaClientCodegen {
	private static final boolean DO_CLIENT_CODEGEN = false;

	private static byte[] compress(byte[] data) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try (GZIPOutputStream gzip = new GZIPOutputStream(baos)) {
			gzip.write(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return baos.toByteArray();
	}

	@SubscribeEvent
	public static void onLogin(ClientPlayerNetworkEvent.LoggingIn event) {
		Path outputPath = Paths
				.get(Minecraft.getInstance().gameDirectory.toPath().toString(), "FontWidths.java");
		if (DO_CLIENT_CODEGEN && !outputPath.toFile().exists()) {
			StringBuilder javaSource = new StringBuilder();
			int maxCodePoint = 0xFFFF; // we don't need the unicode astral planes
			javaSource.append(
					"package com.outurnate.sargasso.datagen;\n" + //
							"\n" + //
							"import java.io.ByteArrayInputStream;\n" + //
							"import java.io.IOException;\n" + //
							"import java.util.Base64;\n" + //
							"import java.util.zip.GZIPInputStream;\n" + //
							"\n" + //
							"public class FontWidths {\n" + //
							"  public static final byte[] WIDTHS;\n" + //
							"  static {\n" + //
							"    try {\n" + //
							"      WIDTHS = new GZIPInputStream(new ByteArrayInputStream(Base64.getDecoder().decode(\"");
			byte[] widths = new byte[maxCodePoint + 1];
			Font font = Minecraft.getInstance().font;
			for (int cp = 0; cp <= maxCodePoint; cp++) {
				widths[cp] = (byte) font.width(new String(Character.toChars(cp)));
			}
			javaSource.append(Base64.getEncoder().encodeToString(compress(widths)));
			javaSource.append(
					"\")))\n" + //
							"      .readAllBytes();\n" + //
							"    } catch (IOException e) {\n" + //
							"      throw new ExceptionInInitializerError(e);\n" + //
							"    }\n" + //
							"  }\n" + //
							"}");
			try {
				Files.writeString(outputPath, javaSource.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
