package com.demo.cook.base.event;

public class BusEvent {

    public static class PublishRecipeSuccess{}

    public static class PublishProductSuccess{}

    public static class ShoppingCartCount{
        public ShoppingCartCount(int count) {
            this.count = count;
        }

        private int count;

        public int getCount() {
            return count;
        }
    }

}
