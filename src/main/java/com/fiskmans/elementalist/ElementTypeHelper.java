package com.fiskmans.elementalist;

public class ElementTypeHelper {
    public static ElementType InvertElement(ElementType type) {
        switch(type)
        {
            case NATURE:
                return ElementType.ENDER;
            case ENDER:
                return ElementType.NATURE;
        }
        return null;
    }
}
