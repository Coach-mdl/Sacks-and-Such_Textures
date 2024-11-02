package mod.traister101.sns.datagen.tfc.data;

public enum DefaultMetal implements MetalData {
	BISMUTH(0.14F, 270),
	BISMUTH_BRONZE(0.35F, 985),
	BLACK_BRONZE(0.35F, 1070),
	BRONZE(0.35F, 950),
	BRASS(0.35F, 930),
	COPPER(0.35F, 1080),
	GOLD(0.6F, 1060),
	NICKEL(0.48F, 1453),
	ROSE_GOLD(0.35F, 960),
	SILVER(0.48F, 961),
	TIN(0.14F, 230),
	ZINC(0.21F, 420),
	STERLING_SILVER(0.35F, 950),
	WROUGHT_IRON(0.35F, 1535),
	CAST_IRON(0.35F, 1535),
	PIG_IRON(0.35F, 1535),
	STEEL(0.35F, 1540),
	BLACK_STEEL(0.35F, 1485),
	BLUE_STEEL(0.35F, 1540),
	RED_STEEL(0.35F, 1540),
	WEAK_STEEL(0.35F, 1540),
	WEAK_BLUE_STEEL(0.35F, 1540),
	WEAK_RED_STEEL(0.35F, 1540),
	HIGH_CARBON_STEEL(0.35F, 1540),
	HIGH_CARBON_BLACK_STEEL(0.35F, 1540),
	HIGH_CARBON_BLUE_STEEL(0.35F, 1540),
	HIGH_CARBON_RED_STEEL(0.35F, 1540),
	UNKNOWN_METAL(0.5F, 400);

	public final float meltTemp;
	private final float baseHeatCapacity;

	DefaultMetal(final float baseHeatCapacity, final float meltTemp) {
		this.baseHeatCapacity = baseHeatCapacity;
		this.meltTemp = meltTemp;
	}

	@Override
	public float getMeltTemp() {
		return this.meltTemp;
	}

	@Override
	public float specificHeatCapacity() {
		return MetalData.specificHeatCapacity(baseHeatCapacity);
	}

	@Override
	public float ingotHeatCapacity() {
		return MetalData.ingotHeatCapacity(baseHeatCapacity);
	}
}