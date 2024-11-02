package mod.traister101.sns.datagen.tfc.data;

public interface MetalData {

	static float specificHeatCapacity(final float baseHeatCapacity) {
		return 300 / baseHeatCapacity / 100_000;
	}

	static float ingotHeatCapacity(final float baseHeatCapacity) {
		return 1 / baseHeatCapacity;
	}

	float getMeltTemp();

	float specificHeatCapacity();

	float ingotHeatCapacity();
}