package model;
import javafx.scene.image.Image;

import java.util.Iterator;

public interface Aggregate {
    Iterator<Image> getIterator();
}
