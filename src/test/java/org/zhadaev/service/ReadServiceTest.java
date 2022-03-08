package org.zhadaev.service;

import org.junit.Assert;
import org.junit.Test;
import org.zhadaev.exception.FileCorruptedException;
import org.zhadaev.model.Item;
import org.zhadaev.model.Order;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ReadServiceTest {

    private ReadService readService = new ReadService();

    @Test
    public void testReadData() throws IOException, FileCorruptedException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("data-1.bin");

        Order order = readService.readData(inputStream);

        List<Item> items = order.getItems();
        Item firstItem = items.get(0);

        Assert.assertEquals("2016-01-10T10:30:00", order.getDateTime());
        Assert.assertEquals(160004, order.getOrderNumber());
        Assert.assertEquals("ООО Ромашка", order.getCustomerName());

        Assert.assertEquals(2, items.size());
        Assert.assertEquals("Дырокол", firstItem.getName());
        Assert.assertEquals(20000, firstItem.getPrice());
        Assert.assertEquals(2, firstItem.getQuantity(), 0.001);
        Assert.assertEquals(40000, firstItem.getSum());
    }
}
