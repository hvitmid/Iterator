package model;

import javafx.scene.image.Image;

import java.io.File;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ConcreteAggregate implements Aggregate {
    private String directoryPath;
    private File[] files;

    public ConcreteAggregate(String directoryPath) {
        this.files = new File(directoryPath).listFiles((dir, name) -> name.endsWith(".png") || name.endsWith(".jpg"));
    }

    @Override
    public Iterator<Image> getIterator() {
        return new ImageIterator();
    }

    private class ImageIterator implements Iterator<Image> {
        private int current = 0;

        @Override
        public boolean hasNext() {
            return files != null && current < files.length;
        }

        @Override
        public Image next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Image image = new Image(files[current].toURI().toString());
            current++;
            return image;
        }
    }
}