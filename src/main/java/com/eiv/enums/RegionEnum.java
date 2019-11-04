package com.eiv.enums;

public enum RegionEnum {
    NOROESTE("NOA"),
    NORDESTE("NOE"),
    PAMPEANA("PAM"),
    CUYO("CUY"),
    PATAGONIA("PAT");
    
    private String region;
    
    private RegionEnum(String region) {
        this.region = region;
    }

    public String getRegion() {
        return region;
    }
    
    public static RegionEnum of(String region) {
        
        if (region == null) {
            throw new IllegalArgumentException("La region no puede ser nula!");
        } else if (region.trim().equalsIgnoreCase("NOA")) {
            return NOROESTE;
        } else if (region.equalsIgnoreCase("NOE")) {
            return NORDESTE;
        } else if (region.trim().equalsIgnoreCase("PAM")) {
            return PAMPEANA;
        } else if (region.trim().equalsIgnoreCase("CUY")) {
            return CUYO;
        } else if (region.trim().equalsIgnoreCase("PATA")) {
            return PATAGONIA;
        } else {
            throw new IllegalArgumentException(
                    String.format("No se reconoce %s como una region!", region));
        }
    }
}
