package com.example.iterator_task4;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.scene.image.Image;
import javafx.util.Duration;
import model.ConcreteAggregate;

import java.io.File;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;

public class HelloController {

    public VBox Vbox;
    public Label label;
    @FXML
    private ImageView imageView;
    @FXML
    private Label selectedFolderLabel;

    private ConcreteAggregate slides;
    private Iterator<Image> imageIterator;
    private int currentIndex = 0;
    private int totalImages = 0;
    private Timeline slideshowTimeline;

    @FXML
    private void chooseFolder() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите файл изображения");

        // Установка фильтров для файлов изображений
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg");
        fileChooser.getExtensionFilters().add(filter);

        // Получение выбранного файла
        File selectedFile = fileChooser.showOpenDialog(Vbox.getScene().getWindow());

        if (selectedFile != null) {
            selectedFolderLabel.setText(selectedFile.getAbsolutePath());
            slides = new ConcreteAggregate(selectedFile.getParent());
            imageIterator = slides.getIterator();
            currentIndex = 0;

            totalImages = getTotalImagesCount();
            if (totalImages > 0) {
                showImage(currentIndex);
            } else {
                selectedFolderLabel.setText("Папка не содержит изображений.");
            }
        }
    }

    private void showImage(int index) {
        if (index >= 0 && index < totalImages) {
            imageIterator = slides.getIterator();
            for (int i = 0; i <= index; i++) {
                if (imageIterator.hasNext()) {
                    Image currentImage = imageIterator.next();
                    imageView.setImage(currentImage);
                }
            }
        }
    }

    @FXML
    private void nextSlide() {
        if (currentIndex < totalImages - 1) {
            currentIndex++;
            showImage(currentIndex);
        }
    }

    @FXML
    private void previousSlide() {
        if (currentIndex > 0) {
            currentIndex--;
            showImage(currentIndex);
        }
    }

    @FXML
    private void firstSlide() {
        currentIndex = 0;
        showImage(currentIndex);
    }

    @FXML
    private void lastSlide() {
        if (totalImages > 0) {
            currentIndex = totalImages - 1;
            showImage(currentIndex);
        }
    }

    // Метод для получения последнего индекса
    private int getTotalImagesCount() {
        int count = 0;
        imageIterator = slides.getIterator();
        while (imageIterator.hasNext()) {
            imageIterator.next();
            count++;
        }
        return count;
    }

    @FXML
    private void startSlideshow() {
        if (slideshowTimeline != null) {
            slideshowTimeline.stop(); // Остановить предыдущее слайд-шоу, если оно работает
        }

        slideshowTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            nextSlide(); // Перейти к следующему слайду
        }));
        slideshowTimeline.setCycleCount(Timeline.INDEFINITE);
        slideshowTimeline.play(); // Запустите слайд-шоу
    }

    @FXML
    private void stopSlideshow() {
        if (slideshowTimeline != null) {
            slideshowTimeline.stop();
        }
    }

    public void localclick(ActionEvent actionEvent) {
        Locale currentLocale = new Locale("en");
        ResourceBundle messages = ResourceBundle.getBundle("messages", currentLocale);
        label.setText(messages.getString("greeting"));
    }

    public void localclickRu(ActionEvent actionEvent) {
        Locale currentLocale = new Locale("ru");
        ResourceBundle messages = ResourceBundle.getBundle("messages", currentLocale);
        label.setText(messages.getString("greeting"));
    }
}