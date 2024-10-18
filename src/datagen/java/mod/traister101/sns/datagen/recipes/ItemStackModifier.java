package mod.traister101.sns.datagen.recipes;

import com.google.gson.*;

public interface ItemStackModifier {

	static ItemStackModifier simple(final String modifierId) {
		return () -> {
			final JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("type", modifierId);
			return jsonObject;
		};
	}

	JsonElement toJson();
}