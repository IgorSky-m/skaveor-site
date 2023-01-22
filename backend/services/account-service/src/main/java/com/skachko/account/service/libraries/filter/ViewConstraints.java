package com.skachko.account.service.libraries.filter;

public class ViewConstraints {

    private ViewConstraints(){}

    public static final String FIELD_VIEW_FILTER_ID = "custom_FIELD_VIEW_FILTER_ID";

    public static class ViewLevel {

        private ViewLevel(){}

        public static final byte BASE = 10;
        public static final byte TABLE = 20;
        public static final byte DETAILED = 30;
    }

    public static class MatchMode {

        private MatchMode(){}

        public static final byte STRICT = 1_00;
        public static final byte HIERARCHICAL = 1_10;

    }

}
