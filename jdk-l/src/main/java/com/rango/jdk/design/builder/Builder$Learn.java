package com.rango.jdk.design.builder;

public class Builder$Learn {

    public static void main(String[] args) {
    }

    private String id;

    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Builder$Learn() {
    }

    public Builder$Learn(String id, String name) {
        this.id = id;
        this.name = name;
    }


    public static class Builder {

        private String id;

        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder$Learn build() {
            return new Builder$Learn(this.id, this.name);
        }
    }
}
