package org.zhadaev.enums;

public enum Tag {
    ORDER_DATE_TIME(1),
    ORDER_NUMBER(2),
    CUSTOMER_NAME(3),
    ITEM(4),
    ITEM_NAME(11),
    ITEM_PRICE(12),
    ITEMS_QUANTITY(13),
    ITEMS_SUM(14)
    ;

    Tag(int id) {
        this.id = (short) id;
    }

    private Short id;

    public Short getId() {
        return id;
    }

    public static Tag getById(short id) {
        for (Tag tag: values()) {
            if (tag.getId().equals(id)) {
                return tag;
            }
        }
        return null;
    }
}
