package com.demo.cook.ui.publish.product.model.data;

public class ProductTag {
    private String typeId;

    private Integer level;

    private String typeName;

    private Integer parentId;

    private String imagePath;

    public ProductTag(String typeId, Integer level, String typeName, Integer parentId, String imagePath) {
        this.typeId = typeId;
        this.level = level;
        this.typeName = typeName;
        this.parentId = parentId;
        this.imagePath = imagePath;
    }

    public ProductTag() {
        super();
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId == null ? null : typeId.trim();
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath == null ? null : imagePath.trim();
    }
}