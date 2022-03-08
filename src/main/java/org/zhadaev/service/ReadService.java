package org.zhadaev.service;

import org.zhadaev.enums.Tag;
import org.zhadaev.exception.FileCorruptedException;
import org.zhadaev.model.Order;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class ReadService {

    public Order readData(InputStream is) throws IOException, FileCorruptedException {
        DataInputStream dis = new DataInputStream(is);

        Order order = new Order();
        while (dis.available() > 0) {
            byte[] tagArray = getByteArrayFromDataInputStream(dis, 2);
            short tagId = ByteBuffer.wrap(tagArray).order(ByteOrder.LITTLE_ENDIAN).getShort();
            Tag tag = Tag.getById(tagId);

            if (tag == null) {
                throw new FileCorruptedException();
            }

            if (Tag.ITEM.equals(tag)) {
                order.addNewItem();
                dis.skipBytes(2);
            } else {
                byte[] lengthArray = getByteArrayFromDataInputStream(dis, 2);
                int length = ByteBuffer.wrap(lengthArray).order(ByteOrder.LITTLE_ENDIAN).getShort();
                byte[] value = getByteArrayFromDataInputStream(dis, length);
                setValue(order, tag, value);
            }
        }

        return order;
    }

    private byte[] getByteArrayFromDataInputStream(DataInputStream dis, int length) throws IOException {
        byte[] array = new byte[length];
        for (int i = 0; i < length; i++) {
            array[i] = (byte) dis.read();
        }
        return array;
    }

    private void setValue(Order order, Tag tag, byte[] value) throws UnsupportedEncodingException {
        switch (tag) {
            case ORDER_DATE_TIME:
                int seconds = ByteBuffer.wrap(Arrays.copyOf(value, 4)).order(ByteOrder.LITTLE_ENDIAN).getInt();
                String date = DateTimeFormatter.ISO_LOCAL_DATE_TIME
                                                .withZone(ZoneId.from(ZoneOffset.UTC))
                                                .format(Instant.ofEpochSecond(seconds));
                order.setDateTime(date);
                break;

            case ORDER_NUMBER:
                order.setOrderNumber(getLong(value));
                break;

            case CUSTOMER_NAME:
                order.setCustomerName(getStringCP886(value));
                break;

            case ITEM_NAME:
                order.getLastItem().setName(getStringCP886(value));
                break;

            case ITEM_PRICE:
                order.getLastItem().setPrice(getLong(value));
                break;

            case ITEMS_QUANTITY:
                if (value.length < 2) {
                    break;
                }
                double point = Math.pow(10, (double) value[0]);
                value = Arrays.copyOfRange(value, 1, value.length);
                double quantity = (double) getLong(value);
                order.getLastItem().setQuantity(quantity/point);
                break;

            case ITEMS_SUM:
                order.getLastItem().setSum(getLong(value));
        }
    }

    private long getLong(byte[] array) {
        return ByteBuffer.wrap(Arrays.copyOf(array, 8)).order(ByteOrder.LITTLE_ENDIAN).getLong();
    }

    private String getStringCP886(byte[] array) throws UnsupportedEncodingException {
        return new String(array, "CP866");
    }

}
