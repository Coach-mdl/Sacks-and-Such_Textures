from mcresources import ResourceManager

import constants


def generate(rm: ResourceManager):
    rm.item_tag("prevented_in_sacks", *constants.SACKS)
    rm.item_tag("allowed_in_farmer_sack", "#tfc:seeds")
