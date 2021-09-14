package com.jack.applications.database.resources;

public enum TMDBFilterKeys {
    API_KEY("api_key"),
    PAGE("page"),
    PRIMARY_RELEASE_DATE_GTE("primary_release_date.gte"),
    PRIMARY_RELEASE_DATE_LTE("primary_release_date.gte"),
    WITH_GENRES("with_genres"),
    SORT_BY("sort_by");

    public static TMDBFilterKeys getValue(String val) {
        for(TMDBFilterKeys filter : TMDBFilterKeys.values()) {
            if (filter.getTag().equals(val)) {
                return filter;
            }
        }

        return null;
    }

    private final String tag;

    TMDBFilterKeys(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

}
