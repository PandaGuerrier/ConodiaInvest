package fr.pandaguerrier.conodiajob.utils;


import org.bukkit.Material;

public enum JobsEnum {
    MINEUR("Mineur", Material.DIAMOND_PICKAXE),
    FARMEUR("Farmeur", Material.DIAMOND_HOE),
    BUCHERON("Bucheron", Material.DIAMOND_AXE),
    CHASSEUR("Chasseur", Material.DIAMOND_SWORD),
    CONSTRUCTEUR("Constructeur", Material.BRICK);

    private final String jobName;
    private final Material material;

    JobsEnum(String jobName, Material material) {
        this.jobName = jobName;
        this.material = material;
    }

    public String getJobName() { return this.jobName; }
    public Material getJobMaterial() { return this.material; }

}
