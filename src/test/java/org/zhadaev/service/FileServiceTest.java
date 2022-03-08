package org.zhadaev.service;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileServiceTest {

    private FileService fileService = new FileService();

    @Test
    public void testFindFile() throws FileNotFoundException {
        File file = fileService.findFile(getClass().getClassLoader().getResource("data-1.bin").getFile());
        Assert.assertTrue(file.exists());
    }

    @Test
    public void testFindNonexistentFile() {
        Assert.assertThrows(FileNotFoundException.class, () -> fileService.findFile("nonexistent-file.bin"));
    }

    @Test
    public void testGetWritableFile() throws IOException {
        File file = File.createTempFile("tmp", null);
        String path = file.getPath();
        String name = file.getName();

        File file1 = fileService.getWritableFile(path);
        File file2 = fileService.getWritableFile(name);

        Assert.assertEquals(path, file1.getPath());
        Assert.assertNotEquals(path, file2.getPath());

        file.delete();
        file1.delete();
        file2.delete();
    }
}
