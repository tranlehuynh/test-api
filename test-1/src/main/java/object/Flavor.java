package object;

public class Flavor {
    String name;
    Integer cpu;
    Integer memory;
    Integer disk;
    String volumeTypeId;
    Integer gpu;
    Integer bandwidth;
    String bandwidthUnit;
    String flavorId;
    Integer weight;
    String status;
    String zoneName;
    String zoneId;
    String priceKey;
    Integer price;
    String zoneStatus;

    public Flavor() {
    }

    public Flavor(String name, Integer cpu, Integer memory, Integer disk, String volumeTypeId, Integer gpu, Integer bandwidth, String bandwidthUnit, String flavorId, Integer weight, String status, String zoneName, String zoneId, String priceKey, Integer price, String zoneStatus) {
        this.name = name;
        this.cpu = cpu;
        this.memory = memory;
        this.disk = disk;
        this.volumeTypeId = volumeTypeId;
        this.gpu = gpu;
        this.bandwidth = bandwidth;
        this.bandwidthUnit = bandwidthUnit;
        this.flavorId = flavorId;
        this.weight = weight;
        this.status = status;
        this.zoneName = zoneName;
        this.zoneId = zoneId;
        this.priceKey = priceKey;
        this.price = price;
        this.zoneStatus = zoneStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCpu() {
        return cpu;
    }

    public void setCpu(Integer cpu) {
        this.cpu = cpu;
    }

    public Integer getMemory() {
        return memory;
    }

    public void setMemory(Integer memory) {
        this.memory = memory;
    }

    public Integer getDisk() {
        return disk;
    }

    public void setDisk(Integer disk) {
        this.disk = disk;
    }

    public String getVolumeTypeId() {
        return volumeTypeId;
    }

    public void setVolumeTypeId(String volumeTypeId) {
        this.volumeTypeId = volumeTypeId;
    }

    public Integer getGpu() {
        return gpu;
    }

    public void setGpu(Integer gpu) {
        this.gpu = gpu;
    }

    public Integer getBandwidth() {
        return bandwidth;
    }

    public void setBandwidth(Integer bandwidth) {
        this.bandwidth = bandwidth;
    }

    public String getBandwidthUnit() {
        return bandwidthUnit;
    }

    public void setBandwidthUnit(String bandwidthUnit) {
        this.bandwidthUnit = bandwidthUnit;
    }

    public String getFlavorId() {
        return flavorId;
    }

    public void setFlavorId(String flavorId) {
        this.flavorId = flavorId;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getPriceKey() {
        return priceKey;
    }

    public void setPriceKey(String priceKey) {
        this.priceKey = priceKey;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getZoneStatus() {
        return zoneStatus;
    }

    public void setZoneStatus(String zoneStatus) {
        this.zoneStatus = zoneStatus;
    }

    @Override
    public String toString() {
        return "ServerGeneralPurposeGetTypeAndPriceReponse{" +
                "name='" + name + '\'' +
                ", cpu=" + cpu +
                ", memory=" + memory +
                ", disk=" + disk +
                ", volumeTypeId='" + volumeTypeId + '\'' +
                ", gpu=" + gpu +
                ", bandwidth=" + bandwidth +
                ", bandwidthUnit='" + bandwidthUnit + '\'' +
                ", flavorId='" + flavorId + '\'' +
                ", weight=" + weight +
                ", status='" + status + '\'' +
                ", zoneName='" + zoneName + '\'' +
                ", zoneId='" + zoneId + '\'' +
                ", priceKey='" + priceKey + '\'' +
                ", price=" + price +
                '}';
    }
}
